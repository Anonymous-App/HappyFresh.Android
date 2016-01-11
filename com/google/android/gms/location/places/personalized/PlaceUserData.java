package com.google.android.gms.location.places.personalized;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzt.zza;
import com.google.android.gms.location.places.personalized.internal.TestDataImpl;
import java.util.List;

public class PlaceUserData
  implements SafeParcelable
{
  public static final zze CREATOR = new zze();
  final int zzCY;
  private final String zzOx;
  private final List<TestDataImpl> zzaBj;
  private final List<PlaceAlias> zzaBk;
  private final List<HereContent> zzaBl;
  private final String zzazK;
  
  PlaceUserData(int paramInt, String paramString1, String paramString2, List<TestDataImpl> paramList, List<PlaceAlias> paramList1, List<HereContent> paramList2)
  {
    this.zzCY = paramInt;
    this.zzOx = paramString1;
    this.zzazK = paramString2;
    this.zzaBj = paramList;
    this.zzaBk = paramList1;
    this.zzaBl = paramList2;
  }
  
  public int describeContents()
  {
    zze localzze = CREATOR;
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if (!(paramObject instanceof PlaceUserData)) {
        return false;
      }
      paramObject = (PlaceUserData)paramObject;
    } while ((this.zzOx.equals(((PlaceUserData)paramObject).zzOx)) && (this.zzazK.equals(((PlaceUserData)paramObject).zzazK)) && (this.zzaBj.equals(((PlaceUserData)paramObject).zzaBj)) && (this.zzaBk.equals(((PlaceUserData)paramObject).zzaBk)) && (this.zzaBl.equals(((PlaceUserData)paramObject).zzaBl)));
    return false;
  }
  
  public String getPlaceId()
  {
    return this.zzazK;
  }
  
  public int hashCode()
  {
    return zzt.hashCode(new Object[] { this.zzOx, this.zzazK, this.zzaBj, this.zzaBk, this.zzaBl });
  }
  
  public String toString()
  {
    return zzt.zzt(this).zzg("accountName", this.zzOx).zzg("placeId", this.zzazK).zzg("testDataImpls", this.zzaBj).zzg("placeAliases", this.zzaBk).zzg("hereContents", this.zzaBl).toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zze localzze = CREATOR;
    zze.zza(this, paramParcel, paramInt);
  }
  
  public String zzvb()
  {
    return this.zzOx;
  }
  
  public List<PlaceAlias> zzvc()
  {
    return this.zzaBk;
  }
  
  public List<HereContent> zzvd()
  {
    return this.zzaBl;
  }
  
  public List<TestDataImpl> zzve()
  {
    return this.zzaBj;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/personalized/PlaceUserData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */