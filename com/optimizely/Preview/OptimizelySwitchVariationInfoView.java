package com.optimizely.Preview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.optimizely.Core.OptimizelyData;
import com.optimizely.JSON.OptimizelyExperiment;
import com.optimizely.JSON.OptimizelyVariation;
import com.optimizely.Optimizely;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OptimizelySwitchVariationInfoView
  extends OverlayContentView
{
  @NonNull
  private final List<OptimizelyExperiment> experiments;
  private final AdapterView.OnItemClickListener mListener = new AdapterView.OnItemClickListener()
  {
    public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
    {
      int i = OptimizelySwitchVariationInfoView.this.pickers.indexOf(paramAnonymousAdapterView);
      if (i != -1)
      {
        paramAnonymousAdapterView = (OptimizelyExperiment)OptimizelySwitchVariationInfoView.this.experiments.get(i);
        if (paramAnonymousAdapterView != null)
        {
          paramAnonymousAdapterView = Collections.singletonMap(paramAnonymousAdapterView, (OptimizelyVariation)paramAnonymousAdapterView.getVariations().get(paramAnonymousInt));
          paramAnonymousView = OptimizelySwitchVariationInfoView.this.optimizely.getPreviewManager();
          paramAnonymousView.savePreviewData(paramAnonymousView.createPreviewData(paramAnonymousAdapterView));
          paramAnonymousView.restartInPreviewMode();
        }
      }
    }
  };
  @NonNull
  private final Optimizely optimizely;
  @NonNull
  private final List<ListView> pickers = new ArrayList();
  @NonNull
  private final LinearLayout rootView;
  
  public OptimizelySwitchVariationInfoView(@NonNull Context paramContext, @NonNull OverlayNavigationViewPager paramOverlayNavigationViewPager, @NonNull Optimizely paramOptimizely)
  {
    super(paramContext, paramOverlayNavigationViewPager);
    this.optimizely = paramOptimizely;
    this.rootView = new LinearLayout(paramContext);
    this.rootView.setOrientation(1);
    this.rootView.setPadding(40, 54, 40, 40);
    this.experiments = new ArrayList(paramOptimizely.getOptimizelyData().getRunningExperimentsById().values());
    paramOverlayNavigationViewPager = this.experiments.iterator();
    while (paramOverlayNavigationViewPager.hasNext())
    {
      paramOptimizely = (OptimizelyExperiment)paramOverlayNavigationViewPager.next();
      if (paramOptimizely != null)
      {
        Object localObject = new TextView(paramContext);
        ((TextView)localObject).setTextAppearance(paramContext, 16973892);
        ((TextView)localObject).setText(String.format("SELECT A VARIATION FOR \"%s\":", new Object[] { paramOptimizely.getDescription() }));
        ((TextView)localObject).setPadding(0, 0, 0, 24);
        this.rootView.addView((View)localObject);
        List localList = paramOptimizely.getVariations();
        localObject = new ArrayList(localList.size());
        int j = 0;
        int i = 0;
        while (i < localList.size())
        {
          OptimizelyVariation localOptimizelyVariation = (OptimizelyVariation)localList.get(i);
          ((List)localObject).add(localOptimizelyVariation.getDescription());
          if (localOptimizelyVariation.equals(paramOptimizely.getActiveVariation())) {
            j = i;
          }
          i += 1;
        }
        paramOptimizely = new ListView(paramContext);
        paramOptimizely.setAdapter(new ArrayAdapter(paramContext, 17367045, (List)localObject));
        paramOptimizely.setDividerHeight(2);
        paramOptimizely.setPadding(0, 0, 0, 54);
        paramOptimizely.setOnItemClickListener(this.mListener);
        paramOptimizely.setChoiceMode(1);
        paramOptimizely.setItemChecked(j, true);
        this.pickers.add(paramOptimizely);
        this.rootView.addView(paramOptimizely);
      }
    }
    paramContext = new TextView(paramContext);
    paramContext.setGravity(1);
    paramContext.setText("Switching variations will restart your app");
    this.rootView.addView(paramContext);
  }
  
  @NonNull
  public View getRootView()
  {
    return this.rootView;
  }
  
  @NonNull
  public String getTitle()
  {
    return "Switch Variations";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Preview/OptimizelySwitchVariationInfoView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */