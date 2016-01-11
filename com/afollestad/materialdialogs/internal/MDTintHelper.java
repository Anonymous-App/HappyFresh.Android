package com.afollestad.materialdialogs.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatEditText;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SeekBar;
import com.afollestad.materialdialogs.R.attr;
import com.afollestad.materialdialogs.R.drawable;
import com.afollestad.materialdialogs.util.DialogUtils;

public class MDTintHelper
{
  private static ColorStateList createEditTextColorStateList(@NonNull Context paramContext, @ColorInt int paramInt)
  {
    int[][] arrayOfInt = new int[3][];
    int[] arrayOfInt1 = new int[3];
    arrayOfInt[0] = { -16842910 };
    arrayOfInt1[0] = DialogUtils.resolveColor(paramContext, R.attr.colorControlNormal);
    int i = 0 + 1;
    arrayOfInt[i] = { -16842919, -16842908 };
    arrayOfInt1[i] = DialogUtils.resolveColor(paramContext, R.attr.colorControlNormal);
    i += 1;
    arrayOfInt[i] = new int[0];
    arrayOfInt1[i] = paramInt;
    return new ColorStateList(arrayOfInt, arrayOfInt1);
  }
  
  public static void setTint(@NonNull CheckBox paramCheckBox, @ColorInt int paramInt)
  {
    int i = DialogUtils.resolveColor(paramCheckBox.getContext(), R.attr.colorControlNormal);
    ColorStateList localColorStateList = new ColorStateList(new int[][] { { -16842912 }, { 16842912 } }, new int[] { i, paramInt });
    if (Build.VERSION.SDK_INT >= 21)
    {
      paramCheckBox.setButtonTintList(localColorStateList);
      return;
    }
    Drawable localDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(paramCheckBox.getContext(), R.drawable.abc_btn_check_material));
    DrawableCompat.setTintList(localDrawable, localColorStateList);
    paramCheckBox.setButtonDrawable(localDrawable);
  }
  
  public static void setTint(@NonNull EditText paramEditText, @ColorInt int paramInt)
  {
    ColorStateList localColorStateList = createEditTextColorStateList(paramEditText.getContext(), paramInt);
    if ((paramEditText instanceof AppCompatEditText)) {
      ((AppCompatEditText)paramEditText).setSupportBackgroundTintList(localColorStateList);
    }
    while (Build.VERSION.SDK_INT < 21) {
      return;
    }
    paramEditText.setBackgroundTintList(localColorStateList);
  }
  
  public static void setTint(@NonNull ProgressBar paramProgressBar, @ColorInt int paramInt)
  {
    setTint(paramProgressBar, paramInt, false);
  }
  
  public static void setTint(@NonNull ProgressBar paramProgressBar, @ColorInt int paramInt, boolean paramBoolean)
  {
    Object localObject = ColorStateList.valueOf(paramInt);
    if (Build.VERSION.SDK_INT >= 21)
    {
      paramProgressBar.setProgressTintList((ColorStateList)localObject);
      paramProgressBar.setSecondaryProgressTintList((ColorStateList)localObject);
      if (!paramBoolean) {
        paramProgressBar.setIndeterminateTintList((ColorStateList)localObject);
      }
    }
    do
    {
      return;
      localObject = PorterDuff.Mode.SRC_IN;
      if (Build.VERSION.SDK_INT <= 10) {
        localObject = PorterDuff.Mode.MULTIPLY;
      }
      if ((!paramBoolean) && (paramProgressBar.getIndeterminateDrawable() != null)) {
        paramProgressBar.getIndeterminateDrawable().setColorFilter(paramInt, (PorterDuff.Mode)localObject);
      }
    } while (paramProgressBar.getProgressDrawable() == null);
    paramProgressBar.getProgressDrawable().setColorFilter(paramInt, (PorterDuff.Mode)localObject);
  }
  
  public static void setTint(@NonNull RadioButton paramRadioButton, @ColorInt int paramInt)
  {
    int i = DialogUtils.resolveColor(paramRadioButton.getContext(), R.attr.colorControlNormal);
    ColorStateList localColorStateList = new ColorStateList(new int[][] { { -16842912 }, { 16842912 } }, new int[] { i, paramInt });
    if (Build.VERSION.SDK_INT >= 21)
    {
      paramRadioButton.setButtonTintList(localColorStateList);
      return;
    }
    Drawable localDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(paramRadioButton.getContext(), R.drawable.abc_btn_radio_material));
    DrawableCompat.setTintList(localDrawable, localColorStateList);
    paramRadioButton.setButtonDrawable(localDrawable);
  }
  
  public static void setTint(@NonNull SeekBar paramSeekBar, @ColorInt int paramInt)
  {
    Object localObject = ColorStateList.valueOf(paramInt);
    if (Build.VERSION.SDK_INT >= 21)
    {
      paramSeekBar.setThumbTintList((ColorStateList)localObject);
      paramSeekBar.setProgressTintList((ColorStateList)localObject);
    }
    do
    {
      do
      {
        return;
        if (Build.VERSION.SDK_INT <= 10) {
          break;
        }
        localDrawable = DrawableCompat.wrap(paramSeekBar.getProgressDrawable());
        paramSeekBar.setProgressDrawable(localDrawable);
        DrawableCompat.setTintList(localDrawable, (ColorStateList)localObject);
      } while (Build.VERSION.SDK_INT < 16);
      Drawable localDrawable = DrawableCompat.wrap(paramSeekBar.getThumb());
      DrawableCompat.setTintList(localDrawable, (ColorStateList)localObject);
      paramSeekBar.setThumb(localDrawable);
      return;
      localObject = PorterDuff.Mode.SRC_IN;
      if (Build.VERSION.SDK_INT <= 10) {
        localObject = PorterDuff.Mode.MULTIPLY;
      }
      if (paramSeekBar.getIndeterminateDrawable() != null) {
        paramSeekBar.getIndeterminateDrawable().setColorFilter(paramInt, (PorterDuff.Mode)localObject);
      }
    } while (paramSeekBar.getProgressDrawable() == null);
    paramSeekBar.getProgressDrawable().setColorFilter(paramInt, (PorterDuff.Mode)localObject);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/afollestad/materialdialogs/internal/MDTintHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */