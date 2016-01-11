package com.google.android.gms.location.places;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzt.zza;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;

public class AddPlaceRequest
  implements SafeParcelable
{
  public static final Parcelable.Creator<AddPlaceRequest> CREATOR = new zzb();
  private final String mName;
  final int zzCY;
  private final String zzajO;
  private final LatLng zzazn;
  private final List<Integer> zzazo;
  private final String zzazp;
  private final Uri zzazq;
  
  AddPlaceRequest(int paramInt, String paramString1, LatLng paramLatLng, String paramString2, List<Integer> paramList, String paramString3, Uri paramUri)
  {
    this.zzCY = paramInt;
    this.mName = zzu.zzcj(paramString1);
    this.zzazn = ((LatLng)zzu.zzu(paramLatLng));
    this.zzajO = paramString2;
    this.zzazo = new ArrayList(paramList);
    if ((!TextUtils.isEmpty(paramString3)) || (paramUri != null)) {}
    for (boolean bool = true;; bool = false)
    {
      zzu.zzb(bool, "One of phone number or URI should be provided.");
      this.zzazp = paramString3;
      this.zzazq = paramUri;
      return;
    }
  }
  
  public AddPlaceRequest(String paramString1, LatLng paramLatLng, String paramString2, List<Integer> paramList, Uri paramUri)
  {
    this(paramString1, paramLatLng, paramString2, paramList, null, (Uri)zzu.zzu(paramUri));
  }
  
  public AddPlaceRequest(String paramString1, LatLng paramLatLng, String paramString2, List<Integer> paramList, String paramString3)
  {
    this(paramString1, paramLatLng, paramString2, paramList, zzu.zzcj(paramString3), null);
  }
  
  public AddPlaceRequest(String paramString1, LatLng paramLatLng, String paramString2, List<Integer> paramList, String paramString3, Uri paramUri)
  {
    this(0, paramString1, paramLatLng, paramString2, paramList, paramString3, paramUri);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String getAddress()
  {
    return this.zzajO;
  }
  
  public LatLng getLatLng()
  {
    return this.zzazn;
  }
  
  public String getName()
  {
    return this.mName;
  }
  
  public String getPhoneNumber()
  {
    return this.zzazp;
  }
  
  public List<Integer> getPlaceTypes()
  {
    return this.zzazo;
  }
  
  public Uri getWebsiteUri()
  {
    return this.zzazq;
  }
  
  public String toString()
  {
    return zzt.zzt(this).zzg("name", this.mName).zzg("latLng", this.zzazn).zzg("address", this.zzajO).zzg("placeTypes", this.zzazo).zzg("phoneNumer", this.zzazp).zzg("websiteUri", this.zzazq).toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzb.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/AddPlaceRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */