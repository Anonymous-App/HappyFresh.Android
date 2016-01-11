package com.ad4screen.sdk.common.compatibility;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.os.Environment;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.R.styleable;
import java.io.File;

@TargetApi(4)
public final class f
{
  public static LinearLayout a(Context paramContext)
  {
    return new LinearLayout(paramContext);
  }
  
  public static String a(byte[] paramArrayOfByte)
  {
    return new String(paramArrayOfByte);
  }
  
  public static void a(Activity paramActivity)
  {
    Window localWindow = paramActivity.getWindow();
    WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
    DisplayMetrics localDisplayMetrics = paramActivity.getResources().getDisplayMetrics();
    paramActivity = paramActivity.getTheme().obtainStyledAttributes(R.styleable.Window);
    if (localDisplayMetrics.widthPixels < localDisplayMetrics.heightPixels)
    {
      i = 1;
      if (i == 0) {
        break label71;
      }
    }
    label71:
    for (int i = R.styleable.Window_com_ad4screen_sdk_windowWidthMinor;; i = R.styleable.Window_com_ad4screen_sdk_windowWidthMajor)
    {
      if ((paramActivity != null) && (paramActivity.hasValue(i))) {
        break label78;
      }
      return;
      i = 0;
      break;
    }
    label78:
    TypedValue localTypedValue = new TypedValue();
    paramActivity.getValue(i, localTypedValue);
    if (localTypedValue.type != 0)
    {
      if (localTypedValue.type != 5) {
        break label134;
      }
      localLayoutParams.width = ((int)localTypedValue.getDimension(localDisplayMetrics));
    }
    for (;;)
    {
      localWindow.setAttributes(localLayoutParams);
      paramActivity.recycle();
      return;
      label134:
      if (localTypedValue.type == 6) {
        localLayoutParams.width = ((int)localTypedValue.getFraction(localDisplayMetrics.widthPixels, localDisplayMetrics.widthPixels));
      }
    }
  }
  
  @SuppressLint({"SetJavaScriptEnabled"})
  public static void a(WebView paramWebView, boolean paramBoolean)
  {
    try
    {
      paramWebView.getSettings().setJavaScriptEnabled(paramBoolean);
      return;
    }
    catch (NullPointerException paramWebView)
    {
      Log.error("Error during enabled Javascript due to TTS feature", paramWebView);
    }
  }
  
  public static boolean a(Context paramContext, View paramView, String paramString, Animation.AnimationListener paramAnimationListener)
  {
    try
    {
      String str = paramContext.getPackageName();
      int i = paramContext.getResources().getIdentifier(paramString, "anim", str);
      if (i != 0)
      {
        paramContext = AnimationUtils.loadAnimation(paramContext, i);
        paramContext.setAnimationListener(paramAnimationListener);
        paramView.startAnimation(paramContext);
        return true;
      }
    }
    catch (Exception paramContext)
    {
      Log.warn("Compatibility|Could not animate view using '" + paramString + '\'', paramContext);
      Log.warn("Animation|Could not use View Animation : " + paramString);
    }
    return false;
  }
  
  public static boolean a(HandlerThread paramHandlerThread)
  {
    paramHandlerThread = paramHandlerThread.getLooper();
    if (paramHandlerThread != null)
    {
      paramHandlerThread.quit();
      return true;
    }
    return false;
  }
  
  public static byte[] a(String paramString, int paramInt)
  {
    return paramString.getBytes();
  }
  
  public static File b(Context paramContext)
  {
    File localFile = Environment.getExternalStorageDirectory();
    return new File(localFile.getAbsolutePath() + "/Android/data/files/" + paramContext.getPackageName() + "/");
  }
  
  public static String c(Context paramContext)
  {
    return new WebView(paramContext).getSettings().getUserAgentString();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/compatibility/f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */