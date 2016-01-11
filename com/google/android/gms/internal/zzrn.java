package com.google.android.gms.internal;

import java.io.IOException;

public abstract class zzrn
{
  protected volatile int zzaWf = -1;
  
  public static final <T extends zzrn> T zza(T paramT, byte[] paramArrayOfByte)
    throws zzrm
  {
    return zzb(paramT, paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public static final void zza(zzrn paramzzrn, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    try
    {
      paramArrayOfByte = zzrg.zzb(paramArrayOfByte, paramInt1, paramInt2);
      paramzzrn.zza(paramArrayOfByte);
      paramArrayOfByte.zzBH();
      return;
    }
    catch (IOException paramzzrn)
    {
      throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", paramzzrn);
    }
  }
  
  public static final <T extends zzrn> T zzb(T paramT, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws zzrm
  {
    try
    {
      paramArrayOfByte = zzrf.zza(paramArrayOfByte, paramInt1, paramInt2);
      paramT.zzb(paramArrayOfByte);
      paramArrayOfByte.zzkz(0);
      return paramT;
    }
    catch (zzrm paramT)
    {
      throw paramT;
    }
    catch (IOException paramT)
    {
      throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).");
    }
  }
  
  public static final byte[] zzf(zzrn paramzzrn)
  {
    byte[] arrayOfByte = new byte[paramzzrn.zzBV()];
    zza(paramzzrn, arrayOfByte, 0, arrayOfByte.length);
    return arrayOfByte;
  }
  
  public String toString()
  {
    return zzro.zzg(this);
  }
  
  protected int zzB()
  {
    return 0;
  }
  
  public zzrn zzBK()
    throws CloneNotSupportedException
  {
    return (zzrn)super.clone();
  }
  
  public int zzBU()
  {
    if (this.zzaWf < 0) {
      zzBV();
    }
    return this.zzaWf;
  }
  
  public int zzBV()
  {
    int i = zzB();
    this.zzaWf = i;
    return i;
  }
  
  public void zza(zzrg paramzzrg)
    throws IOException
  {}
  
  public abstract zzrn zzb(zzrf paramzzrf)
    throws IOException;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzrn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */