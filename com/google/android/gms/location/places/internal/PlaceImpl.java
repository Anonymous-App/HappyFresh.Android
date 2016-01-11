package com.google.android.gms.location.places.internal;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzt.zza;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public final class PlaceImpl
  implements SafeParcelable, Place
{
  public static final zzk CREATOR = new zzk();
  private final String mName;
  final int zzCY;
  private final String zzKI;
  private final long zzaAA;
  private final List<Integer> zzaAB;
  private final String zzaAC;
  private final List<String> zzaAD;
  final boolean zzaAE;
  private final Map<Integer, String> zzaAF;
  private final TimeZone zzaAG;
  private zzo zzaAH;
  private Locale zzaAm;
  private final Bundle zzaAs;
  @Deprecated
  private final PlaceLocalization zzaAt;
  private final float zzaAu;
  private final LatLngBounds zzaAv;
  private final String zzaAw;
  private final boolean zzaAx;
  private final float zzaAy;
  private final int zzaAz;
  private final String zzajO;
  private final LatLng zzazn;
  private final List<Integer> zzazo;
  private final String zzazp;
  private final Uri zzazq;
  
  PlaceImpl(int paramInt1, String paramString1, List<Integer> paramList1, List<Integer> paramList2, Bundle paramBundle, String paramString2, String paramString3, String paramString4, String paramString5, List<String> paramList, LatLng paramLatLng, float paramFloat1, LatLngBounds paramLatLngBounds, String paramString6, Uri paramUri, boolean paramBoolean1, float paramFloat2, int paramInt2, long paramLong, boolean paramBoolean2, PlaceLocalization paramPlaceLocalization)
  {
    this.zzCY = paramInt1;
    this.zzKI = paramString1;
    this.zzazo = Collections.unmodifiableList(paramList1);
    this.zzaAB = paramList2;
    if (paramBundle != null)
    {
      this.zzaAs = paramBundle;
      this.mName = paramString2;
      this.zzajO = paramString3;
      this.zzazp = paramString4;
      this.zzaAC = paramString5;
      if (paramList == null) {
        break label182;
      }
      label68:
      this.zzaAD = paramList;
      this.zzazn = paramLatLng;
      this.zzaAu = paramFloat1;
      this.zzaAv = paramLatLngBounds;
      if (paramString6 == null) {
        break label190;
      }
    }
    for (;;)
    {
      this.zzaAw = paramString6;
      this.zzazq = paramUri;
      this.zzaAx = paramBoolean1;
      this.zzaAy = paramFloat2;
      this.zzaAz = paramInt2;
      this.zzaAA = paramLong;
      this.zzaAF = Collections.unmodifiableMap(new HashMap());
      this.zzaAG = null;
      this.zzaAm = null;
      this.zzaAE = paramBoolean2;
      this.zzaAt = paramPlaceLocalization;
      return;
      paramBundle = new Bundle();
      break;
      label182:
      paramList = Collections.emptyList();
      break label68;
      label190:
      paramString6 = "UTC";
    }
  }
  
  private void zzdp(String paramString)
  {
    if ((this.zzaAE) && (this.zzaAH != null)) {
      this.zzaAH.zzA(this.zzKI, paramString);
    }
  }
  
  public int describeContents()
  {
    zzk localzzk = CREATOR;
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if (!(paramObject instanceof PlaceImpl)) {
        return false;
      }
      paramObject = (PlaceImpl)paramObject;
    } while ((this.zzKI.equals(((PlaceImpl)paramObject).zzKI)) && (zzt.equal(this.zzaAm, ((PlaceImpl)paramObject).zzaAm)) && (this.zzaAA == ((PlaceImpl)paramObject).zzaAA));
    return false;
  }
  
  public String getAddress()
  {
    zzdp("getAddress");
    return this.zzajO;
  }
  
  public String getId()
  {
    zzdp("getId");
    return this.zzKI;
  }
  
  public LatLng getLatLng()
  {
    zzdp("getLatLng");
    return this.zzazn;
  }
  
  public Locale getLocale()
  {
    zzdp("getLocale");
    return this.zzaAm;
  }
  
  public String getName()
  {
    zzdp("getName");
    return this.mName;
  }
  
  public String getPhoneNumber()
  {
    zzdp("getPhoneNumber");
    return this.zzazp;
  }
  
  public List<Integer> getPlaceTypes()
  {
    zzdp("getPlaceTypes");
    return this.zzazo;
  }
  
  public int getPriceLevel()
  {
    zzdp("getPriceLevel");
    return this.zzaAz;
  }
  
  public float getRating()
  {
    zzdp("getRating");
    return this.zzaAy;
  }
  
  public LatLngBounds getViewport()
  {
    zzdp("getViewport");
    return this.zzaAv;
  }
  
  public Uri getWebsiteUri()
  {
    zzdp("getWebsiteUri");
    return this.zzazq;
  }
  
  public int hashCode()
  {
    return zzt.hashCode(new Object[] { this.zzKI, this.zzaAm, Long.valueOf(this.zzaAA) });
  }
  
  public boolean isDataValid()
  {
    return true;
  }
  
  public void setLocale(Locale paramLocale)
  {
    this.zzaAm = paramLocale;
  }
  
  public String toString()
  {
    return zzt.zzt(this).zzg("id", this.zzKI).zzg("placeTypes", this.zzazo).zzg("locale", this.zzaAm).zzg("name", this.mName).zzg("address", this.zzajO).zzg("phoneNumber", this.zzazp).zzg("latlng", this.zzazn).zzg("viewport", this.zzaAv).zzg("websiteUri", this.zzazq).zzg("isPermanentlyClosed", Boolean.valueOf(this.zzaAx)).zzg("priceLevel", Integer.valueOf(this.zzaAz)).zzg("timestampSecs", Long.valueOf(this.zzaAA)).toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzk localzzk = CREATOR;
    zzk.zza(this, paramParcel, paramInt);
  }
  
  public void zza(zzo paramzzo)
  {
    this.zzaAH = paramzzo;
  }
  
  public List<Integer> zzuN()
  {
    zzdp("getTypesDeprecated");
    return this.zzaAB;
  }
  
  public float zzuO()
  {
    zzdp("getLevelNumber");
    return this.zzaAu;
  }
  
  public String zzuP()
  {
    zzdp("getRegularOpenHours");
    return this.zzaAC;
  }
  
  public List<String> zzuQ()
  {
    zzdp("getAttributions");
    return this.zzaAD;
  }
  
  public boolean zzuR()
  {
    zzdp("isPermanentlyClosed");
    return this.zzaAx;
  }
  
  public long zzuS()
  {
    return this.zzaAA;
  }
  
  public Bundle zzuT()
  {
    return this.zzaAs;
  }
  
  public String zzuU()
  {
    return this.zzaAw;
  }
  
  @Deprecated
  public PlaceLocalization zzuV()
  {
    zzdp("getLocalization");
    return this.zzaAt;
  }
  
  public Place zzuW()
  {
    return this;
  }
  
  public static class zza
  {
    private String mName;
    private int zzCY = 0;
    private String zzKI;
    private long zzaAA;
    private String zzaAC;
    private List<String> zzaAD;
    private boolean zzaAE;
    private Bundle zzaAI;
    private List<Integer> zzaAJ;
    private float zzaAu;
    private LatLngBounds zzaAv;
    private String zzaAw;
    private boolean zzaAx;
    private float zzaAy;
    private int zzaAz;
    private String zzajO;
    private LatLng zzazn;
    private String zzazp;
    private Uri zzazq;
    
    public zza zza(LatLng paramLatLng)
    {
      this.zzazn = paramLatLng;
      return this;
    }
    
    public zza zza(LatLngBounds paramLatLngBounds)
    {
      this.zzaAv = paramLatLngBounds;
      return this;
    }
    
    public zza zzad(boolean paramBoolean)
    {
      this.zzaAx = paramBoolean;
      return this;
    }
    
    public zza zzae(boolean paramBoolean)
    {
      this.zzaAE = paramBoolean;
      return this;
    }
    
    public zza zzdq(String paramString)
    {
      this.zzKI = paramString;
      return this;
    }
    
    public zza zzdr(String paramString)
    {
      this.mName = paramString;
      return this;
    }
    
    public zza zzds(String paramString)
    {
      this.zzajO = paramString;
      return this;
    }
    
    public zza zzdt(String paramString)
    {
      this.zzazp = paramString;
      return this;
    }
    
    public zza zzf(float paramFloat)
    {
      this.zzaAu = paramFloat;
      return this;
    }
    
    public zza zzg(float paramFloat)
    {
      this.zzaAy = paramFloat;
      return this;
    }
    
    public zza zzgX(int paramInt)
    {
      this.zzaAz = paramInt;
      return this;
    }
    
    public zza zzk(Uri paramUri)
    {
      this.zzazq = paramUri;
      return this;
    }
    
    public zza zzm(List<Integer> paramList)
    {
      this.zzaAJ = paramList;
      return this;
    }
    
    public zza zzn(List<String> paramList)
    {
      this.zzaAD = paramList;
      return this;
    }
    
    public PlaceImpl zzuX()
    {
      return new PlaceImpl(this.zzCY, this.zzKI, this.zzaAJ, Collections.emptyList(), this.zzaAI, this.mName, this.zzajO, this.zzazp, this.zzaAC, this.zzaAD, this.zzazn, this.zzaAu, this.zzaAv, this.zzaAw, this.zzazq, this.zzaAx, this.zzaAy, this.zzaAz, this.zzaAA, this.zzaAE, PlaceLocalization.zza(this.mName, this.zzajO, this.zzazp, this.zzaAC, this.zzaAD));
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/internal/PlaceImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */