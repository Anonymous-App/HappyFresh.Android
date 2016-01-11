package com.appsee;

import android.annotation.TargetApi;
import android.app.Application;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;

class hd
{
  public static final String C = "png";
  public static final String I = Appsee.G;
  public static final String K = "aac";
  public static final String a = "log";
  public static final String b = "h264";
  public static final int d = 1048576;
  public static final String h = "mp4";
  public static final String i = "md";
  public static final String k = "ico";
  public static final int m = 1024;
  
  public static File i(String paramString)
  {
    return new File(i(), paramString);
  }
  
  public static File i(String paramString1, String paramString2)
  {
    paramString1 = i() + File.separator + paramString1;
    File localFile = new File(paramString1);
    if (!localFile.exists()) {
      localFile.mkdirs();
    }
    return new File(paramString1, paramString2);
  }
  
  /* Error */
  public static String i(File paramFile)
  {
    // Byte code:
    //   0: new 138	java/io/FileInputStream
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 141	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   8: astore_0
    //   9: aload_0
    //   10: invokevirtual 145	java/io/FileInputStream:available	()I
    //   13: newarray <illegal type>
    //   15: astore_1
    //   16: aload_0
    //   17: aload_1
    //   18: invokevirtual 149	java/io/FileInputStream:read	([B)I
    //   21: pop
    //   22: new 151	java/lang/String
    //   25: dup
    //   26: aload_1
    //   27: invokespecial 154	java/lang/String:<init>	([B)V
    //   30: astore_1
    //   31: aload_0
    //   32: ifnull +7 -> 39
    //   35: aload_0
    //   36: invokevirtual 157	java/io/FileInputStream:close	()V
    //   39: aload_1
    //   40: areturn
    //   41: astore_0
    //   42: aconst_null
    //   43: astore_0
    //   44: aload_0
    //   45: ifnull +10 -> 55
    //   48: aload_0
    //   49: invokevirtual 157	java/io/FileInputStream:close	()V
    //   52: aconst_null
    //   53: areturn
    //   54: astore_0
    //   55: aconst_null
    //   56: areturn
    //   57: astore_1
    //   58: aconst_null
    //   59: astore_0
    //   60: aload_0
    //   61: ifnull +7 -> 68
    //   64: aload_0
    //   65: invokevirtual 157	java/io/FileInputStream:close	()V
    //   68: aload_1
    //   69: athrow
    //   70: astore_0
    //   71: aload_1
    //   72: areturn
    //   73: astore_0
    //   74: goto -6 -> 68
    //   77: astore_1
    //   78: goto -18 -> 60
    //   81: astore_1
    //   82: goto -38 -> 44
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	85	0	paramFile	File
    //   15	25	1	localObject1	Object
    //   57	15	1	str	String
    //   77	1	1	localObject2	Object
    //   81	1	1	localIOException	IOException
    // Exception table:
    //   from	to	target	type
    //   0	9	41	java/io/IOException
    //   48	52	54	java/io/IOException
    //   0	9	57	finally
    //   35	39	70	java/io/IOException
    //   64	68	73	java/io/IOException
    //   9	31	77	finally
    //   9	31	81	java/io/IOException
  }
  
  public static List<File> i()
  {
    ArrayList localArrayList = new ArrayList();
    File localFile = i();
    if (localFile.exists()) {
      i(localFile, localArrayList);
    }
    return localArrayList;
  }
  
  @TargetApi(21)
  public static void i()
    throws IOException
  {
    if (Build.VERSION.SDK_INT < 21) {}
    File localFile;
    do
    {
      return;
      localFile = new File(jn.i().getFilesDir() + File.separator + "appsee");
    } while (!localFile.exists());
    mc.l(AppseeBackgroundUploader.i("j$J5W7\025&X1\0311M*\n*R%Z.V4X1\025'B([3\023:V<Q7E1\0001\033!EbH-J1J*\037w\033;V>L"), 1);
    i(localFile, new File(jn.i().getNoBackupFilesDir() + File.separator + "appsee"));
    i(localFile);
  }
  
  public static void i(File paramFile)
  {
    if ((paramFile != null) && (paramFile.exists()))
    {
      if (paramFile.isDirectory())
      {
        String[] arrayOfString = paramFile.list();
        int i1 = arrayOfString.length;
        int j = 0;
        for (int n = 0; j < i1; n = j)
        {
          String str = arrayOfString[n];
          j = n + 1;
          i(new File(paramFile, str));
        }
      }
      paramFile.delete();
    }
  }
  
  public static void i(File paramFile1, File paramFile2)
    throws IOException
  {
    Object localObject1;
    if (paramFile1.isDirectory())
    {
      localObject1 = paramFile1.list();
      if (localObject1.length <= 0) {
        mc.l(paramFile1 + AppseeBackgroundUploader.i("|P#\0257Za\000'\027oD \n\"L?[zK~T:]-["), 1);
      }
    }
    do
    {
      int j;
      for (;;)
      {
        return;
        if (!paramFile2.exists()) {
          paramFile2.mkdir();
        }
        j = 0;
        for (int n = 0; j < localObject1.length; n = j)
        {
          File localFile1 = new File(paramFile1, localObject1[n]);
          File localFile2 = new File(paramFile2, localObject1[n]);
          mc.l(localFile1 + AppseeBackgroundUploader.i("T#]a\036") + localFile2, 1);
          j = n + 1;
          i(localFile1, localFile2);
          i(localFile1);
        }
      }
      paramFile1 = new FileInputStream(paramFile1);
      paramFile2 = new FileOutputStream(paramFile2, true);
      try
      {
        localObject1 = new byte['Ѐ'];
        for (;;)
        {
          j = paramFile1.read((byte[])localObject1);
          if (j <= 0) {
            break;
          }
          paramFile2.write((byte[])localObject1, 0, j);
        }
        if (paramFile1 == null) {
          continue;
        }
      }
      finally
      {
        if (paramFile1 != null) {
          paramFile1.close();
        }
        if (paramFile2 != null) {
          paramFile2.close();
        }
      }
      paramFile1.close();
    } while (paramFile2 == null);
    paramFile2.close();
  }
  
  public static void i(String paramString)
  {
    try
    {
      File localFile = i(AppseeBackgroundUploader.i("Z(M~\006y^4Y"));
      if ((localFile.exists()) && (localFile.length() >= 1048576L)) {
        return;
      }
      i(localFile, (jc.i().i() + AppseeBackgroundUploader.i(";v7") + paramString + AppseeBackgroundUploader.i("4")).getBytes(), true);
      return;
    }
    catch (Exception paramString)
    {
      Log.e("appsee", AppseeBackgroundUploader.i("T\006,T=\n8X%]3Q=\037e\033w^4Y"), paramString);
    }
  }
  
  public static void i(String paramString, Drawable paramDrawable)
    throws IOException
  {
    if (paramDrawable == null) {
      return;
    }
    mc.l(paramString, 1);
    i(paramString, i(paramDrawable));
  }
  
  public static void i(String paramString1, String paramString2)
    throws IOException, JSONException
  {
    paramString2 = AppseeBackgroundUploader.i("\020") + "md";
    File localFile = i(AppseeBackgroundUploader.i("\bF6N"));
    if (localFile.exists())
    {
      vd.i(AppseeBackgroundUploader.i("G)];[;KpT1[7[vR0K5T6N1\021&R<^<\006lG5KzLp\002>\\<\020"));
      return;
    }
    mc.l(localFile.getAbsolutePath(), 1);
    i(localFile, paramString1.getBytes(), false);
    localFile.renameTo(i(paramString2));
  }
  
  public static void i(String paramString, byte[] paramArrayOfByte)
    throws IOException
  {
    i(i(paramString), paramArrayOfByte, false);
  }
  
  public static boolean i(int paramInt)
  {
    return i(i()) >= paramInt;
  }
  
  /* Error */
  public static byte[] i(Drawable paramDrawable)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 301	com/appsee/pb:i	(Landroid/graphics/drawable/Drawable;)Landroid/graphics/Bitmap;
    //   4: astore_0
    //   5: new 303	java/io/ByteArrayOutputStream
    //   8: dup
    //   9: invokespecial 304	java/io/ByteArrayOutputStream:<init>	()V
    //   12: astore_1
    //   13: aload_0
    //   14: getstatic 310	android/graphics/Bitmap$CompressFormat:PNG	Landroid/graphics/Bitmap$CompressFormat;
    //   17: bipush 100
    //   19: aload_1
    //   20: invokevirtual 316	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   23: pop
    //   24: aload_1
    //   25: invokevirtual 319	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   28: astore_0
    //   29: aload_1
    //   30: ifnull +7 -> 37
    //   33: aload_1
    //   34: invokevirtual 320	java/io/ByteArrayOutputStream:close	()V
    //   37: aload_0
    //   38: areturn
    //   39: astore_0
    //   40: aconst_null
    //   41: astore_1
    //   42: aload_1
    //   43: ifnull +7 -> 50
    //   46: aload_1
    //   47: invokevirtual 320	java/io/ByteArrayOutputStream:close	()V
    //   50: aload_0
    //   51: athrow
    //   52: astore_1
    //   53: aload_0
    //   54: areturn
    //   55: astore_1
    //   56: goto -6 -> 50
    //   59: astore_0
    //   60: goto -18 -> 42
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	63	0	paramDrawable	Drawable
    //   12	35	1	localByteArrayOutputStream	java.io.ByteArrayOutputStream
    //   52	1	1	localIOException1	IOException
    //   55	1	1	localIOException2	IOException
    // Exception table:
    //   from	to	target	type
    //   5	13	39	finally
    //   33	37	52	java/io/IOException
    //   46	50	55	java/io/IOException
    //   13	29	59	finally
  }
  
  /* Error */
  public static byte[] i(File paramFile, long paramLong1, long paramLong2)
    throws IOException
  {
    // Byte code:
    //   0: lload_3
    //   1: aload_0
    //   2: invokevirtual 88	java/io/File:length	()J
    //   5: lload_1
    //   6: lsub
    //   7: invokestatic 327	java/lang/Math:min	(JJ)J
    //   10: l2i
    //   11: newarray <illegal type>
    //   13: astore 6
    //   15: new 138	java/io/FileInputStream
    //   18: dup
    //   19: aload_0
    //   20: invokespecial 141	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   23: astore 5
    //   25: aload 5
    //   27: lload_1
    //   28: invokevirtual 331	java/io/FileInputStream:skip	(J)J
    //   31: pop2
    //   32: aload 5
    //   34: aload 6
    //   36: invokevirtual 149	java/io/FileInputStream:read	([B)I
    //   39: pop
    //   40: aload 5
    //   42: ifnull +8 -> 50
    //   45: aload 5
    //   47: invokevirtual 157	java/io/FileInputStream:close	()V
    //   50: aload 6
    //   52: areturn
    //   53: astore_0
    //   54: aconst_null
    //   55: astore 5
    //   57: aload 5
    //   59: ifnull +8 -> 67
    //   62: aload 5
    //   64: invokevirtual 157	java/io/FileInputStream:close	()V
    //   67: aload_0
    //   68: athrow
    //   69: astore_0
    //   70: aload 6
    //   72: areturn
    //   73: astore 5
    //   75: goto -8 -> 67
    //   78: astore_0
    //   79: goto -22 -> 57
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	82	0	paramFile	File
    //   0	82	1	paramLong1	long
    //   0	82	3	paramLong2	long
    //   23	40	5	localFileInputStream	FileInputStream
    //   73	1	5	localException	Exception
    //   13	58	6	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   15	25	53	finally
    //   45	50	69	java/lang/Exception
    //   62	67	73	java/lang/Exception
    //   25	40	78	finally
  }
  
  public static File l(String paramString)
  {
    paramString = i(paramString);
    if (paramString.exists()) {
      return paramString;
    }
    return null;
  }
  
  public static void l()
    throws IOException
  {
    File localFile = i();
    boolean bool = localFile.mkdirs();
    if (bool) {
      mc.l(AppseeBackgroundUploader.i("\030L6W(\\4\0250Vb\021~_&X*I8F(F`\037p\004'A>["), 1);
    }
    if ((!bool) && ((!localFile.exists()) || (!localFile.isDirectory()))) {
      throw new IOException(AppseeBackgroundUploader.i("u=W>Z&\027r\006;Z;OoK<Y)Z?\037w\033;V>L"));
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/hd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */