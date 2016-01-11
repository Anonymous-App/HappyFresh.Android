package com.parse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class ParseRemoveOperation
  implements ParseFieldOperation
{
  protected final HashSet<Object> objects = new HashSet();
  
  public ParseRemoveOperation(Collection<?> paramCollection)
  {
    this.objects.addAll(paramCollection);
  }
  
  public Object apply(Object paramObject, String paramString)
  {
    if (paramObject == null)
    {
      paramObject = new ArrayList();
      return paramObject;
    }
    if ((paramObject instanceof JSONArray)) {
      return new JSONArray((ArrayList)apply(ParseFieldOperations.jsonArrayAsArrayList((JSONArray)paramObject), paramString));
    }
    if ((paramObject instanceof List))
    {
      paramString = new ArrayList((List)paramObject);
      paramString.removeAll(this.objects);
      paramObject = new ArrayList(this.objects);
      ((ArrayList)paramObject).removeAll(paramString);
      HashSet localHashSet = new HashSet();
      paramObject = ((ArrayList)paramObject).iterator();
      while (((Iterator)paramObject).hasNext())
      {
        localObject = ((Iterator)paramObject).next();
        if ((localObject instanceof ParseObject)) {
          localHashSet.add(((ParseObject)localObject).getObjectId());
        }
      }
      Object localObject = paramString.iterator();
      for (;;)
      {
        paramObject = paramString;
        if (!((Iterator)localObject).hasNext()) {
          break;
        }
        paramObject = ((Iterator)localObject).next();
        if (((paramObject instanceof ParseObject)) && (localHashSet.contains(((ParseObject)paramObject).getObjectId()))) {
          ((Iterator)localObject).remove();
        }
      }
    }
    throw new IllegalArgumentException("Operation is invalid after previous operation.");
  }
  
  public JSONObject encode(ParseEncoder paramParseEncoder)
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("__op", "Remove");
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
    if ((paramParseFieldOperation instanceof ParseRemoveOperation))
    {
      paramParseFieldOperation = new HashSet(((ParseRemoveOperation)paramParseFieldOperation).objects);
      paramParseFieldOperation.addAll(this.objects);
      return new ParseRemoveOperation(paramParseFieldOperation);
    }
    throw new IllegalArgumentException("Operation is invalid after previous operation.");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseRemoveOperation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */