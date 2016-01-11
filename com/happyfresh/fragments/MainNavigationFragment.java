package com.happyfresh.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happyfresh.activities.BaseActivity;
import com.happyfresh.activities.MainActivity;
import com.happyfresh.customs.SlidingTabLayout;
import com.happyfresh.customs.SlidingTabLayout.TabColorizer;

public class MainNavigationFragment
  extends BaseFragment
{
  @InjectView(2131558641)
  SlidingTabLayout mSlidingTabLayout;
  private String mStockLocationId;
  private String mSupplierId;
  private String mType;
  @InjectView(2131558775)
  ViewPager mViewPager;
  
  private void selectPage(int paramInt)
  {
    if (paramInt == 0)
    {
      setTitle(2131165284);
      sendTracker("Shop");
    }
    do
    {
      return;
      if (paramInt == 1)
      {
        setTitle(2131165434);
        if (((MainActivity)getActivity()).isRecommendedSelected())
        {
          sendTracker("Recommended List Overview");
          return;
        }
        sendTracker("Favourites List");
        return;
      }
      if (paramInt == 2)
      {
        setTitle(2131165566);
        sendTracker("Share");
        return;
      }
      if (paramInt == 3)
      {
        setTitle(2131165403);
        sendTracker("About");
        return;
      }
    } while (paramInt != 4);
    setTitle(2131165454);
    sendTracker("More");
  }
  
  private void setTitle(int paramInt)
  {
    if ((getActivity() instanceof BaseActivity))
    {
      ActionBar localActionBar = ((BaseActivity)getActivity()).getSupportActionBar();
      if (localActionBar != null) {
        localActionBar.setTitle(getString(paramInt));
      }
    }
  }
  
  private void setupNavigation()
  {
    MainNavigationAdapter localMainNavigationAdapter = new MainNavigationAdapter(this, getFragmentManager());
    this.mViewPager.setAdapter(localMainNavigationAdapter);
    this.mViewPager.setOffscreenPageLimit(0);
    this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
    {
      public void onPageScrollStateChanged(int paramAnonymousInt) {}
      
      public void onPageScrolled(int paramAnonymousInt1, float paramAnonymousFloat, int paramAnonymousInt2) {}
      
      public void onPageSelected(int paramAnonymousInt)
      {
        MainNavigationFragment.this.selectPage(paramAnonymousInt);
      }
    });
    this.mSlidingTabLayout.setCustomTabView(2130903229, 2131559107, 2131559106);
    this.mSlidingTabLayout.setViewPager(this.mViewPager, true);
    this.mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer()
    {
      public int getDividerColor(int paramAnonymousInt)
      {
        return MainNavigationFragment.this.getResources().getColor(17170455);
      }
      
      public int getIndicatorColor(int paramAnonymousInt)
      {
        return MainNavigationFragment.this.getResources().getColor(2131492926);
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
    setupNavigation();
  }
  
  public boolean onBackPressed()
  {
    int i = this.mViewPager.getCurrentItem();
    if (i == 0) {
      return ((MainFragment)((MainNavigationAdapter)this.mViewPager.getAdapter()).getItem(i)).onBackPressed();
    }
    return false;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    paramBundle = getArguments();
    if (getArguments() != null)
    {
      this.mType = paramBundle.getString("ICartConstant.EXTRAS.DEEP_LINKS.TYPE");
      this.mStockLocationId = paramBundle.getString("ICartConstant.EXTRAS.DEEP_LINKS.STOCK_LOCATION_ID");
      this.mSupplierId = paramBundle.getString("ICartConstant.EXTRAS.DEEP_LINKS.SUPPLIER_ID");
    }
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903137, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    return paramLayoutInflater;
  }
  
  public void onResume()
  {
    super.onResume();
    int i = this.mViewPager.getCurrentItem();
    switch (i)
    {
    default: 
      return;
    case 0: 
      if (((MainFragment)((MainNavigationAdapter)this.mViewPager.getAdapter()).getItem(i)).isCategoryPickerOpen())
      {
        sendTracker("Category");
        return;
      }
      sendTracker("Shop");
      return;
    case 1: 
      if (((MainActivity)getActivity()).isRecommendedSelected())
      {
        sendTracker("Recommended List Overview");
        return;
      }
      sendTracker("Favourites List");
      return;
    case 2: 
      sendTracker("Share");
      return;
    case 3: 
      sendTracker("About");
      return;
    }
    sendTracker("More");
  }
  
  public class MainNavigationAdapter
    extends FragmentStatePagerAdapter
  {
    private int[] ICONS = { 2130837867, 2130837864, 2130837866, 2130837862, 2130837863 };
    public MainNavigationFragment mFragment;
    private Fragment[] mPoolFragments;
    
    public MainNavigationAdapter(MainNavigationFragment paramMainNavigationFragment, FragmentManager paramFragmentManager)
    {
      super();
      this.mFragment = paramMainNavigationFragment;
      this.mPoolFragments = new Fragment[getCount()];
    }
    
    public int getCount()
    {
      return 5;
    }
    
    public int getDrawableId(int paramInt)
    {
      return this.ICONS[paramInt];
    }
    
    public Fragment getItem(int paramInt)
    {
      Object localObject = null;
      if (this.mPoolFragments[paramInt] != null)
      {
        localObject = this.mPoolFragments[paramInt];
        ((Fragment)localObject).setTargetFragment(this.mFragment, paramInt);
        return (Fragment)localObject;
      }
      if (paramInt == 0)
      {
        localObject = new MainFragment();
        Bundle localBundle = new Bundle();
        localBundle.putString("ICartConstant.EXTRAS.DEEP_LINKS.TYPE", MainNavigationFragment.this.mType);
        localBundle.putString("ICartConstant.EXTRAS.DEEP_LINKS.STOCK_LOCATION_ID", MainNavigationFragment.this.mStockLocationId);
        localBundle.putString("ICartConstant.EXTRAS.DEEP_LINKS.SUPPLIER_ID", MainNavigationFragment.this.mSupplierId);
        ((Fragment)localObject).setArguments(localBundle);
      }
      for (;;)
      {
        this.mPoolFragments[paramInt] = localObject;
        ((Fragment)localObject).setTargetFragment(this.mFragment, paramInt);
        return (Fragment)localObject;
        if (paramInt == 1) {
          localObject = new ListsFavoriteFragment();
        } else if (paramInt == 2) {
          localObject = new InviteFragment();
        } else if (paramInt == 3) {
          localObject = new AboutFragment();
        } else if (paramInt == 4) {
          localObject = new MoreFragment();
        }
      }
    }
    
    public CharSequence getPageTitle(int paramInt)
    {
      String str = null;
      if (paramInt == 0) {
        str = MainNavigationFragment.this.getString(2131165571);
      }
      do
      {
        return str;
        if (paramInt == 1) {
          return MainNavigationFragment.this.getString(2131165434);
        }
        if (paramInt == 2) {
          return MainNavigationFragment.this.getString(2131165566);
        }
        if (paramInt == 3) {
          return MainNavigationFragment.this.getString(2131165403);
        }
      } while (paramInt != 4);
      return MainNavigationFragment.this.getString(2131165454);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/MainNavigationFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */