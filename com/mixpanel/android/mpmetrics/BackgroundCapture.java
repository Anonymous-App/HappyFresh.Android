package com.mixpanel.android.mpmetrics;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.AsyncTask;
import com.mixpanel.android.util.ActivityImageUtils;
import com.mixpanel.android.util.StackBlurManager;

class BackgroundCapture
{
  private static final int GRAY_72PERCENT_OPAQUE = Color.argb(186, 28, 28, 28);
  private static final String LOGTAG = "MixpanelAPI.BackgroundCapture";
  
  public static void captureBackground(Activity paramActivity, final OnBackgroundCapturedListener paramOnBackgroundCapturedListener)
  {
    paramActivity.runOnUiThread(new Runnable()
    {
      public void run()
      {
        new BackgroundCapture.BackgroundCaptureTask(this.val$parentActivity, paramOnBackgroundCapturedListener).execute(new Void[0]);
      }
    });
  }
  
  private static class BackgroundCaptureTask
    extends AsyncTask<Void, Void, Void>
  {
    private int mCalculatedHighlightColor;
    private final BackgroundCapture.OnBackgroundCapturedListener mListener;
    private final Activity mParentActivity;
    private Bitmap mSourceImage;
    
    public BackgroundCaptureTask(Activity paramActivity, BackgroundCapture.OnBackgroundCapturedListener paramOnBackgroundCapturedListener)
    {
      this.mParentActivity = paramActivity;
      this.mListener = paramOnBackgroundCapturedListener;
      this.mCalculatedHighlightColor = -16777216;
    }
    
    protected Void doInBackground(Void... paramVarArgs)
    {
      if (this.mSourceImage != null) {}
      try
      {
        StackBlurManager.process(this.mSourceImage, 20);
        new Canvas(this.mSourceImage).drawColor(BackgroundCapture.GRAY_72PERCENT_OPAQUE, PorterDuff.Mode.SRC_ATOP);
        return null;
      }
      catch (ArrayIndexOutOfBoundsException paramVarArgs)
      {
        this.mSourceImage = null;
        return null;
      }
      catch (OutOfMemoryError paramVarArgs)
      {
        this.mSourceImage = null;
      }
      return null;
    }
    
    protected void onPostExecute(Void paramVoid)
    {
      this.mListener.onBackgroundCaptured(this.mSourceImage, this.mCalculatedHighlightColor);
    }
    
    protected void onPreExecute()
    {
      this.mSourceImage = ActivityImageUtils.getScaledScreenshot(this.mParentActivity, 2, 2, true);
      this.mCalculatedHighlightColor = ActivityImageUtils.getHighlightColorFromBitmap(this.mSourceImage);
    }
  }
  
  public static abstract interface OnBackgroundCapturedListener
  {
    public abstract void onBackgroundCaptured(Bitmap paramBitmap, int paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/mpmetrics/BackgroundCapture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */