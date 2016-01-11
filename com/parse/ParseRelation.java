package com.parse;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseRelation<T extends ParseObject>
{
  private String key;
  private Set<ParseObject> knownObjects = new HashSet();
  private final Object mutex = new Object();
  private WeakReference<ParseObject> parent;
  private String parentClassName;
  private String parentObjectId;
  private String targetClass;
  
  ParseRelation(ParseObject paramParseObject, String paramString)
  {
    this.parent = new WeakReference(paramParseObject);
    this.parentObjectId = paramParseObject.getObjectId();
    this.parentClassName = paramParseObject.getClassName();
    this.key = paramString;
    this.targetClass = null;
  }
  
  ParseRelation(String paramString)
  {
    this.parent = null;
    this.parentObjectId = null;
    this.parentClassName = null;
    this.key = null;
    this.targetClass = paramString;
  }
  
  ParseRelation(JSONObject paramJSONObject, ParseDecoder paramParseDecoder)
  {
    this.parent = null;
    this.parentObjectId = null;
    this.parentClassName = null;
    this.key = null;
    this.targetClass = paramJSONObject.optString("className", null);
    paramJSONObject = paramJSONObject.optJSONArray("objects");
    if (paramJSONObject != null)
    {
      int i = 0;
      while (i < paramJSONObject.length())
      {
        this.knownObjects.add((ParseObject)paramParseDecoder.decode(paramJSONObject.optJSONObject(i)));
        i += 1;
      }
    }
  }
  
  public void add(T paramT)
  {
    synchronized (this.mutex)
    {
      ParseRelationOperation localParseRelationOperation = new ParseRelationOperation(Collections.singleton(paramT), null);
      this.targetClass = localParseRelationOperation.getTargetClass();
      getParent().performOperation(this.key, localParseRelationOperation);
      this.knownObjects.add(paramT);
      return;
    }
  }
  
  void addKnownObject(ParseObject paramParseObject)
  {
    synchronized (this.mutex)
    {
      this.knownObjects.add(paramParseObject);
      return;
    }
  }
  
  JSONObject encodeToJSON(ParseEncoder paramParseEncoder)
    throws JSONException
  {
    synchronized (this.mutex)
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("__type", "Relation");
      localJSONObject.put("className", this.targetClass);
      JSONArray localJSONArray = new JSONArray();
      Iterator localIterator = this.knownObjects.iterator();
      while (localIterator.hasNext())
      {
        ParseObject localParseObject = (ParseObject)localIterator.next();
        try
        {
          localJSONArray.put(paramParseEncoder.encodeRelatedObject(localParseObject));
        }
        catch (Exception localException) {}
      }
      localJSONObject.put("objects", localJSONArray);
      return localJSONObject;
    }
  }
  
  void ensureParentAndKey(ParseObject paramParseObject, String paramString)
  {
    synchronized (this.mutex)
    {
      if (this.parent == null)
      {
        this.parent = new WeakReference(paramParseObject);
        this.parentObjectId = paramParseObject.getObjectId();
        this.parentClassName = paramParseObject.getClassName();
      }
      if (this.key == null) {
        this.key = paramString;
      }
      if (this.parent.get() != paramParseObject) {
        throw new IllegalStateException("Internal error. One ParseRelation retrieved from two different ParseObjects.");
      }
    }
    if (!this.key.equals(paramString)) {
      throw new IllegalStateException("Internal error. One ParseRelation retrieved from two different keys.");
    }
  }
  
  String getKey()
  {
    return this.key;
  }
  
  Set<ParseObject> getKnownObjects()
  {
    return this.knownObjects;
  }
  
  ParseObject getParent()
  {
    if (this.parent == null) {
      return null;
    }
    if (this.parent.get() == null) {
      return ParseObject.createWithoutData(this.parentClassName, this.parentObjectId);
    }
    return (ParseObject)this.parent.get();
  }
  
  public ParseQuery<T> getQuery()
  {
    synchronized (this.mutex)
    {
      if (this.targetClass == null)
      {
        localObject1 = new ParseQuery.State.Builder(this.parentClassName).redirectClassNameForKey(this.key);
        ((ParseQuery.State.Builder)localObject1).whereRelatedTo(getParent(), this.key);
        localObject1 = new ParseQuery((ParseQuery.State.Builder)localObject1);
        return (ParseQuery<T>)localObject1;
      }
      Object localObject1 = new ParseQuery.State.Builder(this.targetClass);
    }
  }
  
  String getTargetClass()
  {
    synchronized (this.mutex)
    {
      String str = this.targetClass;
      return str;
    }
  }
  
  boolean hasKnownObject(ParseObject paramParseObject)
  {
    synchronized (this.mutex)
    {
      boolean bool = this.knownObjects.contains(paramParseObject);
      return bool;
    }
  }
  
  public void remove(T paramT)
  {
    synchronized (this.mutex)
    {
      ParseRelationOperation localParseRelationOperation = new ParseRelationOperation(null, Collections.singleton(paramT));
      this.targetClass = localParseRelationOperation.getTargetClass();
      getParent().performOperation(this.key, localParseRelationOperation);
      this.knownObjects.remove(paramT);
      return;
    }
  }
  
  void removeKnownObject(ParseObject paramParseObject)
  {
    synchronized (this.mutex)
    {
      this.knownObjects.remove(paramParseObject);
      return;
    }
  }
  
  void setTargetClass(String paramString)
  {
    synchronized (this.mutex)
    {
      this.targetClass = paramString;
      return;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseRelation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */