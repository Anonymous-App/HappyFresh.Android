package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class ConnectionEvent
  implements SafeParcelable
{
  public static final Parcelable.Creator<ConnectionEvent> CREATOR = new zza();
  final int zzCY;
  private final long zzabZ;
  private int zzaca;
  private final String zzacb;
  private final String zzacc;
  private final String zzacd;
  private final String zzace;
  private final String zzacf;
  private final String zzacg;
  private final long zzach;
  private final long zzaci;
  private long zzacj;
  
  ConnectionEvent(int paramInt1, long paramLong1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, long paramLong2, long paramLong3)
  {
    this.zzCY = paramInt1;
    this.zzabZ = paramLong1;
    this.zzaca = paramInt2;
    this.zzacb = paramString1;
    this.zzacc = paramString2;
    this.zzacd = paramString3;
    this.zzace = paramString4;
    this.zzacj = -1L;
    this.zzacf = paramString5;
    this.zzacg = paramString6;
    this.zzach = paramLong2;
    this.zzaci = paramLong3;
  }
  
  public ConnectionEvent(long paramLong1, int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, long paramLong2, long paramLong3)
  {
    this(1, paramLong1, paramInt, paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramLong2, paramLong3);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public int getEventType()
  {
    return this.zzaca;
  }
  
  public long getTimeMillis()
  {
    return this.zzabZ;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza(this, paramParcel, paramInt);
  }
  
  public String zzoG()
  {
    return this.zzacb;
  }
  
  public String zzoH()
  {
    return this.zzacc;
  }
  
  public String zzoI()
  {
    return this.zzacd;
  }
  
  public String zzoJ()
  {
    return this.zzace;
  }
  
  public String zzoK()
  {
    return this.zzacf;
  }
  
  public String zzoL()
  {
    return this.zzacg;
  }
  
  public long zzoM()
  {
    return this.zzaci;
  }
  
  public long zzoN()
  {
    return this.zzach;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/stats/ConnectionEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */