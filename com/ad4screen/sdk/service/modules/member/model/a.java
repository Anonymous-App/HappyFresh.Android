package com.ad4screen.sdk.service.modules.member.model;

import com.ad4screen.sdk.common.persistence.c;
import com.ad4screen.sdk.common.persistence.d;
import com.ad4screen.sdk.common.persistence.e;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a
  implements c<a>, d
{
  public ArrayList<b> a = new ArrayList();
  private final String b = "com.ad4screen.sdk.service.modules.member.model.LinkedMembers";
  private final String c = "members";
  private e d = new e();
  
  public a a(String paramString)
    throws JSONException
  {
    if (paramString == null)
    {
      paramString = new a();
      return paramString;
    }
    JSONArray localJSONArray = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.member.model.LinkedMembers").getJSONArray("members");
    int i = 0;
    for (;;)
    {
      paramString = this;
      if (i >= localJSONArray.length()) {
        break;
      }
      this.a.add(this.d.a(localJSONArray.getString(i), new b()));
      i += 1;
    }
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.member.model.LinkedMembers";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    JSONArray localJSONArray = new JSONArray();
    Iterator localIterator = this.a.iterator();
    while (localIterator.hasNext())
    {
      b localb = (b)localIterator.next();
      localJSONArray.put(this.d.a(localb));
    }
    localJSONObject2.put("members", localJSONArray);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.member.model.LinkedMembers", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/member/model/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */