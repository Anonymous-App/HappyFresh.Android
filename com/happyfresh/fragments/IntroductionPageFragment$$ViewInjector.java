package com.happyfresh.fragments;

import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;

public class IntroductionPageFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, IntroductionPageFragment paramIntroductionPageFragment, Object paramObject)
  {
    paramIntroductionPageFragment.mImage = ((ImageView)paramFinder.findRequiredView(paramObject, 2131558741, "field 'mImage'"));
    paramIntroductionPageFragment.mTitle = ((TextView)paramFinder.findRequiredView(paramObject, 2131558743, "field 'mTitle'"));
    paramIntroductionPageFragment.mContent = ((TextView)paramFinder.findRequiredView(paramObject, 2131558744, "field 'mContent'"));
  }
  
  public static void reset(IntroductionPageFragment paramIntroductionPageFragment)
  {
    paramIntroductionPageFragment.mImage = null;
    paramIntroductionPageFragment.mTitle = null;
    paramIntroductionPageFragment.mContent = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/IntroductionPageFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */