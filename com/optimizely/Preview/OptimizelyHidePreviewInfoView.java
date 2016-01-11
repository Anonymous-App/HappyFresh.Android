package com.optimizely.Preview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.optimizely.Optimizely;
import com.optimizely.utils.OptimizelyConstants;

public class OptimizelyHidePreviewInfoView
  extends OverlayContentView
{
  @NonNull
  private final LinearLayout rootView;
  
  public OptimizelyHidePreviewInfoView(@NonNull Context paramContext, @NonNull OverlayNavigationViewPager paramOverlayNavigationViewPager, @NonNull final Optimizely paramOptimizely)
  {
    super(paramContext, paramOverlayNavigationViewPager);
    this.rootView = new LinearLayout(paramContext);
    this.rootView.setPadding(40, 54, 40, 40);
    this.rootView.setOrientation(1);
    paramOverlayNavigationViewPager = new LinearLayout.LayoutParams(-1, -2);
    TextView localTextView = makeContentTextView("To retrieve the Preview menu icon, draw the Optimizely \"O\" or restart the app.", true);
    localTextView.setGravity(1);
    paramContext = new Button(paramContext);
    paramContext.setBackgroundColor(OptimizelyConstants.OPT_BRIGHT_BLUE);
    paramContext.setText("Hide Preview Icon");
    paramContext.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        PreviewFloatingActionButton.hide(paramOptimizely.getForegroundActivity());
      }
    });
    this.rootView.addView(paramContext, paramOverlayNavigationViewPager);
    this.rootView.addView(localTextView, paramOverlayNavigationViewPager);
  }
  
  @NonNull
  public View getRootView()
  {
    return this.rootView;
  }
  
  @NonNull
  public String getTitle()
  {
    return "Hide Preview Button";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Preview/OptimizelyHidePreviewInfoView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */