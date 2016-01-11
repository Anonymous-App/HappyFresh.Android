package com.mixpanel.android.mpmetrics;

import android.util.Log;
import com.mixpanel.android.viewcrawler.TrackingDebug;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class MixpanelApiRetriever
{
  public static MixpanelAPI getApiInstance()
  {
    AtomicReference localAtomicReference = new AtomicReference();
    localAtomicReference.set(null);
    MixpanelAPI.allInstances(new MixpanelAPI.InstanceProcessor()
    {
      public void process(MixpanelAPI paramAnonymousMixpanelAPI)
      {
        if (this.val$ref.get() == null) {
          this.val$ref.set(paramAnonymousMixpanelAPI);
        }
      }
    });
    return (MixpanelAPI)localAtomicReference.get();
  }
  
  public static boolean setTrackingDebug(TrackingDebug paramTrackingDebug, MixpanelAPI paramMixpanelAPI)
  {
    Object localObject3 = MixpanelAPI.class.getDeclaredFields();
    Object localObject2 = null;
    int j = localObject3.length;
    int i = 0;
    Object localObject1;
    for (;;)
    {
      localObject1 = localObject2;
      if (i < j)
      {
        localObject1 = localObject3[i];
        if (!((Field)localObject1).getType().equals(TrackingDebug.class)) {}
      }
      else
      {
        if (localObject1 != null) {
          break;
        }
        Log.e("OPTMP", "Couldn't find tracking debug field!");
        return false;
      }
      i += 1;
    }
    for (;;)
    {
      try
      {
        ((Field)localObject1).setAccessible(true);
        TrackingDebug localTrackingDebug = (TrackingDebug)((Field)localObject1).get(paramMixpanelAPI);
        if (!(localTrackingDebug instanceof TrackingDebugFanout))
        {
          localObject3 = new TrackingDebugFanout(null);
          localObject2 = localObject3;
          if (localTrackingDebug != null)
          {
            ((TrackingDebugFanout)localObject3).addTracker(localTrackingDebug);
            localObject2 = localObject3;
          }
          ((TrackingDebugFanout)localObject2).addTracker(paramTrackingDebug);
        }
        localObject2 = (TrackingDebugFanout)localTrackingDebug;
      }
      catch (Exception paramTrackingDebug)
      {
        try
        {
          ((Field)localObject1).setAccessible(true);
          ((Field)localObject1).set(paramMixpanelAPI, localObject2);
          return true;
        }
        catch (Exception paramTrackingDebug)
        {
          Log.e("OPTMP", paramTrackingDebug.getLocalizedMessage());
        }
        paramTrackingDebug = paramTrackingDebug;
        Log.e("OPTMP", paramTrackingDebug.getLocalizedMessage());
        return false;
      }
    }
    return false;
  }
  
  private static class TrackingDebugFanout
    implements TrackingDebug
  {
    private CopyOnWriteArrayList<TrackingDebug> mTrackers = new CopyOnWriteArrayList();
    
    public void addTracker(TrackingDebug paramTrackingDebug)
    {
      this.mTrackers.addIfAbsent(paramTrackingDebug);
    }
    
    public void reportTrack(String paramString)
    {
      Iterator localIterator = this.mTrackers.iterator();
      while (localIterator.hasNext()) {
        ((TrackingDebug)localIterator.next()).reportTrack(paramString);
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/mpmetrics/MixpanelApiRetriever.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */