package com.google.android.gms.location.places;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzt.zza;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class NearbyAlertFilter
  extends zza
  implements SafeParcelable
{
  public static final zzd CREATOR = new zzd();
  final int zzCY;
  final List<Integer> zzazs;
  private final Set<Integer> zzazt;
  final List<String> zzazu;
  final List<UserDataType> zzazv;
  private final Set<String> zzazw;
  private final Set<UserDataType> zzazx;
  
  NearbyAlertFilter(int paramInt, List<String> paramList, List<Integer> paramList1, List<UserDataType> paramList2)
  {
    this.zzCY = paramInt;
    if (paramList1 == null)
    {
      paramList1 = Collections.emptyList();
      this.zzazs = paramList1;
      if (paramList2 != null) {
        break label91;
      }
      paramList1 = Collections.emptyList();
      label31:
      this.zzazv = paramList1;
      if (paramList != null) {
        break label100;
      }
    }
    label91:
    label100:
    for (paramList = Collections.emptyList();; paramList = Collections.unmodifiableList(paramList))
    {
      this.zzazu = paramList;
      this.zzazt = zzl(this.zzazs);
      this.zzazx = zzl(this.zzazv);
      this.zzazw = zzl(this.zzazu);
      return;
      paramList1 = Collections.unmodifiableList(paramList1);
      break;
      paramList1 = Collections.unmodifiableList(paramList2);
      break label31;
    }
  }
  
  public static NearbyAlertFilter zza(Collection<String> paramCollection, Collection<Integer> paramCollection1, Collection<UserDataType> paramCollection2)
  {
    if (((paramCollection == null) || (paramCollection.isEmpty())) && ((paramCollection1 == null) || (paramCollection1.isEmpty())) && ((paramCollection2 == null) || (paramCollection2.isEmpty()))) {
      throw new IllegalArgumentException("NearbyAlertFilters must contain at least onePlaceId, PlaceType, or UserDataType to match results with.");
    }
    return new NearbyAlertFilter(0, zzc(paramCollection), zzc(paramCollection1), zzc(paramCollection2));
  }
  
  public int describeContents()
  {
    zzd localzzd = CREATOR;
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if (!(paramObject instanceof NearbyAlertFilter)) {
        return false;
      }
      paramObject = (NearbyAlertFilter)paramObject;
    } while ((this.zzazt.equals(((NearbyAlertFilter)paramObject).zzazt)) && (this.zzazx.equals(((NearbyAlertFilter)paramObject).zzazx)) && (this.zzazw.equals(((NearbyAlertFilter)paramObject).zzazw)));
    return false;
  }
  
  public Set<String> getPlaceIds()
  {
    return this.zzazw;
  }
  
  public int hashCode()
  {
    return zzt.hashCode(new Object[] { this.zzazt, this.zzazx, this.zzazw });
  }
  
  public String toString()
  {
    zzt.zza localzza = zzt.zzt(this);
    if (!this.zzazt.isEmpty()) {
      localzza.zzg("types", this.zzazt);
    }
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
    zzd localzzd = CREATOR;
    zzd.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/NearbyAlertFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */