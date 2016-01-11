package com.optimizely.Preview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.optimizely.Optimizely;
import com.optimizely.utils.OptimizelyLogBuffer;
import java.util.ArrayList;
import java.util.List;

public class OptimizelyLogInfoView
  extends OverlayContentView
{
  @NonNull
  private final ViewPager listPager;
  private final PagerAdapter mPagerAdapter = new PagerAdapter()
  {
    public void destroyItem(ViewGroup paramAnonymousViewGroup, int paramAnonymousInt, Object paramAnonymousObject) {}
    
    public int getCount()
    {
      return OptimizelyLogInfoView.this.pages.size();
    }
    
    public int getItemPosition(Object paramAnonymousObject)
    {
      int j = OptimizelyLogInfoView.this.pages.indexOf(paramAnonymousObject);
      int i = j;
      if (j == -1) {
        i = -2;
      }
      return i;
    }
    
    public CharSequence getPageTitle(int paramAnonymousInt)
    {
      switch (paramAnonymousInt)
      {
      default: 
        return "";
      case 0: 
        return "Goals";
      }
      return "Errors";
    }
    
    public Object instantiateItem(ViewGroup paramAnonymousViewGroup, int paramAnonymousInt)
    {
      return OptimizelyLogInfoView.this.pages.get(paramAnonymousInt);
    }
    
    public boolean isViewFromObject(View paramAnonymousView, Object paramAnonymousObject)
    {
      return paramAnonymousView == paramAnonymousObject;
    }
  };
  @NonNull
  private final List<View> pages = new ArrayList(2);
  
  public OptimizelyLogInfoView(@NonNull Context paramContext, @NonNull OverlayNavigationViewPager paramOverlayNavigationViewPager, @NonNull Optimizely paramOptimizely)
  {
    super(paramContext, paramOverlayNavigationViewPager);
    this.listPager = new ViewPager(paramContext);
    this.listPager.setPadding(40, 24, 40, 20);
    paramOverlayNavigationViewPager = new PagerTabStrip(paramContext);
    Object localObject = new ViewPager.LayoutParams();
    ((ViewPager.LayoutParams)localObject).height = -2;
    ((ViewPager.LayoutParams)localObject).width = -1;
    ((ViewPager.LayoutParams)localObject).gravity = 48;
    this.listPager.addView(paramOverlayNavigationViewPager, (ViewGroup.LayoutParams)localObject);
    paramOverlayNavigationViewPager = paramOptimizely.getLogBuffer();
    if (paramOverlayNavigationViewPager != null) {
      paramOptimizely = paramOverlayNavigationViewPager.getGoalsSnapshot();
    }
    for (paramOverlayNavigationViewPager = paramOverlayNavigationViewPager.getErrorSnapshot();; paramOverlayNavigationViewPager = new ArrayList())
    {
      localObject = new TextView(paramContext);
      ((TextView)localObject).setText("No events so far");
      ListView localListView = new ListView(paramContext);
      localListView.setAdapter(new ArrayAdapterWithPadding(paramContext, paramOptimizely));
      localListView.setEmptyView((View)localObject);
      this.pages.add(localListView);
      paramOptimizely = new ListView(paramContext);
      paramOptimizely.setAdapter(new ArrayAdapterWithPadding(paramContext, paramOverlayNavigationViewPager));
      localListView.setEmptyView((View)localObject);
      this.pages.add(paramOptimizely);
      this.listPager.addView(localListView);
      this.listPager.addView(paramOptimizely);
      this.listPager.setAdapter(this.mPagerAdapter);
      return;
      paramOptimizely = new ArrayList();
    }
  }
  
  @NonNull
  public View getRootView()
  {
    return this.listPager;
  }
  
  @NonNull
  public String getTitle()
  {
    return "Event Logs";
  }
  
  private class ArrayAdapterWithPadding
    extends ArrayAdapter<String>
  {
    public ArrayAdapterWithPadding(@NonNull List<String> paramList)
    {
      super(17367043, localList);
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      paramView = super.getView(paramInt, paramView, paramViewGroup);
      paramView.setPadding(0, 24, 0, 24);
      return paramView;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Preview/OptimizelyLogInfoView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */