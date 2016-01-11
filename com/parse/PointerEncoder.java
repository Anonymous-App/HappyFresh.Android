package com.parse;

import org.json.JSONObject;

class PointerEncoder
  extends PointerOrLocalIdEncoder
{
  private static final PointerEncoder INSTANCE = new PointerEncoder();
  
  public static PointerEncoder get()
  {
    return INSTANCE;
  }
  
  public JSONObject encodeRelatedObject(ParseObject paramParseObject)
  {
    if (paramParseObject.getObjectId() == null) {
      throw new IllegalStateException("unable to encode an association with an unsaved ParseObject");
    }
    return super.encodeRelatedObject(paramParseObject);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/PointerEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */