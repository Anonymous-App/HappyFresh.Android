package me.zhanghai.android.materialprogressbar.internal;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Build.VERSION;
import android.util.Property;

public class ObjectAnimatorCompat
{
  public static <T> ObjectAnimator ofArgb(T paramT, Property<T, Integer> paramProperty, int... paramVarArgs)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return ObjectAnimatorCompatLollipop.ofArgb(paramT, paramProperty, paramVarArgs);
    }
    return ObjectAnimatorCompatBase.ofArgb(paramT, paramProperty, paramVarArgs);
  }
  
  public static ObjectAnimator ofArgb(Object paramObject, String paramString, int... paramVarArgs)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return ObjectAnimatorCompatLollipop.ofArgb(paramObject, paramString, paramVarArgs);
    }
    return ObjectAnimatorCompatBase.ofArgb(paramObject, paramString, paramVarArgs);
  }
  
  public static <T> ObjectAnimator ofFloat(T paramT, Property<T, Float> paramProperty1, Property<T, Float> paramProperty2, Path paramPath)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return ObjectAnimatorCompatLollipop.ofFloat(paramT, paramProperty1, paramProperty2, paramPath);
    }
    return ObjectAnimatorCompatBase.ofFloat(paramT, paramProperty1, paramProperty2, paramPath);
  }
  
  public static ObjectAnimator ofFloat(Object paramObject, String paramString1, String paramString2, Path paramPath)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return ObjectAnimatorCompatLollipop.ofFloat(paramObject, paramString1, paramString2, paramPath);
    }
    return ObjectAnimatorCompatBase.ofFloat(paramObject, paramString1, paramString2, paramPath);
  }
  
  public static <T> ObjectAnimator ofInt(T paramT, Property<T, Integer> paramProperty1, Property<T, Integer> paramProperty2, Path paramPath)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return ObjectAnimatorCompatLollipop.ofInt(paramT, paramProperty1, paramProperty2, paramPath);
    }
    return ObjectAnimatorCompatBase.ofInt(paramT, paramProperty1, paramProperty2, paramPath);
  }
  
  public static ObjectAnimator ofInt(Object paramObject, String paramString1, String paramString2, Path paramPath)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return ObjectAnimatorCompatLollipop.ofInt(paramObject, paramString1, paramString2, paramPath);
    }
    return ObjectAnimatorCompatBase.ofInt(paramObject, paramString1, paramString2, paramPath);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/me/zhanghai/android/materialprogressbar/internal/ObjectAnimatorCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */