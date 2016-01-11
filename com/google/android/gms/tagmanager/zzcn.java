package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzaf.zzf;
import com.google.android.gms.internal.zzpx.zza;
import com.google.android.gms.internal.zzqf;
import com.google.android.gms.internal.zzqf.zzc;
import com.google.android.gms.internal.zzqf.zzg;
import com.google.android.gms.internal.zzrm;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONException;

class zzcn
  implements zzp.zzf
{
  private final Context mContext;
  private final String zzaKy;
  private zzbf<zzpx.zza> zzaMU;
  private final ExecutorService zzaNb;
  
  zzcn(Context paramContext, String paramString)
  {
    this.mContext = paramContext;
    this.zzaKy = paramString;
    this.zzaNb = Executors.newSingleThreadExecutor();
  }
  
  private zzqf.zzc zza(ByteArrayOutputStream paramByteArrayOutputStream)
  {
    try
    {
      paramByteArrayOutputStream = zzaz.zzey(paramByteArrayOutputStream.toString("UTF-8"));
      return paramByteArrayOutputStream;
    }
    catch (UnsupportedEncodingException paramByteArrayOutputStream)
    {
      zzbg.zzay("Failed to convert binary resource to string for JSON parsing; the file format is not UTF-8 format.");
      return null;
    }
    catch (JSONException paramByteArrayOutputStream)
    {
      zzbg.zzaC("Failed to extract the container from the resource file. Resource is a UTF-8 encoded string but doesn't contain a JSON container");
    }
    return null;
  }
  
  private void zzd(zzpx.zza paramzza)
    throws IllegalArgumentException
  {
    if ((paramzza.zziO == null) && (paramzza.zzaPa == null)) {
      throw new IllegalArgumentException("Resource and SupplementedResource are NULL.");
    }
  }
  
  private zzqf.zzc zzr(byte[] paramArrayOfByte)
  {
    try
    {
      paramArrayOfByte = zzqf.zzb(zzaf.zzf.zzc(paramArrayOfByte));
      if (paramArrayOfByte != null) {
        zzbg.zzaB("The container was successfully loaded from the resource (using binary file)");
      }
      return paramArrayOfByte;
    }
    catch (zzrm paramArrayOfByte)
    {
      zzbg.zzaz("The resource file is corrupted. The container cannot be extracted from the binary file");
      return null;
    }
    catch (zzqf.zzg paramArrayOfByte)
    {
      zzbg.zzaC("The resource file is invalid. The container from the binary file is invalid");
    }
    return null;
  }
  
  public void release()
  {
    try
    {
      this.zzaNb.shutdown();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void zza(zzbf<zzpx.zza> paramzzbf)
  {
    this.zzaMU = paramzzbf;
  }
  
  public void zzb(final zzpx.zza paramzza)
  {
    this.zzaNb.execute(new Runnable()
    {
      public void run()
      {
        zzcn.this.zzc(paramzza);
      }
    });
  }
  
  /* Error */
  boolean zzc(zzpx.zza paramzza)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 146	com/google/android/gms/tagmanager/zzcn:zzzo	()Ljava/io/File;
    //   4: astore_3
    //   5: new 148	java/io/FileOutputStream
    //   8: dup
    //   9: aload_3
    //   10: invokespecial 151	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   13: astore_2
    //   14: aload_2
    //   15: aload_1
    //   16: invokestatic 157	com/google/android/gms/internal/zzrn:zzf	(Lcom/google/android/gms/internal/zzrn;)[B
    //   19: invokevirtual 161	java/io/FileOutputStream:write	([B)V
    //   22: aload_2
    //   23: invokevirtual 164	java/io/FileOutputStream:close	()V
    //   26: iconst_1
    //   27: ireturn
    //   28: astore_1
    //   29: ldc -90
    //   31: invokestatic 117	com/google/android/gms/tagmanager/zzbg:zzaz	(Ljava/lang/String;)V
    //   34: iconst_0
    //   35: ireturn
    //   36: astore_1
    //   37: ldc -88
    //   39: invokestatic 70	com/google/android/gms/tagmanager/zzbg:zzaC	(Ljava/lang/String;)V
    //   42: iconst_1
    //   43: ireturn
    //   44: astore_1
    //   45: ldc -86
    //   47: invokestatic 70	com/google/android/gms/tagmanager/zzbg:zzaC	(Ljava/lang/String;)V
    //   50: aload_3
    //   51: invokevirtual 176	java/io/File:delete	()Z
    //   54: pop
    //   55: aload_2
    //   56: invokevirtual 164	java/io/FileOutputStream:close	()V
    //   59: iconst_0
    //   60: ireturn
    //   61: astore_1
    //   62: ldc -88
    //   64: invokestatic 70	com/google/android/gms/tagmanager/zzbg:zzaC	(Ljava/lang/String;)V
    //   67: iconst_0
    //   68: ireturn
    //   69: astore_1
    //   70: aload_2
    //   71: invokevirtual 164	java/io/FileOutputStream:close	()V
    //   74: aload_1
    //   75: athrow
    //   76: astore_2
    //   77: ldc -88
    //   79: invokestatic 70	com/google/android/gms/tagmanager/zzbg:zzaC	(Ljava/lang/String;)V
    //   82: goto -8 -> 74
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	85	0	this	zzcn
    //   0	85	1	paramzza	zzpx.zza
    //   13	58	2	localFileOutputStream	java.io.FileOutputStream
    //   76	1	2	localIOException	java.io.IOException
    //   4	47	3	localFile	File
    // Exception table:
    //   from	to	target	type
    //   5	14	28	java/io/FileNotFoundException
    //   22	26	36	java/io/IOException
    //   14	22	44	java/io/IOException
    //   55	59	61	java/io/IOException
    //   14	22	69	finally
    //   45	55	69	finally
    //   70	74	76	java/io/IOException
  }
  
  /* Error */
  public zzqf.zzc zziR(int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 26	com/google/android/gms/tagmanager/zzcn:mContext	Landroid/content/Context;
    //   4: invokevirtual 186	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   7: iload_1
    //   8: invokevirtual 192	android/content/res/Resources:openRawResource	(I)Ljava/io/InputStream;
    //   11: astore_3
    //   12: new 194	java/lang/StringBuilder
    //   15: dup
    //   16: invokespecial 195	java/lang/StringBuilder:<init>	()V
    //   19: ldc -59
    //   21: invokevirtual 201	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   24: iload_1
    //   25: invokevirtual 204	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   28: ldc -50
    //   30: invokevirtual 201	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   33: aload_0
    //   34: getfield 26	com/google/android/gms/tagmanager/zzcn:mContext	Landroid/content/Context;
    //   37: invokevirtual 186	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   40: iload_1
    //   41: invokevirtual 210	android/content/res/Resources:getResourceName	(I)Ljava/lang/String;
    //   44: invokevirtual 201	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   47: ldc -44
    //   49: invokevirtual 201	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   52: invokevirtual 215	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   55: invokestatic 112	com/google/android/gms/tagmanager/zzbg:zzaB	(Ljava/lang/String;)V
    //   58: new 47	java/io/ByteArrayOutputStream
    //   61: dup
    //   62: invokespecial 216	java/io/ByteArrayOutputStream:<init>	()V
    //   65: astore_2
    //   66: aload_3
    //   67: aload_2
    //   68: invokestatic 219	com/google/android/gms/internal/zzqf:zzc	(Ljava/io/InputStream;Ljava/io/OutputStream;)V
    //   71: aload_0
    //   72: aload_2
    //   73: invokespecial 221	com/google/android/gms/tagmanager/zzcn:zza	(Ljava/io/ByteArrayOutputStream;)Lcom/google/android/gms/internal/zzqf$zzc;
    //   76: astore_3
    //   77: aload_3
    //   78: ifnull +35 -> 113
    //   81: ldc -33
    //   83: invokestatic 112	com/google/android/gms/tagmanager/zzbg:zzaB	(Ljava/lang/String;)V
    //   86: aload_3
    //   87: areturn
    //   88: astore_2
    //   89: new 194	java/lang/StringBuilder
    //   92: dup
    //   93: invokespecial 195	java/lang/StringBuilder:<init>	()V
    //   96: ldc -31
    //   98: invokevirtual 201	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   101: iload_1
    //   102: invokevirtual 204	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   105: invokevirtual 215	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   108: invokestatic 70	com/google/android/gms/tagmanager/zzbg:zzaC	(Ljava/lang/String;)V
    //   111: aconst_null
    //   112: areturn
    //   113: aload_0
    //   114: aload_2
    //   115: invokevirtual 229	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   118: invokespecial 231	com/google/android/gms/tagmanager/zzcn:zzr	([B)Lcom/google/android/gms/internal/zzqf$zzc;
    //   121: astore_2
    //   122: aload_2
    //   123: areturn
    //   124: astore_2
    //   125: new 194	java/lang/StringBuilder
    //   128: dup
    //   129: invokespecial 195	java/lang/StringBuilder:<init>	()V
    //   132: ldc -23
    //   134: invokevirtual 201	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   137: iload_1
    //   138: invokevirtual 204	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   141: ldc -50
    //   143: invokevirtual 201	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   146: aload_0
    //   147: getfield 26	com/google/android/gms/tagmanager/zzcn:mContext	Landroid/content/Context;
    //   150: invokevirtual 186	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   153: iload_1
    //   154: invokevirtual 210	android/content/res/Resources:getResourceName	(I)Ljava/lang/String;
    //   157: invokevirtual 201	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   160: ldc -44
    //   162: invokevirtual 201	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   165: invokevirtual 215	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   168: invokestatic 70	com/google/android/gms/tagmanager/zzbg:zzaC	(Ljava/lang/String;)V
    //   171: aconst_null
    //   172: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	173	0	this	zzcn
    //   0	173	1	paramInt	int
    //   65	8	2	localByteArrayOutputStream	ByteArrayOutputStream
    //   88	27	2	localNotFoundException	android.content.res.Resources.NotFoundException
    //   121	2	2	localzzc	zzqf.zzc
    //   124	1	2	localIOException	java.io.IOException
    //   11	76	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   0	12	88	android/content/res/Resources$NotFoundException
    //   58	77	124	java/io/IOException
    //   81	86	124	java/io/IOException
    //   113	122	124	java/io/IOException
  }
  
  public void zzyw()
  {
    this.zzaNb.execute(new Runnable()
    {
      public void run()
      {
        zzcn.this.zzzn();
      }
    });
  }
  
  /* Error */
  void zzzn()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 128	com/google/android/gms/tagmanager/zzcn:zzaMU	Lcom/google/android/gms/tagmanager/zzbf;
    //   4: ifnonnull +13 -> 17
    //   7: new 240	java/lang/IllegalStateException
    //   10: dup
    //   11: ldc -14
    //   13: invokespecial 243	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   16: athrow
    //   17: aload_0
    //   18: getfield 128	com/google/android/gms/tagmanager/zzcn:zzaMU	Lcom/google/android/gms/tagmanager/zzbf;
    //   21: invokeinterface 248 1 0
    //   26: ldc -6
    //   28: invokestatic 112	com/google/android/gms/tagmanager/zzbg:zzaB	(Ljava/lang/String;)V
    //   31: invokestatic 256	com/google/android/gms/tagmanager/zzcb:zzzf	()Lcom/google/android/gms/tagmanager/zzcb;
    //   34: invokevirtual 260	com/google/android/gms/tagmanager/zzcb:zzzg	()Lcom/google/android/gms/tagmanager/zzcb$zza;
    //   37: getstatic 266	com/google/android/gms/tagmanager/zzcb$zza:zzaMJ	Lcom/google/android/gms/tagmanager/zzcb$zza;
    //   40: if_acmpeq +15 -> 55
    //   43: invokestatic 256	com/google/android/gms/tagmanager/zzcb:zzzf	()Lcom/google/android/gms/tagmanager/zzcb;
    //   46: invokevirtual 260	com/google/android/gms/tagmanager/zzcb:zzzg	()Lcom/google/android/gms/tagmanager/zzcb$zza;
    //   49: getstatic 269	com/google/android/gms/tagmanager/zzcb$zza:zzaMK	Lcom/google/android/gms/tagmanager/zzcb$zza;
    //   52: if_acmpne +32 -> 84
    //   55: aload_0
    //   56: getfield 28	com/google/android/gms/tagmanager/zzcn:zzaKy	Ljava/lang/String;
    //   59: invokestatic 256	com/google/android/gms/tagmanager/zzcb:zzzf	()Lcom/google/android/gms/tagmanager/zzcb;
    //   62: invokevirtual 272	com/google/android/gms/tagmanager/zzcb:getContainerId	()Ljava/lang/String;
    //   65: invokevirtual 278	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   68: ifeq +16 -> 84
    //   71: aload_0
    //   72: getfield 128	com/google/android/gms/tagmanager/zzcn:zzaMU	Lcom/google/android/gms/tagmanager/zzbf;
    //   75: getstatic 284	com/google/android/gms/tagmanager/zzbf$zza:zzaMi	Lcom/google/android/gms/tagmanager/zzbf$zza;
    //   78: invokeinterface 287 2 0
    //   83: return
    //   84: new 289	java/io/FileInputStream
    //   87: dup
    //   88: aload_0
    //   89: invokevirtual 146	com/google/android/gms/tagmanager/zzcn:zzzo	()Ljava/io/File;
    //   92: invokespecial 290	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   95: astore_1
    //   96: new 47	java/io/ByteArrayOutputStream
    //   99: dup
    //   100: invokespecial 216	java/io/ByteArrayOutputStream:<init>	()V
    //   103: astore_2
    //   104: aload_1
    //   105: aload_2
    //   106: invokestatic 219	com/google/android/gms/internal/zzqf:zzc	(Ljava/io/InputStream;Ljava/io/OutputStream;)V
    //   109: aload_2
    //   110: invokevirtual 229	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   113: invokestatic 294	com/google/android/gms/internal/zzpx$zza:zzs	([B)Lcom/google/android/gms/internal/zzpx$zza;
    //   116: astore_2
    //   117: aload_0
    //   118: aload_2
    //   119: invokespecial 296	com/google/android/gms/tagmanager/zzcn:zzd	(Lcom/google/android/gms/internal/zzpx$zza;)V
    //   122: aload_0
    //   123: getfield 128	com/google/android/gms/tagmanager/zzcn:zzaMU	Lcom/google/android/gms/tagmanager/zzbf;
    //   126: aload_2
    //   127: invokeinterface 300 2 0
    //   132: aload_1
    //   133: invokevirtual 301	java/io/FileInputStream:close	()V
    //   136: ldc_w 303
    //   139: invokestatic 112	com/google/android/gms/tagmanager/zzbg:zzaB	(Ljava/lang/String;)V
    //   142: return
    //   143: astore_1
    //   144: ldc_w 305
    //   147: invokestatic 65	com/google/android/gms/tagmanager/zzbg:zzay	(Ljava/lang/String;)V
    //   150: aload_0
    //   151: getfield 128	com/google/android/gms/tagmanager/zzcn:zzaMU	Lcom/google/android/gms/tagmanager/zzbf;
    //   154: getstatic 284	com/google/android/gms/tagmanager/zzbf$zza:zzaMi	Lcom/google/android/gms/tagmanager/zzbf$zza;
    //   157: invokeinterface 287 2 0
    //   162: return
    //   163: astore_1
    //   164: ldc_w 307
    //   167: invokestatic 70	com/google/android/gms/tagmanager/zzbg:zzaC	(Ljava/lang/String;)V
    //   170: goto -34 -> 136
    //   173: astore_2
    //   174: aload_0
    //   175: getfield 128	com/google/android/gms/tagmanager/zzcn:zzaMU	Lcom/google/android/gms/tagmanager/zzbf;
    //   178: getstatic 310	com/google/android/gms/tagmanager/zzbf$zza:zzaMj	Lcom/google/android/gms/tagmanager/zzbf$zza;
    //   181: invokeinterface 287 2 0
    //   186: ldc_w 312
    //   189: invokestatic 70	com/google/android/gms/tagmanager/zzbg:zzaC	(Ljava/lang/String;)V
    //   192: aload_1
    //   193: invokevirtual 301	java/io/FileInputStream:close	()V
    //   196: goto -60 -> 136
    //   199: astore_1
    //   200: ldc_w 307
    //   203: invokestatic 70	com/google/android/gms/tagmanager/zzbg:zzaC	(Ljava/lang/String;)V
    //   206: goto -70 -> 136
    //   209: astore_2
    //   210: aload_0
    //   211: getfield 128	com/google/android/gms/tagmanager/zzcn:zzaMU	Lcom/google/android/gms/tagmanager/zzbf;
    //   214: getstatic 310	com/google/android/gms/tagmanager/zzbf$zza:zzaMj	Lcom/google/android/gms/tagmanager/zzbf$zza;
    //   217: invokeinterface 287 2 0
    //   222: ldc_w 314
    //   225: invokestatic 70	com/google/android/gms/tagmanager/zzbg:zzaC	(Ljava/lang/String;)V
    //   228: aload_1
    //   229: invokevirtual 301	java/io/FileInputStream:close	()V
    //   232: goto -96 -> 136
    //   235: astore_1
    //   236: ldc_w 307
    //   239: invokestatic 70	com/google/android/gms/tagmanager/zzbg:zzaC	(Ljava/lang/String;)V
    //   242: goto -106 -> 136
    //   245: astore_2
    //   246: aload_1
    //   247: invokevirtual 301	java/io/FileInputStream:close	()V
    //   250: aload_2
    //   251: athrow
    //   252: astore_1
    //   253: ldc_w 307
    //   256: invokestatic 70	com/google/android/gms/tagmanager/zzbg:zzaC	(Ljava/lang/String;)V
    //   259: goto -9 -> 250
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	262	0	this	zzcn
    //   95	38	1	localFileInputStream	java.io.FileInputStream
    //   143	1	1	localFileNotFoundException	java.io.FileNotFoundException
    //   163	30	1	localIOException1	java.io.IOException
    //   199	30	1	localIOException2	java.io.IOException
    //   235	12	1	localIOException3	java.io.IOException
    //   252	1	1	localIOException4	java.io.IOException
    //   103	24	2	localObject1	Object
    //   173	1	2	localIOException5	java.io.IOException
    //   209	1	2	localIllegalArgumentException	IllegalArgumentException
    //   245	6	2	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   84	96	143	java/io/FileNotFoundException
    //   132	136	163	java/io/IOException
    //   96	132	173	java/io/IOException
    //   192	196	199	java/io/IOException
    //   96	132	209	java/lang/IllegalArgumentException
    //   228	232	235	java/io/IOException
    //   96	132	245	finally
    //   174	192	245	finally
    //   210	228	245	finally
    //   246	250	252	java/io/IOException
  }
  
  File zzzo()
  {
    String str = "resource_" + this.zzaKy;
    return new File(this.mContext.getDir("google_tagmanager", 0), str);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzcn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */