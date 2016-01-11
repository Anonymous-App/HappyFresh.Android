package com.optimizely.Preview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.view.View;

public class SvgPathView
  extends View
{
  private final Paint mPaint;
  private final Path mPath = new Path();
  private final Path mTransformedPath = new Path();
  
  public SvgPathView(Context paramContext, int paramInt, @NonNull String paramString)
  {
    super(paramContext);
    readPath(paramString, this.mPath);
    this.mPaint = new Paint(1);
    this.mPaint.setStyle(Paint.Style.FILL);
    this.mPaint.setStrokeWidth(20.0F);
    setColor(paramInt);
  }
  
  private void readPath(String paramString, Path paramPath)
  {
    String[] arrayOfString;
    int i;
    int j;
    String str;
    label70:
    float f1;
    float f2;
    try
    {
      arrayOfString = paramString.split("[ ,]");
      paramString = "";
      for (i = 0;; i = j)
      {
        if (i >= arrayOfString.length) {
          break label434;
        }
        j = i + 1;
        str = arrayOfString[i];
        if (!str.trim().isEmpty()) {
          break;
        }
      }
      if (!str.matches("[A-Za-z]")) {
        break label444;
      }
      paramString = str;
      i = j;
      if (str.equalsIgnoreCase("M"))
      {
        j = i + 1;
        f1 = Float.valueOf(arrayOfString[i]).floatValue();
        f2 = Float.valueOf(arrayOfString[j]).floatValue();
        if (str.equals("m")) {
          paramPath.rMoveTo(f1, f2);
        } else {
          paramPath.moveTo(f1, f2);
        }
      }
    }
    catch (IndexOutOfBoundsException paramString)
    {
      throw new RuntimeException("bad data ", paramString);
    }
    if (str.equalsIgnoreCase("L"))
    {
      j = i + 1;
      f1 = Float.valueOf(arrayOfString[i]).floatValue();
      f2 = Float.valueOf(arrayOfString[j]).floatValue();
      if (str.equals("l")) {
        paramPath.rLineTo(f1, f2);
      } else {
        paramPath.lineTo(f1, f2);
      }
    }
    else
    {
      if (str.equalsIgnoreCase("C"))
      {
        j = i + 1;
        f1 = Float.valueOf(arrayOfString[i]).floatValue();
        i = j + 1;
        f2 = Float.valueOf(arrayOfString[j]).floatValue();
        j = i + 1;
        float f3 = Float.valueOf(arrayOfString[i]).floatValue();
        i = j + 1;
        float f4 = Float.valueOf(arrayOfString[j]).floatValue();
        j = i + 1;
        float f5 = Float.valueOf(arrayOfString[i]).floatValue();
        float f6 = Float.valueOf(arrayOfString[j]).floatValue();
        if (str.equals("c"))
        {
          paramPath.rCubicTo(f1, f2, f3, f4, f5, f6);
          break label465;
        }
        paramPath.cubicTo(f1, f2, f3, f4, f5, f6);
        break label465;
      }
      if (str.equalsIgnoreCase("z"))
      {
        paramPath.close();
      }
      else
      {
        throw new RuntimeException(String.format("unknown command [%s] at index %d", new Object[] { str, Integer.valueOf(i) }));
        label434:
        return;
        i = j + 1;
      }
    }
    for (;;)
    {
      break;
      label444:
      str = paramString;
      i = j - 1;
      break label70;
      i = j + 1;
      continue;
      label465:
      i = j + 1;
    }
  }
  
  private void resizePath()
  {
    RectF localRectF1 = new RectF();
    this.mPath.computeBounds(localRectF1, true);
    RectF localRectF2 = new RectF(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
    Matrix localMatrix = new Matrix();
    localMatrix.setRectToRect(localRectF1, localRectF2, Matrix.ScaleToFit.CENTER);
    this.mPath.transform(localMatrix, this.mTransformedPath);
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    paramCanvas.drawPath(this.mTransformedPath, this.mPaint);
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    resizePath();
  }
  
  public void setColor(int paramInt)
  {
    this.mPaint.setColor(paramInt);
    invalidate();
  }
  
  public void setPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.setPadding(paramInt1, paramInt2, paramInt3, paramInt4);
    resizePath();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Preview/SvgPathView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */