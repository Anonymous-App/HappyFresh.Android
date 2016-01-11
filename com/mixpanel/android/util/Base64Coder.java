package com.mixpanel.android.util;

public class Base64Coder
{
  private static char[] map1 = new char[64];
  private static byte[] map2;
  
  static
  {
    int i = 65;
    int j = 0;
    while (i <= 90)
    {
      map1[j] = i;
      i = (char)(i + 1);
      j += 1;
    }
    i = 97;
    while (i <= 122)
    {
      map1[j] = i;
      i = (char)(i + 1);
      j += 1;
    }
    i = 48;
    while (i <= 57)
    {
      map1[j] = i;
      i = (char)(i + 1);
      j += 1;
    }
    char[] arrayOfChar = map1;
    int k = j + 1;
    arrayOfChar[j] = '+';
    map1[k] = '/';
    map2 = new byte[''];
    j = 0;
    while (j < map2.length)
    {
      map2[j] = -1;
      j += 1;
    }
    j = 0;
    while (j < 64)
    {
      map2[map1[j]] = ((byte)j);
      j += 1;
    }
  }
  
  public static byte[] decode(String paramString)
  {
    return decode(paramString.toCharArray());
  }
  
  public static byte[] decode(char[] paramArrayOfChar)
  {
    int i = paramArrayOfChar.length;
    int k = i;
    if (i % 4 != 0) {
      throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
    }
    while ((k > 0) && (paramArrayOfChar[(k - 1)] == '=')) {
      k -= 1;
    }
    int i2 = k * 3 / 4;
    byte[] arrayOfByte = new byte[i2];
    int j = 0;
    i = 0;
    int m;
    int n;
    int i1;
    if (i < k)
    {
      m = i + 1;
      int i3 = paramArrayOfChar[i];
      i = m + 1;
      int i4 = paramArrayOfChar[m];
      if (i < k)
      {
        m = paramArrayOfChar[i];
        i += 1;
        if (i >= k) {
          break label166;
        }
        n = i + 1;
        i1 = paramArrayOfChar[i];
        i = n;
      }
      label166:
      for (n = i1;; n = 65)
      {
        if ((i3 <= 127) && (i4 <= 127) && (m <= 127) && (n <= 127)) {
          break label173;
        }
        throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
        m = 65;
        break;
      }
      label173:
      i3 = map2[i3];
      i4 = map2[i4];
      i1 = map2[m];
      n = map2[n];
      if ((i3 < 0) || (i4 < 0) || (i1 < 0) || (n < 0)) {
        throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
      }
      m = j + 1;
      arrayOfByte[j] = ((byte)(i3 << 2 | i4 >>> 4));
      if (m >= i2) {
        break label322;
      }
      j = m + 1;
      arrayOfByte[m] = ((byte)((i4 & 0xF) << 4 | i1 >>> 2));
    }
    for (;;)
    {
      if (j < i2)
      {
        m = j + 1;
        arrayOfByte[j] = ((byte)((i1 & 0x3) << 6 | n));
        j = m;
      }
      for (;;)
      {
        break;
        return arrayOfByte;
      }
      label322:
      j = m;
    }
  }
  
  public static String decodeString(String paramString)
  {
    return new String(decode(paramString));
  }
  
  public static char[] encode(byte[] paramArrayOfByte)
  {
    return encode(paramArrayOfByte, paramArrayOfByte.length);
  }
  
  public static char[] encode(byte[] paramArrayOfByte, int paramInt)
  {
    int i2 = (paramInt * 4 + 2) / 3;
    char[] arrayOfChar = new char[(paramInt + 2) / 3 * 4];
    int k = 0;
    int j = 0;
    if (j < paramInt)
    {
      int m = j + 1;
      int i3 = paramArrayOfByte[j] & 0xFF;
      label65:
      int n;
      if (m < paramInt)
      {
        j = m + 1;
        m = paramArrayOfByte[m] & 0xFF;
        if (j >= paramInt) {
          break label218;
        }
        n = j + 1;
        int i1 = paramArrayOfByte[j] & 0xFF;
        j = n;
        n = i1;
        label91:
        i1 = k + 1;
        arrayOfChar[k] = map1[(i3 >>> 2)];
        k = i1 + 1;
        arrayOfChar[i1] = map1[((i3 & 0x3) << 4 | m >>> 4)];
        if (k >= i2) {
          break label224;
        }
        i = map1[((m & 0xF) << 2 | n >>> 6)];
        label161:
        arrayOfChar[k] = i;
        k += 1;
        if (k >= i2) {
          break label230;
        }
      }
      label218:
      label224:
      label230:
      for (int i = map1[(n & 0x3F)];; i = 61)
      {
        arrayOfChar[k] = i;
        k += 1;
        break;
        n = 0;
        j = m;
        m = n;
        break label65;
        n = 0;
        break label91;
        i = 61;
        break label161;
      }
    }
    return arrayOfChar;
  }
  
  public static String encodeString(String paramString)
  {
    return new String(encode(paramString.getBytes()));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/util/Base64Coder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */