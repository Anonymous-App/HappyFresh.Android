package com.happyfresh.activities;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.fragments.IntroductionFragment;
import com.happyfresh.fragments.SingleIntroductionFragment;
import com.happyfresh.listeners.LocationRetrievedListener;
import com.optimizely.CodeBlocks.CodeBranch;
import com.optimizely.CodeBlocks.DefaultCodeBranch;
import com.optimizely.CodeBlocks.OptimizelyCodeBlock;
import com.optimizely.CodeBlocks.OptimizelyCodeBlocks.OptimizelyCodeBlockBuilder;
import com.optimizely.Optimizely;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class IntroductionActivity
  extends LocationActivity
{
  private static OptimizelyCodeBlock introFlow = Optimizely.codeBlock("IntroductionFlow").withBranchNames(new String[] { "SinglePageIntroduction" });
  private boolean isSingleIntroductionPage = false;
  private String mAndroidAdsId;
  private String mAreaName;
  private String mCityName;
  private String mZipcode;
  
  private void getAddressByLastKnownLocation()
  {
    Address localAddress = ((ICartApplication)getApplication()).getAddressByLastKnownLocation();
    if (localAddress != null)
    {
      this.mZipcode = localAddress.getPostalCode();
      this.mAreaName = localAddress.getSubLocality();
      this.mCityName = localAddress.getSubAdminArea();
    }
    if (this.mAndroidAdsId != null) {
      this.mICartApplication.sendUserData(this.mAndroidAdsId, this.mCityName, this.mAreaName, this.mZipcode, true);
    }
  }
  
  protected Fragment createFragment()
  {
    introFlow.execute(new DefaultCodeBranch()new CodeBranch
    {
      public void execute()
      {
        IntroductionActivity.access$002(IntroductionActivity.this, false);
      }
    }, new CodeBranch[] { new CodeBranch()
    {
      public void execute()
      {
        IntroductionActivity.access$002(IntroductionActivity.this, true);
      }
    } });
    Object localObject = new IntroductionFragment();
    if (this.isSingleIntroductionPage) {
      localObject = new SingleIntroductionFragment();
    }
    return (Fragment)localObject;
  }
  
  public void getUserData()
  {
    LocationManager localLocationManager = (LocationManager)getSystemService("location");
    if ((!localLocationManager.isProviderEnabled("gps")) && (!localLocationManager.isProviderEnabled("network")))
    {
      this.mICartApplication.getAndroidAdsId(new ICartCallback(TAG)
      {
        public void onSuccess(String paramAnonymousString)
        {
          IntroductionActivity.access$102(IntroductionActivity.this, paramAnonymousString);
          if ((!TextUtils.isEmpty(IntroductionActivity.this.mCityName)) || (!TextUtils.isEmpty(IntroductionActivity.this.mAreaName)) || (!TextUtils.isEmpty(IntroductionActivity.this.mZipcode))) {
            IntroductionActivity.this.mICartApplication.sendUserData(IntroductionActivity.this.mAndroidAdsId, IntroductionActivity.this.mCityName, IntroductionActivity.this.mAreaName, IntroductionActivity.this.mZipcode, true);
          }
        }
      });
      getAddressByLastKnownLocation();
    }
    do
    {
      return;
      requestLocation(new LocationRetrievedListener()
      {
        public void onLocationNotRetrieved()
        {
          if (this == null) {
            return;
          }
          IntroductionActivity.this.getAddressByLastKnownLocation();
        }
        
        public void onLocationRetrieved(Location paramAnonymousLocation)
        {
          double d1 = paramAnonymousLocation.getLatitude();
          double d2 = paramAnonymousLocation.getLongitude();
          try
          {
            paramAnonymousLocation = ((ICartApplication)IntroductionActivity.this.getApplication()).getCurrentLocale();
            paramAnonymousLocation = new Geocoder(IntroductionActivity.this.getApplication(), paramAnonymousLocation).getFromLocation(Double.valueOf(d1).doubleValue(), Double.valueOf(d2).doubleValue(), 100).iterator();
            while (paramAnonymousLocation.hasNext())
            {
              Address localAddress = (Address)paramAnonymousLocation.next();
              if (!TextUtils.isEmpty(localAddress.getSubAdminArea()))
              {
                IntroductionActivity.access$402(IntroductionActivity.this, localAddress.getPostalCode());
                IntroductionActivity.access$302(IntroductionActivity.this, localAddress.getSubLocality());
                IntroductionActivity.access$202(IntroductionActivity.this, localAddress.getSubAdminArea());
                if (IntroductionActivity.this.mAndroidAdsId != null) {
                  IntroductionActivity.this.mICartApplication.sendUserData(IntroductionActivity.this.mAndroidAdsId, IntroductionActivity.this.mCityName, IntroductionActivity.this.mAreaName, IntroductionActivity.this.mZipcode, true);
                }
              }
            }
            return;
          }
          catch (IOException paramAnonymousLocation)
          {
            paramAnonymousLocation.printStackTrace();
          }
        }
      });
    } while (this.mAndroidAdsId != null);
    this.mICartApplication.getAndroidAdsId(new ICartCallback(TAG)
    {
      public void onSuccess(String paramAnonymousString)
      {
        IntroductionActivity.access$102(IntroductionActivity.this, paramAnonymousString);
        if ((!TextUtils.isEmpty(IntroductionActivity.this.mCityName)) || (!TextUtils.isEmpty(IntroductionActivity.this.mAreaName)) || (!TextUtils.isEmpty(IntroductionActivity.this.mZipcode))) {
          IntroductionActivity.this.mICartApplication.sendUserData(IntroductionActivity.this.mAndroidAdsId, IntroductionActivity.this.mCityName, IntroductionActivity.this.mAreaName, IntroductionActivity.this.mZipcode, true);
        }
      }
    });
  }
  
  public void onConnectionSuspended(int paramInt) {}
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setToolbarTitle(null);
    requestLocationPermission();
  }
  
  protected void setContentView()
  {
    setContentView(2130903066);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/IntroductionActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */