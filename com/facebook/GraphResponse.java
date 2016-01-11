package com.facebook;

import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class GraphResponse
{
  private static final String BODY_KEY = "body";
  private static final String CODE_KEY = "code";
  public static final String NON_JSON_RESPONSE_PROPERTY = "FACEBOOK_NON_JSON_RESULT";
  private static final String RESPONSE_LOG_TAG = "Response";
  public static final String SUCCESS_KEY = "success";
  private final HttpURLConnection connection;
  private final FacebookRequestError error;
  private final JSONObject graphObject;
  private final JSONArray graphObjectArray;
  private final String rawResponse;
  private final GraphRequest request;
  
  GraphResponse(GraphRequest paramGraphRequest, HttpURLConnection paramHttpURLConnection, FacebookRequestError paramFacebookRequestError)
  {
    this(paramGraphRequest, paramHttpURLConnection, null, null, null, paramFacebookRequestError);
  }
  
  GraphResponse(GraphRequest paramGraphRequest, HttpURLConnection paramHttpURLConnection, String paramString, JSONArray paramJSONArray)
  {
    this(paramGraphRequest, paramHttpURLConnection, paramString, null, paramJSONArray, null);
  }
  
  GraphResponse(GraphRequest paramGraphRequest, HttpURLConnection paramHttpURLConnection, String paramString, JSONObject paramJSONObject)
  {
    this(paramGraphRequest, paramHttpURLConnection, paramString, paramJSONObject, null, null);
  }
  
  GraphResponse(GraphRequest paramGraphRequest, HttpURLConnection paramHttpURLConnection, String paramString, JSONObject paramJSONObject, JSONArray paramJSONArray, FacebookRequestError paramFacebookRequestError)
  {
    this.request = paramGraphRequest;
    this.connection = paramHttpURLConnection;
    this.rawResponse = paramString;
    this.graphObject = paramJSONObject;
    this.graphObjectArray = paramJSONArray;
    this.error = paramFacebookRequestError;
  }
  
  static List<GraphResponse> constructErrorResponses(List<GraphRequest> paramList, HttpURLConnection paramHttpURLConnection, FacebookException paramFacebookException)
  {
    int j = paramList.size();
    ArrayList localArrayList = new ArrayList(j);
    int i = 0;
    while (i < j)
    {
      localArrayList.add(new GraphResponse((GraphRequest)paramList.get(i), paramHttpURLConnection, new FacebookRequestError(paramHttpURLConnection, paramFacebookException)));
      i += 1;
    }
    return localArrayList;
  }
  
  private static GraphResponse createResponseFromObject(GraphRequest paramGraphRequest, HttpURLConnection paramHttpURLConnection, Object paramObject1, Object paramObject2)
    throws JSONException
  {
    Object localObject = paramObject1;
    if ((paramObject1 instanceof JSONObject))
    {
      paramObject1 = (JSONObject)paramObject1;
      paramObject2 = FacebookRequestError.checkResponseAndCreateError((JSONObject)paramObject1, paramObject2, paramHttpURLConnection);
      if (paramObject2 != null)
      {
        if ((((FacebookRequestError)paramObject2).getErrorCode() == 190) && (Utility.isCurrentAccessToken(paramGraphRequest.getAccessToken()))) {
          AccessToken.setCurrentAccessToken(null);
        }
        return new GraphResponse(paramGraphRequest, paramHttpURLConnection, (FacebookRequestError)paramObject2);
      }
      paramObject1 = Utility.getStringPropertyAsJSON((JSONObject)paramObject1, "body", "FACEBOOK_NON_JSON_RESULT");
      if ((paramObject1 instanceof JSONObject)) {
        return new GraphResponse(paramGraphRequest, paramHttpURLConnection, paramObject1.toString(), (JSONObject)paramObject1);
      }
      if ((paramObject1 instanceof JSONArray)) {
        return new GraphResponse(paramGraphRequest, paramHttpURLConnection, paramObject1.toString(), (JSONArray)paramObject1);
      }
      localObject = JSONObject.NULL;
    }
    if (localObject == JSONObject.NULL) {
      return new GraphResponse(paramGraphRequest, paramHttpURLConnection, localObject.toString(), (JSONObject)null);
    }
    throw new FacebookException("Got unexpected object type in response, class: " + localObject.getClass().getSimpleName());
  }
  
  private static List<GraphResponse> createResponsesFromObject(HttpURLConnection paramHttpURLConnection, List<GraphRequest> paramList, Object paramObject)
    throws FacebookException, JSONException
  {
    int j = paramList.size();
    ArrayList localArrayList = new ArrayList(j);
    Object localObject1 = paramObject;
    GraphRequest localGraphRequest;
    if (j == 1) {
      localGraphRequest = (GraphRequest)paramList.get(0);
    }
    for (;;)
    {
      try
      {
        JSONObject localJSONObject = new JSONObject();
        localJSONObject.put("body", paramObject);
        if (paramHttpURLConnection == null) {
          continue;
        }
        i = paramHttpURLConnection.getResponseCode();
        localJSONObject.put("code", i);
        localObject1 = new JSONArray();
        ((JSONArray)localObject1).put(localJSONObject);
      }
      catch (JSONException localJSONException1)
      {
        localArrayList.add(new GraphResponse(localGraphRequest, paramHttpURLConnection, new FacebookRequestError(paramHttpURLConnection, localJSONException1)));
        Object localObject2 = paramObject;
        continue;
      }
      catch (IOException localIOException)
      {
        localArrayList.add(new GraphResponse(localGraphRequest, paramHttpURLConnection, new FacebookRequestError(paramHttpURLConnection, localIOException)));
        Object localObject3 = paramObject;
        continue;
        localObject3 = (JSONArray)localObject3;
        int i = 0;
        if (i >= ((JSONArray)localObject3).length()) {
          break label327;
        }
        localGraphRequest = (GraphRequest)paramList.get(i);
        try
        {
          localArrayList.add(createResponseFromObject(localGraphRequest, paramHttpURLConnection, ((JSONArray)localObject3).get(i), paramObject));
          i += 1;
        }
        catch (JSONException localJSONException2)
        {
          localArrayList.add(new GraphResponse(localGraphRequest, paramHttpURLConnection, new FacebookRequestError(paramHttpURLConnection, localJSONException2)));
          continue;
        }
        catch (FacebookException localFacebookException)
        {
          localArrayList.add(new GraphResponse(localGraphRequest, paramHttpURLConnection, new FacebookRequestError(paramHttpURLConnection, localFacebookException)));
          continue;
        }
      }
      if (((localObject1 instanceof JSONArray)) && (((JSONArray)localObject1).length() == j)) {
        continue;
      }
      throw new FacebookException("Unexpected number of results");
      i = 200;
    }
    label327:
    return localArrayList;
  }
  
  static List<GraphResponse> createResponsesFromStream(InputStream paramInputStream, HttpURLConnection paramHttpURLConnection, GraphRequestBatch paramGraphRequestBatch)
    throws FacebookException, JSONException, IOException
  {
    paramInputStream = Utility.readStreamToString(paramInputStream);
    Logger.log(LoggingBehavior.INCLUDE_RAW_RESPONSES, "Response", "Response (raw)\n  Size: %d\n  Response:\n%s\n", new Object[] { Integer.valueOf(paramInputStream.length()), paramInputStream });
    return createResponsesFromString(paramInputStream, paramHttpURLConnection, paramGraphRequestBatch);
  }
  
  static List<GraphResponse> createResponsesFromString(String paramString, HttpURLConnection paramHttpURLConnection, GraphRequestBatch paramGraphRequestBatch)
    throws FacebookException, JSONException, IOException
  {
    paramHttpURLConnection = createResponsesFromObject(paramHttpURLConnection, paramGraphRequestBatch, new JSONTokener(paramString).nextValue());
    Logger.log(LoggingBehavior.REQUESTS, "Response", "Response\n  Id: %s\n  Size: %d\n  Responses:\n%s\n", new Object[] { paramGraphRequestBatch.getId(), Integer.valueOf(paramString.length()), paramHttpURLConnection });
    return paramHttpURLConnection;
  }
  
  /* Error */
  static List<GraphResponse> fromHttpConnection(HttpURLConnection paramHttpURLConnection, GraphRequestBatch paramGraphRequestBatch)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 8
    //   3: aconst_null
    //   4: astore 9
    //   6: aconst_null
    //   7: astore 10
    //   9: aconst_null
    //   10: astore 11
    //   12: aconst_null
    //   13: astore_2
    //   14: aload_2
    //   15: astore 4
    //   17: aload 8
    //   19: astore 5
    //   21: aload 9
    //   23: astore 6
    //   25: aload 10
    //   27: astore 7
    //   29: aload 11
    //   31: astore_3
    //   32: aload_0
    //   33: invokevirtual 175	java/net/HttpURLConnection:getResponseCode	()I
    //   36: sipush 400
    //   39: if_icmplt +55 -> 94
    //   42: aload_2
    //   43: astore 4
    //   45: aload 8
    //   47: astore 5
    //   49: aload 9
    //   51: astore 6
    //   53: aload 10
    //   55: astore 7
    //   57: aload 11
    //   59: astore_3
    //   60: aload_0
    //   61: invokevirtual 253	java/net/HttpURLConnection:getErrorStream	()Ljava/io/InputStream;
    //   64: astore_2
    //   65: aload_2
    //   66: astore 4
    //   68: aload_2
    //   69: astore 5
    //   71: aload_2
    //   72: astore 6
    //   74: aload_2
    //   75: astore 7
    //   77: aload_2
    //   78: astore_3
    //   79: aload_2
    //   80: aload_0
    //   81: aload_1
    //   82: invokestatic 255	com/facebook/GraphResponse:createResponsesFromStream	(Ljava/io/InputStream;Ljava/net/HttpURLConnection;Lcom/facebook/GraphRequestBatch;)Ljava/util/List;
    //   85: astore 8
    //   87: aload_2
    //   88: invokestatic 259	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   91: aload 8
    //   93: areturn
    //   94: aload_2
    //   95: astore 4
    //   97: aload 8
    //   99: astore 5
    //   101: aload 9
    //   103: astore 6
    //   105: aload 10
    //   107: astore 7
    //   109: aload 11
    //   111: astore_3
    //   112: aload_0
    //   113: invokevirtual 262	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   116: astore_2
    //   117: goto -52 -> 65
    //   120: astore_2
    //   121: aload 4
    //   123: astore_3
    //   124: getstatic 237	com/facebook/LoggingBehavior:REQUESTS	Lcom/facebook/LoggingBehavior;
    //   127: ldc 20
    //   129: ldc_w 264
    //   132: iconst_1
    //   133: anewarray 4	java/lang/Object
    //   136: dup
    //   137: iconst_0
    //   138: aload_2
    //   139: aastore
    //   140: invokestatic 220	com/facebook/internal/Logger:log	(Lcom/facebook/LoggingBehavior;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   143: aload 4
    //   145: astore_3
    //   146: aload_1
    //   147: aload_0
    //   148: aload_2
    //   149: invokestatic 266	com/facebook/GraphResponse:constructErrorResponses	(Ljava/util/List;Ljava/net/HttpURLConnection;Lcom/facebook/FacebookException;)Ljava/util/List;
    //   152: astore_0
    //   153: aload 4
    //   155: invokestatic 259	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   158: aload_0
    //   159: areturn
    //   160: astore_2
    //   161: aload 5
    //   163: astore_3
    //   164: getstatic 237	com/facebook/LoggingBehavior:REQUESTS	Lcom/facebook/LoggingBehavior;
    //   167: ldc 20
    //   169: ldc_w 264
    //   172: iconst_1
    //   173: anewarray 4	java/lang/Object
    //   176: dup
    //   177: iconst_0
    //   178: aload_2
    //   179: aastore
    //   180: invokestatic 220	com/facebook/internal/Logger:log	(Lcom/facebook/LoggingBehavior;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   183: aload 5
    //   185: astore_3
    //   186: aload_1
    //   187: aload_0
    //   188: new 138	com/facebook/FacebookException
    //   191: dup
    //   192: aload_2
    //   193: invokespecial 269	com/facebook/FacebookException:<init>	(Ljava/lang/Throwable;)V
    //   196: invokestatic 266	com/facebook/GraphResponse:constructErrorResponses	(Ljava/util/List;Ljava/net/HttpURLConnection;Lcom/facebook/FacebookException;)Ljava/util/List;
    //   199: astore_0
    //   200: aload 5
    //   202: invokestatic 259	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   205: aload_0
    //   206: areturn
    //   207: astore_2
    //   208: aload 6
    //   210: astore_3
    //   211: getstatic 237	com/facebook/LoggingBehavior:REQUESTS	Lcom/facebook/LoggingBehavior;
    //   214: ldc 20
    //   216: ldc_w 264
    //   219: iconst_1
    //   220: anewarray 4	java/lang/Object
    //   223: dup
    //   224: iconst_0
    //   225: aload_2
    //   226: aastore
    //   227: invokestatic 220	com/facebook/internal/Logger:log	(Lcom/facebook/LoggingBehavior;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   230: aload 6
    //   232: astore_3
    //   233: aload_1
    //   234: aload_0
    //   235: new 138	com/facebook/FacebookException
    //   238: dup
    //   239: aload_2
    //   240: invokespecial 269	com/facebook/FacebookException:<init>	(Ljava/lang/Throwable;)V
    //   243: invokestatic 266	com/facebook/GraphResponse:constructErrorResponses	(Ljava/util/List;Ljava/net/HttpURLConnection;Lcom/facebook/FacebookException;)Ljava/util/List;
    //   246: astore_0
    //   247: aload 6
    //   249: invokestatic 259	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   252: aload_0
    //   253: areturn
    //   254: astore_2
    //   255: aload 7
    //   257: astore_3
    //   258: getstatic 237	com/facebook/LoggingBehavior:REQUESTS	Lcom/facebook/LoggingBehavior;
    //   261: ldc 20
    //   263: ldc_w 264
    //   266: iconst_1
    //   267: anewarray 4	java/lang/Object
    //   270: dup
    //   271: iconst_0
    //   272: aload_2
    //   273: aastore
    //   274: invokestatic 220	com/facebook/internal/Logger:log	(Lcom/facebook/LoggingBehavior;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   277: aload 7
    //   279: astore_3
    //   280: aload_1
    //   281: aload_0
    //   282: new 138	com/facebook/FacebookException
    //   285: dup
    //   286: aload_2
    //   287: invokespecial 269	com/facebook/FacebookException:<init>	(Ljava/lang/Throwable;)V
    //   290: invokestatic 266	com/facebook/GraphResponse:constructErrorResponses	(Ljava/util/List;Ljava/net/HttpURLConnection;Lcom/facebook/FacebookException;)Ljava/util/List;
    //   293: astore_0
    //   294: aload 7
    //   296: invokestatic 259	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   299: aload_0
    //   300: areturn
    //   301: astore_0
    //   302: aload_3
    //   303: invokestatic 259	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   306: aload_0
    //   307: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	308	0	paramHttpURLConnection	HttpURLConnection
    //   0	308	1	paramGraphRequestBatch	GraphRequestBatch
    //   13	104	2	localInputStream1	InputStream
    //   120	29	2	localFacebookException	FacebookException
    //   160	33	2	localJSONException	JSONException
    //   207	33	2	localIOException	IOException
    //   254	33	2	localSecurityException	SecurityException
    //   31	272	3	localObject1	Object
    //   15	139	4	localInputStream2	InputStream
    //   19	182	5	localObject2	Object
    //   23	225	6	localObject3	Object
    //   27	268	7	localObject4	Object
    //   1	97	8	localList	List
    //   4	98	9	localObject5	Object
    //   7	99	10	localObject6	Object
    //   10	100	11	localObject7	Object
    // Exception table:
    //   from	to	target	type
    //   32	42	120	com/facebook/FacebookException
    //   60	65	120	com/facebook/FacebookException
    //   79	87	120	com/facebook/FacebookException
    //   112	117	120	com/facebook/FacebookException
    //   32	42	160	org/json/JSONException
    //   60	65	160	org/json/JSONException
    //   79	87	160	org/json/JSONException
    //   112	117	160	org/json/JSONException
    //   32	42	207	java/io/IOException
    //   60	65	207	java/io/IOException
    //   79	87	207	java/io/IOException
    //   112	117	207	java/io/IOException
    //   32	42	254	java/lang/SecurityException
    //   60	65	254	java/lang/SecurityException
    //   79	87	254	java/lang/SecurityException
    //   112	117	254	java/lang/SecurityException
    //   32	42	301	finally
    //   60	65	301	finally
    //   79	87	301	finally
    //   112	117	301	finally
    //   124	143	301	finally
    //   146	153	301	finally
    //   164	183	301	finally
    //   186	200	301	finally
    //   211	230	301	finally
    //   233	247	301	finally
    //   258	277	301	finally
    //   280	294	301	finally
  }
  
  public final HttpURLConnection getConnection()
  {
    return this.connection;
  }
  
  public final FacebookRequestError getError()
  {
    return this.error;
  }
  
  public final JSONArray getJSONArray()
  {
    return this.graphObjectArray;
  }
  
  public final JSONObject getJSONObject()
  {
    return this.graphObject;
  }
  
  public String getRawResponse()
  {
    return this.rawResponse;
  }
  
  public GraphRequest getRequest()
  {
    return this.request;
  }
  
  public GraphRequest getRequestForPagedResults(PagingDirection paramPagingDirection)
  {
    Object localObject2 = null;
    Object localObject1 = localObject2;
    JSONObject localJSONObject;
    if (this.graphObject != null)
    {
      localJSONObject = this.graphObject.optJSONObject("paging");
      localObject1 = localObject2;
      if (localJSONObject != null) {
        if (paramPagingDirection != PagingDirection.NEXT) {
          break label55;
        }
      }
    }
    label55:
    for (localObject1 = localJSONObject.optString("next"); Utility.isNullOrEmpty((String)localObject1); localObject1 = localJSONObject.optString("previous")) {
      return null;
    }
    if ((localObject1 != null) && (((String)localObject1).equals(this.request.getUrlForSingleRequest()))) {
      return null;
    }
    try
    {
      paramPagingDirection = new GraphRequest(this.request.getAccessToken(), new URL((String)localObject1));
      return paramPagingDirection;
    }
    catch (MalformedURLException paramPagingDirection) {}
    return null;
  }
  
  public String toString()
  {
    for (;;)
    {
      try
      {
        localObject = Locale.US;
        if (this.connection == null) {
          continue;
        }
        i = this.connection.getResponseCode();
        localObject = String.format((Locale)localObject, "%d", new Object[] { Integer.valueOf(i) });
      }
      catch (IOException localIOException)
      {
        Object localObject;
        int i;
        String str = "unknown";
        continue;
      }
      return "{Response: " + " responseCode: " + (String)localObject + ", graphObject: " + this.graphObject + ", error: " + this.error + "}";
      i = 200;
    }
  }
  
  public static enum PagingDirection
  {
    NEXT,  PREVIOUS;
    
    private PagingDirection() {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/GraphResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */