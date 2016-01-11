package com.parse;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

abstract class ParseTraverser
{
  private boolean traverseParseObjects = false;
  private boolean yieldRoot = false;
  
  private void traverseInternal(Object paramObject, boolean paramBoolean, IdentityHashMap<Object, Object> paramIdentityHashMap)
  {
    if ((paramObject == null) || (paramIdentityHashMap.containsKey(paramObject))) {}
    label12:
    label302:
    do
    {
      do
      {
        break label12;
        break label12;
        break label12;
        for (;;)
        {
          return;
          if ((!paramBoolean) || (visit(paramObject)))
          {
            paramIdentityHashMap.put(paramObject, paramObject);
            Iterator localIterator;
            if ((paramObject instanceof JSONObject))
            {
              paramObject = (JSONObject)paramObject;
              localIterator = ((JSONObject)paramObject).keys();
              while (localIterator.hasNext())
              {
                String str = (String)localIterator.next();
                try
                {
                  traverseInternal(((JSONObject)paramObject).get(str), true, paramIdentityHashMap);
                }
                catch (JSONException paramObject)
                {
                  throw new RuntimeException((Throwable)paramObject);
                }
              }
            }
            if ((paramObject instanceof JSONArray))
            {
              paramObject = (JSONArray)paramObject;
              int i = 0;
              while (i < ((JSONArray)paramObject).length()) {
                try
                {
                  traverseInternal(((JSONArray)paramObject).get(i), true, paramIdentityHashMap);
                  i += 1;
                }
                catch (JSONException paramObject)
                {
                  throw new RuntimeException((Throwable)paramObject);
                }
              }
            }
            if ((paramObject instanceof Map))
            {
              paramObject = ((Map)paramObject).values().iterator();
              while (((Iterator)paramObject).hasNext()) {
                traverseInternal(((Iterator)paramObject).next(), true, paramIdentityHashMap);
              }
            }
            else if ((paramObject instanceof List))
            {
              paramObject = ((List)paramObject).iterator();
              while (((Iterator)paramObject).hasNext()) {
                traverseInternal(((Iterator)paramObject).next(), true, paramIdentityHashMap);
              }
            }
            else
            {
              if (!(paramObject instanceof ParseObject)) {
                break label302;
              }
              if (!this.traverseParseObjects) {
                break;
              }
              paramObject = (ParseObject)paramObject;
              localIterator = ((ParseObject)paramObject).keySet().iterator();
              while (localIterator.hasNext()) {
                traverseInternal(((ParseObject)paramObject).get((String)localIterator.next()), true, paramIdentityHashMap);
              }
            }
          }
        }
      } while (!(paramObject instanceof ParseACL));
      paramObject = ((ParseACL)paramObject).getUnresolvedUser();
    } while ((paramObject == null) || (!((ParseUser)paramObject).isCurrentUser()));
    traverseInternal(paramObject, true, paramIdentityHashMap);
  }
  
  public ParseTraverser setTraverseParseObjects(boolean paramBoolean)
  {
    this.traverseParseObjects = paramBoolean;
    return this;
  }
  
  public ParseTraverser setYieldRoot(boolean paramBoolean)
  {
    this.yieldRoot = paramBoolean;
    return this;
  }
  
  public void traverse(Object paramObject)
  {
    IdentityHashMap localIdentityHashMap = new IdentityHashMap();
    traverseInternal(paramObject, this.yieldRoot, localIdentityHashMap);
  }
  
  protected abstract boolean visit(Object paramObject);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseTraverser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */