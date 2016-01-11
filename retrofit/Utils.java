package retrofit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

final class Utils
{
  private static final int BUFFER_SIZE = 4096;
  
  static Request readBodyToBytesIfNecessary(Request paramRequest)
    throws IOException
  {
    Object localObject = paramRequest.getBody();
    if ((localObject == null) || ((localObject instanceof TypedByteArray))) {
      return paramRequest;
    }
    String str = ((TypedOutput)localObject).mimeType();
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    ((TypedOutput)localObject).writeTo(localByteArrayOutputStream);
    localObject = new TypedByteArray(str, localByteArrayOutputStream.toByteArray());
    return new Request(paramRequest.getMethod(), paramRequest.getUrl(), paramRequest.getHeaders(), (TypedOutput)localObject);
  }
  
  /* Error */
  static Response readBodyToBytesIfNecessary(Response paramResponse)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 67	retrofit/client/Response:getBody	()Lretrofit/mime/TypedInput;
    //   4: astore_1
    //   5: aload_1
    //   6: ifnull +10 -> 16
    //   9: aload_1
    //   10: instanceof 27
    //   13: ifeq +5 -> 18
    //   16: aload_0
    //   17: areturn
    //   18: aload_1
    //   19: invokeinterface 70 1 0
    //   24: astore_2
    //   25: aload_1
    //   26: invokeinterface 74 1 0
    //   31: astore_1
    //   32: new 27	retrofit/mime/TypedByteArray
    //   35: dup
    //   36: aload_2
    //   37: aload_1
    //   38: invokestatic 78	retrofit/Utils:streamToBytes	(Ljava/io/InputStream;)[B
    //   41: invokespecial 47	retrofit/mime/TypedByteArray:<init>	(Ljava/lang/String;[B)V
    //   44: astore_2
    //   45: aload_0
    //   46: aload_2
    //   47: invokestatic 82	retrofit/Utils:replaceResponseBody	(Lretrofit/client/Response;Lretrofit/mime/TypedInput;)Lretrofit/client/Response;
    //   50: astore_0
    //   51: aload_1
    //   52: ifnull +7 -> 59
    //   55: aload_1
    //   56: invokevirtual 87	java/io/InputStream:close	()V
    //   59: aload_0
    //   60: areturn
    //   61: astore_0
    //   62: aload_1
    //   63: ifnull +7 -> 70
    //   66: aload_1
    //   67: invokevirtual 87	java/io/InputStream:close	()V
    //   70: aload_0
    //   71: athrow
    //   72: astore_1
    //   73: goto -14 -> 59
    //   76: astore_1
    //   77: goto -7 -> 70
    //   80: astore_0
    //   81: goto -19 -> 62
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	84	0	paramResponse	Response
    //   4	63	1	localObject1	Object
    //   72	1	1	localIOException1	IOException
    //   76	1	1	localIOException2	IOException
    //   24	23	2	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   32	45	61	finally
    //   55	59	72	java/io/IOException
    //   66	70	76	java/io/IOException
    //   45	51	80	finally
  }
  
  static Response replaceResponseBody(Response paramResponse, TypedInput paramTypedInput)
  {
    return new Response(paramResponse.getUrl(), paramResponse.getStatus(), paramResponse.getReason(), paramResponse.getHeaders(), paramTypedInput);
  }
  
  static byte[] streamToBytes(InputStream paramInputStream)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    if (paramInputStream != null)
    {
      byte[] arrayOfByte = new byte['က'];
      for (;;)
      {
        int i = paramInputStream.read(arrayOfByte);
        if (i == -1) {
          break;
        }
        localByteArrayOutputStream.write(arrayOfByte, 0, i);
      }
    }
    return localByteArrayOutputStream.toByteArray();
  }
  
  static <T> void validateServiceClass(Class<T> paramClass)
  {
    if (!paramClass.isInterface()) {
      throw new IllegalArgumentException("Only interface endpoint definitions are supported.");
    }
    if (paramClass.getInterfaces().length > 0) {
      throw new IllegalArgumentException("Interface definitions must not extend other interfaces.");
    }
  }
  
  static class SynchronousExecutor
    implements Executor
  {
    public void execute(Runnable paramRunnable)
    {
      paramRunnable.run();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/retrofit/Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */