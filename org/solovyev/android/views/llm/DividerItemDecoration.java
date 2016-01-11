package org.solovyev.android.views.llm;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.State;
import android.util.AttributeSet;
import android.view.View;

public class DividerItemDecoration
  extends RecyclerView.ItemDecoration
{
  private Drawable divider;
  private int dividerHeight;
  private int dividerWidth;
  private boolean first = false;
  private boolean last = false;
  
  public DividerItemDecoration(Context paramContext, AttributeSet paramAttributeSet)
  {
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, new int[] { 16843284 });
    setDivider(paramContext.getDrawable(0));
    paramContext.recycle();
  }
  
  public DividerItemDecoration(Context paramContext, AttributeSet paramAttributeSet, boolean paramBoolean1, boolean paramBoolean2)
  {
    this(paramContext, paramAttributeSet);
  }
  
  public DividerItemDecoration(Drawable paramDrawable)
  {
    setDivider(paramDrawable);
  }
  
  public DividerItemDecoration(Drawable paramDrawable, boolean paramBoolean1, boolean paramBoolean2)
  {
    this(paramDrawable);
  }
  
  private int getOrientation(RecyclerView paramRecyclerView)
  {
    paramRecyclerView = paramRecyclerView.getLayoutManager();
    if ((paramRecyclerView instanceof LinearLayoutManager)) {
      return ((LinearLayoutManager)paramRecyclerView).getOrientation();
    }
    throw new IllegalStateException("DividerItemDecoration can only be used with a LinearLayoutManager");
  }
  
  private void setDivider(Drawable paramDrawable)
  {
    int j = 0;
    this.divider = paramDrawable;
    if (paramDrawable == null)
    {
      i = 0;
      this.dividerHeight = i;
      if (paramDrawable != null) {
        break label38;
      }
    }
    label38:
    for (int i = j;; i = paramDrawable.getIntrinsicWidth())
    {
      this.dividerWidth = i;
      return;
      i = paramDrawable.getIntrinsicHeight();
      break;
    }
  }
  
  public void getItemOffsets(Rect paramRect, View paramView, RecyclerView paramRecyclerView, RecyclerView.State paramState)
  {
    int n = 0;
    int m = 0;
    if (this.divider == null)
    {
      super.getItemOffsets(paramRect, paramView, paramRecyclerView, paramState);
      return;
    }
    int j = ((RecyclerView.LayoutParams)paramView.getLayoutParams()).getViewLayoutPosition();
    int i;
    label60:
    int k;
    if (j == 0)
    {
      i = 1;
      if (j != paramRecyclerView.getAdapter().getItemCount() - 1) {
        break label144;
      }
      j = 1;
      if ((!this.first) && (i != 0)) {
        break label150;
      }
      k = 1;
      label75:
      if ((!this.last) || (j == 0)) {
        break label156;
      }
      i = 1;
      label90:
      if (getOrientation(paramRecyclerView) != 1) {
        break label168;
      }
      if (k == 0) {
        break label162;
      }
    }
    label144:
    label150:
    label156:
    label162:
    for (j = this.dividerHeight;; j = 0)
    {
      paramRect.top = j;
      j = m;
      if (i != 0) {
        j = this.dividerHeight;
      }
      paramRect.bottom = j;
      return;
      i = 0;
      break;
      j = 0;
      break label60;
      k = 0;
      break label75;
      i = 0;
      break label90;
    }
    label168:
    if (k != 0) {}
    for (j = this.dividerWidth;; j = 0)
    {
      paramRect.left = j;
      j = n;
      if (i != 0) {
        j = this.dividerWidth;
      }
      paramRect.right = j;
      return;
    }
  }
  
  public void onDraw(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.State paramState)
  {
    if (this.divider == null) {
      super.onDraw(paramCanvas, paramRecyclerView, paramState);
    }
    int j;
    int k;
    int i;
    int m;
    int i1;
    int n;
    label64:
    int i2;
    label148:
    label154:
    label258:
    do
    {
      int i4;
      do
      {
        return;
        j = 0;
        k = 0;
        i = 0;
        m = 0;
        i1 = getOrientation(paramRecyclerView);
        i4 = paramRecyclerView.getChildCount();
        paramState = paramRecyclerView.getAdapter();
        if (paramState != null)
        {
          n = paramState.getItemCount();
          if (i1 != 1) {
            break label148;
          }
          i1 = 1;
          if (i1 == 0) {
            break label154;
          }
          i2 = this.dividerHeight;
          j = paramRecyclerView.getPaddingLeft();
          k = paramRecyclerView.getWidth() - paramRecyclerView.getPaddingRight();
        }
        RecyclerView.LayoutParams localLayoutParams;
        for (;;)
        {
          int i3 = 0;
          for (;;)
          {
            if (i3 >= i4) {
              break label258;
            }
            paramState = paramRecyclerView.getChildAt(i3);
            localLayoutParams = (RecyclerView.LayoutParams)paramState.getLayoutParams();
            if ((localLayoutParams.getViewLayoutPosition() != 0) || (this.first)) {
              break;
            }
            i3 += 1;
          }
          n = 0;
          break;
          i1 = 0;
          break label64;
          i2 = this.dividerWidth;
          i = paramRecyclerView.getPaddingTop();
          m = paramRecyclerView.getHeight() - paramRecyclerView.getPaddingBottom();
        }
        if (i1 != 0)
        {
          i = paramState.getTop() - localLayoutParams.topMargin - i2;
          m = i + i2;
        }
        for (;;)
        {
          this.divider.setBounds(j, i, k, m);
          this.divider.draw(paramCanvas);
          break;
          j = paramState.getLeft() - localLayoutParams.leftMargin - i2;
          k = j + i2;
        }
      } while ((!this.last) || (i4 <= 0));
      paramRecyclerView = paramRecyclerView.getChildAt(i4 - 1);
      paramState = (RecyclerView.LayoutParams)paramRecyclerView.getLayoutParams();
    } while (paramState.getViewLayoutPosition() != n - 1);
    if (i1 != 0)
    {
      i = paramRecyclerView.getBottom() + paramState.bottomMargin;
      m = i + i2;
    }
    for (;;)
    {
      this.divider.setBounds(j, i, k, m);
      this.divider.draw(paramCanvas);
      return;
      j = paramRecyclerView.getRight() + paramState.rightMargin;
      k = j + i2;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/solovyev/android/views/llm/DividerItemDecoration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */