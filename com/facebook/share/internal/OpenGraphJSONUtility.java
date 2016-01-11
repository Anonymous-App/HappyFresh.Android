package com.facebook.share.internal;

import android.support.annotation.Nullable;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class OpenGraphJSONUtility
{
  private static JSONArray toJSONArray(List paramList, PhotoJSONProcessor paramPhotoJSONProcessor)
    throws JSONException
  {
    JSONArray localJSONArray = new JSONArray();
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      localJSONArray.put(toJSONValue(paramList.next(), paramPhotoJSONProcessor));
    }
    return localJSONArray;
  }
  
  public static JSONObject toJSONObject(ShareOpenGraphAction paramShareOpenGraphAction, PhotoJSONProcessor paramPhotoJSONProcessor)
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    Iterator localIterator = paramShareOpenGraphAction.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localJSONObject.put(str, toJSONValue(paramShareOpenGraphAction.get(str), paramPhotoJSONProcessor));
    }
    return localJSONObject;
  }
  
  private static JSONObject toJSONObject(ShareOpenGraphObject paramShareOpenGraphObject, PhotoJSONProcessor paramPhotoJSONProcessor)
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    Iterator localIterator = paramShareOpenGraphObject.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localJSONObject.put(str, toJSONValue(paramShareOpenGraphObject.get(str), paramPhotoJSONProcessor));
    }
    return localJSONObject;
  }
  
  public static Object toJSONValue(@Nullable Object paramObject, PhotoJSONProcessor paramPhotoJSONProcessor)
    throws JSONException
  {
    Object localObject;
    if (paramObject == null) {
      localObject = JSONObject.NULL;
    }
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                return localObject;
                localObject = paramObject;
              } while ((paramObject instanceof String));
              localObject = paramObject;
            } while ((paramObject instanceof Boolean));
            localObject = paramObject;
          } while ((paramObject instanceof Double));
          localObject = paramObject;
        } while ((paramObject instanceof Float));
        localObject = paramObject;
      } while ((paramObject instanceof Integer));
      localObject = paramObject;
    } while ((paramObject instanceof Long));
    if ((paramObject instanceof SharePhoto))
    {
      if (paramPhotoJSONProcessor != null) {
        return paramPhotoJSONProcessor.toJSONObject((SharePhoto)paramObject);
      }
      return null;
    }
    if ((paramObject instanceof ShareOpenGraphObject)) {
      return toJSONObject((ShareOpenGraphObject)paramObject, paramPhotoJSONProcessor);
    }
    if ((paramObject instanceof List)) {
      return toJSONArray((List)paramObject, paramPhotoJSONProcessor);
    }
    throw new IllegalArgumentException("Invalid object found for JSON serialization: " + paramObject.toString());
  }
  
  public static abstract interface PhotoJSONProcessor
  {
    public abstract JSONObject toJSONObject(SharePhoto paramSharePhoto);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/internal/OpenGraphJSONUtility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */