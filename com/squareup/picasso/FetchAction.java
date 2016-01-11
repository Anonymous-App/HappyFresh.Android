package com.squareup.picasso;

import android.graphics.Bitmap;

class FetchAction
  extends Action<Object>
{
  private final Object target = new Object();
  
  FetchAction(Picasso paramPicasso, Request paramRequest, boolean paramBoolean, String paramString, Object paramObject)
  {
    super(paramPicasso, null, paramRequest, paramBoolean, false, 0, null, paramString, paramObject);
  }
  
  void complete(Bitmap paramBitmap, Picasso.LoadedFrom paramLoadedFrom) {}
  
  public void error() {}
  
  Object getTarget()
  {
    return this.target;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/squareup/picasso/FetchAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */