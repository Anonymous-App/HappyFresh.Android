package com.happyfresh.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.activities.AuthenticationActivity;
import com.happyfresh.activities.ChooseLocationActivity;
import com.happyfresh.activities.IntroductionActivity;
import com.happyfresh.common.ICartApplication;
import com.viewpagerindicator.CirclePageIndicator;

public class IntroductionFragment
  extends BaseFragment
{
  private static final String TAG = IntroductionFragment.class.getSimpleName();
  @InjectView(2131558746)
  Button mButtonStart;
  private IntroductionPagerAdapter mIntroductionPagerAdapter;
  @InjectView(2131558737)
  ViewPager mViewPager;
  @InjectView(2131558738)
  CirclePageIndicator mViewPagerIndicator;
  
  protected String getScreenName()
  {
    return null;
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
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mIntroductionPagerAdapter = new IntroductionPagerAdapter(getActivity().getSupportFragmentManager());
    this.mViewPager.setAdapter(this.mIntroductionPagerAdapter);
    this.mViewPagerIndicator.setViewPager(this.mViewPager);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903131, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
  
  public void onResume()
  {
    super.onResume();
    String str;
    switch (this.mViewPager.getCurrentItem())
    {
    default: 
      str = "Introduction AB Testing";
    }
    for (;;)
    {
      sendTracker(str);
      return;
      str = "Introduction 1";
      continue;
      str = "Introduction 2";
      this.mButtonStart.setText(getString(2131165412));
      continue;
      str = "Introduction 3";
      this.mButtonStart.setText(getString(2131165433));
    }
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
    {
      public void onPageScrollStateChanged(int paramAnonymousInt) {}
      
      public void onPageScrolled(int paramAnonymousInt1, float paramAnonymousFloat, int paramAnonymousInt2) {}
      
      public void onPageSelected(int paramAnonymousInt)
      {
        String str;
        switch (paramAnonymousInt)
        {
        default: 
          str = "Introduction AB Testing";
        }
        for (;;)
        {
          IntroductionFragment.this.sendTracker(str);
          return;
          str = "Introduction 1";
          continue;
          str = "Introduction 2";
          IntroductionFragment.this.mButtonStart.setText(IntroductionFragment.this.getString(2131165412));
          continue;
          str = "Introduction 3";
          IntroductionFragment.this.mButtonStart.setText(IntroductionFragment.this.getString(2131165433));
        }
      }
    });
  }
  
  @OnClick({2131558746})
  void skip()
  {
    if (this.mViewPager.getCurrentItem() == 0)
    {
      this.mViewPager.setCurrentItem(1, true);
      return;
    }
    if (this.mViewPager.getCurrentItem() == 1)
    {
      this.mViewPager.setCurrentItem(2, true);
      return;
    }
    ((IntroductionActivity)getActivity()).getUserData();
    if (getApplication().isLoggedIn())
    {
      gotoChooseLocationActivity();
      return;
    }
    startActivity(new Intent(getActivity(), AuthenticationActivity.class));
  }
  
  private class IntroductionPagerAdapter
    extends FragmentStatePagerAdapter
  {
    public IntroductionPagerAdapter(FragmentManager paramFragmentManager)
    {
      super();
    }
    
    public int getCount()
    {
      return 3;
    }
    
    public Fragment getItem(int paramInt)
    {
      Bundle localBundle = new Bundle();
      localBundle.putInt("ICartConstant.KEYS.EXTRAS.INTRODUCTION_PAGE_POSITION", paramInt);
      IntroductionPageFragment localIntroductionPageFragment = new IntroductionPageFragment();
      localIntroductionPageFragment.setArguments(localBundle);
      return localIntroductionPageFragment;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/IntroductionFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */