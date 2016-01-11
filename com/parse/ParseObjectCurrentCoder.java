package com.parse;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class ParseObjectCurrentCoder
  extends ParseObjectCoder
{
  private static final ParseObjectCurrentCoder INSTANCE = new ParseObjectCurrentCoder();
  private static final String KEY_CLASS_NAME = "classname";
  private static final String KEY_CREATED_AT = "createdAt";
  private static final String KEY_DATA = "data";
  private static final String KEY_OBJECT_ID = "objectId";
  private static final String KEY_OLD_CREATED_AT = "created_at";
  private static final String KEY_OLD_OBJECT_ID = "id";
  private static final String KEY_OLD_POINTERS = "pointers";
  private static final String KEY_OLD_UPDATED_AT = "updated_at";
  private static final String KEY_UPDATED_AT = "updatedAt";
  
  public static ParseObjectCurrentCoder get()
  {
    return INSTANCE;
  }
  
  public <T extends ParseObject.State.Init<?>> T decode(T paramT, JSONObject paramJSONObject, ParseDecoder paramParseDecoder)
  {
    Object localObject1;
    Object localObject2;
    try
    {
      if (paramJSONObject.has("id")) {
        paramT.objectId(paramJSONObject.getString("id"));
      }
      if (paramJSONObject.has("created_at"))
      {
        localObject1 = paramJSONObject.getString("created_at");
        if (localObject1 != null) {
          paramT.createdAt(ParseImpreciseDateFormat.getInstance().parse((String)localObject1));
        }
      }
      if (paramJSONObject.has("updated_at"))
      {
        localObject1 = paramJSONObject.getString("updated_at");
        if (localObject1 != null) {
          paramT.updatedAt(ParseImpreciseDateFormat.getInstance().parse((String)localObject1));
        }
      }
      if (paramJSONObject.has("pointers"))
      {
        localObject1 = paramJSONObject.getJSONObject("pointers");
        localObject2 = ((JSONObject)localObject1).keys();
        while (((Iterator)localObject2).hasNext())
        {
          String str = (String)((Iterator)localObject2).next();
          JSONArray localJSONArray = ((JSONObject)localObject1).getJSONArray(str);
          paramT.put(str, ParseObject.createWithoutData(localJSONArray.optString(0), localJSONArray.optString(1)));
        }
      }
      paramJSONObject = paramJSONObject.optJSONObject("data");
    }
    catch (JSONException paramT)
    {
      throw new RuntimeException(paramT);
    }
    if (paramJSONObject != null)
    {
      localObject1 = paramJSONObject.keys();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (String)((Iterator)localObject1).next();
        if (((String)localObject2).equals("objectId")) {
          paramT.objectId(paramJSONObject.getString((String)localObject2));
        } else if (((String)localObject2).equals("createdAt")) {
          paramT.createdAt(ParseDateFormat.getInstance().parse(paramJSONObject.getString((String)localObject2)));
        } else if (((String)localObject2).equals("updatedAt")) {
          paramT.updatedAt(ParseDateFormat.getInstance().parse(paramJSONObject.getString((String)localObject2)));
        } else {
          paramT.put((String)localObject2, paramParseDecoder.decode(paramJSONObject.get((String)localObject2)));
        }
      }
    }
    return paramT;
  }
  
  public <T extends ParseObject.State> JSONObject encode(T paramT, ParseOperationSet paramParseOperationSet, ParseEncoder paramParseEncoder)
  {
    if (paramParseOperationSet != null) {
      throw new IllegalArgumentException("Parameter ParseOperationSet is not null");
    }
    paramParseOperationSet = new JSONObject();
    JSONObject localJSONObject = new JSONObject();
    try
    {
      Iterator localIterator = paramT.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        localJSONObject.put(str, paramParseEncoder.encode(paramT.get(str)));
      }
      if (paramT.createdAt() <= 0L) {
        break label130;
      }
    }
    catch (JSONException paramT)
    {
      throw new RuntimeException("could not serialize object to JSON");
    }
    localJSONObject.put("createdAt", ParseDateFormat.getInstance().format(new Date(paramT.createdAt())));
    label130:
    if (paramT.updatedAt() > 0L) {
      localJSONObject.put("updatedAt", ParseDateFormat.getInstance().format(new Date(paramT.updatedAt())));
    }
    if (paramT.objectId() != null) {
      localJSONObject.put("objectId", paramT.objectId());
    }
    paramParseOperationSet.put("data", localJSONObject);
    paramParseOperationSet.put("classname", paramT.className());
    return paramParseOperationSet;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseObjectCurrentCoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */