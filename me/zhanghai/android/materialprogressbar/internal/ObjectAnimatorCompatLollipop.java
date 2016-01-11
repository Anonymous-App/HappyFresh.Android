package me.zhanghai.android.materialprogressbar.internal;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.Path;
import android.util.Property;

@TargetApi(21)
class ObjectAnimatorCompatLollipop
{
  public static <T> ObjectAnimator ofArgb(T paramT, Property<T, Integer> paramProperty, int... paramVarArgs)
  {
    return ObjectAnimator.ofArgb(paramT, paramProperty, paramVarArgs);
  }
  
  public static ObjectAnimator ofArgb(Object paramObject, String paramString, int... paramVarArgs)
  {
    return ObjectAnimator.ofArgb(paramObject, paramString, paramVarArgs);
  }
  
  public static <T> ObjectAnimator ofFloat(T paramT, Property<T, Float> paramProperty1, Property<T, Float> paramProperty2, Path paramPath)
  {
    return ObjectAnimator.ofFloat(paramT, paramProperty1, paramProperty2, paramPath);
  }
  
  public static ObjectAnimator ofFloat(Object paramObject, String paramString1, String paramString2, Path paramPath)
  {
    return ObjectAnimator.ofFloat(paramObject, paramString1, paramString2, paramPath);
  }
  
  public static <T> ObjectAnimator ofInt(T paramT, Property<T, Integer> paramProperty1, Property<T, Integer> paramProperty2, Path paramPath)
  {
    return ObjectAnimator.ofInt(paramT, paramProperty1, paramProperty2, paramPath);
  }
  
  public static ObjectAnimator ofInt(Object paramObject, String paramString1, String paramString2, Path paramPath)
  {
    return ObjectAnimator.ofInt(paramObject, paramString1, paramString2, paramPath);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/me/zhanghai/android/materialprogressbar/internal/ObjectAnimatorCompatLollipop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */