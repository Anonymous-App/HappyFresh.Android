package com.facebook.share;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.Callback;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.internal.CollectionMapper;
import com.facebook.internal.CollectionMapper.Collection;
import com.facebook.internal.CollectionMapper.OnErrorListener;
import com.facebook.internal.CollectionMapper.OnMapValueCompleteListener;
import com.facebook.internal.CollectionMapper.OnMapperCompleteListener;
import com.facebook.internal.CollectionMapper.ValueMapper;
import com.facebook.internal.Mutable;
import com.facebook.internal.Utility;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.internal.VideoUploader;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ShareApi
{
  private static final String TAG = "ShareApi";
  private String message;
  private final ShareContent shareContent;
  
  public ShareApi(ShareContent paramShareContent)
  {
    this.shareContent = paramShareContent;
  }
  
  private void addCommonParameters(Bundle paramBundle, ShareContent paramShareContent)
  {
    List localList = paramShareContent.getPeopleIds();
    if ((localList != null) && (!localList.isEmpty())) {
      paramBundle.putString("tags", TextUtils.join(", ", localList));
    }
    paramBundle.putString("place", paramShareContent.getPlaceId());
    paramBundle.putString("ref", paramShareContent.getRef());
  }
  
  private static void handleImagesOnAction(Bundle paramBundle)
  {
    String str = paramBundle.getString("image");
    if (str != null) {}
    for (;;)
    {
      int i;
      try
      {
        JSONArray localJSONArray = new JSONArray(str);
        i = 0;
        if (i < localJSONArray.length())
        {
          Object localObject = localJSONArray.optJSONObject(i);
          if (localObject != null)
          {
            putImageInBundleWithArrayFormat(paramBundle, i, (JSONObject)localObject);
          }
          else
          {
            localObject = localJSONArray.getString(i);
            paramBundle.putString(String.format(Locale.ROOT, "image[%d][url]", new Object[] { Integer.valueOf(i) }), (String)localObject);
          }
        }
      }
      catch (JSONException localJSONException) {}
      try
      {
        putImageInBundleWithArrayFormat(paramBundle, 0, new JSONObject(str));
        paramBundle.remove("image");
        return;
      }
      catch (JSONException paramBundle)
      {
        return;
      }
      paramBundle.remove("image");
      return;
      i += 1;
    }
  }
  
  private static void putImageInBundleWithArrayFormat(Bundle paramBundle, int paramInt, JSONObject paramJSONObject)
    throws JSONException
  {
    Iterator localIterator = paramJSONObject.keys();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      paramBundle.putString(String.format(Locale.ROOT, "image[%d][%s]", new Object[] { Integer.valueOf(paramInt), str }), paramJSONObject.get(str).toString());
    }
  }
  
  public static void share(ShareContent paramShareContent, FacebookCallback<Sharer.Result> paramFacebookCallback)
  {
    new ShareApi(paramShareContent).share(paramFacebookCallback);
  }
  
  private void shareLinkContent(ShareLinkContent paramShareLinkContent, final FacebookCallback<Sharer.Result> paramFacebookCallback)
  {
    paramFacebookCallback = new GraphRequest.Callback()
    {
      public void onCompleted(GraphResponse paramAnonymousGraphResponse)
      {
        Object localObject = paramAnonymousGraphResponse.getJSONObject();
        if (localObject == null) {}
        for (localObject = null;; localObject = ((JSONObject)localObject).optString("id"))
        {
          ShareInternalUtility.invokeCallbackWithResults(paramFacebookCallback, (String)localObject, paramAnonymousGraphResponse);
          return;
        }
      }
    };
    Bundle localBundle = new Bundle();
    addCommonParameters(localBundle, paramShareLinkContent);
    localBundle.putString("message", getMessage());
    localBundle.putString("link", Utility.getUriString(paramShareLinkContent.getContentUrl()));
    localBundle.putString("picture", Utility.getUriString(paramShareLinkContent.getImageUrl()));
    localBundle.putString("name", paramShareLinkContent.getContentTitle());
    localBundle.putString("description", paramShareLinkContent.getContentDescription());
    localBundle.putString("ref", paramShareLinkContent.getRef());
    new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/feed", localBundle, HttpMethod.POST, paramFacebookCallback).executeAsync();
  }
  
  private void shareOpenGraphContent(ShareOpenGraphContent paramShareOpenGraphContent, final FacebookCallback<Sharer.Result> paramFacebookCallback)
  {
    final GraphRequest.Callback local1 = new GraphRequest.Callback()
    {
      public void onCompleted(GraphResponse paramAnonymousGraphResponse)
      {
        Object localObject = paramAnonymousGraphResponse.getJSONObject();
        if (localObject == null) {}
        for (localObject = null;; localObject = ((JSONObject)localObject).optString("id"))
        {
          ShareInternalUtility.invokeCallbackWithResults(paramFacebookCallback, (String)localObject, paramAnonymousGraphResponse);
          return;
        }
      }
    };
    final ShareOpenGraphAction localShareOpenGraphAction = paramShareOpenGraphContent.getAction();
    final Bundle localBundle = localShareOpenGraphAction.getBundle();
    addCommonParameters(localBundle, paramShareOpenGraphContent);
    localBundle.putString("message", getMessage());
    stageOpenGraphAction(localBundle, new CollectionMapper.OnMapperCompleteListener()
    {
      public void onComplete()
      {
        try
        {
          ShareApi.handleImagesOnAction(localBundle);
          new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/" + URLEncoder.encode(localShareOpenGraphAction.getActionType(), "UTF-8"), localBundle, HttpMethod.POST, local1).executeAsync();
          return;
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          ShareInternalUtility.invokeCallbackWithException(paramFacebookCallback, localUnsupportedEncodingException);
        }
      }
      
      public void onError(FacebookException paramAnonymousFacebookException)
      {
        ShareInternalUtility.invokeCallbackWithException(paramFacebookCallback, paramAnonymousFacebookException);
      }
    });
  }
  
  private void sharePhotoContent(SharePhotoContent paramSharePhotoContent, final FacebookCallback<Sharer.Result> paramFacebookCallback)
  {
    final Mutable localMutable = new Mutable(Integer.valueOf(0));
    AccessToken localAccessToken = AccessToken.getCurrentAccessToken();
    ArrayList localArrayList = new ArrayList();
    GraphRequest.Callback local3 = new GraphRequest.Callback()
    {
      public void onCompleted(GraphResponse paramAnonymousGraphResponse)
      {
        Object localObject = paramAnonymousGraphResponse.getJSONObject();
        if (localObject != null) {
          this.val$results.add(localObject);
        }
        if (paramAnonymousGraphResponse.getError() != null) {
          this.val$errorResponses.add(paramAnonymousGraphResponse);
        }
        localMutable.value = Integer.valueOf(((Integer)localMutable.value).intValue() - 1);
        if (((Integer)localMutable.value).intValue() == 0)
        {
          if (this.val$errorResponses.isEmpty()) {
            break label105;
          }
          ShareInternalUtility.invokeCallbackWithResults(paramFacebookCallback, null, (GraphResponse)this.val$errorResponses.get(0));
        }
        label105:
        while (this.val$results.isEmpty()) {
          return;
        }
        localObject = ((JSONObject)this.val$results.get(0)).optString("id");
        ShareInternalUtility.invokeCallbackWithResults(paramFacebookCallback, (String)localObject, paramAnonymousGraphResponse);
      }
    };
    Uri localUri;
    try
    {
      Iterator localIterator = paramSharePhotoContent.getPhotos().iterator();
      for (;;)
      {
        if (localIterator.hasNext())
        {
          paramSharePhotoContent = (SharePhoto)localIterator.next();
          Bitmap localBitmap = paramSharePhotoContent.getBitmap();
          localUri = paramSharePhotoContent.getImageUrl();
          String str = paramSharePhotoContent.getCaption();
          paramSharePhotoContent = str;
          if (str == null) {
            paramSharePhotoContent = getMessage();
          }
          if (localBitmap != null)
          {
            localArrayList.add(ShareInternalUtility.newUploadPhotoRequest(localAccessToken, localBitmap, paramSharePhotoContent, local3));
            continue;
          }
        }
      }
    }
    catch (FileNotFoundException paramSharePhotoContent)
    {
      ShareInternalUtility.invokeCallbackWithException(paramFacebookCallback, paramSharePhotoContent);
    }
    while (localUri != null)
    {
      localArrayList.add(ShareInternalUtility.newUploadPhotoRequest(localAccessToken, localUri, paramSharePhotoContent, local3));
      break;
      localMutable.value = Integer.valueOf(((Integer)localMutable.value).intValue() + localArrayList.size());
      paramSharePhotoContent = localArrayList.iterator();
      while (paramSharePhotoContent.hasNext()) {
        ((GraphRequest)paramSharePhotoContent.next()).executeAsync();
      }
    }
  }
  
  private void shareVideoContent(ShareVideoContent paramShareVideoContent, FacebookCallback<Sharer.Result> paramFacebookCallback)
  {
    try
    {
      VideoUploader.uploadAsync(paramShareVideoContent, paramFacebookCallback);
      return;
    }
    catch (FileNotFoundException paramShareVideoContent)
    {
      ShareInternalUtility.invokeCallbackWithException(paramFacebookCallback, paramShareVideoContent);
    }
  }
  
  private static void stageArrayList(ArrayList paramArrayList, CollectionMapper.OnMapValueCompleteListener paramOnMapValueCompleteListener)
  {
    final JSONArray localJSONArray = new JSONArray();
    stageCollectionValues(new CollectionMapper.Collection()new CollectionMapper.OnMapperCompleteListener
    {
      public Object get(Integer paramAnonymousInteger)
      {
        return this.val$arrayList.get(paramAnonymousInteger.intValue());
      }
      
      public Iterator<Integer> keyIterator()
      {
        final int i = this.val$arrayList.size();
        new Iterator()
        {
          public boolean hasNext()
          {
            return ((Integer)this.val$current.value).intValue() < i;
          }
          
          public Integer next()
          {
            Integer localInteger = (Integer)this.val$current.value;
            Mutable localMutable = this.val$current;
            localMutable.value = Integer.valueOf(((Integer)localMutable.value).intValue() + 1);
            return localInteger;
          }
          
          public void remove() {}
        };
      }
      
      public void set(Integer paramAnonymousInteger, Object paramAnonymousObject, CollectionMapper.OnErrorListener paramAnonymousOnErrorListener)
      {
        try
        {
          localJSONArray.put(paramAnonymousInteger.intValue(), paramAnonymousObject);
          return;
        }
        catch (JSONException paramAnonymousInteger)
        {
          paramAnonymousObject = paramAnonymousInteger.getLocalizedMessage();
          paramAnonymousInteger = (Integer)paramAnonymousObject;
          if (paramAnonymousObject == null) {
            paramAnonymousInteger = "Error staging object.";
          }
          paramAnonymousOnErrorListener.onError(new FacebookException(paramAnonymousInteger));
        }
      }
    }, new CollectionMapper.OnMapperCompleteListener()
    {
      public void onComplete()
      {
        this.val$onArrayListStagedListener.onComplete(localJSONArray);
      }
      
      public void onError(FacebookException paramAnonymousFacebookException)
      {
        this.val$onArrayListStagedListener.onError(paramAnonymousFacebookException);
      }
    });
  }
  
  private static <T> void stageCollectionValues(CollectionMapper.Collection<T> paramCollection, CollectionMapper.OnMapperCompleteListener paramOnMapperCompleteListener)
  {
    CollectionMapper.iterate(paramCollection, new CollectionMapper.ValueMapper()
    {
      public void mapValue(Object paramAnonymousObject, CollectionMapper.OnMapValueCompleteListener paramAnonymousOnMapValueCompleteListener)
      {
        if ((paramAnonymousObject instanceof ArrayList))
        {
          ShareApi.stageArrayList((ArrayList)paramAnonymousObject, paramAnonymousOnMapValueCompleteListener);
          return;
        }
        if ((paramAnonymousObject instanceof ShareOpenGraphObject))
        {
          ShareApi.stageOpenGraphObject((ShareOpenGraphObject)paramAnonymousObject, paramAnonymousOnMapValueCompleteListener);
          return;
        }
        if ((paramAnonymousObject instanceof SharePhoto))
        {
          ShareApi.stagePhoto((SharePhoto)paramAnonymousObject, paramAnonymousOnMapValueCompleteListener);
          return;
        }
        paramAnonymousOnMapValueCompleteListener.onComplete(paramAnonymousObject);
      }
    }, paramOnMapperCompleteListener);
  }
  
  private static void stageOpenGraphAction(Bundle paramBundle, CollectionMapper.OnMapperCompleteListener paramOnMapperCompleteListener)
  {
    stageCollectionValues(new CollectionMapper.Collection()
    {
      public Object get(String paramAnonymousString)
      {
        return this.val$parameters.get(paramAnonymousString);
      }
      
      public Iterator<String> keyIterator()
      {
        return this.val$parameters.keySet().iterator();
      }
      
      public void set(String paramAnonymousString, Object paramAnonymousObject, CollectionMapper.OnErrorListener paramAnonymousOnErrorListener)
      {
        if (!Utility.putJSONValueInBundle(this.val$parameters, paramAnonymousString, paramAnonymousObject)) {
          paramAnonymousOnErrorListener.onError(new FacebookException("Unexpected value: " + paramAnonymousObject.toString()));
        }
      }
    }, paramOnMapperCompleteListener);
  }
  
  private static void stageOpenGraphObject(ShareOpenGraphObject paramShareOpenGraphObject, final CollectionMapper.OnMapValueCompleteListener paramOnMapValueCompleteListener)
  {
    final Object localObject2 = paramShareOpenGraphObject.getString("type");
    final Object localObject1 = localObject2;
    if (localObject2 == null) {
      localObject1 = paramShareOpenGraphObject.getString("og:type");
    }
    if (localObject1 == null)
    {
      paramOnMapValueCompleteListener.onError(new FacebookException("Open Graph objects must contain a type value."));
      return;
    }
    localObject2 = new JSONObject();
    stageCollectionValues(new CollectionMapper.Collection()new CollectionMapper.OnMapperCompleteListener
    {
      public Object get(String paramAnonymousString)
      {
        return this.val$object.get(paramAnonymousString);
      }
      
      public Iterator<String> keyIterator()
      {
        return this.val$object.keySet().iterator();
      }
      
      public void set(String paramAnonymousString, Object paramAnonymousObject, CollectionMapper.OnErrorListener paramAnonymousOnErrorListener)
      {
        try
        {
          localObject2.put(paramAnonymousString, paramAnonymousObject);
          return;
        }
        catch (JSONException paramAnonymousString)
        {
          paramAnonymousObject = paramAnonymousString.getLocalizedMessage();
          paramAnonymousString = (String)paramAnonymousObject;
          if (paramAnonymousObject == null) {
            paramAnonymousString = "Error staging object.";
          }
          paramAnonymousOnErrorListener.onError(new FacebookException(paramAnonymousString));
        }
      }
    }, new CollectionMapper.OnMapperCompleteListener()
    {
      public void onCompleted(GraphResponse paramAnonymousGraphResponse)
      {
        Object localObject = paramAnonymousGraphResponse.getError();
        if (localObject != null)
        {
          String str = ((FacebookRequestError)localObject).getErrorMessage();
          localObject = str;
          if (str == null) {
            localObject = "Error staging Open Graph object.";
          }
          this.val$onOpenGraphObjectStagedListener.onError(new FacebookGraphResponseException(paramAnonymousGraphResponse, (String)localObject));
          return;
        }
        localObject = paramAnonymousGraphResponse.getJSONObject();
        if (localObject == null)
        {
          this.val$onOpenGraphObjectStagedListener.onError(new FacebookGraphResponseException(paramAnonymousGraphResponse, "Error staging Open Graph object."));
          return;
        }
        localObject = ((JSONObject)localObject).optString("id");
        if (localObject == null)
        {
          this.val$onOpenGraphObjectStagedListener.onError(new FacebookGraphResponseException(paramAnonymousGraphResponse, "Error staging Open Graph object."));
          return;
        }
        this.val$onOpenGraphObjectStagedListener.onComplete(localObject);
      }
    }
    {
      public void onComplete()
      {
        String str = this.val$stagedObject.toString();
        Object localObject2 = new Bundle();
        ((Bundle)localObject2).putString("object", str);
        try
        {
          new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/objects/" + URLEncoder.encode(localObject1, "UTF-8"), (Bundle)localObject2, HttpMethod.POST, this.val$requestCallback).executeAsync();
          return;
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          localObject2 = localUnsupportedEncodingException.getLocalizedMessage();
          Object localObject1 = localObject2;
          if (localObject2 == null) {
            localObject1 = "Error staging Open Graph object.";
          }
          paramOnMapValueCompleteListener.onError(new FacebookException((String)localObject1));
        }
      }
      
      public void onError(FacebookException paramAnonymousFacebookException)
      {
        paramOnMapValueCompleteListener.onError(paramAnonymousFacebookException);
      }
    });
  }
  
  private static void stagePhoto(final SharePhoto paramSharePhoto, CollectionMapper.OnMapValueCompleteListener paramOnMapValueCompleteListener)
  {
    Object localObject = paramSharePhoto.getBitmap();
    Uri localUri = paramSharePhoto.getImageUrl();
    if ((localObject != null) || (localUri != null))
    {
      paramSharePhoto = new GraphRequest.Callback()
      {
        public void onCompleted(GraphResponse paramAnonymousGraphResponse)
        {
          Object localObject = paramAnonymousGraphResponse.getError();
          if (localObject != null)
          {
            String str = ((FacebookRequestError)localObject).getErrorMessage();
            localObject = str;
            if (str == null) {
              localObject = "Error staging photo.";
            }
            this.val$onPhotoStagedListener.onError(new FacebookGraphResponseException(paramAnonymousGraphResponse, (String)localObject));
            return;
          }
          paramAnonymousGraphResponse = paramAnonymousGraphResponse.getJSONObject();
          if (paramAnonymousGraphResponse == null)
          {
            this.val$onPhotoStagedListener.onError(new FacebookException("Error staging photo."));
            return;
          }
          paramAnonymousGraphResponse = paramAnonymousGraphResponse.optString("uri");
          if (paramAnonymousGraphResponse == null)
          {
            this.val$onPhotoStagedListener.onError(new FacebookException("Error staging photo."));
            return;
          }
          localObject = new JSONObject();
          try
          {
            ((JSONObject)localObject).put("url", paramAnonymousGraphResponse);
            ((JSONObject)localObject).put("user_generated", paramSharePhoto.getUserGenerated());
            this.val$onPhotoStagedListener.onComplete(localObject);
            return;
          }
          catch (JSONException paramAnonymousGraphResponse)
          {
            localObject = paramAnonymousGraphResponse.getLocalizedMessage();
            paramAnonymousGraphResponse = (GraphResponse)localObject;
            if (localObject == null) {
              paramAnonymousGraphResponse = "Error staging photo.";
            }
            this.val$onPhotoStagedListener.onError(new FacebookException(paramAnonymousGraphResponse));
          }
        }
      };
      if (localObject != null)
      {
        ShareInternalUtility.newUploadStagingResourceWithImageRequest(AccessToken.getCurrentAccessToken(), (Bitmap)localObject, paramSharePhoto).executeAsync();
        return;
      }
      try
      {
        ShareInternalUtility.newUploadStagingResourceWithImageRequest(AccessToken.getCurrentAccessToken(), localUri, paramSharePhoto).executeAsync();
        return;
      }
      catch (FileNotFoundException paramSharePhoto)
      {
        localObject = paramSharePhoto.getLocalizedMessage();
        paramSharePhoto = (SharePhoto)localObject;
        if (localObject == null) {
          paramSharePhoto = "Error staging photo.";
        }
        paramOnMapValueCompleteListener.onError(new FacebookException(paramSharePhoto));
        return;
      }
    }
    paramOnMapValueCompleteListener.onError(new FacebookException("Photos must have an imageURL or bitmap."));
  }
  
  public boolean canShare()
  {
    if (getShareContent() == null) {}
    do
    {
      return false;
      localObject = AccessToken.getCurrentAccessToken();
    } while (localObject == null);
    Object localObject = ((AccessToken)localObject).getPermissions();
    if ((localObject == null) || (!((Set)localObject).contains("publish_actions"))) {
      Log.w("ShareApi", "The publish_actions permissions are missing, the share will fail unless this app was authorized to publish in another installation.");
    }
    return true;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public ShareContent getShareContent()
  {
    return this.shareContent;
  }
  
  public void setMessage(String paramString)
  {
    this.message = paramString;
  }
  
  public void share(FacebookCallback<Sharer.Result> paramFacebookCallback)
  {
    if (!canShare()) {
      ShareInternalUtility.invokeCallbackWithError(paramFacebookCallback, "Insufficient permissions for sharing content via Api.");
    }
    do
    {
      return;
      ShareContent localShareContent = getShareContent();
      try
      {
        ShareContentValidation.validateForApiShare(localShareContent);
        if ((localShareContent instanceof ShareLinkContent))
        {
          shareLinkContent((ShareLinkContent)localShareContent, paramFacebookCallback);
          return;
        }
      }
      catch (FacebookException localFacebookException)
      {
        ShareInternalUtility.invokeCallbackWithException(paramFacebookCallback, localFacebookException);
        return;
      }
      if ((localFacebookException instanceof SharePhotoContent))
      {
        sharePhotoContent((SharePhotoContent)localFacebookException, paramFacebookCallback);
        return;
      }
      if ((localFacebookException instanceof ShareVideoContent))
      {
        shareVideoContent((ShareVideoContent)localFacebookException, paramFacebookCallback);
        return;
      }
    } while (!(localFacebookException instanceof ShareOpenGraphContent));
    shareOpenGraphContent((ShareOpenGraphContent)localFacebookException, paramFacebookCallback);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/ShareApi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */