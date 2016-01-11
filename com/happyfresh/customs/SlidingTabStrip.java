package com.happyfresh.customs;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

class SlidingTabStrip
  extends LinearLayout
{
  private static final byte DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = 38;
  private static final int DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 0;
  private static final byte DEFAULT_DIVIDER_COLOR_ALPHA = 32;
  private static final float DEFAULT_DIVIDER_HEIGHT = 0.5F;
  private static final int DEFAULT_DIVIDER_THICKNESS_DIPS = 1;
  private static final int DEFAULT_SELECTED_INDICATOR_COLOR = -13388315;
  private static final int SELECTED_INDICATOR_THICKNESS_DIPS = 3;
  private boolean enableNextDivider = false;
  private final Paint mBottomBorderPaint;
  private final int mBottomBorderThickness;
  private Context mContext;
  private SlidingTabLayout.TabColorizer mCustomTabColorizer;
  private final int mDefaultBottomBorderColor;
  private final SimpleTabColorizer mDefaultTabColorizer;
  private final float mDividerHeight;
  private final Paint mDividerPaint;
  private final Paint mSelectedIndicatorPaint;
  private final int mSelectedIndicatorThickness;
  private int mSelectedPosition;
  private float mSelectionOffset;
  
  SlidingTabStrip(Context paramContext)
  {
    this(paramContext, null);
    this.mContext = paramContext;
  }
  
  SlidingTabStrip(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
    setWillNotDraw(false);
    float f = getResources().getDisplayMetrics().density;
    paramAttributeSet = new TypedValue();
    paramContext.getTheme().resolveAttribute(16842800, paramAttributeSet, true);
    int i = paramAttributeSet.data;
    this.mDefaultBottomBorderColor = setColorAlpha(i, (byte)38);
    this.mDefaultTabColorizer = new SimpleTabColorizer(null);
    this.mDefaultTabColorizer.setIndicatorColors(new int[] { -13388315 });
    this.mDefaultTabColorizer.setDividerColors(new int[] { setColorAlpha(i, 32) });
    this.mBottomBorderThickness = ((int)(0.0F * f));
    this.mBottomBorderPaint = new Paint();
    this.mBottomBorderPaint.setColor(this.mDefaultBottomBorderColor);
    this.mSelectedIndicatorThickness = ((int)(3.0F * f));
    this.mSelectedIndicatorPaint = new Paint();
    this.mDividerHeight = 0.5F;
    this.mDividerPaint = new Paint();
    this.mDividerPaint.setStrokeWidth((int)(1.0F * f));
  }
  
  private static int blendColors(int paramInt1, int paramInt2, float paramFloat)
  {
    float f1 = 1.0F - paramFloat;
    float f2 = Color.red(paramInt1);
    float f3 = Color.red(paramInt2);
    float f4 = Color.green(paramInt1);
    float f5 = Color.green(paramInt2);
    float f6 = Color.blue(paramInt1);
    float f7 = Color.blue(paramInt2);
    return Color.rgb((int)(f2 * paramFloat + f3 * f1), (int)(f4 * paramFloat + f5 * f1), (int)(f6 * paramFloat + f7 * f1));
  }
  
  private static int setColorAlpha(int paramInt, byte paramByte)
  {
    return Color.argb(paramByte, Color.red(paramInt), Color.green(paramInt), Color.blue(paramInt));
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    int i2 = getHeight();
    int i3 = getChildCount();
    int i4 = (int)(Math.min(Math.max(0.0F, this.mDividerHeight), 1.0F) * i2);
    if (this.mCustomTabColorizer != null) {}
    for (Object localObject = this.mCustomTabColorizer;; localObject = this.mDefaultTabColorizer)
    {
      View localView;
      int j;
      if (i3 > 0)
      {
        localView = getChildAt(this.mSelectedPosition);
        int i1 = localView.getLeft();
        int n = localView.getRight();
        j = ((SlidingTabLayout.TabColorizer)localObject).getIndicatorColor(this.mSelectedPosition);
        i = j;
        int m = i1;
        int k = n;
        if (this.mSelectionOffset > 0.0F)
        {
          i = j;
          m = i1;
          k = n;
          if (this.mSelectedPosition < getChildCount() - 1)
          {
            k = ((SlidingTabLayout.TabColorizer)localObject).getIndicatorColor(this.mSelectedPosition + 1);
            i = j;
            if (j != k) {
              i = blendColors(k, j, this.mSelectionOffset);
            }
            localView = getChildAt(this.mSelectedPosition + 1);
            m = (int)(this.mSelectionOffset * localView.getLeft() + (1.0F - this.mSelectionOffset) * i1);
            k = (int)(this.mSelectionOffset * localView.getRight() + (1.0F - this.mSelectionOffset) * n);
          }
        }
        this.mSelectedIndicatorPaint.setColor(i);
        paramCanvas.drawRect(m, i2 - this.mSelectedIndicatorThickness, k, i2, this.mSelectedIndicatorPaint);
      }
      paramCanvas.drawRect(0.0F, i2 - this.mBottomBorderThickness, getWidth(), i2, this.mBottomBorderPaint);
      int i = (i2 - i4) / 2;
      i = 0;
      while (i < i3 - 1)
      {
        localView = getChildAt(i);
        this.mDividerPaint.setColor(((SlidingTabLayout.TabColorizer)localObject).getDividerColor(i));
        this.mDividerPaint.setStrokeWidth(10.0F);
        this.mDividerPaint.setAntiAlias(true);
        if (this.enableNextDivider)
        {
          Bitmap localBitmap = BitmapFactory.decodeResource(getResources(), 2130837821);
          j = (i2 - i4 + localBitmap.getHeight()) / 2;
          paramCanvas.drawBitmap(localBitmap, localView.getRight(), j, this.mDividerPaint);
        }
        i += 1;
      }
    }
  }
  
  void onViewPagerPageChanged(int paramInt, float paramFloat)
  {
    this.mSelectedPosition = paramInt;
    this.mSelectionOffset = paramFloat;
    invalidate();
  }
  
  void setCustomTabColorizer(SlidingTabLayout.TabColorizer paramTabColorizer)
  {
    this.mCustomTabColorizer = paramTabColorizer;
    invalidate();
  }
  
  void setDividerColors(int... paramVarArgs)
  {
    this.mCustomTabColorizer = null;
    this.mDefaultTabColorizer.setDividerColors(paramVarArgs);
    invalidate();
  }
  
  void setEnableNextDivider()
  {
    this.enableNextDivider = true;
  }
  
  void setSelectedIndicatorColors(int... paramVarArgs)
  {
    this.mCustomTabColorizer = null;
    this.mDefaultTabColorizer.setIndicatorColors(paramVarArgs);
    invalidate();
  }
  
  private static class SimpleTabColorizer
    implements SlidingTabLayout.TabColorizer
  {
    private int[] mDividerColors;
    private int[] mIndicatorColors;
    
    public final int getDividerColor(int paramInt)
    {
      return this.mDividerColors[(paramInt % this.mDividerColors.length)];
    }
    
    public final int getIndicatorColor(int paramInt)
    {
      return this.mIndicatorColors[(paramInt % this.mIndicatorColors.length)];
    }
    
    void setDividerColors(int... paramVarArgs)
    {
      this.mDividerColors = paramVarArgs;
    }
    
    void setIndicatorColors(int... paramVarArgs)
    {
      this.mIndicatorColors = paramVarArgs;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/customs/SlidingTabStrip.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */