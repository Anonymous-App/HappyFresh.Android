package com.happyfresh.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.CallbackManager.Factory;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.happyfresh.activities.ChooseLocationActivity;
import com.happyfresh.activities.LoginActivity;
import com.happyfresh.activities.SignUpActivity;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.managers.UserManager;
import com.happyfresh.utils.OptimizelyUtils;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import org.json.JSONException;

public class AuthenticationFragment
  extends BaseFragment
{
  private static final String TAG = ICartApplication.class.getSimpleName();
  CallbackManager mFbCallbackManager;
  @InjectView(2131558613)
  Button mSignUpEmail;
  @InjectView(2131558614)
  CircularProgressBar mSignUpEmailProgressBar;
  @InjectView(2131558610)
  Button mSignUpFB;
  @InjectView(2131558611)
  CircularProgressBar mSignUpFBProgressBar;
  @InjectView(2131558615)
  TextView mTxtAlreadyHaveAccount;
  
  private void loginFacebook(String paramString)
  {
    try
    {
      this.mSignUpFB.setEnabled(false);
      this.mSignUpFBProgressBar.setVisibility(0);
      getApplication().getUserManager().loginFacebook(paramString, new ICartCallback(TAG)
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          super.onFailure(paramAnonymousThrowable);
          if (AuthenticationFragment.this.getActivity() == null) {
            return;
          }
          AuthenticationFragment.this.mSignUpFBProgressBar.setVisibility(4);
          AuthenticationFragment.this.mSignUpFB.setEnabled(true);
        }
        
        public void onSuccess(Object paramAnonymousObject)
        {
          
          if (AuthenticationFragment.this.getActivity() == null) {
            return;
          }
          AuthenticationFragment.this.mSignUpFBProgressBar.setVisibility(4);
          AuthenticationFragment.this.mSignUpFB.setEnabled(true);
          AuthenticationFragment.this.gotoChooseLocationActivity();
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
  
  protected String getScreenName()
  {
    return "Index Screen";
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
  
  @OnClick({2131558615})
  void gotoLoginActivity()
  {
    startActivity(new Intent(getActivity(), LoginActivity.class));
  }
  
  @OnClick({2131558610})
  void loginFacebook()
  {
    LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(new String[] { "email" }));
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    this.mFbCallbackManager.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mFbCallbackManager = CallbackManager.Factory.create();
    LoginManager.getInstance().registerCallback(this.mFbCallbackManager, new FacebookCallback()
    {
      public void onCancel()
      {
        if (AuthenticationFragment.this.getActivity() == null) {
          return;
        }
        Toast.makeText(AuthenticationFragment.this.mContext, AuthenticationFragment.this.getString(2131165620), 0).show();
      }
      
      public void onError(FacebookException paramAnonymousFacebookException)
      {
        if ((paramAnonymousFacebookException instanceof FacebookAuthorizationException)) {
          LoginManager.getInstance().logOut();
        }
        if (AuthenticationFragment.this.getActivity() == null) {
          return;
        }
        Toast.makeText(AuthenticationFragment.this.mContext, AuthenticationFragment.this.getString(2131165620), 0).show();
      }
      
      public void onSuccess(LoginResult paramAnonymousLoginResult)
      {
        if (AuthenticationFragment.this.getActivity() == null) {}
        do
        {
          do
          {
            return;
          } while ((paramAnonymousLoginResult == null) || (paramAnonymousLoginResult.getAccessToken() == null));
          paramAnonymousLoginResult = paramAnonymousLoginResult.getAccessToken().getToken();
        } while (TextUtils.isEmpty(paramAnonymousLoginResult));
        AuthenticationFragment.this.loginFacebook(paramAnonymousLoginResult);
      }
    });
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903108, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    this.mSignUpEmailProgressBar.setVisibility(4);
    this.mSignUpFBProgressBar.setVisibility(4);
    return paramLayoutInflater;
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    paramView = Html.fromHtml(getString(2131165282));
    this.mTxtAlreadyHaveAccount.setText(paramView);
  }
  
  @OnClick({2131558613})
  void signUpWithEmail()
  {
    startActivity(new Intent(getActivity(), SignUpActivity.class));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/AuthenticationFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */