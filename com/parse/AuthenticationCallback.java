package com.parse;

import java.util.Map;

public abstract interface AuthenticationCallback
{
  public abstract boolean onRestore(Map<String, String> paramMap);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/AuthenticationCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */