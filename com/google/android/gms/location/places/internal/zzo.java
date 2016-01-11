package com.google.android.gms.location.places.internal;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.internal.zzu;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class zzo
{
  private static final String TAG = zzo.class.getSimpleName();
  private static final long zzaAQ = TimeUnit.SECONDS.toMillis(1L);
  private static zzo zzaAR;
  private final Context mContext;
  private final Handler mHandler;
  private final Runnable zzaAS = new zza(null);
  private ArrayList<String> zzaAT = null;
  private ArrayList<String> zzaAU = null;
  private final Object zzqt = new Object();
  
  private zzo(Context paramContext)
  {
    this((Context)zzu.zzu(paramContext), new Handler(Looper.getMainLooper()));
  }
  
  zzo(Context paramContext, Handler paramHandler)
  {
    this.mContext = paramContext;
    this.mHandler = paramHandler;
  }
  
  /* Error */
  public static zzo zzax(Context paramContext)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: aload_0
    //   4: invokestatic 58	com/google/android/gms/common/internal/zzu:zzu	(Ljava/lang/Object;)Ljava/lang/Object;
    //   7: pop
    //   8: getstatic 100	android/os/Build$VERSION:SDK_INT	I
    //   11: istore_1
    //   12: iload_1
    //   13: bipush 14
    //   15: if_icmpge +10 -> 25
    //   18: aconst_null
    //   19: astore_0
    //   20: ldc 2
    //   22: monitorexit
    //   23: aload_0
    //   24: areturn
    //   25: getstatic 102	com/google/android/gms/location/places/internal/zzo:zzaAR	Lcom/google/android/gms/location/places/internal/zzo;
    //   28: ifnonnull +17 -> 45
    //   31: new 2	com/google/android/gms/location/places/internal/zzo
    //   34: dup
    //   35: aload_0
    //   36: invokevirtual 106	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   39: invokespecial 108	com/google/android/gms/location/places/internal/zzo:<init>	(Landroid/content/Context;)V
    //   42: putstatic 102	com/google/android/gms/location/places/internal/zzo:zzaAR	Lcom/google/android/gms/location/places/internal/zzo;
    //   45: getstatic 102	com/google/android/gms/location/places/internal/zzo:zzaAR	Lcom/google/android/gms/location/places/internal/zzo;
    //   48: astore_0
    //   49: goto -29 -> 20
    //   52: astore_0
    //   53: ldc 2
    //   55: monitorexit
    //   56: aload_0
    //   57: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	58	0	paramContext	Context
    //   11	5	1	i	int
    // Exception table:
    //   from	to	target	type
    //   3	12	52	finally
    //   25	45	52	finally
    //   45	49	52	finally
  }
  
  public void zzA(String paramString1, String paramString2)
  {
    synchronized (this.zzqt)
    {
      if (this.zzaAT == null)
      {
        this.zzaAT = new ArrayList();
        this.zzaAU = new ArrayList();
        this.mHandler.postDelayed(this.zzaAS, zzaAQ);
      }
      this.zzaAT.add(paramString1);
      this.zzaAU.add(paramString2);
      if (this.zzaAT.size() >= 10000)
      {
        if (Log.isLoggable(TAG, 5)) {
          Log.w(TAG, "Event buffer full, flushing");
        }
        this.zzaAS.run();
        this.mHandler.removeCallbacks(this.zzaAS);
        return;
      }
      return;
    }
  }
  
  private class zza
    implements Runnable
  {
    private zza() {}
    
    public void run()
    {
      synchronized (zzo.zzb(zzo.this))
      {
        Intent localIntent = new Intent("com.google.android.location.places.METHOD_CALL");
        localIntent.setPackage("com.google.android.gms");
        localIntent.putStringArrayListExtra("PLACE_IDS", zzo.zzc(zzo.this));
        localIntent.putStringArrayListExtra("METHOD_NAMES", zzo.zzd(zzo.this));
        zzo.zze(zzo.this).sendBroadcast(localIntent);
        zzo.zza(zzo.this, null);
        zzo.zzb(zzo.this, null);
        return;
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/internal/zzo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */