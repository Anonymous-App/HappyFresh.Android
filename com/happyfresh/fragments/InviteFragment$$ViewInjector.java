package com.happyfresh.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.happyfresh.customs.CircularProgressBar;

public class InviteFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, InviteFragment paramInviteFragment, Object paramObject)
  {
    paramInviteFragment.mInviteTitle = ((TextView)paramFinder.findRequiredView(paramObject, 2131558747, "field 'mInviteTitle'"));
    paramInviteFragment.mInviteText = ((TextView)paramFinder.findRequiredView(paramObject, 2131558748, "field 'mInviteText'"));
    paramInviteFragment.mStoreCreditProgress = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558750, "field 'mStoreCreditProgress'"));
    paramInviteFragment.mEarning = ((TextView)paramFinder.findRequiredView(paramObject, 2131558751, "field 'mEarning'"));
    View localView = paramFinder.findRequiredView(paramObject, 2131558752, "field 'mInviteButton' and method 'shareInvitation'");
    paramInviteFragment.mInviteButton = ((Button)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.shareInvitation();
      }
    });
    paramInviteFragment.shareIcon = ((ImageView)paramFinder.findRequiredView(paramObject, 2131558753, "field 'shareIcon'"));
    paramInviteFragment.wordingProgress = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558754, "field 'wordingProgress'"));
  }
  
  public static void reset(InviteFragment paramInviteFragment)
  {
    paramInviteFragment.mInviteTitle = null;
    paramInviteFragment.mInviteText = null;
    paramInviteFragment.mStoreCreditProgress = null;
    paramInviteFragment.mEarning = null;
    paramInviteFragment.mInviteButton = null;
    paramInviteFragment.shareIcon = null;
    paramInviteFragment.wordingProgress = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/InviteFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */