package com.facebook.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.facebook.FacebookActivity;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import java.util.UUID;

public class DialogPresenter
{
  public static boolean canPresentNativeDialogWithFeature(DialogFeature paramDialogFeature)
  {
    return getProtocolVersionForNativeDialog(paramDialogFeature) != -1;
  }
  
  public static boolean canPresentWebFallbackDialogWithFeature(DialogFeature paramDialogFeature)
  {
    return getDialogWebFallbackUri(paramDialogFeature) != null;
  }
  
  private static Uri getDialogWebFallbackUri(DialogFeature paramDialogFeature)
  {
    Object localObject = paramDialogFeature.name();
    paramDialogFeature = paramDialogFeature.getAction();
    localObject = Utility.getDialogFeatureConfig(FacebookSdk.getApplicationId(), paramDialogFeature, (String)localObject);
    paramDialogFeature = null;
    if (localObject != null) {
      paramDialogFeature = ((Utility.DialogFeatureConfig)localObject).getFallbackUrl();
    }
    return paramDialogFeature;
  }
  
  public static int getProtocolVersionForNativeDialog(DialogFeature paramDialogFeature)
  {
    String str1 = FacebookSdk.getApplicationId();
    String str2 = paramDialogFeature.getAction();
    return NativeProtocol.getLatestAvailableProtocolVersionForAction(str2, getVersionSpecForFeature(str1, str2, paramDialogFeature));
  }
  
  private static int[] getVersionSpecForFeature(String paramString1, String paramString2, DialogFeature paramDialogFeature)
  {
    paramString1 = Utility.getDialogFeatureConfig(paramString1, paramString2, paramDialogFeature.name());
    if (paramString1 != null) {
      return paramString1.getVersionSpec();
    }
    return new int[] { paramDialogFeature.getMinVersion() };
  }
  
  public static void logDialogActivity(Context paramContext, String paramString1, String paramString2)
  {
    paramContext = AppEventsLogger.newLogger(paramContext);
    Bundle localBundle = new Bundle();
    localBundle.putString("fb_dialog_outcome", paramString2);
    paramContext.logSdkEvent(paramString1, null, localBundle);
  }
  
  public static void present(AppCall paramAppCall, Activity paramActivity)
  {
    paramActivity.startActivityForResult(paramAppCall.getRequestIntent(), paramAppCall.getRequestCode());
    paramAppCall.setPending();
  }
  
  public static void present(AppCall paramAppCall, Fragment paramFragment)
  {
    paramFragment.startActivityForResult(paramAppCall.getRequestIntent(), paramAppCall.getRequestCode());
    paramAppCall.setPending();
  }
  
  public static void setupAppCallForCannotShowError(AppCall paramAppCall)
  {
    setupAppCallForValidationError(paramAppCall, new FacebookException("Unable to show the provided content via the web or the installed version of the Facebook app. Some dialogs are only supported starting API 14."));
  }
  
  public static void setupAppCallForErrorResult(AppCall paramAppCall, FacebookException paramFacebookException)
  {
    if (paramFacebookException == null) {
      return;
    }
    Validate.hasFacebookActivity(FacebookSdk.getApplicationContext());
    Intent localIntent = new Intent();
    localIntent.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
    localIntent.setAction(FacebookActivity.PASS_THROUGH_CANCEL_ACTION);
    NativeProtocol.setupProtocolRequestIntent(localIntent, paramAppCall.getCallId().toString(), null, NativeProtocol.getLatestKnownVersion(), NativeProtocol.createBundleForException(paramFacebookException));
    paramAppCall.setRequestIntent(localIntent);
  }
  
  public static void setupAppCallForNativeDialog(AppCall paramAppCall, ParameterProvider paramParameterProvider, DialogFeature paramDialogFeature)
  {
    Context localContext = FacebookSdk.getApplicationContext();
    String str = paramDialogFeature.getAction();
    int i = getProtocolVersionForNativeDialog(paramDialogFeature);
    if (i == -1) {
      throw new FacebookException("Cannot present this dialog. This likely means that the Facebook app is not installed.");
    }
    if (NativeProtocol.isVersionCompatibleWithBucketedIntent(i)) {}
    for (paramParameterProvider = paramParameterProvider.getParameters();; paramParameterProvider = paramParameterProvider.getLegacyParameters())
    {
      paramDialogFeature = paramParameterProvider;
      if (paramParameterProvider == null) {
        paramDialogFeature = new Bundle();
      }
      paramParameterProvider = NativeProtocol.createPlatformActivityIntent(localContext, paramAppCall.getCallId().toString(), str, i, paramDialogFeature);
      if (paramParameterProvider != null) {
        break;
      }
      throw new FacebookException("Unable to create Intent; this likely means theFacebook app is not installed.");
    }
    paramAppCall.setRequestIntent(paramParameterProvider);
  }
  
  public static void setupAppCallForValidationError(AppCall paramAppCall, FacebookException paramFacebookException)
  {
    setupAppCallForErrorResult(paramAppCall, paramFacebookException);
  }
  
  public static void setupAppCallForWebDialog(AppCall paramAppCall, String paramString, Bundle paramBundle)
  {
    Validate.hasFacebookActivity(FacebookSdk.getApplicationContext());
    Validate.hasInternetPermissions(FacebookSdk.getApplicationContext());
    Bundle localBundle = new Bundle();
    localBundle.putString("action", paramString);
    localBundle.putBundle("params", paramBundle);
    paramBundle = new Intent();
    NativeProtocol.setupProtocolRequestIntent(paramBundle, paramAppCall.getCallId().toString(), paramString, NativeProtocol.getLatestKnownVersion(), localBundle);
    paramBundle.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
    paramBundle.setAction("FacebookDialogFragment");
    paramAppCall.setRequestIntent(paramBundle);
  }
  
  public static void setupAppCallForWebFallbackDialog(AppCall paramAppCall, Bundle paramBundle, DialogFeature paramDialogFeature)
  {
    Validate.hasFacebookActivity(FacebookSdk.getApplicationContext());
    Validate.hasInternetPermissions(FacebookSdk.getApplicationContext());
    String str = paramDialogFeature.name();
    Object localObject = getDialogWebFallbackUri(paramDialogFeature);
    if (localObject == null) {
      throw new FacebookException("Unable to fetch the Url for the DialogFeature : '" + str + "'");
    }
    int i = NativeProtocol.getLatestKnownVersion();
    paramBundle = ServerProtocol.getQueryParamsForPlatformActivityIntentWebFallback(paramAppCall.getCallId().toString(), i, paramBundle);
    if (paramBundle == null) {
      throw new FacebookException("Unable to fetch the app's key-hash");
    }
    if (((Uri)localObject).isRelative()) {}
    for (paramBundle = Utility.buildUri(ServerProtocol.getDialogAuthority(), ((Uri)localObject).toString(), paramBundle);; paramBundle = Utility.buildUri(((Uri)localObject).getAuthority(), ((Uri)localObject).getPath(), paramBundle))
    {
      localObject = new Bundle();
      ((Bundle)localObject).putString("url", paramBundle.toString());
      ((Bundle)localObject).putBoolean("is_fallback", true);
      paramBundle = new Intent();
      NativeProtocol.setupProtocolRequestIntent(paramBundle, paramAppCall.getCallId().toString(), paramDialogFeature.getAction(), NativeProtocol.getLatestKnownVersion(), (Bundle)localObject);
      paramBundle.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
      paramBundle.setAction("FacebookDialogFragment");
      paramAppCall.setRequestIntent(paramBundle);
      return;
    }
  }
  
  public static abstract interface ParameterProvider
  {
    public abstract Bundle getLegacyParameters();
    
    public abstract Bundle getParameters();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/internal/DialogPresenter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */