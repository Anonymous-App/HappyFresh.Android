package com.parse;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import bolts.Task;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONException;
import org.json.JSONObject;

class GCMService
  implements ProxyService
{
  public static final String RECEIVE_PUSH_ACTION = "com.google.android.c2dm.intent.RECEIVE";
  public static final String REGISTER_RESPONSE_ACTION = "com.google.android.c2dm.intent.REGISTRATION";
  private static final String TAG = "GCMService";
  private ExecutorService executor;
  private final WeakReference<Service> parent;
  
  GCMService(Service paramService)
  {
    this.parent = new WeakReference(paramService);
  }
  
  private void handleGcmPushIntent(Intent paramIntent)
  {
    String str1 = paramIntent.getStringExtra("message_type");
    if (str1 != null)
    {
      PLog.i("GCMService", "Ignored special message type " + str1 + " from GCM via intent " + paramIntent);
      return;
    }
    String str2 = paramIntent.getStringExtra("push_id");
    String str3 = paramIntent.getStringExtra("time");
    str1 = paramIntent.getStringExtra("data");
    String str4 = paramIntent.getStringExtra("channel");
    paramIntent = null;
    if (str1 != null) {}
    try
    {
      paramIntent = new JSONObject(str1);
      PushRouter.getInstance().handlePush(str2, str3, str4, paramIntent);
      return;
    }
    catch (JSONException paramIntent)
    {
      PLog.e("GCMService", "Ignoring push because of JSON exception while processing: " + str1, paramIntent);
    }
  }
  
  private void handleGcmRegistrationIntent(Intent paramIntent)
  {
    try
    {
      GcmRegistrar.getInstance().handleRegistrationIntentAsync(paramIntent).waitForCompletion();
      return;
    }
    catch (InterruptedException paramIntent) {}
  }
  
  private void onHandleIntent(Intent paramIntent)
  {
    String str;
    if (paramIntent != null)
    {
      str = paramIntent.getAction();
      if ("com.google.android.c2dm.intent.REGISTRATION".equals(str)) {
        handleGcmRegistrationIntent(paramIntent);
      }
    }
    else
    {
      return;
    }
    if ("com.google.android.c2dm.intent.RECEIVE".equals(str))
    {
      handleGcmPushIntent(paramIntent);
      return;
    }
    PLog.e("GCMService", "PushService got unknown intent in GCM mode: " + paramIntent);
  }
  
  private void stopParent(int paramInt)
  {
    Service localService = (Service)this.parent.get();
    if (localService != null) {
      localService.stopSelf(paramInt);
    }
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onCreate()
  {
    this.executor = Executors.newSingleThreadExecutor();
  }
  
  public void onDestroy()
  {
    if (this.executor != null)
    {
      this.executor.shutdown();
      this.executor = null;
    }
  }
  
  public int onStartCommand(final Intent paramIntent, int paramInt1, final int paramInt2)
  {
    this.executor.execute(new Runnable()
    {
      public void run()
      {
        try
        {
          GCMService.this.onHandleIntent(paramIntent);
          return;
        }
        finally
        {
          ServiceUtils.completeWakefulIntent(paramIntent);
          GCMService.this.stopParent(paramInt2);
        }
      }
    });
    return 2;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/GCMService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */