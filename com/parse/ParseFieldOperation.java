package com.parse;

import org.json.JSONException;

abstract interface ParseFieldOperation
{
  public abstract Object apply(Object paramObject, String paramString);
  
  public abstract Object encode(ParseEncoder paramParseEncoder)
    throws JSONException;
  
  public abstract ParseFieldOperation mergeWithPrevious(ParseFieldOperation paramParseFieldOperation);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseFieldOperation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */