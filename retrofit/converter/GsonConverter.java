package retrofit.converter;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import retrofit.mime.TypedOutput;

public class GsonConverter
  implements Converter
{
  private String charset;
  private final Gson gson;
  
  public GsonConverter(Gson paramGson)
  {
    this(paramGson, "UTF-8");
  }
  
  public GsonConverter(Gson paramGson, String paramString)
  {
    this.gson = paramGson;
    this.charset = paramString;
  }
  
  /* Error */
  public Object fromBody(retrofit.mime.TypedInput paramTypedInput, java.lang.reflect.Type paramType)
    throws ConversionException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 28	retrofit/converter/GsonConverter:charset	Ljava/lang/String;
    //   4: astore_3
    //   5: aload_3
    //   6: astore 4
    //   8: aload_1
    //   9: invokeinterface 42 1 0
    //   14: ifnull +15 -> 29
    //   17: aload_1
    //   18: invokeinterface 42 1 0
    //   23: aload_3
    //   24: invokestatic 48	retrofit/mime/MimeUtil:parseCharset	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   27: astore 4
    //   29: aconst_null
    //   30: astore_3
    //   31: aconst_null
    //   32: astore 6
    //   34: aconst_null
    //   35: astore 5
    //   37: new 50	java/io/InputStreamReader
    //   40: dup
    //   41: aload_1
    //   42: invokeinterface 54 1 0
    //   47: aload 4
    //   49: invokespecial 57	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
    //   52: astore_1
    //   53: aload_0
    //   54: getfield 26	retrofit/converter/GsonConverter:gson	Lcom/google/gson/Gson;
    //   57: aload_1
    //   58: aload_2
    //   59: invokevirtual 63	com/google/gson/Gson:fromJson	(Ljava/io/Reader;Ljava/lang/reflect/Type;)Ljava/lang/Object;
    //   62: astore_2
    //   63: aload_1
    //   64: ifnull +7 -> 71
    //   67: aload_1
    //   68: invokevirtual 66	java/io/InputStreamReader:close	()V
    //   71: aload_2
    //   72: areturn
    //   73: astore_1
    //   74: aload 5
    //   76: astore_3
    //   77: new 32	retrofit/converter/ConversionException
    //   80: dup
    //   81: aload_1
    //   82: invokespecial 69	retrofit/converter/ConversionException:<init>	(Ljava/lang/Throwable;)V
    //   85: athrow
    //   86: astore_1
    //   87: aload_3
    //   88: ifnull +7 -> 95
    //   91: aload_3
    //   92: invokevirtual 66	java/io/InputStreamReader:close	()V
    //   95: aload_1
    //   96: athrow
    //   97: astore_1
    //   98: aload 6
    //   100: astore_3
    //   101: new 32	retrofit/converter/ConversionException
    //   104: dup
    //   105: aload_1
    //   106: invokespecial 69	retrofit/converter/ConversionException:<init>	(Ljava/lang/Throwable;)V
    //   109: athrow
    //   110: astore_1
    //   111: aload_2
    //   112: areturn
    //   113: astore_2
    //   114: goto -19 -> 95
    //   117: astore_2
    //   118: aload_1
    //   119: astore_3
    //   120: aload_2
    //   121: astore_1
    //   122: goto -35 -> 87
    //   125: astore_2
    //   126: aload_1
    //   127: astore_3
    //   128: aload_2
    //   129: astore_1
    //   130: goto -29 -> 101
    //   133: astore_2
    //   134: aload_1
    //   135: astore_3
    //   136: aload_2
    //   137: astore_1
    //   138: goto -61 -> 77
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	141	0	this	GsonConverter
    //   0	141	1	paramTypedInput	retrofit.mime.TypedInput
    //   0	141	2	paramType	java.lang.reflect.Type
    //   4	132	3	localObject1	Object
    //   6	42	4	localObject2	Object
    //   35	40	5	localObject3	Object
    //   32	67	6	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   37	53	73	java/io/IOException
    //   37	53	86	finally
    //   77	86	86	finally
    //   101	110	86	finally
    //   37	53	97	com/google/gson/JsonParseException
    //   67	71	110	java/io/IOException
    //   91	95	113	java/io/IOException
    //   53	63	117	finally
    //   53	63	125	com/google/gson/JsonParseException
    //   53	63	133	java/io/IOException
  }
  
  public TypedOutput toBody(Object paramObject)
  {
    try
    {
      paramObject = new JsonTypedOutput(this.gson.toJson(paramObject).getBytes(this.charset), this.charset);
      return (TypedOutput)paramObject;
    }
    catch (UnsupportedEncodingException paramObject)
    {
      throw new AssertionError(paramObject);
    }
  }
  
  private static class JsonTypedOutput
    implements TypedOutput
  {
    private final byte[] jsonBytes;
    private final String mimeType;
    
    JsonTypedOutput(byte[] paramArrayOfByte, String paramString)
    {
      this.jsonBytes = paramArrayOfByte;
      this.mimeType = ("application/json; charset=" + paramString);
    }
    
    public String fileName()
    {
      return null;
    }
    
    public long length()
    {
      return this.jsonBytes.length;
    }
    
    public String mimeType()
    {
      return this.mimeType;
    }
    
    public void writeTo(OutputStream paramOutputStream)
      throws IOException
    {
      paramOutputStream.write(this.jsonBytes);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/retrofit/converter/GsonConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */