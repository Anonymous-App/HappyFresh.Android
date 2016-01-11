package com.happyfresh.fragments;

import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class UnsupportedDialogFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, UnsupportedDialogFragment paramUnsupportedDialogFragment, Object paramObject)
  {
    paramUnsupportedDialogFragment.mDialogTitle = ((TextView)paramFinder.findRequiredView(paramObject, 2131558617, "field 'mDialogTitle'"));
    paramFinder.findRequiredView(paramObject, 2131558711, "method 'close'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.close();
      }
    });
    paramFinder.findRequiredView(paramObject, 2131558716, "method 'notified'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.notified();
      }
    });
    paramFinder.findRequiredView(paramObject, 2131558717, "method 'chooseOtherLocation'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.chooseOtherLocation();
      }
    });
  }
  
  public static void reset(UnsupportedDialogFragment paramUnsupportedDialogFragment)
  {
    paramUnsupportedDialogFragment.mDialogTitle = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/UnsupportedDialogFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */