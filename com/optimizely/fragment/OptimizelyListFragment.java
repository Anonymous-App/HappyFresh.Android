package com.optimizely.fragment;

import android.annotation.TargetApi;
import android.app.ListFragment;
import com.optimizely.Optimizely;

@TargetApi(11)
public class OptimizelyListFragment
  extends ListFragment
  implements OptimizelyTargetable
{
  public String getViewId()
  {
    return null;
  }
  
  public void onResume()
  {
    super.onResume();
    Optimizely.trackViewGoal(FragmentUtil.getViewId(this));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/fragment/OptimizelyListFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */