package com.optimizely.View;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.optimizely.Optimizely;
import com.optimizely.Optimizely.OptimizelyRunningMode;
import com.optimizely.Preview.OptimizelyPreview;
import com.optimizely.Preview.PreviewFloatingActionButton;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class EditGestureListener
  implements View.OnTouchListener
{
  private static final int ANALYZING = 2;
  private static final int COLLECTING = 1;
  private static final int RESTING = 0;
  private int lastMotionHash;
  @NonNull
  private final Optimizely mOptimizely;
  @NonNull
  private final List<Pair<Float, Float>> mPoints = new ArrayList();
  private int mState = 0;
  private final View.OnTouchListener mWrappedListener;
  
  EditGestureListener(@Nullable View.OnTouchListener paramOnTouchListener, @NonNull Optimizely paramOptimizely)
  {
    this.mWrappedListener = paramOnTouchListener;
    this.mOptimizely = paramOptimizely;
  }
  
  private void handleEditGestureTouchEvent(@NonNull MotionEvent paramMotionEvent)
  {
    if (this.mOptimizely.isEditGestureEnabled()) {
      switch (paramMotionEvent.getActionMasked())
      {
      }
    }
    do
    {
      do
      {
        do
        {
          do
          {
            return;
            this.mPoints.clear();
            this.mState = 1;
            return;
          } while (this.mState != 1);
          int j = paramMotionEvent.getHistorySize();
          int i = 0;
          while (i < j)
          {
            Pair localPair = new Pair(Float.valueOf(paramMotionEvent.getHistoricalX(i)), Float.valueOf(paramMotionEvent.getHistoricalY(i)));
            this.mPoints.add(localPair);
            i += 1;
          }
          paramMotionEvent = new Pair(Float.valueOf(paramMotionEvent.getX()), Float.valueOf(paramMotionEvent.getY()));
          this.mPoints.add(paramMotionEvent);
          return;
        } while (this.mState != 1);
        this.mState = 2;
      } while (!isCircle());
      paramMotionEvent = this.mOptimizely;
      if (Optimizely.getRunningMode() == Optimizely.OptimizelyRunningMode.NORMAL)
      {
        this.mOptimizely.getPreviewManager().restartInEditMode();
        return;
      }
      paramMotionEvent = this.mOptimizely;
    } while (Optimizely.getRunningMode() != Optimizely.OptimizelyRunningMode.PREVIEW);
    PreviewFloatingActionButton.show(this.mOptimizely.getForegroundActivity());
  }
  
  private boolean isCircle()
  {
    if (this.mPoints.size() < 10) {
      return false;
    }
    float f2 = 0.0F;
    float f1 = 0.0F;
    Iterator localIterator = this.mPoints.iterator();
    Pair localPair;
    while (localIterator.hasNext())
    {
      localPair = (Pair)localIterator.next();
      f2 += ((Float)localPair.first).floatValue();
      f1 += ((Float)localPair.second).floatValue();
    }
    f2 /= this.mPoints.size();
    float f3 = f1 / this.mPoints.size();
    f1 = 0.0F;
    localIterator = this.mPoints.iterator();
    while (localIterator.hasNext())
    {
      localPair = (Pair)localIterator.next();
      f1 = (float)(f1 + Math.sqrt(Math.pow(((Float)localPair.first).floatValue() - f2, 2.0D) + Math.pow(((Float)localPair.second).floatValue() - f3, 2.0D)));
    }
    float f4 = f1 / this.mPoints.size();
    if (f4 < 100.0F) {
      return false;
    }
    f1 = 0.0F;
    localIterator = this.mPoints.iterator();
    while (localIterator.hasNext())
    {
      localPair = (Pair)localIterator.next();
      f1 = (float)(f1 + Math.abs(f4 - Math.sqrt(Math.pow(((Float)localPair.first).floatValue() - f2, 2.0D) + Math.pow(((Float)localPair.second).floatValue() - f3, 2.0D))) / f4);
    }
    return f1 / this.mPoints.size() < 0.2D;
  }
  
  public static void wrap(@NonNull View paramView, @NonNull Optimizely paramOptimizely)
  {
    View.OnTouchListener localOnTouchListener = GoalHandler.getOnTouchListener(paramView, paramOptimizely);
    if ((!(localOnTouchListener instanceof EditGestureListener)) && (!(localOnTouchListener instanceof GoalHandler.NormalModeTouchHandler))) {
      paramView.setOnTouchListener(new EditGestureListener(localOnTouchListener, paramOptimizely));
    }
  }
  
  @Nullable
  public View.OnTouchListener getWrappedListener()
  {
    return this.mWrappedListener;
  }
  
  public boolean onTouch(View paramView, @NonNull MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.hashCode() == this.lastMotionHash) {
      return false;
    }
    this.lastMotionHash = paramMotionEvent.hashCode();
    boolean bool = false;
    try
    {
      if (this.mWrappedListener != null) {
        bool = this.mWrappedListener.onTouch(paramView, paramMotionEvent);
      }
      handleEditGestureTouchEvent(paramMotionEvent);
      this.lastMotionHash = 0;
      return bool;
    }
    finally
    {
      handleEditGestureTouchEvent(paramMotionEvent);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/View/EditGestureListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */