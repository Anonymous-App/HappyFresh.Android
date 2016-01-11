package com.parse;

import org.json.JSONException;
import org.json.JSONObject;

class PointerOrLocalIdEncoder
  extends ParseEncoder
{
  private static final PointerOrLocalIdEncoder INSTANCE = new PointerOrLocalIdEncoder();
  
  public static PointerOrLocalIdEncoder get()
  {
    return INSTANCE;
  }
  
  public JSONObject encodeRelatedObject(ParseObject paramParseObject)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      if (paramParseObject.getObjectId() != null)
      {
        localJSONObject.put("__type", "Pointer");
        localJSONObject.put("className", paramParseObject.getClassName());
        localJSONObject.put("objectId", paramParseObject.getObjectId());
        return localJSONObject;
      }
      localJSONObject.put("__type", "Pointer");
      localJSONObject.put("className", paramParseObject.getClassName());
      localJSONObject.put("localId", paramParseObject.getOrCreateLocalId());
      return localJSONObject;
    }
    catch (JSONException paramParseObject)
    {
      throw new RuntimeException(paramParseObject);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/PointerOrLocalIdEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */