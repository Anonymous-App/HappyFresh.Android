package com.google.android.gms.location.places.ui;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.internal.PlaceImpl;
import com.google.android.gms.location.places.internal.zzo;
import com.google.android.gms.maps.model.LatLngBounds;

public class PlacePicker
{
  public static final int RESULT_ERROR = 2;
  
  public static String getAttributions(Intent paramIntent)
  {
    return paramIntent.getStringExtra("third_party_attributions");
  }
  
  public static LatLngBounds getLatLngBounds(Intent paramIntent)
  {
    return (LatLngBounds)zzc.zza(paramIntent, "final_latlng_bounds", LatLngBounds.CREATOR);
  }
  
  public static Place getPlace(Intent paramIntent, Context paramContext)
  {
    zzu.zzb(paramContext, "context must not be null");
    paramIntent = (PlaceImpl)zzc.zza(paramIntent, "selected_place", PlaceImpl.CREATOR);
    paramIntent.zza(zzo.zzax(paramContext));
    return paramIntent;
  }
  
  public static class IntentBuilder
  {
    private final Intent mIntent = new Intent("com.google.android.gms.location.places.ui.PICK_PLACE");
    
    public IntentBuilder()
    {
      this.mIntent.setPackage("com.google.android.gms");
      this.mIntent.putExtra("gmscore_client_jar_version", GoogleApiAvailability.GOOGLE_PLAY_SERVICES_VERSION_CODE);
    }
    
    public Intent build(Context paramContext)
      throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException
    {
      GoogleApiAvailability.getInstance().zzZ(paramContext);
      return this.mIntent;
    }
    
    public IntentBuilder setLatLngBounds(LatLngBounds paramLatLngBounds)
    {
      zzu.zzu(paramLatLngBounds);
      zzc.zza(paramLatLngBounds, this.mIntent, "latlng_bounds");
      return this;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/ui/PlacePicker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */