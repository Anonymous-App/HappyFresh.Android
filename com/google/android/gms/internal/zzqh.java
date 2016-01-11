package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tagmanager.zzbg;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class zzqh
{
  public static final Integer zzaPM = Integer.valueOf(0);
  public static final Integer zzaPN = Integer.valueOf(1);
  private final Context mContext;
  private final ExecutorService zzaNb;
  
  public zzqh(Context paramContext)
  {
    this(paramContext, Executors.newSingleThreadExecutor());
  }
  
  zzqh(Context paramContext, ExecutorService paramExecutorService)
  {
    this.mContext = paramContext;
    this.zzaNb = paramExecutorService;
  }
  
  private String zzfc(String paramString)
  {
    return "resource_" + paramString;
  }
  
  private byte[] zzm(InputStream paramInputStream)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    for (;;)
    {
      try
      {
        zzlg.zza(paramInputStream, localByteArrayOutputStream);
      }
      catch (IOException localIOException)
      {
        zzbg.zzaC("Failed to read the resource from disk");
        try
        {
          paramInputStream.close();
        }
        catch (IOException paramInputStream)
        {
          zzbg.zzaC("Error closing stream for reading resource from disk");
          return null;
        }
      }
      finally
      {
        try
        {
          paramInputStream.close();
          throw ((Throwable)localObject);
        }
        catch (IOException paramInputStream)
        {
          zzbg.zzaC("Error closing stream for reading resource from disk");
        }
      }
      try
      {
        paramInputStream.close();
        return localByteArrayOutputStream.toByteArray();
      }
      catch (IOException paramInputStream)
      {
        zzbg.zzaC("Error closing stream for reading resource from disk");
        return null;
      }
    }
    return null;
  }
  
  public void zza(final String paramString, final Integer paramInteger, final zzqb paramzzqb, final zzqg paramzzqg)
  {
    this.zzaNb.execute(new Runnable()
    {
      public void run()
      {
        zzqh.this.zzb(paramString, paramInteger, paramzzqb, paramzzqg);
      }
    });
  }
  
  void zzb(String paramString, Integer paramInteger, zzqb paramzzqb, zzqg paramzzqg)
  {
    zzbg.zzaB("DiskLoader: Starting to load resource from Disk.");
    try
    {
      Object localObject = paramzzqb.zzt(zzm(new FileInputStream(zzfb(paramString))));
      if (localObject != null)
      {
        zzbg.zzaB("Saved resource loaded: " + zzfc(paramString));
        paramzzqg.zza(Status.zzXP, localObject, zzaPN, zzfa(paramString));
        return;
      }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      zzbg.zzaz("Saved resource not found: " + zzfc(paramString));
      if (paramInteger == null)
      {
        paramzzqg.zza(Status.zzXR, null, null, 0L);
        return;
      }
    }
    catch (zzqf.zzg localzzg)
    {
      for (;;)
      {
        zzbg.zzaz("Saved resource is corrupted: " + zzfc(paramString));
      }
    }
    try
    {
      paramString = this.mContext.getResources().openRawResource(paramInteger.intValue());
      if (paramString != null)
      {
        paramString = paramzzqb.zzt(zzm(paramString));
        if (paramString != null)
        {
          zzbg.zzaB("Default resource loaded: " + this.mContext.getResources().getResourceEntryName(paramInteger.intValue()));
          paramzzqg.zza(Status.zzXP, paramString, zzaPM, 0L);
          return;
        }
      }
    }
    catch (Resources.NotFoundException paramString)
    {
      zzbg.zzaz("Default resource not found. ID: " + paramInteger);
      paramzzqg.zza(Status.zzXR, null, null, 0L);
      return;
    }
    catch (zzqf.zzg paramString)
    {
      for (;;)
      {
        zzbg.zzaz("Default resource resource is corrupted: " + paramInteger);
      }
    }
  }
  
  public void zze(final String paramString, final byte[] paramArrayOfByte)
  {
    this.zzaNb.execute(new Runnable()
    {
      public void run()
      {
        zzqh.this.zzf(paramString, paramArrayOfByte);
      }
    });
  }
  
  /* Error */
  void zzf(String paramString, byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual 120	com/google/android/gms/internal/zzqh:zzfb	(Ljava/lang/String;)Ljava/io/File;
    //   5: astore 4
    //   7: new 197	java/io/FileOutputStream
    //   10: dup
    //   11: aload 4
    //   13: invokespecial 198	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   16: astore_3
    //   17: aload_3
    //   18: aload_2
    //   19: invokevirtual 202	java/io/FileOutputStream:write	([B)V
    //   22: aload_3
    //   23: invokevirtual 203	java/io/FileOutputStream:close	()V
    //   26: new 49	java/lang/StringBuilder
    //   29: dup
    //   30: invokespecial 50	java/lang/StringBuilder:<init>	()V
    //   33: ldc -51
    //   35: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   38: aload_1
    //   39: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   42: ldc -49
    //   44: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   47: invokevirtual 60	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   50: invokestatic 114	com/google/android/gms/tagmanager/zzbg:zzaB	(Ljava/lang/String;)V
    //   53: return
    //   54: astore_1
    //   55: ldc -47
    //   57: invokestatic 155	com/google/android/gms/tagmanager/zzbg:zzaz	(Ljava/lang/String;)V
    //   60: return
    //   61: astore_1
    //   62: ldc -45
    //   64: invokestatic 155	com/google/android/gms/tagmanager/zzbg:zzaz	(Ljava/lang/String;)V
    //   67: return
    //   68: astore_2
    //   69: ldc -43
    //   71: invokestatic 155	com/google/android/gms/tagmanager/zzbg:zzaz	(Ljava/lang/String;)V
    //   74: aload 4
    //   76: invokevirtual 219	java/io/File:delete	()Z
    //   79: pop
    //   80: aload_3
    //   81: invokevirtual 203	java/io/FileOutputStream:close	()V
    //   84: new 49	java/lang/StringBuilder
    //   87: dup
    //   88: invokespecial 50	java/lang/StringBuilder:<init>	()V
    //   91: ldc -51
    //   93: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   96: aload_1
    //   97: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   100: ldc -49
    //   102: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   105: invokevirtual 60	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   108: invokestatic 114	com/google/android/gms/tagmanager/zzbg:zzaB	(Ljava/lang/String;)V
    //   111: return
    //   112: astore_1
    //   113: ldc -45
    //   115: invokestatic 155	com/google/android/gms/tagmanager/zzbg:zzaz	(Ljava/lang/String;)V
    //   118: return
    //   119: astore_2
    //   120: aload_3
    //   121: invokevirtual 203	java/io/FileOutputStream:close	()V
    //   124: new 49	java/lang/StringBuilder
    //   127: dup
    //   128: invokespecial 50	java/lang/StringBuilder:<init>	()V
    //   131: ldc -51
    //   133: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   136: aload_1
    //   137: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   140: ldc -49
    //   142: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   145: invokevirtual 60	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   148: invokestatic 114	com/google/android/gms/tagmanager/zzbg:zzaB	(Ljava/lang/String;)V
    //   151: aload_2
    //   152: athrow
    //   153: astore_1
    //   154: ldc -45
    //   156: invokestatic 155	com/google/android/gms/tagmanager/zzbg:zzaz	(Ljava/lang/String;)V
    //   159: goto -8 -> 151
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	162	0	this	zzqh
    //   0	162	1	paramString	String
    //   0	162	2	paramArrayOfByte	byte[]
    //   16	105	3	localFileOutputStream	java.io.FileOutputStream
    //   5	70	4	localFile	File
    // Exception table:
    //   from	to	target	type
    //   7	17	54	java/io/FileNotFoundException
    //   22	53	61	java/io/IOException
    //   17	22	68	java/io/IOException
    //   80	111	112	java/io/IOException
    //   17	22	119	finally
    //   69	80	119	finally
    //   120	151	153	java/io/IOException
  }
  
  public long zzfa(String paramString)
  {
    paramString = zzfb(paramString);
    if (paramString.exists()) {
      return paramString.lastModified();
    }
    return 0L;
  }
  
  File zzfb(String paramString)
  {
    return new File(this.mContext.getDir("google_tagmanager", 0), zzfc(paramString));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzqh.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */