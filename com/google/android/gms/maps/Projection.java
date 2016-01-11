package com.google.android.gms.maps;

import android.graphics.Point;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.maps.internal.IProjectionDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.VisibleRegion;

public final class Projection
{
  private final IProjectionDelegate zzaCi;
  
  Projection(IProjectionDelegate paramIProjectionDelegate)
  {
    this.zzaCi = paramIProjectionDelegate;
  }
  
  public LatLng fromScreenLocation(Point paramPoint)
  {
    try
    {
      paramPoint = this.zzaCi.fromScreenLocation(zze.zzw(paramPoint));
      return paramPoint;
    }
    catch (RemoteException paramPoint)
    {
      throw new RuntimeRemoteException(paramPoint);
    }
  }
  
  public VisibleRegion getVisibleRegion()
  {
    try
    {
      VisibleRegion localVisibleRegion = this.zzaCi.getVisibleRegion();
      return localVisibleRegion;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }
  
  public Point toScreenLocation(LatLng paramLatLng)
  {
    try
    {
      paramLatLng = (Point)zze.zzn(this.zzaCi.toScreenLocation(paramLatLng));
      return paramLatLng;
    }
    catch (RemoteException paramLatLng)
    {
      throw new RuntimeRemoteException(paramLatLng);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/Projection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */