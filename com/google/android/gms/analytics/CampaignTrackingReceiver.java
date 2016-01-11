package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.text.TextUtils;
import com.google.android.gms.analytics.internal.zzaf;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.analytics.internal.zzr;
import com.google.android.gms.common.internal.zzu;

public class CampaignTrackingReceiver
  extends BroadcastReceiver
{
  static PowerManager.WakeLock zzIc;
  static Boolean zzId;
  static Object zzoW = new Object();
  
  public static boolean zzT(Context paramContext)
  {
    zzu.zzu(paramContext);
    if (zzId != null) {
      return zzId.booleanValue();
    }
    boolean bool = zzam.zza(paramContext, CampaignTrackingReceiver.class, true);
    zzId = Boolean.valueOf(bool);
    return bool;
  }
  
  public void onReceive(Context paramContext, Intent arg2)
  {
    Object localObject = zzf.zzV(paramContext);
    localzzaf = ((zzf)localObject).zzhQ();
    String str = ???.getStringExtra("referrer");
    ??? = ???.getAction();
    localzzaf.zza("CampaignTrackingReceiver received", ???);
    if ((!"com.android.vending.INSTALL_REFERRER".equals(???)) || (TextUtils.isEmpty(str)))
    {
      localzzaf.zzaW("CampaignTrackingReceiver received unexpected intent without referrer extra");
      return;
    }
    boolean bool = CampaignTrackingService.zzU(paramContext);
    if (!bool) {
      localzzaf.zzaW("CampaignTrackingService not registered or disabled. Installation tracking not possible. See http://goo.gl/8Rd3yj for instructions.");
    }
    zzaL(str);
    if (((zzf)localObject).zzhR().zziW())
    {
      localzzaf.zzaX("Received unexpected installation campaign on package side");
      return;
    }
    ??? = zzhf();
    zzu.zzu(???);
    localObject = new Intent(paramContext, ???);
    ((Intent)localObject).putExtra("referrer", str);
    synchronized (zzoW)
    {
      paramContext.startService((Intent)localObject);
      if (!bool) {
        return;
      }
    }
    try
    {
      paramContext = (PowerManager)paramContext.getSystemService("power");
      if (zzIc == null)
      {
        zzIc = paramContext.newWakeLock(1, "Analytics campaign WakeLock");
        zzIc.setReferenceCounted(false);
      }
      zzIc.acquire(1000L);
    }
    catch (SecurityException paramContext)
    {
      for (;;)
      {
        localzzaf.zzaW("CampaignTrackingService service at risk of not starting. For more reliable installation campaign reports, add the WAKE_LOCK permission to your manifest. See http://goo.gl/8Rd3yj for instructions.");
      }
    }
  }
  
  protected void zzaL(String paramString) {}
  
  protected Class<? extends CampaignTrackingService> zzhf()
  {
    return CampaignTrackingService.class;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/CampaignTrackingReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */