package com.happyfresh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import com.happyfresh.fragments.FullImageFragment;
import java.util.ArrayList;

public class FullImageActivity
  extends BaseActivity
{
  protected Fragment createFragment()
  {
    Object localObject = getIntent().getParcelableArrayListExtra("ICartConstant.KEYS.EXTRAS.PRODUCT_IMAGES");
    int i = getIntent().getIntExtra("ICartConstant.KEYS.EXTRAS.PRODUCT_IMAGES_POSITION", 0);
    Bundle localBundle = new Bundle();
    localBundle.putParcelableArrayList("ICartConstant.KEYS.EXTRAS.PRODUCT_IMAGES", (ArrayList)localObject);
    localBundle.putInt("ICartConstant.KEYS.EXTRAS.PRODUCT_IMAGES_POSITION", i);
    localObject = new FullImageFragment();
    ((Fragment)localObject).setArguments(localBundle);
    return (Fragment)localObject;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mToolbar.setNavigationIcon(2130837585);
    paramBundle = getSupportActionBar();
    if (paramBundle != null) {
      paramBundle.setTitle(null);
    }
  }
  
  protected void setContentView()
  {
    setContentView(2130903069);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/FullImageActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */