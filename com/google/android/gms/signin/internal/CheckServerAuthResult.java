package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CheckServerAuthResult
  implements SafeParcelable
{
  public static final Parcelable.Creator<CheckServerAuthResult> CREATOR = new zzc();
  final int zzCY;
  final boolean zzaJY;
  final List<Scope> zzaJZ;
  
  CheckServerAuthResult(int paramInt, boolean paramBoolean, List<Scope> paramList)
  {
    this.zzCY = paramInt;
    this.zzaJY = paramBoolean;
    this.zzaJZ = paramList;
  }
  
  public CheckServerAuthResult(boolean paramBoolean, Set<Scope> paramSet)
  {
    this(1, paramBoolean, zzf(paramSet));
  }
  
  private static List<Scope> zzf(Set<Scope> paramSet)
  {
    if (paramSet == null) {
      return Collections.emptyList();
    }
    return Collections.unmodifiableList(new ArrayList(paramSet));
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzc.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/signin/internal/CheckServerAuthResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */