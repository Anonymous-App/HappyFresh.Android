package com.happyfresh.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import butterknife.ButterKnife;
import com.activeandroid.ActiveAndroid;
import com.happyfresh.activities.ChooseLocationActivity;
import com.happyfresh.activities.IntroductionActivity;
import com.happyfresh.activities.MainActivity;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.managers.ConfigManager;
import com.happyfresh.managers.ProductManager;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.models.Country;
import com.happyfresh.models.Product;
import com.happyfresh.models.Taxon;
import com.happyfresh.models.Taxonomy;
import com.happyfresh.models.TaxonomyResponse;
import java.util.Iterator;
import java.util.List;

public class SplashFragment
  extends BaseFragment
{
  private static final int MAX_ATTEMPT = 3;
  private static final String TAG = SplashFragment.class.getSimpleName();
  private int attempt = 0;
  
  private void deleteProducts() {}
  
  private void deleteTaxonomiesAndTaxons()
  {
    Taxonomy.deleteAll();
    Taxon.deleteAll();
  }
  
  private void fetchCountries()
  {
    getApplication().getConfigManager().getCountries(new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        SplashFragment.this.getTaxonomies();
      }
      
      public void onSuccess(Object paramAnonymousObject)
      {
        SplashFragment.this.fetchStates();
      }
    });
  }
  
  private void fetchStates()
  {
    if (getApplication().getCountryCode() == null)
    {
      getTaxonomies();
      return;
    }
    Country localCountry = Country.findByCode(getApplication().getCountryCode());
    if (localCountry == null)
    {
      getTaxonomies();
      return;
    }
    getApplication().getConfigManager().getStates(localCountry.getRemoteId(), new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable) {}
      
      public void onSuccess(Object paramAnonymousObject)
      {
        SplashFragment.this.getTaxonomies();
      }
    });
  }
  
  private void getAppConfiguration()
  {
    getApplication().getConfigManager().getMobileConfiguration(new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        SplashFragment.access$308(SplashFragment.this);
        if (SplashFragment.this.attempt > 3) {
          return;
        }
        SplashFragment.this.getAppConfiguration();
      }
      
      public void onSuccess(Object paramAnonymousObject)
      {
        SplashFragment.this.deleteProducts();
        SplashFragment.this.deleteTaxonomiesAndTaxons();
        SplashFragment.this.fetchCountries();
      }
    });
  }
  
  private void getTaxonomies()
  {
    getApplication().getProductManager().getCategories(Integer.valueOf(1), Integer.valueOf(1000), new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        if (SplashFragment.this.getActivity() == null) {}
      }
      
      public void onSuccess(TaxonomyResponse paramAnonymousTaxonomyResponse)
      {
        paramAnonymousTaxonomyResponse = paramAnonymousTaxonomyResponse.taxonomies.iterator();
        for (;;)
        {
          if (paramAnonymousTaxonomyResponse.hasNext())
          {
            Taxonomy localTaxonomy = (Taxonomy)paramAnonymousTaxonomyResponse.next();
            if (!localTaxonomy.isCategory()) {
              continue;
            }
            localTaxonomy.save();
            SplashFragment.this.saveRootCategories(localTaxonomy.root.taxons);
            if (SplashFragment.this.getActivity() != null) {}
          }
          else
          {
            return;
          }
          SplashFragment.this.gotoNextActivity();
        }
      }
    });
  }
  
  private void gotoActivity(Class paramClass)
  {
    FragmentActivity localFragmentActivity = getActivity();
    paramClass = new Intent(localFragmentActivity, paramClass);
    paramClass.setFlags(268468224);
    localFragmentActivity.startActivity(paramClass);
  }
  
  private void gotoMainActivity()
  {
    long l = getApplication().getStockLocationId();
    Intent localIntent = new Intent(getActivity(), MainActivity.class);
    localIntent.setFlags(268468224);
    localIntent.putExtra("ICartConstant.KEYS.EXTRAS.STOCK_LOCATION_ID", Long.valueOf(l));
    if (getArguments() != null)
    {
      String str = getArguments().getString("ICartConstant.EXTRAS.DEEP_LINKS.TYPE");
      if (str != null) {
        localIntent.putExtra("ICartConstant.EXTRAS.DEEP_LINKS.TYPE", str);
      }
      str = getArguments().getString("ICartConstant.EXTRAS.DEEP_LINKS.STOCK_LOCATION_ID");
      if (str != null) {
        localIntent.putExtra("ICartConstant.EXTRAS.DEEP_LINKS.STOCK_LOCATION_ID", str);
      }
      str = getArguments().getString("ICartConstant.EXTRAS.DEEP_LINKS.SUPPLIER_ID");
      if (str != null) {
        localIntent.putExtra("ICartConstant.EXTRAS.DEEP_LINKS.SUPPLIER_ID", str);
      }
    }
    startActivity(localIntent);
  }
  
  private boolean hasSelectedLocation()
  {
    long l = getApplication().getStockLocationId();
    String str = ICartApplication.get(this).getSharedPreferencesManager().getStockLocationName();
    if (Long.valueOf(l).longValue() < 0L) {}
    while (TextUtils.isEmpty(str)) {
      return false;
    }
    return true;
  }
  
  protected String getScreenName()
  {
    return "Launch";
  }
  
  public void gotoNextActivity()
  {
    if (!getApplication().isLoggedIn())
    {
      gotoActivity(IntroductionActivity.class);
      return;
    }
    if (hasSelectedLocation())
    {
      gotoMainActivity();
      return;
    }
    if (getArguments() != null)
    {
      if ("popular".equalsIgnoreCase(getArguments().getString("ICartConstant.EXTRAS.DEEP_LINKS.TYPE")))
      {
        gotoMainActivity();
        return;
      }
      gotoActivity(ChooseLocationActivity.class);
      return;
    }
    gotoActivity(ChooseLocationActivity.class);
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    getAppConfiguration();
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 == 13)
    {
      if (paramInt2 != -1) {
        break label33;
      }
      if (getApplication().isLoggedIn()) {
        gotoNextActivity();
      }
    }
    for (;;)
    {
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
      return;
      label33:
      if (paramInt2 == 0) {
        getActivity().finish();
      }
    }
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903155, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
  
  public void saveRootCategories(List<Taxon> paramList)
  {
    ActiveAndroid.beginTransaction();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      Taxon localTaxon = (Taxon)paramList.next();
      if (!URLUtil.isNetworkUrl(localTaxon.iconUrl)) {
        localTaxon.iconUrl = null;
      }
      localTaxon.isRoot = Boolean.valueOf(true);
      localTaxon.save();
    }
    ActiveAndroid.setTransactionSuccessful();
    ActiveAndroid.endTransaction();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/SplashFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */