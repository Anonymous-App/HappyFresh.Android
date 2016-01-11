package com.squareup.picasso;

import android.content.Context;
import android.net.Uri;
import java.io.IOException;

class ContentStreamRequestHandler
  extends RequestHandler
{
  final Context context;
  
  ContentStreamRequestHandler(Context paramContext)
  {
    this.context = paramContext;
  }
  
  public boolean canHandleRequest(Request paramRequest)
  {
    return "content".equals(paramRequest.uri.getScheme());
  }
  
  /* Error */
  protected android.graphics.Bitmap decodeContentStream(Request paramRequest)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 13	com/squareup/picasso/ContentStreamRequestHandler:context	Landroid/content/Context;
    //   4: invokevirtual 46	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   7: astore 5
    //   9: aload_1
    //   10: invokestatic 50	com/squareup/picasso/ContentStreamRequestHandler:createBitmapOptions	(Lcom/squareup/picasso/Request;)Landroid/graphics/BitmapFactory$Options;
    //   13: astore 4
    //   15: aload 4
    //   17: invokestatic 54	com/squareup/picasso/ContentStreamRequestHandler:requiresInSampleSize	(Landroid/graphics/BitmapFactory$Options;)Z
    //   20: ifeq +43 -> 63
    //   23: aconst_null
    //   24: astore_2
    //   25: aload 5
    //   27: aload_1
    //   28: getfield 24	com/squareup/picasso/Request:uri	Landroid/net/Uri;
    //   31: invokevirtual 60	android/content/ContentResolver:openInputStream	(Landroid/net/Uri;)Ljava/io/InputStream;
    //   34: astore_3
    //   35: aload_3
    //   36: astore_2
    //   37: aload_3
    //   38: aconst_null
    //   39: aload 4
    //   41: invokestatic 66	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;Landroid/graphics/Rect;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   44: pop
    //   45: aload_3
    //   46: invokestatic 72	com/squareup/picasso/Utils:closeQuietly	(Ljava/io/InputStream;)V
    //   49: aload_1
    //   50: getfield 76	com/squareup/picasso/Request:targetWidth	I
    //   53: aload_1
    //   54: getfield 79	com/squareup/picasso/Request:targetHeight	I
    //   57: aload 4
    //   59: aload_1
    //   60: invokestatic 83	com/squareup/picasso/ContentStreamRequestHandler:calculateInSampleSize	(IILandroid/graphics/BitmapFactory$Options;Lcom/squareup/picasso/Request;)V
    //   63: aload 5
    //   65: aload_1
    //   66: getfield 24	com/squareup/picasso/Request:uri	Landroid/net/Uri;
    //   69: invokevirtual 60	android/content/ContentResolver:openInputStream	(Landroid/net/Uri;)Ljava/io/InputStream;
    //   72: astore_1
    //   73: aload_1
    //   74: aconst_null
    //   75: aload 4
    //   77: invokestatic 66	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;Landroid/graphics/Rect;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   80: astore_2
    //   81: aload_1
    //   82: invokestatic 72	com/squareup/picasso/Utils:closeQuietly	(Ljava/io/InputStream;)V
    //   85: aload_2
    //   86: areturn
    //   87: astore_1
    //   88: aload_2
    //   89: invokestatic 72	com/squareup/picasso/Utils:closeQuietly	(Ljava/io/InputStream;)V
    //   92: aload_1
    //   93: athrow
    //   94: astore_2
    //   95: aload_1
    //   96: invokestatic 72	com/squareup/picasso/Utils:closeQuietly	(Ljava/io/InputStream;)V
    //   99: aload_2
    //   100: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	101	0	this	ContentStreamRequestHandler
    //   0	101	1	paramRequest	Request
    //   24	65	2	localObject1	Object
    //   94	6	2	localObject2	Object
    //   34	12	3	localInputStream	java.io.InputStream
    //   13	63	4	localOptions	android.graphics.BitmapFactory.Options
    //   7	57	5	localContentResolver	android.content.ContentResolver
    // Exception table:
    //   from	to	target	type
    //   25	35	87	finally
    //   37	45	87	finally
    //   73	81	94	finally
  }
  
  public RequestHandler.Result load(Request paramRequest)
    throws IOException
  {
    return new RequestHandler.Result(decodeContentStream(paramRequest), Picasso.LoadedFrom.DISK);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/squareup/picasso/ContentStreamRequestHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */