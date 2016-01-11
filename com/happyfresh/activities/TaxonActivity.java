package com.happyfresh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.happyfresh.fragments.TaxonFragment;
import com.happyfresh.models.Taxon;

public class TaxonActivity
  extends BaseActivity
{
  public static final String IS_WITH_CAT_BAR = "TaxonActivity.KEYS.IS_WITH_CAT_BAR";
  
  private void searchProduct()
  {
    startActivity(new Intent(this, SearchProductActivity.class));
  }
  
  protected Fragment createFragment()
  {
    TaxonFragment localTaxonFragment = new TaxonFragment();
    Taxon localTaxon = (Taxon)getIntent().getParcelableExtra("ICartConstant.KEYS.TAXON");
    if (localTaxon != null)
    {
      Bundle localBundle = new Bundle();
      localBundle.putParcelable("ICartConstant.KEYS.TAXON", localTaxon);
      localBundle.putBoolean("TaxonActivity.KEYS.IS_WITH_CAT_BAR", true);
      localTaxonFragment.setArguments(localBundle);
    }
    return localTaxonFragment;
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131623938, paramMenu);
    return true;
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
  
  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    initCartMenuItem(paramMenu.findItem(2131559112));
    return super.onPrepareOptionsMenu(paramMenu);
  }
  
  public void openCart()
  {
    startActivity(new Intent(this, ShoppingCartActivity.class));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/TaxonActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */