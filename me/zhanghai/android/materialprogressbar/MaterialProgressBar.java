package me.zhanghai.android.materialprogressbar;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ProgressBar;
import me.zhanghai.android.materialprogressbar.internal.DrawableCompat;

public class MaterialProgressBar
  extends ProgressBar
{
  public static final int PROGRESS_STYLE_CIRCULAR = 0;
  public static final int PROGRESS_STYLE_HORIZONTAL = 1;
  private static final String TAG = MaterialProgressBar.class.getSimpleName();
  private int mProgressStyle;
  private TintInfo mProgressTint = new TintInfo(null);
  
  public MaterialProgressBar(Context paramContext)
  {
    super(paramContext);
    init(paramContext, null, 0, 0);
  }
  
  public MaterialProgressBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet, 0, 0);
  }
  
  public MaterialProgressBar(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet, paramInt, 0);
  }
  
  @TargetApi(21)
  public MaterialProgressBar(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    init(paramContext, paramAttributeSet, paramInt1, paramInt2);
  }
  
  private void applyDeterminateProgressTint()
  {
    if ((this.mProgressTint.mHasTintList) || (this.mProgressTint.mHasTintMode))
    {
      Drawable localDrawable = getProgressDrawable();
      if (localDrawable != null) {
        applyTintForDrawable(localDrawable, this.mProgressTint);
      }
    }
  }
  
  private void applyIndeterminateProgressTint()
  {
    if ((this.mProgressTint.mHasTintList) || (this.mProgressTint.mHasTintMode))
    {
      Drawable localDrawable = getIndeterminateDrawable();
      if (localDrawable != null) {
        applyTintForDrawable(localDrawable, this.mProgressTint);
      }
    }
  }
  
  private void applyProgressTint()
  {
    applyDeterminateProgressTint();
    applyIndeterminateProgressTint();
  }
  
  @SuppressLint({"NewApi"})
  private void applyTintForDrawable(Drawable paramDrawable, TintInfo paramTintInfo)
  {
    if ((paramTintInfo.mHasTintList) || (paramTintInfo.mHasTintMode))
    {
      if (paramTintInfo.mHasTintList)
      {
        if (!(paramDrawable instanceof TintableDrawable)) {
          break label85;
        }
        ((TintableDrawable)paramDrawable).setTintList(paramTintInfo.mTintList);
      }
      if (paramTintInfo.mHasTintMode)
      {
        if (!(paramDrawable instanceof TintableDrawable)) {
          break label113;
        }
        ((TintableDrawable)paramDrawable).setTintMode(paramTintInfo.mTintMode);
      }
    }
    for (;;)
    {
      if (paramDrawable.isStateful()) {
        paramDrawable.setState(getDrawableState());
      }
      return;
      label85:
      Log.w(TAG, "drawable did not implement TintableDrawable, it won't be tinted below Lollipop");
      if (Build.VERSION.SDK_INT < 21) {
        break;
      }
      paramDrawable.setTintList(paramTintInfo.mTintList);
      break;
      label113:
      Log.w(TAG, "drawable did not implement TintableDrawable, it won't be tinted below Lollipop");
      if (Build.VERSION.SDK_INT >= 21) {
        paramDrawable.setTintMode(paramTintInfo.mTintMode);
      }
    }
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    boolean bool1 = false;
    paramAttributeSet = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.MaterialProgressBar, paramInt1, paramInt2);
    this.mProgressStyle = paramAttributeSet.getInt(R.styleable.MaterialProgressBar_mpb_progressStyle, 0);
    boolean bool2 = paramAttributeSet.getBoolean(R.styleable.MaterialProgressBar_mpb_setBothDrawables, false);
    boolean bool3 = paramAttributeSet.getBoolean(R.styleable.MaterialProgressBar_mpb_useIntrinsicPadding, true);
    paramInt1 = R.styleable.MaterialProgressBar_mpb_showTrack;
    if (this.mProgressStyle == 1) {
      bool1 = true;
    }
    bool1 = paramAttributeSet.getBoolean(paramInt1, bool1);
    if (paramAttributeSet.hasValue(R.styleable.MaterialProgressBar_android_tint))
    {
      this.mProgressTint.mTintList = paramAttributeSet.getColorStateList(R.styleable.MaterialProgressBar_android_tint);
      this.mProgressTint.mHasTintList = true;
    }
    if (paramAttributeSet.hasValue(R.styleable.MaterialProgressBar_mpb_tintMode))
    {
      this.mProgressTint.mTintMode = DrawableCompat.parseTintMode(paramAttributeSet.getInt(R.styleable.MaterialProgressBar_mpb_tintMode, -1), null);
      this.mProgressTint.mHasTintMode = true;
    }
    paramAttributeSet.recycle();
    switch (this.mProgressStyle)
    {
    default: 
      throw new IllegalArgumentException("Unknown progress style: " + this.mProgressStyle);
    case 0: 
      if ((!isIndeterminate()) || (bool2)) {
        throw new UnsupportedOperationException("Determinate circular drawable is not yet supported");
      }
      setIndeterminateDrawable(new IndeterminateProgressDrawable(paramContext));
    }
    for (;;)
    {
      setUseIntrinsicPadding(bool3);
      setShowTrack(bool1);
      return;
      if ((isIndeterminate()) || (bool2)) {
        setIndeterminateDrawable(new IndeterminateHorizontalProgressDrawable(paramContext));
      }
      if ((!isIndeterminate()) || (bool2)) {
        setProgressDrawable(new HorizontalProgressDrawable(paramContext));
      }
    }
  }
  
  public Drawable getDrawable()
  {
    if (isIndeterminate()) {
      return getIndeterminateDrawable();
    }
    return getProgressDrawable();
  }
  
  public int getProgressStyle()
  {
    return this.mProgressStyle;
  }
  
  @Nullable
  public ColorStateList getProgressTintList()
  {
    return this.mProgressTint.mTintList;
  }
  
  @Nullable
  public PorterDuff.Mode getProgressTintMode()
  {
    return this.mProgressTint.mTintMode;
  }
  
  public boolean getShowTrack()
  {
    Drawable localDrawable = getDrawable();
    if ((localDrawable instanceof ShowTrackDrawable)) {
      return ((ShowTrackDrawable)localDrawable).getShowTrack();
    }
    return false;
  }
  
  public boolean getUseIntrinsicPadding()
  {
    Drawable localDrawable = getDrawable();
    if ((localDrawable instanceof IntrinsicPaddingDrawable)) {
      return ((IntrinsicPaddingDrawable)localDrawable).getUseIntrinsicPadding();
    }
    throw new IllegalStateException("Drawable does not implement IntrinsicPaddingDrawable");
  }
  
  public void setIndeterminateDrawable(Drawable paramDrawable)
  {
    super.setIndeterminateDrawable(paramDrawable);
    if (this.mProgressTint != null) {
      applyIndeterminateProgressTint();
    }
  }
  
  public void setProgressDrawable(Drawable paramDrawable)
  {
    super.setProgressDrawable(paramDrawable);
    if (this.mProgressTint != null) {
      applyDeterminateProgressTint();
    }
  }
  
  public void setProgressTintList(@Nullable ColorStateList paramColorStateList)
  {
    this.mProgressTint.mTintList = paramColorStateList;
    this.mProgressTint.mHasTintList = true;
    applyProgressTint();
  }
  
  public void setProgressTintMode(@Nullable PorterDuff.Mode paramMode)
  {
    this.mProgressTint.mTintMode = paramMode;
    this.mProgressTint.mHasTintMode = true;
    applyProgressTint();
  }
  
  public void setShowTrack(boolean paramBoolean)
  {
    Drawable localDrawable = getDrawable();
    if ((localDrawable instanceof ShowTrackDrawable)) {
      ((ShowTrackDrawable)localDrawable).setShowTrack(paramBoolean);
    }
    while (!paramBoolean) {
      return;
    }
    throw new IllegalStateException("Drawable does not implement ShowTrackDrawable");
  }
  
  public void setUseIntrinsicPadding(boolean paramBoolean)
  {
    Drawable localDrawable = getDrawable();
    if ((localDrawable instanceof IntrinsicPaddingDrawable))
    {
      ((IntrinsicPaddingDrawable)localDrawable).setUseIntrinsicPadding(paramBoolean);
      return;
    }
    throw new IllegalStateException("Drawable does not implement IntrinsicPaddingDrawable");
  }
  
  private static class TintInfo
  {
    boolean mHasTintList;
    boolean mHasTintMode;
    ColorStateList mTintList;
    PorterDuff.Mode mTintMode;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/me/zhanghai/android/materialprogressbar/MaterialProgressBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */