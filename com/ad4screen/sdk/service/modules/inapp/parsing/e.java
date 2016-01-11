package com.ad4screen.sdk.service.modules.inapp.parsing;

import com.ad4screen.sdk.service.modules.inapp.model.f;
import com.ad4screen.sdk.service.modules.inapp.model.h;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

public class e
{
  public List<f> a(JSONArray paramJSONArray)
    throws JSONException
  {
    if ((paramJSONArray == null) || (paramJSONArray.length() == 0)) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    while (i < paramJSONArray.length())
    {
      f localf = new f();
      localf.a(h.a("latitude", paramJSONArray.getJSONObject(i)).doubleValue());
      localf.b(h.a("longitude", paramJSONArray.getJSONObject(i)).doubleValue());
      localf.c(h.a("locationRange", paramJSONArray.getJSONObject(i)).doubleValue());
      localArrayList.add(localf);
      i += 1;
    }
    return localArrayList;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/parsing/e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */