package com.happyfresh.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.happyfresh.activities.CheckoutActivity;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.callbacks.ICartDialogCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.listeners.OnFindStoreBackListener;
import com.happyfresh.listeners.OnFindStoreClickListener;
import com.happyfresh.managers.ConfigManager;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.managers.UserManager;
import com.happyfresh.models.Address;
import com.happyfresh.models.Country;
import com.happyfresh.models.District;
import com.happyfresh.models.FindStoreModel;
import com.happyfresh.models.State;
import com.happyfresh.models.SubDistrict;
import com.happyfresh.models.User;
import com.happyfresh.utils.DialogUtils;
import com.happyfresh.utils.GAUtils;
import com.happyfresh.utils.PhoneNumberUtil;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;

public class CreateAddressFormFragment
  extends AddressFormFragment
{
  private static final String STATE_CHOOSE_AREA = "STATE_CHOOSE_AREA";
  private static final String STATE_CHOOSE_CITY = "STATE_CHOOSE_CITY";
  private static final String STATE_CHOOSE_COUNTRY = "STATE_CHOOSE_COUNTRY";
  private static final String TAG = AddressFormFragment.class.getSimpleName();
  private FindStoreDialogFragment mChooseAreaDialog;
  private FindStoreDialogFragment mChooseCityDialog;
  private FindStoreDialogFragment mChooseCountryDialog;
  private String mChooseState = "";
  private int mEditedAddressPosition = -1;
  private boolean mExpressCheckout = false;
  private boolean mOnSaveInstance = false;
  private String mScreenName;
  private Address mSelectedAddress;
  private String mStateName;
  private SubDistrict mSubDistrict;
  
  private boolean hasPrimaryAddress()
  {
    Iterator localIterator = getApplication().getCurrentUser().getAddresses().iterator();
    while (localIterator.hasNext()) {
      if (((Address)localIterator.next()).isPrimary) {
        return true;
      }
    }
    return false;
  }
  
  private void popToBackStack(Address paramAddress)
  {
    paramAddress = getApplication().getCurrentUser();
    if ((this.mExpressCheckout) && (getTargetFragment() != null))
    {
      paramAddress = paramAddress.getAddresses();
      switch (getTargetRequestCode())
      {
      default: 
        return;
      case 1000: 
        ((AddressManagementFragment)getTargetFragment()).popPassBackAddress((Address)paramAddress.get(paramAddress.size() - 1));
        return;
      case 2000: 
        ((MyAccountFragment)getTargetFragment()).popBackToAccount();
        return;
      }
      ((MyAccountFragment)getTargetFragment()).popBackToAccount();
      return;
    }
    new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        if ((CreateAddressFormFragment.this.getActivity() == null) || (CreateAddressFormFragment.this.getFragmentManager() == null)) {
          return;
        }
        CheckoutDeliveryFragment localCheckoutDeliveryFragment = new CheckoutDeliveryFragment();
        CreateAddressFormFragment.this.getFragmentManager().beginTransaction().replace(2131558581, localCheckoutDeliveryFragment).commit();
      }
    }, 200L);
  }
  
  private void processDeletingAddress()
  {
    DialogUtils.showDialog(getActivity(), null, getString(2131165346), getString(2131165478), getString(2131165293), null, new ICartDialogCallback()
    {
      public void onCancel() {}
      
      public void onNegative(MaterialDialog paramAnonymousMaterialDialog) {}
      
      public void onNeutral(MaterialDialog paramAnonymousMaterialDialog) {}
      
      public void onPositive(MaterialDialog paramAnonymousMaterialDialog)
      {
        CreateAddressFormFragment.this.deleteAddress();
      }
    });
  }
  
  private void saveNewAddress()
  {
    String str1 = this.mFirstName.getText().toString();
    String str2 = this.mLastName.getText().toString();
    String str3 = this.mStreetAddress.getText().toString();
    String str4 = this.mStreetAddress2.getText().toString();
    String str5 = this.mPhoneNumber.getText().toString();
    String str6 = this.mInstruction.getText().toString();
    String str7 = this.mFloorTaipei.getText().toString();
    String str8 = this.mNoTaipei.getText().toString();
    String str9 = this.mUnitTaipei.getText().toString();
    String str10 = this.mAlleyTaipei.getText().toString();
    String str11 = this.mLaneTaipei.getText().toString();
    String str12 = this.mCompanyTaxTaipei.getText().toString();
    boolean bool2 = true;
    boolean bool1 = bool2;
    if (getApplication().getCurrentUser().getAddresses().size() > 0)
    {
      bool1 = bool2;
      if (hasPrimaryAddress()) {
        bool1 = false;
      }
    }
    Object localObject4 = null;
    Object localObject5 = null;
    Object localObject6 = null;
    Object localObject3 = localObject4;
    Object localObject2 = localObject5;
    Object localObject1 = localObject6;
    try
    {
      if (this.mSubDistrict.district != null)
      {
        localObject3 = localObject4;
        localObject2 = localObject5;
        localObject1 = localObject6;
        if (this.mSubDistrict.district.getState() != null)
        {
          localObject3 = this.mSubDistrict.district.getState().countryId;
          localObject2 = this.mSubDistrict.district.getState().remoteId;
          localObject1 = this.mSubDistrict.district.getState().name;
        }
      }
      getApplication().getUserManager().addAddress(str1, str2, str3, str4, (Long)localObject3, (Long)localObject2, this.mSubDistrict.remoteId, (String)localObject1, this.mSubDistrict.zipcode, str5, str8, str7, str9, str11, str10, str12, bool1, 0, str6, new ICartCallback(TAG)
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          super.onFailure(paramAnonymousThrowable);
          if (CreateAddressFormFragment.this.getActivity() == null) {
            return;
          }
          CreateAddressFormFragment.this.showProgress(false);
        }
        
        public void onSuccess(Address paramAnonymousAddress)
        {
          if (paramAnonymousAddress.isPrimary)
          {
            Iterator localIterator = CreateAddressFormFragment.this.getApplication().getCurrentUser().getAddresses().iterator();
            while (localIterator.hasNext())
            {
              Address localAddress = (Address)localIterator.next();
              if (localAddress.isPrimary) {
                localAddress.isPrimary = false;
              }
            }
          }
          CreateAddressFormFragment.this.getApplication().getCurrentUser().getAddresses().add(paramAnonymousAddress);
          if (CreateAddressFormFragment.this.getActivity() == null) {
            return;
          }
          CreateAddressFormFragment.this.showProgress(false);
          if ((CreateAddressFormFragment.this.getTargetFragment() == null) || ((CreateAddressFormFragment.this.getTargetFragment() != null) && (CreateAddressFormFragment.this.getTargetRequestCode() == 1000))) {
            GAUtils.trackingECommerceCheckout(CreateAddressFormFragment.this.getApplication(), 2, paramAnonymousAddress.toString(CreateAddressFormFragment.this.mICartApplication), CreateAddressFormFragment.this.getScreenName());
          }
          CreateAddressFormFragment.this.popToBackStack(paramAnonymousAddress);
        }
      });
      return;
    }
    catch (JSONException localJSONException)
    {
      Log.e(TAG, localJSONException.getLocalizedMessage());
      showProgress(false);
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      Log.e(TAG, localUnsupportedEncodingException.getLocalizedMessage());
      showProgress(false);
    }
  }
  
  private void setDefaultValue()
  {
    User localUser = getApplication().getCurrentUser();
    this.mFirstName.setText(localUser.firstName);
    this.mLastName.setText(localUser.lastName);
    this.mSubDistrict = getApplication().currentSubDistrict;
    if ((this.mSubDistrict != null) && (getTargetRequestCode() < 2000))
    {
      if (!this.mIsCountryTaiwan) {
        break label110;
      }
      this.mSubDistrictTaipei.setText(this.mSubDistrict.toString());
    }
    for (;;)
    {
      this.mSubDistrictText.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          CreateAddressFormFragment.this.showChooseCountryOrCityDialog();
        }
      });
      this.mSubDistrictTaipei.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          CreateAddressFormFragment.this.showChooseCountryOrCityDialog();
        }
      });
      return;
      label110:
      this.mSubDistrictText.setText(this.mSubDistrict.toString());
    }
  }
  
  private void setValueWithAddress(Address paramAddress)
  {
    this.mAddressTitle.setText(2131165349);
    this.mAddressTitle.setTextColor(getResources().getColor(2131493011));
    this.mFirstName.setText(paramAddress.firstName);
    this.mLastName.setText(paramAddress.lastName);
    this.mStreetAddress.setText(paramAddress.address1);
    this.mStreetAddress2.setText(paramAddress.address2);
    this.mPhoneNumber.setText(paramAddress.phone);
    this.mSubDistrict = paramAddress.subDistrict;
    if (this.mSubDistrict != null)
    {
      if (!this.mIsCountryTaiwan) {
        break label258;
      }
      this.mSubDistrictTaipei.setText(this.mSubDistrict.toString());
      this.mSubDistrictTaipei.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          CreateAddressFormFragment.this.showChooseCountryOrCityDialog();
        }
      });
    }
    for (;;)
    {
      this.mInstruction.setText(paramAddress.deliveryInstruction);
      if (this.mIsCountryTaiwan)
      {
        this.mNoTaipei.setText(paramAddress.number);
        this.mFloorTaipei.setText(paramAddress.floor);
        if (!TextUtils.isEmpty(paramAddress.lane)) {
          this.mLaneTaipei.setText(paramAddress.lane);
        }
        if (!TextUtils.isEmpty(paramAddress.alley)) {
          this.mAlleyTaipei.setText(paramAddress.alley);
        }
        if (!TextUtils.isEmpty(paramAddress.unit)) {
          this.mUnitTaipei.setText(paramAddress.unit);
        }
        if (!TextUtils.isEmpty(paramAddress.companyTaxID)) {
          this.mCompanyTaxTaipei.setText(paramAddress.companyTaxID);
        }
      }
      return;
      label258:
      this.mSubDistrictText.setText(this.mSubDistrict.toString());
      this.mSubDistrictText.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          CreateAddressFormFragment.this.showChooseCountryOrCityDialog();
        }
      });
    }
  }
  
  private void updateAddress(Address paramAddress)
  {
    long l = paramAddress.remoteId;
    String str1 = this.mFirstName.getText().toString();
    String str2 = this.mLastName.getText().toString();
    String str3 = this.mStreetAddress.getText().toString();
    String str4 = this.mStreetAddress2.getText().toString();
    String str5 = this.mPhoneNumber.getText().toString();
    String str6 = this.mInstruction.getText().toString();
    String str7 = this.mFloorTaipei.getText().toString();
    String str8 = this.mNoTaipei.getText().toString();
    String str9 = this.mUnitTaipei.getText().toString();
    String str10 = this.mAlleyTaipei.getText().toString();
    String str11 = this.mLaneTaipei.getText().toString();
    String str12 = this.mCompanyTaxTaipei.getText().toString();
    boolean bool = paramAddress.isPrimary;
    Object localObject3 = null;
    Object localObject4 = null;
    Object localObject5 = null;
    Object localObject2 = localObject3;
    Object localObject1 = localObject4;
    paramAddress = (Address)localObject5;
    if (this.mSubDistrict.district != null)
    {
      localObject2 = localObject3;
      localObject1 = localObject4;
      paramAddress = (Address)localObject5;
      if (this.mSubDistrict.district.getState() != null)
      {
        localObject2 = this.mSubDistrict.district.getState().countryId;
        localObject1 = this.mSubDistrict.district.getState().remoteId;
        paramAddress = this.mSubDistrict.district.getState().name;
      }
    }
    try
    {
      getApplication().getUserManager().updateAddress(Long.valueOf(l), str1, str2, str3, str4, (Long)localObject2, (Long)localObject1, this.mSubDistrict.remoteId, paramAddress, this.mSubDistrict.zipcode, str5, str8, str7, str9, str11, str10, str12, bool, 0, str6, new ICartCallback(TAG)
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          super.onFailure(paramAnonymousThrowable);
          if (CreateAddressFormFragment.this.getActivity() == null) {
            return;
          }
          CreateAddressFormFragment.this.showProgress(false);
        }
        
        public void onSuccess(Address paramAnonymousAddress)
        {
          CreateAddressFormFragment.this.getApplication().getCurrentUser().getAddresses().remove(CreateAddressFormFragment.this.mEditedAddressPosition);
          CreateAddressFormFragment.this.getApplication().getCurrentUser().getAddresses().add(CreateAddressFormFragment.this.mEditedAddressPosition, paramAnonymousAddress);
          if (CreateAddressFormFragment.this.getActivity() == null) {
            return;
          }
          CreateAddressFormFragment.this.showProgress(false);
          CreateAddressFormFragment.this.popToBackStack(paramAnonymousAddress);
        }
      });
      return;
    }
    catch (JSONException paramAddress)
    {
      Log.e(TAG, paramAddress.getLocalizedMessage());
      showProgress(false);
      return;
    }
    catch (UnsupportedEncodingException paramAddress)
    {
      Log.e(TAG, paramAddress.getLocalizedMessage());
      showProgress(false);
    }
  }
  
  protected void deleteAddress()
  {
    long l = this.mSelectedAddress.remoteId;
    getApplication().getUserManager().deleteAddress(Long.valueOf(l), new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        if (CreateAddressFormFragment.this.getActivity() == null) {
          return;
        }
        CreateAddressFormFragment.this.popToBackStack(null);
      }
      
      public void onSuccess(Address paramAnonymousAddress)
      {
        CreateAddressFormFragment.this.getApplication().getCurrentUser().getAddresses().remove(CreateAddressFormFragment.this.mEditedAddressPosition);
        if (CreateAddressFormFragment.this.getActivity() == null) {
          return;
        }
        CreateAddressFormFragment.this.popToBackStack(paramAnonymousAddress);
      }
    });
  }
  
  protected void doSubmit()
  {
    showProgress(true);
    String str = this.mPhoneNumber.getText().toString();
    this.mPhoneNumber.setText(PhoneNumberUtil.convertPhoneNumber(str, getApplication().getSharedPreferencesManager().getStockLocationCountryCode()));
    if (this.mEditedAddressPosition > -1)
    {
      updateAddress(this.mSelectedAddress);
      return;
    }
    saveNewAddress();
  }
  
  protected String getScreenName()
  {
    return this.mScreenName;
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    if (getArguments() != null)
    {
      this.mExpressCheckout = getArguments().getBoolean("ICartConstant.KEYS.EXPRESS_CHECKOUT", false);
      this.mEditedAddressPosition = getArguments().getInt("ICartConstant.KEYS.EDITED_ADDRESS", -1);
    }
    if (this.mExpressCheckout) {
      this.mCreateAddressHint.setVisibility(8);
    }
    if (this.mEditedAddressPosition > -1)
    {
      this.mSelectedAddress = null;
      try
      {
        this.mSelectedAddress = ((Address)getApplication().getCurrentUser().getAddresses().get(this.mEditedAddressPosition));
        return;
      }
      catch (Exception paramBundle)
      {
        paramBundle.printStackTrace();
        return;
      }
      finally
      {
        if (this.mSelectedAddress != null)
        {
          setValueWithAddress(this.mSelectedAddress);
          setHasOptionsMenu(true);
        }
      }
    }
    setDefaultValue();
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt1 == 1000)
    {
      getActivity();
      if (paramInt2 == -1)
      {
        paramIntent = (SubDistrict)paramIntent.getParcelableExtra("ICartConstant.KEYS.SUB_DISTRICT_BUNDLE");
        if (paramIntent != null)
        {
          this.mSubDistrict = paramIntent;
          if (!this.mIsCountryTaiwan) {
            break label66;
          }
          this.mSubDistrictTaipei.setText(this.mSubDistrict.toString());
        }
      }
    }
    return;
    label66:
    this.mSubDistrictText.setText(this.mSubDistrict.toString());
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mScreenName = getArguments().getString("ICartConstant.KEYS.ADDRESS_SCREEN_TITLE", "Add Address");
    if ((getActivity() instanceof CheckoutActivity)) {
      ((CheckoutActivity)getActivity()).setDeliveryScreenSelected(this.mScreenName);
    }
    if (getTargetFragment() == null) {
      GAUtils.trackingECommerceCheckout(getApplication(), 2, null, getScreenName());
    }
  }
  
  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    getActivity().getMenuInflater().inflate(2131623936, paramMenu);
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    }
    processDeletingAddress();
    return true;
  }
  
  public void onResume()
  {
    super.onResume();
    if (this.mOnSaveInstance)
    {
      this.mOnSaveInstance = false;
      if (!this.mChooseState.equals("STATE_CHOOSE_COUNTRY")) {
        break label64;
      }
      if ((this.mChooseCountryDialog != null) && (!this.mChooseCountryDialog.isVisible())) {
        this.mChooseCountryDialog.show(getFragmentManager(), null);
      }
    }
    for (;;)
    {
      this.mChooseState = "";
      return;
      label64:
      if (this.mChooseState.equals("STATE_CHOOSE_AREA"))
      {
        if ((this.mChooseAreaDialog != null) && (!this.mChooseAreaDialog.isVisible())) {
          this.mChooseAreaDialog.show(getFragmentManager(), null);
        }
      }
      else if ((this.mChooseState.equals("STATE_CHOOSE_CITY")) && (this.mChooseCityDialog != null) && (!this.mChooseCityDialog.isVisible())) {
        this.mChooseCityDialog.show(getFragmentManager(), null);
      }
    }
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    this.mOnSaveInstance = true;
  }
  
  void showChooseArea(final State paramState)
  {
    this.mStateName = paramState.name;
    getApplication().getConfigManager().getSubdistrictByStateId(paramState.remoteId, new ICartCallback(TAG)
    {
      public void onSuccess(Object paramAnonymousObject)
      {
        paramAnonymousObject = null;
        if (CreateAddressFormFragment.this.getApplication().currentSubDistrict != null) {
          paramAnonymousObject = CreateAddressFormFragment.this.getApplication().currentSubDistrict;
        }
        List localList = SubDistrict.findByState(paramState.remoteId);
        Iterator localIterator = localList.iterator();
        while (localIterator.hasNext())
        {
          SubDistrict localSubDistrict = (SubDistrict)localIterator.next();
          if ((paramAnonymousObject != null) && (localSubDistrict.remoteId.equals(((SubDistrict)paramAnonymousObject).remoteId))) {
            localSubDistrict.setSelected(true);
          } else {
            localSubDistrict.setSelected(false);
          }
        }
        paramAnonymousObject = (ArrayList)localList;
        if (CreateAddressFormFragment.this.mChooseAreaDialog != null)
        {
          CreateAddressFormFragment.this.mChooseAreaDialog.dismissDelayed();
          CreateAddressFormFragment.access$202(CreateAddressFormFragment.this, null);
        }
        CreateAddressFormFragment.access$202(CreateAddressFormFragment.this, FindStoreDialogFragment.newInstance(2131165440, (ArrayList)paramAnonymousObject, "Choose Area"));
        CreateAddressFormFragment.this.mChooseAreaDialog.setOnFindStoreClickListener(new OnFindStoreClickListener()
        {
          public void onSelected(FindStoreModel paramAnonymous2FindStoreModel)
          {
            CreateAddressFormFragment.access$302(CreateAddressFormFragment.this, (SubDistrict)paramAnonymous2FindStoreModel);
            if (CreateAddressFormFragment.this.mIsCountryTaiwan) {
              CreateAddressFormFragment.this.mSubDistrictTaipei.setText(CreateAddressFormFragment.this.mSubDistrict.toString());
            }
            for (;;)
            {
              if (CreateAddressFormFragment.this.mChooseCityDialog != null) {
                CreateAddressFormFragment.this.mChooseCityDialog.dismissDelayed();
              }
              CreateAddressFormFragment.this.mChooseAreaDialog.dismissDelayed();
              CreateAddressFormFragment.this.sendTracker(CreateAddressFormFragment.this.getScreenName());
              return;
              CreateAddressFormFragment.this.mSubDistrictText.setText(CreateAddressFormFragment.this.mSubDistrict.toString());
            }
          }
        });
        CreateAddressFormFragment.this.mChooseAreaDialog.setOnFindStoreBackListener(new OnFindStoreBackListener()
        {
          public void onBack()
          {
            CreateAddressFormFragment.access$202(CreateAddressFormFragment.this, null);
            if (CreateAddressFormFragment.this.mChooseCityDialog != null)
            {
              CreateAddressFormFragment.this.mChooseCityDialog.clearAllProgressBar();
              CreateAddressFormFragment.this.sendTracker("Choose City");
              return;
            }
            CreateAddressFormFragment.this.sendTracker(CreateAddressFormFragment.this.getScreenName());
          }
        });
        CreateAddressFormFragment.access$402(CreateAddressFormFragment.this, "STATE_CHOOSE_AREA");
        if (!CreateAddressFormFragment.this.mOnSaveInstance) {
          new Handler().postDelayed(new Runnable()
          {
            public void run()
            {
              if ((CreateAddressFormFragment.this.getActivity() == null) || (CreateAddressFormFragment.this.getFragmentManager() == null) || (CreateAddressFormFragment.this.mChooseAreaDialog == null)) {
                return;
              }
              CreateAddressFormFragment.this.mChooseAreaDialog.setTitle(CreateAddressFormFragment.this.mStateName);
              CreateAddressFormFragment.this.mChooseAreaDialog.show(CreateAddressFormFragment.this.getFragmentManager(), null);
            }
          }, 200L);
        }
      }
    });
  }
  
  void showChooseCity(Country paramCountry)
  {
    this.mChooseState = "STATE_CHOOSE_CITY";
    Iterator localIterator = null;
    Object localObject1 = localIterator;
    Object localObject2;
    if (getApplication().currentSubDistrict != null)
    {
      localObject2 = getApplication().currentSubDistrict;
      localObject1 = localIterator;
      if (((SubDistrict)localObject2).district != null)
      {
        localObject2 = ((SubDistrict)localObject2).district;
        localObject1 = localIterator;
        if (((District)localObject2).getState() != null) {
          localObject1 = ((District)localObject2).getState();
        }
      }
    }
    paramCountry = State.findByCountry(paramCountry.remoteId);
    localIterator = paramCountry.iterator();
    while (localIterator.hasNext())
    {
      localObject2 = (State)localIterator.next();
      if ((localObject1 != null) && (((State)localObject2).remoteId.equals(((State)localObject1).remoteId))) {
        ((State)localObject2).setSelected(true);
      } else {
        ((State)localObject2).setSelected(false);
      }
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
        CreateAddressFormFragment.this.showChooseArea((State)paramAnonymousFindStoreModel);
      }
    });
    this.mChooseCityDialog.setOnFindStoreBackListener(new OnFindStoreBackListener()
    {
      public void onBack()
      {
        CreateAddressFormFragment.access$102(CreateAddressFormFragment.this, null);
        if (CreateAddressFormFragment.this.mChooseCountryDialog != null)
        {
          CreateAddressFormFragment.this.mChooseCountryDialog.clearAllProgressBar();
          CreateAddressFormFragment.this.sendTracker("Choose Country");
          return;
        }
        CreateAddressFormFragment.this.sendTracker(CreateAddressFormFragment.this.getScreenName());
      }
    });
    if (!this.mOnSaveInstance) {
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
        CreateAddressFormFragment.this.showChooseCity((Country)paramAnonymousFindStoreModel);
      }
    });
    this.mChooseCountryDialog.setOnFindStoreBackListener(new OnFindStoreBackListener()
    {
      public void onBack()
      {
        CreateAddressFormFragment.access$002(CreateAddressFormFragment.this, null);
        CreateAddressFormFragment.this.sendTracker(CreateAddressFormFragment.this.getScreenName());
      }
    });
    if (!this.mOnSaveInstance) {
      this.mChooseCountryDialog.show(getFragmentManager(), null);
    }
  }
  
  void showChooseCountryOrCityDialog()
  {
    Country localCountry = Country.findByCode(getApplication().getCountryCode());
    if (localCountry == null)
    {
      showChooseCountry();
      return;
    }
    showChooseCity(localCountry);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/CreateAddressFormFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */