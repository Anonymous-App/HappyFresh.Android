package com.happyfresh.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.managers.ConfigManager;
import com.happyfresh.models.User;
import com.happyfresh.models.UserConfig;
import com.happyfresh.utils.AccengageTrackerUtils;
import com.happyfresh.utils.AdjustUtils;
import com.happyfresh.utils.MixpanelTrackerUtils;
import java.lang.ref.WeakReference;

public class InviteFragment
  extends BaseFragment
{
  private static final String TAG = InviteFragment.class.getSimpleName();
  @InjectView(2131558751)
  TextView mEarning;
  @InjectView(2131558752)
  Button mInviteButton;
  @InjectView(2131558748)
  TextView mInviteText;
  @InjectView(2131558747)
  TextView mInviteTitle;
  @InjectView(2131558750)
  CircularProgressBar mStoreCreditProgress;
  @InjectView(2131558753)
  ImageView shareIcon;
  @InjectView(2131558754)
  CircularProgressBar wordingProgress;
  
  private void fetchContent()
  {
    this.mStoreCreditProgress.setVisibility(0);
    User localUser = getApplication().getCurrentUser();
    final WeakReference localWeakReference = new WeakReference(this);
    getApplication().getConfigManager().getUserConfiguration(Long.valueOf(localUser.remoteId), new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        if (InviteFragment.this.getActivity() == null) {
          return;
        }
        InviteFragment.this.mStoreCreditProgress.setVisibility(8);
      }
      
      public void onSuccess(UserConfig paramAnonymousUserConfig)
      {
        if (((InviteFragment)localWeakReference.get()).mEarning == null) {
          return;
        }
        String str = InviteFragment.this.getApplication().getString(2131165567, new Object[] { paramAnonymousUserConfig.storeCreditAmountTotal });
        ((InviteFragment)localWeakReference.get()).mEarning.setText(Html.fromHtml(str));
        ((InviteFragment)localWeakReference.get()).mEarning.setVisibility(0);
        ((InviteFragment)localWeakReference.get()).mInviteTitle.setText(paramAnonymousUserConfig.discountCodeTitle);
        ((InviteFragment)localWeakReference.get()).mInviteText.setText(paramAnonymousUserConfig.discountCodeDescription);
        ((InviteFragment)localWeakReference.get()).mStoreCreditProgress.setVisibility(4);
      }
    });
  }
  
  protected String getScreenName()
  {
    return null;
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    paramBundle = getApplication().getCurrentUser();
    this.mInviteButton.setText(paramBundle.code);
    fetchContent();
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903132, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    this.mStoreCreditProgress.setVisibility(4);
    this.wordingProgress.setVisibility(4);
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
  
  @OnClick({2131558752})
  public void shareInvitation()
  {
    this.shareIcon.setVisibility(8);
    this.wordingProgress.setVisibility(0);
    Object localObject1 = getApplication().getCurrentUser();
    Object localObject2 = (UserConfig)new Select().from(UserConfig.class).executeSingle();
    this.shareIcon.setVisibility(0);
    this.wordingProgress.setVisibility(4);
    if ((localObject2 != null) && (!TextUtils.isEmpty(((UserConfig)localObject2).invitationText))) {}
    for (localObject1 = ((UserConfig)localObject2).invitationText;; localObject1 = getString(2131165568, new Object[] { ((User)localObject1).code }))
    {
      localObject2 = new Intent();
      ((Intent)localObject2).setAction("android.intent.action.SEND");
      ((Intent)localObject2).putExtra("android.intent.extra.TEXT", (String)localObject1);
      ((Intent)localObject2).setType("text/plain");
      startActivity(Intent.createChooser((Intent)localObject2, null));
      AdjustUtils.trackShared();
      MixpanelTrackerUtils.trackShared(getApplication());
      AccengageTrackerUtils.trackShared(getApplication());
      getApplication().sendEvent("ui_action", "Shared", null);
      return;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/InviteFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */