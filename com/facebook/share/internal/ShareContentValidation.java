package com.facebook.share.internal;

import android.graphics.Bitmap;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.ShareOpenGraphValueContainer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ShareContentValidation
{
  private static Validator ApiValidator;
  private static Validator DefaultValidator;
  private static Validator WebShareValidator;
  
  private static Validator getApiValidator()
  {
    if (ApiValidator == null) {
      ApiValidator = new ApiValidator(null);
    }
    return ApiValidator;
  }
  
  private static Validator getDefaultValidator()
  {
    if (DefaultValidator == null) {
      DefaultValidator = new Validator(null);
    }
    return DefaultValidator;
  }
  
  private static Validator getWebShareValidator()
  {
    if (WebShareValidator == null) {
      WebShareValidator = new WebShareValidator(null);
    }
    return WebShareValidator;
  }
  
  private static void validate(ShareContent paramShareContent, Validator paramValidator)
    throws FacebookException
  {
    if (paramShareContent == null) {
      throw new FacebookException("Must provide non-null content to share");
    }
    if ((paramShareContent instanceof ShareLinkContent)) {
      paramValidator.validate((ShareLinkContent)paramShareContent);
    }
    do
    {
      return;
      if ((paramShareContent instanceof SharePhotoContent))
      {
        paramValidator.validate((SharePhotoContent)paramShareContent);
        return;
      }
      if ((paramShareContent instanceof ShareVideoContent))
      {
        paramValidator.validate((ShareVideoContent)paramShareContent);
        return;
      }
    } while (!(paramShareContent instanceof ShareOpenGraphContent));
    paramValidator.validate((ShareOpenGraphContent)paramShareContent);
  }
  
  public static void validateForApiShare(ShareContent paramShareContent)
  {
    validate(paramShareContent, getApiValidator());
  }
  
  public static void validateForMessage(ShareContent paramShareContent)
  {
    validate(paramShareContent, getDefaultValidator());
  }
  
  public static void validateForNativeShare(ShareContent paramShareContent)
  {
    validate(paramShareContent, getDefaultValidator());
  }
  
  public static void validateForWebShare(ShareContent paramShareContent)
  {
    validate(paramShareContent, getWebShareValidator());
  }
  
  private static void validateLinkContent(ShareLinkContent paramShareLinkContent, Validator paramValidator)
  {
    paramShareLinkContent = paramShareLinkContent.getImageUrl();
    if ((paramShareLinkContent != null) && (!Utility.isWebUri(paramShareLinkContent))) {
      throw new FacebookException("Image Url must be an http:// or https:// url");
    }
  }
  
  private static void validateOpenGraphAction(ShareOpenGraphAction paramShareOpenGraphAction, Validator paramValidator)
  {
    if (paramShareOpenGraphAction == null) {
      throw new FacebookException("Must specify a non-null ShareOpenGraphAction");
    }
    if (Utility.isNullOrEmpty(paramShareOpenGraphAction.getActionType())) {
      throw new FacebookException("ShareOpenGraphAction must have a non-empty actionType");
    }
    paramValidator.validate(paramShareOpenGraphAction, false);
  }
  
  private static void validateOpenGraphContent(ShareOpenGraphContent paramShareOpenGraphContent, Validator paramValidator)
  {
    paramValidator.validate(paramShareOpenGraphContent.getAction());
    paramValidator = paramShareOpenGraphContent.getPreviewPropertyName();
    if (Utility.isNullOrEmpty(paramValidator)) {
      throw new FacebookException("Must specify a previewPropertyName.");
    }
    if (paramShareOpenGraphContent.getAction().get(paramValidator) == null) {
      throw new FacebookException("Property \"" + paramValidator + "\" was not found on the action. " + "The name of the preview property must match the name of an " + "action property.");
    }
  }
  
  private static void validateOpenGraphKey(String paramString, boolean paramBoolean)
  {
    if (!paramBoolean) {}
    for (;;)
    {
      return;
      String[] arrayOfString = paramString.split(":");
      if (arrayOfString.length < 2) {
        throw new FacebookException("Open Graph keys must be namespaced: %s", new Object[] { paramString });
      }
      int j = arrayOfString.length;
      int i = 0;
      while (i < j)
      {
        if (arrayOfString[i].isEmpty()) {
          throw new FacebookException("Invalid key found in Open Graph dictionary: %s", new Object[] { paramString });
        }
        i += 1;
      }
    }
  }
  
  private static void validateOpenGraphObject(ShareOpenGraphObject paramShareOpenGraphObject, Validator paramValidator)
  {
    if (paramShareOpenGraphObject == null) {
      throw new FacebookException("Cannot share a null ShareOpenGraphObject");
    }
    paramValidator.validate(paramShareOpenGraphObject, true);
  }
  
  private static void validateOpenGraphValueContainer(ShareOpenGraphValueContainer paramShareOpenGraphValueContainer, Validator paramValidator, boolean paramBoolean)
  {
    Iterator localIterator = paramShareOpenGraphValueContainer.keySet().iterator();
    while (localIterator.hasNext())
    {
      Object localObject1 = (String)localIterator.next();
      validateOpenGraphKey((String)localObject1, paramBoolean);
      localObject1 = paramShareOpenGraphValueContainer.get((String)localObject1);
      if ((localObject1 instanceof List))
      {
        localObject1 = ((List)localObject1).iterator();
        while (((Iterator)localObject1).hasNext())
        {
          Object localObject2 = ((Iterator)localObject1).next();
          if (localObject2 == null) {
            throw new FacebookException("Cannot put null objects in Lists in ShareOpenGraphObjects and ShareOpenGraphActions");
          }
          validateOpenGraphValueContainerObject(localObject2, paramValidator);
        }
      }
      else
      {
        validateOpenGraphValueContainerObject(localObject1, paramValidator);
      }
    }
  }
  
  private static void validateOpenGraphValueContainerObject(Object paramObject, Validator paramValidator)
  {
    if ((paramObject instanceof ShareOpenGraphObject)) {
      paramValidator.validate((ShareOpenGraphObject)paramObject);
    }
    while (!(paramObject instanceof SharePhoto)) {
      return;
    }
    paramValidator.validate((SharePhoto)paramObject);
  }
  
  private static void validatePhotoContent(SharePhotoContent paramSharePhotoContent, Validator paramValidator)
  {
    paramSharePhotoContent = paramSharePhotoContent.getPhotos();
    if ((paramSharePhotoContent == null) || (paramSharePhotoContent.isEmpty())) {
      throw new FacebookException("Must specify at least one Photo in SharePhotoContent.");
    }
    if (paramSharePhotoContent.size() > 6) {
      throw new FacebookException(String.format(Locale.ROOT, "Cannot add more than %d photos.", new Object[] { Integer.valueOf(6) }));
    }
    paramSharePhotoContent = paramSharePhotoContent.iterator();
    while (paramSharePhotoContent.hasNext()) {
      paramValidator.validate((SharePhoto)paramSharePhotoContent.next());
    }
  }
  
  private static void validatePhotoForApi(SharePhoto paramSharePhoto, Validator paramValidator)
  {
    if (paramSharePhoto == null) {
      throw new FacebookException("Cannot share a null SharePhoto");
    }
    Bitmap localBitmap = paramSharePhoto.getBitmap();
    paramSharePhoto = paramSharePhoto.getImageUrl();
    if (localBitmap == null)
    {
      if (paramSharePhoto == null) {
        throw new FacebookException("SharePhoto does not have a Bitmap or ImageUrl specified");
      }
      if ((Utility.isWebUri(paramSharePhoto)) && (!paramValidator.isOpenGraphContent())) {
        throw new FacebookException("Cannot set the ImageUrl of a SharePhoto to the Uri of an image on the web when sharing SharePhotoContent");
      }
    }
  }
  
  private static void validatePhotoForNativeDialog(SharePhoto paramSharePhoto, Validator paramValidator)
  {
    validatePhotoForApi(paramSharePhoto, paramValidator);
    if ((paramSharePhoto.getBitmap() != null) || (!Utility.isWebUri(paramSharePhoto.getImageUrl()))) {
      Validate.hasContentProvider(FacebookSdk.getApplicationContext());
    }
  }
  
  private static void validatePhotoForWebDialog(SharePhoto paramSharePhoto, Validator paramValidator)
  {
    if (paramSharePhoto == null) {
      throw new FacebookException("Cannot share a null SharePhoto");
    }
    paramSharePhoto = paramSharePhoto.getImageUrl();
    if ((paramSharePhoto == null) || (!Utility.isWebUri(paramSharePhoto))) {
      throw new FacebookException("SharePhoto must have a non-null imageUrl set to the Uri of an image on the web");
    }
  }
  
  private static void validateVideo(ShareVideo paramShareVideo, Validator paramValidator)
  {
    if (paramShareVideo == null) {
      throw new FacebookException("Cannot share a null ShareVideo");
    }
    if (paramShareVideo.getLocalUrl() == null) {
      throw new FacebookException("ShareVideo does not have a LocalUrl specified");
    }
  }
  
  private static void validateVideoContent(ShareVideoContent paramShareVideoContent, Validator paramValidator)
  {
    paramValidator.validate(paramShareVideoContent.getVideo());
    paramShareVideoContent = paramShareVideoContent.getPreviewPhoto();
    if (paramShareVideoContent != null) {
      paramValidator.validate(paramShareVideoContent);
    }
  }
  
  private static class ApiValidator
    extends ShareContentValidation.Validator
  {
    private ApiValidator()
    {
      super();
    }
    
    public void validate(SharePhoto paramSharePhoto)
    {
      ShareContentValidation.validatePhotoForApi(paramSharePhoto, this);
    }
  }
  
  private static class Validator
  {
    private boolean isOpenGraphContent = false;
    
    public boolean isOpenGraphContent()
    {
      return this.isOpenGraphContent;
    }
    
    public void validate(ShareLinkContent paramShareLinkContent)
    {
      ShareContentValidation.validateLinkContent(paramShareLinkContent, this);
    }
    
    public void validate(ShareOpenGraphAction paramShareOpenGraphAction)
    {
      ShareContentValidation.validateOpenGraphAction(paramShareOpenGraphAction, this);
    }
    
    public void validate(ShareOpenGraphContent paramShareOpenGraphContent)
    {
      this.isOpenGraphContent = true;
      ShareContentValidation.validateOpenGraphContent(paramShareOpenGraphContent, this);
    }
    
    public void validate(ShareOpenGraphObject paramShareOpenGraphObject)
    {
      ShareContentValidation.validateOpenGraphObject(paramShareOpenGraphObject, this);
    }
    
    public void validate(ShareOpenGraphValueContainer paramShareOpenGraphValueContainer, boolean paramBoolean)
    {
      ShareContentValidation.validateOpenGraphValueContainer(paramShareOpenGraphValueContainer, this, paramBoolean);
    }
    
    public void validate(SharePhoto paramSharePhoto)
    {
      ShareContentValidation.validatePhotoForNativeDialog(paramSharePhoto, this);
    }
    
    public void validate(SharePhotoContent paramSharePhotoContent)
    {
      ShareContentValidation.validatePhotoContent(paramSharePhotoContent, this);
    }
    
    public void validate(ShareVideo paramShareVideo)
    {
      ShareContentValidation.validateVideo(paramShareVideo, this);
    }
    
    public void validate(ShareVideoContent paramShareVideoContent)
    {
      ShareContentValidation.validateVideoContent(paramShareVideoContent, this);
    }
  }
  
  private static class WebShareValidator
    extends ShareContentValidation.Validator
  {
    private WebShareValidator()
    {
      super();
    }
    
    public void validate(SharePhoto paramSharePhoto)
    {
      ShareContentValidation.validatePhotoForWebDialog(paramSharePhoto, this);
    }
    
    public void validate(SharePhotoContent paramSharePhotoContent)
    {
      throw new FacebookException("Cannot share SharePhotoContent via web sharing dialogs");
    }
    
    public void validate(ShareVideoContent paramShareVideoContent)
    {
      throw new FacebookException("Cannot share ShareVideoContent via web sharing dialogs");
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/internal/ShareContentValidation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */