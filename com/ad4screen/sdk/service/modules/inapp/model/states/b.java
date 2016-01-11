package com.ad4screen.sdk.service.modules.inapp.model.states;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

public final class b
  extends a
{
  public String a;
  public Pattern b;
  
  public b() {}
  
  public b(String paramString1, String paramString2)
  {
    this.a = paramString1;
    this.b = Pattern.compile(paramString2, 2);
  }
  
  public boolean a(Map<String, c> paramMap)
  {
    paramMap = (c)paramMap.get(this.a);
    if (paramMap == null) {
      return false;
    }
    if (this.a == null) {
      return false;
    }
    if (!this.a.equals(paramMap.a)) {
      return false;
    }
    if (this.b != null) {
      return this.b.matcher(paramMap.b).matches();
    }
    return false;
  }
  
  public b b(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.inapp.model.states.RegexState");
    return new b(paramString.getString("name"), paramString.getString("regex"));
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.inapp.model.states.RegexState";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("name", this.a);
    localJSONObject2.put("regex", this.b.pattern());
    localJSONObject1.put("com.ad4screen.sdk.service.modules.inapp.model.states.RegexState", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/states/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */