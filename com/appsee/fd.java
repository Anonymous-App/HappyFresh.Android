package com.appsee;

import android.content.OperationApplicationException;
import android.graphics.Canvas;
import java.util.List;

class fd
{
  public fd(int paramInt1, int paramInt2, int paramInt3)
  {
    for (int n = 0; j < paramInt1; n = j)
    {
      j = n + 1;
      i(paramInt2, paramInt3);
    }
  }
  
  public xd i()
    throws Exception
  {
    if (this.b) {
      return null;
    }
    if (this.G) {
      throw new OperationApplicationException(AppseeBackgroundUploader.i("\017L/Z2^pA=\027v\021*\033.\n,F)H4Z>\037s\001?Z>L"));
    }
    synchronized (this.m)
    {
      if (this.m.isEmpty())
      {
        mc.l(AppseeBackgroundUploader.i("\024P1\0354]<[vQ)_6P \027p\002?R#K-F)\t<P(\037g\035=Y4\036$V2]5G;YvX~H$C?Z%G=\037<Mp\031<\022u\020"), 2);
        return null;
      }
    }
    xd localxd = (xd)this.m.remove(0);
    return localxd;
  }
  
  public void i()
  {
    int j = 0;
    for (;;)
    {
      synchronized (this.m)
      {
        if (this.k != this.m.size())
        {
          mc.l(AppseeBackgroundUploader.i("v7^v\021y^.X0V.\031 Z=[1\0201^<Dh^lA;I?\037p\0305\034:R:\\?X$P6\027s\0018]*X<\n*F(\0379St\0257U5Y"), 1);
          break label127;
          if (j < this.i.size())
          {
            List localList2 = this.i;
            j = n + 1;
            ((xd)localList2.get(n)).i();
            n = j;
            int i1 = j;
            j = n;
            n = i1;
            continue;
          }
          this.m.clear();
          this.i.clear();
          this.G = true;
          this.b = false;
          return;
        }
      }
      label127:
      int n = 0;
    }
  }
  
  public void i(int paramInt1, int paramInt2)
  {
    synchronized (this.m)
    {
      xd localxd = new xd(paramInt1, paramInt2);
      this.m.add(localxd);
      this.i.add(localxd);
      this.k += 1;
      return;
    }
  }
  
  public void i(xd paramxd)
  {
    if ((paramxd == null) || (this.G)) {
      return;
    }
    synchronized (this.m)
    {
      paramxd.i().drawColor(-16777216);
      this.m.add(paramxd);
      return;
    }
  }
  
  public void l()
  {
    this.b = true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/fd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */