package com.happyfresh.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class RateOurServiceFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, RateOurServiceFragment paramRateOurServiceFragment, Object paramObject)
  {
    paramRateOurServiceFragment.rateContainer = ((ScrollView)paramFinder.findRequiredView(paramObject, 2131558825, "field 'rateContainer'"));
    paramRateOurServiceFragment.orderNumber = ((TextView)paramFinder.findRequiredView(paramObject, 2131558792, "field 'orderNumber'"));
    paramRateOurServiceFragment.orderNumber2 = ((TextView)paramFinder.findRequiredView(paramObject, 2131558828, "field 'orderNumber2'"));
    paramRateOurServiceFragment.displayPrice = ((TextView)paramFinder.findRequiredView(paramObject, 2131558827, "field 'displayPrice'"));
    paramRateOurServiceFragment.ratingBar = ((RatingBar)paramFinder.findRequiredView(paramObject, 2131558829, "field 'ratingBar'"));
    paramFinder = paramFinder.findRequiredView(paramObject, 2131558702, "field 'submitButton' and method 'submitRate'");
    paramRateOurServiceFragment.submitButton = ((Button)paramFinder);
    paramFinder.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.submitRate();
      }
    });
  }
  
  public static void reset(RateOurServiceFragment paramRateOurServiceFragment)
  {
    paramRateOurServiceFragment.rateContainer = null;
    paramRateOurServiceFragment.orderNumber = null;
    paramRateOurServiceFragment.orderNumber2 = null;
    paramRateOurServiceFragment.displayPrice = null;
    paramRateOurServiceFragment.ratingBar = null;
    paramRateOurServiceFragment.submitButton = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/RateOurServiceFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */