package com.happyfresh.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.models.payload.PaymentPayload;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class WebPaymentFragment
  extends BaseFragment
{
  @InjectView(2131558820)
  WebView mPaymentWebView;
  @InjectView(2131558694)
  protected CircularProgressBar mProgressBar;
  
  protected static Map<String, String> mapQuery(String paramString)
    throws UnsupportedEncodingException
  {
    if (paramString != null)
    {
      LinkedHashMap localLinkedHashMap = new LinkedHashMap();
      String[] arrayOfString = paramString.split("&");
      int j = arrayOfString.length;
      int i = 0;
      for (;;)
      {
        paramString = localLinkedHashMap;
        if (i >= j) {
          break;
        }
        paramString = arrayOfString[i];
        int k = paramString.indexOf("=");
        localLinkedHashMap.put(URLDecoder.decode(paramString.substring(0, k), "UTF-8"), URLDecoder.decode(paramString.substring(k + 1), "UTF-8"));
        i += 1;
      }
    }
    paramString = Collections.EMPTY_MAP;
    return paramString;
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903142, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    this.mPaymentWebView.getSettings().setJavaScriptEnabled(true);
    this.mProgressBar.setVisibility(4);
    this.mProgressBar.setVisibility(0);
    this.mPaymentWebView.setWebViewClient(new WebViewClient()
    {
      public void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString)
      {
        super.onPageFinished(paramAnonymousWebView, paramAnonymousString);
        if (WebPaymentFragment.this.mProgressBar != null) {
          WebPaymentFragment.this.mProgressBar.setVisibility(4);
        }
        WebPaymentFragment.this.setPaymentStatusFromReceivedUrl(paramAnonymousString);
      }
      
      public void onPageStarted(WebView paramAnonymousWebView, String paramAnonymousString, Bitmap paramAnonymousBitmap)
      {
        super.onPageStarted(paramAnonymousWebView, paramAnonymousString, paramAnonymousBitmap);
      }
      
      public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
      {
        paramAnonymousWebView.loadUrl(paramAnonymousString);
        return true;
      }
    });
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
  
  public abstract void setPaymentStatusFromReceivedUrl(String paramString);
  
  public abstract void startPayment(PaymentPayload paramPaymentPayload);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/WebPaymentFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */