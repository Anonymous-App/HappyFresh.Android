package com.ad4screen.sdk.service.modules.tracking.webview;

import android.os.Bundle;
import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.Log;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class b
{
  protected final A4S a;
  
  public b(A4S paramA4S)
  {
    this.a = paramA4S;
  }
  
  private void d(List<String> paramList)
  {
    paramList = c(paramList);
    if (paramList.size() > 0) {
      this.a.updateDeviceInfo(paramList);
    }
  }
  
  public abstract void a(List<String> paramList);
  
  public void b(List<String> paramList)
  {
    if ((paramList != null) && (!paramList.isEmpty())) {
      d(paramList);
    }
  }
  
  protected Bundle c(List<String> paramList)
  {
    Bundle localBundle = new Bundle();
    if (paramList == null) {
      return localBundle;
    }
    paramList = paramList.iterator();
    for (;;)
    {
      if (paramList.hasNext())
      {
        String str1 = (String)paramList.next();
        if (str1 == null) {
          continue;
        }
        try
        {
          JSONObject localJSONObject = new JSONObject(str1);
          Iterator localIterator = localJSONObject.keys();
          while (localIterator.hasNext())
          {
            String str2 = (String)localIterator.next();
            try
            {
              localBundle.putString(str2, localJSONObject.getString(str2));
            }
            catch (JSONException localJSONException2)
            {
              Log.internal("DefaultUrlActionsExecutor| No value for key " + str2);
            }
          }
        }
        catch (JSONException localJSONException1)
        {
          Log.internal("DefaultUrlActionsExecutor| Impossible to parse value in JSONObject : " + str1);
        }
      }
    }
    return localBundle;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/webview/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */