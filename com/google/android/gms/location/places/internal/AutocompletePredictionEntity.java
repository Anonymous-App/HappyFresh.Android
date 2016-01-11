package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzt.zza;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePrediction.Substring;
import java.util.List;

public class AutocompletePredictionEntity
  implements SafeParcelable, AutocompletePrediction
{
  public static final Parcelable.Creator<AutocompletePredictionEntity> CREATOR = new zza();
  final int zzCY;
  final List<SubstringEntity> zzaAe;
  final int zzaAf;
  final String zzakM;
  final String zzazK;
  final List<Integer> zzazo;
  
  AutocompletePredictionEntity(int paramInt1, String paramString1, String paramString2, List<Integer> paramList, List<SubstringEntity> paramList1, int paramInt2)
  {
    this.zzCY = paramInt1;
    this.zzakM = paramString1;
    this.zzazK = paramString2;
    this.zzazo = paramList;
    this.zzaAe = paramList1;
    this.zzaAf = paramInt2;
  }
  
  public static AutocompletePredictionEntity zza(String paramString1, String paramString2, List<Integer> paramList, List<SubstringEntity> paramList1, int paramInt)
  {
    return new AutocompletePredictionEntity(0, (String)com.google.android.gms.common.internal.zzu.zzu(paramString1), paramString2, paramList, paramList1, paramInt);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if (!(paramObject instanceof AutocompletePredictionEntity)) {
        return false;
      }
      paramObject = (AutocompletePredictionEntity)paramObject;
    } while ((zzt.equal(this.zzakM, ((AutocompletePredictionEntity)paramObject).zzakM)) && (zzt.equal(this.zzazK, ((AutocompletePredictionEntity)paramObject).zzazK)) && (zzt.equal(this.zzazo, ((AutocompletePredictionEntity)paramObject).zzazo)) && (zzt.equal(this.zzaAe, ((AutocompletePredictionEntity)paramObject).zzaAe)) && (zzt.equal(Integer.valueOf(this.zzaAf), Integer.valueOf(((AutocompletePredictionEntity)paramObject).zzaAf))));
    return false;
  }
  
  public String getDescription()
  {
    return this.zzakM;
  }
  
  public List<? extends AutocompletePrediction.Substring> getMatchedSubstrings()
  {
    return this.zzaAe;
  }
  
  public String getPlaceId()
  {
    return this.zzazK;
  }
  
  public List<Integer> getPlaceTypes()
  {
    return this.zzazo;
  }
  
  public int hashCode()
  {
    return zzt.hashCode(new Object[] { this.zzakM, this.zzazK, this.zzazo, this.zzaAe, Integer.valueOf(this.zzaAf) });
  }
  
  public boolean isDataValid()
  {
    return true;
  }
  
  public String toString()
  {
    return zzt.zzt(this).zzg("description", this.zzakM).zzg("placeId", this.zzazK).zzg("placeTypes", this.zzazo).zzg("substrings", this.zzaAe).toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza(this, paramParcel, paramInt);
  }
  
  public AutocompletePrediction zzuL()
  {
    return this;
  }
  
  public static class SubstringEntity
    implements SafeParcelable, AutocompletePrediction.Substring
  {
    public static final Parcelable.Creator<SubstringEntity> CREATOR = new zzu();
    final int mLength;
    final int mOffset;
    final int zzCY;
    
    public SubstringEntity(int paramInt1, int paramInt2, int paramInt3)
    {
      this.zzCY = paramInt1;
      this.mOffset = paramInt2;
      this.mLength = paramInt3;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {}
      do
      {
        return true;
        if (!(paramObject instanceof SubstringEntity)) {
          return false;
        }
        paramObject = (SubstringEntity)paramObject;
      } while ((zzt.equal(Integer.valueOf(this.mOffset), Integer.valueOf(((SubstringEntity)paramObject).mOffset))) && (zzt.equal(Integer.valueOf(this.mLength), Integer.valueOf(((SubstringEntity)paramObject).mLength))));
      return false;
    }
    
    public int getLength()
    {
      return this.mLength;
    }
    
    public int getOffset()
    {
      return this.mOffset;
    }
    
    public int hashCode()
    {
      return zzt.hashCode(new Object[] { Integer.valueOf(this.mOffset), Integer.valueOf(this.mLength) });
    }
    
    public String toString()
    {
      return zzt.zzt(this).zzg("offset", Integer.valueOf(this.mOffset)).zzg("length", Integer.valueOf(this.mLength)).toString();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      zzu.zza(this, paramParcel, paramInt);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/internal/AutocompletePredictionEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */