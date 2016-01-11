package com.happyfresh.fragments;

import android.os.Bundle;
import android.webkit.WebView;
import com.happyfresh.models.payload.AsiaPayload;
import com.happyfresh.models.payload.PaymentPayload;
import java.io.UnsupportedEncodingException;

public class AsiaPayFragment
  extends WebPaymentFragment
{
  private AsiaPayload mAsiaPayload;
  
  protected String getScreenName()
  {
    return null;
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
  }
  
  protected void sendTracker() {}
  
  public void setPaymentStatusFromReceivedUrl(String paramString) {}
  
  public void startPayment(PaymentPayload paramPaymentPayload)
  {
    try
    {
      this.mAsiaPayload = ((AsiaPayload)paramPaymentPayload);
      this.mPaymentWebView.postUrl(this.mAsiaPayload.paymentUrl, this.mAsiaPayload.toByteArray());
      return;
    }
    catch (UnsupportedEncodingException paramPaymentPayload)
    {
      paramPaymentPayload.printStackTrace();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/AsiaPayFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */