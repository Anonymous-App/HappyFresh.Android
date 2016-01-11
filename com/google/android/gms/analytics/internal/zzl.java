package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.analytics.AnalyticsReceiver;
import com.google.android.gms.analytics.AnalyticsService;
import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.google.android.gms.analytics.CampaignTrackingService;
import com.google.android.gms.analytics.zza;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzio;
import com.google.android.gms.internal.zzip;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzno;
import com.google.android.gms.internal.zzns;
import com.google.android.gms.internal.zznx;
import com.google.android.gms.internal.zzny;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class zzl
  extends zzd
{
  private boolean mStarted;
  private final zzj zzKn;
  private final zzah zzKo;
  private final zzag zzKp;
  private final zzi zzKq;
  private long zzKr;
  private final zzt zzKs;
  private final zzt zzKt;
  private final zzaj zzKu;
  private long zzKv;
  private boolean zzKw;
  
  protected zzl(zzf paramzzf, zzg paramzzg)
  {
    super(paramzzf);
    zzu.zzu(paramzzg);
    this.zzKr = Long.MIN_VALUE;
    this.zzKp = paramzzg.zzk(paramzzf);
    this.zzKn = paramzzg.zzm(paramzzf);
    this.zzKo = paramzzg.zzn(paramzzf);
    this.zzKq = paramzzg.zzo(paramzzf);
    this.zzKu = new zzaj(zzhP());
    this.zzKs = new zzt(paramzzf)
    {
      public void run()
      {
        zzl.zza(zzl.this);
      }
    };
    this.zzKt = new zzt(paramzzf)
    {
      public void run()
      {
        zzl.zzb(zzl.this);
      }
    };
  }
  
  private void zza(zzh paramzzh, zzny paramzzny)
  {
    zzu.zzu(paramzzh);
    zzu.zzu(paramzzny);
    Object localObject1 = new zza(zzhM());
    ((zza)localObject1).zzaI(paramzzh.zzij());
    ((zza)localObject1).enableAdvertisingIdCollection(paramzzh.zzik());
    localObject1 = ((zza)localObject1).zzhc();
    zzip localzzip = (zzip)((zzno)localObject1).zze(zzip.class);
    localzzip.zzaN("data");
    localzzip.zzF(true);
    ((zzno)localObject1).zzb(paramzzny);
    zzio localzzio = (zzio)((zzno)localObject1).zze(zzio.class);
    zznx localzznx = (zznx)((zzno)localObject1).zze(zznx.class);
    Iterator localIterator = paramzzh.zzn().entrySet().iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = (Map.Entry)localIterator.next();
      String str = (String)((Map.Entry)localObject2).getKey();
      localObject2 = (String)((Map.Entry)localObject2).getValue();
      if ("an".equals(str)) {
        localzznx.setAppName((String)localObject2);
      } else if ("av".equals(str)) {
        localzznx.setAppVersion((String)localObject2);
      } else if ("aid".equals(str)) {
        localzznx.setAppId((String)localObject2);
      } else if ("aiid".equals(str)) {
        localzznx.setAppInstallerId((String)localObject2);
      } else if ("uid".equals(str)) {
        localzzip.setUserId((String)localObject2);
      } else {
        localzzio.set(str, (String)localObject2);
      }
    }
    zzb("Sending installation campaign to", paramzzh.zzij(), paramzzny);
    ((zzno)localObject1).zzL(zzhU().zzkk());
    ((zzno)localObject1).zzvT();
  }
  
  private boolean zzba(String paramString)
  {
    return getContext().checkCallingOrSelfPermission(paramString) == 0;
  }
  
  private void zziA()
  {
    zzb(new zzw()
    {
      public void zzc(Throwable paramAnonymousThrowable)
      {
        zzl.this.zziG();
      }
    });
  }
  
  private void zziB()
  {
    try
    {
      this.zzKn.zzis();
      zziG();
      this.zzKt.zzt(zzhR().zzjy());
      return;
    }
    catch (SQLiteException localSQLiteException)
    {
      for (;;)
      {
        zzd("Failed to delete stale hits", localSQLiteException);
      }
    }
  }
  
  private boolean zziH()
  {
    if (this.zzKw) {}
    while (((zzhR().zziW()) && (!zzhR().zziX())) || (zziN() <= 0L)) {
      return false;
    }
    return true;
  }
  
  private void zziI()
  {
    zzv localzzv = zzhT();
    if (!localzzv.zzjG()) {}
    long l;
    do
    {
      do
      {
        return;
      } while (localzzv.zzbp());
      l = zzit();
    } while ((l == 0L) || (Math.abs(zzhP().currentTimeMillis() - l) > zzhR().zzjg()));
    zza("Dispatch alarm scheduled (ms)", Long.valueOf(zzhR().zzjf()));
    localzzv.zzjH();
  }
  
  private void zziJ()
  {
    zziI();
    long l2 = zziN();
    long l1 = zzhU().zzkm();
    if (l1 != 0L)
    {
      l1 = l2 - Math.abs(zzhP().currentTimeMillis() - l1);
      if (l1 <= 0L) {}
    }
    for (;;)
    {
      zza("Dispatch scheduled (ms)", Long.valueOf(l1));
      if (!this.zzKs.zzbp()) {
        break;
      }
      l1 = Math.max(1L, l1 + this.zzKs.zzjD());
      this.zzKs.zzu(l1);
      return;
      l1 = Math.min(zzhR().zzjd(), l2);
      continue;
      l1 = Math.min(zzhR().zzjd(), l2);
    }
    this.zzKs.zzt(l1);
  }
  
  private void zziK()
  {
    zziL();
    zziM();
  }
  
  private void zziL()
  {
    if (this.zzKs.zzbp()) {
      zzaT("All hits dispatched or no network/service. Going to power save mode");
    }
    this.zzKs.cancel();
  }
  
  private void zziM()
  {
    zzv localzzv = zzhT();
    if (localzzv.zzbp()) {
      localzzv.cancel();
    }
  }
  
  private void zziy()
  {
    Context localContext = zzhM().getContext();
    if (!AnalyticsReceiver.zzT(localContext)) {
      zzaW("AnalyticsReceiver is not registered or is disabled. Register the receiver for reliable dispatching on non-Google Play devices. See http://goo.gl/8Rd3yj for instructions.");
    }
    do
    {
      while (!CampaignTrackingReceiver.zzT(localContext))
      {
        zzaW("CampaignTrackingReceiver is not registered, not exported or is disabled. Installation campaign tracking is not possible. See http://goo.gl/8Rd3yj for instructions.");
        return;
        if (!AnalyticsService.zzU(localContext)) {
          zzaX("AnalyticsService is not registered or is disabled. Analytics service at risk of not starting. See http://goo.gl/8Rd3yj for instructions.");
        }
      }
    } while (CampaignTrackingService.zzU(localContext));
    zzaW("CampaignTrackingService is not registered or is disabled. Installation campaign tracking is not possible. See http://goo.gl/8Rd3yj for instructions.");
  }
  
  protected void onServiceConnected()
  {
    zzhO();
    if (!zzhR().zziW()) {
      zziD();
    }
  }
  
  void start()
  {
    zzia();
    if (!this.mStarted) {}
    for (boolean bool = true;; bool = false)
    {
      zzu.zza(bool, "Analytics backend already started");
      this.mStarted = true;
      if (!zzhR().zziW()) {
        zziy();
      }
      zzhS().zze(new Runnable()
      {
        public void run()
        {
          zzl.this.zziz();
        }
      });
      return;
    }
  }
  
  public void zzG(boolean paramBoolean)
  {
    zziG();
  }
  
  public long zza(zzh paramzzh, boolean paramBoolean)
  {
    zzu.zzu(paramzzh);
    zzia();
    zzhO();
    long l;
    for (;;)
    {
      try
      {
        this.zzKn.beginTransaction();
        this.zzKn.zza(paramzzh.zzii(), paramzzh.getClientId());
        l = this.zzKn.zza(paramzzh.zzii(), paramzzh.getClientId(), paramzzh.zzij());
        if (!paramBoolean)
        {
          paramzzh.zzn(l);
          this.zzKn.zzb(paramzzh);
          this.zzKn.setTransactionSuccessful();
        }
      }
      catch (SQLiteException paramzzh)
      {
        paramzzh = paramzzh;
        zze("Failed to update Analytics property", paramzzh);
        try
        {
          this.zzKn.endTransaction();
          return -1L;
        }
        catch (SQLiteException paramzzh)
        {
          zze("Failed to end transaction", paramzzh);
          return -1L;
        }
      }
      finally {}
      try
      {
        this.zzKn.endTransaction();
        return l;
      }
      catch (SQLiteException paramzzh)
      {
        zze("Failed to end transaction", paramzzh);
        return l;
      }
      paramzzh.zzn(1L + l);
    }
    try
    {
      this.zzKn.endTransaction();
      throw paramzzh;
    }
    catch (SQLiteException localSQLiteException)
    {
      for (;;)
      {
        zze("Failed to end transaction", localSQLiteException);
      }
    }
  }
  
  public void zza(zzab paramzzab)
  {
    zzu.zzu(paramzzab);
    zzns.zzhO();
    zzia();
    if (this.zzKw) {
      zzaU("Hit delivery not possible. Missing network permissions. See http://goo.gl/8Rd3yj for instructions");
    }
    for (;;)
    {
      paramzzab = zzf(paramzzab);
      zziC();
      if (!this.zzKq.zzb(paramzzab)) {
        break;
      }
      zzaU("Hit sent to the device AnalyticsService for delivery");
      return;
      zza("Delivering hit", paramzzab);
    }
    if (zzhR().zziW())
    {
      zzhQ().zza(paramzzab, "Service unavailable on package side");
      return;
    }
    try
    {
      this.zzKn.zzc(paramzzab);
      zziG();
      return;
    }
    catch (SQLiteException localSQLiteException)
    {
      zze("Delivery failed to save hit to a database", localSQLiteException);
      zzhQ().zza(paramzzab, "deliver: failed to insert hit to database");
    }
  }
  
  public void zza(final zzw paramzzw, final long paramLong)
  {
    zzns.zzhO();
    zzia();
    long l1 = -1L;
    long l2 = zzhU().zzkm();
    if (l2 != 0L) {
      l1 = Math.abs(zzhP().currentTimeMillis() - l2);
    }
    zzb("Dispatching local hits. Elapsed time since last dispatch (ms)", Long.valueOf(l1));
    if (!zzhR().zziW()) {
      zziC();
    }
    try
    {
      if (zziE())
      {
        zzhS().zze(new Runnable()
        {
          public void run()
          {
            zzl.this.zza(paramzzw, paramLong);
          }
        });
        return;
      }
      zzhU().zzkn();
      zziG();
      if (paramzzw != null) {
        paramzzw.zzc(null);
      }
      if (this.zzKv != paramLong)
      {
        this.zzKp.zzkf();
        return;
      }
    }
    catch (Throwable localThrowable)
    {
      zze("Local dispatch failed", localThrowable);
      zzhU().zzkn();
      zziG();
      if (paramzzw != null) {
        paramzzw.zzc(localThrowable);
      }
    }
  }
  
  public void zzb(zzw paramzzw)
  {
    zza(paramzzw, this.zzKv);
  }
  
  public void zzbb(String paramString)
  {
    zzu.zzcj(paramString);
    zzhO();
    zzhN();
    zzny localzzny = zzam.zza(zzhQ(), paramString);
    if (localzzny == null) {
      zzd("Parsing failed. Ignoring invalid campaign data", paramString);
    }
    for (;;)
    {
      return;
      String str = zzhU().zzko();
      if (paramString.equals(str))
      {
        zzaW("Ignoring duplicate install campaign");
        return;
      }
      if (!TextUtils.isEmpty(str))
      {
        zzd("Ignoring multiple install campaigns. original, new", str, paramString);
        return;
      }
      zzhU().zzbf(paramString);
      if (zzhU().zzkl().zzv(zzhR().zzjB()))
      {
        zzd("Campaign received too late, ignoring", localzzny);
        return;
      }
      zzb("Received installation campaign", localzzny);
      paramString = this.zzKn.zzr(0L).iterator();
      while (paramString.hasNext()) {
        zza((zzh)paramString.next(), localzzny);
      }
    }
  }
  
  protected void zzc(zzh paramzzh)
  {
    zzhO();
    zzb("Sending first hit to property", paramzzh.zzij());
    if (zzhU().zzkl().zzv(zzhR().zzjB())) {}
    do
    {
      return;
      localObject = zzhU().zzko();
    } while (TextUtils.isEmpty((CharSequence)localObject));
    Object localObject = zzam.zza(zzhQ(), (String)localObject);
    zzb("Found relevant installation campaign", localObject);
    zza(paramzzh, (zzny)localObject);
  }
  
  zzab zzf(zzab paramzzab)
  {
    if (!TextUtils.isEmpty(paramzzab.zzka())) {}
    do
    {
      return paramzzab;
      localObject2 = zzhU().zzkp().zzks();
    } while (localObject2 == null);
    Object localObject1 = (Long)((Pair)localObject2).second;
    Object localObject2 = (String)((Pair)localObject2).first;
    localObject1 = localObject1 + ":" + (String)localObject2;
    localObject2 = new HashMap(paramzzab.zzn());
    ((Map)localObject2).put("_m", localObject1);
    return zzab.zza(this, paramzzab, (Map)localObject2);
  }
  
  public void zzhG()
  {
    zzns.zzhO();
    zzia();
    if (!zzhR().zziW()) {
      zzaT("Delete all hits from local store");
    }
    try
    {
      this.zzKn.zziq();
      this.zzKn.zzir();
      zziG();
      zziC();
      if (this.zzKq.zzim()) {
        zzaT("Device service unavailable. Can't clear hits stored on the device service.");
      }
      return;
    }
    catch (SQLiteException localSQLiteException)
    {
      for (;;)
      {
        zzd("Failed to delete hits from store", localSQLiteException);
      }
    }
  }
  
  public void zzhJ()
  {
    zzns.zzhO();
    zzia();
    zzaT("Service disconnected");
  }
  
  void zzhL()
  {
    zzhO();
    this.zzKv = zzhP().currentTimeMillis();
  }
  
  protected void zzhn()
  {
    this.zzKn.zza();
    this.zzKo.zza();
    this.zzKq.zza();
  }
  
  protected void zziC()
  {
    if (this.zzKw) {}
    do
    {
      long l;
      do
      {
        do
        {
          return;
        } while ((!zzhR().zziY()) || (this.zzKq.isConnected()));
        l = zzhR().zzjt();
      } while (!this.zzKu.zzv(l));
      this.zzKu.start();
      zzaT("Connecting to service");
    } while (!this.zzKq.connect());
    zzaT("Connected to service");
    this.zzKu.clear();
    onServiceConnected();
  }
  
  public void zziD()
  {
    zzns.zzhO();
    zzia();
    zzhN();
    if (!zzhR().zziY()) {
      zzaW("Service client disabled. Can't dispatch local hits to device AnalyticsService");
    }
    if (!this.zzKq.isConnected()) {
      zzaT("Service not connected");
    }
    while (this.zzKn.isEmpty()) {
      return;
    }
    zzaT("Dispatching local hits to device AnalyticsService");
    for (;;)
    {
      try
      {
        List localList = this.zzKn.zzp(zzhR().zzjh());
        if (!localList.isEmpty()) {
          break label126;
        }
        zziG();
        return;
      }
      catch (SQLiteException localSQLiteException1)
      {
        zze("Failed to read hits from store", localSQLiteException1);
        zziK();
        return;
      }
      label107:
      Object localObject;
      localSQLiteException1.remove(localObject);
      try
      {
        this.zzKn.zzq(((zzab)localObject).zzjV());
        label126:
        if (!localSQLiteException1.isEmpty())
        {
          localObject = (zzab)localSQLiteException1.get(0);
          if (this.zzKq.zzb((zzab)localObject)) {
            break label107;
          }
          zziG();
          return;
        }
      }
      catch (SQLiteException localSQLiteException2)
      {
        zze("Failed to remove hit that was send for delivery", localSQLiteException2);
        zziK();
      }
    }
  }
  
  protected boolean zziE()
  {
    int j = 1;
    zzns.zzhO();
    zzia();
    zzaT("Dispatching a batch of local hits");
    int i;
    if ((!this.zzKq.isConnected()) && (!zzhR().zziW()))
    {
      i = 1;
      if (this.zzKo.zzkg()) {
        break label70;
      }
    }
    for (;;)
    {
      if ((i == 0) || (j == 0)) {
        break label75;
      }
      zzaT("No network or service available. Will retry later");
      return false;
      i = 0;
      break;
      label70:
      j = 0;
    }
    label75:
    long l3 = Math.max(zzhR().zzjh(), zzhR().zzji());
    ArrayList localArrayList = new ArrayList();
    l1 = 0L;
    for (;;)
    {
      try
      {
        this.zzKn.beginTransaction();
        localArrayList.clear();
        try
        {
          localList = this.zzKn.zzp(l3);
          if (localList.isEmpty())
          {
            zzaT("Store is empty, nothing to dispatch");
            zziK();
            try
            {
              this.zzKn.setTransactionSuccessful();
              this.zzKn.endTransaction();
              return false;
            }
            catch (SQLiteException localSQLiteException1)
            {
              zze("Failed to commit local dispatch transaction", localSQLiteException1);
              zziK();
              return false;
            }
          }
          zza("Hits loaded from store. count", Integer.valueOf(localList.size()));
          localObject2 = localList.iterator();
          if (((Iterator)localObject2).hasNext())
          {
            if (((zzab)((Iterator)localObject2).next()).zzjV() != l1) {
              continue;
            }
            zzd("Database contains successfully uploaded hit", Long.valueOf(l1), Integer.valueOf(localList.size()));
            zziK();
            try
            {
              this.zzKn.setTransactionSuccessful();
              this.zzKn.endTransaction();
              return false;
            }
            catch (SQLiteException localSQLiteException2)
            {
              zze("Failed to commit local dispatch transaction", localSQLiteException2);
              zziK();
              return false;
            }
          }
          l2 = l1;
        }
        catch (SQLiteException localSQLiteException3)
        {
          zzd("Failed to read hits from persisted store", localSQLiteException3);
          zziK();
          try
          {
            this.zzKn.setTransactionSuccessful();
            this.zzKn.endTransaction();
            return false;
          }
          catch (SQLiteException localSQLiteException4)
          {
            zze("Failed to commit local dispatch transaction", localSQLiteException4);
            zziK();
            return false;
          }
          l2 = l1;
          if (!this.zzKq.isConnected()) {
            continue;
          }
        }
        if (zzhR().zziW()) {
          continue;
        }
        zzaT("Service connected, sending hits to the service");
        l2 = l1;
        if (localList.isEmpty()) {
          continue;
        }
        localObject2 = (zzab)localList.get(0);
        if (this.zzKq.zzb((zzab)localObject2)) {
          continue;
        }
      }
      finally
      {
        long l2;
        try
        {
          List localList;
          Object localObject2;
          Iterator localIterator;
          this.zzKn.setTransactionSuccessful();
          this.zzKn.endTransaction();
          throw ((Throwable)localObject1);
        }
        catch (SQLiteException localSQLiteException11)
        {
          zze("Failed to commit local dispatch transaction", localSQLiteException11);
          zziK();
          return false;
        }
        l1 = l2;
        continue;
      }
      l2 = l1;
      if (this.zzKo.zzkg())
      {
        localObject2 = this.zzKo.zzf(localList);
        localIterator = ((List)localObject2).iterator();
        if (localIterator.hasNext())
        {
          l1 = Math.max(l1, ((Long)localIterator.next()).longValue());
          continue;
          l1 = Math.max(l1, ((zzab)localObject2).zzjV());
          localList.remove(localObject2);
          zzb("Hit sent do device AnalyticsService for delivery", localObject2);
          try
          {
            this.zzKn.zzq(((zzab)localObject2).zzjV());
            localSQLiteException4.add(Long.valueOf(((zzab)localObject2).zzjV()));
          }
          catch (SQLiteException localSQLiteException5)
          {
            zze("Failed to remove hit that was send for delivery", localSQLiteException5);
            zziK();
            try
            {
              this.zzKn.setTransactionSuccessful();
              this.zzKn.endTransaction();
              return false;
            }
            catch (SQLiteException localSQLiteException6)
            {
              zze("Failed to commit local dispatch transaction", localSQLiteException6);
              zziK();
              return false;
            }
          }
        }
        localList.removeAll((Collection)localObject2);
      }
      try
      {
        this.zzKn.zzd((List)localObject2);
        localSQLiteException6.addAll((Collection)localObject2);
        l2 = l1;
        boolean bool = localSQLiteException6.isEmpty();
        if (bool) {
          try
          {
            this.zzKn.setTransactionSuccessful();
            this.zzKn.endTransaction();
            return false;
          }
          catch (SQLiteException localSQLiteException7)
          {
            zze("Failed to commit local dispatch transaction", localSQLiteException7);
            zziK();
            return false;
          }
        }
      }
      catch (SQLiteException localSQLiteException8)
      {
        zze("Failed to remove successfully uploaded hits", localSQLiteException8);
        zziK();
        try
        {
          this.zzKn.setTransactionSuccessful();
          this.zzKn.endTransaction();
          return false;
        }
        catch (SQLiteException localSQLiteException9)
        {
          zze("Failed to commit local dispatch transaction", localSQLiteException9);
          zziK();
          return false;
        }
        try
        {
          this.zzKn.setTransactionSuccessful();
          this.zzKn.endTransaction();
          l1 = l2;
        }
        catch (SQLiteException localSQLiteException10)
        {
          zze("Failed to commit local dispatch transaction", localSQLiteException10);
          zziK();
          return false;
        }
      }
    }
  }
  
  public void zziF()
  {
    zzns.zzhO();
    zzia();
    zzaU("Sync dispatching local hits");
    long l = this.zzKv;
    if (!zzhR().zziW()) {
      zziC();
    }
    try
    {
      while (zziE()) {}
      zzhU().zzkn();
      zziG();
      if (this.zzKv != l) {
        this.zzKp.zzkf();
      }
      return;
    }
    catch (Throwable localThrowable)
    {
      zze("Sync local dispatch failed", localThrowable);
      zziG();
    }
  }
  
  public void zziG()
  {
    zzhM().zzhO();
    zzia();
    if (!zziH())
    {
      this.zzKp.unregister();
      zziK();
      return;
    }
    if (this.zzKn.isEmpty())
    {
      this.zzKp.unregister();
      zziK();
      return;
    }
    if (!((Boolean)zzy.zzLI.get()).booleanValue()) {
      this.zzKp.zzkd();
    }
    for (boolean bool = this.zzKp.isConnected(); bool; bool = true)
    {
      zziJ();
      return;
    }
    zziK();
    zziI();
  }
  
  public long zziN()
  {
    long l;
    if (this.zzKr != Long.MIN_VALUE) {
      l = this.zzKr;
    }
    do
    {
      return l;
      l = zzhR().zzje();
    } while (!zzhm().zzjQ());
    return zzhm().zzkH() * 1000L;
  }
  
  public void zziO()
  {
    zzia();
    zzhO();
    this.zzKw = true;
    this.zzKq.disconnect();
    zziG();
  }
  
  public long zzit()
  {
    zzns.zzhO();
    zzia();
    try
    {
      long l = this.zzKn.zzit();
      return l;
    }
    catch (SQLiteException localSQLiteException)
    {
      zze("Failed to get min/max hit times from local store", localSQLiteException);
    }
    return 0L;
  }
  
  protected void zziz()
  {
    zzia();
    zzhU().zzkk();
    if (!zzba("android.permission.ACCESS_NETWORK_STATE"))
    {
      zzaX("Missing required android.permission.ACCESS_NETWORK_STATE. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
      zziO();
    }
    if (!zzba("android.permission.INTERNET"))
    {
      zzaX("Missing required android.permission.INTERNET. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
      zziO();
    }
    if (AnalyticsService.zzU(getContext())) {
      zzaT("AnalyticsService registered in the app manifest and enabled");
    }
    for (;;)
    {
      if ((!this.zzKw) && (!zzhR().zziW()) && (!this.zzKn.isEmpty())) {
        zziC();
      }
      zziG();
      return;
      if (zzhR().zziW()) {
        zzaX("Device AnalyticsService not registered! Hits will not be delivered reliably.");
      } else {
        zzaW("AnalyticsService not registered in the app manifest. Hits might not be delivered reliably. See http://goo.gl/8Rd3yj for instructions.");
      }
    }
  }
  
  public void zzs(long paramLong)
  {
    zzns.zzhO();
    zzia();
    long l = paramLong;
    if (paramLong < 0L) {
      l = 0L;
    }
    this.zzKr = l;
    zziG();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/zzl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */