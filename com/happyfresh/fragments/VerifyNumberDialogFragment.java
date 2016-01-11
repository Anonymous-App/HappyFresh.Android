package com.happyfresh.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.listeners.OnVerifyNumberListener;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.managers.UserManager;
import com.happyfresh.models.Address;
import com.happyfresh.models.User;
import com.happyfresh.utils.PhoneNumberUtil;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

public class VerifyNumberDialogFragment
  extends BaseDialogFragment
{
  private final String TAG = getClass().getSimpleName();
  private ICartApplication mApplication;
  @InjectView(2131558722)
  Button mBtnSubmit;
  @InjectView(2131558720)
  EditText mEtCountryCode;
  @InjectView(2131558721)
  EditText mEtPhoneNumber;
  private OnVerifyNumberListener mOnVerifyListener;
  @InjectView(2131558694)
  CircularProgressBar mProgressBar;
  @InjectView(2131558718)
  View mWarningInvalidNumber;
  private String phoneNumber;
  private TextWatcher textWatcher = new TextWatcher()
  {
    public void afterTextChanged(Editable paramAnonymousEditable)
    {
      VerifyNumberDialogFragment.this.toggleSubmitButton();
    }
    
    public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
    
    public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
  };
  
  private void displayPhoneNumber()
  {
    if (this.phoneNumber == null)
    {
      Address localAddress = this.mApplication.getCurrentUser().getPrimaryShippingAddress();
      if (localAddress != null) {
        this.phoneNumber = PhoneNumberUtil.convertPhoneNumber(localAddress.phone, this.mApplication.getSharedPreferencesManager().getStockLocationCountryCode());
      }
    }
    if (!TextUtils.isEmpty(this.phoneNumber))
    {
      this.mEtCountryCode.setText(this.phoneNumber.substring(0, 3));
      this.mEtPhoneNumber.setText(this.phoneNumber.substring(3));
    }
  }
  
  public static VerifyNumberDialogFragment newInstance(String paramString)
  {
    VerifyNumberDialogFragment localVerifyNumberDialogFragment = new VerifyNumberDialogFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("ICartConstant.KEYS.PHONE_NUMBER", paramString);
    localVerifyNumberDialogFragment.setArguments(localBundle);
    return localVerifyNumberDialogFragment;
  }
  
  private void toggleSubmitButton()
  {
    String str1 = this.mEtCountryCode.getText().toString();
    String str2 = this.mEtPhoneNumber.getText().toString();
    if ((TextUtils.isEmpty(str1)) || (TextUtils.isEmpty(str2))) {
      this.mBtnSubmit.setEnabled(false);
    }
    for (;;)
    {
      if ((this.mEtCountryCode.isFocused()) && (str1.length() == 3)) {
        this.mEtPhoneNumber.requestFocus();
      }
      if (this.mWarningInvalidNumber.getVisibility() == 0) {
        this.mWarningInvalidNumber.setVisibility(8);
      }
      return;
      this.mBtnSubmit.setEnabled(true);
    }
  }
  
  @OnClick({2131558711})
  void close()
  {
    dismiss();
    sendTracker("Delivery");
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mApplication = ICartApplication.get(this);
    this.phoneNumber = getArguments().getString("ICartConstant.KEYS.PHONE_NUMBER");
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903123, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    getDialog().getWindow().requestFeature(1);
    setCancelable(false);
    return paramLayoutInflater;
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mEtCountryCode.addTextChangedListener(this.textWatcher);
    this.mEtPhoneNumber.addTextChangedListener(this.textWatcher);
    toggleSubmitButton();
    displayPhoneNumber();
    this.mProgressBar.setVisibility(8);
  }
  
  public void setOnVerifyNumberListener(OnVerifyNumberListener paramOnVerifyNumberListener)
  {
    this.mOnVerifyListener = paramOnVerifyNumberListener;
  }
  
  @OnClick({2131558722})
  public void verifyNumberClicked()
  {
    ((InputMethodManager)this.mApplication.getSystemService("input_method")).hideSoftInputFromWindow(this.mEtCountryCode.getWindowToken(), 0);
    final String str = this.mEtCountryCode.getText().toString() + this.mEtPhoneNumber.getText().toString();
    if (PhoneNumberUtil.isPhoneNumberValid(str))
    {
      this.mProgressBar.setVisibility(0);
      try
      {
        this.mApplication.getUserManager().verifyNumber(str, new ICartCallback(this.TAG)
        {
          public void onFailure(Throwable paramAnonymousThrowable)
          {
            super.onFailure(paramAnonymousThrowable);
            if (VerifyNumberDialogFragment.this.getActivity() == null) {
              return;
            }
            VerifyNumberDialogFragment.this.mProgressBar.setVisibility(8);
          }
          
          public void onSuccess(Object paramAnonymousObject)
          {
            if (VerifyNumberDialogFragment.this.getActivity() == null) {
              return;
            }
            VerifyNumberDialogFragment.this.mProgressBar.setVisibility(8);
            if (VerifyNumberDialogFragment.this.mOnVerifyListener != null) {
              VerifyNumberDialogFragment.this.mOnVerifyListener.onSuccessVerifyNumber(str);
            }
            VerifyNumberDialogFragment.this.dismiss();
          }
        });
        return;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        return;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        localUnsupportedEncodingException.printStackTrace();
        return;
      }
    }
    this.mWarningInvalidNumber.setVisibility(0);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/VerifyNumberDialogFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */