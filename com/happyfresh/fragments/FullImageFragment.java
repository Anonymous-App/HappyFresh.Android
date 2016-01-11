package com.happyfresh.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happyfresh.models.Image;
import com.viewpagerindicator.CirclePageIndicator;
import java.util.ArrayList;
import java.util.List;

public class FullImageFragment
  extends BaseFragment
{
  private static final String TAG = FullImageFragment.class.getSimpleName();
  private List<Image> mProductImages = new ArrayList();
  private ProductPagerAdapter mProductPagerAdapter;
  @InjectView(2131558737)
  ViewPager mViewPager;
  @InjectView(2131558738)
  CirclePageIndicator mViewPagerIndicator;
  
  protected String getScreenName()
  {
    return "Product Images";
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mProductPagerAdapter = new ProductPagerAdapter(getActivity().getSupportFragmentManager());
    this.mViewPager.setAdapter(this.mProductPagerAdapter);
    this.mViewPagerIndicator.setViewPager(this.mViewPager);
    this.mProductImages = getArguments().getParcelableArrayList("ICartConstant.KEYS.EXTRAS.PRODUCT_IMAGES");
    this.mViewPager.setCurrentItem(getArguments().getInt("ICartConstant.KEYS.EXTRAS.PRODUCT_IMAGES_POSITION"));
    this.mProductPagerAdapter.notifyDataSetChanged();
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903127, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
  
  private class ProductPagerAdapter
    extends FragmentStatePagerAdapter
  {
    public ProductPagerAdapter(FragmentManager paramFragmentManager)
    {
      super();
    }
    
    public int getCount()
    {
      return FullImageFragment.this.mProductImages.size();
    }
    
    public Fragment getItem(int paramInt)
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("ICartConstant.KEYS.EXTRAS.PRODUCT_IMAGE_URL", ((Image)FullImageFragment.this.mProductImages.get(paramInt)).originalUrl);
      ImageFragment localImageFragment = new ImageFragment();
      localImageFragment.setArguments(localBundle);
      return localImageFragment;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/FullImageFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */