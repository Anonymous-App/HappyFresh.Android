package com.facebook.share.internal;

import android.os.Bundle;
import android.util.Pair;
import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public class NativeDialogParameters
{
  private static Bundle create(ShareLinkContent paramShareLinkContent, boolean paramBoolean)
  {
    Bundle localBundle = createBaseParameters(paramShareLinkContent, paramBoolean);
    Utility.putNonEmptyString(localBundle, "TITLE", paramShareLinkContent.getContentTitle());
    Utility.putNonEmptyString(localBundle, "DESCRIPTION", paramShareLinkContent.getContentDescription());
    Utility.putUri(localBundle, "IMAGE", paramShareLinkContent.getImageUrl());
    return localBundle;
  }
  
  private static Bundle create(ShareOpenGraphContent paramShareOpenGraphContent, JSONObject paramJSONObject, boolean paramBoolean)
  {
    Bundle localBundle = createBaseParameters(paramShareOpenGraphContent, paramBoolean);
    Utility.putNonEmptyString(localBundle, "PREVIEW_PROPERTY_NAME", (String)ShareInternalUtility.getFieldNameAndNamespaceFromFullName(paramShareOpenGraphContent.getPreviewPropertyName()).second);
    Utility.putNonEmptyString(localBundle, "ACTION_TYPE", paramShareOpenGraphContent.getAction().getActionType());
    Utility.putNonEmptyString(localBundle, "ACTION", paramJSONObject.toString());
    return localBundle;
  }
  
  private static Bundle create(SharePhotoContent paramSharePhotoContent, List<String> paramList, boolean paramBoolean)
  {
    paramSharePhotoContent = createBaseParameters(paramSharePhotoContent, paramBoolean);
    paramSharePhotoContent.putStringArrayList("PHOTOS", new ArrayList(paramList));
    return paramSharePhotoContent;
  }
  
  private static Bundle create(ShareVideoContent paramShareVideoContent, boolean paramBoolean)
  {
    ShareVideo localShareVideo = paramShareVideoContent.getVideo();
    Bundle localBundle = createBaseParameters(paramShareVideoContent, paramBoolean);
    Utility.putNonEmptyString(localBundle, "TITLE", paramShareVideoContent.getContentTitle());
    Utility.putNonEmptyString(localBundle, "DESCRIPTION", paramShareVideoContent.getContentDescription());
    Utility.putUri(localBundle, "VIDEO", localShareVideo.getLocalUrl());
    return localBundle;
  }
  
  public static Bundle create(UUID paramUUID, ShareContent paramShareContent, boolean paramBoolean)
  {
    Validate.notNull(paramShareContent, "shareContent");
    Validate.notNull(paramUUID, "callId");
    Object localObject = null;
    if ((paramShareContent instanceof ShareLinkContent)) {
      localObject = create((ShareLinkContent)paramShareContent, paramBoolean);
    }
    do
    {
      return (Bundle)localObject;
      if ((paramShareContent instanceof SharePhotoContent))
      {
        paramShareContent = (SharePhotoContent)paramShareContent;
        return create(paramShareContent, ShareInternalUtility.getPhotoUrls(paramShareContent, paramUUID), paramBoolean);
      }
      if ((paramShareContent instanceof ShareVideoContent)) {
        return create((ShareVideoContent)paramShareContent, paramBoolean);
      }
    } while (!(paramShareContent instanceof ShareOpenGraphContent));
    paramShareContent = (ShareOpenGraphContent)paramShareContent;
    localObject = paramShareContent.getAction();
    try
    {
      paramUUID = create(paramShareContent, ShareInternalUtility.removeNamespacesFromOGJsonObject(ShareInternalUtility.toJSONObjectForCall(paramUUID, (ShareOpenGraphAction)localObject), false), paramBoolean);
      return paramUUID;
    }
    catch (JSONException paramUUID)
    {
      throw new FacebookException("Unable to create a JSON Object from the provided ShareOpenGraphContent: " + paramUUID.getMessage());
    }
  }
  
  private static Bundle createBaseParameters(ShareContent paramShareContent, boolean paramBoolean)
  {
    Bundle localBundle = new Bundle();
    Utility.putUri(localBundle, "LINK", paramShareContent.getContentUrl());
    Utility.putNonEmptyString(localBundle, "PLACE", paramShareContent.getPlaceId());
    Utility.putNonEmptyString(localBundle, "REF", paramShareContent.getRef());
    localBundle.putBoolean("DATA_FAILURES_FATAL", paramBoolean);
    paramShareContent = paramShareContent.getPeopleIds();
    if (!Utility.isNullOrEmpty(paramShareContent)) {
      localBundle.putStringArrayList("FRIENDS", new ArrayList(paramShareContent));
    }
    return localBundle;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/internal/NativeDialogParameters.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */