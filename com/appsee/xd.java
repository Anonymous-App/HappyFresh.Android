package com.appsee;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;

class xd
{
  public xd(int paramInt1, int paramInt2)
  {
    Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
    this.k = localBitmap;
    this.G = new Canvas(localBitmap);
  }
  
  public Bitmap i()
  {
    return this.k;
  }
  
  public Canvas i()
  {
    return this.G;
  }
  
  public void i()
  {
    this.k.recycle();
    this.k = null;
    this.G = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/xd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */