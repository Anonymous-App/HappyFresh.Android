package com.ad4screen.sdk.service.modules.inapp.model;

import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.persistence.c;
import com.ad4screen.sdk.model.displayformats.a;
import com.ad4screen.sdk.model.displayformats.g.a;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class e
  implements c<e>, com.ad4screen.sdk.common.persistence.d
{
  public i[] a = new i[0];
  public HashMap<String, g> b = new HashMap();
  private final String c = "com.ad4screen.sdk.service.modules.inapp.model.InAppConfig";
  private final String d = "rules";
  private final String e = "messages";
  private com.ad4screen.sdk.common.persistence.e f = new com.ad4screen.sdk.common.persistence.e();
  
  public com.ad4screen.sdk.model.displayformats.d a(com.ad4screen.sdk.model.displayformats.d paramd, String paramString)
  {
    if (paramd == null) {
      return null;
    }
    if ((paramd instanceof a))
    {
      paramd = (a)paramd;
      if (paramString.equals(paramd.d.i)) {
        return paramd.d;
      }
    }
    else if ((paramd instanceof com.ad4screen.sdk.model.displayformats.g))
    {
      paramd = (com.ad4screen.sdk.model.displayformats.g)paramd;
      int i = 0;
      while (i < paramd.c.length)
      {
        if (paramString.equals(paramd.i + '#' + i)) {
          return paramd.c[i].d;
        }
        i += 1;
      }
    }
    return null;
  }
  
  public g a(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    String[] arrayOfString = paramString.split("#");
    if (arrayOfString.length > 1) {
      return (g)this.b.get(arrayOfString[0]);
    }
    return (g)this.b.get(paramString);
  }
  
  public void a(e parame)
  {
    this.a = parame.a;
    HashMap localHashMap = new HashMap();
    Iterator localIterator = parame.b.keySet().iterator();
    if (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      g localg = (g)this.b.get(str);
      if (localg == null) {
        localg = (g)parame.b.get(str);
      }
      for (;;)
      {
        localHashMap.put(localg.a().i, localg);
        break;
        localg.a(((g)parame.b.get(str)).a());
      }
    }
    this.b = localHashMap;
  }
  
  public com.ad4screen.sdk.model.displayformats.d b(String paramString)
  {
    Object localObject;
    if (paramString == null)
    {
      localObject = null;
      return (com.ad4screen.sdk.model.displayformats.d)localObject;
    }
    String[] arrayOfString = paramString.split("#");
    if (arrayOfString.length > 1)
    {
      paramString = (g)this.b.get(arrayOfString[0]);
      if (paramString == null) {
        return null;
      }
      paramString = paramString.a();
      int i = 1;
      for (;;)
      {
        localObject = paramString;
        if (i >= arrayOfString.length) {
          break;
        }
        localObject = paramString;
        if (paramString == null) {
          break;
        }
        localObject = new String[i + 1];
        System.arraycopy(arrayOfString, 0, localObject, 0, i + 1);
        paramString = a(paramString, h.a("#", (String[])localObject));
        i += 1;
      }
    }
    paramString = (g)this.b.get(paramString);
    if (paramString != null) {
      return paramString.a();
    }
    return null;
  }
  
  public e c(String paramString)
    throws JSONException
  {
    int j = 0;
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.inapp.model.InAppConfig");
    Object localObject = paramString.getJSONArray("rules");
    this.a = new i[((JSONArray)localObject).length()];
    int i = 0;
    while (i < ((JSONArray)localObject).length())
    {
      i locali = new i();
      this.a[i] = ((i)this.f.a(((JSONArray)localObject).getString(i), locali));
      i += 1;
    }
    paramString = paramString.getJSONArray("messages");
    i = j;
    while (i < paramString.length())
    {
      localObject = paramString.getJSONObject(i);
      this.b.put(((JSONObject)localObject).getString("key"), this.f.a(((JSONObject)localObject).getString("message"), new g()));
      i += 1;
    }
    return this;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.inapp.model.InAppConfig";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    JSONArray localJSONArray = new JSONArray();
    Object localObject1 = this.a;
    int j = localObject1.length;
    int i = 0;
    while (i < j)
    {
      localObject2 = localObject1[i];
      localJSONArray.put(this.f.a(localObject2));
      i += 1;
    }
    localObject1 = new JSONArray();
    Object localObject2 = this.b.keySet().iterator();
    while (((Iterator)localObject2).hasNext())
    {
      String str = (String)((Iterator)localObject2).next();
      JSONObject localJSONObject3 = new JSONObject();
      localJSONObject3.put("key", str);
      localJSONObject3.put("message", this.f.a(this.b.get(str)));
      ((JSONArray)localObject1).put(localJSONObject3);
    }
    localJSONObject2.put("rules", localJSONArray);
    localJSONObject2.put("messages", localObject1);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.inapp.model.InAppConfig", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */