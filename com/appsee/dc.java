package com.appsee;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

class dc
{
  public dc(String paramString1, String paramString2, JSONArray paramJSONArray)
  {
    this.G = paramString1;
    this.i = paramString2;
    this.k = new ArrayList();
    if ((paramJSONArray != null) && (paramJSONArray.length() > 0))
    {
      int m = 0;
      for (int j = 0; m < paramJSONArray.length(); j = m)
      {
        paramString1 = this.k;
        m = j + 1;
        paramString1.add(paramJSONArray.optString(j));
      }
    }
  }
  
  public String i()
  {
    return this.G;
  }
  
  public List<String> i()
  {
    return this.k;
  }
  
  public String l()
  {
    return this.i;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/dc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */