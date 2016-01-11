package com.google.android.gms.gcm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.zzc;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GoogleCloudMessaging
{
  public static final String ERROR_MAIN_THREAD = "MAIN_THREAD";
  public static final String ERROR_SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";
  public static final String INSTANCE_ID_SCOPE = "GCM";
  @Deprecated
  public static final String MESSAGE_TYPE_DELETED = "deleted_messages";
  @Deprecated
  public static final String MESSAGE_TYPE_MESSAGE = "gcm";
  @Deprecated
  public static final String MESSAGE_TYPE_SEND_ERROR = "send_error";
  @Deprecated
  public static final String MESSAGE_TYPE_SEND_EVENT = "send_event";
  public static int zzavP = 5000000;
  public static int zzavQ = 6500000;
  public static int zzavR = 7000000;
  static GoogleCloudMessaging zzavS;
  private static final AtomicInteger zzavV = new AtomicInteger(1);
  private PendingIntent zzavT;
  private Map<String, Handler> zzavU = Collections.synchronizedMap(new HashMap());
  private final BlockingQueue<Intent> zzavW = new LinkedBlockingQueue();
  final Messenger zzavX = new Messenger(new Handler(Looper.getMainLooper())
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if ((paramAnonymousMessage == null) || (!(paramAnonymousMessage.obj instanceof Intent))) {
        Log.w("GCM", "Dropping invalid message");
      }
      paramAnonymousMessage = (Intent)paramAnonymousMessage.obj;
      if ("com.google.android.c2dm.intent.REGISTRATION".equals(paramAnonymousMessage.getAction())) {
        GoogleCloudMessaging.zza(GoogleCloudMessaging.this).add(paramAnonymousMessage);
      }
      while (GoogleCloudMessaging.zza(GoogleCloudMessaging.this, paramAnonymousMessage)) {
        return;
      }
      paramAnonymousMessage.setPackage(GoogleCloudMessaging.zzb(GoogleCloudMessaging.this).getPackageName());
      GoogleCloudMessaging.zzb(GoogleCloudMessaging.this).sendBroadcast(paramAnonymousMessage);
    }
  });
  private Context zzpH;
  
  public static GoogleCloudMessaging getInstance(Context paramContext)
  {
    try
    {
      if (zzavS == null)
      {
        zzavS = new GoogleCloudMessaging();
        zzavS.zzpH = paramContext.getApplicationContext();
      }
      paramContext = zzavS;
      return paramContext;
    }
    finally {}
  }
  
  static String zza(Intent paramIntent, String paramString)
    throws IOException
  {
    if (paramIntent == null) {
      throw new IOException("SERVICE_NOT_AVAILABLE");
    }
    paramString = paramIntent.getStringExtra(paramString);
    if (paramString != null) {
      return paramString;
    }
    paramIntent = paramIntent.getStringExtra("error");
    if (paramIntent != null) {
      throw new IOException(paramIntent);
    }
    throw new IOException("SERVICE_NOT_AVAILABLE");
  }
  
  private void zza(String paramString1, String paramString2, long paramLong, int paramInt, Bundle paramBundle)
    throws IOException
  {
    if (paramString1 == null) {
      throw new IllegalArgumentException("Missing 'to'");
    }
    Object localObject1 = new Intent("com.google.android.gcm.intent.SEND");
    if (paramBundle != null) {
      ((Intent)localObject1).putExtras(paramBundle);
    }
    zzm((Intent)localObject1);
    ((Intent)localObject1).setPackage(zzas(this.zzpH));
    ((Intent)localObject1).putExtra("google.to", paramString1);
    ((Intent)localObject1).putExtra("google.message_id", paramString2);
    ((Intent)localObject1).putExtra("google.ttl", Long.toString(paramLong));
    ((Intent)localObject1).putExtra("google.delay", Integer.toString(paramInt));
    if (zzas(this.zzpH).contains(".gsf"))
    {
      localObject1 = new Bundle();
      Iterator localIterator = paramBundle.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        Object localObject2 = paramBundle.get(str);
        if ((localObject2 instanceof String)) {
          ((Bundle)localObject1).putString("gcm." + str, (String)localObject2);
        }
      }
      ((Bundle)localObject1).putString("google.to", paramString1);
      ((Bundle)localObject1).putString("google.message_id", paramString2);
      InstanceID.getInstance(this.zzpH).zzc("GCM", "upstream", (Bundle)localObject1);
      return;
    }
    this.zzpH.sendOrderedBroadcast((Intent)localObject1, "com.google.android.gtalkservice.permission.GTALK_SERVICE");
  }
  
  public static String zzas(Context paramContext)
  {
    return zzc.zzaw(paramContext);
  }
  
  public static int zzat(Context paramContext)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    try
    {
      int i = localPackageManager.getPackageInfo(zzas(paramContext), 0).versionCode;
      return i;
    }
    catch (PackageManager.NameNotFoundException paramContext) {}
    return -1;
  }
  
  private boolean zzl(Intent paramIntent)
  {
    Object localObject2 = paramIntent.getStringExtra("In-Reply-To");
    Object localObject1 = localObject2;
    if (localObject2 == null)
    {
      localObject1 = localObject2;
      if (paramIntent.hasExtra("error")) {
        localObject1 = paramIntent.getStringExtra("google.message_id");
      }
    }
    if (localObject1 != null)
    {
      localObject1 = (Handler)this.zzavU.remove(localObject1);
      if (localObject1 != null)
      {
        localObject2 = Message.obtain();
        ((Message)localObject2).obj = paramIntent;
        return ((Handler)localObject1).sendMessage((Message)localObject2);
      }
    }
    return false;
  }
  
  private String zztS()
  {
    return "google.rpc" + String.valueOf(zzavV.getAndIncrement());
  }
  
  public void close()
  {
    zzavS = null;
    zza.zzavF = null;
    zztT();
  }
  
  public String getMessageType(Intent paramIntent)
  {
    if (!"com.google.android.c2dm.intent.RECEIVE".equals(paramIntent.getAction())) {
      paramIntent = null;
    }
    String str;
    do
    {
      return paramIntent;
      str = paramIntent.getStringExtra("message_type");
      paramIntent = str;
    } while (str != null);
    return "gcm";
  }
  
  /* Error */
  @Deprecated
  public String register(String... paramVarArgs)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: invokevirtual 345	com/google/android/gms/gcm/GoogleCloudMessaging:zzc	([Ljava/lang/String;)Ljava/lang/String;
    //   7: astore_1
    //   8: new 191	android/os/Bundle
    //   11: dup
    //   12: invokespecial 192	android/os/Bundle:<init>	()V
    //   15: astore_2
    //   16: aload_0
    //   17: getfield 116	com/google/android/gms/gcm/GoogleCloudMessaging:zzpH	Landroid/content/Context;
    //   20: invokestatic 154	com/google/android/gms/gcm/GoogleCloudMessaging:zzas	(Landroid/content/Context;)Ljava/lang/String;
    //   23: ldc -73
    //   25: invokevirtual 189	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   28: ifeq +30 -> 58
    //   31: aload_2
    //   32: ldc_w 347
    //   35: aload_1
    //   36: invokevirtual 232	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   39: aload_0
    //   40: getfield 116	com/google/android/gms/gcm/GoogleCloudMessaging:zzpH	Landroid/content/Context;
    //   43: invokestatic 237	com/google/android/gms/iid/InstanceID:getInstance	(Landroid/content/Context;)Lcom/google/android/gms/iid/InstanceID;
    //   46: aload_1
    //   47: ldc 16
    //   49: aload_2
    //   50: invokevirtual 350	com/google/android/gms/iid/InstanceID:getToken	(Ljava/lang/String;Ljava/lang/String;Landroid/os/Bundle;)Ljava/lang/String;
    //   53: astore_1
    //   54: aload_0
    //   55: monitorexit
    //   56: aload_1
    //   57: areturn
    //   58: aload_2
    //   59: ldc_w 352
    //   62: aload_1
    //   63: invokevirtual 232	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   66: aload_0
    //   67: aload_2
    //   68: invokevirtual 355	com/google/android/gms/gcm/GoogleCloudMessaging:zzx	(Landroid/os/Bundle;)Landroid/content/Intent;
    //   71: ldc_w 357
    //   74: invokestatic 359	com/google/android/gms/gcm/GoogleCloudMessaging:zza	(Landroid/content/Intent;Ljava/lang/String;)Ljava/lang/String;
    //   77: astore_1
    //   78: goto -24 -> 54
    //   81: astore_1
    //   82: aload_0
    //   83: monitorexit
    //   84: aload_1
    //   85: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	86	0	this	GoogleCloudMessaging
    //   0	86	1	paramVarArgs	String[]
    //   15	53	2	localBundle	Bundle
    // Exception table:
    //   from	to	target	type
    //   2	54	81	finally
    //   58	78	81	finally
  }
  
  public void send(String paramString1, String paramString2, long paramLong, Bundle paramBundle)
    throws IOException
  {
    zza(paramString1, paramString2, paramLong, -1, paramBundle);
  }
  
  public void send(String paramString1, String paramString2, Bundle paramBundle)
    throws IOException
  {
    send(paramString1, paramString2, -1L, paramBundle);
  }
  
  @Deprecated
  public void unregister()
    throws IOException
  {
    try
    {
      if (Looper.getMainLooper() == Looper.myLooper()) {
        throw new IOException("MAIN_THREAD");
      }
    }
    finally {}
    InstanceID.getInstance(this.zzpH).deleteInstanceID();
  }
  
  String zzc(String... paramVarArgs)
  {
    if ((paramVarArgs == null) || (paramVarArgs.length == 0)) {
      throw new IllegalArgumentException("No senderIds");
    }
    StringBuilder localStringBuilder = new StringBuilder(paramVarArgs[0]);
    int i = 1;
    while (i < paramVarArgs.length)
    {
      localStringBuilder.append(',').append(paramVarArgs[i]);
      i += 1;
    }
    return localStringBuilder.toString();
  }
  
  void zzm(Intent paramIntent)
  {
    try
    {
      if (this.zzavT == null)
      {
        Intent localIntent = new Intent();
        localIntent.setPackage("com.google.example.invalidpackage");
        this.zzavT = PendingIntent.getBroadcast(this.zzpH, 0, localIntent, 0);
      }
      paramIntent.putExtra("app", this.zzavT);
      return;
    }
    finally {}
  }
  
  void zztT()
  {
    try
    {
      if (this.zzavT != null)
      {
        this.zzavT.cancel();
        this.zzavT = null;
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  @Deprecated
  Intent zzx(Bundle paramBundle)
    throws IOException
  {
    if (Looper.getMainLooper() == Looper.myLooper()) {
      throw new IOException("MAIN_THREAD");
    }
    if (zzat(this.zzpH) < 0) {
      throw new IOException("Google Play Services missing");
    }
    Bundle localBundle = paramBundle;
    if (paramBundle == null) {
      localBundle = new Bundle();
    }
    paramBundle = new Intent("com.google.android.c2dm.intent.REGISTER");
    paramBundle.setPackage(zzas(this.zzpH));
    zzm(paramBundle);
    paramBundle.putExtra("google.message_id", zztS());
    paramBundle.putExtras(localBundle);
    paramBundle.putExtra("google.messenger", this.zzavX);
    this.zzpH.startService(paramBundle);
    try
    {
      paramBundle = (Intent)this.zzavW.poll(30000L, TimeUnit.MILLISECONDS);
      return paramBundle;
    }
    catch (InterruptedException paramBundle)
    {
      throw new IOException(paramBundle.getMessage());
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/gcm/GoogleCloudMessaging.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */