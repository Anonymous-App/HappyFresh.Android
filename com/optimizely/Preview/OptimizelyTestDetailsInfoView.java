package com.optimizely.Preview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import com.optimizely.Build;
import com.optimizely.Core.OptimizelyData;
import com.optimizely.Core.OptimizelyUtils;
import com.optimizely.JSON.OptimizelyCodeTest;
import com.optimizely.JSON.OptimizelyExperiment;
import com.optimizely.JSON.OptimizelyVariable;
import com.optimizely.JSON.OptimizelyVariation;
import com.optimizely.Optimizely;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class OptimizelyTestDetailsInfoView
  extends OverlayContentView
{
  @NonNull
  private final ScrollView rootView;
  
  public OptimizelyTestDetailsInfoView(@NonNull Context paramContext, @NonNull OverlayNavigationViewPager paramOverlayNavigationViewPager, @NonNull Optimizely paramOptimizely)
  {
    super(paramContext, paramOverlayNavigationViewPager);
    this.rootView = new ScrollView(paramContext);
    LinearLayout localLinearLayout = new LinearLayout(paramContext);
    localLinearLayout.setPadding(40, 54, 40, 40);
    localLinearLayout.setOrientation(1);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -2);
    Object localObject5 = paramOptimizely.getPreviewManager().getHumanReadableExperimentsToVariations();
    paramOverlayNavigationViewPager = makeContentTextView("APP DETAILS", true);
    Object localObject1 = makeContentTextView(String.format("%s - %s\nOptimizely SDK Version %s", new Object[] { OptimizelyUtils.applicationName(paramContext), OptimizelyUtils.applicationVersion(paramOptimizely), Build.sdkVersion() }), false);
    Object localObject2 = makeContentTextView("PROJECT DETAILS", true);
    Object localObject3 = makeContentTextView(String.format("Data File Version: %.0f", new Object[] { paramOptimizely.getOptimizelyData().getCodeRevision() }), false);
    Object localObject4 = makeContentTextView("EXPERIMENT", true);
    if (!((HashMap)localObject5).isEmpty()) {}
    for (paramContext = (Map.Entry)((HashMap)localObject5).entrySet().iterator().next();; paramContext = new AbstractMap.SimpleEntry("Unknown", "Unknown"))
    {
      localObject5 = makeContentTextView((String)paramContext.getKey(), false);
      TextView localTextView = makeContentTextView("VARIATION", true);
      paramContext = makeContentTextView((String)paramContext.getValue(), false);
      localLinearLayout.addView(paramOverlayNavigationViewPager, localLayoutParams);
      localLinearLayout.addView((View)localObject1, localLayoutParams);
      localLinearLayout.addView((View)localObject2, localLayoutParams);
      localLinearLayout.addView((View)localObject3, localLayoutParams);
      localLinearLayout.addView((View)localObject4, localLayoutParams);
      localLinearLayout.addView((View)localObject5, localLayoutParams);
      localLinearLayout.addView(localTextView, localLayoutParams);
      localLinearLayout.addView(paramContext, localLayoutParams);
      if (paramOptimizely.getOptimizelyData().getRunningExperimentsById().values().iterator().hasNext()) {
        break;
      }
      this.rootView.addView(localLinearLayout);
      return;
    }
    paramContext = ((OptimizelyExperiment)paramOptimizely.getOptimizelyData().getRunningExperimentsById().values().iterator().next()).getActiveVariation();
    paramOptimizely = makeContentTextView("CODE BLOCKS", true);
    paramOverlayNavigationViewPager = new StringBuilder();
    if ((paramContext != null) && (paramContext.getCodeTests() != null))
    {
      localObject1 = paramContext.getCodeTests().iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (OptimizelyCodeTest)((Iterator)localObject1).next();
        paramOverlayNavigationViewPager.append(((OptimizelyCodeTest)localObject2).getBlockName());
        paramOverlayNavigationViewPager.append(": ");
        paramOverlayNavigationViewPager.append(((OptimizelyCodeTest)localObject2).getBlockKey());
        paramOverlayNavigationViewPager.append("\n");
      }
    }
    localObject1 = makeContentTextView(paramOverlayNavigationViewPager.toString(), false);
    localObject2 = makeContentTextView("LIVE VARIABLES", true);
    localObject3 = new StringBuilder();
    if ((paramContext != null) && (paramContext.getVariables() != null))
    {
      localObject4 = paramContext.getVariables().iterator();
      while (((Iterator)localObject4).hasNext())
      {
        paramContext = (OptimizelyVariable)((Iterator)localObject4).next();
        ((StringBuilder)localObject3).append(paramContext.getVariableKey());
        paramContext = paramContext.getValue();
        if (paramContext != null)
        {
          ((StringBuilder)localObject3).append(": ");
          paramOverlayNavigationViewPager = paramContext.toString();
          paramContext = paramOverlayNavigationViewPager;
          if (paramOverlayNavigationViewPager != null)
          {
            paramContext = paramOverlayNavigationViewPager;
            if (paramOverlayNavigationViewPager.length() > 15) {
              paramContext = paramOverlayNavigationViewPager.substring(0, 12) + "...";
            }
          }
          ((StringBuilder)localObject3).append(paramContext);
        }
        ((StringBuilder)localObject3).append("\n");
      }
    }
    paramContext = makeContentTextView(((StringBuilder)localObject3).toString(), false);
    localLinearLayout.addView(paramOptimizely, localLayoutParams);
    localLinearLayout.addView((View)localObject1, localLayoutParams);
    localLinearLayout.addView((View)localObject2, localLayoutParams);
    localLinearLayout.addView(paramContext, localLayoutParams);
    this.rootView.addView(localLinearLayout);
  }
  
  @NonNull
  public View getRootView()
  {
    return this.rootView;
  }
  
  @NonNull
  public String getTitle()
  {
    return "Test Details";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Preview/OptimizelyTestDetailsInfoView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */