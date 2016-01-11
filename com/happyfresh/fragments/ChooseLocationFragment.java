package com.happyfresh.fragments;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.activities.BaseActivity;
import com.happyfresh.activities.ChooseLocationActivity;
import com.happyfresh.activities.ChooseStoreActivity;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.listeners.LocationRetrievedListener;
import com.happyfresh.listeners.OnFindStoreBackListener;
import com.happyfresh.listeners.OnFindStoreClickListener;
import com.happyfresh.managers.LocationManager;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.models.Coordinate;
import com.happyfresh.models.Country;
import com.happyfresh.models.FindStoreModel;
import com.happyfresh.models.State;
import com.happyfresh.models.StockLocation;
import com.happyfresh.models.StockLocationResponse;
import com.happyfresh.models.SubDistrict;
import com.happyfresh.utils.AccengageTrackerUtils;
import com.happyfresh.utils.DialogUtils;
import com.happyfresh.utils.MixpanelTrackerUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import retrofit.RetrofitError;

public class ChooseLocationFragment
  extends BaseFragment
{
  public static final String ACTION_CLOSE_DUPLICATE_CHOOSE_STORE_ACTIVITY = "ChooseStoreActivity.ACTION_CLOSE";
  private static final String STATE_CHOOSE_AREA = "STATE_CHOOSE_AREA";
  private static final String STATE_CHOOSE_CITY = "STATE_CHOOSE_CITY";
  private static final String STATE_CHOOSE_COUNTRY = "STATE_CHOOSE_COUNTRY";
  private static final String STATE_UNSUPPORTED_AREA = "STATE_UNAVAILABLE_AREA";
  private static final String TAG = ChooseLocationFragment.class.getSimpleName();
  private FindStoreDialogFragment mChooseAreaDialog;
  private FindStoreDialogFragment mChooseCityDialog;
  private FindStoreDialogFragment mChooseCountryDialog;
  @InjectView(2131558688)
  Button mChooseLocation;
  private String mChooseState = "";
  @InjectView(2131558691)
  CircularProgressBar mCircularProgressBar;
  private String mCityName = "";
  @InjectView(2131558689)
  TextView mCurrentCountry;
  @InjectView(2131558690)
  View mProgressContainer;
  private boolean mReturningWithResult = false;
  private boolean mShowingUnsupportedDialog = false;
  private UnsupportedDialogFragment mUnsupportedDialog;
  
  private void getNearbyStockLocation(final SubDistrict paramSubDistrict, final Double paramDouble1, final Double paramDouble2)
  {
    getApplication().getLocationManager().getNearbyStockLocations(paramDouble1, paramDouble2, paramSubDistrict.zipcode, true, paramSubDistrict.name, null, null, new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        if (ChooseLocationFragment.this.getActivity() == null) {
          return;
        }
        ChooseLocationFragment.this.mProgressContainer.setVisibility(8);
        ChooseLocationFragment.this.mCircularProgressBar.setVisibility(4);
      }
      
      public void onSuccess(StockLocationResponse paramAnonymousStockLocationResponse)
      {
        ChooseLocationActivity localChooseLocationActivity = (ChooseLocationActivity)ChooseLocationFragment.this.getActivity();
        if (localChooseLocationActivity == null) {}
        do
        {
          return;
          ChooseLocationFragment.this.mProgressContainer.setVisibility(8);
          ChooseLocationFragment.this.mCircularProgressBar.setVisibility(4);
          if (!paramAnonymousStockLocationResponse.stockLocations.isEmpty())
          {
            if (((StockLocation)paramAnonymousStockLocationResponse.stockLocations.get(0)).country != null)
            {
              paramAnonymousStockLocationResponse = ((StockLocation)paramAnonymousStockLocationResponse.stockLocations.get(0)).country;
              ChooseLocationFragment.this.getApplication().overrideCountry(paramAnonymousStockLocationResponse.isoName);
            }
            paramAnonymousStockLocationResponse = new Intent(localChooseLocationActivity, ChooseStoreActivity.class);
            paramAnonymousStockLocationResponse.putExtra("ICartConstant.KEYS.EXTRAS.LONGITUDE", paramDouble1);
            paramAnonymousStockLocationResponse.putExtra("ICartConstant.KEYS.EXTRAS.LATITUDE", paramDouble2);
            paramAnonymousStockLocationResponse.putExtra("ICartConstant.KEYS.EXTRAS.ZIP_CODE", paramSubDistrict.zipcode);
            paramAnonymousStockLocationResponse.putExtra("ICartConstant.KEYS.EXTRAS.SUB_LOCALITY", paramSubDistrict.name);
            paramAnonymousStockLocationResponse.putExtra("ICartConstant.KEYS.EXTRAS.CITY_NAME", ChooseLocationFragment.this.mCityName);
            ChooseLocationFragment.this.startActivity(paramAnonymousStockLocationResponse);
            return;
          }
          MixpanelTrackerUtils.trackLocationOutOfRange(ChooseLocationFragment.this.getApplication(), paramSubDistrict);
          AccengageTrackerUtils.trackLocationOutOfRange(ChooseLocationFragment.this.getApplication(), paramSubDistrict);
        } while (ChooseLocationFragment.this.getActivity() == null);
        ChooseLocationFragment.this.showUnsupportedDialog(null, paramDouble1, paramDouble2);
      }
    });
  }
  
  private void getStockLocations(final SubDistrict paramSubDistrict)
  {
    getApplication().getLocationManager().getStockLocations(paramSubDistrict.remoteId, null, null, new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        if (ChooseLocationFragment.this.getActivity() == null) {}
        do
        {
          return;
          paramAnonymousThrowable = (RetrofitError)paramAnonymousThrowable;
          if ((paramAnonymousThrowable != null) && (paramAnonymousThrowable.getBody() != null)) {
            DialogUtils.showDialog(ChooseLocationFragment.this.getActivity(), ChooseLocationFragment.this.getString(2131165378), paramAnonymousThrowable.getBody().toString(), null, null, null, null);
          }
        } while (ChooseLocationFragment.this.mChooseAreaDialog == null);
        ChooseLocationFragment.this.mChooseAreaDialog.clearAllProgressBar();
      }
      
      public void onSuccess(StockLocationResponse paramAnonymousStockLocationResponse)
      {
        Object localObject;
        if (paramAnonymousStockLocationResponse != null)
        {
          if (paramAnonymousStockLocationResponse.stockLocations.isEmpty()) {
            break label200;
          }
          localObject = ChooseLocationFragment.this.getApplication().getSharedPreferencesManager();
          ((SharedPreferencesManager)localObject).deleteCoordinate();
          ((SharedPreferencesManager)localObject).deleteZipCode();
          ChooseLocationFragment.this.getApplication().currentSubDistrict = paramSubDistrict;
          ((SharedPreferencesManager)localObject).deleteSubDistrict();
          ((SharedPreferencesManager)localObject).saveSubDistrict(paramSubDistrict);
          if ((ChooseLocationFragment.this.getActivity() != null) && (ChooseLocationFragment.this.isAdded())) {
            break label80;
          }
        }
        label80:
        label200:
        label292:
        for (;;)
        {
          return;
          localObject = new Intent("ChooseStoreActivity.ACTION_CLOSE");
          ChooseLocationFragment.this.getActivity().sendBroadcast((Intent)localObject);
          localObject = new Intent(ChooseLocationFragment.this.getActivity(), ChooseStoreActivity.class);
          ((Intent)localObject).putParcelableArrayListExtra("ICartConstant.KEYS.EXTRAS.STOCK_LOCATIONS", paramAnonymousStockLocationResponse.stockLocations);
          ((Intent)localObject).putExtra("ICartConstant.KEYS.EXTRAS.SUB_LOCALITY", paramSubDistrict.name);
          ((Intent)localObject).putExtra("ICartConstant.KEYS.EXTRAS.ZIP_CODE", paramSubDistrict.zipcode);
          ((Intent)localObject).putExtra("ICartConstant.KEYS.EXTRAS.CITY_NAME", ChooseLocationFragment.this.mCityName);
          ChooseLocationFragment.this.startActivity((Intent)localObject);
          for (;;)
          {
            if (ChooseLocationFragment.this.mChooseAreaDialog == null) {
              break label292;
            }
            ChooseLocationFragment.this.mChooseAreaDialog.clearAllProgressBar();
            return;
            MixpanelTrackerUtils.trackLocationOutOfRange(ChooseLocationFragment.this.getApplication(), paramSubDistrict);
            AccengageTrackerUtils.trackLocationOutOfRange(ChooseLocationFragment.this.getApplication(), paramSubDistrict);
            if ((ChooseLocationFragment.this.getActivity() == null) || (!ChooseLocationFragment.this.isAdded())) {
              break;
            }
            ChooseLocationFragment.this.showUnsupportedDialog(paramSubDistrict, null, null);
            ((InputMethodManager)ChooseLocationFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(ChooseLocationFragment.this.getView().getWindowToken(), 0);
          }
        }
      }
    });
  }
  
  private void getZipCode(Location paramLocation)
  {
    new AsyncTask()
    {
      protected SubDistrict doInBackground(Void... paramAnonymousVarArgs)
      {
        paramAnonymousVarArgs = ChooseLocationFragment.this.getApplication().getSubDistrictByLatLon(this.val$lat.doubleValue(), this.val$lon.doubleValue());
        if (paramAnonymousVarArgs != null)
        {
          SharedPreferencesManager localSharedPreferencesManager = ChooseLocationFragment.this.getApplication().getSharedPreferencesManager();
          ChooseLocationFragment.this.getApplication().currentSubDistrict = null;
          localSharedPreferencesManager.deleteSubDistrict();
          if (!TextUtils.isEmpty(paramAnonymousVarArgs.name)) {
            localSharedPreferencesManager.saveSubDistrictName(paramAnonymousVarArgs.name);
          }
          localSharedPreferencesManager.deleteCoordinate();
          localSharedPreferencesManager.saveCoordinate(new Coordinate(this.val$lat, this.val$lon));
          localSharedPreferencesManager.deleteZipCode();
          localSharedPreferencesManager.saveZipCode(paramAnonymousVarArgs.zipcode);
        }
        return paramAnonymousVarArgs;
      }
      
      protected void onPostExecute(SubDistrict paramAnonymousSubDistrict)
      {
        if (paramAnonymousSubDistrict != null) {
          ChooseLocationFragment.this.getNearbyStockLocation(paramAnonymousSubDistrict, this.val$lat, this.val$lon);
        }
      }
    }.execute(new Void[0]);
  }
  
  private void showUnsupportedDialog(SubDistrict paramSubDistrict, Double paramDouble1, Double paramDouble2)
  {
    this.mChooseState = "STATE_UNAVAILABLE_AREA";
    String str = null;
    if (paramSubDistrict != null) {
      str = paramSubDistrict.name;
    }
    if ((this.mShowingUnsupportedDialog) && (this.mUnsupportedDialog != null)) {
      if (!this.mUnsupportedDialog.changeContent(str, paramSubDistrict, paramDouble1, paramDouble2)) {}
    }
    do
    {
      do
      {
        return;
      } while ((this.mUnsupportedDialog.isVisible()) || (!this.mUnsupportedDialog.isDismiss()));
      this.mUnsupportedDialog = null;
      this.mShowingUnsupportedDialog = false;
      return;
      this.mUnsupportedDialog = UnsupportedDialogFragment.newInstance(str, paramSubDistrict, paramDouble1, paramDouble2);
      this.mUnsupportedDialog.setOnDismissListener(new DialogInterface.OnDismissListener()
      {
        public void onDismiss(DialogInterface paramAnonymousDialogInterface)
        {
          if (paramAnonymousDialogInterface == null)
          {
            ChooseLocationFragment.access$402(ChooseLocationFragment.this, false);
            return;
          }
          if (ChooseLocationFragment.this.mChooseAreaDialog != null) {
            ChooseLocationFragment.this.mChooseAreaDialog.clearAllProgressBar();
          }
          ChooseLocationFragment.access$402(ChooseLocationFragment.this, false);
        }
      });
    } while (this.mReturningWithResult);
    this.mShowingUnsupportedDialog = true;
    new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        if ((ChooseLocationFragment.this.getActivity() == null) || (ChooseLocationFragment.this.getFragmentManager() == null) || (ChooseLocationFragment.this.mUnsupportedDialog == null))
        {
          ChooseLocationFragment.access$402(ChooseLocationFragment.this, false);
          return;
        }
        if (ChooseLocationFragment.this.getFragmentManager().findFragmentByTag("unsupportedDialog") == null)
        {
          ChooseLocationFragment.this.mUnsupportedDialog.show(ChooseLocationFragment.this.getFragmentManager(), "unsupportedDialog");
          return;
        }
        ChooseLocationFragment.access$402(ChooseLocationFragment.this, false);
      }
    }, 200L);
    sendTracker("Area Not Covered");
  }
  
  private void updateCountry()
  {
    Object localObject = getApplication().getCurrentLocale();
    localObject = Html.fromHtml(getString(2131165438, new Object[] { getApplication().getLocaleByCountryCode(getApplication().getCountryCode()).getDisplayCountry((Locale)localObject) }));
    this.mCurrentCountry.setText((CharSequence)localObject);
  }
  
  @OnClick({2131558689})
  void changeCountry()
  {
    showChooseCountry();
  }
  
  @OnClick({2131558688})
  void chooseLocationManually()
  {
    Country localCountry = Country.findByCode(getApplication().getCountryCode());
    if (localCountry != null)
    {
      showChooseCity(localCountry);
      return;
    }
    showChooseCountry();
  }
  
  protected String getScreenName()
  {
    return "Choose Location";
  }
  
  void getSuggestedLocationByCurrentLocation()
  {
    this.mProgressContainer.setVisibility(0);
    this.mCircularProgressBar.setVisibility(0);
    ChooseLocationActivity localChooseLocationActivity = (ChooseLocationActivity)getActivity();
    if (localChooseLocationActivity.checkLocationService())
    {
      localChooseLocationActivity.requestLocation(new LocationRetrievedListener()
      {
        public void onLocationNotRetrieved()
        {
          if (ChooseLocationFragment.this.getActivity() == null) {
            return;
          }
          ChooseLocationFragment.this.mProgressContainer.setVisibility(8);
          ChooseLocationFragment.this.mCircularProgressBar.setVisibility(4);
          DialogUtils.showDialog(ChooseLocationFragment.this.getActivity(), ChooseLocationFragment.this.getString(2131165378), ChooseLocationFragment.this.getString(2131165310), ChooseLocationFragment.this.getString(2131165478), null, null, null);
        }
        
        public void onLocationRetrieved(Location paramAnonymousLocation)
        {
          if (ChooseLocationFragment.this.getActivity() == null) {
            return;
          }
          ChooseLocationFragment.this.getZipCode(paramAnonymousLocation);
        }
      });
      return;
    }
    this.mProgressContainer.setVisibility(8);
    this.mCircularProgressBar.setVisibility(4);
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    this.mReturningWithResult = true;
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = getActivity().getLayoutInflater().inflate(2130903116, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    this.mProgressContainer.setVisibility(8);
    this.mCircularProgressBar.setVisibility(4);
    paramViewGroup = Html.fromHtml(getString(2131165444));
    this.mChooseLocation.setText(paramViewGroup);
    return paramLayoutInflater;
  }
  
  @OnClick({2131558687})
  void onCurrentLocationButtonClick()
  {
    BaseActivity localBaseActivity = (BaseActivity)getActivity();
    if (localBaseActivity.isLocationPermissionGranted())
    {
      getSuggestedLocationByCurrentLocation();
      return;
    }
    localBaseActivity.requestLocationPermission(new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable) {}
      
      public void onSuccess(Object paramAnonymousObject)
      {
        if (ChooseLocationFragment.this.getActivity() == null) {
          return;
        }
        ChooseLocationFragment.this.getSuggestedLocationByCurrentLocation();
      }
    }, true);
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
  
  public void onResume()
  {
    super.onResume();
    updateCountry();
    if ((this.mChooseCountryDialog == null) && (this.mChooseCityDialog == null) && (this.mChooseAreaDialog == null) && (this.mUnsupportedDialog == null))
    {
      sendTracker(getScreenName());
      if (this.mReturningWithResult)
      {
        if (!this.mChooseState.equals("STATE_CHOOSE_COUNTRY")) {
          break label171;
        }
        if ((this.mChooseCountryDialog != null) && (!this.mChooseCountryDialog.isVisible())) {
          this.mChooseCountryDialog.show(getFragmentManager(), null);
        }
      }
    }
    for (;;)
    {
      this.mChooseState = "";
      this.mReturningWithResult = false;
      return;
      if (this.mChooseAreaDialog != null)
      {
        sendTracker("Choose Area");
        break;
      }
      if (this.mChooseCityDialog != null)
      {
        sendTracker("Choose City");
        break;
      }
      if (this.mChooseCountryDialog != null)
      {
        sendTracker("Choose Country");
        break;
      }
      if (this.mUnsupportedDialog == null) {
        break;
      }
      sendTracker("Area Not Covered");
      break;
      label171:
      if (this.mChooseState.equals("STATE_CHOOSE_CITY"))
      {
        if ((this.mChooseCityDialog != null) && (!this.mChooseCityDialog.isVisible())) {
          this.mChooseCityDialog.show(getFragmentManager(), null);
        }
      }
      else if (this.mChooseState.equals("STATE_CHOOSE_AREA"))
      {
        if ((this.mChooseAreaDialog != null) && (!this.mChooseAreaDialog.isVisible())) {
          this.mChooseAreaDialog.show(getFragmentManager(), null);
        }
      }
      else if ((this.mChooseState.equals("STATE_UNAVAILABLE_AREA")) && (this.mUnsupportedDialog != null) && (!this.mUnsupportedDialog.isVisible())) {
        this.mUnsupportedDialog.show(getFragmentManager(), null);
      }
    }
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    if (this.mICartApplication.getSharedPreferencesManager().isNeedReferralCode()) {
      showInviteCode();
    }
  }
  
  protected void sendTracker() {}
  
  void showChooseArea(final State paramState)
  {
    this.mChooseState = "STATE_CHOOSE_AREA";
    this.mCityName = paramState.getName();
    Object localObject = SubDistrict.findByState(paramState.remoteId);
    Iterator localIterator = ((List)localObject).iterator();
    while (localIterator.hasNext()) {
      ((SubDistrict)localIterator.next()).setSelected(false);
    }
    localObject = (ArrayList)localObject;
    if (this.mChooseAreaDialog != null)
    {
      this.mChooseAreaDialog.dismissDelayed();
      this.mChooseAreaDialog = null;
    }
    this.mChooseAreaDialog = FindStoreDialogFragment.newInstance(2131165440, (ArrayList)localObject, "Choose Area");
    this.mChooseAreaDialog.setOnFindStoreClickListener(new OnFindStoreClickListener()
    {
      public void onSelected(FindStoreModel paramAnonymousFindStoreModel)
      {
        ChooseLocationFragment.this.getStockLocations((SubDistrict)paramAnonymousFindStoreModel);
      }
    });
    this.mChooseAreaDialog.setOnFindStoreBackListener(new OnFindStoreBackListener()
    {
      public void onBack()
      {
        ChooseLocationFragment.access$302(ChooseLocationFragment.this, null);
        if (ChooseLocationFragment.this.mChooseCityDialog != null)
        {
          ChooseLocationFragment.this.mChooseCityDialog.clearAllProgressBar();
          ChooseLocationFragment.this.sendTracker("Choose City");
          return;
        }
        if (ChooseLocationFragment.this.mChooseCountryDialog != null)
        {
          ChooseLocationFragment.this.mChooseCountryDialog.clearAllProgressBar();
          ChooseLocationFragment.this.sendTracker("Choose Country");
          return;
        }
        ChooseLocationFragment.this.sendTracker("Choose Location");
      }
    });
    if (!this.mReturningWithResult) {
      new Handler().postDelayed(new Runnable()
      {
        public void run()
        {
          if ((ChooseLocationFragment.this.getActivity() == null) || (ChooseLocationFragment.this.getFragmentManager() == null) || (ChooseLocationFragment.this.mChooseAreaDialog == null)) {}
          do
          {
            return;
            ChooseLocationFragment.this.mChooseAreaDialog.setTitle(paramState.name);
          } while (ChooseLocationFragment.this.mChooseAreaDialog.isAdded());
          ChooseLocationFragment.this.mChooseAreaDialog.show(ChooseLocationFragment.this.getFragmentManager(), null);
        }
      }, 200L);
    }
  }
  
  void showChooseCity(Country paramCountry)
  {
    this.mChooseState = "STATE_CHOOSE_CITY";
    paramCountry = State.findByCountry(paramCountry.remoteId);
    Iterator localIterator = paramCountry.iterator();
    while (localIterator.hasNext()) {
      ((State)localIterator.next()).setSelected(false);
    }
    paramCountry = (ArrayList)paramCountry;
    if (this.mChooseCityDialog != null)
    {
      this.mChooseCityDialog.dismissDelayed();
      this.mChooseCityDialog = null;
    }
    this.mChooseCityDialog = FindStoreDialogFragment.newInstance(2131165441, paramCountry, "Choose City");
    this.mChooseCityDialog.setOnFindStoreClickListener(new OnFindStoreClickListener()
    {
      public void onSelected(FindStoreModel paramAnonymousFindStoreModel)
      {
        ChooseLocationFragment.this.showChooseArea((State)paramAnonymousFindStoreModel);
      }
    });
    this.mChooseCityDialog.setOnFindStoreBackListener(new OnFindStoreBackListener()
    {
      public void onBack()
      {
        ChooseLocationFragment.access$102(ChooseLocationFragment.this, null);
        if (ChooseLocationFragment.this.mChooseCountryDialog != null)
        {
          ChooseLocationFragment.this.mChooseCountryDialog.clearAllProgressBar();
          ChooseLocationFragment.this.sendTracker("Choose Country");
          return;
        }
        ChooseLocationFragment.this.sendTracker("Choose Location");
      }
    });
    if ((!this.mReturningWithResult) && (!this.mChooseCityDialog.isAdded())) {
      this.mChooseCityDialog.show(getFragmentManager(), null);
    }
  }
  
  void showChooseCountry()
  {
    this.mChooseState = "STATE_CHOOSE_COUNTRY";
    Object localObject = Country.findAll();
    Iterator localIterator = ((List)localObject).iterator();
    while (localIterator.hasNext())
    {
      Country localCountry = (Country)localIterator.next();
      if (localCountry.getCode().equals(getApplication().getCountryCode())) {
        localCountry.setSelected(true);
      } else {
        localCountry.setSelected(false);
      }
    }
    localObject = (ArrayList)localObject;
    if (this.mChooseCountryDialog != null)
    {
      this.mChooseCountryDialog.dismissDelayed();
      this.mChooseCountryDialog = null;
    }
    this.mChooseCountryDialog = FindStoreDialogFragment.newInstance(2131165442, (ArrayList)localObject, "Choose Country");
    this.mChooseCountryDialog.setOnFindStoreClickListener(new OnFindStoreClickListener()
    {
      public void onSelected(FindStoreModel paramAnonymousFindStoreModel)
      {
        ChooseLocationFragment.this.showChooseCity((Country)paramAnonymousFindStoreModel);
      }
    });
    this.mChooseCountryDialog.setOnFindStoreBackListener(new OnFindStoreBackListener()
    {
      public void onBack()
      {
        ChooseLocationFragment.access$002(ChooseLocationFragment.this, null);
        ChooseLocationFragment.this.sendTracker("Choose Location");
      }
    });
    if ((!this.mReturningWithResult) && (!this.mChooseCountryDialog.isAdded())) {
      this.mChooseCountryDialog.show(getFragmentManager(), null);
    }
  }
  
  void showInviteCode()
  {
    new InviteCodeFragment().show(getFragmentManager(), null);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/ChooseLocationFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */