package com.appsee;

import android.view.MotionEvent;

class kc
{
  public kc(d paramd)
  {
    this.J = paramd;
    this.I = -1;
    this.K = -1;
  }
  
  public boolean i(MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getActionMasked())
    {
    }
    int j;
    do
    {
      do
      {
        return false;
        this.b = false;
        this.I = paramMotionEvent.getPointerId(paramMotionEvent.getActionIndex());
        return false;
      } while ((this.K != -1) || (this.I == -1));
      this.K = paramMotionEvent.getPointerId(paramMotionEvent.getActionIndex());
      this.e = paramMotionEvent.getX(paramMotionEvent.findPointerIndex(this.I));
      this.d = paramMotionEvent.getY(paramMotionEvent.findPointerIndex(this.I));
      this.h = paramMotionEvent.getX(paramMotionEvent.findPointerIndex(this.K));
      this.M = paramMotionEvent.getY(paramMotionEvent.findPointerIndex(this.K));
      return false;
      if (this.b)
      {
        float f = i(this.h, this.M, this.e, this.d, this.C, this.a, this.k, this.G);
        j = i(this.e, this.d, this.h, this.M);
        j = (int)rj.i(i(this.k, this.G, this.C, this.a) - j);
        if (this.J != null)
        {
          if (Math.abs(f) > 30.0F) {
            this.J.i(f, this.i, paramMotionEvent);
          }
          if (Math.abs(j) > 40) {
            this.J.i(j, this.i, paramMotionEvent);
          }
        }
      }
      this.I = -1;
      return false;
      j = paramMotionEvent.getPointerId(paramMotionEvent.getActionIndex());
    } while ((j != this.I) && (j != this.K));
    this.i = new short[] { xb.i().l(this.I), xb.i().l(this.K) };
    this.b = true;
    this.k = paramMotionEvent.getX(paramMotionEvent.findPointerIndex(this.I));
    this.G = paramMotionEvent.getY(paramMotionEvent.findPointerIndex(this.I));
    this.C = paramMotionEvent.getX(paramMotionEvent.findPointerIndex(this.K));
    this.a = paramMotionEvent.getY(paramMotionEvent.findPointerIndex(this.K));
    this.K = -1;
    this.I = -1;
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/kc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */