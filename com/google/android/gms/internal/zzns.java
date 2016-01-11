package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Process;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.common.internal.zzu;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzns
{
  private static volatile zzns zzaEh;
  private final Context mContext;
  private volatile zznx zzKm;
  private final List<zznt> zzaEi;
  private final zznn zzaEj;
  private final zza zzaEk;
  private Thread.UncaughtExceptionHandler zzaEl;
  
  zzns(Context paramContext)
  {
    paramContext = paramContext.getApplicationContext();
    zzu.zzu(paramContext);
    this.mContext = paramContext;
    this.zzaEk = new zza();
    this.zzaEi = new CopyOnWriteArrayList();
    this.zzaEj = new zznn();
  }
  
  public static zzns zzaB(Context paramContext)
  {
    zzu.zzu(paramContext);
    if (zzaEh == null) {}
    try
    {
      if (zzaEh == null) {
        zzaEh = new zzns(paramContext);
      }
      return zzaEh;
    }
    finally {}
  }
  
  private void zzb(zzno paramzzno)
  {
    zzu.zzbZ("deliver should be called from worker thread");
    zzu.zzb(paramzzno.zzvU(), "Measurement must be submitted");
    Object localObject = paramzzno.zzvR();
    if (((List)localObject).isEmpty()) {}
    for (;;)
    {
      return;
      HashSet localHashSet = new HashSet();
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        zznu localzznu = (zznu)((Iterator)localObject).next();
        Uri localUri = localzznu.zzhe();
        if (!localHashSet.contains(localUri))
        {
          localHashSet.add(localUri);
          localzznu.zzb(paramzzno);
        }
      }
    }
  }
  
  public static void zzhO()
  {
    if (!(Thread.currentThread() instanceof zzc)) {
      throw new IllegalStateException("Call expected from worker thread");
    }
  }
  
  public Context getContext()
  {
    return this.mContext;
  }
  
  public void zza(Thread.UncaughtExceptionHandler paramUncaughtExceptionHandler)
  {
    this.zzaEl = paramUncaughtExceptionHandler;
  }
  
  public <V> Future<V> zzb(Callable<V> paramCallable)
  {
    zzu.zzu(paramCallable);
    if ((Thread.currentThread() instanceof zzc))
    {
      paramCallable = new FutureTask(paramCallable);
      paramCallable.run();
      return paramCallable;
    }
    return this.zzaEk.submit(paramCallable);
  }
  
  void zze(final zzno paramzzno)
  {
    if (paramzzno.zzvY()) {
      throw new IllegalStateException("Measurement prototype can't be submitted");
    }
    if (paramzzno.zzvU()) {
      throw new IllegalStateException("Measurement can only be submitted once");
    }
    paramzzno = paramzzno.zzvP();
    paramzzno.zzvV();
    this.zzaEk.execute(new Runnable()
    {
      public void run()
      {
        paramzzno.zzvW().zza(paramzzno);
        Iterator localIterator = zzns.zza(zzns.this).iterator();
        while (localIterator.hasNext()) {
          ((zznt)localIterator.next()).zza(paramzzno);
        }
        zzns.zza(zzns.this, paramzzno);
      }
    });
  }
  
  public void zze(Runnable paramRunnable)
  {
    zzu.zzu(paramRunnable);
    this.zzaEk.submit(paramRunnable);
  }
  
  public zznx zzwc()
  {
    if (this.zzKm == null) {}
    Object localObject5;
    Object localObject3;
    try
    {
      zznx localzznx;
      PackageManager localPackageManager;
      String str2;
      if (this.zzKm == null)
      {
        localzznx = new zznx();
        localPackageManager = this.mContext.getPackageManager();
        str2 = this.mContext.getPackageName();
        localzznx.setAppId(str2);
        localzznx.setAppInstallerId(localPackageManager.getInstallerPackageName(str2));
        localObject5 = null;
        localObject3 = str2;
      }
      try
      {
        PackageInfo localPackageInfo = localPackageManager.getPackageInfo(this.mContext.getPackageName(), 0);
        localObject4 = localObject5;
        str1 = str2;
        if (localPackageInfo != null)
        {
          localObject3 = str2;
          localObject4 = localPackageManager.getApplicationLabel(localPackageInfo.applicationInfo);
          str1 = str2;
          localObject3 = str2;
          if (!TextUtils.isEmpty((CharSequence)localObject4))
          {
            localObject3 = str2;
            str1 = ((CharSequence)localObject4).toString();
          }
          localObject3 = str1;
          localObject4 = localPackageInfo.versionName;
        }
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        for (;;)
        {
          String str1;
          Log.e("GAv4", "Error retrieving package info: appName set to " + (String)localObject3);
          Object localObject4 = localObject5;
          Object localObject1 = localObject3;
        }
      }
      localzznx.setAppName(str1);
      localzznx.setAppVersion((String)localObject4);
      this.zzKm = localzznx;
      return this.zzKm;
    }
    finally {}
  }
  
  public zznz zzwd()
  {
    DisplayMetrics localDisplayMetrics = this.mContext.getResources().getDisplayMetrics();
    zznz localzznz = new zznz();
    localzznz.setLanguage(zzam.zza(Locale.getDefault()));
    localzznz.zzhG(localDisplayMetrics.widthPixels);
    localzznz.zzhH(localDisplayMetrics.heightPixels);
    return localzznz;
  }
  
  private class zza
    extends ThreadPoolExecutor
  {
    public zza()
    {
      super(1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
      setThreadFactory(new zzns.zzb(null));
    }
    
    protected <T> RunnableFuture<T> newTaskFor(Runnable paramRunnable, T paramT)
    {
      new FutureTask(paramRunnable, paramT)
      {
        protected void setException(Throwable paramAnonymousThrowable)
        {
          Thread.UncaughtExceptionHandler localUncaughtExceptionHandler = zzns.zzb(zzns.this);
          if (localUncaughtExceptionHandler != null) {
            localUncaughtExceptionHandler.uncaughtException(Thread.currentThread(), paramAnonymousThrowable);
          }
          for (;;)
          {
            super.setException(paramAnonymousThrowable);
            return;
            if (Log.isLoggable("GAv4", 6)) {
              Log.e("GAv4", "MeasurementExecutor: job failed with " + paramAnonymousThrowable);
            }
          }
        }
      };
    }
  }
  
  private static class zzb
    implements ThreadFactory
  {
    private static final AtomicInteger zzaEp = new AtomicInteger();
    
    public Thread newThread(Runnable paramRunnable)
    {
      return new zzns.zzc(paramRunnable, "measurement-" + zzaEp.incrementAndGet());
    }
  }
  
  private static class zzc
    extends Thread
  {
    zzc(Runnable paramRunnable, String paramString)
    {
      super(paramString);
    }
    
    public void run()
    {
      Process.setThreadPriority(10);
      super.run();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzns.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */