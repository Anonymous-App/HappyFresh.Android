package com.happyfresh.fragments;

import android.webkit.WebView;
import butterknife.ButterKnife.Finder;

public class WebFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, WebFragment paramWebFragment, Object paramObject)
  {
    paramWebFragment.webView = ((WebView)paramFinder.findRequiredView(paramObject, 2131558820, "field 'webView'"));
  }
  
  public static void reset(WebFragment paramWebFragment)
  {
    paramWebFragment.webView = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/WebFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */