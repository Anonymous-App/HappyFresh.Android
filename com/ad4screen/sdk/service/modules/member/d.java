package com.ad4screen.sdk.service.modules.member;

import android.content.Context;
import com.ad4screen.sdk.service.modules.member.model.a;
import org.json.JSONObject;

public class d
  extends com.ad4screen.sdk.common.persistence.b
{
  public d(Context paramContext)
  {
    super(paramContext, "com.ad4screen.sdk.service.modules.member.LinkedMembers");
  }
  
  public a a()
  {
    return (a)b("com.ad4screen.LinkedMembers", new a());
  }
  
  public void a(a parama)
  {
    a("com.ad4screen.LinkedMembers", parama);
  }
  
  public void a(com.ad4screen.sdk.service.modules.member.model.b paramb)
  {
    a("com.ad4screen.ActiveMember", paramb);
  }
  
  public boolean a(int paramInt, JSONObject paramJSONObject)
  {
    return false;
  }
  
  public int b()
  {
    return 4;
  }
  
  public com.ad4screen.sdk.service.modules.member.model.b e()
  {
    return (com.ad4screen.sdk.service.modules.member.model.b)b("com.ad4screen.ActiveMember", new com.ad4screen.sdk.service.modules.member.model.b());
  }
  
  public void f()
  {
    a("com.ad4screen.ActiveMember");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/member/d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */