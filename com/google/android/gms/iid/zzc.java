package com.google.android.gms.iid;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import java.io.IOException;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class zzc
{
  static String zzaxd = null;
  static int zzaxe = 0;
  static int zzaxf = 0;
  static int zzaxg = 0;
  PendingIntent zzavT;
  Messenger zzavX;
  Map<String, Object> zzaxh = new HashMap();
  Messenger zzaxi;
  MessengerCompat zzaxj;
  long zzaxk;
  long zzaxl;
  int zzaxm;
  int zzaxn;
  long zzaxo;
  Context zzpH;
  
  public zzc(Context paramContext)
  {
    this.zzpH = paramContext;
  }
  
  /* Error */
  static String zza(KeyPair paramKeyPair, String... paramVarArgs)
  {
    // Byte code:
    //   0: ldc 60
    //   2: aload_1
    //   3: invokestatic 66	android/text/TextUtils:join	(Ljava/lang/CharSequence;[Ljava/lang/Object;)Ljava/lang/String;
    //   6: ldc 68
    //   8: invokevirtual 74	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   11: astore_1
    //   12: aload_0
    //   13: invokevirtual 80	java/security/KeyPair:getPrivate	()Ljava/security/PrivateKey;
    //   16: astore_2
    //   17: aload_2
    //   18: instanceof 82
    //   21: ifeq +43 -> 64
    //   24: ldc 84
    //   26: astore_0
    //   27: aload_0
    //   28: invokestatic 90	java/security/Signature:getInstance	(Ljava/lang/String;)Ljava/security/Signature;
    //   31: astore_0
    //   32: aload_0
    //   33: aload_2
    //   34: invokevirtual 94	java/security/Signature:initSign	(Ljava/security/PrivateKey;)V
    //   37: aload_0
    //   38: aload_1
    //   39: invokevirtual 98	java/security/Signature:update	([B)V
    //   42: aload_0
    //   43: invokevirtual 102	java/security/Signature:sign	()[B
    //   46: invokestatic 108	com/google/android/gms/iid/InstanceID:zzm	([B)Ljava/lang/String;
    //   49: astore_0
    //   50: aload_0
    //   51: areturn
    //   52: astore_0
    //   53: ldc 110
    //   55: ldc 112
    //   57: aload_0
    //   58: invokestatic 118	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   61: pop
    //   62: aconst_null
    //   63: areturn
    //   64: ldc 120
    //   66: astore_0
    //   67: goto -40 -> 27
    //   70: astore_0
    //   71: ldc 110
    //   73: ldc 122
    //   75: aload_0
    //   76: invokestatic 118	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   79: pop
    //   80: aconst_null
    //   81: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	82	0	paramKeyPair	KeyPair
    //   0	82	1	paramVarArgs	String[]
    //   16	18	2	localPrivateKey	java.security.PrivateKey
    // Exception table:
    //   from	to	target	type
    //   0	12	52	java/io/UnsupportedEncodingException
    //   12	24	70	java/security/GeneralSecurityException
    //   27	50	70	java/security/GeneralSecurityException
  }
  
  public static String zzaw(Context paramContext)
  {
    if (zzaxd != null) {
      return zzaxd;
    }
    zzaxe = Process.myUid();
    paramContext = paramContext.getPackageManager();
    Object localObject1 = paramContext.queryIntentServices(new Intent("com.google.android.c2dm.intent.REGISTER"), 0).iterator();
    for (;;)
    {
      Object localObject2;
      if (((Iterator)localObject1).hasNext())
      {
        localObject2 = (ResolveInfo)((Iterator)localObject1).next();
        if (paramContext.checkPermission("com.google.android.c2dm.permission.RECEIVE", ((ResolveInfo)localObject2).serviceInfo.packageName) != 0) {}
      }
      try
      {
        ApplicationInfo localApplicationInfo = paramContext.getApplicationInfo(((ResolveInfo)localObject2).serviceInfo.packageName, 0);
        Log.w("InstanceID/Rpc", "Found " + localApplicationInfo.uid);
        zzaxf = localApplicationInfo.uid;
        zzaxd = ((ResolveInfo)localObject2).serviceInfo.packageName;
        localObject2 = zzaxd;
        return (String)localObject2;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException2) {}
      Log.w("InstanceID/Rpc", "Possible malicious package " + ((ResolveInfo)localObject2).serviceInfo.packageName + " declares " + "com.google.android.c2dm.intent.REGISTER" + " without permission");
      continue;
      Log.w("InstanceID/Rpc", "Failed to resolve REGISTER intent, falling back");
      try
      {
        localObject1 = paramContext.getApplicationInfo("com.google.android.gms", 0);
        zzaxd = ((ApplicationInfo)localObject1).packageName;
        zzaxf = ((ApplicationInfo)localObject1).uid;
        localObject1 = zzaxd;
        return (String)localObject1;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException1)
      {
        try
        {
          paramContext = paramContext.getApplicationInfo("com.google.android.gsf", 0);
          zzaxd = paramContext.packageName;
          zzaxf = paramContext.uid;
          paramContext = zzaxd;
          return paramContext;
        }
        catch (PackageManager.NameNotFoundException paramContext)
        {
          Log.w("InstanceID/Rpc", "Both Google Play Services and legacy GSF package are missing");
          return null;
        }
      }
    }
  }
  
  private Intent zzb(Bundle arg1, KeyPair paramKeyPair)
    throws IOException
  {
    ConditionVariable localConditionVariable = new ConditionVariable();
    String str = zzuk();
    synchronized (getClass())
    {
      this.zzaxh.put(str, localConditionVariable);
      zza(???, paramKeyPair, str);
      localConditionVariable.block(30000L);
    }
    synchronized (getClass())
    {
      paramKeyPair = this.zzaxh.remove(str);
      if ((paramKeyPair instanceof Intent))
      {
        paramKeyPair = (Intent)paramKeyPair;
        return paramKeyPair;
        ??? = finally;
        throw ???;
      }
      if ((paramKeyPair instanceof String)) {
        throw new IOException((String)paramKeyPair);
      }
    }
    Log.w("InstanceID/Rpc", "No response " + paramKeyPair);
    throw new IOException("TIMEOUT");
  }
  
  private void zzdd(String paramString)
  {
    if (!"com.google.android.gsf".equals(zzaxd)) {}
    do
    {
      return;
      this.zzaxm += 1;
    } while (this.zzaxm < 3);
    if (this.zzaxm == 3) {
      this.zzaxn = (new Random().nextInt(1000) + 1000);
    }
    this.zzaxn *= 2;
    this.zzaxo = (SystemClock.elapsedRealtime() + this.zzaxn);
    Log.w("InstanceID/Rpc", "Backoff due to " + paramString + " for " + this.zzaxn);
  }
  
  private void zze(Object paramObject1, Object paramObject2)
  {
    if ((paramObject1 instanceof ConditionVariable)) {
      ((ConditionVariable)paramObject1).open();
    }
    Message localMessage;
    if ((paramObject1 instanceof Messenger))
    {
      paramObject1 = (Messenger)paramObject1;
      localMessage = Message.obtain();
      localMessage.obj = paramObject2;
    }
    try
    {
      ((Messenger)paramObject1).send(localMessage);
      return;
    }
    catch (RemoteException paramObject1)
    {
      Log.w("InstanceID/Rpc", "Failed to send response " + paramObject1);
    }
  }
  
  private void zzi(String paramString, Object paramObject)
  {
    synchronized (getClass())
    {
      Object localObject = this.zzaxh.get(paramString);
      this.zzaxh.put(paramString, paramObject);
      zze(localObject, paramObject);
      return;
    }
  }
  
  public static String zzuk()
  {
    try
    {
      int i = zzaxg;
      zzaxg = i + 1;
      String str = Integer.toString(i);
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  private void zzx(Object paramObject)
  {
    synchronized (getClass())
    {
      Iterator localIterator = this.zzaxh.keySet().iterator();
      if (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        Object localObject = this.zzaxh.get(str);
        this.zzaxh.put(str, paramObject);
        zze(localObject, paramObject);
      }
    }
  }
  
  Intent zza(Bundle paramBundle, KeyPair paramKeyPair)
    throws IOException
  {
    Intent localIntent2 = zzb(paramBundle, paramKeyPair);
    Intent localIntent1 = localIntent2;
    if (localIntent2 != null)
    {
      localIntent1 = localIntent2;
      if (localIntent2.hasExtra("google.messenger")) {
        localIntent1 = zzb(paramBundle, paramKeyPair);
      }
    }
    return localIntent1;
  }
  
  void zza(Bundle paramBundle, KeyPair paramKeyPair, String paramString)
    throws IOException
  {
    long l = SystemClock.elapsedRealtime();
    if ((this.zzaxo != 0L) && (l <= this.zzaxo))
    {
      Log.w("InstanceID/Rpc", "Backoff mode, next request attempt: " + (this.zzaxo - l) + " interval: " + this.zzaxn);
      throw new IOException("RETRY_LATER");
    }
    zzuj();
    if (zzaxd == null) {
      throw new IOException("MISSING_INSTANCEID_SERVICE");
    }
    this.zzaxk = SystemClock.elapsedRealtime();
    Intent localIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
    localIntent.setPackage(zzaxd);
    paramBundle.putString("gmsv", Integer.toString(GoogleCloudMessaging.zzat(this.zzpH)));
    paramBundle.putString("osv", Integer.toString(Build.VERSION.SDK_INT));
    paramBundle.putString("app_ver", Integer.toString(InstanceID.zzau(this.zzpH)));
    paramBundle.putString("cliv", "1");
    paramBundle.putString("appid", InstanceID.zza(paramKeyPair));
    String str = InstanceID.zzm(paramKeyPair.getPublic().getEncoded());
    paramBundle.putString("pub2", str);
    paramBundle.putString("sig", zza(paramKeyPair, new String[] { this.zzpH.getPackageName(), str }));
    localIntent.putExtras(paramBundle);
    zzo(localIntent);
    zzb(localIntent, paramString);
  }
  
  protected void zzb(Intent paramIntent, String paramString)
  {
    this.zzaxk = SystemClock.elapsedRealtime();
    paramIntent.putExtra("kid", "|ID|" + paramString + "|");
    paramIntent.putExtra("X-kid", "|ID|" + paramString + "|");
    boolean bool = "com.google.android.gsf".equals(zzaxd);
    paramString = paramIntent.getStringExtra("useGsf");
    if (paramString != null) {
      bool = "1".equals(paramString);
    }
    if (Log.isLoggable("InstanceID/Rpc", 3)) {
      Log.d("InstanceID/Rpc", "Sending " + paramIntent.getExtras());
    }
    if (this.zzaxi != null)
    {
      paramIntent.putExtra("google.messenger", this.zzavX);
      paramString = Message.obtain();
      paramString.obj = paramIntent;
      try
      {
        this.zzaxi.send(paramString);
        return;
      }
      catch (RemoteException paramString)
      {
        if (Log.isLoggable("InstanceID/Rpc", 3)) {
          Log.d("InstanceID/Rpc", "Messenger failed, fallback to startService");
        }
      }
    }
    if (bool)
    {
      paramString = new Intent("com.google.android.gms.iid.InstanceID");
      paramString.setPackage(this.zzpH.getPackageName());
      paramString.putExtra("GSF", paramIntent);
      this.zzpH.startService(paramString);
      return;
    }
    paramIntent.putExtra("google.messenger", this.zzavX);
    paramIntent.putExtra("messenger2", "1");
    if (this.zzaxj != null)
    {
      paramString = Message.obtain();
      paramString.obj = paramIntent;
      try
      {
        this.zzaxj.send(paramString);
        return;
      }
      catch (RemoteException paramString)
      {
        if (Log.isLoggable("InstanceID/Rpc", 3)) {
          Log.d("InstanceID/Rpc", "Messenger failed, fallback to startService");
        }
      }
    }
    this.zzpH.startService(paramIntent);
  }
  
  public void zze(Message paramMessage)
  {
    if (paramMessage == null) {
      return;
    }
    if ((paramMessage.obj instanceof Intent))
    {
      Object localObject = (Intent)paramMessage.obj;
      ((Intent)localObject).setExtrasClassLoader(MessengerCompat.class.getClassLoader());
      if (((Intent)localObject).hasExtra("google.messenger"))
      {
        localObject = ((Intent)localObject).getParcelableExtra("google.messenger");
        if ((localObject instanceof MessengerCompat)) {
          this.zzaxj = ((MessengerCompat)localObject);
        }
        if ((localObject instanceof Messenger)) {
          this.zzaxi = ((Messenger)localObject);
        }
      }
      zzr((Intent)paramMessage.obj);
      return;
    }
    Log.w("InstanceID/Rpc", "Dropping invalid message");
  }
  
  void zzo(Intent paramIntent)
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
  
  String zzp(Intent paramIntent)
    throws IOException
  {
    if (paramIntent == null) {
      throw new IOException("SERVICE_NOT_AVAILABLE");
    }
    String str2 = paramIntent.getStringExtra("registration_id");
    String str1 = str2;
    if (str2 == null) {
      str1 = paramIntent.getStringExtra("unregistered");
    }
    paramIntent.getLongExtra("Retry-After", 0L);
    if ((str1 == null) || (str1 == null))
    {
      str1 = paramIntent.getStringExtra("error");
      if (str1 != null) {
        throw new IOException(str1);
      }
      Log.w("InstanceID/Rpc", "Unexpected response from GCM " + paramIntent.getExtras(), new Throwable());
      throw new IOException("SERVICE_NOT_AVAILABLE");
    }
    return str1;
  }
  
  void zzq(Intent paramIntent)
  {
    String str = paramIntent.getStringExtra("error");
    if (str == null) {
      Log.w("InstanceID/Rpc", "Unexpected response, no error or registration id " + paramIntent.getExtras());
    }
    Object localObject1;
    label309:
    label320:
    do
    {
      return;
      if (Log.isLoggable("InstanceID/Rpc", 3)) {
        Log.d("InstanceID/Rpc", "Received InstanceID error " + str);
      }
      Object localObject2 = null;
      Object localObject3 = null;
      localObject1 = str;
      if (str.startsWith("|"))
      {
        localObject1 = str.split("\\|");
        if (!"ID".equals(localObject1[1])) {
          Log.w("InstanceID/Rpc", "Unexpected structured response " + str);
        }
        if (localObject1.length > 2)
        {
          localObject3 = localObject1[2];
          str = localObject1[3];
          localObject2 = localObject3;
          localObject1 = str;
          if (str.startsWith(":"))
          {
            localObject1 = str.substring(1);
            localObject2 = localObject3;
          }
          paramIntent.putExtra("error", (String)localObject1);
        }
      }
      else
      {
        if (localObject2 != null) {
          break label309;
        }
        zzx(localObject1);
      }
      for (;;)
      {
        long l = paramIntent.getLongExtra("Retry-After", 0L);
        if (l <= 0L) {
          break label320;
        }
        this.zzaxl = SystemClock.elapsedRealtime();
        this.zzaxn = ((int)l * 1000);
        this.zzaxo = (SystemClock.elapsedRealtime() + this.zzaxn);
        Log.w("InstanceID/Rpc", "Explicit request from server to backoff: " + this.zzaxn);
        return;
        localObject1 = "UNKNOWN";
        localObject2 = localObject3;
        break;
        zzi((String)localObject2, localObject1);
      }
    } while ((!"SERVICE_NOT_AVAILABLE".equals(localObject1)) && (!"AUTHENTICATION_FAILED".equals(localObject1)));
    zzdd((String)localObject1);
  }
  
  void zzr(Intent paramIntent)
  {
    if (paramIntent == null) {
      if (Log.isLoggable("InstanceID/Rpc", 3)) {
        Log.d("InstanceID/Rpc", "Unexpected response: null");
      }
    }
    do
    {
      return;
      localObject1 = paramIntent.getAction();
      if (("com.google.android.c2dm.intent.REGISTRATION".equals(localObject1)) || ("com.google.android.gms.iid.InstanceID".equals(localObject1))) {
        break;
      }
    } while (!Log.isLoggable("InstanceID/Rpc", 3));
    Log.d("InstanceID/Rpc", "Unexpected response " + paramIntent.getAction());
    return;
    Object localObject1 = paramIntent.getStringExtra("registration_id");
    if (localObject1 == null) {
      localObject1 = paramIntent.getStringExtra("unregistered");
    }
    for (;;)
    {
      if (localObject1 == null)
      {
        zzq(paramIntent);
        return;
      }
      this.zzaxk = SystemClock.elapsedRealtime();
      this.zzaxo = 0L;
      this.zzaxm = 0;
      this.zzaxn = 0;
      if (Log.isLoggable("InstanceID/Rpc", 3)) {
        Log.d("InstanceID/Rpc", "AppIDResponse: " + (String)localObject1 + " " + paramIntent.getExtras());
      }
      String str = null;
      Object localObject2;
      if (((String)localObject1).startsWith("|"))
      {
        localObject2 = ((String)localObject1).split("\\|");
        if (!"ID".equals(localObject2[1])) {
          Log.w("InstanceID/Rpc", "Unexpected structured response " + (String)localObject1);
        }
        str = localObject2[2];
        if (localObject2.length > 4)
        {
          if (!"SYNC".equals(localObject2[3])) {
            break label329;
          }
          InstanceIDListenerService.zzav(this.zzpH);
        }
      }
      label329:
      while (!"RST".equals(localObject2[3]))
      {
        localObject2 = localObject2[(localObject2.length - 1)];
        localObject1 = localObject2;
        if (((String)localObject2).startsWith(":")) {
          localObject1 = ((String)localObject2).substring(1);
        }
        paramIntent.putExtra("registration_id", (String)localObject1);
        if (str != null) {
          break;
        }
        zzx(paramIntent);
        return;
      }
      InstanceIDListenerService.zza(this.zzpH, InstanceID.getInstance(this.zzpH).zzug());
      paramIntent.removeExtra("registration_id");
      zzi(str, paramIntent);
      return;
      zzi(str, paramIntent);
      return;
    }
  }
  
  void zzuj()
  {
    if (this.zzavX != null) {
      return;
    }
    zzaw(this.zzpH);
    this.zzavX = new Messenger(new Handler(Looper.getMainLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        zzc.this.zze(paramAnonymousMessage);
      }
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/iid/zzc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */