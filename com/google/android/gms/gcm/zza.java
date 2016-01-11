package com.google.android.gms.gcm;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import java.util.Iterator;
import java.util.Set;

class zza
{
  static zza zzavF;
  private Context mContext;
  
  private zza(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
  }
  
  private void zza(String paramString, Notification paramNotification)
  {
    if (Log.isLoggable("GcmNotification", 3)) {
      Log.d("GcmNotification", "Showing notification");
    }
    NotificationManager localNotificationManager = (NotificationManager)this.mContext.getSystemService("notification");
    String str = paramString;
    if (TextUtils.isEmpty(paramString)) {
      str = "GCM-Notification:" + SystemClock.uptimeMillis();
    }
    localNotificationManager.notify(str, 0, paramNotification);
  }
  
  static zza zzar(Context paramContext)
  {
    try
    {
      if (zzavF == null) {
        zzavF = new zza(paramContext);
      }
      paramContext = zzavF;
      return paramContext;
    }
    finally {}
  }
  
  static String zzb(Bundle paramBundle, String paramString)
  {
    String str2 = paramBundle.getString(paramString);
    String str1 = str2;
    if (str2 == null) {
      str1 = paramBundle.getString(paramString.replace("gcm.n.", "gcm.notification."));
    }
    return str1;
  }
  
  private int zzda(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      throw new zza("Missing icon", null);
    }
    Resources localResources = this.mContext.getResources();
    int i = localResources.getIdentifier(paramString, "drawable", this.mContext.getPackageName());
    if (i != 0) {}
    int j;
    do
    {
      return i;
      j = localResources.getIdentifier(paramString, "mipmap", this.mContext.getPackageName());
      i = j;
    } while (j != 0);
    throw new zza("Icon resource not found: " + paramString, null);
  }
  
  private Uri zzdb(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    if ("default".equals(paramString)) {
      return RingtoneManager.getDefaultUri(2);
    }
    throw new zza("Invalid sound: " + paramString, null);
  }
  
  static boolean zzt(Bundle paramBundle)
  {
    return zzb(paramBundle, "gcm.n.title") != null;
  }
  
  private Notification zzv(Bundle paramBundle)
  {
    Object localObject = zzb(paramBundle, "gcm.n.title");
    if (TextUtils.isEmpty((CharSequence)localObject)) {
      throw new zza("Missing title", null);
    }
    String str = zzb(paramBundle, "gcm.n.body");
    int i = zzda(zzb(paramBundle, "gcm.n.icon"));
    Uri localUri = zzdb(zzb(paramBundle, "gcm.n.sound"));
    PendingIntent localPendingIntent = zzw(paramBundle);
    if (Build.VERSION.SDK_INT >= 11)
    {
      localObject = new Notification.Builder(this.mContext).setAutoCancel(true).setSmallIcon(i).setContentTitle((CharSequence)localObject).setContentText(str);
      if (Build.VERSION.SDK_INT >= 21)
      {
        paramBundle = zzb(paramBundle, "gcm.n.color");
        if (!TextUtils.isEmpty(paramBundle)) {
          ((Notification.Builder)localObject).setColor(Color.parseColor(paramBundle));
        }
      }
      if (localUri != null) {
        ((Notification.Builder)localObject).setSound(localUri);
      }
      if (localPendingIntent != null) {
        ((Notification.Builder)localObject).setContentIntent(localPendingIntent);
      }
      if (Build.VERSION.SDK_INT >= 16) {
        return ((Notification.Builder)localObject).build();
      }
      return ((Notification.Builder)localObject).getNotification();
    }
    Notification localNotification = new Notification();
    localNotification.flags |= 0x10;
    localNotification.icon = i;
    if (localUri != null) {
      localNotification.sound = localUri;
    }
    paramBundle = localPendingIntent;
    if (localPendingIntent == null)
    {
      paramBundle = new Intent();
      paramBundle.setPackage("com.google.example.invalidpackage");
      paramBundle = PendingIntent.getBroadcast(this.mContext, 0, paramBundle, 0);
    }
    localNotification.setLatestEventInfo(this.mContext, (CharSequence)localObject, str, paramBundle);
    return localNotification;
  }
  
  private PendingIntent zzw(Bundle paramBundle)
  {
    Object localObject = zzb(paramBundle, "gcm.n.click_action");
    if (TextUtils.isEmpty((CharSequence)localObject)) {
      return null;
    }
    localObject = new Intent((String)localObject);
    ((Intent)localObject).setPackage(this.mContext.getPackageName());
    ((Intent)localObject).setFlags(268435456);
    ((Intent)localObject).putExtras(paramBundle);
    paramBundle = paramBundle.keySet().iterator();
    while (paramBundle.hasNext())
    {
      String str = (String)paramBundle.next();
      if ((str.startsWith("gcm.n.")) || (str.startsWith("gcm.notification."))) {
        ((Intent)localObject).removeExtra(str);
      }
    }
    return PendingIntent.getActivity(this.mContext, 0, (Intent)localObject, 1073741824);
  }
  
  boolean zzu(Bundle paramBundle)
  {
    try
    {
      Notification localNotification = zzv(paramBundle);
      zza(zzb(paramBundle, "gcm.n.tag"), localNotification);
      return true;
    }
    catch (zza paramBundle)
    {
      Log.w("GcmNotification", "Failed to show notification: " + paramBundle.getMessage());
    }
    return false;
  }
  
  private class zza
    extends IllegalArgumentException
  {
    private zza(String paramString)
    {
      super();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/gcm/zza.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */