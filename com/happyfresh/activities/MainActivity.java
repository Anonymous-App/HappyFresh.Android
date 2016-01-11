package com.happyfresh.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.fragments.MainNavigationFragment;
import com.happyfresh.fragments.TaxonFragment;
import com.happyfresh.managers.OrderManager;
import com.happyfresh.models.Order;
import com.happyfresh.models.OrderResponse;
import com.happyfresh.models.Taxon;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import java.util.List;

public class MainActivity
  extends BaseActivity
{
  private static final String TAG = MainActivity.class.getSimpleName();
  private final String MAIN_FRAGMENT_TAG = "MainActivity.MAIN_FRAGMENT";
  private Taxon mCurrentTaxon;
  private boolean mRecommendedSelected = false;
  FragmentManager.OnBackStackChangedListener onBackStackChanged = new FragmentManager.OnBackStackChangedListener()
  {
    public void onBackStackChanged()
    {
      Object localObject = MainActivity.this.getSupportFragmentManager().findFragmentById(2131558513);
      if (localObject.getClass().equals(TaxonFragment.class))
      {
        localObject = ((TaxonFragment)localObject).getTaxon();
        MainActivity.this.setToolbarTitle(MainActivity.this.getDisplayTitle((Taxon)localObject));
        return;
      }
      MainActivity.this.setToolbarTitle(MainActivity.this.getString(2131165284));
    }
  };
  
  private String getDisplayTitle(Taxon paramTaxon)
  {
    if (paramTaxon == null) {
      paramTaxon = null;
    }
    String str;
    do
    {
      return paramTaxon;
      str = paramTaxon.name;
      paramTaxon = str;
    } while (!str.contains("#{ALL}"));
    return getString(2131165279, new Object[] { str.replace("#{ALL}", "") });
  }
  
  private void searchProduct()
  {
    startActivity(new Intent(this, SearchProductActivity.class));
  }
  
  private void showRateLastOrder(Order paramOrder)
  {
    if (paramOrder != null)
    {
      Intent localIntent = new Intent(this, RateOurServiceActivity.class);
      localIntent.putExtra("ICartConstant.ORDER_NUMBER", paramOrder.number);
      localIntent.putExtra("ICartConstant.ORDER_PRICE", paramOrder.displayTotal);
      startActivityForResult(localIntent, 300);
    }
  }
  
  protected Fragment createFragment()
  {
    MainNavigationFragment localMainNavigationFragment = new MainNavigationFragment();
    Object localObject = getIntent().getExtras();
    if (localObject != null)
    {
      Bundle localBundle = new Bundle();
      String str1 = ((Bundle)localObject).getString("ICartConstant.EXTRAS.DEEP_LINKS.TYPE");
      String str2 = ((Bundle)localObject).getString("ICartConstant.EXTRAS.DEEP_LINKS.STOCK_LOCATION_ID");
      localObject = ((Bundle)localObject).getString("ICartConstant.EXTRAS.DEEP_LINKS.SUPPLIER_ID");
      localBundle.putString("ICartConstant.EXTRAS.DEEP_LINKS.TYPE", str1);
      localBundle.putString("ICartConstant.EXTRAS.DEEP_LINKS.STOCK_LOCATION_ID", str2);
      localBundle.putString("ICartConstant.EXTRAS.DEEP_LINKS.SUPPLIER_ID", (String)localObject);
      localMainNavigationFragment.setArguments(localBundle);
    }
    return localMainNavigationFragment;
  }
  
  public String getFragmentTag()
  {
    return "MainActivity.MAIN_FRAGMENT";
  }
  
  public boolean isRecommendedSelected()
  {
    return this.mRecommendedSelected;
  }
  
  public void onBackPressed()
  {
    Object localObject = getSupportFragmentManager().findFragmentById(2131558513);
    if ((localObject != null) && ((localObject instanceof MainNavigationFragment)) && (((MainNavigationFragment)localObject).onBackPressed())) {}
    do
    {
      return;
      if (getSupportFragmentManager().getBackStackEntryCount() <= 1)
      {
        super.onBackPressed();
        return;
      }
      getSupportFragmentManager().popBackStackImmediate();
      localObject = getSupportActionBar();
    } while (localObject == null);
    ((ActionBar)localObject).setDefaultDisplayHomeAsUpEnabled(true);
    ((ActionBar)localObject).setDisplayHomeAsUpEnabled(true);
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    if (paramBundle != null)
    {
      Long localLong = Long.valueOf(paramBundle.getLong("ICartConstant.KEYS.CURRENT_TAXON", -1L));
      if (localLong.longValue() > -1L) {
        this.mCurrentTaxon = Taxon.findByParent(localLong);
      }
    }
    requestLocationPermission();
    super.onCreate(paramBundle);
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131623938, paramMenu);
    return true;
  }
  
  protected void onDestroy()
  {
    ICartApplication.get(this).getMixpanel().flush();
    super.onDestroy();
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    case 2131559111: 
      searchProduct();
      return true;
    }
    openCart();
    return true;
  }
  
  protected void onPause()
  {
    getSupportFragmentManager().removeOnBackStackChangedListener(this.onBackStackChanged);
    super.onPause();
  }
  
  protected void onPostCreate(Bundle paramBundle)
  {
    super.onPostCreate(paramBundle);
    if (getSupportFragmentManager().findFragmentById(2131558513).getClass().equals(TaxonFragment.class))
    {
      invalidateOptionsMenu();
      if (this.mCurrentTaxon != null) {
        setToolbarTitle(getDisplayTitle(this.mCurrentTaxon));
      }
    }
  }
  
  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    initCartMenuItem(paramMenu.findItem(2131559112));
    return super.onPrepareOptionsMenu(paramMenu);
  }
  
  protected void onResume()
  {
    super.onResume();
    if (this.mICartApplication.isLoggedIn()) {
      this.mICartApplication.getOrderManager().getLastCompletedOrder(new ICartCallback(TAG)
      {
        public void onSuccess(OrderResponse paramAnonymousOrderResponse)
        {
          if (MainActivity.this == null) {}
          do
          {
            do
            {
              return;
            } while ((paramAnonymousOrderResponse.orders == null) || (paramAnonymousOrderResponse.orders.size() <= 0));
            paramAnonymousOrderResponse = (Order)paramAnonymousOrderResponse.orders.get(0);
          } while (paramAnonymousOrderResponse.rating != null);
          MainActivity.this.showRateLastOrder(paramAnonymousOrderResponse);
        }
      });
    }
    getSupportFragmentManager().addOnBackStackChangedListener(this.onBackStackChanged);
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mCurrentTaxon != null)
    {
      if (this.mCurrentTaxon.remoteId < 0L) {
        this.mCurrentTaxon = Taxon.findByParent(this.mCurrentTaxon.parentId);
      }
      paramBundle.putLong("ICartConstant.KEYS.CURRENT_TAXON", this.mCurrentTaxon.remoteId);
    }
  }
  
  public void openCart()
  {
    startActivity(new Intent(this, ShoppingCartActivity.class));
  }
  
  protected void setContentView()
  {
    setContentView(2130903068);
  }
  
  public void setRecommendedSelected(boolean paramBoolean)
  {
    this.mRecommendedSelected = paramBoolean;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/MainActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */