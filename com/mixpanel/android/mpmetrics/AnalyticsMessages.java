package com.mixpanel.android.mpmetrics;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.mixpanel.android.util.Base64Coder;
import com.mixpanel.android.util.HttpService;
import com.mixpanel.android.util.RemoteService;
import com.mixpanel.android.util.RemoteService.ServiceUnavailableException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

class AnalyticsMessages
{
  private static final int ENQUEUE_EVENTS = 1;
  private static final int ENQUEUE_PEOPLE = 0;
  private static final int FLUSH_QUEUE = 2;
  private static final int INSTALL_DECIDE_CHECK = 12;
  private static final int KILL_WORKER = 5;
  private static final String LOGTAG = "MixpanelAPI.Messages";
  private static final int REGISTER_FOR_GCM = 13;
  private static final Map<Context, AnalyticsMessages> sInstances = new HashMap();
  private final MPConfig mConfig;
  private final Context mContext;
  private final Worker mWorker;
  
  AnalyticsMessages(Context paramContext)
  {
    this.mContext = paramContext;
    this.mConfig = getConfig(paramContext);
    this.mWorker = new Worker();
  }
  
  public static AnalyticsMessages getInstance(Context paramContext)
  {
    synchronized (sInstances)
    {
      Context localContext = paramContext.getApplicationContext();
      if (!sInstances.containsKey(localContext))
      {
        paramContext = new AnalyticsMessages(localContext);
        sInstances.put(localContext, paramContext);
        return paramContext;
      }
      paramContext = (AnalyticsMessages)sInstances.get(localContext);
    }
  }
  
  private void logAboutMessageToMixpanel(String paramString)
  {
    if (MPConfig.DEBUG) {
      Log.v("MixpanelAPI.Messages", paramString + " (Thread " + Thread.currentThread().getId() + ")");
    }
  }
  
  private void logAboutMessageToMixpanel(String paramString, Throwable paramThrowable)
  {
    if (MPConfig.DEBUG) {
      Log.v("MixpanelAPI.Messages", paramString + " (Thread " + Thread.currentThread().getId() + ")", paramThrowable);
    }
  }
  
  public void eventsMessage(EventDescription paramEventDescription)
  {
    Message localMessage = Message.obtain();
    localMessage.what = 1;
    localMessage.obj = paramEventDescription;
    this.mWorker.runMessage(localMessage);
  }
  
  protected MPConfig getConfig(Context paramContext)
  {
    return MPConfig.getInstance(paramContext);
  }
  
  protected RemoteService getPoster()
  {
    return new HttpService();
  }
  
  public void hardKill()
  {
    Message localMessage = Message.obtain();
    localMessage.what = 5;
    this.mWorker.runMessage(localMessage);
  }
  
  public void installDecideCheck(DecideMessages paramDecideMessages)
  {
    Message localMessage = Message.obtain();
    localMessage.what = 12;
    localMessage.obj = paramDecideMessages;
    this.mWorker.runMessage(localMessage);
  }
  
  boolean isDead()
  {
    return this.mWorker.isDead();
  }
  
  protected MPDbAdapter makeDbAdapter(Context paramContext)
  {
    return new MPDbAdapter(paramContext);
  }
  
  public void peopleMessage(JSONObject paramJSONObject)
  {
    Message localMessage = Message.obtain();
    localMessage.what = 0;
    localMessage.obj = paramJSONObject;
    this.mWorker.runMessage(localMessage);
  }
  
  public void postToServer()
  {
    Message localMessage = Message.obtain();
    localMessage.what = 2;
    this.mWorker.runMessage(localMessage);
  }
  
  public void registerForGCM(String paramString)
  {
    Message localMessage = Message.obtain();
    localMessage.what = 13;
    localMessage.obj = paramString;
    this.mWorker.runMessage(localMessage);
  }
  
  static class EventDescription
  {
    private final String eventName;
    private final JSONObject properties;
    private final String token;
    
    public EventDescription(String paramString1, JSONObject paramJSONObject, String paramString2)
    {
      this.eventName = paramString1;
      this.properties = paramJSONObject;
      this.token = paramString2;
    }
    
    public String getEventName()
    {
      return this.eventName;
    }
    
    public JSONObject getProperties()
    {
      return this.properties;
    }
    
    public String getToken()
    {
      return this.token;
    }
  }
  
  private class Worker
  {
    private long mAveFlushFrequency = 0L;
    private long mFlushCount = 0L;
    private Handler mHandler = restartWorkerThread();
    private final Object mHandlerLock = new Object();
    private long mLastFlushTime = -1L;
    private SystemInformation mSystemInformation;
    
    public Worker() {}
    
    private Handler restartWorkerThread()
    {
      HandlerThread localHandlerThread = new HandlerThread("com.mixpanel.android.AnalyticsWorker", 1);
      localHandlerThread.start();
      return new AnalyticsMessageHandler(localHandlerThread.getLooper());
    }
    
    private void updateFlushFrequency()
    {
      long l1 = System.currentTimeMillis();
      long l2 = this.mFlushCount + 1L;
      if (this.mLastFlushTime > 0L)
      {
        this.mAveFlushFrequency = ((l1 - this.mLastFlushTime + this.mAveFlushFrequency * this.mFlushCount) / l2);
        long l3 = this.mAveFlushFrequency / 1000L;
        AnalyticsMessages.this.logAboutMessageToMixpanel("Average send frequency approximately " + l3 + " seconds.");
      }
      this.mLastFlushTime = l1;
      this.mFlushCount = l2;
    }
    
    public boolean isDead()
    {
      for (;;)
      {
        synchronized (this.mHandlerLock)
        {
          if (this.mHandler == null)
          {
            bool = true;
            return bool;
          }
        }
        boolean bool = false;
      }
    }
    
    public void runMessage(Message paramMessage)
    {
      synchronized (this.mHandlerLock)
      {
        if (this.mHandler == null)
        {
          AnalyticsMessages.this.logAboutMessageToMixpanel("Dead mixpanel worker dropping a message: " + paramMessage.what);
          return;
        }
        this.mHandler.sendMessage(paramMessage);
      }
    }
    
    private class AnalyticsMessageHandler
      extends Handler
    {
      private MPDbAdapter mDbAdapter = null;
      private final DecideChecker mDecideChecker = new DecideChecker(AnalyticsMessages.this.mContext, AnalyticsMessages.this.mConfig);
      private final boolean mDisableFallback = AnalyticsMessages.this.mConfig.getDisableFallback();
      private final long mFlushInterval = AnalyticsMessages.this.mConfig.getFlushInterval(AnalyticsMessages.this.mContext);
      private long mRetryAfter;
      
      public AnalyticsMessageHandler(Looper paramLooper)
      {
        super();
        AnalyticsMessages.Worker.access$302(AnalyticsMessages.Worker.this, new SystemInformation(AnalyticsMessages.this.mContext));
        this.mRetryAfter = -1L;
      }
      
      private JSONObject getDefaultEventProperties()
        throws JSONException
      {
        localJSONObject = new JSONObject();
        localJSONObject.put("mp_lib", "android");
        localJSONObject.put("$lib_version", "4.6.3");
        localJSONObject.put("$os", "Android");
        Object localObject;
        if (Build.VERSION.RELEASE == null)
        {
          localObject = "UNKNOWN";
          localJSONObject.put("$os_version", localObject);
          if (Build.MANUFACTURER != null) {
            break label401;
          }
          localObject = "UNKNOWN";
          label61:
          localJSONObject.put("$manufacturer", localObject);
          if (Build.BRAND != null) {
            break label408;
          }
          localObject = "UNKNOWN";
          label78:
          localJSONObject.put("$brand", localObject);
          if (Build.MODEL != null) {
            break label415;
          }
          localObject = "UNKNOWN";
          label95:
          localJSONObject.put("$model", localObject);
        }
        for (;;)
        {
          try
          {
            int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(AnalyticsMessages.this.mContext);
            switch (i)
            {
            }
          }
          catch (RuntimeException localRuntimeException)
          {
            localJSONObject.put("$google_play_services", "not configured");
            continue;
          }
          catch (NoClassDefFoundError localNoClassDefFoundError)
          {
            label401:
            label408:
            label415:
            localJSONObject.put("$google_play_services", "not included");
            continue;
            localJSONObject.put("$google_play_services", "missing");
            continue;
            localJSONObject.put("$google_play_services", "out of date");
            continue;
            localJSONObject.put("$google_play_services", "disabled");
            continue;
            localJSONObject.put("$google_play_services", "invalid");
            continue;
          }
          localObject = AnalyticsMessages.Worker.this.mSystemInformation.getDisplayMetrics();
          localJSONObject.put("$screen_dpi", ((DisplayMetrics)localObject).densityDpi);
          localJSONObject.put("$screen_height", ((DisplayMetrics)localObject).heightPixels);
          localJSONObject.put("$screen_width", ((DisplayMetrics)localObject).widthPixels);
          localObject = AnalyticsMessages.Worker.this.mSystemInformation.getAppVersionName();
          if (localObject != null) {
            localJSONObject.put("$app_version", localObject);
          }
          localObject = Boolean.valueOf(AnalyticsMessages.Worker.this.mSystemInformation.hasNFC());
          if (localObject != null) {
            localJSONObject.put("$has_nfc", ((Boolean)localObject).booleanValue());
          }
          localObject = Boolean.valueOf(AnalyticsMessages.Worker.this.mSystemInformation.hasTelephony());
          if (localObject != null) {
            localJSONObject.put("$has_telephone", ((Boolean)localObject).booleanValue());
          }
          localObject = AnalyticsMessages.Worker.this.mSystemInformation.getCurrentNetworkOperator();
          if (localObject != null) {
            localJSONObject.put("$carrier", localObject);
          }
          localObject = AnalyticsMessages.Worker.this.mSystemInformation.isWifiConnected();
          if (localObject != null) {
            localJSONObject.put("$wifi", ((Boolean)localObject).booleanValue());
          }
          localObject = AnalyticsMessages.Worker.this.mSystemInformation.isBluetoothEnabled();
          if (localObject != null) {
            localJSONObject.put("$bluetooth_enabled", localObject);
          }
          localObject = AnalyticsMessages.Worker.this.mSystemInformation.getBluetoothVersion();
          if (localObject != null) {
            localJSONObject.put("$bluetooth_version", localObject);
          }
          return localJSONObject;
          localObject = Build.VERSION.RELEASE;
          break;
          localObject = Build.MANUFACTURER;
          break label61;
          localObject = Build.BRAND;
          break label78;
          localObject = Build.MODEL;
          break label95;
          localJSONObject.put("$google_play_services", "available");
        }
      }
      
      private JSONObject prepareEventObject(AnalyticsMessages.EventDescription paramEventDescription)
        throws JSONException
      {
        JSONObject localJSONObject1 = new JSONObject();
        JSONObject localJSONObject2 = paramEventDescription.getProperties();
        JSONObject localJSONObject3 = getDefaultEventProperties();
        localJSONObject3.put("token", paramEventDescription.getToken());
        if (localJSONObject2 != null)
        {
          Iterator localIterator = localJSONObject2.keys();
          while (localIterator.hasNext())
          {
            String str = (String)localIterator.next();
            localJSONObject3.put(str, localJSONObject2.get(str));
          }
        }
        localJSONObject1.put("event", paramEventDescription.getEventName());
        localJSONObject1.put("properties", localJSONObject3);
        return localJSONObject1;
      }
      
      private void runGCMRegistration(final String paramString)
      {
        try
        {
          if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(AnalyticsMessages.this.mContext) != 0)
          {
            Log.i("MixpanelAPI.Messages", "Can't register for push notifications, Google Play Services are not installed.");
            return;
          }
        }
        catch (RuntimeException paramString)
        {
          Log.i("MixpanelAPI.Messages", "Can't register for push notifications, Google Play services are not configured.");
          return;
        }
        catch (IOException paramString)
        {
          Log.i("MixpanelAPI.Messages", "Exception when trying to register for GCM", paramString);
          return;
          paramString = GoogleCloudMessaging.getInstance(AnalyticsMessages.this.mContext).register(new String[] { paramString });
          MixpanelAPI.allInstances(new MixpanelAPI.InstanceProcessor()
          {
            public void process(MixpanelAPI paramAnonymousMixpanelAPI)
            {
              if (MPConfig.DEBUG) {
                Log.v("MixpanelAPI.Messages", "Using existing pushId " + paramString);
              }
              paramAnonymousMixpanelAPI.getPeople().setPushRegistrationId(paramString);
            }
          });
          return;
        }
        catch (NoClassDefFoundError paramString)
        {
          Log.w("MixpanelAPI.Messages", "Google play services were not part of this build, push notifications cannot be registered or delivered");
        }
      }
      
      private void sendAllData(MPDbAdapter paramMPDbAdapter)
        throws RemoteService.ServiceUnavailableException
      {
        if (!AnalyticsMessages.this.getPoster().isOnline(AnalyticsMessages.this.mContext))
        {
          AnalyticsMessages.this.logAboutMessageToMixpanel("Not flushing data to Mixpanel because the device is not connected to the internet.");
          return;
        }
        AnalyticsMessages.this.logAboutMessageToMixpanel("Sending records to Mixpanel");
        if (this.mDisableFallback)
        {
          sendData(paramMPDbAdapter, MPDbAdapter.Table.EVENTS, new String[] { AnalyticsMessages.this.mConfig.getEventsEndpoint() });
          sendData(paramMPDbAdapter, MPDbAdapter.Table.PEOPLE, new String[] { AnalyticsMessages.this.mConfig.getPeopleEndpoint() });
          return;
        }
        sendData(paramMPDbAdapter, MPDbAdapter.Table.EVENTS, new String[] { AnalyticsMessages.this.mConfig.getEventsEndpoint(), AnalyticsMessages.this.mConfig.getEventsFallbackEndpoint() });
        sendData(paramMPDbAdapter, MPDbAdapter.Table.PEOPLE, new String[] { AnalyticsMessages.this.mConfig.getPeopleEndpoint(), AnalyticsMessages.this.mConfig.getPeopleFallbackEndpoint() });
      }
      
      private void sendData(MPDbAdapter paramMPDbAdapter, MPDbAdapter.Table paramTable, String[] paramArrayOfString)
        throws RemoteService.ServiceUnavailableException
      {
        RemoteService localRemoteService = AnalyticsMessages.this.getPoster();
        Object localObject1 = paramMPDbAdapter.generateDataString(paramTable);
        String str1;
        String str2;
        ArrayList localArrayList;
        int i;
        int k;
        int j;
        if (localObject1 != null)
        {
          str1 = localObject1[0];
          str2 = localObject1[1];
          localObject1 = Base64Coder.encodeString(str2);
          localArrayList = new ArrayList(1);
          localArrayList.add(new BasicNameValuePair("data", (String)localObject1));
          if (MPConfig.DEBUG) {
            localArrayList.add(new BasicNameValuePair("verbose", "1"));
          }
          i = 1;
          int i2 = paramArrayOfString.length;
          k = 0;
          j = i;
          if (k < i2)
          {
            localObject1 = paramArrayOfString[k];
            j = i;
          }
        }
        try
        {
          localObject2 = localRemoteService.performRequest((String)localObject1, localArrayList, AnalyticsMessages.this.mConfig.getSSLSocketFactory());
          n = 1;
          i1 = 1;
          m = 1;
          if (localObject2 != null) {
            break label246;
          }
          j = n;
          i = i1;
          AnalyticsMessages.this.logAboutMessageToMixpanel("Response was null, unexpected failure posting to " + (String)localObject1 + ".");
          j = m;
        }
        catch (OutOfMemoryError paramArrayOfString)
        {
          for (;;)
          {
            try
            {
              int m;
              Object localObject2 = new String((byte[])localObject2, "UTF-8");
              j = n;
              i = i1;
              AnalyticsMessages.this.logAboutMessageToMixpanel("Successfully posted to " + (String)localObject1 + ": \n" + str2);
              j = n;
              i = i1;
              AnalyticsMessages.this.logAboutMessageToMixpanel("Response was " + (String)localObject2);
              j = m;
            }
            catch (UnsupportedEncodingException localUnsupportedEncodingException)
            {
              int n;
              int i1;
              j = n;
              i = i1;
              throw new RuntimeException("UTF not supported on this platform?", localUnsupportedEncodingException);
            }
            paramArrayOfString = paramArrayOfString;
            Log.e("MixpanelAPI.Messages", "Out of memory when posting to " + (String)localObject1 + ".", paramArrayOfString);
          }
        }
        catch (MalformedURLException paramArrayOfString)
        {
          for (;;)
          {
            Log.e("MixpanelAPI.Messages", "Cannot interpret " + (String)localObject1 + " as a URL.", paramArrayOfString);
            j = i;
          }
        }
        catch (IOException localIOException)
        {
          AnalyticsMessages.this.logAboutMessageToMixpanel("Cannot post message to " + (String)localObject1 + ".", localIOException);
          i = 0;
          k += 1;
        }
        if (j != 0)
        {
          AnalyticsMessages.this.logAboutMessageToMixpanel("Not retrying this batch of events, deleting them from DB.");
          paramMPDbAdapter.cleanupEvents(str1, paramTable);
        }
        label246:
        do
        {
          return;
          j = n;
          i = i1;
          break;
          AnalyticsMessages.this.logAboutMessageToMixpanel("Retrying this batch of events.");
        } while (hasMessages(2));
        sendEmptyMessageDelayed(2, this.mFlushInterval);
      }
      
      /* Error */
      public void handleMessage(Message arg1)
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 32	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:mDbAdapter	Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
        //   4: ifnonnull +83 -> 87
        //   7: aload_0
        //   8: aload_0
        //   9: getfield 27	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:this$1	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
        //   12: getfield 38	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker:this$0	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
        //   15: aload_0
        //   16: getfield 27	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:this$1	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
        //   19: getfield 38	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker:this$0	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
        //   22: invokestatic 42	com/mixpanel/android/mpmetrics/AnalyticsMessages:access$100	(Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;)Landroid/content/Context;
        //   25: invokevirtual 487	com/mixpanel/android/mpmetrics/AnalyticsMessages:makeDbAdapter	(Landroid/content/Context;)Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
        //   28: putfield 32	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:mDbAdapter	Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
        //   31: aload_0
        //   32: getfield 32	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:mDbAdapter	Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
        //   35: invokestatic 493	java/lang/System:currentTimeMillis	()J
        //   38: aload_0
        //   39: getfield 27	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:this$1	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
        //   42: getfield 38	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker:this$0	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
        //   45: invokestatic 46	com/mixpanel/android/mpmetrics/AnalyticsMessages:access$200	(Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;)Lcom/mixpanel/android/mpmetrics/MPConfig;
        //   48: invokevirtual 497	com/mixpanel/android/mpmetrics/MPConfig:getDataExpiration	()I
        //   51: i2l
        //   52: lsub
        //   53: getstatic 348	com/mixpanel/android/mpmetrics/MPDbAdapter$Table:EVENTS	Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;
        //   56: invokevirtual 500	com/mixpanel/android/mpmetrics/MPDbAdapter:cleanupEvents	(JLcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;)V
        //   59: aload_0
        //   60: getfield 32	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:mDbAdapter	Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
        //   63: invokestatic 493	java/lang/System:currentTimeMillis	()J
        //   66: aload_0
        //   67: getfield 27	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:this$1	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
        //   70: getfield 38	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker:this$0	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
        //   73: invokestatic 46	com/mixpanel/android/mpmetrics/AnalyticsMessages:access$200	(Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;)Lcom/mixpanel/android/mpmetrics/MPConfig;
        //   76: invokevirtual 497	com/mixpanel/android/mpmetrics/MPConfig:getDataExpiration	()I
        //   79: i2l
        //   80: lsub
        //   81: getstatic 358	com/mixpanel/android/mpmetrics/MPDbAdapter$Table:PEOPLE	Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;
        //   84: invokevirtual 500	com/mixpanel/android/mpmetrics/MPDbAdapter:cleanupEvents	(JLcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;)V
        //   87: bipush -3
        //   89: istore_3
        //   90: aload_1
        //   91: getfield 505	android/os/Message:what	I
        //   94: ifne +149 -> 243
        //   97: aload_1
        //   98: getfield 509	android/os/Message:obj	Ljava/lang/Object;
        //   101: checkcast 89	org/json/JSONObject
        //   104: astore_1
        //   105: aload_0
        //   106: getfield 27	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:this$1	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
        //   109: getfield 38	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker:this$0	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
        //   112: ldc_w 511
        //   115: invokestatic 340	com/mixpanel/android/mpmetrics/AnalyticsMessages:access$000	(Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;)V
        //   118: aload_0
        //   119: getfield 27	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:this$1	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
        //   122: getfield 38	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker:this$0	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
        //   125: new 420	java/lang/StringBuilder
        //   128: dup
        //   129: invokespecial 421	java/lang/StringBuilder:<init>	()V
        //   132: ldc_w 513
        //   135: invokevirtual 427	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   138: aload_1
        //   139: invokevirtual 514	org/json/JSONObject:toString	()Ljava/lang/String;
        //   142: invokevirtual 427	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   145: invokevirtual 432	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   148: invokestatic 340	com/mixpanel/android/mpmetrics/AnalyticsMessages:access$000	(Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;)V
        //   151: aload_0
        //   152: getfield 32	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:mDbAdapter	Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
        //   155: aload_1
        //   156: getstatic 358	com/mixpanel/android/mpmetrics/MPDbAdapter$Table:PEOPLE	Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;
        //   159: invokevirtual 518	com/mixpanel/android/mpmetrics/MPDbAdapter:addJSON	(Lorg/json/JSONObject;Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;)I
        //   162: istore_2
        //   163: iload_2
        //   164: aload_0
        //   165: getfield 27	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:this$1	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
        //   168: getfield 38	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker:this$0	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
        //   171: invokestatic 46	com/mixpanel/android/mpmetrics/AnalyticsMessages:access$200	(Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;)Lcom/mixpanel/android/mpmetrics/MPConfig;
        //   174: invokevirtual 521	com/mixpanel/android/mpmetrics/MPConfig:getBulkUploadLimit	()I
        //   177: if_icmpge +9 -> 186
        //   180: iload_2
        //   181: bipush -2
        //   183: if_icmpne +614 -> 797
        //   186: invokestatic 526	android/os/SystemClock:elapsedRealtime	()J
        //   189: aload_0
        //   190: getfield 78	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:mRetryAfter	J
        //   193: lcmp
        //   194: iflt +603 -> 797
        //   197: aload_0
        //   198: getfield 27	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:this$1	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
        //   201: getfield 38	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker:this$0	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
        //   204: ldc_w 528
        //   207: invokestatic 340	com/mixpanel/android/mpmetrics/AnalyticsMessages:access$000	(Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;)V
        //   210: aload_0
        //   211: getfield 27	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:this$1	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
        //   214: invokestatic 532	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker:access$400	(Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;)V
        //   217: aload_0
        //   218: aload_0
        //   219: getfield 32	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:mDbAdapter	Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
        //   222: invokespecial 534	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:sendAllData	(Lcom/mixpanel/android/mpmetrics/MPDbAdapter;)V
        //   225: aload_0
        //   226: getfield 51	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:mDecideChecker	Lcom/mixpanel/android/mpmetrics/DecideChecker;
        //   229: aload_0
        //   230: getfield 27	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:this$1	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
        //   233: getfield 38	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker:this$0	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
        //   236: invokevirtual 328	com/mixpanel/android/mpmetrics/AnalyticsMessages:getPoster	()Lcom/mixpanel/android/util/RemoteService;
        //   239: invokevirtual 538	com/mixpanel/android/mpmetrics/DecideChecker:runDecideChecks	(Lcom/mixpanel/android/util/RemoteService;)V
        //   242: return
        //   243: aload_1
        //   244: getfield 505	android/os/Message:what	I
        //   247: iconst_1
        //   248: if_icmpne +181 -> 429
        //   251: aload_1
        //   252: getfield 509	android/os/Message:obj	Ljava/lang/Object;
        //   255: checkcast 239	com/mixpanel/android/mpmetrics/AnalyticsMessages$EventDescription
        //   258: astore_1
        //   259: aload_0
        //   260: aload_1
        //   261: invokespecial 540	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:prepareEventObject	(Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$EventDescription;)Lorg/json/JSONObject;
        //   264: astore 8
        //   266: aload_0
        //   267: getfield 27	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:this$1	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
        //   270: getfield 38	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker:this$0	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
        //   273: ldc_w 542
        //   276: invokestatic 340	com/mixpanel/android/mpmetrics/AnalyticsMessages:access$000	(Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;)V
        //   279: aload_0
        //   280: getfield 27	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:this$1	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
        //   283: getfield 38	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker:this$0	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
        //   286: new 420	java/lang/StringBuilder
        //   289: dup
        //   290: invokespecial 421	java/lang/StringBuilder:<init>	()V
        //   293: ldc_w 513
        //   296: invokevirtual 427	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   299: aload 8
        //   301: invokevirtual 514	org/json/JSONObject:toString	()Ljava/lang/String;
        //   304: invokevirtual 427	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   307: invokevirtual 432	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   310: invokestatic 340	com/mixpanel/android/mpmetrics/AnalyticsMessages:access$000	(Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;)V
        //   313: aload_0
        //   314: getfield 32	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:mDbAdapter	Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
        //   317: aload 8
        //   319: getstatic 348	com/mixpanel/android/mpmetrics/MPDbAdapter$Table:EVENTS	Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;
        //   322: invokevirtual 518	com/mixpanel/android/mpmetrics/MPDbAdapter:addJSON	(Lorg/json/JSONObject;Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;)I
        //   325: istore_2
        //   326: goto -163 -> 163
        //   329: astore 8
        //   331: ldc_w 281
        //   334: new 420	java/lang/StringBuilder
        //   337: dup
        //   338: invokespecial 421	java/lang/StringBuilder:<init>	()V
        //   341: ldc_w 544
        //   344: invokevirtual 427	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   347: aload_1
        //   348: invokevirtual 273	com/mixpanel/android/mpmetrics/AnalyticsMessages$EventDescription:getEventName	()Ljava/lang/String;
        //   351: invokevirtual 427	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   354: invokevirtual 432	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   357: aload 8
        //   359: invokestatic 454	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
        //   362: pop
        //   363: iload_3
        //   364: istore_2
        //   365: goto -202 -> 163
        //   368: astore 8
        //   370: ldc_w 281
        //   373: ldc_w 546
        //   376: aload 8
        //   378: invokestatic 454	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
        //   381: pop
        //   382: aload_0
        //   383: getfield 27	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:this$1	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
        //   386: invokestatic 550	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker:access$500	(Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;)Ljava/lang/Object;
        //   389: astore_1
        //   390: aload_1
        //   391: monitorenter
        //   392: aload_0
        //   393: getfield 27	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:this$1	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
        //   396: aconst_null
        //   397: invokestatic 554	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker:access$602	(Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;Landroid/os/Handler;)Landroid/os/Handler;
        //   400: pop
        //   401: invokestatic 560	android/os/Looper:myLooper	()Landroid/os/Looper;
        //   404: invokevirtual 563	android/os/Looper:quit	()V
        //   407: ldc_w 281
        //   410: ldc_w 565
        //   413: aload 8
        //   415: invokestatic 454	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
        //   418: pop
        //   419: aload_1
        //   420: monitorexit
        //   421: return
        //   422: astore 8
        //   424: aload_1
        //   425: monitorexit
        //   426: aload 8
        //   428: athrow
        //   429: aload_1
        //   430: getfield 505	android/os/Message:what	I
        //   433: iconst_2
        //   434: if_icmpne +97 -> 531
        //   437: aload_0
        //   438: getfield 27	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:this$1	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
        //   441: getfield 38	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker:this$0	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
        //   444: ldc_w 567
        //   447: invokestatic 340	com/mixpanel/android/mpmetrics/AnalyticsMessages:access$000	(Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;)V
        //   450: aload_0
        //   451: getfield 27	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:this$1	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
        //   454: invokestatic 532	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker:access$400	(Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;)V
        //   457: invokestatic 526	android/os/SystemClock:elapsedRealtime	()J
        //   460: lstore 4
        //   462: aload_0
        //   463: getfield 78	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:mRetryAfter	J
        //   466: lstore 6
        //   468: iload_3
        //   469: istore_2
        //   470: lload 4
        //   472: lload 6
        //   474: lcmp
        //   475: iflt -312 -> 163
        //   478: aload_0
        //   479: aload_0
        //   480: getfield 32	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:mDbAdapter	Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
        //   483: invokespecial 534	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:sendAllData	(Lcom/mixpanel/android/mpmetrics/MPDbAdapter;)V
        //   486: aload_0
        //   487: getfield 51	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:mDecideChecker	Lcom/mixpanel/android/mpmetrics/DecideChecker;
        //   490: aload_0
        //   491: getfield 27	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:this$1	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
        //   494: getfield 38	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker:this$0	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
        //   497: invokevirtual 328	com/mixpanel/android/mpmetrics/AnalyticsMessages:getPoster	()Lcom/mixpanel/android/util/RemoteService;
        //   500: invokevirtual 538	com/mixpanel/android/mpmetrics/DecideChecker:runDecideChecks	(Lcom/mixpanel/android/util/RemoteService;)V
        //   503: iload_3
        //   504: istore_2
        //   505: goto -342 -> 163
        //   508: astore_1
        //   509: aload_0
        //   510: invokestatic 526	android/os/SystemClock:elapsedRealtime	()J
        //   513: aload_1
        //   514: invokevirtual 570	com/mixpanel/android/util/RemoteService$ServiceUnavailableException:getRetryAfter	()I
        //   517: sipush 1000
        //   520: imul
        //   521: i2l
        //   522: ladd
        //   523: putfield 78	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:mRetryAfter	J
        //   526: iload_3
        //   527: istore_2
        //   528: goto -365 -> 163
        //   531: aload_1
        //   532: getfield 505	android/os/Message:what	I
        //   535: bipush 12
        //   537: if_icmpne +98 -> 635
        //   540: aload_0
        //   541: getfield 27	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:this$1	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
        //   544: getfield 38	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker:this$0	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
        //   547: ldc_w 572
        //   550: invokestatic 340	com/mixpanel/android/mpmetrics/AnalyticsMessages:access$000	(Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;)V
        //   553: aload_1
        //   554: getfield 509	android/os/Message:obj	Ljava/lang/Object;
        //   557: checkcast 574	com/mixpanel/android/mpmetrics/DecideMessages
        //   560: astore_1
        //   561: aload_0
        //   562: getfield 51	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:mDecideChecker	Lcom/mixpanel/android/mpmetrics/DecideChecker;
        //   565: aload_1
        //   566: invokevirtual 578	com/mixpanel/android/mpmetrics/DecideChecker:addDecideCheck	(Lcom/mixpanel/android/mpmetrics/DecideMessages;)V
        //   569: invokestatic 526	android/os/SystemClock:elapsedRealtime	()J
        //   572: lstore 4
        //   574: aload_0
        //   575: getfield 78	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:mRetryAfter	J
        //   578: lstore 6
        //   580: iload_3
        //   581: istore_2
        //   582: lload 4
        //   584: lload 6
        //   586: lcmp
        //   587: iflt -424 -> 163
        //   590: aload_0
        //   591: getfield 51	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:mDecideChecker	Lcom/mixpanel/android/mpmetrics/DecideChecker;
        //   594: aload_0
        //   595: getfield 27	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:this$1	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
        //   598: getfield 38	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker:this$0	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
        //   601: invokevirtual 328	com/mixpanel/android/mpmetrics/AnalyticsMessages:getPoster	()Lcom/mixpanel/android/util/RemoteService;
        //   604: invokevirtual 538	com/mixpanel/android/mpmetrics/DecideChecker:runDecideChecks	(Lcom/mixpanel/android/util/RemoteService;)V
        //   607: iload_3
        //   608: istore_2
        //   609: goto -446 -> 163
        //   612: astore_1
        //   613: aload_0
        //   614: invokestatic 526	android/os/SystemClock:elapsedRealtime	()J
        //   617: aload_1
        //   618: invokevirtual 570	com/mixpanel/android/util/RemoteService$ServiceUnavailableException:getRetryAfter	()I
        //   621: sipush 1000
        //   624: imul
        //   625: i2l
        //   626: ladd
        //   627: putfield 78	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:mRetryAfter	J
        //   630: iload_3
        //   631: istore_2
        //   632: goto -469 -> 163
        //   635: aload_1
        //   636: getfield 505	android/os/Message:what	I
        //   639: bipush 13
        //   641: if_icmpne +19 -> 660
        //   644: aload_0
        //   645: aload_1
        //   646: getfield 509	android/os/Message:obj	Ljava/lang/Object;
        //   649: checkcast 264	java/lang/String
        //   652: invokespecial 580	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:runGCMRegistration	(Ljava/lang/String;)V
        //   655: iload_3
        //   656: istore_2
        //   657: goto -494 -> 163
        //   660: aload_1
        //   661: getfield 505	android/os/Message:what	I
        //   664: iconst_5
        //   665: if_icmpne +81 -> 746
        //   668: ldc_w 281
        //   671: new 420	java/lang/StringBuilder
        //   674: dup
        //   675: invokespecial 421	java/lang/StringBuilder:<init>	()V
        //   678: ldc_w 582
        //   681: invokevirtual 427	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   684: invokestatic 588	java/lang/Thread:currentThread	()Ljava/lang/Thread;
        //   687: invokevirtual 591	java/lang/Thread:getId	()J
        //   690: invokevirtual 594	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
        //   693: invokevirtual 432	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   696: invokestatic 320	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
        //   699: pop
        //   700: aload_0
        //   701: getfield 27	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:this$1	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
        //   704: invokestatic 550	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker:access$500	(Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;)Ljava/lang/Object;
        //   707: astore_1
        //   708: aload_1
        //   709: monitorenter
        //   710: aload_0
        //   711: getfield 32	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:mDbAdapter	Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
        //   714: invokevirtual 597	com/mixpanel/android/mpmetrics/MPDbAdapter:deleteDB	()V
        //   717: aload_0
        //   718: getfield 27	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:this$1	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
        //   721: aconst_null
        //   722: invokestatic 554	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker:access$602	(Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;Landroid/os/Handler;)Landroid/os/Handler;
        //   725: pop
        //   726: invokestatic 560	android/os/Looper:myLooper	()Landroid/os/Looper;
        //   729: invokevirtual 563	android/os/Looper:quit	()V
        //   732: aload_1
        //   733: monitorexit
        //   734: iload_3
        //   735: istore_2
        //   736: goto -573 -> 163
        //   739: astore 8
        //   741: aload_1
        //   742: monitorexit
        //   743: aload 8
        //   745: athrow
        //   746: ldc_w 281
        //   749: new 420	java/lang/StringBuilder
        //   752: dup
        //   753: invokespecial 421	java/lang/StringBuilder:<init>	()V
        //   756: ldc_w 599
        //   759: invokevirtual 427	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   762: aload_1
        //   763: invokevirtual 602	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   766: invokevirtual 432	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   769: invokestatic 604	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
        //   772: pop
        //   773: iload_3
        //   774: istore_2
        //   775: goto -612 -> 163
        //   778: astore_1
        //   779: aload_0
        //   780: invokestatic 526	android/os/SystemClock:elapsedRealtime	()J
        //   783: aload_1
        //   784: invokevirtual 570	com/mixpanel/android/util/RemoteService$ServiceUnavailableException:getRetryAfter	()I
        //   787: sipush 1000
        //   790: imul
        //   791: i2l
        //   792: ladd
        //   793: putfield 78	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:mRetryAfter	J
        //   796: return
        //   797: iload_2
        //   798: ifle -556 -> 242
        //   801: aload_0
        //   802: iconst_2
        //   803: invokevirtual 475	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:hasMessages	(I)Z
        //   806: ifne -564 -> 242
        //   809: aload_0
        //   810: getfield 27	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:this$1	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
        //   813: getfield 38	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker:this$0	Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
        //   816: new 420	java/lang/StringBuilder
        //   819: dup
        //   820: invokespecial 421	java/lang/StringBuilder:<init>	()V
        //   823: ldc_w 606
        //   826: invokevirtual 427	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   829: iload_2
        //   830: invokevirtual 609	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   833: ldc_w 611
        //   836: invokevirtual 427	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   839: aload_0
        //   840: getfield 65	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:mFlushInterval	J
        //   843: invokevirtual 594	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
        //   846: invokevirtual 432	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   849: invokestatic 340	com/mixpanel/android/mpmetrics/AnalyticsMessages:access$000	(Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;)V
        //   852: aload_0
        //   853: getfield 65	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:mFlushInterval	J
        //   856: lconst_0
        //   857: lcmp
        //   858: iflt -616 -> 242
        //   861: aload_0
        //   862: iconst_2
        //   863: aload_0
        //   864: getfield 65	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:mFlushInterval	J
        //   867: invokevirtual 479	com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler:sendEmptyMessageDelayed	(IJ)Z
        //   870: pop
        //   871: return
        //   872: astore 8
        //   874: ldc_w 281
        //   877: ldc_w 613
        //   880: aload 8
        //   882: invokestatic 454	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
        //   885: pop
        //   886: goto -467 -> 419
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	889	0	this	AnalyticsMessageHandler
        //   162	668	2	i	int
        //   89	685	3	j	int
        //   460	123	4	l1	long
        //   466	119	6	l2	long
        //   264	54	8	localJSONObject	JSONObject
        //   329	29	8	localJSONException	JSONException
        //   368	46	8	localRuntimeException	RuntimeException
        //   422	5	8	localObject1	Object
        //   739	5	8	localObject2	Object
        //   872	9	8	localException	Exception
        // Exception table:
        //   from	to	target	type
        //   259	326	329	org/json/JSONException
        //   90	163	368	java/lang/RuntimeException
        //   163	180	368	java/lang/RuntimeException
        //   186	217	368	java/lang/RuntimeException
        //   217	242	368	java/lang/RuntimeException
        //   243	259	368	java/lang/RuntimeException
        //   259	326	368	java/lang/RuntimeException
        //   331	363	368	java/lang/RuntimeException
        //   429	468	368	java/lang/RuntimeException
        //   478	503	368	java/lang/RuntimeException
        //   509	526	368	java/lang/RuntimeException
        //   531	580	368	java/lang/RuntimeException
        //   590	607	368	java/lang/RuntimeException
        //   613	630	368	java/lang/RuntimeException
        //   635	655	368	java/lang/RuntimeException
        //   660	710	368	java/lang/RuntimeException
        //   743	746	368	java/lang/RuntimeException
        //   746	773	368	java/lang/RuntimeException
        //   779	796	368	java/lang/RuntimeException
        //   801	871	368	java/lang/RuntimeException
        //   392	401	422	finally
        //   401	419	422	finally
        //   419	421	422	finally
        //   424	426	422	finally
        //   874	886	422	finally
        //   478	503	508	com/mixpanel/android/util/RemoteService$ServiceUnavailableException
        //   590	607	612	com/mixpanel/android/util/RemoteService$ServiceUnavailableException
        //   710	734	739	finally
        //   741	743	739	finally
        //   217	242	778	com/mixpanel/android/util/RemoteService$ServiceUnavailableException
        //   401	419	872	java/lang/Exception
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/mpmetrics/AnalyticsMessages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */