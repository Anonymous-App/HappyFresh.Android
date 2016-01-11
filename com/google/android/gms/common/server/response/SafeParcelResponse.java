package com.google.android.gms.common.server.response;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzkx;
import com.google.android.gms.internal.zzky;
import com.google.android.gms.internal.zzlh;
import com.google.android.gms.internal.zzli;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SafeParcelResponse
  extends FastJsonResponse
  implements SafeParcelable
{
  public static final zze CREATOR = new zze();
  private final String mClassName;
  private final int zzCY;
  private final FieldMappingDictionary zzabO;
  private final Parcel zzabV;
  private final int zzabW;
  private int zzabX;
  private int zzabY;
  
  SafeParcelResponse(int paramInt, Parcel paramParcel, FieldMappingDictionary paramFieldMappingDictionary)
  {
    this.zzCY = paramInt;
    this.zzabV = ((Parcel)zzu.zzu(paramParcel));
    this.zzabW = 2;
    this.zzabO = paramFieldMappingDictionary;
    if (this.zzabO == null) {}
    for (this.mClassName = null;; this.mClassName = this.zzabO.zzoC())
    {
      this.zzabX = 2;
      return;
    }
  }
  
  private SafeParcelResponse(SafeParcelable paramSafeParcelable, FieldMappingDictionary paramFieldMappingDictionary, String paramString)
  {
    this.zzCY = 1;
    this.zzabV = Parcel.obtain();
    paramSafeParcelable.writeToParcel(this.zzabV, 0);
    this.zzabW = 1;
    this.zzabO = ((FieldMappingDictionary)zzu.zzu(paramFieldMappingDictionary));
    this.mClassName = ((String)zzu.zzu(paramString));
    this.zzabX = 2;
  }
  
  private static HashMap<Integer, Map.Entry<String, FastJsonResponse.Field<?, ?>>> zzC(Map<String, FastJsonResponse.Field<?, ?>> paramMap)
  {
    HashMap localHashMap = new HashMap();
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      localHashMap.put(Integer.valueOf(((FastJsonResponse.Field)localEntry.getValue()).zzot()), localEntry);
    }
    return localHashMap;
  }
  
  public static <T extends FastJsonResponse,  extends SafeParcelable> SafeParcelResponse zza(T paramT)
  {
    String str = paramT.getClass().getCanonicalName();
    FieldMappingDictionary localFieldMappingDictionary = zzb(paramT);
    return new SafeParcelResponse((SafeParcelable)paramT, localFieldMappingDictionary, str);
  }
  
  private static void zza(FieldMappingDictionary paramFieldMappingDictionary, FastJsonResponse paramFastJsonResponse)
  {
    Object localObject = paramFastJsonResponse.getClass();
    if (!paramFieldMappingDictionary.zzb((Class)localObject))
    {
      Map localMap = paramFastJsonResponse.zzom();
      paramFieldMappingDictionary.zza((Class)localObject, localMap);
      localObject = localMap.keySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        paramFastJsonResponse = (FastJsonResponse.Field)localMap.get((String)((Iterator)localObject).next());
        Class localClass = paramFastJsonResponse.zzou();
        if (localClass != null) {
          try
          {
            zza(paramFieldMappingDictionary, (FastJsonResponse)localClass.newInstance());
          }
          catch (InstantiationException paramFieldMappingDictionary)
          {
            throw new IllegalStateException("Could not instantiate an object of type " + paramFastJsonResponse.zzou().getCanonicalName(), paramFieldMappingDictionary);
          }
          catch (IllegalAccessException paramFieldMappingDictionary)
          {
            throw new IllegalStateException("Could not access object of type " + paramFastJsonResponse.zzou().getCanonicalName(), paramFieldMappingDictionary);
          }
        }
      }
    }
  }
  
  private void zza(StringBuilder paramStringBuilder, int paramInt, Object paramObject)
  {
    switch (paramInt)
    {
    default: 
      throw new IllegalArgumentException("Unknown type = " + paramInt);
    case 0: 
    case 1: 
    case 2: 
    case 3: 
    case 4: 
    case 5: 
    case 6: 
      paramStringBuilder.append(paramObject);
      return;
    case 7: 
      paramStringBuilder.append("\"").append(zzlh.zzcr(paramObject.toString())).append("\"");
      return;
    case 8: 
      paramStringBuilder.append("\"").append(zzky.zzi((byte[])paramObject)).append("\"");
      return;
    case 9: 
      paramStringBuilder.append("\"").append(zzky.zzj((byte[])paramObject));
      paramStringBuilder.append("\"");
      return;
    case 10: 
      zzli.zza(paramStringBuilder, (HashMap)paramObject);
      return;
    }
    throw new IllegalArgumentException("Method does not accept concrete type.");
  }
  
  private void zza(StringBuilder paramStringBuilder, FastJsonResponse.Field<?, ?> paramField, Parcel paramParcel, int paramInt)
  {
    switch (paramField.zzol())
    {
    default: 
      throw new IllegalArgumentException("Unknown field out type = " + paramField.zzol());
    case 0: 
      zzb(paramStringBuilder, paramField, zza(paramField, Integer.valueOf(zza.zzg(paramParcel, paramInt))));
      return;
    case 1: 
      zzb(paramStringBuilder, paramField, zza(paramField, zza.zzk(paramParcel, paramInt)));
      return;
    case 2: 
      zzb(paramStringBuilder, paramField, zza(paramField, Long.valueOf(zza.zzi(paramParcel, paramInt))));
      return;
    case 3: 
      zzb(paramStringBuilder, paramField, zza(paramField, Float.valueOf(zza.zzl(paramParcel, paramInt))));
      return;
    case 4: 
      zzb(paramStringBuilder, paramField, zza(paramField, Double.valueOf(zza.zzm(paramParcel, paramInt))));
      return;
    case 5: 
      zzb(paramStringBuilder, paramField, zza(paramField, zza.zzn(paramParcel, paramInt)));
      return;
    case 6: 
      zzb(paramStringBuilder, paramField, zza(paramField, Boolean.valueOf(zza.zzc(paramParcel, paramInt))));
      return;
    case 7: 
      zzb(paramStringBuilder, paramField, zza(paramField, zza.zzo(paramParcel, paramInt)));
      return;
    case 8: 
    case 9: 
      zzb(paramStringBuilder, paramField, zza(paramField, zza.zzr(paramParcel, paramInt)));
      return;
    case 10: 
      zzb(paramStringBuilder, paramField, zza(paramField, zzh(zza.zzq(paramParcel, paramInt))));
      return;
    }
    throw new IllegalArgumentException("Method does not accept concrete type.");
  }
  
  private void zza(StringBuilder paramStringBuilder, String paramString, FastJsonResponse.Field<?, ?> paramField, Parcel paramParcel, int paramInt)
  {
    paramStringBuilder.append("\"").append(paramString).append("\":");
    if (paramField.zzow())
    {
      zza(paramStringBuilder, paramField, paramParcel, paramInt);
      return;
    }
    zzb(paramStringBuilder, paramField, paramParcel, paramInt);
  }
  
  private void zza(StringBuilder paramStringBuilder, Map<String, FastJsonResponse.Field<?, ?>> paramMap, Parcel paramParcel)
  {
    paramMap = zzC(paramMap);
    paramStringBuilder.append('{');
    int j = zza.zzab(paramParcel);
    int i = 0;
    while (paramParcel.dataPosition() < j)
    {
      int k = zza.zzaa(paramParcel);
      Map.Entry localEntry = (Map.Entry)paramMap.get(Integer.valueOf(zza.zzbA(k)));
      if (localEntry != null)
      {
        if (i != 0) {
          paramStringBuilder.append(",");
        }
        zza(paramStringBuilder, (String)localEntry.getKey(), (FastJsonResponse.Field)localEntry.getValue(), paramParcel, k);
        i = 1;
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    paramStringBuilder.append('}');
  }
  
  private static FieldMappingDictionary zzb(FastJsonResponse paramFastJsonResponse)
  {
    FieldMappingDictionary localFieldMappingDictionary = new FieldMappingDictionary(paramFastJsonResponse.getClass());
    zza(localFieldMappingDictionary, paramFastJsonResponse);
    localFieldMappingDictionary.zzoA();
    localFieldMappingDictionary.zzoz();
    return localFieldMappingDictionary;
  }
  
  private void zzb(StringBuilder paramStringBuilder, FastJsonResponse.Field<?, ?> paramField, Parcel paramParcel, int paramInt)
  {
    if (paramField.zzor())
    {
      paramStringBuilder.append("[");
      switch (paramField.zzol())
      {
      default: 
        throw new IllegalStateException("Unknown field type out.");
      case 0: 
        zzkx.zza(paramStringBuilder, zza.zzu(paramParcel, paramInt));
      }
      for (;;)
      {
        paramStringBuilder.append("]");
        return;
        zzkx.zza(paramStringBuilder, zza.zzw(paramParcel, paramInt));
        continue;
        zzkx.zza(paramStringBuilder, zza.zzv(paramParcel, paramInt));
        continue;
        zzkx.zza(paramStringBuilder, zza.zzx(paramParcel, paramInt));
        continue;
        zzkx.zza(paramStringBuilder, zza.zzy(paramParcel, paramInt));
        continue;
        zzkx.zza(paramStringBuilder, zza.zzz(paramParcel, paramInt));
        continue;
        zzkx.zza(paramStringBuilder, zza.zzt(paramParcel, paramInt));
        continue;
        zzkx.zza(paramStringBuilder, zza.zzA(paramParcel, paramInt));
        continue;
        throw new UnsupportedOperationException("List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported");
        paramParcel = zza.zzE(paramParcel, paramInt);
        int i = paramParcel.length;
        paramInt = 0;
        while (paramInt < i)
        {
          if (paramInt > 0) {
            paramStringBuilder.append(",");
          }
          paramParcel[paramInt].setDataPosition(0);
          zza(paramStringBuilder, paramField.zzoy(), paramParcel[paramInt]);
          paramInt += 1;
        }
      }
    }
    switch (paramField.zzol())
    {
    default: 
      throw new IllegalStateException("Unknown field type out");
    case 0: 
      paramStringBuilder.append(zza.zzg(paramParcel, paramInt));
      return;
    case 1: 
      paramStringBuilder.append(zza.zzk(paramParcel, paramInt));
      return;
    case 2: 
      paramStringBuilder.append(zza.zzi(paramParcel, paramInt));
      return;
    case 3: 
      paramStringBuilder.append(zza.zzl(paramParcel, paramInt));
      return;
    case 4: 
      paramStringBuilder.append(zza.zzm(paramParcel, paramInt));
      return;
    case 5: 
      paramStringBuilder.append(zza.zzn(paramParcel, paramInt));
      return;
    case 6: 
      paramStringBuilder.append(zza.zzc(paramParcel, paramInt));
      return;
    case 7: 
      paramField = zza.zzo(paramParcel, paramInt);
      paramStringBuilder.append("\"").append(zzlh.zzcr(paramField)).append("\"");
      return;
    case 8: 
      paramField = zza.zzr(paramParcel, paramInt);
      paramStringBuilder.append("\"").append(zzky.zzi(paramField)).append("\"");
      return;
    case 9: 
      paramField = zza.zzr(paramParcel, paramInt);
      paramStringBuilder.append("\"").append(zzky.zzj(paramField));
      paramStringBuilder.append("\"");
      return;
    case 10: 
      paramField = zza.zzq(paramParcel, paramInt);
      paramParcel = paramField.keySet();
      paramParcel.size();
      paramStringBuilder.append("{");
      paramParcel = paramParcel.iterator();
      for (paramInt = 1; paramParcel.hasNext(); paramInt = 0)
      {
        String str = (String)paramParcel.next();
        if (paramInt == 0) {
          paramStringBuilder.append(",");
        }
        paramStringBuilder.append("\"").append(str).append("\"");
        paramStringBuilder.append(":");
        paramStringBuilder.append("\"").append(zzlh.zzcr(paramField.getString(str))).append("\"");
      }
      paramStringBuilder.append("}");
      return;
    }
    paramParcel = zza.zzD(paramParcel, paramInt);
    paramParcel.setDataPosition(0);
    zza(paramStringBuilder, paramField.zzoy(), paramParcel);
  }
  
  private void zzb(StringBuilder paramStringBuilder, FastJsonResponse.Field<?, ?> paramField, Object paramObject)
  {
    if (paramField.zzoq())
    {
      zzb(paramStringBuilder, paramField, (ArrayList)paramObject);
      return;
    }
    zza(paramStringBuilder, paramField.zzok(), paramObject);
  }
  
  private void zzb(StringBuilder paramStringBuilder, FastJsonResponse.Field<?, ?> paramField, ArrayList<?> paramArrayList)
  {
    paramStringBuilder.append("[");
    int j = paramArrayList.size();
    int i = 0;
    while (i < j)
    {
      if (i != 0) {
        paramStringBuilder.append(",");
      }
      zza(paramStringBuilder, paramField.zzok(), paramArrayList.get(i));
      i += 1;
    }
    paramStringBuilder.append("]");
  }
  
  public static HashMap<String, String> zzh(Bundle paramBundle)
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = paramBundle.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localHashMap.put(str, paramBundle.getString(str));
    }
    return localHashMap;
  }
  
  public int describeContents()
  {
    zze localzze = CREATOR;
    return 0;
  }
  
  public int getVersionCode()
  {
    return this.zzCY;
  }
  
  public String toString()
  {
    zzu.zzb(this.zzabO, "Cannot convert to JSON on client side.");
    Parcel localParcel = zzoE();
    localParcel.setDataPosition(0);
    StringBuilder localStringBuilder = new StringBuilder(100);
    zza(localStringBuilder, this.zzabO.zzco(this.mClassName), localParcel);
    return localStringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zze localzze = CREATOR;
    zze.zza(this, paramParcel, paramInt);
  }
  
  protected Object zzck(String paramString)
  {
    throw new UnsupportedOperationException("Converting to JSON does not require this method.");
  }
  
  protected boolean zzcl(String paramString)
  {
    throw new UnsupportedOperationException("Converting to JSON does not require this method.");
  }
  
  public Parcel zzoE()
  {
    switch (this.zzabX)
    {
    }
    for (;;)
    {
      return this.zzabV;
      this.zzabY = zzb.zzac(this.zzabV);
      zzb.zzH(this.zzabV, this.zzabY);
      this.zzabX = 2;
      continue;
      zzb.zzH(this.zzabV, this.zzabY);
      this.zzabX = 2;
    }
  }
  
  FieldMappingDictionary zzoF()
  {
    switch (this.zzabW)
    {
    default: 
      throw new IllegalStateException("Invalid creation type: " + this.zzabW);
    case 0: 
      return null;
    case 1: 
      return this.zzabO;
    }
    return this.zzabO;
  }
  
  public Map<String, FastJsonResponse.Field<?, ?>> zzom()
  {
    if (this.zzabO == null) {
      return null;
    }
    return this.zzabO.zzco(this.mClassName);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/server/response/SafeParcelResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */