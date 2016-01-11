package com.happyfresh.customs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerItemClickListener
  implements RecyclerView.OnItemTouchListener
{
  private GestureDetector mGestureDetector;
  private OnItemClickListener mListener;
  
  public RecyclerItemClickListener(Context paramContext, final RecyclerView paramRecyclerView, OnItemClickListener paramOnItemClickListener)
  {
    this.mListener = paramOnItemClickListener;
    this.mGestureDetector = new GestureDetector(paramContext, new GestureDetector.SimpleOnGestureListener()
    {
      public void onLongPress(MotionEvent paramAnonymousMotionEvent)
      {
        paramAnonymousMotionEvent = paramRecyclerView.findChildViewUnder(paramAnonymousMotionEvent.getX(), paramAnonymousMotionEvent.getY());
        if ((paramAnonymousMotionEvent != null) && (RecyclerItemClickListener.this.mListener != null)) {
          RecyclerItemClickListener.this.mListener.onItemLongClick(paramAnonymousMotionEvent, paramRecyclerView.getChildPosition(paramAnonymousMotionEvent));
        }
      }
      
      public boolean onSingleTapUp(MotionEvent paramAnonymousMotionEvent)
      {
        return true;
      }
    });
  }
  
  public boolean onInterceptTouchEvent(RecyclerView paramRecyclerView, MotionEvent paramMotionEvent)
  {
    View localView = paramRecyclerView.findChildViewUnder(paramMotionEvent.getX(), paramMotionEvent.getY());
    if ((localView != null) && (this.mListener != null) && (this.mGestureDetector.onTouchEvent(paramMotionEvent))) {
      this.mListener.onItemClick(localView, paramRecyclerView.getChildPosition(localView));
    }
    return false;
  }
  
  public void onRequestDisallowInterceptTouchEvent(boolean paramBoolean) {}
  
  public void onTouchEvent(RecyclerView paramRecyclerView, MotionEvent paramMotionEvent) {}
  
  public static abstract interface OnItemClickListener
  {
    public abstract void onItemClick(View paramView, int paramInt);
    
    public abstract void onItemLongClick(View paramView, int paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/customs/RecyclerItemClickListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */