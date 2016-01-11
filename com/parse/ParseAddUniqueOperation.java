package com.parse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class ParseAddUniqueOperation
  implements ParseFieldOperation
{
  protected final LinkedHashSet<Object> objects = new LinkedHashSet();
  
  public ParseAddUniqueOperation(Collection<?> paramCollection)
  {
    this.objects.addAll(paramCollection);
  }
  
  public Object apply(Object paramObject, String paramString)
  {
    if (paramObject == null)
    {
      paramObject = new ArrayList(this.objects);
      return paramObject;
    }
    if ((paramObject instanceof JSONArray)) {
      return new JSONArray((ArrayList)apply(ParseFieldOperations.jsonArrayAsArrayList((JSONArray)paramObject), paramString));
    }
    if ((paramObject instanceof List))
    {
      paramString = new ArrayList((List)paramObject);
      HashMap localHashMap = new HashMap();
      int i = 0;
      while (i < paramString.size())
      {
        if ((paramString.get(i) instanceof ParseObject)) {
          localHashMap.put(((ParseObject)paramString.get(i)).getObjectId(), Integer.valueOf(i));
        }
        i += 1;
      }
      Iterator localIterator = this.objects.iterator();
      for (;;)
      {
        paramObject = paramString;
        if (!localIterator.hasNext()) {
          break;
        }
        paramObject = localIterator.next();
        if ((paramObject instanceof ParseObject))
        {
          String str = ((ParseObject)paramObject).getObjectId();
          if ((str != null) && (localHashMap.containsKey(str))) {
            paramString.set(((Integer)localHashMap.get(str)).intValue(), paramObject);
          } else if (!paramString.contains(paramObject)) {
            paramString.add(paramObject);
          }
        }
        else if (!paramString.contains(paramObject))
        {
          paramString.add(paramObject);
        }
      }
    }
    throw new IllegalArgumentException("Operation is invalid after previous operation.");
  }
  
  public JSONObject encode(ParseEncoder paramParseEncoder)
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("__op", "AddUnique");
    localJSONObject.put("objects", paramParseEncoder.encode(new ArrayList(this.objects)));
    return localJSONObject;
  }
  
  public ParseFieldOperation mergeWithPrevious(ParseFieldOperation paramParseFieldOperation)
  {
    if (paramParseFieldOperation == null) {
      return this;
    }
    if ((paramParseFieldOperation instanceof ParseDeleteOperation)) {
      return new ParseSetOperation(this.objects);
    }
    if ((paramParseFieldOperation instanceof ParseSetOperation))
    {
      paramParseFieldOperation = ((ParseSetOperation)paramParseFieldOperation).getValue();
      if (((paramParseFieldOperation instanceof JSONArray)) || ((paramParseFieldOperation instanceof List))) {
        return new ParseSetOperation(apply(paramParseFieldOperation, null));
      }
      throw new IllegalArgumentException("You can only add an item to a List or JSONArray.");
    }
    if ((paramParseFieldOperation instanceof ParseAddUniqueOperation)) {
      return new ParseAddUniqueOperation((List)apply(new ArrayList(((ParseAddUniqueOperation)paramParseFieldOperation).objects), null));
    }
    throw new IllegalArgumentException("Operation is invalid after previous operation.");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseAddUniqueOperation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */