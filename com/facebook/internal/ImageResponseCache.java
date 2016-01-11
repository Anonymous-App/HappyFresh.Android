package com.facebook.internal;

import android.content.Context;
import android.net.Uri;
import com.facebook.LoggingBehavior;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class ImageResponseCache
{
  static final String TAG = ImageResponseCache.class.getSimpleName();
  private static volatile FileLruCache imageCache;
  
  static void clearCache(Context paramContext)
  {
    try
    {
      getCache(paramContext).clearCache();
      return;
    }
    catch (IOException paramContext)
    {
      Logger.log(LoggingBehavior.CACHE, 5, TAG, "clearCache failed " + paramContext.getMessage());
    }
  }
  
  static FileLruCache getCache(Context paramContext)
    throws IOException
  {
    try
    {
      if (imageCache == null) {
        imageCache = new FileLruCache(TAG, new FileLruCache.Limits());
      }
      paramContext = imageCache;
      return paramContext;
    }
    finally {}
  }
  
  static InputStream getCachedImageStream(Uri paramUri, Context paramContext)
  {
    Object localObject2 = null;
    Object localObject1 = localObject2;
    if (paramUri != null)
    {
      localObject1 = localObject2;
      if (!isCDNURL(paramUri)) {}
    }
    try
    {
      localObject1 = getCache(paramContext).get(paramUri.toString());
      return (InputStream)localObject1;
    }
    catch (IOException paramUri)
    {
      Logger.log(LoggingBehavior.CACHE, 5, TAG, paramUri.toString());
    }
    return null;
  }
  
  static InputStream interceptAndCacheImageStream(Context paramContext, HttpURLConnection paramHttpURLConnection)
    throws IOException
  {
    Object localObject = null;
    Uri localUri;
    InputStream localInputStream;
    if (paramHttpURLConnection.getResponseCode() == 200)
    {
      localUri = Uri.parse(paramHttpURLConnection.getURL().toString());
      localInputStream = paramHttpURLConnection.getInputStream();
      localObject = localInputStream;
    }
    try
    {
      if (isCDNURL(localUri)) {
        localObject = getCache(paramContext).interceptAndPut(localUri.toString(), new BufferedHttpInputStream(localInputStream, paramHttpURLConnection));
      }
      return (InputStream)localObject;
    }
    catch (IOException paramContext) {}
    return localInputStream;
  }
  
  private static boolean isCDNURL(Uri paramUri)
  {
    if (paramUri != null)
    {
      paramUri = paramUri.getHost();
      if (paramUri.endsWith("fbcdn.net")) {}
      while ((paramUri.startsWith("fbcdn")) && (paramUri.endsWith("akamaihd.net"))) {
        return true;
      }
    }
    return false;
  }
  
  private static class BufferedHttpInputStream
    extends BufferedInputStream
  {
    HttpURLConnection connection;
    
    BufferedHttpInputStream(InputStream paramInputStream, HttpURLConnection paramHttpURLConnection)
    {
      super(8192);
      this.connection = paramHttpURLConnection;
    }
    
    public void close()
      throws IOException
    {
      super.close();
      Utility.disconnectQuietly(this.connection);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/internal/ImageResponseCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */