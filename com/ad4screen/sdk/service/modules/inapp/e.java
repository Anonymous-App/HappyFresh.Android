package com.ad4screen.sdk.service.modules.inapp;

import android.content.Intent;
import android.location.Location;
import com.ad4screen.sdk.plugins.model.Geofence;
import com.ad4screen.sdk.service.modules.alarm.model.b;
import com.ad4screen.sdk.service.modules.alarm.model.c;
import com.ad4screen.sdk.service.modules.inapp.model.f;
import com.ad4screen.sdk.service.modules.inapp.model.g;
import com.ad4screen.sdk.service.modules.inapp.model.i;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public final class e
{
  public static double a(Location paramLocation, Geofence paramGeofence)
  {
    f localf = new f();
    localf.a(paramGeofence.latitude);
    localf.b(paramGeofence.longitude);
    localf.c(paramGeofence.radius);
    return a(paramLocation, localf);
  }
  
  public static double a(Location paramLocation, f paramf)
  {
    double d1 = paramLocation.getLatitude();
    double d3 = paramLocation.getLongitude();
    double d2 = paramf.a();
    double d7 = paramf.b() * 0.017453292519943295D - d3 * 0.017453292519943295D;
    d1 = Math.atan(Math.tan(d1 * 0.017453292519943295D) * 0.996647189328169D);
    d2 = Math.atan(0.996647189328169D * Math.tan(d2 * 0.017453292519943295D));
    double d8 = Math.cos(d1);
    double d9 = Math.cos(d2);
    double d10 = Math.sin(d1);
    double d11 = Math.sin(d2);
    double d12 = d8 * d9;
    double d13 = d10 * d11;
    int i = 0;
    double d4 = d7;
    d3 = 0.0D;
    d2 = 0.0D;
    double d5;
    for (d1 = 0.0D;; d1 = d5)
    {
      double d14;
      double d15;
      double d6;
      if (i < 20)
      {
        d1 = Math.cos(d4);
        d2 = Math.sin(d4);
        d3 = d9 * d2;
        d5 = d8 * d11 - d10 * d9 * d1;
        d14 = Math.sqrt(d3 * d3 + d5 * d5);
        d15 = d13 + d1 * d12;
        d1 = Math.atan2(d14, d15);
        if (d14 != 0.0D) {
          break label498;
        }
        d3 = 0.0D;
        d6 = 1.0D - d3 * d3;
        if (d6 != 0.0D) {
          break label511;
        }
      }
      label498:
      label511:
      for (d5 = 0.0D;; d5 = d15 - 2.0D * d13 / d6)
      {
        double d16 = d6 * 0.006739496756586903D;
        d2 = 1.0D + d16 / 16384.0D * (4096.0D + (-768.0D + (320.0D - 175.0D * d16) * d16) * d16);
        d16 = (d16 * (-128.0D + (74.0D - 47.0D * d16) * d16) + 256.0D) * (d16 / 1024.0D);
        double d17 = 2.0955066698943685E-4D * d6 * ((4.0D - d6 * 3.0D) * 0.0033528106718309896D + 4.0D);
        d6 = d5 * d5;
        double d18 = d16 / 4.0D;
        d6 = (((-1.0D + 2.0D * d6) * d15 - (d6 * 4.0D - 3.0D) * (d16 / 6.0D * d5 * (-3.0D + 4.0D * d14 * d14))) * d18 + d5) * (d16 * d14);
        d3 = d7 + ((d5 + d15 * d17 * (-1.0D + 2.0D * d5 * d5)) * (d14 * d17) + d1) * (d3 * ((1.0D - d17) * 0.0033528106718309896D));
        if (Math.abs((d3 - d4) / d3) >= 1.0E-12D) {
          break label528;
        }
        d3 = d6;
        d4 = d1;
        d1 = d2;
        d2 = d4;
        return (d2 - d3) * (d1 * 6356752.3142D);
        d3 = d12 * d2 / d14;
        break;
      }
      label528:
      i += 1;
      d4 = d3;
      d3 = d6;
      d5 = d2;
      d2 = d1;
    }
  }
  
  public static void a(Intent paramIntent, HashMap<String, String> paramHashMap)
  {
    Iterator localIterator = paramHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      paramIntent.putExtra(str, (String)paramHashMap.get(str));
    }
  }
  
  public static void a(com.ad4screen.sdk.service.modules.inapp.model.e parame)
  {
    Arrays.sort(parame.a, new Comparator()
    {
      public int a(i paramAnonymousi1, i paramAnonymousi2)
      {
        g localg1 = (g)this.a.b.get(paramAnonymousi1.a());
        g localg2 = (g)this.a.b.get(paramAnonymousi2.a());
        if ((localg1 != null) && (localg1.a() != null) && ((localg1.a() instanceof c))) {}
        do
        {
          do
          {
            do
            {
              return -1;
              if ((localg2 != null) && (localg2.a() != null) && ((localg2.a() instanceof c))) {
                return 1;
              }
            } while ((localg1 != null) && (localg1.a() != null) && ((localg1.a() instanceof b)));
            if ((localg2 != null) && (localg2.a() != null) && ((localg2.a() instanceof b))) {
              return 1;
            }
          } while ((paramAnonymousi1.k() != null) || (paramAnonymousi1.l() != null));
          if ((paramAnonymousi2.k() != null) || (paramAnonymousi2.l() != null)) {
            return 1;
          }
          if (paramAnonymousi1.i() != paramAnonymousi2.i()) {
            return paramAnonymousi1.i() - paramAnonymousi2.i();
          }
          if ((paramAnonymousi1.d() != null) || (paramAnonymousi2.d() != null)) {
            break;
          }
          if ((localg2 != null) && (localg1 != null)) {
            return localg1.b() - localg2.b();
          }
        } while (localg1 != null);
        if (localg2 != null) {
          return 1;
        }
        return 0;
        if (((paramAnonymousi1.d() != null) && (paramAnonymousi2.d() == null)) || ((paramAnonymousi1.d() == null) && (paramAnonymousi2.d() != null))) {
          return localg1.c() - localg2.c();
        }
        if ((paramAnonymousi1.d() != null) && (paramAnonymousi2.d() != null)) {
          if (localg1 == null) {
            break label364;
          }
        }
        label364:
        for (float f1 = localg1.b() / paramAnonymousi1.d().floatValue();; f1 = Float.MAX_VALUE)
        {
          if (localg2 != null) {}
          for (float f2 = localg2.b() / paramAnonymousi2.d().floatValue();; f2 = Float.MAX_VALUE)
          {
            if (f1 < f2) {}
            for (int i = -1;; i = 1) {
              return i;
            }
            return 0;
          }
        }
      }
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */