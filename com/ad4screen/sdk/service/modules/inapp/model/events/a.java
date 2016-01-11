package com.ad4screen.sdk.service.modules.inapp.model.events;

import com.ad4screen.sdk.common.persistence.d;
import com.ad4screen.sdk.common.persistence.e;
import org.json.JSONException;
import org.json.JSONObject;

public class a
  implements com.ad4screen.sdk.common.persistence.c<a>, d
{
  public a a(String paramString)
    throws JSONException
  {
    int i = 0;
    e locale = new e();
    a[] arrayOfa = new a[4];
    arrayOfa[0] = new com.ad4screen.sdk.service.modules.inapp.model.events.regular.c();
    arrayOfa[1] = new com.ad4screen.sdk.service.modules.inapp.model.events.regular.b();
    arrayOfa[2] = new com.ad4screen.sdk.service.modules.inapp.model.events.composite.b();
    arrayOfa[3] = new com.ad4screen.sdk.service.modules.inapp.model.events.composite.c();
    paramString = new JSONObject(paramString);
    while (i < arrayOfa.length)
    {
      if (!paramString.isNull(arrayOfa[i].getClassKey())) {
        return (a)locale.a(paramString.toString(), arrayOfa[i]);
      }
      i += 1;
    }
    return this;
  }
  
  public Long a()
  {
    return null;
  }
  
  public String b()
  {
    return null;
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


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/events/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */