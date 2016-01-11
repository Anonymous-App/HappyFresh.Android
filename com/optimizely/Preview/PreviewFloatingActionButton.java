package com.optimizely.Preview;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import com.optimizely.Core.OptimizelyUtils;
import com.optimizely.Optimizely;
import com.optimizely.Optimizely.OptimizelyRunningMode;
import com.optimizely.utils.OptimizelyConstants;
import java.util.Map;

public class PreviewFloatingActionButton
  extends FrameLayout
{
  public static final String FLOATING_ACTION_BUTTON = "PreviewFloatingActionButton";
  private static final int MINIMIZED_DIAMETER = 100;
  private static PreviewFloatingActionButton sInstance;
  private int currentX = -1;
  private int currentY = -1;
  @NonNull
  public final OverlayNavigationViewPager expandedContentView;
  @NonNull
  private final SvgPathView iconView;
  public boolean isExpanded = false;
  private boolean isShown;
  private RectF mCachedSize = new RectF();
  @NonNull
  private final Optimizely mOptimizely;
  private final View.OnTouchListener mTouchListener = new View.OnTouchListener()
  {
    private float initialTouchX;
    private float initialTouchY;
    private boolean isDragEvent = false;
    
    public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
    {
      boolean bool = true;
      if ((paramAnonymousMotionEvent == null) || (PreviewFloatingActionButton.this.params == null) || (PreviewFloatingActionButton.this.windowManager == null)) {
        bool = false;
      }
      float f1;
      float f2;
      int i;
      do
      {
        do
        {
          return bool;
          switch (paramAnonymousMotionEvent.getAction())
          {
          default: 
            return false;
          case 0: 
            this.initialTouchX = paramAnonymousMotionEvent.getRawX();
            this.initialTouchY = paramAnonymousMotionEvent.getRawY();
            this.isDragEvent = false;
            return true;
          case 1: 
            if ((this.isDragEvent) && (!PreviewFloatingActionButton.this.isExpanded))
            {
              PreviewFloatingActionButton.access$202(PreviewFloatingActionButton.this, PreviewFloatingActionButton.this.params.x);
              PreviewFloatingActionButton.access$302(PreviewFloatingActionButton.this, PreviewFloatingActionButton.this.params.y);
              return true;
            }
            break;
          }
        } while (PreviewFloatingActionButton.this.isExpanded);
        PreviewFloatingActionButton.this.toggleView();
        return true;
        f1 = paramAnonymousMotionEvent.getRawX() - this.initialTouchX;
        f2 = paramAnonymousMotionEvent.getRawY() - this.initialTouchY;
        if (!PreviewFloatingActionButton.this.isExpanded)
        {
          PreviewFloatingActionButton.this.params.x = (PreviewFloatingActionButton.this.currentX + (int)f1);
          PreviewFloatingActionButton.this.params.y = (PreviewFloatingActionButton.this.currentY + (int)f2);
          PreviewFloatingActionButton.this.windowManager.updateViewLayout(PreviewFloatingActionButton.this, PreviewFloatingActionButton.this.params);
        }
        i = ViewConfiguration.get(paramAnonymousView.getContext()).getScaledTouchSlop();
      } while ((f1 <= i) && (f2 <= i));
      this.isDragEvent = true;
      return true;
    }
  };
  @NonNull
  private final Paint paint;
  private WindowManager.LayoutParams params;
  private WindowManager windowManager;
  
  private PreviewFloatingActionButton(@NonNull Optimizely paramOptimizely)
  {
    super(paramOptimizely.getCurrentContext());
    this.mOptimizely = paramOptimizely;
    setOnTouchListener(this.mTouchListener);
    setWillNotDraw(false);
    if (paramOptimizely.isEditorEnabled().booleanValue()) {}
    for (int i = OptimizelyConstants.OPT_TOAST_LIGHT_BLUE;; i = OptimizelyConstants.OPT_TOAST_DARK_BLUE)
    {
      this.iconView = new SvgPathView(paramOptimizely.getCurrentContext(), i, "m 599.9,74.6 -88.5,32 C 466.1,55.2 399.5,23.4 322.4,23.4 168.5,23.4 31,150.2 15.4,306.6 -0.2,463 111.9,589.8 265.8,589.8 442.31558,589.1588 557.2,463 572.8,306.6 578.1,253.3 568.6,203.5 547.7,160.9 Z M 534.7,278.5 C 522.8,397.7 413.9,494.3 291.5,494.3 169.1,494.3 79.6,397.7 91.5,278.5 103.4,159.3 212.3,62.7 334.7,62.7 404.3,62.7 463.3,94 499,142.9 l 58.6,-35.5 -48.6,50.5 -35.2,36.6 -196.3,203.6 c 15.5,6 32.6,9.3 50.9,9.3 79.8,0 150.8,-63 158.5,-140.7 1.6,-16.5 0.3,-32.3 -3.6,-47 l 33.2,-47.5 c 15.2,31.4 22,67.6 18.2,106.3 M 356.6,126 c -79.8,0 -150.8,63 -158.5,140.7 c -1.9,19.4,0.3,38,5.9,54.8 l 252.1,-152.7 C 432.4,142.4,397.3,126,356.6,126 z");
      this.iconView.setPadding(15, 15, 15, 15);
      addView(this.iconView);
      this.expandedContentView = new OverlayNavigationViewPager(getContext(), this);
      addView(this.expandedContentView);
      this.expandedContentView.setVisibility(4);
      this.paint = new Paint(1);
      this.paint.setStyle(Paint.Style.FILL);
      return;
    }
  }
  
  public static void hide(@NonNull Activity paramActivity)
  {
    if (sInstance == null) {}
    do
    {
      do
      {
        return;
      } while (!sInstance.isShown);
      sInstance.isShown = false;
    } while (Optimizely.getRunningMode() == Optimizely.OptimizelyRunningMode.NORMAL);
    if (sInstance.isExpanded) {
      sInstance.toggleView();
    }
    sInstance.windowManager = paramActivity.getWindowManager();
    try
    {
      sInstance.windowManager.removeViewImmediate(sInstance);
      return;
    }
    catch (Exception paramActivity)
    {
      sInstance.mOptimizely.verboseLog(true, "PreviewFloatingActionButton", "Error hiding Preview Mode Button %s", new Object[] { paramActivity });
    }
  }
  
  public static void show(@NonNull Activity paramActivity)
  {
    if ((sInstance == null) || (Optimizely.getRunningMode() == Optimizely.OptimizelyRunningMode.NORMAL)) {}
    Object localObject;
    do
    {
      do
      {
        return;
      } while (sInstance.isShown);
      sInstance.isShown = true;
      sInstance.windowManager = paramActivity.getWindowManager();
      localObject = OptimizelyUtils.getScreenSizeMap(paramActivity);
      if ((sInstance.currentX < 0) || (sInstance.currentX > ((Integer)((Map)localObject).get("width")).intValue())) {
        sInstance.currentX = (((Integer)((Map)localObject).get("width")).intValue() - 100);
      }
      if ((sInstance.currentY < 0) || (sInstance.currentY > ((Integer)((Map)localObject).get("height")).intValue())) {
        sInstance.currentY = 100;
      }
      sInstance.params = new WindowManager.LayoutParams(100, 100, 1003, 8, -3);
      localObject = paramActivity.getWindow().getDecorView().getRootView().getApplicationWindowToken();
      sInstance.params.token = ((IBinder)localObject);
      sInstance.params.gravity = 51;
      sInstance.params.x = sInstance.currentX;
      sInstance.params.y = sInstance.currentY;
      if (localObject != null) {
        try
        {
          sInstance.windowManager.addView(sInstance, sInstance.params);
          return;
        }
        catch (Exception paramActivity)
        {
          sInstance.mOptimizely.verboseLog(true, "PreviewFloatingActionButton", "Error displaying Preview Mode Button %s", new Object[] { paramActivity });
          return;
        }
      }
      localObject = paramActivity.findViewById(16908290);
    } while (localObject == null);
    ((View)localObject).post(new Runnable()
    {
      public void run()
      {
        PreviewFloatingActionButton.sInstance.params.token = this.val$activity.getWindow().getDecorView().getWindowToken();
        try
        {
          WindowManager localWindowManager = this.val$activity.getWindowManager();
          if (localWindowManager != null) {
            localWindowManager.addView(PreviewFloatingActionButton.sInstance, PreviewFloatingActionButton.sInstance.params);
          }
          return;
        }
        catch (Exception localException)
        {
          PreviewFloatingActionButton.sInstance.mOptimizely.verboseLog(true, "PreviewFloatingActionButton", "Error displaying Preview Mode Button %s", new Object[] { localException });
        }
      }
    });
  }
  
  public static PreviewFloatingActionButton start(@NonNull Optimizely paramOptimizely)
  {
    if (sInstance == null) {
      sInstance = new PreviewFloatingActionButton(paramOptimizely);
    }
    return sInstance;
  }
  
  public static void stop()
  {
    sInstance = null;
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    if (paramCanvas == null) {
      return;
    }
    if (this.isExpanded)
    {
      this.paint.setColor(OptimizelyConstants.OPT_MED_BLUE_A);
      paramCanvas.drawRect(this.mCachedSize, this.paint);
      return;
    }
    Paint localPaint = this.paint;
    if (this.mOptimizely.isEditorEnabled().booleanValue())
    {
      i = OptimizelyConstants.OPT_TOAST_DARK_BLUE;
      localPaint.setColor(i);
      paramCanvas.drawOval(this.mCachedSize, this.paint);
      paramCanvas = this.iconView;
      if (!this.mOptimizely.isEditorEnabled().booleanValue()) {
        break label109;
      }
    }
    label109:
    for (int i = OptimizelyConstants.OPT_TOAST_LIGHT_BLUE;; i = OptimizelyConstants.OPT_TOAST_DARK_BLUE)
    {
      paramCanvas.setColor(i);
      return;
      i = OptimizelyConstants.OPT_TOAST_LIGHT_BLUE;
      break;
    }
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.mCachedSize = new RectF(0.0F, 0.0F, paramInt1, paramInt2);
  }
  
  void toggleView()
  {
    int j = 4;
    boolean bool = false;
    if ((this.windowManager == null) || (this.params == null)) {
      return;
    }
    Object localObject;
    if (this.isExpanded)
    {
      this.params.width = 100;
      this.params.height = 100;
      this.params.x = this.currentX;
      this.params.y = this.currentY;
      localObject = this.expandedContentView;
      if (!this.isExpanded) {
        break label249;
      }
    }
    label249:
    for (int i = 4;; i = 0)
    {
      ((OverlayNavigationViewPager)localObject).setVisibility(i);
      localObject = this.iconView;
      i = j;
      if (this.isExpanded) {
        i = 0;
      }
      ((SvgPathView)localObject).setVisibility(i);
      if (!this.isExpanded) {
        bool = true;
      }
      this.isExpanded = bool;
      this.windowManager.updateViewLayout(this, this.params);
      return;
      this.params.width = -1;
      this.params.height = -1;
      this.params.x = 0;
      this.params.y = 0;
      this.expandedContentView.clear();
      if (this.mOptimizely.isEditorEnabled().booleanValue())
      {
        this.expandedContentView.pushPage(new OptimizelyEditModeInfoView(getContext(), this.expandedContentView, this.mOptimizely));
        break;
      }
      this.expandedContentView.pushPage(new OptimizelyPreviewModeInfoRootView(getContext(), this.expandedContentView, this.mOptimizely));
      break;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Preview/PreviewFloatingActionButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */