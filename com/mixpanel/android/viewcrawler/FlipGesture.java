package com.mixpanel.android.viewcrawler;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import com.mixpanel.android.mpmetrics.MPConfig;

class FlipGesture
  implements SensorEventListener
{
  private static final float ACCELEROMETER_SMOOTHING = 0.7F;
  private static final int FLIP_STATE_DOWN = 1;
  private static final int FLIP_STATE_NONE = 0;
  private static final int FLIP_STATE_UP = -1;
  private static final String LOGTAG = "MixpanelAPI.FlipGesture";
  private static final float MAXIMUM_GRAVITY_FOR_FLIP = 11.8F;
  private static final long MINIMUM_CANCEL_DURATION = 1000000000L;
  private static final float MINIMUM_GRAVITY_FOR_FLIP = 7.8F;
  private static final long MINIMUM_UP_DOWN_DURATION = 250000000L;
  private static final int TRIGGER_STATE_BEGIN = 1;
  private static final int TRIGGER_STATE_NONE = 0;
  private int mFlipState = 0;
  private long mLastFlipTime = -1L;
  private final OnFlipGestureListener mListener;
  private final float[] mSmoothed = new float[3];
  private int mTriggerState = -1;
  
  public FlipGesture(OnFlipGestureListener paramOnFlipGestureListener)
  {
    this.mListener = paramOnFlipGestureListener;
  }
  
  private float[] smoothXYZ(float[] paramArrayOfFloat)
  {
    int i = 0;
    while (i < 3)
    {
      float f = this.mSmoothed[i];
      this.mSmoothed[i] = (0.7F * (paramArrayOfFloat[i] - f) + f);
      i += 1;
    }
    return this.mSmoothed;
  }
  
  public void onAccuracyChanged(Sensor paramSensor, int paramInt) {}
  
  public void onSensorChanged(SensorEvent paramSensorEvent)
  {
    float[] arrayOfFloat = smoothXYZ(paramSensorEvent.values);
    int i = this.mFlipState;
    float f = arrayOfFloat[0] * arrayOfFloat[0] + arrayOfFloat[1] * arrayOfFloat[1] + arrayOfFloat[2] * arrayOfFloat[2];
    this.mFlipState = 0;
    if ((arrayOfFloat[2] > 7.8F) && (arrayOfFloat[2] < 11.8F)) {
      this.mFlipState = -1;
    }
    if ((arrayOfFloat[2] < -7.8F) && (arrayOfFloat[2] > -11.8F)) {
      this.mFlipState = 1;
    }
    if ((f < 60.840004F) || (f > 139.24F)) {
      this.mFlipState = 0;
    }
    if (i != this.mFlipState) {
      this.mLastFlipTime = paramSensorEvent.timestamp;
    }
    long l = paramSensorEvent.timestamp - this.mLastFlipTime;
    switch (this.mFlipState)
    {
    }
    do
    {
      do
      {
        do
        {
          return;
        } while ((l <= 250000000L) || (this.mTriggerState != 0));
        if (MPConfig.DEBUG) {
          Log.v("MixpanelAPI.FlipGesture", "Flip gesture begun");
        }
        this.mTriggerState = 1;
        return;
      } while ((l <= 250000000L) || (this.mTriggerState != 1));
      if (MPConfig.DEBUG) {
        Log.v("MixpanelAPI.FlipGesture", "Flip gesture completed");
      }
      this.mTriggerState = 0;
      this.mListener.onFlipGesture();
      return;
    } while ((l <= 1000000000L) || (this.mTriggerState == 0));
    if (MPConfig.DEBUG) {
      Log.v("MixpanelAPI.FlipGesture", "Flip gesture abandoned");
    }
    this.mTriggerState = 0;
  }
  
  public static abstract interface OnFlipGestureListener
  {
    public abstract void onFlipGesture();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/viewcrawler/FlipGesture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */