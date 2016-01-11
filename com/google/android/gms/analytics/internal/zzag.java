package com.google.android.gms.analytics.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import com.google.android.gms.common.internal.zzu;

class zzag
  extends BroadcastReceiver
{
  static final String zzMo = zzag.class.getName();
  private final zzf zzJy;
  private boolean zzMp;
  private boolean zzMq;
  
  zzag(zzf paramzzf)
  {
    zzu.zzu(paramzzf);
    this.zzJy = paramzzf;
  }
  
  private Context getContext()
  {
    return this.zzJy.getContext();
  }
  
  private zzaf zzhQ()
  {
    return this.zzJy.zzhQ();
  }
  
  private zzb zzhl()
  {
    return this.zzJy.zzhl();
  }
  
  private void zzke()
  {
    zzhQ();
    zzhl();
  }
  
  public boolean isConnected()
  {
    if (!this.zzMp) {
      this.zzJy.zzhQ().zzaW("Connectivity unknown. Receiver not registered");
    }
    return this.zzMq;
  }
  
  public boolean isRegistered()
  {
    return this.zzMp;
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    zzke();
    paramContext = paramIntent.getAction();
    this.zzJy.zzhQ().zza("NetworkBroadcastReceiver received action", paramContext);
    if ("android.net.conn.CONNECTIVITY_CHANGE".equals(paramContext))
    {
      boolean bool = zzkg();
      if (this.zzMq != bool)
      {
        this.zzMq = bool;
        zzhl().zzG(bool);
      }
    }
    do
    {
      return;
      if (!"com.google.analytics.RADIO_POWERED".equals(paramContext)) {
        break;
      }
    } while (paramIntent.hasExtra(zzMo));
    zzhl().zzhK();
    return;
    this.zzJy.zzhQ().zzd("NetworkBroadcastReceiver received unknown action", paramContext);
  }
  
  public void unregister()
  {
    if (!isRegistered()) {
      return;
    }
    this.zzJy.zzhQ().zzaT("Unregistering connectivity change receiver");
    this.zzMp = false;
    this.zzMq = false;
    Context localContext = getContext();
    try
    {
      localContext.unregisterReceiver(this);
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      zzhQ().zze("Failed to unregister the network broadcast receiver", localIllegalArgumentException);
    }
  }
  
  public void zzkd()
  {
    zzke();
    if (this.zzMp) {
      return;
    }
    Context localContext = getContext();
    localContext.registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    IntentFilter localIntentFilter = new IntentFilter("com.google.analytics.RADIO_POWERED");
    localIntentFilter.addCategory(localContext.getPackageName());
    localContext.registerReceiver(this, localIntentFilter);
    this.zzMq = zzkg();
    this.zzJy.zzhQ().zza("Registering connectivity change receiver. Network connected", Boolean.valueOf(this.zzMq));
    this.zzMp = true;
  }
  
  public void zzkf()
  {
    if (Build.VERSION.SDK_INT <= 10) {
      return;
    }
    Context localContext = getContext();
    Intent localIntent = new Intent("com.google.analytics.RADIO_POWERED");
    localIntent.addCategory(localContext.getPackageName());
    localIntent.putExtra(zzMo, true);
    localContext.sendOrderedBroadcast(localIntent, null);
  }
  
  protected boolean zzkg()
  {
    Object localObject = (ConnectivityManager)getContext().getSystemService("connectivity");
    try
    {
      localObject = ((ConnectivityManager)localObject).getActiveNetworkInfo();
      if (localObject != null)
      {
        boolean bool = ((NetworkInfo)localObject).isConnected();
        if (bool) {
          return true;
        }
      }
      return false;
    }
    catch (SecurityException localSecurityException) {}
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/zzag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */