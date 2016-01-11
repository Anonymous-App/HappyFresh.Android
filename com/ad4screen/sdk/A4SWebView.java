package com.ad4screen.sdk;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.compatibility.k.m;
import com.ad4screen.sdk.common.e;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.service.modules.tracking.webview.a;
import com.ad4screen.sdk.systems.d.b;

@SuppressLint({"SetJavaScriptEnabled"})
@API
public class A4SWebView
  extends WebView
{
  private WebViewClient a;
  private A4S.Callback<Void> b;
  
  public A4SWebView(Context paramContext)
  {
    super(paramContext);
    a(paramContext);
  }
  
  public A4SWebView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    a(paramContext);
  }
  
  public A4SWebView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    a(paramContext);
  }
  
  private void a(Context paramContext)
  {
    if (isInEditMode()) {
      return;
    }
    b(paramContext);
    k.m.d(this, true);
    k.m.a(this, true);
    addJavascriptInterface(new A4SJSInterface(null), "A4S");
    super.setWebViewClient(new a(null));
  }
  
  private void b(Context paramContext)
  {
    ApplicationInfo localApplicationInfo = paramContext.getApplicationInfo();
    int i = localApplicationInfo.flags & 0x2;
    localApplicationInfo.flags = i;
    if (i != 0) {
      k.m.a(paramContext);
    }
  }
  
  public void setCloseUrlActionCallback(A4S.Callback<Void> paramCallback)
  {
    this.b = paramCallback;
  }
  
  public void setWebViewClient(WebViewClient paramWebViewClient)
  {
    this.a = paramWebViewClient;
  }
  
  @API
  private class A4SJSInterface
  {
    private A4SJSInterface() {}
    
    @JavascriptInterface
    public void setView(String paramString)
    {
      A4S.get(A4SWebView.this.getContext()).setView(paramString);
    }
    
    @JavascriptInterface
    public void trackAddToCart(String paramString)
    {
      paramString = com.ad4screen.sdk.common.b.b(paramString);
      Log.debug("A4SWebView|Tracking Cart event WebView with id " + paramString.getId());
      A4S.get(A4SWebView.this.getContext()).trackAddToCart(paramString);
    }
    
    @JavascriptInterface
    public void trackEvent(int paramInt, String paramString)
    {
      switch (paramInt)
      {
      default: 
        Log.debug("A4SWebView|Tracking event WebView with id " + paramInt);
        A4S.get(A4SWebView.this.getContext()).trackEvent(paramInt, new String[] { paramString });
        return;
      case 10: 
        trackLead(paramString);
        return;
      case 30: 
        trackAddToCart(paramString);
        return;
      }
      trackPurchase(paramString);
    }
    
    @JavascriptInterface
    public void trackLead(String paramString)
    {
      paramString = com.ad4screen.sdk.common.b.a(paramString);
      Log.debug("A4SWebView|Tracking Lead event WebView with label " + paramString.getLabel() + " and value " + paramString.getValue());
      A4S.get(A4SWebView.this.getContext()).trackLead(paramString);
    }
    
    @JavascriptInterface
    public void trackPurchase(String paramString)
    {
      paramString = com.ad4screen.sdk.common.b.c(paramString);
      Log.debug("A4SWebView|Tracking Purchase event WebView with id " + paramString.getId());
      A4S.get(A4SWebView.this.getContext()).trackPurchase(paramString);
    }
    
    @JavascriptInterface
    public void updateDeviceInfo(String paramString)
    {
      A4S.get(A4SWebView.this.getContext()).updateDeviceInfo(com.ad4screen.sdk.common.b.d(paramString));
    }
  }
  
  private class a
    extends WebViewClient
  {
    private a() {}
    
    private void a(String paramString)
    {
      com.ad4screen.sdk.service.modules.tracking.webview.d.a(paramString).a(new a(A4S.get(A4SWebView.this.getContext()), A4SWebView.b(A4SWebView.this)));
    }
    
    public void doUpdateVisitedHistory(WebView paramWebView, String paramString, boolean paramBoolean)
    {
      if (A4SWebView.a(A4SWebView.this) != null)
      {
        A4SWebView.a(A4SWebView.this).doUpdateVisitedHistory(paramWebView, paramString, paramBoolean);
        return;
      }
      super.doUpdateVisitedHistory(paramWebView, paramString, paramBoolean);
    }
    
    public void onFormResubmission(WebView paramWebView, Message paramMessage1, Message paramMessage2)
    {
      if (A4SWebView.a(A4SWebView.this) != null)
      {
        A4SWebView.a(A4SWebView.this).onFormResubmission(paramWebView, paramMessage1, paramMessage2);
        return;
      }
      super.onFormResubmission(paramWebView, paramMessage1, paramMessage2);
    }
    
    public void onLoadResource(WebView paramWebView, String paramString)
    {
      if (A4SWebView.a(A4SWebView.this) != null)
      {
        A4SWebView.a(A4SWebView.this).onLoadResource(paramWebView, paramString);
        return;
      }
      super.onLoadResource(paramWebView, paramString);
    }
    
    public void onPageFinished(WebView paramWebView, String paramString)
    {
      com.ad4screen.sdk.systems.b localb = com.ad4screen.sdk.systems.b.a(A4SWebView.this.getContext());
      String str2 = localb.J;
      String str1 = str2;
      if (TextUtils.isEmpty(str2)) {
        str1 = com.ad4screen.sdk.systems.d.a(A4SWebView.this.getContext()).a(d.b.F);
      }
      str1 = h.a(A4SWebView.this.getContext(), str1, new e[] { new e("partnerId", Uri.encode(localb.d)), new e("sharedId", Uri.encode(localb.f)) });
      A4SWebView.this.loadUrl("javascript:(function() {var ad4sInsertScript=function(u){var b=document,c=b.getElementsByTagName('head')[0],e=b.createElement('script');e.text='var A4SPartnerID=\"" + localb.d + "\"';d=b.createElement('script');d.setAttribute('src',u);c.appendChild(e);c.appendChild(d);};ad4sInsertScript('" + str1 + "')})()");
      if (A4SWebView.a(A4SWebView.this) != null)
      {
        A4SWebView.a(A4SWebView.this).onPageFinished(paramWebView, paramString);
        return;
      }
      super.onPageFinished(paramWebView, paramString);
    }
    
    public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
    {
      if (A4SWebView.a(A4SWebView.this) != null)
      {
        A4SWebView.a(A4SWebView.this).onPageStarted(paramWebView, paramString, paramBitmap);
        return;
      }
      super.onPageStarted(paramWebView, paramString, paramBitmap);
    }
    
    public void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2)
    {
      if (A4SWebView.a(A4SWebView.this) != null)
      {
        A4SWebView.a(A4SWebView.this).onReceivedError(paramWebView, paramInt, paramString1, paramString2);
        return;
      }
      super.onReceivedError(paramWebView, paramInt, paramString1, paramString2);
    }
    
    public void onReceivedHttpAuthRequest(WebView paramWebView, HttpAuthHandler paramHttpAuthHandler, String paramString1, String paramString2)
    {
      if (A4SWebView.a(A4SWebView.this) != null)
      {
        A4SWebView.a(A4SWebView.this).onReceivedHttpAuthRequest(paramWebView, paramHttpAuthHandler, paramString1, paramString2);
        return;
      }
      super.onReceivedHttpAuthRequest(paramWebView, paramHttpAuthHandler, paramString1, paramString2);
    }
    
    @TargetApi(12)
    public void onReceivedLoginRequest(WebView paramWebView, String paramString1, String paramString2, String paramString3)
    {
      if (A4SWebView.a(A4SWebView.this) != null)
      {
        A4SWebView.a(A4SWebView.this).onReceivedLoginRequest(paramWebView, paramString1, paramString2, paramString3);
        return;
      }
      super.onReceivedLoginRequest(paramWebView, paramString1, paramString2, paramString3);
    }
    
    @TargetApi(8)
    public void onReceivedSslError(WebView paramWebView, SslErrorHandler paramSslErrorHandler, SslError paramSslError)
    {
      if (A4SWebView.a(A4SWebView.this) != null)
      {
        A4SWebView.a(A4SWebView.this).onReceivedSslError(paramWebView, paramSslErrorHandler, paramSslError);
        return;
      }
      super.onReceivedSslError(paramWebView, paramSslErrorHandler, paramSslError);
    }
    
    public void onScaleChanged(WebView paramWebView, float paramFloat1, float paramFloat2)
    {
      if (A4SWebView.a(A4SWebView.this) != null)
      {
        A4SWebView.a(A4SWebView.this).onScaleChanged(paramWebView, paramFloat1, paramFloat2);
        return;
      }
      super.onScaleChanged(paramWebView, paramFloat1, paramFloat2);
    }
    
    public void onTooManyRedirects(WebView paramWebView, Message paramMessage1, Message paramMessage2)
    {
      if (A4SWebView.a(A4SWebView.this) != null)
      {
        A4SWebView.a(A4SWebView.this).onTooManyRedirects(paramWebView, paramMessage1, paramMessage2);
        return;
      }
      super.onTooManyRedirects(paramWebView, paramMessage1, paramMessage2);
    }
    
    public void onUnhandledKeyEvent(WebView paramWebView, KeyEvent paramKeyEvent)
    {
      if (A4SWebView.a(A4SWebView.this) != null)
      {
        A4SWebView.a(A4SWebView.this).onUnhandledKeyEvent(paramWebView, paramKeyEvent);
        return;
      }
      super.onUnhandledKeyEvent(paramWebView, paramKeyEvent);
    }
    
    @TargetApi(11)
    public WebResourceResponse shouldInterceptRequest(WebView paramWebView, String paramString)
    {
      if (A4SWebView.a(A4SWebView.this) != null) {
        return A4SWebView.a(A4SWebView.this).shouldInterceptRequest(paramWebView, paramString);
      }
      return super.shouldInterceptRequest(paramWebView, paramString);
    }
    
    public boolean shouldOverrideKeyEvent(WebView paramWebView, KeyEvent paramKeyEvent)
    {
      if (A4SWebView.a(A4SWebView.this) != null) {
        return A4SWebView.a(A4SWebView.this).shouldOverrideKeyEvent(paramWebView, paramKeyEvent);
      }
      return super.shouldOverrideKeyEvent(paramWebView, paramKeyEvent);
    }
    
    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      a(paramString);
      if (A4SWebView.a(A4SWebView.this) != null) {
        return A4SWebView.a(A4SWebView.this).shouldOverrideUrlLoading(paramWebView, paramString);
      }
      return super.shouldOverrideUrlLoading(paramWebView, paramString);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/A4SWebView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */