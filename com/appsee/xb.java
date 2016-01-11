package com.appsee;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

class xb
{
  public static xb i()
  {
    try
    {
      if (K == null) {
        K = new xb();
      }
      xb localxb = K;
      return localxb;
    }
    finally {}
  }
  
  public List<db> i()
  {
    return this.G;
  }
  
  public JSONArray i()
    throws JSONException
  {
    synchronized (this.G)
    {
      JSONArray localJSONArray = new JSONArray();
      Iterator localIterator = this.G.iterator();
      while (localIterator.hasNext()) {
        localJSONArray.put(((db)localIterator.next()).i());
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
  
  public void i(MotionEvent paramMotionEvent, View paramView)
    throws Exception
  {
    bb localbb = bb.b;
    short s3 = (short)(int)paramMotionEvent.getX(paramMotionEvent.getActionIndex());
    short s2 = (short)(int)paramMotionEvent.getY(paramMotionEvent.getActionIndex());
    Object localObject;
    short s1;
    switch (paramMotionEvent.getActionMasked())
    {
    case 3: 
    case 4: 
    default: 
      localObject = localbb;
      s1 = 0;
    }
    for (;;)
    {
      if ((localObject != bb.b) && (s1 > 0))
      {
        i(s1, s3, s2, localbb, paramView);
        i(paramMotionEvent, new short[] { s1 }, paramView);
      }
      return;
      localbb = bb.k;
      s1 = i(paramMotionEvent.getPointerId(paramMotionEvent.getActionIndex()));
      localObject = localbb;
      continue;
      localbb = bb.G;
      s1 = l(paramMotionEvent.getPointerId(paramMotionEvent.getActionIndex()));
      localObject = localbb;
      continue;
      localbb = bb.b;
      localObject = new short[paramMotionEvent.getPointerCount()];
      int j = 0;
      int n = 0;
      s1 = 0;
      while (j < paramMotionEvent.getPointerCount())
      {
        s1 = l(paramMotionEvent.getPointerId(n));
        localObject[n] = s1;
        if (s1 > 0)
        {
          s3 = (short)(int)paramMotionEvent.getX(n);
          s2 = (short)(int)paramMotionEvent.getY(n);
          i(s1, s3, s2, localbb, paramView);
        }
        j = n + 1;
        n = j;
      }
      i(paramMotionEvent, (short[])localObject, paramView);
      localObject = localbb;
      continue;
      localbb = bb.k;
      s1 = i(paramMotionEvent.getPointerId(paramMotionEvent.getActionIndex()));
      localObject = localbb;
      continue;
      localbb = bb.G;
      s1 = l(paramMotionEvent.getPointerId(paramMotionEvent.getActionIndex()));
      localObject = localbb;
    }
  }
  
  public void i(short paramShort1, short paramShort2, short paramShort3, bb parambb, View arg5)
    throws Exception
  {
    if (!jc.i().i()) {}
    while (jc.i().i() == -1L) {
      return;
    }
    if ((parambb == bb.k) || (parambb == bb.G)) {
      ub.i().c();
    }
    ??? = pb.i(???);
    paramShort2 = (short)(???.left + paramShort2);
    paramShort3 = (short)(???.top + paramShort3);
    ??? = pb.i(new Point(paramShort2, paramShort3));
    if ((m.i().F()) && (m.i().i()))
    {
      mc.l(AppseeBackgroundUploader.i("z;W?G;YvT.Z:Y*Nl_3[?P1\000(W>P"), 1);
      return;
    }
    db localdb = new db(paramShort1, (short)???.x, (short)???.y, jc.i().i(), parambb, ud.i().i());
    for (;;)
    {
      synchronized (this.G)
      {
        if (parambb != bb.b) {
          break;
        }
        if (this.G.isEmpty())
        {
          parambb = null;
          if ((parambb == null) || (parambb.i() != bb.b) || (localdb.i() - parambb.i() >= 120L)) {
            break;
          }
          return;
        }
      }
      parambb = (db)this.G.get(this.G.size() - 1);
    }
    mc.l(paramShort1 + AppseeBackgroundUploader.i("g\017}\020") + paramShort2 + AppseeBackgroundUploader.i("\024") + paramShort3 + AppseeBackgroundUploader.i("\026zT,\022&N.]"), 1);
    this.G.add(localdb);
  }
  
  public boolean i()
  {
    synchronized (this.G)
    {
      if (this.G.isEmpty()) {
        return false;
      }
      db localdb = (db)this.G.get(this.G.size() - 1);
      if ((localdb != null) && (localdb.i() == bb.b) && (jc.i().i() - localdb.i() <= 500L)) {
        return true;
      }
    }
    return false;
  }
  
  public short[] i(int paramInt)
  {
    if (this.i.containsKey(Integer.valueOf(paramInt))) {
      return (short[])this.i.get(Integer.valueOf(paramInt));
    }
    return null;
  }
  
  public short l(int paramInt)
  {
    if (paramInt >= this.m.length) {
      return -1;
    }
    return this.m[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/xb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */