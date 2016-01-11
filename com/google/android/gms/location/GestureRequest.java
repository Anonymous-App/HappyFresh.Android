package com.google.android.gms.location;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class GestureRequest
  implements SafeParcelable
{
  public static final zzb CREATOR = new zzb();
  private static final List<Integer> zzaxL = Collections.unmodifiableList(Arrays.asList(new Integer[] { Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(12), Integer.valueOf(13), Integer.valueOf(14), Integer.valueOf(15), Integer.valueOf(16), Integer.valueOf(17), Integer.valueOf(18), Integer.valueOf(19) }));
  private static final List<Integer> zzaxM = Collections.unmodifiableList(Arrays.asList(new Integer[] { Integer.valueOf(1) }));
  private static final List<Integer> zzaxN = Collections.unmodifiableList(Arrays.asList(new Integer[] { Integer.valueOf(2), Integer.valueOf(4), Integer.valueOf(6), Integer.valueOf(8), Integer.valueOf(10), Integer.valueOf(12), Integer.valueOf(14), Integer.valueOf(16), Integer.valueOf(18), Integer.valueOf(19) }));
  private static final List<Integer> zzaxO = Collections.unmodifiableList(Arrays.asList(new Integer[] { Integer.valueOf(3), Integer.valueOf(5), Integer.valueOf(7), Integer.valueOf(9), Integer.valueOf(11), Integer.valueOf(13), Integer.valueOf(15), Integer.valueOf(17) }));
  private final int zzCY;
  private final List<Integer> zzaxP;
  
  GestureRequest(int paramInt, List<Integer> paramList)
  {
    this.zzCY = paramInt;
    this.zzaxP = paramList;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public int getVersionCode()
  {
    return this.zzCY;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzb.zza(this, paramParcel, paramInt);
  }
  
  public List<Integer> zzuo()
  {
    return this.zzaxP;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/GestureRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */