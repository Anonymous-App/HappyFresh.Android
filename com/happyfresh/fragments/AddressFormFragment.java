package com.happyfresh.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.utils.PhoneNumberUtil;

public abstract class AddressFormFragment
  extends BaseFragment
{
  private static final String TAG = AddressFormFragment.class.getSimpleName();
  @InjectView(2131558587)
  protected View mAddressFormNormal;
  @InjectView(2131558591)
  protected View mAddressFormTaipei;
  @InjectView(2131558586)
  protected TextView mAddressTitle;
  @InjectView(2131558594)
  protected EditText mAlleyTaipei;
  @InjectView(2131558598)
  protected EditText mCompanyTaxTaipei;
  @InjectView(2131558583)
  protected TextView mCreateAddressHint;
  @InjectView(2131558584)
  protected EditText mFirstName;
  @InjectView(2131558597)
  protected EditText mFloorTaipei;
  @InjectView(2131558600)
  protected EditText mInstruction;
  protected boolean mIsCountryTaiwan = false;
  @InjectView(2131558593)
  protected EditText mLaneTaipei;
  @InjectView(2131558585)
  protected EditText mLastName;
  @InjectView(2131558595)
  protected EditText mNoTaipei;
  @InjectView(2131558599)
  protected EditText mPhoneNumber;
  @InjectView(2131558602)
  CircularProgressBar mProgressIndicator;
  @InjectView(2131558601)
  protected Button mSaveAddress;
  @InjectView(2131558588)
  protected EditText mStreetAddress;
  @InjectView(2131558589)
  protected EditText mStreetAddress2;
  @InjectView(2131558592)
  protected TextView mSubDistrictTaipei;
  @InjectView(2131558590)
  protected TextView mSubDistrictText;
  @InjectView(2131558596)
  protected EditText mUnitTaipei;
  
  @OnClick({2131558601})
  protected void attemptToSubmitAddress()
  {
    this.mSaveAddress.post(new Runnable()
    {
      public void run()
      {
        if ((AddressFormFragment.this.getActivity() == null) || (AddressFormFragment.this.mFirstName == null)) {
          return;
        }
        ((InputMethodManager)AddressFormFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(AddressFormFragment.this.mFirstName.getWindowToken(), 0);
      }
    });
    this.mFirstName.setError(null);
    this.mLastName.setError(null);
    this.mStreetAddress.setError(null);
    this.mStreetAddress2.setError(null);
    this.mSubDistrictText.setError(null);
    this.mPhoneNumber.setError(null);
    this.mInstruction.setError(null);
    this.mSubDistrictTaipei.setError(null);
    this.mNoTaipei.setError(null);
    this.mFloorTaipei.setError(null);
    Object localObject2 = null;
    String str8 = this.mFirstName.getText().toString();
    String str7 = this.mLastName.getText().toString();
    String str6 = this.mStreetAddress.getText().toString();
    String str5 = this.mSubDistrictText.getText().toString();
    String str1 = this.mPhoneNumber.getText().toString();
    String str4 = this.mSubDistrictTaipei.getText().toString();
    String str3 = this.mNoTaipei.getText().toString();
    String str2 = this.mFloorTaipei.getText().toString();
    int j = 0;
    int i = j;
    Object localObject1 = localObject2;
    if (0 == 0)
    {
      i = j;
      localObject1 = localObject2;
      if (TextUtils.isEmpty(str8))
      {
        this.mFirstName.setError(getString(2131165379));
        localObject1 = this.mFirstName;
        i = 1;
      }
    }
    j = i;
    localObject2 = localObject1;
    if (i == 0)
    {
      j = i;
      localObject2 = localObject1;
      if (TextUtils.isEmpty(str7))
      {
        this.mLastName.setError(getString(2131165379));
        localObject2 = this.mLastName;
        j = 1;
      }
    }
    i = j;
    localObject1 = localObject2;
    if (j == 0)
    {
      i = j;
      localObject1 = localObject2;
      if (!this.mIsCountryTaiwan)
      {
        i = j;
        localObject1 = localObject2;
        if (TextUtils.isEmpty(str6))
        {
          this.mStreetAddress.setError(getString(2131165379));
          localObject1 = this.mStreetAddress;
          i = 1;
        }
      }
    }
    j = i;
    localObject2 = localObject1;
    if (i == 0)
    {
      j = i;
      localObject2 = localObject1;
      if (!this.mIsCountryTaiwan)
      {
        j = i;
        localObject2 = localObject1;
        if (TextUtils.isEmpty(str5))
        {
          this.mSubDistrictText.setError(getString(2131165379));
          localObject2 = this.mSubDistrictText;
          j = 1;
        }
      }
    }
    i = j;
    localObject1 = localObject2;
    if (j == 0)
    {
      i = j;
      localObject1 = localObject2;
      if (this.mIsCountryTaiwan)
      {
        i = j;
        localObject1 = localObject2;
        if (TextUtils.isEmpty(str4))
        {
          this.mSubDistrictTaipei.setError(getString(2131165379));
          localObject1 = this.mSubDistrictTaipei;
          i = 1;
        }
      }
    }
    j = i;
    localObject2 = localObject1;
    if (i == 0)
    {
      j = i;
      localObject2 = localObject1;
      if (this.mIsCountryTaiwan)
      {
        j = i;
        localObject2 = localObject1;
        if (TextUtils.isEmpty(str3))
        {
          this.mNoTaipei.setError(getString(2131165379));
          localObject2 = this.mNoTaipei;
          j = 1;
        }
      }
    }
    i = j;
    localObject1 = localObject2;
    if (j == 0)
    {
      i = j;
      localObject1 = localObject2;
      if (this.mIsCountryTaiwan)
      {
        i = j;
        localObject1 = localObject2;
        if (TextUtils.isEmpty(str2))
        {
          this.mFloorTaipei.setError(getString(2131165379));
          localObject1 = this.mFloorTaipei;
          i = 1;
        }
      }
    }
    if ((i == 0) && (TextUtils.isEmpty(str1)))
    {
      this.mPhoneNumber.setError(getString(2131165379));
      localObject1 = this.mPhoneNumber;
      i = 1;
    }
    for (;;)
    {
      j = i;
      localObject2 = localObject1;
      if (i == 0)
      {
        j = i;
        localObject2 = localObject1;
        if (this.mPhoneNumber.getText().toString().length() > 17)
        {
          this.mPhoneNumber.setError(getString(2131165474));
          localObject2 = this.mPhoneNumber;
          j = 1;
        }
      }
      if (j == 0) {
        break;
      }
      ((View)localObject2).requestFocus();
      if (localObject2.getClass().equals(EditText.class)) {
        ((EditText)localObject2).setCursorVisible(true);
      }
      return;
      this.mPhoneNumber.setText(PhoneNumberUtil.convertPhoneNumber(str1, getApplication().getSharedPreferencesManager().getStockLocationCountryCode()));
    }
    doSubmit();
  }
  
  protected abstract void doSubmit();
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mProgressIndicator.setVisibility(4);
    if (this.mIsCountryTaiwan)
    {
      this.mAddressFormNormal.setVisibility(8);
      this.mAddressFormTaipei.setVisibility(0);
    }
    for (;;)
    {
      this.mPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener()
      {
        public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
        {
          if (AddressFormFragment.this.mPhoneNumber != null)
          {
            paramAnonymousView = AddressFormFragment.this.mPhoneNumber.getText().toString();
            if (!TextUtils.isEmpty(paramAnonymousView)) {
              AddressFormFragment.this.mPhoneNumber.setText(PhoneNumberUtil.convertPhoneNumber(paramAnonymousView, AddressFormFragment.this.getApplication().getSharedPreferencesManager().getStockLocationCountryCode()));
            }
          }
        }
      });
      return;
      this.mAddressFormNormal.setVisibility(0);
      this.mAddressFormTaipei.setVisibility(8);
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (this.mICartApplication.getSharedPreferencesManager().getStockLocationCountryCode().toLowerCase().equals("tw")) {
      this.mIsCountryTaiwan = true;
    }
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903106, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
  
  protected void showProgress(boolean paramBoolean)
  {
    CircularProgressBar localCircularProgressBar = this.mProgressIndicator;
    if (paramBoolean) {}
    for (int i = 0;; i = 4)
    {
      localCircularProgressBar.setVisibility(i);
      return;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/AddressFormFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */