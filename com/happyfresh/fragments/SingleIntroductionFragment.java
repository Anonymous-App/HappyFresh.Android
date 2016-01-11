package com.happyfresh.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.happyfresh.activities.AuthenticationActivity;
import com.happyfresh.activities.ChooseLocationActivity;
import com.happyfresh.activities.IntroductionActivity;
import com.happyfresh.common.ICartApplication;

public class SingleIntroductionFragment
  extends BaseFragment
{
  @OnClick({2131558870})
  void enter()
  {
    ((IntroductionActivity)getActivity()).getUserData();
    if (getApplication().isLoggedIn())
    {
      gotoChooseLocationActivity();
      return;
    }
    startActivity(new Intent(getActivity(), AuthenticationActivity.class));
  }
  
  protected String getScreenName()
  {
    return "Introduction AB Testing";
  }
  
  public void gotoChooseLocationActivity()
  {
    if (getActivity() == null) {
      return;
    }
    Intent localIntent = new Intent(getActivity(), ChooseLocationActivity.class);
    localIntent.setFlags(268468224);
    startActivity(localIntent);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903153, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/SingleIntroductionFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */