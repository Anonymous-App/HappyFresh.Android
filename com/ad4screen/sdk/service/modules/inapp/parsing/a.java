package com.ad4screen.sdk.service.modules.inapp.parsing;

import com.ad4screen.sdk.service.modules.inapp.model.b;
import com.ad4screen.sdk.service.modules.inapp.model.h;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

public class a
{
  public List<b> a(JSONArray paramJSONArray)
    throws JSONException
  {
    if ((paramJSONArray == null) || (paramJSONArray.length() == 0)) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    while (i < paramJSONArray.length())
    {
      b localb = new b();
      localb.a(h.b("id", paramJSONArray.getJSONObject(i)));
      localb.b(h.b("transition", paramJSONArray.getJSONObject(i)));
      localb.c(h.b("acc", paramJSONArray.getJSONObject(i)));
      localArrayList.add(localb);
      i += 1;
    }
    return localArrayList;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/parsing/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */