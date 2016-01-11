package android.support.v4.animation;

import android.os.Build.VERSION;
import android.view.View;

public abstract class AnimatorCompatHelper
{
  static AnimatorProvider IMPL = new DonutAnimatorCompatProvider();
  
  static
  {
    if (Build.VERSION.SDK_INT >= 12)
    {
      IMPL = new HoneycombMr1AnimatorCompatProvider();
      return;
    }
  }
  
  public static void clearInterpolator(View paramView)
  {
    IMPL.clearInterpolator(paramView);
  }
  
  public static ValueAnimatorCompat emptyValueAnimator()
  {
    return IMPL.emptyValueAnimator();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/android/support/v4/animation/AnimatorCompatHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */