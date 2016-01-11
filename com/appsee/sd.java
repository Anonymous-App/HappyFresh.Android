package com.appsee;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Build.VERSION;
import android.os.PowerManager;

class sd
{
  public static sd i()
  {
    try
    {
      if (a == null) {
        a = new sd();
      }
      sd localsd = a;
      return localsd;
    }
    finally {}
  }
  
  public static boolean i()
  {
    return Build.VERSION.SDK_INT >= 14;
  }
  
  @TargetApi(14)
  public void F()
  {
    if ((i()) && (this.d == null))
    {
      this.d = new nd(this, null);
      jn.i().registerActivityLifecycleCallbacks(this.d);
    }
  }
  
  public void a()
  {
    if (!this.h.isScreenOn()) {
      l(AppseeBackgroundUploader.i("y/[?Z4\037x\007lF=X"));
    }
  }
  
  public wd i()
  {
    return new wd(this);
  }
  
  public void i(int paramInt) {}
  
  public void i(Activity paramActivity, boolean paramBoolean)
  {
    i(paramActivity, paramBoolean, false);
  }
  
  public void i(u paramu)
  {
    this.K = paramu;
  }
  
  public void i(String paramString)
  {
    i(paramString, false);
  }
  
  public void l()
    throws Exception
  {
    if (this.k)
    {
      mc.l(AppseeBackgroundUploader.i("f9^a\0047U(\n?K9Z?\037,^}\035(H/W<X|X ErV}\006;Z+SoC\"\t8^9Tv\006#\\5Z"), 1);
      return;
    }
    synchronized (this.b)
    {
      this.b.wait(2000L);
      if (kk.i().l())
      {
        mc.l(AppseeBackgroundUploader.i("q~\0010_oK,^%_3K#\037r\034#F([!\032|K5A Nx\0329\033?K:Y)\t,^6Vu\0258@4P"), 1);
        ol.i(ak.b);
        return;
      }
    }
    synchronized (this.m)
    {
      if (!this.i) {
        l(AppseeBackgroundUploader.i("\034T!C1\025=O&\\&^5\t-^)\037a\0259Z>Z"));
      }
      return;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/sd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */