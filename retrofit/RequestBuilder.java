package retrofit;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import retrofit.client.Request;
import retrofit.converter.Converter;
import retrofit.http.Body;
import retrofit.http.EncodedPath;
import retrofit.http.EncodedQuery;
import retrofit.http.EncodedQueryMap;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import retrofit.mime.FormUrlEncodedTypedOutput;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedOutput;
import retrofit.mime.TypedString;

final class RequestBuilder
  implements RequestInterceptor.RequestFacade
{
  private final String apiUrl;
  private TypedOutput body;
  private String contentTypeHeader;
  private final Converter converter;
  private final FormUrlEncodedTypedOutput formBody;
  private List<retrofit.client.Header> headers;
  private final boolean isObservable;
  private final boolean isSynchronous;
  private final MultipartTypedOutput multipartBody;
  private final Annotation[] paramAnnotations;
  private StringBuilder queryParams;
  private String relativeUrl;
  private final String requestMethod;
  
  RequestBuilder(String paramString, RestMethodInfo paramRestMethodInfo, Converter paramConverter)
  {
    this.apiUrl = paramString;
    this.converter = paramConverter;
    this.paramAnnotations = paramRestMethodInfo.requestParamAnnotations;
    this.requestMethod = paramRestMethodInfo.requestMethod;
    this.isSynchronous = paramRestMethodInfo.isSynchronous;
    this.isObservable = paramRestMethodInfo.isObservable;
    if (paramRestMethodInfo.headers != null) {
      this.headers = new ArrayList(paramRestMethodInfo.headers);
    }
    this.contentTypeHeader = paramRestMethodInfo.contentTypeHeader;
    this.relativeUrl = paramRestMethodInfo.requestUrl;
    paramString = paramRestMethodInfo.requestQuery;
    if (paramString != null) {
      this.queryParams = new StringBuilder().append('?').append(paramString);
    }
    switch (paramRestMethodInfo.requestType)
    {
    default: 
      throw new IllegalArgumentException("Unknown request type: " + paramRestMethodInfo.requestType);
    case ???: 
      this.formBody = new FormUrlEncodedTypedOutput();
      this.multipartBody = null;
      this.body = this.formBody;
      return;
    case ???: 
      this.formBody = null;
      this.multipartBody = new MultipartTypedOutput();
      this.body = this.multipartBody;
      return;
    }
    this.formBody = null;
    this.multipartBody = null;
  }
  
  private void addPathParam(String paramString1, String paramString2, boolean paramBoolean)
  {
    if (paramString1 == null) {
      throw new IllegalArgumentException("Path replacement name must not be null.");
    }
    if (paramString2 == null) {
      throw new IllegalArgumentException("Path replacement \"" + paramString1 + "\" value must not be null.");
    }
    if (paramBoolean) {}
    try
    {
      String str = URLEncoder.encode(String.valueOf(paramString2), "UTF-8").replace("+", "%20");
      this.relativeUrl = this.relativeUrl.replace("{" + paramString1 + "}", str);
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException("Unable to convert path parameter \"" + paramString1 + "\" value to UTF-8:" + paramString2, localUnsupportedEncodingException);
    }
    this.relativeUrl = this.relativeUrl.replace("{" + paramString1 + "}", String.valueOf(paramString2));
  }
  
  private void addQueryParam(String paramString, Object paramObject, boolean paramBoolean1, boolean paramBoolean2)
  {
    Object localObject;
    if ((paramObject instanceof Iterable))
    {
      paramObject = ((Iterable)paramObject).iterator();
      while (((Iterator)paramObject).hasNext())
      {
        localObject = ((Iterator)paramObject).next();
        if (localObject != null) {
          addQueryParam(paramString, localObject.toString(), paramBoolean1, paramBoolean2);
        }
      }
    }
    if (paramObject.getClass().isArray())
    {
      int i = 0;
      int j = Array.getLength(paramObject);
      while (i < j)
      {
        localObject = Array.get(paramObject, i);
        if (localObject != null) {
          addQueryParam(paramString, localObject.toString(), paramBoolean1, paramBoolean2);
        }
        i += 1;
      }
    }
    addQueryParam(paramString, paramObject.toString(), paramBoolean1, paramBoolean2);
  }
  
  /* Error */
  private void addQueryParam(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +13 -> 14
    //   4: new 106	java/lang/IllegalArgumentException
    //   7: dup
    //   8: ldc -37
    //   10: invokespecial 118	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   13: athrow
    //   14: aload_2
    //   15: ifnonnull +35 -> 50
    //   18: new 106	java/lang/IllegalArgumentException
    //   21: dup
    //   22: new 80	java/lang/StringBuilder
    //   25: dup
    //   26: invokespecial 81	java/lang/StringBuilder:<init>	()V
    //   29: ldc -35
    //   31: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   34: aload_1
    //   35: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   38: ldc -115
    //   40: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   43: invokevirtual 115	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   46: invokespecial 118	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   49: athrow
    //   50: aload_1
    //   51: astore 7
    //   53: aload_2
    //   54: astore 8
    //   56: aload_0
    //   57: getfield 90	retrofit/RequestBuilder:queryParams	Ljava/lang/StringBuilder;
    //   60: astore 6
    //   62: aload 6
    //   64: astore 9
    //   66: aload 6
    //   68: ifnonnull +30 -> 98
    //   71: aload_1
    //   72: astore 7
    //   74: aload_2
    //   75: astore 8
    //   77: new 80	java/lang/StringBuilder
    //   80: dup
    //   81: invokespecial 81	java/lang/StringBuilder:<init>	()V
    //   84: astore 9
    //   86: aload_1
    //   87: astore 7
    //   89: aload_2
    //   90: astore 8
    //   92: aload_0
    //   93: aload 9
    //   95: putfield 90	retrofit/RequestBuilder:queryParams	Ljava/lang/StringBuilder;
    //   98: aload_1
    //   99: astore 7
    //   101: aload_2
    //   102: astore 8
    //   104: aload 9
    //   106: invokevirtual 224	java/lang/StringBuilder:length	()I
    //   109: ifle +88 -> 197
    //   112: bipush 38
    //   114: istore 5
    //   116: aload_1
    //   117: astore 7
    //   119: aload_2
    //   120: astore 8
    //   122: aload 9
    //   124: iload 5
    //   126: invokevirtual 85	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   129: pop
    //   130: aload_1
    //   131: astore 6
    //   133: iload_3
    //   134: ifeq +17 -> 151
    //   137: aload_1
    //   138: astore 7
    //   140: aload_2
    //   141: astore 8
    //   143: aload_1
    //   144: ldc -107
    //   146: invokestatic 155	java/net/URLEncoder:encode	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   149: astore 6
    //   151: aload_2
    //   152: astore_1
    //   153: iload 4
    //   155: ifeq +17 -> 172
    //   158: aload 6
    //   160: astore 7
    //   162: aload_2
    //   163: astore 8
    //   165: aload_2
    //   166: ldc -107
    //   168: invokestatic 155	java/net/URLEncoder:encode	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   171: astore_1
    //   172: aload 6
    //   174: astore 7
    //   176: aload_1
    //   177: astore 8
    //   179: aload 9
    //   181: aload 6
    //   183: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   186: bipush 61
    //   188: invokevirtual 85	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   191: aload_1
    //   192: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   195: pop
    //   196: return
    //   197: bipush 63
    //   199: istore 5
    //   201: goto -85 -> 116
    //   204: astore_1
    //   205: new 169	java/lang/RuntimeException
    //   208: dup
    //   209: new 80	java/lang/StringBuilder
    //   212: dup
    //   213: invokespecial 81	java/lang/StringBuilder:<init>	()V
    //   216: ldc -30
    //   218: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   221: aload 7
    //   223: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   226: ldc -28
    //   228: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   231: aload 8
    //   233: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   236: invokevirtual 115	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   239: aload_1
    //   240: invokespecial 176	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   243: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	244	0	this	RequestBuilder
    //   0	244	1	paramString1	String
    //   0	244	2	paramString2	String
    //   0	244	3	paramBoolean1	boolean
    //   0	244	4	paramBoolean2	boolean
    //   114	86	5	c	char
    //   60	122	6	localObject1	Object
    //   51	171	7	localObject2	Object
    //   54	178	8	str	String
    //   64	116	9	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   56	62	204	java/io/UnsupportedEncodingException
    //   77	86	204	java/io/UnsupportedEncodingException
    //   92	98	204	java/io/UnsupportedEncodingException
    //   104	112	204	java/io/UnsupportedEncodingException
    //   122	130	204	java/io/UnsupportedEncodingException
    //   143	151	204	java/io/UnsupportedEncodingException
    //   165	172	204	java/io/UnsupportedEncodingException
    //   179	196	204	java/io/UnsupportedEncodingException
  }
  
  private void addQueryParamMap(int paramInt, Map<?, ?> paramMap, boolean paramBoolean1, boolean paramBoolean2)
  {
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Object localObject2 = (Map.Entry)paramMap.next();
      Object localObject1 = ((Map.Entry)localObject2).getKey();
      if (localObject1 == null) {
        throw new IllegalArgumentException("Parameter #" + (paramInt + 1) + " query map contained null key.");
      }
      localObject2 = ((Map.Entry)localObject2).getValue();
      if (localObject2 != null) {
        addQueryParam(localObject1.toString(), localObject2.toString(), paramBoolean1, paramBoolean2);
      }
    }
  }
  
  public void addEncodedPathParam(String paramString1, String paramString2)
  {
    addPathParam(paramString1, paramString2, false);
  }
  
  public void addEncodedQueryParam(String paramString1, String paramString2)
  {
    addQueryParam(paramString1, paramString2, false, false);
  }
  
  public void addHeader(String paramString1, String paramString2)
  {
    if (paramString1 == null) {
      throw new IllegalArgumentException("Header name must not be null.");
    }
    if ("Content-Type".equalsIgnoreCase(paramString1))
    {
      this.contentTypeHeader = paramString2;
      return;
    }
    List localList = this.headers;
    Object localObject = localList;
    if (localList == null)
    {
      localObject = new ArrayList(2);
      this.headers = ((List)localObject);
    }
    ((List)localObject).add(new retrofit.client.Header(paramString1, paramString2));
  }
  
  public void addPathParam(String paramString1, String paramString2)
  {
    addPathParam(paramString1, paramString2, true);
  }
  
  public void addQueryParam(String paramString1, String paramString2)
  {
    addQueryParam(paramString1, paramString2, false, true);
  }
  
  Request build()
    throws UnsupportedEncodingException
  {
    if ((this.multipartBody != null) && (this.multipartBody.getPartCount() == 0)) {
      throw new IllegalStateException("Multipart requests must contain at least one part.");
    }
    Object localObject = this.apiUrl;
    StringBuilder localStringBuilder = new StringBuilder((String)localObject);
    if (((String)localObject).endsWith("/")) {
      localStringBuilder.deleteCharAt(localStringBuilder.length() - 1);
    }
    localStringBuilder.append(this.relativeUrl);
    localObject = this.queryParams;
    if (localObject != null) {
      localStringBuilder.append((CharSequence)localObject);
    }
    TypedOutput localTypedOutput = this.body;
    List localList2 = this.headers;
    localObject = localTypedOutput;
    List localList1 = localList2;
    if (this.contentTypeHeader != null)
    {
      if (localTypedOutput == null) {
        break label154;
      }
      localObject = new MimeOverridingTypedOutput(localTypedOutput, this.contentTypeHeader);
      localList1 = localList2;
    }
    for (;;)
    {
      return new Request(this.requestMethod, localStringBuilder.toString(), localList1, (TypedOutput)localObject);
      label154:
      localObject = new retrofit.client.Header("Content-Type", this.contentTypeHeader);
      if (localList2 == null)
      {
        localList1 = Collections.singletonList(localObject);
        localObject = localTypedOutput;
      }
      else
      {
        localList2.add(localObject);
        localObject = localTypedOutput;
        localList1 = localList2;
      }
    }
  }
  
  void setArguments(Object[] paramArrayOfObject)
  {
    if (paramArrayOfObject == null) {
      return;
    }
    int j = paramArrayOfObject.length;
    int i = j;
    if (!this.isSynchronous)
    {
      i = j;
      if (!this.isObservable) {
        i = j - 1;
      }
    }
    j = 0;
    label32:
    Object localObject1;
    Object localObject2;
    Object localObject3;
    if (j < i)
    {
      localObject1 = paramArrayOfObject[j];
      localObject2 = this.paramAnnotations[j];
      localObject3 = ((Annotation)localObject2).annotationType();
      if (localObject3 != Path.class) {
        break label147;
      }
      localObject2 = (Path)localObject2;
      localObject3 = ((Path)localObject2).value();
      if (localObject1 == null) {
        throw new IllegalArgumentException("Path parameter \"" + (String)localObject3 + "\" value must not be null.");
      }
      addPathParam((String)localObject3, localObject1.toString(), ((Path)localObject2).encode());
    }
    for (;;)
    {
      j += 1;
      break label32;
      break;
      label147:
      if (localObject3 == EncodedPath.class)
      {
        localObject2 = ((EncodedPath)localObject2).value();
        if (localObject1 == null) {
          throw new IllegalArgumentException("Path parameter \"" + (String)localObject2 + "\" value must not be null.");
        }
        addPathParam((String)localObject2, localObject1.toString(), false);
      }
      else if (localObject3 == Query.class)
      {
        if (localObject1 != null)
        {
          localObject2 = (Query)localObject2;
          addQueryParam(((Query)localObject2).value(), localObject1, ((Query)localObject2).encodeName(), ((Query)localObject2).encodeValue());
        }
      }
      else if (localObject3 == EncodedQuery.class)
      {
        if (localObject1 != null) {
          addQueryParam(((EncodedQuery)localObject2).value(), localObject1, false, false);
        }
      }
      else if (localObject3 == QueryMap.class)
      {
        if (localObject1 != null)
        {
          localObject2 = (QueryMap)localObject2;
          addQueryParamMap(j, (Map)localObject1, ((QueryMap)localObject2).encodeNames(), ((QueryMap)localObject2).encodeValues());
        }
      }
      else if (localObject3 == EncodedQueryMap.class)
      {
        if (localObject1 != null) {
          addQueryParamMap(j, (Map)localObject1, false, false);
        }
      }
      else if (localObject3 == retrofit.http.Header.class)
      {
        if (localObject1 != null) {
          addHeader(((retrofit.http.Header)localObject2).value(), localObject1.toString());
        }
      }
      else if (localObject3 == Field.class)
      {
        localObject2 = ((Field)localObject2).value();
        if (localObject1 != null) {
          if ((localObject1 instanceof Iterable))
          {
            localObject1 = ((Iterable)localObject1).iterator();
            while (((Iterator)localObject1).hasNext())
            {
              localObject3 = ((Iterator)localObject1).next();
              if (localObject3 != null) {
                this.formBody.addField((String)localObject2, localObject3.toString());
              }
            }
          }
          else if (localObject1.getClass().isArray())
          {
            int k = 0;
            int m = Array.getLength(localObject1);
            while (k < m)
            {
              localObject3 = Array.get(localObject1, k);
              if (localObject3 != null) {
                this.formBody.addField((String)localObject2, localObject3.toString());
              }
              k += 1;
            }
          }
          else
          {
            this.formBody.addField((String)localObject2, localObject1.toString());
          }
        }
      }
      else if (localObject3 == FieldMap.class)
      {
        if (localObject1 != null)
        {
          localObject1 = ((Map)localObject1).entrySet().iterator();
          while (((Iterator)localObject1).hasNext())
          {
            localObject3 = (Map.Entry)((Iterator)localObject1).next();
            localObject2 = ((Map.Entry)localObject3).getKey();
            if (localObject2 == null) {
              throw new IllegalArgumentException("Parameter #" + (j + 1) + " field map contained null key.");
            }
            localObject3 = ((Map.Entry)localObject3).getValue();
            if (localObject3 != null) {
              this.formBody.addField(localObject2.toString(), localObject3.toString());
            }
          }
        }
      }
      else if (localObject3 == Part.class)
      {
        localObject3 = ((Part)localObject2).value();
        if (localObject1 != null)
        {
          localObject2 = ((Part)localObject2).encoding();
          if ((localObject1 instanceof TypedOutput)) {
            this.multipartBody.addPart((String)localObject3, (String)localObject2, (TypedOutput)localObject1);
          } else if ((localObject1 instanceof String)) {
            this.multipartBody.addPart((String)localObject3, (String)localObject2, new TypedString((String)localObject1));
          } else {
            this.multipartBody.addPart((String)localObject3, (String)localObject2, this.converter.toBody(localObject1));
          }
        }
      }
      else if (localObject3 == PartMap.class)
      {
        if (localObject1 != null)
        {
          localObject2 = ((PartMap)localObject2).encoding();
          localObject1 = ((Map)localObject1).entrySet().iterator();
          while (((Iterator)localObject1).hasNext())
          {
            localObject3 = (Map.Entry)((Iterator)localObject1).next();
            Object localObject4 = ((Map.Entry)localObject3).getKey();
            if (localObject4 == null) {
              throw new IllegalArgumentException("Parameter #" + (j + 1) + " part map contained null key.");
            }
            localObject4 = localObject4.toString();
            localObject3 = ((Map.Entry)localObject3).getValue();
            if (localObject3 != null) {
              if ((localObject3 instanceof TypedOutput)) {
                this.multipartBody.addPart((String)localObject4, (String)localObject2, (TypedOutput)localObject3);
              } else if ((localObject3 instanceof String)) {
                this.multipartBody.addPart((String)localObject4, (String)localObject2, new TypedString((String)localObject3));
              } else {
                this.multipartBody.addPart((String)localObject4, (String)localObject2, this.converter.toBody(localObject3));
              }
            }
          }
        }
      }
      else
      {
        if (localObject3 != Body.class) {
          break label1123;
        }
        if (localObject1 == null) {
          throw new IllegalArgumentException("Body parameter value must not be null.");
        }
        if ((localObject1 instanceof TypedOutput)) {
          this.body = ((TypedOutput)localObject1);
        } else {
          this.body = this.converter.toBody(localObject1);
        }
      }
    }
    label1123:
    throw new IllegalArgumentException("Unknown annotation: " + ((Class)localObject3).getCanonicalName());
  }
  
  private static class MimeOverridingTypedOutput
    implements TypedOutput
  {
    private final TypedOutput delegate;
    private final String mimeType;
    
    MimeOverridingTypedOutput(TypedOutput paramTypedOutput, String paramString)
    {
      this.delegate = paramTypedOutput;
      this.mimeType = paramString;
    }
    
    public String fileName()
    {
      return this.delegate.fileName();
    }
    
    public long length()
    {
      return this.delegate.length();
    }
    
    public String mimeType()
    {
      return this.mimeType;
    }
    
    public void writeTo(OutputStream paramOutputStream)
      throws IOException
    {
      this.delegate.writeTo(paramOutputStream);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/retrofit/RequestBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */