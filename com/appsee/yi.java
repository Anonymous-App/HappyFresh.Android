package com.appsee;

import android.content.Context;
import android.telephony.TelephonyManager;
import java.util.UUID;

final class yi
  implements v
{
  yi(StringBuilder paramStringBuilder) {}
  
  public void i()
    throws Exception
  {
    Object localObject1 = jn.i();
    if (((Context)localObject1).checkCallingOrSelfPermission(AppseeBackgroundUploader.i("^\0204L?WxC9K=\\!Dx\0330\025\035o\016n\023y\022p\024zN'\022b\002v")) != 0)
    {
      this.G.append(rj.a());
      return;
    }
    Object localObject2 = (TelephonyManager)((Context)localObject1).getSystemService(AppseeBackgroundUploader.i("\004.L8V"));
    if (localObject2 == null)
    {
      this.G.append(rj.a());
      return;
    }
    localObject1 = ((TelephonyManager)localObject2).getDeviceId();
    localObject2 = ((TelephonyManager)localObject2).getSimSerialNumber();
    long l1 = rj.a().hashCode();
    long l2 = ((String)localObject1).hashCode();
    localObject1 = new UUID(l1, ((String)localObject2).hashCode() | l2 << 32);
    this.G.append(((UUID)localObject1).toString());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/yi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */