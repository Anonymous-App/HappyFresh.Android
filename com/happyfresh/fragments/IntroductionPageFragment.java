package com.happyfresh.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.models.OnboardingString;

public class IntroductionPageFragment
  extends Fragment
{
  @InjectView(2131558744)
  TextView mContent;
  private ICartApplication mICartApplication;
  @InjectView(2131558741)
  ImageView mImage;
  private int mPosition;
  @InjectView(2131558743)
  TextView mTitle;
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mICartApplication = ICartApplication.get(this);
    this.mPosition = getArguments().getInt("ICartConstant.KEYS.EXTRAS.INTRODUCTION_PAGE_POSITION");
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903129, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    paramView = this.mICartApplication.getSharedPreferencesManager().getOnboardingStrings();
    int i;
    switch (this.mPosition)
    {
    default: 
      i = 2130837847;
      paramBundle = paramView.screen1Title;
      paramView = paramView.screen1Content;
    }
    for (;;)
    {
      this.mImage.setBackgroundResource(i);
      this.mTitle.setText(paramBundle);
      this.mContent.setText(paramView);
      return;
      i = 2130837847;
      paramBundle = paramView.screen1Title;
      paramView = paramView.screen1Content;
      continue;
      i = 2130837751;
      paramBundle = paramView.screen2Title;
      paramView = paramView.screen2Content;
      continue;
      i = 2130837741;
      paramBundle = paramView.screen3Title;
      paramView = paramView.screen3Content;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/IntroductionPageFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */