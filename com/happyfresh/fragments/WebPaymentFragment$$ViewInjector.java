package com.happyfresh.fragments;

import android.webkit.WebView;
import butterknife.ButterKnife.Finder;
import com.happyfresh.customs.CircularProgressBar;

public class WebPaymentFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, WebPaymentFragment paramWebPaymentFragment, Object paramObject)
  {
    paramWebPaymentFragment.mProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558694, "field 'mProgressBar'"));
    paramWebPaymentFragment.mPaymentWebView = ((WebView)paramFinder.findRequiredView(paramObject, 2131558820, "field 'mPaymentWebView'"));
  }
  
  public static void reset(WebPaymentFragment paramWebPaymentFragment)
  {
    paramWebPaymentFragment.mProgressBar = null;
    paramWebPaymentFragment.mPaymentWebView = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/WebPaymentFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */