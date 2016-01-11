package com.google.android.gms.playlog.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzu;

public class PlayLoggerContext
  implements SafeParcelable
{
  public static final zze CREATOR = new zze();
  public final String packageName;
  public final int versionCode;
  public final int zzaGP;
  public final int zzaGQ;
  public final String zzaGR;
  public final String zzaGS;
  public final boolean zzaGT;
  public final String zzaGU;
  public final boolean zzaGV;
  
  public PlayLoggerContext(int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2, String paramString3, boolean paramBoolean1, String paramString4, boolean paramBoolean2)
  {
    this.versionCode = paramInt1;
    this.packageName = paramString1;
    this.zzaGP = paramInt2;
    this.zzaGQ = paramInt3;
    this.zzaGR = paramString2;
    this.zzaGS = paramString3;
    this.zzaGT = paramBoolean1;
    this.zzaGU = paramString4;
    this.zzaGV = paramBoolean2;
  }
  
  @Deprecated
  public PlayLoggerContext(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, boolean paramBoolean)
  {
    this.versionCode = 1;
    this.packageName = ((String)zzu.zzu(paramString1));
    this.zzaGP = paramInt1;
    this.zzaGQ = paramInt2;
    this.zzaGU = null;
    this.zzaGR = paramString2;
    this.zzaGS = paramString3;
    this.zzaGT = paramBoolean;
    this.zzaGV = false;
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
      if (!(paramObject instanceof PlayLoggerContext)) {
        break;
      }
      paramObject = (PlayLoggerContext)paramObject;
    } while ((this.versionCode == ((PlayLoggerContext)paramObject).versionCode) && (this.packageName.equals(((PlayLoggerContext)paramObject).packageName)) && (this.zzaGP == ((PlayLoggerContext)paramObject).zzaGP) && (this.zzaGQ == ((PlayLoggerContext)paramObject).zzaGQ) && (zzt.equal(this.zzaGU, ((PlayLoggerContext)paramObject).zzaGU)) && (zzt.equal(this.zzaGR, ((PlayLoggerContext)paramObject).zzaGR)) && (zzt.equal(this.zzaGS, ((PlayLoggerContext)paramObject).zzaGS)) && (this.zzaGT == ((PlayLoggerContext)paramObject).zzaGT) && (this.zzaGV == ((PlayLoggerContext)paramObject).zzaGV));
    return false;
    return false;
  }
  
  public int hashCode()
  {
    return zzt.hashCode(new Object[] { Integer.valueOf(this.versionCode), this.packageName, Integer.valueOf(this.zzaGP), Integer.valueOf(this.zzaGQ), this.zzaGU, this.zzaGR, this.zzaGS, Boolean.valueOf(this.zzaGT), Boolean.valueOf(this.zzaGV) });
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("PlayLoggerContext[");
    localStringBuilder.append("versionCode=").append(this.versionCode).append(',');
    localStringBuilder.append("package=").append(this.packageName).append(',');
    localStringBuilder.append("packageVersionCode=").append(this.zzaGP).append(',');
    localStringBuilder.append("logSource=").append(this.zzaGQ).append(',');
    localStringBuilder.append("logSourceName=").append(this.zzaGU).append(',');
    localStringBuilder.append("uploadAccount=").append(this.zzaGR).append(',');
    localStringBuilder.append("loggingId=").append(this.zzaGS).append(',');
    localStringBuilder.append("logAndroidId=").append(this.zzaGT).append(',');
    localStringBuilder.append("isAnonymous=").append(this.zzaGV);
    localStringBuilder.append("]");
    return localStringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zze.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/playlog/internal/PlayLoggerContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */