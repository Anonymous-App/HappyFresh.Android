package com.parse;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.SSLSessionCache;
import android.os.Build.VERSION;
import com.parse.http.ParseHttpRequest;
import com.parse.http.ParseHttpRequest.Builder;
import com.parse.http.ParseHttpResponse;
import com.parse.http.ParseNetworkInterceptor;
import com.parse.http.ParseNetworkInterceptor.Chain;
import java.io.File;
import java.io.IOException;

class ParsePlugins
{
  private static final String INSTALLATION_ID_LOCATION = "installationId";
  private static final Object LOCK = new Object();
  private static ParsePlugins instance;
  private final String applicationId;
  File cacheDir;
  private final String clientKey;
  File filesDir;
  private InstallationId installationId;
  final Object lock = new Object();
  File parseDir;
  private ParseHttpClient restClient;
  
  private ParsePlugins(String paramString1, String paramString2)
  {
    this.applicationId = paramString1;
    this.clientKey = paramString2;
  }
  
  private static File createFileDir(File paramFile)
  {
    if ((!paramFile.exists()) && (!paramFile.mkdirs())) {}
    return paramFile;
  }
  
  static ParsePlugins get()
  {
    synchronized (LOCK)
    {
      ParsePlugins localParsePlugins = instance;
      return localParsePlugins;
    }
  }
  
  static void initialize(String paramString1, String paramString2)
  {
    set(new ParsePlugins(paramString1, paramString2));
  }
  
  static void reset()
  {
    synchronized (LOCK)
    {
      instance = null;
      return;
    }
  }
  
  static void set(ParsePlugins paramParsePlugins)
  {
    synchronized (LOCK)
    {
      if (instance != null) {
        throw new IllegalStateException("ParsePlugins is already initialized");
      }
    }
    instance = paramParsePlugins;
  }
  
  String applicationId()
  {
    return this.applicationId;
  }
  
  String clientKey()
  {
    return this.clientKey;
  }
  
  File getCacheDir()
  {
    throw new IllegalStateException("Stub");
  }
  
  File getFilesDir()
  {
    throw new IllegalStateException("Stub");
  }
  
  @Deprecated
  File getParseDir()
  {
    throw new IllegalStateException("Stub");
  }
  
  InstallationId installationId()
  {
    synchronized (this.lock)
    {
      if (this.installationId == null) {
        this.installationId = new InstallationId(new File(getParseDir(), "installationId"));
      }
      InstallationId localInstallationId = this.installationId;
      return localInstallationId;
    }
  }
  
  ParseHttpClient newHttpClient()
  {
    return ParseHttpClient.createClient(10000, null);
  }
  
  ParseHttpClient restClient()
  {
    synchronized (this.lock)
    {
      if (this.restClient == null)
      {
        this.restClient = newHttpClient();
        this.restClient.addInternalInterceptor(new ParseNetworkInterceptor()
        {
          public ParseHttpResponse intercept(ParseNetworkInterceptor.Chain paramAnonymousChain)
            throws IOException
          {
            ParseHttpRequest localParseHttpRequest = paramAnonymousChain.getRequest();
            ParseHttpRequest.Builder localBuilder = new ParseHttpRequest.Builder(localParseHttpRequest).addHeader("X-Parse-Application-Id", ParsePlugins.this.applicationId).addHeader("X-Parse-Client-Key", ParsePlugins.this.clientKey).addHeader("X-Parse-Client-Version", Parse.externalVersionName()).addHeader("X-Parse-App-Build-Version", String.valueOf(ManifestInfo.getVersionCode())).addHeader("X-Parse-App-Display-Version", ManifestInfo.getVersionName()).addHeader("X-Parse-OS-Version", Build.VERSION.RELEASE).addHeader("User-Agent", ParsePlugins.this.userAgent());
            if (localParseHttpRequest.getHeader("X-Parse-Installation-Id") == null) {
              localBuilder.addHeader("X-Parse-Installation-Id", ParsePlugins.this.installationId().get());
            }
            return paramAnonymousChain.proceed(localBuilder.build());
          }
        });
      }
      ParseHttpClient localParseHttpClient = this.restClient;
      return localParseHttpClient;
    }
  }
  
  String userAgent()
  {
    return "Parse Java SDK";
  }
  
  static class Android
    extends ParsePlugins
  {
    private final Context applicationContext;
    
    private Android(Context paramContext, String paramString1, String paramString2)
    {
      super(paramString2, null);
      this.applicationContext = paramContext.getApplicationContext();
    }
    
    static Android get()
    {
      return (Android)ParsePlugins.get();
    }
    
    static void initialize(Context paramContext, String paramString1, String paramString2)
    {
      ParsePlugins.set(new Android(paramContext, paramString1, paramString2));
    }
    
    Context applicationContext()
    {
      return this.applicationContext;
    }
    
    File getCacheDir()
    {
      synchronized (this.lock)
      {
        if (this.cacheDir == null) {
          this.cacheDir = new File(this.applicationContext.getCacheDir(), "com.parse");
        }
        File localFile = ParsePlugins.createFileDir(this.cacheDir);
        return localFile;
      }
    }
    
    File getFilesDir()
    {
      synchronized (this.lock)
      {
        if (this.filesDir == null) {
          this.filesDir = new File(this.applicationContext.getFilesDir(), "com.parse");
        }
        File localFile = ParsePlugins.createFileDir(this.filesDir);
        return localFile;
      }
    }
    
    File getParseDir()
    {
      synchronized (this.lock)
      {
        if (this.parseDir == null) {
          this.parseDir = this.applicationContext.getDir("Parse", 0);
        }
        File localFile = ParsePlugins.createFileDir(this.parseDir);
        return localFile;
      }
    }
    
    public ParseHttpClient newHttpClient()
    {
      return ParseHttpClient.createClient(10000, new SSLSessionCache(this.applicationContext));
    }
    
    String userAgent()
    {
      Object localObject = "unknown";
      try
      {
        String str = this.applicationContext.getPackageName();
        int i = this.applicationContext.getPackageManager().getPackageInfo(str, 0).versionCode;
        str = str + "/" + i;
        localObject = str;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        for (;;) {}
      }
      return "Parse Android SDK 1.10.2 (" + (String)localObject + ") API Level " + Build.VERSION.SDK_INT;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParsePlugins.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */