package android.support.v4.widget;

import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

public class TextViewCompat
{
  static final TextViewCompatImpl IMPL = new BaseTextViewCompatImpl();
  
  static
  {
    int i = Build.VERSION.SDK_INT;
    if (i >= 18)
    {
      IMPL = new JbMr2TextViewCompatImpl();
      return;
    }
    if (i >= 17)
    {
      IMPL = new JbMr1TextViewCompatImpl();
      return;
    }
    if (i >= 16)
    {
      IMPL = new JbTextViewCompatImpl();
      return;
    }
  }
  
  public static int getMaxLines(@NonNull TextView paramTextView)
  {
    return IMPL.getMaxLines(paramTextView);
  }
  
  public static int getMinLines(@NonNull TextView paramTextView)
  {
    return IMPL.getMinLines(paramTextView);
  }
  
  public static void setCompoundDrawablesRelative(@NonNull TextView paramTextView, @Nullable Drawable paramDrawable1, @Nullable Drawable paramDrawable2, @Nullable Drawable paramDrawable3, @Nullable Drawable paramDrawable4)
  {
    IMPL.setCompoundDrawablesRelative(paramTextView, paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
  }
  
  public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    IMPL.setCompoundDrawablesRelativeWithIntrinsicBounds(paramTextView, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView paramTextView, @Nullable Drawable paramDrawable1, @Nullable Drawable paramDrawable2, @Nullable Drawable paramDrawable3, @Nullable Drawable paramDrawable4)
  {
    IMPL.setCompoundDrawablesRelativeWithIntrinsicBounds(paramTextView, paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
  }
  
  static class BaseTextViewCompatImpl
    implements TextViewCompat.TextViewCompatImpl
  {
    public int getMaxLines(TextView paramTextView)
    {
      return TextViewCompatDonut.getMaxLines(paramTextView);
    }
    
    public int getMinLines(TextView paramTextView)
    {
      return TextViewCompatDonut.getMinLines(paramTextView);
    }
    
    public void setCompoundDrawablesRelative(@NonNull TextView paramTextView, @Nullable Drawable paramDrawable1, @Nullable Drawable paramDrawable2, @Nullable Drawable paramDrawable3, @Nullable Drawable paramDrawable4)
    {
      paramTextView.setCompoundDrawables(paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
    }
    
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      paramTextView.setCompoundDrawablesWithIntrinsicBounds(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView paramTextView, @Nullable Drawable paramDrawable1, @Nullable Drawable paramDrawable2, @Nullable Drawable paramDrawable3, @Nullable Drawable paramDrawable4)
    {
      paramTextView.setCompoundDrawablesWithIntrinsicBounds(paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
    }
  }
  
  static class JbMr1TextViewCompatImpl
    extends TextViewCompat.JbTextViewCompatImpl
  {
    public void setCompoundDrawablesRelative(@NonNull TextView paramTextView, @Nullable Drawable paramDrawable1, @Nullable Drawable paramDrawable2, @Nullable Drawable paramDrawable3, @Nullable Drawable paramDrawable4)
    {
      TextViewCompatJbMr1.setCompoundDrawablesRelative(paramTextView, paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
    }
    
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      TextViewCompatJbMr1.setCompoundDrawablesRelativeWithIntrinsicBounds(paramTextView, paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView paramTextView, @Nullable Drawable paramDrawable1, @Nullable Drawable paramDrawable2, @Nullable Drawable paramDrawable3, @Nullable Drawable paramDrawable4)
    {
      TextViewCompatJbMr1.setCompoundDrawablesRelativeWithIntrinsicBounds(paramTextView, paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
    }
  }
  
  static class JbMr2TextViewCompatImpl
    extends TextViewCompat.JbMr1TextViewCompatImpl
  {
    public void setCompoundDrawablesRelative(@NonNull TextView paramTextView, @Nullable Drawable paramDrawable1, @Nullable Drawable paramDrawable2, @Nullable Drawable paramDrawable3, @Nullable Drawable paramDrawable4)
    {
      TextViewCompatJbMr2.setCompoundDrawablesRelative(paramTextView, paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
    }
    
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      TextViewCompatJbMr2.setCompoundDrawablesRelativeWithIntrinsicBounds(paramTextView, paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView paramTextView, @Nullable Drawable paramDrawable1, @Nullable Drawable paramDrawable2, @Nullable Drawable paramDrawable3, @Nullable Drawable paramDrawable4)
    {
      TextViewCompatJbMr2.setCompoundDrawablesRelativeWithIntrinsicBounds(paramTextView, paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
    }
  }
  
  static class JbTextViewCompatImpl
    extends TextViewCompat.BaseTextViewCompatImpl
  {
    public int getMaxLines(TextView paramTextView)
    {
      return TextViewCompatJb.getMaxLines(paramTextView);
    }
    
    public int getMinLines(TextView paramTextView)
    {
      return TextViewCompatJb.getMinLines(paramTextView);
    }
  }
  
  static abstract interface TextViewCompatImpl
  {
    public abstract int getMaxLines(TextView paramTextView);
    
    public abstract int getMinLines(TextView paramTextView);
    
    public abstract void setCompoundDrawablesRelative(@NonNull TextView paramTextView, @Nullable Drawable paramDrawable1, @Nullable Drawable paramDrawable2, @Nullable Drawable paramDrawable3, @Nullable Drawable paramDrawable4);
    
    public abstract void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
    
    public abstract void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView paramTextView, @Nullable Drawable paramDrawable1, @Nullable Drawable paramDrawable2, @Nullable Drawable paramDrawable3, @Nullable Drawable paramDrawable4);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/android/support/v4/widget/TextViewCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */