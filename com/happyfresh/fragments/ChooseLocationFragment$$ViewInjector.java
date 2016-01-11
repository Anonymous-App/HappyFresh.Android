package com.happyfresh.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.happyfresh.customs.CircularProgressBar;

public class ChooseLocationFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, ChooseLocationFragment paramChooseLocationFragment, Object paramObject)
  {
    View localView = paramFinder.findRequiredView(paramObject, 2131558688, "field 'mChooseLocation' and method 'chooseLocationManually'");
    paramChooseLocationFragment.mChooseLocation = ((Button)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.chooseLocationManually();
      }
    });
    paramChooseLocationFragment.mProgressContainer = paramFinder.findRequiredView(paramObject, 2131558690, "field 'mProgressContainer'");
    paramChooseLocationFragment.mCircularProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558691, "field 'mCircularProgressBar'"));
    localView = paramFinder.findRequiredView(paramObject, 2131558689, "field 'mCurrentCountry' and method 'changeCountry'");
    paramChooseLocationFragment.mCurrentCountry = ((TextView)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.changeCountry();
      }
    });
    paramFinder.findRequiredView(paramObject, 2131558687, "method 'onCurrentLocationButtonClick'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onCurrentLocationButtonClick();
      }
    });
  }
  
  public static void reset(ChooseLocationFragment paramChooseLocationFragment)
  {
    paramChooseLocationFragment.mChooseLocation = null;
    paramChooseLocationFragment.mProgressContainer = null;
    paramChooseLocationFragment.mCircularProgressBar = null;
    paramChooseLocationFragment.mCurrentCountry = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/ChooseLocationFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */