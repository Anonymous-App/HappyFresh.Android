package com.appsee;

import android.os.Build.VERSION;
import java.security.InvalidParameterException;
import java.util.List;

class ob
{
  public static List<String> i()
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return jd.i();
    }
    return null;
  }
  
  public void i()
    throws Exception
  {
    if (this.k == null) {
      throw new Exception(AppseeBackgroundUploader.i("~!I N)[zQ5K1\035>\\)Q7_5C5QrQ~\006~^!I N%G=\037;Q1\035=T:]"));
    }
    this.k.i();
    this.k = null;
  }
  
  public void i(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString, boolean paramBoolean)
    throws Exception
  {
    l();
    boolean bool = AppseeNativeExtensions.i(this.G);
    if ((this.k == null) || (!bool)) {
      throw new Exception(AppseeBackgroundUploader.i("z1[<XeT2T.No\\%M?PzZ\027?Q8J"));
    }
    this.k.i(paramInt1, paramInt2, paramInt3, paramInt4, paramString, paramBoolean);
    mc.i();
  }
  
  public void i(xd paramxd, long paramLong)
    throws Exception
  {
    if (this.k == null) {
      throw new Exception(AppseeBackgroundUploader.i("~!I N)[zQ5K1\035>\\)Q7_5C5QrQ~\006~^!I N%G=\037;Q1\035=T:]"));
    }
    if (paramxd == null) {
      throw new InvalidParameterException(AppseeBackgroundUploader.i("\035@!C1\007+K?F6\n%D;X?\037s\0016S8J"));
    }
    if (paramLong < 0L) {
      throw new InvalidParameterException(AppseeBackgroundUploader.i("x'DeT.I*Y*D8H.V5QE\035=P\bK"));
    }
    this.k.i(paramxd, paramLong);
  }
  
  public boolean i()
    throws Exception
  {
    if (this.k == null) {
      throw new Exception(AppseeBackgroundUploader.i("~!I N)[zQ5K1\035>\\)Q7_5C5QrQ~\006~^!I N%G=\037;Q1\035=T:]"));
    }
    return this.k.i();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/ob.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */