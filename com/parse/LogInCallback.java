package com.parse;

public abstract interface LogInCallback
  extends ParseCallback2<ParseUser, ParseException>
{
  public abstract void done(ParseUser paramParseUser, ParseException paramParseException);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/LogInCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */