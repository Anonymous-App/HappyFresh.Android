package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class CredentialRequest
  implements SafeParcelable
{
  public static final Parcelable.Creator<CredentialRequest> CREATOR = new zzb();
  final int zzCY;
  private final boolean zzOY;
  private final String[] zzOZ;
  
  CredentialRequest(int paramInt, boolean paramBoolean, String[] paramArrayOfString)
  {
    this.zzCY = paramInt;
    this.zzOY = paramBoolean;
    this.zzOZ = paramArrayOfString;
  }
  
  private CredentialRequest(Builder paramBuilder)
  {
    this.zzCY = 1;
    this.zzOY = paramBuilder.zzOY;
    this.zzOZ = paramBuilder.zzOZ;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String[] getAccountTypes()
  {
    return this.zzOZ;
  }
  
  public boolean getSupportsPasswordLogin()
  {
    return this.zzOY;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzb.zza(this, paramParcel, paramInt);
  }
  
  public static final class Builder
  {
    boolean zzOY;
    String[] zzOZ;
    
    public CredentialRequest build()
    {
      if (this.zzOZ == null) {
        this.zzOZ = new String[0];
      }
      if ((!this.zzOY) && (this.zzOZ.length == 0)) {
        throw new IllegalStateException("At least one authentication method must be specified");
      }
      return new CredentialRequest(this, null);
    }
    
    public Builder setAccountTypes(String... paramVarArgs)
    {
      this.zzOZ = paramVarArgs;
      return this;
    }
    
    public Builder setSupportsPasswordLogin(boolean paramBoolean)
    {
      this.zzOY = paramBoolean;
      return this;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/auth/api/credentials/CredentialRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */