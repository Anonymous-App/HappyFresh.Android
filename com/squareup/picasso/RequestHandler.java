package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.net.NetworkInfo;
import java.io.IOException;

public abstract class RequestHandler
{
  static void calculateInSampleSize(int paramInt1, int paramInt2, int paramInt3, int paramInt4, BitmapFactory.Options paramOptions, Request paramRequest)
  {
    int i = 1;
    if ((paramInt4 > paramInt2) || (paramInt3 > paramInt1)) {
      if (paramInt2 != 0) {
        break label43;
      }
    }
    for (i = (int)Math.floor(paramInt3 / paramInt1);; i = (int)Math.floor(paramInt4 / paramInt2))
    {
      paramOptions.inSampleSize = i;
      paramOptions.inJustDecodeBounds = false;
      return;
      label43:
      if (paramInt1 != 0) {
        break;
      }
    }
    paramInt2 = (int)Math.floor(paramInt4 / paramInt2);
    paramInt1 = (int)Math.floor(paramInt3 / paramInt1);
    if (paramRequest.centerInside) {}
    for (i = Math.max(paramInt2, paramInt1);; i = Math.min(paramInt2, paramInt1)) {
      break;
    }
  }
  
  static void calculateInSampleSize(int paramInt1, int paramInt2, BitmapFactory.Options paramOptions, Request paramRequest)
  {
    calculateInSampleSize(paramInt1, paramInt2, paramOptions.outWidth, paramOptions.outHeight, paramOptions, paramRequest);
  }
  
  static BitmapFactory.Options createBitmapOptions(Request paramRequest)
  {
    boolean bool = paramRequest.hasSize();
    if (paramRequest.config != null) {}
    for (int i = 1;; i = 0)
    {
      Object localObject = null;
      if ((bool) || (i != 0))
      {
        BitmapFactory.Options localOptions = new BitmapFactory.Options();
        localOptions.inJustDecodeBounds = bool;
        localObject = localOptions;
        if (i != 0)
        {
          localOptions.inPreferredConfig = paramRequest.config;
          localObject = localOptions;
        }
      }
      return (BitmapFactory.Options)localObject;
    }
  }
  
  static boolean requiresInSampleSize(BitmapFactory.Options paramOptions)
  {
    return (paramOptions != null) && (paramOptions.inJustDecodeBounds);
  }
  
  public abstract boolean canHandleRequest(Request paramRequest);
  
  int getRetryCount()
  {
    return 0;
  }
  
  public abstract Result load(Request paramRequest)
    throws IOException;
  
  boolean shouldRetry(boolean paramBoolean, NetworkInfo paramNetworkInfo)
  {
    return false;
  }
  
  boolean supportsReplay()
  {
    return false;
  }
  
  public static final class Result
  {
    private final Bitmap bitmap;
    private final int exifOrientation;
    private final Picasso.LoadedFrom loadedFrom;
    
    public Result(Bitmap paramBitmap, Picasso.LoadedFrom paramLoadedFrom)
    {
      this(paramBitmap, paramLoadedFrom, 0);
    }
    
    Result(Bitmap paramBitmap, Picasso.LoadedFrom paramLoadedFrom, int paramInt)
    {
      this.bitmap = paramBitmap;
      this.loadedFrom = paramLoadedFrom;
      this.exifOrientation = paramInt;
    }
    
    public Bitmap getBitmap()
    {
      return this.bitmap;
    }
    
    int getExifOrientation()
    {
      return this.exifOrientation;
    }
    
    public Picasso.LoadedFrom getLoadedFrom()
    {
      return this.loadedFrom;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/squareup/picasso/RequestHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */