package com.google.android.gms.analytics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.analytics.internal.zza;
import com.google.android.gms.analytics.internal.zzab;
import com.google.android.gms.analytics.internal.zzad;
import com.google.android.gms.analytics.internal.zzaf;
import com.google.android.gms.analytics.internal.zzal;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zzan;
import com.google.android.gms.analytics.internal.zzb;
import com.google.android.gms.analytics.internal.zzd;
import com.google.android.gms.analytics.internal.zze;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.analytics.internal.zzh;
import com.google.android.gms.analytics.internal.zzk;
import com.google.android.gms.analytics.internal.zzn;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzns;
import com.google.android.gms.internal.zznx;
import com.google.android.gms.internal.zznz;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class Tracker
  extends zzd
{
  private boolean zzIH;
  private final Map<String, String> zzII = new HashMap();
  private final zzad zzIJ;
  private final zza zzIK;
  private ExceptionReporter zzIL;
  private zzal zzIM;
  private final Map<String, String> zzyn = new HashMap();
  
  Tracker(zzf paramzzf, String paramString, zzad paramzzad)
  {
    super(paramzzf);
    if (paramString != null) {
      this.zzyn.put("&tid", paramString);
    }
    this.zzyn.put("useSecure", "1");
    this.zzyn.put("&a", Integer.toString(new Random().nextInt(Integer.MAX_VALUE) + 1));
    if (paramzzad == null) {}
    for (this.zzIJ = new zzad("tracking");; this.zzIJ = paramzzad)
    {
      this.zzIK = new zza(paramzzf);
      return;
    }
  }
  
  private static void zza(Map<String, String> paramMap1, Map<String, String> paramMap2)
  {
    com.google.android.gms.common.internal.zzu.zzu(paramMap2);
    if (paramMap1 == null) {}
    for (;;)
    {
      return;
      paramMap1 = paramMap1.entrySet().iterator();
      while (paramMap1.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramMap1.next();
        String str = zzb(localEntry);
        if (str != null) {
          paramMap2.put(str, localEntry.getValue());
        }
      }
    }
  }
  
  private static boolean zza(Map.Entry<String, String> paramEntry)
  {
    String str = (String)paramEntry.getKey();
    paramEntry = (String)paramEntry.getValue();
    return (str.startsWith("&")) && (str.length() >= 2);
  }
  
  private static String zzb(Map.Entry<String, String> paramEntry)
  {
    if (!zza(paramEntry)) {
      return null;
    }
    return ((String)paramEntry.getKey()).substring(1);
  }
  
  private static void zzb(Map<String, String> paramMap1, Map<String, String> paramMap2)
  {
    com.google.android.gms.common.internal.zzu.zzu(paramMap2);
    if (paramMap1 == null) {}
    for (;;)
    {
      return;
      paramMap1 = paramMap1.entrySet().iterator();
      while (paramMap1.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramMap1.next();
        String str = zzb(localEntry);
        if ((str != null) && (!paramMap2.containsKey(str))) {
          paramMap2.put(str, localEntry.getValue());
        }
      }
    }
  }
  
  private boolean zzho()
  {
    return this.zzIL != null;
  }
  
  static String zzp(Activity paramActivity)
  {
    com.google.android.gms.common.internal.zzu.zzu(paramActivity);
    paramActivity = paramActivity.getIntent();
    if (paramActivity == null) {}
    do
    {
      return null;
      paramActivity = paramActivity.getStringExtra("android.intent.extra.REFERRER_NAME");
    } while (TextUtils.isEmpty(paramActivity));
    return paramActivity;
  }
  
  public void enableAdvertisingIdCollection(boolean paramBoolean)
  {
    this.zzIH = paramBoolean;
  }
  
  public void enableAutoActivityTracking(boolean paramBoolean)
  {
    this.zzIK.enableAutoActivityTracking(paramBoolean);
  }
  
  public void enableExceptionReporting(boolean paramBoolean)
  {
    for (;;)
    {
      try
      {
        if (zzho() == paramBoolean) {
          return;
        }
        if (paramBoolean)
        {
          Context localContext = getContext();
          this.zzIL = new ExceptionReporter(this, Thread.getDefaultUncaughtExceptionHandler(), localContext);
          Thread.setDefaultUncaughtExceptionHandler(this.zzIL);
          zzaT("Uncaught exceptions will be reported to Google Analytics");
          return;
        }
      }
      finally {}
      Thread.setDefaultUncaughtExceptionHandler(this.zzIL.zzhh());
      zzaT("Uncaught exceptions will not be reported to Google Analytics");
    }
  }
  
  public String get(String paramString)
  {
    zzia();
    if (TextUtils.isEmpty(paramString)) {}
    do
    {
      return null;
      if (this.zzyn.containsKey(paramString)) {
        return (String)this.zzyn.get(paramString);
      }
      if (paramString.equals("&ul")) {
        return zzam.zza(Locale.getDefault());
      }
      if (paramString.equals("&cid")) {
        return zzhV().zziP();
      }
      if (paramString.equals("&sr")) {
        return zzhY().zzjF();
      }
      if (paramString.equals("&aid")) {
        return zzhX().zzix().zzsK();
      }
      if (paramString.equals("&an")) {
        return zzhX().zzix().zzjL();
      }
      if (paramString.equals("&av")) {
        return zzhX().zzix().zzjN();
      }
    } while (!paramString.equals("&aiid"));
    return zzhX().zzix().zzwi();
  }
  
  public void send(final Map<String, String> paramMap)
  {
    final long l = zzhP().currentTimeMillis();
    if (zzhg().getAppOptOut())
    {
      zzaU("AppOptOut is set to true. Not sending Google Analytics hit");
      return;
    }
    boolean bool1 = zzhg().isDryRunEnabled();
    final HashMap localHashMap = new HashMap();
    zza(this.zzyn, localHashMap);
    zza(paramMap, localHashMap);
    final boolean bool2 = zzam.zze((String)this.zzyn.get("useSecure"), true);
    zzb(this.zzII, localHashMap);
    this.zzII.clear();
    paramMap = (String)localHashMap.get("t");
    if (TextUtils.isEmpty(paramMap))
    {
      zzhQ().zzg(localHashMap, "Missing hit type parameter");
      return;
    }
    final String str = (String)localHashMap.get("tid");
    if (TextUtils.isEmpty(str))
    {
      zzhQ().zzg(localHashMap, "Missing tracking id parameter");
      return;
    }
    final boolean bool3 = zzhp();
    try
    {
      if (("screenview".equalsIgnoreCase(paramMap)) || ("pageview".equalsIgnoreCase(paramMap)) || ("appview".equalsIgnoreCase(paramMap)) || (TextUtils.isEmpty(paramMap)))
      {
        int j = Integer.parseInt((String)this.zzyn.get("&a")) + 1;
        int i = j;
        if (j >= Integer.MAX_VALUE) {
          i = 1;
        }
        this.zzyn.put("&a", Integer.toString(i));
      }
      zzhS().zze(new Runnable()
      {
        public void run()
        {
          boolean bool = true;
          if (Tracker.zza(Tracker.this).zzhq()) {
            localHashMap.put("sc", "start");
          }
          zzam.zzc(localHashMap, "cid", Tracker.this.zzhg().getClientId());
          Object localObject = (String)localHashMap.get("sf");
          if (localObject != null)
          {
            double d = zzam.zza((String)localObject, 100.0D);
            if (zzam.zza(d, (String)localHashMap.get("cid")))
            {
              Tracker.this.zzb("Sampling enabled. Hit sampled out. sample rate", Double.valueOf(d));
              return;
            }
          }
          localObject = Tracker.zzb(Tracker.this);
          if (bool3)
          {
            zzam.zzb(localHashMap, "ate", ((zza)localObject).zzhy());
            zzam.zzb(localHashMap, "adid", ((zza)localObject).zzhC());
            localObject = Tracker.zzc(Tracker.this).zzix();
            zzam.zzb(localHashMap, "an", ((zznx)localObject).zzjL());
            zzam.zzb(localHashMap, "av", ((zznx)localObject).zzjN());
            zzam.zzb(localHashMap, "aid", ((zznx)localObject).zzsK());
            zzam.zzb(localHashMap, "aiid", ((zznx)localObject).zzwi());
            localHashMap.put("v", "1");
            localHashMap.put("_v", zze.zzJB);
            zzam.zzb(localHashMap, "ul", Tracker.zzd(Tracker.this).zzjE().getLanguage());
            zzam.zzb(localHashMap, "sr", Tracker.zze(Tracker.this).zzjF());
            if ((!paramMap.equals("transaction")) && (!paramMap.equals("item"))) {
              break label383;
            }
          }
          label383:
          for (int i = 1;; i = 0)
          {
            if ((i != 0) || (Tracker.zzf(Tracker.this).zzkb())) {
              break label388;
            }
            Tracker.zzg(Tracker.this).zzg(localHashMap, "Too many hits sent too quickly, rate limiting invoked");
            return;
            localHashMap.remove("ate");
            localHashMap.remove("adid");
            break;
          }
          label388:
          long l2 = zzam.zzbj((String)localHashMap.get("ht"));
          long l1 = l2;
          if (l2 == 0L) {
            l1 = l;
          }
          if (bool2)
          {
            localObject = new zzab(Tracker.this, localHashMap, l1, str);
            Tracker.zzh(Tracker.this).zzc("Dry run enabled. Would have sent hit", localObject);
            return;
          }
          localObject = (String)localHashMap.get("cid");
          HashMap localHashMap = new HashMap();
          zzam.zza(localHashMap, "uid", localHashMap);
          zzam.zza(localHashMap, "an", localHashMap);
          zzam.zza(localHashMap, "aid", localHashMap);
          zzam.zza(localHashMap, "av", localHashMap);
          zzam.zza(localHashMap, "aiid", localHashMap);
          String str = this.zzIT;
          if (!TextUtils.isEmpty((CharSequence)localHashMap.get("adid"))) {}
          for (;;)
          {
            localObject = new zzh(0L, (String)localObject, str, bool, 0L, localHashMap);
            l2 = Tracker.zzi(Tracker.this).zza((zzh)localObject);
            localHashMap.put("_s", String.valueOf(l2));
            localObject = new zzab(Tracker.this, localHashMap, l1, str);
            Tracker.zzj(Tracker.this).zza((zzab)localObject);
            return;
            bool = false;
          }
        }
      });
      return;
    }
    finally {}
  }
  
  public void set(String paramString1, String paramString2)
  {
    com.google.android.gms.common.internal.zzu.zzb(paramString1, "Key should be non-null");
    if (TextUtils.isEmpty(paramString1)) {
      return;
    }
    this.zzyn.put(paramString1, paramString2);
  }
  
  public void setAnonymizeIp(boolean paramBoolean)
  {
    set("&aip", zzam.zzH(paramBoolean));
  }
  
  public void setAppId(String paramString)
  {
    set("&aid", paramString);
  }
  
  public void setAppInstallerId(String paramString)
  {
    set("&aiid", paramString);
  }
  
  public void setAppName(String paramString)
  {
    set("&an", paramString);
  }
  
  public void setAppVersion(String paramString)
  {
    set("&av", paramString);
  }
  
  public void setCampaignParamsOnNextHit(Uri paramUri)
  {
    if ((paramUri == null) || (paramUri.isOpaque())) {}
    do
    {
      do
      {
        return;
        paramUri = paramUri.getQueryParameter("referrer");
      } while (TextUtils.isEmpty(paramUri));
      paramUri = Uri.parse("http://hostname/?" + paramUri);
      String str = paramUri.getQueryParameter("utm_id");
      if (str != null) {
        this.zzII.put("&ci", str);
      }
      str = paramUri.getQueryParameter("anid");
      if (str != null) {
        this.zzII.put("&anid", str);
      }
      str = paramUri.getQueryParameter("utm_campaign");
      if (str != null) {
        this.zzII.put("&cn", str);
      }
      str = paramUri.getQueryParameter("utm_content");
      if (str != null) {
        this.zzII.put("&cc", str);
      }
      str = paramUri.getQueryParameter("utm_medium");
      if (str != null) {
        this.zzII.put("&cm", str);
      }
      str = paramUri.getQueryParameter("utm_source");
      if (str != null) {
        this.zzII.put("&cs", str);
      }
      str = paramUri.getQueryParameter("utm_term");
      if (str != null) {
        this.zzII.put("&ck", str);
      }
      str = paramUri.getQueryParameter("dclid");
      if (str != null) {
        this.zzII.put("&dclid", str);
      }
      str = paramUri.getQueryParameter("gclid");
      if (str != null) {
        this.zzII.put("&gclid", str);
      }
      paramUri = paramUri.getQueryParameter("aclid");
    } while (paramUri == null);
    this.zzII.put("&aclid", paramUri);
  }
  
  public void setClientId(String paramString)
  {
    set("&cid", paramString);
  }
  
  public void setEncoding(String paramString)
  {
    set("&de", paramString);
  }
  
  public void setHostname(String paramString)
  {
    set("&dh", paramString);
  }
  
  public void setLanguage(String paramString)
  {
    set("&ul", paramString);
  }
  
  public void setLocation(String paramString)
  {
    set("&dl", paramString);
  }
  
  public void setPage(String paramString)
  {
    set("&dp", paramString);
  }
  
  public void setReferrer(String paramString)
  {
    set("&dr", paramString);
  }
  
  public void setSampleRate(double paramDouble)
  {
    set("&sf", Double.toString(paramDouble));
  }
  
  public void setScreenColors(String paramString)
  {
    set("&sd", paramString);
  }
  
  public void setScreenName(String paramString)
  {
    set("&cd", paramString);
  }
  
  public void setScreenResolution(int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) && (paramInt2 < 0))
    {
      zzaW("Invalid width or height. The values should be non-negative.");
      return;
    }
    set("&sr", paramInt1 + "x" + paramInt2);
  }
  
  public void setSessionTimeout(long paramLong)
  {
    this.zzIK.setSessionTimeout(1000L * paramLong);
  }
  
  public void setTitle(String paramString)
  {
    set("&dt", paramString);
  }
  
  public void setUseSecure(boolean paramBoolean)
  {
    set("useSecure", zzam.zzH(paramBoolean));
  }
  
  public void setViewportSize(String paramString)
  {
    set("&vp", paramString);
  }
  
  void zza(zzal paramzzal)
  {
    zzaT("Loading Tracker config values");
    this.zzIM = paramzzal;
    if (this.zzIM.zzky())
    {
      paramzzal = this.zzIM.getTrackingId();
      set("&tid", paramzzal);
      zza("trackingId loaded", paramzzal);
    }
    if (this.zzIM.zzkz())
    {
      paramzzal = Double.toString(this.zzIM.zzkA());
      set("&sf", paramzzal);
      zza("Sample frequency loaded", paramzzal);
    }
    if (this.zzIM.zzkB())
    {
      int i = this.zzIM.getSessionTimeout();
      setSessionTimeout(i);
      zza("Session timeout loaded", Integer.valueOf(i));
    }
    boolean bool;
    if (this.zzIM.zzkC())
    {
      bool = this.zzIM.zzkD();
      enableAutoActivityTracking(bool);
      zza("Auto activity tracking loaded", Boolean.valueOf(bool));
    }
    if (this.zzIM.zzkE())
    {
      bool = this.zzIM.zzkF();
      if (bool) {
        set("&aip", "1");
      }
      zza("Anonymize ip loaded", Boolean.valueOf(bool));
    }
    enableExceptionReporting(this.zzIM.zzkG());
  }
  
  protected void zzhn()
  {
    this.zzIK.zza();
    String str = zzhm().zzjL();
    if (str != null) {
      set("&an", str);
    }
    str = zzhm().zzjN();
    if (str != null) {
      set("&av", str);
    }
  }
  
  boolean zzhp()
  {
    return this.zzIH;
  }
  
  private class zza
    extends zzd
    implements GoogleAnalytics.zza
  {
    private boolean zzIV;
    private int zzIW;
    private long zzIX = -1L;
    private boolean zzIY;
    private long zzIZ;
    
    protected zza(zzf paramzzf)
    {
      super();
    }
    
    private void zzhr()
    {
      if ((this.zzIX >= 0L) || (this.zzIV))
      {
        zzhg().zza(Tracker.zza(Tracker.this));
        return;
      }
      zzhg().zzb(Tracker.zza(Tracker.this));
    }
    
    public void enableAutoActivityTracking(boolean paramBoolean)
    {
      this.zzIV = paramBoolean;
      zzhr();
    }
    
    public void setSessionTimeout(long paramLong)
    {
      this.zzIX = paramLong;
      zzhr();
    }
    
    protected void zzhn() {}
    
    public boolean zzhq()
    {
      try
      {
        boolean bool = this.zzIY;
        this.zzIY = false;
        return bool;
      }
      finally
      {
        localObject = finally;
        throw ((Throwable)localObject);
      }
    }
    
    boolean zzhs()
    {
      return zzhP().elapsedRealtime() >= this.zzIZ + Math.max(1000L, this.zzIX);
    }
    
    public void zzn(Activity paramActivity)
    {
      if ((this.zzIW == 0) && (zzhs())) {
        this.zzIY = true;
      }
      this.zzIW += 1;
      HashMap localHashMap;
      Tracker localTracker;
      if (this.zzIV)
      {
        localObject = paramActivity.getIntent();
        if (localObject != null) {
          Tracker.this.setCampaignParamsOnNextHit(((Intent)localObject).getData());
        }
        localHashMap = new HashMap();
        localHashMap.put("&t", "screenview");
        localTracker = Tracker.this;
        if (Tracker.zzk(Tracker.this) == null) {
          break label159;
        }
      }
      label159:
      for (Object localObject = Tracker.zzk(Tracker.this).zzq(paramActivity);; localObject = paramActivity.getClass().getCanonicalName())
      {
        localTracker.set("&cd", (String)localObject);
        if (TextUtils.isEmpty((CharSequence)localHashMap.get("&dr")))
        {
          paramActivity = Tracker.zzp(paramActivity);
          if (!TextUtils.isEmpty(paramActivity)) {
            localHashMap.put("&dr", paramActivity);
          }
        }
        Tracker.this.send(localHashMap);
        return;
      }
    }
    
    public void zzo(Activity paramActivity)
    {
      this.zzIW -= 1;
      this.zzIW = Math.max(0, this.zzIW);
      if (this.zzIW == 0) {
        this.zzIZ = zzhP().elapsedRealtime();
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/Tracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */