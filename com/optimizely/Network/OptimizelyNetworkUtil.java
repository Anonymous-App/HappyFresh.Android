package com.optimizely.Network;

import android.support.annotation.NonNull;
import com.optimizely.Optimizely;
import com.squareup.okhttp.ResponseBody;

public class OptimizelyNetworkUtil<T>
{
  public static final int ERROR_4XX = 3587;
  public static final int ERROR_NETWORK = 3586;
  public static final int ERROR_NO_PERMISSION = 3585;
  public static final int ERROR_UNKNOWN = 3737;
  private static final String OPTIMIZELY_NETWORK_UTIL = "OptimizelyNetworkUtil";
  @NonNull
  public static final Function<byte[]> TRANSFORM_TO_BYTE_ARRAY = new Function()
  {
    @NonNull
    public byte[] transform(@NonNull ResponseBody paramAnonymousResponseBody)
      throws Exception
    {
      return paramAnonymousResponseBody.bytes();
    }
  };
  @NonNull
  public static final Function<String> TRANSFORM_TO_STRING = new Function()
  {
    @NonNull
    public String transform(@NonNull ResponseBody paramAnonymousResponseBody)
      throws Exception
    {
      return paramAnonymousResponseBody.string();
    }
  };
  private final int networkTimeout;
  @NonNull
  private final Optimizely optimizely;
  @NonNull
  private final Function<T> transform;
  
  public OptimizelyNetworkUtil(@NonNull Optimizely paramOptimizely, int paramInt, @NonNull Function<T> paramFunction)
  {
    this.optimizely = paramOptimizely;
    this.networkTimeout = paramInt;
    this.transform = paramFunction;
  }
  
  /* Error */
  @NonNull
  public android.util.Pair<T, Integer> downloadFromUrl(@NonNull com.squareup.okhttp.OkHttpClient paramOkHttpClient, @NonNull String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: aload_0
    //   2: getfield 53	com/optimizely/Network/OptimizelyNetworkUtil:networkTimeout	I
    //   5: i2l
    //   6: getstatic 72	java/util/concurrent/TimeUnit:MILLISECONDS	Ljava/util/concurrent/TimeUnit;
    //   9: invokevirtual 78	com/squareup/okhttp/OkHttpClient:setConnectTimeout	(JLjava/util/concurrent/TimeUnit;)V
    //   12: aload_1
    //   13: iconst_0
    //   14: invokevirtual 82	com/squareup/okhttp/OkHttpClient:setRetryOnConnectionFailure	(Z)V
    //   17: new 84	com/squareup/okhttp/Request$Builder
    //   20: dup
    //   21: invokespecial 85	com/squareup/okhttp/Request$Builder:<init>	()V
    //   24: aload_2
    //   25: invokevirtual 89	com/squareup/okhttp/Request$Builder:url	(Ljava/lang/String;)Lcom/squareup/okhttp/Request$Builder;
    //   28: invokevirtual 93	com/squareup/okhttp/Request$Builder:build	()Lcom/squareup/okhttp/Request;
    //   31: astore_2
    //   32: aload_0
    //   33: getfield 51	com/optimizely/Network/OptimizelyNetworkUtil:optimizely	Lcom/optimizely/Optimizely;
    //   36: ldc 25
    //   38: ldc 95
    //   40: iconst_1
    //   41: anewarray 5	java/lang/Object
    //   44: dup
    //   45: iconst_0
    //   46: aload_2
    //   47: invokevirtual 101	com/squareup/okhttp/Request:urlString	()Ljava/lang/String;
    //   50: aastore
    //   51: invokevirtual 107	com/optimizely/Optimizely:verboseLog	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   54: aload_1
    //   55: aload_2
    //   56: invokevirtual 111	com/squareup/okhttp/OkHttpClient:newCall	(Lcom/squareup/okhttp/Request;)Lcom/squareup/okhttp/Call;
    //   59: invokevirtual 117	com/squareup/okhttp/Call:execute	()Lcom/squareup/okhttp/Response;
    //   62: astore_1
    //   63: aload_1
    //   64: invokevirtual 123	com/squareup/okhttp/Response:body	()Lcom/squareup/okhttp/ResponseBody;
    //   67: astore_3
    //   68: aload_1
    //   69: invokevirtual 127	com/squareup/okhttp/Response:isSuccessful	()Z
    //   72: ifeq +109 -> 181
    //   75: aload_3
    //   76: ifnull +39 -> 115
    //   79: new 129	android/util/Pair
    //   82: dup
    //   83: aload_0
    //   84: getfield 55	com/optimizely/Network/OptimizelyNetworkUtil:transform	Lcom/optimizely/Network/OptimizelyNetworkUtil$Function;
    //   87: aload_3
    //   88: invokeinterface 132 2 0
    //   93: iconst_m1
    //   94: invokestatic 138	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   97: invokespecial 141	android/util/Pair:<init>	(Ljava/lang/Object;Ljava/lang/Object;)V
    //   100: astore_2
    //   101: aload_2
    //   102: astore_1
    //   103: aload_3
    //   104: ifnull +9 -> 113
    //   107: aload_3
    //   108: invokevirtual 146	com/squareup/okhttp/ResponseBody:close	()V
    //   111: aload_2
    //   112: astore_1
    //   113: aload_1
    //   114: areturn
    //   115: new 129	android/util/Pair
    //   118: dup
    //   119: aconst_null
    //   120: sipush 3737
    //   123: invokestatic 138	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   126: invokespecial 141	android/util/Pair:<init>	(Ljava/lang/Object;Ljava/lang/Object;)V
    //   129: astore_2
    //   130: aload_2
    //   131: astore_1
    //   132: aload_3
    //   133: ifnull -20 -> 113
    //   136: aload_3
    //   137: invokevirtual 146	com/squareup/okhttp/ResponseBody:close	()V
    //   140: aload_2
    //   141: areturn
    //   142: astore_1
    //   143: aload_0
    //   144: getfield 51	com/optimizely/Network/OptimizelyNetworkUtil:optimizely	Lcom/optimizely/Optimizely;
    //   147: iconst_1
    //   148: ldc 25
    //   150: ldc -108
    //   152: iconst_1
    //   153: anewarray 5	java/lang/Object
    //   156: dup
    //   157: iconst_0
    //   158: aload_1
    //   159: invokevirtual 151	java/io/IOException:getMessage	()Ljava/lang/String;
    //   162: aastore
    //   163: invokevirtual 154	com/optimizely/Optimizely:verboseLog	(ZLjava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   166: new 129	android/util/Pair
    //   169: dup
    //   170: aconst_null
    //   171: sipush 3586
    //   174: invokestatic 138	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   177: invokespecial 141	android/util/Pair:<init>	(Ljava/lang/Object;Ljava/lang/Object;)V
    //   180: areturn
    //   181: new 129	android/util/Pair
    //   184: dup
    //   185: aconst_null
    //   186: sipush 3587
    //   189: invokestatic 138	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   192: invokespecial 141	android/util/Pair:<init>	(Ljava/lang/Object;Ljava/lang/Object;)V
    //   195: astore_2
    //   196: aload_2
    //   197: astore_1
    //   198: aload_3
    //   199: ifnull -86 -> 113
    //   202: aload_3
    //   203: invokevirtual 146	com/squareup/okhttp/ResponseBody:close	()V
    //   206: aload_2
    //   207: areturn
    //   208: astore_1
    //   209: aload_0
    //   210: getfield 51	com/optimizely/Network/OptimizelyNetworkUtil:optimizely	Lcom/optimizely/Optimizely;
    //   213: iconst_1
    //   214: ldc 25
    //   216: ldc -100
    //   218: iconst_1
    //   219: anewarray 5	java/lang/Object
    //   222: dup
    //   223: iconst_0
    //   224: aload_1
    //   225: invokevirtual 159	java/lang/SecurityException:getLocalizedMessage	()Ljava/lang/String;
    //   228: aastore
    //   229: invokevirtual 154	com/optimizely/Optimizely:verboseLog	(ZLjava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   232: new 129	android/util/Pair
    //   235: dup
    //   236: aconst_null
    //   237: sipush 3585
    //   240: invokestatic 138	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   243: invokespecial 141	android/util/Pair:<init>	(Ljava/lang/Object;Ljava/lang/Object;)V
    //   246: areturn
    //   247: astore_1
    //   248: aload_3
    //   249: ifnull +7 -> 256
    //   252: aload_3
    //   253: invokevirtual 146	com/squareup/okhttp/ResponseBody:close	()V
    //   256: aload_1
    //   257: athrow
    //   258: astore_1
    //   259: aload_0
    //   260: getfield 51	com/optimizely/Network/OptimizelyNetworkUtil:optimizely	Lcom/optimizely/Optimizely;
    //   263: iconst_1
    //   264: ldc 25
    //   266: ldc -95
    //   268: iconst_1
    //   269: anewarray 5	java/lang/Object
    //   272: dup
    //   273: iconst_0
    //   274: aload_1
    //   275: invokevirtual 162	java/lang/Exception:getLocalizedMessage	()Ljava/lang/String;
    //   278: aastore
    //   279: invokevirtual 154	com/optimizely/Optimizely:verboseLog	(ZLjava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   282: new 129	android/util/Pair
    //   285: dup
    //   286: aconst_null
    //   287: sipush 3737
    //   290: invokestatic 138	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   293: invokespecial 141	android/util/Pair:<init>	(Ljava/lang/Object;Ljava/lang/Object;)V
    //   296: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	297	0	this	OptimizelyNetworkUtil
    //   0	297	1	paramOkHttpClient	com.squareup.okhttp.OkHttpClient
    //   0	297	2	paramString	String
    //   67	186	3	localResponseBody	ResponseBody
    // Exception table:
    //   from	to	target	type
    //   17	68	142	java/io/IOException
    //   107	111	142	java/io/IOException
    //   136	140	142	java/io/IOException
    //   202	206	142	java/io/IOException
    //   252	256	142	java/io/IOException
    //   256	258	142	java/io/IOException
    //   17	68	208	java/lang/SecurityException
    //   107	111	208	java/lang/SecurityException
    //   136	140	208	java/lang/SecurityException
    //   202	206	208	java/lang/SecurityException
    //   252	256	208	java/lang/SecurityException
    //   256	258	208	java/lang/SecurityException
    //   68	75	247	finally
    //   79	101	247	finally
    //   115	130	247	finally
    //   181	196	247	finally
    //   17	68	258	java/lang/Exception
    //   107	111	258	java/lang/Exception
    //   136	140	258	java/lang/Exception
    //   202	206	258	java/lang/Exception
    //   252	256	258	java/lang/Exception
    //   256	258	258	java/lang/Exception
  }
  
  public static abstract interface Function<T>
  {
    @NonNull
    public abstract T transform(ResponseBody paramResponseBody)
      throws Exception;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/OptimizelyNetworkUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */