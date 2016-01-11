package com.parse;

public abstract interface LocationCallback
  extends ParseCallback2<ParseGeoPoint, ParseException>
{
  public abstract void done(ParseGeoPoint paramParseGeoPoint, ParseException paramParseException);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/LocationCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */