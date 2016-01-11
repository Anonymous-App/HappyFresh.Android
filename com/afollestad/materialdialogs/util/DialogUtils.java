package com.afollestad.materialdialogs.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ArrayRes;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;

public class DialogUtils
{
  public static int adjustAlpha(int paramInt, float paramFloat)
  {
    return Color.argb(Math.round(Color.alpha(paramInt) * paramFloat), Color.red(paramInt), Color.green(paramInt), Color.blue(paramInt));
  }
  
  public static ColorStateList getActionTextColorStateList(Context paramContext, @ColorRes int paramInt)
  {
    TypedValue localTypedValue = new TypedValue();
    paramContext.getResources().getValue(paramInt, localTypedValue, true);
    if ((localTypedValue.type >= 28) && (localTypedValue.type <= 31)) {
      return getActionTextStateList(paramContext, localTypedValue.data);
    }
    if (Build.VERSION.SDK_INT <= 22) {
      return paramContext.getResources().getColorStateList(paramInt);
    }
    return paramContext.getColorStateList(paramInt);
  }
  
  public static ColorStateList getActionTextStateList(Context paramContext, int paramInt)
  {
    int j = resolveColor(paramContext, 16842806);
    int i = paramInt;
    if (paramInt == 0) {
      i = j;
    }
    paramContext = new int[] { -16842910 };
    paramInt = adjustAlpha(i, 0.4F);
    return new ColorStateList(new int[][] { paramContext, new int[0] }, new int[] { paramInt, i });
  }
  
  public static int getColor(Context paramContext, @ColorRes int paramInt)
  {
    if (Build.VERSION.SDK_INT <= 22) {
      return paramContext.getResources().getColor(paramInt);
    }
    return paramContext.getColor(paramInt);
  }
  
  public static int[] getColorArray(@NonNull Context paramContext, @ArrayRes int paramInt)
  {
    if (paramInt == 0) {
      return null;
    }
    paramContext = paramContext.getResources().obtainTypedArray(paramInt);
    int[] arrayOfInt = new int[paramContext.length()];
    paramInt = 0;
    while (paramInt < paramContext.length())
    {
      arrayOfInt[paramInt] = paramContext.getColor(paramInt, 0);
      paramInt += 1;
    }
    paramContext.recycle();
    return arrayOfInt;
  }
  
  private static int gravityEnumToAttrInt(GravityEnum paramGravityEnum)
  {
    switch (paramGravityEnum)
    {
    default: 
      return 0;
    case ???: 
      return 1;
    }
    return 2;
  }
  
  public static void hideKeyboard(DialogInterface paramDialogInterface, final MaterialDialog.Builder paramBuilder)
  {
    paramDialogInterface = (MaterialDialog)paramDialogInterface;
    if (paramDialogInterface.getInputEditText() == null) {
      return;
    }
    paramDialogInterface.getInputEditText().post(new Runnable()
    {
      public void run()
      {
        this.val$dialog.getInputEditText().requestFocus();
        InputMethodManager localInputMethodManager = (InputMethodManager)paramBuilder.getContext().getSystemService("input_method");
        if (localInputMethodManager != null) {
          localInputMethodManager.hideSoftInputFromWindow(this.val$dialog.getInputEditText().getWindowToken(), 0);
        }
      }
    });
  }
  
  public static boolean isColorDark(int paramInt)
  {
    return 1.0D - (0.299D * Color.red(paramInt) + 0.587D * Color.green(paramInt) + 0.114D * Color.blue(paramInt)) / 255.0D >= 0.5D;
  }
  
  public static ColorStateList resolveActionTextColorStateList(Context paramContext, @AttrRes int paramInt, ColorStateList paramColorStateList)
  {
    TypedArray localTypedArray = paramContext.getTheme().obtainStyledAttributes(new int[] { paramInt });
    try
    {
      TypedValue localTypedValue = localTypedArray.peekValue(0);
      if (localTypedValue == null) {
        return paramColorStateList;
      }
      if ((localTypedValue.type >= 28) && (localTypedValue.type <= 31))
      {
        paramContext = getActionTextStateList(paramContext, localTypedValue.data);
        return paramContext;
      }
      paramContext = localTypedArray.getColorStateList(0);
      if (paramContext != null) {
        return paramContext;
      }
      return paramColorStateList;
    }
    finally
    {
      localTypedArray.recycle();
    }
  }
  
  public static boolean resolveBoolean(Context paramContext, @AttrRes int paramInt)
  {
    return resolveBoolean(paramContext, paramInt, false);
  }
  
  public static boolean resolveBoolean(Context paramContext, @AttrRes int paramInt, boolean paramBoolean)
  {
    paramContext = paramContext.getTheme().obtainStyledAttributes(new int[] { paramInt });
    try
    {
      paramBoolean = paramContext.getBoolean(0, paramBoolean);
      return paramBoolean;
    }
    finally
    {
      paramContext.recycle();
    }
  }
  
  public static int resolveColor(Context paramContext, @AttrRes int paramInt)
  {
    return resolveColor(paramContext, paramInt, 0);
  }
  
  public static int resolveColor(Context paramContext, @AttrRes int paramInt1, int paramInt2)
  {
    paramContext = paramContext.getTheme().obtainStyledAttributes(new int[] { paramInt1 });
    try
    {
      paramInt1 = paramContext.getColor(0, paramInt2);
      return paramInt1;
    }
    finally
    {
      paramContext.recycle();
    }
  }
  
  public static int resolveDimension(Context paramContext, @AttrRes int paramInt)
  {
    return resolveDimension(paramContext, paramInt, -1);
  }
  
  private static int resolveDimension(Context paramContext, @AttrRes int paramInt1, int paramInt2)
  {
    paramContext = paramContext.getTheme().obtainStyledAttributes(new int[] { paramInt1 });
    try
    {
      paramInt1 = paramContext.getDimensionPixelSize(0, paramInt2);
      return paramInt1;
    }
    finally
    {
      paramContext.recycle();
    }
  }
  
  public static Drawable resolveDrawable(Context paramContext, @AttrRes int paramInt)
  {
    return resolveDrawable(paramContext, paramInt, null);
  }
  
  private static Drawable resolveDrawable(Context paramContext, @AttrRes int paramInt, Drawable paramDrawable)
  {
    TypedArray localTypedArray = paramContext.getTheme().obtainStyledAttributes(new int[] { paramInt });
    try
    {
      paramContext = localTypedArray.getDrawable(0);
      Object localObject = paramContext;
      if (paramContext == null)
      {
        localObject = paramContext;
        if (paramDrawable != null) {
          localObject = paramDrawable;
        }
      }
      return (Drawable)localObject;
    }
    finally
    {
      localTypedArray.recycle();
    }
  }
  
  public static float resolveFloat(Context paramContext, int paramInt)
  {
    paramContext = paramContext.obtainStyledAttributes(null, new int[] { paramInt });
    try
    {
      float f = paramContext.getFloat(0, 0.0F);
      return f;
    }
    finally
    {
      paramContext.recycle();
    }
  }
  
  public static GravityEnum resolveGravityEnum(Context paramContext, @AttrRes int paramInt, GravityEnum paramGravityEnum)
  {
    paramContext = paramContext.getTheme().obtainStyledAttributes(new int[] { paramInt });
    for (;;)
    {
      try
      {
        switch (paramContext.getInt(0, gravityEnumToAttrInt(paramGravityEnum)))
        {
        case 1: 
          paramGravityEnum = GravityEnum.START;
          return paramGravityEnum;
        }
      }
      finally
      {
        paramContext.recycle();
      }
      paramGravityEnum = GravityEnum.CENTER;
      paramContext.recycle();
      return paramGravityEnum;
      paramGravityEnum = GravityEnum.END;
      paramContext.recycle();
      return paramGravityEnum;
    }
  }
  
  public static String resolveString(Context paramContext, @AttrRes int paramInt)
  {
    TypedValue localTypedValue = new TypedValue();
    paramContext.getTheme().resolveAttribute(paramInt, localTypedValue, true);
    return (String)localTypedValue.string;
  }
  
  public static void setBackgroundCompat(View paramView, Drawable paramDrawable)
  {
    if (Build.VERSION.SDK_INT < 16)
    {
      paramView.setBackgroundDrawable(paramDrawable);
      return;
    }
    paramView.setBackground(paramDrawable);
  }
  
  public static void showKeyboard(DialogInterface paramDialogInterface, final MaterialDialog.Builder paramBuilder)
  {
    paramDialogInterface = (MaterialDialog)paramDialogInterface;
    if (paramDialogInterface.getInputEditText() == null) {
      return;
    }
    paramDialogInterface.getInputEditText().post(new Runnable()
    {
      public void run()
      {
        this.val$dialog.getInputEditText().requestFocus();
        InputMethodManager localInputMethodManager = (InputMethodManager)paramBuilder.getContext().getSystemService("input_method");
        if (localInputMethodManager != null) {
          localInputMethodManager.showSoftInput(this.val$dialog.getInputEditText(), 1);
        }
      }
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/afollestad/materialdialogs/util/DialogUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */