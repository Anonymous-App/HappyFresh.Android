package com.optimizely.Preview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.optimizely.Optimizely;
import com.optimizely.utils.OptimizelyConstants;

public class OptimizelyPreviewModeInfoRootView
  extends OverlayContentView
{
  @NonNull
  private final LinearLayout rootView;
  
  public OptimizelyPreviewModeInfoRootView(@NonNull Context paramContext, @NonNull OverlayNavigationViewPager paramOverlayNavigationViewPager, @NonNull final Optimizely paramOptimizely)
  {
    super(paramContext, paramOverlayNavigationViewPager);
    this.rootView = new LinearLayout(paramContext);
    this.rootView.setPadding(40, 54, 40, 40);
    this.rootView.setOrientation(1);
    LinearLayout localLinearLayout = new LinearLayout(paramContext);
    localLinearLayout.setOrientation(1);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -2);
    localLinearLayout.addView(makeNavigationLink("Switch Variations", new OptimizelySwitchVariationInfoView(paramContext, paramOverlayNavigationViewPager, paramOptimizely)), localLayoutParams);
    localLinearLayout.addView(makeNavigationLink("View Event Log", new OptimizelyLogInfoView(paramContext, paramOverlayNavigationViewPager, paramOptimizely)), localLayoutParams);
    localLinearLayout.addView(makeNavigationLink("See Test Details", new OptimizelyTestDetailsInfoView(paramContext, paramOverlayNavigationViewPager, paramOptimizely)), localLayoutParams);
    localLinearLayout.addView(makeNavigationLink("Hide Preview Icon", new OptimizelyHidePreviewInfoView(paramContext, paramOverlayNavigationViewPager, paramOptimizely)), localLayoutParams);
    paramOverlayNavigationViewPager = new LinearLayout.LayoutParams(-1, 0, 0.8F);
    this.rootView.addView(localLinearLayout, paramOverlayNavigationViewPager);
    paramContext = new Button(paramContext);
    paramContext.setText("Exit Preview");
    paramContext.setBackgroundColor(OptimizelyConstants.OPT_DARK_BLUE);
    paramOverlayNavigationViewPager = new LinearLayout.LayoutParams(-1, -2);
    paramContext.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramOptimizely.getPreviewManager().restartInEditMode();
      }
    });
    this.rootView.addView(paramContext, paramOverlayNavigationViewPager);
  }
  
  @NonNull
  public View getRootView()
  {
    return this.rootView;
  }
  
  @NonNull
  public String getTitle()
  {
    return "Preview Mode";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Preview/OptimizelyPreviewModeInfoRootView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */