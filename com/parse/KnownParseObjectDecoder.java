package com.parse;

import java.util.Map;

class KnownParseObjectDecoder
  extends ParseDecoder
{
  private Map<String, ParseObject> fetchedObjects;
  
  public KnownParseObjectDecoder(Map<String, ParseObject> paramMap)
  {
    this.fetchedObjects = paramMap;
  }
  
  protected ParseObject decodePointer(String paramString1, String paramString2)
  {
    if ((this.fetchedObjects != null) && (this.fetchedObjects.containsKey(paramString2))) {
      return (ParseObject)this.fetchedObjects.get(paramString2);
    }
    return super.decodePointer(paramString1, paramString2);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/KnownParseObjectDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */