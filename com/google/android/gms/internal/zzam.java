package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class zzam
  extends zzal
{
  private static AdvertisingIdClient zznn = null;
  private static CountDownLatch zzno = new CountDownLatch(1);
  private static boolean zznp;
  private boolean zznq;
  
  protected zzam(Context paramContext, zzap paramzzap, zzaq paramzzaq, boolean paramBoolean)
  {
    super(paramContext, paramzzap, paramzzaq);
    this.zznq = paramBoolean;
  }
  
  public static zzam zza(String paramString, Context paramContext, boolean paramBoolean)
  {
    zzah localzzah = new zzah();
    zza(paramString, paramContext, localzzah);
    if (paramBoolean) {}
    try
    {
      if (zznn == null) {
        new Thread(new zzb(paramContext)).start();
      }
      return new zzam(paramContext, localzzah, new zzas(239), paramBoolean);
    }
    finally {}
  }
  
  zza zzY()
    throws IOException
  {
    try
    {
      if (!zzno.await(2L, TimeUnit.SECONDS))
      {
        zza localzza1 = new zza(null, false);
        return localzza1;
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      zza localzza2 = new zza(null, false);
      return localzza2;
    }
    finally {}
    if (zznn == null)
    {
      localObject2 = new zza(null, false);
      return (zza)localObject2;
    }
    Object localObject2 = zznn.getInfo();
    return new zza(zzk(((AdvertisingIdClient.Info)localObject2).getId()), ((AdvertisingIdClient.Info)localObject2).isLimitAdTrackingEnabled());
  }
  
  protected void zzc(Context paramContext)
  {
    super.zzc(paramContext);
    try
    {
      if ((zznp) || (!this.zznq))
      {
        zza(24, zze(paramContext));
        return;
      }
      paramContext = zzY();
      str = paramContext.getId();
      if (str == null) {
        return;
      }
      if (!paramContext.isLimitAdTrackingEnabled()) {
        break label81;
      }
      l = 1L;
    }
    catch (IOException paramContext)
    {
      for (;;)
      {
        String str;
        return;
        long l = 0L;
      }
    }
    catch (zzal.zza paramContext) {}
    zza(28, l);
    zza(26, 5L);
    zza(24, str);
    return;
    label81:
  }
  
  class zza
  {
    private String zznr;
    private boolean zzns;
    
    public zza(String paramString, boolean paramBoolean)
    {
      this.zznr = paramString;
      this.zzns = paramBoolean;
    }
    
    public String getId()
    {
      return this.zznr;
    }
    
    public boolean isLimitAdTrackingEnabled()
    {
      return this.zzns;
    }
  }
  
  private static final class zzb
    implements Runnable
  {
    private Context zznu;
    
    public zzb(Context paramContext)
    {
      this.zznu = paramContext.getApplicationContext();
      if (this.zznu == null) {
        this.zznu = paramContext;
      }
    }
    
    /* Error */
    public void run()
    {
      // Byte code:
      //   0: new 34	com/google/android/gms/ads/identifier/AdvertisingIdClient
      //   3: dup
      //   4: aload_0
      //   5: getfield 24	com/google/android/gms/internal/zzam$zzb:zznu	Landroid/content/Context;
      //   8: invokespecial 36	com/google/android/gms/ads/identifier/AdvertisingIdClient:<init>	(Landroid/content/Context;)V
      //   11: astore_1
      //   12: aload_1
      //   13: invokevirtual 39	com/google/android/gms/ads/identifier/AdvertisingIdClient:start	()V
      //   16: ldc 8
      //   18: monitorenter
      //   19: invokestatic 43	com/google/android/gms/internal/zzam:zzZ	()Lcom/google/android/gms/ads/identifier/AdvertisingIdClient;
      //   22: ifnonnull +18 -> 40
      //   25: aload_1
      //   26: invokestatic 47	com/google/android/gms/internal/zzam:zza	(Lcom/google/android/gms/ads/identifier/AdvertisingIdClient;)Lcom/google/android/gms/ads/identifier/AdvertisingIdClient;
      //   29: pop
      //   30: ldc 8
      //   32: monitorexit
      //   33: invokestatic 51	com/google/android/gms/internal/zzam:zzaa	()Ljava/util/concurrent/CountDownLatch;
      //   36: invokevirtual 56	java/util/concurrent/CountDownLatch:countDown	()V
      //   39: return
      //   40: aload_1
      //   41: invokevirtual 59	com/google/android/gms/ads/identifier/AdvertisingIdClient:finish	()V
      //   44: goto -14 -> 30
      //   47: astore_1
      //   48: ldc 8
      //   50: monitorexit
      //   51: aload_1
      //   52: athrow
      //   53: astore_1
      //   54: iconst_1
      //   55: invokestatic 62	com/google/android/gms/internal/zzam:zza	(Z)Z
      //   58: pop
      //   59: goto -26 -> 33
      //   62: astore_1
      //   63: goto -30 -> 33
      //   66: astore_1
      //   67: goto -34 -> 33
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	70	0	this	zzb
      //   11	30	1	localAdvertisingIdClient	AdvertisingIdClient
      //   47	5	1	localObject	Object
      //   53	1	1	localGooglePlayServicesNotAvailableException	com.google.android.gms.common.GooglePlayServicesNotAvailableException
      //   62	1	1	localGooglePlayServicesRepairableException	com.google.android.gms.common.GooglePlayServicesRepairableException
      //   66	1	1	localIOException	IOException
      // Exception table:
      //   from	to	target	type
      //   19	30	47	finally
      //   30	33	47	finally
      //   40	44	47	finally
      //   48	51	47	finally
      //   0	19	53	com/google/android/gms/common/GooglePlayServicesNotAvailableException
      //   51	53	53	com/google/android/gms/common/GooglePlayServicesNotAvailableException
      //   0	19	62	com/google/android/gms/common/GooglePlayServicesRepairableException
      //   51	53	62	com/google/android/gms/common/GooglePlayServicesRepairableException
      //   0	19	66	java/io/IOException
      //   51	53	66	java/io/IOException
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */