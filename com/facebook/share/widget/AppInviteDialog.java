package com.facebook.share.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.facebook.FacebookCallback;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.CallbackManagerImpl.Callback;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;
import com.facebook.internal.DialogFeature;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.DialogPresenter.ParameterProvider;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.internal.FacebookDialogBase.ModeHandler;
import com.facebook.share.internal.AppInviteDialogFeature;
import com.facebook.share.internal.ResultProcessor;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.model.AppInviteContent;
import java.util.ArrayList;
import java.util.List;

public class AppInviteDialog
  extends FacebookDialogBase<AppInviteContent, Result>
{
  private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.AppInvite.toRequestCode();
  private static final String TAG = "AppInviteDialog";
  
  public AppInviteDialog(Activity paramActivity)
  {
    super(paramActivity, DEFAULT_REQUEST_CODE);
  }
  
  public AppInviteDialog(Fragment paramFragment)
  {
    super(paramFragment, DEFAULT_REQUEST_CODE);
  }
  
  public static boolean canShow()
  {
    return (canShowNativeDialog()) || (canShowWebFallback());
  }
  
  private static boolean canShowNativeDialog()
  {
    return (Build.VERSION.SDK_INT >= 14) && (DialogPresenter.canPresentNativeDialogWithFeature(getFeature()));
  }
  
  private static boolean canShowWebFallback()
  {
    return (Build.VERSION.SDK_INT >= 14) && (DialogPresenter.canPresentWebFallbackDialogWithFeature(getFeature()));
  }
  
  private static Bundle createParameters(AppInviteContent paramAppInviteContent)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("app_link_url", paramAppInviteContent.getApplinkUrl());
    localBundle.putString("preview_image_url", paramAppInviteContent.getPreviewImageUrl());
    return localBundle;
  }
  
  private static DialogFeature getFeature()
  {
    return AppInviteDialogFeature.APP_INVITES_DIALOG;
  }
  
  public static void show(Activity paramActivity, AppInviteContent paramAppInviteContent)
  {
    new AppInviteDialog(paramActivity).show(paramAppInviteContent);
  }
  
  public static void show(Fragment paramFragment, AppInviteContent paramAppInviteContent)
  {
    new AppInviteDialog(paramFragment).show(paramAppInviteContent);
  }
  
  protected AppCall createBaseAppCall()
  {
    return new AppCall(getRequestCode());
  }
  
  protected List<FacebookDialogBase<AppInviteContent, Result>.ModeHandler> getOrderedModeHandlers()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new NativeHandler(null));
    localArrayList.add(new WebFallbackHandler(null));
    return localArrayList;
  }
  
  protected void registerCallbackImpl(CallbackManagerImpl paramCallbackManagerImpl, final FacebookCallback<Result> paramFacebookCallback)
  {
    if (paramFacebookCallback == null) {}
    for (paramFacebookCallback = null;; paramFacebookCallback = new ResultProcessor(paramFacebookCallback)
        {
          public void onSuccess(AppCall paramAnonymousAppCall, Bundle paramAnonymousBundle)
          {
            if ("cancel".equalsIgnoreCase(ShareInternalUtility.getNativeDialogCompletionGesture(paramAnonymousBundle)))
            {
              paramFacebookCallback.onCancel();
              return;
            }
            paramFacebookCallback.onSuccess(new AppInviteDialog.Result(paramAnonymousBundle));
          }
        })
    {
      paramFacebookCallback = new CallbackManagerImpl.Callback()
      {
        public boolean onActivityResult(int paramAnonymousInt, Intent paramAnonymousIntent)
        {
          return ShareInternalUtility.handleActivityResult(AppInviteDialog.this.getRequestCode(), paramAnonymousInt, paramAnonymousIntent, paramFacebookCallback);
        }
      };
      paramCallbackManagerImpl.registerCallback(getRequestCode(), paramFacebookCallback);
      return;
    }
  }
  
  private class NativeHandler
    extends FacebookDialogBase<AppInviteContent, AppInviteDialog.Result>.ModeHandler
  {
    private NativeHandler()
    {
      super();
    }
    
    public boolean canShow(AppInviteContent paramAppInviteContent)
    {
      return AppInviteDialog.access$200();
    }
    
    public AppCall createAppCall(final AppInviteContent paramAppInviteContent)
    {
      AppCall localAppCall = AppInviteDialog.this.createBaseAppCall();
      DialogPresenter.setupAppCallForNativeDialog(localAppCall, new DialogPresenter.ParameterProvider()
      {
        public Bundle getLegacyParameters()
        {
          Log.e("AppInviteDialog", "Attempting to present the AppInviteDialog with an outdated Facebook app on the device");
          return new Bundle();
        }
        
        public Bundle getParameters()
        {
          return AppInviteDialog.createParameters(paramAppInviteContent);
        }
      }, AppInviteDialog.access$400());
      return localAppCall;
    }
  }
  
  public static final class Result
  {
    private final Bundle bundle;
    
    public Result(Bundle paramBundle)
    {
      this.bundle = paramBundle;
    }
    
    public Bundle getData()
    {
      return this.bundle;
    }
  }
  
  private class WebFallbackHandler
    extends FacebookDialogBase<AppInviteContent, AppInviteDialog.Result>.ModeHandler
  {
    private WebFallbackHandler()
    {
      super();
    }
    
    public boolean canShow(AppInviteContent paramAppInviteContent)
    {
      return AppInviteDialog.access$500();
    }
    
    public AppCall createAppCall(AppInviteContent paramAppInviteContent)
    {
      AppCall localAppCall = AppInviteDialog.this.createBaseAppCall();
      DialogPresenter.setupAppCallForWebFallbackDialog(localAppCall, AppInviteDialog.createParameters(paramAppInviteContent), AppInviteDialog.access$400());
      return localAppCall;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/widget/AppInviteDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */