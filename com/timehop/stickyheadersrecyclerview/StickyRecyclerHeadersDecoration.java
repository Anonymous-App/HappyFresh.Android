package com.timehop.stickyheadersrecyclerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.util.LongSparseArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class StickyRecyclerHeadersDecoration
  extends RecyclerView.ItemDecoration
{
  private final StickyRecyclerHeadersAdapter mAdapter;
  private final SparseArray<Rect> mHeaderRects = new SparseArray();
  private final LongSparseArray<View> mHeaderViews = new LongSparseArray();
  
  public StickyRecyclerHeadersDecoration(StickyRecyclerHeadersAdapter paramStickyRecyclerHeadersAdapter)
  {
    this.mAdapter = paramStickyRecyclerHeadersAdapter;
  }
  
  private int getFirstHeaderPosition()
  {
    int i = 0;
    while (i < this.mAdapter.getItemCount())
    {
      if (this.mAdapter.getHeaderId(i) >= 0L) {
        return i;
      }
      i += 1;
    }
    return -1;
  }
  
  private View getNextView(RecyclerView paramRecyclerView)
  {
    View localView1 = getHeaderView(paramRecyclerView, paramRecyclerView.getChildPosition(paramRecyclerView.getChildAt(0)));
    int i = 0;
    while (i < paramRecyclerView.getChildCount())
    {
      View localView2 = paramRecyclerView.getChildAt(i);
      RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)localView2.getLayoutParams();
      if (getOrientation(paramRecyclerView) == 1)
      {
        if (localView2.getTop() - localLayoutParams.topMargin <= localView1.getHeight()) {}
      }
      else {
        while (localView2.getLeft() - localLayoutParams.leftMargin > localView1.getWidth()) {
          return localView2;
        }
      }
      i += 1;
    }
    return null;
  }
  
  private int getOrientation(RecyclerView paramRecyclerView)
  {
    if ((paramRecyclerView.getLayoutManager() instanceof LinearLayoutManager)) {
      return ((LinearLayoutManager)paramRecyclerView.getLayoutManager()).getOrientation();
    }
    throw new IllegalStateException("StickyListHeadersDecoration can only be used with a LinearLayoutManager.");
  }
  
  private boolean hasNewHeader(int paramInt)
  {
    if (getFirstHeaderPosition() == paramInt) {}
    do
    {
      return true;
      if (this.mAdapter.getHeaderId(paramInt) < 0L) {
        return false;
      }
      if ((paramInt <= 0) || (paramInt >= this.mAdapter.getItemCount())) {
        break;
      }
    } while (this.mAdapter.getHeaderId(paramInt) != this.mAdapter.getHeaderId(paramInt - 1));
    return false;
    return false;
  }
  
  public int findHeaderPositionUnder(int paramInt1, int paramInt2)
  {
    int i = 0;
    while (i < this.mHeaderRects.size())
    {
      if (((Rect)this.mHeaderRects.get(this.mHeaderRects.keyAt(i))).contains(paramInt1, paramInt2)) {
        return this.mHeaderRects.keyAt(i);
      }
      i += 1;
    }
    return -1;
  }
  
  public View getHeaderView(RecyclerView paramRecyclerView, int paramInt)
  {
    long l = this.mAdapter.getHeaderId(paramInt);
    View localView = (View)this.mHeaderViews.get(l);
    Object localObject = localView;
    int i;
    if (localView == null)
    {
      localObject = this.mAdapter.onCreateHeaderViewHolder(paramRecyclerView);
      this.mAdapter.onBindHeaderViewHolder((RecyclerView.ViewHolder)localObject, paramInt);
      localObject = ((RecyclerView.ViewHolder)localObject).itemView;
      if (((View)localObject).getLayoutParams() == null) {
        ((View)localObject).setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
      }
      if (getOrientation(paramRecyclerView) != 1) {
        break label196;
      }
      i = View.MeasureSpec.makeMeasureSpec(paramRecyclerView.getWidth(), 1073741824);
    }
    for (paramInt = View.MeasureSpec.makeMeasureSpec(paramRecyclerView.getHeight(), 0);; paramInt = View.MeasureSpec.makeMeasureSpec(paramRecyclerView.getHeight(), 1073741824))
    {
      ((View)localObject).measure(ViewGroup.getChildMeasureSpec(i, paramRecyclerView.getPaddingLeft() + paramRecyclerView.getPaddingRight(), ((View)localObject).getLayoutParams().width), ViewGroup.getChildMeasureSpec(paramInt, paramRecyclerView.getPaddingTop() + paramRecyclerView.getPaddingBottom(), ((View)localObject).getLayoutParams().height));
      ((View)localObject).layout(0, 0, ((View)localObject).getMeasuredWidth(), ((View)localObject).getMeasuredHeight());
      this.mHeaderViews.put(l, localObject);
      return (View)localObject;
      label196:
      i = View.MeasureSpec.makeMeasureSpec(paramRecyclerView.getWidth(), 0);
    }
  }
  
  public void getItemOffsets(Rect paramRect, View paramView, RecyclerView paramRecyclerView, RecyclerView.State paramState)
  {
    super.getItemOffsets(paramRect, paramView, paramRecyclerView, paramState);
    int i = getOrientation(paramRecyclerView);
    int j = paramRecyclerView.getChildPosition(paramView);
    if (hasNewHeader(j))
    {
      paramView = getHeaderView(paramRecyclerView, j);
      if (i == 1) {
        paramRect.top = paramView.getHeight();
      }
    }
    else
    {
      return;
    }
    paramRect.left = paramView.getWidth();
  }
  
  public void invalidateHeaders()
  {
    this.mHeaderViews.clear();
  }
  
  public void onDrawOver(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.State paramState)
  {
    super.onDrawOver(paramCanvas, paramRecyclerView, paramState);
    int n = getOrientation(paramRecyclerView);
    this.mHeaderRects.clear();
    if ((paramRecyclerView.getChildCount() > 0) && (this.mAdapter.getItemCount() > 0))
    {
      int i1 = paramRecyclerView.getChildPosition(paramRecyclerView.getChildAt(0));
      View localView1;
      int k;
      int m;
      int i;
      int j;
      View localView2;
      if (this.mAdapter.getHeaderId(i1) >= 0L)
      {
        paramState = getHeaderView(paramRecyclerView, i1);
        localView1 = getNextView(paramRecyclerView);
        k = Math.max(paramRecyclerView.getChildAt(0).getLeft() - paramState.getWidth(), 0);
        m = Math.max(paramRecyclerView.getChildAt(0).getTop() - paramState.getHeight(), 0);
        int i2 = paramRecyclerView.getChildPosition(localView1);
        i = k;
        j = m;
        if (i2 > 0)
        {
          i = k;
          j = m;
          if (hasNewHeader(i2))
          {
            localView2 = getHeaderView(paramRecyclerView, i2);
            if ((n != 1) || (localView1.getTop() - localView2.getHeight() - paramState.getHeight() >= 0)) {
              break label411;
            }
            j = m + (localView1.getTop() - localView2.getHeight() - paramState.getHeight());
            i = k;
          }
        }
        paramCanvas.save();
        paramCanvas.translate(i, j);
        paramState.draw(paramCanvas);
        paramCanvas.restore();
        this.mHeaderRects.put(i1, new Rect(i, j, paramState.getWidth() + i, paramState.getHeight() + j));
      }
      else
      {
        i = 1;
        label278:
        if (i >= paramRecyclerView.getChildCount()) {
          return;
        }
        m = paramRecyclerView.getChildPosition(paramRecyclerView.getChildAt(i));
        if (hasNewHeader(m))
        {
          j = 0;
          k = 0;
          paramState = getHeaderView(paramRecyclerView, m);
          if (n != 1) {
            break label479;
          }
          k = paramRecyclerView.getChildAt(i).getTop() - paramState.getHeight();
        }
      }
      for (;;)
      {
        paramCanvas.save();
        paramCanvas.translate(j, k);
        paramState.draw(paramCanvas);
        paramCanvas.restore();
        this.mHeaderRects.put(m, new Rect(j, k, paramState.getWidth() + j, paramState.getHeight() + k));
        i += 1;
        break label278;
        label411:
        i = k;
        j = m;
        if (n != 0) {
          break;
        }
        i = k;
        j = m;
        if (localView1.getLeft() - localView2.getWidth() - paramState.getWidth() >= 0) {
          break;
        }
        i = k + (localView1.getLeft() - localView2.getWidth() - paramState.getWidth());
        j = m;
        break;
        label479:
        j = paramRecyclerView.getChildAt(i).getLeft() - paramState.getWidth();
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/timehop/stickyheadersrecyclerview/StickyRecyclerHeadersDecoration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */