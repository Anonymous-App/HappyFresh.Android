package com.google.android.gms.internal;

class zzrj
  implements Cloneable
{
  private static final zzrk zzaVX = new zzrk();
  private int mSize;
  private boolean zzaVY = false;
  private int[] zzaVZ;
  private zzrk[] zzaWa;
  
  public zzrj()
  {
    this(10);
  }
  
  public zzrj(int paramInt)
  {
    paramInt = idealIntArraySize(paramInt);
    this.zzaVZ = new int[paramInt];
    this.zzaWa = new zzrk[paramInt];
    this.mSize = 0;
  }
  
  private void gc()
  {
    int m = this.mSize;
    int[] arrayOfInt = this.zzaVZ;
    zzrk[] arrayOfzzrk = this.zzaWa;
    int i = 0;
    int k;
    for (int j = 0; i < m; j = k)
    {
      zzrk localzzrk = arrayOfzzrk[i];
      k = j;
      if (localzzrk != zzaVX)
      {
        if (i != j)
        {
          arrayOfInt[j] = arrayOfInt[i];
          arrayOfzzrk[j] = localzzrk;
          arrayOfzzrk[i] = null;
        }
        k = j + 1;
      }
      i += 1;
    }
    this.zzaVY = false;
    this.mSize = j;
  }
  
  private int idealByteArraySize(int paramInt)
  {
    int i = 4;
    for (;;)
    {
      int j = paramInt;
      if (i < 32)
      {
        if (paramInt <= (1 << i) - 12) {
          j = (1 << i) - 12;
        }
      }
      else {
        return j;
      }
      i += 1;
    }
  }
  
  private int idealIntArraySize(int paramInt)
  {
    return idealByteArraySize(paramInt * 4) / 4;
  }
  
  private boolean zza(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt)
  {
    int i = 0;
    while (i < paramInt)
    {
      if (paramArrayOfInt1[i] != paramArrayOfInt2[i]) {
        return false;
      }
      i += 1;
    }
    return true;
  }
  
  private boolean zza(zzrk[] paramArrayOfzzrk1, zzrk[] paramArrayOfzzrk2, int paramInt)
  {
    int i = 0;
    while (i < paramInt)
    {
      if (!paramArrayOfzzrk1[i].equals(paramArrayOfzzrk2[i])) {
        return false;
      }
      i += 1;
    }
    return true;
  }
  
  private int zzkT(int paramInt)
  {
    int i = 0;
    int j = this.mSize - 1;
    while (i <= j)
    {
      int k = i + j >>> 1;
      int m = this.zzaVZ[k];
      if (m < paramInt) {
        i = k + 1;
      } else if (m > paramInt) {
        j = k - 1;
      } else {
        return k;
      }
    }
    return i ^ 0xFFFFFFFF;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {}
    do
    {
      return true;
      if (!(paramObject instanceof zzrj)) {
        return false;
      }
      paramObject = (zzrj)paramObject;
      if (size() != ((zzrj)paramObject).size()) {
        return false;
      }
    } while ((zza(this.zzaVZ, ((zzrj)paramObject).zzaVZ, this.mSize)) && (zza(this.zzaWa, ((zzrj)paramObject).zzaWa, this.mSize)));
    return false;
  }
  
  public int hashCode()
  {
    if (this.zzaVY) {
      gc();
    }
    int j = 17;
    int i = 0;
    while (i < this.mSize)
    {
      j = (j * 31 + this.zzaVZ[i]) * 31 + this.zzaWa[i].hashCode();
      i += 1;
    }
    return j;
  }
  
  public boolean isEmpty()
  {
    return size() == 0;
  }
  
  public int size()
  {
    if (this.zzaVY) {
      gc();
    }
    return this.mSize;
  }
  
  public final zzrj zzBL()
  {
    int i = 0;
    int j = size();
    zzrj localzzrj = new zzrj(j);
    System.arraycopy(this.zzaVZ, 0, localzzrj.zzaVZ, 0, j);
    while (i < j)
    {
      if (this.zzaWa[i] != null) {
        localzzrj.zzaWa[i] = this.zzaWa[i].zzBM();
      }
      i += 1;
    }
    localzzrj.mSize = j;
    return localzzrj;
  }
  
  public void zza(int paramInt, zzrk paramzzrk)
  {
    int i = zzkT(paramInt);
    if (i >= 0)
    {
      this.zzaWa[i] = paramzzrk;
      return;
    }
    int j = i ^ 0xFFFFFFFF;
    if ((j < this.mSize) && (this.zzaWa[j] == zzaVX))
    {
      this.zzaVZ[j] = paramInt;
      this.zzaWa[j] = paramzzrk;
      return;
    }
    i = j;
    if (this.zzaVY)
    {
      i = j;
      if (this.mSize >= this.zzaVZ.length)
      {
        gc();
        i = zzkT(paramInt) ^ 0xFFFFFFFF;
      }
    }
    if (this.mSize >= this.zzaVZ.length)
    {
      j = idealIntArraySize(this.mSize + 1);
      int[] arrayOfInt = new int[j];
      zzrk[] arrayOfzzrk = new zzrk[j];
      System.arraycopy(this.zzaVZ, 0, arrayOfInt, 0, this.zzaVZ.length);
      System.arraycopy(this.zzaWa, 0, arrayOfzzrk, 0, this.zzaWa.length);
      this.zzaVZ = arrayOfInt;
      this.zzaWa = arrayOfzzrk;
    }
    if (this.mSize - i != 0)
    {
      System.arraycopy(this.zzaVZ, i, this.zzaVZ, i + 1, this.mSize - i);
      System.arraycopy(this.zzaWa, i, this.zzaWa, i + 1, this.mSize - i);
    }
    this.zzaVZ[i] = paramInt;
    this.zzaWa[i] = paramzzrk;
    this.mSize += 1;
  }
  
  public zzrk zzkR(int paramInt)
  {
    paramInt = zzkT(paramInt);
    if ((paramInt < 0) || (this.zzaWa[paramInt] == zzaVX)) {
      return null;
    }
    return this.zzaWa[paramInt];
  }
  
  public zzrk zzkS(int paramInt)
  {
    if (this.zzaVY) {
      gc();
    }
    return this.zzaWa[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzrj.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */