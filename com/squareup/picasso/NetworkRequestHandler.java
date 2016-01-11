package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.NetworkInfo;
import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;

class NetworkRequestHandler
  extends RequestHandler
{
  private static final int MARKER = 65536;
  static final int RETRY_COUNT = 2;
  private static final String SCHEME_HTTP = "http";
  private static final String SCHEME_HTTPS = "https";
  private final Downloader downloader;
  private final Stats stats;
  
  public NetworkRequestHandler(Downloader paramDownloader, Stats paramStats)
  {
    this.downloader = paramDownloader;
    this.stats = paramStats;
  }
  
  private Bitmap decodeStream(InputStream paramInputStream, Request paramRequest)
    throws IOException
  {
    Object localObject = new MarkableInputStream(paramInputStream);
    long l = ((MarkableInputStream)localObject).savePosition(65536);
    paramInputStream = createBitmapOptions(paramRequest);
    boolean bool1 = requiresInSampleSize(paramInputStream);
    boolean bool2 = Utils.isWebPFile((InputStream)localObject);
    ((MarkableInputStream)localObject).reset(l);
    if (bool2)
    {
      localObject = Utils.toByteArray((InputStream)localObject);
      if (bool1)
      {
        BitmapFactory.decodeByteArray((byte[])localObject, 0, localObject.length, paramInputStream);
        calculateInSampleSize(paramRequest.targetWidth, paramRequest.targetHeight, paramInputStream, paramRequest);
      }
      paramInputStream = BitmapFactory.decodeByteArray((byte[])localObject, 0, localObject.length, paramInputStream);
    }
    do
    {
      return paramInputStream;
      if (bool1)
      {
        BitmapFactory.decodeStream((InputStream)localObject, null, paramInputStream);
        calculateInSampleSize(paramRequest.targetWidth, paramRequest.targetHeight, paramInputStream, paramRequest);
        ((MarkableInputStream)localObject).reset(l);
      }
      paramRequest = BitmapFactory.decodeStream((InputStream)localObject, null, paramInputStream);
      paramInputStream = paramRequest;
    } while (paramRequest != null);
    throw new IOException("Failed to decode stream.");
  }
  
  public boolean canHandleRequest(Request paramRequest)
  {
    paramRequest = paramRequest.uri.getScheme();
    return ("http".equals(paramRequest)) || ("https".equals(paramRequest));
  }
  
  int getRetryCount()
  {
    return 2;
  }
  
  public RequestHandler.Result load(Request paramRequest)
    throws IOException
  {
    Downloader.Response localResponse = this.downloader.load(paramRequest.uri, paramRequest.loadFromLocalCacheOnly);
    if (localResponse == null) {}
    Picasso.LoadedFrom localLoadedFrom;
    Object localObject;
    do
    {
      return null;
      if (localResponse.cached) {}
      for (localLoadedFrom = Picasso.LoadedFrom.DISK;; localLoadedFrom = Picasso.LoadedFrom.NETWORK)
      {
        localObject = localResponse.getBitmap();
        if (localObject == null) {
          break;
        }
        return new RequestHandler.Result((Bitmap)localObject, localLoadedFrom);
      }
      localObject = localResponse.getInputStream();
    } while (localObject == null);
    if (localResponse.getContentLength() == 0L)
    {
      Utils.closeQuietly((InputStream)localObject);
      throw new IOException("Received response with 0 content-length header.");
    }
    if ((localLoadedFrom == Picasso.LoadedFrom.NETWORK) && (localResponse.getContentLength() > 0L)) {
      this.stats.dispatchDownloadFinished(localResponse.getContentLength());
    }
    try
    {
      paramRequest = new RequestHandler.Result(decodeStream((InputStream)localObject, paramRequest), localLoadedFrom);
      return paramRequest;
    }
    finally
    {
      Utils.closeQuietly((InputStream)localObject);
    }
  }
  
  boolean shouldRetry(boolean paramBoolean, NetworkInfo paramNetworkInfo)
  {
    return (paramNetworkInfo == null) || (paramNetworkInfo.isConnected());
  }
  
  boolean supportsReplay()
  {
    return true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/squareup/picasso/NetworkRequestHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */