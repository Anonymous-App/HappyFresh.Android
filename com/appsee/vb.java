package com.appsee;

import android.graphics.Paint;
import java.io.File;
import java.util.EnumSet;

class vb
{
  public static vb i()
  {
    try
    {
      if (i == null) {
        i = new vb();
      }
      vb localvb = i;
      return localvb;
    }
    finally {}
  }
  
  public static String i()
  {
    return "test.mp4";
  }
  
  public void i()
    throws Exception
  {
    if (this.G) {
      return;
    }
    this.G = true;
    for (;;)
    {
      int i1;
      try
      {
        localDimension = new Dimension(ge.i().i(), ge.i().l());
        j = ge.i().F();
        i1 = (int)ge.i().l();
        l2 = rj.i();
        localObject2 = hd.i(i()).getAbsolutePath();
        mc.l(localDimension.getWidth() + AppseeBackgroundUploader.i("\024") + localDimension.getHeight() + AppseeBackgroundUploader.i("\n*[;R?Mp\000,\f`\030") + i1 + AppseeBackgroundUploader.i("zOp\000!\f`\030") + (String)localObject2, 1);
        localob = new ob();
        localob.i(localDimension.getWidth(), localDimension.getHeight(), j * 1024, i1, (String)localObject2, ge.i().i().contains(vi.k));
        localObject2 = new xd(localDimension.getWidth(), localDimension.getHeight());
        localPaint = new Paint();
        localPaint.setStrokeWidth(0.0F);
        i((xd)localObject2, localDimension, localPaint, this.b);
        j = 0;
        l1 = 0L;
        n = 0;
      }
      catch (Exception localException)
      {
        Dimension localDimension;
        long l2;
        Object localObject2;
        ob localob;
        Paint localPaint;
        long l1;
        int i2;
        vd.l(localException, AppseeBackgroundUploader.i("\023A.V\"\025;Y1\027,^.^&D+\t.Z)K1\002 H8W"));
        hd.i(hd.i(i()));
        return;
      }
      finally
      {
        this.G = false;
      }
      i((xd)localObject2, localDimension, localPaint, this.k);
      if (n == i1 * 2) {
        i((xd)localObject2, localDimension, localPaint, this.b);
      }
      mc.l(n + AppseeBackgroundUploader.i("\0231  A8\002") + l1, 1);
      localob.i((xd)localObject2, l1);
      i2 = 1000000 / i1;
      int j = n + 1;
      l1 = i2 + l1;
      int n = j;
      break label443;
      mc.l(AppseeBackgroundUploader.i("s;Yx\0076R!Mo^)Z.\037,Vu\021&\002s\026"), 1);
      localob.i();
      mc.l(rj.i() - l2 + AppseeBackgroundUploader.i("lD3S3Lt\027&B9K"), 1);
      this.G = false;
      return;
      label443:
      if (j <= i1 * 4) {
        if (n != i1) {
          if (n != i1 * 3) {}
        }
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/vb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */