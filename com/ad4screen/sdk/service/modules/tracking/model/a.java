package com.ad4screen.sdk.service.modules.tracking.model;

import com.ad4screen.sdk.common.persistence.c;
import com.ad4screen.sdk.common.persistence.d;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a
  implements c<a>, d
{
  List<a> a = new ArrayList();
  private final String b = "com.ad4screen.sdk.service.modules.tracking.model.ErrorsResponse";
  
  public a a(String paramString)
    throws JSONException
  {
    this.a.clear();
    paramString = new JSONObject(paramString).getJSONArray("errors");
    int i = 0;
    while (i < paramString.length())
    {
      Object localObject = paramString.getJSONObject(i).toString();
      localObject = new a().a((String)localObject);
      this.a.add(localObject);
      i += 1;
    }
    return this;
  }
  
  public List<a> a()
  {
    return this.a;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.tracking.model.ErrorsResponse";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    JSONArray localJSONArray = new JSONArray();
    Iterator localIterator = this.a.iterator();
    while (localIterator.hasNext()) {
      localJSONArray.put(((a)localIterator.next()).toJSON());
    }
    localJSONObject.put("errors", localJSONArray);
    return localJSONObject;
  }
  
  public class a
    implements c<a>, d
  {
    private final String b = "com.ad4screen.sdk.service.modules.tracking.model.ErrorResponse";
    private String c;
    private String d;
    
    public a() {}
    
    public a a(String paramString)
      throws JSONException
    {
      paramString = new JSONObject(paramString);
      this.c = paramString.getString("label");
      this.d = paramString.getString("message");
      return this;
    }
    
    public String a()
    {
      return this.c;
    }
    
    public String b()
    {
      return this.d;
    }
    
    public String getClassKey()
    {
      return "com.ad4screen.sdk.service.modules.tracking.model.ErrorResponse";
    }
    
    public JSONObject toJSON()
      throws JSONException
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("label", this.c);
      localJSONObject.put("message", this.d);
      return localJSONObject;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/model/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */