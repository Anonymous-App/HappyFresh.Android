package com.facebook.internal;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.Callback;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class Utility
{
  private static final String APPLICATION_FIELDS = "fields";
  private static final String APP_SETTINGS_PREFS_KEY_FORMAT = "com.facebook.internal.APP_SETTINGS.%s";
  private static final String APP_SETTINGS_PREFS_STORE = "com.facebook.internal.preferences.APP_SETTINGS";
  private static final String APP_SETTING_ANDROID_SDK_ERROR_CATEGORIES = "android_sdk_error_categories";
  private static final String APP_SETTING_DIALOG_CONFIGS = "android_dialog_configs";
  private static final String[] APP_SETTING_FIELDS = { "supports_implicit_sdk_logging", "gdpv4_nux_content", "gdpv4_nux_enabled", "android_dialog_configs", "android_sdk_error_categories" };
  private static final String APP_SETTING_NUX_CONTENT = "gdpv4_nux_content";
  private static final String APP_SETTING_NUX_ENABLED = "gdpv4_nux_enabled";
  private static final String APP_SETTING_SUPPORTS_IMPLICIT_SDK_LOGGING = "supports_implicit_sdk_logging";
  public static final int DEFAULT_STREAM_BUFFER_SIZE = 8192;
  private static final String DIALOG_CONFIG_DIALOG_NAME_FEATURE_NAME_SEPARATOR = "\\|";
  private static final String DIALOG_CONFIG_NAME_KEY = "name";
  private static final String DIALOG_CONFIG_URL_KEY = "url";
  private static final String DIALOG_CONFIG_VERSIONS_KEY = "versions";
  private static final String EXTRA_APP_EVENTS_INFO_FORMAT_VERSION = "a1";
  private static final String HASH_ALGORITHM_MD5 = "MD5";
  private static final String HASH_ALGORITHM_SHA1 = "SHA-1";
  static final String LOG_TAG = "FacebookSDK";
  private static final String URL_SCHEME = "https";
  private static final String UTF8 = "UTF-8";
  private static Map<String, FetchedAppSettings> fetchedAppSettings = new ConcurrentHashMap();
  private static AtomicBoolean loadingSettings = new AtomicBoolean(false);
  
  public static <T> boolean areObjectsEqual(T paramT1, T paramT2)
  {
    if (paramT1 == null) {
      return paramT2 == null;
    }
    return paramT1.equals(paramT2);
  }
  
  public static <T> ArrayList<T> arrayList(T... paramVarArgs)
  {
    ArrayList localArrayList = new ArrayList(paramVarArgs.length);
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      localArrayList.add(paramVarArgs[i]);
      i += 1;
    }
    return localArrayList;
  }
  
  public static <T> List<T> asListNoNulls(T... paramVarArgs)
  {
    ArrayList localArrayList = new ArrayList();
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      T ? = paramVarArgs[i];
      if (? != null) {
        localArrayList.add(?);
      }
      i += 1;
    }
    return localArrayList;
  }
  
  public static JSONObject awaitGetGraphMeRequestWithCache(String paramString)
  {
    JSONObject localJSONObject = ProfileInformationCache.getProfileInformation(paramString);
    if (localJSONObject != null) {
      return localJSONObject;
    }
    paramString = getGraphMeRequestWithCache(paramString).executeAndWait();
    if (paramString.getError() != null) {
      return null;
    }
    return paramString.getJSONObject();
  }
  
  public static Uri buildUri(String paramString1, String paramString2, Bundle paramBundle)
  {
    Uri.Builder localBuilder = new Uri.Builder();
    localBuilder.scheme("https");
    localBuilder.authority(paramString1);
    localBuilder.path(paramString2);
    if (paramBundle != null)
    {
      paramString1 = paramBundle.keySet().iterator();
      while (paramString1.hasNext())
      {
        paramString2 = (String)paramString1.next();
        Object localObject = paramBundle.get(paramString2);
        if ((localObject instanceof String)) {
          localBuilder.appendQueryParameter(paramString2, (String)localObject);
        }
      }
    }
    return localBuilder.build();
  }
  
  public static void clearCaches(Context paramContext)
  {
    ImageDownloader.clearCache(paramContext);
  }
  
  private static void clearCookiesForDomain(Context paramContext, String paramString)
  {
    CookieSyncManager.createInstance(paramContext).sync();
    paramContext = CookieManager.getInstance();
    Object localObject = paramContext.getCookie(paramString);
    if (localObject == null) {
      return;
    }
    localObject = ((String)localObject).split(";");
    int j = localObject.length;
    int i = 0;
    while (i < j)
    {
      String[] arrayOfString = localObject[i].split("=");
      if (arrayOfString.length > 0) {
        paramContext.setCookie(paramString, arrayOfString[0].trim() + "=;expires=Sat, 1 Jan 2000 00:00:01 UTC;");
      }
      i += 1;
    }
    paramContext.removeExpiredCookie();
  }
  
  public static void clearFacebookCookies(Context paramContext)
  {
    clearCookiesForDomain(paramContext, "facebook.com");
    clearCookiesForDomain(paramContext, ".facebook.com");
    clearCookiesForDomain(paramContext, "https://facebook.com");
    clearCookiesForDomain(paramContext, "https://.facebook.com");
  }
  
  public static void closeQuietly(Closeable paramCloseable)
  {
    if (paramCloseable != null) {}
    try
    {
      paramCloseable.close();
      return;
    }
    catch (IOException paramCloseable) {}
  }
  
  public static String coerceValueIfNullOrEmpty(String paramString1, String paramString2)
  {
    if (isNullOrEmpty(paramString1)) {
      return paramString2;
    }
    return paramString1;
  }
  
  static Map<String, Object> convertJSONObjectToHashMap(JSONObject paramJSONObject)
  {
    HashMap localHashMap = new HashMap();
    JSONArray localJSONArray = paramJSONObject.names();
    int i = 0;
    while (i < localJSONArray.length())
    {
      try
      {
        String str = localJSONArray.getString(i);
        Object localObject2 = paramJSONObject.get(str);
        Object localObject1 = localObject2;
        if ((localObject2 instanceof JSONObject)) {
          localObject1 = convertJSONObjectToHashMap((JSONObject)localObject2);
        }
        localHashMap.put(str, localObject1);
      }
      catch (JSONException localJSONException)
      {
        for (;;) {}
      }
      i += 1;
    }
    return localHashMap;
  }
  
  public static int copyAndCloseInputStream(InputStream paramInputStream, OutputStream paramOutputStream)
    throws IOException
  {
    byte[] arrayOfByte = null;
    int i = 0;
    try
    {
      BufferedInputStream localBufferedInputStream = new BufferedInputStream(paramInputStream);
      int j;
      if (paramOutputStream == null) {
        break label83;
      }
    }
    finally
    {
      try
      {
        arrayOfByte = new byte[' '];
        for (;;)
        {
          j = localBufferedInputStream.read(arrayOfByte);
          if (j == -1) {
            break;
          }
          paramOutputStream.write(arrayOfByte, 0, j);
          i += j;
        }
        if (localBufferedInputStream != null) {
          localBufferedInputStream.close();
        }
        if (paramInputStream != null) {
          paramInputStream.close();
        }
        return i;
      }
      finally
      {
        for (;;)
        {
          paramOutputStream = (OutputStream)localObject1;
          Object localObject2 = localObject3;
        }
      }
      localObject1 = finally;
      paramOutputStream = arrayOfByte;
    }
    paramOutputStream.close();
    label83:
    if (paramInputStream != null) {
      paramInputStream.close();
    }
    throw ((Throwable)localObject1);
  }
  
  public static void deleteDirectory(File paramFile)
  {
    if (!paramFile.exists()) {
      return;
    }
    if (paramFile.isDirectory())
    {
      File[] arrayOfFile = paramFile.listFiles();
      int j = arrayOfFile.length;
      int i = 0;
      while (i < j)
      {
        deleteDirectory(arrayOfFile[i]);
        i += 1;
      }
    }
    paramFile.delete();
  }
  
  public static void disconnectQuietly(URLConnection paramURLConnection)
  {
    if ((paramURLConnection instanceof HttpURLConnection)) {
      ((HttpURLConnection)paramURLConnection).disconnect();
    }
  }
  
  public static <T> List<T> filter(List<T> paramList, Predicate<T> paramPredicate)
  {
    if (paramList == null) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      Object localObject = paramList.next();
      if (paramPredicate.apply(localObject)) {
        localArrayList.add(localObject);
      }
    }
    paramList = localArrayList;
    if (localArrayList.size() == 0) {
      paramList = null;
    }
    return paramList;
  }
  
  public static String getActivityName(Context paramContext)
  {
    if (paramContext == null) {
      return "null";
    }
    if (paramContext == paramContext.getApplicationContext()) {
      return "unknown";
    }
    return paramContext.getClass().getSimpleName();
  }
  
  private static JSONObject getAppSettingsQueryResponse(String paramString)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("fields", TextUtils.join(",", APP_SETTING_FIELDS));
    paramString = GraphRequest.newGraphPathRequest(null, paramString, null);
    paramString.setSkipClientToken(true);
    paramString.setParameters(localBundle);
    return paramString.executeAndWait().getJSONObject();
  }
  
  public static FetchedAppSettings getAppSettingsWithoutQuery(String paramString)
  {
    if (paramString != null) {
      return (FetchedAppSettings)fetchedAppSettings.get(paramString);
    }
    return null;
  }
  
  public static Date getBundleLongAsDate(Bundle paramBundle, String paramString, Date paramDate)
  {
    if (paramBundle == null) {
      return null;
    }
    paramBundle = paramBundle.get(paramString);
    long l;
    if ((paramBundle instanceof Long)) {
      l = ((Long)paramBundle).longValue();
    }
    for (;;)
    {
      if (l == 0L)
      {
        return new Date(Long.MAX_VALUE);
        if (!(paramBundle instanceof String)) {
          break;
        }
        try
        {
          l = Long.parseLong((String)paramBundle);
        }
        catch (NumberFormatException paramBundle)
        {
          return null;
        }
      }
    }
    return new Date(paramDate.getTime() + 1000L * l);
  }
  
  public static long getContentSize(Uri paramUri)
  {
    Uri localUri = null;
    try
    {
      paramUri = FacebookSdk.getApplicationContext().getContentResolver().query(paramUri, null, null, null, null);
      localUri = paramUri;
      int i = paramUri.getColumnIndex("_size");
      localUri = paramUri;
      paramUri.moveToFirst();
      localUri = paramUri;
      long l = paramUri.getLong(i);
      return l;
    }
    finally
    {
      if (localUri != null) {
        localUri.close();
      }
    }
  }
  
  public static DialogFeatureConfig getDialogFeatureConfig(String paramString1, String paramString2, String paramString3)
  {
    if ((isNullOrEmpty(paramString2)) || (isNullOrEmpty(paramString3))) {}
    do
    {
      do
      {
        return null;
        paramString1 = (FetchedAppSettings)fetchedAppSettings.get(paramString1);
      } while (paramString1 == null);
      paramString1 = (Map)paramString1.getDialogConfigurations().get(paramString2);
    } while (paramString1 == null);
    return (DialogFeatureConfig)paramString1.get(paramString3);
  }
  
  private static GraphRequest getGraphMeRequestWithCache(String paramString)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("fields", "id,name,first_name,middle_name,last_name,link");
    localBundle.putString("access_token", paramString);
    return new GraphRequest(null, "me", localBundle, HttpMethod.GET, null);
  }
  
  public static void getGraphMeRequestWithCacheAsync(final String paramString, GraphMeRequestWithCacheCallback paramGraphMeRequestWithCacheCallback)
  {
    JSONObject localJSONObject = ProfileInformationCache.getProfileInformation(paramString);
    if (localJSONObject != null)
    {
      paramGraphMeRequestWithCacheCallback.onSuccess(localJSONObject);
      return;
    }
    paramGraphMeRequestWithCacheCallback = new GraphRequest.Callback()
    {
      public void onCompleted(GraphResponse paramAnonymousGraphResponse)
      {
        if (paramAnonymousGraphResponse.getError() != null)
        {
          this.val$callback.onFailure(paramAnonymousGraphResponse.getError().getException());
          return;
        }
        ProfileInformationCache.putProfileInformation(paramString, paramAnonymousGraphResponse.getJSONObject());
        this.val$callback.onSuccess(paramAnonymousGraphResponse.getJSONObject());
      }
    };
    paramString = getGraphMeRequestWithCache(paramString);
    paramString.setCallback(paramGraphMeRequestWithCacheCallback);
    paramString.executeAsync();
  }
  
  public static String getMetadataApplicationId(Context paramContext)
  {
    Validate.notNull(paramContext, "context");
    FacebookSdk.sdkInitialize(paramContext);
    return FacebookSdk.getApplicationId();
  }
  
  public static Method getMethodQuietly(Class<?> paramClass, String paramString, Class<?>... paramVarArgs)
  {
    try
    {
      paramClass = paramClass.getMethod(paramString, paramVarArgs);
      return paramClass;
    }
    catch (NoSuchMethodException paramClass) {}
    return null;
  }
  
  public static Method getMethodQuietly(String paramString1, String paramString2, Class<?>... paramVarArgs)
  {
    try
    {
      paramString1 = getMethodQuietly(Class.forName(paramString1), paramString2, paramVarArgs);
      return paramString1;
    }
    catch (ClassNotFoundException paramString1) {}
    return null;
  }
  
  public static Object getStringPropertyAsJSON(JSONObject paramJSONObject, String paramString1, String paramString2)
    throws JSONException
  {
    paramString1 = paramJSONObject.opt(paramString1);
    paramJSONObject = paramString1;
    if (paramString1 != null)
    {
      paramJSONObject = paramString1;
      if ((paramString1 instanceof String)) {
        paramJSONObject = new JSONTokener((String)paramString1).nextValue();
      }
    }
    if ((paramJSONObject != null) && (!(paramJSONObject instanceof JSONObject)) && (!(paramJSONObject instanceof JSONArray)))
    {
      if (paramString2 != null)
      {
        paramString1 = new JSONObject();
        paramString1.putOpt(paramString2, paramJSONObject);
        return paramString1;
      }
      throw new FacebookException("Got an unexpected non-JSON object.");
    }
    return paramJSONObject;
  }
  
  public static String getUriString(Uri paramUri)
  {
    if (paramUri == null) {
      return null;
    }
    return paramUri.toString();
  }
  
  public static boolean hasSameId(JSONObject paramJSONObject1, JSONObject paramJSONObject2)
  {
    if ((paramJSONObject1 == null) || (paramJSONObject2 == null) || (!paramJSONObject1.has("id")) || (!paramJSONObject2.has("id"))) {}
    do
    {
      return false;
      if (paramJSONObject1.equals(paramJSONObject2)) {
        return true;
      }
      paramJSONObject1 = paramJSONObject1.optString("id");
      paramJSONObject2 = paramJSONObject2.optString("id");
    } while ((paramJSONObject1 == null) || (paramJSONObject2 == null));
    return paramJSONObject1.equals(paramJSONObject2);
  }
  
  private static String hashBytes(MessageDigest paramMessageDigest, byte[] paramArrayOfByte)
  {
    paramMessageDigest.update(paramArrayOfByte);
    paramMessageDigest = paramMessageDigest.digest();
    paramArrayOfByte = new StringBuilder();
    int j = paramMessageDigest.length;
    int i = 0;
    while (i < j)
    {
      int k = paramMessageDigest[i];
      paramArrayOfByte.append(Integer.toHexString(k >> 4 & 0xF));
      paramArrayOfByte.append(Integer.toHexString(k >> 0 & 0xF));
      i += 1;
    }
    return paramArrayOfByte.toString();
  }
  
  public static <T> HashSet<T> hashSet(T... paramVarArgs)
  {
    HashSet localHashSet = new HashSet(paramVarArgs.length);
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      localHashSet.add(paramVarArgs[i]);
      i += 1;
    }
    return localHashSet;
  }
  
  private static String hashWithAlgorithm(String paramString1, String paramString2)
  {
    return hashWithAlgorithm(paramString1, paramString2.getBytes());
  }
  
  private static String hashWithAlgorithm(String paramString, byte[] paramArrayOfByte)
  {
    try
    {
      paramString = MessageDigest.getInstance(paramString);
      return hashBytes(paramString, paramArrayOfByte);
    }
    catch (NoSuchAlgorithmException paramString) {}
    return null;
  }
  
  public static int[] intersectRanges(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    if (paramArrayOfInt1 == null) {
      return paramArrayOfInt2;
    }
    if (paramArrayOfInt2 == null) {
      return paramArrayOfInt1;
    }
    int[] arrayOfInt = new int[paramArrayOfInt1.length + paramArrayOfInt2.length];
    int m = 0;
    int i2 = 0;
    int i1 = 0;
    int i = m;
    if (i2 < paramArrayOfInt1.length)
    {
      i = m;
      if (i1 < paramArrayOfInt2.length)
      {
        int n = Integer.MIN_VALUE;
        int i3 = Integer.MAX_VALUE;
        int k = paramArrayOfInt1[i2];
        i = Integer.MAX_VALUE;
        int i4 = paramArrayOfInt2[i1];
        int j = Integer.MAX_VALUE;
        if (i2 < paramArrayOfInt1.length - 1) {
          i = paramArrayOfInt1[(i2 + 1)];
        }
        if (i1 < paramArrayOfInt2.length - 1) {
          j = paramArrayOfInt2[(i1 + 1)];
        }
        if (k < i4) {
          if (i > i4)
          {
            n = i4;
            if (i > j)
            {
              i = j;
              k = i1 + 2;
              j = i2;
            }
          }
        }
        for (;;)
        {
          i2 = j;
          i1 = k;
          if (n == Integer.MIN_VALUE) {
            break;
          }
          i1 = m + 1;
          arrayOfInt[m] = n;
          if (i == Integer.MAX_VALUE) {
            break label285;
          }
          m = i1 + 1;
          arrayOfInt[i1] = i;
          i2 = j;
          i1 = k;
          break;
          j = i2 + 2;
          k = i1;
          continue;
          j = i2 + 2;
          k = i1;
          i = i3;
          continue;
          if (j > k)
          {
            n = k;
            if (j > i)
            {
              j = i2 + 2;
              k = i1;
            }
            else
            {
              i = j;
              k = i1 + 2;
              j = i2;
            }
          }
          else
          {
            k = i1 + 2;
            j = i2;
            i = i3;
          }
        }
        label285:
        i = i1;
      }
    }
    return Arrays.copyOf(arrayOfInt, i);
  }
  
  public static Object invokeMethodQuietly(Object paramObject, Method paramMethod, Object... paramVarArgs)
  {
    try
    {
      paramObject = paramMethod.invoke(paramObject, paramVarArgs);
      return paramObject;
    }
    catch (IllegalAccessException paramObject)
    {
      return null;
    }
    catch (InvocationTargetException paramObject) {}
    return null;
  }
  
  public static boolean isContentUri(Uri paramUri)
  {
    return (paramUri != null) && ("content".equalsIgnoreCase(paramUri.getScheme()));
  }
  
  public static boolean isCurrentAccessToken(AccessToken paramAccessToken)
  {
    if (paramAccessToken != null) {
      return paramAccessToken.equals(AccessToken.getCurrentAccessToken());
    }
    return false;
  }
  
  public static boolean isFileUri(Uri paramUri)
  {
    return (paramUri != null) && ("file".equalsIgnoreCase(paramUri.getScheme()));
  }
  
  public static boolean isNullOrEmpty(String paramString)
  {
    return (paramString == null) || (paramString.length() == 0);
  }
  
  public static <T> boolean isNullOrEmpty(Collection<T> paramCollection)
  {
    return (paramCollection == null) || (paramCollection.size() == 0);
  }
  
  public static <T> boolean isSubset(Collection<T> paramCollection1, Collection<T> paramCollection2)
  {
    boolean bool = false;
    if ((paramCollection2 == null) || (paramCollection2.size() == 0))
    {
      if ((paramCollection1 == null) || (paramCollection1.size() == 0)) {
        bool = true;
      }
      return bool;
    }
    paramCollection2 = new HashSet(paramCollection2);
    paramCollection1 = paramCollection1.iterator();
    while (paramCollection1.hasNext()) {
      if (!paramCollection2.contains(paramCollection1.next())) {
        return false;
      }
    }
    return true;
  }
  
  public static boolean isWebUri(Uri paramUri)
  {
    return (paramUri != null) && (("http".equalsIgnoreCase(paramUri.getScheme())) || ("https".equalsIgnoreCase(paramUri.getScheme())));
  }
  
  public static List<String> jsonArrayToStringList(JSONArray paramJSONArray)
    throws JSONException
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    while (i < paramJSONArray.length())
    {
      localArrayList.add(paramJSONArray.getString(i));
      i += 1;
    }
    return localArrayList;
  }
  
  public static void loadAppSettingsAsync(final Context paramContext, String paramString)
  {
    boolean bool = loadingSettings.compareAndSet(false, true);
    if ((isNullOrEmpty(paramString)) || (fetchedAppSettings.containsKey(paramString)) || (!bool)) {}
    do
    {
      final Object localObject;
      do
      {
        return;
        localObject = String.format("com.facebook.internal.APP_SETTINGS.%s", new Object[] { paramString });
        FacebookSdk.getExecutor().execute(new Runnable()
        {
          public void run()
          {
            JSONObject localJSONObject = Utility.getAppSettingsQueryResponse(this.val$applicationId);
            if (localJSONObject != null)
            {
              Utility.parseAppSettingsFromJSON(this.val$applicationId, localJSONObject);
              paramContext.getSharedPreferences("com.facebook.internal.preferences.APP_SETTINGS", 0).edit().putString(localObject, localJSONObject.toString()).apply();
            }
            Utility.loadingSettings.set(false);
          }
        });
        localObject = paramContext.getSharedPreferences("com.facebook.internal.preferences.APP_SETTINGS", 0).getString((String)localObject, null);
      } while (isNullOrEmpty((String)localObject));
      paramContext = null;
      try
      {
        localObject = new JSONObject((String)localObject);
        paramContext = (Context)localObject;
      }
      catch (JSONException localJSONException)
      {
        for (;;)
        {
          logd("FacebookSDK", localJSONException);
        }
      }
    } while (paramContext == null);
    parseAppSettingsFromJSON(paramString, paramContext);
  }
  
  public static void logd(String paramString, Exception paramException)
  {
    if ((FacebookSdk.isDebugEnabled()) && (paramString != null) && (paramException != null)) {
      Log.d(paramString, paramException.getClass().getSimpleName() + ": " + paramException.getMessage());
    }
  }
  
  public static void logd(String paramString1, String paramString2)
  {
    if ((FacebookSdk.isDebugEnabled()) && (paramString1 != null) && (paramString2 != null)) {
      Log.d(paramString1, paramString2);
    }
  }
  
  public static void logd(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if ((FacebookSdk.isDebugEnabled()) && (!isNullOrEmpty(paramString1))) {
      Log.d(paramString1, paramString2, paramThrowable);
    }
  }
  
  public static <T, K> List<K> map(List<T> paramList, Mapper<T, K> paramMapper)
  {
    if (paramList == null) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      Object localObject = paramMapper.apply(paramList.next());
      if (localObject != null) {
        localArrayList.add(localObject);
      }
    }
    paramList = localArrayList;
    if (localArrayList.size() == 0) {
      paramList = null;
    }
    return paramList;
  }
  
  public static String md5hash(String paramString)
  {
    return hashWithAlgorithm("MD5", paramString);
  }
  
  private static FetchedAppSettings parseAppSettingsFromJSON(String paramString, JSONObject paramJSONObject)
  {
    Object localObject = paramJSONObject.optJSONArray("android_sdk_error_categories");
    if (localObject == null) {}
    for (localObject = FacebookRequestErrorClassification.getDefaultErrorClassification();; localObject = FacebookRequestErrorClassification.createFromJSON((JSONArray)localObject))
    {
      paramJSONObject = new FetchedAppSettings(paramJSONObject.optBoolean("supports_implicit_sdk_logging", false), paramJSONObject.optString("gdpv4_nux_content", ""), paramJSONObject.optBoolean("gdpv4_nux_enabled", false), parseDialogConfigurations(paramJSONObject.optJSONObject("android_dialog_configs")), (FacebookRequestErrorClassification)localObject, null);
      fetchedAppSettings.put(paramString, paramJSONObject);
      return paramJSONObject;
    }
  }
  
  private static Map<String, Map<String, DialogFeatureConfig>> parseDialogConfigurations(JSONObject paramJSONObject)
  {
    HashMap localHashMap = new HashMap();
    if (paramJSONObject != null)
    {
      JSONArray localJSONArray = paramJSONObject.optJSONArray("data");
      if (localJSONArray != null)
      {
        int i = 0;
        if (i < localJSONArray.length())
        {
          DialogFeatureConfig localDialogFeatureConfig = DialogFeatureConfig.parseDialogConfig(localJSONArray.optJSONObject(i));
          if (localDialogFeatureConfig == null) {}
          for (;;)
          {
            i += 1;
            break;
            String str = localDialogFeatureConfig.getDialogName();
            Map localMap = (Map)localHashMap.get(str);
            paramJSONObject = localMap;
            if (localMap == null)
            {
              paramJSONObject = new HashMap();
              localHashMap.put(str, paramJSONObject);
            }
            paramJSONObject.put(localDialogFeatureConfig.getFeatureName(), localDialogFeatureConfig);
          }
        }
      }
    }
    return localHashMap;
  }
  
  public static Bundle parseUrlQueryString(String paramString)
  {
    Bundle localBundle = new Bundle();
    int j;
    int i;
    if (!isNullOrEmpty(paramString))
    {
      paramString = paramString.split("&");
      j = paramString.length;
      i = 0;
    }
    for (;;)
    {
      if (i < j)
      {
        String[] arrayOfString = paramString[i].split("=");
        try
        {
          if (arrayOfString.length == 2) {
            localBundle.putString(URLDecoder.decode(arrayOfString[0], "UTF-8"), URLDecoder.decode(arrayOfString[1], "UTF-8"));
          } else if (arrayOfString.length == 1) {
            localBundle.putString(URLDecoder.decode(arrayOfString[0], "UTF-8"), "");
          }
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          logd("FacebookSDK", localUnsupportedEncodingException);
        }
      }
      return localBundle;
      i += 1;
    }
  }
  
  public static void putCommaSeparatedStringList(Bundle paramBundle, String paramString, ArrayList<String> paramArrayList)
  {
    if (paramArrayList != null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      paramArrayList = paramArrayList.iterator();
      while (paramArrayList.hasNext())
      {
        localStringBuilder.append((String)paramArrayList.next());
        localStringBuilder.append(",");
      }
      paramArrayList = "";
      if (localStringBuilder.length() > 0) {
        paramArrayList = localStringBuilder.substring(0, localStringBuilder.length() - 1);
      }
      paramBundle.putString(paramString, paramArrayList);
    }
  }
  
  public static boolean putJSONValueInBundle(Bundle paramBundle, String paramString, Object paramObject)
  {
    if (paramObject == null) {
      paramBundle.remove(paramString);
    }
    for (;;)
    {
      return true;
      if ((paramObject instanceof Boolean))
      {
        paramBundle.putBoolean(paramString, ((Boolean)paramObject).booleanValue());
      }
      else if ((paramObject instanceof boolean[]))
      {
        paramBundle.putBooleanArray(paramString, (boolean[])paramObject);
      }
      else if ((paramObject instanceof Double))
      {
        paramBundle.putDouble(paramString, ((Double)paramObject).doubleValue());
      }
      else if ((paramObject instanceof double[]))
      {
        paramBundle.putDoubleArray(paramString, (double[])paramObject);
      }
      else if ((paramObject instanceof Integer))
      {
        paramBundle.putInt(paramString, ((Integer)paramObject).intValue());
      }
      else if ((paramObject instanceof int[]))
      {
        paramBundle.putIntArray(paramString, (int[])paramObject);
      }
      else if ((paramObject instanceof Long))
      {
        paramBundle.putLong(paramString, ((Long)paramObject).longValue());
      }
      else if ((paramObject instanceof long[]))
      {
        paramBundle.putLongArray(paramString, (long[])paramObject);
      }
      else if ((paramObject instanceof String))
      {
        paramBundle.putString(paramString, (String)paramObject);
      }
      else if ((paramObject instanceof JSONArray))
      {
        paramBundle.putString(paramString, ((JSONArray)paramObject).toString());
      }
      else
      {
        if (!(paramObject instanceof JSONObject)) {
          break;
        }
        paramBundle.putString(paramString, ((JSONObject)paramObject).toString());
      }
    }
    return false;
  }
  
  public static void putNonEmptyString(Bundle paramBundle, String paramString1, String paramString2)
  {
    if (!isNullOrEmpty(paramString2)) {
      paramBundle.putString(paramString1, paramString2);
    }
  }
  
  public static void putUri(Bundle paramBundle, String paramString, Uri paramUri)
  {
    if (paramUri != null) {
      putNonEmptyString(paramBundle, paramString, paramUri.toString());
    }
  }
  
  public static FetchedAppSettings queryAppSettings(String paramString, boolean paramBoolean)
  {
    if ((!paramBoolean) && (fetchedAppSettings.containsKey(paramString))) {
      return (FetchedAppSettings)fetchedAppSettings.get(paramString);
    }
    JSONObject localJSONObject = getAppSettingsQueryResponse(paramString);
    if (localJSONObject == null) {
      return null;
    }
    return parseAppSettingsFromJSON(paramString, localJSONObject);
  }
  
  /* Error */
  public static String readStreamToString(InputStream paramInputStream)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aconst_null
    //   3: astore_3
    //   4: new 340	java/io/BufferedInputStream
    //   7: dup
    //   8: aload_0
    //   9: invokespecial 343	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   12: astore_0
    //   13: new 928	java/io/InputStreamReader
    //   16: dup
    //   17: aload_0
    //   18: invokespecial 929	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   21: astore_2
    //   22: new 258	java/lang/StringBuilder
    //   25: dup
    //   26: invokespecial 259	java/lang/StringBuilder:<init>	()V
    //   29: astore_3
    //   30: sipush 2048
    //   33: newarray <illegal type>
    //   35: astore 4
    //   37: aload_2
    //   38: aload 4
    //   40: invokevirtual 932	java/io/InputStreamReader:read	([C)I
    //   43: istore_1
    //   44: iload_1
    //   45: iconst_m1
    //   46: if_icmpeq +32 -> 78
    //   49: aload_3
    //   50: aload 4
    //   52: iconst_0
    //   53: iload_1
    //   54: invokevirtual 935	java/lang/StringBuilder:append	([CII)Ljava/lang/StringBuilder;
    //   57: pop
    //   58: goto -21 -> 37
    //   61: astore 4
    //   63: aload_2
    //   64: astore_3
    //   65: aload 4
    //   67: astore_2
    //   68: aload_0
    //   69: invokestatic 937	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   72: aload_3
    //   73: invokestatic 937	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   76: aload_2
    //   77: athrow
    //   78: aload_3
    //   79: invokevirtual 272	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   82: astore_3
    //   83: aload_0
    //   84: invokestatic 937	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   87: aload_2
    //   88: invokestatic 937	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   91: aload_3
    //   92: areturn
    //   93: astore 4
    //   95: aload_2
    //   96: astore_0
    //   97: aload 4
    //   99: astore_2
    //   100: goto -32 -> 68
    //   103: astore_2
    //   104: goto -36 -> 68
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	107	0	paramInputStream	InputStream
    //   43	11	1	i	int
    //   1	99	2	localObject1	Object
    //   103	1	2	localObject2	Object
    //   3	89	3	localObject3	Object
    //   35	16	4	arrayOfChar	char[]
    //   61	5	4	localObject4	Object
    //   93	5	4	localObject5	Object
    // Exception table:
    //   from	to	target	type
    //   22	37	61	finally
    //   37	44	61	finally
    //   49	58	61	finally
    //   78	83	61	finally
    //   4	13	93	finally
    //   13	22	103	finally
  }
  
  public static Map<String, String> readStringMapFromParcel(Parcel paramParcel)
  {
    int j = paramParcel.readInt();
    Object localObject;
    if (j < 0)
    {
      localObject = null;
      return (Map<String, String>)localObject;
    }
    HashMap localHashMap = new HashMap();
    int i = 0;
    for (;;)
    {
      localObject = localHashMap;
      if (i >= j) {
        break;
      }
      localHashMap.put(paramParcel.readString(), paramParcel.readString());
      i += 1;
    }
  }
  
  public static String safeGetStringFromResponse(JSONObject paramJSONObject, String paramString)
  {
    if (paramJSONObject != null) {
      return paramJSONObject.optString(paramString, "");
    }
    return "";
  }
  
  public static void setAppEventAttributionParameters(JSONObject paramJSONObject, AttributionIdentifiers paramAttributionIdentifiers, String paramString, boolean paramBoolean)
    throws JSONException
  {
    boolean bool2 = true;
    if ((paramAttributionIdentifiers != null) && (paramAttributionIdentifiers.getAttributionId() != null)) {
      paramJSONObject.put("attribution", paramAttributionIdentifiers.getAttributionId());
    }
    boolean bool1;
    if ((paramAttributionIdentifiers != null) && (paramAttributionIdentifiers.getAndroidAdvertiserId() != null))
    {
      paramJSONObject.put("advertiser_id", paramAttributionIdentifiers.getAndroidAdvertiserId());
      if (!paramAttributionIdentifiers.isTrackingLimited())
      {
        bool1 = true;
        paramJSONObject.put("advertiser_tracking_enabled", bool1);
      }
    }
    else
    {
      paramJSONObject.put("anon_id", paramString);
      if (paramBoolean) {
        break label101;
      }
    }
    label101:
    for (paramBoolean = bool2;; paramBoolean = false)
    {
      paramJSONObject.put("application_tracking_enabled", paramBoolean);
      return;
      bool1 = false;
      break;
    }
  }
  
  public static void setAppEventExtendedDeviceInfoParameters(JSONObject paramJSONObject, Context paramContext)
    throws JSONException
  {
    JSONArray localJSONArray = new JSONArray();
    localJSONArray.put("a1");
    String str2 = paramContext.getPackageName();
    int j = -1;
    str1 = "";
    int i = j;
    try
    {
      paramContext = paramContext.getPackageManager().getPackageInfo(str2, 0);
      i = j;
      j = paramContext.versionCode;
      i = j;
      paramContext = paramContext.versionName;
      i = j;
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      for (;;)
      {
        paramContext = str1;
      }
    }
    localJSONArray.put(str2);
    localJSONArray.put(i);
    localJSONArray.put(paramContext);
    paramJSONObject.put("extinfo", localJSONArray.toString());
  }
  
  public static String sha1hash(String paramString)
  {
    return hashWithAlgorithm("SHA-1", paramString);
  }
  
  public static String sha1hash(byte[] paramArrayOfByte)
  {
    return hashWithAlgorithm("SHA-1", paramArrayOfByte);
  }
  
  public static boolean stringsEqualOrEmpty(String paramString1, String paramString2)
  {
    boolean bool1 = TextUtils.isEmpty(paramString1);
    boolean bool2 = TextUtils.isEmpty(paramString2);
    if ((bool1) && (bool2)) {
      return true;
    }
    if ((!bool1) && (!bool2)) {
      return paramString1.equals(paramString2);
    }
    return false;
  }
  
  public static JSONArray tryGetJSONArrayFromResponse(JSONObject paramJSONObject, String paramString)
  {
    if (paramJSONObject != null) {
      return paramJSONObject.optJSONArray(paramString);
    }
    return null;
  }
  
  public static JSONObject tryGetJSONObjectFromResponse(JSONObject paramJSONObject, String paramString)
  {
    if (paramJSONObject != null) {
      return paramJSONObject.optJSONObject(paramString);
    }
    return null;
  }
  
  public static <T> Collection<T> unmodifiableCollection(T... paramVarArgs)
  {
    return Collections.unmodifiableCollection(Arrays.asList(paramVarArgs));
  }
  
  public static void writeStringMapToParcel(Parcel paramParcel, Map<String, String> paramMap)
  {
    if (paramMap == null) {
      paramParcel.writeInt(-1);
    }
    for (;;)
    {
      return;
      paramParcel.writeInt(paramMap.size());
      paramMap = paramMap.entrySet().iterator();
      while (paramMap.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramMap.next();
        paramParcel.writeString((String)localEntry.getKey());
        paramParcel.writeString((String)localEntry.getValue());
      }
    }
  }
  
  public static class DialogFeatureConfig
  {
    private String dialogName;
    private Uri fallbackUrl;
    private String featureName;
    private int[] featureVersionSpec;
    
    private DialogFeatureConfig(String paramString1, String paramString2, Uri paramUri, int[] paramArrayOfInt)
    {
      this.dialogName = paramString1;
      this.featureName = paramString2;
      this.fallbackUrl = paramUri;
      this.featureVersionSpec = paramArrayOfInt;
    }
    
    private static DialogFeatureConfig parseDialogConfig(JSONObject paramJSONObject)
    {
      Object localObject = paramJSONObject.optString("name");
      if (Utility.isNullOrEmpty((String)localObject)) {}
      String str1;
      String str2;
      do
      {
        do
        {
          return null;
          localObject = ((String)localObject).split("\\|");
        } while (localObject.length != 2);
        str1 = localObject[0];
        str2 = localObject[1];
      } while ((Utility.isNullOrEmpty(str1)) || (Utility.isNullOrEmpty(str2)));
      String str3 = paramJSONObject.optString("url");
      localObject = null;
      if (!Utility.isNullOrEmpty(str3)) {
        localObject = Uri.parse(str3);
      }
      return new DialogFeatureConfig(str1, str2, (Uri)localObject, parseVersionSpec(paramJSONObject.optJSONArray("versions")));
    }
    
    private static int[] parseVersionSpec(JSONArray paramJSONArray)
    {
      Object localObject = null;
      if (paramJSONArray != null)
      {
        int m = paramJSONArray.length();
        int[] arrayOfInt = new int[m];
        int j = 0;
        for (;;)
        {
          localObject = arrayOfInt;
          if (j < m)
          {
            int k = paramJSONArray.optInt(j, -1);
            int i = k;
            if (k == -1)
            {
              localObject = paramJSONArray.optString(j);
              i = k;
              if (Utility.isNullOrEmpty((String)localObject)) {}
            }
            try
            {
              i = Integer.parseInt((String)localObject);
              arrayOfInt[j] = i;
              j += 1;
            }
            catch (NumberFormatException localNumberFormatException)
            {
              for (;;)
              {
                Utility.logd("FacebookSDK", localNumberFormatException);
                i = -1;
              }
            }
          }
        }
      }
      return localNumberFormatException;
    }
    
    public String getDialogName()
    {
      return this.dialogName;
    }
    
    public Uri getFallbackUrl()
    {
      return this.fallbackUrl;
    }
    
    public String getFeatureName()
    {
      return this.featureName;
    }
    
    public int[] getVersionSpec()
    {
      return this.featureVersionSpec;
    }
  }
  
  public static class FetchedAppSettings
  {
    private Map<String, Map<String, Utility.DialogFeatureConfig>> dialogConfigMap;
    private FacebookRequestErrorClassification errorClassification;
    private String nuxContent;
    private boolean nuxEnabled;
    private boolean supportsImplicitLogging;
    
    private FetchedAppSettings(boolean paramBoolean1, String paramString, boolean paramBoolean2, Map<String, Map<String, Utility.DialogFeatureConfig>> paramMap, FacebookRequestErrorClassification paramFacebookRequestErrorClassification)
    {
      this.supportsImplicitLogging = paramBoolean1;
      this.nuxContent = paramString;
      this.nuxEnabled = paramBoolean2;
      this.dialogConfigMap = paramMap;
      this.errorClassification = paramFacebookRequestErrorClassification;
    }
    
    public Map<String, Map<String, Utility.DialogFeatureConfig>> getDialogConfigurations()
    {
      return this.dialogConfigMap;
    }
    
    public FacebookRequestErrorClassification getErrorClassification()
    {
      return this.errorClassification;
    }
    
    public String getNuxContent()
    {
      return this.nuxContent;
    }
    
    public boolean getNuxEnabled()
    {
      return this.nuxEnabled;
    }
    
    public boolean supportsImplicitLogging()
    {
      return this.supportsImplicitLogging;
    }
  }
  
  public static abstract interface GraphMeRequestWithCacheCallback
  {
    public abstract void onFailure(FacebookException paramFacebookException);
    
    public abstract void onSuccess(JSONObject paramJSONObject);
  }
  
  public static abstract interface Mapper<T, K>
  {
    public abstract K apply(T paramT);
  }
  
  public static abstract interface Predicate<T>
  {
    public abstract boolean apply(T paramT);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/internal/Utility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */