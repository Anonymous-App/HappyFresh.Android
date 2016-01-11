package com.optimizely.Network;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.optimizely.Core.OptimizelyUtils;
import com.optimizely.Optimizely;
import com.optimizely.Optimizely.OptimizelyRunningMode;
import com.optimizely.View.OptimizelyViews;
import com.optimizely.View.ViewUtils;
import com.optimizely.View.idmanager.OptimizelyIdManager;
import com.optimizely.utils.OptimizelyThreadPoolExecutor;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class OptimizelyScreenshot
{
  private static final long DEBOUNCE_THRESHOLD = 500L;
  private static final String DEVICE_ORIENTATION = "deviceOrientation";
  private static final String SCREENSHOT = "screenShot";
  private static final int SCREENSHOT_ATTEMPTS = 10;
  private static final int SCREENSHOT_POLLING_INTERVAL_MS = 250;
  private static final String SENDING_SCREENSHOT = "sendingScreenshot";
  private static final double defaultCompression = 0.7D;
  private static final double defaultImageScale = 1.0D;
  private volatile Bitmap bitmap;
  private volatile Canvas canvas;
  private int lastOrientation = 0;
  private ReusableByteArrayOutputStream mConversionResult;
  private long nextScreenshotTime;
  @NonNull
  private final Optimizely optimizely;
  @Nullable
  private AsyncTask<Void, Void, Void> pendingScreenshot;
  private final boolean shouldSendScreenshots = true;
  
  public OptimizelyScreenshot(@NonNull Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
  }
  
  private String encodeTobase64(@NonNull Bitmap paramBitmap)
  {
    if (this.mConversionResult == null) {
      this.mConversionResult = new ReusableByteArrayOutputStream(null);
    }
    for (;;)
    {
      paramBitmap.compress(Bitmap.CompressFormat.JPEG, 70, this.mConversionResult);
      return Base64.encodeToString(this.mConversionResult.getInternalBuffer(), 0, this.mConversionResult.getCount(), 2);
      this.mConversionResult.reset();
    }
  }
  
  private Bitmap getBitmap(int paramInt1, int paramInt2)
  {
    if (Build.VERSION.SDK_INT >= 17)
    {
      DisplayMetrics localDisplayMetrics = this.optimizely.getCurrentContext().getResources().getDisplayMetrics();
      if (paramInt1 > 0) {
        if (paramInt2 <= 0) {
          break label45;
        }
      }
      for (;;)
      {
        return Bitmap.createBitmap(localDisplayMetrics, paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
        paramInt1 = 1;
        break;
        label45:
        paramInt2 = 1;
      }
    }
    if (paramInt1 > 0) {
      if (paramInt2 <= 0) {
        break label72;
      }
    }
    for (;;)
    {
      return Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
      paramInt1 = 1;
      break;
      label72:
      paramInt2 = 1;
    }
  }
  
  @NonNull
  private JsonObject getSubViewDataForView(@Nullable View paramView)
  {
    if ((paramView == null) || (!ViewUtils.isViewOnScreen(paramView, this.optimizely))) {
      return new JsonObject();
    }
    Object localObject2 = new JsonArray();
    if ((paramView instanceof ViewGroup))
    {
      localObject1 = (ViewGroup)paramView;
      int i = 0;
      while (i < ((ViewGroup)localObject1).getChildCount())
      {
        ((JsonArray)localObject2).add(getSubViewDataForView(((ViewGroup)localObject1).getChildAt(i)));
        i += 1;
      }
    }
    Object localObject1 = new JsonObject();
    String str = this.optimizely.getIdManager().getOptimizelyId(paramView);
    if (str != null) {
      ((JsonObject)localObject1).addProperty("id", str);
    }
    ((JsonObject)localObject1).add("children", (JsonElement)localObject2);
    paramView = ViewUtils.getViewBoundsOnScreen(paramView, true, this.optimizely);
    float f = OptimizelyUtils.getScreenshotScaling(this.optimizely.getCurrentContext());
    localObject2 = new JsonObject();
    ((JsonObject)localObject2).addProperty("left", Integer.valueOf((int)(paramView.left * f)));
    ((JsonObject)localObject2).addProperty("top", Integer.valueOf((int)(paramView.top * f)));
    ((JsonObject)localObject2).addProperty("width", Integer.valueOf((int)(paramView.width() * f)));
    ((JsonObject)localObject2).addProperty("height", Integer.valueOf((int)(paramView.height() * f)));
    ((JsonObject)localObject1).add("frame", (JsonElement)localObject2);
    return (JsonObject)localObject1;
  }
  
  @NonNull
  Canvas getCanvas()
  {
    int i = this.optimizely.getCurrentContext().getResources().getConfiguration().orientation;
    float f;
    if ((this.canvas == null) || (i != this.lastOrientation))
    {
      Rect localRect = OptimizelyUtils.getScreenshotSize(this.optimizely.getCurrentContext());
      f = OptimizelyUtils.getScreenshotScaling(this.optimizely.getCurrentContext());
      this.bitmap = getBitmap((int)(localRect.width() * f + 0.5F), (int)(localRect.height() * f + 0.5F));
      if (this.bitmap == null) {
        break label127;
      }
      this.canvas = new Canvas(this.bitmap);
    }
    for (;;)
    {
      this.canvas.scale(f, f);
      sendOrientationToEditorIfNecessary(true);
      return this.canvas;
      label127:
      this.optimizely.verboseLog(true, "screenShot", "Out of memory! Could not allocatememory for screenshot.", new Object[0]);
    }
  }
  
  @Nullable
  public AsyncTask getPendingScreenshotTask()
  {
    return this.pendingScreenshot;
  }
  
  public boolean isScreenshotPending()
  {
    return this.pendingScreenshot != null;
  }
  
  public void sendOrientationToEditorIfNecessary(boolean paramBoolean)
  {
    if (Optimizely.getRunningMode() != Optimizely.OptimizelyRunningMode.EDIT) {}
    int i;
    do
    {
      return;
      i = this.optimizely.getCurrentContext().getResources().getConfiguration().orientation;
    } while ((i == this.lastOrientation) && (!paramBoolean));
    HashMap localHashMap = new HashMap();
    localHashMap.put("action", "deviceOrientation");
    localHashMap.put("orientation", Integer.valueOf(i));
    this.optimizely.sendMap(localHashMap);
    this.lastOrientation = i;
  }
  
  @Nullable
  public AsyncTask<Void, Void, Void> sendScreenShotToEditor()
  {
    if ((!this.shouldSendScreenshots) || (!this.optimizely.isActive()) || (!this.optimizely.isEditorEnabled().booleanValue())) {
      return null;
    }
    this.nextScreenshotTime = (500L + System.currentTimeMillis());
    if (this.pendingScreenshot != null) {
      return this.pendingScreenshot;
    }
    this.pendingScreenshot = new ScreenshotTask(null);
    this.pendingScreenshot.executeOnExecutor(OptimizelyThreadPoolExecutor.instance(), new Void[0]);
    return this.pendingScreenshot;
  }
  
  private class LoadBitmapRunnable
    implements Runnable
  {
    private final Canvas canvas;
    private boolean mFinished;
    final View view;
    
    public LoadBitmapRunnable(@NonNull View paramView, Canvas paramCanvas)
    {
      this.view = paramView;
      this.canvas = paramCanvas;
    }
    
    public boolean isFinished()
    {
      return this.mFinished;
    }
    
    public void run()
    {
      if (this.view != null) {
        this.view.draw(this.canvas);
      }
      this.mFinished = true;
      try
      {
        notify();
        return;
      }
      finally {}
    }
  }
  
  private static class ReusableByteArrayOutputStream
    extends ByteArrayOutputStream
  {
    private int getCount()
    {
      return this.count;
    }
    
    private byte[] getInternalBuffer()
    {
      return this.buf;
    }
  }
  
  private class ScreenshotTask
    extends AsyncTask<Void, Void, Void>
  {
    private ScreenshotTask() {}
    
    @Nullable
    protected Void doInBackground(Void... paramVarArgs)
    {
      for (;;)
      {
        long l = System.currentTimeMillis();
        if (l >= OptimizelyScreenshot.this.nextScreenshotTime)
        {
          paramVarArgs = new HashMap();
          paramVarArgs.put("action", "sendingScreenshot");
          OptimizelyScreenshot.this.optimizely.sendMap(paramVarArgs);
          paramVarArgs = OptimizelyScreenshot.this.optimizely.getViews().getCurrentActivity();
          if (paramVarArgs == null) {
            return null;
          }
        }
        else
        {
          try
          {
            Thread.sleep(OptimizelyScreenshot.this.nextScreenshotTime - l);
          }
          catch (InterruptedException paramVarArgs)
          {
            OptimizelyScreenshot.access$202(OptimizelyScreenshot.this, null);
            return null;
          }
        }
      }
      int i = 0;
      for (;;)
      {
        if (i < 10)
        {
          localObject = ViewUtils.rootView(paramVarArgs);
          if (localObject != null)
          {
            localObject = new OptimizelyScreenshot.LoadBitmapRunnable(OptimizelyScreenshot.this, (View)localObject, OptimizelyScreenshot.this.getCanvas());
            paramVarArgs.runOnUiThread((Runnable)localObject);
          }
        }
        else
        {
          try
          {
            if (!((OptimizelyScreenshot.LoadBitmapRunnable)localObject).isFinished()) {
              localObject.wait();
            }
            if (((OptimizelyScreenshot.LoadBitmapRunnable)localObject).isFinished())
            {
              if (OptimizelyScreenshot.this.bitmap != null) {
                break;
              }
              OptimizelyScreenshot.access$202(OptimizelyScreenshot.this, null);
              return null;
            }
          }
          catch (InterruptedException paramVarArgs)
          {
            OptimizelyScreenshot.access$202(OptimizelyScreenshot.this, null);
            return null;
          }
          finally {}
        }
        try
        {
          Thread.sleep(250L);
          i += 1;
        }
        catch (InterruptedException paramVarArgs)
        {
          OptimizelyScreenshot.access$202(OptimizelyScreenshot.this, null);
          return null;
        }
      }
      OptimizelyScreenshot.this.sendOrientationToEditorIfNecessary(false);
      Object localObject = OptimizelyScreenshot.this.encodeTobase64(OptimizelyScreenshot.this.bitmap);
      JsonObject localJsonObject = new JsonObject();
      localJsonObject.addProperty("action", "screenShot");
      localJsonObject.addProperty("imageData", (String)localObject);
      localJsonObject.addProperty("scale", Double.valueOf(1.0D));
      localJsonObject.addProperty("compression", Double.valueOf(0.7D));
      localJsonObject.add("viewDictionary", OptimizelyScreenshot.this.getSubViewDataForView(ViewUtils.rootView(paramVarArgs)));
      OptimizelyScreenshot.this.optimizely.sendObjectImmediate(localJsonObject);
      OptimizelyScreenshot.access$202(OptimizelyScreenshot.this, null);
      return null;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/OptimizelyScreenshot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */