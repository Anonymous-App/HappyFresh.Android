package com.happyfresh.fragments;

import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class AboutFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, AboutFragment paramAboutFragment, Object paramObject)
  {
    paramAboutFragment.mVersion = ((TextView)paramFinder.findRequiredView(paramObject, 2131558580, "field 'mVersion'"));
    paramFinder.findRequiredView(paramObject, 2131558572, "method 'openFaq'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.openFaq();
      }
    });
    paramFinder.findRequiredView(paramObject, 2131558574, "method 'openToc'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.openToc();
      }
    });
    paramFinder.findRequiredView(paramObject, 2131558576, "method 'openPrivacy'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.openPrivacy();
      }
    });
    paramFinder.findRequiredView(paramObject, 2131558578, "method 'openContactUs'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.openContactUs();
      }
    });
  }
  
  public static void reset(AboutFragment paramAboutFragment)
  {
    paramAboutFragment.mVersion = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/AboutFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */