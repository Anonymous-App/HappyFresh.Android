package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.model.internal.zzl;
import com.google.android.gms.maps.model.internal.zzl.zza;

public final class TileOverlayOptions
  implements SafeParcelable
{
  public static final zzn CREATOR = new zzn();
  private final int zzCY;
  private zzl zzaDG;
  private TileProvider zzaDH;
  private boolean zzaDI = true;
  private float zzaDa;
  private boolean zzaDb = true;
  
  public TileOverlayOptions()
  {
    this.zzCY = 1;
  }
  
  TileOverlayOptions(int paramInt, IBinder paramIBinder, boolean paramBoolean1, float paramFloat, boolean paramBoolean2)
  {
    this.zzCY = paramInt;
    this.zzaDG = zzl.zza.zzcT(paramIBinder);
    if (this.zzaDG == null) {}
    for (paramIBinder = null;; paramIBinder = new TileProvider()
        {
          private final zzl zzaDJ = TileOverlayOptions.zza(TileOverlayOptions.this);
          
          public Tile getTile(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
          {
            try
            {
              Tile localTile = this.zzaDJ.getTile(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3);
              return localTile;
            }
            catch (RemoteException localRemoteException) {}
            return null;
          }
        })
    {
      this.zzaDH = paramIBinder;
      this.zzaDb = paramBoolean1;
      this.zzaDa = paramFloat;
      this.zzaDI = paramBoolean2;
      return;
    }
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public TileOverlayOptions fadeIn(boolean paramBoolean)
  {
    this.zzaDI = paramBoolean;
    return this;
  }
  
  public boolean getFadeIn()
  {
    return this.zzaDI;
  }
  
  public TileProvider getTileProvider()
  {
    return this.zzaDH;
  }
  
  int getVersionCode()
  {
    return this.zzCY;
  }
  
  public float getZIndex()
  {
    return this.zzaDa;
  }
  
  public boolean isVisible()
  {
    return this.zzaDb;
  }
  
  public TileOverlayOptions tileProvider(final TileProvider paramTileProvider)
  {
    this.zzaDH = paramTileProvider;
    if (this.zzaDH == null) {}
    for (paramTileProvider = null;; paramTileProvider = new zzl.zza()
        {
          public Tile getTile(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
          {
            return paramTileProvider.getTile(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3);
          }
        })
    {
      this.zzaDG = paramTileProvider;
      return this;
    }
  }
  
  public TileOverlayOptions visible(boolean paramBoolean)
  {
    this.zzaDb = paramBoolean;
    return this;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzn.zza(this, paramParcel, paramInt);
  }
  
  public TileOverlayOptions zIndex(float paramFloat)
  {
    this.zzaDa = paramFloat;
    return this;
  }
  
  IBinder zzvL()
  {
    return this.zzaDG.asBinder();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/model/TileOverlayOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */