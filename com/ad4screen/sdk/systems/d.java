package com.ad4screen.sdk.systems;

import android.content.Context;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.a;
import com.ad4screen.sdk.common.g;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class d
{
  private static d b;
  private final e a;
  private Context c;
  
  private d(Context paramContext)
  {
    this.a = new e(paramContext);
    this.c = paramContext;
  }
  
  public static d a(Context paramContext)
  {
    try
    {
      if (b == null) {
        b = new d(paramContext.getApplicationContext());
      }
      paramContext = b;
      return paramContext;
    }
    finally {}
  }
  
  public String a(b paramb)
  {
    if (paramb == b.a)
    {
      paramb = b.a(this.c);
      if (paramb.C.equalsIgnoreCase("development")) {
        return "http://api.ad4s.local:8000/routes?partnerId=|partnerId|&sharedId=|sharedId|&version=|version|";
      }
      if (paramb.C.equalsIgnoreCase("preproduction")) {
        return "http://preprodapi.a4.tl/routes?partnerId=|partnerId|&sharedId=|sharedId|&version=|version|";
      }
      return "https://api|SERVER|.accengage.com/routes?partnerId=|partnerId|&sharedId=|sharedId|&version=|version|";
    }
    if ((paramb == b.F) && (this.a.a(paramb) == null))
    {
      paramb = b.a(this.c);
      if (paramb.C.equalsIgnoreCase("development")) {
        return "http://apptrk.ad4s.local/api/event/?partnerId=|partnerId|";
      }
      if (paramb.C.equalsIgnoreCase("preproduction")) {
        return "http://preprodapptrk.a4.tl/api/event/?partnerId=|partnerId|";
      }
      return "https://apptrk.a4.tl/api/event/?partnerId=|partnerId|";
    }
    return this.a.a(paramb);
  }
  
  public List<b> a()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.addAll(d.a.c.a);
    localArrayList.addAll(d.a.b.a);
    localArrayList.addAll(d.a.a.a);
    return Collections.unmodifiableList(localArrayList);
  }
  
  public void a(b paramb, int paramInt)
  {
    this.a.a(paramb, paramInt);
  }
  
  public void a(b paramb, long paramLong)
  {
    this.a.b(paramb, paramLong);
  }
  
  public void a(b paramb, String paramString)
  {
    this.a.a(paramb, paramString);
  }
  
  public boolean b()
  {
    Iterator localIterator = a().iterator();
    while (localIterator.hasNext())
    {
      b localb = (b)localIterator.next();
      if (a(localb) == null)
      {
        Log.debug(localb.toString() + " url is missing");
        return true;
      }
    }
    return false;
  }
  
  public boolean b(b paramb)
  {
    long l1 = g.e().a() / 1000L;
    long l2 = this.a.b(paramb);
    if ((l2 > 0L) && (l1 - l2 > 2592000L)) {
      return true;
    }
    return c(paramb);
  }
  
  public void c()
  {
    b[] arrayOfb = b.values();
    int j = arrayOfb.length;
    int i = 0;
    while (i < j)
    {
      b localb = arrayOfb[i];
      this.a.a(localb, 0);
      i += 1;
    }
  }
  
  public boolean c(b paramb)
  {
    int i = this.a.c(paramb);
    int j = this.a.d(paramb) + 1;
    long l = this.a.e(paramb);
    if (g.e().a() < l) {
      return false;
    }
    if ((i > 1) && (j < i))
    {
      this.a.c(paramb, j);
      return false;
    }
    this.a.c(paramb, 0);
    return true;
  }
  
  public void d()
  {
    b[] arrayOfb = b.values();
    int j = arrayOfb.length;
    int i = 0;
    while (i < j)
    {
      b localb = arrayOfb[i];
      this.a.b(localb, 0L);
      i += 1;
    }
  }
  
  public boolean d(b paramb)
  {
    return (this.a.b(paramb, -1) != -1) || (this.a.c(paramb, -1L) != -1L);
  }
  
  public void e(b paramb)
  {
    this.a.a(paramb, g.e().a() / 1000L);
  }
  
  private static class a
  {
    public static class a
    {
      public static final List<d.b> a;
      
      static
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(d.b.n);
        localArrayList.add(d.b.o);
        localArrayList.add(d.b.p);
        a = Collections.unmodifiableList(localArrayList);
      }
    }
    
    public static class b
    {
      public static final List<d.b> a;
      
      static
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(d.b.k);
        localArrayList.add(d.b.m);
        localArrayList.add(d.b.l);
        localArrayList.add(d.b.d);
        a = Collections.unmodifiableList(localArrayList);
      }
    }
    
    public static class c
    {
      public static final List<d.b> a;
      
      static
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(d.b.f);
        localArrayList.add(d.b.b);
        localArrayList.add(d.b.g);
        localArrayList.add(d.b.h);
        localArrayList.add(d.b.i);
        localArrayList.add(d.b.t);
        localArrayList.add(d.b.u);
        localArrayList.add(d.b.v);
        localArrayList.add(d.b.w);
        localArrayList.add(d.b.D);
        localArrayList.add(d.b.x);
        localArrayList.add(d.b.y);
        localArrayList.add(d.b.A);
        localArrayList.add(d.b.z);
        localArrayList.add(d.b.B);
        localArrayList.add(d.b.C);
        localArrayList.add(d.b.c);
        localArrayList.add(d.b.q);
        localArrayList.add(d.b.r);
        localArrayList.add(d.b.s);
        localArrayList.add(d.b.e);
        localArrayList.add(d.b.j);
        localArrayList.add(d.b.F);
        a = Collections.unmodifiableList(localArrayList);
      }
    }
  }
  
  public static enum b
  {
    private b() {}
    
    public static b a(String paramString)
    {
      if ("BMA4SBulk".equals(paramString)) {
        return f;
      }
      if ("BMA4SVersion".equals(paramString)) {
        return g;
      }
      if ("BMA4SAuthentication".equals(paramString)) {
        return b;
      }
      if ("BMA4SReferrer".equals(paramString)) {
        return j;
      }
      if ("BMA4SInAppNotification".equals(paramString)) {
        return e;
      }
      if ("BMA4SNotificationToken".equals(paramString)) {
        return x;
      }
      if ("BMA4SPrivateTracker".equals(paramString)) {
        return h;
      }
      if ("BMA4SConfig".equals(paramString)) {
        return i;
      }
      if ("BMA4SGeoLoc".equals(paramString)) {
        return y;
      }
      if ("BMA4SBeacons".equals(paramString)) {
        return B;
      }
      if ("BMA4SBeaconsUpdate".equals(paramString)) {
        return C;
      }
      if ("BMA4SGeofencing".equals(paramString)) {
        return A;
      }
      if ("BMA4SGeofencingUpdate".equals(paramString)) {
        return z;
      }
      if ("BMA4SEvent".equals(paramString)) {
        return t;
      }
      if ("BMA4SLead".equals(paramString)) {
        return u;
      }
      if ("BMA4SCart".equals(paramString)) {
        return v;
      }
      if ("BMA4SPurchase".equals(paramString)) {
        return w;
      }
      if ("BMA4SUpdateDeviceFields".equals(paramString)) {
        return c;
      }
      if ("BMA4SUpdateMemberFields".equals(paramString)) {
        return d;
      }
      if ("BMA4SUploadFacebookProfile".equals(paramString)) {
        return D;
      }
      if ("BMA4SEventFacebook".equals(paramString)) {
        return E;
      }
      if ("BMA4SMemberLink".equals(paramString)) {
        return k;
      }
      if ("BMA4SMemberUnlink".equals(paramString)) {
        return l;
      }
      if ("BMA4SMemberList".equals(paramString)) {
        return m;
      }
      if ("BMA4SLocalDate".equals(paramString)) {
        return G;
      }
      if ("BMA4SConnectionType".equals(paramString)) {
        return H;
      }
      if ("BMA4SCarrierName".equals(paramString)) {
        return I;
      }
      if ("BMA4SNotificationTracking".equals(paramString)) {
        return q;
      }
      if ("BMA4SInAppTracking".equals(paramString)) {
        return r;
      }
      if ("BMA4SInboxTracking".equals(paramString)) {
        return s;
      }
      if ("BMA4SInboxList".equals(paramString)) {
        return n;
      }
      if ("BMA4SInboxDetails".equals(paramString)) {
        return o;
      }
      if ("BMA4SInboxUpdate".equals(paramString)) {
        return p;
      }
      if ("BMA4SWebviewScriptUrl".equals(paramString)) {
        return F;
      }
      if ("BMA4SAlwaysUpdateDeviceFields".equals(paramString)) {
        return J;
      }
      return null;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/systems/d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */