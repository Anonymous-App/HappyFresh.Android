package com.ad4screen.sdk.service.modules.tracking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.A4SService.a;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.plugins.BeaconPlugin;
import com.ad4screen.sdk.plugins.GeofencePlugin;
import com.ad4screen.sdk.plugins.model.Beacon;
import com.ad4screen.sdk.plugins.model.Geofence;
import com.ad4screen.sdk.service.modules.common.h;
import com.ad4screen.sdk.systems.d.b;
import com.ad4screen.sdk.systems.h.i;
import com.ad4screen.sdk.systems.h.j;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public final class f
{
  private final g a;
  private final A4SService.a b;
  private b c;
  private final com.ad4screen.sdk.service.modules.beacons.a.c d = new com.ad4screen.sdk.service.modules.beacons.a.c()
  {
    public void a() {}
    
    public void a(boolean paramAnonymousBoolean1, boolean paramAnonymousBoolean2, Beacon[] paramAnonymousArrayOfBeacon, Date paramAnonymousDate)
    {
      ArrayList localArrayList = new ArrayList();
      Object localObject2 = new ArrayList(Arrays.asList(paramAnonymousArrayOfBeacon));
      paramAnonymousArrayOfBeacon = new a(f.b(f.this).a());
      Object localObject4 = paramAnonymousArrayOfBeacon.a();
      Object localObject1 = new HashMap();
      Object localObject3 = new com.ad4screen.sdk.common.persistence.e();
      localObject4 = ((List)localObject4).iterator();
      while (((Iterator)localObject4).hasNext())
      {
        Object localObject5 = (JSONObject)((Iterator)localObject4).next();
        try
        {
          localObject5 = (Beacon)((com.ad4screen.sdk.common.persistence.e)localObject3).a(((JSONObject)localObject5).toString(), new Beacon(""));
          if (!TextUtils.isEmpty(((Beacon)localObject5).id))
          {
            ((HashMap)localObject1).put(((Beacon)localObject5).id, localObject5);
            localArrayList.add(((Beacon)localObject5).uuid);
          }
        }
        catch (JSONException localJSONException)
        {
          Log.error("Tracker|Failed to parse Beacons");
        }
      }
      if (!paramAnonymousBoolean1) {
        ((HashMap)localObject1).clear();
      }
      localObject2 = ((List)localObject2).iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject3 = (Beacon)((Iterator)localObject2).next();
        if (localObject3 != null) {
          if (((Beacon)localObject3).toRemove) {
            ((HashMap)localObject1).remove(((Beacon)localObject3).id);
          } else {
            ((HashMap)localObject1).put(((Beacon)localObject3).id, localObject3);
          }
        }
      }
      localObject1 = new ArrayList(((HashMap)localObject1).values());
      paramAnonymousArrayOfBeacon.a((ArrayList)localObject1);
      if ((localArrayList.size() > 0) || (((ArrayList)localObject1).size() > 0))
      {
        if (localArrayList.size() > 0)
        {
          Log.debug("Tracker|Unregistering to " + localArrayList.size() + " beacons");
          f.b(f.this).h().a((String[])localArrayList.toArray(new String[localArrayList.size()]));
        }
        if (((ArrayList)localObject1).size() > 0)
        {
          Log.debug("Tracker|Registering to " + ((ArrayList)localObject1).size() + " beacons");
          f.b(f.this).h().a((Beacon[])((ArrayList)localObject1).toArray(new Beacon[((ArrayList)localObject1).size()]));
        }
      }
      for (;;)
      {
        f.d(f.this).b(paramAnonymousDate.getTime());
        return;
        Log.debug("Tracker|No beacons to register/unregister for. Aborting bind to A4SBeaconService");
      }
    }
  };
  private final com.ad4screen.sdk.service.modules.geofencing.a.c e = new com.ad4screen.sdk.service.modules.geofencing.a.c()
  {
    public void a() {}
    
    public void a(boolean paramAnonymousBoolean1, boolean paramAnonymousBoolean2, Geofence[] paramAnonymousArrayOfGeofence, Date paramAnonymousDate)
    {
      ArrayList localArrayList = new ArrayList();
      Object localObject2 = new ArrayList(Arrays.asList(paramAnonymousArrayOfGeofence));
      paramAnonymousArrayOfGeofence = new d(f.b(f.this).a());
      Object localObject4 = paramAnonymousArrayOfGeofence.a();
      Object localObject1 = new HashMap();
      Object localObject3 = new com.ad4screen.sdk.common.persistence.e();
      localObject4 = ((List)localObject4).iterator();
      while (((Iterator)localObject4).hasNext())
      {
        Object localObject5 = (JSONObject)((Iterator)localObject4).next();
        try
        {
          localObject5 = (Geofence)((com.ad4screen.sdk.common.persistence.e)localObject3).a(((JSONObject)localObject5).toString(), new Geofence(""));
          if (TextUtils.isEmpty(((Geofence)localObject5).id)) {
            continue;
          }
          if (paramAnonymousBoolean1) {
            break label160;
          }
          localArrayList.add(((Geofence)localObject5).id);
        }
        catch (JSONException localJSONException)
        {
          Log.error("Tracker|Failed to parse Geofence");
        }
        continue;
        label160:
        ((HashMap)localObject1).put(localJSONException.id, localJSONException);
      }
      localObject2 = ((List)localObject2).iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject3 = (Geofence)((Iterator)localObject2).next();
        if (localObject3 != null) {
          if (((Geofence)localObject3).toRemove)
          {
            ((HashMap)localObject1).remove(((Geofence)localObject3).id);
            localArrayList.add(((Geofence)localObject3).id);
          }
          else
          {
            ((HashMap)localObject1).put(((Geofence)localObject3).id, localObject3);
          }
        }
      }
      localObject1 = new ArrayList(((HashMap)localObject1).values());
      paramAnonymousArrayOfGeofence.a((ArrayList)localObject1);
      if ((paramAnonymousBoolean1) || (!paramAnonymousBoolean2))
      {
        paramAnonymousArrayOfGeofence = com.ad4screen.sdk.systems.a.a(f.b(f.this).a()).d();
        if (paramAnonymousArrayOfGeofence != null)
        {
          localObject2 = ((ArrayList)localObject1).iterator();
          while (((Iterator)localObject2).hasNext())
          {
            localObject3 = (Geofence)((Iterator)localObject2).next();
            ((Geofence)localObject3).distance = i.a(paramAnonymousArrayOfGeofence.getLatitude(), paramAnonymousArrayOfGeofence.getLongitude(), ((Geofence)localObject3).latitude, ((Geofence)localObject3).longitude);
          }
          Collections.sort((List)localObject1);
        }
      }
      paramAnonymousArrayOfGeofence = com.ad4screen.sdk.common.plugins.b.d();
      if (paramAnonymousArrayOfGeofence != null)
      {
        if (localArrayList.size() > 0)
        {
          Log.debug("Tracker|Unregistering to " + localArrayList.size() + " geofences");
          paramAnonymousArrayOfGeofence.remove(f.b(f.this).a(), (String[])localArrayList.toArray(new String[localArrayList.size()]));
        }
        if (((ArrayList)localObject1).size() > 0)
        {
          Log.debug("Tracker|Registering to " + ((ArrayList)localObject1).size() + " geofences");
          paramAnonymousArrayOfGeofence.add(f.b(f.this).a(), (Geofence[])((ArrayList)localObject1).toArray(new Geofence[((ArrayList)localObject1).size()]));
        }
      }
      f.d(f.this).a(paramAnonymousDate.getTime());
    }
  };
  private final h.i f = new h.i()
  {
    public void a()
    {
      com.ad4screen.sdk.systems.b.a(f.b(f.this).a()).a();
      f.this.a();
    }
  };
  
  public f(A4SService.a parama)
  {
    this.b = parama;
    this.a = new g(parama.a());
    this.c = new b(parama);
    com.ad4screen.sdk.systems.f.a().a(h.j.class, this.f);
    com.ad4screen.sdk.systems.f.a().a(com.ad4screen.sdk.service.modules.geofencing.a.b.class, this.e);
    com.ad4screen.sdk.systems.f.a().a(com.ad4screen.sdk.service.modules.geofencing.a.a.class, this.e);
    com.ad4screen.sdk.systems.f.a().a(com.ad4screen.sdk.service.modules.beacons.a.b.class, this.d);
    com.ad4screen.sdk.systems.f.a().a(com.ad4screen.sdk.service.modules.beacons.a.a.class, this.d);
  }
  
  public static void a(final A4S.Callback<String> paramCallback, Context paramContext)
  {
    com.ad4screen.sdk.systems.b localb = com.ad4screen.sdk.systems.b.a(paramContext);
    if (localb.c != null)
    {
      Log.debug("Tracker|IDFV was cached in application : " + localb.c);
      paramCallback.onResult(localb.c);
      return;
    }
    Log.debug("Tracker|No IDFV stored, querying other apps...");
    Intent localIntent = new Intent("com.ad4screen.sdk.intent.action.QUERY");
    localIntent.addCategory("com.ad4screen.sdk.intent.category.ANONYM_ID");
    paramContext.sendOrderedBroadcast(localIntent, null, new BroadcastReceiver()new Handler
    {
      public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
      {
        paramAnonymousIntent = getResultExtras(true).getStringArrayList("anonymId");
        paramAnonymousContext = new HashMap();
        if (paramAnonymousIntent != null)
        {
          Log.debug("Tracker|" + paramAnonymousIntent.size() + " Response(s) from other apps...");
          paramAnonymousIntent = paramAnonymousIntent.iterator();
          while (paramAnonymousIntent.hasNext())
          {
            Object localObject2 = ((String)paramAnonymousIntent.next()).split("\\|");
            if (localObject2.length != 2)
            {
              Log.debug("Tracker|1 response with wrong format found...");
            }
            else
            {
              localObject1 = localObject2[0];
              localObject2 = localObject2[1];
              if (!i.a((String)localObject1 + "y^X*4]6k],:!e%$&n{@[#!|S2[yH#/I1]Qd;^{+'J1rAkp8!%&&)OV0)\"" + "H$#V2{\"O<+v^6k=q}74;1}=6X3-:G~&F!$]f_L6C>@M").equals(localObject2))
              {
                Log.debug("Tracker|1 bad response found...");
              }
              else
              {
                localObject2 = (int[])paramAnonymousContext.get(localObject1);
                if (localObject2 == null) {
                  paramAnonymousContext.put(localObject1, new int[] { 1 });
                } else {
                  localObject2[0] += 1;
                }
              }
            }
          }
        }
        Object localObject1 = paramAnonymousContext.entrySet().iterator();
        paramAnonymousContext = null;
        while (((Iterator)localObject1).hasNext())
        {
          paramAnonymousIntent = (Map.Entry)((Iterator)localObject1).next();
          Log.debug("Tracker|Id '" + (String)paramAnonymousIntent.getKey() + "' has " + ((int[])paramAnonymousIntent.getValue())[0] + " occurence(s)...");
          if (paramAnonymousContext == null)
          {
            paramAnonymousContext = paramAnonymousIntent;
          }
          else
          {
            if (((int[])paramAnonymousContext.getValue())[0] >= ((int[])paramAnonymousIntent.getValue())[0]) {
              break label455;
            }
            paramAnonymousContext = paramAnonymousIntent;
          }
        }
        label455:
        for (;;)
        {
          break;
          if (paramAnonymousContext != null)
          {
            Log.debug("Tracker|Id '" + (String)paramAnonymousContext.getKey() + " is probably good, using it.");
            paramAnonymousIntent = (String)paramAnonymousContext.getKey();
            paramAnonymousContext = paramAnonymousIntent;
            if (paramAnonymousIntent.contains("#")) {
              paramAnonymousContext = paramAnonymousIntent.substring(1);
            }
          }
          for (;;)
          {
            this.a.c(paramAnonymousContext);
            com.ad4screen.sdk.systems.f.a().a(new h.f());
            paramCallback.onResult(paramAnonymousContext);
            return;
            paramAnonymousContext = UUID.randomUUID().toString();
            Log.debug("Tracker|No good entry found, generating one : '" + paramAnonymousContext + "'.");
          }
        }
      }
    }, new Handler(), -1, null, null);
  }
  
  protected static boolean a(Object paramObject)
  {
    if ((paramObject instanceof String))
    {
      paramObject = (String)paramObject;
      if ((((String)paramObject).startsWith("+")) || (((String)paramObject).startsWith("-"))) {
        return true;
      }
    }
    return false;
  }
  
  private boolean d()
  {
    return this.a.g();
  }
  
  public void a()
  {
    Log.debug("Tracker|Tracking started");
    a(new A4S.Callback()
    {
      public void a(String paramAnonymousString)
      {
        if (f.a(f.this))
        {
          Log.info("Tracker|Tracking refused");
          return;
        }
        new com.ad4screen.sdk.service.modules.inapp.g(f.b(f.this).a(), null, false).run();
        f.c(f.this).a();
      }
      
      public void onError(int paramAnonymousInt, String paramAnonymousString) {}
    }, this.b.a());
  }
  
  public void a(long paramLong, Bundle paramBundle, String... paramVarArgs)
  {
    if (d())
    {
      Log.debug("Tracker|Tracking refused");
      return;
    }
    this.c.a(paramLong, paramBundle, paramVarArgs);
  }
  
  public void a(Bundle paramBundle, boolean paramBoolean)
  {
    if ((paramBoolean) || ((com.ad4screen.sdk.systems.d.a(this.b.a()).d(d.b.J)) && (!com.ad4screen.sdk.systems.d.a(this.b.a()).c(d.b.J)))) {
      if (paramBundle.size() > 0) {
        h.a(this.b, paramBundle, paramBoolean);
      }
    }
    Bundle localBundle2;
    do
    {
      return;
      Bundle localBundle1 = this.a.f();
      localBundle2 = new Bundle(paramBundle);
      Iterator localIterator = paramBundle.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        if ((!a(paramBundle.get(str))) && (localBundle1.containsKey(str)) && (localBundle1.get(str).equals(paramBundle.get(str))))
        {
          Log.debug("Tracker|Key : " + str + " is already up to date and will not be sent again");
          localBundle2.remove(str);
        }
      }
      localBundle1.putAll(localBundle2);
      this.a.a(localBundle1);
    } while (localBundle2.size() <= 0);
    h.a(this.b, localBundle2, paramBoolean);
  }
  
  public void a(Cart paramCart, Bundle paramBundle)
  {
    if (d())
    {
      Log.debug("Tracker|Tracking refused");
      return;
    }
    this.c.a(paramCart, paramBundle);
  }
  
  public void a(Lead paramLead, Bundle paramBundle)
  {
    if (d())
    {
      Log.debug("Tracker|Tracking refused");
      return;
    }
    this.c.a(paramLead, paramBundle);
  }
  
  public void a(Purchase paramPurchase, Bundle paramBundle)
  {
    if (d())
    {
      Log.debug("Tracker|Tracking refused");
      return;
    }
    this.c.a(paramPurchase, paramBundle);
  }
  
  public void a(com.ad4screen.sdk.service.modules.tracking.model.c paramc)
  {
    this.c.a(paramc);
  }
  
  public void a(String paramString)
  {
    com.ad4screen.sdk.systems.b.a(this.b.a()).a(paramString);
    new e(this.b.a()).run();
  }
  
  public void a(String paramString1, String paramString2, String[] paramArrayOfString)
  {
    Log.debug("Tracker|Uploading Facebook Profile");
    new c(this.b.a(), paramString1, paramString2, paramArrayOfString).run();
  }
  
  public void a(boolean paramBoolean)
  {
    Object localObject = com.ad4screen.sdk.common.plugins.b.d();
    if (localObject != null)
    {
      if (!((GeofencePlugin)localObject).isGeofencingServiceDeclared(this.b.a())) {
        break label105;
      }
      new com.ad4screen.sdk.service.modules.geofencing.d(this.b.a(), c().longValue()).run();
    }
    for (;;)
    {
      localObject = com.ad4screen.sdk.common.plugins.b.e();
      if (localObject != null)
      {
        if (!((BeaconPlugin)localObject).isBeaconServiceDeclared(this.b.a())) {
          break;
        }
        new com.ad4screen.sdk.service.modules.beacons.e(this.b.a(), b().longValue()).run();
      }
      return;
      label105:
      Log.warn("Tracker|Can't use Geofencing plugin because A4SGeofencingService service is not declared in your AndroidManifest.xml");
      Log.warn("Tracker|If you want to use Geofencing feature, please add : <service android:name=\"com.ad4screen.sdk.A4SGeofencingService\" android:exported=\"false\"></service> to your AndroidManifest.xml");
    }
    Log.warn("Tracker|Can't use Beacon plugin because A4SBeaconService service is not declared in your AndroidManifest.xml");
    Log.warn("Tracker|If you want to use Beacon feature, please add : <service android:name=\"com.ad4screen.sdk.A4SBeaconService\" android:exported=\"true\"></service> to your AndroidManifest.xml");
  }
  
  public Long b()
  {
    return Long.valueOf(this.a.a());
  }
  
  public Long c()
  {
    return Long.valueOf(this.a.e());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */