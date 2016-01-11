package com.happyfresh.fragments;

import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class CannotEditOrderDialogFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, CannotEditOrderDialogFragment paramCannotEditOrderDialogFragment, Object paramObject)
  {
    View localView = paramFinder.findRequiredView(paramObject, 2131558713, "field 'mContactUs' and method 'contactUs'");
    paramCannotEditOrderDialogFragment.mContactUs = ((TextView)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.contactUs();
      }
    });
    paramFinder.findRequiredView(paramObject, 2131558711, "method 'close'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.close();
      }
    });
    paramFinder.findRequiredView(paramObject, 2131558712, "method 'gotIt'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.gotIt();
      }
    });
  }
  
  public static void reset(CannotEditOrderDialogFragment paramCannotEditOrderDialogFragment)
  {
    paramCannotEditOrderDialogFragment.mContactUs = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/CannotEditOrderDialogFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */