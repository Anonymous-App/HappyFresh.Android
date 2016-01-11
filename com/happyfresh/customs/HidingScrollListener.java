package com.happyfresh.customs;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;

public abstract class HidingScrollListener
  extends RecyclerView.OnScrollListener
{
  private static final int HIDE_THRESHOLD = 20;
  private boolean controlsVisible = true;
  private int scrolledDistance = 0;
  
  public abstract void onHide();
  
  public void onScrolled(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
  {
    super.onScrolled(paramRecyclerView, paramInt1, paramInt2);
    if ((this.scrolledDistance > 20) && (this.controlsVisible))
    {
      onHide();
      this.controlsVisible = false;
    }
    for (this.scrolledDistance = 0;; this.scrolledDistance = 0)
    {
      do
      {
        if (((this.controlsVisible) && (paramInt2 > 0)) || ((!this.controlsVisible) && (paramInt2 < 0))) {
          this.scrolledDistance += paramInt2;
        }
        return;
      } while ((this.scrolledDistance >= -20) || (this.controlsVisible));
      onShow();
      this.controlsVisible = true;
    }
  }
  
  public abstract void onShow();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/customs/HidingScrollListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */