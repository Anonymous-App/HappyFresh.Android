package com.google.android.gms.internal;

import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class zzoa
  extends zznq<zzoa>
{
  private ProductAction zzIC;
  private final Map<String, List<Product>> zzID = new HashMap();
  private final List<Promotion> zzIE = new ArrayList();
  private final List<Product> zzIF = new ArrayList();
  
  public String toString()
  {
    HashMap localHashMap = new HashMap();
    if (!this.zzIF.isEmpty()) {
      localHashMap.put("products", this.zzIF);
    }
    if (!this.zzIE.isEmpty()) {
      localHashMap.put("promotions", this.zzIE);
    }
    if (!this.zzID.isEmpty()) {
      localHashMap.put("impressions", this.zzID);
    }
    localHashMap.put("productAction", this.zzIC);
    return zzy(localHashMap);
  }
  
  public void zza(Product paramProduct, String paramString)
  {
    if (paramProduct == null) {
      return;
    }
    String str = paramString;
    if (paramString == null) {
      str = "";
    }
    if (!this.zzID.containsKey(str)) {
      this.zzID.put(str, new ArrayList());
    }
    ((List)this.zzID.get(str)).add(paramProduct);
  }
  
  public void zza(zzoa paramzzoa)
  {
    paramzzoa.zzIF.addAll(this.zzIF);
    paramzzoa.zzIE.addAll(this.zzIE);
    Iterator localIterator = this.zzID.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Object localObject = (Map.Entry)localIterator.next();
      String str = (String)((Map.Entry)localObject).getKey();
      localObject = ((List)((Map.Entry)localObject).getValue()).iterator();
      while (((Iterator)localObject).hasNext()) {
        paramzzoa.zza((Product)((Iterator)localObject).next(), str);
      }
    }
    if (this.zzIC != null) {
      paramzzoa.zzIC = this.zzIC;
    }
  }
  
  public ProductAction zzwu()
  {
    return this.zzIC;
  }
  
  public List<Product> zzwv()
  {
    return Collections.unmodifiableList(this.zzIF);
  }
  
  public Map<String, List<Product>> zzww()
  {
    return this.zzID;
  }
  
  public List<Promotion> zzwx()
  {
    return Collections.unmodifiableList(this.zzIE);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzoa.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */