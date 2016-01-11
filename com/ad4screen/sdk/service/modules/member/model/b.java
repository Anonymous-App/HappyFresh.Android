package com.ad4screen.sdk.service.modules.member.model;

import com.ad4screen.sdk.common.a;
import com.ad4screen.sdk.common.g;
import com.ad4screen.sdk.common.persistence.c;
import com.ad4screen.sdk.common.persistence.d;
import org.json.JSONException;
import org.json.JSONObject;

public class b
  implements c<b>, d
{
  public String a;
  public int b;
  public long c;
  private final String d = "com.ad4screen.sdk.service.modules.member.model.Member";
  private final String e = "id";
  private final String f = "totalConnections";
  private final String g = "lastConnectDate";
  
  public b() {}
  
  public b(String paramString)
  {
    this(paramString, 1, g.e().a());
  }
  
  public b(String paramString, int paramInt, long paramLong)
  {
    this.a = paramString;
    this.b = paramInt;
    this.c = paramLong;
  }
  
  public b a(String paramString)
    throws JSONException
  {
    if (paramString == null) {
      return null;
    }
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.member.model.Member");
    if (!paramString.isNull("id")) {
      this.a = paramString.getString("id");
    }
    this.b = paramString.getInt("totalConnections");
    this.c = paramString.getLong("lastConnectDate");
    return this;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.member.model.Member";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("id", this.a);
    localJSONObject2.put("lastConnectDate", this.c);
    localJSONObject2.put("totalConnections", this.b);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.member.model.Member", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/member/model/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */