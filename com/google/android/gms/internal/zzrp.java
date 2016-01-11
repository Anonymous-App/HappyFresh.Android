package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Arrays;

final class zzrp
{
  final int tag;
  final byte[] zzaWg;
  
  zzrp(int paramInt, byte[] paramArrayOfByte)
  {
    this.tag = paramInt;
    this.zzaWg = paramArrayOfByte;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {}
    do
    {
      return true;
      if (!(paramObject instanceof zzrp)) {
        return false;
      }
      paramObject = (zzrp)paramObject;
    } while ((this.tag == ((zzrp)paramObject).tag) && (Arrays.equals(this.zzaWg, ((zzrp)paramObject).zzaWg)));
    return false;
  }
  
  public int hashCode()
  {
    return (this.tag + 527) * 31 + Arrays.hashCode(this.zzaWg);
  }
  
  int zzB()
  {
    return 0 + zzrg.zzkO(this.tag) + this.zzaWg.length;
  }
  
  void zza(zzrg paramzzrg)
    throws IOException
  {
    paramzzrg.zzkN(this.tag);
    paramzzrg.zzD(this.zzaWg);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzrp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */