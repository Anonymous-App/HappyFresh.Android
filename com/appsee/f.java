package com.appsee;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;

class f
  extends TouchDelegate
{
  public f(n paramn, View paramView) {}
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    ol.l(new b(this, paramMotionEvent));
    if (this.i != null) {
      return this.i.onTouchEvent(paramMotionEvent);
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */