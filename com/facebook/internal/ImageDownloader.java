package com.facebook.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.facebook.FacebookException;
import com.facebook.R.string;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class ImageDownloader
{
  private static final int CACHE_READ_QUEUE_MAX_CONCURRENT = 2;
  private static final int DOWNLOAD_QUEUE_MAX_CONCURRENT = 8;
  private static WorkQueue cacheReadQueue = new WorkQueue(2);
  private static WorkQueue downloadQueue = new WorkQueue(8);
  private static Handler handler;
  private static final Map<RequestKey, DownloaderContext> pendingRequests = new HashMap();
  
  public static boolean cancelRequest(ImageRequest arg0)
  {
    boolean bool = false;
    RequestKey localRequestKey = new RequestKey(???.getImageUri(), ???.getCallerTag());
    synchronized (pendingRequests)
    {
      DownloaderContext localDownloaderContext = (DownloaderContext)pendingRequests.get(localRequestKey);
      if (localDownloaderContext != null)
      {
        bool = true;
        if (localDownloaderContext.workItem.cancel()) {
          pendingRequests.remove(localRequestKey);
        }
      }
      else
      {
        return bool;
      }
      localDownloaderContext.isCancelled = true;
    }
  }
  
  public static void clearCache(Context paramContext)
  {
    ImageResponseCache.clearCache(paramContext);
    UrlRedirectCache.clearCache();
  }
  
  private static void download(RequestKey paramRequestKey, Context paramContext)
  {
    Object localObject6 = null;
    Object localObject5 = null;
    Object localObject10 = null;
    Object localObject11 = null;
    Object localObject9 = null;
    char[] arrayOfChar = null;
    Object localObject8 = null;
    Object localObject7 = null;
    int k = 1;
    int j = 1;
    int i = j;
    Object localObject1 = localObject9;
    Object localObject2 = localObject11;
    try
    {
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(paramRequestKey.uri.toString()).openConnection();
      localObject5 = localHttpURLConnection;
      i = j;
      localObject1 = localObject9;
      localObject6 = localHttpURLConnection;
      localObject2 = localObject11;
      localHttpURLConnection.setInstanceFollowRedirects(false);
      localObject5 = localHttpURLConnection;
      i = j;
      localObject1 = localObject9;
      localObject6 = localHttpURLConnection;
      localObject2 = localObject11;
      switch (localHttpURLConnection.getResponseCode())
      {
      }
      Object localObject4;
      Object localObject3;
      int m;
      Object localObject12;
      DownloaderContext localDownloaderContext;
      for (;;)
      {
        localObject5 = localHttpURLConnection;
        i = j;
        localObject1 = localObject9;
        localObject6 = localHttpURLConnection;
        localObject2 = localObject11;
        localObject4 = localHttpURLConnection.getErrorStream();
        localObject5 = localHttpURLConnection;
        i = j;
        localObject1 = localObject4;
        localObject6 = localHttpURLConnection;
        localObject2 = localObject4;
        localObject3 = new StringBuilder();
        if (localObject4 != null)
        {
          localObject5 = localHttpURLConnection;
          i = j;
          localObject1 = localObject4;
          localObject6 = localHttpURLConnection;
          localObject2 = localObject4;
          paramContext = new InputStreamReader((InputStream)localObject4);
          localObject5 = localHttpURLConnection;
          i = j;
          localObject1 = localObject4;
          localObject6 = localHttpURLConnection;
          localObject2 = localObject4;
          arrayOfChar = new char[''];
          for (;;)
          {
            localObject5 = localHttpURLConnection;
            i = j;
            localObject1 = localObject4;
            localObject6 = localHttpURLConnection;
            localObject2 = localObject4;
            m = paramContext.read(arrayOfChar, 0, arrayOfChar.length);
            if (m <= 0) {
              break;
            }
            localObject5 = localHttpURLConnection;
            i = j;
            localObject1 = localObject4;
            localObject6 = localHttpURLConnection;
            localObject2 = localObject4;
            ((StringBuilder)localObject3).append(arrayOfChar, 0, m);
          }
        }
      }
    }
    catch (IOException paramContext)
    {
      Utility.closeQuietly((Closeable)localObject1);
      Utility.disconnectQuietly((URLConnection)localObject5);
      localObject3 = paramContext;
      paramContext = (Context)localObject7;
      if (i != 0) {
        issueResponse(paramRequestKey, (Exception)localObject3, paramContext, false);
      }
      return;
      m = 0;
      k = 0;
      localObject5 = localHttpURLConnection;
      i = k;
      localObject1 = localObject9;
      localObject6 = localHttpURLConnection;
      localObject2 = localObject11;
      localObject12 = localHttpURLConnection.getHeaderField("location");
      localObject5 = localHttpURLConnection;
      i = k;
      localObject1 = localObject9;
      paramContext = (Context)localObject8;
      localObject3 = arrayOfChar;
      j = m;
      localObject4 = localObject10;
      localObject6 = localHttpURLConnection;
      localObject2 = localObject11;
      if (!Utility.isNullOrEmpty((String)localObject12))
      {
        localObject5 = localHttpURLConnection;
        i = k;
        localObject1 = localObject9;
        localObject6 = localHttpURLConnection;
        localObject2 = localObject11;
        localObject12 = Uri.parse((String)localObject12);
        localObject5 = localHttpURLConnection;
        i = k;
        localObject1 = localObject9;
        localObject6 = localHttpURLConnection;
        localObject2 = localObject11;
        UrlRedirectCache.cacheUriRedirect(paramRequestKey.uri, (Uri)localObject12);
        localObject5 = localHttpURLConnection;
        i = k;
        localObject1 = localObject9;
        localObject6 = localHttpURLConnection;
        localObject2 = localObject11;
        localDownloaderContext = removePendingRequest(paramRequestKey);
        paramContext = (Context)localObject8;
        localObject3 = arrayOfChar;
        j = m;
        localObject4 = localObject10;
        if (localDownloaderContext != null)
        {
          localObject5 = localHttpURLConnection;
          i = k;
          localObject1 = localObject9;
          paramContext = (Context)localObject8;
          localObject3 = arrayOfChar;
          j = m;
          localObject4 = localObject10;
          localObject6 = localHttpURLConnection;
          localObject2 = localObject11;
          if (!localDownloaderContext.isCancelled)
          {
            localObject5 = localHttpURLConnection;
            i = k;
            localObject1 = localObject9;
            localObject6 = localHttpURLConnection;
            localObject2 = localObject11;
            enqueueCacheRead(localDownloaderContext.request, new RequestKey((Uri)localObject12, paramRequestKey.tag), false);
            localObject4 = localObject10;
            j = m;
            localObject3 = arrayOfChar;
            paramContext = (Context)localObject8;
          }
        }
      }
      for (;;)
      {
        Utility.closeQuietly((Closeable)localObject4);
        Utility.disconnectQuietly(localHttpURLConnection);
        i = j;
        break;
        localObject5 = localHttpURLConnection;
        i = j;
        localObject1 = localObject9;
        localObject6 = localHttpURLConnection;
        localObject2 = localObject11;
        localObject4 = ImageResponseCache.interceptAndCacheImageStream(paramContext, localHttpURLConnection);
        localObject5 = localHttpURLConnection;
        i = j;
        localObject1 = localObject4;
        localObject6 = localHttpURLConnection;
        localObject2 = localObject4;
        paramContext = BitmapFactory.decodeStream((InputStream)localObject4);
        localObject3 = arrayOfChar;
        j = k;
      }
      localObject5 = localHttpURLConnection;
      i = j;
      localObject1 = localObject4;
      localObject6 = localHttpURLConnection;
      localObject2 = localObject4;
      Utility.closeQuietly(paramContext);
      for (;;)
      {
        localObject5 = localHttpURLConnection;
        i = j;
        localObject1 = localObject4;
        localObject6 = localHttpURLConnection;
        localObject2 = localObject4;
        localObject3 = new FacebookException(((StringBuilder)localObject3).toString());
        paramContext = (Context)localObject8;
        j = k;
        break;
        localObject5 = localHttpURLConnection;
        i = j;
        localObject1 = localObject4;
        localObject6 = localHttpURLConnection;
        localObject2 = localObject4;
        ((StringBuilder)localObject3).append(paramContext.getString(R.string.com_facebook_image_download_unknown_error));
      }
    }
    finally
    {
      Utility.closeQuietly((Closeable)localObject2);
      Utility.disconnectQuietly((URLConnection)localObject6);
    }
  }
  
  public static void downloadAsync(ImageRequest paramImageRequest)
  {
    if (paramImageRequest == null) {
      return;
    }
    RequestKey localRequestKey = new RequestKey(paramImageRequest.getImageUri(), paramImageRequest.getCallerTag());
    for (;;)
    {
      synchronized (pendingRequests)
      {
        DownloaderContext localDownloaderContext = (DownloaderContext)pendingRequests.get(localRequestKey);
        if (localDownloaderContext != null)
        {
          localDownloaderContext.request = paramImageRequest;
          localDownloaderContext.isCancelled = false;
          localDownloaderContext.workItem.moveToFront();
          return;
        }
      }
      enqueueCacheRead(paramImageRequest, localRequestKey, paramImageRequest.isCachedRedirectAllowed());
    }
  }
  
  private static void enqueueCacheRead(ImageRequest paramImageRequest, RequestKey paramRequestKey, boolean paramBoolean)
  {
    enqueueRequest(paramImageRequest, paramRequestKey, cacheReadQueue, new CacheReadWorkItem(paramImageRequest.getContext(), paramRequestKey, paramBoolean));
  }
  
  private static void enqueueDownload(ImageRequest paramImageRequest, RequestKey paramRequestKey)
  {
    enqueueRequest(paramImageRequest, paramRequestKey, downloadQueue, new DownloadImageWorkItem(paramImageRequest.getContext(), paramRequestKey));
  }
  
  private static void enqueueRequest(ImageRequest paramImageRequest, RequestKey paramRequestKey, WorkQueue paramWorkQueue, Runnable paramRunnable)
  {
    synchronized (pendingRequests)
    {
      DownloaderContext localDownloaderContext = new DownloaderContext(null);
      localDownloaderContext.request = paramImageRequest;
      pendingRequests.put(paramRequestKey, localDownloaderContext);
      localDownloaderContext.workItem = paramWorkQueue.addActiveWorkItem(paramRunnable);
      return;
    }
  }
  
  private static Handler getHandler()
  {
    try
    {
      if (handler == null) {
        handler = new Handler(Looper.getMainLooper());
      }
      Handler localHandler = handler;
      return localHandler;
    }
    finally {}
  }
  
  private static void issueResponse(RequestKey paramRequestKey, final Exception paramException, final Bitmap paramBitmap, final boolean paramBoolean)
  {
    paramRequestKey = removePendingRequest(paramRequestKey);
    if ((paramRequestKey != null) && (!paramRequestKey.isCancelled))
    {
      paramRequestKey = paramRequestKey.request;
      final ImageRequest.Callback localCallback = paramRequestKey.getCallback();
      if (localCallback != null) {
        getHandler().post(new Runnable()
        {
          public void run()
          {
            ImageResponse localImageResponse = new ImageResponse(this.val$request, paramException, paramBoolean, paramBitmap);
            localCallback.onCompleted(localImageResponse);
          }
        });
      }
    }
  }
  
  public static void prioritizeRequest(ImageRequest arg0)
  {
    Object localObject1 = new RequestKey(???.getImageUri(), ???.getCallerTag());
    synchronized (pendingRequests)
    {
      localObject1 = (DownloaderContext)pendingRequests.get(localObject1);
      if (localObject1 != null) {
        ((DownloaderContext)localObject1).workItem.moveToFront();
      }
      return;
    }
  }
  
  private static void readFromCache(RequestKey paramRequestKey, Context paramContext, boolean paramBoolean)
  {
    Object localObject2 = null;
    boolean bool2 = false;
    Object localObject1 = localObject2;
    boolean bool1 = bool2;
    if (paramBoolean)
    {
      Uri localUri = UrlRedirectCache.getRedirectedUri(paramRequestKey.uri);
      localObject1 = localObject2;
      bool1 = bool2;
      if (localUri != null)
      {
        localObject1 = ImageResponseCache.getCachedImageStream(localUri, paramContext);
        if (localObject1 == null) {
          break label91;
        }
        bool1 = true;
      }
    }
    if (!bool1) {
      localObject1 = ImageResponseCache.getCachedImageStream(paramRequestKey.uri, paramContext);
    }
    if (localObject1 != null)
    {
      paramContext = BitmapFactory.decodeStream((InputStream)localObject1);
      Utility.closeQuietly((Closeable)localObject1);
      issueResponse(paramRequestKey, null, paramContext, bool1);
    }
    label91:
    do
    {
      return;
      bool1 = false;
      break;
      paramContext = removePendingRequest(paramRequestKey);
    } while ((paramContext == null) || (paramContext.isCancelled));
    enqueueDownload(paramContext.request, paramRequestKey);
  }
  
  private static DownloaderContext removePendingRequest(RequestKey paramRequestKey)
  {
    synchronized (pendingRequests)
    {
      paramRequestKey = (DownloaderContext)pendingRequests.remove(paramRequestKey);
      return paramRequestKey;
    }
  }
  
  private static class CacheReadWorkItem
    implements Runnable
  {
    private boolean allowCachedRedirects;
    private Context context;
    private ImageDownloader.RequestKey key;
    
    CacheReadWorkItem(Context paramContext, ImageDownloader.RequestKey paramRequestKey, boolean paramBoolean)
    {
      this.context = paramContext;
      this.key = paramRequestKey;
      this.allowCachedRedirects = paramBoolean;
    }
    
    public void run()
    {
      ImageDownloader.readFromCache(this.key, this.context, this.allowCachedRedirects);
    }
  }
  
  private static class DownloadImageWorkItem
    implements Runnable
  {
    private Context context;
    private ImageDownloader.RequestKey key;
    
    DownloadImageWorkItem(Context paramContext, ImageDownloader.RequestKey paramRequestKey)
    {
      this.context = paramContext;
      this.key = paramRequestKey;
    }
    
    public void run()
    {
      ImageDownloader.download(this.key, this.context);
    }
  }
  
  private static class DownloaderContext
  {
    boolean isCancelled;
    ImageRequest request;
    WorkQueue.WorkItem workItem;
  }
  
  private static class RequestKey
  {
    private static final int HASH_MULTIPLIER = 37;
    private static final int HASH_SEED = 29;
    Object tag;
    Uri uri;
    
    RequestKey(Uri paramUri, Object paramObject)
    {
      this.uri = paramUri;
      this.tag = paramObject;
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool2 = false;
      boolean bool1 = bool2;
      if (paramObject != null)
      {
        bool1 = bool2;
        if ((paramObject instanceof RequestKey))
        {
          paramObject = (RequestKey)paramObject;
          if ((((RequestKey)paramObject).uri != this.uri) || (((RequestKey)paramObject).tag != this.tag)) {
            break label48;
          }
          bool1 = true;
        }
      }
      return bool1;
      label48:
      return false;
    }
    
    public int hashCode()
    {
      return (this.uri.hashCode() + 1073) * 37 + this.tag.hashCode();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/internal/ImageDownloader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */