package com.mixpanel.android.mpmetrics;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.mixpanel.android.R.anim;
import com.mixpanel.android.R.id;
import com.mixpanel.android.R.layout;

@SuppressLint({"ClickableViewAccessibility"})
@TargetApi(16)
public class InAppFragment
  extends Fragment
{
  private static final String LOGTAG = "MixpanelAPI.InAppFrag";
  private static final int MINI_REMOVE_TIME = 10000;
  private boolean mCleanedUp;
  private GestureDetector mDetector;
  private Runnable mDisplayMini;
  private UpdateDisplayState.DisplayState.InAppNotificationState mDisplayState;
  private int mDisplayStateId;
  private Handler mHandler;
  private View mInAppView;
  private MixpanelAPI mMixpanel;
  private Activity mParent;
  private Runnable mRemover;
  
  private void cleanUp()
  {
    if (!this.mCleanedUp)
    {
      this.mHandler.removeCallbacks(this.mRemover);
      this.mHandler.removeCallbacks(this.mDisplayMini);
      UpdateDisplayState.releaseDisplayState(this.mDisplayStateId);
      this.mParent.getFragmentManager().beginTransaction().remove(this).commit();
    }
    this.mCleanedUp = true;
  }
  
  private void remove()
  {
    if ((this.mParent != null) && (!this.mCleanedUp))
    {
      this.mHandler.removeCallbacks(this.mRemover);
      this.mHandler.removeCallbacks(this.mDisplayMini);
      this.mParent.getFragmentManager().beginTransaction().setCustomAnimations(0, R.anim.com_mixpanel_android_slide_down).remove(this).commit();
      UpdateDisplayState.releaseDisplayState(this.mDisplayStateId);
      this.mCleanedUp = true;
    }
  }
  
  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mParent = paramActivity;
    if (this.mDisplayState == null)
    {
      cleanUp();
      return;
    }
    this.mHandler = new Handler();
    this.mRemover = new Runnable()
    {
      public void run()
      {
        InAppFragment.this.remove();
      }
    };
    this.mDisplayMini = new Runnable()
    {
      public void run()
      {
        InAppFragment.this.mInAppView.setVisibility(0);
        InAppFragment.this.mInAppView.setBackgroundColor(InAppFragment.this.mDisplayState.getHighlightColor());
        InAppFragment.this.mInAppView.setOnTouchListener(new View.OnTouchListener()
        {
          public boolean onTouch(View paramAnonymous2View, MotionEvent paramAnonymous2MotionEvent)
          {
            return InAppFragment.this.mDetector.onTouchEvent(paramAnonymous2MotionEvent);
          }
        });
        ImageView localImageView = (ImageView)InAppFragment.this.mInAppView.findViewById(R.id.com_mixpanel_android_notification_image);
        float f = TypedValue.applyDimension(1, 75.0F, InAppFragment.this.mParent.getResources().getDisplayMetrics());
        Object localObject = new TranslateAnimation(0.0F, 0.0F, f, 0.0F);
        ((TranslateAnimation)localObject).setInterpolator(new DecelerateInterpolator());
        ((TranslateAnimation)localObject).setDuration(200L);
        InAppFragment.this.mInAppView.startAnimation((Animation)localObject);
        localObject = new ScaleAnimation(0.0F, 1.0F, 0.0F, 1.0F, f / 2.0F, f / 2.0F);
        ((ScaleAnimation)localObject).setInterpolator(new InAppFragment.SineBounceInterpolator(InAppFragment.this));
        ((ScaleAnimation)localObject).setDuration(400L);
        ((ScaleAnimation)localObject).setStartOffset(200L);
        localImageView.startAnimation((Animation)localObject);
      }
    };
    this.mDetector = new GestureDetector(paramActivity, new GestureDetector.OnGestureListener()
    {
      public boolean onDown(MotionEvent paramAnonymousMotionEvent)
      {
        return true;
      }
      
      public boolean onFling(MotionEvent paramAnonymousMotionEvent1, MotionEvent paramAnonymousMotionEvent2, float paramAnonymousFloat1, float paramAnonymousFloat2)
      {
        if (paramAnonymousFloat2 > 0.0F) {
          InAppFragment.this.remove();
        }
        return true;
      }
      
      public void onLongPress(MotionEvent paramAnonymousMotionEvent) {}
      
      public boolean onScroll(MotionEvent paramAnonymousMotionEvent1, MotionEvent paramAnonymousMotionEvent2, float paramAnonymousFloat1, float paramAnonymousFloat2)
      {
        return false;
      }
      
      public void onShowPress(MotionEvent paramAnonymousMotionEvent) {}
      
      /* Error */
      public boolean onSingleTapUp(MotionEvent paramAnonymousMotionEvent)
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 17	com/mixpanel/android/mpmetrics/InAppFragment$3:this$0	Lcom/mixpanel/android/mpmetrics/InAppFragment;
        //   4: invokestatic 41	com/mixpanel/android/mpmetrics/InAppFragment:access$200	(Lcom/mixpanel/android/mpmetrics/InAppFragment;)Lcom/mixpanel/android/mpmetrics/UpdateDisplayState$DisplayState$InAppNotificationState;
        //   7: invokevirtual 47	com/mixpanel/android/mpmetrics/UpdateDisplayState$DisplayState$InAppNotificationState:getInAppNotification	()Lcom/mixpanel/android/mpmetrics/InAppNotification;
        //   10: astore_1
        //   11: aload_1
        //   12: invokevirtual 53	com/mixpanel/android/mpmetrics/InAppNotification:getCallToActionUrl	()Ljava/lang/String;
        //   15: astore_2
        //   16: aload_2
        //   17: ifnull +55 -> 72
        //   20: aload_2
        //   21: invokevirtual 59	java/lang/String:length	()I
        //   24: ifle +48 -> 72
        //   27: aload_2
        //   28: invokestatic 65	android/net/Uri:parse	(Ljava/lang/String;)Landroid/net/Uri;
        //   31: astore_2
        //   32: new 67	android/content/Intent
        //   35: dup
        //   36: ldc 69
        //   38: aload_2
        //   39: invokespecial 72	android/content/Intent:<init>	(Ljava/lang/String;Landroid/net/Uri;)V
        //   42: astore_3
        //   43: aload_0
        //   44: getfield 17	com/mixpanel/android/mpmetrics/InAppFragment$3:this$0	Lcom/mixpanel/android/mpmetrics/InAppFragment;
        //   47: invokestatic 76	com/mixpanel/android/mpmetrics/InAppFragment:access$400	(Lcom/mixpanel/android/mpmetrics/InAppFragment;)Landroid/app/Activity;
        //   50: aload_3
        //   51: invokevirtual 82	android/app/Activity:startActivity	(Landroid/content/Intent;)V
        //   54: aload_0
        //   55: getfield 17	com/mixpanel/android/mpmetrics/InAppFragment$3:this$0	Lcom/mixpanel/android/mpmetrics/InAppFragment;
        //   58: invokestatic 86	com/mixpanel/android/mpmetrics/InAppFragment:access$500	(Lcom/mixpanel/android/mpmetrics/InAppFragment;)Lcom/mixpanel/android/mpmetrics/MixpanelAPI;
        //   61: invokevirtual 92	com/mixpanel/android/mpmetrics/MixpanelAPI:getPeople	()Lcom/mixpanel/android/mpmetrics/MixpanelAPI$People;
        //   64: ldc 94
        //   66: aload_1
        //   67: invokeinterface 100 3 0
        //   72: aload_0
        //   73: getfield 17	com/mixpanel/android/mpmetrics/InAppFragment$3:this$0	Lcom/mixpanel/android/mpmetrics/InAppFragment;
        //   76: invokestatic 28	com/mixpanel/android/mpmetrics/InAppFragment:access$000	(Lcom/mixpanel/android/mpmetrics/InAppFragment;)V
        //   79: iconst_1
        //   80: ireturn
        //   81: astore_1
        //   82: ldc 102
        //   84: ldc 104
        //   86: aload_1
        //   87: invokestatic 110	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
        //   90: pop
        //   91: iconst_1
        //   92: ireturn
        //   93: astore_1
        //   94: ldc 102
        //   96: new 112	java/lang/StringBuilder
        //   99: dup
        //   100: invokespecial 113	java/lang/StringBuilder:<init>	()V
        //   103: ldc 115
        //   105: invokevirtual 119	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   108: aload_2
        //   109: invokevirtual 122	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   112: invokevirtual 125	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   115: invokestatic 128	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
        //   118: pop
        //   119: goto -47 -> 72
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	122	0	this	3
        //   0	122	1	paramAnonymousMotionEvent	MotionEvent
        //   15	94	2	localObject	Object
        //   42	9	3	localIntent	android.content.Intent
        // Exception table:
        //   from	to	target	type
        //   27	32	81	java/lang/IllegalArgumentException
        //   32	72	93	android/content/ActivityNotFoundException
      }
    });
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mCleanedUp = false;
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    if (this.mDisplayState == null) {
      cleanUp();
    }
    for (;;)
    {
      return this.mInAppView;
      this.mInAppView = paramLayoutInflater.inflate(R.layout.com_mixpanel_android_activity_notification_mini, paramViewGroup, false);
      paramLayoutInflater = (TextView)this.mInAppView.findViewById(R.id.com_mixpanel_android_notification_title);
      paramViewGroup = (ImageView)this.mInAppView.findViewById(R.id.com_mixpanel_android_notification_image);
      paramBundle = this.mDisplayState.getInAppNotification();
      paramLayoutInflater.setText(paramBundle.getTitle());
      paramViewGroup.setImageBitmap(paramBundle.getImage());
      this.mHandler.postDelayed(this.mRemover, 10000L);
    }
  }
  
  public void onPause()
  {
    super.onPause();
    cleanUp();
  }
  
  public void onResume()
  {
    super.onResume();
    this.mHandler.postDelayed(this.mDisplayMini, 500L);
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    cleanUp();
    super.onSaveInstanceState(paramBundle);
  }
  
  public void onStart()
  {
    super.onStart();
    if (this.mCleanedUp) {
      this.mParent.getFragmentManager().beginTransaction().remove(this).commit();
    }
  }
  
  public void setDisplayState(MixpanelAPI paramMixpanelAPI, int paramInt, UpdateDisplayState.DisplayState.InAppNotificationState paramInAppNotificationState)
  {
    this.mMixpanel = paramMixpanelAPI;
    this.mDisplayStateId = paramInt;
    this.mDisplayState = paramInAppNotificationState;
  }
  
  private class SineBounceInterpolator
    implements Interpolator
  {
    public SineBounceInterpolator() {}
    
    public float getInterpolation(float paramFloat)
    {
      return (float)-(Math.pow(2.718281828459045D, -8.0F * paramFloat) * Math.cos(12.0F * paramFloat)) + 1.0F;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/mpmetrics/InAppFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */