package com.parse;

import bolts.Task;

abstract interface ParseObjectCurrentController<T extends ParseObject>
{
  public abstract void clearFromDisk();
  
  public abstract void clearFromMemory();
  
  public abstract Task<Boolean> existsAsync();
  
  public abstract Task<T> getAsync();
  
  public abstract boolean isCurrent(T paramT);
  
  public abstract Task<Void> setAsync(T paramT);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseObjectCurrentController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */