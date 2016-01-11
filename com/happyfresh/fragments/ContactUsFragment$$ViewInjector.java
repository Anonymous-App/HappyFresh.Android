package com.happyfresh.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.happyfresh.customs.CircularProgressBar;

public class ContactUsFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, ContactUsFragment paramContactUsFragment, Object paramObject)
  {
    paramContactUsFragment.mCsIcon = ((ImageView)paramFinder.findRequiredView(paramObject, 2131558697, "field 'mCsIcon'"));
    paramContactUsFragment.mPart1Line1 = ((TextView)paramFinder.findRequiredView(paramObject, 2131558621, "field 'mPart1Line1'"));
    paramContactUsFragment.mPart1Line2 = ((TextView)paramFinder.findRequiredView(paramObject, 2131558622, "field 'mPart1Line2'"));
    paramContactUsFragment.mPart2Line1 = ((TextView)paramFinder.findRequiredView(paramObject, 2131558623, "field 'mPart2Line1'"));
    paramContactUsFragment.mPart2Line2 = ((TextView)paramFinder.findRequiredView(paramObject, 2131558624, "field 'mPart2Line2'"));
    paramContactUsFragment.mPart3Line1 = ((TextView)paramFinder.findRequiredView(paramObject, 2131558698, "field 'mPart3Line1'"));
    paramContactUsFragment.mPart3Line2 = ((TextView)paramFinder.findRequiredView(paramObject, 2131558699, "field 'mPart3Line2'"));
    View localView = paramFinder.findRequiredView(paramObject, 2131558702, "field 'mSubmitButton' and method 'submit'");
    paramContactUsFragment.mSubmitButton = ((Button)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.submit();
      }
    });
    paramContactUsFragment.mProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558664, "field 'mProgressBar'"));
    paramContactUsFragment.mTitleMessage = ((TextView)paramFinder.findRequiredView(paramObject, 2131558700, "field 'mTitleMessage'"));
    paramContactUsFragment.mContactUsMessage = ((EditText)paramFinder.findRequiredView(paramObject, 2131558701, "field 'mContactUsMessage'"));
    paramContactUsFragment.mScrollView = ((ScrollView)paramFinder.findRequiredView(paramObject, 2131558618, "field 'mScrollView'"));
    paramContactUsFragment.mProgressContainer = paramFinder.findRequiredView(paramObject, 2131558703, "field 'mProgressContainer'");
    paramContactUsFragment.mCircularProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558704, "field 'mCircularProgressBar'"));
  }
  
  public static void reset(ContactUsFragment paramContactUsFragment)
  {
    paramContactUsFragment.mCsIcon = null;
    paramContactUsFragment.mPart1Line1 = null;
    paramContactUsFragment.mPart1Line2 = null;
    paramContactUsFragment.mPart2Line1 = null;
    paramContactUsFragment.mPart2Line2 = null;
    paramContactUsFragment.mPart3Line1 = null;
    paramContactUsFragment.mPart3Line2 = null;
    paramContactUsFragment.mSubmitButton = null;
    paramContactUsFragment.mProgressBar = null;
    paramContactUsFragment.mTitleMessage = null;
    paramContactUsFragment.mContactUsMessage = null;
    paramContactUsFragment.mScrollView = null;
    paramContactUsFragment.mProgressContainer = null;
    paramContactUsFragment.mCircularProgressBar = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/ContactUsFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */