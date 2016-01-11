package com.ad4screen.sdk.service.modules.inapp.parsing;

import com.ad4screen.sdk.service.modules.inapp.model.h;
import com.ad4screen.sdk.service.modules.inapp.model.k;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

public class g
{
  public List<k> a(JSONArray paramJSONArray)
    throws JSONException
  {
    if ((paramJSONArray == null) || (paramJSONArray.length() == 0)) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    while (i < paramJSONArray.length())
    {
      localArrayList.add(new k(h.b("name", paramJSONArray.getJSONObject(i))));
      i += 1;
    }
    return localArrayList;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/parsing/g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */