package com.happyfresh.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.managers.UserManager;
import com.happyfresh.utils.MixpanelTrackerUtils;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

public class InviteCodeFragment
  extends BaseDialogFragment
{
  private static final String TAG = ICartApplication.class.getSimpleName();
  @InjectView(2131558756)
  Button mBtnSubmit;
  @InjectView(2131558755)
  EditText mEtInvitationCode;
  @InjectView(2131558694)
  CircularProgressBar mProgressBar;
  
  private void setReferral(String paramString)
  {
    try
    {
      this.mProgressBar.setVisibility(0);
      getApplication().getUserManager().setReferral(paramString, new ICartCallback(TAG)
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          super.onFailure(paramAnonymousThrowable);
          if (InviteCodeFragment.this.getActivity() == null) {
            return;
          }
          InviteCodeFragment.this.mProgressBar.setVisibility(4);
        }
        
        public void onSuccess(Object paramAnonymousObject)
        {
          InviteCodeFragment.this.mICartApplication.getSharedPreferencesManager().markingNeedReferralCode(false);
          if (InviteCodeFragment.this.getActivity() == null) {
            return;
          }
          InviteCodeFragment.this.dismiss();
        }
      });
      return;
    }
    catch (JSONException paramString)
    {
      paramString.printStackTrace();
      return;
    }
    catch (UnsupportedEncodingException paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  @OnClick({2131558711})
  void dismissDialog()
  {
    this.mICartApplication.getSharedPreferencesManager().markingNeedReferralCode(false);
    MixpanelTrackerUtils.trackFBReferral(this.mICartApplication, "");
    dismiss();
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903133, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    getDialog().getWindow().requestFeature(1);
    setCancelable(false);
    this.mProgressBar.setVisibility(4);
    return paramLayoutInflater;
  }
  
  public void onDismiss(DialogInterface paramDialogInterface)
  {
    super.onDismiss(paramDialogInterface);
    sendTracker("Choose Location");
  }
  
  public void onResume()
  {
    super.onResume();
    sendTracker("FB Invite Code Popup");
  }
  
  @OnClick({2131558756})
  void submit()
  {
    ((InputMethodManager)this.mICartApplication.getSystemService("input_method")).hideSoftInputFromWindow(this.mEtInvitationCode.getWindowToken(), 0);
    String str = this.mEtInvitationCode.getText().toString();
    if (TextUtils.isEmpty(str))
    {
      dismissDialog();
      return;
    }
    setReferral(str);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/InviteCodeFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */