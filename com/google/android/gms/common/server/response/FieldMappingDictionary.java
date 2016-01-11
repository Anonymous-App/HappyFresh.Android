package com.google.android.gms.common.server.response;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class FieldMappingDictionary
  implements SafeParcelable
{
  public static final zzc CREATOR = new zzc();
  private final int zzCY;
  private final HashMap<String, Map<String, FastJsonResponse.Field<?, ?>>> zzabQ;
  private final ArrayList<Entry> zzabR;
  private final String zzabS;
  
  FieldMappingDictionary(int paramInt, ArrayList<Entry> paramArrayList, String paramString)
  {
    this.zzCY = paramInt;
    this.zzabR = null;
    this.zzabQ = zzc(paramArrayList);
    this.zzabS = ((String)zzu.zzu(paramString));
    zzoz();
  }
  
  public FieldMappingDictionary(Class<? extends FastJsonResponse> paramClass)
  {
    this.zzCY = 1;
    this.zzabR = null;
    this.zzabQ = new HashMap();
    this.zzabS = paramClass.getCanonicalName();
  }
  
  private static HashMap<String, Map<String, FastJsonResponse.Field<?, ?>>> zzc(ArrayList<Entry> paramArrayList)
  {
    HashMap localHashMap = new HashMap();
    int j = paramArrayList.size();
    int i = 0;
    while (i < j)
    {
      Entry localEntry = (Entry)paramArrayList.get(i);
      localHashMap.put(localEntry.className, localEntry.zzoD());
      i += 1;
    }
    return localHashMap;
  }
  
  public int describeContents()
  {
    zzc localzzc = CREATOR;
    return 0;
  }
  
  int getVersionCode()
  {
    return this.zzCY;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator1 = this.zzabQ.keySet().iterator();
    while (localIterator1.hasNext())
    {
      Object localObject = (String)localIterator1.next();
      localStringBuilder.append((String)localObject).append(":\n");
      localObject = (Map)this.zzabQ.get(localObject);
      Iterator localIterator2 = ((Map)localObject).keySet().iterator();
      while (localIterator2.hasNext())
      {
        String str = (String)localIterator2.next();
        localStringBuilder.append("  ").append(str).append(": ");
        localStringBuilder.append(((Map)localObject).get(str));
      }
    }
    return localStringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzc localzzc = CREATOR;
    zzc.zza(this, paramParcel, paramInt);
  }
  
  public void zza(Class<? extends FastJsonResponse> paramClass, Map<String, FastJsonResponse.Field<?, ?>> paramMap)
  {
    this.zzabQ.put(paramClass.getCanonicalName(), paramMap);
  }
  
  public boolean zzb(Class<? extends FastJsonResponse> paramClass)
  {
    return this.zzabQ.containsKey(paramClass.getCanonicalName());
  }
  
  public Map<String, FastJsonResponse.Field<?, ?>> zzco(String paramString)
  {
    return (Map)this.zzabQ.get(paramString);
  }
  
  public void zzoA()
  {
    Iterator localIterator1 = this.zzabQ.keySet().iterator();
    while (localIterator1.hasNext())
    {
      String str1 = (String)localIterator1.next();
      Map localMap = (Map)this.zzabQ.get(str1);
      HashMap localHashMap = new HashMap();
      Iterator localIterator2 = localMap.keySet().iterator();
      while (localIterator2.hasNext())
      {
        String str2 = (String)localIterator2.next();
        localHashMap.put(str2, ((FastJsonResponse.Field)localMap.get(str2)).zzop());
      }
      this.zzabQ.put(str1, localHashMap);
    }
  }
  
  ArrayList<Entry> zzoB()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.zzabQ.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localArrayList.add(new Entry(str, (Map)this.zzabQ.get(str)));
    }
    return localArrayList;
  }
  
  public String zzoC()
  {
    return this.zzabS;
  }
  
  public void zzoz()
  {
    Iterator localIterator1 = this.zzabQ.keySet().iterator();
    while (localIterator1.hasNext())
    {
      Object localObject = (String)localIterator1.next();
      localObject = (Map)this.zzabQ.get(localObject);
      Iterator localIterator2 = ((Map)localObject).keySet().iterator();
      while (localIterator2.hasNext()) {
        ((FastJsonResponse.Field)((Map)localObject).get((String)localIterator2.next())).zza(this);
      }
    }
  }
  
  public static class Entry
    implements SafeParcelable
  {
    public static final zzd CREATOR = new zzd();
    final String className;
    final int versionCode;
    final ArrayList<FieldMappingDictionary.FieldMapPair> zzabT;
    
    Entry(int paramInt, String paramString, ArrayList<FieldMappingDictionary.FieldMapPair> paramArrayList)
    {
      this.versionCode = paramInt;
      this.className = paramString;
      this.zzabT = paramArrayList;
    }
    
    Entry(String paramString, Map<String, FastJsonResponse.Field<?, ?>> paramMap)
    {
      this.versionCode = 1;
      this.className = paramString;
      this.zzabT = zzB(paramMap);
    }
    
    private static ArrayList<FieldMappingDictionary.FieldMapPair> zzB(Map<String, FastJsonResponse.Field<?, ?>> paramMap)
    {
      if (paramMap == null) {
        return null;
      }
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator = paramMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        localArrayList.add(new FieldMappingDictionary.FieldMapPair(str, (FastJsonResponse.Field)paramMap.get(str)));
      }
      return localArrayList;
    }
    
    public int describeContents()
    {
      zzd localzzd = CREATOR;
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      zzd localzzd = CREATOR;
      zzd.zza(this, paramParcel, paramInt);
    }
    
    HashMap<String, FastJsonResponse.Field<?, ?>> zzoD()
    {
      HashMap localHashMap = new HashMap();
      int j = this.zzabT.size();
      int i = 0;
      while (i < j)
      {
        FieldMappingDictionary.FieldMapPair localFieldMapPair = (FieldMappingDictionary.FieldMapPair)this.zzabT.get(i);
        localHashMap.put(localFieldMapPair.zzaC, localFieldMapPair.zzabU);
        i += 1;
      }
      return localHashMap;
    }
  }
  
  public static class FieldMapPair
    implements SafeParcelable
  {
    public static final zzb CREATOR = new zzb();
    final int versionCode;
    final String zzaC;
    final FastJsonResponse.Field<?, ?> zzabU;
    
    FieldMapPair(int paramInt, String paramString, FastJsonResponse.Field<?, ?> paramField)
    {
      this.versionCode = paramInt;
      this.zzaC = paramString;
      this.zzabU = paramField;
    }
    
    FieldMapPair(String paramString, FastJsonResponse.Field<?, ?> paramField)
    {
      this.versionCode = 1;
      this.zzaC = paramString;
      this.zzabU = paramField;
    }
    
    public int describeContents()
    {
      zzb localzzb = CREATOR;
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      zzb localzzb = CREATOR;
      zzb.zza(this, paramParcel, paramInt);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/server/response/FieldMappingDictionary.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */