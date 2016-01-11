package com.ad4screen.sdk.common.persistence.adapters;

import android.os.Bundle;
import com.ad4screen.sdk.common.persistence.adapters.model.b;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class d
  extends b<Bundle>
{
  private final String a = "android.os.Bundle";
  
  public JSONObject a(Bundle paramBundle)
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    Iterator localIterator = paramBundle.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Object localObject = paramBundle.get(str);
      if ((localObject instanceof Bundle)) {
        localJSONObject2.put(str, a(paramBundle.getBundle(str)));
      } else {
        localJSONObject2.put(str, localObject);
      }
    }
    localJSONObject1.put("type", "android.os.Bundle");
    localJSONObject1.put("android.os.Bundle", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/persistence/adapters/d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */