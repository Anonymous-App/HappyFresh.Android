package me.zhanghai.android.materialprogressbar;

import android.graphics.Path;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

class Interpolators
{
  public static class INDETERMINATE_HORIZONTAL_RECT1_SCALE_X
  {
    public static final Interpolator INSTANCE = PathInterpolatorCompat.create(PATH_INDETERMINATE_HORIZONTAL_RECT1_SCALE_X);
    private static final Path PATH_INDETERMINATE_HORIZONTAL_RECT1_SCALE_X = new Path();
    
    static
    {
      PATH_INDETERMINATE_HORIZONTAL_RECT1_SCALE_X.moveTo(0.0F, 0.0F);
      PATH_INDETERMINATE_HORIZONTAL_RECT1_SCALE_X.lineTo(0.3665F, 0.0F);
      PATH_INDETERMINATE_HORIZONTAL_RECT1_SCALE_X.cubicTo(0.4725262F, 0.06240991F, 0.6154161F, 0.5F, 0.68325F, 0.5F);
      PATH_INDETERMINATE_HORIZONTAL_RECT1_SCALE_X.cubicTo(0.7547506F, 0.5F, 0.7572583F, 0.8145101F, 1.0F, 1.0F);
    }
  }
  
  public static class INDETERMINATE_HORIZONTAL_RECT1_TRANSLATE_X
  {
    public static final Interpolator INSTANCE = PathInterpolatorCompat.create(PATH_INDETERMINATE_HORIZONTAL_RECT1_TRANSLATE_X);
    private static final Path PATH_INDETERMINATE_HORIZONTAL_RECT1_TRANSLATE_X = new Path();
    
    static
    {
      PATH_INDETERMINATE_HORIZONTAL_RECT1_TRANSLATE_X.moveTo(0.0F, 0.0F);
      PATH_INDETERMINATE_HORIZONTAL_RECT1_TRANSLATE_X.lineTo(0.2F, 0.0F);
      PATH_INDETERMINATE_HORIZONTAL_RECT1_TRANSLATE_X.cubicTo(0.39583334F, 0.0F, 0.47484508F, 0.20679761F, 0.59166664F, 0.41708294F);
      PATH_INDETERMINATE_HORIZONTAL_RECT1_TRANSLATE_X.cubicTo(0.715161F, 0.6393796F, 0.81625F, 0.9745569F, 1.0F, 1.0F);
    }
  }
  
  public static class INDETERMINATE_HORIZONTAL_RECT2_SCALE_X
  {
    public static final Interpolator INSTANCE = PathInterpolatorCompat.create(PATH_INDETERMINATE_HORIZONTAL_RECT2_SCALE_X);
    private static final Path PATH_INDETERMINATE_HORIZONTAL_RECT2_SCALE_X = new Path();
    
    static
    {
      PATH_INDETERMINATE_HORIZONTAL_RECT2_SCALE_X.moveTo(0.0F, 0.0F);
      PATH_INDETERMINATE_HORIZONTAL_RECT2_SCALE_X.cubicTo(0.06834272F, 0.019925667F, 0.19220331F, 0.15855429F, 0.33333334F, 0.3492616F);
      PATH_INDETERMINATE_HORIZONTAL_RECT2_SCALE_X.cubicTo(0.38410434F, 0.41477913F, 0.5494579F, 0.6813603F, 0.6666667F, 0.68279964F);
      PATH_INDETERMINATE_HORIZONTAL_RECT2_SCALE_X.cubicTo(0.75258625F, 0.6817962F, 0.73725396F, 0.8788962F, 1.0F, 1.0F);
    }
  }
  
  public static class INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X
  {
    public static final Interpolator INSTANCE = PathInterpolatorCompat.create(PATH_INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X);
    private static final Path PATH_INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X = new Path();
    
    static
    {
      PATH_INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X.moveTo(0.0F, 0.0F);
      PATH_INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X.cubicTo(0.0375F, 0.0F, 0.12876461F, 0.0895381F, 0.25F, 0.21855351F);
      PATH_INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X.cubicTo(0.32241032F, 0.2956106F, 0.43666667F, 0.41759142F, 0.48333332F, 0.48982617F);
      PATH_INDETERMINATE_HORIZONTAL_RECT2_TRANSLATE_X.cubicTo(0.69F, 0.80972296F, 0.79333335F, 0.95001614F, 1.0F, 1.0F);
    }
  }
  
  public static class LINEAR
  {
    public static final Interpolator INSTANCE = new LinearInterpolator();
  }
  
  public static class TRIM_PATH_END
  {
    public static final Interpolator INSTANCE = PathInterpolatorCompat.create(PATH_TRIM_PATH_END);
    private static final Path PATH_TRIM_PATH_END = new Path();
    
    static
    {
      PATH_TRIM_PATH_END.cubicTo(0.2F, 0.0F, 0.1F, 1.0F, 0.5F, 1.0F);
      PATH_TRIM_PATH_END.lineTo(1.0F, 1.0F);
    }
  }
  
  public static class TRIM_PATH_START
  {
    public static final Interpolator INSTANCE = PathInterpolatorCompat.create(PATH_TRIM_PATH_START);
    private static final Path PATH_TRIM_PATH_START = new Path();
    
    static
    {
      PATH_TRIM_PATH_START.lineTo(0.5F, 0.0F);
      PATH_TRIM_PATH_START.cubicTo(0.7F, 0.0F, 0.6F, 1.0F, 1.0F, 1.0F);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/me/zhanghai/android/materialprogressbar/Interpolators.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */