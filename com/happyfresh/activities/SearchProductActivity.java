package com.happyfresh.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import com.happyfresh.fragments.SearchProductFragment;
import com.happyfresh.models.Taxon;

public class SearchProductActivity
  extends SearchActivity
{
  SearchProductFragment mSearchFragment;
  
  protected Fragment createFragment()
  {
    if (this.mSearchFragment == null) {
      this.mSearchFragment = new SearchProductFragment();
    }
    return this.mSearchFragment;
  }
  
  public void openTaxonFragment(Taxon paramTaxon)
  {
    Intent localIntent = new Intent(this, TaxonActivity.class);
    localIntent.putExtra("ICartConstant.KEYS.TAXON", paramTaxon);
    startActivity(localIntent);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/SearchProductActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */