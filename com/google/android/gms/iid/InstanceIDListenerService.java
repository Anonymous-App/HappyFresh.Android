package com.google.android.gms.iid;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.gcm.GcmReceiver;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import java.io.IOException;

public class InstanceIDListenerService
  extends Service
{
  static String ACTION = "action";
  private static String zzavK = "google.com/iid";
  private static String zzawW = "CMD";
  MessengerCompat zzawU = new MessengerCompat(new Handler(Looper.getMainLooper())
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      InstanceIDListenerService.zza(InstanceIDListenerService.this, paramAnonymousMessage, MessengerCompat.zzc(paramAnonymousMessage));
    }
  });
  BroadcastReceiver zzawV = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if (Log.isLoggable("InstanceID", 3))
      {
        paramAnonymousIntent.getStringExtra("registration_id");
        Log.d("InstanceID", "Received GSF callback using dynamic receiver: " + paramAnonymousIntent.getExtras());
      }
      InstanceIDListenerService.this.zzn(paramAnonymousIntent);
      InstanceIDListenerService.this.stop();
    }
  };
  int zzawX;
  int zzawY;
  
  static void zza(Context paramContext, zzd paramzzd)
  {
    paramzzd.zzul();
    paramzzd = new Intent("com.google.android.gms.iid.InstanceID");
    paramzzd.putExtra(zzawW, "RST");
    paramzzd.setPackage(paramContext.getPackageName());
    paramContext.startService(paramzzd);
  }
  
  private void zza(Message paramMessage, int paramInt)
  {
    zzc.zzaw(this);
    getPackageManager();
    if ((paramInt != zzc.zzaxf) && (paramInt != zzc.zzaxe))
    {
      Log.w("InstanceID", "Message from unexpected caller " + paramInt + " mine=" + zzc.zzaxe + " appid=" + zzc.zzaxf);
      return;
    }
    zzn((Intent)paramMessage.obj);
  }
  
  static void zzav(Context paramContext)
  {
    Intent localIntent = new Intent("com.google.android.gms.iid.InstanceID");
    localIntent.setPackage(paramContext.getPackageName());
    localIntent.putExtra(zzawW, "SYNC");
    paramContext.startService(localIntent);
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    if ((paramIntent != null) && ("com.google.android.gms.iid.InstanceID".equals(paramIntent.getAction()))) {
      return this.zzawU.getBinder();
    }
    return null;
  }
  
  public void onCreate()
  {
    IntentFilter localIntentFilter = new IntentFilter("com.google.android.c2dm.intent.REGISTRATION");
    localIntentFilter.addCategory(getPackageName());
    registerReceiver(this.zzawV, localIntentFilter, "com.google.android.c2dm.permission.RECEIVE", null);
  }
  
  public void onDestroy()
  {
    unregisterReceiver(this.zzawV);
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    zzgn(paramInt2);
    if (paramIntent == null)
    {
      stop();
      return 2;
    }
    try
    {
      if ("com.google.android.gms.iid.InstanceID".equals(paramIntent.getAction()))
      {
        if (Build.VERSION.SDK_INT <= 18)
        {
          Intent localIntent = (Intent)paramIntent.getParcelableExtra("GSF");
          if (localIntent != null)
          {
            startService(localIntent);
            return 1;
          }
        }
        zzn(paramIntent);
      }
      stop();
      if (paramIntent.getStringExtra("from") != null) {
        GcmReceiver.completeWakefulIntent(paramIntent);
      }
      return 2;
    }
    finally
    {
      stop();
    }
  }
  
  public void onTokenRefresh() {}
  
  void stop()
  {
    try
    {
      this.zzawX -= 1;
      if (this.zzawX == 0) {
        stopSelf(this.zzawY);
      }
      if (Log.isLoggable("InstanceID", 3)) {
        Log.d("InstanceID", "Stop " + this.zzawX + " " + this.zzawY);
      }
      return;
    }
    finally {}
  }
  
  public void zzab(boolean paramBoolean)
  {
    onTokenRefresh();
  }
  
  void zzgn(int paramInt)
  {
    try
    {
      this.zzawX += 1;
      if (paramInt > this.zzawY) {
        this.zzawY = paramInt;
      }
      return;
    }
    finally {}
  }
  
  public void zzn(Intent paramIntent)
  {
    String str2 = paramIntent.getStringExtra("subtype");
    Object localObject;
    String str1;
    if (str2 == null)
    {
      localObject = InstanceID.getInstance(this);
      str1 = paramIntent.getStringExtra(zzawW);
      if ((paramIntent.getStringExtra("error") == null) && (paramIntent.getStringExtra("registration_id") == null)) {
        break label116;
      }
      if (Log.isLoggable("InstanceID", 3)) {
        Log.d("InstanceID", "Register result in service " + str2);
      }
      ((InstanceID)localObject).zzuh().zzr(paramIntent);
    }
    label116:
    label268:
    do
    {
      do
      {
        return;
        localObject = new Bundle();
        ((Bundle)localObject).putString("subtype", str2);
        localObject = InstanceID.zza(this, (Bundle)localObject);
        break;
        if (Log.isLoggable("InstanceID", 3)) {
          Log.d("InstanceID", "Service command " + str2 + " " + str1 + " " + paramIntent.getExtras());
        }
        if (paramIntent.getStringExtra("unregistered") != null)
        {
          zzd localzzd = ((InstanceID)localObject).zzug();
          str1 = str2;
          if (str2 == null) {
            str1 = "";
          }
          localzzd.zzdi(str1);
          ((InstanceID)localObject).zzuh().zzr(paramIntent);
          return;
        }
        if ("RST".equals(str1))
        {
          ((InstanceID)localObject).zzuf();
          zzab(true);
          return;
        }
        if (!"RST_FULL".equals(str1)) {
          break label268;
        }
      } while (((InstanceID)localObject).zzug().isEmpty());
      ((InstanceID)localObject).zzug().zzul();
      zzab(true);
      return;
      if ("SYNC".equals(str1))
      {
        ((InstanceID)localObject).zzug().zzdi(str2);
        zzab(false);
        return;
      }
    } while (!"PING".equals(str1));
    try
    {
      GoogleCloudMessaging.getInstance(this).send(zzavK, zzc.zzuk(), 0L, paramIntent.getExtras());
      return;
    }
    catch (IOException paramIntent)
    {
      Log.w("InstanceID", "Failed to send ping response");
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/iid/InstanceIDListenerService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */