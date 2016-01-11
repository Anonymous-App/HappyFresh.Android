package com.google.android.gms.location.places.internal;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.google.android.gms.location.places.AddPlaceRequest;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.NearbyAlertRequest;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.PlaceRequest;
import com.google.android.gms.location.places.UserDataType;
import com.google.android.gms.location.places.personalized.PlaceAlias;
import com.google.android.gms.location.places.zzn;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.zzd;
import java.util.List;

public abstract interface zzf
  extends IInterface
{
  public abstract void zza(AddPlaceRequest paramAddPlaceRequest, PlacesParams paramPlacesParams, zzh paramzzh)
    throws RemoteException;
  
  public abstract void zza(NearbyAlertRequest paramNearbyAlertRequest, PlacesParams paramPlacesParams, PendingIntent paramPendingIntent)
    throws RemoteException;
  
  public abstract void zza(PlaceReport paramPlaceReport, PlacesParams paramPlacesParams)
    throws RemoteException;
  
  public abstract void zza(PlaceRequest paramPlaceRequest, PlacesParams paramPlacesParams, PendingIntent paramPendingIntent)
    throws RemoteException;
  
  public abstract void zza(UserDataType paramUserDataType, LatLngBounds paramLatLngBounds, List<String> paramList, PlacesParams paramPlacesParams, zzh paramzzh)
    throws RemoteException;
  
  public abstract void zza(PlacesParams paramPlacesParams, PendingIntent paramPendingIntent)
    throws RemoteException;
  
  public abstract void zza(PlaceAlias paramPlaceAlias, String paramString1, String paramString2, PlacesParams paramPlacesParams, zzh paramzzh)
    throws RemoteException;
  
  public abstract void zza(LatLng paramLatLng, PlaceFilter paramPlaceFilter, PlacesParams paramPlacesParams, zzh paramzzh)
    throws RemoteException;
  
  public abstract void zza(LatLngBounds paramLatLngBounds, int paramInt, String paramString, PlaceFilter paramPlaceFilter, PlacesParams paramPlacesParams, zzh paramzzh)
    throws RemoteException;
  
  public abstract void zza(String paramString, int paramInt1, int paramInt2, int paramInt3, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;
  
  public abstract void zza(String paramString, int paramInt, PlacesParams paramPlacesParams, zzh paramzzh)
    throws RemoteException;
  
  public abstract void zza(String paramString, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;
  
  public abstract void zza(String paramString, PlacesParams paramPlacesParams, zzh paramzzh)
    throws RemoteException;
  
  public abstract void zza(String paramString, LatLngBounds paramLatLngBounds, AutocompleteFilter paramAutocompleteFilter, PlacesParams paramPlacesParams, zzh paramzzh)
    throws RemoteException;
  
  public abstract void zza(List<String> paramList, PlacesParams paramPlacesParams, zzh paramzzh)
    throws RemoteException;
  
  public abstract void zzb(PlaceFilter paramPlaceFilter, PlacesParams paramPlacesParams, zzh paramzzh)
    throws RemoteException;
  
  public abstract void zzb(PlacesParams paramPlacesParams, PendingIntent paramPendingIntent)
    throws RemoteException;
  
  public abstract void zzb(String paramString, PlacesParams paramPlacesParams, zzh paramzzh)
    throws RemoteException;
  
  public abstract void zzb(List<String> paramList, PlacesParams paramPlacesParams, zzh paramzzh)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzf
  {
    public static zzf zzca(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
      if ((localIInterface != null) && ((localIInterface instanceof zzf))) {
        return (zzf)localIInterface;
      }
      return new zza(paramIBinder);
    }
    
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      Object localObject6 = null;
      Object localObject3 = null;
      Object localObject1 = null;
      Object localObject7 = null;
      Object localObject8 = null;
      Object localObject9 = null;
      Object localObject4 = null;
      Object localObject5 = null;
      Object localObject10 = null;
      Object localObject11 = null;
      Object localObject12 = null;
      Object localObject2 = null;
      switch (paramInt1)
      {
      default: 
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902: 
        paramParcel2.writeString("com.google.android.gms.location.places.internal.IGooglePlacesService");
        return true;
      case 2: 
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        if (paramParcel1.readInt() != 0)
        {
          paramParcel2 = LatLngBounds.CREATOR.zzeO(paramParcel1);
          paramInt1 = paramParcel1.readInt();
          localObject3 = paramParcel1.readString();
          if (paramParcel1.readInt() == 0) {
            break label316;
          }
        }
        for (localObject1 = PlaceFilter.CREATOR.zzer(paramParcel1);; localObject1 = null)
        {
          if (paramParcel1.readInt() != 0) {
            localObject2 = PlacesParams.CREATOR.zzeB(paramParcel1);
          }
          zza(paramParcel2, paramInt1, (String)localObject3, (PlaceFilter)localObject1, (PlacesParams)localObject2, zzh.zza.zzcc(paramParcel1.readStrongBinder()));
          return true;
          paramParcel2 = null;
          break;
        }
      case 3: 
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        localObject1 = paramParcel1.readString();
        paramParcel2 = (Parcel)localObject6;
        if (paramParcel1.readInt() != 0) {
          paramParcel2 = PlacesParams.CREATOR.zzeB(paramParcel1);
        }
        zza((String)localObject1, paramParcel2, zzh.zza.zzcc(paramParcel1.readStrongBinder()));
        return true;
      case 4: 
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        if (paramParcel1.readInt() != 0)
        {
          paramParcel2 = LatLng.CREATOR.zzeP(paramParcel1);
          if (paramParcel1.readInt() == 0) {
            break label448;
          }
        }
        for (localObject1 = PlaceFilter.CREATOR.zzer(paramParcel1);; localObject1 = null)
        {
          localObject2 = localObject3;
          if (paramParcel1.readInt() != 0) {
            localObject2 = PlacesParams.CREATOR.zzeB(paramParcel1);
          }
          zza(paramParcel2, (PlaceFilter)localObject1, (PlacesParams)localObject2, zzh.zza.zzcc(paramParcel1.readStrongBinder()));
          return true;
          paramParcel2 = null;
          break;
        }
      case 5: 
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        if (paramParcel1.readInt() != 0) {}
        for (paramParcel2 = PlaceFilter.CREATOR.zzer(paramParcel1);; paramParcel2 = null)
        {
          if (paramParcel1.readInt() != 0) {
            localObject1 = PlacesParams.CREATOR.zzeB(paramParcel1);
          }
          zzb(paramParcel2, (PlacesParams)localObject1, zzh.zza.zzcc(paramParcel1.readStrongBinder()));
          return true;
        }
      case 6: 
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        localObject1 = paramParcel1.readString();
        paramParcel2 = (Parcel)localObject7;
        if (paramParcel1.readInt() != 0) {
          paramParcel2 = PlacesParams.CREATOR.zzeB(paramParcel1);
        }
        zzb((String)localObject1, paramParcel2, zzh.zza.zzcc(paramParcel1.readStrongBinder()));
        return true;
      case 7: 
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        localObject1 = paramParcel1.createStringArrayList();
        paramParcel2 = (Parcel)localObject8;
        if (paramParcel1.readInt() != 0) {
          paramParcel2 = PlacesParams.CREATOR.zzeB(paramParcel1);
        }
        zza((List)localObject1, paramParcel2, zzh.zza.zzcc(paramParcel1.readStrongBinder()));
        return true;
      case 17: 
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        localObject1 = paramParcel1.createStringArrayList();
        paramParcel2 = (Parcel)localObject9;
        if (paramParcel1.readInt() != 0) {
          paramParcel2 = PlacesParams.CREATOR.zzeB(paramParcel1);
        }
        zzb((List)localObject1, paramParcel2, zzh.zza.zzcc(paramParcel1.readStrongBinder()));
        return true;
      case 8: 
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        if (paramParcel1.readInt() != 0)
        {
          paramParcel2 = UserDataType.CREATOR.zzew(paramParcel1);
          if (paramParcel1.readInt() == 0) {
            break label734;
          }
          localObject1 = LatLngBounds.CREATOR.zzeO(paramParcel1);
          localObject3 = paramParcel1.createStringArrayList();
          if (paramParcel1.readInt() == 0) {
            break label740;
          }
        }
        for (localObject2 = PlacesParams.CREATOR.zzeB(paramParcel1);; localObject2 = null)
        {
          zza(paramParcel2, (LatLngBounds)localObject1, (List)localObject3, (PlacesParams)localObject2, zzh.zza.zzcc(paramParcel1.readStrongBinder()));
          return true;
          paramParcel2 = null;
          break;
          localObject1 = null;
          break label687;
        }
      case 9: 
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        if (paramParcel1.readInt() != 0)
        {
          paramParcel2 = (PlaceRequest)PlaceRequest.CREATOR.createFromParcel(paramParcel1);
          if (paramParcel1.readInt() == 0) {
            break label823;
          }
          localObject1 = PlacesParams.CREATOR.zzeB(paramParcel1);
          if (paramParcel1.readInt() == 0) {
            break label829;
          }
        }
        for (paramParcel1 = (PendingIntent)PendingIntent.CREATOR.createFromParcel(paramParcel1);; paramParcel1 = null)
        {
          zza(paramParcel2, (PlacesParams)localObject1, paramParcel1);
          return true;
          paramParcel2 = null;
          break;
          localObject1 = null;
          break label788;
        }
      case 10: 
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        if (paramParcel1.readInt() != 0)
        {
          paramParcel2 = PlacesParams.CREATOR.zzeB(paramParcel1);
          if (paramParcel1.readInt() == 0) {
            break label888;
          }
        }
        for (paramParcel1 = (PendingIntent)PendingIntent.CREATOR.createFromParcel(paramParcel1);; paramParcel1 = null)
        {
          zza(paramParcel2, paramParcel1);
          return true;
          paramParcel2 = null;
          break;
        }
      case 11: 
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        if (paramParcel1.readInt() != 0)
        {
          paramParcel2 = NearbyAlertRequest.CREATOR.zzeq(paramParcel1);
          if (paramParcel1.readInt() == 0) {
            break label965;
          }
          localObject1 = PlacesParams.CREATOR.zzeB(paramParcel1);
          if (paramParcel1.readInt() == 0) {
            break label971;
          }
        }
        for (paramParcel1 = (PendingIntent)PendingIntent.CREATOR.createFromParcel(paramParcel1);; paramParcel1 = null)
        {
          zza(paramParcel2, (PlacesParams)localObject1, paramParcel1);
          return true;
          paramParcel2 = null;
          break;
          localObject1 = null;
          break label930;
        }
      case 12: 
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        if (paramParcel1.readInt() != 0)
        {
          paramParcel2 = PlacesParams.CREATOR.zzeB(paramParcel1);
          if (paramParcel1.readInt() == 0) {
            break label1030;
          }
        }
        for (paramParcel1 = (PendingIntent)PendingIntent.CREATOR.createFromParcel(paramParcel1);; paramParcel1 = null)
        {
          zzb(paramParcel2, paramParcel1);
          return true;
          paramParcel2 = null;
          break;
        }
      case 13: 
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        localObject3 = paramParcel1.readString();
        if (paramParcel1.readInt() != 0)
        {
          paramParcel2 = LatLngBounds.CREATOR.zzeO(paramParcel1);
          if (paramParcel1.readInt() == 0) {
            break label1119;
          }
          localObject1 = AutocompleteFilter.CREATOR.zzeo(paramParcel1);
          if (paramParcel1.readInt() == 0) {
            break label1125;
          }
        }
        for (localObject2 = PlacesParams.CREATOR.zzeB(paramParcel1);; localObject2 = null)
        {
          zza((String)localObject3, paramParcel2, (AutocompleteFilter)localObject1, (PlacesParams)localObject2, zzh.zza.zzcc(paramParcel1.readStrongBinder()));
          return true;
          paramParcel2 = null;
          break;
          localObject1 = null;
          break label1078;
        }
      case 14: 
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        if (paramParcel1.readInt() != 0) {}
        for (paramParcel2 = (AddPlaceRequest)AddPlaceRequest.CREATOR.createFromParcel(paramParcel1);; paramParcel2 = null)
        {
          localObject1 = localObject4;
          if (paramParcel1.readInt() != 0) {
            localObject1 = PlacesParams.CREATOR.zzeB(paramParcel1);
          }
          zza(paramParcel2, (PlacesParams)localObject1, zzh.zza.zzcc(paramParcel1.readStrongBinder()));
          return true;
        }
      case 15: 
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        if (paramParcel1.readInt() != 0) {}
        for (paramParcel2 = (PlaceReport)PlaceReport.CREATOR.createFromParcel(paramParcel1);; paramParcel2 = null)
        {
          localObject1 = localObject5;
          if (paramParcel1.readInt() != 0) {
            localObject1 = PlacesParams.CREATOR.zzeB(paramParcel1);
          }
          zza(paramParcel2, (PlacesParams)localObject1);
          return true;
        }
      case 16: 
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        if (paramParcel1.readInt() != 0)
        {
          paramParcel2 = PlaceAlias.CREATOR.zzeF(paramParcel1);
          localObject2 = paramParcel1.readString();
          localObject3 = paramParcel1.readString();
          if (paramParcel1.readInt() == 0) {
            break label1332;
          }
        }
        for (localObject1 = PlacesParams.CREATOR.zzeB(paramParcel1);; localObject1 = null)
        {
          zza(paramParcel2, (String)localObject2, (String)localObject3, (PlacesParams)localObject1, zzh.zza.zzcc(paramParcel1.readStrongBinder()));
          return true;
          paramParcel2 = null;
          break;
        }
      case 18: 
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        localObject1 = paramParcel1.readString();
        paramInt1 = paramParcel1.readInt();
        paramParcel2 = (Parcel)localObject10;
        if (paramParcel1.readInt() != 0) {
          paramParcel2 = PlacesParams.CREATOR.zzeB(paramParcel1);
        }
        zza((String)localObject1, paramInt1, paramParcel2, zzh.zza.zzcc(paramParcel1.readStrongBinder()));
        return true;
      case 19: 
        label316:
        label448:
        label687:
        label734:
        label740:
        label788:
        label823:
        label829:
        label888:
        label930:
        label965:
        label971:
        label1030:
        label1078:
        label1119:
        label1125:
        label1332:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        localObject1 = paramParcel1.readString();
        paramParcel2 = (Parcel)localObject11;
        if (paramParcel1.readInt() != 0) {
          paramParcel2 = PlacesParams.CREATOR.zzeB(paramParcel1);
        }
        zza((String)localObject1, paramParcel2, zzg.zza.zzcb(paramParcel1.readStrongBinder()));
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
      localObject1 = paramParcel1.readString();
      paramInt1 = paramParcel1.readInt();
      paramInt2 = paramParcel1.readInt();
      int i = paramParcel1.readInt();
      paramParcel2 = (Parcel)localObject12;
      if (paramParcel1.readInt() != 0) {
        paramParcel2 = PlacesParams.CREATOR.zzeB(paramParcel1);
      }
      zza((String)localObject1, paramInt1, paramInt2, i, paramParcel2, zzg.zza.zzcb(paramParcel1.readStrongBinder()));
      return true;
    }
    
    private static class zza
      implements zzf
    {
      private IBinder zznF;
      
      zza(IBinder paramIBinder)
      {
        this.zznF = paramIBinder;
      }
      
      public IBinder asBinder()
      {
        return this.zznF;
      }
      
      public void zza(AddPlaceRequest paramAddPlaceRequest, PlacesParams paramPlacesParams, zzh paramzzh)
        throws RemoteException
      {
        Object localObject = null;
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            if (paramAddPlaceRequest != null)
            {
              localParcel.writeInt(1);
              paramAddPlaceRequest.writeToParcel(localParcel, 0);
              if (paramPlacesParams != null)
              {
                localParcel.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel, 0);
                paramAddPlaceRequest = (AddPlaceRequest)localObject;
                if (paramzzh != null) {
                  paramAddPlaceRequest = paramzzh.asBinder();
                }
                localParcel.writeStrongBinder(paramAddPlaceRequest);
                this.zznF.transact(14, localParcel, null, 1);
              }
            }
            else
            {
              localParcel.writeInt(0);
              continue;
            }
            localParcel.writeInt(0);
          }
          finally
          {
            localParcel.recycle();
          }
        }
      }
      
      public void zza(NearbyAlertRequest paramNearbyAlertRequest, PlacesParams paramPlacesParams, PendingIntent paramPendingIntent)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            if (paramNearbyAlertRequest != null)
            {
              localParcel.writeInt(1);
              paramNearbyAlertRequest.writeToParcel(localParcel, 0);
              if (paramPlacesParams != null)
              {
                localParcel.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel, 0);
                if (paramPendingIntent == null) {
                  break label111;
                }
                localParcel.writeInt(1);
                paramPendingIntent.writeToParcel(localParcel, 0);
                this.zznF.transact(11, localParcel, null, 1);
              }
            }
            else
            {
              localParcel.writeInt(0);
              continue;
            }
            localParcel.writeInt(0);
          }
          finally
          {
            localParcel.recycle();
          }
          continue;
          label111:
          localParcel.writeInt(0);
        }
      }
      
      public void zza(PlaceReport paramPlaceReport, PlacesParams paramPlacesParams)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            if (paramPlaceReport != null)
            {
              localParcel.writeInt(1);
              paramPlaceReport.writeToParcel(localParcel, 0);
              if (paramPlacesParams != null)
              {
                localParcel.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel, 0);
                this.zznF.transact(15, localParcel, null, 1);
              }
            }
            else
            {
              localParcel.writeInt(0);
              continue;
            }
            localParcel.writeInt(0);
          }
          finally
          {
            localParcel.recycle();
          }
        }
      }
      
      public void zza(PlaceRequest paramPlaceRequest, PlacesParams paramPlacesParams, PendingIntent paramPendingIntent)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            if (paramPlaceRequest != null)
            {
              localParcel.writeInt(1);
              paramPlaceRequest.writeToParcel(localParcel, 0);
              if (paramPlacesParams != null)
              {
                localParcel.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel, 0);
                if (paramPendingIntent == null) {
                  break label111;
                }
                localParcel.writeInt(1);
                paramPendingIntent.writeToParcel(localParcel, 0);
                this.zznF.transact(9, localParcel, null, 1);
              }
            }
            else
            {
              localParcel.writeInt(0);
              continue;
            }
            localParcel.writeInt(0);
          }
          finally
          {
            localParcel.recycle();
          }
          continue;
          label111:
          localParcel.writeInt(0);
        }
      }
      
      public void zza(UserDataType paramUserDataType, LatLngBounds paramLatLngBounds, List<String> paramList, PlacesParams paramPlacesParams, zzh paramzzh)
        throws RemoteException
      {
        Object localObject = null;
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            if (paramUserDataType != null)
            {
              localParcel.writeInt(1);
              paramUserDataType.writeToParcel(localParcel, 0);
              if (paramLatLngBounds != null)
              {
                localParcel.writeInt(1);
                paramLatLngBounds.writeToParcel(localParcel, 0);
                localParcel.writeStringList(paramList);
                if (paramPlacesParams == null) {
                  break label144;
                }
                localParcel.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel, 0);
                paramUserDataType = (UserDataType)localObject;
                if (paramzzh != null) {
                  paramUserDataType = paramzzh.asBinder();
                }
                localParcel.writeStrongBinder(paramUserDataType);
                this.zznF.transact(8, localParcel, null, 1);
              }
            }
            else
            {
              localParcel.writeInt(0);
              continue;
            }
            localParcel.writeInt(0);
          }
          finally
          {
            localParcel.recycle();
          }
          continue;
          label144:
          localParcel.writeInt(0);
        }
      }
      
      public void zza(PlacesParams paramPlacesParams, PendingIntent paramPendingIntent)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            if (paramPlacesParams != null)
            {
              localParcel.writeInt(1);
              paramPlacesParams.writeToParcel(localParcel, 0);
              if (paramPendingIntent != null)
              {
                localParcel.writeInt(1);
                paramPendingIntent.writeToParcel(localParcel, 0);
                this.zznF.transact(10, localParcel, null, 1);
              }
            }
            else
            {
              localParcel.writeInt(0);
              continue;
            }
            localParcel.writeInt(0);
          }
          finally
          {
            localParcel.recycle();
          }
        }
      }
      
      public void zza(PlaceAlias paramPlaceAlias, String paramString1, String paramString2, PlacesParams paramPlacesParams, zzh paramzzh)
        throws RemoteException
      {
        Object localObject = null;
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            if (paramPlaceAlias != null)
            {
              localParcel.writeInt(1);
              paramPlaceAlias.writeToParcel(localParcel, 0);
              localParcel.writeString(paramString1);
              localParcel.writeString(paramString2);
              if (paramPlacesParams != null)
              {
                localParcel.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel, 0);
                paramPlaceAlias = (PlaceAlias)localObject;
                if (paramzzh != null) {
                  paramPlaceAlias = paramzzh.asBinder();
                }
                localParcel.writeStrongBinder(paramPlaceAlias);
                this.zznF.transact(16, localParcel, null, 1);
              }
            }
            else
            {
              localParcel.writeInt(0);
              continue;
            }
            localParcel.writeInt(0);
          }
          finally
          {
            localParcel.recycle();
          }
        }
      }
      
      public void zza(LatLng paramLatLng, PlaceFilter paramPlaceFilter, PlacesParams paramPlacesParams, zzh paramzzh)
        throws RemoteException
      {
        Object localObject = null;
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            if (paramLatLng != null)
            {
              localParcel.writeInt(1);
              paramLatLng.writeToParcel(localParcel, 0);
              if (paramPlaceFilter != null)
              {
                localParcel.writeInt(1);
                paramPlaceFilter.writeToParcel(localParcel, 0);
                if (paramPlacesParams == null) {
                  break label135;
                }
                localParcel.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel, 0);
                paramLatLng = (LatLng)localObject;
                if (paramzzh != null) {
                  paramLatLng = paramzzh.asBinder();
                }
                localParcel.writeStrongBinder(paramLatLng);
                this.zznF.transact(4, localParcel, null, 1);
              }
            }
            else
            {
              localParcel.writeInt(0);
              continue;
            }
            localParcel.writeInt(0);
          }
          finally
          {
            localParcel.recycle();
          }
          continue;
          label135:
          localParcel.writeInt(0);
        }
      }
      
      public void zza(LatLngBounds paramLatLngBounds, int paramInt, String paramString, PlaceFilter paramPlaceFilter, PlacesParams paramPlacesParams, zzh paramzzh)
        throws RemoteException
      {
        Object localObject = null;
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            if (paramLatLngBounds != null)
            {
              localParcel.writeInt(1);
              paramLatLngBounds.writeToParcel(localParcel, 0);
              localParcel.writeInt(paramInt);
              localParcel.writeString(paramString);
              if (paramPlaceFilter != null)
              {
                localParcel.writeInt(1);
                paramPlaceFilter.writeToParcel(localParcel, 0);
                if (paramPlacesParams == null) {
                  break label151;
                }
                localParcel.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel, 0);
                paramLatLngBounds = (LatLngBounds)localObject;
                if (paramzzh != null) {
                  paramLatLngBounds = paramzzh.asBinder();
                }
                localParcel.writeStrongBinder(paramLatLngBounds);
                this.zznF.transact(2, localParcel, null, 1);
              }
            }
            else
            {
              localParcel.writeInt(0);
              continue;
            }
            localParcel.writeInt(0);
          }
          finally
          {
            localParcel.recycle();
          }
          continue;
          label151:
          localParcel.writeInt(0);
        }
      }
      
      /* Error */
      public void zza(String paramString, int paramInt1, int paramInt2, int paramInt3, PlacesParams paramPlacesParams, zzg paramzzg)
        throws RemoteException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore 7
        //   3: invokestatic 30	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   6: astore 8
        //   8: aload 8
        //   10: ldc 32
        //   12: invokevirtual 36	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   15: aload 8
        //   17: aload_1
        //   18: invokevirtual 102	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   21: aload 8
        //   23: iload_2
        //   24: invokevirtual 40	android/os/Parcel:writeInt	(I)V
        //   27: aload 8
        //   29: iload_3
        //   30: invokevirtual 40	android/os/Parcel:writeInt	(I)V
        //   33: aload 8
        //   35: iload 4
        //   37: invokevirtual 40	android/os/Parcel:writeInt	(I)V
        //   40: aload 5
        //   42: ifnull +61 -> 103
        //   45: aload 8
        //   47: iconst_1
        //   48: invokevirtual 40	android/os/Parcel:writeInt	(I)V
        //   51: aload 5
        //   53: aload 8
        //   55: iconst_0
        //   56: invokevirtual 49	com/google/android/gms/location/places/internal/PlacesParams:writeToParcel	(Landroid/os/Parcel;I)V
        //   59: aload 7
        //   61: astore_1
        //   62: aload 6
        //   64: ifnull +11 -> 75
        //   67: aload 6
        //   69: invokeinterface 114 1 0
        //   74: astore_1
        //   75: aload 8
        //   77: aload_1
        //   78: invokevirtual 56	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   81: aload_0
        //   82: getfield 18	com/google/android/gms/location/places/internal/zzf$zza$zza:zznF	Landroid/os/IBinder;
        //   85: bipush 20
        //   87: aload 8
        //   89: aconst_null
        //   90: iconst_1
        //   91: invokeinterface 62 5 0
        //   96: pop
        //   97: aload 8
        //   99: invokevirtual 65	android/os/Parcel:recycle	()V
        //   102: return
        //   103: aload 8
        //   105: iconst_0
        //   106: invokevirtual 40	android/os/Parcel:writeInt	(I)V
        //   109: goto -50 -> 59
        //   112: astore_1
        //   113: aload 8
        //   115: invokevirtual 65	android/os/Parcel:recycle	()V
        //   118: aload_1
        //   119: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	120	0	this	zza
        //   0	120	1	paramString	String
        //   0	120	2	paramInt1	int
        //   0	120	3	paramInt2	int
        //   0	120	4	paramInt3	int
        //   0	120	5	paramPlacesParams	PlacesParams
        //   0	120	6	paramzzg	zzg
        //   1	59	7	localObject	Object
        //   6	108	8	localParcel	Parcel
        // Exception table:
        //   from	to	target	type
        //   8	40	112	finally
        //   45	59	112	finally
        //   67	75	112	finally
        //   75	97	112	finally
        //   103	109	112	finally
      }
      
      /* Error */
      public void zza(String paramString, int paramInt, PlacesParams paramPlacesParams, zzh paramzzh)
        throws RemoteException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore 5
        //   3: invokestatic 30	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   6: astore 6
        //   8: aload 6
        //   10: ldc 32
        //   12: invokevirtual 36	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   15: aload 6
        //   17: aload_1
        //   18: invokevirtual 102	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   21: aload 6
        //   23: iload_2
        //   24: invokevirtual 40	android/os/Parcel:writeInt	(I)V
        //   27: aload_3
        //   28: ifnull +60 -> 88
        //   31: aload 6
        //   33: iconst_1
        //   34: invokevirtual 40	android/os/Parcel:writeInt	(I)V
        //   37: aload_3
        //   38: aload 6
        //   40: iconst_0
        //   41: invokevirtual 49	com/google/android/gms/location/places/internal/PlacesParams:writeToParcel	(Landroid/os/Parcel;I)V
        //   44: aload 5
        //   46: astore_1
        //   47: aload 4
        //   49: ifnull +11 -> 60
        //   52: aload 4
        //   54: invokeinterface 53 1 0
        //   59: astore_1
        //   60: aload 6
        //   62: aload_1
        //   63: invokevirtual 56	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   66: aload_0
        //   67: getfield 18	com/google/android/gms/location/places/internal/zzf$zza$zza:zznF	Landroid/os/IBinder;
        //   70: bipush 18
        //   72: aload 6
        //   74: aconst_null
        //   75: iconst_1
        //   76: invokeinterface 62 5 0
        //   81: pop
        //   82: aload 6
        //   84: invokevirtual 65	android/os/Parcel:recycle	()V
        //   87: return
        //   88: aload 6
        //   90: iconst_0
        //   91: invokevirtual 40	android/os/Parcel:writeInt	(I)V
        //   94: goto -50 -> 44
        //   97: astore_1
        //   98: aload 6
        //   100: invokevirtual 65	android/os/Parcel:recycle	()V
        //   103: aload_1
        //   104: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	105	0	this	zza
        //   0	105	1	paramString	String
        //   0	105	2	paramInt	int
        //   0	105	3	paramPlacesParams	PlacesParams
        //   0	105	4	paramzzh	zzh
        //   1	44	5	localObject	Object
        //   6	93	6	localParcel	Parcel
        // Exception table:
        //   from	to	target	type
        //   8	27	97	finally
        //   31	44	97	finally
        //   52	60	97	finally
        //   60	82	97	finally
        //   88	94	97	finally
      }
      
      /* Error */
      public void zza(String paramString, PlacesParams paramPlacesParams, zzg paramzzg)
        throws RemoteException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore 4
        //   3: invokestatic 30	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   6: astore 5
        //   8: aload 5
        //   10: ldc 32
        //   12: invokevirtual 36	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   15: aload 5
        //   17: aload_1
        //   18: invokevirtual 102	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   21: aload_2
        //   22: ifnull +58 -> 80
        //   25: aload 5
        //   27: iconst_1
        //   28: invokevirtual 40	android/os/Parcel:writeInt	(I)V
        //   31: aload_2
        //   32: aload 5
        //   34: iconst_0
        //   35: invokevirtual 49	com/google/android/gms/location/places/internal/PlacesParams:writeToParcel	(Landroid/os/Parcel;I)V
        //   38: aload 4
        //   40: astore_1
        //   41: aload_3
        //   42: ifnull +10 -> 52
        //   45: aload_3
        //   46: invokeinterface 114 1 0
        //   51: astore_1
        //   52: aload 5
        //   54: aload_1
        //   55: invokevirtual 56	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   58: aload_0
        //   59: getfield 18	com/google/android/gms/location/places/internal/zzf$zza$zza:zznF	Landroid/os/IBinder;
        //   62: bipush 19
        //   64: aload 5
        //   66: aconst_null
        //   67: iconst_1
        //   68: invokeinterface 62 5 0
        //   73: pop
        //   74: aload 5
        //   76: invokevirtual 65	android/os/Parcel:recycle	()V
        //   79: return
        //   80: aload 5
        //   82: iconst_0
        //   83: invokevirtual 40	android/os/Parcel:writeInt	(I)V
        //   86: goto -48 -> 38
        //   89: astore_1
        //   90: aload 5
        //   92: invokevirtual 65	android/os/Parcel:recycle	()V
        //   95: aload_1
        //   96: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	97	0	this	zza
        //   0	97	1	paramString	String
        //   0	97	2	paramPlacesParams	PlacesParams
        //   0	97	3	paramzzg	zzg
        //   1	38	4	localObject	Object
        //   6	85	5	localParcel	Parcel
        // Exception table:
        //   from	to	target	type
        //   8	21	89	finally
        //   25	38	89	finally
        //   45	52	89	finally
        //   52	74	89	finally
        //   80	86	89	finally
      }
      
      /* Error */
      public void zza(String paramString, PlacesParams paramPlacesParams, zzh paramzzh)
        throws RemoteException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore 4
        //   3: invokestatic 30	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   6: astore 5
        //   8: aload 5
        //   10: ldc 32
        //   12: invokevirtual 36	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   15: aload 5
        //   17: aload_1
        //   18: invokevirtual 102	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   21: aload_2
        //   22: ifnull +57 -> 79
        //   25: aload 5
        //   27: iconst_1
        //   28: invokevirtual 40	android/os/Parcel:writeInt	(I)V
        //   31: aload_2
        //   32: aload 5
        //   34: iconst_0
        //   35: invokevirtual 49	com/google/android/gms/location/places/internal/PlacesParams:writeToParcel	(Landroid/os/Parcel;I)V
        //   38: aload 4
        //   40: astore_1
        //   41: aload_3
        //   42: ifnull +10 -> 52
        //   45: aload_3
        //   46: invokeinterface 53 1 0
        //   51: astore_1
        //   52: aload 5
        //   54: aload_1
        //   55: invokevirtual 56	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   58: aload_0
        //   59: getfield 18	com/google/android/gms/location/places/internal/zzf$zza$zza:zznF	Landroid/os/IBinder;
        //   62: iconst_3
        //   63: aload 5
        //   65: aconst_null
        //   66: iconst_1
        //   67: invokeinterface 62 5 0
        //   72: pop
        //   73: aload 5
        //   75: invokevirtual 65	android/os/Parcel:recycle	()V
        //   78: return
        //   79: aload 5
        //   81: iconst_0
        //   82: invokevirtual 40	android/os/Parcel:writeInt	(I)V
        //   85: goto -47 -> 38
        //   88: astore_1
        //   89: aload 5
        //   91: invokevirtual 65	android/os/Parcel:recycle	()V
        //   94: aload_1
        //   95: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	96	0	this	zza
        //   0	96	1	paramString	String
        //   0	96	2	paramPlacesParams	PlacesParams
        //   0	96	3	paramzzh	zzh
        //   1	38	4	localObject	Object
        //   6	84	5	localParcel	Parcel
        // Exception table:
        //   from	to	target	type
        //   8	21	88	finally
        //   25	38	88	finally
        //   45	52	88	finally
        //   52	73	88	finally
        //   79	85	88	finally
      }
      
      public void zza(String paramString, LatLngBounds paramLatLngBounds, AutocompleteFilter paramAutocompleteFilter, PlacesParams paramPlacesParams, zzh paramzzh)
        throws RemoteException
      {
        Object localObject = null;
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            localParcel.writeString(paramString);
            if (paramLatLngBounds != null)
            {
              localParcel.writeInt(1);
              paramLatLngBounds.writeToParcel(localParcel, 0);
              if (paramAutocompleteFilter != null)
              {
                localParcel.writeInt(1);
                paramAutocompleteFilter.writeToParcel(localParcel, 0);
                if (paramPlacesParams == null) {
                  break label144;
                }
                localParcel.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel, 0);
                paramString = (String)localObject;
                if (paramzzh != null) {
                  paramString = paramzzh.asBinder();
                }
                localParcel.writeStrongBinder(paramString);
                this.zznF.transact(13, localParcel, null, 1);
              }
            }
            else
            {
              localParcel.writeInt(0);
              continue;
            }
            localParcel.writeInt(0);
          }
          finally
          {
            localParcel.recycle();
          }
          continue;
          label144:
          localParcel.writeInt(0);
        }
      }
      
      /* Error */
      public void zza(List<String> paramList, PlacesParams paramPlacesParams, zzh paramzzh)
        throws RemoteException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore 4
        //   3: invokestatic 30	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   6: astore 5
        //   8: aload 5
        //   10: ldc 32
        //   12: invokevirtual 36	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   15: aload 5
        //   17: aload_1
        //   18: invokevirtual 92	android/os/Parcel:writeStringList	(Ljava/util/List;)V
        //   21: aload_2
        //   22: ifnull +58 -> 80
        //   25: aload 5
        //   27: iconst_1
        //   28: invokevirtual 40	android/os/Parcel:writeInt	(I)V
        //   31: aload_2
        //   32: aload 5
        //   34: iconst_0
        //   35: invokevirtual 49	com/google/android/gms/location/places/internal/PlacesParams:writeToParcel	(Landroid/os/Parcel;I)V
        //   38: aload 4
        //   40: astore_1
        //   41: aload_3
        //   42: ifnull +10 -> 52
        //   45: aload_3
        //   46: invokeinterface 53 1 0
        //   51: astore_1
        //   52: aload 5
        //   54: aload_1
        //   55: invokevirtual 56	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   58: aload_0
        //   59: getfield 18	com/google/android/gms/location/places/internal/zzf$zza$zza:zznF	Landroid/os/IBinder;
        //   62: bipush 7
        //   64: aload 5
        //   66: aconst_null
        //   67: iconst_1
        //   68: invokeinterface 62 5 0
        //   73: pop
        //   74: aload 5
        //   76: invokevirtual 65	android/os/Parcel:recycle	()V
        //   79: return
        //   80: aload 5
        //   82: iconst_0
        //   83: invokevirtual 40	android/os/Parcel:writeInt	(I)V
        //   86: goto -48 -> 38
        //   89: astore_1
        //   90: aload 5
        //   92: invokevirtual 65	android/os/Parcel:recycle	()V
        //   95: aload_1
        //   96: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	97	0	this	zza
        //   0	97	1	paramList	List<String>
        //   0	97	2	paramPlacesParams	PlacesParams
        //   0	97	3	paramzzh	zzh
        //   1	38	4	localObject	Object
        //   6	85	5	localParcel	Parcel
        // Exception table:
        //   from	to	target	type
        //   8	21	89	finally
        //   25	38	89	finally
        //   45	52	89	finally
        //   52	74	89	finally
        //   80	86	89	finally
      }
      
      public void zzb(PlaceFilter paramPlaceFilter, PlacesParams paramPlacesParams, zzh paramzzh)
        throws RemoteException
      {
        Object localObject = null;
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            if (paramPlaceFilter != null)
            {
              localParcel.writeInt(1);
              paramPlaceFilter.writeToParcel(localParcel, 0);
              if (paramPlacesParams != null)
              {
                localParcel.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel, 0);
                paramPlaceFilter = (PlaceFilter)localObject;
                if (paramzzh != null) {
                  paramPlaceFilter = paramzzh.asBinder();
                }
                localParcel.writeStrongBinder(paramPlaceFilter);
                this.zznF.transact(5, localParcel, null, 1);
              }
            }
            else
            {
              localParcel.writeInt(0);
              continue;
            }
            localParcel.writeInt(0);
          }
          finally
          {
            localParcel.recycle();
          }
        }
      }
      
      public void zzb(PlacesParams paramPlacesParams, PendingIntent paramPendingIntent)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            if (paramPlacesParams != null)
            {
              localParcel.writeInt(1);
              paramPlacesParams.writeToParcel(localParcel, 0);
              if (paramPendingIntent != null)
              {
                localParcel.writeInt(1);
                paramPendingIntent.writeToParcel(localParcel, 0);
                this.zznF.transact(12, localParcel, null, 1);
              }
            }
            else
            {
              localParcel.writeInt(0);
              continue;
            }
            localParcel.writeInt(0);
          }
          finally
          {
            localParcel.recycle();
          }
        }
      }
      
      /* Error */
      public void zzb(String paramString, PlacesParams paramPlacesParams, zzh paramzzh)
        throws RemoteException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore 4
        //   3: invokestatic 30	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   6: astore 5
        //   8: aload 5
        //   10: ldc 32
        //   12: invokevirtual 36	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   15: aload 5
        //   17: aload_1
        //   18: invokevirtual 102	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   21: aload_2
        //   22: ifnull +58 -> 80
        //   25: aload 5
        //   27: iconst_1
        //   28: invokevirtual 40	android/os/Parcel:writeInt	(I)V
        //   31: aload_2
        //   32: aload 5
        //   34: iconst_0
        //   35: invokevirtual 49	com/google/android/gms/location/places/internal/PlacesParams:writeToParcel	(Landroid/os/Parcel;I)V
        //   38: aload 4
        //   40: astore_1
        //   41: aload_3
        //   42: ifnull +10 -> 52
        //   45: aload_3
        //   46: invokeinterface 53 1 0
        //   51: astore_1
        //   52: aload 5
        //   54: aload_1
        //   55: invokevirtual 56	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   58: aload_0
        //   59: getfield 18	com/google/android/gms/location/places/internal/zzf$zza$zza:zznF	Landroid/os/IBinder;
        //   62: bipush 6
        //   64: aload 5
        //   66: aconst_null
        //   67: iconst_1
        //   68: invokeinterface 62 5 0
        //   73: pop
        //   74: aload 5
        //   76: invokevirtual 65	android/os/Parcel:recycle	()V
        //   79: return
        //   80: aload 5
        //   82: iconst_0
        //   83: invokevirtual 40	android/os/Parcel:writeInt	(I)V
        //   86: goto -48 -> 38
        //   89: astore_1
        //   90: aload 5
        //   92: invokevirtual 65	android/os/Parcel:recycle	()V
        //   95: aload_1
        //   96: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	97	0	this	zza
        //   0	97	1	paramString	String
        //   0	97	2	paramPlacesParams	PlacesParams
        //   0	97	3	paramzzh	zzh
        //   1	38	4	localObject	Object
        //   6	85	5	localParcel	Parcel
        // Exception table:
        //   from	to	target	type
        //   8	21	89	finally
        //   25	38	89	finally
        //   45	52	89	finally
        //   52	74	89	finally
        //   80	86	89	finally
      }
      
      /* Error */
      public void zzb(List<String> paramList, PlacesParams paramPlacesParams, zzh paramzzh)
        throws RemoteException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore 4
        //   3: invokestatic 30	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   6: astore 5
        //   8: aload 5
        //   10: ldc 32
        //   12: invokevirtual 36	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   15: aload 5
        //   17: aload_1
        //   18: invokevirtual 92	android/os/Parcel:writeStringList	(Ljava/util/List;)V
        //   21: aload_2
        //   22: ifnull +58 -> 80
        //   25: aload 5
        //   27: iconst_1
        //   28: invokevirtual 40	android/os/Parcel:writeInt	(I)V
        //   31: aload_2
        //   32: aload 5
        //   34: iconst_0
        //   35: invokevirtual 49	com/google/android/gms/location/places/internal/PlacesParams:writeToParcel	(Landroid/os/Parcel;I)V
        //   38: aload 4
        //   40: astore_1
        //   41: aload_3
        //   42: ifnull +10 -> 52
        //   45: aload_3
        //   46: invokeinterface 53 1 0
        //   51: astore_1
        //   52: aload 5
        //   54: aload_1
        //   55: invokevirtual 56	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   58: aload_0
        //   59: getfield 18	com/google/android/gms/location/places/internal/zzf$zza$zza:zznF	Landroid/os/IBinder;
        //   62: bipush 17
        //   64: aload 5
        //   66: aconst_null
        //   67: iconst_1
        //   68: invokeinterface 62 5 0
        //   73: pop
        //   74: aload 5
        //   76: invokevirtual 65	android/os/Parcel:recycle	()V
        //   79: return
        //   80: aload 5
        //   82: iconst_0
        //   83: invokevirtual 40	android/os/Parcel:writeInt	(I)V
        //   86: goto -48 -> 38
        //   89: astore_1
        //   90: aload 5
        //   92: invokevirtual 65	android/os/Parcel:recycle	()V
        //   95: aload_1
        //   96: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	97	0	this	zza
        //   0	97	1	paramList	List<String>
        //   0	97	2	paramPlacesParams	PlacesParams
        //   0	97	3	paramzzh	zzh
        //   1	38	4	localObject	Object
        //   6	85	5	localParcel	Parcel
        // Exception table:
        //   from	to	target	type
        //   8	21	89	finally
        //   25	38	89	finally
        //   45	52	89	finally
        //   52	74	89	finally
        //   80	86	89	finally
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/internal/zzf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */