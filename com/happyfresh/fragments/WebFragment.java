package com.happyfresh.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class WebFragment
  extends BaseFragment
{
  @InjectView(2131558820)
  WebView webView;
  
  public static WebFragment newInstance(String paramString)
  {
    WebFragment localWebFragment = new WebFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("ABOUT_URL", paramString);
    localWebFragment.setArguments(localBundle);
    return localWebFragment;
  }
  
  protected String getScreenName()
  {
    return null;
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903159, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    this.webView.getSettings().setJavaScriptEnabled(true);
    this.webView.setWebViewClient(new WebViewClient()
    {
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
  
  public void onResume()
  {
    super.onResume();
    String str = getArguments().getString("ABOUT_URL");
    this.webView.loadUrl(str);
  }
  
  protected void sendTracker() {}
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/WebFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */