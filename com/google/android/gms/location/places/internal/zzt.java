package com.google.android.gms.location.places.internal;

import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.zzrd;
import com.google.android.gms.internal.zzrm;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class zzt
  extends com.google.android.gms.common.data.zzc
{
  private final String TAG = "SafeDataBufferRef";
  
  public zzt(DataHolder paramDataHolder, int paramInt)
  {
    super(paramDataHolder, paramInt);
  }
  
  protected String zzB(String paramString1, String paramString2)
  {
    String str = paramString2;
    if (zzbV(paramString1))
    {
      str = paramString2;
      if (!zzbX(paramString1)) {
        str = getString(paramString1);
      }
    }
    return str;
  }
  
  protected <E extends SafeParcelable> E zza(String paramString, Parcelable.Creator<E> paramCreator)
  {
    paramString = zzc(paramString, null);
    if (paramString == null) {
      return null;
    }
    return com.google.android.gms.common.internal.safeparcel.zzc.zza(paramString, paramCreator);
  }
  
  protected <E extends SafeParcelable> List<E> zza(String paramString, Parcelable.Creator<E> paramCreator, List<E> paramList)
  {
    paramString = zzc(paramString, null);
    if (paramString == null) {}
    do
    {
      for (;;)
      {
        return paramList;
        try
        {
          Object localObject = zzrd.zzx(paramString);
          if (((zzrd)localObject).zzaVG != null)
          {
            paramString = new ArrayList(((zzrd)localObject).zzaVG.length);
            localObject = ((zzrd)localObject).zzaVG;
            int j = localObject.length;
            int i = 0;
            while (i < j)
            {
              paramString.add(com.google.android.gms.common.internal.safeparcel.zzc.zza(localObject[i], paramCreator));
              i += 1;
            }
            return paramString;
          }
        }
        catch (zzrm paramString) {}
      }
    } while (!Log.isLoggable("SafeDataBufferRef", 6));
    Log.e("SafeDataBufferRef", "Cannot parse byte[]", paramString);
    return paramList;
  }
  
  protected List<Integer> zza(String paramString, List<Integer> paramList)
  {
    paramString = zzc(paramString, null);
    if (paramString == null) {}
    do
    {
      for (;;)
      {
        return paramList;
        try
        {
          paramString = zzrd.zzx(paramString);
          if (paramString.zzaVF != null)
          {
            ArrayList localArrayList = new ArrayList(paramString.zzaVF.length);
            int i = 0;
            while (i < paramString.zzaVF.length)
            {
              localArrayList.add(Integer.valueOf(paramString.zzaVF[i]));
              i += 1;
            }
            return localArrayList;
          }
        }
        catch (zzrm paramString) {}
      }
    } while (!Log.isLoggable("SafeDataBufferRef", 6));
    Log.e("SafeDataBufferRef", "Cannot parse byte[]", paramString);
    return paramList;
  }
  
  protected float zzb(String paramString, float paramFloat)
  {
    float f = paramFloat;
    if (zzbV(paramString))
    {
      f = paramFloat;
      if (!zzbX(paramString)) {
        f = getFloat(paramString);
      }
    }
    return f;
  }
  
  protected List<String> zzb(String paramString, List<String> paramList)
  {
    paramString = zzc(paramString, null);
    if (paramString == null) {}
    do
    {
      for (;;)
      {
        return paramList;
        try
        {
          paramString = zzrd.zzx(paramString);
          if (paramString.zzaVE != null)
          {
            paramString = Arrays.asList(paramString.zzaVE);
            return paramString;
          }
        }
        catch (zzrm paramString) {}
      }
    } while (!Log.isLoggable("SafeDataBufferRef", 6));
    Log.e("SafeDataBufferRef", "Cannot parse byte[]", paramString);
    return paramList;
  }
  
  protected byte[] zzc(String paramString, byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte = paramArrayOfByte;
    if (zzbV(paramString))
    {
      arrayOfByte = paramArrayOfByte;
      if (!zzbX(paramString)) {
        arrayOfByte = getByteArray(paramString);
      }
    }
    return arrayOfByte;
  }
  
  protected boolean zzh(String paramString, boolean paramBoolean)
  {
    boolean bool = paramBoolean;
    if (zzbV(paramString))
    {
      bool = paramBoolean;
      if (!zzbX(paramString)) {
        bool = getBoolean(paramString);
      }
    }
    return bool;
  }
  
  protected int zzz(String paramString, int paramInt)
  {
    int i = paramInt;
    if (zzbV(paramString))
    {
      i = paramInt;
      if (!zzbX(paramString)) {
        i = getInteger(paramString);
      }
    }
    return i;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/internal/zzt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */