package me.zhanghai.android.materialprogressbar.internal;

import android.content.Context;
import android.content.res.TypedArray;

public class ThemeUtils
{
  public static int getColorFromAttrRes(int paramInt, Context paramContext)
  {
    paramContext = paramContext.obtainStyledAttributes(new int[] { paramInt });
    try
    {
      paramInt = paramContext.getColor(0, 0);
      return paramInt;
    }
    finally
    {
      paramContext.recycle();
    }
  }
  
  public static float getFloatFromAttrRes(int paramInt, Context paramContext)
  {
    paramContext = paramContext.obtainStyledAttributes(new int[] { paramInt });
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
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/me/zhanghai/android/materialprogressbar/internal/ThemeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */