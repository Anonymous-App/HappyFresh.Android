package com.facebook.share.internal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.Callback;
import com.facebook.GraphRequest.ParcelableResourceWithMimeType;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.CallbackManagerImpl.Callback;
import com.facebook.internal.GraphUtil;
import com.facebook.internal.NativeAppCallAttachmentStore;
import com.facebook.internal.NativeAppCallAttachmentStore.Attachment;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.Mapper;
import com.facebook.share.Sharer.Result;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.LikeView.ObjectType;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ShareInternalUtility
{
  private static final String CAPTION_PARAM = "caption";
  private static final String MY_ACTION_FORMAT = "me/%s";
  private static final String MY_FEED = "me/feed";
  private static final String MY_OBJECTS_FORMAT = "me/objects/%s";
  private static final String MY_PHOTOS = "me/photos";
  private static final String MY_STAGING_RESOURCES = "me/staging_resources";
  private static final String OBJECT_PARAM = "object";
  private static final String PICTURE_PARAM = "picture";
  private static final String STAGING_PARAM = "file";
  
  private static AppCall getAppCallFromActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    paramIntent = NativeProtocol.getCallIdFromIntent(paramIntent);
    if (paramIntent == null) {
      return null;
    }
    return AppCall.finishPendingCall(paramIntent, paramInt1);
  }
  
  private static NativeAppCallAttachmentStore.Attachment getAttachment(UUID paramUUID, SharePhoto paramSharePhoto)
  {
    Bitmap localBitmap = paramSharePhoto.getBitmap();
    Uri localUri = paramSharePhoto.getImageUrl();
    paramSharePhoto = null;
    if (localBitmap != null) {
      paramSharePhoto = NativeAppCallAttachmentStore.createAttachment(paramUUID, localBitmap);
    }
    while (localUri == null) {
      return paramSharePhoto;
    }
    return NativeAppCallAttachmentStore.createAttachment(paramUUID, localUri);
  }
  
  public static Pair<String, String> getFieldNameAndNamespaceFromFullName(String paramString)
  {
    String str = null;
    int i = paramString.indexOf(':');
    if ((i != -1) && (paramString.length() > i + 1))
    {
      str = paramString.substring(0, i);
      paramString = paramString.substring(i + 1);
    }
    for (;;)
    {
      return new Pair(str, paramString);
    }
  }
  
  @Nullable
  public static LikeView.ObjectType getMostSpecificObjectType(LikeView.ObjectType paramObjectType1, LikeView.ObjectType paramObjectType2)
  {
    if (paramObjectType1 == paramObjectType2) {}
    do
    {
      return paramObjectType1;
      if (paramObjectType1 == LikeView.ObjectType.UNKNOWN) {
        return paramObjectType2;
      }
    } while (paramObjectType2 == LikeView.ObjectType.UNKNOWN);
    return null;
  }
  
  public static String getNativeDialogCompletionGesture(Bundle paramBundle)
  {
    if (paramBundle.containsKey("completionGesture")) {
      return paramBundle.getString("completionGesture");
    }
    return paramBundle.getString("com.facebook.platform.extra.COMPLETION_GESTURE");
  }
  
  public static boolean getNativeDialogDidComplete(Bundle paramBundle)
  {
    if (paramBundle.containsKey("didComplete")) {
      return paramBundle.getBoolean("didComplete");
    }
    return paramBundle.getBoolean("com.facebook.platform.extra.DID_COMPLETE", false);
  }
  
  public static List<String> getPhotoUrls(SharePhotoContent paramSharePhotoContent, UUID paramUUID)
  {
    if (paramSharePhotoContent != null)
    {
      paramSharePhotoContent = paramSharePhotoContent.getPhotos();
      if (paramSharePhotoContent != null) {}
    }
    else
    {
      return null;
    }
    paramSharePhotoContent = Utility.map(paramSharePhotoContent, new Utility.Mapper()
    {
      public NativeAppCallAttachmentStore.Attachment apply(SharePhoto paramAnonymousSharePhoto)
      {
        return ShareInternalUtility.getAttachment(this.val$appCallId, paramAnonymousSharePhoto);
      }
    });
    paramUUID = Utility.map(paramSharePhotoContent, new Utility.Mapper()
    {
      public String apply(NativeAppCallAttachmentStore.Attachment paramAnonymousAttachment)
      {
        return paramAnonymousAttachment.getAttachmentUrl();
      }
    });
    NativeAppCallAttachmentStore.addAttachments(paramSharePhotoContent);
    return paramUUID;
  }
  
  public static String getShareDialogPostId(Bundle paramBundle)
  {
    if (paramBundle.containsKey("postId")) {
      return paramBundle.getString("postId");
    }
    if (paramBundle.containsKey("com.facebook.platform.extra.POST_ID")) {
      return paramBundle.getString("com.facebook.platform.extra.POST_ID");
    }
    return paramBundle.getString("post_id");
  }
  
  public static ResultProcessor getShareResultProcessor(final FacebookCallback<Sharer.Result> paramFacebookCallback)
  {
    new ResultProcessor(paramFacebookCallback)
    {
      public void onCancel(AppCall paramAnonymousAppCall)
      {
        ShareInternalUtility.invokeOnCancelCallback(paramFacebookCallback);
      }
      
      public void onError(AppCall paramAnonymousAppCall, FacebookException paramAnonymousFacebookException)
      {
        ShareInternalUtility.invokeOnErrorCallback(paramFacebookCallback, paramAnonymousFacebookException);
      }
      
      public void onSuccess(AppCall paramAnonymousAppCall, Bundle paramAnonymousBundle)
      {
        if (paramAnonymousBundle != null)
        {
          paramAnonymousAppCall = ShareInternalUtility.getNativeDialogCompletionGesture(paramAnonymousBundle);
          if ((paramAnonymousAppCall == null) || ("post".equalsIgnoreCase(paramAnonymousAppCall)))
          {
            paramAnonymousAppCall = ShareInternalUtility.getShareDialogPostId(paramAnonymousBundle);
            ShareInternalUtility.invokeOnSuccessCallback(paramFacebookCallback, paramAnonymousAppCall);
          }
        }
        else
        {
          return;
        }
        if ("cancel".equalsIgnoreCase(paramAnonymousAppCall))
        {
          ShareInternalUtility.invokeOnCancelCallback(paramFacebookCallback);
          return;
        }
        ShareInternalUtility.invokeOnErrorCallback(paramFacebookCallback, new FacebookException("UnknownError"));
      }
    };
  }
  
  public static boolean handleActivityResult(int paramInt1, int paramInt2, Intent paramIntent, ResultProcessor paramResultProcessor)
  {
    boolean bool = true;
    AppCall localAppCall = getAppCallFromActivityResult(paramInt1, paramInt2, paramIntent);
    if (localAppCall == null) {
      bool = false;
    }
    do
    {
      return bool;
      NativeAppCallAttachmentStore.cleanupAttachmentsForCall(localAppCall.getCallId());
    } while (paramResultProcessor == null);
    FacebookException localFacebookException = NativeProtocol.getExceptionFromErrorData(NativeProtocol.getErrorDataFromResultIntent(paramIntent));
    if (localFacebookException != null)
    {
      if ((localFacebookException instanceof FacebookOperationCanceledException))
      {
        paramResultProcessor.onCancel(localAppCall);
        return true;
      }
      paramResultProcessor.onError(localAppCall, localFacebookException);
      return true;
    }
    paramResultProcessor.onSuccess(localAppCall, NativeProtocol.getSuccessResultsFromIntent(paramIntent));
    return true;
  }
  
  public static void invokeCallbackWithError(FacebookCallback<Sharer.Result> paramFacebookCallback, String paramString)
  {
    invokeOnErrorCallback(paramFacebookCallback, paramString);
  }
  
  public static void invokeCallbackWithException(FacebookCallback<Sharer.Result> paramFacebookCallback, Exception paramException)
  {
    if ((paramException instanceof FacebookException))
    {
      invokeOnErrorCallback(paramFacebookCallback, (FacebookException)paramException);
      return;
    }
    invokeCallbackWithError(paramFacebookCallback, "Error preparing share content: " + paramException.getLocalizedMessage());
  }
  
  public static void invokeCallbackWithResults(FacebookCallback<Sharer.Result> paramFacebookCallback, String paramString, GraphResponse paramGraphResponse)
  {
    Object localObject = paramGraphResponse.getError();
    if (localObject != null)
    {
      localObject = ((FacebookRequestError)localObject).getErrorMessage();
      paramString = (String)localObject;
      if (Utility.isNullOrEmpty((String)localObject)) {
        paramString = "Unexpected error sharing.";
      }
      invokeOnErrorCallback(paramFacebookCallback, paramGraphResponse, paramString);
      return;
    }
    invokeOnSuccessCallback(paramFacebookCallback, paramString);
  }
  
  static void invokeOnCancelCallback(FacebookCallback<Sharer.Result> paramFacebookCallback)
  {
    logShareResult("cancelled", null);
    if (paramFacebookCallback != null) {
      paramFacebookCallback.onCancel();
    }
  }
  
  static void invokeOnErrorCallback(FacebookCallback<Sharer.Result> paramFacebookCallback, FacebookException paramFacebookException)
  {
    logShareResult("error", paramFacebookException.getMessage());
    if (paramFacebookCallback != null) {
      paramFacebookCallback.onError(paramFacebookException);
    }
  }
  
  static void invokeOnErrorCallback(FacebookCallback<Sharer.Result> paramFacebookCallback, GraphResponse paramGraphResponse, String paramString)
  {
    logShareResult("error", paramString);
    if (paramFacebookCallback != null) {
      paramFacebookCallback.onError(new FacebookGraphResponseException(paramGraphResponse, paramString));
    }
  }
  
  static void invokeOnErrorCallback(FacebookCallback<Sharer.Result> paramFacebookCallback, String paramString)
  {
    logShareResult("error", paramString);
    if (paramFacebookCallback != null) {
      paramFacebookCallback.onError(new FacebookException(paramString));
    }
  }
  
  static void invokeOnSuccessCallback(FacebookCallback<Sharer.Result> paramFacebookCallback, String paramString)
  {
    logShareResult("succeeded", null);
    if (paramFacebookCallback != null) {
      paramFacebookCallback.onSuccess(new Sharer.Result(paramString));
    }
  }
  
  private static void logShareResult(String paramString1, String paramString2)
  {
    AppEventsLogger localAppEventsLogger = AppEventsLogger.newLogger(FacebookSdk.getApplicationContext());
    Bundle localBundle = new Bundle();
    localBundle.putString("fb_share_dialog_outcome", paramString1);
    if (paramString2 != null) {
      localBundle.putString("error_message", paramString2);
    }
    localAppEventsLogger.logSdkEvent("fb_share_dialog_result", null, localBundle);
  }
  
  public static GraphRequest newPostOpenGraphActionRequest(AccessToken paramAccessToken, JSONObject paramJSONObject, GraphRequest.Callback paramCallback)
  {
    if (paramJSONObject == null) {
      throw new FacebookException("openGraphAction cannot be null");
    }
    String str = paramJSONObject.optString("type");
    if (Utility.isNullOrEmpty(str)) {
      throw new FacebookException("openGraphAction must have non-null 'type' property");
    }
    return GraphRequest.newPostRequest(paramAccessToken, String.format("me/%s", new Object[] { str }), paramJSONObject, paramCallback);
  }
  
  public static GraphRequest newPostOpenGraphObjectRequest(AccessToken paramAccessToken, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, JSONObject paramJSONObject, GraphRequest.Callback paramCallback)
  {
    return newPostOpenGraphObjectRequest(paramAccessToken, GraphUtil.createOpenGraphObjectForPost(paramString1, paramString2, paramString3, paramString4, paramString5, paramJSONObject, null), paramCallback);
  }
  
  public static GraphRequest newPostOpenGraphObjectRequest(AccessToken paramAccessToken, JSONObject paramJSONObject, GraphRequest.Callback paramCallback)
  {
    if (paramJSONObject == null) {
      throw new FacebookException("openGraphObject cannot be null");
    }
    if (Utility.isNullOrEmpty(paramJSONObject.optString("type"))) {
      throw new FacebookException("openGraphObject must have non-null 'type' property");
    }
    if (Utility.isNullOrEmpty(paramJSONObject.optString("title"))) {
      throw new FacebookException("openGraphObject must have non-null 'title' property");
    }
    String str = String.format("me/objects/%s", new Object[] { paramJSONObject.optString("type") });
    Bundle localBundle = new Bundle();
    localBundle.putString("object", paramJSONObject.toString());
    return new GraphRequest(paramAccessToken, str, localBundle, HttpMethod.POST, paramCallback);
  }
  
  public static GraphRequest newStatusUpdateRequest(AccessToken paramAccessToken, String paramString, GraphRequest.Callback paramCallback)
  {
    return newStatusUpdateRequest(paramAccessToken, paramString, (String)null, null, paramCallback);
  }
  
  private static GraphRequest newStatusUpdateRequest(AccessToken paramAccessToken, String paramString1, String paramString2, List<String> paramList, GraphRequest.Callback paramCallback)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("message", paramString1);
    if (paramString2 != null) {
      localBundle.putString("place", paramString2);
    }
    if ((paramList != null) && (paramList.size() > 0)) {
      localBundle.putString("tags", TextUtils.join(",", paramList));
    }
    return new GraphRequest(paramAccessToken, "me/feed", localBundle, HttpMethod.POST, paramCallback);
  }
  
  public static GraphRequest newStatusUpdateRequest(AccessToken paramAccessToken, String paramString, JSONObject paramJSONObject, List<JSONObject> paramList, GraphRequest.Callback paramCallback)
  {
    Object localObject = null;
    if (paramList != null)
    {
      ArrayList localArrayList = new ArrayList(paramList.size());
      paramList = paramList.iterator();
      for (;;)
      {
        localObject = localArrayList;
        if (!paramList.hasNext()) {
          break;
        }
        localArrayList.add(((JSONObject)paramList.next()).optString("id"));
      }
    }
    if (paramJSONObject == null) {}
    for (paramJSONObject = null;; paramJSONObject = paramJSONObject.optString("id")) {
      return newStatusUpdateRequest(paramAccessToken, paramString, paramJSONObject, (List)localObject, paramCallback);
    }
  }
  
  public static GraphRequest newUpdateOpenGraphObjectRequest(AccessToken paramAccessToken, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, JSONObject paramJSONObject, GraphRequest.Callback paramCallback)
  {
    return newUpdateOpenGraphObjectRequest(paramAccessToken, GraphUtil.createOpenGraphObjectForPost(null, paramString2, paramString3, paramString4, paramString5, paramJSONObject, paramString1), paramCallback);
  }
  
  public static GraphRequest newUpdateOpenGraphObjectRequest(AccessToken paramAccessToken, JSONObject paramJSONObject, GraphRequest.Callback paramCallback)
  {
    if (paramJSONObject == null) {
      throw new FacebookException("openGraphObject cannot be null");
    }
    String str = paramJSONObject.optString("id");
    if (str == null) {
      throw new FacebookException("openGraphObject must have an id");
    }
    Bundle localBundle = new Bundle();
    localBundle.putString("object", paramJSONObject.toString());
    return new GraphRequest(paramAccessToken, str, localBundle, HttpMethod.POST, paramCallback);
  }
  
  public static GraphRequest newUploadPhotoRequest(AccessToken paramAccessToken, Bitmap paramBitmap, String paramString, GraphRequest.Callback paramCallback)
  {
    Bundle localBundle = new Bundle(1);
    localBundle.putParcelable("picture", paramBitmap);
    if ((paramString != null) && (!paramString.isEmpty())) {
      localBundle.putString("caption", paramString);
    }
    return new GraphRequest(paramAccessToken, "me/photos", localBundle, HttpMethod.POST, paramCallback);
  }
  
  public static GraphRequest newUploadPhotoRequest(AccessToken paramAccessToken, Uri paramUri, String paramString, GraphRequest.Callback paramCallback)
    throws FileNotFoundException
  {
    if (Utility.isFileUri(paramUri)) {
      return newUploadPhotoRequest(paramAccessToken, new File(paramUri.getPath()), paramString, paramCallback);
    }
    if (!Utility.isContentUri(paramUri)) {
      throw new FacebookException("The photo Uri must be either a file:// or content:// Uri");
    }
    paramString = new Bundle(1);
    paramString.putParcelable("picture", paramUri);
    return new GraphRequest(paramAccessToken, "me/photos", paramString, HttpMethod.POST, paramCallback);
  }
  
  public static GraphRequest newUploadPhotoRequest(AccessToken paramAccessToken, File paramFile, String paramString, GraphRequest.Callback paramCallback)
    throws FileNotFoundException
  {
    paramFile = ParcelFileDescriptor.open(paramFile, 268435456);
    Bundle localBundle = new Bundle(1);
    localBundle.putParcelable("picture", paramFile);
    if ((paramString != null) && (!paramString.isEmpty())) {
      localBundle.putString("caption", paramString);
    }
    return new GraphRequest(paramAccessToken, "me/photos", localBundle, HttpMethod.POST, paramCallback);
  }
  
  public static GraphRequest newUploadStagingResourceWithImageRequest(AccessToken paramAccessToken, Bitmap paramBitmap, GraphRequest.Callback paramCallback)
  {
    Bundle localBundle = new Bundle(1);
    localBundle.putParcelable("file", paramBitmap);
    return new GraphRequest(paramAccessToken, "me/staging_resources", localBundle, HttpMethod.POST, paramCallback);
  }
  
  public static GraphRequest newUploadStagingResourceWithImageRequest(AccessToken paramAccessToken, Uri paramUri, GraphRequest.Callback paramCallback)
    throws FileNotFoundException
  {
    if (Utility.isFileUri(paramUri)) {
      return newUploadStagingResourceWithImageRequest(paramAccessToken, new File(paramUri.getPath()), paramCallback);
    }
    if (!Utility.isContentUri(paramUri)) {
      throw new FacebookException("The image Uri must be either a file:// or content:// Uri");
    }
    paramUri = new GraphRequest.ParcelableResourceWithMimeType(paramUri, "image/png");
    Bundle localBundle = new Bundle(1);
    localBundle.putParcelable("file", paramUri);
    return new GraphRequest(paramAccessToken, "me/staging_resources", localBundle, HttpMethod.POST, paramCallback);
  }
  
  public static GraphRequest newUploadStagingResourceWithImageRequest(AccessToken paramAccessToken, File paramFile, GraphRequest.Callback paramCallback)
    throws FileNotFoundException
  {
    paramFile = new GraphRequest.ParcelableResourceWithMimeType(ParcelFileDescriptor.open(paramFile, 268435456), "image/png");
    Bundle localBundle = new Bundle(1);
    localBundle.putParcelable("file", paramFile);
    return new GraphRequest(paramAccessToken, "me/staging_resources", localBundle, HttpMethod.POST, paramCallback);
  }
  
  public static void registerSharerCallback(int paramInt, CallbackManager paramCallbackManager, final FacebookCallback<Sharer.Result> paramFacebookCallback)
  {
    if (!(paramCallbackManager instanceof CallbackManagerImpl)) {
      throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
    }
    ((CallbackManagerImpl)paramCallbackManager).registerCallback(paramInt, new CallbackManagerImpl.Callback()
    {
      public boolean onActivityResult(int paramAnonymousInt, Intent paramAnonymousIntent)
      {
        return ShareInternalUtility.handleActivityResult(this.val$requestCode, paramAnonymousInt, paramAnonymousIntent, ShareInternalUtility.getShareResultProcessor(paramFacebookCallback));
      }
    });
  }
  
  public static void registerStaticShareCallback(int paramInt)
  {
    CallbackManagerImpl.registerStaticCallback(paramInt, new CallbackManagerImpl.Callback()
    {
      public boolean onActivityResult(int paramAnonymousInt, Intent paramAnonymousIntent)
      {
        return ShareInternalUtility.handleActivityResult(this.val$requestCode, paramAnonymousInt, paramAnonymousIntent, ShareInternalUtility.getShareResultProcessor(null));
      }
    });
  }
  
  public static JSONArray removeNamespacesFromOGJsonArray(JSONArray paramJSONArray, boolean paramBoolean)
    throws JSONException
  {
    JSONArray localJSONArray = new JSONArray();
    int i = 0;
    if (i < paramJSONArray.length())
    {
      Object localObject2 = paramJSONArray.get(i);
      Object localObject1;
      if ((localObject2 instanceof JSONArray)) {
        localObject1 = removeNamespacesFromOGJsonArray((JSONArray)localObject2, paramBoolean);
      }
      for (;;)
      {
        localJSONArray.put(localObject1);
        i += 1;
        break;
        localObject1 = localObject2;
        if ((localObject2 instanceof JSONObject)) {
          localObject1 = removeNamespacesFromOGJsonObject((JSONObject)localObject2, paramBoolean);
        }
      }
    }
    return localJSONArray;
  }
  
  public static JSONObject removeNamespacesFromOGJsonObject(JSONObject paramJSONObject, boolean paramBoolean)
  {
    if (paramJSONObject == null)
    {
      paramJSONObject = null;
      return paramJSONObject;
    }
    for (;;)
    {
      JSONObject localJSONObject1;
      JSONObject localJSONObject2;
      int i;
      String str;
      Object localObject2;
      Object localObject1;
      Object localObject3;
      try
      {
        localJSONObject1 = new JSONObject();
        localJSONObject2 = new JSONObject();
        JSONArray localJSONArray = paramJSONObject.names();
        i = 0;
        if (i >= localJSONArray.length()) {
          break label252;
        }
        str = localJSONArray.getString(i);
        localObject2 = paramJSONObject.get(str);
        if ((localObject2 instanceof JSONObject))
        {
          localObject1 = removeNamespacesFromOGJsonObject((JSONObject)localObject2, true);
          localObject3 = getFieldNameAndNamespaceFromFullName(str);
          localObject2 = (String)((Pair)localObject3).first;
          localObject3 = (String)((Pair)localObject3).second;
          if (!paramBoolean) {
            break label212;
          }
          if ((localObject2 != null) && (((String)localObject2).equals("fbsdk")))
          {
            localJSONObject1.put(str, localObject1);
            break label277;
          }
        }
        else
        {
          localObject1 = localObject2;
          if (!(localObject2 instanceof JSONArray)) {
            continue;
          }
          localObject1 = removeNamespacesFromOGJsonArray((JSONArray)localObject2, true);
          continue;
        }
        if ((localObject2 == null) || (((String)localObject2).equals("og"))) {
          localJSONObject1.put((String)localObject3, localObject1);
        }
      }
      catch (JSONException paramJSONObject)
      {
        throw new FacebookException("Failed to create json object from share content");
      }
      localJSONObject2.put((String)localObject3, localObject1);
      break label277;
      label212:
      if ((localObject2 != null) && (((String)localObject2).equals("fb")))
      {
        localJSONObject1.put(str, localObject1);
      }
      else
      {
        localJSONObject1.put((String)localObject3, localObject1);
        break label277;
        label252:
        paramJSONObject = localJSONObject1;
        if (localJSONObject2.length() <= 0) {
          break;
        }
        localJSONObject1.put("data", localJSONObject2);
        return localJSONObject1;
      }
      label277:
      i += 1;
    }
  }
  
  public static JSONObject toJSONObjectForCall(UUID paramUUID, ShareOpenGraphAction paramShareOpenGraphAction)
    throws JSONException
  {
    final ArrayList localArrayList = new ArrayList();
    paramUUID = OpenGraphJSONUtility.toJSONObject(paramShareOpenGraphAction, new OpenGraphJSONUtility.PhotoJSONProcessor()
    {
      public JSONObject toJSONObject(SharePhoto paramAnonymousSharePhoto)
      {
        Object localObject = ShareInternalUtility.getAttachment(this.val$callId, paramAnonymousSharePhoto);
        if (localObject == null) {
          localObject = null;
        }
        for (;;)
        {
          return (JSONObject)localObject;
          localArrayList.add(localObject);
          JSONObject localJSONObject = new JSONObject();
          try
          {
            localJSONObject.put("url", ((NativeAppCallAttachmentStore.Attachment)localObject).getAttachmentUrl());
            localObject = localJSONObject;
            if (!paramAnonymousSharePhoto.getUserGenerated()) {
              continue;
            }
            localJSONObject.put("user_generated", true);
            return localJSONObject;
          }
          catch (JSONException paramAnonymousSharePhoto)
          {
            throw new FacebookException("Unable to attach images", paramAnonymousSharePhoto);
          }
        }
      }
    });
    NativeAppCallAttachmentStore.addAttachments(localArrayList);
    return paramUUID;
  }
  
  public static JSONObject toJSONObjectForWeb(ShareOpenGraphContent paramShareOpenGraphContent)
    throws JSONException
  {
    OpenGraphJSONUtility.toJSONObject(paramShareOpenGraphContent.getAction(), new OpenGraphJSONUtility.PhotoJSONProcessor()
    {
      public JSONObject toJSONObject(SharePhoto paramAnonymousSharePhoto)
      {
        paramAnonymousSharePhoto = paramAnonymousSharePhoto.getImageUrl();
        JSONObject localJSONObject = new JSONObject();
        try
        {
          localJSONObject.put("url", paramAnonymousSharePhoto.toString());
          return localJSONObject;
        }
        catch (JSONException paramAnonymousSharePhoto)
        {
          throw new FacebookException("Unable to attach images", paramAnonymousSharePhoto);
        }
      }
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/internal/ShareInternalUtility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */