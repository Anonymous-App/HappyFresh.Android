package com.optimizely.Core;

import android.support.annotation.NonNull;

public class MurmurHash3
{
  public static int murmurhash3_x86_32(@NonNull CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt3;
    int i4 = paramInt1 + paramInt2;
    int m = 0;
    int k = 0;
    paramInt3 = 0;
    if (paramInt1 < i4)
    {
      int j = paramInt1 + 1;
      paramInt2 = paramCharSequence.charAt(paramInt1);
      label45:
      int i1;
      int n;
      if (paramInt2 < 128)
      {
        paramInt1 = 8;
        int i3 = m | paramInt2 << k;
        int i2 = k + paramInt1;
        i1 = i;
        m = i3;
        n = paramInt3;
        k = i2;
        if (i2 >= 32)
        {
          k = i3 * -862048943;
          i ^= (k << 15 | k >>> 17) * 461845907;
          i1 = (i << 13 | i >>> 19) * 5 - 430675100;
          k = i2 - 32;
          if (k == 0) {
            break label347;
          }
        }
      }
      label347:
      for (m = paramInt2 >>> paramInt1 - k;; m = 0)
      {
        n = paramInt3 + 4;
        paramInt1 = j;
        i = i1;
        paramInt3 = n;
        break;
        if (paramInt2 < 2048)
        {
          paramInt2 = paramInt2 >> 6 | 0xC0 | (paramInt2 & 0x3F | 0x80) << 8;
          paramInt1 = 16;
          break label45;
        }
        if ((paramInt2 < 55296) || (paramInt2 > 57343) || (j >= i4))
        {
          paramInt2 = paramInt2 >> 12 | 0xE0 | (paramInt2 >> 6 & 0x3F | 0x80) << 8 | (paramInt2 & 0x3F | 0x80) << 16;
          paramInt1 = 24;
          break label45;
        }
        paramInt1 = (paramInt2 - 55232 << 10) + (paramCharSequence.charAt(j) & 0x3FF);
        paramInt2 = (paramInt1 >> 18 | 0xF0) & 0xFF | (paramInt1 >> 12 & 0x3F | 0x80) << 8 | (paramInt1 >> 6 & 0x3F | 0x80) << 16 | (paramInt1 & 0x3F | 0x80) << 24;
        paramInt1 = 32;
        j += 1;
        break label45;
      }
    }
    paramInt2 = i;
    paramInt1 = paramInt3;
    if (k > 0)
    {
      paramInt1 = paramInt3 + (k >> 3);
      paramInt2 = m * -862048943;
      paramInt2 = i ^ (paramInt2 << 15 | paramInt2 >>> 17) * 461845907;
    }
    paramInt1 = paramInt2 ^ paramInt1;
    paramInt1 = (paramInt1 ^ paramInt1 >>> 16) * -2048144789;
    paramInt1 = (paramInt1 ^ paramInt1 >>> 13) * -1028477387;
    return paramInt1 ^ paramInt1 >>> 16;
  }
  
  public static int murmurhash3_x86_32(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt3;
    int j = paramInt1 + (paramInt2 & 0xFFFFFFFC);
    paramInt3 = paramInt1;
    paramInt1 = i;
    while (paramInt3 < j)
    {
      i = (paramArrayOfByte[paramInt3] & 0xFF | (paramArrayOfByte[(paramInt3 + 1)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt3 + 2)] & 0xFF) << 16 | paramArrayOfByte[(paramInt3 + 3)] << 24) * -862048943;
      paramInt1 ^= (i << 15 | i >>> 17) * 461845907;
      paramInt1 = (paramInt1 << 13 | paramInt1 >>> 19) * 5 - 430675100;
      paramInt3 += 4;
    }
    i = 0;
    paramInt3 = 0;
    switch (paramInt2 & 0x3)
    {
    }
    for (;;)
    {
      paramInt1 ^= paramInt2;
      paramInt1 = (paramInt1 ^ paramInt1 >>> 16) * -2048144789;
      paramInt1 = (paramInt1 ^ paramInt1 >>> 13) * -1028477387;
      return paramInt1 ^ paramInt1 >>> 16;
      paramInt3 = (paramArrayOfByte[(j + 2)] & 0xFF) << 16;
      i = paramInt3 | (paramArrayOfByte[(j + 1)] & 0xFF) << 8;
      paramInt3 = (i | paramArrayOfByte[j] & 0xFF) * -862048943;
      paramInt1 ^= (paramInt3 << 15 | paramInt3 >>> 17) * 461845907;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Core/MurmurHash3.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */