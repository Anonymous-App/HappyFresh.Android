package com.google.android.gms.location.places;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzt.zza;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AutocompleteFilter
  implements SafeParcelable
{
  public static final zzc CREATOR = new zzc();
  final int zzCY;
  final boolean zzazr;
  final List<Integer> zzazs;
  private final Set<Integer> zzazt;
  
  AutocompleteFilter(int paramInt, boolean paramBoolean, Collection<Integer> paramCollection)
  {
    this.zzCY = paramInt;
    this.zzazr = paramBoolean;
    if (paramCollection == null) {}
    for (paramCollection = Collections.emptyList();; paramCollection = new ArrayList(paramCollection))
    {
      this.zzazs = paramCollection;
      if (!this.zzazs.isEmpty()) {
        break;
      }
      this.zzazt = Collections.emptySet();
      return;
    }
    this.zzazt = Collections.unmodifiableSet(new HashSet(this.zzazs));
  }
  
  public static AutocompleteFilter create(Collection<Integer> paramCollection)
  {
    return zza(true, paramCollection);
  }
  
  public static AutocompleteFilter zza(boolean paramBoolean, Collection<Integer> paramCollection)
  {
    return new AutocompleteFilter(0, paramBoolean, paramCollection);
  }
  
  public int describeContents()
  {
    zzc localzzc = CREATOR;
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if (!(paramObject instanceof AutocompleteFilter)) {
        return false;
      }
      paramObject = (AutocompleteFilter)paramObject;
    } while ((this.zzazt.equals(((AutocompleteFilter)paramObject).zzazt)) && (this.zzazr == ((AutocompleteFilter)paramObject).zzazr));
    return false;
  }
  
  public Set<Integer> getPlaceTypes()
  {
    return this.zzazt;
  }
  
  public int hashCode()
  {
    return zzt.hashCode(new Object[] { Boolean.valueOf(this.zzazr), this.zzazt });
  }
  
  public String toString()
  {
    zzt.zza localzza = zzt.zzt(this);
    if (!this.zzazr) {
      localzza.zzg("restrictedToPlaces", Boolean.valueOf(this.zzazr));
    }
    localzza.zzg("placeTypes", this.zzazt);
    return localzza.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzc localzzc = CREATOR;
    zzc.zza(this, paramParcel, paramInt);
  }
  
  public boolean zzuE()
  {
    return this.zzazr;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/AutocompleteFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */