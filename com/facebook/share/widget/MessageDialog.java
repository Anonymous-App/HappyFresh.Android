package com.facebook.share.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.facebook.FacebookCallback;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;
import com.facebook.internal.DialogFeature;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.DialogPresenter.ParameterProvider;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.internal.FacebookDialogBase.ModeHandler;
import com.facebook.share.Sharer;
import com.facebook.share.Sharer.Result;
import com.facebook.share.internal.LegacyNativeDialogParameters;
import com.facebook.share.internal.MessageDialogFeature;
import com.facebook.share.internal.NativeDialogParameters;
import com.facebook.share.internal.OpenGraphMessageDialogFeature;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import java.util.ArrayList;
import java.util.List;

public final class MessageDialog
  extends FacebookDialogBase<ShareContent, Sharer.Result>
  implements Sharer
{
  private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.Message.toRequestCode();
  private boolean shouldFailOnDataError = false;
  
  public MessageDialog(Activity paramActivity)
  {
    super(paramActivity, DEFAULT_REQUEST_CODE);
    ShareInternalUtility.registerStaticShareCallback(DEFAULT_REQUEST_CODE);
  }
  
  MessageDialog(Activity paramActivity, int paramInt)
  {
    super(paramActivity, paramInt);
    ShareInternalUtility.registerStaticShareCallback(paramInt);
  }
  
  public MessageDialog(Fragment paramFragment)
  {
    super(paramFragment, DEFAULT_REQUEST_CODE);
    ShareInternalUtility.registerStaticShareCallback(DEFAULT_REQUEST_CODE);
  }
  
  MessageDialog(Fragment paramFragment, int paramInt)
  {
    super(paramFragment, paramInt);
    ShareInternalUtility.registerStaticShareCallback(paramInt);
  }
  
  public static boolean canShow(Class<? extends ShareContent> paramClass)
  {
    paramClass = getFeature(paramClass);
    return (paramClass != null) && (DialogPresenter.canPresentNativeDialogWithFeature(paramClass));
  }
  
  private static DialogFeature getFeature(Class<? extends ShareContent> paramClass)
  {
    if (ShareLinkContent.class.isAssignableFrom(paramClass)) {
      return MessageDialogFeature.MESSAGE_DIALOG;
    }
    if (SharePhotoContent.class.isAssignableFrom(paramClass)) {
      return MessageDialogFeature.PHOTOS;
    }
    if (ShareVideoContent.class.isAssignableFrom(paramClass)) {
      return MessageDialogFeature.VIDEO;
    }
    if (ShareOpenGraphContent.class.isAssignableFrom(paramClass)) {
      return OpenGraphMessageDialogFeature.OG_MESSAGE_DIALOG;
    }
    return null;
  }
  
  public static void show(Activity paramActivity, ShareContent paramShareContent)
  {
    new MessageDialog(paramActivity).show(paramShareContent);
  }
  
  public static void show(Fragment paramFragment, ShareContent paramShareContent)
  {
    new MessageDialog(paramFragment).show(paramShareContent);
  }
  
  protected AppCall createBaseAppCall()
  {
    return new AppCall(getRequestCode());
  }
  
  protected List<FacebookDialogBase<ShareContent, Sharer.Result>.ModeHandler> getOrderedModeHandlers()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new NativeHandler(null));
    return localArrayList;
  }
  
  public boolean getShouldFailOnDataError()
  {
    return this.shouldFailOnDataError;
  }
  
  protected void registerCallbackImpl(CallbackManagerImpl paramCallbackManagerImpl, FacebookCallback<Sharer.Result> paramFacebookCallback)
  {
    ShareInternalUtility.registerSharerCallback(getRequestCode(), paramCallbackManagerImpl, paramFacebookCallback);
  }
  
  public void setShouldFailOnDataError(boolean paramBoolean)
  {
    this.shouldFailOnDataError = paramBoolean;
  }
  
  private class NativeHandler
    extends FacebookDialogBase<ShareContent, Sharer.Result>.ModeHandler
  {
    private NativeHandler()
    {
      super();
    }
    
    public boolean canShow(ShareContent paramShareContent)
    {
      return (paramShareContent != null) && (MessageDialog.canShow(paramShareContent.getClass()));
    }
    
    public AppCall createAppCall(final ShareContent paramShareContent)
    {
      ShareContentValidation.validateForMessage(paramShareContent);
      final AppCall localAppCall = MessageDialog.this.createBaseAppCall();
      final boolean bool = MessageDialog.this.getShouldFailOnDataError();
      MessageDialog.this.getActivityContext();
      DialogPresenter.setupAppCallForNativeDialog(localAppCall, new DialogPresenter.ParameterProvider()
      {
        public Bundle getLegacyParameters()
        {
          return LegacyNativeDialogParameters.create(localAppCall.getCallId(), paramShareContent, bool);
        }
        
        public Bundle getParameters()
        {
          return NativeDialogParameters.create(localAppCall.getCallId(), paramShareContent, bool);
        }
      }, MessageDialog.getFeature(paramShareContent.getClass()));
      return localAppCall;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/widget/MessageDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */