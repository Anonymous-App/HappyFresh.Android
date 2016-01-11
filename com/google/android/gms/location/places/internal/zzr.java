package com.google.android.gms.location.places.internal;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class zzr
  extends zzt
  implements Place
{
  private boolean zzaAE;
  private final zzo zzaAH;
  private final String zzazK;
  
  public zzr(DataHolder paramDataHolder, int paramInt, Context paramContext)
  {
    super(paramDataHolder, paramInt);
    if (paramContext != null) {}
    for (paramDataHolder = zzo.zzax(paramContext);; paramDataHolder = null)
    {
      this.zzaAH = paramDataHolder;
      this.zzaAE = zzh("place_is_logging_enabled", false);
      this.zzazK = zzB("place_id", "");
      return;
    }
  }
  
  private void zzdp(String paramString)
  {
    if ((this.zzaAE) && (this.zzaAH != null)) {
      this.zzaAH.zzA(this.zzazK, paramString);
    }
  }
  
  public CharSequence getAddress()
  {
    zzdp("getAddress");
    return zzB("place_address", "");
  }
  
  public String getId()
  {
    zzdp("getId");
    return this.zzazK;
  }
  
  public LatLng getLatLng()
  {
    zzdp("getLatLng");
    return (LatLng)zza("place_lat_lng", LatLng.CREATOR);
  }
  
  public Locale getLocale()
  {
    zzdp("getLocale");
    String str = zzB("place_locale", "");
    if (!TextUtils.isEmpty(str)) {
      return new Locale(str);
    }
    return Locale.getDefault();
  }
  
  public CharSequence getName()
  {
    zzdp("getName");
    return zzB("place_name", "");
  }
  
  public CharSequence getPhoneNumber()
  {
    zzdp("getPhoneNumber");
    return zzB("place_phone_number", "");
  }
  
  public List<Integer> getPlaceTypes()
  {
    zzdp("getPlaceTypes");
    return zza("place_types", Collections.emptyList());
  }
  
  public int getPriceLevel()
  {
    zzdp("getPriceLevel");
    return zzz("place_price_level", -1);
  }
  
  public float getRating()
  {
    zzdp("getRating");
    return zzb("place_rating", -1.0F);
  }
  
  public LatLngBounds getViewport()
  {
    zzdp("getViewport");
    return (LatLngBounds)zza("place_viewport", LatLngBounds.CREATOR);
  }
  
  public Uri getWebsiteUri()
  {
    zzdp("getWebsiteUri");
    String str = zzB("place_website_uri", null);
    if (str == null) {
      return null;
    }
    return Uri.parse(str);
  }
  
  public float zzuO()
  {
    zzdp("getLevelNumber");
    return zzb("place_level_number", 0.0F);
  }
  
  public boolean zzuR()
  {
    zzdp("isPermanentlyClosed");
    return zzh("place_is_permanently_closed", false);
  }
  
  public Place zzuW()
  {
    Object localObject = new PlaceImpl.zza().zzae(this.zzaAE);
    this.zzaAE = false;
    localObject = ((PlaceImpl.zza)localObject).zzds(getAddress().toString()).zzn(zzb("place_attributions", Collections.emptyList())).zzdq(getId()).zzad(zzuR()).zza(getLatLng()).zzf(zzuO()).zzdr(getName().toString()).zzdt(getPhoneNumber().toString()).zzgX(getPriceLevel()).zzg(getRating()).zzm(getPlaceTypes()).zza(getViewport()).zzk(getWebsiteUri()).zzuX();
    ((PlaceImpl)localObject).setLocale(getLocale());
    ((PlaceImpl)localObject).zza(this.zzaAH);
    return (Place)localObject;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/internal/zzr.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */