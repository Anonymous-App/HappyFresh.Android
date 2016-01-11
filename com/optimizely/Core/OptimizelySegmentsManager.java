package com.optimizely.Core;

import android.os.Build.VERSION;
import com.optimizely.Build;
import com.optimizely.JSON.OptimizelySegment;
import com.optimizely.Optimizely;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class OptimizelySegmentsManager
{
  static final String OPTIMIZELY = "optimizely_";
  private static final String OPTIMIZELY_EVENT_COMPONENT = "OptimizelyEvent";
  Optimizely optimizely;
  
  public OptimizelySegmentsManager(Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
  }
  
  public void addSegmentingInformation(JSONObject paramJSONObject)
    throws JSONException
  {
    Object localObject = this.optimizely.getOptimizelyData().getSegments();
    if (localObject != null)
    {
      Iterator localIterator = ((List)localObject).iterator();
      while (localIterator.hasNext())
      {
        localObject = (OptimizelySegment)localIterator.next();
        String str = ((OptimizelySegment)localObject).getApiName();
        if (str.equals("optimizely_android_sdk_version"))
        {
          paramJSONObject.put("s" + ((OptimizelySegment)localObject).getSegmentId(), Build.sdkVersion());
        }
        else if (str.equals("optimizely_android_app_version"))
        {
          paramJSONObject.put("s" + ((OptimizelySegment)localObject).getSegmentId(), OptimizelyUtils.applicationVersion(this.optimizely));
        }
        else if (str.equals("optimizely_android_device_model"))
        {
          paramJSONObject.put("s" + ((OptimizelySegment)localObject).getSegmentId(), OptimizelyUtils.deviceModel());
        }
        else if (str.equals("optimizely_android_device_screen_size_inches"))
        {
          paramJSONObject.put("s" + ((OptimizelySegment)localObject).getSegmentId(), String.valueOf(OptimizelyUtils.getScreenSize(this.optimizely.getCurrentContext())));
        }
        else if (str.equals("optimizely_android_device_screen_size_dp"))
        {
          paramJSONObject.put("s" + ((OptimizelySegment)localObject).getSegmentId(), String.valueOf(OptimizelyUtils.getDensity(this.optimizely.getCurrentContext())));
        }
        else if (str.equals("optimizely_android_device_dpi"))
        {
          paramJSONObject.put("s" + ((OptimizelySegment)localObject).getSegmentId(), String.valueOf(OptimizelyUtils.getDeviceDPI(this.optimizely.getCurrentContext())));
        }
        else if (str.equals("optimizely_language"))
        {
          paramJSONObject.put("s" + ((OptimizelySegment)localObject).getSegmentId(), OptimizelyUtils.getLocaleTag());
        }
        else
        {
          if (str.equals("optimizely_has_ppid"))
          {
            str = "s" + ((OptimizelySegment)localObject).getSegmentId();
            if (this.optimizely.getPPID(this.optimizely.getCurrentContext()) != null) {}
            for (localObject = "true";; localObject = "false")
            {
              paramJSONObject.put(str, localObject);
              break;
            }
          }
          if (str.equals("optimizely_android_os_version")) {
            paramJSONObject.put("s" + ((OptimizelySegment)localObject).getSegmentId(), String.valueOf(Build.VERSION.SDK_INT));
          } else {
            this.optimizely.verboseLog(true, "OptimizelyEvent", "Invalid segmenting api name %1$s", new Object[] { str });
          }
        }
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Core/OptimizelySegmentsManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */