package com.appsee;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

class o
  implements View.OnTouchListener
{
  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    ol.l(new r(this, paramMotionEvent));
    if (n.i(this.G) != null) {
      return n.i(this.G).onTouch(paramView, paramMotionEvent);
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/o.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */