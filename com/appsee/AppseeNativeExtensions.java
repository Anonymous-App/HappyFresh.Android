package com.appsee;

import android.graphics.Bitmap;
import java.nio.ByteBuffer;

class AppseeNativeExtensions
{
  static
  {
    K = false;
    k = false;
  }
  
  public static void F()
  {
    if (!K) {
      return;
    }
    ol.i(new mn());
  }
  
  public static boolean F()
  {
    try
    {
      if (!G)
      {
        if (!i(AppseeBackgroundUploader.i("~\no4G.k=S\030Y-N3d\"T!D)["), AppseeBackgroundUploader.i("\017Q)Ja\004\021i;O+\n-[9W3\\\005a,^kH9Aim*O\tz\"\002\004^?Q\026xl[3W>P#]\"N"))) {
          return false;
        }
        G = true;
      }
      return true;
    }
    catch (Throwable localThrowable)
    {
      vd.l(new Exception(localThrowable), AppseeBackgroundUploader.i("\t[(P(\037\035{?_\"@1\023'M.V\fzgG,BzV<U+LvD\"T!D)["));
    }
    return false;
  }
  
  public static void a()
  {
    if (!K) {
      return;
    }
    ol.i(new kn());
  }
  
  public static boolean a()
  {
    try
    {
      if (!K)
      {
        l();
        if ((!i(AppseeBackgroundUploader.i("U>D"))) && (!i(AppseeBackgroundUploader.i("<]<Z"))) && (!i(AppseeBackgroundUploader.i("t\037"))))
        {
          mc.i(AppseeBackgroundUploader.i("\002_%Y&Z>RqX#H:R2^pT\"Gb\021;\033!K;C:LzZ\"Kt\032\rr DoF%Kz[/ZQ`1\033\"@ R%E>\0379O\022\002\nl\023\0370F/A?G5V%A>L"), 1);
          return false;
        }
        i(AppseeBackgroundUploader.i("\033O\nl\"G\005O.V'Q\tQ\"V>F8[\"Z"));
        K = true;
        a();
      }
      return true;
    }
    catch (Throwable localThrowable)
    {
      vd.l(new Exception(localThrowable), AppseeBackgroundUploader.i("o=X#[zS5^\025}0\\kO&C:I?\030\t?)C?G,ZqQ4]3]#A8[\"Z"));
    }
    return false;
  }
  
  public static native void crashNative();
  
  public static String i(int paramInt1, int paramInt2, int paramInt3)
  {
    if (!G) {
      return null;
    }
    return createAndAttachEglImage(paramInt1, paramInt2, paramInt3);
  }
  
  public static String i(Bitmap paramBitmap, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (!G) {
      return null;
    }
    return copyEglImageBufferToBitmap(paramBitmap, paramBoolean1, paramBoolean2);
  }
  
  public static String i(boolean paramBoolean)
  {
    if (!G) {
      return null;
    }
    return changeEGLSwapBehavior(paramBoolean);
  }
  
  public static void i(int paramInt1, int paramInt2, Bitmap paramBitmap, long paramLong, boolean paramBoolean)
  {
    if (!k) {
      return;
    }
    convertAndEncode(paramInt1, paramInt2, paramBitmap, paramLong, paramBoolean);
  }
  
  public static void i(int paramInt1, int paramInt2, Bitmap paramBitmap, ByteBuffer paramByteBuffer, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if (!K) {
      return;
    }
    convertToYuv(paramInt1, paramInt2, paramBitmap, paramByteBuffer, paramBoolean1, paramBoolean2, paramBoolean3);
  }
  
  public static boolean i()
  {
    if (!k) {
      return false;
    }
    return finishEncoding();
  }
  
  public static boolean i(z paramz)
  {
    switch (th.G[paramz.ordinal()])
    {
    default: 
      return true;
    case 1: 
      return a();
    }
    return k();
  }
  
  public static boolean i(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, String[] paramArrayOfString, int[] paramArrayOfInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if (!k) {
      return false;
    }
    return initEncoder(paramString, paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfString, paramArrayOfInt, paramBoolean1, paramBoolean2, paramBoolean3);
  }
  
  public static String l()
  {
    if (!G) {
      return null;
    }
    return disposeEglImage();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/AppseeNativeExtensions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */