package com.appsee;

import android.os.Handler;
import android.os.HandlerThread;

class we
  extends HandlerThread
{
  public we(co paramco)
  {
    super(AppseeBackgroundUploader.i("oq\024/\\5t!N\027\bR+O o\"J5[?ME\034:H;["), 0);
  }
  
  public void a()
  {
    quit();
  }
  
  public void i()
    throws Exception
  {
    l();
    synchronized (this.K)
    {
      this.i.sendEmptyMessage(1);
      this.K.wait();
      if (this.G != null) {
        throw this.G;
      }
    }
  }
  
  public void l()
  {
    synchronized (this.b)
    {
      for (;;)
      {
        boolean bool = this.d;
        if (bool) {
          break;
        }
        try
        {
          this.b.wait();
        }
        catch (InterruptedException localInterruptedException) {}
      }
      return;
    }
  }
  
  protected void onLooperPrepared()
  {
    super.onLooperPrepared();
    this.i = new lj(this, getLooper());
    synchronized (this.b)
    {
      this.d = true;
      this.b.notify();
      return;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/we.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */