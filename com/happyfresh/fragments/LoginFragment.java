package com.happyfresh.fragments;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.afollestad.materialdialogs.MaterialDialog;
import com.appsee.Appsee;
import com.happyfresh.activities.ChooseLocationActivity;
import com.happyfresh.activities.SignUpActivity;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.managers.LoginManager;
import com.happyfresh.managers.UserManager;
import com.happyfresh.utils.DialogUtils;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginFragment
  extends BaseFragment
{
  private static final String TAG = LoginFragment.class.getSimpleName();
  @InjectView(2131558768)
  View forgotPassword;
  private MaterialDialog mDialog;
  @InjectView(2131558762)
  EditText mEmail;
  @InjectView(2131558765)
  Button mLoginButton;
  @InjectView(2131558766)
  CircularProgressBar mLoginProgressBar;
  @InjectView(2131558763)
  EditText mPassword;
  TextWatcher textWatcher = new TextWatcher()
  {
    public void afterTextChanged(Editable paramAnonymousEditable)
    {
      paramAnonymousEditable = LoginFragment.this.mEmail.getText().toString();
      String str = LoginFragment.this.mPassword.getText().toString();
      if ((TextUtils.isEmpty(paramAnonymousEditable)) || (TextUtils.isEmpty(str)))
      {
        LoginFragment.this.mLoginButton.setEnabled(false);
        return;
      }
      LoginFragment.this.mLoginButton.setEnabled(true);
    }
    
    public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
    
    public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
  };
  
  private void doLogin(final String paramString1, final String paramString2)
  {
    this.mICartApplication.getAndroidAdsId(new ICartCallback(TAG)
    {
      public void onSuccess(String paramAnonymousString)
      {
        try
        {
          String str = LoginFragment.this.getApplication().getOrderNumber();
          LoginFragment.this.getApplication().getLoginManager().login(paramString1, paramString2, str, paramAnonymousString, new ICartCallback(LoginFragment.TAG)
          {
            public void onFailure(Throwable paramAnonymous2Throwable)
            {
              super.onFailure(paramAnonymous2Throwable);
              if (LoginFragment.this.getActivity() == null) {
                return;
              }
              paramAnonymous2Throwable = (RetrofitError)paramAnonymous2Throwable;
              if ((paramAnonymous2Throwable != null) && (paramAnonymous2Throwable.getResponse() != null) && (paramAnonymous2Throwable.getResponse().getStatus() == 401)) {}
              for (paramAnonymous2Throwable = DialogUtils.showDialog(LoginFragment.this.getActivity(), LoginFragment.this.getString(2131165378), LoginFragment.this.getString(2131165638), LoginFragment.this.getString(2131165478), null);; paramAnonymous2Throwable = DialogUtils.showDialog(LoginFragment.this.getActivity(), LoginFragment.this.getString(2131165378), paramAnonymous2Throwable.getLocalizedMessage(), LoginFragment.this.getString(2131165478), null))
              {
                paramAnonymous2Throwable.setOnDismissListener(new DialogInterface.OnDismissListener()
                {
                  public void onDismiss(DialogInterface paramAnonymous3DialogInterface)
                  {
                    LoginFragment.this.sendTracker("Login");
                  }
                });
                paramAnonymous2Throwable.show();
                LoginFragment.this.sendTracker("Login Error Popup");
                LoginFragment.this.resetView();
                return;
              }
            }
            
            public void onSuccess(Object paramAnonymous2Object)
            {
              paramAnonymous2Object = LoginFragment.this.getApplication();
              ((ICartApplication)paramAnonymous2Object).getShippingAddress();
              ((ICartApplication)paramAnonymous2Object).subscribeParseChannels();
              ((ICartApplication)paramAnonymous2Object).setMixpanelUser();
              ((ICartApplication)paramAnonymous2Object).setMixpanelPushNotifications();
              ((ICartApplication)paramAnonymous2Object).setAccengageUser();
              LoginFragment.this.gotoChooseLocationActivity();
            }
          });
          return;
        }
        catch (JSONException paramAnonymousString)
        {
          paramAnonymousString.printStackTrace();
          return;
        }
        catch (UnsupportedEncodingException paramAnonymousString)
        {
          paramAnonymousString.printStackTrace();
        }
      }
    });
  }
  
  private void resetView()
  {
    this.mLoginButton.setClickable(true);
    this.mLoginProgressBar.setVisibility(4);
    this.mEmail.setCursorVisible(true);
    this.mPassword.setCursorVisible(true);
  }
  
  private void sendResetPassword(String paramString, final ICartCallback paramICartCallback)
  {
    try
    {
      getApplication().getUserManager().resetPassword(paramString, new ICartCallback(TAG)
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          super.onFailure(paramAnonymousThrowable);
          if (paramICartCallback != null) {
            paramICartCallback.onFailure(paramAnonymousThrowable);
          }
        }
        
        public void onSuccess(Object paramAnonymousObject)
        {
          if (paramICartCallback != null) {
            paramICartCallback.onSuccess(paramAnonymousObject);
          }
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
  
  private void showInvalidEmail()
  {
    View localView = getActivity().getLayoutInflater().inflate(2130903143, null);
    ((TextView)localView.findViewById(2131558822)).setText(2131165413);
    ((TextView)localView.findViewById(2131558823)).setText(2131165414);
    Button localButton = (Button)localView.findViewById(2131558824);
    localButton.setText(2131165478);
    localButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        LoginFragment.this.mDialog.dismiss();
      }
    });
    ((ImageView)localView.findViewById(2131558821)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        LoginFragment.this.mDialog.dismiss();
      }
    });
    this.mDialog = DialogUtils.showDialog(getActivity(), localView);
    this.mDialog.setOnDismissListener(new DialogInterface.OnDismissListener()
    {
      public void onDismiss(DialogInterface paramAnonymousDialogInterface)
      {
        LoginFragment.this.sendTracker("Login");
      }
    });
    sendTracker("Invalid Email Popup");
  }
  
  private void showResetPasswordSuccess()
  {
    View localView = getActivity().getLayoutInflater().inflate(2130903143, null);
    ((TextView)localView.findViewById(2131558822)).setText(2131165499);
    ((TextView)localView.findViewById(2131558823)).setText(2131165500);
    Button localButton = (Button)localView.findViewById(2131558824);
    localButton.setText(2131165478);
    localButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        LoginFragment.this.mDialog.dismiss();
      }
    });
    ((ImageView)localView.findViewById(2131558821)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        LoginFragment.this.mDialog.dismiss();
      }
    });
    this.mDialog = DialogUtils.showDialog(getActivity(), localView);
  }
  
  @OnClick({2131558765})
  void attemptLogin()
  {
    this.mLoginButton.post(new Runnable()
    {
      public void run()
      {
        ((InputMethodManager)LoginFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(LoginFragment.this.mPassword.getWindowToken(), 0);
      }
    });
    this.mEmail.clearFocus();
    this.mEmail.setCursorVisible(false);
    this.mEmail.setError(null);
    this.mPassword.clearFocus();
    this.mPassword.setCursorVisible(false);
    this.mPassword.setError(null);
    Object localObject2 = null;
    String str1 = this.mEmail.getText().toString();
    String str2 = this.mPassword.getText().toString();
    int j = 0;
    int i = j;
    Object localObject1 = localObject2;
    if (0 == 0)
    {
      i = j;
      localObject1 = localObject2;
      if (TextUtils.isEmpty(str1))
      {
        this.mEmail.setError(getString(2131165379));
        localObject1 = this.mEmail;
        i = 1;
      }
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(str1).matches())
    {
      this.mEmail.setError(getString(2131165474));
      localObject1 = this.mEmail;
      i = 1;
    }
    j = i;
    localObject2 = localObject1;
    if (i == 0)
    {
      j = i;
      localObject2 = localObject1;
      if (TextUtils.isEmpty(str2))
      {
        this.mPassword.setError(getString(2131165379));
        localObject2 = this.mPassword;
        j = 1;
      }
    }
    if (j != 0)
    {
      ((View)localObject2).requestFocus();
      this.mEmail.setCursorVisible(true);
      this.mPassword.setCursorVisible(true);
      return;
    }
    this.mLoginButton.setClickable(false);
    this.mLoginProgressBar.setVisibility(0);
    doLogin(str1, str2);
  }
  
  protected String getScreenName()
  {
    return "Login";
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
  
  @OnClick({2131558767})
  void gotoSignUpActivity()
  {
    startActivity(new Intent(getActivity(), SignUpActivity.class));
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mPassword.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        if (LoginFragment.this.mPassword.getCompoundDrawables()[2] == null) {}
        while ((paramAnonymousMotionEvent.getAction() != 1) || (paramAnonymousMotionEvent.getRawX() < LoginFragment.this.mPassword.getRight() - LoginFragment.this.mPassword.getCompoundDrawables()[2].getBounds().width())) {
          return false;
        }
        int i = LoginFragment.this.mPassword.getSelectionStart();
        int j = LoginFragment.this.mPassword.getSelectionEnd();
        if (LoginFragment.this.mPassword.getTransformationMethod() != null) {
          LoginFragment.this.mPassword.setTransformationMethod(null);
        }
        for (;;)
        {
          LoginFragment.this.mPassword.setSelection(i, j);
          Appsee.addEvent("show password in Login");
          return true;
          LoginFragment.this.mPassword.setTransformationMethod(new PasswordTransformationMethod());
        }
      }
    });
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903135, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    this.mLoginProgressBar.setVisibility(4);
    this.mEmail.addTextChangedListener(this.textWatcher);
    this.mPassword.addTextChangedListener(this.textWatcher);
    this.mLoginButton.setEnabled(false);
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
  
  @OnClick({2131558768})
  void resetPassword()
  {
    View localView = getActivity().getLayoutInflater().inflate(2130903125, null);
    final CircularProgressBar localCircularProgressBar = (CircularProgressBar)localView.findViewById(2131558694);
    localCircularProgressBar.setVisibility(4);
    final Button localButton = (Button)localView.findViewById(2131558732);
    localButton.setEnabled(false);
    ImageView localImageView = (ImageView)localView.findViewById(2131558711);
    final EditText localEditText = (EditText)localView.findViewById(2131558729);
    localEditText.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable)
      {
        if (TextUtils.isEmpty(paramAnonymousEditable.toString()))
        {
          localButton.setEnabled(false);
          return;
        }
        localButton.setEnabled(true);
      }
      
      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      
      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
    });
    localButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        localButton.setEnabled(false);
        localCircularProgressBar.setVisibility(0);
        LoginFragment.this.sendResetPassword(localEditText.getText().toString(), new ICartCallback(LoginFragment.TAG)
        {
          public void onFailure(Throwable paramAnonymous2Throwable)
          {
            if (LoginFragment.this.getActivity() == null) {
              return;
            }
            if ((paramAnonymous2Throwable != null) && (((RetrofitError)paramAnonymous2Throwable).getResponse() != null) && (((RetrofitError)paramAnonymous2Throwable).getResponse().getStatus() == 404))
            {
              LoginFragment.this.mDialog.dismiss();
              LoginFragment.this.showInvalidEmail();
            }
            LoginFragment.4.this.val$resetButton.setEnabled(true);
            LoginFragment.4.this.val$progress.setVisibility(4);
          }
          
          public void onSuccess(Object paramAnonymous2Object)
          {
            if (LoginFragment.this.getActivity() == null) {
              return;
            }
            LoginFragment.4.this.val$progress.setVisibility(4);
            LoginFragment.4.this.val$resetButton.setEnabled(true);
            LoginFragment.this.mDialog.dismiss();
            LoginFragment.this.showResetPasswordSuccess();
          }
        });
      }
    });
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        LoginFragment.this.mDialog.dismiss();
        LoginFragment.this.sendTracker("Login");
      }
    });
    this.mDialog = DialogUtils.showDialog(getActivity(), localView);
    sendTracker("Reset Password");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/LoginFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */