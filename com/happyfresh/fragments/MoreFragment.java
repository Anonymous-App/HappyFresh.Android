package com.happyfresh.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.happyfresh.activities.ChooseStoreActivity;
import com.happyfresh.activities.MainActivity;
import com.happyfresh.activities.MyAccountActivity;
import com.happyfresh.activities.OrderListActivity;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartNotification;
import com.happyfresh.common.ICartNotification.Type;
import com.happyfresh.interfaces.Listener;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.models.Coordinate;
import com.happyfresh.models.Order;

public class MoreFragment
  extends BaseFragment
{
  private Listener mCurrentOrderSetListener = new Listener()
  {
    public void onHappened(Object paramAnonymousObject, ICartNotification.Type paramAnonymousType, Order paramAnonymousOrder)
    {
      ((MainActivity)MoreFragment.this.getActivity()).invalidateOptionsMenu();
    }
  };
  
  protected String getScreenName()
  {
    return null;
  }
  
  @OnClick({2131558777})
  public void myAccountOnClick()
  {
    startActivity(new Intent(getActivity(), MyAccountActivity.class));
  }
  
  @OnClick({2131558776})
  public void myOrdersOnClick()
  {
    startActivity(new Intent(getActivity(), OrderListActivity.class));
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    getApplication().getICartNotification().addCurrentOrderSetListener(this.mCurrentOrderSetListener);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903138, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    return paramLayoutInflater;
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    getApplication().getICartNotification().removeCurrentOrderSetListener(this.mCurrentOrderSetListener);
  }
  
  @OnClick({2131558778})
  public void shopAtOnClick()
  {
    Object localObject = getApplication().getSharedPreferencesManager();
    FragmentActivity localFragmentActivity = getActivity();
    Intent localIntent = new Intent(localFragmentActivity, ChooseStoreActivity.class);
    localIntent.putExtra("ICartConstant.KEYS.EXTRAS.SUB_DISTRICT_ID", ((SharedPreferencesManager)localObject).getSubDistrictId());
    localIntent.putExtra("ICartConstant.KEYS.EXTRAS.SUB_DISTRICT_NAME", ((SharedPreferencesManager)localObject).getSubDistrictName());
    Coordinate localCoordinate = ((SharedPreferencesManager)localObject).getCoordinate();
    if (localCoordinate != null)
    {
      localIntent.putExtra("ICartConstant.KEYS.EXTRAS.LONGITUDE", localCoordinate.latitude);
      localIntent.putExtra("ICartConstant.KEYS.EXTRAS.LATITUDE", localCoordinate.longitude);
    }
    localObject = ((SharedPreferencesManager)localObject).getZipCode();
    if (localObject != null) {
      localIntent.putExtra("ICartConstant.KEYS.EXTRAS.ZIP_CODE", (String)localObject);
    }
    localFragmentActivity.startActivityForResult(localIntent, 12);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/MoreFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */