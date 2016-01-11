package com.mixpanel.android.surveys;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

public class MiniCircleImageView
  extends ImageView
{
  private int mCanvasHeight;
  private int mCanvasWidth;
  private Paint mWhitePaint;
  
  public MiniCircleImageView(Context paramContext)
  {
    super(paramContext);
    init();
  }
  
  public MiniCircleImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }
  
  public MiniCircleImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }
  
  private void init()
  {
    this.mWhitePaint = new Paint(1);
    this.mWhitePaint.setColor(getResources().getColor(17170443));
    this.mWhitePaint.setStyle(Paint.Style.STROKE);
    float f = TypedValue.applyDimension(1, 2.0F, getResources().getDisplayMetrics());
    this.mWhitePaint.setStrokeWidth(f);
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    float f1 = this.mCanvasWidth / 2;
    float f2 = this.mCanvasHeight / 2;
    paramCanvas.drawCircle(f1, f2, 0.7F * Math.min(f1, f2), this.mWhitePaint);
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.mCanvasWidth = paramInt1;
    this.mCanvasHeight = paramInt2;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/surveys/MiniCircleImageView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */