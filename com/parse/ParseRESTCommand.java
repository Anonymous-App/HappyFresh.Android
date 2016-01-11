package com.parse;

import bolts.Task;
import com.parse.http.ParseHttpBody;
import com.parse.http.ParseHttpRequest;
import com.parse.http.ParseHttpRequest.Builder;
import com.parse.http.ParseHttpRequest.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

class ParseRESTCommand
  extends ParseRequest<JSONObject>
{
  static final String HEADER_APPLICATION_ID = "X-Parse-Application-Id";
  static final String HEADER_APP_BUILD_VERSION = "X-Parse-App-Build-Version";
  static final String HEADER_APP_DISPLAY_VERSION = "X-Parse-App-Display-Version";
  static final String HEADER_CLIENT_KEY = "X-Parse-Client-Key";
  static final String HEADER_CLIENT_VERSION = "X-Parse-Client-Version";
  static final String HEADER_INSTALLATION_ID = "X-Parse-Installation-Id";
  private static final String HEADER_MASTER_KEY = "X-Parse-Master-Key";
  static final String HEADER_OS_VERSION = "X-Parse-OS-Version";
  private static final String HEADER_SESSION_TOKEN = "X-Parse-Session-Token";
  private static final String PARAMETER_METHOD_OVERRIDE = "_method";
  static final String USER_AGENT = "User-Agent";
  String httpPath;
  private String installationId;
  final JSONObject jsonParameters;
  private String localId;
  public String masterKey;
  private String operationSetUUID;
  private final String sessionToken;
  
  ParseRESTCommand(Init<?> paramInit)
  {
    super(paramInit.method, createUrl(paramInit.httpPath));
    this.sessionToken = paramInit.sessionToken;
    this.installationId = paramInit.installationId;
    this.masterKey = paramInit.masterKey;
    this.httpPath = paramInit.httpPath;
    this.jsonParameters = paramInit.jsonParameters;
    this.operationSetUUID = paramInit.operationSetUUID;
    this.localId = paramInit.localId;
  }
  
  public ParseRESTCommand(String paramString1, ParseHttpRequest.Method paramMethod, Map<String, ?> paramMap, String paramString2) {}
  
  protected ParseRESTCommand(String paramString1, ParseHttpRequest.Method paramMethod, JSONObject paramJSONObject, String paramString2)
  {
    this(paramString1, paramMethod, paramJSONObject, null, paramString2);
  }
  
  private ParseRESTCommand(String paramString1, ParseHttpRequest.Method paramMethod, JSONObject paramJSONObject, String paramString2, String paramString3)
  {
    super(paramMethod, createUrl(paramString1));
    this.httpPath = paramString1;
    this.jsonParameters = paramJSONObject;
    this.localId = paramString2;
    this.sessionToken = paramString3;
  }
  
  private static void addToStringer(JSONStringer paramJSONStringer, Object paramObject)
    throws JSONException
  {
    if ((paramObject instanceof JSONObject))
    {
      paramJSONStringer.object();
      paramObject = (JSONObject)paramObject;
      Iterator localIterator = ((JSONObject)paramObject).keys();
      Object localObject = new ArrayList();
      while (localIterator.hasNext()) {
        ((ArrayList)localObject).add(localIterator.next());
      }
      Collections.sort((List)localObject);
      localIterator = ((ArrayList)localObject).iterator();
      while (localIterator.hasNext())
      {
        localObject = (String)localIterator.next();
        paramJSONStringer.key((String)localObject);
        addToStringer(paramJSONStringer, ((JSONObject)paramObject).opt((String)localObject));
      }
      paramJSONStringer.endObject();
      return;
    }
    if ((paramObject instanceof JSONArray))
    {
      paramObject = (JSONArray)paramObject;
      paramJSONStringer.array();
      int i = 0;
      while (i < ((JSONArray)paramObject).length())
      {
        addToStringer(paramJSONStringer, ((JSONArray)paramObject).get(i));
        i += 1;
      }
      paramJSONStringer.endArray();
      return;
    }
    paramJSONStringer.value(paramObject);
  }
  
  private static String createUrl(String paramString)
  {
    return String.format("%s/1/%s", new Object[] { ParseObject.server, paramString });
  }
  
  public static ParseRESTCommand fromJSONObject(JSONObject paramJSONObject)
  {
    String str1 = paramJSONObject.optString("httpPath");
    ParseHttpRequest.Method localMethod = ParseHttpRequest.Method.fromString(paramJSONObject.optString("httpMethod"));
    String str2 = paramJSONObject.optString("sessionToken", null);
    String str3 = paramJSONObject.optString("localId", null);
    return new ParseRESTCommand(str1, localMethod, paramJSONObject.optJSONObject("parameters"), str3, str2);
  }
  
  private static LocalIdManager getLocalIdManager()
  {
    return ParseCorePlugins.getInstance().getLocalIdManager();
  }
  
  protected static void getLocalPointersIn(Object paramObject, ArrayList<JSONObject> paramArrayList)
    throws JSONException
  {
    JSONObject localJSONObject;
    if ((paramObject instanceof JSONObject))
    {
      localJSONObject = (JSONObject)paramObject;
      if (("Pointer".equals(localJSONObject.opt("__type"))) && (localJSONObject.has("localId"))) {
        paramArrayList.add((JSONObject)paramObject);
      }
    }
    for (;;)
    {
      return;
      Iterator localIterator = localJSONObject.keys();
      while (localIterator.hasNext()) {
        getLocalPointersIn(localJSONObject.get((String)localIterator.next()), paramArrayList);
      }
      if ((paramObject instanceof JSONArray))
      {
        paramObject = (JSONArray)paramObject;
        int i = 0;
        while (i < ((JSONArray)paramObject).length())
        {
          getLocalPointersIn(((JSONArray)paramObject).get(i), paramArrayList);
          i += 1;
        }
      }
    }
  }
  
  static boolean isValidCommandJSONObject(JSONObject paramJSONObject)
  {
    return paramJSONObject.has("httpPath");
  }
  
  static boolean isValidOldFormatCommandJSONObject(JSONObject paramJSONObject)
  {
    return paramJSONObject.has("op");
  }
  
  private void maybeChangeServerOperation()
    throws JSONException
  {
    if (this.localId != null)
    {
      String str = getLocalIdManager().getObjectId(this.localId);
      if (str != null)
      {
        this.localId = null;
        this.httpPath += String.format("/%s", new Object[] { str });
        this.url = createUrl(this.httpPath);
        if ((this.httpPath.startsWith("classes")) && (this.method == ParseHttpRequest.Method.POST)) {
          this.method = ParseHttpRequest.Method.PUT;
        }
      }
    }
  }
  
  static String toDeterministicString(Object paramObject)
    throws JSONException
  {
    JSONStringer localJSONStringer = new JSONStringer();
    addToStringer(localJSONStringer, paramObject);
    return localJSONStringer.toString();
  }
  
  protected void addAdditionalHeaders(ParseHttpRequest.Builder paramBuilder)
  {
    if (this.installationId != null) {
      paramBuilder.addHeader("X-Parse-Installation-Id", this.installationId);
    }
    if (this.sessionToken != null) {
      paramBuilder.addHeader("X-Parse-Session-Token", this.sessionToken);
    }
    if (this.masterKey != null) {
      paramBuilder.addHeader("X-Parse-Master-Key", this.masterKey);
    }
  }
  
  void enableRetrying()
  {
    setMaxRetries(4);
  }
  
  public Task<JSONObject> executeAsync(ParseHttpClient paramParseHttpClient, ProgressCallback paramProgressCallback1, ProgressCallback paramProgressCallback2, Task<Void> paramTask)
  {
    resolveLocalIds();
    return super.executeAsync(paramParseHttpClient, paramProgressCallback1, paramProgressCallback2, paramTask);
  }
  
  public String getCacheKey()
  {
    if (this.jsonParameters != null) {}
    for (;;)
    {
      try
      {
        String str1 = toDeterministicString(this.jsonParameters);
        String str3 = str1;
        if (this.sessionToken != null) {
          str3 = str1 + this.sessionToken;
        }
        return String.format("ParseRESTCommand.%s.%s.%s", new Object[] { this.method.toString(), ParseDigestUtils.md5(this.httpPath), ParseDigestUtils.md5(str3) });
      }
      catch (JSONException localJSONException)
      {
        throw new RuntimeException(localJSONException.getMessage());
      }
      String str2 = "";
    }
  }
  
  public String getLocalId()
  {
    return this.localId;
  }
  
  public String getOperationSetUUID()
  {
    return this.operationSetUUID;
  }
  
  public String getSessionToken()
  {
    return this.sessionToken;
  }
  
  protected ParseHttpBody newBody(ProgressCallback paramProgressCallback)
  {
    if (this.jsonParameters == null) {
      throw new IllegalArgumentException(String.format("Trying to execute a %s command without body parameters.", new Object[] { this.method.toString() }));
    }
    try
    {
      paramProgressCallback = this.jsonParameters;
      if ((this.method == ParseHttpRequest.Method.GET) || (this.method == ParseHttpRequest.Method.DELETE))
      {
        paramProgressCallback = new JSONObject(this.jsonParameters.toString());
        paramProgressCallback.put("_method", this.method.toString());
      }
      paramProgressCallback = new ParseByteArrayHttpBody(paramProgressCallback.toString(), "application/json");
      return paramProgressCallback;
    }
    catch (Exception paramProgressCallback)
    {
      throw new RuntimeException(paramProgressCallback.getMessage());
    }
  }
  
  protected ParseHttpRequest newRequest(ParseHttpRequest.Method paramMethod, String paramString, ProgressCallback paramProgressCallback)
  {
    if ((this.jsonParameters != null) && (paramMethod != ParseHttpRequest.Method.POST) && (paramMethod != ParseHttpRequest.Method.PUT)) {}
    for (paramMethod = super.newRequest(ParseHttpRequest.Method.POST, paramString, paramProgressCallback);; paramMethod = super.newRequest(paramMethod, paramString, paramProgressCallback))
    {
      paramMethod = new ParseHttpRequest.Builder(paramMethod);
      addAdditionalHeaders(paramMethod);
      return paramMethod.build();
    }
  }
  
  /* Error */
  protected Task<JSONObject> onResponseAsync(com.parse.http.ParseHttpResponse paramParseHttpResponse, ProgressCallback paramProgressCallback)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore_2
    //   5: aload_1
    //   6: invokevirtual 411	com/parse/http/ParseHttpResponse:getContent	()Ljava/io/InputStream;
    //   9: astore 5
    //   11: aload 5
    //   13: astore_2
    //   14: aload 5
    //   16: astore 4
    //   18: new 168	java/lang/String
    //   21: dup
    //   22: aload 5
    //   24: invokestatic 417	com/parse/ParseIOUtils:toByteArray	(Ljava/io/InputStream;)[B
    //   27: invokespecial 420	java/lang/String:<init>	([B)V
    //   30: astore 6
    //   32: aload 5
    //   34: invokestatic 424	com/parse/ParseIOUtils:closeQuietly	(Ljava/io/InputStream;)V
    //   37: aload_1
    //   38: invokevirtual 427	com/parse/http/ParseHttpResponse:getStatusCode	()I
    //   41: istore_3
    //   42: iload_3
    //   43: sipush 200
    //   46: if_icmplt +130 -> 176
    //   49: iload_3
    //   50: sipush 600
    //   53: if_icmpge +123 -> 176
    //   56: new 117	org/json/JSONObject
    //   59: dup
    //   60: aload 6
    //   62: invokespecial 376	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   65: astore_1
    //   66: iload_3
    //   67: sipush 400
    //   70: if_icmplt +57 -> 127
    //   73: iload_3
    //   74: sipush 500
    //   77: if_icmpge +50 -> 127
    //   80: aload_0
    //   81: aload_1
    //   82: ldc_w 429
    //   85: invokevirtual 433	org/json/JSONObject:optInt	(Ljava/lang/String;)I
    //   88: aload_1
    //   89: ldc_w 435
    //   92: invokevirtual 220	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   95: invokevirtual 439	com/parse/ParseRESTCommand:newPermanentException	(ILjava/lang/String;)Lcom/parse/ParseException;
    //   98: invokestatic 445	bolts/Task:forError	(Ljava/lang/Exception;)Lbolts/Task;
    //   101: astore_1
    //   102: aload_1
    //   103: areturn
    //   104: astore_1
    //   105: aload_2
    //   106: astore 4
    //   108: aload_1
    //   109: invokestatic 445	bolts/Task:forError	(Ljava/lang/Exception;)Lbolts/Task;
    //   112: astore_1
    //   113: aload_2
    //   114: invokestatic 424	com/parse/ParseIOUtils:closeQuietly	(Ljava/io/InputStream;)V
    //   117: aload_1
    //   118: areturn
    //   119: astore_1
    //   120: aload 4
    //   122: invokestatic 424	com/parse/ParseIOUtils:closeQuietly	(Ljava/io/InputStream;)V
    //   125: aload_1
    //   126: athrow
    //   127: iload_3
    //   128: sipush 500
    //   131: if_icmplt +25 -> 156
    //   134: aload_0
    //   135: aload_1
    //   136: ldc_w 429
    //   139: invokevirtual 433	org/json/JSONObject:optInt	(Ljava/lang/String;)I
    //   142: aload_1
    //   143: ldc_w 435
    //   146: invokevirtual 220	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   149: invokevirtual 448	com/parse/ParseRESTCommand:newTemporaryException	(ILjava/lang/String;)Lcom/parse/ParseException;
    //   152: invokestatic 445	bolts/Task:forError	(Ljava/lang/Exception;)Lbolts/Task;
    //   155: areturn
    //   156: aload_1
    //   157: invokestatic 452	bolts/Task:forResult	(Ljava/lang/Object;)Lbolts/Task;
    //   160: astore_1
    //   161: aload_1
    //   162: areturn
    //   163: astore_1
    //   164: aload_0
    //   165: ldc_w 454
    //   168: aload_1
    //   169: invokevirtual 457	com/parse/ParseRESTCommand:newTemporaryException	(Ljava/lang/String;Ljava/lang/Throwable;)Lcom/parse/ParseException;
    //   172: invokestatic 445	bolts/Task:forError	(Ljava/lang/Exception;)Lbolts/Task;
    //   175: areturn
    //   176: aload_0
    //   177: iconst_m1
    //   178: aload 6
    //   180: invokevirtual 439	com/parse/ParseRESTCommand:newPermanentException	(ILjava/lang/String;)Lcom/parse/ParseException;
    //   183: invokestatic 445	bolts/Task:forError	(Ljava/lang/Exception;)Lbolts/Task;
    //   186: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	187	0	this	ParseRESTCommand
    //   0	187	1	paramParseHttpResponse	com.parse.http.ParseHttpResponse
    //   0	187	2	paramProgressCallback	ProgressCallback
    //   41	91	3	i	int
    //   1	120	4	localObject	Object
    //   9	24	5	localInputStream	java.io.InputStream
    //   30	149	6	str	String
    // Exception table:
    //   from	to	target	type
    //   5	11	104	java/io/IOException
    //   18	32	104	java/io/IOException
    //   5	11	119	finally
    //   18	32	119	finally
    //   108	113	119	finally
    //   56	66	163	org/json/JSONException
    //   80	102	163	org/json/JSONException
    //   134	156	163	org/json/JSONException
    //   156	161	163	org/json/JSONException
  }
  
  public void releaseLocalIds()
  {
    if (this.localId != null) {
      getLocalIdManager().releaseLocalIdOnDisk(this.localId);
    }
    try
    {
      Object localObject = new ArrayList();
      getLocalPointersIn(this.jsonParameters, (ArrayList)localObject);
      localObject = ((ArrayList)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        String str = (String)((JSONObject)((Iterator)localObject).next()).get("localId");
        getLocalIdManager().releaseLocalIdOnDisk(str);
      }
      return;
    }
    catch (JSONException localJSONException) {}
  }
  
  public void resolveLocalIds()
  {
    try
    {
      Object localObject = new ArrayList();
      getLocalPointersIn(this.jsonParameters, (ArrayList)localObject);
      localObject = ((ArrayList)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        JSONObject localJSONObject = (JSONObject)((Iterator)localObject).next();
        String str = (String)localJSONObject.get("localId");
        str = getLocalIdManager().getObjectId(str);
        if (str == null) {
          throw new IllegalStateException("Tried to serialize a command referencing a new, unsaved object.");
        }
        localJSONObject.put("objectId", str);
        localJSONObject.remove("localId");
      }
      maybeChangeServerOperation();
      return;
    }
    catch (JSONException localJSONException) {}
  }
  
  public void retainLocalIds()
  {
    if (this.localId != null) {
      getLocalIdManager().retainLocalIdOnDisk(this.localId);
    }
    try
    {
      Object localObject = new ArrayList();
      getLocalPointersIn(this.jsonParameters, (ArrayList)localObject);
      localObject = ((ArrayList)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        String str = (String)((JSONObject)((Iterator)localObject).next()).get("localId");
        getLocalIdManager().retainLocalIdOnDisk(str);
      }
      return;
    }
    catch (JSONException localJSONException) {}
  }
  
  public void setLocalId(String paramString)
  {
    this.localId = paramString;
  }
  
  void setOperationSetUUID(String paramString)
  {
    this.operationSetUUID = paramString;
  }
  
  public JSONObject toJSONObject()
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      if (this.httpPath != null) {
        localJSONObject.put("httpPath", this.httpPath);
      }
      localJSONObject.put("httpMethod", this.method.toString());
      if (this.jsonParameters != null) {
        localJSONObject.put("parameters", this.jsonParameters);
      }
      if (this.sessionToken != null) {
        localJSONObject.put("sessionToken", this.sessionToken);
      }
      if (this.localId != null) {
        localJSONObject.put("localId", this.localId);
      }
      return localJSONObject;
    }
    catch (JSONException localJSONException)
    {
      throw new RuntimeException(localJSONException.getMessage());
    }
  }
  
  public static class Builder
    extends ParseRESTCommand.Init<Builder>
  {
    public ParseRESTCommand build()
    {
      return new ParseRESTCommand(this);
    }
    
    Builder self()
    {
      return this;
    }
  }
  
  static abstract class Init<T extends Init<T>>
  {
    private String httpPath;
    private String installationId;
    private JSONObject jsonParameters;
    private String localId;
    public String masterKey;
    private ParseHttpRequest.Method method = ParseHttpRequest.Method.GET;
    private String operationSetUUID;
    private String sessionToken;
    
    public T httpPath(String paramString)
    {
      this.httpPath = paramString;
      return self();
    }
    
    public T installationId(String paramString)
    {
      this.installationId = paramString;
      return self();
    }
    
    public T jsonParameters(JSONObject paramJSONObject)
    {
      this.jsonParameters = paramJSONObject;
      return self();
    }
    
    public T localId(String paramString)
    {
      this.localId = paramString;
      return self();
    }
    
    public T masterKey(String paramString)
    {
      this.masterKey = paramString;
      return self();
    }
    
    public T method(ParseHttpRequest.Method paramMethod)
    {
      this.method = paramMethod;
      return self();
    }
    
    public T operationSetUUID(String paramString)
    {
      this.operationSetUUID = paramString;
      return self();
    }
    
    abstract T self();
    
    public T sessionToken(String paramString)
    {
      this.sessionToken = paramString;
      return self();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseRESTCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */