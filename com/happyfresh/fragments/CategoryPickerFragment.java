package com.happyfresh.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happyfresh.activities.BaseActivity;
import com.happyfresh.models.Taxon;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CategoryPickerFragment
  extends BaseFragment
{
  private BaseActivity mActivity;
  private CategoryListAdapter mAdapter;
  private final Map<Long, List<Taxon>> mContent = new HashMap();
  @InjectView(2131558640)
  ExpandableListView mExpandedListView;
  private Taxon mFavoriteTaxon = new Taxon();
  private int mLastExpandedPosition = -1;
  private CategoryPickerListener mListener;
  private Taxon mSpecialTaxon = new Taxon();
  private final List<Taxon> mTmpHeaderTaxons = new ArrayList();
  
  public int dpToPx(int paramInt)
  {
    DisplayMetrics localDisplayMetrics = getActivity().getResources().getDisplayMetrics();
    return Math.round(paramInt * (localDisplayMetrics.xdpi / 160.0F));
  }
  
  protected String getScreenName()
  {
    return null;
  }
  
  public void initDataSubCat()
  {
    this.mTmpHeaderTaxons.clear();
    this.mFavoriteTaxon.name = getString(2131165382);
    this.mFavoriteTaxon.parentId = Long.valueOf(Long.MAX_VALUE);
    this.mFavoriteTaxon.levelOneId = Long.valueOf(Long.MAX_VALUE);
    this.mTmpHeaderTaxons.add(this.mFavoriteTaxon);
    this.mSpecialTaxon.name = getString(2131165588);
    this.mSpecialTaxon.parentId = Long.valueOf(Long.MAX_VALUE);
    this.mSpecialTaxon.levelOneId = Long.valueOf(Long.MAX_VALUE);
    this.mTmpHeaderTaxons.add(this.mSpecialTaxon);
    Iterator localIterator1 = Taxon.getTopLevel().iterator();
    while (localIterator1.hasNext())
    {
      Taxon localTaxon1 = (Taxon)localIterator1.next();
      List localList = Taxon.getSubLevel(localTaxon1.remoteId);
      int i = 0;
      Iterator localIterator2 = localList.iterator();
      while (localIterator2.hasNext())
      {
        Taxon localTaxon2 = (Taxon)localIterator2.next();
        if (localTaxon2.levelOneId != null) {
          i += localTaxon2.productsCount.intValue();
        }
      }
      if ((!localList.isEmpty()) && (i > 0))
      {
        this.mTmpHeaderTaxons.add(localTaxon1);
        this.mContent.put(Long.valueOf(localTaxon1.remoteId), localList);
      }
    }
    this.mAdapter.notifyDataSetChanged();
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mAdapter = new CategoryListAdapter(getActivity(), this.mTmpHeaderTaxons, this.mContent);
    this.mExpandedListView.setAdapter(this.mAdapter);
    initDataSubCat();
    paramBundle = new DisplayMetrics();
    getActivity().getWindowManager().getDefaultDisplay().getMetrics(paramBundle);
    int i = paramBundle.widthPixels;
    if (Build.VERSION.SDK_INT < 18) {
      this.mExpandedListView.setIndicatorBounds(i - dpToPx(40), 0);
    }
    for (;;)
    {
      this.mExpandedListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
      {
        public boolean onGroupClick(ExpandableListView paramAnonymousExpandableListView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
        {
          paramAnonymousExpandableListView = (Taxon)CategoryPickerFragment.this.mTmpHeaderTaxons.get(paramAnonymousInt);
          if (paramAnonymousExpandableListView.name.equalsIgnoreCase(CategoryPickerFragment.this.getString(2131165382))) {
            if (CategoryPickerFragment.this.mListener != null) {
              CategoryPickerFragment.this.mListener.onFavouritesClick(CategoryPickerFragment.this.mFavoriteTaxon);
            }
          }
          do
          {
            return true;
            if (!paramAnonymousExpandableListView.name.equalsIgnoreCase(CategoryPickerFragment.this.getString(2131165588))) {
              break;
            }
          } while (CategoryPickerFragment.this.mListener == null);
          CategoryPickerFragment.this.mListener.onSpecialClick(CategoryPickerFragment.this.mSpecialTaxon);
          return true;
          return false;
        }
      });
      this.mExpandedListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
      {
        public boolean onChildClick(ExpandableListView paramAnonymousExpandableListView, View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2, long paramAnonymousLong)
        {
          paramAnonymousExpandableListView = (Taxon)CategoryPickerFragment.this.mTmpHeaderTaxons.get(paramAnonymousInt1);
          paramAnonymousView = (Taxon)((List)CategoryPickerFragment.this.mContent.get(Long.valueOf(paramAnonymousExpandableListView.remoteId))).get(paramAnonymousInt2);
          if (paramAnonymousView.remoteId < 0L)
          {
            paramAnonymousView.remoteId *= -1L;
            paramAnonymousView.parentId = Long.valueOf(1L);
          }
          if (CategoryPickerFragment.this.mListener != null) {
            CategoryPickerFragment.this.mListener.onSubCategoryClick(paramAnonymousExpandableListView, paramAnonymousView);
          }
          return false;
        }
      });
      return;
      this.mExpandedListView.setIndicatorBoundsRelative(i - dpToPx(40), 0);
    }
  }
  
  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mActivity = ((BaseActivity)paramActivity);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903111, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    setHasOptionsMenu(true);
    return paramLayoutInflater;
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    ((BaseActivity)getActivity()).disableUpNavigation();
  }
  
  public void onResume()
  {
    super.onResume();
  }
  
  public void setListener(CategoryPickerListener paramCategoryPickerListener)
  {
    this.mListener = paramCategoryPickerListener;
  }
  
  private class CategoryListAdapter
    extends BaseExpandableListAdapter
  {
    private Map<Long, List<Taxon>> mContent;
    private Context mContext;
    private List<Taxon> mHeader;
    
    public CategoryListAdapter(List<Taxon> paramList, Map<Long, List<Taxon>> paramMap)
    {
      this.mContext = paramList;
      this.mHeader = paramMap;
      Map localMap;
      this.mContent = localMap;
    }
    
    public Taxon getChild(int paramInt1, int paramInt2)
    {
      long l = getGroupId(paramInt1);
      return (Taxon)((List)this.mContent.get(Long.valueOf(l))).get(paramInt2);
    }
    
    public long getChildId(int paramInt1, int paramInt2)
    {
      return getChild(paramInt1, paramInt2).remoteId;
    }
    
    public View getChildView(int paramInt1, int paramInt2, boolean paramBoolean, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null)
      {
        paramView = LayoutInflater.from(this.mContext).inflate(2130903169, null);
        paramViewGroup = new CategoryPickerFragment.ItemViewHolder(paramView);
        paramView.setTag(paramViewGroup);
      }
      for (;;)
      {
        String str2 = getChild(paramInt1, paramInt2).name;
        String str1 = str2;
        if (str2.contains("#{ALL}"))
        {
          str1 = str2.replace("#{ALL}", "");
          str1 = this.mContext.getString(2131165279, new Object[] { str1 });
        }
        paramViewGroup.categoryName.setText(str1);
        return paramView;
        paramViewGroup = (CategoryPickerFragment.ItemViewHolder)paramView.getTag();
      }
    }
    
    public int getChildrenCount(int paramInt)
    {
      long l = getGroupId(paramInt);
      List localList = (List)this.mContent.get(Long.valueOf(l));
      if (localList != null) {
        return localList.size();
      }
      return 0;
    }
    
    public Taxon getGroup(int paramInt)
    {
      return (Taxon)this.mHeader.get(paramInt);
    }
    
    public int getGroupCount()
    {
      return this.mHeader.size();
    }
    
    public long getGroupId(int paramInt)
    {
      return getGroup(paramInt).remoteId;
    }
    
    public View getGroupView(int paramInt, boolean paramBoolean, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null)
      {
        paramView = LayoutInflater.from(this.mContext).inflate(2130903168, null);
        paramViewGroup = new CategoryPickerFragment.GroupViewHolder(paramView);
        paramView.setTag(paramViewGroup);
      }
      Taxon localTaxon;
      for (;;)
      {
        localTaxon = getGroup(paramInt);
        paramViewGroup.categoryName.setText(localTaxon.name);
        if (!localTaxon.name.equalsIgnoreCase(CategoryPickerFragment.this.getString(2131165382))) {
          break;
        }
        Picasso.with(this.mContext).load(2130837767).into(paramViewGroup.categoryIcon);
        return paramView;
        paramViewGroup = (CategoryPickerFragment.GroupViewHolder)paramView.getTag();
      }
      if (localTaxon.name.equalsIgnoreCase(CategoryPickerFragment.this.getString(2131165588)))
      {
        Picasso.with(this.mContext).load(2130837790).into(paramViewGroup.categoryIcon);
        return paramView;
      }
      Picasso.with(this.mContext).load(localTaxon.iconUrl).into(paramViewGroup.categoryIcon);
      return paramView;
    }
    
    public boolean hasStableIds()
    {
      return false;
    }
    
    public boolean isChildSelectable(int paramInt1, int paramInt2)
    {
      return true;
    }
    
    public void onGroupExpanded(final int paramInt)
    {
      if ((CategoryPickerFragment.this.mLastExpandedPosition != -1) && (paramInt != CategoryPickerFragment.this.mLastExpandedPosition)) {
        CategoryPickerFragment.this.mExpandedListView.collapseGroup(CategoryPickerFragment.this.mLastExpandedPosition);
      }
      new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
      {
        public void run()
        {
          CategoryPickerFragment.this.mExpandedListView.setSelectionFromTop(paramInt, 0);
        }
      }, 300L);
      CategoryPickerFragment.access$502(CategoryPickerFragment.this, paramInt);
    }
  }
  
  public static abstract interface CategoryPickerListener
  {
    public abstract void onFavouritesClick(Taxon paramTaxon);
    
    public abstract void onSpecialClick(Taxon paramTaxon);
    
    public abstract void onSubCategoryClick(Taxon paramTaxon1, Taxon paramTaxon2);
  }
  
  static class GroupViewHolder
  {
    @InjectView(2131558941)
    ImageView categoryIcon;
    @InjectView(2131558524)
    TextView categoryName;
    
    public GroupViewHolder(View paramView)
    {
      ButterKnife.inject(this, paramView);
    }
  }
  
  static class ItemViewHolder
  {
    @InjectView(2131558524)
    TextView categoryName;
    
    public ItemViewHolder(View paramView)
    {
      ButterKnife.inject(this, paramView);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/CategoryPickerFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */