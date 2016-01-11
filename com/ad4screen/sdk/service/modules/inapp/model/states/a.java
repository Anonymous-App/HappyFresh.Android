package com.ad4screen.sdk.service.modules.inapp.model.states;

import com.ad4screen.sdk.common.persistence.d;
import com.ad4screen.sdk.common.persistence.e;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class a
  implements com.ad4screen.sdk.common.persistence.c<a>, d
{
  private e a = new e();
  
  public a a(String paramString)
    throws JSONException
  {
    int i = 0;
    a[] arrayOfa = new a[4];
    arrayOfa[0] = new b();
    arrayOfa[1] = new c();
    arrayOfa[2] = new com.ad4screen.sdk.service.modules.inapp.model.states.composites.a();
    arrayOfa[3] = new com.ad4screen.sdk.service.modules.inapp.model.states.composites.b();
    paramString = new JSONObject(paramString);
    while (i < arrayOfa.length)
    {
      if (!paramString.isNull(arrayOfa[i].getClassKey())) {
        return (a)this.a.a(paramString.toString(), arrayOfa[i]);
      }
      i += 1;
    }
    return this;
  }
  
  public boolean a(Map<String, c> paramMap)
  {
    return false;
  }
  
  public String getClassKey()
  {
    return null;
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    return null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/states/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */