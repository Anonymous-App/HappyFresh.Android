package com.happyfresh.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import com.happyfresh.activities.CheckoutActivity;
import com.happyfresh.activities.OrderPaymentStatusActivity;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.models.PaymentStatus;
import com.happyfresh.models.payload.AdyenPayload;
import com.happyfresh.models.payload.PaymentPayload;
import com.happyfresh.utils.AccengageTrackerUtils;
import com.happyfresh.utils.AdjustUtils;
import com.happyfresh.utils.GAUtils;
import com.happyfresh.utils.LogUtils;
import com.happyfresh.utils.MixpanelTrackerUtils;
import com.happyfresh.utils.OptimizelyUtils;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.util.EncodingUtils;

public class AdyenFragment
  extends WebPaymentFragment
{
  private final String URL_POST_DATA = String.format("%s.URL_POST_DATA", new Object[] { AdyenFragment.class.getSimpleName() });
  private final String URL_TAG = String.format("%s.URL_TAG", new Object[] { AdyenFragment.class.getSimpleName() });
  private AdyenPayload mAdyenPayload;
  private String mPaymentUrl;
  private String mPostData;
  private boolean mSucceeded = false;
  private String paymentUrl = "%s/payment/create?disable_delay=true";
  
  private String getPaymentMethod(URI paramURI)
  {
    HashMap localHashMap = new HashMap();
    paramURI = paramURI.getQuery();
    if (paramURI != null)
    {
      paramURI = paramURI.split("&");
      int j = paramURI.length;
      int i = 0;
      while (i < j)
      {
        String[] arrayOfString = paramURI[i].split("=");
        localHashMap.put(arrayOfString[0], arrayOfString[1]);
        i += 1;
      }
    }
    return (String)localHashMap.get("payment_method_name");
  }
  
  private void showOrderStatus(PaymentStatus paramPaymentStatus, PaymentPayload paramPaymentPayload, URI paramURI)
  {
    if (getActivity() == null) {
      return;
    }
    if (paramPaymentStatus == PaymentStatus.SUCCESS)
    {
      paramURI = getPaymentMethod(paramURI);
      LogUtils.LOG("paymentMethod >> " + paramURI);
      trackCompletePayment(paramURI);
      paramURI = new Intent(getActivity(), OrderPaymentStatusActivity.class);
      paramURI.putExtra("ICartConstant.ORDER_STATUS", paramPaymentStatus.toString());
      paramURI.putExtra("ICartConstant.ORDER_PAYLOAD", paramPaymentPayload);
      startActivity(paramURI);
      getActivity().finish();
      return;
    }
    paramPaymentStatus = null;
    try
    {
      paramPaymentPayload = mapQuery(paramURI.getQuery());
      paramPaymentStatus = paramPaymentPayload;
    }
    catch (UnsupportedEncodingException paramPaymentPayload)
    {
      for (;;)
      {
        paramPaymentPayload.printStackTrace();
      }
    }
    ((CheckoutActivity)getActivity()).getFragment().showPaymentError(paramPaymentStatus);
  }
  
  private void trackCompletePayment(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      GAUtils.trackECommerceTransaction(getApplication(), paramString);
      MixpanelTrackerUtils.trackCompletedPayment(getApplication(), paramString);
      AccengageTrackerUtils.trackCompletedPayment(getApplication(), paramString);
    }
    AdjustUtils.trackCompletedPayment(getApplication());
    OptimizelyUtils.trackRevenue(getApplication());
    if (this.mICartApplication.getSharedPreferencesManager().isLessThanTwoHoursSinceLastCancelOrder())
    {
      GAUtils.trackCompletePaymentAfterCancelOrder(this.mICartApplication);
      this.mICartApplication.getSharedPreferencesManager().resetLastCancelOrder();
    }
  }
  
  protected String getScreenName()
  {
    return null;
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putString(this.URL_TAG, this.mPaymentUrl);
    paramBundle.putString(this.URL_POST_DATA, this.mPostData);
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    if (paramBundle != null)
    {
      this.mPaymentUrl = paramBundle.getString(this.URL_TAG);
      this.mPostData = paramBundle.getString(this.URL_POST_DATA);
      if ((!TextUtils.isEmpty(this.mPaymentUrl)) && (!TextUtils.isEmpty(this.mPostData))) {
        this.mPaymentWebView.postUrl(this.mPaymentUrl, EncodingUtils.getBytes(this.mPostData, "BASE64"));
      }
    }
  }
  
  protected void sendTracker() {}
  
  public void setPaymentStatusFromReceivedUrl(String paramString)
  {
    try
    {
      URI localURI2 = new URI("https://api.happyfresh.com");
      URI localURI1 = new URI(paramString);
      String str = localURI1.getPath();
      LogUtils.LOG("URL >>  " + paramString);
      if (localURI2.getHost().equals(localURI1.getHost()))
      {
        if (str.equals("/payment/create")) {
          sendTracker("Redirect To Adyen");
        }
        paramString = PaymentStatus.fromString(str.substring(str.lastIndexOf('/') + 1));
        if ((paramString != PaymentStatus.UNKNOWN) && (!this.mSucceeded))
        {
          showOrderStatus(paramString, this.mAdyenPayload, localURI1);
          this.mSucceeded = true;
        }
      }
      else if (str.equals("/hpp/pay.shtml"))
      {
        sendTracker("Credit Card Details");
        return;
      }
    }
    catch (URISyntaxException paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void startPayment(PaymentPayload paramPaymentPayload)
  {
    this.mPaymentWebView.clearView();
    this.mSucceeded = false;
    this.mAdyenPayload = ((AdyenPayload)paramPaymentPayload);
    this.mPaymentUrl = String.format(this.paymentUrl, new Object[] { "https://api.happyfresh.com", this.mAdyenPayload.orderToken, this.mAdyenPayload.orderNumber });
    this.mPostData = ("order_number=" + this.mAdyenPayload.orderNumber + "&order_token=" + this.mAdyenPayload.orderToken + "&method=" + this.mAdyenPayload.paymentMethod);
    this.mPaymentWebView.postUrl(this.mPaymentUrl, EncodingUtils.getBytes(this.mPostData, "BASE64"));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/AdyenFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */