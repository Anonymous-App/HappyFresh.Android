package com.appsee;

import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

class sc
{
  public static sc i()
  {
    try
    {
      if (k == null) {
        k = new sc();
      }
      sc localsc = k;
      return localsc;
    }
    finally {}
  }
  
  public wc i()
  {
    synchronized (this.i)
    {
      if (!this.i.isEmpty())
      {
        wc localwc = (wc)this.i.get(this.i.size() - 1);
        return localwc;
      }
      return null;
    }
  }
  
  public JSONArray i()
    throws JSONException
  {
    synchronized (this.G)
    {
      JSONArray localJSONArray = new JSONArray();
      Iterator localIterator = this.G.iterator();
      while (localIterator.hasNext()) {
        localJSONArray.put(((zc)localIterator.next()).i());
      }
      return localJSONArray;
    }
  }
  
  public void i()
  {
    synchronized (this.i)
    {
      this.i.clear();
    }
    synchronized (this.G)
    {
      this.G.clear();
      return;
      localObject1 = finally;
      throw ((Throwable)localObject1);
    }
  }
  
  public void i(zc paramzc, String arg2)
  {
    if ((!jc.i().i()) || (!ge.i().c())) {
      return;
    }
    synchronized (this.G)
    {
      this.G.add(paramzc);
      mc.l(paramzc.l(), 1);
      return;
    }
  }
  
  public void i(String arg1, vc paramvc, boolean paramBoolean)
  {
    Object localObject1 = ???;
    if (??? == null) {
      localObject1 = "";
    }
    ??? = (String)localObject1;
    boolean bool = paramBoolean;
    if (paramBoolean)
    {
      ??? = new AppseeScreenInfo((String)localObject1);
      ol.i(new tc(this, (AppseeScreenInfo)???), false);
      if (lb.i(((AppseeScreenInfo)???).getScreenName())) {
        return;
      }
      ??? = (String)localObject1;
      bool = paramBoolean;
      if (!((String)localObject1).equals(((AppseeScreenInfo)???).getScreenName()))
      {
        ??? = ((AppseeScreenInfo)???).getScreenName();
        bool = false;
      }
    }
    wc localwc = i();
    if ((localwc != null) && (lb.i(localwc.i(), ???)))
    {
      mc.l(???, 1);
      return;
    }
    localObject1 = new wc(???, jc.i().i(), bool, paramvc);
    if (((wc)localObject1).i() < 0L) {
      ((wc)localObject1).i(0L);
    }
    if ((localwc != null) && (localwc.i() == ((wc)localObject1).i()) && (localwc.i() != 0L)) {
      mc.l(AppseeBackgroundUploader.i("\rzLr\006)L2Jz\023/X=PrCx\031;\027oF.Y8\t3LzHx\032\"@2^"), 1);
    }
    synchronized (this.i)
    {
      this.i.remove(localwc);
      mc.l(??? + AppseeBackgroundUploader.i("zKh\004)\ta\031") + paramvc.ordinal() + AppseeBackgroundUploader.i("lH(\031") + ((wc)localObject1).i(), 1);
      synchronized (this.i)
      {
        this.i.add(localObject1);
        return;
      }
    }
  }
  
  public JSONArray l()
    throws JSONException
  {
    synchronized (this.i)
    {
      JSONArray localJSONArray = new JSONArray();
      Iterator localIterator = this.i.iterator();
      while (localIterator.hasNext()) {
        localJSONArray.put(((wc)localIterator.next()).i());
      }
      return localJSONArray;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/sc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */