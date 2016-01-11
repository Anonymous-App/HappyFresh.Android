package me.zhanghai.android.materialprogressbar;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import me.zhanghai.android.materialprogressbar.internal.ObjectAnimatorCompat;

class Animators
{
  private static final Path PATH_INDETERMINATE_HORIZONTAL_RECT1_SCALE_X;
  private static final Path PATH_INDETERMINATE_HORIZONTAL_RECT1_TRANSLATE_X = new Path();
  private static final Path PATH_INDETERMINATE_HORIZONTAL_RECT2_SCALE_X;
  private static final Path PATH_INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X;
  
  static
  {
    PATH_INDETERMINATE_HORIZONTAL_RECT1_TRANSLATE_X.moveTo(-522.6F, 0.0F);
    PATH_INDETERMINATE_HORIZONTAL_RECT1_TRANSLATE_X.rCubicTo(48.89972F, 0.0F, 166.02657F, 0.0F, 301.2173F, 0.0F);
    PATH_INDETERMINATE_HORIZONTAL_RECT1_TRANSLATE_X.rCubicTo(197.58128F, 0.0F, 420.9827F, 0.0F, 420.9827F, 0.0F);
    PATH_INDETERMINATE_HORIZONTAL_RECT1_SCALE_X = new Path();
    PATH_INDETERMINATE_HORIZONTAL_RECT1_SCALE_X.moveTo(0.0F, 0.1F);
    PATH_INDETERMINATE_HORIZONTAL_RECT1_SCALE_X.lineTo(1.0F, 0.8268492F);
    PATH_INDETERMINATE_HORIZONTAL_RECT1_SCALE_X.lineTo(2.0F, 0.1F);
    PATH_INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X = new Path();
    PATH_INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X.moveTo(-197.6F, 0.0F);
    PATH_INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X.rCubicTo(14.28182F, 0.0F, 85.07782F, 0.0F, 135.54689F, 0.0F);
    PATH_INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X.rCubicTo(54.26191F, 0.0F, 90.42461F, 0.0F, 168.24332F, 0.0F);
    PATH_INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X.rCubicTo(144.72154F, 0.0F, 316.40982F, 0.0F, 316.40982F, 0.0F);
    PATH_INDETERMINATE_HORIZONTAL_RECT2_SCALE_X = new Path();
    PATH_INDETERMINATE_HORIZONTAL_RECT2_SCALE_X.moveTo(0.0F, 0.1F);
    PATH_INDETERMINATE_HORIZONTAL_RECT2_SCALE_X.lineTo(1.0F, 0.5713795F);
    PATH_INDETERMINATE_HORIZONTAL_RECT2_SCALE_X.lineTo(2.0F, 0.90995026F);
    PATH_INDETERMINATE_HORIZONTAL_RECT2_SCALE_X.lineTo(3.0F, 0.1F);
  }
  
  public static Animator createIndeterminate(Object paramObject)
  {
    ObjectAnimator localObjectAnimator1 = ObjectAnimator.ofFloat(paramObject, "trimPathStart", new float[] { 0.0F, 0.75F });
    localObjectAnimator1.setDuration(1333L);
    localObjectAnimator1.setInterpolator(Interpolators.TRIM_PATH_START.INSTANCE);
    localObjectAnimator1.setRepeatCount(-1);
    ObjectAnimator localObjectAnimator2 = ObjectAnimator.ofFloat(paramObject, "trimPathEnd", new float[] { 0.0F, 0.75F });
    localObjectAnimator2.setDuration(1333L);
    localObjectAnimator2.setInterpolator(Interpolators.TRIM_PATH_END.INSTANCE);
    localObjectAnimator2.setRepeatCount(-1);
    paramObject = ObjectAnimator.ofFloat(paramObject, "trimPathOffset", new float[] { 0.0F, 0.25F });
    ((ObjectAnimator)paramObject).setDuration(1333L);
    ((ObjectAnimator)paramObject).setInterpolator(Interpolators.LINEAR.INSTANCE);
    ((ObjectAnimator)paramObject).setRepeatCount(-1);
    AnimatorSet localAnimatorSet = new AnimatorSet();
    localAnimatorSet.playTogether(new Animator[] { localObjectAnimator1, localObjectAnimator2, paramObject });
    return localAnimatorSet;
  }
  
  public static Animator createIndeterminateHorizontalRect1(Object paramObject)
  {
    ObjectAnimator localObjectAnimator = ObjectAnimatorCompat.ofFloat(paramObject, "translateX", null, PATH_INDETERMINATE_HORIZONTAL_RECT1_TRANSLATE_X);
    localObjectAnimator.setDuration(2000L);
    localObjectAnimator.setInterpolator(Interpolators.INDETERMINATE_HORIZONTAL_RECT1_TRANSLATE_X.INSTANCE);
    localObjectAnimator.setRepeatCount(-1);
    paramObject = ObjectAnimatorCompat.ofFloat(paramObject, null, "scaleX", PATH_INDETERMINATE_HORIZONTAL_RECT1_SCALE_X);
    ((ObjectAnimator)paramObject).setDuration(2000L);
    ((ObjectAnimator)paramObject).setInterpolator(Interpolators.INDETERMINATE_HORIZONTAL_RECT1_SCALE_X.INSTANCE);
    ((ObjectAnimator)paramObject).setRepeatCount(-1);
    AnimatorSet localAnimatorSet = new AnimatorSet();
    localAnimatorSet.playTogether(new Animator[] { localObjectAnimator, paramObject });
    return localAnimatorSet;
  }
  
  public static Animator createIndeterminateHorizontalRect2(Object paramObject)
  {
    ObjectAnimator localObjectAnimator = ObjectAnimatorCompat.ofFloat(paramObject, "translateX", null, PATH_INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X);
    localObjectAnimator.setDuration(2000L);
    localObjectAnimator.setInterpolator(Interpolators.INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X.INSTANCE);
    localObjectAnimator.setRepeatCount(-1);
    paramObject = ObjectAnimatorCompat.ofFloat(paramObject, null, "scaleX", PATH_INDETERMINATE_HORIZONTAL_RECT2_SCALE_X);
    ((ObjectAnimator)paramObject).setDuration(2000L);
    ((ObjectAnimator)paramObject).setInterpolator(Interpolators.INDETERMINATE_HORIZONTAL_RECT2_SCALE_X.INSTANCE);
    ((ObjectAnimator)paramObject).setRepeatCount(-1);
    AnimatorSet localAnimatorSet = new AnimatorSet();
    localAnimatorSet.playTogether(new Animator[] { localObjectAnimator, paramObject });
    return localAnimatorSet;
  }
  
  public static Animator createIndeterminateRotation(Object paramObject)
  {
    paramObject = ObjectAnimator.ofFloat(paramObject, "rotation", new float[] { 0.0F, 720.0F });
    ((ObjectAnimator)paramObject).setDuration(6665L);
    ((ObjectAnimator)paramObject).setInterpolator(Interpolators.LINEAR.INSTANCE);
    ((ObjectAnimator)paramObject).setRepeatCount(-1);
    return (Animator)paramObject;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/me/zhanghai/android/materialprogressbar/Animators.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */