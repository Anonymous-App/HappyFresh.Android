package org.solovyev.android.views.llm;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;
import android.view.View.MeasureSpec;
import java.lang.reflect.Field;

public class LinearLayoutManager
  extends android.support.v7.widget.LinearLayoutManager
{
  private static final int CHILD_HEIGHT = 1;
  private static final int CHILD_WIDTH = 0;
  private static final int DEFAULT_CHILD_SIZE = 100;
  private static boolean canMakeInsetsDirty = true;
  private static Field insetsDirtyField = null;
  private final int[] childDimensions = new int[2];
  private int childSize = 100;
  private boolean hasChildSize;
  private int overScrollMode = 0;
  private final Rect tmpRect = new Rect();
  private final RecyclerView view;
  
  public LinearLayoutManager(Context paramContext)
  {
    super(paramContext);
    this.view = null;
  }
  
  public LinearLayoutManager(Context paramContext, int paramInt, boolean paramBoolean)
  {
    super(paramContext, paramInt, paramBoolean);
    this.view = null;
  }
  
  public LinearLayoutManager(RecyclerView paramRecyclerView)
  {
    super(paramRecyclerView.getContext());
    this.view = paramRecyclerView;
    this.overScrollMode = ViewCompat.getOverScrollMode(paramRecyclerView);
  }
  
  public LinearLayoutManager(RecyclerView paramRecyclerView, int paramInt, boolean paramBoolean)
  {
    super(paramRecyclerView.getContext(), paramInt, paramBoolean);
    this.view = paramRecyclerView;
    this.overScrollMode = ViewCompat.getOverScrollMode(paramRecyclerView);
  }
  
  private void initChildDimensions(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if ((this.childDimensions[0] != 0) || (this.childDimensions[1] != 0)) {
      return;
    }
    if (paramBoolean)
    {
      this.childDimensions[0] = paramInt1;
      this.childDimensions[1] = this.childSize;
      return;
    }
    this.childDimensions[0] = this.childSize;
    this.childDimensions[1] = paramInt2;
  }
  
  private void logMeasureWarning(int paramInt) {}
  
  private static void makeInsetsDirty(RecyclerView.LayoutParams paramLayoutParams)
  {
    if (!canMakeInsetsDirty) {
      return;
    }
    try
    {
      if (insetsDirtyField == null)
      {
        insetsDirtyField = RecyclerView.LayoutParams.class.getDeclaredField("mInsetsDirty");
        insetsDirtyField.setAccessible(true);
      }
      insetsDirtyField.set(paramLayoutParams, Boolean.valueOf(true));
      return;
    }
    catch (NoSuchFieldException paramLayoutParams)
    {
      onMakeInsertDirtyFailed();
      return;
    }
    catch (IllegalAccessException paramLayoutParams)
    {
      onMakeInsertDirtyFailed();
    }
  }
  
  public static int makeUnspecifiedSpec()
  {
    return View.MeasureSpec.makeMeasureSpec(0, 0);
  }
  
  private void measureChild(RecyclerView.Recycler paramRecycler, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    try
    {
      View localView = paramRecycler.getViewForPosition(paramInt1);
      RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)localView.getLayoutParams();
      paramInt1 = getPaddingLeft();
      int i = getPaddingRight();
      int j = getPaddingTop();
      int k = getPaddingBottom();
      int m = localLayoutParams.leftMargin;
      int n = localLayoutParams.rightMargin;
      int i1 = localLayoutParams.topMargin;
      int i2 = localLayoutParams.bottomMargin;
      makeInsetsDirty(localLayoutParams);
      calculateItemDecorationsForChild(localView, this.tmpRect);
      int i3 = getRightDecorationWidth(localView);
      int i4 = getLeftDecorationWidth(localView);
      int i5 = getTopDecorationHeight(localView);
      int i6 = getBottomDecorationHeight(localView);
      localView.measure(getChildMeasureSpec(paramInt2, paramInt1 + i + (m + n) + (i3 + i4), localLayoutParams.width, canScrollHorizontally()), getChildMeasureSpec(paramInt3, j + k + (i1 + i2) + (i5 + i6), localLayoutParams.height, canScrollVertically()));
      paramArrayOfInt[0] = (getDecoratedMeasuredWidth(localView) + localLayoutParams.leftMargin + localLayoutParams.rightMargin);
      paramArrayOfInt[1] = (getDecoratedMeasuredHeight(localView) + localLayoutParams.bottomMargin + localLayoutParams.topMargin);
      makeInsetsDirty(localLayoutParams);
      paramRecycler.recycleView(localView);
      return;
    }
    catch (IndexOutOfBoundsException paramRecycler) {}
  }
  
  private static void onMakeInsertDirtyFailed()
  {
    canMakeInsetsDirty = false;
  }
  
  public void clearChildSize()
  {
    this.hasChildSize = false;
    setChildSize(100);
  }
  
  public void onMeasure(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt1, int paramInt2)
  {
    int j = View.MeasureSpec.getMode(paramInt1);
    int i = View.MeasureSpec.getMode(paramInt2);
    int i3 = View.MeasureSpec.getSize(paramInt1);
    int i4 = View.MeasureSpec.getSize(paramInt2);
    int k;
    int m;
    label42:
    int i1;
    label52:
    int n;
    label62:
    int i5;
    if (j != 0)
    {
      k = 1;
      if (i == 0) {
        break label93;
      }
      m = 1;
      if (j != 1073741824) {
        break label99;
      }
      i1 = 1;
      if (i != 1073741824) {
        break label105;
      }
      n = 1;
      i5 = makeUnspecifiedSpec();
      if ((i1 == 0) || (n == 0)) {
        break label111;
      }
      super.onMeasure(paramRecycler, paramState, paramInt1, paramInt2);
    }
    label93:
    label99:
    label105:
    label111:
    boolean bool;
    int i6;
    int i2;
    label204:
    label258:
    label266:
    label275:
    do
    {
      return;
      k = 0;
      break;
      m = 0;
      break label42;
      i1 = 0;
      break label52;
      n = 0;
      break label62;
      if (getOrientation() != 1) {
        break label349;
      }
      bool = true;
      initChildDimensions(i3, i4, bool);
      paramInt2 = 0;
      paramInt1 = 0;
      paramRecycler.clear();
      i6 = paramState.getItemCount();
      int i7 = getItemCount();
      i2 = 0;
      i = paramInt1;
      j = paramInt2;
      if (i2 < i7)
      {
        if (!bool) {
          break label364;
        }
        if (!this.hasChildSize)
        {
          if (i2 >= i6) {
            break label355;
          }
          measureChild(paramRecycler, i2, i3, i5, this.childDimensions);
        }
        paramInt1 += this.childDimensions[1];
        if (i2 == 0) {
          paramInt2 = this.childDimensions[0];
        }
        j = paramInt1;
        i = paramInt2;
        if (m == 0) {
          break label449;
        }
        j = paramInt1;
        i = paramInt2;
        if (paramInt1 < i4) {
          break label449;
        }
        j = paramInt2;
        i = paramInt1;
      }
      if (i1 == 0) {
        break label474;
      }
      paramInt1 = i3;
      if (n == 0) {
        break label507;
      }
      paramInt2 = i4;
      setMeasuredDimension(paramInt1, paramInt2);
    } while ((this.view == null) || (this.overScrollMode != 1));
    if (((bool) && ((m == 0) || (paramInt2 < i4))) || ((!bool) && ((k == 0) || (paramInt1 < i3))))
    {
      paramInt1 = 1;
      label332:
      paramRecycler = this.view;
      if (paramInt1 == 0) {
        break label547;
      }
    }
    label349:
    label355:
    label364:
    label449:
    label465:
    label474:
    label507:
    label547:
    for (paramInt1 = 2;; paramInt1 = 0)
    {
      ViewCompat.setOverScrollMode(paramRecycler, paramInt1);
      return;
      bool = false;
      break;
      logMeasureWarning(i2);
      break label204;
      if (!this.hasChildSize)
      {
        if (i2 >= i6) {
          break label465;
        }
        measureChild(paramRecycler, i2, i5, i4, this.childDimensions);
      }
      for (;;)
      {
        paramInt2 += this.childDimensions[0];
        if (i2 == 0) {
          paramInt1 = this.childDimensions[1];
        }
        j = paramInt1;
        i = paramInt2;
        if (k != 0)
        {
          i = paramInt1;
          j = paramInt2;
          if (paramInt2 >= i3) {
            break label258;
          }
          i = paramInt2;
          j = paramInt1;
        }
        i2 += 1;
        paramInt1 = j;
        paramInt2 = i;
        break;
        logMeasureWarning(i2);
      }
      paramInt2 = j + (getPaddingLeft() + getPaddingRight());
      paramInt1 = paramInt2;
      if (k == 0) {
        break label266;
      }
      paramInt1 = Math.min(paramInt2, i3);
      break label266;
      i += getPaddingTop() + getPaddingBottom();
      paramInt2 = i;
      if (m == 0) {
        break label275;
      }
      paramInt2 = Math.min(i, i4);
      break label275;
      paramInt1 = 0;
      break label332;
    }
  }
  
  public void setChildSize(int paramInt)
  {
    this.hasChildSize = true;
    if (this.childSize != paramInt)
    {
      this.childSize = paramInt;
      requestLayout();
    }
  }
  
  public void setOrientation(int paramInt)
  {
    if ((this.childDimensions != null) && (getOrientation() != paramInt))
    {
      this.childDimensions[0] = 0;
      this.childDimensions[1] = 0;
    }
    super.setOrientation(paramInt);
  }
  
  public void setOverScrollMode(int paramInt)
  {
    if ((paramInt < 0) || (paramInt > 2)) {
      throw new IllegalArgumentException("Unknown overscroll mode: " + paramInt);
    }
    if (this.view == null) {
      throw new IllegalStateException("view == null");
    }
    this.overScrollMode = paramInt;
    ViewCompat.setOverScrollMode(this.view, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/solovyev/android/views/llm/LinearLayoutManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */