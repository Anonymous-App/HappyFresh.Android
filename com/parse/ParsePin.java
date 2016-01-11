package com.parse;

import java.util.List;

@ParseClassName("_Pin")
class ParsePin
  extends ParseObject
{
  static final String KEY_NAME = "_name";
  private static final String KEY_OBJECTS = "_objects";
  
  public String getName()
  {
    return getString("_name");
  }
  
  public List<ParseObject> getObjects()
  {
    return getList("_objects");
  }
  
  boolean needsDefaultACL()
  {
    return false;
  }
  
  public void setName(String paramString)
  {
    put("_name", paramString);
  }
  
  public void setObjects(List<ParseObject> paramList)
  {
    put("_objects", paramList);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParsePin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */