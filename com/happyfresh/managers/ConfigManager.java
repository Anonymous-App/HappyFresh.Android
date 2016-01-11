package com.happyfresh.managers;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.Looper;
import com.activeandroid.ActiveAndroid;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.callbacks.ICartRetrofitCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartRestClient;
import com.happyfresh.models.AppConfig;
import com.happyfresh.models.ContactUsConfig;
import com.happyfresh.models.Country;
import com.happyfresh.models.CountryResponse;
import com.happyfresh.models.District;
import com.happyfresh.models.State;
import com.happyfresh.models.StateResponse;
import com.happyfresh.models.StateTimestamp;
import com.happyfresh.models.SubDistrict;
import com.happyfresh.models.SubDistrictResponse;
import com.happyfresh.models.UserConfig;
import com.happyfresh.services.ConfigService;
import io.fabric.sdk.android.services.concurrency.AsyncTask;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ConfigManager
  extends BaseManager
{
  private static final String TAG = ConfigManager.class.getSimpleName();
  private AsyncTask<Void, Void, Void> mCurrentTask = null;
  
  public ConfigManager(ICartApplication paramICartApplication)
  {
    super(paramICartApplication);
  }
  
  public void getContactUsConfiguration(final ICartCallback paramICartCallback)
  {
    this.mICartRestClient.getConfigService().getContactUsConfig(new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(ContactUsConfig paramAnonymousContactUsConfig, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousContactUsConfig);
        }
      }
    });
  }
  
  public void getCountries(final ICartCallback paramICartCallback)
  {
    this.mICartRestClient.getConfigService().getCountries(new Callback()
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(CountryResponse paramAnonymousCountryResponse, Response paramAnonymousResponse)
      {
        paramAnonymousCountryResponse = paramAnonymousCountryResponse.countries.iterator();
        while (paramAnonymousCountryResponse.hasNext()) {
          ((Country)paramAnonymousCountryResponse.next()).save();
        }
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(null);
        }
      }
    });
  }
  
  public void getMobileConfiguration(final ICartCallback paramICartCallback)
  {
    this.mICartRestClient.getConfigService().getConfig(new Callback()
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(AppConfig paramAnonymousAppConfig, Response paramAnonymousResponse)
      {
        AppConfig.deleteConfig();
        ConfigManager.this.mICartApplication.getSharedPreferencesManager().clearOnboardingStrings();
        paramAnonymousAppConfig.save();
        ConfigManager.this.mICartApplication.getSharedPreferencesManager().saveOnboardingStrings(paramAnonymousAppConfig.onboardingString);
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousAppConfig);
        }
      }
    });
  }
  
  public void getStates(final Long paramLong, final ICartCallback paramICartCallback)
  {
    this.mICartRestClient.getConfigService().getStates(paramLong, new Callback()
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(StateResponse paramAnonymousStateResponse, Response paramAnonymousResponse)
      {
        ConfigManager.this.saveStates(paramLong, paramAnonymousStateResponse.states, paramICartCallback);
      }
    });
  }
  
  public void getSubdistrictByStateId(final Long paramLong, final ICartCallback paramICartCallback)
  {
    Object localObject2 = null;
    final Object localObject3 = SubDistrict.findByState(paramLong);
    Object localObject1 = localObject2;
    if (localObject3 != null)
    {
      localObject1 = localObject2;
      if (((List)localObject3).size() > 0)
      {
        localObject3 = StateTimestamp.findByStateId(paramLong);
        localObject1 = localObject2;
        if (localObject3 != null) {
          localObject1 = ((StateTimestamp)localObject3).timestamps;
        }
      }
    }
    localObject3 = new HashMap();
    localObject2 = District.findAll().iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject4 = (District)((Iterator)localObject2).next();
      ((HashMap)localObject3).put(Long.valueOf(((District)localObject4).remoteId), localObject4);
    }
    Object localObject4 = this.mICartRestClient.getConfigService();
    localObject2 = null;
    try
    {
      int i = this.mICartApplication.getPackageManager().getPackageInfo(this.mICartApplication.getPackageName(), 0).versionCode;
      localObject2 = String.valueOf(i);
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      for (;;)
      {
        localNameNotFoundException.printStackTrace();
      }
    }
    ((ConfigService)localObject4).getSubdistrictByState(paramLong, (Date)localObject1, (String)localObject2, Integer.valueOf(100000), Integer.valueOf(1), new Callback()
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(final SubDistrictResponse paramAnonymousSubDistrictResponse, Response paramAnonymousResponse)
      {
        if ((paramAnonymousSubDistrictResponse != null) && (paramAnonymousSubDistrictResponse.subDistricts.size() > 0)) {
          new Thread(new Runnable()
          {
            public void run()
            {
              
              for (;;)
              {
                try
                {
                  localObject3 = paramAnonymousSubDistrictResponse.subDistricts.iterator();
                  if (!((Iterator)localObject3).hasNext()) {
                    break;
                  }
                  SubDistrict localSubDistrict = (SubDistrict)((Iterator)localObject3).next();
                  if (!ConfigManager.5.this.val$districtMap.containsKey(Long.valueOf(localSubDistrict.district.remoteId)))
                  {
                    District localDistrict = localSubDistrict.district;
                    localDistrict.save();
                    ConfigManager.5.this.val$districtMap.put(Long.valueOf(localDistrict.remoteId), localDistrict);
                    localSubDistrict.district = localDistrict;
                    localSubDistrict.stateId = localDistrict.stateId;
                    localSubDistrict.save();
                  }
                  else
                  {
                    localObject2 = (District)ConfigManager.5.this.val$districtMap.get(Long.valueOf(localSubDistrict.district.remoteId));
                  }
                }
                finally
                {
                  ActiveAndroid.endTransaction();
                  new Handler(Looper.getMainLooper()).post(new Runnable()
                  {
                    public void run()
                    {
                      if (ConfigManager.5.this.val$callback != null) {
                        ConfigManager.5.this.val$callback.onSuccess(null);
                      }
                    }
                  });
                }
              }
              Object localObject3 = StateTimestamp.findByStateId(ConfigManager.5.this.val$districtStateId);
              Object localObject2 = localObject3;
              if (localObject3 == null)
              {
                localObject2 = new StateTimestamp();
                ((StateTimestamp)localObject2).stateId = ConfigManager.5.this.val$districtStateId;
              }
              ((StateTimestamp)localObject2).timestamps = new Date();
              ((StateTimestamp)localObject2).save();
              ActiveAndroid.setTransactionSuccessful();
              ActiveAndroid.endTransaction();
              new Handler(Looper.getMainLooper()).post(new Runnable()
              {
                public void run()
                {
                  if (ConfigManager.5.this.val$callback != null) {
                    ConfigManager.5.this.val$callback.onSuccess(null);
                  }
                }
              });
            }
          }).start();
        }
        while (paramICartCallback == null) {
          return;
        }
        paramICartCallback.onSuccess(null);
      }
    });
  }
  
  public void getUserConfiguration(final Long paramLong, final ICartCallback<UserConfig> paramICartCallback)
  {
    this.mICartRestClient.getConfigService().getUserConfig(new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(UserConfig paramAnonymousUserConfig, Response paramAnonymousResponse)
      {
        UserConfig.deleteConfig();
        paramAnonymousUserConfig.userId = paramLong;
        paramAnonymousUserConfig.save();
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousUserConfig);
        }
      }
    });
  }
  
  void saveStates(final Long paramLong, final List<State> paramList, final ICartCallback paramICartCallback)
  {
    if (this.mCurrentTask != null) {
      this.mCurrentTask.cancel(true);
    }
    this.mCurrentTask = new AsyncTask()
    {
      protected Void doInBackground(Void... paramAnonymousVarArgs)
      {
        try
        {
          paramAnonymousVarArgs = State.findNotExisting(paramLong, paramList).iterator();
          while (paramAnonymousVarArgs.hasNext()) {
            ((State)paramAnonymousVarArgs.next()).save();
          }
          return null;
        }
        catch (Exception paramAnonymousVarArgs)
        {
          paramAnonymousVarArgs.printStackTrace();
        }
      }
      
      protected void onPostExecute(Void paramAnonymousVoid)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(null);
        }
      }
    }.execute(new Void[0]);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/managers/ConfigManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */