package com.google.android.gms.gcm;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;

public class OneoffTask
  extends Task
{
  public static final Parcelable.Creator<OneoffTask> CREATOR = new Parcelable.Creator()
  {
    public OneoffTask zzdV(Parcel paramAnonymousParcel)
    {
      return new OneoffTask(paramAnonymousParcel, null);
    }
    
    public OneoffTask[] zzgh(int paramAnonymousInt)
    {
      return new OneoffTask[paramAnonymousInt];
    }
  };
  private final long zzavZ;
  private final long zzawa;
  
  @Deprecated
  private OneoffTask(Parcel paramParcel)
  {
    super(paramParcel);
    this.zzavZ = paramParcel.readLong();
    this.zzawa = paramParcel.readLong();
  }
  
  private OneoffTask(Builder paramBuilder)
  {
    super(paramBuilder);
    this.zzavZ = Builder.zza(paramBuilder);
    this.zzawa = Builder.zzb(paramBuilder);
  }
  
  public long getWindowEnd()
  {
    return this.zzawa;
  }
  
  public long getWindowStart()
  {
    return this.zzavZ;
  }
  
  public void toBundle(Bundle paramBundle)
  {
    super.toBundle(paramBundle);
    paramBundle.putLong("window_start", this.zzavZ);
    paramBundle.putLong("window_end", this.zzawa);
  }
  
  public String toString()
  {
    return super.toString() + " " + "windowStart=" + getWindowStart() + " " + "windowEnd=" + getWindowEnd();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    paramParcel.writeLong(this.zzavZ);
    paramParcel.writeLong(this.zzawa);
  }
  
  public static class Builder
    extends Task.Builder
  {
    private long zzawb = -1L;
    private long zzawc = -1L;
    
    public Builder()
    {
      this.isPersisted = false;
    }
    
    public OneoffTask build()
    {
      checkConditions();
      return new OneoffTask(this, null);
    }
    
    protected void checkConditions()
    {
      super.checkConditions();
      if ((this.zzawb == -1L) || (this.zzawc == -1L)) {
        throw new IllegalArgumentException("Must specify an execution window using setExecutionWindow.");
      }
      if (this.zzawb >= this.zzawc) {
        throw new IllegalArgumentException("Window start must be shorter than window end.");
      }
    }
    
    public Builder setExecutionWindow(long paramLong1, long paramLong2)
    {
      this.zzawb = paramLong1;
      this.zzawc = paramLong2;
      return this;
    }
    
    public Builder setPersisted(boolean paramBoolean)
    {
      this.isPersisted = paramBoolean;
      return this;
    }
    
    public Builder setRequiredNetwork(int paramInt)
    {
      this.requiredNetworkState = paramInt;
      return this;
    }
    
    public Builder setRequiresCharging(boolean paramBoolean)
    {
      this.requiresCharging = paramBoolean;
      return this;
    }
    
    public Builder setService(Class<? extends GcmTaskService> paramClass)
    {
      this.gcmTaskService = paramClass.getName();
      return this;
    }
    
    public Builder setTag(String paramString)
    {
      this.tag = paramString;
      return this;
    }
    
    public Builder setUpdateCurrent(boolean paramBoolean)
    {
      this.updateCurrent = paramBoolean;
      return this;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/gcm/OneoffTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */