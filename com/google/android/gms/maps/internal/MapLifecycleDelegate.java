package com.google.android.gms.maps.internal;

import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.maps.OnMapReadyCallback;

public abstract interface MapLifecycleDelegate
  extends LifecycleDelegate
{
  public abstract void getMapAsync(OnMapReadyCallback paramOnMapReadyCallback);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/internal/MapLifecycleDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */