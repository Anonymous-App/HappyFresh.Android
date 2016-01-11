package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.tagmanager.zzbg;

public class zzqo
  implements Runnable
{
  private final Context mContext;
  private final zzqm zzaMS;
  private final zzqd zzaPU;
  private final zzqn zzaQb;
  private final zzqi zzaQc;
  
  public zzqo(Context paramContext, zzqd paramzzqd, zzqn paramzzqn)
  {
    this(paramContext, paramzzqd, paramzzqn, new zzqm(), new zzqi());
  }
  
  zzqo(Context paramContext, zzqd paramzzqd, zzqn paramzzqn, zzqm paramzzqm, zzqi paramzzqi)
  {
    zzu.zzu(paramContext);
    zzu.zzu(paramzzqn);
    this.mContext = paramContext;
    this.zzaPU = paramzzqd;
    this.zzaQb = paramzzqn;
    this.zzaMS = paramzzqm;
    this.zzaQc = paramzzqi;
  }
  
  public zzqo(Context paramContext, zzqd paramzzqd, zzqn paramzzqn, String paramString)
  {
    this(paramContext, paramzzqd, paramzzqn, new zzqm(), new zzqi());
    this.zzaQc.zzeU(paramString);
  }
  
  public void run()
  {
    zzeH();
  }
  
  boolean zzAI()
  {
    if (!zzba("android.permission.INTERNET"))
    {
      zzbg.zzaz("Missing android.permission.INTERNET. Please add the following declaration to your AndroidManifest.xml: <uses-permission android:name=\"android.permission.INTERNET\" />");
      return false;
    }
    if (!zzba("android.permission.ACCESS_NETWORK_STATE"))
    {
      zzbg.zzaz("Missing android.permission.ACCESS_NETWORK_STATE. Please add the following declaration to your AndroidManifest.xml: <uses-permission android:name=\"android.permission.ACCESS_NETWORK_STATE\" />");
      return false;
    }
    NetworkInfo localNetworkInfo = ((ConnectivityManager)this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
    if ((localNetworkInfo == null) || (!localNetworkInfo.isConnected()))
    {
      zzbg.zzaC("NetworkLoader: No network connectivity - Offline");
      return false;
    }
    return true;
  }
  
  boolean zzba(String paramString)
  {
    return this.mContext.getPackageManager().checkPermission(paramString, this.mContext.getPackageName()) == 0;
  }
  
  /* Error */
  void zzeH()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 119	com/google/android/gms/internal/zzqo:zzAI	()Z
    //   4: ifne +14 -> 18
    //   7: aload_0
    //   8: getfield 43	com/google/android/gms/internal/zzqo:zzaQb	Lcom/google/android/gms/internal/zzqn;
    //   11: getstatic 125	com/google/android/gms/internal/zzqn$zza:zzaPW	Lcom/google/android/gms/internal/zzqn$zza;
    //   14: invokevirtual 131	com/google/android/gms/internal/zzqn:zza	(Lcom/google/android/gms/internal/zzqn$zza;)V
    //   17: return
    //   18: ldc -123
    //   20: invokestatic 136	com/google/android/gms/tagmanager/zzbg:zzaB	(Ljava/lang/String;)V
    //   23: aload_0
    //   24: getfield 45	com/google/android/gms/internal/zzqo:zzaMS	Lcom/google/android/gms/internal/zzqm;
    //   27: invokevirtual 140	com/google/android/gms/internal/zzqm:zzAG	()Lcom/google/android/gms/internal/zzql;
    //   30: astore_1
    //   31: aload_0
    //   32: getfield 47	com/google/android/gms/internal/zzqo:zzaQc	Lcom/google/android/gms/internal/zzqi;
    //   35: aload_0
    //   36: getfield 41	com/google/android/gms/internal/zzqo:zzaPU	Lcom/google/android/gms/internal/zzqd;
    //   39: invokevirtual 146	com/google/android/gms/internal/zzqd:zzAf	()Ljava/util/List;
    //   42: invokevirtual 150	com/google/android/gms/internal/zzqi:zzt	(Ljava/util/List;)Ljava/lang/String;
    //   45: astore_2
    //   46: aload_1
    //   47: aload_2
    //   48: invokeinterface 156 2 0
    //   53: astore_3
    //   54: new 158	java/io/ByteArrayOutputStream
    //   57: dup
    //   58: invokespecial 159	java/io/ByteArrayOutputStream:<init>	()V
    //   61: astore 4
    //   63: aload_3
    //   64: aload 4
    //   66: invokestatic 164	com/google/android/gms/internal/zzlg:zza	(Ljava/io/InputStream;Ljava/io/OutputStream;)J
    //   69: pop2
    //   70: aload_0
    //   71: getfield 43	com/google/android/gms/internal/zzqo:zzaQb	Lcom/google/android/gms/internal/zzqn;
    //   74: aload 4
    //   76: invokevirtual 168	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   79: invokevirtual 171	com/google/android/gms/internal/zzqn:zzu	([B)V
    //   82: aload_1
    //   83: invokeinterface 174 1 0
    //   88: ldc -80
    //   90: invokestatic 136	com/google/android/gms/tagmanager/zzbg:zzaB	(Ljava/lang/String;)V
    //   93: return
    //   94: astore_3
    //   95: new 178	java/lang/StringBuilder
    //   98: dup
    //   99: invokespecial 179	java/lang/StringBuilder:<init>	()V
    //   102: ldc -75
    //   104: invokevirtual 185	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   107: aload_2
    //   108: invokevirtual 185	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: invokevirtual 188	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   114: invokestatic 71	com/google/android/gms/tagmanager/zzbg:zzaz	(Ljava/lang/String;)V
    //   117: aload_0
    //   118: getfield 43	com/google/android/gms/internal/zzqo:zzaQb	Lcom/google/android/gms/internal/zzqn;
    //   121: getstatic 191	com/google/android/gms/internal/zzqn$zza:zzaPY	Lcom/google/android/gms/internal/zzqn$zza;
    //   124: invokevirtual 131	com/google/android/gms/internal/zzqn:zza	(Lcom/google/android/gms/internal/zzqn$zza;)V
    //   127: aload_1
    //   128: invokeinterface 174 1 0
    //   133: return
    //   134: astore_3
    //   135: new 178	java/lang/StringBuilder
    //   138: dup
    //   139: invokespecial 179	java/lang/StringBuilder:<init>	()V
    //   142: ldc -63
    //   144: invokevirtual 185	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   147: aload_2
    //   148: invokevirtual 185	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   151: ldc -61
    //   153: invokevirtual 185	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   156: aload_3
    //   157: invokevirtual 198	java/io/IOException:getMessage	()Ljava/lang/String;
    //   160: invokevirtual 185	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   163: invokevirtual 188	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   166: aload_3
    //   167: invokestatic 202	com/google/android/gms/tagmanager/zzbg:zzb	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   170: aload_0
    //   171: getfield 43	com/google/android/gms/internal/zzqo:zzaQb	Lcom/google/android/gms/internal/zzqn;
    //   174: getstatic 205	com/google/android/gms/internal/zzqn$zza:zzaPX	Lcom/google/android/gms/internal/zzqn$zza;
    //   177: invokevirtual 131	com/google/android/gms/internal/zzqn:zza	(Lcom/google/android/gms/internal/zzqn$zza;)V
    //   180: aload_1
    //   181: invokeinterface 174 1 0
    //   186: return
    //   187: astore_3
    //   188: new 178	java/lang/StringBuilder
    //   191: dup
    //   192: invokespecial 179	java/lang/StringBuilder:<init>	()V
    //   195: ldc -49
    //   197: invokevirtual 185	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   200: aload_2
    //   201: invokevirtual 185	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   204: ldc -61
    //   206: invokevirtual 185	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   209: aload_3
    //   210: invokevirtual 198	java/io/IOException:getMessage	()Ljava/lang/String;
    //   213: invokevirtual 185	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   216: invokevirtual 188	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   219: aload_3
    //   220: invokestatic 202	com/google/android/gms/tagmanager/zzbg:zzb	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   223: aload_0
    //   224: getfield 43	com/google/android/gms/internal/zzqo:zzaQb	Lcom/google/android/gms/internal/zzqn;
    //   227: getstatic 191	com/google/android/gms/internal/zzqn$zza:zzaPY	Lcom/google/android/gms/internal/zzqn$zza;
    //   230: invokevirtual 131	com/google/android/gms/internal/zzqn:zza	(Lcom/google/android/gms/internal/zzqn$zza;)V
    //   233: aload_1
    //   234: invokeinterface 174 1 0
    //   239: return
    //   240: astore_2
    //   241: aload_1
    //   242: invokeinterface 174 1 0
    //   247: aload_2
    //   248: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	249	0	this	zzqo
    //   30	212	1	localzzql	zzql
    //   45	156	2	str	String
    //   240	8	2	localObject	Object
    //   53	11	3	localInputStream	java.io.InputStream
    //   94	1	3	localFileNotFoundException	java.io.FileNotFoundException
    //   134	33	3	localIOException1	java.io.IOException
    //   187	33	3	localIOException2	java.io.IOException
    //   61	14	4	localByteArrayOutputStream	java.io.ByteArrayOutputStream
    // Exception table:
    //   from	to	target	type
    //   46	54	94	java/io/FileNotFoundException
    //   46	54	134	java/io/IOException
    //   54	82	187	java/io/IOException
    //   31	46	240	finally
    //   46	54	240	finally
    //   54	82	240	finally
    //   95	127	240	finally
    //   135	180	240	finally
    //   188	233	240	finally
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzqo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */