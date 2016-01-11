package com.happyfresh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Toast;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartRestError;
import com.happyfresh.fragments.ShoppingCartFragment;
import com.happyfresh.managers.OrderManager;
import com.happyfresh.models.Order;
import com.happyfresh.utils.AccengageTrackerUtils;
import com.happyfresh.utils.AdjustUtils;
import com.happyfresh.utils.MixpanelTrackerUtils;
import java.util.List;

public class ShoppingCartActivity
  extends BaseActivity
{
  private static final String TAG = MainActivity.class.getSimpleName();
  private ShoppingCartFragment mShoppingCartFragment;
  
  private void gotoCheckOutActivity()
  {
    showButtonProgress(false);
    trackContinueCheckout();
    startActivity(new Intent(this, CheckoutActivity.class));
  }
  
  private void next()
  {
    Order localOrder = this.mICartApplication.currentOrder;
    this.mICartApplication.getOrderManager().next(localOrder.number, new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        if (ShoppingCartActivity.this == null) {}
        do
        {
          return;
          ShoppingCartActivity.this.showButtonProgress(false);
          paramAnonymousThrowable = ICartRestError.getErrorMessage(ShoppingCartActivity.this.getApplication(), paramAnonymousThrowable);
        } while (TextUtils.isEmpty(paramAnonymousThrowable));
        Toast.makeText(ShoppingCartActivity.this, paramAnonymousThrowable, 0).show();
      }
      
      public void onSuccess(Object paramAnonymousObject)
      {
        ShoppingCartActivity.this.gotoCheckOutActivity();
      }
    });
  }
  
  private void processCheckout()
  {
    Order localOrder = this.mICartApplication.currentOrder;
    if (localOrder != null)
    {
      if (localOrder.isCart()) {
        next();
      }
    }
    else {
      return;
    }
    gotoCheckOutActivity();
  }
  
  private void showButtonProgress(boolean paramBoolean)
  {
    if (this.mShoppingCartFragment != null) {
      this.mShoppingCartFragment.showButtonProgressBar(paramBoolean);
    }
  }
  
  private void trackContinueCheckout()
  {
    AdjustUtils.trackContinueCheckout(this.mICartApplication);
    MixpanelTrackerUtils.trackContinueCheckout(this.mICartApplication);
    AccengageTrackerUtils.trackContinueCheckout(this.mICartApplication);
  }
  
  public void attemptToCheckout()
  {
    if (this.mICartApplication.isLoggedIn())
    {
      processCheckout();
      return;
    }
    showButtonProgress(false);
    startActivityForResult(new Intent(this, SignUpActivity.class), 1);
  }
  
  protected Fragment createFragment()
  {
    this.mShoppingCartFragment = new ShoppingCartFragment();
    String str = getIntent().getStringExtra("ICartConstant.KEYS.EXTRAS.PREVIOUS_SCREEN");
    Bundle localBundle = new Bundle();
    localBundle.putString("ICartConstant.KEYS.EXTRAS.PREVIOUS_SCREEN", str);
    this.mShoppingCartFragment.setArguments(localBundle);
    return this.mShoppingCartFragment;
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt1 == 1) && (this.mICartApplication.isLoggedIn()))
    {
      showButtonProgress(true);
      processCheckout();
    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public void onBackPressed()
  {
    super.onBackPressed();
    this.mICartApplication.outOfStockItems.clear();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    paramBundle = getSupportActionBar();
    if (paramBundle != null)
    {
      paramBundle.setDefaultDisplayHomeAsUpEnabled(false);
      paramBundle.setDisplayHomeAsUpEnabled(false);
      paramBundle.setTitle(null);
    }
    this.mToolbar.setVisibility(8);
  }
  
  protected void setContentView()
  {
    setContentView(2130903069);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/ShoppingCartActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */