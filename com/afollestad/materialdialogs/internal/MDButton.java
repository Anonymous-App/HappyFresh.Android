package com.afollestad.materialdialogs.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v7.text.AllCapsTransformationMethod;
import android.util.AttributeSet;
import android.widget.TextView;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.R.dimen;
import com.afollestad.materialdialogs.util.DialogUtils;

public class MDButton
  extends TextView
{
  private Drawable mDefaultBackground;
  private boolean mStacked = false;
  private Drawable mStackedBackground;
  private int mStackedEndPadding;
  private GravityEnum mStackedGravity;
  
  public MDButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet, 0, 0);
  }
  
  public MDButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet, paramInt, 0);
  }
  
  @TargetApi(21)
  public MDButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    init(paramContext, paramAttributeSet, paramInt1, paramInt2);
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    this.mStackedEndPadding = paramContext.getResources().getDimensionPixelSize(R.dimen.md_dialog_frame_margin);
    this.mStackedGravity = GravityEnum.END;
  }
  
  public void setAllCapsCompat(boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 14)
    {
      setAllCaps(paramBoolean);
      return;
    }
    if (paramBoolean)
    {
      setTransformationMethod(new AllCapsTransformationMethod(getContext()));
      return;
    }
    setTransformationMethod(null);
  }
  
  public void setDefaultSelector(Drawable paramDrawable)
  {
    this.mDefaultBackground = paramDrawable;
    if (!this.mStacked) {
      setStacked(false, true);
    }
  }
  
  void setStacked(boolean paramBoolean1, boolean paramBoolean2)
  {
    int i;
    if ((this.mStacked != paramBoolean1) || (paramBoolean2))
    {
      if (!paramBoolean1) {
        break label103;
      }
      i = this.mStackedGravity.getGravityInt() | 0x10;
      setGravity(i);
      if (Build.VERSION.SDK_INT >= 17)
      {
        if (!paramBoolean1) {
          break label109;
        }
        i = this.mStackedGravity.getTextAlignment();
        label52:
        setTextAlignment(i);
      }
      if (!paramBoolean1) {
        break label114;
      }
    }
    label103:
    label109:
    label114:
    for (Drawable localDrawable = this.mStackedBackground;; localDrawable = this.mDefaultBackground)
    {
      DialogUtils.setBackgroundCompat(this, localDrawable);
      if (paramBoolean1) {
        setPadding(this.mStackedEndPadding, getPaddingTop(), this.mStackedEndPadding, getPaddingBottom());
      }
      this.mStacked = paramBoolean1;
      return;
      i = 17;
      break;
      i = 4;
      break label52;
    }
  }
  
  public void setStackedGravity(GravityEnum paramGravityEnum)
  {
    this.mStackedGravity = paramGravityEnum;
  }
  
  public void setStackedSelector(Drawable paramDrawable)
  {
    this.mStackedBackground = paramDrawable;
    if (this.mStacked) {
      setStacked(true, true);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/afollestad/materialdialogs/internal/MDButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */