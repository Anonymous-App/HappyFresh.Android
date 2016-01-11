package com.squareup.picasso;

import android.graphics.Bitmap;

public abstract interface Cache
{
  public static final Cache NONE = new Cache()
  {
    public void clear() {}
    
    public Bitmap get(String paramAnonymousString)
    {
      return null;
    }
    
    public int maxSize()
    {
      return 0;
    }
    
    public void set(String paramAnonymousString, Bitmap paramAnonymousBitmap) {}
    
    public int size()
    {
      return 0;
    }
  };
  
  public abstract void clear();
  
  public abstract Bitmap get(String paramString);
  
  public abstract int maxSize();
  
  public abstract void set(String paramString, Bitmap paramBitmap);
  
  public abstract int size();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/squareup/picasso/Cache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */