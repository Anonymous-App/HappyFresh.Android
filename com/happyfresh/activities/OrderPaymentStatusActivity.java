package com.happyfresh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.happyfresh.fragments.OrderPaymentStatusFragment;
import com.happyfresh.listeners.OnBackPressedListener;
import com.happyfresh.models.PaymentStatus;
import com.happyfresh.models.payload.PaymentPayload;

public class OrderPaymentStatusActivity
  extends BaseActivity
{
  private OnBackPressedListener mOnBackPressedListener;
  
  protected Fragment createFragment()
  {
    Object localObject2 = getIntent().getExtras();
    Object localObject1 = ((Bundle)localObject2).getString("ICartConstant.ORDER_STATUS");
    localObject2 = ((Bundle)localObject2).getParcelable("ICartConstant.ORDER_PAYLOAD");
    localObject1 = OrderPaymentStatusFragment.newInstance(PaymentStatus.fromString((String)localObject1), (PaymentPayload)localObject2);
    this.mOnBackPressedListener = ((OnBackPressedListener)localObject1);
    return (Fragment)localObject1;
  }
  
  public void onBackPressed()
  {
    if ((this.mOnBackPressedListener != null) && (this.mOnBackPressedListener.onBackPressed())) {
      super.onBackPressed();
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setToolbarTitle(null);
  }
  
  protected void setContentView()
  {
    setContentView(2130903069);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/OrderPaymentStatusActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */