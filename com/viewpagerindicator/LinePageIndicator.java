package com.viewpagerindicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;

public class LinePageIndicator
  extends View
  implements PageIndicator
{
  private static final int INVALID_POINTER = -1;
  private int mActivePointerId = -1;
  private boolean mCentered;
  private int mCurrentPage;
  private float mGapWidth;
  private boolean mIsDragging;
  private float mLastMotionX = -1.0F;
  private float mLineWidth;
  private ViewPager.OnPageChangeListener mListener;
  private final Paint mPaintSelected = new Paint(1);
  private final Paint mPaintUnselected = new Paint(1);
  private int mTouchSlop;
  private ViewPager mViewPager;
  
  public LinePageIndicator(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public LinePageIndicator(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.vpiLinePageIndicatorStyle);
  }
  
  public LinePageIndicator(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (isInEditMode()) {
      return;
    }
    Object localObject = getResources();
    int i = ((Resources)localObject).getColor(R.color.default_line_indicator_selected_color);
    int j = ((Resources)localObject).getColor(R.color.default_line_indicator_unselected_color);
    float f1 = ((Resources)localObject).getDimension(R.dimen.default_line_indicator_line_width);
    float f2 = ((Resources)localObject).getDimension(R.dimen.default_line_indicator_gap_width);
    float f3 = ((Resources)localObject).getDimension(R.dimen.default_line_indicator_stroke_width);
    boolean bool = ((Resources)localObject).getBoolean(R.bool.default_line_indicator_centered);
    paramAttributeSet = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.LinePageIndicator, paramInt, 0);
    this.mCentered = paramAttributeSet.getBoolean(R.styleable.LinePageIndicator_centered, bool);
    this.mLineWidth = paramAttributeSet.getDimension(R.styleable.LinePageIndicator_lineWidth, f1);
    this.mGapWidth = paramAttributeSet.getDimension(R.styleable.LinePageIndicator_gapWidth, f2);
    setStrokeWidth(paramAttributeSet.getDimension(R.styleable.LinePageIndicator_strokeWidth, f3));
    this.mPaintUnselected.setColor(paramAttributeSet.getColor(R.styleable.LinePageIndicator_unselectedColor, j));
    this.mPaintSelected.setColor(paramAttributeSet.getColor(R.styleable.LinePageIndicator_selectedColor, i));
    localObject = paramAttributeSet.getDrawable(R.styleable.LinePageIndicator_android_background);
    if (localObject != null) {
      setBackgroundDrawable((Drawable)localObject);
    }
    paramAttributeSet.recycle();
    this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(paramContext));
  }
  
  private int measureHeight(int paramInt)
  {
    int i = View.MeasureSpec.getMode(paramInt);
    paramInt = View.MeasureSpec.getSize(paramInt);
    float f1;
    if (i == 1073741824) {
      f1 = paramInt;
    }
    for (;;)
    {
      return (int)FloatMath.ceil(f1);
      float f2 = this.mPaintSelected.getStrokeWidth() + getPaddingTop() + getPaddingBottom();
      f1 = f2;
      if (i == Integer.MIN_VALUE) {
        f1 = Math.min(f2, paramInt);
      }
    }
  }
  
  private int measureWidth(int paramInt)
  {
    int i = View.MeasureSpec.getMode(paramInt);
    paramInt = View.MeasureSpec.getSize(paramInt);
    float f1;
    if ((i == 1073741824) || (this.mViewPager == null)) {
      f1 = paramInt;
    }
    for (;;)
    {
      return (int)FloatMath.ceil(f1);
      int j = this.mViewPager.getAdapter().getCount();
      float f2 = getPaddingLeft() + getPaddingRight() + j * this.mLineWidth + (j - 1) * this.mGapWidth;
      f1 = f2;
      if (i == Integer.MIN_VALUE) {
        f1 = Math.min(f2, paramInt);
      }
    }
  }
  
  public float getGapWidth()
  {
    return this.mGapWidth;
  }
  
  public float getLineWidth()
  {
    return this.mLineWidth;
  }
  
  public int getSelectedColor()
  {
    return this.mPaintSelected.getColor();
  }
  
  public float getStrokeWidth()
  {
    return this.mPaintSelected.getStrokeWidth();
  }
  
  public int getUnselectedColor()
  {
    return this.mPaintUnselected.getColor();
  }
  
  public boolean isCentered()
  {
    return this.mCentered;
  }
  
  public void notifyDataSetChanged()
  {
    invalidate();
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.mViewPager == null) {}
    int j;
    do
    {
      return;
      j = this.mViewPager.getAdapter().getCount();
    } while (j == 0);
    if (this.mCurrentPage >= j)
    {
      setCurrentItem(j - 1);
      return;
    }
    float f4 = this.mLineWidth + this.mGapWidth;
    float f6 = j;
    float f7 = this.mGapWidth;
    float f1 = getPaddingTop();
    float f3 = getPaddingLeft();
    float f8 = getPaddingRight();
    float f5 = f1 + (getHeight() - f1 - getPaddingBottom()) / 2.0F;
    float f2 = f3;
    f1 = f2;
    if (this.mCentered) {
      f1 = f2 + ((getWidth() - f3 - f8) / 2.0F - (f6 * f4 - f7) / 2.0F);
    }
    int i = 0;
    label151:
    if (i < j)
    {
      f2 = f1 + i * f4;
      f3 = this.mLineWidth;
      if (i != this.mCurrentPage) {
        break label212;
      }
    }
    label212:
    for (Paint localPaint = this.mPaintSelected;; localPaint = this.mPaintUnselected)
    {
      paramCanvas.drawLine(f2, f5, f2 + f3, f5, localPaint);
      i += 1;
      break label151;
      break;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    setMeasuredDimension(measureWidth(paramInt1), measureHeight(paramInt2));
  }
  
  public void onPageScrollStateChanged(int paramInt)
  {
    if (this.mListener != null) {
      this.mListener.onPageScrollStateChanged(paramInt);
    }
  }
  
  public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
  {
    if (this.mListener != null) {
      this.mListener.onPageScrolled(paramInt1, paramFloat, paramInt2);
    }
  }
  
  public void onPageSelected(int paramInt)
  {
    this.mCurrentPage = paramInt;
    invalidate();
    if (this.mListener != null) {
      this.mListener.onPageSelected(paramInt);
    }
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    paramParcelable = (SavedState)paramParcelable;
    super.onRestoreInstanceState(paramParcelable.getSuperState());
    this.mCurrentPage = paramParcelable.currentPage;
    requestLayout();
  }
  
  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    localSavedState.currentPage = this.mCurrentPage;
    return localSavedState;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (super.onTouchEvent(paramMotionEvent)) {
      return true;
    }
    if ((this.mViewPager == null) || (this.mViewPager.getAdapter().getCount() == 0)) {
      return false;
    }
    int i = paramMotionEvent.getAction() & 0xFF;
    switch (i)
    {
    case 4: 
    default: 
    case 0: 
    case 2: 
    case 1: 
    case 3: 
    case 5: 
      for (;;)
      {
        return true;
        this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, 0);
        this.mLastMotionX = paramMotionEvent.getX();
        continue;
        float f1 = MotionEventCompat.getX(paramMotionEvent, MotionEventCompat.findPointerIndex(paramMotionEvent, this.mActivePointerId));
        float f2 = f1 - this.mLastMotionX;
        if ((!this.mIsDragging) && (Math.abs(f2) > this.mTouchSlop)) {
          this.mIsDragging = true;
        }
        if (this.mIsDragging)
        {
          this.mLastMotionX = f1;
          if ((this.mViewPager.isFakeDragging()) || (this.mViewPager.beginFakeDrag()))
          {
            this.mViewPager.fakeDragBy(f2);
            continue;
            if (!this.mIsDragging)
            {
              int j = this.mViewPager.getAdapter().getCount();
              int k = getWidth();
              f1 = k / 2.0F;
              f2 = k / 6.0F;
              if ((this.mCurrentPage > 0) && (paramMotionEvent.getX() < f1 - f2))
              {
                if (i != 3) {
                  this.mViewPager.setCurrentItem(this.mCurrentPage - 1);
                }
                return true;
              }
              if ((this.mCurrentPage < j - 1) && (paramMotionEvent.getX() > f1 + f2))
              {
                if (i != 3) {
                  this.mViewPager.setCurrentItem(this.mCurrentPage + 1);
                }
                return true;
              }
            }
            this.mIsDragging = false;
            this.mActivePointerId = -1;
            if (this.mViewPager.isFakeDragging())
            {
              this.mViewPager.endFakeDrag();
              continue;
              i = MotionEventCompat.getActionIndex(paramMotionEvent);
              this.mLastMotionX = MotionEventCompat.getX(paramMotionEvent, i);
              this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, i);
            }
          }
        }
      }
    }
    i = MotionEventCompat.getActionIndex(paramMotionEvent);
    if (MotionEventCompat.getPointerId(paramMotionEvent, i) == this.mActivePointerId) {
      if (i != 0) {
        break label434;
      }
    }
    label434:
    for (i = 1;; i = 0)
    {
      this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, i);
      this.mLastMotionX = MotionEventCompat.getX(paramMotionEvent, MotionEventCompat.findPointerIndex(paramMotionEvent, this.mActivePointerId));
      break;
    }
  }
  
  public void setCentered(boolean paramBoolean)
  {
    this.mCentered = paramBoolean;
    invalidate();
  }
  
  public void setCurrentItem(int paramInt)
  {
    if (this.mViewPager == null) {
      throw new IllegalStateException("ViewPager has not been bound.");
    }
    this.mViewPager.setCurrentItem(paramInt);
    this.mCurrentPage = paramInt;
    invalidate();
  }
  
  public void setGapWidth(float paramFloat)
  {
    this.mGapWidth = paramFloat;
    invalidate();
  }
  
  public void setLineWidth(float paramFloat)
  {
    this.mLineWidth = paramFloat;
    invalidate();
  }
  
  public void setOnPageChangeListener(ViewPager.OnPageChangeListener paramOnPageChangeListener)
  {
    this.mListener = paramOnPageChangeListener;
  }
  
  public void setSelectedColor(int paramInt)
  {
    this.mPaintSelected.setColor(paramInt);
    invalidate();
  }
  
  public void setStrokeWidth(float paramFloat)
  {
    this.mPaintSelected.setStrokeWidth(paramFloat);
    this.mPaintUnselected.setStrokeWidth(paramFloat);
    invalidate();
  }
  
  public void setUnselectedColor(int paramInt)
  {
    this.mPaintUnselected.setColor(paramInt);
    invalidate();
  }
  
  public void setViewPager(ViewPager paramViewPager)
  {
    if (this.mViewPager == paramViewPager) {
      return;
    }
    if (this.mViewPager != null) {
      this.mViewPager.setOnPageChangeListener(null);
    }
    if (paramViewPager.getAdapter() == null) {
      throw new IllegalStateException("ViewPager does not have adapter instance.");
    }
    this.mViewPager = paramViewPager;
    this.mViewPager.setOnPageChangeListener(this);
    invalidate();
  }
  
  public void setViewPager(ViewPager paramViewPager, int paramInt)
  {
    setViewPager(paramViewPager);
    setCurrentItem(paramInt);
  }
  
  static class SavedState
    extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public LinePageIndicator.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new LinePageIndicator.SavedState(paramAnonymousParcel, null);
      }
      
      public LinePageIndicator.SavedState[] newArray(int paramAnonymousInt)
      {
        return new LinePageIndicator.SavedState[paramAnonymousInt];
      }
    };
    int currentPage;
    
    private SavedState(Parcel paramParcel)
    {
      super();
      this.currentPage = paramParcel.readInt();
    }
    
    public SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.currentPage);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/viewpagerindicator/LinePageIndicator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */