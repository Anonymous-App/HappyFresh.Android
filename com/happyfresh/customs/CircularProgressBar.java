package com.happyfresh.customs;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import com.happyfresh.R.styleable;

public class CircularProgressBar
  extends View
{
  private CircularProgressDrawable mDrawable;
  
  static
  {
    if (!CircularProgressBar.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public CircularProgressBar(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public CircularProgressBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public CircularProgressBar(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    int i = getResources().getColor(2131493005);
    paramInt = i;
    if (paramAttributeSet != null)
    {
      paramContext = getContext().getTheme().obtainStyledAttributes(paramAttributeSet, R.styleable.com_happyfresh_customs_CircularProgressBar, 0, 0);
      try
      {
        i = paramContext.getColor(0, i);
        paramContext.recycle();
        paramInt = i;
        if (!$assertionsDisabled)
        {
          paramInt = i;
          if (4 <= 0) {
            throw new AssertionError();
          }
        }
      }
      finally
      {
        paramContext.recycle();
      }
    }
    this.mDrawable = new CircularProgressDrawable(paramInt, 4);
    this.mDrawable.setCallback(this);
  }
  
  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    this.mDrawable.draw(paramCanvas);
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.mDrawable.setBounds(0, 0, paramInt1, paramInt2);
  }
  
  protected void onVisibilityChanged(View paramView, int paramInt)
  {
    super.onVisibilityChanged(paramView, paramInt);
    if (paramInt == 0)
    {
      this.mDrawable.start();
      return;
    }
    this.mDrawable.stop();
  }
  
  protected boolean verifyDrawable(Drawable paramDrawable)
  {
    return (paramDrawable == this.mDrawable) || (super.verifyDrawable(paramDrawable));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/customs/CircularProgressBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */