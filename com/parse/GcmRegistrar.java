package com.parse;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import bolts.Continuation;
import bolts.Task;
import bolts.Task.TaskCompletionSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

class GcmRegistrar
{
  private static final String ERROR_EXTRA = "error";
  private static final String FILENAME_DEVICE_TOKEN_LAST_MODIFIED = "deviceTokenLastModified";
  private static final String PARSE_SENDER_ID = "1076345567071";
  public static final String REGISTER_ACTION = "com.google.android.c2dm.intent.REGISTER";
  private static final String REGISTRATION_ID_EXTRA = "registration_id";
  private static final String SENDER_ID_EXTRA = "com.parse.push.gcm_sender_id";
  private static final String TAG = "com.parse.GcmRegistrar";
  private Context context = null;
  private long localDeviceTokenLastModified;
  private final Object localDeviceTokenLastModifiedMutex = new Object();
  private final Object lock = new Object();
  private Request request = null;
  
  GcmRegistrar(Context paramContext)
  {
    this.context = paramContext;
  }
  
  private static String actualSenderIDFromExtra(Object paramObject)
  {
    if (!(paramObject instanceof String)) {}
    do
    {
      return null;
      paramObject = (String)paramObject;
    } while (!((String)paramObject).startsWith("id:"));
    return ((String)paramObject).substring(3);
  }
  
  static void deleteLocalDeviceTokenLastModifiedFile()
  {
    ParseFileUtils.deleteQuietly(getLocalDeviceTokenLastModifiedFile());
  }
  
  public static GcmRegistrar getInstance()
  {
    return Singleton.INSTANCE;
  }
  
  private Task<Long> getLocalDeviceTokenLastModifiedAsync()
  {
    Task.call(new Callable()
    {
      public Long call()
        throws Exception
      {
        synchronized (GcmRegistrar.this.localDeviceTokenLastModifiedMutex)
        {
          long l = GcmRegistrar.this.localDeviceTokenLastModified;
          if (l == 0L) {}
          try
          {
            String str = ParseFileUtils.readFileToString(GcmRegistrar.getLocalDeviceTokenLastModifiedFile(), "UTF-8");
            GcmRegistrar.access$502(GcmRegistrar.this, Long.valueOf(str).longValue());
            l = GcmRegistrar.this.localDeviceTokenLastModified;
            return Long.valueOf(l);
          }
          catch (IOException localIOException)
          {
            for (;;)
            {
              GcmRegistrar.access$502(GcmRegistrar.this, 0L);
            }
          }
        }
      }
    }, Task.BACKGROUND_EXECUTOR);
  }
  
  static File getLocalDeviceTokenLastModifiedFile()
  {
    return new File(Parse.getParseCacheDir("GCMRegistrar"), "deviceTokenLastModified");
  }
  
  private Task<Void> sendRegistrationRequestAsync()
  {
    for (;;)
    {
      Object localObject5;
      String str;
      synchronized (this.lock)
      {
        if (this.request != null)
        {
          localObject1 = Task.forResult(null);
          return (Task<Void>)localObject1;
        }
        localObject5 = ManifestInfo.getApplicationMetadata(this.context);
        str = "1076345567071";
        Object localObject1 = str;
        if (localObject5 != null)
        {
          localObject5 = ((Bundle)localObject5).get("com.parse.push.gcm_sender_id");
          localObject1 = str;
          if (localObject5 != null)
          {
            localObject1 = actualSenderIDFromExtra(localObject5);
            if (localObject1 == null) {
              break label133;
            }
            localObject1 = "1076345567071" + "," + (String)localObject1;
          }
        }
        this.request = Request.createAndSend(this.context, (String)localObject1);
        localObject1 = this.request.getTask().continueWith(new Continuation()
        {
          public Void then(Task<String> arg1)
          {
            ??? = ???.getError();
            if (??? != null) {
              PLog.e("com.parse.GcmRegistrar", "Got error when trying to register for GCM push", ???);
            }
            synchronized (GcmRegistrar.this.lock)
            {
              GcmRegistrar.access$202(GcmRegistrar.this, null);
              return null;
            }
          }
        });
        return (Task<Void>)localObject1;
      }
      label133:
      PLog.e("com.parse.GcmRegistrar", "Found com.parse.push.gcm_sender_id <meta-data> element with value \"" + localObject5.toString() + "\", but the value is missing the expected \"id:\" " + "prefix.");
      Object localObject3 = str;
    }
  }
  
  int getRequestIdentifier()
  {
    for (;;)
    {
      synchronized (this.lock)
      {
        if (this.request != null)
        {
          i = this.request.identifier;
          return i;
        }
      }
      int i = 0;
    }
  }
  
  public Task<Void> handleRegistrationIntentAsync(Intent paramIntent)
  {
    ArrayList localArrayList = new ArrayList();
    ??? = paramIntent.getStringExtra("registration_id");
    if ((??? != null) && (((String)???).length() > 0))
    {
      ParseInstallation localParseInstallation = ParseInstallation.getCurrentInstallation();
      if (!((String)???).equals(localParseInstallation.getDeviceToken()))
      {
        localParseInstallation.setPushType(PushType.GCM);
        localParseInstallation.setDeviceToken((String)???);
        localArrayList.add(localParseInstallation.saveInBackground());
      }
      localArrayList.add(updateLocalDeviceTokenLastModifiedAsync());
    }
    synchronized (this.lock)
    {
      if (this.request != null) {
        this.request.onReceiveResponseIntent(paramIntent);
      }
      return Task.whenAll(localArrayList);
    }
  }
  
  Task<Boolean> isLocalDeviceTokenStaleAsync()
  {
    getLocalDeviceTokenLastModifiedAsync().onSuccessTask(new Continuation()
    {
      public Task<Boolean> then(Task<Long> paramAnonymousTask)
        throws Exception
      {
        if (((Long)paramAnonymousTask.getResult()).longValue() != ManifestInfo.getLastModified()) {}
        for (boolean bool = true;; bool = false) {
          return Task.forResult(Boolean.valueOf(bool));
        }
      }
    });
  }
  
  public Task<Void> registerAsync()
  {
    if (ManifestInfo.getPushType() != PushType.GCM) {
      return Task.forResult(null);
    }
    for (;;)
    {
      synchronized (this.lock)
      {
        final ParseInstallation localParseInstallation = ParseInstallation.getCurrentInstallation();
        if (localParseInstallation.getDeviceToken() == null)
        {
          Task localTask1 = Task.forResult(Boolean.valueOf(true));
          localTask1 = localTask1.onSuccessTask(new Continuation()
          {
            public Task<Void> then(Task<Boolean> paramAnonymousTask)
              throws Exception
            {
              if (!((Boolean)paramAnonymousTask.getResult()).booleanValue()) {
                return Task.forResult(null);
              }
              if (localParseInstallation.getPushType() != PushType.GCM) {
                localParseInstallation.setPushType(PushType.GCM);
              }
              for (paramAnonymousTask = localParseInstallation.saveInBackground();; paramAnonymousTask = Task.forResult(null))
              {
                GcmRegistrar.this.sendRegistrationRequestAsync();
                return paramAnonymousTask;
              }
            }
          });
          return localTask1;
        }
      }
      Task localTask2 = isLocalDeviceTokenStaleAsync();
    }
  }
  
  Task<Void> updateLocalDeviceTokenLastModifiedAsync()
  {
    Task.call(new Callable()
    {
      public Void call()
        throws Exception
      {
        long l;
        synchronized (GcmRegistrar.this.localDeviceTokenLastModifiedMutex)
        {
          GcmRegistrar.access$502(GcmRegistrar.this, ManifestInfo.getLastModified());
          l = GcmRegistrar.this.localDeviceTokenLastModified;
        }
        try
        {
          ParseFileUtils.writeStringToFile(GcmRegistrar.getLocalDeviceTokenLastModifiedFile(), String.valueOf(l), "UTF-8");
          return null;
          localObject2 = finally;
          throw ((Throwable)localObject2);
        }
        catch (IOException localIOException)
        {
          for (;;) {}
        }
      }
    }, Task.BACKGROUND_EXECUTOR);
  }
  
  private static class Request
  {
    private static final int BACKOFF_INTERVAL_MS = 3000;
    private static final int MAX_RETRIES = 5;
    private static final String RETRY_ACTION = "com.parse.RetryGcmRegistration";
    private final PendingIntent appIntent;
    private final Context context;
    private final int identifier;
    private final Random random;
    private final PendingIntent retryIntent;
    private final BroadcastReceiver retryReceiver;
    private final String senderId;
    private final Task<String>.TaskCompletionSource tcs;
    private final AtomicInteger tries;
    
    private Request(Context paramContext, String paramString)
    {
      this.context = paramContext;
      this.senderId = paramString;
      this.random = new Random();
      this.identifier = this.random.nextInt();
      this.tcs = Task.create();
      this.appIntent = PendingIntent.getBroadcast(this.context, this.identifier, new Intent(), 0);
      this.tries = new AtomicInteger(0);
      paramString = this.context.getPackageName();
      Object localObject = new Intent("com.parse.RetryGcmRegistration").setPackage(paramString);
      ((Intent)localObject).addCategory(paramString);
      ((Intent)localObject).putExtra("random", this.identifier);
      this.retryIntent = PendingIntent.getBroadcast(this.context, this.identifier, (Intent)localObject, 0);
      this.retryReceiver = new BroadcastReceiver()
      {
        public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
        {
          if ((paramAnonymousIntent != null) && (paramAnonymousIntent.getIntExtra("random", 0) == GcmRegistrar.Request.this.identifier)) {
            GcmRegistrar.Request.this.send();
          }
        }
      };
      localObject = new IntentFilter();
      ((IntentFilter)localObject).addAction("com.parse.RetryGcmRegistration");
      ((IntentFilter)localObject).addCategory(paramString);
      paramContext.registerReceiver(this.retryReceiver, (IntentFilter)localObject);
    }
    
    public static Request createAndSend(Context paramContext, String paramString)
    {
      paramContext = new Request(paramContext, paramString);
      paramContext.send();
      return paramContext;
    }
    
    private void finish(String paramString1, String paramString2)
    {
      if (paramString1 != null) {}
      for (boolean bool = this.tcs.trySetResult(paramString1);; bool = this.tcs.trySetError(new Exception("GCM registration error: " + paramString2)))
      {
        if (bool)
        {
          this.appIntent.cancel();
          this.retryIntent.cancel();
          this.context.unregisterReceiver(this.retryReceiver);
        }
        return;
      }
    }
    
    private void send()
    {
      Object localObject2 = new Intent("com.google.android.c2dm.intent.REGISTER");
      ((Intent)localObject2).setPackage("com.google.android.gsf");
      ((Intent)localObject2).putExtra("sender", this.senderId);
      ((Intent)localObject2).putExtra("app", this.appIntent);
      Object localObject1 = null;
      try
      {
        localObject2 = this.context.startService((Intent)localObject2);
        localObject1 = localObject2;
      }
      catch (SecurityException localSecurityException)
      {
        for (;;) {}
      }
      if (localObject1 == null) {
        finish(null, "GSF_PACKAGE_NOT_AVAILABLE");
      }
      this.tries.incrementAndGet();
      PLog.v("com.parse.GcmRegistrar", "Sending GCM registration intent");
    }
    
    public Task<String> getTask()
    {
      return this.tcs.getTask();
    }
    
    public void onReceiveResponseIntent(Intent paramIntent)
    {
      String str = paramIntent.getStringExtra("registration_id");
      paramIntent = paramIntent.getStringExtra("error");
      if ((str == null) && (paramIntent == null))
      {
        PLog.e("com.parse.GcmRegistrar", "Got no registration info in GCM onReceiveResponseIntent");
        return;
      }
      if (("SERVICE_NOT_AVAILABLE".equals(paramIntent)) && (this.tries.get() < 5))
      {
        paramIntent = (AlarmManager)this.context.getSystemService("alarm");
        long l = (1 << this.tries.get()) * 3000 + this.random.nextInt(3000);
        paramIntent.set(2, SystemClock.elapsedRealtime() + l, this.retryIntent);
        return;
      }
      finish(str, paramIntent);
    }
  }
  
  private static class Singleton
  {
    public static final GcmRegistrar INSTANCE = new GcmRegistrar(Parse.getApplicationContext());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/GcmRegistrar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */