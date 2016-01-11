package com.happyfresh.fragments;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.happyfresh.activities.CheckoutActivity;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartNotification;
import com.happyfresh.customs.SlidingTabLayout;
import com.happyfresh.customs.SlidingTabLayout.TabColorizer;
import com.happyfresh.managers.OrderManager;
import com.happyfresh.models.Order;
import com.happyfresh.models.PaymentService;
import com.happyfresh.models.User;
import com.happyfresh.models.payload.AsiaPayload;
import com.happyfresh.models.payload.PaymentPayload;
import com.happyfresh.utils.DialogUtils;
import com.happyfresh.utils.GAUtils;
import java.util.Map;

public class CheckoutFragment
  extends BaseFragment
{
  private static final String TAG = CheckoutFragment.class.getSimpleName();
  private CheckoutActivity mActivity;
  private CheckoutPagerAdapter mCheckoutPagerAdapter;
  private User mCurrentUser;
  private PaymentPayload mPayload;
  private PaymentService mPaymentService = PaymentService.ADYEN;
  private SlidingTabLayout mSlidingTabLayout;
  private ViewPager mViewPager;
  
  private void getAsiaPaymentPayload()
  {
    getApplication().getOrderManager().getPaymentPayload(getApplication().currentOrder.number, new ICartCallback(TAG)
    {
      public void onSuccess(AsiaPayload paramAnonymousAsiaPayload)
      {
        CheckoutFragment.this.mViewPager.setCurrentItem(2);
        CheckoutFragment.access$202(CheckoutFragment.this, paramAnonymousAsiaPayload);
        ((CheckoutPaymentFragment)CheckoutFragment.this.mCheckoutPagerAdapter.instantiateItem(CheckoutFragment.this.mViewPager, 2)).processToPayment(PaymentService.ASIAPAY, CheckoutFragment.this.mPayload);
      }
    });
  }
  
  private Fragment getDeliveryFragment()
  {
    if (this.mCurrentUser.getPrimaryShippingAddress() != null)
    {
      localBundle = new Bundle();
      localBundle.putBoolean("ICartConstant.KEYS.EXPRESS_CHECKOUT", true);
      localObject = new CheckoutDeliveryFragment();
      ((Fragment)localObject).setArguments(localBundle);
      this.mActivity.setDeliveryScreenSelected("Delivery");
      return (Fragment)localObject;
    }
    Bundle localBundle = new Bundle();
    localBundle.putString("ICartConstant.KEYS.ADDRESS_SCREEN_TITLE", "Add Address");
    Object localObject = new CreateAddressFormFragment();
    ((Fragment)localObject).setArguments(localBundle);
    this.mActivity.setDeliveryScreenSelected("Add Address");
    return (Fragment)localObject;
  }
  
  public void backToDelivery()
  {
    this.mViewPager.setCurrentItem(1);
  }
  
  public void backToPaymentMethod()
  {
    CheckoutPaymentFragment localCheckoutPaymentFragment = ((CheckoutPagerAdapter)this.mViewPager.getAdapter()).getCheckoutPaymentFragment();
    if (localCheckoutPaymentFragment != null) {
      localCheckoutPaymentFragment.setPaymentWebViewVisibility(false);
    }
  }
  
  public void backToReplacement()
  {
    this.mViewPager.setCurrentItem(0);
  }
  
  protected String getScreenName()
  {
    return null;
  }
  
  public void goToDelivery()
  {
    if (this.mViewPager != null) {
      this.mViewPager.setCurrentItem(1);
    }
    getApplication().getICartNotification().raiseOnCheckoutStateEvent(this, 1);
  }
  
  public void goToPayment()
  {
    GAUtils.trackingECommerceCheckout(getApplication(), 4, null, "Choose Payment Method");
    switch (this.mPaymentService)
    {
    default: 
      return;
    case ???: 
      getAsiaPaymentPayload();
      return;
    }
    this.mViewPager.setCurrentItem(2);
    CheckoutPaymentFragment localCheckoutPaymentFragment = (CheckoutPaymentFragment)this.mCheckoutPagerAdapter.instantiateItem(this.mViewPager, 2);
    localCheckoutPaymentFragment.setPaymentWebViewVisibility(false);
    localCheckoutPaymentFragment.updateView();
  }
  
  public boolean isDelivery()
  {
    return (this.mViewPager != null) && (this.mViewPager.getCurrentItem() == 1);
  }
  
  public boolean isPayment()
  {
    if ((this.mViewPager != null) && (this.mViewPager.getCurrentItem() == 2))
    {
      CheckoutPaymentFragment localCheckoutPaymentFragment = ((CheckoutPagerAdapter)this.mViewPager.getAdapter()).getCheckoutPaymentFragment();
      if ((localCheckoutPaymentFragment != null) && (localCheckoutPaymentFragment.isPayment())) {
        return true;
      }
    }
    return false;
  }
  
  public boolean isPaymentMethod()
  {
    if ((this.mViewPager != null) && (this.mViewPager.getCurrentItem() == 2))
    {
      CheckoutPaymentFragment localCheckoutPaymentFragment = ((CheckoutPagerAdapter)this.mViewPager.getAdapter()).getCheckoutPaymentFragment();
      if ((localCheckoutPaymentFragment != null) && (!localCheckoutPaymentFragment.isPayment())) {
        return true;
      }
    }
    return false;
  }
  
  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mActivity = ((CheckoutActivity)paramActivity);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mCurrentUser = getApplication().getCurrentUser();
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903112, null);
  }
  
  public void onResume()
  {
    super.onResume();
    int i = this.mViewPager.getCurrentItem();
    if (i == 0) {
      sendTracker("Replacement");
    }
    do
    {
      return;
      if (i == 1)
      {
        sendTracker(this.mActivity.getDeliveryScreenSelected());
        return;
      }
    } while (i != 2);
    sendTracker("Choose Payment Method");
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    this.mCheckoutPagerAdapter = new CheckoutPagerAdapter(this, getChildFragmentManager());
    this.mViewPager = ((ViewPager)paramView.findViewById(2131558642));
    this.mViewPager.setAdapter(this.mCheckoutPagerAdapter);
    this.mViewPager.setOffscreenPageLimit(3);
    this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
    {
      public void onPageScrollStateChanged(int paramAnonymousInt) {}
      
      public void onPageScrolled(int paramAnonymousInt1, float paramAnonymousFloat, int paramAnonymousInt2) {}
      
      public void onPageSelected(int paramAnonymousInt)
      {
        if (paramAnonymousInt == 0) {
          CheckoutFragment.this.sendTracker("Replacement");
        }
        do
        {
          return;
          if (paramAnonymousInt == 1)
          {
            CheckoutFragment.this.sendTracker(CheckoutFragment.this.mActivity.getDeliveryScreenSelected());
            return;
          }
        } while (paramAnonymousInt != 2);
        CheckoutFragment.this.sendTracker("Choose Payment Method");
      }
    });
    this.mSlidingTabLayout = ((SlidingTabLayout)paramView.findViewById(2131558641));
    this.mSlidingTabLayout.setViewPager(this.mViewPager, false);
    this.mSlidingTabLayout.enableNextDivider();
    this.mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer()
    {
      public int getDividerColor(int paramAnonymousInt)
      {
        return CheckoutFragment.this.getActivity().getResources().getColor(17170455);
      }
      
      public int getIndicatorColor(int paramAnonymousInt)
      {
        return CheckoutFragment.this.getActivity().getResources().getColor(2131492926);
      }
    });
  }
  
  protected void sendTracker() {}
  
  public void showPaymentError(Map<String, String> paramMap)
  {
    this.mViewPager.setCurrentItem(0);
    StringBuffer localStringBuffer = new StringBuffer();
    if ("slot_not_available".equalsIgnoreCase((String)paramMap.get("error")))
    {
      localStringBuffer.append(getString(2131165510));
      DialogUtils.showDialog(getActivity(), getString(2131165355), localStringBuffer.toString(), getString(2131165250), null, null, null);
      return;
    }
    String str = (String)paramMap.get("auth_result");
    paramMap = (String)paramMap.get("merchant_reference");
    if ("REFUSED".equalsIgnoreCase(str)) {
      localStringBuffer.append(getString(2131165617));
    }
    for (;;)
    {
      localStringBuffer.append(getString(2131165502, new Object[] { paramMap }));
      DialogUtils.showDialog(getActivity(), getString(2131165501), localStringBuffer.toString(), getString(2131165250), null, null, null);
      return;
      localStringBuffer.append(getString(2131165618));
    }
  }
  
  class CheckoutPagerAdapter
    extends FragmentPagerAdapter
  {
    private CheckoutPaymentFragment mCheckoutPaymentFragment;
    CheckoutFragment mFragment;
    
    public CheckoutPagerAdapter(CheckoutFragment paramCheckoutFragment, FragmentManager paramFragmentManager)
    {
      super();
      this.mFragment = paramCheckoutFragment;
    }
    
    public CheckoutPaymentFragment getCheckoutPaymentFragment()
    {
      return this.mCheckoutPaymentFragment;
    }
    
    public int getCount()
    {
      return 3;
    }
    
    public Fragment getItem(int paramInt)
    {
      Object localObject = null;
      if (paramInt == 0) {
        localObject = new ReplacementFragment();
      }
      for (;;)
      {
        ((Fragment)localObject).setTargetFragment(this.mFragment, paramInt);
        return (Fragment)localObject;
        if (paramInt == 1)
        {
          localObject = CheckoutFragment.this.getDeliveryFragment();
        }
        else if (paramInt == 2)
        {
          this.mCheckoutPaymentFragment = CheckoutPaymentFragment.newInstance(CheckoutFragment.this.mPaymentService);
          localObject = this.mCheckoutPaymentFragment;
        }
      }
    }
    
    public CharSequence getPageTitle(int paramInt)
    {
      String str = null;
      if (paramInt == 0) {
        str = CheckoutFragment.this.getString(2131165602);
      }
      do
      {
        return str;
        if (paramInt == 1) {
          return CheckoutFragment.this.getString(2131165600);
        }
      } while (paramInt != 2);
      return CheckoutFragment.this.getString(2131165601);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/CheckoutFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */