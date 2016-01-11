package com.google.android.gms.location.places;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class UserDataType
  implements SafeParcelable
{
  public static final zzn CREATOR = new zzn();
  public static final UserDataType zzaAa;
  public static final UserDataType zzaAb;
  public static final Set<UserDataType> zzaAc;
  public static final UserDataType zzazZ = zzy("test_type", 1);
  final int zzCY;
  final String zzEl;
  final int zzaAd;
  
  static
  {
    zzaAa = zzy("labeled_place", 6);
    zzaAb = zzy("here_content", 7);
    zzaAc = Collections.unmodifiableSet(new HashSet(Arrays.asList(new UserDataType[] { zzazZ, zzaAa, zzaAb })));
  }
  
  UserDataType(int paramInt1, String paramString, int paramInt2)
  {
    zzu.zzcj(paramString);
    this.zzCY = paramInt1;
    this.zzEl = paramString;
    this.zzaAd = paramInt2;
  }
  
  private static UserDataType zzy(String paramString, int paramInt)
  {
    return new UserDataType(0, paramString, paramInt);
  }
  
  public int describeContents()
  {
    zzn localzzn = CREATOR;
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if (!(paramObject instanceof UserDataType)) {
        return false;
      }
      paramObject = (UserDataType)paramObject;
    } while ((this.zzEl.equals(((UserDataType)paramObject).zzEl)) && (this.zzaAd == ((UserDataType)paramObject).zzaAd));
    return false;
  }
  
  public int hashCode()
  {
    return this.zzEl.hashCode();
  }
  
  public String toString()
  {
    return this.zzEl;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzn localzzn = CREATOR;
    zzn.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/UserDataType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */