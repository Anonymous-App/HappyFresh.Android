package com.optimizely.Preview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.optimizely.View.ViewUtils;
import com.optimizely.utils.OptimizelyConstants;
import java.util.ArrayList;
import java.util.List;

public class OverlayNavigationViewPager
  extends LinearLayout
{
  private static final int BACK_BUTTON_ID = ViewUtils.generateViewId();
  public static final int CLOSE_BUTTON_ID = ;
  private final View backButton;
  private final List<OverlayContentView> backStack = new ArrayList();
  private final ViewPager contentView;
  private volatile boolean listeningForAnimationFinish;
  private final PagerAdapter mAdapter = new PagerAdapter()
  {
    public void destroyItem(ViewGroup paramAnonymousViewGroup, int paramAnonymousInt, Object paramAnonymousObject)
    {
      OverlayNavigationViewPager.this.contentView.removeView(((OverlayContentView)paramAnonymousObject).getRootView());
    }
    
    public int getCount()
    {
      return OverlayNavigationViewPager.this.backStack.size();
    }
    
    public int getItemPosition(Object paramAnonymousObject)
    {
      return -2;
    }
    
    public Object instantiateItem(ViewGroup paramAnonymousViewGroup, int paramAnonymousInt)
    {
      paramAnonymousViewGroup = (OverlayContentView)OverlayNavigationViewPager.this.backStack.get(paramAnonymousInt);
      OverlayNavigationViewPager.this.contentView.addView(paramAnonymousViewGroup.getRootView(), -1, -1);
      return paramAnonymousViewGroup;
    }
    
    public boolean isViewFromObject(View paramAnonymousView, Object paramAnonymousObject)
    {
      return paramAnonymousView == ((OverlayContentView)paramAnonymousObject).getRootView();
    }
  };
  private final ViewPager.OnPageChangeListener mListener = new ViewPager.SimpleOnPageChangeListener()
  {
    public void onPageScrollStateChanged(int paramAnonymousInt)
    {
      if ((paramAnonymousInt == 0) && (OverlayNavigationViewPager.this.listeningForAnimationFinish))
      {
        if (OverlayNavigationViewPager.this.shouldRemoveLastPage)
        {
          OverlayNavigationViewPager.this.backStack.remove(OverlayNavigationViewPager.this.backStack.size() - 1);
          OverlayNavigationViewPager.this.mAdapter.notifyDataSetChanged();
          OverlayNavigationViewPager.this.updateNavBar();
          OverlayNavigationViewPager.access$402(OverlayNavigationViewPager.this, false);
        }
        OverlayNavigationViewPager.access$302(OverlayNavigationViewPager.this, false);
      }
    }
    
    public void onPageSelected(int paramAnonymousInt)
    {
      OverlayNavigationViewPager.access$302(OverlayNavigationViewPager.this, true);
    }
  };
  private boolean shouldRemoveLastPage;
  public final TextView titleText;
  
  public OverlayNavigationViewPager(@NonNull Context paramContext, @NonNull final PreviewFloatingActionButton paramPreviewFloatingActionButton)
  {
    super(paramContext);
    paramContext = new LinearLayout(getContext());
    paramContext.setOrientation(0);
    paramContext.setGravity(17);
    paramContext.setBackgroundColor(OptimizelyConstants.OPT_DARK_BLUE);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(0, -1, 0.1F);
    this.backButton = new SvgPathView(getContext(), OptimizelyConstants.OPT_BRIGHT_BLUE, "M 15.41 7.41 L 14 6 l -6 6 6 6 1.41 -1.41 L 10.83 12 z");
    this.backButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        OverlayNavigationViewPager.this.popPage();
      }
    });
    this.backButton.setPadding(5, 70, 5, 70);
    this.backButton.setId(BACK_BUTTON_ID);
    paramContext.addView(this.backButton, localLayoutParams);
    this.titleText = new TextView(getContext());
    this.titleText.setTextAppearance(getContext(), 16973890);
    this.titleText.setGravity(17);
    this.titleText.setTextColor(-1);
    Object localObject = new LinearLayout.LayoutParams(0, -1, 0.8F);
    paramContext.addView(this.titleText, (ViewGroup.LayoutParams)localObject);
    localObject = new ImageButton(getContext());
    ((ImageButton)localObject).setBackgroundColor(0);
    ((ImageButton)localObject).setImageResource(17301560);
    ((ImageButton)localObject).setColorFilter(OptimizelyConstants.OPT_BRIGHT_BLUE);
    ((ImageButton)localObject).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        OverlayNavigationViewPager.this.clear();
        paramPreviewFloatingActionButton.toggleView();
      }
    });
    ((ImageButton)localObject).setId(CLOSE_BUTTON_ID);
    paramContext.addView((View)localObject, localLayoutParams);
    this.contentView = new ViewPager(getContext())
    {
      public boolean onInterceptTouchEvent(MotionEvent paramAnonymousMotionEvent)
      {
        return false;
      }
      
      public boolean onTouchEvent(MotionEvent paramAnonymousMotionEvent)
      {
        return false;
      }
    };
    this.contentView.setAdapter(this.mAdapter);
    this.contentView.setOnPageChangeListener(this.mListener);
    setOrientation(1);
    addView(paramContext, new LinearLayout.LayoutParams(-1, 0, 0.15F));
    paramContext = new LinearLayout.LayoutParams(-1, 0, 0.85F);
    addView(this.contentView, paramContext);
  }
  
  private void popPage()
  {
    this.contentView.setCurrentItem(this.backStack.size() - 2, true);
    this.shouldRemoveLastPage = true;
  }
  
  private void updateNavBar()
  {
    this.titleText.setText(((OverlayContentView)this.backStack.get(this.backStack.size() - 1)).getTitle());
    View localView = this.backButton;
    if (this.backStack.size() > 1) {}
    for (int i = 0;; i = 4)
    {
      localView.setVisibility(i);
      return;
    }
  }
  
  public void clear()
  {
    this.backStack.clear();
    this.mAdapter.notifyDataSetChanged();
    this.contentView.setCurrentItem(0);
    this.listeningForAnimationFinish = false;
  }
  
  public View getCurrentPage()
  {
    return this.contentView.getChildAt(this.contentView.getCurrentItem());
  }
  
  public void pushPage(@NonNull OverlayContentView paramOverlayContentView)
  {
    this.backStack.add(paramOverlayContentView);
    this.mAdapter.notifyDataSetChanged();
    this.contentView.setCurrentItem(this.backStack.size() - 1, true);
    updateNavBar();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Preview/OverlayNavigationViewPager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */