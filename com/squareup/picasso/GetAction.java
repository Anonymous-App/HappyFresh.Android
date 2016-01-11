package com.squareup.picasso;

import android.graphics.Bitmap;

class GetAction
  extends Action<Void>
{
  GetAction(Picasso paramPicasso, Request paramRequest, boolean paramBoolean, String paramString, Object paramObject)
  {
    super(paramPicasso, null, paramRequest, paramBoolean, false, 0, null, paramString, paramObject);
  }
  
  void complete(Bitmap paramBitmap, Picasso.LoadedFrom paramLoadedFrom) {}
  
  public void error() {}
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/squareup/picasso/GetAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */