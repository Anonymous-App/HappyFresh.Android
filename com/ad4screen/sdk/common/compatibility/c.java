package com.ad4screen.sdk.common.compatibility;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Binder;
import android.webkit.WebSettings;
import android.webkit.WebView;
import java.lang.reflect.Method;

@TargetApi(18)
public final class c
{
  @SuppressLint({"SetJavaScriptEnabled"})
  public static void a(WebView paramWebView, boolean paramBoolean)
  {
    paramWebView.getSettings().setJavaScriptEnabled(paramBoolean);
  }
  
  public static boolean a(Context paramContext)
  {
    try
    {
      Object localObject = paramContext.getSystemService("appops");
      int i = ((Integer)localObject.getClass().getMethod("checkOpNoThrow", new Class[] { Integer.TYPE, Integer.TYPE, String.class }).invoke(localObject, new Object[] { Integer.valueOf(11), Integer.valueOf(Binder.getCallingUid()), paramContext.getPackageName() })).intValue();
      return i != 0;
    }
    catch (Error paramContext)
    {
      return false;
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/compatibility/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */