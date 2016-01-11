package com.parse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class ParseFieldOperations
{
  private static Map<String, ParseFieldOperationFactory> opDecoderMap = new HashMap();
  
  static ParseFieldOperation decode(JSONObject paramJSONObject, ParseDecoder paramParseDecoder)
    throws JSONException
  {
    String str = paramJSONObject.optString("__op");
    ParseFieldOperationFactory localParseFieldOperationFactory = (ParseFieldOperationFactory)opDecoderMap.get(str);
    if (localParseFieldOperationFactory == null) {
      throw new RuntimeException("Unable to decode operation of type " + str);
    }
    return localParseFieldOperationFactory.decode(paramJSONObject, paramParseDecoder);
  }
  
  static ArrayList<Object> jsonArrayAsArrayList(JSONArray paramJSONArray)
  {
    ArrayList localArrayList = new ArrayList(paramJSONArray.length());
    int i = 0;
    while (i < paramJSONArray.length()) {
      try
      {
        localArrayList.add(paramJSONArray.get(i));
        i += 1;
      }
      catch (JSONException paramJSONArray)
      {
        throw new RuntimeException(paramJSONArray);
      }
    }
    return localArrayList;
  }
  
  private static void registerDecoder(String paramString, ParseFieldOperationFactory paramParseFieldOperationFactory)
  {
    opDecoderMap.put(paramString, paramParseFieldOperationFactory);
  }
  
  static void registerDefaultDecoders()
  {
    registerDecoder("Batch", new ParseFieldOperationFactory()
    {
      public ParseFieldOperation decode(JSONObject paramAnonymousJSONObject, ParseDecoder paramAnonymousParseDecoder)
        throws JSONException
      {
        Object localObject = null;
        JSONArray localJSONArray = paramAnonymousJSONObject.getJSONArray("ops");
        int i = 0;
        paramAnonymousJSONObject = (JSONObject)localObject;
        while (i < localJSONArray.length())
        {
          paramAnonymousJSONObject = ParseFieldOperations.decode(localJSONArray.getJSONObject(i), paramAnonymousParseDecoder).mergeWithPrevious(paramAnonymousJSONObject);
          i += 1;
        }
        return paramAnonymousJSONObject;
      }
    });
    registerDecoder("Delete", new ParseFieldOperationFactory()
    {
      public ParseFieldOperation decode(JSONObject paramAnonymousJSONObject, ParseDecoder paramAnonymousParseDecoder)
        throws JSONException
      {
        return ParseDeleteOperation.getInstance();
      }
    });
    registerDecoder("Increment", new ParseFieldOperationFactory()
    {
      public ParseFieldOperation decode(JSONObject paramAnonymousJSONObject, ParseDecoder paramAnonymousParseDecoder)
        throws JSONException
      {
        return new ParseIncrementOperation((Number)paramAnonymousParseDecoder.decode(paramAnonymousJSONObject.opt("amount")));
      }
    });
    registerDecoder("Add", new ParseFieldOperationFactory()
    {
      public ParseFieldOperation decode(JSONObject paramAnonymousJSONObject, ParseDecoder paramAnonymousParseDecoder)
        throws JSONException
      {
        return new ParseAddOperation((Collection)paramAnonymousParseDecoder.decode(paramAnonymousJSONObject.opt("objects")));
      }
    });
    registerDecoder("AddUnique", new ParseFieldOperationFactory()
    {
      public ParseFieldOperation decode(JSONObject paramAnonymousJSONObject, ParseDecoder paramAnonymousParseDecoder)
        throws JSONException
      {
        return new ParseAddUniqueOperation((Collection)paramAnonymousParseDecoder.decode(paramAnonymousJSONObject.opt("objects")));
      }
    });
    registerDecoder("Remove", new ParseFieldOperationFactory()
    {
      public ParseFieldOperation decode(JSONObject paramAnonymousJSONObject, ParseDecoder paramAnonymousParseDecoder)
        throws JSONException
      {
        return new ParseRemoveOperation((Collection)paramAnonymousParseDecoder.decode(paramAnonymousJSONObject.opt("objects")));
      }
    });
    registerDecoder("AddRelation", new ParseFieldOperationFactory()
    {
      public ParseFieldOperation decode(JSONObject paramAnonymousJSONObject, ParseDecoder paramAnonymousParseDecoder)
        throws JSONException
      {
        return new ParseRelationOperation(new HashSet((List)paramAnonymousParseDecoder.decode(paramAnonymousJSONObject.optJSONArray("objects"))), null);
      }
    });
    registerDecoder("RemoveRelation", new ParseFieldOperationFactory()
    {
      public ParseFieldOperation decode(JSONObject paramAnonymousJSONObject, ParseDecoder paramAnonymousParseDecoder)
        throws JSONException
      {
        return new ParseRelationOperation(null, new HashSet((List)paramAnonymousParseDecoder.decode(paramAnonymousJSONObject.optJSONArray("objects"))));
      }
    });
  }
  
  private static abstract interface ParseFieldOperationFactory
  {
    public abstract ParseFieldOperation decode(JSONObject paramJSONObject, ParseDecoder paramParseDecoder)
      throws JSONException;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseFieldOperations.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */