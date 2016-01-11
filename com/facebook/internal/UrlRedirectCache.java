package com.facebook.internal;

import android.net.Uri;
import com.facebook.LoggingBehavior;
import java.io.IOException;
import java.io.OutputStream;

class UrlRedirectCache
{
  private static final String REDIRECT_CONTENT_TAG = TAG + "_Redirect";
  static final String TAG = UrlRedirectCache.class.getSimpleName();
  private static volatile FileLruCache urlRedirectCache;
  
  static void cacheUriRedirect(Uri paramUri1, Uri paramUri2)
  {
    if ((paramUri1 == null) || (paramUri2 == null)) {
      return;
    }
    Uri localUri2 = null;
    Uri localUri1 = null;
    try
    {
      paramUri1 = getCache().openPutStream(paramUri1.toString(), REDIRECT_CONTENT_TAG);
      localUri1 = paramUri1;
      localUri2 = paramUri1;
      paramUri1.write(paramUri2.toString().getBytes());
      return;
    }
    catch (IOException paramUri1) {}finally
    {
      Utility.closeQuietly(localUri2);
    }
  }
  
  static void clearCache()
  {
    try
    {
      getCache().clearCache();
      return;
    }
    catch (IOException localIOException)
    {
      Logger.log(LoggingBehavior.CACHE, 5, TAG, "clearCache failed " + localIOException.getMessage());
    }
  }
  
  static FileLruCache getCache()
    throws IOException
  {
    try
    {
      if (urlRedirectCache == null) {
        urlRedirectCache = new FileLruCache(TAG, new FileLruCache.Limits());
      }
      FileLruCache localFileLruCache = urlRedirectCache;
      return localFileLruCache;
    }
    finally {}
  }
  
  /* Error */
  static Uri getRedirectedUri(Uri paramUri)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +5 -> 6
    //   4: aconst_null
    //   5: areturn
    //   6: aload_0
    //   7: invokevirtual 48	android/net/Uri:toString	()Ljava/lang/String;
    //   10: astore 4
    //   12: aconst_null
    //   13: astore_3
    //   14: aconst_null
    //   15: astore_0
    //   16: invokestatic 45	com/facebook/internal/UrlRedirectCache:getCache	()Lcom/facebook/internal/FileLruCache;
    //   19: astore 6
    //   21: iconst_0
    //   22: istore_1
    //   23: aconst_null
    //   24: astore_0
    //   25: aload 4
    //   27: astore_3
    //   28: aload 6
    //   30: aload_3
    //   31: getstatic 35	com/facebook/internal/UrlRedirectCache:REDIRECT_CONTENT_TAG	Ljava/lang/String;
    //   34: invokevirtual 107	com/facebook/internal/FileLruCache:get	(Ljava/lang/String;Ljava/lang/String;)Ljava/io/InputStream;
    //   37: astore 4
    //   39: aload 4
    //   41: ifnull +125 -> 166
    //   44: iconst_1
    //   45: istore_1
    //   46: new 109	java/io/InputStreamReader
    //   49: dup
    //   50: aload 4
    //   52: invokespecial 112	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   55: astore 4
    //   57: aload 4
    //   59: astore_0
    //   60: aload 4
    //   62: astore_3
    //   63: sipush 128
    //   66: newarray <illegal type>
    //   68: astore 5
    //   70: aload 4
    //   72: astore_0
    //   73: aload 4
    //   75: astore_3
    //   76: new 21	java/lang/StringBuilder
    //   79: dup
    //   80: invokespecial 24	java/lang/StringBuilder:<init>	()V
    //   83: astore 7
    //   85: aload 4
    //   87: astore_0
    //   88: aload 4
    //   90: astore_3
    //   91: aload 4
    //   93: aload 5
    //   95: iconst_0
    //   96: aload 5
    //   98: arraylength
    //   99: invokevirtual 116	java/io/InputStreamReader:read	([CII)I
    //   102: istore_2
    //   103: iload_2
    //   104: ifle +29 -> 133
    //   107: aload 4
    //   109: astore_0
    //   110: aload 4
    //   112: astore_3
    //   113: aload 7
    //   115: aload 5
    //   117: iconst_0
    //   118: iload_2
    //   119: invokevirtual 119	java/lang/StringBuilder:append	([CII)Ljava/lang/StringBuilder;
    //   122: pop
    //   123: goto -38 -> 85
    //   126: astore_3
    //   127: aload_0
    //   128: invokestatic 72	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   131: aconst_null
    //   132: areturn
    //   133: aload 4
    //   135: astore_0
    //   136: aload 4
    //   138: astore_3
    //   139: aload 4
    //   141: invokestatic 72	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   144: aload 4
    //   146: astore_0
    //   147: aload 4
    //   149: astore_3
    //   150: aload 7
    //   152: invokevirtual 33	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   155: astore 5
    //   157: aload 5
    //   159: astore_3
    //   160: aload 4
    //   162: astore_0
    //   163: goto -135 -> 28
    //   166: iload_1
    //   167: ifeq +14 -> 181
    //   170: aload_3
    //   171: invokestatic 123	android/net/Uri:parse	(Ljava/lang/String;)Landroid/net/Uri;
    //   174: astore_3
    //   175: aload_0
    //   176: invokestatic 72	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   179: aload_3
    //   180: areturn
    //   181: aload_0
    //   182: invokestatic 72	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   185: aconst_null
    //   186: areturn
    //   187: astore_0
    //   188: aload_3
    //   189: invokestatic 72	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   192: aload_0
    //   193: athrow
    //   194: astore 4
    //   196: aload_0
    //   197: astore_3
    //   198: aload 4
    //   200: astore_0
    //   201: goto -13 -> 188
    //   204: astore_3
    //   205: goto -78 -> 127
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	208	0	paramUri	Uri
    //   22	145	1	i	int
    //   102	17	2	j	int
    //   13	100	3	localObject1	Object
    //   126	1	3	localIOException1	IOException
    //   138	60	3	localObject2	Object
    //   204	1	3	localIOException2	IOException
    //   10	151	4	localObject3	Object
    //   194	5	4	localObject4	Object
    //   68	90	5	localObject5	Object
    //   19	10	6	localFileLruCache	FileLruCache
    //   83	68	7	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   16	21	126	java/io/IOException
    //   63	70	126	java/io/IOException
    //   76	85	126	java/io/IOException
    //   91	103	126	java/io/IOException
    //   113	123	126	java/io/IOException
    //   139	144	126	java/io/IOException
    //   150	157	126	java/io/IOException
    //   16	21	187	finally
    //   63	70	187	finally
    //   76	85	187	finally
    //   91	103	187	finally
    //   113	123	187	finally
    //   139	144	187	finally
    //   150	157	187	finally
    //   28	39	194	finally
    //   46	57	194	finally
    //   170	175	194	finally
    //   28	39	204	java/io/IOException
    //   46	57	204	java/io/IOException
    //   170	175	204	java/io/IOException
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/internal/UrlRedirectCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */