package com.parse;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import bolts.Continuation;
import bolts.Task;
import com.parse.http.ParseNetworkInterceptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Parse
{
  public static final int LOG_LEVEL_DEBUG = 3;
  public static final int LOG_LEVEL_ERROR = 6;
  public static final int LOG_LEVEL_INFO = 4;
  public static final int LOG_LEVEL_NONE = Integer.MAX_VALUE;
  public static final int LOG_LEVEL_VERBOSE = 2;
  public static final int LOG_LEVEL_WARNING = 5;
  private static final Object MUTEX = new Object();
  private static final Object MUTEX_CALLBACKS = new Object();
  private static final String PARSE_APPLICATION_ID = "com.parse.APPLICATION_ID";
  private static final String PARSE_CLIENT_KEY = "com.parse.CLIENT_KEY";
  private static Set<ParseCallbacks> callbacks = new HashSet();
  static ParseEventuallyQueue eventuallyQueue = null;
  private static List<ParseNetworkInterceptor> interceptors;
  private static boolean isLocalDatastoreEnabled;
  private static OfflineStore offlineStore;
  
  private Parse()
  {
    throw new AssertionError();
  }
  
  public static void addParseNetworkInterceptor(ParseNetworkInterceptor paramParseNetworkInterceptor)
  {
    if (isInitialized()) {
      throw new IllegalStateException("`Parse#addParseNetworkInterceptor(ParseNetworkInterceptor)` must be invoked before `Parse#initialize(Context)`");
    }
    if (interceptors == null) {
      interceptors = new ArrayList();
    }
    interceptors.add(paramParseNetworkInterceptor);
  }
  
  private static boolean allParsePushIntentReceiversInternal()
  {
    Iterator localIterator = ManifestInfo.getIntentReceivers(new String[] { "com.parse.push.intent.RECEIVE", "com.parse.push.intent.DELETE", "com.parse.push.intent.OPEN" }).iterator();
    while (localIterator.hasNext()) {
      if (((ResolveInfo)localIterator.next()).activityInfo.exported) {
        return false;
      }
    }
    return true;
  }
  
  static void checkCacheApplicationId()
  {
    String str;
    Object localObject3;
    synchronized (MUTEX)
    {
      str = ParsePlugins.get().applicationId();
      Object localObject4;
      boolean bool1;
      if (str != null)
      {
        localObject3 = getParseCacheDir();
        localObject4 = new File((File)localObject3, "applicationId");
        bool1 = ((File)localObject4).exists();
        if (bool1) {
          bool1 = false;
        }
      }
      try
      {
        localObject4 = new RandomAccessFile((File)localObject4, "r");
        byte[] arrayOfByte = new byte[(int)((RandomAccessFile)localObject4).length()];
        ((RandomAccessFile)localObject4).readFully(arrayOfByte);
        ((RandomAccessFile)localObject4).close();
        boolean bool2 = new String(arrayOfByte, "UTF-8").equals(str);
        bool1 = bool2;
      }
      catch (IOException localIOException3)
      {
        for (;;) {}
      }
      catch (FileNotFoundException localFileNotFoundException2)
      {
        for (;;) {}
      }
      if (bool1) {}
    }
    try
    {
      ParseFileUtils.deleteDirectory((File)localObject3);
      localObject3 = new File((File)localObject3, "applicationId");
    }
    catch (IOException localIOException2)
    {
      try
      {
        localObject3 = new FileOutputStream((File)localObject3);
        ((FileOutputStream)localObject3).write(str.getBytes("UTF-8"));
        ((FileOutputStream)localObject3).close();
        return;
        localObject2 = finally;
        throw ((Throwable)localObject2);
        localIOException2 = localIOException2;
      }
      catch (IOException localIOException1)
      {
        for (;;) {}
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        for (;;) {}
      }
      catch (FileNotFoundException localFileNotFoundException1)
      {
        for (;;) {}
      }
    }
  }
  
  static void checkContext()
  {
    if (ParsePlugins.Android.get().applicationContext() == null) {
      throw new RuntimeException("applicationContext is null. You must call Parse.initialize(Context) before using the Parse library.");
    }
  }
  
  static void checkInit()
  {
    if (ParsePlugins.get() == null) {
      throw new RuntimeException("You must call Parse.initialize(Context) before using the Parse library.");
    }
    if (ParsePlugins.get().applicationId() == null) {
      throw new RuntimeException("applicationId is null. You must call Parse.initialize(Context) before using the Parse library.");
    }
    if (ParsePlugins.get().clientKey() == null) {
      throw new RuntimeException("clientKey is null. You must call Parse.initialize(Context) before using the Parse library.");
    }
  }
  
  private static ParseCallbacks[] collectParseCallbacks()
  {
    synchronized (MUTEX_CALLBACKS)
    {
      if (callbacks == null) {
        return null;
      }
      ParseCallbacks[] arrayOfParseCallbacks2 = new ParseCallbacks[callbacks.size()];
      ParseCallbacks[] arrayOfParseCallbacks1 = arrayOfParseCallbacks2;
      if (callbacks.size() > 0) {
        arrayOfParseCallbacks1 = (ParseCallbacks[])callbacks.toArray(arrayOfParseCallbacks2);
      }
      return arrayOfParseCallbacks1;
    }
  }
  
  static void destroy()
  {
    synchronized (MUTEX)
    {
      ParseEventuallyQueue localParseEventuallyQueue = eventuallyQueue;
      eventuallyQueue = null;
      if (localParseEventuallyQueue != null) {
        localParseEventuallyQueue.onDestroy();
      }
      ParseCorePlugins.getInstance().reset();
      ParsePlugins.reset();
      return;
    }
  }
  
  static void disableLocalDatastore()
  {
    setLocalDatastore(null);
    ParseCorePlugins.getInstance().reset();
  }
  
  private static void dispatchOnParseInitialized()
  {
    ParseCallbacks[] arrayOfParseCallbacks = collectParseCallbacks();
    if (arrayOfParseCallbacks != null)
    {
      int j = arrayOfParseCallbacks.length;
      int i = 0;
      while (i < j)
      {
        arrayOfParseCallbacks[i].onParseInitialized();
        i += 1;
      }
    }
  }
  
  public static void enableLocalDatastore(Context paramContext)
  {
    if (isInitialized()) {
      throw new IllegalStateException("`Parse#enableLocalDatastore(Context)` must be invoked before `Parse#initialize(Context)`");
    }
    isLocalDatastoreEnabled = true;
  }
  
  static String externalVersionName()
  {
    return "a1.10.2";
  }
  
  static Context getApplicationContext()
  {
    checkContext();
    return ParsePlugins.Android.get().applicationContext();
  }
  
  static ParseEventuallyQueue getEventuallyQueue()
  {
    Context localContext = ParsePlugins.Android.get().applicationContext();
    synchronized (MUTEX)
    {
      boolean bool = isLocalDatastoreEnabled();
      if ((eventuallyQueue == null) || ((bool) && ((eventuallyQueue instanceof ParseCommandCache))) || ((!bool) && ((eventuallyQueue instanceof ParsePinningEventuallyQueue))))
      {
        checkContext();
        if (!bool) {
          break label96;
        }
        localObject1 = new ParsePinningEventuallyQueue(localContext);
        eventuallyQueue = (ParseEventuallyQueue)localObject1;
        if ((bool) && (ParseCommandCache.getPendingCount() > 0)) {
          new ParseCommandCache(localContext);
        }
      }
      Object localObject1 = eventuallyQueue;
      return (ParseEventuallyQueue)localObject1;
      label96:
      localObject1 = new ParseCommandCache(localContext);
    }
  }
  
  static OfflineStore getLocalDatastore()
  {
    return offlineStore;
  }
  
  public static int getLogLevel()
  {
    return PLog.getLogLevel();
  }
  
  static File getParseCacheDir()
  {
    return ParsePlugins.get().getCacheDir();
  }
  
  static File getParseCacheDir(String paramString)
  {
    synchronized (MUTEX)
    {
      paramString = new File(getParseCacheDir(), paramString);
      if (!paramString.exists()) {
        paramString.mkdirs();
      }
      return paramString;
    }
  }
  
  @Deprecated
  static File getParseDir()
  {
    return ParsePlugins.get().getParseDir();
  }
  
  static File getParseFilesDir()
  {
    return ParsePlugins.get().getFilesDir();
  }
  
  static File getParseFilesDir(String paramString)
  {
    synchronized (MUTEX)
    {
      paramString = new File(getParseFilesDir(), paramString);
      if (!paramString.exists()) {
        paramString.mkdirs();
      }
      return paramString;
    }
  }
  
  static boolean hasPermission(String paramString)
  {
    return getApplicationContext().checkCallingOrSelfPermission(paramString) == 0;
  }
  
  public static void initialize(Context paramContext)
  {
    Object localObject = ManifestInfo.getApplicationMetadata(paramContext.getApplicationContext());
    String str;
    if (localObject != null)
    {
      str = ((Bundle)localObject).getString("com.parse.APPLICATION_ID");
      localObject = ((Bundle)localObject).getString("com.parse.CLIENT_KEY");
      if (str == null) {
        throw new RuntimeException("ApplicationId not defined. You must provide ApplicationId in AndroidManifest.xml.\n<meta-data\n    android:name=\"com.parse.APPLICATION_ID\"\n    android:value=\"<Your Application Id>\" />");
      }
      if (localObject == null) {
        throw new RuntimeException("ClientKey not defined. You must provide ClientKey in AndroidManifest.xml.\n<meta-data\n    android:name=\"com.parse.CLIENT_KEY\"\n    android:value=\"<Your Client Key>\" />");
      }
    }
    else
    {
      throw new RuntimeException("Can't get Application Metadata");
    }
    initialize(paramContext, str, (String)localObject);
  }
  
  public static void initialize(Context arg0, String paramString1, String paramString2)
  {
    ParsePlugins.Android.initialize(???, paramString1, paramString2);
    paramString1 = ???.getApplicationContext();
    ParseHttpClient.setKeepAlive(true);
    ParseHttpClient.setMaxConnections(20);
    ParseRequest.setDefaultClient(ParsePlugins.get().restClient());
    if (interceptors != null) {
      initializeParseHttpClientsWithParseNetworkInterceptors();
    }
    ParseObject.registerParseSubclasses();
    if (isLocalDatastoreEnabled()) {
      offlineStore = new OfflineStore(???);
    }
    for (;;)
    {
      checkCacheApplicationId();
      new Thread("Parse.initialize Disk Check & Starting Command Cache")
      {
        public void run()
        {
          Parse.getEventuallyQueue();
        }
      }.start();
      ParseFieldOperations.registerDefaultDecoders();
      if (allParsePushIntentReceiversInternal()) {
        break;
      }
      throw new SecurityException("To prevent external tampering to your app's notifications, all receivers registered to handle the following actions must have their exported attributes set to false: com.parse.push.intent.RECEIVE, com.parse.push.intent.OPEN, com.parse.push.intent.DELETE");
      ParseKeyValueCache.initialize(???);
    }
    GcmRegistrar.getInstance().registerAsync().continueWithTask(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return ParseUser.getCurrentUserAsync().makeVoid();
      }
    }).continueWith(new Continuation()
    {
      public Void then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        ParseConfig.getCurrentConfig();
        return null;
      }
    }, Task.BACKGROUND_EXECUTOR);
    if (ManifestInfo.getPushType() == PushType.PPNS) {
      PushService.startServiceIfRequired(paramString1);
    }
    dispatchOnParseInitialized();
    synchronized (MUTEX_CALLBACKS)
    {
      callbacks = null;
      return;
    }
  }
  
  private static void initializeParseHttpClientsWithParseNetworkInterceptors()
  {
    if (interceptors == null) {
      return;
    }
    Object localObject = new ArrayList();
    ((List)localObject).add(ParsePlugins.get().restClient());
    ((List)localObject).add(ParseCorePlugins.getInstance().getFileController().awsClient());
    localObject = ((List)localObject).iterator();
    while (((Iterator)localObject).hasNext())
    {
      ParseHttpClient localParseHttpClient = (ParseHttpClient)((Iterator)localObject).next();
      localParseHttpClient.addInternalInterceptor(new ParseDecompressInterceptor());
      Iterator localIterator = interceptors.iterator();
      while (localIterator.hasNext()) {
        localParseHttpClient.addExternalInterceptor((ParseNetworkInterceptor)localIterator.next());
      }
    }
    interceptors = null;
  }
  
  static boolean isInitialized()
  {
    return ParsePlugins.get() != null;
  }
  
  static boolean isLocalDatastoreEnabled()
  {
    return isLocalDatastoreEnabled;
  }
  
  static void registerParseCallbacks(ParseCallbacks paramParseCallbacks)
  {
    if (isInitialized()) {
      throw new IllegalStateException("You must register callbacks before Parse.initialize(Context)");
    }
    synchronized (MUTEX_CALLBACKS)
    {
      if (callbacks == null) {
        return;
      }
      callbacks.add(paramParseCallbacks);
      return;
    }
  }
  
  public static void removeParseNetworkInterceptor(ParseNetworkInterceptor paramParseNetworkInterceptor)
  {
    if (isInitialized()) {
      throw new IllegalStateException("`Parse#addParseNetworkInterceptor(ParseNetworkInterceptor)` must be invoked before `Parse#initialize(Context)`");
    }
    if (interceptors == null) {
      return;
    }
    interceptors.remove(paramParseNetworkInterceptor);
  }
  
  static void requirePermission(String paramString)
  {
    if (!hasPermission(paramString)) {
      throw new IllegalStateException("To use this functionality, add this to your AndroidManifest.xml:\n<uses-permission android:name=\"" + paramString + "\" />");
    }
  }
  
  static void setLocalDatastore(OfflineStore paramOfflineStore)
  {
    if (paramOfflineStore != null) {}
    for (boolean bool = true;; bool = false)
    {
      isLocalDatastoreEnabled = bool;
      offlineStore = paramOfflineStore;
      return;
    }
  }
  
  public static void setLogLevel(int paramInt)
  {
    PLog.setLogLevel(paramInt);
  }
  
  static void unregisterParseCallbacks(ParseCallbacks paramParseCallbacks)
  {
    synchronized (MUTEX_CALLBACKS)
    {
      if (callbacks == null) {
        return;
      }
      callbacks.remove(paramParseCallbacks);
      return;
    }
  }
  
  static abstract interface ParseCallbacks
  {
    public abstract void onParseInitialized();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/Parse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */