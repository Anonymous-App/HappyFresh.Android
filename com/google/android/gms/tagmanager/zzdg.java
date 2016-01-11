package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class zzdg
  extends zzdd
{
  private static final String ID = zzad.zzcB.toString();
  private static final String zzaOC = zzae.zzdi.toString();
  private static final String zzaOD = zzae.zzds.toString();
  private static final String zzaOE = zzae.zzeN.toString();
  private static final String zzaOF = zzae.zzeH.toString();
  private static final String zzaOG = zzae.zzeG.toString();
  private static final String zzaOH = zzae.zzdr.toString();
  private static final String zzaOI = zzae.zzhk.toString();
  private static final String zzaOJ = zzae.zzhn.toString();
  private static final String zzaOK = zzae.zzhp.toString();
  private static final List<String> zzaOL = Arrays.asList(new String[] { "detail", "checkout", "checkout_option", "click", "add", "remove", "purchase", "refund" });
  private static final Pattern zzaOM = Pattern.compile("dimension(\\d+)");
  private static final Pattern zzaON = Pattern.compile("metric(\\d+)");
  private static Map<String, String> zzaOO;
  private static Map<String, String> zzaOP;
  private final DataLayer zzaKz;
  private final Set<String> zzaOQ;
  private final zzdc zzaOR;
  
  public zzdg(Context paramContext, DataLayer paramDataLayer)
  {
    this(paramContext, paramDataLayer, new zzdc(paramContext));
  }
  
  zzdg(Context paramContext, DataLayer paramDataLayer, zzdc paramzzdc)
  {
    super(ID, new String[0]);
    this.zzaKz = paramDataLayer;
    this.zzaOR = paramzzdc;
    this.zzaOQ = new HashSet();
    this.zzaOQ.add("");
    this.zzaOQ.add("0");
    this.zzaOQ.add("false");
  }
  
  private Double zzM(Object paramObject)
  {
    if ((paramObject instanceof String)) {
      try
      {
        paramObject = Double.valueOf((String)paramObject);
        return (Double)paramObject;
      }
      catch (NumberFormatException paramObject)
      {
        throw new RuntimeException("Cannot convert the object to Double: " + ((NumberFormatException)paramObject).getMessage());
      }
    }
    if ((paramObject instanceof Integer)) {
      return Double.valueOf(((Integer)paramObject).doubleValue());
    }
    if ((paramObject instanceof Double)) {
      return (Double)paramObject;
    }
    throw new RuntimeException("Cannot convert the object to Double: " + paramObject.toString());
  }
  
  private Integer zzN(Object paramObject)
  {
    if ((paramObject instanceof String)) {
      try
      {
        paramObject = Integer.valueOf((String)paramObject);
        return (Integer)paramObject;
      }
      catch (NumberFormatException paramObject)
      {
        throw new RuntimeException("Cannot convert the object to Integer: " + ((NumberFormatException)paramObject).getMessage());
      }
    }
    if ((paramObject instanceof Double)) {
      return Integer.valueOf(((Double)paramObject).intValue());
    }
    if ((paramObject instanceof Integer)) {
      return (Integer)paramObject;
    }
    throw new RuntimeException("Cannot convert the object to Integer: " + paramObject.toString());
  }
  
  private Promotion zzO(Map<String, String> paramMap)
  {
    Promotion localPromotion = new Promotion();
    String str = (String)paramMap.get("id");
    if (str != null) {
      localPromotion.setId(String.valueOf(str));
    }
    str = (String)paramMap.get("name");
    if (str != null) {
      localPromotion.setName(String.valueOf(str));
    }
    str = (String)paramMap.get("creative");
    if (str != null) {
      localPromotion.setCreative(String.valueOf(str));
    }
    paramMap = (String)paramMap.get("position");
    if (paramMap != null) {
      localPromotion.setPosition(String.valueOf(paramMap));
    }
    return localPromotion;
  }
  
  private Product zzP(Map<String, Object> paramMap)
  {
    Product localProduct = new Product();
    Object localObject = paramMap.get("id");
    if (localObject != null) {
      localProduct.setId(String.valueOf(localObject));
    }
    localObject = paramMap.get("name");
    if (localObject != null) {
      localProduct.setName(String.valueOf(localObject));
    }
    localObject = paramMap.get("brand");
    if (localObject != null) {
      localProduct.setBrand(String.valueOf(localObject));
    }
    localObject = paramMap.get("category");
    if (localObject != null) {
      localProduct.setCategory(String.valueOf(localObject));
    }
    localObject = paramMap.get("variant");
    if (localObject != null) {
      localProduct.setVariant(String.valueOf(localObject));
    }
    localObject = paramMap.get("coupon");
    if (localObject != null) {
      localProduct.setCouponCode(String.valueOf(localObject));
    }
    localObject = paramMap.get("position");
    if (localObject != null) {
      localProduct.setPosition(zzN(localObject).intValue());
    }
    localObject = paramMap.get("price");
    if (localObject != null) {
      localProduct.setPrice(zzM(localObject).doubleValue());
    }
    localObject = paramMap.get("quantity");
    if (localObject != null) {
      localProduct.setQuantity(zzN(localObject).intValue());
    }
    localObject = paramMap.keySet().iterator();
    while (((Iterator)localObject).hasNext())
    {
      String str = (String)((Iterator)localObject).next();
      Matcher localMatcher1 = zzaOM.matcher(str);
      int i;
      if (localMatcher1.matches())
      {
        try
        {
          i = Integer.parseInt(localMatcher1.group(1));
          localProduct.setCustomDimension(i, String.valueOf(paramMap.get(str)));
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          zzbg.zzaC("illegal number in custom dimension value: " + str);
        }
      }
      else
      {
        Matcher localMatcher2 = zzaON.matcher(str);
        if (localMatcher2.matches()) {
          try
          {
            i = Integer.parseInt(localMatcher2.group(1));
            localProduct.setCustomMetric(i, zzN(paramMap.get(str)).intValue());
          }
          catch (NumberFormatException localNumberFormatException2)
          {
            zzbg.zzaC("illegal number in custom metric value: " + str);
          }
        }
      }
    }
    return localProduct;
  }
  
  private Map<String, String> zzQ(Map<String, zzag.zza> paramMap)
  {
    paramMap = (zzag.zza)paramMap.get(zzaOJ);
    if (paramMap != null) {
      return zzc(paramMap);
    }
    if (zzaOO == null)
    {
      paramMap = new HashMap();
      paramMap.put("transactionId", "&ti");
      paramMap.put("transactionAffiliation", "&ta");
      paramMap.put("transactionTax", "&tt");
      paramMap.put("transactionShipping", "&ts");
      paramMap.put("transactionTotal", "&tr");
      paramMap.put("transactionCurrency", "&cu");
      zzaOO = paramMap;
    }
    return zzaOO;
  }
  
  private Map<String, String> zzR(Map<String, zzag.zza> paramMap)
  {
    paramMap = (zzag.zza)paramMap.get(zzaOK);
    if (paramMap != null) {
      return zzc(paramMap);
    }
    if (zzaOP == null)
    {
      paramMap = new HashMap();
      paramMap.put("name", "&in");
      paramMap.put("sku", "&ic");
      paramMap.put("category", "&iv");
      paramMap.put("price", "&ip");
      paramMap.put("quantity", "&iq");
      paramMap.put("currency", "&cu");
      zzaOP = paramMap;
    }
    return zzaOP;
  }
  
  private void zza(Tracker paramTracker, Map<String, zzag.zza> paramMap)
  {
    String str = zzeO("transactionId");
    if (str == null) {
      zzbg.zzaz("Cannot find transactionId in data layer.");
    }
    for (;;)
    {
      return;
      LinkedList localLinkedList = new LinkedList();
      Object localObject2;
      Object localObject3;
      try
      {
        localObject1 = zzm((zzag.zza)paramMap.get(zzaOH));
        ((Map)localObject1).put("&t", "transaction");
        localObject2 = zzQ(paramMap).entrySet().iterator();
        while (((Iterator)localObject2).hasNext())
        {
          localObject3 = (Map.Entry)((Iterator)localObject2).next();
          zzd((Map)localObject1, (String)((Map.Entry)localObject3).getValue(), zzeO((String)((Map.Entry)localObject3).getKey()));
        }
        localLinkedList.add(localObject1);
      }
      catch (IllegalArgumentException paramTracker)
      {
        zzbg.zzb("Unable to send transaction", paramTracker);
        return;
      }
      Object localObject1 = zzeP("transactionProducts");
      if (localObject1 != null)
      {
        localObject1 = ((List)localObject1).iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (Map)((Iterator)localObject1).next();
          if (((Map)localObject2).get("name") == null)
          {
            zzbg.zzaz("Unable to send transaction item hit due to missing 'name' field.");
            return;
          }
          localObject3 = zzm((zzag.zza)paramMap.get(zzaOH));
          ((Map)localObject3).put("&t", "item");
          ((Map)localObject3).put("&ti", str);
          Iterator localIterator = zzR(paramMap).entrySet().iterator();
          while (localIterator.hasNext())
          {
            Map.Entry localEntry = (Map.Entry)localIterator.next();
            zzd((Map)localObject3, (String)localEntry.getValue(), (String)((Map)localObject2).get(localEntry.getKey()));
          }
          localLinkedList.add(localObject3);
        }
      }
      paramMap = localLinkedList.iterator();
      while (paramMap.hasNext()) {
        paramTracker.send((Map)paramMap.next());
      }
    }
  }
  
  private void zzb(Tracker paramTracker, Map<String, zzag.zza> paramMap)
  {
    HitBuilders.ScreenViewBuilder localScreenViewBuilder = new HitBuilders.ScreenViewBuilder();
    Object localObject1 = zzm((zzag.zza)paramMap.get(zzaOH));
    localScreenViewBuilder.setAll((Map)localObject1);
    if (zzi(paramMap, zzaOF))
    {
      paramMap = this.zzaKz.get("ecommerce");
      if (!(paramMap instanceof Map)) {
        break label731;
      }
      paramMap = (Map)paramMap;
    }
    for (;;)
    {
      if (paramMap != null)
      {
        Object localObject2 = (String)((Map)localObject1).get("&cu");
        localObject1 = localObject2;
        if (localObject2 == null) {
          localObject1 = (String)paramMap.get("currencyCode");
        }
        if (localObject1 != null) {
          localScreenViewBuilder.set("&cu", (String)localObject1);
        }
        localObject1 = paramMap.get("impressions");
        if ((localObject1 instanceof List))
        {
          localObject1 = ((List)localObject1).iterator();
          for (;;)
          {
            if (((Iterator)localObject1).hasNext())
            {
              localObject2 = (Map)((Iterator)localObject1).next();
              try
              {
                localScreenViewBuilder.addImpression(zzP((Map)localObject2), (String)((Map)localObject2).get("list"));
              }
              catch (RuntimeException localRuntimeException1)
              {
                zzbg.zzaz("Failed to extract a product from DataLayer. " + localRuntimeException1.getMessage());
              }
              continue;
              paramMap = zzdf.zzl((zzag.zza)paramMap.get(zzaOG));
              if (!(paramMap instanceof Map)) {
                break label726;
              }
              paramMap = (Map)paramMap;
              break;
            }
          }
        }
        if (paramMap.containsKey("promoClick")) {
          localObject1 = (List)((Map)paramMap.get("promoClick")).get("promotions");
        }
      }
      for (;;)
      {
        if (localObject1 != null)
        {
          localObject1 = ((List)localObject1).iterator();
          for (;;)
          {
            if (((Iterator)localObject1).hasNext())
            {
              Map localMap1 = (Map)((Iterator)localObject1).next();
              try
              {
                localScreenViewBuilder.addPromotion(zzO(localMap1));
              }
              catch (RuntimeException localRuntimeException2)
              {
                zzbg.zzaz("Failed to extract a promotion from DataLayer. " + localRuntimeException2.getMessage());
              }
              continue;
              if (!paramMap.containsKey("promoView")) {
                break label720;
              }
              localObject1 = (List)((Map)paramMap.get("promoView")).get("promotions");
              break;
            }
          }
          if (paramMap.containsKey("promoClick")) {
            localScreenViewBuilder.set("&promoa", "click");
          }
        }
        for (int i = 0;; i = 1)
        {
          if (i == 0) {
            break label667;
          }
          Object localObject3 = zzaOL.iterator();
          do
          {
            if (!((Iterator)localObject3).hasNext()) {
              break;
            }
            localObject1 = (String)((Iterator)localObject3).next();
          } while (!paramMap.containsKey(localObject1));
          paramMap = (Map)paramMap.get(localObject1);
          localObject3 = (List)paramMap.get("products");
          if (localObject3 == null) {
            break;
          }
          localObject3 = ((List)localObject3).iterator();
          while (((Iterator)localObject3).hasNext())
          {
            Map localMap2 = (Map)((Iterator)localObject3).next();
            try
            {
              localScreenViewBuilder.addProduct(zzP(localMap2));
            }
            catch (RuntimeException localRuntimeException3)
            {
              zzbg.zzaz("Failed to extract a product from DataLayer. " + localRuntimeException3.getMessage());
            }
          }
          localScreenViewBuilder.set("&promoa", "view");
        }
        for (;;)
        {
          try
          {
            if (!paramMap.containsKey("actionField")) {
              continue;
            }
            paramMap = zze((String)localObject1, (Map)paramMap.get("actionField"));
            localScreenViewBuilder.setProductAction(paramMap);
          }
          catch (RuntimeException paramMap)
          {
            label667:
            zzbg.zzaz("Failed to extract a product action from DataLayer. " + paramMap.getMessage());
            continue;
          }
          paramTracker.send(localScreenViewBuilder.build());
          return;
          paramMap = new ProductAction((String)localObject1);
        }
        label720:
        localObject1 = null;
      }
      label726:
      paramMap = null;
      continue;
      label731:
      paramMap = null;
    }
  }
  
  private Map<String, String> zzc(zzag.zza paramzza)
  {
    paramzza = zzdf.zzl(paramzza);
    if (!(paramzza instanceof Map)) {
      return null;
    }
    Object localObject = (Map)paramzza;
    paramzza = new LinkedHashMap();
    localObject = ((Map)localObject).entrySet().iterator();
    while (((Iterator)localObject).hasNext())
    {
      Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
      paramzza.put(localEntry.getKey().toString(), localEntry.getValue().toString());
    }
    return paramzza;
  }
  
  private void zzd(Map<String, String> paramMap, String paramString1, String paramString2)
  {
    if (paramString2 != null) {
      paramMap.put(paramString1, paramString2);
    }
  }
  
  private ProductAction zze(String paramString, Map<String, Object> paramMap)
  {
    paramString = new ProductAction(paramString);
    Object localObject = paramMap.get("id");
    if (localObject != null) {
      paramString.setTransactionId(String.valueOf(localObject));
    }
    localObject = paramMap.get("affiliation");
    if (localObject != null) {
      paramString.setTransactionAffiliation(String.valueOf(localObject));
    }
    localObject = paramMap.get("coupon");
    if (localObject != null) {
      paramString.setTransactionCouponCode(String.valueOf(localObject));
    }
    localObject = paramMap.get("list");
    if (localObject != null) {
      paramString.setProductActionList(String.valueOf(localObject));
    }
    localObject = paramMap.get("option");
    if (localObject != null) {
      paramString.setCheckoutOptions(String.valueOf(localObject));
    }
    localObject = paramMap.get("revenue");
    if (localObject != null) {
      paramString.setTransactionRevenue(zzM(localObject).doubleValue());
    }
    localObject = paramMap.get("tax");
    if (localObject != null) {
      paramString.setTransactionTax(zzM(localObject).doubleValue());
    }
    localObject = paramMap.get("shipping");
    if (localObject != null) {
      paramString.setTransactionShipping(zzM(localObject).doubleValue());
    }
    paramMap = paramMap.get("step");
    if (paramMap != null) {
      paramString.setCheckoutStep(zzN(paramMap).intValue());
    }
    return paramString;
  }
  
  private String zzeO(String paramString)
  {
    paramString = this.zzaKz.get(paramString);
    if (paramString == null) {
      return null;
    }
    return paramString.toString();
  }
  
  private List<Map<String, String>> zzeP(String paramString)
  {
    paramString = this.zzaKz.get(paramString);
    if (paramString == null) {
      return null;
    }
    if (!(paramString instanceof List)) {
      throw new IllegalArgumentException("transactionProducts should be of type List.");
    }
    Iterator localIterator = ((List)paramString).iterator();
    while (localIterator.hasNext()) {
      if (!(localIterator.next() instanceof Map)) {
        throw new IllegalArgumentException("Each element of transactionProducts should be of type Map.");
      }
    }
    return (List)paramString;
  }
  
  private boolean zzi(Map<String, zzag.zza> paramMap, String paramString)
  {
    paramMap = (zzag.zza)paramMap.get(paramString);
    if (paramMap == null) {
      return false;
    }
    return zzdf.zzk(paramMap).booleanValue();
  }
  
  private Map<String, String> zzm(zzag.zza paramzza)
  {
    if (paramzza == null) {
      return new HashMap();
    }
    paramzza = zzc(paramzza);
    if (paramzza == null) {
      return new HashMap();
    }
    String str = (String)paramzza.get("&aip");
    if ((str != null) && (this.zzaOQ.contains(str.toLowerCase()))) {
      paramzza.remove("&aip");
    }
    return paramzza;
  }
  
  public void zzG(Map<String, zzag.zza> paramMap)
  {
    Tracker localTracker = this.zzaOR.zzeG("_GTM_DEFAULT_TRACKER_");
    localTracker.enableAdvertisingIdCollection(zzi(paramMap, "collect_adid"));
    if (zzi(paramMap, zzaOE))
    {
      zzb(localTracker, paramMap);
      return;
    }
    if (zzi(paramMap, zzaOD))
    {
      localTracker.send(zzm((zzag.zza)paramMap.get(zzaOH)));
      return;
    }
    if (zzi(paramMap, zzaOI))
    {
      zza(localTracker, paramMap);
      return;
    }
    zzbg.zzaC("Ignoring unknown tag.");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzdg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */