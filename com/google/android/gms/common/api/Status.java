package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender.SendIntentException;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzt.zza;

public final class Status
  implements Result, SafeParcelable
{
  public static final Parcelable.Creator<Status> CREATOR = new zzk();
  public static final Status zzXP = new Status(0);
  public static final Status zzXQ = new Status(14);
  public static final Status zzXR = new Status(8);
  public static final Status zzXS = new Status(15);
  public static final Status zzXT = new Status(16);
  private final PendingIntent mPendingIntent;
  private final int zzCY;
  private final int zzTS;
  private final String zzXU;
  
  public Status(int paramInt)
  {
    this(paramInt, null);
  }
  
  Status(int paramInt1, int paramInt2, String paramString, PendingIntent paramPendingIntent)
  {
    this.zzCY = paramInt1;
    this.zzTS = paramInt2;
    this.zzXU = paramString;
    this.mPendingIntent = paramPendingIntent;
  }
  
  public Status(int paramInt, String paramString)
  {
    this(1, paramInt, paramString, null);
  }
  
  public Status(int paramInt, String paramString, PendingIntent paramPendingIntent)
  {
    this(1, paramInt, paramString, paramPendingIntent);
  }
  
  private String zzmU()
  {
    if (this.zzXU != null) {
      return this.zzXU;
    }
    return CommonStatusCodes.getStatusCodeString(this.zzTS);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof Status)) {}
    do
    {
      return false;
      paramObject = (Status)paramObject;
    } while ((this.zzCY != ((Status)paramObject).zzCY) || (this.zzTS != ((Status)paramObject).zzTS) || (!zzt.equal(this.zzXU, ((Status)paramObject).zzXU)) || (!zzt.equal(this.mPendingIntent, ((Status)paramObject).mPendingIntent)));
    return true;
  }
  
  public PendingIntent getResolution()
  {
    return this.mPendingIntent;
  }
  
  public Status getStatus()
  {
    return this;
  }
  
  public int getStatusCode()
  {
    return this.zzTS;
  }
  
  public String getStatusMessage()
  {
    return this.zzXU;
  }
  
  int getVersionCode()
  {
    return this.zzCY;
  }
  
  public boolean hasResolution()
  {
    return this.mPendingIntent != null;
  }
  
  public int hashCode()
  {
    return zzt.hashCode(new Object[] { Integer.valueOf(this.zzCY), Integer.valueOf(this.zzTS), this.zzXU, this.mPendingIntent });
  }
  
  public boolean isCanceled()
  {
    return this.zzTS == 16;
  }
  
  public boolean isInterrupted()
  {
    return this.zzTS == 14;
  }
  
  public boolean isSuccess()
  {
    return this.zzTS <= 0;
  }
  
  public void startResolutionForResult(Activity paramActivity, int paramInt)
    throws IntentSender.SendIntentException
  {
    if (!hasResolution()) {
      return;
    }
    paramActivity.startIntentSenderForResult(this.mPendingIntent.getIntentSender(), paramInt, null, 0, 0, 0);
  }
  
  public String toString()
  {
    return zzt.zzt(this).zzg("statusCode", zzmU()).zzg("resolution", this.mPendingIntent).toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzk.zza(this, paramParcel, paramInt);
  }
  
  PendingIntent zzmT()
  {
    return this.mPendingIntent;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/api/Status.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */