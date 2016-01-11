package com.ad4screen.sdk.service.modules.beacons;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.A4SService.a;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.plugins.BeaconPlugin;
import com.ad4screen.sdk.plugins.beacons.BindBeaconService;
import com.ad4screen.sdk.plugins.beacons.IBeaconService;
import com.ad4screen.sdk.plugins.model.Beacon;
import com.ad4screen.sdk.service.modules.inapp.c.e;
import com.ad4screen.sdk.service.modules.inapp.i;
import com.ad4screen.sdk.systems.f;
import com.ad4screen.sdk.systems.h;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class b
{
  private final A4SService.a a;
  private Bundle b;
  private final com.ad4screen.sdk.service.modules.tracking.a c;
  private final BindBeaconService d;
  
  public b(A4SService.a parama)
  {
    this.a = parama;
    this.c = new com.ad4screen.sdk.service.modules.tracking.a(parama.a());
    this.d = new BindBeaconService(this.a.a().getApplicationContext());
  }
  
  private List<String> b()
  {
    if (System.currentTimeMillis() - this.c.e() >= 3600000L)
    {
      this.c.a(new ArrayList());
      this.c.a(System.currentTimeMillis());
    }
    return this.c.f();
  }
  
  public void a()
  {
    if (this.d != null) {
      this.d.unbindService();
    }
  }
  
  public void a(Bundle paramBundle)
  {
    this.b = paramBundle;
    if (this.b == null)
    {
      Log.error("BeaconManager|No Beacon information found, aborting...");
      return;
    }
    Bundle localBundle1 = paramBundle.getBundle("com.ad4screen.sdk.extra.BEACON_PAYLOAD");
    List localList = b();
    int i;
    if (localBundle1 != null)
    {
      i locali = i.a(h.a(this.a.a()));
      Iterator localIterator = localBundle1.keySet().iterator();
      i = 0;
      if (localIterator.hasNext())
      {
        Bundle localBundle2 = localBundle1.getBundle((String)localIterator.next());
        String str1 = localBundle2.getString("id");
        String str2 = localBundle2.getString("acc");
        if (localBundle2.getInt("transition") == 1)
        {
          paramBundle = "enter";
          label127:
          String str3 = localBundle2.getString("uuid");
          int j = localBundle2.getInt("maj");
          int k = localBundle2.getInt("min");
          if (str1 != null) {
            break label224;
          }
          paramBundle = str3 + ";" + j + ";" + k;
          if (!localList.contains(paramBundle)) {
            localList.add(paramBundle);
          }
        }
        for (i = 1;; i = 1)
        {
          break;
          paramBundle = "exit";
          break label127;
          label224:
          locali.a(new com.ad4screen.sdk.service.modules.inapp.model.a(str1, str2, paramBundle));
          if (localList.contains(str1)) {
            break label371;
          }
          localList.add(str1);
        }
      }
      locali.b(h.a(this.a.a()));
    }
    for (;;)
    {
      if (i == 0)
      {
        Log.debug("BeaconManager|Some beacons have been triggered but were all already sent to server on this session");
        f.a().a(new c.e(this.a.d().f(), true));
        return;
      }
      this.c.a(localList);
      new d(this.a.a(), this.b).run();
      this.a.d().a(this.b, true);
      return;
      label371:
      break;
      i = 0;
    }
  }
  
  public void a(final Beacon[] paramArrayOfBeacon)
  {
    if ((paramArrayOfBeacon == null) || (paramArrayOfBeacon.length == 0)) {}
    BeaconPlugin localBeaconPlugin;
    do
    {
      return;
      localBeaconPlugin = com.ad4screen.sdk.common.plugins.b.e();
    } while ((localBeaconPlugin == null) || (!localBeaconPlugin.isBeaconServiceDeclared(this.a.a())));
    this.d.getService(new A4S.Callback()
    {
      public void a(IBeaconService paramAnonymousIBeaconService)
      {
        if (paramAnonymousIBeaconService == null) {
          return;
        }
        try
        {
          paramAnonymousIBeaconService.add(b.a(b.this).a().getPackageName(), paramArrayOfBeacon);
          return;
        }
        catch (RemoteException paramAnonymousIBeaconService)
        {
          Log.error("BeaconManager|Error while calling BeaconService methods", paramAnonymousIBeaconService);
        }
      }
      
      public void onError(int paramAnonymousInt, String paramAnonymousString) {}
    });
  }
  
  public void a(final String[] paramArrayOfString)
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {}
    BeaconPlugin localBeaconPlugin;
    do
    {
      return;
      localBeaconPlugin = com.ad4screen.sdk.common.plugins.b.e();
    } while ((localBeaconPlugin == null) || (!localBeaconPlugin.isBeaconServiceDeclared(this.a.a())));
    this.d.getService(new A4S.Callback()
    {
      public void a(IBeaconService paramAnonymousIBeaconService)
      {
        if (paramAnonymousIBeaconService == null) {
          return;
        }
        try
        {
          paramAnonymousIBeaconService.remove(b.a(b.this).a().getPackageName(), paramArrayOfString);
          return;
        }
        catch (RemoteException paramAnonymousIBeaconService)
        {
          Log.error("BeaconManager|Error while calling BeaconService methods", paramAnonymousIBeaconService);
        }
      }
      
      public void onError(int paramAnonymousInt, String paramAnonymousString) {}
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/beacons/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */