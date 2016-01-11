package com.ad4screen.sdk.service.modules.beacons;

import com.ad4screen.sdk.plugins.model.Beacon;
import com.ad4screen.sdk.systems.f.a;
import java.util.Date;

public final class a
{
  public static final class a
    implements f.a<a.c>
  {
    public void a(a.c paramc)
    {
      paramc.a();
    }
  }
  
  public static final class b
    implements f.a<a.c>
  {
    boolean a;
    boolean b;
    Beacon[] c;
    Date d;
    
    public b(boolean paramBoolean1, boolean paramBoolean2, Beacon[] paramArrayOfBeacon, Date paramDate)
    {
      this.a = paramBoolean1;
      this.b = paramBoolean2;
      this.c = paramArrayOfBeacon;
      this.d = paramDate;
    }
    
    public void a(a.c paramc)
    {
      paramc.a(this.a, this.b, this.c, this.d);
    }
  }
  
  public static abstract interface c
  {
    public abstract void a();
    
    public abstract void a(boolean paramBoolean1, boolean paramBoolean2, Beacon[] paramArrayOfBeacon, Date paramDate);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/beacons/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */