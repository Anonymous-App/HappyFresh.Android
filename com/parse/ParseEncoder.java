package com.parse;

import android.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

abstract class ParseEncoder
{
  static boolean isValidType(Object paramObject)
  {
    return ((paramObject instanceof JSONObject)) || ((paramObject instanceof JSONArray)) || ((paramObject instanceof String)) || ((paramObject instanceof Number)) || ((paramObject instanceof Boolean)) || (paramObject == JSONObject.NULL) || ((paramObject instanceof ParseObject)) || ((paramObject instanceof ParseACL)) || ((paramObject instanceof ParseFile)) || ((paramObject instanceof ParseGeoPoint)) || ((paramObject instanceof Date)) || ((paramObject instanceof byte[])) || ((paramObject instanceof List)) || ((paramObject instanceof Map)) || ((paramObject instanceof ParseRelation));
  }
  
  public Object encode(Object paramObject)
  {
    Object localObject1;
    try
    {
      if ((paramObject instanceof ParseObject)) {
        return encodeRelatedObject((ParseObject)paramObject);
      }
      if ((paramObject instanceof ParseQuery.State.Builder)) {
        return encode(((ParseQuery.State.Builder)paramObject).build());
      }
      if ((paramObject instanceof ParseQuery.State)) {
        return ((ParseQuery.State)paramObject).toJSON(this);
      }
      if ((paramObject instanceof Date)) {
        return encodeDate((Date)paramObject);
      }
      if ((paramObject instanceof byte[]))
      {
        localObject1 = new JSONObject();
        ((JSONObject)localObject1).put("__type", "Bytes");
        ((JSONObject)localObject1).put("base64", Base64.encodeToString((byte[])paramObject, 2));
        return localObject1;
      }
    }
    catch (JSONException paramObject)
    {
      throw new RuntimeException((Throwable)paramObject);
    }
    if ((paramObject instanceof ParseFile)) {
      return ((ParseFile)paramObject).encode();
    }
    if ((paramObject instanceof ParseGeoPoint))
    {
      paramObject = (ParseGeoPoint)paramObject;
      localObject1 = new JSONObject();
      ((JSONObject)localObject1).put("__type", "GeoPoint");
      ((JSONObject)localObject1).put("latitude", ((ParseGeoPoint)paramObject).getLatitude());
      ((JSONObject)localObject1).put("longitude", ((ParseGeoPoint)paramObject).getLongitude());
      return localObject1;
    }
    if ((paramObject instanceof ParseACL)) {
      return ((ParseACL)paramObject).toJSONObject(this);
    }
    Object localObject2;
    if ((paramObject instanceof Map))
    {
      paramObject = (Map)paramObject;
      localObject1 = new JSONObject();
      localObject2 = ((Map)paramObject).entrySet().iterator();
      for (;;)
      {
        paramObject = localObject1;
        if (!((Iterator)localObject2).hasNext()) {
          break;
        }
        paramObject = (Map.Entry)((Iterator)localObject2).next();
        ((JSONObject)localObject1).put((String)((Map.Entry)paramObject).getKey(), encode(((Map.Entry)paramObject).getValue()));
      }
    }
    if ((paramObject instanceof JSONObject))
    {
      localObject2 = (JSONObject)paramObject;
      localObject1 = new JSONObject();
      Iterator localIterator = ((JSONObject)localObject2).keys();
      for (;;)
      {
        paramObject = localObject1;
        if (!localIterator.hasNext()) {
          break;
        }
        paramObject = (String)localIterator.next();
        ((JSONObject)localObject1).put((String)paramObject, encode(((JSONObject)localObject2).opt((String)paramObject)));
      }
    }
    if ((paramObject instanceof Collection))
    {
      localObject1 = new JSONArray();
      paramObject = ((Collection)paramObject).iterator();
      while (((Iterator)paramObject).hasNext()) {
        ((JSONArray)localObject1).put(encode(((Iterator)paramObject).next()));
      }
    }
    if ((paramObject instanceof JSONArray))
    {
      localObject2 = (JSONArray)paramObject;
      localObject1 = new JSONArray();
      int i = 0;
      for (;;)
      {
        paramObject = localObject1;
        if (i >= ((JSONArray)localObject2).length()) {
          break;
        }
        ((JSONArray)localObject1).put(encode(((JSONArray)localObject2).opt(i)));
        i += 1;
      }
    }
    if ((paramObject instanceof ParseRelation)) {
      return ((ParseRelation)paramObject).encodeToJSON(this);
    }
    if ((paramObject instanceof ParseFieldOperation)) {
      return ((ParseFieldOperation)paramObject).encode(this);
    }
    if ((paramObject instanceof ParseQuery.RelationConstraint)) {
      return ((ParseQuery.RelationConstraint)paramObject).encode(this);
    }
    if (paramObject == null)
    {
      paramObject = JSONObject.NULL;
      return paramObject;
    }
    if (isValidType(paramObject)) {
      return paramObject;
    }
    throw new IllegalArgumentException("invalid type for ParseObject: " + paramObject.getClass().toString());
    return paramObject;
    return localObject1;
  }
  
  protected JSONObject encodeDate(Date paramDate)
  {
    JSONObject localJSONObject = new JSONObject();
    paramDate = ParseDateFormat.getInstance().format(paramDate);
    try
    {
      localJSONObject.put("__type", "Date");
      localJSONObject.put("iso", paramDate);
      return localJSONObject;
    }
    catch (JSONException paramDate)
    {
      throw new RuntimeException(paramDate);
    }
  }
  
  protected abstract JSONObject encodeRelatedObject(ParseObject paramParseObject);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */