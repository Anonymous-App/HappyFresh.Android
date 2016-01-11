package com.parse;

import bolts.Task;

abstract interface ParseCurrentUserController
  extends ParseObjectCurrentController<ParseUser>
{
  public abstract Task<ParseUser> getAsync(boolean paramBoolean);
  
  public abstract Task<String> getCurrentSessionTokenAsync();
  
  public abstract Task<Void> logOutAsync();
  
  public abstract Task<Void> setIfNeededAsync(ParseUser paramParseUser);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseCurrentUserController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */