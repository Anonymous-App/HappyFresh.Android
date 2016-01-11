package com.appsee;

class yl
  implements Thread.UncaughtExceptionHandler
{
  yl(Thread.UncaughtExceptionHandler paramUncaughtExceptionHandler)
  {
    this.k = paramUncaughtExceptionHandler;
  }
  
  public static void i()
  {
    try
    {
      Thread.UncaughtExceptionHandler localUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
      if (!(localUncaughtExceptionHandler instanceof yl)) {
        Thread.setDefaultUncaughtExceptionHandler(new yl(localUncaughtExceptionHandler));
      }
      return;
    }
    catch (Exception localException)
    {
      vd.l(localException, AppseeBackgroundUploader.i("\t[(P(\037x\032fQ6Q?@(\\\"\025\023Ga\007;^\nR,O<]3P4wp\032\"O6D"));
    }
  }
  
  public static void i(to arg0, boolean paramBoolean)
  {
    for (;;)
    {
      try
      {
        vd.l(???.i(), AppseeBackgroundUploader.i("\037X%R:C1\0010S.D+F)MzZ\"\\t\0042J<X"));
      }
      catch (Exception ???)
      {
        vd.l(???, AppseeBackgroundUploader.i("\nX=E>\t2^4[}\035(DsC8P=V%R:C1\0010S.D+F)MzZ\"\\t\0042J<X"));
        synchronized (G)
        {
          i = false;
          G.notify();
          return;
        }
        ol.i(new fm(???));
        continue;
      }
      finally
      {
        synchronized (G)
        {
          i = false;
          G.notify();
          throw ((Throwable)localObject5);
        }
      }
      synchronized (G)
      {
        if (i)
        {
          mc.l(AppseeBackgroundUploader.i("B\037/S#_8T|Z\"T!_1\034?U+F&D+\t8Z9^d\007#\0032Z$V=])\025:V\0202R!MoK\"F.W?M1\0274B ^"), 3);
          G.wait(3000L);
          synchronized (G)
          {
            i = false;
            G.notify();
            return;
          }
        }
        i = true;
        if (paramBoolean)
        {
          i(???);
          synchronized (G)
          {
            i = false;
            G.notify();
            return;
          }
        }
      }
    }
  }
  
  public void uncaughtException(Thread paramThread, Throwable paramThrowable)
  {
    i(new to(paramThrowable), ol.i());
    try
    {
      if (this.k != null) {
        this.k.uncaughtException(paramThread, paramThrowable);
      }
      return;
    }
    catch (Exception paramThread)
    {
      vd.l(paramThread, AppseeBackgroundUploader.i("T\0064L!\026\"N?P0P0\\jZ6^6]>I\022kgW2Q;Q+F.JvV(V4D(P?[r@x\0006\033 X&M%G;SzWp\032\"O6D"));
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/yl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */