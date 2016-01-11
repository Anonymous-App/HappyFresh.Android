package com.ad4screen.sdk.common.persistence;

import org.json.JSONException;

public abstract interface c<T>
{
  public abstract T fromJSON(String paramString)
    throws JSONException;
  
  public abstract String getClassKey();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/persistence/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */