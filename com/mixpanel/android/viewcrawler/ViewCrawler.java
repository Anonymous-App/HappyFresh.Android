package com.mixpanel.android.viewcrawler;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.JsonWriter;
import android.util.Log;
import android.util.Pair;
import com.mixpanel.android.mpmetrics.MPConfig;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.mpmetrics.MixpanelAPI.People;
import com.mixpanel.android.mpmetrics.ResourceReader.Ids;
import com.mixpanel.android.mpmetrics.SuperPropertyUpdate;
import com.mixpanel.android.mpmetrics.Tweaks;
import com.mixpanel.android.mpmetrics.Tweaks.OnTweakDeclaredListener;
import com.mixpanel.android.util.JSONUtils;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.net.ssl.SSLSocketFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@TargetApi(16)
public class ViewCrawler
  implements UpdatesFromMixpanel, TrackingDebug, ViewVisitor.OnLayoutErrorListener
{
  private static final int EMULATOR_CONNECT_ATTEMPT_INTERVAL_MILLIS = 30000;
  private static final String LOGTAG = "MixpanelAPI.ViewCrawler";
  private static final int MESSAGE_CONNECT_TO_EDITOR = 1;
  private static final int MESSAGE_EVENT_BINDINGS_RECEIVED = 5;
  private static final int MESSAGE_HANDLE_EDITOR_BINDINGS_RECEIVED = 6;
  private static final int MESSAGE_HANDLE_EDITOR_CHANGES_CLEARED = 10;
  private static final int MESSAGE_HANDLE_EDITOR_CHANGES_RECEIVED = 3;
  private static final int MESSAGE_HANDLE_EDITOR_CLOSED = 8;
  private static final int MESSAGE_HANDLE_EDITOR_TWEAKS_RECEIVED = 11;
  private static final int MESSAGE_INITIALIZE_CHANGES = 0;
  private static final int MESSAGE_SEND_DEVICE_INFO = 4;
  private static final int MESSAGE_SEND_EVENT_TRACKED = 7;
  private static final int MESSAGE_SEND_LAYOUT_ERROR = 12;
  private static final int MESSAGE_SEND_STATE_FOR_EDITING = 2;
  private static final int MESSAGE_VARIANTS_RECEIVED = 9;
  private static final String SHARED_PREF_BINDINGS_KEY = "mixpanel.viewcrawler.bindings";
  private static final String SHARED_PREF_CHANGES_KEY = "mixpanel.viewcrawler.changes";
  private static final String SHARED_PREF_EDITS_FILE = "mixpanel.viewcrawler.changes";
  private final MPConfig mConfig;
  private final Map<String, String> mDeviceInfo;
  private final DynamicEventTracker mDynamicEventTracker;
  private final EditState mEditState;
  private final ViewCrawlerHandler mMessageThreadHandler;
  private final MixpanelAPI mMixpanel;
  private final float mScaledDensity;
  private final Tweaks mTweaks;
  
  public ViewCrawler(Context paramContext, String paramString, MixpanelAPI paramMixpanelAPI, Tweaks paramTweaks)
  {
    this.mConfig = MPConfig.getInstance(paramContext);
    this.mEditState = new EditState();
    this.mTweaks = paramTweaks;
    this.mDeviceInfo = paramMixpanelAPI.getDeviceInfo();
    this.mScaledDensity = Resources.getSystem().getDisplayMetrics().scaledDensity;
    ((Application)paramContext.getApplicationContext()).registerActivityLifecycleCallbacks(new LifecycleCallbacks());
    paramTweaks = new HandlerThread(ViewCrawler.class.getCanonicalName());
    paramTweaks.setPriority(10);
    paramTweaks.start();
    this.mMessageThreadHandler = new ViewCrawlerHandler(paramContext, paramString, paramTweaks.getLooper(), this);
    this.mDynamicEventTracker = new DynamicEventTracker(paramMixpanelAPI, this.mMessageThreadHandler);
    this.mMixpanel = paramMixpanelAPI;
    this.mTweaks.addOnTweakDeclaredListener(new Tweaks.OnTweakDeclaredListener()
    {
      public void onTweakDeclared()
      {
        Message localMessage = ViewCrawler.this.mMessageThreadHandler.obtainMessage(4);
        ViewCrawler.this.mMessageThreadHandler.sendMessage(localMessage);
      }
    });
  }
  
  public Tweaks getTweaks()
  {
    return this.mTweaks;
  }
  
  public void onLayoutError(ViewVisitor.LayoutErrorMessage paramLayoutErrorMessage)
  {
    Message localMessage = this.mMessageThreadHandler.obtainMessage();
    localMessage.what = 12;
    localMessage.obj = paramLayoutErrorMessage;
    this.mMessageThreadHandler.sendMessage(localMessage);
  }
  
  public void reportTrack(String paramString)
  {
    Message localMessage = this.mMessageThreadHandler.obtainMessage();
    localMessage.what = 7;
    localMessage.obj = paramString;
    this.mMessageThreadHandler.sendMessage(localMessage);
  }
  
  public void setEventBindings(JSONArray paramJSONArray)
  {
    Message localMessage = this.mMessageThreadHandler.obtainMessage(5);
    localMessage.obj = paramJSONArray;
    this.mMessageThreadHandler.sendMessage(localMessage);
  }
  
  public void setVariants(JSONArray paramJSONArray)
  {
    Message localMessage = this.mMessageThreadHandler.obtainMessage(9);
    localMessage.obj = paramJSONArray;
    this.mMessageThreadHandler.sendMessage(localMessage);
  }
  
  public void startUpdates()
  {
    this.mMessageThreadHandler.start();
    this.mMessageThreadHandler.sendMessage(this.mMessageThreadHandler.obtainMessage(0));
  }
  
  private class Editor
    implements EditorConnection.Editor
  {
    private Editor() {}
    
    public void bindEvents(JSONObject paramJSONObject)
    {
      Message localMessage = ViewCrawler.this.mMessageThreadHandler.obtainMessage(6);
      localMessage.obj = paramJSONObject;
      ViewCrawler.this.mMessageThreadHandler.sendMessage(localMessage);
    }
    
    public void cleanup()
    {
      Message localMessage = ViewCrawler.this.mMessageThreadHandler.obtainMessage(8);
      ViewCrawler.this.mMessageThreadHandler.sendMessage(localMessage);
    }
    
    public void clearEdits(JSONObject paramJSONObject)
    {
      Message localMessage = ViewCrawler.this.mMessageThreadHandler.obtainMessage(10);
      localMessage.obj = paramJSONObject;
      ViewCrawler.this.mMessageThreadHandler.sendMessage(localMessage);
    }
    
    public void performEdit(JSONObject paramJSONObject)
    {
      Message localMessage = ViewCrawler.this.mMessageThreadHandler.obtainMessage(3);
      localMessage.obj = paramJSONObject;
      ViewCrawler.this.mMessageThreadHandler.sendMessage(localMessage);
    }
    
    public void sendDeviceInfo()
    {
      Message localMessage = ViewCrawler.this.mMessageThreadHandler.obtainMessage(4);
      ViewCrawler.this.mMessageThreadHandler.sendMessage(localMessage);
    }
    
    public void sendSnapshot(JSONObject paramJSONObject)
    {
      Message localMessage = ViewCrawler.this.mMessageThreadHandler.obtainMessage(2);
      localMessage.obj = paramJSONObject;
      ViewCrawler.this.mMessageThreadHandler.sendMessage(localMessage);
    }
    
    public void setTweaks(JSONObject paramJSONObject)
    {
      Message localMessage = ViewCrawler.this.mMessageThreadHandler.obtainMessage(11);
      localMessage.obj = paramJSONObject;
      ViewCrawler.this.mMessageThreadHandler.sendMessage(localMessage);
    }
  }
  
  private class EmulatorConnector
    implements Runnable
  {
    private volatile boolean mStopped = true;
    
    public EmulatorConnector() {}
    
    public void run()
    {
      if (!this.mStopped)
      {
        Message localMessage = ViewCrawler.this.mMessageThreadHandler.obtainMessage(1);
        ViewCrawler.this.mMessageThreadHandler.sendMessage(localMessage);
      }
      ViewCrawler.this.mMessageThreadHandler.postDelayed(this, 30000L);
    }
    
    public void start()
    {
      this.mStopped = false;
      ViewCrawler.this.mMessageThreadHandler.post(this);
    }
    
    public void stop()
    {
      this.mStopped = true;
      ViewCrawler.this.mMessageThreadHandler.removeCallbacks(this);
    }
  }
  
  private class LifecycleCallbacks
    implements Application.ActivityLifecycleCallbacks, FlipGesture.OnFlipGestureListener
  {
    private final ViewCrawler.EmulatorConnector mEmulatorConnector = new ViewCrawler.EmulatorConnector(ViewCrawler.this);
    private final FlipGesture mFlipGesture = new FlipGesture(this);
    
    public LifecycleCallbacks() {}
    
    private void installConnectionSensor(Activity paramActivity)
    {
      if ((isInEmulator()) && (!ViewCrawler.this.mConfig.getDisableEmulatorBindingUI())) {
        this.mEmulatorConnector.start();
      }
      while (ViewCrawler.this.mConfig.getDisableGestureBindingUI()) {
        return;
      }
      paramActivity = (SensorManager)paramActivity.getSystemService("sensor");
      Sensor localSensor = paramActivity.getDefaultSensor(1);
      paramActivity.registerListener(this.mFlipGesture, localSensor, 3);
    }
    
    private boolean isInEmulator()
    {
      if (!Build.HARDWARE.equals("goldfish")) {}
      while ((!Build.BRAND.startsWith("generic")) || (!Build.DEVICE.startsWith("generic")) || (!Build.PRODUCT.contains("sdk")) || (!Build.MODEL.toLowerCase(Locale.US).contains("sdk"))) {
        return false;
      }
      return true;
    }
    
    private void uninstallConnectionSensor(Activity paramActivity)
    {
      if ((isInEmulator()) && (!ViewCrawler.this.mConfig.getDisableEmulatorBindingUI())) {
        this.mEmulatorConnector.stop();
      }
      while (ViewCrawler.this.mConfig.getDisableGestureBindingUI()) {
        return;
      }
      ((SensorManager)paramActivity.getSystemService("sensor")).unregisterListener(this.mFlipGesture);
    }
    
    public void onActivityCreated(Activity paramActivity, Bundle paramBundle) {}
    
    public void onActivityDestroyed(Activity paramActivity) {}
    
    public void onActivityPaused(Activity paramActivity)
    {
      ViewCrawler.this.mEditState.remove(paramActivity);
      if (ViewCrawler.this.mEditState.isEmpty()) {
        uninstallConnectionSensor(paramActivity);
      }
    }
    
    public void onActivityResumed(Activity paramActivity)
    {
      installConnectionSensor(paramActivity);
      ViewCrawler.this.mEditState.add(paramActivity);
    }
    
    public void onActivitySaveInstanceState(Activity paramActivity, Bundle paramBundle) {}
    
    public void onActivityStarted(Activity paramActivity) {}
    
    public void onActivityStopped(Activity paramActivity) {}
    
    public void onFlipGesture()
    {
      Message localMessage = ViewCrawler.this.mMessageThreadHandler.obtainMessage(1);
      ViewCrawler.this.mMessageThreadHandler.sendMessage(localMessage);
    }
  }
  
  private static class VariantChange
  {
    public final String activityName;
    public final JSONObject change;
    public final Pair<Integer, Integer> variantId;
    
    public VariantChange(String paramString, JSONObject paramJSONObject, Pair<Integer, Integer> paramPair)
    {
      this.activityName = paramString;
      this.change = paramJSONObject;
      this.variantId = paramPair;
    }
  }
  
  private static class VariantTweak
  {
    public final JSONObject tweak;
    public final Pair<Integer, Integer> variantId;
    
    public VariantTweak(JSONObject paramJSONObject, Pair<Integer, Integer> paramPair)
    {
      this.tweak = paramJSONObject;
      this.variantId = paramPair;
    }
  }
  
  private class ViewCrawlerHandler
    extends Handler
  {
    private final Context mContext;
    private final List<String> mEditorAssetUrls;
    private final Map<String, Pair<String, JSONObject>> mEditorChanges;
    private EditorConnection mEditorConnection;
    private final List<Pair<String, JSONObject>> mEditorEventBindings;
    private final List<JSONObject> mEditorTweaks;
    private final ImageStore mImageStore;
    private final List<ViewCrawler.VariantChange> mPersistentChanges;
    private final List<Pair<String, JSONObject>> mPersistentEventBindings;
    private final List<ViewCrawler.VariantTweak> mPersistentTweaks;
    private final EditProtocol mProtocol;
    private final Set<Pair<Integer, Integer>> mSeenExperiments;
    private ViewSnapshot mSnapshot;
    private final Lock mStartLock;
    private final String mToken;
    
    public ViewCrawlerHandler(Context paramContext, String paramString, Looper paramLooper, ViewVisitor.OnLayoutErrorListener paramOnLayoutErrorListener)
    {
      super();
      this.mContext = paramContext;
      this.mToken = paramString;
      this.mSnapshot = null;
      paramString = ViewCrawler.this.mConfig.getResourcePackageName();
      this$1 = paramString;
      if (paramString == null) {
        this$1 = paramContext.getPackageName();
      }
      this$1 = new ResourceReader.Ids(ViewCrawler.this, paramContext);
      this.mImageStore = new ImageStore(paramContext);
      this.mProtocol = new EditProtocol(ViewCrawler.this, this.mImageStore, paramOnLayoutErrorListener);
      this.mEditorChanges = new HashMap();
      this.mEditorTweaks = new ArrayList();
      this.mEditorAssetUrls = new ArrayList();
      this.mEditorEventBindings = new ArrayList();
      this.mPersistentChanges = new ArrayList();
      this.mPersistentTweaks = new ArrayList();
      this.mPersistentEventBindings = new ArrayList();
      this.mSeenExperiments = new HashSet();
      this.mStartLock = new ReentrantLock();
      this.mStartLock.lock();
    }
    
    private void applyVariantsAndEventBindings()
    {
      Object localObject4 = new ArrayList();
      Object localObject3 = new HashSet();
      int j = this.mPersistentChanges.size();
      int i = 0;
      Object localObject5;
      for (;;)
      {
        if (i < j)
        {
          ViewCrawler.VariantChange localVariantChange = (ViewCrawler.VariantChange)this.mPersistentChanges.get(i);
          try
          {
            localObject5 = this.mProtocol.readEdit(localVariantChange.change);
            ((List)localObject4).add(new Pair(localVariantChange.activityName, ((EditProtocol.Edit)localObject5).visitor));
            if (!this.mSeenExperiments.contains(localVariantChange.variantId)) {
              ((Set)localObject3).add(localVariantChange.variantId);
            }
            i += 1;
          }
          catch (EditProtocol.CantGetEditAssetsException localCantGetEditAssetsException1)
          {
            for (;;)
            {
              Log.v("MixpanelAPI.ViewCrawler", "Can't load assets for an edit, won't apply the change now", localCantGetEditAssetsException1);
            }
          }
          catch (EditProtocol.InapplicableInstructionsException localInapplicableInstructionsException1)
          {
            for (;;)
            {
              Log.i("MixpanelAPI.ViewCrawler", localInapplicableInstructionsException1.getMessage());
            }
          }
          catch (EditProtocol.BadInstructionsException localBadInstructionsException1)
          {
            for (;;)
            {
              Log.e("MixpanelAPI.ViewCrawler", "Bad persistent change request cannot be applied.", localBadInstructionsException1);
            }
          }
        }
      }
      j = this.mPersistentTweaks.size();
      i = 0;
      for (;;)
      {
        if (i < j)
        {
          ViewCrawler.VariantTweak localVariantTweak = (ViewCrawler.VariantTweak)this.mPersistentTweaks.get(i);
          try
          {
            localObject5 = this.mProtocol.readTweak(localVariantTweak.tweak);
            ViewCrawler.this.mTweaks.set((String)((Pair)localObject5).first, ((Pair)localObject5).second);
            if (!this.mSeenExperiments.contains(localVariantTweak.variantId)) {
              ((Set)localObject3).add(localVariantTweak.variantId);
            }
            i += 1;
          }
          catch (EditProtocol.BadInstructionsException localBadInstructionsException2)
          {
            for (;;)
            {
              Log.e("MixpanelAPI.ViewCrawler", "Bad editor tweak cannot be applied.", localBadInstructionsException2);
            }
          }
        }
      }
      Object localObject1 = this.mEditorChanges.values().iterator();
      Object localObject7;
      while (((Iterator)localObject1).hasNext())
      {
        localObject5 = (Pair)((Iterator)localObject1).next();
        try
        {
          localObject7 = this.mProtocol.readEdit((JSONObject)((Pair)localObject5).second);
          ((List)localObject4).add(new Pair(((Pair)localObject5).first, ((EditProtocol.Edit)localObject7).visitor));
          this.mEditorAssetUrls.addAll(((EditProtocol.Edit)localObject7).imageUrls);
        }
        catch (EditProtocol.CantGetEditAssetsException localCantGetEditAssetsException2)
        {
          Log.v("MixpanelAPI.ViewCrawler", "Can't load assets for an edit, won't apply the change now", localCantGetEditAssetsException2);
        }
        catch (EditProtocol.InapplicableInstructionsException localInapplicableInstructionsException4)
        {
          Log.i("MixpanelAPI.ViewCrawler", localInapplicableInstructionsException4.getMessage());
        }
        catch (EditProtocol.BadInstructionsException localBadInstructionsException6)
        {
          Log.e("MixpanelAPI.ViewCrawler", "Bad editor change request cannot be applied.", localBadInstructionsException6);
        }
      }
      j = this.mEditorTweaks.size();
      i = 0;
      for (;;)
      {
        if (i < j)
        {
          localObject1 = (JSONObject)this.mEditorTweaks.get(i);
          try
          {
            localObject1 = this.mProtocol.readTweak((JSONObject)localObject1);
            ViewCrawler.this.mTweaks.set((String)((Pair)localObject1).first, ((Pair)localObject1).second);
            i += 1;
          }
          catch (EditProtocol.BadInstructionsException localBadInstructionsException3)
          {
            for (;;)
            {
              Log.e("MixpanelAPI.ViewCrawler", "Strange tweaks received", localBadInstructionsException3);
            }
          }
        }
      }
      j = this.mPersistentEventBindings.size();
      i = 0;
      for (;;)
      {
        if (i < j)
        {
          Pair localPair1 = (Pair)this.mPersistentEventBindings.get(i);
          try
          {
            localObject6 = this.mProtocol.readEventBinding((JSONObject)localPair1.second, ViewCrawler.this.mDynamicEventTracker);
            ((List)localObject4).add(new Pair(localPair1.first, localObject6));
            i += 1;
          }
          catch (EditProtocol.InapplicableInstructionsException localInapplicableInstructionsException2)
          {
            for (;;)
            {
              Log.i("MixpanelAPI.ViewCrawler", localInapplicableInstructionsException2.getMessage());
            }
          }
          catch (EditProtocol.BadInstructionsException localBadInstructionsException4)
          {
            for (;;)
            {
              Log.e("MixpanelAPI.ViewCrawler", "Bad persistent event binding cannot be applied.", localBadInstructionsException4);
            }
          }
        }
      }
      j = this.mEditorEventBindings.size();
      i = 0;
      for (;;)
      {
        if (i < j)
        {
          Pair localPair2 = (Pair)this.mEditorEventBindings.get(i);
          try
          {
            localObject6 = this.mProtocol.readEventBinding((JSONObject)localPair2.second, ViewCrawler.this.mDynamicEventTracker);
            ((List)localObject4).add(new Pair(localPair2.first, localObject6));
            i += 1;
          }
          catch (EditProtocol.InapplicableInstructionsException localInapplicableInstructionsException3)
          {
            for (;;)
            {
              Log.i("MixpanelAPI.ViewCrawler", localInapplicableInstructionsException3.getMessage());
            }
          }
          catch (EditProtocol.BadInstructionsException localBadInstructionsException5)
          {
            for (;;)
            {
              Log.e("MixpanelAPI.ViewCrawler", "Bad editor event binding cannot be applied.", localBadInstructionsException5);
            }
          }
        }
      }
      Object localObject6 = new HashMap();
      j = ((List)localObject4).size();
      i = 0;
      final Object localObject2;
      if (i < j)
      {
        localObject7 = (Pair)((List)localObject4).get(i);
        if (((Map)localObject6).containsKey(((Pair)localObject7).first)) {
          localObject2 = (List)((Map)localObject6).get(((Pair)localObject7).first);
        }
        for (;;)
        {
          ((List)localObject2).add(((Pair)localObject7).second);
          i += 1;
          break;
          localObject2 = new ArrayList();
          ((Map)localObject6).put(((Pair)localObject7).first, localObject2);
        }
      }
      ViewCrawler.this.mEditState.setEdits((Map)localObject6);
      this.mSeenExperiments.addAll((Collection)localObject3);
      if (((Set)localObject3).size() > 0)
      {
        localObject2 = new JSONObject();
        try
        {
          localObject3 = ((Set)localObject3).iterator();
          while (((Iterator)localObject3).hasNext())
          {
            localObject4 = (Pair)((Iterator)localObject3).next();
            i = ((Integer)((Pair)localObject4).first).intValue();
            j = ((Integer)((Pair)localObject4).second).intValue();
            localObject4 = new JSONObject();
            ((JSONObject)localObject4).put("$experiment_id", i);
            ((JSONObject)localObject4).put("$variant_id", j);
            ViewCrawler.this.mMixpanel.track("$experiment_started", (JSONObject)localObject4);
            ((JSONObject)localObject2).put(Integer.toString(i), j);
          }
          return;
        }
        catch (JSONException localJSONException)
        {
          Log.wtf("MixpanelAPI.ViewCrawler", "Could not build JSON for reporting experiment start", localJSONException);
          ViewCrawler.this.mMixpanel.getPeople().merge("$experiments", (JSONObject)localObject2);
          ViewCrawler.this.mMixpanel.updateSuperProperties(new SuperPropertyUpdate()
          {
            public JSONObject update(JSONObject paramAnonymousJSONObject)
            {
              try
              {
                paramAnonymousJSONObject.put("$experiments", localObject2);
                return paramAnonymousJSONObject;
              }
              catch (JSONException localJSONException)
              {
                Log.wtf("MixpanelAPI.ViewCrawler", "Can't write $experiments super property", localJSONException);
              }
              return paramAnonymousJSONObject;
            }
          });
        }
      }
    }
    
    private void connectToEditor()
    {
      if (MPConfig.DEBUG) {
        Log.v("MixpanelAPI.ViewCrawler", "connecting to editor");
      }
      if ((this.mEditorConnection != null) && (this.mEditorConnection.isValid())) {
        if (MPConfig.DEBUG) {
          Log.v("MixpanelAPI.ViewCrawler", "There is already a valid connection to an events editor.");
        }
      }
      Object localObject;
      do
      {
        return;
        localObject = ViewCrawler.this.mConfig.getSSLSocketFactory();
        if (localObject != null) {
          break;
        }
      } while (!MPConfig.DEBUG);
      Log.v("MixpanelAPI.ViewCrawler", "SSL is not available on this device, no connection will be attempted to the events editor.");
      return;
      String str = MPConfig.getInstance(this.mContext).getEditorUrl() + this.mToken;
      try
      {
        localObject = ((SSLSocketFactory)localObject).createSocket();
        this.mEditorConnection = new EditorConnection(new URI(str), new ViewCrawler.Editor(ViewCrawler.this, null), (Socket)localObject);
        return;
      }
      catch (URISyntaxException localURISyntaxException)
      {
        Log.e("MixpanelAPI.ViewCrawler", "Error parsing URI " + str + " for editor websocket", localURISyntaxException);
        return;
      }
      catch (EditorConnection.EditorConnectionException localEditorConnectionException)
      {
        Log.e("MixpanelAPI.ViewCrawler", "Error connecting to URI " + str, localEditorConnectionException);
        return;
      }
      catch (IOException localIOException)
      {
        Log.i("MixpanelAPI.ViewCrawler", "Can't create SSL Socket to connect to editor service", localIOException);
      }
    }
    
    private SharedPreferences getSharedPreferences()
    {
      String str = "mixpanel.viewcrawler.changes" + this.mToken;
      return this.mContext.getSharedPreferences(str, 0);
    }
    
    private void handleEditorBindingsCleared(JSONObject paramJSONObject)
    {
      try
      {
        paramJSONObject = paramJSONObject.getJSONObject("payload").getJSONArray("actions");
        int i = 0;
        while (i < paramJSONObject.length())
        {
          String str = paramJSONObject.getString(i);
          this.mEditorChanges.remove(str);
          i += 1;
        }
        return;
      }
      catch (JSONException paramJSONObject)
      {
        Log.e("MixpanelAPI.ViewCrawler", "Bad clear request received", paramJSONObject);
        applyVariantsAndEventBindings();
      }
    }
    
    /* Error */
    private void handleEditorBindingsReceived(JSONObject paramJSONObject)
    {
      // Byte code:
      //   0: aload_1
      //   1: ldc_w 446
      //   4: invokevirtual 450	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
      //   7: ldc_w 474
      //   10: invokevirtual 456	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
      //   13: astore_1
      //   14: aload_1
      //   15: invokevirtual 461	org/json/JSONArray:length	()I
      //   18: istore_3
      //   19: aload_0
      //   20: getfield 106	com/mixpanel/android/viewcrawler/ViewCrawler$ViewCrawlerHandler:mEditorEventBindings	Ljava/util/List;
      //   23: invokeinterface 477 1 0
      //   28: iconst_0
      //   29: istore_2
      //   30: iload_2
      //   31: iload_3
      //   32: if_icmpge +96 -> 128
      //   35: aload_1
      //   36: iload_2
      //   37: invokevirtual 480	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
      //   40: astore 4
      //   42: aload 4
      //   44: ldc_w 482
      //   47: invokestatic 488	com/mixpanel/android/util/JSONUtils:optionalStringKey	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
      //   50: astore 5
      //   52: aload_0
      //   53: getfield 106	com/mixpanel/android/viewcrawler/ViewCrawler$ViewCrawlerHandler:mEditorEventBindings	Ljava/util/List;
      //   56: new 159	android/util/Pair
      //   59: dup
      //   60: aload 5
      //   62: aload 4
      //   64: invokespecial 171	android/util/Pair:<init>	(Ljava/lang/Object;Ljava/lang/Object;)V
      //   67: invokeinterface 175 2 0
      //   72: pop
      //   73: iload_2
      //   74: iconst_1
      //   75: iadd
      //   76: istore_2
      //   77: goto -47 -> 30
      //   80: astore_1
      //   81: ldc -69
      //   83: ldc_w 490
      //   86: aload_1
      //   87: invokestatic 207	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   90: pop
      //   91: return
      //   92: astore 4
      //   94: ldc -69
      //   96: new 392	java/lang/StringBuilder
      //   99: dup
      //   100: invokespecial 393	java/lang/StringBuilder:<init>	()V
      //   103: ldc_w 492
      //   106: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   109: aload_1
      //   110: invokevirtual 493	org/json/JSONArray:toString	()Ljava/lang/String;
      //   113: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   116: invokevirtual 406	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   119: aload 4
      //   121: invokestatic 207	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   124: pop
      //   125: goto -52 -> 73
      //   128: aload_0
      //   129: invokespecial 471	com/mixpanel/android/viewcrawler/ViewCrawler$ViewCrawlerHandler:applyVariantsAndEventBindings	()V
      //   132: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	133	0	this	ViewCrawlerHandler
      //   0	133	1	paramJSONObject	JSONObject
      //   29	48	2	i	int
      //   18	15	3	j	int
      //   40	23	4	localJSONObject	JSONObject
      //   92	28	4	localJSONException	JSONException
      //   50	11	5	str	String
      // Exception table:
      //   from	to	target	type
      //   0	14	80	org/json/JSONException
      //   35	73	92	org/json/JSONException
    }
    
    private void handleEditorChangeReceived(JSONObject paramJSONObject)
    {
      try
      {
        paramJSONObject = paramJSONObject.getJSONObject("payload").getJSONArray("actions");
        int i = 0;
        while (i < paramJSONObject.length())
        {
          JSONObject localJSONObject = paramJSONObject.getJSONObject(i);
          String str1 = JSONUtils.optionalStringKey(localJSONObject, "target_activity");
          String str2 = localJSONObject.getString("name");
          this.mEditorChanges.put(str2, new Pair(str1, localJSONObject));
          i += 1;
        }
        applyVariantsAndEventBindings();
        return;
      }
      catch (JSONException paramJSONObject)
      {
        Log.e("MixpanelAPI.ViewCrawler", "Bad change request received", paramJSONObject);
      }
    }
    
    private void handleEditorClosed()
    {
      this.mEditorChanges.clear();
      this.mEditorEventBindings.clear();
      this.mSnapshot = null;
      if (MPConfig.DEBUG) {
        Log.v("MixpanelAPI.ViewCrawler", "Editor closed- freeing snapshot");
      }
      applyVariantsAndEventBindings();
      Iterator localIterator = this.mEditorAssetUrls.iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        this.mImageStore.deleteStorage(str);
      }
    }
    
    private void handleEditorTweaksReceived(JSONObject paramJSONObject)
    {
      try
      {
        this.mEditorTweaks.clear();
        paramJSONObject = paramJSONObject.getJSONObject("payload").getJSONArray("tweaks");
        int j = paramJSONObject.length();
        int i = 0;
        while (i < j)
        {
          JSONObject localJSONObject = paramJSONObject.getJSONObject(i);
          this.mEditorTweaks.add(localJSONObject);
          i += 1;
        }
        return;
      }
      catch (JSONException paramJSONObject)
      {
        Log.e("MixpanelAPI.ViewCrawler", "Bad tweaks received", paramJSONObject);
        applyVariantsAndEventBindings();
      }
    }
    
    private void handleEventBindingsReceived(JSONArray paramJSONArray)
    {
      SharedPreferences.Editor localEditor = getSharedPreferences().edit();
      localEditor.putString("mixpanel.viewcrawler.bindings", paramJSONArray.toString());
      localEditor.apply();
      initializeChanges();
    }
    
    private void handleVariantsReceived(JSONArray paramJSONArray)
    {
      SharedPreferences.Editor localEditor = getSharedPreferences().edit();
      localEditor.putString("mixpanel.viewcrawler.changes", paramJSONArray.toString());
      localEditor.apply();
      initializeChanges();
    }
    
    private void initializeChanges()
    {
      Object localObject1 = getSharedPreferences();
      Object localObject3 = ((SharedPreferences)localObject1).getString("mixpanel.viewcrawler.changes", null);
      Object localObject2 = ((SharedPreferences)localObject1).getString("mixpanel.viewcrawler.bindings", null);
      if (localObject3 != null) {}
      for (;;)
      {
        try
        {
          this.mPersistentChanges.clear();
          this.mPersistentTweaks.clear();
          localObject3 = new JSONArray((String)localObject3);
          int k = ((JSONArray)localObject3).length();
          int i = 0;
          Object localObject4;
          if (i < k)
          {
            Object localObject5 = ((JSONArray)localObject3).getJSONObject(i);
            int j = ((JSONObject)localObject5).getInt("id");
            localObject4 = new Pair(Integer.valueOf(((JSONObject)localObject5).getInt("experiment_id")), Integer.valueOf(j));
            Object localObject6 = ((JSONObject)localObject5).getJSONArray("actions");
            j = 0;
            if (j < ((JSONArray)localObject6).length())
            {
              Object localObject7 = ((JSONArray)localObject6).getJSONObject(j);
              localObject7 = new ViewCrawler.VariantChange(JSONUtils.optionalStringKey((JSONObject)localObject7, "target_activity"), (JSONObject)localObject7, (Pair)localObject4);
              this.mPersistentChanges.add(localObject7);
              j += 1;
              continue;
            }
            localObject5 = ((JSONObject)localObject5).getJSONArray("tweaks");
            int m = ((JSONArray)localObject5).length();
            j = 0;
            if (j < m)
            {
              localObject6 = new ViewCrawler.VariantTweak(((JSONArray)localObject5).getJSONObject(j), (Pair)localObject4);
              this.mPersistentTweaks.add(localObject6);
              j += 1;
              continue;
            }
          }
          else if (localObject2 != null)
          {
            localObject2 = new JSONArray((String)localObject2);
            this.mPersistentEventBindings.clear();
            i = 0;
            if (i < ((JSONArray)localObject2).length())
            {
              localObject3 = ((JSONArray)localObject2).getJSONObject(i);
              localObject4 = JSONUtils.optionalStringKey((JSONObject)localObject3, "target_activity");
              this.mPersistentEventBindings.add(new Pair(localObject4, localObject3));
              i += 1;
              continue;
            }
          }
          i += 1;
        }
        catch (JSONException localJSONException)
        {
          Log.i("MixpanelAPI.ViewCrawler", "JSON error when initializing saved changes, clearing persistent memory", localJSONException);
          localObject1 = ((SharedPreferences)localObject1).edit();
          ((SharedPreferences.Editor)localObject1).remove("mixpanel.viewcrawler.changes");
          ((SharedPreferences.Editor)localObject1).remove("mixpanel.viewcrawler.bindings");
          ((SharedPreferences.Editor)localObject1).apply();
          applyVariantsAndEventBindings();
          return;
        }
      }
    }
    
    private void loadKnownChanges()
    {
      Object localObject1 = getSharedPreferences();
      Object localObject2 = ((SharedPreferences)localObject1).getString("mixpanel.viewcrawler.changes", null);
      if (localObject2 != null) {
        try
        {
          localObject2 = new JSONArray((String)localObject2);
          int j = ((JSONArray)localObject2).length();
          int i = 0;
          while (i < j)
          {
            Object localObject3 = ((JSONArray)localObject2).getJSONObject(i);
            int k = ((JSONObject)localObject3).getInt("id");
            localObject3 = new Pair(Integer.valueOf(((JSONObject)localObject3).getInt("experiment_id")), Integer.valueOf(k));
            this.mSeenExperiments.add(localObject3);
            i += 1;
          }
          return;
        }
        catch (JSONException localJSONException)
        {
          Log.e("MixpanelAPI.ViewCrawler", "Malformed variants found in persistent storage, clearing all variants", localJSONException);
          localObject1 = ((SharedPreferences)localObject1).edit();
          ((SharedPreferences.Editor)localObject1).remove("mixpanel.viewcrawler.changes");
          ((SharedPreferences.Editor)localObject1).remove("mixpanel.viewcrawler.bindings");
          ((SharedPreferences.Editor)localObject1).apply();
        }
      }
    }
    
    /* Error */
    private void sendDeviceInfo()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 377	com/mixpanel/android/viewcrawler/ViewCrawler$ViewCrawlerHandler:mEditorConnection	Lcom/mixpanel/android/viewcrawler/EditorConnection;
      //   4: ifnonnull +4 -> 8
      //   7: return
      //   8: new 572	android/util/JsonWriter
      //   11: dup
      //   12: new 574	java/io/OutputStreamWriter
      //   15: dup
      //   16: aload_0
      //   17: getfield 377	com/mixpanel/android/viewcrawler/ViewCrawler$ViewCrawlerHandler:mEditorConnection	Lcom/mixpanel/android/viewcrawler/EditorConnection;
      //   20: invokevirtual 578	com/mixpanel/android/viewcrawler/EditorConnection:getBufferedOutputStream	()Ljava/io/BufferedOutputStream;
      //   23: invokespecial 581	java/io/OutputStreamWriter:<init>	(Ljava/io/OutputStream;)V
      //   26: invokespecial 584	android/util/JsonWriter:<init>	(Ljava/io/Writer;)V
      //   29: astore_1
      //   30: aload_1
      //   31: invokevirtual 588	android/util/JsonWriter:beginObject	()Landroid/util/JsonWriter;
      //   34: pop
      //   35: aload_1
      //   36: ldc_w 590
      //   39: invokevirtual 593	android/util/JsonWriter:name	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   42: ldc_w 595
      //   45: invokevirtual 598	android/util/JsonWriter:value	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   48: pop
      //   49: aload_1
      //   50: ldc_w 446
      //   53: invokevirtual 593	android/util/JsonWriter:name	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   56: invokevirtual 588	android/util/JsonWriter:beginObject	()Landroid/util/JsonWriter;
      //   59: pop
      //   60: aload_1
      //   61: ldc_w 600
      //   64: invokevirtual 593	android/util/JsonWriter:name	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   67: ldc_w 602
      //   70: invokevirtual 598	android/util/JsonWriter:value	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   73: pop
      //   74: aload_1
      //   75: ldc_w 604
      //   78: invokevirtual 593	android/util/JsonWriter:name	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   81: new 392	java/lang/StringBuilder
      //   84: dup
      //   85: invokespecial 393	java/lang/StringBuilder:<init>	()V
      //   88: getstatic 609	android/os/Build:BRAND	Ljava/lang/String;
      //   91: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   94: ldc_w 611
      //   97: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   100: getstatic 614	android/os/Build:MODEL	Ljava/lang/String;
      //   103: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   106: invokevirtual 406	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   109: invokevirtual 598	android/util/JsonWriter:value	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   112: pop
      //   113: aload_1
      //   114: ldc_w 616
      //   117: invokevirtual 593	android/util/JsonWriter:name	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   120: aload_0
      //   121: getfield 47	com/mixpanel/android/viewcrawler/ViewCrawler$ViewCrawlerHandler:this$0	Lcom/mixpanel/android/viewcrawler/ViewCrawler;
      //   124: invokestatic 620	com/mixpanel/android/viewcrawler/ViewCrawler:access$400	(Lcom/mixpanel/android/viewcrawler/ViewCrawler;)F
      //   127: f2d
      //   128: invokevirtual 623	android/util/JsonWriter:value	(D)Landroid/util/JsonWriter;
      //   131: pop
      //   132: aload_0
      //   133: getfield 47	com/mixpanel/android/viewcrawler/ViewCrawler$ViewCrawlerHandler:this$0	Lcom/mixpanel/android/viewcrawler/ViewCrawler;
      //   136: invokestatic 627	com/mixpanel/android/viewcrawler/ViewCrawler:access$500	(Lcom/mixpanel/android/viewcrawler/ViewCrawler;)Ljava/util/Map;
      //   139: invokeinterface 631 1 0
      //   144: invokeinterface 309 1 0
      //   149: astore_2
      //   150: aload_2
      //   151: invokeinterface 256 1 0
      //   156: ifeq +70 -> 226
      //   159: aload_2
      //   160: invokeinterface 260 1 0
      //   165: checkcast 633	java/util/Map$Entry
      //   168: astore_3
      //   169: aload_1
      //   170: aload_3
      //   171: invokeinterface 636 1 0
      //   176: checkcast 226	java/lang/String
      //   179: invokevirtual 593	android/util/JsonWriter:name	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   182: aload_3
      //   183: invokeinterface 639 1 0
      //   188: checkcast 226	java/lang/String
      //   191: invokevirtual 598	android/util/JsonWriter:value	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   194: pop
      //   195: goto -45 -> 150
      //   198: astore_2
      //   199: ldc -69
      //   201: ldc_w 641
      //   204: aload_2
      //   205: invokestatic 207	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   208: pop
      //   209: aload_1
      //   210: invokevirtual 644	android/util/JsonWriter:close	()V
      //   213: return
      //   214: astore_1
      //   215: ldc -69
      //   217: ldc_w 646
      //   220: aload_1
      //   221: invokestatic 207	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   224: pop
      //   225: return
      //   226: aload_0
      //   227: getfield 47	com/mixpanel/android/viewcrawler/ViewCrawler$ViewCrawlerHandler:this$0	Lcom/mixpanel/android/viewcrawler/ViewCrawler;
      //   230: invokestatic 220	com/mixpanel/android/viewcrawler/ViewCrawler:access$600	(Lcom/mixpanel/android/viewcrawler/ViewCrawler;)Lcom/mixpanel/android/mpmetrics/Tweaks;
      //   233: invokevirtual 650	com/mixpanel/android/mpmetrics/Tweaks:getAllValues	()Ljava/util/Map;
      //   236: astore_2
      //   237: aload_1
      //   238: ldc_w 512
      //   241: invokevirtual 593	android/util/JsonWriter:name	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   244: invokevirtual 653	android/util/JsonWriter:beginArray	()Landroid/util/JsonWriter;
      //   247: pop
      //   248: aload_2
      //   249: invokeinterface 631 1 0
      //   254: invokeinterface 309 1 0
      //   259: astore_2
      //   260: aload_2
      //   261: invokeinterface 256 1 0
      //   266: ifeq +333 -> 599
      //   269: aload_2
      //   270: invokeinterface 260 1 0
      //   275: checkcast 633	java/util/Map$Entry
      //   278: astore 4
      //   280: aload 4
      //   282: invokeinterface 639 1 0
      //   287: checkcast 655	com/mixpanel/android/mpmetrics/Tweaks$TweakValue
      //   290: astore_3
      //   291: aload 4
      //   293: invokeinterface 636 1 0
      //   298: checkcast 226	java/lang/String
      //   301: astore 4
      //   303: aload_1
      //   304: invokevirtual 588	android/util/JsonWriter:beginObject	()Landroid/util/JsonWriter;
      //   307: pop
      //   308: aload_1
      //   309: ldc_w 496
      //   312: invokevirtual 593	android/util/JsonWriter:name	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   315: aload 4
      //   317: invokevirtual 598	android/util/JsonWriter:value	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   320: pop
      //   321: aload_1
      //   322: ldc_w 657
      //   325: invokevirtual 593	android/util/JsonWriter:name	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   328: aconst_null
      //   329: checkcast 659	java/lang/Number
      //   332: invokevirtual 662	android/util/JsonWriter:value	(Ljava/lang/Number;)Landroid/util/JsonWriter;
      //   335: pop
      //   336: aload_1
      //   337: ldc_w 664
      //   340: invokevirtual 593	android/util/JsonWriter:name	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   343: aconst_null
      //   344: checkcast 659	java/lang/Number
      //   347: invokevirtual 662	android/util/JsonWriter:value	(Ljava/lang/Number;)Landroid/util/JsonWriter;
      //   350: pop
      //   351: aload_3
      //   352: getfield 667	com/mixpanel/android/mpmetrics/Tweaks$TweakValue:type	I
      //   355: tableswitch	default:+290->645, 1:+79->434, 2:+114->469, 3:+163->518, 4:+212->567
      //   384: ldc -69
      //   386: new 392	java/lang/StringBuilder
      //   389: dup
      //   390: invokespecial 393	java/lang/StringBuilder:<init>	()V
      //   393: ldc_w 669
      //   396: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   399: aload_3
      //   400: getfield 667	com/mixpanel/android/mpmetrics/Tweaks$TweakValue:type	I
      //   403: invokevirtual 672	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   406: ldc_w 674
      //   409: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   412: invokevirtual 406	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   415: invokestatic 676	android/util/Log:wtf	(Ljava/lang/String;Ljava/lang/String;)I
      //   418: pop
      //   419: aload_1
      //   420: invokevirtual 679	android/util/JsonWriter:endObject	()Landroid/util/JsonWriter;
      //   423: pop
      //   424: goto -164 -> 260
      //   427: astore_2
      //   428: aload_1
      //   429: invokevirtual 644	android/util/JsonWriter:close	()V
      //   432: aload_2
      //   433: athrow
      //   434: aload_1
      //   435: ldc_w 590
      //   438: invokevirtual 593	android/util/JsonWriter:name	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   441: ldc_w 681
      //   444: invokevirtual 598	android/util/JsonWriter:value	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   447: pop
      //   448: aload_1
      //   449: ldc_w 682
      //   452: invokevirtual 593	android/util/JsonWriter:name	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   455: aload_3
      //   456: invokevirtual 686	com/mixpanel/android/mpmetrics/Tweaks$TweakValue:getBooleanValue	()Ljava/lang/Boolean;
      //   459: invokevirtual 691	java/lang/Boolean:booleanValue	()Z
      //   462: invokevirtual 694	android/util/JsonWriter:value	(Z)Landroid/util/JsonWriter;
      //   465: pop
      //   466: goto -47 -> 419
      //   469: aload_1
      //   470: ldc_w 590
      //   473: invokevirtual 593	android/util/JsonWriter:name	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   476: ldc_w 696
      //   479: invokevirtual 598	android/util/JsonWriter:value	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   482: pop
      //   483: aload_1
      //   484: ldc_w 698
      //   487: invokevirtual 593	android/util/JsonWriter:name	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   490: ldc_w 700
      //   493: invokevirtual 598	android/util/JsonWriter:value	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   496: pop
      //   497: aload_1
      //   498: ldc_w 682
      //   501: invokevirtual 593	android/util/JsonWriter:name	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   504: aload_3
      //   505: invokevirtual 704	com/mixpanel/android/mpmetrics/Tweaks$TweakValue:getNumberValue	()Ljava/lang/Number;
      //   508: invokevirtual 708	java/lang/Number:doubleValue	()D
      //   511: invokevirtual 623	android/util/JsonWriter:value	(D)Landroid/util/JsonWriter;
      //   514: pop
      //   515: goto -96 -> 419
      //   518: aload_1
      //   519: ldc_w 590
      //   522: invokevirtual 593	android/util/JsonWriter:name	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   525: ldc_w 696
      //   528: invokevirtual 598	android/util/JsonWriter:value	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   531: pop
      //   532: aload_1
      //   533: ldc_w 698
      //   536: invokevirtual 593	android/util/JsonWriter:name	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   539: ldc_w 710
      //   542: invokevirtual 598	android/util/JsonWriter:value	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   545: pop
      //   546: aload_1
      //   547: ldc_w 682
      //   550: invokevirtual 593	android/util/JsonWriter:name	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   553: aload_3
      //   554: invokevirtual 704	com/mixpanel/android/mpmetrics/Tweaks$TweakValue:getNumberValue	()Ljava/lang/Number;
      //   557: invokevirtual 714	java/lang/Number:longValue	()J
      //   560: invokevirtual 717	android/util/JsonWriter:value	(J)Landroid/util/JsonWriter;
      //   563: pop
      //   564: goto -145 -> 419
      //   567: aload_1
      //   568: ldc_w 590
      //   571: invokevirtual 593	android/util/JsonWriter:name	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   574: ldc_w 719
      //   577: invokevirtual 598	android/util/JsonWriter:value	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   580: pop
      //   581: aload_1
      //   582: ldc_w 682
      //   585: invokevirtual 593	android/util/JsonWriter:name	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   588: aload_3
      //   589: invokevirtual 722	com/mixpanel/android/mpmetrics/Tweaks$TweakValue:getStringValue	()Ljava/lang/String;
      //   592: invokevirtual 598	android/util/JsonWriter:value	(Ljava/lang/String;)Landroid/util/JsonWriter;
      //   595: pop
      //   596: goto -177 -> 419
      //   599: aload_1
      //   600: invokevirtual 725	android/util/JsonWriter:endArray	()Landroid/util/JsonWriter;
      //   603: pop
      //   604: aload_1
      //   605: invokevirtual 679	android/util/JsonWriter:endObject	()Landroid/util/JsonWriter;
      //   608: pop
      //   609: aload_1
      //   610: invokevirtual 679	android/util/JsonWriter:endObject	()Landroid/util/JsonWriter;
      //   613: pop
      //   614: aload_1
      //   615: invokevirtual 644	android/util/JsonWriter:close	()V
      //   618: return
      //   619: astore_1
      //   620: ldc -69
      //   622: ldc_w 646
      //   625: aload_1
      //   626: invokestatic 207	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   629: pop
      //   630: return
      //   631: astore_1
      //   632: ldc -69
      //   634: ldc_w 646
      //   637: aload_1
      //   638: invokestatic 207	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   641: pop
      //   642: goto -210 -> 432
      //   645: goto -261 -> 384
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	648	0	this	ViewCrawlerHandler
      //   29	181	1	localJsonWriter	JsonWriter
      //   214	401	1	localIOException1	IOException
      //   619	7	1	localIOException2	IOException
      //   631	7	1	localIOException3	IOException
      //   149	11	2	localIterator	Iterator
      //   198	7	2	localIOException4	IOException
      //   236	34	2	localObject1	Object
      //   427	6	2	localObject2	Object
      //   168	421	3	localObject3	Object
      //   278	38	4	localObject4	Object
      // Exception table:
      //   from	to	target	type
      //   30	150	198	java/io/IOException
      //   150	195	198	java/io/IOException
      //   226	260	198	java/io/IOException
      //   260	384	198	java/io/IOException
      //   384	419	198	java/io/IOException
      //   419	424	198	java/io/IOException
      //   434	466	198	java/io/IOException
      //   469	515	198	java/io/IOException
      //   518	564	198	java/io/IOException
      //   567	596	198	java/io/IOException
      //   599	614	198	java/io/IOException
      //   209	213	214	java/io/IOException
      //   30	150	427	finally
      //   150	195	427	finally
      //   199	209	427	finally
      //   226	260	427	finally
      //   260	384	427	finally
      //   384	419	427	finally
      //   419	424	427	finally
      //   434	466	427	finally
      //   469	515	427	finally
      //   518	564	427	finally
      //   567	596	427	finally
      //   599	614	427	finally
      //   614	618	619	java/io/IOException
      //   428	432	631	java/io/IOException
    }
    
    private void sendError(String paramString)
    {
      if (this.mEditorConnection == null) {
        return;
      }
      JSONObject localJSONObject = new JSONObject();
      try
      {
        localJSONObject.put("error_message", paramString);
        paramString = new OutputStreamWriter(this.mEditorConnection.getBufferedOutputStream());
      }
      catch (JSONException paramString)
      {
        try
        {
          for (;;)
          {
            paramString.write("{\"type\": \"error\", ");
            paramString.write("\"payload\": ");
            paramString.write(localJSONObject.toString());
            paramString.write("}");
            try
            {
              paramString.close();
              return;
            }
            catch (IOException paramString)
            {
              Log.e("MixpanelAPI.ViewCrawler", "Could not close output writer to editor", paramString);
              return;
            }
            paramString = paramString;
            Log.e("MixpanelAPI.ViewCrawler", "Apparently impossible JSONException", paramString);
          }
        }
        catch (IOException localIOException)
        {
          localIOException = localIOException;
          Log.e("MixpanelAPI.ViewCrawler", "Can't write error message to editor", localIOException);
          try
          {
            paramString.close();
            return;
          }
          catch (IOException paramString)
          {
            Log.e("MixpanelAPI.ViewCrawler", "Could not close output writer to editor", paramString);
            return;
          }
        }
        finally {}
      }
      try
      {
        paramString.close();
        throw ((Throwable)localObject);
      }
      catch (IOException paramString)
      {
        for (;;)
        {
          Log.e("MixpanelAPI.ViewCrawler", "Could not close output writer to editor", paramString);
        }
      }
    }
    
    private void sendLayoutError(ViewVisitor.LayoutErrorMessage paramLayoutErrorMessage)
    {
      if (this.mEditorConnection == null) {
        return;
      }
      localJsonWriter = new JsonWriter(new OutputStreamWriter(this.mEditorConnection.getBufferedOutputStream()));
      try
      {
        localJsonWriter.beginObject();
        localJsonWriter.name("type").value("layout_error");
        localJsonWriter.name("exception_type").value(paramLayoutErrorMessage.getErrorType());
        localJsonWriter.name("cid").value(paramLayoutErrorMessage.getName());
        localJsonWriter.endObject();
        try
        {
          localJsonWriter.close();
          return;
        }
        catch (IOException paramLayoutErrorMessage)
        {
          Log.e("MixpanelAPI.ViewCrawler", "Can't close writer.", paramLayoutErrorMessage);
          return;
        }
        try
        {
          localJsonWriter.close();
          throw paramLayoutErrorMessage;
        }
        catch (IOException localIOException)
        {
          for (;;)
          {
            Log.e("MixpanelAPI.ViewCrawler", "Can't close writer.", localIOException);
          }
        }
      }
      catch (IOException paramLayoutErrorMessage)
      {
        paramLayoutErrorMessage = paramLayoutErrorMessage;
        Log.e("MixpanelAPI.ViewCrawler", "Can't write track_message to server", paramLayoutErrorMessage);
        try
        {
          localJsonWriter.close();
          return;
        }
        catch (IOException paramLayoutErrorMessage)
        {
          Log.e("MixpanelAPI.ViewCrawler", "Can't close writer.", paramLayoutErrorMessage);
          return;
        }
      }
      finally {}
    }
    
    private void sendReportTrackToEditor(String paramString)
    {
      if (this.mEditorConnection == null) {
        return;
      }
      localJsonWriter = new JsonWriter(new OutputStreamWriter(this.mEditorConnection.getBufferedOutputStream()));
      try
      {
        localJsonWriter.beginObject();
        localJsonWriter.name("type").value("track_message");
        localJsonWriter.name("payload");
        localJsonWriter.beginObject();
        localJsonWriter.name("event_name").value(paramString);
        localJsonWriter.endObject();
        localJsonWriter.endObject();
        localJsonWriter.flush();
        try
        {
          localJsonWriter.close();
          return;
        }
        catch (IOException paramString)
        {
          Log.e("MixpanelAPI.ViewCrawler", "Can't close writer.", paramString);
          return;
        }
        try
        {
          localJsonWriter.close();
          throw paramString;
        }
        catch (IOException localIOException)
        {
          for (;;)
          {
            Log.e("MixpanelAPI.ViewCrawler", "Can't close writer.", localIOException);
          }
        }
      }
      catch (IOException paramString)
      {
        paramString = paramString;
        Log.e("MixpanelAPI.ViewCrawler", "Can't write track_message to server", paramString);
        try
        {
          localJsonWriter.close();
          return;
        }
        catch (IOException paramString)
        {
          Log.e("MixpanelAPI.ViewCrawler", "Can't close writer.", paramString);
          return;
        }
      }
      finally {}
    }
    
    private void sendSnapshot(JSONObject paramJSONObject)
    {
      long l1 = System.currentTimeMillis();
      try
      {
        paramJSONObject = paramJSONObject.getJSONObject("payload");
        if (paramJSONObject.has("config"))
        {
          this.mSnapshot = this.mProtocol.readSnapshotConfig(paramJSONObject);
          if (MPConfig.DEBUG) {
            Log.v("MixpanelAPI.ViewCrawler", "Initializing snapshot with configuration");
          }
        }
        if (this.mSnapshot == null)
        {
          sendError("No snapshot configuration (or a malformed snapshot configuration) was sent.");
          Log.w("MixpanelAPI.ViewCrawler", "Mixpanel editor is misconfigured, sent a snapshot request without a valid configuration.");
          return;
        }
      }
      catch (JSONException paramJSONObject)
      {
        Log.e("MixpanelAPI.ViewCrawler", "Payload with snapshot config required with snapshot request", paramJSONObject);
        sendError("Payload with snapshot config required with snapshot request");
        return;
      }
      catch (EditProtocol.BadInstructionsException paramJSONObject)
      {
        Log.e("MixpanelAPI.ViewCrawler", "Editor sent malformed message with snapshot request", paramJSONObject);
        sendError(paramJSONObject.getMessage());
        return;
      }
      BufferedOutputStream localBufferedOutputStream = this.mEditorConnection.getBufferedOutputStream();
      paramJSONObject = new OutputStreamWriter(localBufferedOutputStream);
      try
      {
        paramJSONObject.write("{");
        paramJSONObject.write("\"type\": \"snapshot_response\",");
        paramJSONObject.write("\"payload\": {");
        paramJSONObject.write("\"activities\":");
        paramJSONObject.flush();
        this.mSnapshot.snapshots(ViewCrawler.this.mEditState, localBufferedOutputStream);
        long l2 = System.currentTimeMillis();
        paramJSONObject.write(",\"snapshot_time_millis\": ");
        paramJSONObject.write(Long.toString(l2 - l1));
        paramJSONObject.write("}");
        paramJSONObject.write("}");
        try
        {
          paramJSONObject.close();
          return;
        }
        catch (IOException paramJSONObject)
        {
          Log.e("MixpanelAPI.ViewCrawler", "Can't close writer.", paramJSONObject);
          return;
        }
        try
        {
          paramJSONObject.close();
          throw ((Throwable)localObject);
        }
        catch (IOException paramJSONObject)
        {
          for (;;)
          {
            Log.e("MixpanelAPI.ViewCrawler", "Can't close writer.", paramJSONObject);
          }
        }
      }
      catch (IOException localIOException)
      {
        localIOException = localIOException;
        Log.e("MixpanelAPI.ViewCrawler", "Can't write snapshot request to server", localIOException);
        try
        {
          paramJSONObject.close();
          return;
        }
        catch (IOException paramJSONObject)
        {
          Log.e("MixpanelAPI.ViewCrawler", "Can't close writer.", paramJSONObject);
          return;
        }
      }
      finally {}
    }
    
    public void handleMessage(Message paramMessage)
    {
      this.mStartLock.lock();
      for (;;)
      {
        try
        {
          int i = paramMessage.what;
          switch (i)
          {
          default: 
            return;
          }
        }
        finally
        {
          this.mStartLock.unlock();
        }
        loadKnownChanges();
        initializeChanges();
        continue;
        connectToEditor();
        continue;
        sendDeviceInfo();
        continue;
        sendSnapshot((JSONObject)paramMessage.obj);
        continue;
        sendReportTrackToEditor((String)paramMessage.obj);
        continue;
        sendLayoutError((ViewVisitor.LayoutErrorMessage)paramMessage.obj);
        continue;
        handleVariantsReceived((JSONArray)paramMessage.obj);
        continue;
        handleEditorChangeReceived((JSONObject)paramMessage.obj);
        continue;
        handleEventBindingsReceived((JSONArray)paramMessage.obj);
        continue;
        handleEditorBindingsReceived((JSONObject)paramMessage.obj);
        continue;
        handleEditorBindingsCleared((JSONObject)paramMessage.obj);
        continue;
        handleEditorTweaksReceived((JSONObject)paramMessage.obj);
        continue;
        handleEditorClosed();
      }
    }
    
    public void start()
    {
      this.mStartLock.unlock();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/viewcrawler/ViewCrawler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */