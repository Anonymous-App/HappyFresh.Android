package com.ad4screen.sdk.common.compatibility;

import android.annotation.TargetApi;
import android.webkit.WebSettings;
import android.webkit.WebView;

@TargetApi(7)
public final class h
{
  public static String a()
  {
    return System.getProperty("http.agent");
  }
  
  public static void a(WebView paramWebView, boolean paramBoolean)
  {
    paramWebView.getSettings().setDomStorageEnabled(paramBoolean);
  }
  
  public static void b(WebView paramWebView, boolean paramBoolean)
  {
    paramWebView.getSettings().setLoadWithOverviewMode(paramBoolean);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/compatibility/h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */