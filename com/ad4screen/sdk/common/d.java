package com.ad4screen.sdk.common;

import com.ad4screen.sdk.Log;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public final class d
{
  public static String a(String paramString)
  {
    return a(paramString, "GET", null);
  }
  
  /* Error */
  private static String a(String paramString1, String paramString2, byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: new 18	java/net/URL
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 22	java/net/URL:<init>	(Ljava/lang/String;)V
    //   8: astore 4
    //   10: aload 4
    //   12: invokevirtual 26	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   15: checkcast 28	java/net/HttpURLConnection
    //   18: astore_3
    //   19: aload_3
    //   20: aload_1
    //   21: invokevirtual 31	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   24: aload_3
    //   25: iconst_1
    //   26: invokevirtual 35	java/net/HttpURLConnection:setDoInput	(Z)V
    //   29: aload_2
    //   30: ifnull +33 -> 63
    //   33: aload_3
    //   34: iconst_1
    //   35: invokevirtual 38	java/net/HttpURLConnection:setDoOutput	(Z)V
    //   38: new 40	java/io/BufferedOutputStream
    //   41: dup
    //   42: aload_3
    //   43: invokevirtual 44	java/net/HttpURLConnection:getOutputStream	()Ljava/io/OutputStream;
    //   46: invokespecial 47	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   49: astore_1
    //   50: aload_1
    //   51: aload_2
    //   52: invokevirtual 53	java/io/OutputStream:write	([B)V
    //   55: aload_1
    //   56: invokevirtual 57	java/io/OutputStream:flush	()V
    //   59: aload_1
    //   60: invokevirtual 60	java/io/OutputStream:close	()V
    //   63: aload 4
    //   65: aload_3
    //   66: invokestatic 63	com/ad4screen/sdk/common/d:a	(Ljava/net/URL;Ljava/net/HttpURLConnection;)Ljava/lang/String;
    //   69: astore_1
    //   70: aload_3
    //   71: invokevirtual 66	java/net/HttpURLConnection:disconnect	()V
    //   74: aload_1
    //   75: areturn
    //   76: astore_2
    //   77: aconst_null
    //   78: astore_1
    //   79: new 68	java/lang/StringBuilder
    //   82: dup
    //   83: invokespecial 70	java/lang/StringBuilder:<init>	()V
    //   86: ldc 72
    //   88: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   91: aload_0
    //   92: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   98: aload_2
    //   99: invokestatic 86	com/ad4screen/sdk/Log:error	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   102: aload_1
    //   103: areturn
    //   104: astore_2
    //   105: aconst_null
    //   106: astore_1
    //   107: new 68	java/lang/StringBuilder
    //   110: dup
    //   111: invokespecial 70	java/lang/StringBuilder:<init>	()V
    //   114: ldc 88
    //   116: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   119: aload_0
    //   120: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   123: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   126: aload_2
    //   127: invokestatic 86	com/ad4screen/sdk/Log:error	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   130: aload_1
    //   131: areturn
    //   132: astore_2
    //   133: goto -26 -> 107
    //   136: astore_2
    //   137: goto -58 -> 79
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	140	0	paramString1	String
    //   0	140	1	paramString2	String
    //   0	140	2	paramArrayOfByte	byte[]
    //   18	53	3	localHttpURLConnection	HttpURLConnection
    //   8	56	4	localURL	URL
    // Exception table:
    //   from	to	target	type
    //   0	29	76	java/net/MalformedURLException
    //   33	63	76	java/net/MalformedURLException
    //   63	70	76	java/net/MalformedURLException
    //   0	29	104	java/io/IOException
    //   33	63	104	java/io/IOException
    //   63	70	104	java/io/IOException
    //   70	74	132	java/io/IOException
    //   70	74	136	java/net/MalformedURLException
  }
  
  public static String a(String paramString, byte[] paramArrayOfByte)
  {
    return a(paramString, "POST", paramArrayOfByte);
  }
  
  private static String a(URL paramURL, HttpURLConnection paramHttpURLConnection)
  {
    try
    {
      localObject = new BufferedInputStream(paramHttpURLConnection.getInputStream(), 8192);
      paramHttpURLConnection = new ByteArrayOutputStream();
      byte[] arrayOfByte = new byte[' '];
      for (;;)
      {
        int i = ((BufferedInputStream)localObject).read(arrayOfByte, 0, 8192);
        if (i == -1) {
          break;
        }
        paramHttpURLConnection.write(arrayOfByte, 0, i);
      }
      ((BufferedInputStream)localObject).close();
    }
    catch (IOException paramHttpURLConnection)
    {
      Log.error("HTTP|Could not read response from url : " + paramURL, paramHttpURLConnection);
      return null;
    }
    Object localObject = paramHttpURLConnection.toByteArray();
    paramHttpURLConnection.close();
    paramHttpURLConnection = new String((byte[])localObject);
    return paramHttpURLConnection;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */