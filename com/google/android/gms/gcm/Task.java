package com.google.android.gms.gcm;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.zzu;

public abstract class Task
  implements Parcelable
{
  public static final int NETWORK_STATE_ANY = 2;
  public static final int NETWORK_STATE_CONNECTED = 0;
  public static final int NETWORK_STATE_UNMETERED = 1;
  protected static final long UNINITIALIZED = -1L;
  private final String mTag;
  private final String zzawf;
  private final boolean zzawg;
  private final boolean zzawh;
  private final int zzawi;
  private final boolean zzawj;
  
  @Deprecated
  Task(Parcel paramParcel)
  {
    this.zzawf = paramParcel.readString();
    this.mTag = paramParcel.readString();
    if (paramParcel.readInt() == 1)
    {
      bool1 = true;
      this.zzawg = bool1;
      if (paramParcel.readInt() != 1) {
        break label68;
      }
    }
    label68:
    for (boolean bool1 = bool2;; bool1 = false)
    {
      this.zzawh = bool1;
      this.zzawi = 2;
      this.zzawj = false;
      return;
      bool1 = false;
      break;
    }
  }
  
  Task(Builder paramBuilder)
  {
    this.zzawf = paramBuilder.gcmTaskService;
    this.mTag = paramBuilder.tag;
    this.zzawg = paramBuilder.updateCurrent;
    this.zzawh = paramBuilder.isPersisted;
    this.zzawi = paramBuilder.requiredNetworkState;
    this.zzawj = paramBuilder.requiresCharging;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public int getRequiredNetwork()
  {
    return this.zzawi;
  }
  
  public boolean getRequiresCharging()
  {
    return this.zzawj;
  }
  
  public String getServiceName()
  {
    return this.zzawf;
  }
  
  public String getTag()
  {
    return this.mTag;
  }
  
  public boolean isPersisted()
  {
    return this.zzawh;
  }
  
  public boolean isUpdateCurrent()
  {
    return this.zzawg;
  }
  
  public void toBundle(Bundle paramBundle)
  {
    paramBundle.putString("tag", this.mTag);
    paramBundle.putBoolean("update_current", this.zzawg);
    paramBundle.putBoolean("persisted", this.zzawh);
    paramBundle.putString("service", this.zzawf);
    paramBundle.putInt("requiredNetwork", this.zzawi);
    paramBundle.putBoolean("requiresCharging", this.zzawj);
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    int i = 1;
    paramParcel.writeString(this.zzawf);
    paramParcel.writeString(this.mTag);
    if (this.zzawg)
    {
      paramInt = 1;
      paramParcel.writeInt(paramInt);
      if (!this.zzawh) {
        break label52;
      }
    }
    label52:
    for (paramInt = i;; paramInt = 0)
    {
      paramParcel.writeInt(paramInt);
      return;
      paramInt = 0;
      break;
    }
  }
  
  public static abstract class Builder
  {
    protected String gcmTaskService;
    protected boolean isPersisted;
    protected int requiredNetworkState;
    protected boolean requiresCharging;
    protected String tag;
    protected boolean updateCurrent;
    
    public abstract Task build();
    
    protected void checkConditions()
    {
      if (this.gcmTaskService != null) {}
      for (boolean bool = true;; bool = false)
      {
        zzu.zzb(bool, "Must provide an endpoint for this task by calling setService(ComponentName).");
        GcmNetworkManager.zzcY(this.tag);
        return;
      }
    }
    
    public abstract Builder setPersisted(boolean paramBoolean);
    
    public abstract Builder setRequiredNetwork(int paramInt);
    
    public abstract Builder setRequiresCharging(boolean paramBoolean);
    
    public abstract Builder setService(Class<? extends GcmTaskService> paramClass);
    
    public abstract Builder setTag(String paramString);
    
    public abstract Builder setUpdateCurrent(boolean paramBoolean);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/gcm/Task.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */