package com.happyfresh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartNotification;
import com.happyfresh.common.ICartNotification.Type;
import com.happyfresh.fragments.ViewRecommendedListFragment;
import com.happyfresh.interfaces.Listener;
import com.happyfresh.models.Order;

public class ViewRecommendedListActivity
  extends BaseActivity
{
  private Listener mCurrentOrderSetListener = new Listener()
  {
    public void onHappened(Object paramAnonymousObject, ICartNotification.Type paramAnonymousType, Order paramAnonymousOrder)
    {
      ViewRecommendedListActivity.this.invalidateOptionsMenu();
    }
  };
  private Long mShoppingListId;
  private String mShoppingListName;
  
  private void setTitle(String paramString)
  {
    ActionBar localActionBar = getSupportActionBar();
    if (localActionBar != null)
    {
      localActionBar.setDefaultDisplayHomeAsUpEnabled(false);
      localActionBar.setDisplayHomeAsUpEnabled(true);
      localActionBar.setTitle(paramString);
    }
  }
  
  protected Fragment createFragment()
  {
    this.mShoppingListId = Long.valueOf(getIntent().getLongExtra("ICartConstant.KEYS.EXTRAS.SHOPPING_LIST_ID", 0L));
    this.mShoppingListName = getIntent().getStringExtra("ICartConstant.KEYS.EXTRAS.SHOPPING_LIST_NAME");
    ViewRecommendedListFragment localViewRecommendedListFragment = new ViewRecommendedListFragment();
    Bundle localBundle = new Bundle();
    localBundle.putLong("ICartConstant.KEYS.EXTRAS.SHOPPING_LIST_ID", this.mShoppingListId.longValue());
    localViewRecommendedListFragment.setArguments(localBundle);
    return localViewRecommendedListFragment;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mICartApplication.getICartNotification().addCurrentOrderSetListener(this.mCurrentOrderSetListener);
    setTitle(this.mShoppingListName);
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131623938, paramMenu);
    paramMenu.findItem(2131559111).setVisible(false);
    return true;
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    this.mICartApplication.getICartNotification().removeCurrentOrderSetListener(this.mCurrentOrderSetListener);
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
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


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/ViewRecommendedListActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */