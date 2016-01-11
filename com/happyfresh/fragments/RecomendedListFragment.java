package com.happyfresh.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.managers.ShoppingListManager;
import java.util.List;

public class RecomendedListFragment
  extends BaseFragment
{
  LinearLayoutManager layoutManager;
  @InjectView(2131558758)
  View mEmptyList;
  @InjectView(2131558694)
  CircularProgressBar mProgressBar;
  @InjectView(2131558760)
  TextView mTxtEmptyList;
  @InjectView(2131558715)
  RecyclerView recyclerList;
  
  private void fetchData()
  {
    getApplication().getShoppingListManager().getRecommendedList(new ICartCallback(getScreenName())
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
      }
      
      public void onSuccess(Object paramAnonymousObject)
      {
        if (RecomendedListFragment.this.getActivity() == null) {
          return;
        }
        if (((List)paramAnonymousObject).size() == 0)
        {
          RecomendedListFragment.this.mEmptyList.setVisibility(0);
          RecomendedListFragment.this.mTxtEmptyList.setText(RecomendedListFragment.this.getString(2131165472, new Object[] { RecomendedListFragment.this.getApplication().getSharedPreferencesManager().getStockLocationName() }));
          return;
        }
        RecomendedListFragment.this.mEmptyList.setVisibility(8);
      }
    });
  }
  
  protected String getScreenName()
  {
    return getClass().getSimpleName();
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.layoutManager = new LinearLayoutManager(getApplication());
    this.layoutManager.setOrientation(1);
    this.recyclerList.setLayoutManager(this.layoutManager);
    this.mProgressBar.setVisibility(4);
    fetchData();
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903134, null);
    ButterKnife.inject(this, paramLayoutInflater);
    setHasOptionsMenu(true);
    return paramLayoutInflater;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/RecomendedListFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */