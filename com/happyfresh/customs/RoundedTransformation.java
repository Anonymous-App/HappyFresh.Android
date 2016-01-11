package com.happyfresh.customs;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import com.squareup.picasso.Transformation;

public class RoundedTransformation
  implements Transformation
{
  private final int margin;
  private final int radius;
  
  public RoundedTransformation(int paramInt1, int paramInt2)
  {
    this.radius = paramInt1;
    this.margin = paramInt2;
  }
  
  public String key()
  {
    return "rounded";
  }
  
  public Bitmap transform(Bitmap paramBitmap)
  {
    Paint localPaint = new Paint();
    localPaint.setAntiAlias(true);
    localPaint.setShader(new BitmapShader(paramBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
    Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    new Canvas(localBitmap).drawRoundRect(new RectF(this.margin, this.margin, paramBitmap.getWidth() - this.margin, paramBitmap.getHeight() - this.margin), this.radius, this.radius, localPaint);
    if (paramBitmap != localBitmap) {
      paramBitmap.recycle();
    }
    return localBitmap;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/customs/RoundedTransformation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */