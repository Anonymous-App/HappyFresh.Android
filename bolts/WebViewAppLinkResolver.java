package bolts;

import android.content.Context;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WebViewAppLinkResolver
  implements AppLinkResolver
{
  private static final String KEY_AL_VALUE = "value";
  private static final String KEY_ANDROID = "android";
  private static final String KEY_APP_NAME = "app_name";
  private static final String KEY_CLASS = "class";
  private static final String KEY_PACKAGE = "package";
  private static final String KEY_SHOULD_FALLBACK = "should_fallback";
  private static final String KEY_URL = "url";
  private static final String KEY_WEB = "web";
  private static final String KEY_WEB_URL = "url";
  private static final String META_TAG_PREFIX = "al";
  private static final String PREFER_HEADER = "Prefer-Html-Meta-Tags";
  private static final String TAG_EXTRACTION_JAVASCRIPT = "javascript:boltsWebViewAppLinkResolverResult.setValue((function() {  var metaTags = document.getElementsByTagName('meta');  var results = [];  for (var i = 0; i < metaTags.length; i++) {    var property = metaTags[i].getAttribute('property');    if (property && property.substring(0, 'al:'.length) === 'al:') {      var tag = { \"property\": metaTags[i].getAttribute('property') };      if (metaTags[i].hasAttribute('content')) {        tag['content'] = metaTags[i].getAttribute('content');      }      results.push(tag);    }  }  return JSON.stringify(results);})())";
  private final Context context;
  
  public WebViewAppLinkResolver(Context paramContext)
  {
    this.context = paramContext;
  }
  
  private static List<Map<String, Object>> getAlList(Map<String, Object> paramMap, String paramString)
  {
    paramString = (List)paramMap.get(paramString);
    paramMap = paramString;
    if (paramString == null) {
      paramMap = Collections.emptyList();
    }
    return paramMap;
  }
  
  private static AppLink makeAppLinkFromAlData(Map<String, Object> paramMap, Uri paramUri)
  {
    ArrayList localArrayList = new ArrayList();
    Object localObject2 = (List)paramMap.get("android");
    Object localObject1 = localObject2;
    if (localObject2 == null) {
      localObject1 = Collections.emptyList();
    }
    localObject2 = ((List)localObject1).iterator();
    List localList1;
    if (((Iterator)localObject2).hasNext())
    {
      localObject1 = (Map)((Iterator)localObject2).next();
      localList1 = getAlList((Map)localObject1, "url");
      List localList2 = getAlList((Map)localObject1, "package");
      List localList3 = getAlList((Map)localObject1, "class");
      List localList4 = getAlList((Map)localObject1, "app_name");
      int j = Math.max(localList1.size(), Math.max(localList2.size(), Math.max(localList3.size(), localList4.size())));
      int i = 0;
      label143:
      label179:
      Uri localUri;
      label223:
      String str1;
      label264:
      String str2;
      if (i < j)
      {
        if (localList1.size() <= i) {
          break label341;
        }
        localObject1 = ((Map)localList1.get(i)).get("value");
        localUri = tryCreateUrl((String)localObject1);
        if (localList2.size() <= i) {
          break label347;
        }
        localObject1 = ((Map)localList2.get(i)).get("value");
        str1 = (String)localObject1;
        if (localList3.size() <= i) {
          break label353;
        }
        localObject1 = ((Map)localList3.get(i)).get("value");
        str2 = (String)localObject1;
        if (localList4.size() <= i) {
          break label359;
        }
      }
      label341:
      label347:
      label353:
      label359:
      for (localObject1 = ((Map)localList4.get(i)).get("value");; localObject1 = null)
      {
        localArrayList.add(new AppLink.Target(str1, str2, localUri, (String)localObject1));
        i += 1;
        break label143;
        break;
        localObject1 = null;
        break label179;
        localObject1 = null;
        break label223;
        localObject1 = null;
        break label264;
      }
    }
    localObject1 = paramUri;
    paramMap = (List)paramMap.get("web");
    localObject2 = localObject1;
    if (paramMap != null)
    {
      localObject2 = localObject1;
      if (paramMap.size() > 0)
      {
        paramMap = (Map)paramMap.get(0);
        localList1 = (List)paramMap.get("url");
        localObject2 = (List)paramMap.get("should_fallback");
        paramMap = (Map<String, Object>)localObject1;
        if (localObject2 != null)
        {
          paramMap = (Map<String, Object>)localObject1;
          if (((List)localObject2).size() > 0)
          {
            localObject2 = (String)((Map)((List)localObject2).get(0)).get("value");
            paramMap = (Map<String, Object>)localObject1;
            if (Arrays.asList(new String[] { "no", "false", "0" }).contains(((String)localObject2).toLowerCase())) {
              paramMap = null;
            }
          }
        }
        localObject2 = paramMap;
        if (paramMap != null)
        {
          localObject2 = paramMap;
          if (localList1 != null)
          {
            localObject2 = paramMap;
            if (localList1.size() > 0) {
              localObject2 = tryCreateUrl((String)((Map)localList1.get(0)).get("value"));
            }
          }
        }
      }
    }
    return new AppLink(paramUri, localArrayList, (Uri)localObject2);
  }
  
  private static Map<String, Object> parseAlData(JSONArray paramJSONArray)
    throws JSONException
  {
    HashMap localHashMap = new HashMap();
    int i = 0;
    if (i < paramJSONArray.length())
    {
      JSONObject localJSONObject = paramJSONArray.getJSONObject(i);
      String[] arrayOfString = localJSONObject.getString("property").split(":");
      if (!arrayOfString[0].equals("al")) {}
      for (;;)
      {
        i += 1;
        break;
        Object localObject1 = localHashMap;
        int j = 1;
        if (j < arrayOfString.length)
        {
          List localList = (List)((Map)localObject1).get(arrayOfString[j]);
          Object localObject2 = localList;
          if (localList == null)
          {
            localObject2 = new ArrayList();
            ((Map)localObject1).put(arrayOfString[j], localObject2);
          }
          if (((List)localObject2).size() > 0) {}
          for (localObject1 = (Map)((List)localObject2).get(((List)localObject2).size() - 1);; localObject1 = null)
          {
            if ((localObject1 == null) || (j == arrayOfString.length - 1))
            {
              localObject1 = new HashMap();
              ((List)localObject2).add(localObject1);
            }
            j += 1;
            break;
          }
        }
        if (localJSONObject.has("content")) {
          if (localJSONObject.isNull("content")) {
            ((Map)localObject1).put("value", null);
          } else {
            ((Map)localObject1).put("value", localJSONObject.getString("content"));
          }
        }
      }
    }
    return localHashMap;
  }
  
  /* Error */
  private static String readFromConnection(URLConnection paramURLConnection)
    throws java.io.IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: instanceof 220
    //   4: ifeq +71 -> 75
    //   7: aload_0
    //   8: checkcast 220	java/net/HttpURLConnection
    //   11: astore 4
    //   13: aload_0
    //   14: invokevirtual 226	java/net/URLConnection:getInputStream	()Ljava/io/InputStream;
    //   17: astore_3
    //   18: new 228	java/io/ByteArrayOutputStream
    //   21: dup
    //   22: invokespecial 229	java/io/ByteArrayOutputStream:<init>	()V
    //   25: astore 6
    //   27: sipush 1024
    //   30: newarray <illegal type>
    //   32: astore 4
    //   34: aload_3
    //   35: aload 4
    //   37: invokevirtual 235	java/io/InputStream:read	([B)I
    //   40: istore_1
    //   41: iload_1
    //   42: iconst_m1
    //   43: if_icmpeq +40 -> 83
    //   46: aload 6
    //   48: aload 4
    //   50: iconst_0
    //   51: iload_1
    //   52: invokevirtual 239	java/io/ByteArrayOutputStream:write	([BII)V
    //   55: goto -21 -> 34
    //   58: astore_0
    //   59: aload_3
    //   60: invokevirtual 242	java/io/InputStream:close	()V
    //   63: aload_0
    //   64: athrow
    //   65: astore_3
    //   66: aload 4
    //   68: invokevirtual 245	java/net/HttpURLConnection:getErrorStream	()Ljava/io/InputStream;
    //   71: astore_3
    //   72: goto -54 -> 18
    //   75: aload_0
    //   76: invokevirtual 226	java/net/URLConnection:getInputStream	()Ljava/io/InputStream;
    //   79: astore_3
    //   80: goto -62 -> 18
    //   83: aload_0
    //   84: invokevirtual 248	java/net/URLConnection:getContentEncoding	()Ljava/lang/String;
    //   87: astore 5
    //   89: aload 5
    //   91: astore 4
    //   93: aload 5
    //   95: ifnonnull +60 -> 155
    //   98: aload_0
    //   99: invokevirtual 251	java/net/URLConnection:getContentType	()Ljava/lang/String;
    //   102: ldc -3
    //   104: invokevirtual 199	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   107: astore 4
    //   109: aload 4
    //   111: arraylength
    //   112: istore_2
    //   113: iconst_0
    //   114: istore_1
    //   115: aload 5
    //   117: astore_0
    //   118: iload_1
    //   119: iload_2
    //   120: if_icmpge +63 -> 183
    //   123: aload 4
    //   125: iload_1
    //   126: aaload
    //   127: invokevirtual 256	java/lang/String:trim	()Ljava/lang/String;
    //   130: astore_0
    //   131: aload_0
    //   132: ldc_w 258
    //   135: invokevirtual 261	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   138: ifeq +38 -> 176
    //   141: aload_0
    //   142: ldc_w 258
    //   145: invokevirtual 262	java/lang/String:length	()I
    //   148: invokevirtual 266	java/lang/String:substring	(I)Ljava/lang/String;
    //   151: astore_0
    //   152: goto +31 -> 183
    //   155: new 135	java/lang/String
    //   158: dup
    //   159: aload 6
    //   161: invokevirtual 270	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   164: aload 4
    //   166: invokespecial 273	java/lang/String:<init>	([BLjava/lang/String;)V
    //   169: astore_0
    //   170: aload_3
    //   171: invokevirtual 242	java/io/InputStream:close	()V
    //   174: aload_0
    //   175: areturn
    //   176: iload_1
    //   177: iconst_1
    //   178: iadd
    //   179: istore_1
    //   180: goto -65 -> 115
    //   183: aload_0
    //   184: astore 4
    //   186: aload_0
    //   187: ifnonnull -32 -> 155
    //   190: ldc_w 275
    //   193: astore 4
    //   195: goto -40 -> 155
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	198	0	paramURLConnection	URLConnection
    //   40	140	1	i	int
    //   112	9	2	j	int
    //   17	43	3	localInputStream1	java.io.InputStream
    //   65	1	3	localException	Exception
    //   71	100	3	localInputStream2	java.io.InputStream
    //   11	183	4	localObject	Object
    //   87	29	5	str	String
    //   25	135	6	localByteArrayOutputStream	java.io.ByteArrayOutputStream
    // Exception table:
    //   from	to	target	type
    //   18	34	58	finally
    //   34	41	58	finally
    //   46	55	58	finally
    //   83	89	58	finally
    //   98	113	58	finally
    //   123	152	58	finally
    //   155	170	58	finally
    //   13	18	65	java/lang/Exception
  }
  
  private static Uri tryCreateUrl(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    return Uri.parse(paramString);
  }
  
  public Task<AppLink> getAppLinkFromUrlInBackground(final Uri paramUri)
  {
    final Capture localCapture1 = new Capture();
    final Capture localCapture2 = new Capture();
    Task.callInBackground(new Callable()
    {
      public Void call()
        throws Exception
      {
        URL localURL = new URL(paramUri.toString());
        URLConnection localURLConnection = null;
        while (localURL != null)
        {
          localURLConnection = localURL.openConnection();
          if ((localURLConnection instanceof HttpURLConnection)) {
            ((HttpURLConnection)localURLConnection).setInstanceFollowRedirects(true);
          }
          localURLConnection.setRequestProperty("Prefer-Html-Meta-Tags", "al");
          localURLConnection.connect();
          if ((localURLConnection instanceof HttpURLConnection))
          {
            HttpURLConnection localHttpURLConnection = (HttpURLConnection)localURLConnection;
            if ((localHttpURLConnection.getResponseCode() >= 300) && (localHttpURLConnection.getResponseCode() < 400))
            {
              localURL = new URL(localHttpURLConnection.getHeaderField("Location"));
              localHttpURLConnection.disconnect();
            }
            else
            {
              localURL = null;
            }
          }
          else
          {
            localURL = null;
          }
        }
        try
        {
          localCapture1.set(WebViewAppLinkResolver.readFromConnection(localURLConnection));
          localCapture2.set(localURLConnection.getContentType());
          return null;
        }
        finally
        {
          if ((localURLConnection instanceof HttpURLConnection)) {
            ((HttpURLConnection)localURLConnection).disconnect();
          }
        }
      }
    }).onSuccessTask(new Continuation()
    {
      public Task<JSONArray> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        final TaskCompletionSource localTaskCompletionSource = new TaskCompletionSource();
        WebView localWebView = new WebView(WebViewAppLinkResolver.this.context);
        localWebView.getSettings().setJavaScriptEnabled(true);
        localWebView.setNetworkAvailable(false);
        localWebView.setWebViewClient(new WebViewClient()
        {
          private boolean loaded = false;
          
          private void runJavaScript(WebView paramAnonymous2WebView)
          {
            if (!this.loaded)
            {
              this.loaded = true;
              paramAnonymous2WebView.loadUrl("javascript:boltsWebViewAppLinkResolverResult.setValue((function() {  var metaTags = document.getElementsByTagName('meta');  var results = [];  for (var i = 0; i < metaTags.length; i++) {    var property = metaTags[i].getAttribute('property');    if (property && property.substring(0, 'al:'.length) === 'al:') {      var tag = { \"property\": metaTags[i].getAttribute('property') };      if (metaTags[i].hasAttribute('content')) {        tag['content'] = metaTags[i].getAttribute('content');      }      results.push(tag);    }  }  return JSON.stringify(results);})())");
            }
          }
          
          public void onLoadResource(WebView paramAnonymous2WebView, String paramAnonymous2String)
          {
            super.onLoadResource(paramAnonymous2WebView, paramAnonymous2String);
            runJavaScript(paramAnonymous2WebView);
          }
          
          public void onPageFinished(WebView paramAnonymous2WebView, String paramAnonymous2String)
          {
            super.onPageFinished(paramAnonymous2WebView, paramAnonymous2String);
            runJavaScript(paramAnonymous2WebView);
          }
        });
        localWebView.addJavascriptInterface(new Object()
        {
          @JavascriptInterface
          public void setValue(String paramAnonymous2String)
          {
            try
            {
              localTaskCompletionSource.trySetResult(new JSONArray(paramAnonymous2String));
              return;
            }
            catch (JSONException paramAnonymous2String)
            {
              localTaskCompletionSource.trySetError(paramAnonymous2String);
            }
          }
        }, "boltsWebViewAppLinkResolverResult");
        paramAnonymousTask = null;
        if (localCapture2.get() != null) {
          paramAnonymousTask = ((String)localCapture2.get()).split(";")[0];
        }
        localWebView.loadDataWithBaseURL(paramUri.toString(), (String)localCapture1.get(), paramAnonymousTask, null, null);
        return localTaskCompletionSource.getTask();
      }
    }, Task.UI_THREAD_EXECUTOR).onSuccess(new Continuation()
    {
      public AppLink then(Task<JSONArray> paramAnonymousTask)
        throws Exception
      {
        return WebViewAppLinkResolver.makeAppLinkFromAlData(WebViewAppLinkResolver.access$000((JSONArray)paramAnonymousTask.getResult()), paramUri);
      }
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/bolts/WebViewAppLinkResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */