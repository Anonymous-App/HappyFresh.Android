package com.optimizely.Preview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.optimizely.Optimizely;
import com.optimizely.utils.OptimizelyConstants;

public class OptimizelyEditModeInfoView
  extends OverlayContentView
{
  private final String buttonText = "Exit Edit Mode";
  private final String editModeDescription = "Your device is in Edit Mode and ready to pair with the Optimizely editor";
  private final String navigationDescription = "Use the device to navigate between app views. The editor will display the same view as your device";
  private final String navigationSectionTitle = "NAVIGATION";
  @NonNull
  private final LinearLayout rootView;
  private final String title = "Using Edit Mode";
  private final String variationsDescription = "Use the Optimizely editor to select and edit the elements you wish to change.";
  private final String variationsSectionTitle = "CREATING VARIATIONS";
  
  public OptimizelyEditModeInfoView(@NonNull Context paramContext, @NonNull OverlayNavigationViewPager paramOverlayNavigationViewPager, @NonNull final Optimizely paramOptimizely)
  {
    super(paramContext, paramOverlayNavigationViewPager);
    this.rootView = new LinearLayout(paramContext);
    this.rootView.setPadding(40, 54, 40, 40);
    this.rootView.setOrientation(1);
    paramOverlayNavigationViewPager = new LinearLayout(paramContext);
    paramOverlayNavigationViewPager.setOrientation(1);
    Object localObject = makeContentTextView("Your device is in Edit Mode and ready to pair with the Optimizely editor", false);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -2);
    paramOverlayNavigationViewPager.addView((View)localObject, localLayoutParams);
    paramOverlayNavigationViewPager.addView(makeContentTextView("NAVIGATION", true), localLayoutParams);
    paramOverlayNavigationViewPager.addView(makeContentTextView("Use the device to navigate between app views. The editor will display the same view as your device", false), localLayoutParams);
    paramOverlayNavigationViewPager.addView(makeContentTextView("CREATING VARIATIONS", true), localLayoutParams);
    paramOverlayNavigationViewPager.addView(makeContentTextView("Use the Optimizely editor to select and edit the elements you wish to change.", false), localLayoutParams);
    localObject = new LinearLayout.LayoutParams(-1, 0, 0.8F);
    this.rootView.addView(paramOverlayNavigationViewPager, (ViewGroup.LayoutParams)localObject);
    paramContext = new Button(paramContext);
    paramContext.setText("Exit Edit Mode");
    paramContext.setBackgroundColor(OptimizelyConstants.OPT_DARK_BLUE);
    paramContext.setTextColor(-1);
    paramOverlayNavigationViewPager = new LinearLayout.LayoutParams(-1, -2);
    this.rootView.addView(paramContext, paramOverlayNavigationViewPager);
    paramContext.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramOptimizely.getPreviewManager().restartInNormalMode();
      }
    });
  }
  
  @NonNull
  public View getRootView()
  {
    return this.rootView;
  }
  
  @NonNull
  public String getTitle()
  {
    return "Using Edit Mode";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Preview/OptimizelyEditModeInfoView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */