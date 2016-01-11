package com.happyfresh.fragments;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.customs.SquareRelativeLayout;

public class CheckoutPaymentFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, CheckoutPaymentFragment paramCheckoutPaymentFragment, Object paramObject)
  {
    paramCheckoutPaymentFragment.totalFee = ((TextView)paramFinder.findRequiredView(paramObject, 2131558668, "field 'totalFee'"));
    paramCheckoutPaymentFragment.circleButtonCc = ((ImageView)paramFinder.findRequiredView(paramObject, 2131558678, "field 'circleButtonCc'"));
    View localView = paramFinder.findRequiredView(paramObject, 2131558676, "field 'optionCc' and method 'onCreditCardSelected'");
    paramCheckoutPaymentFragment.optionCc = ((SquareRelativeLayout)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onCreditCardSelected();
      }
    });
    paramCheckoutPaymentFragment.iconCod = ((ImageView)paramFinder.findRequiredView(paramObject, 2131558674, "field 'iconCod'"));
    paramCheckoutPaymentFragment.iconCc = ((ImageView)paramFinder.findRequiredView(paramObject, 2131558677, "field 'iconCc'"));
    paramCheckoutPaymentFragment.circleButtonCod = ((ImageView)paramFinder.findRequiredView(paramObject, 2131558675, "field 'circleButtonCod'"));
    localView = paramFinder.findRequiredView(paramObject, 2131558673, "field 'optionCod' and method 'onCodSelected'");
    paramCheckoutPaymentFragment.optionCod = ((SquareRelativeLayout)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onCodSelected();
      }
    });
    paramCheckoutPaymentFragment.paymentContainer = ((FrameLayout)paramFinder.findRequiredView(paramObject, 2131558685, "field 'paymentContainer'"));
    paramCheckoutPaymentFragment.paymentFooter = ((RelativeLayout)paramFinder.findRequiredView(paramObject, 2131558665, "field 'paymentFooter'"));
    paramCheckoutPaymentFragment.buttonProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558670, "field 'buttonProgressBar'"));
    paramCheckoutPaymentFragment.paymentCcInfo = paramFinder.findRequiredView(paramObject, 2131558679, "field 'paymentCcInfo'");
    paramCheckoutPaymentFragment.paymentCcInfoArrow = ((ImageView)paramFinder.findRequiredView(paramObject, 2131558684, "field 'paymentCcInfoArrow'"));
    paramCheckoutPaymentFragment.paymentCcInfoText1 = ((TextView)paramFinder.findRequiredView(paramObject, 2131558680, "field 'paymentCcInfoText1'"));
    paramCheckoutPaymentFragment.paymentCcInfoText2 = ((TextView)paramFinder.findRequiredView(paramObject, 2131558681, "field 'paymentCcInfoText2'"));
    paramCheckoutPaymentFragment.paymentCcInfoText3 = ((TextView)paramFinder.findRequiredView(paramObject, 2131558682, "field 'paymentCcInfoText3'"));
    paramCheckoutPaymentFragment.paymentCcInfoText4 = ((TextView)paramFinder.findRequiredView(paramObject, 2131558683, "field 'paymentCcInfoText4'"));
    paramCheckoutPaymentFragment.paymentOptionsContainer = paramFinder.findRequiredView(paramObject, 2131558671, "field 'paymentOptionsContainer'");
    paramCheckoutPaymentFragment.paymentProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558686, "field 'paymentProgressBar'"));
    paramFinder.findRequiredView(paramObject, 2131558669, "method 'onSubmit'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onSubmit();
      }
    });
  }
  
  public static void reset(CheckoutPaymentFragment paramCheckoutPaymentFragment)
  {
    paramCheckoutPaymentFragment.totalFee = null;
    paramCheckoutPaymentFragment.circleButtonCc = null;
    paramCheckoutPaymentFragment.optionCc = null;
    paramCheckoutPaymentFragment.iconCod = null;
    paramCheckoutPaymentFragment.iconCc = null;
    paramCheckoutPaymentFragment.circleButtonCod = null;
    paramCheckoutPaymentFragment.optionCod = null;
    paramCheckoutPaymentFragment.paymentContainer = null;
    paramCheckoutPaymentFragment.paymentFooter = null;
    paramCheckoutPaymentFragment.buttonProgressBar = null;
    paramCheckoutPaymentFragment.paymentCcInfo = null;
    paramCheckoutPaymentFragment.paymentCcInfoArrow = null;
    paramCheckoutPaymentFragment.paymentCcInfoText1 = null;
    paramCheckoutPaymentFragment.paymentCcInfoText2 = null;
    paramCheckoutPaymentFragment.paymentCcInfoText3 = null;
    paramCheckoutPaymentFragment.paymentCcInfoText4 = null;
    paramCheckoutPaymentFragment.paymentOptionsContainer = null;
    paramCheckoutPaymentFragment.paymentProgressBar = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/CheckoutPaymentFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */