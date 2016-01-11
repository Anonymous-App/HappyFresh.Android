package com.google.android.gms.internal;

import java.io.IOException;

public final class zzrf
{
  private final byte[] buffer;
  private int zzaVK;
  private int zzaVL;
  private int zzaVM;
  private int zzaVN;
  private int zzaVO;
  private int zzaVP = Integer.MAX_VALUE;
  private int zzaVQ;
  private int zzaVR = 64;
  private int zzaVS = 67108864;
  
  private zzrf(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.buffer = paramArrayOfByte;
    this.zzaVK = paramInt1;
    this.zzaVL = (paramInt1 + paramInt2);
    this.zzaVN = paramInt1;
  }
  
  private void zzBC()
  {
    this.zzaVL += this.zzaVM;
    int i = this.zzaVL;
    if (i > this.zzaVP)
    {
      this.zzaVM = (i - this.zzaVP);
      this.zzaVL -= this.zzaVM;
      return;
    }
    this.zzaVM = 0;
  }
  
  public static long zzV(long paramLong)
  {
    return paramLong >>> 1 ^ -(1L & paramLong);
  }
  
  public static zzrf zza(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return new zzrf(paramArrayOfByte, paramInt1, paramInt2);
  }
  
  public static int zzkB(int paramInt)
  {
    return paramInt >>> 1 ^ -(paramInt & 0x1);
  }
  
  public static zzrf zzz(byte[] paramArrayOfByte)
  {
    return zza(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public int getPosition()
  {
    return this.zzaVN - this.zzaVK;
  }
  
  public byte[] readBytes()
    throws IOException
  {
    int i = zzBy();
    if ((i <= this.zzaVL - this.zzaVN) && (i > 0))
    {
      byte[] arrayOfByte = new byte[i];
      System.arraycopy(this.buffer, this.zzaVN, arrayOfByte, 0, i);
      this.zzaVN = (i + this.zzaVN);
      return arrayOfByte;
    }
    return zzkF(i);
  }
  
  public double readDouble()
    throws IOException
  {
    return Double.longBitsToDouble(zzBB());
  }
  
  public float readFloat()
    throws IOException
  {
    return Float.intBitsToFloat(zzBA());
  }
  
  public String readString()
    throws IOException
  {
    int i = zzBy();
    if ((i <= this.zzaVL - this.zzaVN) && (i > 0))
    {
      String str = new String(this.buffer, this.zzaVN, i, "UTF-8");
      this.zzaVN = (i + this.zzaVN);
      return str;
    }
    return new String(zzkF(i), "UTF-8");
  }
  
  public int zzBA()
    throws IOException
  {
    return zzBF() & 0xFF | (zzBF() & 0xFF) << 8 | (zzBF() & 0xFF) << 16 | (zzBF() & 0xFF) << 24;
  }
  
  public long zzBB()
    throws IOException
  {
    int i = zzBF();
    int j = zzBF();
    int k = zzBF();
    int m = zzBF();
    int n = zzBF();
    int i1 = zzBF();
    int i2 = zzBF();
    int i3 = zzBF();
    long l = i;
    return (j & 0xFF) << 8 | l & 0xFF | (k & 0xFF) << 16 | (m & 0xFF) << 24 | (n & 0xFF) << 32 | (i1 & 0xFF) << 40 | (i2 & 0xFF) << 48 | (i3 & 0xFF) << 56;
  }
  
  public int zzBD()
  {
    if (this.zzaVP == Integer.MAX_VALUE) {
      return -1;
    }
    int i = this.zzaVN;
    return this.zzaVP - i;
  }
  
  public boolean zzBE()
  {
    return this.zzaVN == this.zzaVL;
  }
  
  public byte zzBF()
    throws IOException
  {
    if (this.zzaVN == this.zzaVL) {
      throw zzrm.zzBN();
    }
    byte[] arrayOfByte = this.buffer;
    int i = this.zzaVN;
    this.zzaVN = (i + 1);
    return arrayOfByte[i];
  }
  
  public int zzBr()
    throws IOException
  {
    if (zzBE())
    {
      this.zzaVO = 0;
      return 0;
    }
    this.zzaVO = zzBy();
    if (this.zzaVO == 0) {
      throw zzrm.zzBQ();
    }
    return this.zzaVO;
  }
  
  public void zzBs()
    throws IOException
  {
    int i;
    do
    {
      i = zzBr();
    } while ((i != 0) && (zzkA(i)));
  }
  
  public long zzBt()
    throws IOException
  {
    return zzBz();
  }
  
  public int zzBu()
    throws IOException
  {
    return zzBy();
  }
  
  public boolean zzBv()
    throws IOException
  {
    return zzBy() != 0;
  }
  
  public int zzBw()
    throws IOException
  {
    return zzkB(zzBy());
  }
  
  public long zzBx()
    throws IOException
  {
    return zzV(zzBz());
  }
  
  public int zzBy()
    throws IOException
  {
    int i = zzBF();
    if (i >= 0) {}
    int k;
    do
    {
      return i;
      i &= 0x7F;
      j = zzBF();
      if (j >= 0) {
        return i | j << 7;
      }
      i |= (j & 0x7F) << 7;
      j = zzBF();
      if (j >= 0) {
        return i | j << 14;
      }
      i |= (j & 0x7F) << 14;
      k = zzBF();
      if (k >= 0) {
        return i | k << 21;
      }
      j = zzBF();
      k = i | (k & 0x7F) << 21 | j << 28;
      i = k;
    } while (j >= 0);
    int j = 0;
    for (;;)
    {
      if (j >= 5) {
        break label133;
      }
      i = k;
      if (zzBF() >= 0) {
        break;
      }
      j += 1;
    }
    label133:
    throw zzrm.zzBP();
  }
  
  public long zzBz()
    throws IOException
  {
    int i = 0;
    long l = 0L;
    while (i < 64)
    {
      int j = zzBF();
      l |= (j & 0x7F) << i;
      if ((j & 0x80) == 0) {
        return l;
      }
      i += 7;
    }
    throw zzrm.zzBP();
  }
  
  public void zza(zzrn paramzzrn)
    throws IOException
  {
    int i = zzBy();
    if (this.zzaVQ >= this.zzaVR) {
      throw zzrm.zzBT();
    }
    i = zzkC(i);
    this.zzaVQ += 1;
    paramzzrn.zzb(this);
    zzkz(0);
    this.zzaVQ -= 1;
    zzkD(i);
  }
  
  public void zza(zzrn paramzzrn, int paramInt)
    throws IOException
  {
    if (this.zzaVQ >= this.zzaVR) {
      throw zzrm.zzBT();
    }
    this.zzaVQ += 1;
    paramzzrn.zzb(this);
    zzkz(zzrq.zzD(paramInt, 4));
    this.zzaVQ -= 1;
  }
  
  public boolean zzkA(int paramInt)
    throws IOException
  {
    switch (zzrq.zzkU(paramInt))
    {
    default: 
      throw zzrm.zzBS();
    case 0: 
      zzBu();
      return true;
    case 1: 
      zzBB();
      return true;
    case 2: 
      zzkG(zzBy());
      return true;
    case 3: 
      zzBs();
      zzkz(zzrq.zzD(zzrq.zzkV(paramInt), 4));
      return true;
    case 4: 
      return false;
    }
    zzBA();
    return true;
  }
  
  public int zzkC(int paramInt)
    throws zzrm
  {
    if (paramInt < 0) {
      throw zzrm.zzBO();
    }
    paramInt = this.zzaVN + paramInt;
    int i = this.zzaVP;
    if (paramInt > i) {
      throw zzrm.zzBN();
    }
    this.zzaVP = paramInt;
    zzBC();
    return i;
  }
  
  public void zzkD(int paramInt)
  {
    this.zzaVP = paramInt;
    zzBC();
  }
  
  public void zzkE(int paramInt)
  {
    if (paramInt > this.zzaVN - this.zzaVK) {
      throw new IllegalArgumentException("Position " + paramInt + " is beyond current " + (this.zzaVN - this.zzaVK));
    }
    if (paramInt < 0) {
      throw new IllegalArgumentException("Bad position " + paramInt);
    }
    this.zzaVN = (this.zzaVK + paramInt);
  }
  
  public byte[] zzkF(int paramInt)
    throws IOException
  {
    if (paramInt < 0) {
      throw zzrm.zzBO();
    }
    if (this.zzaVN + paramInt > this.zzaVP)
    {
      zzkG(this.zzaVP - this.zzaVN);
      throw zzrm.zzBN();
    }
    if (paramInt <= this.zzaVL - this.zzaVN)
    {
      byte[] arrayOfByte = new byte[paramInt];
      System.arraycopy(this.buffer, this.zzaVN, arrayOfByte, 0, paramInt);
      this.zzaVN += paramInt;
      return arrayOfByte;
    }
    throw zzrm.zzBN();
  }
  
  public void zzkG(int paramInt)
    throws IOException
  {
    if (paramInt < 0) {
      throw zzrm.zzBO();
    }
    if (this.zzaVN + paramInt > this.zzaVP)
    {
      zzkG(this.zzaVP - this.zzaVN);
      throw zzrm.zzBN();
    }
    if (paramInt <= this.zzaVL - this.zzaVN)
    {
      this.zzaVN += paramInt;
      return;
    }
    throw zzrm.zzBN();
  }
  
  public void zzkz(int paramInt)
    throws zzrm
  {
    if (this.zzaVO != paramInt) {
      throw zzrm.zzBR();
    }
  }
  
  public byte[] zzx(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0) {
      return zzrq.zzaWo;
    }
    byte[] arrayOfByte = new byte[paramInt2];
    int i = this.zzaVK;
    System.arraycopy(this.buffer, i + paramInt1, arrayOfByte, 0, paramInt2);
    return arrayOfByte;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzrf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */