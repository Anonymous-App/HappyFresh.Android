package com.ad4screen.sdk.common.persistence;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.FrameLayout.LayoutParams;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.persistence.adapters.f;
import com.ad4screen.sdk.common.persistence.adapters.g;
import com.ad4screen.sdk.common.persistence.adapters.h;
import com.ad4screen.sdk.common.persistence.adapters.i;
import com.ad4screen.sdk.common.persistence.adapters.j;
import com.ad4screen.sdk.common.persistence.adapters.k;
import com.ad4screen.sdk.common.persistence.adapters.l;
import com.ad4screen.sdk.common.persistence.adapters.m;
import com.ad4screen.sdk.common.persistence.adapters.n;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class e
{
  private final HashMap<Class<?>, com.ad4screen.sdk.common.persistence.adapters.model.b<?>> a = new HashMap();
  private final HashMap<String, com.ad4screen.sdk.common.persistence.adapters.model.a<?>> b = new HashMap();
  
  public e()
  {
    this.a.put(Intent.class, new j());
    this.a.put(Bundle.class, new com.ad4screen.sdk.common.persistence.adapters.d());
    this.a.put(HashMap.class, new h());
    this.a.put(ConcurrentHashMap.class, new f());
    this.a.put(Location.class, new n());
    this.a.put(FrameLayout.LayoutParams.class, new l());
    this.a.put(ArrayList.class, new com.ad4screen.sdk.common.persistence.adapters.b());
    i locali = new i();
    com.ad4screen.sdk.common.persistence.adapters.c localc = new com.ad4screen.sdk.common.persistence.adapters.c();
    g localg = new g();
    com.ad4screen.sdk.common.persistence.adapters.e locale = new com.ad4screen.sdk.common.persistence.adapters.e();
    m localm = new m();
    k localk = new k();
    com.ad4screen.sdk.common.persistence.adapters.a locala = new com.ad4screen.sdk.common.persistence.adapters.a();
    this.b.put(locali.a(), locali);
    this.b.put(localc.a(), localc);
    this.b.put(localg.a(), localg);
    this.b.put(locale.a(), locale);
    this.b.put(localm.a(), localm);
    this.b.put(localk.a(), localk);
    this.b.put(locala.a(), locala);
  }
  
  public <T> T a(String paramString, T paramT)
    throws JSONException
  {
    if (paramT == null) {
      Log.error("SerializerManager|fromJSON Default object can't be null, aborting deserialization");
    }
    Object localObject;
    do
    {
      return paramT;
      if ((paramT instanceof c)) {
        return (T)((c)paramT).fromJSON(paramString);
      }
      localObject = new JSONObject(paramString).getString("type");
      localObject = (com.ad4screen.sdk.common.persistence.adapters.model.a)this.b.get(localObject);
    } while (localObject == null);
    return (T)((com.ad4screen.sdk.common.persistence.adapters.model.a)localObject).b(paramString);
  }
  
  public <T> JSONObject a(T paramT)
    throws JSONException
  {
    if (paramT == null) {
      return null;
    }
    com.ad4screen.sdk.common.persistence.adapters.model.b localb = (com.ad4screen.sdk.common.persistence.adapters.model.b)this.a.get(paramT.getClass());
    if (localb != null) {
      return localb.a(paramT);
    }
    if ((paramT instanceof d)) {
      return ((d)paramT).toJSON();
    }
    return null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/persistence/e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */