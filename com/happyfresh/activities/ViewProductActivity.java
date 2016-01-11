package com.happyfresh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import com.happyfresh.fragments.ViewProductFragment;
import com.happyfresh.models.Product;

public class ViewProductActivity
  extends BaseActivity
{
  protected Fragment createFragment()
  {
    Object localObject = (Product)getIntent().getParcelableExtra("ICartConstant.KEYS.EXTRAS.PRODUCT");
    Long localLong = Long.valueOf(getIntent().getLongExtra("ICartConstant.SEARCH_ID", 0L));
    boolean bool1 = getIntent().getBooleanExtra("ICartConstant.KEYS.EXTRAS.FROM_ORDER_HISTORY", false);
    boolean bool2 = getIntent().getBooleanExtra("ICartConstant.KEYS.EXTRAS.FROM_FAVOURITES_LIST", false);
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("ICartConstant.KEYS.EXTRAS.PRODUCT", (Parcelable)localObject);
    localBundle.putBoolean("ICartConstant.KEYS.EXTRAS.FROM_ORDER_HISTORY", bool1);
    localBundle.putBoolean("ICartConstant.KEYS.EXTRAS.FROM_FAVOURITES_LIST", bool2);
    if (localLong.longValue() != 0L) {
      localBundle.putLong("ICartConstant.SEARCH_ID", localLong.longValue());
    }
    localObject = new ViewProductFragment();
    ((Fragment)localObject).setArguments(localBundle);
    return (Fragment)localObject;
  }
  
  public void onBackPressed()
  {
    if (getSupportFragmentManager().getBackStackEntryCount() <= 1)
    {
      finish();
      super.onBackPressed();
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    paramBundle = getSupportActionBar();
    if (paramBundle != null)
    {
      paramBundle.setDefaultDisplayHomeAsUpEnabled(true);
      paramBundle.setDisplayHomeAsUpEnabled(true);
      paramBundle.setTitle(null);
    }
  }
  
  protected void setContentView()
  {
    setContentView(2130903069);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/ViewProductActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */