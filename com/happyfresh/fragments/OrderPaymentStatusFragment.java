package com.happyfresh.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.TaskStackBuilder;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.crashlytics.android.Crashlytics;
import com.happyfresh.activities.MainActivity;
import com.happyfresh.activities.OrderStatusActivity;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.listeners.OnBackPressedListener;
import com.happyfresh.managers.OrderManager;
import com.happyfresh.models.Country;
import com.happyfresh.models.Order;
import com.happyfresh.models.PaymentService;
import com.happyfresh.models.PaymentStatus;
import com.happyfresh.models.payload.AdyenPayload;
import com.happyfresh.models.payload.PaymentPayload;
import com.happyfresh.utils.LogUtils;
import java.util.List;

public class OrderPaymentStatusFragment
  extends BaseFragment
  implements OnBackPressedListener
{
  private static final String TAG = MainFragment.class.getSimpleName();
  private boolean mBackPressed;
  private String mOrderNumber;
  private PaymentStatus mPaymentStatus;
  @InjectView(2131558793)
  TextView orderContactEnquiries;
  @InjectView(2131558794)
  View orderFailed;
  @InjectView(2131558792)
  TextView orderNumber;
  @InjectView(2131558791)
  View orderSuccessful;
  
  public static OrderPaymentStatusFragment newInstance(PaymentStatus paramPaymentStatus, PaymentPayload paramPaymentPayload)
  {
    String str = null;
    if (paramPaymentPayload.getType() == PaymentService.ADYEN) {
      str = ((AdyenPayload)paramPaymentPayload).orderNumber;
    }
    paramPaymentPayload = new OrderPaymentStatusFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("ICartConstant.ORDER_STATUS", paramPaymentStatus.toString());
    localBundle.putString("ICartConstant.ORDER_NUMBER", str);
    paramPaymentPayload.setArguments(localBundle);
    return paramPaymentPayload;
  }
  
  private void openOrderTracking()
  {
    ICartApplication localICartApplication = getApplication();
    Intent localIntent1 = new Intent(localICartApplication, MainActivity.class);
    Intent localIntent2 = new Intent(localICartApplication, OrderStatusActivity.class);
    localIntent2.putExtra("ICartConstant.KEYS.ORDER_NUMBER", this.mOrderNumber);
    LogUtils.LOG("ORDER STATUS NUMBER PAYMENT >> " + this.mOrderNumber);
    Crashlytics.log("ORDER STATUS NUMBER PAYMENT >> " + this.mOrderNumber);
    TaskStackBuilder.create(localICartApplication).addNextIntentWithParentStack(localIntent1).addNextIntent(localIntent2).startActivities();
  }
  
  private void showOrderTracking()
  {
    new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        if (!OrderPaymentStatusFragment.this.mBackPressed)
        {
          OrderPaymentStatusFragment.access$002(OrderPaymentStatusFragment.this, true);
          OrderPaymentStatusFragment.this.syncOrder();
        }
      }
    }, 6000L);
  }
  
  private void syncOrder()
  {
    getApplication().getOrderManager().getOrder(getApplication().currentOrder.number, new ICartCallback(getScreenName())
    {
      public void onSuccess(Order paramAnonymousOrder)
      {
        OrderPaymentStatusFragment.this.getApplication().currentOrder = paramAnonymousOrder;
        OrderPaymentStatusFragment.this.getApplication().getOrderList().add(paramAnonymousOrder);
        if (OrderPaymentStatusFragment.this.getActivity() == null) {
          return;
        }
        OrderPaymentStatusFragment.this.openOrderTracking();
        OrderPaymentStatusFragment.this.getApplication().createOrder();
      }
    });
  }
  
  protected String getScreenName()
  {
    return "Order Confirmation";
  }
  
  public boolean onBackPressed()
  {
    if (!this.mBackPressed)
    {
      this.mBackPressed = true;
      syncOrder();
    }
    return false;
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903140, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    paramViewGroup = getArguments();
    this.mOrderNumber = paramViewGroup.getString("ICartConstant.ORDER_NUMBER");
    this.mPaymentStatus = PaymentStatus.fromString(paramViewGroup.getString("ICartConstant.ORDER_STATUS"));
    if (this.mPaymentStatus == PaymentStatus.SUCCESS)
    {
      this.orderSuccessful.setVisibility(0);
      this.orderFailed.setVisibility(8);
      this.orderNumber.setText(this.mOrderNumber);
      if (getApplication().currentOrder != null)
      {
        paramViewGroup = this.mICartApplication.currentOrder.country.csPhone;
        this.orderContactEnquiries.setText(Html.fromHtml(getString(2131165323, new Object[] { paramViewGroup })));
      }
      for (;;)
      {
        showOrderTracking();
        return paramLayoutInflater;
        this.orderContactEnquiries.setText("");
      }
    }
    this.orderSuccessful.setVisibility(8);
    this.orderFailed.setVisibility(0);
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/OrderPaymentStatusFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */