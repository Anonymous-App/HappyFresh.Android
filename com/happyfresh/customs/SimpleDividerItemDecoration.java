package com.happyfresh.customs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class SimpleDividerItemDecoration
  extends RecyclerView.ItemDecoration
{
  private Drawable mDivider;
  
  public SimpleDividerItemDecoration(Context paramContext)
  {
    this.mDivider = paramContext.getResources().getDrawable(2130837844);
  }
  
  public void onDraw(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.State paramState)
  {
    int j = paramRecyclerView.getPaddingLeft();
    int k = paramRecyclerView.getWidth();
    int m = paramRecyclerView.getPaddingRight();
    int n = paramRecyclerView.getChildCount();
    int i = 0;
    while (i < n)
    {
      paramState = paramRecyclerView.getChildAt(i);
      RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramState.getLayoutParams();
      int i1 = paramState.getBottom() + localLayoutParams.bottomMargin;
      int i2 = this.mDivider.getIntrinsicHeight();
      this.mDivider.setBounds(j, i1, k - m, i1 + i2);
      this.mDivider.draw(paramCanvas);
      i += 1;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/customs/SimpleDividerItemDecoration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */