package com.facebook.internal;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

public class FacebookDialogFragment
  extends DialogFragment
{
  public static final String TAG = "FacebookDialogFragment";
  private Dialog dialog;
  
  private void onCompleteWebDialog(Bundle paramBundle, FacebookException paramFacebookException)
  {
    FragmentActivity localFragmentActivity = getActivity();
    paramBundle = NativeProtocol.createProtocolResultIntent(localFragmentActivity.getIntent(), paramBundle, paramFacebookException);
    if (paramFacebookException == null) {}
    for (int i = -1;; i = 0)
    {
      localFragmentActivity.setResult(i, paramBundle);
      localFragmentActivity.finish();
      return;
    }
  }
  
  private void onCompleteWebFallbackDialog(Bundle paramBundle)
  {
    FragmentActivity localFragmentActivity = getActivity();
    Intent localIntent = new Intent();
    Bundle localBundle = paramBundle;
    if (paramBundle == null) {
      localBundle = new Bundle();
    }
    localIntent.putExtras(localBundle);
    localFragmentActivity.setResult(-1, localIntent);
    localFragmentActivity.finish();
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    if ((this.dialog instanceof WebDialog)) {
      ((WebDialog)this.dialog).resize();
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle;
    String str;
    if (this.dialog == null)
    {
      paramBundle = getActivity();
      localBundle = NativeProtocol.getMethodArgumentsFromIntent(paramBundle.getIntent());
      if (localBundle.getBoolean("is_fallback", false)) {
        break label99;
      }
      str = localBundle.getString("action");
      localBundle = localBundle.getBundle("params");
      if (Utility.isNullOrEmpty(str))
      {
        Utility.logd("FacebookDialogFragment", "Cannot start a WebDialog with an empty/missing 'actionName'");
        paramBundle.finish();
      }
    }
    else
    {
      return;
    }
    paramBundle = new WebDialog.Builder(paramBundle, str, localBundle).setOnCompleteListener(new WebDialog.OnCompleteListener()
    {
      public void onComplete(Bundle paramAnonymousBundle, FacebookException paramAnonymousFacebookException)
      {
        FacebookDialogFragment.this.onCompleteWebDialog(paramAnonymousBundle, paramAnonymousFacebookException);
      }
    }).build();
    for (;;)
    {
      this.dialog = paramBundle;
      return;
      label99:
      str = localBundle.getString("url");
      if (Utility.isNullOrEmpty(str))
      {
        Utility.logd("FacebookDialogFragment", "Cannot start a fallback WebDialog with an empty/missing 'url'");
        paramBundle.finish();
        return;
      }
      paramBundle = new FacebookWebFallbackDialog(paramBundle, str, String.format("fb%s://bridge/", new Object[] { FacebookSdk.getApplicationId() }));
      paramBundle.setOnCompleteListener(new WebDialog.OnCompleteListener()
      {
        public void onComplete(Bundle paramAnonymousBundle, FacebookException paramAnonymousFacebookException)
        {
          FacebookDialogFragment.this.onCompleteWebFallbackDialog(paramAnonymousBundle);
        }
      });
    }
  }
  
  @NonNull
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    if (this.dialog == null)
    {
      onCompleteWebDialog(null, null);
      setShowsDialog(false);
    }
    return this.dialog;
  }
  
  public void onDestroyView()
  {
    if ((getDialog() != null) && (getRetainInstance())) {
      getDialog().setDismissMessage(null);
    }
    super.onDestroyView();
  }
  
  public void setDialog(Dialog paramDialog)
  {
    this.dialog = paramDialog;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/internal/FacebookDialogFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */