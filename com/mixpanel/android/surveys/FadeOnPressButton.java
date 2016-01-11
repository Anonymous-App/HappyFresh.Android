package com.mixpanel.android.surveys;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

@TargetApi(16)
public class FadeOnPressButton
  extends Button
{
  private boolean mIsFaded;
  
  public FadeOnPressButton(Context paramContext)
  {
    super(paramContext);
  }
  
  public FadeOnPressButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public FadeOnPressButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void setAlphaBySDK(float paramFloat)
  {
    setAlpha(paramFloat);
  }
  
  protected void drawableStateChanged()
  {
    int[] arrayOfInt = getDrawableState();
    int k = 0;
    int m = arrayOfInt.length;
    int i = 0;
    for (;;)
    {
      int j = k;
      if (i < m)
      {
        if (arrayOfInt[i] == 16842919)
        {
          if (!this.mIsFaded) {
            setAlphaBySDK(0.5F);
          }
          j = 1;
        }
      }
      else
      {
        if ((this.mIsFaded) && (j == 0))
        {
          setAlphaBySDK(1.0F);
          this.mIsFaded = true;
        }
        super.drawableStateChanged();
        return;
      }
      i += 1;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/surveys/FadeOnPressButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */