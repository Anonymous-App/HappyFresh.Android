package com.happyfresh.customs;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NonSwipeViewPager
  extends ViewPager
{
  public NonSwipeViewPager(Context paramContext)
  {
    super(paramContext);
  }
  
  public NonSwipeViewPager(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    return false;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/customs/NonSwipeViewPager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */