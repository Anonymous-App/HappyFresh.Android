package com.happyfresh.fragments;

import android.widget.TextView;
import butterknife.ButterKnife.Finder;

public class OrderPaymentStatusFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, OrderPaymentStatusFragment paramOrderPaymentStatusFragment, Object paramObject)
  {
    paramOrderPaymentStatusFragment.orderSuccessful = paramFinder.findRequiredView(paramObject, 2131558791, "field 'orderSuccessful'");
    paramOrderPaymentStatusFragment.orderFailed = paramFinder.findRequiredView(paramObject, 2131558794, "field 'orderFailed'");
    paramOrderPaymentStatusFragment.orderNumber = ((TextView)paramFinder.findRequiredView(paramObject, 2131558792, "field 'orderNumber'"));
    paramOrderPaymentStatusFragment.orderContactEnquiries = ((TextView)paramFinder.findRequiredView(paramObject, 2131558793, "field 'orderContactEnquiries'"));
  }
  
  public static void reset(OrderPaymentStatusFragment paramOrderPaymentStatusFragment)
  {
    paramOrderPaymentStatusFragment.orderSuccessful = null;
    paramOrderPaymentStatusFragment.orderFailed = null;
    paramOrderPaymentStatusFragment.orderNumber = null;
    paramOrderPaymentStatusFragment.orderContactEnquiries = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/OrderPaymentStatusFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */