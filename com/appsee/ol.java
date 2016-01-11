package com.appsee;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ol
{
  static final String b = "com.appsee.Action.Mode";
  static final String k = "com.appsee.Action.UploadMode";
  
  public static void i(AppseeListener paramAppseeListener)
  {
    synchronized (G)
    {
      if ((m != null) && (m.contains(paramAppseeListener))) {
        m.remove(paramAppseeListener);
      }
      return;
    }
  }
  
  public static void i(ak paramak)
  {
    i(paramak, null);
  }
  
  public static void i(ak paramak, Bundle paramBundle)
  {
    Application localApplication = jn.i();
    Intent localIntent = new Intent(localApplication, AppseeBackgroundUploader.class);
    localIntent.putExtra("com.appsee.Action.Mode", paramak.ordinal());
    if (paramBundle != null) {
      localIntent.putExtras(paramBundle);
    }
    localApplication.startService(localIntent);
    mc.l(paramak, 1);
  }
  
  public static void i(s params, boolean paramBoolean)
  {
    for (;;)
    {
      synchronized (G)
      {
        if ((m != null) && (!m.isEmpty()))
        {
          if (paramBoolean)
          {
            i(AppseeBackgroundUploader.i("t\"Gb\021;\026\034O<Y%F4\022\026Vb\0005[6D"), new te(params));
            return;
          }
          i(params);
        }
      }
    }
  }
  
  public static void i(v paramv)
  {
    if (i())
    {
      l(paramv);
      return;
    }
    try
    {
      Handler localHandler = new Handler(Looper.getMainLooper());
      synchronized (new Object())
      {
        localHandler.post(new rg(paramv, ???));
        ???.wait();
        return;
      }
      return;
    }
    catch (Exception paramv)
    {
      vd.l(paramv, AppseeBackgroundUploader.i("o>[5MzMd\032>\\=Qv@%W3] X\035$^+\n DlD;V4\037e\034\"P2R"));
    }
  }
  
  public static void i(String paramString, v paramv)
  {
    try
    {
      if (!i.containsKey(paramString)) {
        i.put(paramString, Executors.newSingleThreadExecutor());
      }
      ((ExecutorService)i.get(paramString)).submit(new qf(paramv, paramString));
      return;
    }
    finally {}
  }
  
  public static boolean i()
  {
    return Thread.currentThread().equals(Looper.getMainLooper().getThread());
  }
  
  public static void l(AppseeListener paramAppseeListener)
  {
    synchronized (G)
    {
      if (m == null) {
        m = new ArrayList();
      }
      if (!m.contains(paramAppseeListener)) {
        m.add(paramAppseeListener);
      }
      return;
    }
  }
  
  public static void l(v paramv)
  {
    try
    {
      paramv.i();
      return;
    }
    catch (Exception paramv)
    {
      vd.l(paramv, AppseeBackgroundUploader.i("\026T'W?\026\031A3P\"Cx\0330\033;B=E;GzV4\037e\025#^i\026"));
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/ol.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */