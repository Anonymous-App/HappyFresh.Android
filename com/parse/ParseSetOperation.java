package com.parse;

class ParseSetOperation
  implements ParseFieldOperation
{
  private final Object value;
  
  public ParseSetOperation(Object paramObject)
  {
    this.value = paramObject;
  }
  
  public Object apply(Object paramObject, String paramString)
  {
    return this.value;
  }
  
  public Object encode(ParseEncoder paramParseEncoder)
  {
    return paramParseEncoder.encode(this.value);
  }
  
  public Object getValue()
  {
    return this.value;
  }
  
  public ParseFieldOperation mergeWithPrevious(ParseFieldOperation paramParseFieldOperation)
  {
    return this;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseSetOperation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */