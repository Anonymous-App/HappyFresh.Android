package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.io.OutputStream;
import java.io.Writer;

final class ExceptionUtils
{
  private static String getMessage(Throwable paramThrowable)
  {
    paramThrowable = paramThrowable.getLocalizedMessage();
    if (paramThrowable == null) {
      return null;
    }
    return paramThrowable.replaceAll("(\r\n|\n|\f)", " ");
  }
  
  /* Error */
  public static void writeStackTrace(android.content.Context paramContext, Throwable paramThrowable, String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aconst_null
    //   3: astore 4
    //   5: new 33	java/io/PrintWriter
    //   8: dup
    //   9: aload_0
    //   10: aload_2
    //   11: iconst_0
    //   12: invokevirtual 39	android/content/Context:openFileOutput	(Ljava/lang/String;I)Ljava/io/FileOutputStream;
    //   15: invokespecial 42	java/io/PrintWriter:<init>	(Ljava/io/OutputStream;)V
    //   18: astore_0
    //   19: aload_1
    //   20: aload_0
    //   21: invokestatic 45	com/crashlytics/android/core/ExceptionUtils:writeStackTrace	(Ljava/lang/Throwable;Ljava/io/Writer;)V
    //   24: aload_0
    //   25: ldc 47
    //   27: invokestatic 53	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   30: return
    //   31: astore_1
    //   32: aload 4
    //   34: astore_0
    //   35: aload_0
    //   36: astore_3
    //   37: invokestatic 59	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   40: ldc 61
    //   42: ldc 63
    //   44: aload_1
    //   45: invokeinterface 69 4 0
    //   50: aload_0
    //   51: ldc 47
    //   53: invokestatic 53	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   56: return
    //   57: astore_0
    //   58: aload_3
    //   59: ldc 47
    //   61: invokestatic 53	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   64: aload_0
    //   65: athrow
    //   66: astore_1
    //   67: aload_0
    //   68: astore_3
    //   69: aload_1
    //   70: astore_0
    //   71: goto -13 -> 58
    //   74: astore_1
    //   75: goto -40 -> 35
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	78	0	paramContext	android.content.Context
    //   0	78	1	paramThrowable	Throwable
    //   0	78	2	paramString	String
    //   1	68	3	localContext	android.content.Context
    //   3	30	4	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   5	19	31	java/lang/Exception
    //   5	19	57	finally
    //   37	50	57	finally
    //   19	24	66	finally
    //   19	24	74	java/lang/Exception
  }
  
  /* Error */
  private static void writeStackTrace(Throwable paramThrowable, OutputStream paramOutputStream)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aconst_null
    //   3: astore_3
    //   4: new 33	java/io/PrintWriter
    //   7: dup
    //   8: aload_1
    //   9: invokespecial 42	java/io/PrintWriter:<init>	(Ljava/io/OutputStream;)V
    //   12: astore_1
    //   13: aload_0
    //   14: aload_1
    //   15: invokestatic 45	com/crashlytics/android/core/ExceptionUtils:writeStackTrace	(Ljava/lang/Throwable;Ljava/io/Writer;)V
    //   18: aload_1
    //   19: ldc 47
    //   21: invokestatic 53	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   24: return
    //   25: astore_1
    //   26: aload_3
    //   27: astore_0
    //   28: aload_0
    //   29: astore_2
    //   30: invokestatic 59	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   33: ldc 61
    //   35: ldc 63
    //   37: aload_1
    //   38: invokeinterface 69 4 0
    //   43: aload_0
    //   44: ldc 47
    //   46: invokestatic 53	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   49: return
    //   50: astore_0
    //   51: aload_2
    //   52: ldc 47
    //   54: invokestatic 53	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   57: aload_0
    //   58: athrow
    //   59: astore_0
    //   60: aload_1
    //   61: astore_2
    //   62: goto -11 -> 51
    //   65: astore_2
    //   66: aload_1
    //   67: astore_0
    //   68: aload_2
    //   69: astore_1
    //   70: goto -42 -> 28
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	73	0	paramThrowable	Throwable
    //   0	73	1	paramOutputStream	OutputStream
    //   1	61	2	localObject1	Object
    //   65	4	2	localException	Exception
    //   3	24	3	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   4	13	25	java/lang/Exception
    //   4	13	50	finally
    //   30	43	50	finally
    //   13	18	59	finally
    //   13	18	65	java/lang/Exception
  }
  
  private static void writeStackTrace(Throwable paramThrowable, Writer paramWriter)
  {
    int i = 1;
    if (paramThrowable != null) {}
    for (;;)
    {
      String str;
      try
      {
        localObject = getMessage(paramThrowable);
        if (localObject == null) {
          break label166;
        }
      }
      catch (Exception paramThrowable)
      {
        int j;
        int k;
        Fabric.getLogger().e("CrashlyticsCore", "Could not write stack trace", paramThrowable);
      }
      paramWriter.write(str + paramThrowable.getClass().getName() + ": " + (String)localObject + "\n");
      j = 0;
      Object localObject = paramThrowable.getStackTrace();
      k = localObject.length;
      i = 0;
      if (i < k)
      {
        str = localObject[i];
        paramWriter.write("\tat " + str.toString() + "\n");
        i += 1;
      }
      else
      {
        paramThrowable = paramThrowable.getCause();
        i = j;
        break;
        return;
        for (;;)
        {
          if (i == 0) {
            break label173;
          }
          str = "";
          break;
          label166:
          localObject = "";
        }
        label173:
        str = "Caused by: ";
      }
    }
  }
  
  public static void writeStackTraceIfNotNull(Throwable paramThrowable, OutputStream paramOutputStream)
  {
    if (paramOutputStream != null) {
      writeStackTrace(paramThrowable, paramOutputStream);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/crashlytics/android/core/ExceptionUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */