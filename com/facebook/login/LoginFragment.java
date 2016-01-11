package com.facebook.login;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.R.id;
import com.facebook.R.layout;

public class LoginFragment
  extends Fragment
{
  private static final String EXTRA_REQUEST = "request";
  private static final String NULL_CALLING_PKG_ERROR_MSG = "Cannot call LoginFragment with a null calling package. This can occur if the launchMode of the caller is singleInstance.";
  static final String RESULT_KEY = "com.facebook.LoginFragment:Result";
  private static final String SAVED_LOGIN_CLIENT = "loginClient";
  private static final String TAG = "LoginFragment";
  private String callingPackage;
  private LoginClient loginClient;
  private LoginClient.Request request;
  
  private void initializeCallingPackage(Activity paramActivity)
  {
    paramActivity = paramActivity.getCallingActivity();
    if (paramActivity == null) {
      return;
    }
    this.callingPackage = paramActivity.getPackageName();
  }
  
  private void onLoginClientCompleted(LoginClient.Result paramResult)
  {
    this.request = null;
    if (paramResult.code == LoginClient.Result.Code.CANCEL) {}
    for (int i = 0;; i = -1)
    {
      Bundle localBundle = new Bundle();
      localBundle.putParcelable("com.facebook.LoginFragment:Result", paramResult);
      paramResult = new Intent();
      paramResult.putExtras(localBundle);
      if (isAdded())
      {
        getActivity().setResult(i, paramResult);
        getActivity().finish();
      }
      return;
    }
  }
  
  static Bundle populateIntentExtras(LoginClient.Request paramRequest)
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("request", paramRequest);
    return localBundle;
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    this.loginClient.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.loginClient = ((LoginClient)paramBundle.getParcelable("loginClient"));
      this.loginClient.setFragment(this);
      this.loginClient.setOnCompletedListener(new LoginClient.OnCompletedListener()
      {
        public void onCompleted(LoginClient.Result paramAnonymousResult)
        {
          LoginFragment.this.onLoginClientCompleted(paramAnonymousResult);
        }
      });
      paramBundle = getActivity();
      if (paramBundle != null) {
        break label70;
      }
    }
    label70:
    do
    {
      return;
      this.loginClient = new LoginClient(this);
      break;
      initializeCallingPackage(paramBundle);
    } while (paramBundle.getIntent() == null);
    this.request = ((LoginClient.Request)paramBundle.getIntent().getParcelableExtra("request"));
  }
  
  public View onCreateView(final LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(R.layout.com_facebook_login_fragment, paramViewGroup, false);
    this.loginClient.setBackgroundProcessingListener(new LoginClient.BackgroundProcessingListener()
    {
      public void onBackgroundProcessingStarted()
      {
        paramLayoutInflater.findViewById(R.id.com_facebook_login_activity_progress_bar).setVisibility(0);
      }
      
      public void onBackgroundProcessingStopped()
      {
        paramLayoutInflater.findViewById(R.id.com_facebook_login_activity_progress_bar).setVisibility(8);
      }
    });
    return paramLayoutInflater;
  }
  
  public void onDestroy()
  {
    this.loginClient.cancelCurrentHandler();
    super.onDestroy();
  }
  
  public void onDetach()
  {
    super.onDetach();
    if (this.loginClient.getInProgress())
    {
      Object localObject = LoginClient.Result.createCancelResult(this.request, "Operation canceled");
      Bundle localBundle = new Bundle();
      localBundle.putParcelable("com.facebook.LoginFragment:Result", (Parcelable)localObject);
      localObject = new Intent();
      ((Intent)localObject).putExtras(localBundle);
      getActivity().setResult(0, (Intent)localObject);
      getActivity().finish();
    }
  }
  
  public void onPause()
  {
    super.onPause();
    getActivity().findViewById(R.id.com_facebook_login_activity_progress_bar).setVisibility(8);
  }
  
  public void onResume()
  {
    super.onResume();
    if (this.callingPackage == null)
    {
      Log.e("LoginFragment", "Cannot call LoginFragment with a null calling package. This can occur if the launchMode of the caller is singleInstance.");
      getActivity().finish();
      return;
    }
    this.loginClient.startOrContinueAuth(this.request);
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putParcelable("loginClient", this.loginClient);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/login/LoginFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */