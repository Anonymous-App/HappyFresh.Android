package com.appsee;

import java.io.File;
import java.util.List;

class ac
  implements j
{
  public void i()
    throws Exception
  {
    this.d.l();
    if (!this.K) {
      throw new Exception(AppseeBackgroundUploader.i("\025[1Xu\021,\033&YoD#]zV4Ve\035?O9]"));
    }
    mc.l(this.G, 1);
    boolean bool = AppseeNativeExtensions.i();
    if (!bool) {
      mc.l(AppseeBackgroundUploader.i("\024^e\035(^|\\8P3]9[5\027w\0350R<B*Nl^3K2\037t\006,T.J"), 1);
    }
    mc.l(AppseeBackgroundUploader.i("\001\\\"V4X1\0027_9V"), 1);
    try
    {
      this.m = true;
      mo localmo = yc.i().i(AppseeBackgroundUploader.i("\\\001&R2^"));
      localmo.a();
      l();
      localmo.F();
      localmo.l();
      this.m = false;
      if (!bool) {
        throw new Exception(AppseeBackgroundUploader.i("\031K$\\.\0316\\<^b\0347U(\n*D/F>V4X1\0027_9V"));
      }
    }
    finally
    {
      this.m = false;
    }
  }
  
  public void i(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString, boolean paramBoolean)
    throws Exception
  {
    this.I = paramInt1;
    this.i = paramInt2;
    this.a = paramInt3;
    this.C = paramInt4;
    this.b = paramString;
    this.e = (AppseeBackgroundUploader.i("\027") + "h264");
    this.k = paramBoolean;
    this.h.clear();
    this.G = 0;
    mc.l(AppseeBackgroundUploader.i("r2P\"Z=U9O7\027\025*R9Oo\\%M?PzZ\0271_9K"), 1);
    Object localObject = ge.i().F();
    paramString = new String[((List)localObject).size()];
    int[] arrayOfInt = new int[((List)localObject).size()];
    paramInt2 = 0;
    for (paramInt1 = 0; paramInt2 < ((List)localObject).size(); paramInt1 = paramInt2)
    {
      paramString[paramInt1] = ((a)((List)localObject).get(paramInt1)).G;
      paramInt3 = ((a)((List)localObject).get(paramInt1)).k.intValue();
      paramInt2 = paramInt1 + 1;
      arrayOfInt[paramInt1] = paramInt3;
    }
    localObject = hd.i(this.e).getAbsolutePath();
    paramInt1 = this.I;
    paramInt2 = this.i;
    paramInt3 = this.C;
    paramInt4 = this.a;
    boolean bool = ge.i().p();
    if (mc.i() <= 1) {}
    for (paramBoolean = true;; paramBoolean = false)
    {
      this.K = AppseeNativeExtensions.i((String)localObject, paramInt1, paramInt2, paramInt3, paramInt4, paramString, arrayOfInt, bool, paramBoolean, this.k);
      if (this.K) {
        break;
      }
      throw new Exception(AppseeBackgroundUploader.i("R\0250U3MvP.\\1A7\027\025*R9Oo\\%M?PzZ\0271_9K"));
    }
  }
  
  public void i(xd paramxd, long paramLong)
    throws Exception
  {
    if (!this.K) {
      throw new Exception(AppseeBackgroundUploader.i("u=M?E9\0315[1Xu\021,\033&YoD#]zV4Ve\035?O9]"));
    }
    this.d.a();
    if (this.k) {
      mc.l(AppseeBackgroundUploader.i("';U8P8T|_\"T?R1\0221IoD.^%_?\037?Qr\033:R2^"), 1);
    }
    this.G += 1;
    this.h.add(Long.valueOf(paramLong));
    AppseeNativeExtensions.i(this.I, this.i, paramxd.i(), paramLong, this.k);
    this.d.F();
  }
  
  public boolean i()
  {
    return this.m;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/ac.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */