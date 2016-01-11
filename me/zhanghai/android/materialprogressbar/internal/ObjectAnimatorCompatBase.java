package me.zhanghai.android.materialprogressbar.internal;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Size;
import android.util.Property;

class ObjectAnimatorCompatBase
{
  private static final int NUM_POINTS = 500;
  
  private static void calculateXYValues(Path paramPath, @Size(500L) float[] paramArrayOfFloat1, @Size(500L) float[] paramArrayOfFloat2)
  {
    paramPath = new PathMeasure(paramPath, false);
    float f = paramPath.getLength();
    float[] arrayOfFloat = new float[2];
    int i = 0;
    while (i < 500)
    {
      paramPath.getPosTan(i * f / 499.0F, arrayOfFloat, null);
      paramArrayOfFloat1[i] = arrayOfFloat[0];
      paramArrayOfFloat2[i] = arrayOfFloat[1];
      i += 1;
    }
  }
  
  private static void calculateXYValues(Path paramPath, @Size(500L) int[] paramArrayOfInt1, @Size(500L) int[] paramArrayOfInt2)
  {
    paramPath = new PathMeasure(paramPath, false);
    float f = paramPath.getLength();
    float[] arrayOfFloat = new float[2];
    int i = 0;
    while (i < 500)
    {
      paramPath.getPosTan(i * f / 499.0F, arrayOfFloat, null);
      paramArrayOfInt1[i] = Math.round(arrayOfFloat[0]);
      paramArrayOfInt2[i] = Math.round(arrayOfFloat[1]);
      i += 1;
    }
  }
  
  public static <T> ObjectAnimator ofArgb(T paramT, Property<T, Integer> paramProperty, int... paramVarArgs)
  {
    paramT = ObjectAnimator.ofInt(paramT, paramProperty, paramVarArgs);
    paramT.setEvaluator(new ArgbEvaluator());
    return paramT;
  }
  
  public static ObjectAnimator ofArgb(Object paramObject, String paramString, int... paramVarArgs)
  {
    paramObject = ObjectAnimator.ofInt(paramObject, paramString, paramVarArgs);
    ((ObjectAnimator)paramObject).setEvaluator(new ArgbEvaluator());
    return (ObjectAnimator)paramObject;
  }
  
  public static <T> ObjectAnimator ofFloat(T paramT, Property<T, Float> paramProperty1, Property<T, Float> paramProperty2, Path paramPath)
  {
    float[] arrayOfFloat1 = new float['Ǵ'];
    float[] arrayOfFloat2 = new float['Ǵ'];
    calculateXYValues(paramPath, arrayOfFloat1, arrayOfFloat2);
    return ObjectAnimator.ofPropertyValuesHolder(paramT, new PropertyValuesHolder[] { PropertyValuesHolder.ofFloat(paramProperty1, arrayOfFloat1), PropertyValuesHolder.ofFloat(paramProperty2, arrayOfFloat2) });
  }
  
  public static ObjectAnimator ofFloat(Object paramObject, String paramString1, String paramString2, Path paramPath)
  {
    float[] arrayOfFloat1 = new float['Ǵ'];
    float[] arrayOfFloat2 = new float['Ǵ'];
    calculateXYValues(paramPath, arrayOfFloat1, arrayOfFloat2);
    return ObjectAnimator.ofPropertyValuesHolder(paramObject, new PropertyValuesHolder[] { PropertyValuesHolder.ofFloat(paramString1, arrayOfFloat1), PropertyValuesHolder.ofFloat(paramString2, arrayOfFloat2) });
  }
  
  public static <T> ObjectAnimator ofInt(T paramT, Property<T, Integer> paramProperty1, Property<T, Integer> paramProperty2, Path paramPath)
  {
    int[] arrayOfInt1 = new int['Ǵ'];
    int[] arrayOfInt2 = new int['Ǵ'];
    calculateXYValues(paramPath, arrayOfInt1, arrayOfInt2);
    return ObjectAnimator.ofPropertyValuesHolder(paramT, new PropertyValuesHolder[] { PropertyValuesHolder.ofInt(paramProperty1, arrayOfInt1), PropertyValuesHolder.ofInt(paramProperty2, arrayOfInt2) });
  }
  
  public static ObjectAnimator ofInt(Object paramObject, String paramString1, String paramString2, Path paramPath)
  {
    int[] arrayOfInt1 = new int['Ǵ'];
    int[] arrayOfInt2 = new int['Ǵ'];
    calculateXYValues(paramPath, arrayOfInt1, arrayOfInt2);
    return ObjectAnimator.ofPropertyValuesHolder(paramObject, new PropertyValuesHolder[] { PropertyValuesHolder.ofInt(paramString1, arrayOfInt1), PropertyValuesHolder.ofInt(paramString2, arrayOfInt2) });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/me/zhanghai/android/materialprogressbar/internal/ObjectAnimatorCompatBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */