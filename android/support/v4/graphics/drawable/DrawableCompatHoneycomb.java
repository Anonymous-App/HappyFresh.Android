package android.support.v4.graphics.drawable;

import android.graphics.drawable.Drawable;

class DrawableCompatHoneycomb
{
  public static void jumpToCurrentState(Drawable paramDrawable)
  {
    paramDrawable.jumpToCurrentState();
  }
  
  public static Drawable wrapForTinting(Drawable paramDrawable)
  {
    Object localObject = paramDrawable;
    if (!(paramDrawable instanceof DrawableWrapperHoneycomb)) {
      localObject = new DrawableWrapperHoneycomb(paramDrawable);
    }
    return (Drawable)localObject;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/android/support/v4/graphics/drawable/DrawableCompatHoneycomb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */