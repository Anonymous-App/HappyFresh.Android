package com.ad4screen.sdk.common.persistence.adapters;

import android.os.Bundle;
import com.ad4screen.sdk.common.persistence.adapters.model.a;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class c
  extends a<Bundle>
{
  private final String a = "android.os.Bundle";
  
  public Bundle a(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString).getJSONObject("android.os.Bundle");
    Bundle localBundle = new Bundle();
    Iterator localIterator = paramString.keys();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Object localObject = paramString.get(str);
      if ((localObject instanceof String)) {
        localBundle.putString(str, (String)localObject);
      } else if ((localObject instanceof Boolean)) {
        localBundle.putBoolean(str, ((Boolean)localObject).booleanValue());
      } else if ((localObject instanceof Integer)) {
        localBundle.putInt(str, ((Integer)localObject).intValue());
      } else if ((localObject instanceof Long)) {
        localBundle.putLong(str, ((Long)localObject).longValue());
      } else if ((localObject instanceof Float)) {
        localBundle.putFloat(str, ((Float)localObject).floatValue());
      } else if ((localObject instanceof Double)) {
        localBundle.putDouble(str, ((Double)localObject).doubleValue());
      } else {
        localBundle.putBundle(str, a(localObject.toString()));
      }
    }
    return localBundle;
  }
  
  public String a()
  {
    return "android.os.Bundle";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/persistence/adapters/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */