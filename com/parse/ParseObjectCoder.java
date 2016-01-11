package com.parse;

import java.util.Iterator;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class ParseObjectCoder
{
  private static final ParseObjectCoder INSTANCE = new ParseObjectCoder();
  private static final String KEY_ACL = "ACL";
  private static final String KEY_CLASS_NAME = "className";
  private static final String KEY_CREATED_AT = "createdAt";
  private static final String KEY_OBJECT_ID = "objectId";
  private static final String KEY_UPDATED_AT = "updatedAt";
  
  public static ParseObjectCoder get()
  {
    return INSTANCE;
  }
  
  public <T extends ParseObject.State.Init<?>> T decode(T paramT, JSONObject paramJSONObject, ParseDecoder paramParseDecoder)
  {
    for (;;)
    {
      String str;
      try
      {
        Iterator localIterator = paramJSONObject.keys();
        if (!localIterator.hasNext()) {
          break;
        }
        str = (String)localIterator.next();
        if ((str.equals("__type")) || (str.equals("className"))) {
          continue;
        }
        if (str.equals("objectId"))
        {
          paramT.objectId(paramJSONObject.getString(str));
          continue;
        }
        if (!str.equals("createdAt")) {
          break label112;
        }
      }
      catch (JSONException paramT)
      {
        throw new RuntimeException(paramT);
      }
      paramT.createdAt(ParseDateFormat.getInstance().parse(paramJSONObject.getString(str)));
      continue;
      label112:
      if (str.equals("updatedAt")) {
        paramT.updatedAt(ParseDateFormat.getInstance().parse(paramJSONObject.getString(str)));
      } else if (str.equals("ACL")) {
        paramT.put("ACL", ParseACL.createACLFromJSONObject(paramJSONObject.getJSONObject(str), paramParseDecoder));
      } else {
        paramT.put(str, paramParseDecoder.decode(paramJSONObject.get(str)));
      }
    }
    return paramT;
  }
  
  public <T extends ParseObject.State> JSONObject encode(T paramT, ParseOperationSet paramParseOperationSet, ParseEncoder paramParseEncoder)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      Iterator localIterator = paramParseOperationSet.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        localJSONObject.put(str, paramParseEncoder.encode((ParseFieldOperation)paramParseOperationSet.get(str)));
      }
      if (paramT.objectId() == null) {
        break label96;
      }
    }
    catch (JSONException paramT)
    {
      throw new RuntimeException("could not serialize object to JSON");
    }
    localJSONObject.put("objectId", paramT.objectId());
    label96:
    return localJSONObject;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseObjectCoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */