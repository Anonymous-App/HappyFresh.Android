package com.google.android.gms.internal;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;

public final class zzrg
{
  private final ByteBuffer zzaVT;
  
  private zzrg(ByteBuffer paramByteBuffer)
  {
    this.zzaVT = paramByteBuffer;
  }
  
  private zzrg(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this(ByteBuffer.wrap(paramArrayOfByte, paramInt1, paramInt2));
  }
  
  public static int zzA(int paramInt1, int paramInt2)
  {
    return zzkM(paramInt1) + zzkJ(paramInt2);
  }
  
  public static zzrg zzA(byte[] paramArrayOfByte)
  {
    return zzb(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public static int zzB(int paramInt1, int paramInt2)
  {
    return zzkM(paramInt1) + zzkK(paramInt2);
  }
  
  public static int zzC(byte[] paramArrayOfByte)
  {
    return zzkO(paramArrayOfByte.length) + paramArrayOfByte.length;
  }
  
  public static int zzY(long paramLong)
  {
    return zzab(paramLong);
  }
  
  public static int zzZ(long paramLong)
  {
    return zzab(zzad(paramLong));
  }
  
  private static int zza(CharSequence paramCharSequence)
  {
    int m = paramCharSequence.length();
    int i = 0;
    while ((i < m) && (paramCharSequence.charAt(i) < '')) {
      i += 1;
    }
    for (;;)
    {
      int k = i;
      int j;
      if (j < m)
      {
        k = paramCharSequence.charAt(j);
        if (k < 2048)
        {
          j += 1;
          i = (127 - k >>> 31) + i;
        }
        else
        {
          k = i + zza(paramCharSequence, j);
        }
      }
      else
      {
        if (k < m) {
          throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (k + 4294967296L));
        }
        return k;
        j = i;
        i = m;
      }
    }
  }
  
  private static int zza(CharSequence paramCharSequence, int paramInt)
  {
    int m = paramCharSequence.length();
    int i = 0;
    if (paramInt < m)
    {
      int n = paramCharSequence.charAt(paramInt);
      int j;
      if (n < 2048)
      {
        i += (127 - n >>> 31);
        j = paramInt;
      }
      for (;;)
      {
        paramInt = j + 1;
        break;
        int k = i + 2;
        j = paramInt;
        i = k;
        if (55296 <= n)
        {
          j = paramInt;
          i = k;
          if (n <= 57343)
          {
            if (Character.codePointAt(paramCharSequence, paramInt) < 65536) {
              throw new IllegalArgumentException("Unpaired surrogate at index " + paramInt);
            }
            j = paramInt + 1;
            i = k;
          }
        }
      }
    }
    return i;
  }
  
  private static int zza(CharSequence paramCharSequence, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int k = paramCharSequence.length();
    int j = 0;
    int m = paramInt1 + paramInt2;
    paramInt2 = j;
    while ((paramInt2 < k) && (paramInt2 + paramInt1 < m))
    {
      j = paramCharSequence.charAt(paramInt2);
      if (j >= 128) {
        break;
      }
      paramArrayOfByte[(paramInt1 + paramInt2)] = ((byte)j);
      paramInt2 += 1;
    }
    if (paramInt2 == k) {
      return paramInt1 + k;
    }
    paramInt1 += paramInt2;
    if (paramInt2 < k)
    {
      int i = paramCharSequence.charAt(paramInt2);
      if ((i < 128) && (paramInt1 < m))
      {
        j = paramInt1 + 1;
        paramArrayOfByte[paramInt1] = ((byte)i);
        paramInt1 = j;
      }
      for (;;)
      {
        paramInt2 += 1;
        break;
        if ((i < 2048) && (paramInt1 <= m - 2))
        {
          j = paramInt1 + 1;
          paramArrayOfByte[paramInt1] = ((byte)(i >>> 6 | 0x3C0));
          paramInt1 = j + 1;
          paramArrayOfByte[j] = ((byte)(i & 0x3F | 0x80));
        }
        else
        {
          int n;
          if (((i < 55296) || (57343 < i)) && (paramInt1 <= m - 3))
          {
            j = paramInt1 + 1;
            paramArrayOfByte[paramInt1] = ((byte)(i >>> 12 | 0x1E0));
            n = j + 1;
            paramArrayOfByte[j] = ((byte)(i >>> 6 & 0x3F | 0x80));
            paramInt1 = n + 1;
            paramArrayOfByte[n] = ((byte)(i & 0x3F | 0x80));
          }
          else
          {
            if (paramInt1 > m - 4) {
              break label442;
            }
            j = paramInt2;
            char c;
            if (paramInt2 + 1 != paramCharSequence.length())
            {
              paramInt2 += 1;
              c = paramCharSequence.charAt(paramInt2);
              if (!Character.isSurrogatePair(i, c)) {
                j = paramInt2;
              }
            }
            else
            {
              throw new IllegalArgumentException("Unpaired surrogate at index " + (j - 1));
            }
            j = Character.toCodePoint(i, c);
            n = paramInt1 + 1;
            paramArrayOfByte[paramInt1] = ((byte)(j >>> 18 | 0xF0));
            paramInt1 = n + 1;
            paramArrayOfByte[n] = ((byte)(j >>> 12 & 0x3F | 0x80));
            n = paramInt1 + 1;
            paramArrayOfByte[paramInt1] = ((byte)(j >>> 6 & 0x3F | 0x80));
            paramInt1 = n + 1;
            paramArrayOfByte[n] = ((byte)(j & 0x3F | 0x80));
          }
        }
      }
      label442:
      throw new ArrayIndexOutOfBoundsException("Failed writing " + i + " at index " + paramInt1);
    }
    return paramInt1;
  }
  
  private static void zza(CharSequence paramCharSequence, ByteBuffer paramByteBuffer)
  {
    if (paramByteBuffer.isReadOnly()) {
      throw new ReadOnlyBufferException();
    }
    if (paramByteBuffer.hasArray()) {
      try
      {
        paramByteBuffer.position(zza(paramCharSequence, paramByteBuffer.array(), paramByteBuffer.arrayOffset() + paramByteBuffer.position(), paramByteBuffer.remaining()) - paramByteBuffer.arrayOffset());
        return;
      }
      catch (ArrayIndexOutOfBoundsException paramCharSequence)
      {
        paramByteBuffer = new BufferOverflowException();
        paramByteBuffer.initCause(paramCharSequence);
        throw paramByteBuffer;
      }
    }
    zzb(paramCharSequence, paramByteBuffer);
  }
  
  public static int zzab(long paramLong)
  {
    if ((0xFFFFFFFFFFFFFF80 & paramLong) == 0L) {
      return 1;
    }
    if ((0xFFFFFFFFFFFFC000 & paramLong) == 0L) {
      return 2;
    }
    if ((0xFFFFFFFFFFE00000 & paramLong) == 0L) {
      return 3;
    }
    if ((0xFFFFFFFFF0000000 & paramLong) == 0L) {
      return 4;
    }
    if ((0xFFFFFFF800000000 & paramLong) == 0L) {
      return 5;
    }
    if ((0xFFFFFC0000000000 & paramLong) == 0L) {
      return 6;
    }
    if ((0xFFFE000000000000 & paramLong) == 0L) {
      return 7;
    }
    if ((0xFF00000000000000 & paramLong) == 0L) {
      return 8;
    }
    if ((0x8000000000000000 & paramLong) == 0L) {
      return 9;
    }
    return 10;
  }
  
  public static long zzad(long paramLong)
  {
    return paramLong << 1 ^ paramLong >> 63;
  }
  
  public static int zzas(boolean paramBoolean)
  {
    return 1;
  }
  
  public static int zzb(int paramInt, double paramDouble)
  {
    return zzkM(paramInt) + zzi(paramDouble);
  }
  
  public static int zzb(int paramInt, zzrn paramzzrn)
  {
    return zzkM(paramInt) * 2 + zzd(paramzzrn);
  }
  
  public static int zzb(int paramInt, byte[] paramArrayOfByte)
  {
    return zzkM(paramInt) + zzC(paramArrayOfByte);
  }
  
  public static zzrg zzb(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return new zzrg(paramArrayOfByte, paramInt1, paramInt2);
  }
  
  private static void zzb(CharSequence paramCharSequence, ByteBuffer paramByteBuffer)
  {
    int m = paramCharSequence.length();
    int j = 0;
    if (j < m)
    {
      int i = paramCharSequence.charAt(j);
      if (i < 128) {
        paramByteBuffer.put((byte)i);
      }
      for (;;)
      {
        j += 1;
        break;
        if (i < 2048)
        {
          paramByteBuffer.put((byte)(i >>> 6 | 0x3C0));
          paramByteBuffer.put((byte)(i & 0x3F | 0x80));
        }
        else if ((i < 55296) || (57343 < i))
        {
          paramByteBuffer.put((byte)(i >>> 12 | 0x1E0));
          paramByteBuffer.put((byte)(i >>> 6 & 0x3F | 0x80));
          paramByteBuffer.put((byte)(i & 0x3F | 0x80));
        }
        else
        {
          int k = j;
          char c;
          if (j + 1 != paramCharSequence.length())
          {
            j += 1;
            c = paramCharSequence.charAt(j);
            if (!Character.isSurrogatePair(i, c)) {
              k = j;
            }
          }
          else
          {
            throw new IllegalArgumentException("Unpaired surrogate at index " + (k - 1));
          }
          k = Character.toCodePoint(i, c);
          paramByteBuffer.put((byte)(k >>> 18 | 0xF0));
          paramByteBuffer.put((byte)(k >>> 12 & 0x3F | 0x80));
          paramByteBuffer.put((byte)(k >>> 6 & 0x3F | 0x80));
          paramByteBuffer.put((byte)(k & 0x3F | 0x80));
        }
      }
    }
  }
  
  public static int zzc(int paramInt, float paramFloat)
  {
    return zzkM(paramInt) + zzj(paramFloat);
  }
  
  public static int zzc(int paramInt, zzrn paramzzrn)
  {
    return zzkM(paramInt) + zze(paramzzrn);
  }
  
  public static int zzc(int paramInt, boolean paramBoolean)
  {
    return zzkM(paramInt) + zzas(paramBoolean);
  }
  
  public static int zzd(int paramInt, long paramLong)
  {
    return zzkM(paramInt) + zzY(paramLong);
  }
  
  public static int zzd(zzrn paramzzrn)
  {
    return paramzzrn.zzBV();
  }
  
  public static int zze(int paramInt, long paramLong)
  {
    return zzkM(paramInt) + zzZ(paramLong);
  }
  
  public static int zze(zzrn paramzzrn)
  {
    int i = paramzzrn.zzBV();
    return i + zzkO(i);
  }
  
  public static int zzfj(String paramString)
  {
    int i = zza(paramString);
    return i + zzkO(i);
  }
  
  public static int zzi(double paramDouble)
  {
    return 8;
  }
  
  public static int zzj(float paramFloat)
  {
    return 4;
  }
  
  public static int zzk(int paramInt, String paramString)
  {
    return zzkM(paramInt) + zzfj(paramString);
  }
  
  public static int zzkJ(int paramInt)
  {
    if (paramInt >= 0) {
      return zzkO(paramInt);
    }
    return 10;
  }
  
  public static int zzkK(int paramInt)
  {
    return zzkO(zzkQ(paramInt));
  }
  
  public static int zzkM(int paramInt)
  {
    return zzkO(zzrq.zzD(paramInt, 0));
  }
  
  public static int zzkO(int paramInt)
  {
    if ((paramInt & 0xFFFFFF80) == 0) {
      return 1;
    }
    if ((paramInt & 0xC000) == 0) {
      return 2;
    }
    if ((0xFFE00000 & paramInt) == 0) {
      return 3;
    }
    if ((0xF0000000 & paramInt) == 0) {
      return 4;
    }
    return 5;
  }
  
  public static int zzkQ(int paramInt)
  {
    return paramInt << 1 ^ paramInt >> 31;
  }
  
  public void zzB(byte[] paramArrayOfByte)
    throws IOException
  {
    zzkN(paramArrayOfByte.length);
    zzD(paramArrayOfByte);
  }
  
  public int zzBG()
  {
    return this.zzaVT.remaining();
  }
  
  public void zzBH()
  {
    if (zzBG() != 0) {
      throw new IllegalStateException("Did not write as much data as expected.");
    }
  }
  
  public void zzC(int paramInt1, int paramInt2)
    throws IOException
  {
    zzkN(zzrq.zzD(paramInt1, paramInt2));
  }
  
  public void zzD(byte[] paramArrayOfByte)
    throws IOException
  {
    zzc(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public void zzW(long paramLong)
    throws IOException
  {
    zzaa(paramLong);
  }
  
  public void zzX(long paramLong)
    throws IOException
  {
    zzaa(zzad(paramLong));
  }
  
  public void zza(int paramInt, double paramDouble)
    throws IOException
  {
    zzC(paramInt, 1);
    zzh(paramDouble);
  }
  
  public void zza(int paramInt, zzrn paramzzrn)
    throws IOException
  {
    zzC(paramInt, 2);
    zzc(paramzzrn);
  }
  
  public void zza(int paramInt, byte[] paramArrayOfByte)
    throws IOException
  {
    zzC(paramInt, 2);
    zzB(paramArrayOfByte);
  }
  
  public void zzaa(long paramLong)
    throws IOException
  {
    for (;;)
    {
      if ((0xFFFFFFFFFFFFFF80 & paramLong) == 0L)
      {
        zzkL((int)paramLong);
        return;
      }
      zzkL((int)paramLong & 0x7F | 0x80);
      paramLong >>>= 7;
    }
  }
  
  public void zzac(long paramLong)
    throws IOException
  {
    zzkL((int)paramLong & 0xFF);
    zzkL((int)(paramLong >> 8) & 0xFF);
    zzkL((int)(paramLong >> 16) & 0xFF);
    zzkL((int)(paramLong >> 24) & 0xFF);
    zzkL((int)(paramLong >> 32) & 0xFF);
    zzkL((int)(paramLong >> 40) & 0xFF);
    zzkL((int)(paramLong >> 48) & 0xFF);
    zzkL((int)(paramLong >> 56) & 0xFF);
  }
  
  public void zzar(boolean paramBoolean)
    throws IOException
  {
    if (paramBoolean) {}
    for (int i = 1;; i = 0)
    {
      zzkL(i);
      return;
    }
  }
  
  public void zzb(byte paramByte)
    throws IOException
  {
    if (!this.zzaVT.hasRemaining()) {
      throw new zza(this.zzaVT.position(), this.zzaVT.limit());
    }
    this.zzaVT.put(paramByte);
  }
  
  public void zzb(int paramInt, float paramFloat)
    throws IOException
  {
    zzC(paramInt, 5);
    zzi(paramFloat);
  }
  
  public void zzb(int paramInt, long paramLong)
    throws IOException
  {
    zzC(paramInt, 0);
    zzW(paramLong);
  }
  
  public void zzb(int paramInt, String paramString)
    throws IOException
  {
    zzC(paramInt, 2);
    zzfi(paramString);
  }
  
  public void zzb(int paramInt, boolean paramBoolean)
    throws IOException
  {
    zzC(paramInt, 0);
    zzar(paramBoolean);
  }
  
  public void zzb(zzrn paramzzrn)
    throws IOException
  {
    paramzzrn.zza(this);
  }
  
  public void zzc(int paramInt, long paramLong)
    throws IOException
  {
    zzC(paramInt, 0);
    zzX(paramLong);
  }
  
  public void zzc(zzrn paramzzrn)
    throws IOException
  {
    zzkN(paramzzrn.zzBU());
    paramzzrn.zza(this);
  }
  
  public void zzc(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (this.zzaVT.remaining() >= paramInt2)
    {
      this.zzaVT.put(paramArrayOfByte, paramInt1, paramInt2);
      return;
    }
    throw new zza(this.zzaVT.position(), this.zzaVT.limit());
  }
  
  public void zzfi(String paramString)
    throws IOException
  {
    try
    {
      int i = zzkO(paramString.length());
      if (i == zzkO(paramString.length() * 3))
      {
        int j = this.zzaVT.position();
        this.zzaVT.position(j + i);
        zza(paramString, this.zzaVT);
        int k = this.zzaVT.position();
        this.zzaVT.position(j);
        zzkN(k - j - i);
        this.zzaVT.position(k);
        return;
      }
      zzkN(zza(paramString));
      zza(paramString, this.zzaVT);
      return;
    }
    catch (BufferOverflowException paramString)
    {
      throw new zza(this.zzaVT.position(), this.zzaVT.limit());
    }
  }
  
  public void zzh(double paramDouble)
    throws IOException
  {
    zzac(Double.doubleToLongBits(paramDouble));
  }
  
  public void zzi(float paramFloat)
    throws IOException
  {
    zzkP(Float.floatToIntBits(paramFloat));
  }
  
  public void zzkH(int paramInt)
    throws IOException
  {
    if (paramInt >= 0)
    {
      zzkN(paramInt);
      return;
    }
    zzaa(paramInt);
  }
  
  public void zzkI(int paramInt)
    throws IOException
  {
    zzkN(zzkQ(paramInt));
  }
  
  public void zzkL(int paramInt)
    throws IOException
  {
    zzb((byte)paramInt);
  }
  
  public void zzkN(int paramInt)
    throws IOException
  {
    for (;;)
    {
      if ((paramInt & 0xFFFFFF80) == 0)
      {
        zzkL(paramInt);
        return;
      }
      zzkL(paramInt & 0x7F | 0x80);
      paramInt >>>= 7;
    }
  }
  
  public void zzkP(int paramInt)
    throws IOException
  {
    zzkL(paramInt & 0xFF);
    zzkL(paramInt >> 8 & 0xFF);
    zzkL(paramInt >> 16 & 0xFF);
    zzkL(paramInt >> 24 & 0xFF);
  }
  
  public void zzy(int paramInt1, int paramInt2)
    throws IOException
  {
    zzC(paramInt1, 0);
    zzkH(paramInt2);
  }
  
  public void zzz(int paramInt1, int paramInt2)
    throws IOException
  {
    zzC(paramInt1, 0);
    zzkI(paramInt2);
  }
  
  public static class zza
    extends IOException
  {
    zza(int paramInt1, int paramInt2)
    {
      super();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzrg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */