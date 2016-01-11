package com.timehop.stickyheadersrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class StickyRecyclerHeadersTouchListener
  implements RecyclerView.OnItemTouchListener
{
  private final StickyRecyclerHeadersDecoration mDecor;
  private OnHeaderClickListener mOnHeaderClickListener;
  private final RecyclerView mRecyclerView;
  private final GestureDetector mTapDetector = new GestureDetector(paramRecyclerView.getContext(), new SingleTapDetector(null));
  
  public StickyRecyclerHeadersTouchListener(RecyclerView paramRecyclerView, StickyRecyclerHeadersDecoration paramStickyRecyclerHeadersDecoration)
  {
    this.mRecyclerView = paramRecyclerView;
    this.mDecor = paramStickyRecyclerHeadersDecoration;
  }
  
  public StickyRecyclerHeadersAdapter getAdapter()
  {
    if ((this.mRecyclerView.getAdapter() instanceof StickyRecyclerHeadersAdapter)) {
      return (StickyRecyclerHeadersAdapter)this.mRecyclerView.getAdapter();
    }
    throw new IllegalStateException("A RecyclerView with " + StickyRecyclerHeadersTouchListener.class.getSimpleName() + " requires a " + StickyRecyclerHeadersAdapter.class.getSimpleName());
  }
  
  public boolean onInterceptTouchEvent(RecyclerView paramRecyclerView, MotionEvent paramMotionEvent)
  {
    return (this.mOnHeaderClickListener != null) && (this.mTapDetector.onTouchEvent(paramMotionEvent));
  }
  
  public void onTouchEvent(RecyclerView paramRecyclerView, MotionEvent paramMotionEvent) {}
  
  public void setOnHeaderClickListener(OnHeaderClickListener paramOnHeaderClickListener)
  {
    this.mOnHeaderClickListener = paramOnHeaderClickListener;
  }
  
  public static abstract interface OnHeaderClickListener
  {
    public abstract void onHeaderClick(View paramView, int paramInt, long paramLong);
  }
  
  private class SingleTapDetector
    extends GestureDetector.SimpleOnGestureListener
  {
    private SingleTapDetector() {}
    
    public boolean onSingleTapConfirmed(MotionEvent paramMotionEvent)
    {
      int i = StickyRecyclerHeadersTouchListener.this.mDecor.findHeaderPositionUnder((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());
      if (i != -1)
      {
        paramMotionEvent = StickyRecyclerHeadersTouchListener.this.mDecor.getHeaderView(StickyRecyclerHeadersTouchListener.this.mRecyclerView, i);
        long l = StickyRecyclerHeadersTouchListener.this.getAdapter().getHeaderId(i);
        StickyRecyclerHeadersTouchListener.this.mOnHeaderClickListener.onHeaderClick(paramMotionEvent, i, l);
        return true;
      }
      return false;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/timehop/stickyheadersrecyclerview/StickyRecyclerHeadersTouchListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */