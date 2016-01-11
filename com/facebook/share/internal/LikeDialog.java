package com.facebook.share.internal;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.facebook.FacebookCallback;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;
import com.facebook.internal.DialogFeature;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.DialogPresenter.ParameterProvider;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.internal.FacebookDialogBase.ModeHandler;
import java.util.ArrayList;
import java.util.List;

public class LikeDialog
  extends FacebookDialogBase<LikeContent, Result>
{
  private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.Like.toRequestCode();
  private static final String TAG = "LikeDialog";
  
  LikeDialog(Activity paramActivity)
  {
    super(paramActivity, DEFAULT_REQUEST_CODE);
  }
  
  LikeDialog(Fragment paramFragment)
  {
    super(paramFragment, DEFAULT_REQUEST_CODE);
  }
  
  public static boolean canShowNativeDialog()
  {
    return (Build.VERSION.SDK_INT >= 14) && (DialogPresenter.canPresentNativeDialogWithFeature(getFeature()));
  }
  
  public static boolean canShowWebFallback()
  {
    return (Build.VERSION.SDK_INT >= 14) && (DialogPresenter.canPresentWebFallbackDialogWithFeature(getFeature()));
  }
  
  private static Bundle createParameters(LikeContent paramLikeContent)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("object_id", paramLikeContent.getObjectId());
    localBundle.putString("object_type", paramLikeContent.getObjectType());
    return localBundle;
  }
  
  private static DialogFeature getFeature()
  {
    return LikeDialogFeature.LIKE_DIALOG;
  }
  
  protected AppCall createBaseAppCall()
  {
    return new AppCall(getRequestCode());
  }
  
  protected List<FacebookDialogBase<LikeContent, Result>.ModeHandler> getOrderedModeHandlers()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new NativeHandler(null));
    localArrayList.add(new WebFallbackHandler(null));
    return localArrayList;
  }
  
  protected void registerCallbackImpl(CallbackManagerImpl paramCallbackManagerImpl, FacebookCallback<Result> paramFacebookCallback)
  {
    throw new UnsupportedOperationException("registerCallback is not supported for LikeDialog");
  }
  
  private class NativeHandler
    extends FacebookDialogBase<LikeContent, LikeDialog.Result>.ModeHandler
  {
    private NativeHandler()
    {
      super();
    }
    
    public boolean canShow(LikeContent paramLikeContent)
    {
      return (paramLikeContent != null) && (LikeDialog.canShowNativeDialog());
    }
    
    public AppCall createAppCall(final LikeContent paramLikeContent)
    {
      AppCall localAppCall = LikeDialog.this.createBaseAppCall();
      DialogPresenter.setupAppCallForNativeDialog(localAppCall, new DialogPresenter.ParameterProvider()
      {
        public Bundle getLegacyParameters()
        {
          Log.e("LikeDialog", "Attempting to present the Like Dialog with an outdated Facebook app on the device");
          return new Bundle();
        }
        
        public Bundle getParameters()
        {
          return LikeDialog.createParameters(paramLikeContent);
        }
      }, LikeDialog.access$300());
      return localAppCall;
    }
  }
  
  public static final class Result {}
  
  private class WebFallbackHandler
    extends FacebookDialogBase<LikeContent, LikeDialog.Result>.ModeHandler
  {
    private WebFallbackHandler()
    {
      super();
    }
    
    public boolean canShow(LikeContent paramLikeContent)
    {
      return (paramLikeContent != null) && (LikeDialog.canShowWebFallback());
    }
    
    public AppCall createAppCall(LikeContent paramLikeContent)
    {
      AppCall localAppCall = LikeDialog.this.createBaseAppCall();
      DialogPresenter.setupAppCallForWebFallbackDialog(localAppCall, LikeDialog.createParameters(paramLikeContent), LikeDialog.access$300());
      return localAppCall;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/internal/LikeDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */