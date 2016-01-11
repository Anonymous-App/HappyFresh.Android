package com.parse;

import bolts.Task;
import java.util.Map;

abstract interface ParseUserController
{
  public abstract Task<ParseUser.State> getUserAsync(String paramString);
  
  public abstract Task<ParseUser.State> logInAsync(ParseUser.State paramState, ParseOperationSet paramParseOperationSet);
  
  public abstract Task<ParseUser.State> logInAsync(String paramString1, String paramString2);
  
  public abstract Task<ParseUser.State> logInAsync(String paramString, Map<String, String> paramMap);
  
  public abstract Task<Void> requestPasswordResetAsync(String paramString);
  
  public abstract Task<ParseUser.State> signUpAsync(ParseObject.State paramState, ParseOperationSet paramParseOperationSet, String paramString);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseUserController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */