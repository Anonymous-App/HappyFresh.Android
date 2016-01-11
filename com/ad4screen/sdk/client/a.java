package com.ad4screen.sdk.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import com.ad4screen.sdk.A4SService;
import com.ad4screen.sdk.Log;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public abstract class a<I>
{
  private final Context a;
  private final Handler b;
  private final Queue<a<I>> c = new LinkedList();
  private I d;
  private boolean e;
  private boolean f;
  private boolean g;
  private final ServiceConnection h = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      Log.debug("Connected to A4SService");
      a.a(a.this, a.this.b(paramAnonymousIBinder));
      a.a(a.this, false);
      a.a(a.this, new a.a("onServiceConnected")
      {
        public void a(I paramAnonymous2I)
          throws RemoteException
        {
          a.this.a(paramAnonymous2I);
        }
      });
      a.a(a.this);
      a.b(a.this);
    }
    
    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      Log.debug("Disconnected from A4SService");
      a.a(a.this, null);
    }
  };
  private final Runnable i = new Runnable()
  {
    public void run()
    {
      Log.debug("Unbinding from A4SService");
      if (a.c(a.this) != null) {
        a.e(a.this).unbindService(a.d(a.this));
      }
      a.a(a.this, null);
      a.b(a.this, false);
    }
  };
  
  public a(Context paramContext)
  {
    this.a = paramContext.getApplicationContext();
    this.b = new Handler(Looper.getMainLooper());
  }
  
  private void a(Runnable paramRunnable)
  {
    if (Thread.currentThread() == this.b.getLooper().getThread())
    {
      paramRunnable.run();
      return;
    }
    this.b.post(paramRunnable);
  }
  
  private void b()
  {
    d();
    if ((this.d == null) && (!this.e))
    {
      Log.debug("Binding to A4SService");
      Intent localIntent = new Intent(this.a, A4SService.class);
      if (this.a.bindService(localIntent, this.h, 1)) {
        this.e = true;
      }
    }
    else
    {
      return;
    }
    Log.error("Could not bind to A4SService, please check your AndroidManifest.xml");
  }
  
  private void b(a<I> parama)
  {
    a.a(parama, this.d);
  }
  
  private void c()
  {
    if (!this.f)
    {
      this.b.postDelayed(this.i, 10000L);
      this.f = true;
    }
  }
  
  private void d()
  {
    if (this.g) {
      return;
    }
    this.b.removeCallbacks(this.i);
    this.f = false;
  }
  
  private void e()
  {
    Iterator localIterator = this.c.iterator();
    while (localIterator.hasNext()) {
      a.a((a)localIterator.next(), this.d);
    }
    this.c.clear();
  }
  
  public void a()
  {
    this.g = true;
    this.c.clear();
    if (!this.f)
    {
      this.b.post(this.i);
      this.f = true;
    }
    this.a.stopService(new Intent(this.a, A4SService.class));
  }
  
  public void a(final a<I> parama)
  {
    if (this.g) {
      return;
    }
    a(new Runnable()
    {
      public void run()
      {
        a.f(a.this);
        if (a.c(a.this) == null)
        {
          a.g(a.this).offer(parama);
          return;
        }
        a.a.a(parama, a.c(a.this));
        a.b(a.this);
      }
    });
  }
  
  protected abstract void a(I paramI)
    throws RemoteException;
  
  protected abstract I b(IBinder paramIBinder);
  
  public static abstract class a<I>
  {
    private final String a;
    
    public a(String paramString)
    {
      this.a = paramString;
    }
    
    private final void b(I paramI)
    {
      Log.verbose("Sending '" + this.a + "' command");
      try
      {
        a(paramI);
        return;
      }
      catch (RemoteException paramI)
      {
        Log.error("Error while sending '" + this.a + "' command", paramI);
      }
    }
    
    public abstract void a(I paramI)
      throws RemoteException;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/client/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */