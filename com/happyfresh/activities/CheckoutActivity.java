package com.happyfresh.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.happyfresh.fragments.CheckoutFragment;

public class CheckoutActivity
  extends BaseActivity
{
  private final String CHECKOUT_FRAGMENT_TAG = "com.happyfresh.activities.CheckoutActivity.CHECKOUT_FRAGMENT_TAG";
  public String deliveryScreenSelected = "Delivery";
  private CheckoutFragment mFragment = new CheckoutFragment();
  public boolean replacementToggledOnce = false;
  
  protected Fragment createFragment()
  {
    return this.mFragment;
  }
  
  public String getDeliveryScreenSelected()
  {
    return this.deliveryScreenSelected;
  }
  
  public CheckoutFragment getFragment()
  {
    return this.mFragment;
  }
  
  protected String getFragmentTag()
  {
    return "com.happyfresh.activities.CheckoutActivity.CHECKOUT_FRAGMENT_TAG";
  }
  
  public void onBackPressed()
  {
    if (getSupportFragmentManager().getBackStackEntryCount() > 1)
    {
      getSupportFragmentManager().popBackStackImmediate();
      return;
    }
    if (this.mFragment.isDelivery())
    {
      this.mFragment.backToReplacement();
      return;
    }
    if (this.mFragment.isPaymentMethod())
    {
      this.mFragment.backToDelivery();
      return;
    }
    if (this.mFragment.isPayment())
    {
      this.mFragment.backToPaymentMethod();
      return;
    }
    super.onBackPressed();
  }
  
  protected void restoreFragment(Fragment paramFragment)
  {
    this.mFragment = ((CheckoutFragment)getSupportFragmentManager().findFragmentByTag("com.happyfresh.activities.CheckoutActivity.CHECKOUT_FRAGMENT_TAG"));
  }
  
  public void setDeliveryScreenSelected(String paramString)
  {
    this.deliveryScreenSelected = paramString;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/CheckoutActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */