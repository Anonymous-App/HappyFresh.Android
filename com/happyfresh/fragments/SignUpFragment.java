package com.happyfresh.fragments;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.appsee.Appsee;
import com.happyfresh.activities.ChooseLocationActivity;
import com.happyfresh.activities.LoginActivity;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartErrorException;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.managers.LoginManager;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.models.AppConfig;
import com.happyfresh.models.User;
import com.happyfresh.utils.AccengageTrackerUtils;
import com.happyfresh.utils.AdjustUtils;
import com.happyfresh.utils.DialogUtils;
import com.happyfresh.utils.GAUtils;
import com.happyfresh.utils.MixpanelTrackerUtils;
import com.happyfresh.utils.OptimizelyUtils;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import retrofit.RetrofitError;

public class SignUpFragment
  extends BaseFragment
{
  private static final String TAG = LoginFragment.class.getSimpleName();
  @InjectView(2131558866)
  EditText mCode;
  @InjectView(2131558762)
  EditText mEmail;
  @InjectView(2131558584)
  EditText mFirstName;
  @InjectView(2131558585)
  EditText mLastName;
  @InjectView(2131558763)
  EditText mPassword;
  @InjectView(2131558867)
  EditText mPromoCode;
  @InjectView(2131558767)
  Button mSignUpButton;
  @InjectView(2131558869)
  CircularProgressBar mSignUpProgressBar;
  TextWatcher textWatcher = new TextWatcher()
  {
    public void afterTextChanged(Editable paramAnonymousEditable)
    {
      paramAnonymousEditable = SignUpFragment.this.mFirstName.getText().toString();
      String str1 = SignUpFragment.this.mEmail.getText().toString();
      String str2 = SignUpFragment.this.mPassword.getText().toString();
      if ((TextUtils.isEmpty(paramAnonymousEditable)) || (TextUtils.isEmpty(str1)) || (TextUtils.isEmpty(str2)))
      {
        SignUpFragment.this.mSignUpButton.setEnabled(false);
        return;
      }
      SignUpFragment.this.mSignUpButton.setEnabled(true);
    }
    
    public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
    
    public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
  };
  
  private void doSignUp(final String paramString1, final String paramString2, final String paramString3, final String paramString4, final String paramString5)
  {
    this.mICartApplication.getAndroidAdsId(new ICartCallback(TAG)
    {
      public void onSuccess(String paramAnonymousString)
      {
        try
        {
          String str = SignUpFragment.this.getApplication().getOrderNumber();
          SignUpFragment.this.getApplication().getLoginManager().signUp(paramString1, paramString2, paramString3, paramString4, paramString5, str, paramAnonymousString, new ICartCallback(SignUpFragment.TAG)
          {
            public void onFailure(Throwable paramAnonymous2Throwable)
            {
              super.onFailure(paramAnonymous2Throwable);
              if (SignUpFragment.this.getActivity() == null) {
                return;
              }
              Object localObject = (RetrofitError)paramAnonymous2Throwable;
              String str = ((RetrofitError)localObject).getLocalizedMessage();
              paramAnonymous2Throwable = str;
              if (localObject != null)
              {
                paramAnonymous2Throwable = str;
                if (((RetrofitError)localObject).getResponse() != null)
                {
                  localObject = ICartErrorException.getICartErrorException((Throwable)localObject);
                  paramAnonymous2Throwable = str;
                  if (localObject != null)
                  {
                    paramAnonymous2Throwable = str;
                    if (!TextUtils.isEmpty(((ICartErrorException)localObject).exception)) {
                      paramAnonymous2Throwable = ((ICartErrorException)localObject).exception;
                    }
                  }
                }
              }
              DialogUtils.showDialog(SignUpFragment.this.getActivity(), SignUpFragment.this.getString(2131165378), paramAnonymous2Throwable, SignUpFragment.this.getString(2131165478), null, null, null);
              SignUpFragment.this.resetView();
            }
            
            public void onSuccess(Object paramAnonymous2Object)
            {
              SignUpFragment.this.getApplication().getShippingAddress();
              paramAnonymous2Object = (User)paramAnonymous2Object;
              SignUpFragment.this.trackSignUp((User)paramAnonymous2Object, SignUpFragment.3.this.val$code);
              SignUpFragment.this.getApplication().getSharedPreferencesManager().markingNewUser(true);
              SignUpFragment.this.getApplication().subscribeParseChannels();
              SignUpFragment.this.getApplication().setMixpanelUser();
              SignUpFragment.this.getApplication().setMixpanelPushNotifications();
              SignUpFragment.this.getApplication().setAccengageUser();
              SignUpFragment.this.gotoChooseLocationActivity();
            }
          });
          return;
        }
        catch (JSONException paramAnonymousString)
        {
          Log.d(SignUpFragment.TAG, paramAnonymousString.getLocalizedMessage());
          return;
        }
        catch (UnsupportedEncodingException paramAnonymousString)
        {
          Log.d(SignUpFragment.TAG, paramAnonymousString.getLocalizedMessage());
        }
      }
    });
  }
  
  private void resetView()
  {
    this.mSignUpButton.setClickable(true);
    this.mSignUpProgressBar.setVisibility(4);
    this.mFirstName.setCursorVisible(true);
    this.mLastName.setCursorVisible(true);
    this.mEmail.setCursorVisible(true);
    this.mPassword.setCursorVisible(true);
    this.mCode.setCursorVisible(true);
  }
  
  private void trackSignUp(User paramUser, String paramString)
  {
    GAUtils.trackSignUp(getApplication(), paramUser);
    AdjustUtils.trackSignUp(paramUser);
    MixpanelTrackerUtils.trackSignUp(getApplication(), paramString);
    AccengageTrackerUtils.trackSignUp(getApplication(), paramString);
    OptimizelyUtils.trackSignUp();
  }
  
  @OnClick({2131558767})
  void attemptSignUp()
  {
    this.mSignUpButton.post(new Runnable()
    {
      public void run()
      {
        ((InputMethodManager)SignUpFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(SignUpFragment.this.mFirstName.getWindowToken(), 0);
      }
    });
    this.mFirstName.clearFocus();
    this.mFirstName.setCursorVisible(false);
    this.mFirstName.setError(null);
    this.mLastName.clearFocus();
    this.mLastName.setCursorVisible(false);
    this.mLastName.setError(null);
    this.mEmail.clearFocus();
    this.mEmail.setCursorVisible(false);
    this.mEmail.setError(null);
    this.mPassword.clearFocus();
    this.mPassword.setCursorVisible(false);
    this.mPassword.setError(null);
    this.mCode.clearFocus();
    this.mCode.setCursorVisible(false);
    this.mCode.setError(null);
    Object localObject2 = null;
    String str3 = this.mFirstName.getText().toString();
    String str4 = this.mLastName.getText().toString();
    String str5 = this.mEmail.getText().toString();
    String str6 = this.mPassword.getText().toString();
    String str1 = this.mCode.getText().toString();
    String str2 = this.mPromoCode.getText().toString();
    int j = 0;
    int i = j;
    Object localObject1 = localObject2;
    if (0 == 0)
    {
      i = j;
      localObject1 = localObject2;
      if (TextUtils.isEmpty(str3))
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
      if (TextUtils.isEmpty(str5))
      {
        this.mEmail.setError(getString(2131165379));
        localObject2 = this.mEmail;
        j = 1;
      }
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(str5).matches())
    {
      this.mEmail.setError(getString(2131165474));
      localObject2 = this.mEmail;
      j = 1;
    }
    i = j;
    localObject1 = localObject2;
    if (j == 0)
    {
      i = j;
      localObject1 = localObject2;
      if (TextUtils.isEmpty(str6))
      {
        this.mPassword.setError(getString(2131165379));
        localObject1 = this.mPassword;
        i = 1;
      }
    }
    Object localObject3;
    if (AppConfig.isRequireCode())
    {
      localObject2 = str1;
      j = i;
      localObject3 = localObject1;
      if (i == 0)
      {
        localObject2 = str1;
        j = i;
        localObject3 = localObject1;
        if (TextUtils.isEmpty(str1))
        {
          this.mCode.setError(getString(2131165379));
          localObject3 = this.mCode;
          j = 1;
          localObject2 = str1;
        }
      }
    }
    while (j != 0)
    {
      ((View)localObject3).requestFocus();
      this.mFirstName.setCursorVisible(true);
      this.mEmail.setCursorVisible(true);
      this.mPassword.setCursorVisible(true);
      this.mCode.setCursorVisible(true);
      return;
      localObject2 = str1;
      j = i;
      localObject3 = localObject1;
      if (!TextUtils.isEmpty(str2))
      {
        localObject2 = str2;
        j = i;
        localObject3 = localObject1;
      }
    }
    this.mSignUpButton.setClickable(false);
    this.mSignUpProgressBar.setVisibility(0);
    doSignUp(str3, str4, str5, str6, (String)localObject2);
  }
  
  protected String getScreenName()
  {
    return "Signup";
  }
  
  public void gotoChooseLocationActivity()
  {
    if (getActivity() == null) {
      return;
    }
    Intent localIntent = new Intent(getActivity(), ChooseLocationActivity.class);
    localIntent.setFlags(268468224);
    startActivity(localIntent);
  }
  
  @OnClick({2131558765})
  void gotoLoginActivity()
  {
    startActivity(new Intent(getActivity(), LoginActivity.class));
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    if (AppConfig.isRequireCode())
    {
      this.mCode.setVisibility(0);
      this.mPromoCode.setVisibility(8);
    }
    for (;;)
    {
      this.mPassword.setOnTouchListener(new View.OnTouchListener()
      {
        public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
        {
          paramAnonymousView = SignUpFragment.this.mPassword.getCompoundDrawables()[2];
          if (paramAnonymousView == null) {}
          while ((paramAnonymousMotionEvent.getAction() != 1) || (paramAnonymousMotionEvent.getRawX() < SignUpFragment.this.mPassword.getRight() - paramAnonymousView.getBounds().width())) {
            return false;
          }
          int i = SignUpFragment.this.mPassword.getSelectionStart();
          int j = SignUpFragment.this.mPassword.getSelectionEnd();
          if (SignUpFragment.this.mPassword.getTransformationMethod() != null) {
            SignUpFragment.this.mPassword.setTransformationMethod(null);
          }
          for (;;)
          {
            SignUpFragment.this.mPassword.setSelection(i, j);
            Appsee.addEvent("show password in Signup");
            return true;
            SignUpFragment.this.mPassword.setTransformationMethod(new PasswordTransformationMethod());
          }
        }
      });
      return;
      this.mCode.setVisibility(8);
      this.mPromoCode.setVisibility(0);
    }
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903152, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    this.mSignUpProgressBar.setVisibility(4);
    this.mFirstName.addTextChangedListener(this.textWatcher);
    this.mEmail.addTextChangedListener(this.textWatcher);
    this.mPassword.addTextChangedListener(this.textWatcher);
    this.mSignUpButton.setEnabled(false);
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/SignUpFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */