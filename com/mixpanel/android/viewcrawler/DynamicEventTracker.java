package com.mixpanel.android.viewcrawler;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class DynamicEventTracker
  implements ViewVisitor.OnEventListener
{
  private static final int DEBOUNCE_TIME_MILLIS = 1000;
  private static String LOGTAG = "MixpanelAPI.DynamicEventTracker";
  private static final int MAX_PROPERTY_LENGTH = 128;
  private final Map<Signature, UnsentEvent> mDebouncedEvents;
  private final Handler mHandler;
  private final MixpanelAPI mMixpanel;
  private final Runnable mTask;
  
  public DynamicEventTracker(MixpanelAPI paramMixpanelAPI, Handler paramHandler)
  {
    this.mMixpanel = paramMixpanelAPI;
    this.mDebouncedEvents = new HashMap();
    this.mTask = new SendDebouncedTask(null);
    this.mHandler = paramHandler;
  }
  
  private static String textPropertyFromView(View paramView)
  {
    Object localObject2 = null;
    Object localObject1;
    if ((paramView instanceof TextView))
    {
      paramView = ((TextView)paramView).getText();
      localObject1 = localObject2;
      if (paramView != null) {
        localObject1 = paramView.toString();
      }
    }
    StringBuilder localStringBuilder;
    int j;
    do
    {
      do
      {
        return (String)localObject1;
        localObject1 = localObject2;
      } while (!(paramView instanceof ViewGroup));
      localStringBuilder = new StringBuilder();
      paramView = (ViewGroup)paramView;
      int m = paramView.getChildCount();
      j = 0;
      int i = 0;
      while ((i < m) && (localStringBuilder.length() < 128))
      {
        localObject1 = textPropertyFromView(paramView.getChildAt(i));
        int k = j;
        if (localObject1 != null)
        {
          k = j;
          if (((String)localObject1).length() > 0)
          {
            if (j != 0) {
              localStringBuilder.append(", ");
            }
            localStringBuilder.append((String)localObject1);
            k = 1;
          }
        }
        i += 1;
        j = k;
      }
      if (localStringBuilder.length() > 128) {
        return localStringBuilder.substring(0, 128);
      }
      localObject1 = localObject2;
    } while (j == 0);
    return localStringBuilder.toString();
  }
  
  public void OnEvent(View paramView, String arg2, boolean paramBoolean)
  {
    long l = System.currentTimeMillis();
    Object localObject = new JSONObject();
    try
    {
      ((JSONObject)localObject).put("$text", textPropertyFromView(paramView));
      ((JSONObject)localObject).put("$from_binding", true);
      ((JSONObject)localObject).put("time", l / 1000L);
      if (paramBoolean)
      {
        paramView = new Signature(paramView, ???);
        localObject = new UnsentEvent(???, (JSONObject)localObject, l);
      }
      this.mMixpanel.track(???, (JSONObject)localObject);
    }
    catch (JSONException localJSONException)
    {
      synchronized (this.mDebouncedEvents)
      {
        paramBoolean = this.mDebouncedEvents.isEmpty();
        this.mDebouncedEvents.put(paramView, localObject);
        if (paramBoolean) {
          this.mHandler.postDelayed(this.mTask, 1000L);
        }
        return;
        localJSONException = localJSONException;
        Log.e(LOGTAG, "Can't format properties from view due to JSON issue", localJSONException);
      }
    }
  }
  
  private final class SendDebouncedTask
    implements Runnable
  {
    private SendDebouncedTask() {}
    
    public void run()
    {
      long l = System.currentTimeMillis();
      synchronized (DynamicEventTracker.this.mDebouncedEvents)
      {
        Iterator localIterator = DynamicEventTracker.this.mDebouncedEvents.entrySet().iterator();
        while (localIterator.hasNext())
        {
          DynamicEventTracker.UnsentEvent localUnsentEvent = (DynamicEventTracker.UnsentEvent)((Map.Entry)localIterator.next()).getValue();
          if (l - localUnsentEvent.timeSentMillis > 1000L)
          {
            DynamicEventTracker.this.mMixpanel.track(localUnsentEvent.eventName, localUnsentEvent.properties);
            localIterator.remove();
          }
        }
      }
      if (!DynamicEventTracker.this.mDebouncedEvents.isEmpty()) {
        DynamicEventTracker.this.mHandler.postDelayed(this, 500L);
      }
    }
  }
  
  private static class Signature
  {
    private final int mHashCode;
    
    public Signature(View paramView, String paramString)
    {
      this.mHashCode = (paramView.hashCode() ^ paramString.hashCode());
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool2 = false;
      boolean bool1 = bool2;
      if ((paramObject instanceof Signature))
      {
        bool1 = bool2;
        if (this.mHashCode == paramObject.hashCode()) {
          bool1 = true;
        }
      }
      return bool1;
    }
    
    public int hashCode()
    {
      return this.mHashCode;
    }
  }
  
  private static class UnsentEvent
  {
    public final String eventName;
    public final JSONObject properties;
    public final long timeSentMillis;
    
    public UnsentEvent(String paramString, JSONObject paramJSONObject, long paramLong)
    {
      this.eventName = paramString;
      this.properties = paramJSONObject;
      this.timeSentMillis = paramLong;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/viewcrawler/DynamicEventTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */