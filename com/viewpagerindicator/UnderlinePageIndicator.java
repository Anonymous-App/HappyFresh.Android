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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.ViewConfiguration;

public class UnderlinePageIndicator
  extends View
  implements PageIndicator
{
  private static final int FADE_FRAME_MS = 30;
  private static final int INVALID_POINTER = -1;
  private int mActivePointerId = -1;
  private int mCurrentPage;
  private int mFadeBy;
  private int mFadeDelay;
  private int mFadeLength;
  private final Runnable mFadeRunnable = new Runnable()
  {
    public void run()
    {
      if (!UnderlinePageIndicator.this.mFades) {}
      int i;
      do
      {
        return;
        i = Math.max(UnderlinePageIndicator.this.mPaint.getAlpha() - UnderlinePageIndicator.this.mFadeBy, 0);
        UnderlinePageIndicator.this.mPaint.setAlpha(i);
        UnderlinePageIndicator.this.invalidate();
      } while (i <= 0);
      UnderlinePageIndicator.this.postDelayed(this, 30L);
    }
  };
  private boolean mFades;
  private boolean mIsDragging;
  private float mLastMotionX = -1.0F;
  private ViewPager.OnPageChangeListener mListener;
  private final Paint mPaint = new Paint(1);
  private float mPositionOffset;
  private int mScrollState;
  private int mTouchSlop;
  private ViewPager mViewPager;
  
  public UnderlinePageIndicator(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public UnderlinePageIndicator(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.vpiUnderlinePageIndicatorStyle);
  }
  
  public UnderlinePageIndicator(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (isInEditMode()) {
      return;
    }
    Object localObject = getResources();
    boolean bool = ((Resources)localObject).getBoolean(R.bool.default_underline_indicator_fades);
    int i = ((Resources)localObject).getInteger(R.integer.default_underline_indicator_fade_delay);
    int j = ((Resources)localObject).getInteger(R.integer.default_underline_indicator_fade_length);
    int k = ((Resources)localObject).getColor(R.color.default_underline_indicator_selected_color);
    paramAttributeSet = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.UnderlinePageIndicator, paramInt, 0);
    setFades(paramAttributeSet.getBoolean(R.styleable.UnderlinePageIndicator_fades, bool));
    setSelectedColor(paramAttributeSet.getColor(R.styleable.UnderlinePageIndicator_selectedColor, k));
    setFadeDelay(paramAttributeSet.getInteger(R.styleable.UnderlinePageIndicator_fadeDelay, i));
    setFadeLength(paramAttributeSet.getInteger(R.styleable.UnderlinePageIndicator_fadeLength, j));
    localObject = paramAttributeSet.getDrawable(R.styleable.UnderlinePageIndicator_android_background);
    if (localObject != null) {
      setBackgroundDrawable((Drawable)localObject);
    }
    paramAttributeSet.recycle();
    this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(paramContext));
  }
  
  public int getFadeDelay()
  {
    return this.mFadeDelay;
  }
  
  public int getFadeLength()
  {
    return this.mFadeLength;
  }
  
  public boolean getFades()
  {
    return this.mFades;
  }
  
  public int getSelectedColor()
  {
    return this.mPaint.getColor();
  }
  
  public void notifyDataSetChanged()
  {
    invalidate();
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.mViewPager == null) {}
    int i;
    do
    {
      return;
      i = this.mViewPager.getAdapter().getCount();
    } while (i == 0);
    if (this.mCurrentPage >= i)
    {
      setCurrentItem(i - 1);
      return;
    }
    int j = getPaddingLeft();
    float f1 = (getWidth() - j - getPaddingRight()) / (1.0F * i);
    float f2 = j + (this.mCurrentPage + this.mPositionOffset) * f1;
    paramCanvas.drawRect(f2, getPaddingTop(), f2 + f1, getHeight() - getPaddingBottom(), this.mPaint);
  }
  
  public void onPageScrollStateChanged(int paramInt)
  {
    this.mScrollState = paramInt;
    if (this.mListener != null) {
      this.mListener.onPageScrollStateChanged(paramInt);
    }
  }
  
  public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
  {
    this.mCurrentPage = paramInt1;
    this.mPositionOffset = paramFloat;
    if (this.mFades)
    {
      if (paramInt2 <= 0) {
        break label64;
      }
      removeCallbacks(this.mFadeRunnable);
      this.mPaint.setAlpha(255);
    }
    for (;;)
    {
      invalidate();
      if (this.mListener != null) {
        this.mListener.onPageScrolled(paramInt1, paramFloat, paramInt2);
      }
      return;
      label64:
      if (this.mScrollState != 1) {
        postDelayed(this.mFadeRunnable, this.mFadeDelay);
      }
    }
  }
  
  public void onPageSelected(int paramInt)
  {
    if (this.mScrollState == 0)
    {
      this.mCurrentPage = paramInt;
      this.mPositionOffset = 0.0F;
      invalidate();
      this.mFadeRunnable.run();
    }
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
  
  public void setCurrentItem(int paramInt)
  {
    if (this.mViewPager == null) {
      throw new IllegalStateException("ViewPager has not been bound.");
    }
    this.mViewPager.setCurrentItem(paramInt);
    this.mCurrentPage = paramInt;
    invalidate();
  }
  
  public void setFadeDelay(int paramInt)
  {
    this.mFadeDelay = paramInt;
  }
  
  public void setFadeLength(int paramInt)
  {
    this.mFadeLength = paramInt;
    this.mFadeBy = (255 / (this.mFadeLength / 30));
  }
  
  public void setFades(boolean paramBoolean)
  {
    if (paramBoolean != this.mFades)
    {
      this.mFades = paramBoolean;
      if (paramBoolean) {
        post(this.mFadeRunnable);
      }
    }
    else
    {
      return;
    }
    removeCallbacks(this.mFadeRunnable);
    this.mPaint.setAlpha(255);
    invalidate();
  }
  
  public void setOnPageChangeListener(ViewPager.OnPageChangeListener paramOnPageChangeListener)
  {
    this.mListener = paramOnPageChangeListener;
  }
  
  public void setSelectedColor(int paramInt)
  {
    this.mPaint.setColor(paramInt);
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
    post(new Runnable()
    {
      public void run()
      {
        if (UnderlinePageIndicator.this.mFades) {
          UnderlinePageIndicator.this.post(UnderlinePageIndicator.this.mFadeRunnable);
        }
      }
    });
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
      public UnderlinePageIndicator.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new UnderlinePageIndicator.SavedState(paramAnonymousParcel, null);
      }
      
      public UnderlinePageIndicator.SavedState[] newArray(int paramAnonymousInt)
      {
        return new UnderlinePageIndicator.SavedState[paramAnonymousInt];
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


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/viewpagerindicator/UnderlinePageIndicator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */