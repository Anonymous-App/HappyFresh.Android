package com.parse;

import bolts.Task;
import java.util.List;

abstract interface ParseQueryController
{
  public abstract <T extends ParseObject> Task<Integer> countAsync(ParseQuery.State<T> paramState, ParseUser paramParseUser, Task<Void> paramTask);
  
  public abstract <T extends ParseObject> Task<List<T>> findAsync(ParseQuery.State<T> paramState, ParseUser paramParseUser, Task<Void> paramTask);
  
  public abstract <T extends ParseObject> Task<T> getFirstAsync(ParseQuery.State<T> paramState, ParseUser paramParseUser, Task<Void> paramTask);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseQueryController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */