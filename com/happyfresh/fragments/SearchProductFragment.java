package com.happyfresh.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happyfresh.activities.SearchProductActivity;
import com.happyfresh.activities.ViewProductActivity;
import com.happyfresh.adapters.SearchProductGridAdapter;
import com.happyfresh.adapters.SuggestedSearchAdapter;
import com.happyfresh.adapters.SuggestedSearchAdapter.SuggestedListItemClickListener;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartNotification;
import com.happyfresh.common.ICartNotification.Type;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.interfaces.Listener;
import com.happyfresh.listeners.OnProductClickListener;
import com.happyfresh.loaders.ProductsLoader;
import com.happyfresh.managers.ProductManager;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.models.AutoSuggestionResponse;
import com.happyfresh.models.Order;
import com.happyfresh.models.PopularProduct;
import com.happyfresh.models.Product;
import com.happyfresh.models.ProductResponse;
import com.happyfresh.models.Taxon;
import java.util.ArrayList;
import java.util.List;
import org.solovyev.android.views.llm.LinearLayoutManager;

public class SearchProductFragment
  extends SearchFragment
  implements LoaderManager.LoaderCallbacks<List<PopularProduct>>
{
  private static final String TAG = SearchProductFragment.class.getSimpleName();
  @InjectView(2131558841)
  RecyclerView listSuggested;
  private Context mContext;
  private Listener mCurrentOrderSetListener = new Listener()
  {
    public void onHappened(Object paramAnonymousObject, ICartNotification.Type paramAnonymousType, Order paramAnonymousOrder)
    {
      SearchProductFragment.this.mPopularProductAdapter.notifyDataSetChanged();
    }
  };
  private SearchView.OnQueryTextListener mOnQueryTextListener = new SearchView.OnQueryTextListener()
  {
    public boolean onQueryTextChange(String paramAnonymousString)
    {
      return SearchProductFragment.this.checkQuery(paramAnonymousString);
    }
    
    public boolean onQueryTextSubmit(String paramAnonymousString)
    {
      return SearchProductFragment.this.submitQuery(paramAnonymousString);
    }
  };
  private SearchProductGridAdapter mPopularProductAdapter;
  private GridLayoutManager mProductLayoutManager;
  private List<Product> mProducts = new ArrayList();
  private Long mSearchId;
  private SuggestedSearchAdapter mSuggestedSearchAdapter;
  private AutoSubmitTimer mTimer;
  EditText searchText;
  SearchView searchView;
  @InjectView(2131558840)
  View transparentView;
  
  private void displayCategoryProducts()
  {
    this.mPopularProductAdapter.notifyDataSetChanged();
  }
  
  private void setVisibilitySuggestedSearchTermList(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.listSuggested.setVisibility(0);
      this.transparentView.setVisibility(0);
      this.mSearchingLocation.setVisibility(8);
      this.mRecentLocation.setVisibility(8);
      return;
    }
    this.listSuggested.setVisibility(8);
    this.transparentView.setVisibility(8);
    this.mSearchingLocation.setVisibility(0);
    this.mRecentLocation.setVisibility(0);
  }
  
  protected boolean checkQuery(String paramString)
  {
    this.mProgressBar.setVisibility(4);
    this.mTimer.cancel();
    if (paramString.length() >= 3)
    {
      this.mTimer.start();
      return false;
    }
    setVisibilitySuggestedSearchTermList(false);
    return false;
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mRecentSearchLocationText.setText(2131165520);
    this.mSearchingLocationQuery.setText(getApplication().getSharedPreferencesManager().getStockLocationName());
    this.mProductLayoutManager = new GridLayoutManager(getActivity(), 3);
    this.mList.setLayoutManager(this.mProductLayoutManager);
    this.mPopularProductAdapter = new SearchProductGridAdapter(getActivity());
    this.mList.setAdapter(this.mPopularProductAdapter);
    this.mProductLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
    {
      public int getSpanSize(int paramAnonymousInt)
      {
        return SearchProductFragment.this.mPopularProductAdapter.getSpanSize(paramAnonymousInt);
      }
    });
    this.mPopularProductAdapter.setOnItemClickListener(new OnProductClickListener()
    {
      public void onHeaderClick(View paramAnonymousView, Taxon paramAnonymousTaxon)
      {
        ((SearchProductActivity)SearchProductFragment.this.getActivity()).openTaxonFragment(paramAnonymousTaxon);
      }
      
      public void onItemClick(View paramAnonymousView, Product paramAnonymousProduct)
      {
        paramAnonymousView = new Intent(SearchProductFragment.this.getActivity(), ViewProductActivity.class);
        paramAnonymousView.putExtra("ICartConstant.KEYS.EXTRAS.PRODUCT", paramAnonymousProduct);
        paramAnonymousView.putExtra("ICartConstant.SEARCH_ID", SearchProductFragment.this.mSearchId);
        SearchProductFragment.this.startActivity(paramAnonymousView);
        SearchProductFragment.this.getActivity().overridePendingTransition(2130968599, 2130968594);
      }
      
      public void onViewMoreClick(View paramAnonymousView, Taxon paramAnonymousTaxon)
      {
        ((SearchProductActivity)SearchProductFragment.this.getActivity()).openTaxonFragment(paramAnonymousTaxon);
      }
    });
    this.mProgressBar.setVisibility(4);
    this.mTimer = new AutoSubmitTimer();
    paramBundle = new LinearLayoutManager(this.mContext);
    this.listSuggested.setLayoutManager(paramBundle);
    this.mSuggestedSearchAdapter = new SuggestedSearchAdapter(this.mContext);
    this.listSuggested.setAdapter(this.mSuggestedSearchAdapter);
    this.mSuggestedSearchAdapter.setOnSuggestedItemClickListener(new SuggestedSearchAdapter.SuggestedListItemClickListener()
    {
      public void onSuggestedArrowIconClick(String paramAnonymousString)
      {
        if (SearchProductFragment.this.searchView != null)
        {
          SearchProductFragment.this.searchView.setOnQueryTextListener(null);
          SearchProductFragment.this.searchView.setQuery(paramAnonymousString, false);
          SearchProductFragment.this.searchView.setOnQueryTextListener(SearchProductFragment.this.mOnQueryTextListener);
        }
      }
      
      public void onSuggestedListItemClick(String paramAnonymousString)
      {
        SearchProductFragment.this.mTimer.cancel();
        SearchProductFragment.this.searchView.setQuery(paramAnonymousString, true);
        SearchProductFragment.this.setVisibilitySuggestedSearchTermList(false);
        SearchProductFragment.this.mTimer.cancel();
      }
    });
  }
  
  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mContext = paramActivity.getApplicationContext();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    getApplication().getICartNotification().addCurrentOrderSetListener(this.mCurrentOrderSetListener);
  }
  
  public Loader<List<PopularProduct>> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    return new ProductsLoader(getActivity(), this.mProducts, null, true);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903147, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    setHasOptionsMenu(true);
    return paramLayoutInflater;
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    getApplication().getICartNotification().removeCurrentOrderSetListener(this.mCurrentOrderSetListener);
  }
  
  public void onLoadFinished(Loader<List<PopularProduct>> paramLoader, List<PopularProduct> paramList)
  {
    displayCategoryProducts();
    this.mPopularProductAdapter.addAll(paramList);
    this.mPopularProductAdapter.notifyDataSetChanged();
  }
  
  public void onLoaderReset(Loader<List<PopularProduct>> paramLoader) {}
  
  public void onPrepareOptionsMenu(Menu paramMenu)
  {
    super.onPrepareOptionsMenu(paramMenu);
    paramMenu = paramMenu.findItem(2131559114);
    if (paramMenu == null) {}
    do
    {
      return;
      MenuItemCompat.expandActionView(paramMenu);
      this.searchView = ((SearchView)MenuItemCompat.getActionView(paramMenu));
      this.searchView.setQueryHint(this.mContext.getString(2131165554));
      this.searchText = ((EditText)this.searchView.findViewById(2131558507));
      MenuItemCompat.setOnActionExpandListener(paramMenu, new MenuItemCompat.OnActionExpandListener()
      {
        public boolean onMenuItemActionCollapse(MenuItem paramAnonymousMenuItem)
        {
          SearchProductFragment.this.getActivity().finish();
          return false;
        }
        
        public boolean onMenuItemActionExpand(MenuItem paramAnonymousMenuItem)
        {
          return false;
        }
      });
    } while (this.searchView == null);
    this.searchView.setOnQueryTextListener(this.mOnQueryTextListener);
  }
  
  protected boolean submitQuery(String paramString)
  {
    this.mTimer.cancel();
    if (paramString.length() == 0)
    {
      this.mProgressBar.setVisibility(4);
      this.mList.setVisibility(8);
      this.mRecentLocation.setVisibility(0);
      return false;
    }
    this.mList.setVisibility(8);
    this.searchText = ((EditText)this.searchView.findViewById(2131558507));
    this.searchText.setCursorVisible(false);
    long l = getApplication().getStockLocationId();
    this.mSearchingLocationText.setText(this.mContext.getString(2131165556, new Object[] { paramString }));
    this.mProgressBar.setVisibility(0);
    getApplication().getProductManager().searchProduct(Long.valueOf(l), paramString, null, null, new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        if ((SearchProductFragment.this.getActivity() == null) || (!SearchProductFragment.this.isAdded())) {
          return;
        }
        SearchProductFragment.this.mProgressBar.setVisibility(4);
        SearchProductFragment.this.searchText.setCursorVisible(true);
        SearchProductFragment.this.mRecentLocation.setVisibility(0);
        SearchProductFragment.this.mList.setVisibility(8);
        SearchProductFragment.this.setVisibilitySuggestedSearchTermList(false);
      }
      
      public void onSuccess(ProductResponse paramAnonymousProductResponse)
      {
        if ((SearchProductFragment.this.getActivity() == null) || (!SearchProductFragment.this.isAdded())) {
          return;
        }
        SearchProductFragment.this.mProgressBar.setVisibility(4);
        SearchProductFragment.access$102(SearchProductFragment.this, Long.valueOf(paramAnonymousProductResponse.searchId));
        SearchProductFragment.this.searchText.setCursorVisible(true);
        paramAnonymousProductResponse = paramAnonymousProductResponse.products;
        SearchProductFragment.this.mSearchingLocationText.setText(SearchProductFragment.this.mContext.getString(2131165553, new Object[] { Integer.valueOf(paramAnonymousProductResponse.size()) }));
        if (!paramAnonymousProductResponse.isEmpty())
        {
          SearchProductFragment.this.mList.setVisibility(0);
          SearchProductFragment.this.mRecentLocation.setVisibility(8);
          SearchProductFragment.access$602(SearchProductFragment.this, paramAnonymousProductResponse);
          if (SearchProductFragment.this.getLoaderManager().getLoader(0) == null) {
            SearchProductFragment.this.getLoaderManager().initLoader(0, null, SearchProductFragment.this);
          }
        }
        for (;;)
        {
          SearchProductFragment.this.setVisibilitySuggestedSearchTermList(false);
          return;
          SearchProductFragment.this.getLoaderManager().restartLoader(0, null, SearchProductFragment.this);
          continue;
          SearchProductFragment.this.mList.setVisibility(8);
          SearchProductFragment.this.mRecentLocation.setVisibility(0);
        }
      }
    });
    return false;
  }
  
  private class AutoSubmitTimer
    extends CountDownTimer
  {
    public AutoSubmitTimer()
    {
      super(500L);
    }
    
    public void onFinish()
    {
      if (SearchProductFragment.this.getActivity() == null) {
        return;
      }
      final String str = SearchProductFragment.this.searchText.getText().toString().toLowerCase();
      SearchProductFragment.this.getApplication().getProductManager().getSearchSuggestions(str, new ICartCallback(SearchProductFragment.TAG)
      {
        public void onSuccess(AutoSuggestionResponse paramAnonymousAutoSuggestionResponse)
        {
          if ((SearchProductFragment.this.getActivity() == null) || (!SearchProductFragment.this.isAdded())) {
            return;
          }
          SearchProductFragment.this.mSuggestedSearchAdapter.setData(paramAnonymousAutoSuggestionResponse.keywords, str);
          SearchProductFragment.this.mSuggestedSearchAdapter.notifyDataSetChanged();
          SearchProductFragment.this.setVisibilitySuggestedSearchTermList(true);
        }
      });
    }
    
    public void onTick(long paramLong) {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/SearchProductFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */