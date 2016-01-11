package com.optimizely.LogAndEvent.Data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.optimizely.Optimizely;
import org.json.JSONException;
import org.json.JSONObject;

public class OptimizelySessionEvent
  extends OptimizelyEvent
{
  private final long sessionBeginTime;
  private final long sessionLength;
  
  public OptimizelySessionEvent(@NonNull Optimizely paramOptimizely, @NonNull String paramString1, String paramString2, long paramLong1, long paramLong2)
  {
    super(paramOptimizely, paramString1, paramString2);
    this.sessionBeginTime = paramLong1;
    this.sessionLength = paramLong2;
  }
  
  @Nullable
  protected JSONObject getJSONObject()
  {
    JSONObject localJSONObject = super.getJSONObject();
    if (localJSONObject == null) {
      return null;
    }
    try
    {
      localJSONObject.remove("time");
      localJSONObject.put("time", Long.toString(this.sessionBeginTime));
      localJSONObject.put("v", Long.toString(this.sessionLength));
      return localJSONObject;
    }
    catch (JSONException localJSONException) {}
    return localJSONObject;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/LogAndEvent/Data/OptimizelySessionEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */