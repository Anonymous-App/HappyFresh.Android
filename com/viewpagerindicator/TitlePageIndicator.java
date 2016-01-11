package com.viewpagerindicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
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
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import java.util.ArrayList;

public class TitlePageIndicator
  extends View
  implements PageIndicator
{
  private static final float BOLD_FADE_PERCENTAGE = 0.05F;
  private static final String EMPTY_TITLE = "";
  private static final int INVALID_POINTER = -1;
  private static final float SELECTION_FADE_PERCENTAGE = 0.25F;
  private int mActivePointerId = -1;
  private boolean mBoldText;
  private final Rect mBounds = new Rect();
  private OnCenterItemClickListener mCenterItemClickListener;
  private float mClipPadding;
  private int mColorSelected;
  private int mColorText;
  private int mCurrentPage = -1;
  private float mFooterIndicatorHeight;
  private IndicatorStyle mFooterIndicatorStyle;
  private float mFooterIndicatorUnderlinePadding;
  private float mFooterLineHeight;
  private float mFooterPadding;
  private boolean mIsDragging;
  private float mLastMotionX = -1.0F;
  private LinePosition mLinePosition;
  private ViewPager.OnPageChangeListener mListener;
  private float mPageOffset;
  private final Paint mPaintFooterIndicator = new Paint();
  private final Paint mPaintFooterLine = new Paint();
  private final Paint mPaintText = new Paint();
  private Path mPath = new Path();
  private int mScrollState;
  private float mTitlePadding;
  private float mTopPadding;
  private int mTouchSlop;
  private ViewPager mViewPager;
  
  public TitlePageIndicator(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public TitlePageIndicator(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.vpiTitlePageIndicatorStyle);
  }
  
  public TitlePageIndicator(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (isInEditMode()) {
      return;
    }
    Object localObject = getResources();
    int i = ((Resources)localObject).getColor(R.color.default_title_indicator_footer_color);
    float f1 = ((Resources)localObject).getDimension(R.dimen.default_title_indicator_footer_line_height);
    int j = ((Resources)localObject).getInteger(R.integer.default_title_indicator_footer_indicator_style);
    float f2 = ((Resources)localObject).getDimension(R.dimen.default_title_indicator_footer_indicator_height);
    float f3 = ((Resources)localObject).getDimension(R.dimen.default_title_indicator_footer_indicator_underline_padding);
    float f4 = ((Resources)localObject).getDimension(R.dimen.default_title_indicator_footer_padding);
    int k = ((Resources)localObject).getInteger(R.integer.default_title_indicator_line_position);
    int m = ((Resources)localObject).getColor(R.color.default_title_indicator_selected_color);
    boolean bool = ((Resources)localObject).getBoolean(R.bool.default_title_indicator_selected_bold);
    int n = ((Resources)localObject).getColor(R.color.default_title_indicator_text_color);
    float f5 = ((Resources)localObject).getDimension(R.dimen.default_title_indicator_text_size);
    float f6 = ((Resources)localObject).getDimension(R.dimen.default_title_indicator_title_padding);
    float f7 = ((Resources)localObject).getDimension(R.dimen.default_title_indicator_clip_padding);
    float f8 = ((Resources)localObject).getDimension(R.dimen.default_title_indicator_top_padding);
    paramAttributeSet = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.TitlePageIndicator, paramInt, 0);
    this.mFooterLineHeight = paramAttributeSet.getDimension(R.styleable.TitlePageIndicator_footerLineHeight, f1);
    this.mFooterIndicatorStyle = IndicatorStyle.fromValue(paramAttributeSet.getInteger(R.styleable.TitlePageIndicator_footerIndicatorStyle, j));
    this.mFooterIndicatorHeight = paramAttributeSet.getDimension(R.styleable.TitlePageIndicator_footerIndicatorHeight, f2);
    this.mFooterIndicatorUnderlinePadding = paramAttributeSet.getDimension(R.styleable.TitlePageIndicator_footerIndicatorUnderlinePadding, f3);
    this.mFooterPadding = paramAttributeSet.getDimension(R.styleable.TitlePageIndicator_footerPadding, f4);
    this.mLinePosition = LinePosition.fromValue(paramAttributeSet.getInteger(R.styleable.TitlePageIndicator_linePosition, k));
    this.mTopPadding = paramAttributeSet.getDimension(R.styleable.TitlePageIndicator_topPadding, f8);
    this.mTitlePadding = paramAttributeSet.getDimension(R.styleable.TitlePageIndicator_titlePadding, f6);
    this.mClipPadding = paramAttributeSet.getDimension(R.styleable.TitlePageIndicator_clipPadding, f7);
    this.mColorSelected = paramAttributeSet.getColor(R.styleable.TitlePageIndicator_selectedColor, m);
    this.mColorText = paramAttributeSet.getColor(R.styleable.TitlePageIndicator_android_textColor, n);
    this.mBoldText = paramAttributeSet.getBoolean(R.styleable.TitlePageIndicator_selectedBold, bool);
    f1 = paramAttributeSet.getDimension(R.styleable.TitlePageIndicator_android_textSize, f5);
    paramInt = paramAttributeSet.getColor(R.styleable.TitlePageIndicator_footerColor, i);
    this.mPaintText.setTextSize(f1);
    this.mPaintText.setAntiAlias(true);
    this.mPaintFooterLine.setStyle(Paint.Style.FILL_AND_STROKE);
    this.mPaintFooterLine.setStrokeWidth(this.mFooterLineHeight);
    this.mPaintFooterLine.setColor(paramInt);
    this.mPaintFooterIndicator.setStyle(Paint.Style.FILL_AND_STROKE);
    this.mPaintFooterIndicator.setColor(paramInt);
    localObject = paramAttributeSet.getDrawable(R.styleable.TitlePageIndicator_android_background);
    if (localObject != null) {
      setBackgroundDrawable((Drawable)localObject);
    }
    paramAttributeSet.recycle();
    this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(paramContext));
  }
  
  private Rect calcBounds(int paramInt, Paint paramPaint)
  {
    Rect localRect = new Rect();
    CharSequence localCharSequence = getTitle(paramInt);
    localRect.right = ((int)paramPaint.measureText(localCharSequence, 0, localCharSequence.length()));
    localRect.bottom = ((int)(paramPaint.descent() - paramPaint.ascent()));
    return localRect;
  }
  
  private ArrayList<Rect> calculateAllBounds(Paint paramPaint)
  {
    ArrayList localArrayList = new ArrayList();
    int j = this.mViewPager.getAdapter().getCount();
    int k = getWidth();
    int m = k / 2;
    int i = 0;
    while (i < j)
    {
      Rect localRect = calcBounds(i, paramPaint);
      int n = localRect.right - localRect.left;
      int i1 = localRect.bottom;
      int i2 = localRect.top;
      localRect.left = ((int)(m - n / 2.0F + (i - this.mCurrentPage - this.mPageOffset) * k));
      localRect.right = (localRect.left + n);
      localRect.top = 0;
      localRect.bottom = (i1 - i2);
      localArrayList.add(localRect);
      i += 1;
    }
    return localArrayList;
  }
  
  private void clipViewOnTheLeft(Rect paramRect, float paramFloat, int paramInt)
  {
    paramRect.left = ((int)(paramInt + this.mClipPadding));
    paramRect.right = ((int)(this.mClipPadding + paramFloat));
  }
  
  private void clipViewOnTheRight(Rect paramRect, float paramFloat, int paramInt)
  {
    paramRect.right = ((int)(paramInt - this.mClipPadding));
    paramRect.left = ((int)(paramRect.right - paramFloat));
  }
  
  private CharSequence getTitle(int paramInt)
  {
    CharSequence localCharSequence = this.mViewPager.getAdapter().getPageTitle(paramInt);
    Object localObject = localCharSequence;
    if (localCharSequence == null) {
      localObject = "";
    }
    return (CharSequence)localObject;
  }
  
  public float getClipPadding()
  {
    return this.mClipPadding;
  }
  
  public int getFooterColor()
  {
    return this.mPaintFooterLine.getColor();
  }
  
  public float getFooterIndicatorHeight()
  {
    return this.mFooterIndicatorHeight;
  }
  
  public float getFooterIndicatorPadding()
  {
    return this.mFooterPadding;
  }
  
  public IndicatorStyle getFooterIndicatorStyle()
  {
    return this.mFooterIndicatorStyle;
  }
  
  public float getFooterLineHeight()
  {
    return this.mFooterLineHeight;
  }
  
  public LinePosition getLinePosition()
  {
    return this.mLinePosition;
  }
  
  public int getSelectedColor()
  {
    return this.mColorSelected;
  }
  
  public int getTextColor()
  {
    return this.mColorText;
  }
  
  public float getTextSize()
  {
    return this.mPaintText.getTextSize();
  }
  
  public float getTitlePadding()
  {
    return this.mTitlePadding;
  }
  
  public float getTopPadding()
  {
    return this.mTopPadding;
  }
  
  public Typeface getTypeface()
  {
    return this.mPaintText.getTypeface();
  }
  
  public boolean isSelectedBold()
  {
    return this.mBoldText;
  }
  
  public void notifyDataSetChanged()
  {
    invalidate();
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.mViewPager == null) {}
    int i2;
    float f2;
    int i;
    int j;
    label177:
    float f6;
    label424:
    label430:
    label436:
    label665:
    label969:
    label975:
    do
    {
      int i4;
      do
      {
        return;
        i4 = this.mViewPager.getAdapter().getCount();
      } while (i4 == 0);
      if ((this.mCurrentPage == -1) && (this.mViewPager != null)) {
        this.mCurrentPage = this.mViewPager.getCurrentItem();
      }
      localObject1 = calculateAllBounds(this.mPaintText);
      i2 = ((ArrayList)localObject1).size();
      if (this.mCurrentPage >= i2)
      {
        setCurrentItem(i2 - 1);
        return;
      }
      float f5 = getWidth() / 2.0F;
      int i5 = getLeft();
      f2 = i5 + this.mClipPadding;
      int i3 = getWidth();
      int i1 = getHeight();
      int i6 = i5 + i3;
      f3 = i6 - this.mClipPadding;
      i = this.mCurrentPage;
      if (this.mPageOffset <= 0.5D)
      {
        f1 = this.mPageOffset;
        if (f1 > 0.25F) {
          break label424;
        }
        j = 1;
        if (f1 > 0.05F) {
          break label430;
        }
      }
      Rect localRect;
      int n;
      Object localObject2;
      for (int k = 1;; k = 0)
      {
        f6 = (0.25F - f1) / 0.25F;
        localRect = (Rect)((ArrayList)localObject1).get(this.mCurrentPage);
        f1 = localRect.right - localRect.left;
        if (localRect.left < f2) {
          clipViewOnTheLeft(localRect, f1, i5);
        }
        if (localRect.right > f3) {
          clipViewOnTheRight(localRect, f1, i6);
        }
        if (this.mCurrentPage <= 0) {
          break label436;
        }
        m = this.mCurrentPage - 1;
        while (m >= 0)
        {
          localRect = (Rect)((ArrayList)localObject1).get(m);
          if (localRect.left < f2)
          {
            n = localRect.right - localRect.left;
            clipViewOnTheLeft(localRect, n, i5);
            localObject2 = (Rect)((ArrayList)localObject1).get(m + 1);
            if (localRect.right + this.mTitlePadding > ((Rect)localObject2).left)
            {
              localRect.left = ((int)(((Rect)localObject2).left - n - this.mTitlePadding));
              localRect.right = (localRect.left + n);
            }
          }
          m -= 1;
        }
        i += 1;
        f1 = 1.0F - this.mPageOffset;
        break;
        j = 0;
        break label177;
      }
      if (this.mCurrentPage < i4 - 1)
      {
        m = this.mCurrentPage + 1;
        while (m < i4)
        {
          localRect = (Rect)((ArrayList)localObject1).get(m);
          if (localRect.right > f3)
          {
            n = localRect.right - localRect.left;
            clipViewOnTheRight(localRect, n, i6);
            localObject2 = (Rect)((ArrayList)localObject1).get(m - 1);
            if (localRect.left - this.mTitlePadding < ((Rect)localObject2).right)
            {
              localRect.left = ((int)(((Rect)localObject2).right + this.mTitlePadding));
              localRect.right = (localRect.left + n);
            }
          }
          m += 1;
        }
      }
      int i7 = this.mColorText >>> 24;
      int m = 0;
      if (m < i4)
      {
        localRect = (Rect)((ArrayList)localObject1).get(m);
        Object localObject3;
        if (((localRect.left > i5) && (localRect.left < i6)) || ((localRect.right > i5) && (localRect.right < i6)))
        {
          if (m != i) {
            break label969;
          }
          n = 1;
          localObject2 = getTitle(m);
          localObject3 = this.mPaintText;
          if ((n == 0) || (k == 0) || (!this.mBoldText)) {
            break label975;
          }
        }
        for (boolean bool = true;; bool = false)
        {
          ((Paint)localObject3).setFakeBoldText(bool);
          this.mPaintText.setColor(this.mColorText);
          if ((n != 0) && (j != 0)) {
            this.mPaintText.setAlpha(i7 - (int)(i7 * f6));
          }
          if (m < i2 - 1)
          {
            localObject3 = (Rect)((ArrayList)localObject1).get(m + 1);
            if (localRect.right + this.mTitlePadding > ((Rect)localObject3).left)
            {
              i8 = localRect.right - localRect.left;
              localRect.left = ((int)(((Rect)localObject3).left - i8 - this.mTitlePadding));
              localRect.right = (localRect.left + i8);
            }
          }
          int i8 = ((CharSequence)localObject2).length();
          f1 = localRect.left;
          f2 = localRect.bottom;
          paramCanvas.drawText((CharSequence)localObject2, 0, i8, f1, this.mTopPadding + f2, this.mPaintText);
          if ((n != 0) && (j != 0))
          {
            this.mPaintText.setColor(this.mColorSelected);
            this.mPaintText.setAlpha((int)((this.mColorSelected >>> 24) * f6));
            n = ((CharSequence)localObject2).length();
            f1 = localRect.left;
            f2 = localRect.bottom;
            paramCanvas.drawText((CharSequence)localObject2, 0, n, f1, this.mTopPadding + f2, this.mPaintText);
          }
          m += 1;
          break;
          n = 0;
          break label665;
        }
      }
      f4 = this.mFooterLineHeight;
      f3 = this.mFooterIndicatorHeight;
      f1 = f3;
      f2 = f4;
      k = i1;
      if (this.mLinePosition == LinePosition.Top)
      {
        k = 0;
        f2 = -f4;
        f1 = -f3;
      }
      this.mPath.reset();
      this.mPath.moveTo(0.0F, k - f2 / 2.0F);
      this.mPath.lineTo(i3, k - f2 / 2.0F);
      this.mPath.close();
      paramCanvas.drawPath(this.mPath, this.mPaintFooterLine);
      f2 = k - f2;
      switch (this.mFooterIndicatorStyle)
      {
      default: 
        return;
      case ???: 
        this.mPath.reset();
        this.mPath.moveTo(f5, f2 - f1);
        this.mPath.lineTo(f5 + f1, f2);
        this.mPath.lineTo(f5 - f1, f2);
        this.mPath.close();
        paramCanvas.drawPath(this.mPath, this.mPaintFooterIndicator);
        return;
      }
    } while ((j == 0) || (i >= i2));
    Object localObject1 = (Rect)((ArrayList)localObject1).get(i);
    float f3 = ((Rect)localObject1).right + this.mFooterIndicatorUnderlinePadding;
    float f4 = ((Rect)localObject1).left - this.mFooterIndicatorUnderlinePadding;
    float f1 = f2 - f1;
    this.mPath.reset();
    this.mPath.moveTo(f4, f2);
    this.mPath.lineTo(f3, f2);
    this.mPath.lineTo(f3, f1);
    this.mPath.lineTo(f4, f1);
    this.mPath.close();
    this.mPaintFooterIndicator.setAlpha((int)(255.0F * f6));
    paramCanvas.drawPath(this.mPath, this.mPaintFooterIndicator);
    this.mPaintFooterIndicator.setAlpha(255);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    paramInt1 = View.MeasureSpec.getSize(paramInt1);
    float f1;
    if (View.MeasureSpec.getMode(paramInt2) == 1073741824) {
      f1 = View.MeasureSpec.getSize(paramInt2);
    }
    for (;;)
    {
      setMeasuredDimension(paramInt1, (int)f1);
      return;
      this.mBounds.setEmpty();
      this.mBounds.bottom = ((int)(this.mPaintText.descent() - this.mPaintText.ascent()));
      float f2 = this.mBounds.bottom - this.mBounds.top + this.mFooterLineHeight + this.mFooterPadding + this.mTopPadding;
      f1 = f2;
      if (this.mFooterIndicatorStyle != IndicatorStyle.None) {
        f1 = f2 + this.mFooterIndicatorHeight;
      }
    }
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
    this.mPageOffset = paramFloat;
    invalidate();
    if (this.mListener != null) {
      this.mListener.onPageScrolled(paramInt1, paramFloat, paramInt2);
    }
  }
  
  public void onPageSelected(int paramInt)
  {
    if (this.mScrollState == 0)
    {
      this.mCurrentPage = paramInt;
      invalidate();
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
              float f3 = paramMotionEvent.getX();
              if (f3 < f1 - f2)
              {
                if (this.mCurrentPage > 0)
                {
                  if (i != 3) {
                    this.mViewPager.setCurrentItem(this.mCurrentPage - 1);
                  }
                  return true;
                }
              }
              else if (f3 > f1 + f2)
              {
                if (this.mCurrentPage < j - 1)
                {
                  if (i != 3) {
                    this.mViewPager.setCurrentItem(this.mCurrentPage + 1);
                  }
                  return true;
                }
              }
              else if ((this.mCenterItemClickListener != null) && (i != 3)) {
                this.mCenterItemClickListener.onCenterItemClick(this.mCurrentPage);
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
        break label462;
      }
    }
    label462:
    for (i = 1;; i = 0)
    {
      this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, i);
      this.mLastMotionX = MotionEventCompat.getX(paramMotionEvent, MotionEventCompat.findPointerIndex(paramMotionEvent, this.mActivePointerId));
      break;
    }
  }
  
  public void setClipPadding(float paramFloat)
  {
    this.mClipPadding = paramFloat;
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
  
  public void setFooterColor(int paramInt)
  {
    this.mPaintFooterLine.setColor(paramInt);
    this.mPaintFooterIndicator.setColor(paramInt);
    invalidate();
  }
  
  public void setFooterIndicatorHeight(float paramFloat)
  {
    this.mFooterIndicatorHeight = paramFloat;
    invalidate();
  }
  
  public void setFooterIndicatorPadding(float paramFloat)
  {
    this.mFooterPadding = paramFloat;
    invalidate();
  }
  
  public void setFooterIndicatorStyle(IndicatorStyle paramIndicatorStyle)
  {
    this.mFooterIndicatorStyle = paramIndicatorStyle;
    invalidate();
  }
  
  public void setFooterLineHeight(float paramFloat)
  {
    this.mFooterLineHeight = paramFloat;
    this.mPaintFooterLine.setStrokeWidth(this.mFooterLineHeight);
    invalidate();
  }
  
  public void setLinePosition(LinePosition paramLinePosition)
  {
    this.mLinePosition = paramLinePosition;
    invalidate();
  }
  
  public void setOnCenterItemClickListener(OnCenterItemClickListener paramOnCenterItemClickListener)
  {
    this.mCenterItemClickListener = paramOnCenterItemClickListener;
  }
  
  public void setOnPageChangeListener(ViewPager.OnPageChangeListener paramOnPageChangeListener)
  {
    this.mListener = paramOnPageChangeListener;
  }
  
  public void setSelectedBold(boolean paramBoolean)
  {
    this.mBoldText = paramBoolean;
    invalidate();
  }
  
  public void setSelectedColor(int paramInt)
  {
    this.mColorSelected = paramInt;
    invalidate();
  }
  
  public void setTextColor(int paramInt)
  {
    this.mPaintText.setColor(paramInt);
    this.mColorText = paramInt;
    invalidate();
  }
  
  public void setTextSize(float paramFloat)
  {
    this.mPaintText.setTextSize(paramFloat);
    invalidate();
  }
  
  public void setTitlePadding(float paramFloat)
  {
    this.mTitlePadding = paramFloat;
    invalidate();
  }
  
  public void setTopPadding(float paramFloat)
  {
    this.mTopPadding = paramFloat;
    invalidate();
  }
  
  public void setTypeface(Typeface paramTypeface)
  {
    this.mPaintText.setTypeface(paramTypeface);
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
  
  public static enum IndicatorStyle
  {
    None(0),  Triangle(1),  Underline(2);
    
    public final int value;
    
    private IndicatorStyle(int paramInt)
    {
      this.value = paramInt;
    }
    
    public static IndicatorStyle fromValue(int paramInt)
    {
      IndicatorStyle[] arrayOfIndicatorStyle = values();
      int j = arrayOfIndicatorStyle.length;
      int i = 0;
      while (i < j)
      {
        IndicatorStyle localIndicatorStyle = arrayOfIndicatorStyle[i];
        if (localIndicatorStyle.value == paramInt) {
          return localIndicatorStyle;
        }
        i += 1;
      }
      return null;
    }
  }
  
  public static enum LinePosition
  {
    Bottom(0),  Top(1);
    
    public final int value;
    
    private LinePosition(int paramInt)
    {
      this.value = paramInt;
    }
    
    public static LinePosition fromValue(int paramInt)
    {
      LinePosition[] arrayOfLinePosition = values();
      int j = arrayOfLinePosition.length;
      int i = 0;
      while (i < j)
      {
        LinePosition localLinePosition = arrayOfLinePosition[i];
        if (localLinePosition.value == paramInt) {
          return localLinePosition;
        }
        i += 1;
      }
      return null;
    }
  }
  
  public static abstract interface OnCenterItemClickListener
  {
    public abstract void onCenterItemClick(int paramInt);
  }
  
  static class SavedState
    extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public TitlePageIndicator.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new TitlePageIndicator.SavedState(paramAnonymousParcel, null);
      }
      
      public TitlePageIndicator.SavedState[] newArray(int paramAnonymousInt)
      {
        return new TitlePageIndicator.SavedState[paramAnonymousInt];
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


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/viewpagerindicator/TitlePageIndicator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */