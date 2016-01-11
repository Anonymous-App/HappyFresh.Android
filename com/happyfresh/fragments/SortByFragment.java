package com.happyfresh.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.appsee.Appsee;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.utils.GAUtils;
import java.util.HashMap;
import java.util.Map;

public class SortByFragment
  extends BaseFragment
{
  private static final String TAG = SortByFragment.class.getSimpleName();
  @InjectView(2131558880)
  RadioButton sortByAToZ;
  @InjectView(2131558879)
  RadioButton sortByHighestPrice;
  @InjectView(2131558878)
  RadioButton sortByLowestPrice;
  @InjectView(2131558875)
  RadioGroup sortByOptions;
  @InjectView(2131558877)
  RadioButton sortByPopularity;
  @InjectView(2131558881)
  RadioButton sortByZToA;
  
  private void trackSortByOption()
  {
    int i = getApplication().getSortByCriteria();
    String str = "Popularity";
    switch (i)
    {
    }
    for (;;)
    {
      GAUtils.trackSortByOption(getActivity(), str);
      new HashMap().put("Option", str);
      Appsee.addEvent("change sort by option in Subcategory");
      return;
      str = "Highest Price";
      continue;
      str = "Lowest Price";
      continue;
      str = "A - Z";
      continue;
      str = "Z - A";
    }
  }
  
  private void updateView()
  {
    this.sortByOptions.clearCheck();
    switch (getApplication().getSortByCriteria())
    {
    default: 
      return;
    case 0: 
      this.sortByPopularity.setChecked(true);
      return;
    case 1: 
      this.sortByLowestPrice.setChecked(true);
      return;
    case 2: 
      this.sortByHighestPrice.setChecked(true);
      return;
    case 3: 
      this.sortByAToZ.setChecked(true);
      return;
    }
    this.sortByZToA.setChecked(true);
  }
  
  protected String getScreenName()
  {
    return "Sorting";
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    updateView();
  }
  
  @OnClick({2131558880})
  void onByAToZCheckedChanged()
  {
    if (this.sortByAToZ.isChecked())
    {
      getApplication().setSortByCriteria(3);
      trackSortByOption();
      getActivity().finish();
    }
  }
  
  @OnClick({2131558879})
  void onByHighestPriceCheckedChanged()
  {
    if (this.sortByHighestPrice.isChecked())
    {
      getApplication().setSortByCriteria(2);
      trackSortByOption();
      getActivity().finish();
    }
  }
  
  @OnClick({2131558878})
  void onByLowestPriceCheckedChanged()
  {
    if (this.sortByLowestPrice.isChecked())
    {
      getApplication().setSortByCriteria(1);
      trackSortByOption();
      getActivity().finish();
    }
  }
  
  @OnClick({2131558877})
  void onByPopularityCheckedChanged()
  {
    if (this.sortByPopularity.isChecked())
    {
      getApplication().setSortByCriteria(0);
      trackSortByOption();
      getActivity().finish();
    }
  }
  
  @OnClick({2131558881})
  void onByZToACheckedChanged()
  {
    if (this.sortByZToA.isChecked())
    {
      getApplication().setSortByCriteria(4);
      trackSortByOption();
      getActivity().finish();
    }
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903154, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/SortByFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */