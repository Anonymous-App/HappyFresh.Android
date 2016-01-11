package com.ad4screen.sdk.common.compatibility;

import android.annotation.TargetApi;
import android.app.Notification.Builder;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.ad4screen.sdk.A4SService;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.i;
import java.io.File;

@TargetApi(21)
public final class e
{
  public static Notification.Builder a(Context paramContext, int paramInt1, String paramString, int paramInt2, boolean paramBoolean, Notification.Builder paramBuilder)
  {
    paramBuilder.setPriority(paramInt1);
    String str = paramString;
    if (paramString == null) {
      str = "promo";
    }
    paramBuilder.setCategory(str);
    if (paramBoolean)
    {
      paramInt1 = paramInt2;
      if (paramInt2 == 0)
      {
        paramInt2 = -7829368;
        paramContext = i.a(paramContext, "com.ad4screen.notifications.accent_color", A4SService.class);
        paramInt1 = paramInt2;
        if (paramContext == null) {}
      }
    }
    try
    {
      paramInt1 = Integer.parseInt(paramContext);
      paramBuilder.setColor(paramInt1);
      paramBuilder.setVisibility(1);
      return paramBuilder;
    }
    catch (NumberFormatException paramContext)
    {
      for (;;)
      {
        Log.debug("Wrong color provided for com.ad4screen.notifications.accent_color value (must be an int constant like @android:color/gray)");
        paramInt1 = paramInt2;
      }
    }
  }
  
  public static Bitmap a(Context paramContext, int paramInt1, int paramInt2)
  {
    Bitmap localBitmap1 = BitmapFactory.decodeResource(paramContext.getResources(), paramInt1);
    float f2 = paramContext.getResources().getDimension(17104901);
    float f3 = paramContext.getResources().getDimension(17104902);
    Bitmap localBitmap2 = Bitmap.createScaledBitmap(localBitmap1, (int)f2, (int)f3, true).copy(Bitmap.Config.ARGB_8888, true);
    Canvas localCanvas = new Canvas(localBitmap2);
    Paint localPaint = new Paint();
    localPaint.setAntiAlias(true);
    paramInt1 = paramInt2;
    if (paramInt2 == 0)
    {
      paramInt2 = -7829368;
      paramContext = i.a(paramContext, "com.ad4screen.notifications.accent_color", A4SService.class);
      paramInt1 = paramInt2;
      if (paramContext == null) {}
    }
    try
    {
      paramInt1 = Integer.parseInt(paramContext);
      localPaint.setColor(paramInt1);
      float f4 = f2 / 2.0F;
      float f5 = f3 / 2.0F;
      if (f2 > f3)
      {
        f1 = f3 / 2.0F;
        localCanvas.drawCircle(f4, f5, f1, localPaint);
        localCanvas.drawBitmap(localBitmap1, f2 / 4.0F, f3 / 4.0F, localPaint);
        return localBitmap2;
      }
    }
    catch (NumberFormatException paramContext)
    {
      for (;;)
      {
        Log.debug("Wrong color provided for com.ad4screen.notifications.accent_color value (must be an int constant like @android:color/gray)");
        paramInt1 = paramInt2;
        continue;
        float f1 = f2 / 2.0F;
      }
    }
  }
  
  public static File a(Context paramContext)
  {
    return paramContext.getNoBackupFilesDir();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/compatibility/e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */