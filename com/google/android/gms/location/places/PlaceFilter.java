package com.google.android.gms.location.places;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzt.zza;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class PlaceFilter
  extends zza
  implements SafeParcelable
{
  public static final zzf CREATOR = new zzf();
  final int zzCY;
  final boolean zzazC;
  final List<Integer> zzazs;
  private final Set<Integer> zzazt;
  final List<String> zzazu;
  final List<UserDataType> zzazv;
  private final Set<String> zzazw;
  private final Set<UserDataType> zzazx;
  
  public PlaceFilter()
  {
    this(false, null);
  }
  
  PlaceFilter(int paramInt, List<Integer> paramList, boolean paramBoolean, List<String> paramList1, List<UserDataType> paramList2)
  {
    this.zzCY = paramInt;
    if (paramList == null)
    {
      paramList = Collections.emptyList();
      this.zzazs = paramList;
      this.zzazC = paramBoolean;
      if (paramList2 != null) {
        break label97;
      }
      paramList = Collections.emptyList();
      label36:
      this.zzazv = paramList;
      if (paramList1 != null) {
        break label106;
      }
    }
    label97:
    label106:
    for (paramList = Collections.emptyList();; paramList = Collections.unmodifiableList(paramList1))
    {
      this.zzazu = paramList;
      this.zzazt = zzl(this.zzazs);
      this.zzazx = zzl(this.zzazv);
      this.zzazw = zzl(this.zzazu);
      return;
      paramList = Collections.unmodifiableList(paramList);
      break;
      paramList = Collections.unmodifiableList(paramList2);
      break label36;
    }
  }
  
  public PlaceFilter(Collection<Integer> paramCollection, boolean paramBoolean, Collection<String> paramCollection1, Collection<UserDataType> paramCollection2)
  {
    this(0, zzc(paramCollection), paramBoolean, zzc(paramCollection1), zzc(paramCollection2));
  }
  
  public PlaceFilter(boolean paramBoolean, Collection<String> paramCollection)
  {
    this(null, paramBoolean, paramCollection, null);
  }
  
  @Deprecated
  public static PlaceFilter zzuJ()
  {
    return new zza(null).zzuK();
  }
  
  public int describeContents()
  {
    zzf localzzf = CREATOR;
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if (!(paramObject instanceof PlaceFilter)) {
        return false;
      }
      paramObject = (PlaceFilter)paramObject;
    } while ((this.zzazt.equals(((PlaceFilter)paramObject).zzazt)) && (this.zzazC == ((PlaceFilter)paramObject).zzazC) && (this.zzazx.equals(((PlaceFilter)paramObject).zzazx)) && (this.zzazw.equals(((PlaceFilter)paramObject).zzazw)));
    return false;
  }
  
  public Set<String> getPlaceIds()
  {
    return this.zzazw;
  }
  
  public Set<Integer> getPlaceTypes()
  {
    return this.zzazt;
  }
  
  public int hashCode()
  {
    return zzt.hashCode(new Object[] { this.zzazt, Boolean.valueOf(this.zzazC), this.zzazx, this.zzazw });
  }
  
  public boolean isRestrictedToPlacesOpenNow()
  {
    return this.zzazC;
  }
  
  public String toString()
  {
    zzt.zza localzza = zzt.zzt(this);
    if (!this.zzazt.isEmpty()) {
      localzza.zzg("types", this.zzazt);
    }
    localzza.zzg("requireOpenNow", Boolean.valueOf(this.zzazC));
    if (!this.zzazw.isEmpty()) {
      localzza.zzg("placeIds", this.zzazw);
    }
    if (!this.zzazx.isEmpty()) {
      localzza.zzg("requestedUserDataTypes", this.zzazx);
    }
    return localzza.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzf localzzf = CREATOR;
    zzf.zza(this, paramParcel, paramInt);
  }
  
  public Set<UserDataType> zzuI()
  {
    return this.zzazx;
  }
  
  @Deprecated
  public static final class zza
  {
    private boolean zzazC = false;
    private Collection<Integer> zzazD = null;
    private Collection<UserDataType> zzazE = null;
    private String[] zzazF = null;
    
    public PlaceFilter zzuK()
    {
      List localList = null;
      ArrayList localArrayList1;
      if (this.zzazD != null)
      {
        localArrayList1 = new ArrayList(this.zzazD);
        if (this.zzazE == null) {
          break label75;
        }
      }
      label75:
      for (ArrayList localArrayList2 = new ArrayList(this.zzazE);; localArrayList2 = null)
      {
        if (this.zzazF != null) {
          localList = Arrays.asList(this.zzazF);
        }
        return new PlaceFilter(localArrayList1, this.zzazC, localList, localArrayList2);
        localArrayList1 = null;
        break;
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/PlaceFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */