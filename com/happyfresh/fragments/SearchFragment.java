package com.happyfresh.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happyfresh.customs.CircularProgressBar;

public abstract class SearchFragment
  extends BaseFragment
{
  @InjectView(2131558715)
  RecyclerView mList;
  @InjectView(2131558694)
  CircularProgressBar mProgressBar;
  @InjectView(2131558837)
  View mRecentLocation;
  @InjectView(2131558839)
  TextView mRecentSearchLocationText;
  @InjectView(2131558833)
  RelativeLayout mSearchingLocation;
  @InjectView(2131558836)
  TextView mSearchingLocationQuery;
  @InjectView(2131558835)
  TextView mSearchingLocationText;
  
  protected String getScreenName()
  {
    return "Search Products";
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903146, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    setHasOptionsMenu(true);
    this.mProgressBar.setVisibility(4);
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/SearchFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */