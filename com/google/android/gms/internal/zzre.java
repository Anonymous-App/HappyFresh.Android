package com.google.android.gms.internal;

public class zzre
{
  private final byte[] zzaVH = new byte['Ā'];
  private int zzaVI;
  private int zzaVJ;
  
  public zzre(byte[] paramArrayOfByte)
  {
    int j = 0;
    while (j < 256)
    {
      this.zzaVH[j] = ((byte)j);
      j += 1;
    }
    int k = 0;
    j = 0;
    while (j < 256)
    {
      k = k + this.zzaVH[j] + paramArrayOfByte[(j % paramArrayOfByte.length)] & 0xFF;
      int i = this.zzaVH[j];
      this.zzaVH[j] = this.zzaVH[k];
      this.zzaVH[k] = i;
      j += 1;
    }
    this.zzaVI = 0;
    this.zzaVJ = 0;
  }
  
  public void zzy(byte[] paramArrayOfByte)
  {
    int m = this.zzaVI;
    int k = this.zzaVJ;
    int j = 0;
    while (j < paramArrayOfByte.length)
    {
      m = m + 1 & 0xFF;
      k = k + this.zzaVH[m] & 0xFF;
      int i = this.zzaVH[m];
      this.zzaVH[m] = this.zzaVH[k];
      this.zzaVH[k] = i;
      paramArrayOfByte[j] = ((byte)(paramArrayOfByte[j] ^ this.zzaVH[(this.zzaVH[m] + this.zzaVH[k] & 0xFF)]));
      j += 1;
    }
    this.zzaVI = m;
    this.zzaVJ = k;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzre.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */