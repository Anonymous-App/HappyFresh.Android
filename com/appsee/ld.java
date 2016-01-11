package com.appsee;

import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;

class ld
  implements d, GestureDetector.OnGestureListener, l
{
  public static ld i()
  {
    try
    {
      if (m == null) {
        m = new ld();
      }
      ld localld = m;
      return localld;
    }
    finally {}
  }
  
  public JSONArray i()
    throws JSONException
  {
    synchronized (this.G)
    {
      JSONArray localJSONArray = new JSONArray();
      Iterator localIterator = this.G.iterator();
      while (localIterator.hasNext()) {
        localJSONArray.put(((td)localIterator.next()).l());
      }
      return localJSONArray;
    }
  }
  
  public void i()
  {
    synchronized (this.G)
    {
      this.G.clear();
      return;
    }
  }
  
  public void i(float paramFloat, short[] paramArrayOfShort, MotionEvent paramMotionEvent)
  {
    i(ed.K, paramMotionEvent, paramArrayOfShort);
  }
  
  public void i(int paramInt, short[] paramArrayOfShort, MotionEvent paramMotionEvent)
  {
    if (paramInt >= 0) {}
    for (ed localed = ed.i;; localed = ed.C)
    {
      i(localed, paramMotionEvent, paramArrayOfShort);
      return;
    }
  }
  
  public void i(MotionEvent paramMotionEvent)
  {
    i(ed.a, paramMotionEvent);
  }
  
  public void i(MotionEvent paramMotionEvent, View paramView)
    throws Exception
  {
    if (!ge.i().J()) {}
    do
    {
      return;
      this.K = new WeakReference(paramView);
      if (this.b == null) {
        this.b = new GestureDetector(jn.i(), this);
      }
      this.i.i(paramMotionEvent);
      this.b.onTouchEvent(paramMotionEvent);
      this.k.i(paramMotionEvent);
    } while (paramMotionEvent.getActionMasked() != 1);
    lk.i().i(pb.a(pb.i(paramMotionEvent, paramView)));
  }
  
  public void i(ed paramed, MotionEvent paramMotionEvent, short[] paramArrayOfShort, View arg4)
  {
    if (!jc.i().i()) {}
    Object localObject2;
    Object localObject3;
    do
    {
      do
      {
        return;
        if (paramArrayOfShort != null)
        {
          ??? = paramArrayOfShort;
          if (paramArrayOfShort.length != 0) {}
        }
        else
        {
          ??? = xb.i().i(paramMotionEvent.hashCode());
        }
      } while ((??? == null) || (???.length == 0));
      if (paramMotionEvent != null) {}
      for (int j = paramMotionEvent.hashCode();; j = 0)
      {
        paramArrayOfShort = new td(paramed, true, (short[])???, -1L, -1L, null, j);
        synchronized (xb.i().i())
        {
          localObject2 = paramArrayOfShort.i();
          if (localObject2 == null) {
            break;
          }
          localObject3 = xb.i().i().iterator();
          db localdb;
          do
          {
            do
            {
              if (!((Iterator)localObject3).hasNext()) {
                break;
              }
              localdb = (db)((Iterator)localObject3).next();
            } while (!((List)localObject2).contains(Short.valueOf(localdb.a())));
            if ((paramArrayOfShort.l() == -1L) || (localdb.i() < paramArrayOfShort.l())) {
              paramArrayOfShort.l(localdb.i());
            }
          } while ((paramArrayOfShort.i() != -1L) && (localdb.i() <= paramArrayOfShort.i()));
          paramArrayOfShort.i(localdb.i());
        }
      }
    } while ((paramArrayOfShort.l() == -1L) || (paramArrayOfShort.i() == -1L));
    if (!ge.i().m()) {
      paramArrayOfShort.i(null);
    }
    if (paramed == ed.d) {}
    for (;;)
    {
      try
      {
        ??? = ge.i().i();
        localObject2 = pb.a(???);
        localObject3 = pb.i(???);
        i(paramArrayOfShort, ???, (View)localObject2, (View)localObject3);
        if ((((Set)???).contains(vi.G)) || (((Set)???).contains(vi.K)))
        {
          mc.l(pb.a(???), 1);
          mc.l(pb.a((View)localObject2), 1);
          pb.i(???.getRootView(), ((Set)???).contains(vi.G), ((Set)???).contains(vi.K));
        }
        lk.i().i((View)localObject2, ???, paramArrayOfShort, paramMotionEvent);
        if ((!ge.i().L()) && (localObject3 == null)) {
          paramArrayOfShort.i(false);
        }
      }
      catch (Exception paramMotionEvent)
      {
        vd.l(paramMotionEvent, AppseeBackgroundUploader.i("p E~\006~Z+N&D+\t.^*\037p\027'_4P"));
        continue;
        paramMotionEvent = (td)this.G.get(this.G.size() - 1);
        continue;
        this.G.add(paramArrayOfShort);
        mc.l(paramed + AppseeBackgroundUploader.i("oY8H(KzKx\0316\026f\036") + paramArrayOfShort.l() + AppseeBackgroundUploader.i("lL4[zKx\0316\026f\036") + paramArrayOfShort.i(), 1);
        continue;
      }
      synchronized (this.G)
      {
        if (this.G.isEmpty())
        {
          paramMotionEvent = null;
          if ((paramMotionEvent == null) || (!paramMotionEvent.i()) || (!paramArrayOfShort.l()) || (paramMotionEvent.l() != paramArrayOfShort.l()) || (paramMotionEvent.i() != paramArrayOfShort.i())) {
            continue;
          }
        }
      }
    }
  }
  
  public void l(MotionEvent paramMotionEvent)
  {
    synchronized (this.G)
    {
      if (this.G.isEmpty())
      {
        localtd = null;
        if ((localtd != null) && (localtd.i() == ed.d) && (localtd.i() == paramMotionEvent.hashCode())) {
          localtd.i(false);
        }
        return;
      }
      td localtd = (td)this.G.get(this.G.size() - 1);
    }
  }
  
  public boolean onDown(MotionEvent paramMotionEvent)
  {
    return false;
  }
  
  public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
  {
    paramFloat1 = paramMotionEvent2.getY() - paramMotionEvent1.getY();
    paramFloat2 = paramMotionEvent2.getX() - paramMotionEvent1.getX();
    if (Math.abs(paramFloat2) > Math.abs(paramFloat1)) {
      if (paramFloat2 > 0.0F) {
        i(ed.k, paramMotionEvent2);
      }
    }
    for (;;)
    {
      return false;
      i(ed.m, paramMotionEvent2);
      continue;
      if (paramFloat1 > 0.0F) {
        i(ed.G, paramMotionEvent2);
      } else {
        i(ed.b, paramMotionEvent2);
      }
    }
  }
  
  public void onLongPress(MotionEvent paramMotionEvent) {}
  
  public boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
  {
    return false;
  }
  
  public void onShowPress(MotionEvent paramMotionEvent) {}
  
  public boolean onSingleTapUp(MotionEvent paramMotionEvent)
  {
    View localView = pb.i(paramMotionEvent, (View)this.K.get());
    this.K = null;
    i(ed.d, paramMotionEvent, null, localView);
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/ld.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */