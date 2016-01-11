package com.ad4screen.sdk.common.compatibility;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorInflater;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.i;

@TargetApi(11)
public final class a
{
  public static Notification.Builder a(Notification.Builder paramBuilder, RemoteViews paramRemoteViews)
  {
    return paramBuilder.setContent(paramRemoteViews);
  }
  
  public static Notification.Builder a(Context paramContext, String paramString, int paramInt, Bitmap paramBitmap, com.ad4screen.sdk.service.modules.push.model.a parama, PendingIntent paramPendingIntent)
  {
    int i = 1;
    Notification.Builder localBuilder = new Notification.Builder(paramContext);
    if (paramBitmap != null) {
      localBuilder.setLargeIcon(paramBitmap);
    }
    paramString = localBuilder.setAutoCancel(true).setSmallIcon(paramInt).setTicker(parama.n).setContentTitle(paramString).setContentText(parama.n).setContentIntent(paramPendingIntent);
    paramInt = i;
    if (i.a(paramContext, "android.permission.VIBRATE")) {
      paramInt = 3;
    }
    paramString.setDefaults(paramInt).setLights(-1, 1000, 3000).setNumber(0);
    return localBuilder;
  }
  
  public static Notification a(Notification.Builder paramBuilder)
  {
    return paramBuilder.getNotification();
  }
  
  public static LinearLayout a(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    return new LinearLayout(paramContext, paramAttributeSet, paramInt);
  }
  
  public static void a(WebView paramWebView)
  {
    paramWebView.setBackgroundColor(((ColorDrawable)paramWebView.getBackground()).getColor());
  }
  
  public static void a(WebView paramWebView, boolean paramBoolean)
  {
    paramWebView.getSettings().setDisplayZoomControls(paramBoolean);
  }
  
  public static boolean a(Context paramContext, View paramView, String paramString, Animator.AnimatorListener paramAnimatorListener)
  {
    try
    {
      String str = paramContext.getPackageName();
      int i = paramContext.getResources().getIdentifier(paramString, "animator", str);
      if (i != 0)
      {
        paramContext = AnimatorInflater.loadAnimator(paramContext, i);
        paramContext.addListener(paramAnimatorListener);
        paramContext.setTarget(paramView);
        paramContext.start();
        return true;
      }
    }
    catch (Exception paramContext)
    {
      Log.warn("Animation|Could not use Property Animation : " + paramString);
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/compatibility/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */