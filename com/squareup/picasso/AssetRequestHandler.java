package com.squareup.picasso;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import java.io.IOException;
import java.util.List;

class AssetRequestHandler
  extends RequestHandler
{
  protected static final String ANDROID_ASSET = "android_asset";
  private static final int ASSET_PREFIX_LENGTH = "file:///android_asset/".length();
  private final AssetManager assetManager;
  
  public AssetRequestHandler(Context paramContext)
  {
    this.assetManager = paramContext.getAssets();
  }
  
  public boolean canHandleRequest(Request paramRequest)
  {
    boolean bool2 = false;
    paramRequest = paramRequest.uri;
    boolean bool1 = bool2;
    if ("file".equals(paramRequest.getScheme()))
    {
      bool1 = bool2;
      if (!paramRequest.getPathSegments().isEmpty())
      {
        bool1 = bool2;
        if ("android_asset".equals(paramRequest.getPathSegments().get(0))) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  /* Error */
  android.graphics.Bitmap decodeAsset(Request paramRequest, String paramString)
    throws IOException
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 79	com/squareup/picasso/AssetRequestHandler:createBitmapOptions	(Lcom/squareup/picasso/Request;)Landroid/graphics/BitmapFactory$Options;
    //   4: astore 5
    //   6: aload 5
    //   8: invokestatic 83	com/squareup/picasso/AssetRequestHandler:requiresInSampleSize	(Landroid/graphics/BitmapFactory$Options;)Z
    //   11: ifeq +46 -> 57
    //   14: aconst_null
    //   15: astore_3
    //   16: aload_0
    //   17: getfield 37	com/squareup/picasso/AssetRequestHandler:assetManager	Landroid/content/res/AssetManager;
    //   20: aload_2
    //   21: invokevirtual 89	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
    //   24: astore 4
    //   26: aload 4
    //   28: astore_3
    //   29: aload 4
    //   31: aconst_null
    //   32: aload 5
    //   34: invokestatic 95	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;Landroid/graphics/Rect;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   37: pop
    //   38: aload 4
    //   40: invokestatic 101	com/squareup/picasso/Utils:closeQuietly	(Ljava/io/InputStream;)V
    //   43: aload_1
    //   44: getfield 104	com/squareup/picasso/Request:targetWidth	I
    //   47: aload_1
    //   48: getfield 107	com/squareup/picasso/Request:targetHeight	I
    //   51: aload 5
    //   53: aload_1
    //   54: invokestatic 111	com/squareup/picasso/AssetRequestHandler:calculateInSampleSize	(IILandroid/graphics/BitmapFactory$Options;Lcom/squareup/picasso/Request;)V
    //   57: aload_0
    //   58: getfield 37	com/squareup/picasso/AssetRequestHandler:assetManager	Landroid/content/res/AssetManager;
    //   61: aload_2
    //   62: invokevirtual 89	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
    //   65: astore_1
    //   66: aload_1
    //   67: aconst_null
    //   68: aload 5
    //   70: invokestatic 95	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;Landroid/graphics/Rect;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   73: astore_2
    //   74: aload_1
    //   75: invokestatic 101	com/squareup/picasso/Utils:closeQuietly	(Ljava/io/InputStream;)V
    //   78: aload_2
    //   79: areturn
    //   80: astore_1
    //   81: aload_3
    //   82: invokestatic 101	com/squareup/picasso/Utils:closeQuietly	(Ljava/io/InputStream;)V
    //   85: aload_1
    //   86: athrow
    //   87: astore_2
    //   88: aload_1
    //   89: invokestatic 101	com/squareup/picasso/Utils:closeQuietly	(Ljava/io/InputStream;)V
    //   92: aload_2
    //   93: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	94	0	this	AssetRequestHandler
    //   0	94	1	paramRequest	Request
    //   0	94	2	paramString	String
    //   15	67	3	localObject	Object
    //   24	15	4	localInputStream	java.io.InputStream
    //   4	65	5	localOptions	android.graphics.BitmapFactory.Options
    // Exception table:
    //   from	to	target	type
    //   16	26	80	finally
    //   29	38	80	finally
    //   66	74	87	finally
  }
  
  public RequestHandler.Result load(Request paramRequest)
    throws IOException
  {
    return new RequestHandler.Result(decodeAsset(paramRequest, paramRequest.uri.toString().substring(ASSET_PREFIX_LENGTH)), Picasso.LoadedFrom.DISK);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/squareup/picasso/AssetRequestHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */