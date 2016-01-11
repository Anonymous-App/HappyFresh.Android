package com.google.android.gms.auth.api.credentials;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import java.util.Collections;
import java.util.List;

public class Credential
  implements SafeParcelable
{
  public static final Parcelable.Creator<Credential> CREATOR = new zza();
  public static final String EXTRA_KEY = "com.google.android.gms.credentials.Credential";
  private final String mName;
  final int zzCY;
  private final String zzKI;
  private final String zzOS;
  private final String zzOT;
  private final Uri zzOU;
  private final List<IdToken> zzOV;
  private final String zzOW;
  private final String zzOX;
  
  Credential(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, Uri paramUri, List<IdToken> paramList, String paramString5, String paramString6)
  {
    this.zzCY = paramInt;
    this.zzOS = paramString1;
    this.zzOT = paramString2;
    this.zzKI = ((String)zzu.zzu(paramString3));
    this.mName = paramString4;
    this.zzOU = paramUri;
    if (paramList == null) {}
    for (paramString1 = Collections.emptyList();; paramString1 = Collections.unmodifiableList(paramList))
    {
      this.zzOV = paramString1;
      this.zzOW = paramString5;
      this.zzOX = paramString6;
      return;
    }
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String getAccountType()
  {
    return this.zzOX;
  }
  
  public String getId()
  {
    return this.zzKI;
  }
  
  public String getName()
  {
    return this.mName;
  }
  
  public String getPassword()
  {
    return this.zzOW;
  }
  
  public Uri getProfilePictureUri()
  {
    return this.zzOU;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza(this, paramParcel, paramInt);
  }
  
  public String zzkZ()
  {
    return this.zzOS;
  }
  
  public String zzla()
  {
    return this.zzOT;
  }
  
  public List<IdToken> zzlb()
  {
    return this.zzOV;
  }
  
  public static class Builder
  {
    private String mName;
    private final String zzKI;
    private String zzOS;
    private String zzOT;
    private Uri zzOU;
    private List<IdToken> zzOV;
    private String zzOW;
    private String zzOX;
    
    public Builder(Credential paramCredential)
    {
      this.zzKI = Credential.zza(paramCredential);
      this.mName = Credential.zzb(paramCredential);
      this.zzOU = Credential.zzc(paramCredential);
      this.zzOV = Credential.zzd(paramCredential);
      this.zzOW = Credential.zze(paramCredential);
      this.zzOX = Credential.zzf(paramCredential);
      this.zzOS = Credential.zzg(paramCredential);
      this.zzOT = Credential.zzh(paramCredential);
    }
    
    public Builder(String paramString)
    {
      this.zzKI = paramString;
    }
    
    public Credential build()
    {
      if ((!TextUtils.isEmpty(this.zzOW)) && (!TextUtils.isEmpty(this.zzOX))) {
        throw new IllegalStateException("Only one of password or accountType may be set");
      }
      return new Credential(1, this.zzOS, this.zzOT, this.zzKI, this.mName, this.zzOU, this.zzOV, this.zzOW, this.zzOX);
    }
    
    public Builder setAccountType(String paramString)
    {
      this.zzOX = paramString;
      return this;
    }
    
    public Builder setName(String paramString)
    {
      this.mName = paramString;
      return this;
    }
    
    public Builder setPassword(String paramString)
    {
      this.zzOW = paramString;
      return this;
    }
    
    public Builder setProfilePictureUri(Uri paramUri)
    {
      this.zzOU = paramUri;
      return this;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/auth/api/credentials/Credential.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */