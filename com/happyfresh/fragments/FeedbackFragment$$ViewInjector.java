package com.happyfresh.fragments;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.happyfresh.customs.CircularProgressBar;

public class FeedbackFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, FeedbackFragment paramFeedbackFragment, Object paramObject)
  {
    paramFeedbackFragment.mProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558664, "field 'mProgressBar'"));
    paramFeedbackFragment.mFeedbackArrival = ((CheckBox)paramFinder.findRequiredView(paramObject, 2131558723, "field 'mFeedbackArrival'"));
    paramFeedbackFragment.mFeedbackApp = ((CheckBox)paramFinder.findRequiredView(paramObject, 2131558724, "field 'mFeedbackApp'"));
    paramFeedbackFragment.mFeedbackQuality = ((CheckBox)paramFinder.findRequiredView(paramObject, 2131558725, "field 'mFeedbackQuality'"));
    paramFeedbackFragment.mFeedBackOther = ((CheckBox)paramFinder.findRequiredView(paramObject, 2131558726, "field 'mFeedBackOther'"));
    paramFeedbackFragment.mFeedbackComment = ((EditText)paramFinder.findRequiredView(paramObject, 2131558727, "field 'mFeedbackComment'"));
    paramFinder.findRequiredView(paramObject, 2131558728, "method 'submit'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.submit();
      }
    });
  }
  
  public static void reset(FeedbackFragment paramFeedbackFragment)
  {
    paramFeedbackFragment.mProgressBar = null;
    paramFeedbackFragment.mFeedbackArrival = null;
    paramFeedbackFragment.mFeedbackApp = null;
    paramFeedbackFragment.mFeedbackQuality = null;
    paramFeedbackFragment.mFeedBackOther = null;
    paramFeedbackFragment.mFeedbackComment = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/FeedbackFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */