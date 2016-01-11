package com.mixpanel.android.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

public class ActivityImageUtils
{
  private static final String LOGTAG = "MixpanelAPI.ActImgUtils";
  
  public static int getHighlightColor(int paramInt)
  {
    float[] arrayOfFloat = new float[3];
    Color.colorToHSV(paramInt, arrayOfFloat);
    arrayOfFloat[2] = 0.3F;
    return Color.HSVToColor(242, arrayOfFloat);
  }
  
  public static int getHighlightColorFromBackground(Activity paramActivity)
  {
    int i = -16777216;
    paramActivity = getScaledScreenshot(paramActivity, 1, 1, false);
    if (paramActivity != null) {
      i = paramActivity.getPixel(0, 0);
    }
    return getHighlightColor(i);
  }
  
  public static int getHighlightColorFromBitmap(Bitmap paramBitmap)
  {
    int i = -16777216;
    if (paramBitmap != null) {
      i = Bitmap.createScaledBitmap(paramBitmap, 1, 1, false).getPixel(0, 0);
    }
    return getHighlightColor(i);
  }
  
  public static Bitmap getScaledScreenshot(Activity paramActivity, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    View localView = paramActivity.findViewById(16908290).getRootView();
    boolean bool = localView.isDrawingCacheEnabled();
    localView.setDrawingCacheEnabled(true);
    localView.buildDrawingCache(true);
    Bitmap localBitmap = localView.getDrawingCache();
    Object localObject = null;
    paramActivity = (Activity)localObject;
    int j;
    int i;
    if (localBitmap != null)
    {
      paramActivity = (Activity)localObject;
      if (localBitmap.getWidth() > 0)
      {
        paramActivity = (Activity)localObject;
        if (localBitmap.getHeight() > 0)
        {
          j = paramInt1;
          i = paramInt2;
          if (paramBoolean)
          {
            j = localBitmap.getWidth() / paramInt1;
            i = localBitmap.getHeight() / paramInt2;
          }
          paramActivity = (Activity)localObject;
          if (j > 0)
          {
            paramActivity = (Activity)localObject;
            if (i <= 0) {}
          }
        }
      }
    }
    try
    {
      paramActivity = Bitmap.createScaledBitmap(localBitmap, j, i, false);
      if (!bool) {
        localView.setDrawingCacheEnabled(false);
      }
      return paramActivity;
    }
    catch (OutOfMemoryError paramActivity)
    {
      for (;;)
      {
        Log.i("MixpanelAPI.ActImgUtils", "Not enough memory to produce scaled image, returning a null screenshot");
        paramActivity = (Activity)localObject;
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/util/ActivityImageUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */