package com.happyfresh.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.happyfresh.customs.CircularProgressBar;

public class CheckoutDeliveryFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, CheckoutDeliveryFragment paramCheckoutDeliveryFragment, Object paramObject)
  {
    paramCheckoutDeliveryFragment.checkoutContainer = paramFinder.findRequiredView(paramObject, 2131558581, "field 'checkoutContainer'");
    paramCheckoutDeliveryFragment.deliveryInfoLabel = ((TextView)paramFinder.findRequiredView(paramObject, 2131558646, "field 'deliveryInfoLabel'"));
    paramCheckoutDeliveryFragment.mCheckoutDeliveryContainer = ((LinearLayout)paramFinder.findRequiredView(paramObject, 2131558645, "field 'mCheckoutDeliveryContainer'"));
    paramCheckoutDeliveryFragment.mDeliveryAddressText = ((TextView)paramFinder.findRequiredView(paramObject, 2131558516, "field 'mDeliveryAddressText'"));
    paramCheckoutDeliveryFragment.mDeliveryTime = ((TextView)paramFinder.findRequiredView(paramObject, 2131558652, "field 'mDeliveryTime'"));
    paramCheckoutDeliveryFragment.mPaymentDetail = paramFinder.findRequiredView(paramObject, 2131558655, "field 'mPaymentDetail'");
    paramCheckoutDeliveryFragment.mPaymentContent = paramFinder.findRequiredView(paramObject, 2131558656, "field 'mPaymentContent'");
    paramCheckoutDeliveryFragment.mPaymentProgress = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558662, "field 'mPaymentProgress'"));
    paramCheckoutDeliveryFragment.mSubtotal = ((TextView)paramFinder.findRequiredView(paramObject, 2131558657, "field 'mSubtotal'"));
    paramCheckoutDeliveryFragment.mDeliveryFee = ((TextView)paramFinder.findRequiredView(paramObject, 2131558658, "field 'mDeliveryFee'"));
    paramCheckoutDeliveryFragment.mDiscount = ((TextView)paramFinder.findRequiredView(paramObject, 2131558660, "field 'mDiscount'"));
    paramCheckoutDeliveryFragment.mDiscountContainer = paramFinder.findRequiredView(paramObject, 2131558659, "field 'mDiscountContainer'");
    paramCheckoutDeliveryFragment.mTotalFee = ((TextView)paramFinder.findRequiredView(paramObject, 2131558661, "field 'mTotalFee'"));
    paramCheckoutDeliveryFragment.mVoucherCodeText = ((EditText)paramFinder.findRequiredView(paramObject, 2131558654, "field 'mVoucherCodeText'"));
    View localView = paramFinder.findRequiredView(paramObject, 2131558663, "field 'mSubmitButton' and method 'attemptOpenPayment'");
    paramCheckoutDeliveryFragment.mSubmitButton = ((Button)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.attemptOpenPayment();
      }
    });
    paramCheckoutDeliveryFragment.mDeliverySlot = paramFinder.findRequiredView(paramObject, 2131558651, "field 'mDeliverySlot'");
    paramCheckoutDeliveryFragment.mProgressIndicator = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558664, "field 'mProgressIndicator'"));
    paramCheckoutDeliveryFragment.mSlotUpdateProgress = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558653, "field 'mSlotUpdateProgress'"));
    paramCheckoutDeliveryFragment.mDeliveryNumber = ((TextView)paramFinder.findRequiredView(paramObject, 2131558648, "field 'mDeliveryNumber'"));
    paramCheckoutDeliveryFragment.mVerifyArea = paramFinder.findRequiredView(paramObject, 2131558647, "field 'mVerifyArea'");
    localView = paramFinder.findRequiredView(paramObject, 2131558649, "field 'mVerifyNow' and method 'onVerifyNowClick'");
    paramCheckoutDeliveryFragment.mVerifyNow = ((TextView)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onVerifyNowClick();
      }
    });
    paramCheckoutDeliveryFragment.mVerified = paramFinder.findRequiredView(paramObject, 2131558650, "field 'mVerified'");
  }
  
  public static void reset(CheckoutDeliveryFragment paramCheckoutDeliveryFragment)
  {
    paramCheckoutDeliveryFragment.checkoutContainer = null;
    paramCheckoutDeliveryFragment.deliveryInfoLabel = null;
    paramCheckoutDeliveryFragment.mCheckoutDeliveryContainer = null;
    paramCheckoutDeliveryFragment.mDeliveryAddressText = null;
    paramCheckoutDeliveryFragment.mDeliveryTime = null;
    paramCheckoutDeliveryFragment.mPaymentDetail = null;
    paramCheckoutDeliveryFragment.mPaymentContent = null;
    paramCheckoutDeliveryFragment.mPaymentProgress = null;
    paramCheckoutDeliveryFragment.mSubtotal = null;
    paramCheckoutDeliveryFragment.mDeliveryFee = null;
    paramCheckoutDeliveryFragment.mDiscount = null;
    paramCheckoutDeliveryFragment.mDiscountContainer = null;
    paramCheckoutDeliveryFragment.mTotalFee = null;
    paramCheckoutDeliveryFragment.mVoucherCodeText = null;
    paramCheckoutDeliveryFragment.mSubmitButton = null;
    paramCheckoutDeliveryFragment.mDeliverySlot = null;
    paramCheckoutDeliveryFragment.mProgressIndicator = null;
    paramCheckoutDeliveryFragment.mSlotUpdateProgress = null;
    paramCheckoutDeliveryFragment.mDeliveryNumber = null;
    paramCheckoutDeliveryFragment.mVerifyArea = null;
    paramCheckoutDeliveryFragment.mVerifyNow = null;
    paramCheckoutDeliveryFragment.mVerified = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/CheckoutDeliveryFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */