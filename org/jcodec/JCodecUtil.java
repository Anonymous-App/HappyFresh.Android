package org.jcodec;

import java.nio.ByteBuffer;

public class JCodecUtil
{
  public static byte[] asciiString(String paramString)
  {
    paramString = paramString.toCharArray();
    byte[] arrayOfByte = new byte[paramString.length];
    int i = 0;
    while (i < paramString.length)
    {
      arrayOfByte[i] = ((byte)paramString[i]);
      i += 1;
    }
    return arrayOfByte;
  }
  
  public static int[] getAsIntArray(ByteBuffer paramByteBuffer, int paramInt)
  {
    byte[] arrayOfByte = new byte[paramInt];
    int[] arrayOfInt = new int[paramInt];
    paramByteBuffer.get(arrayOfByte);
    paramInt = 0;
    while (paramInt < arrayOfByte.length)
    {
      arrayOfByte[paramInt] &= 0xFF;
      paramInt += 1;
    }
    return arrayOfInt;
  }
  
  public static int readBER32(ByteBuffer paramByteBuffer)
  {
    int i = 0;
    int j = 0;
    for (;;)
    {
      int k = i;
      if (j < 4)
      {
        k = paramByteBuffer.get();
        i = i << 7 | k & 0x7F;
        if ((k & 0xFF) >> 7 == 0) {
          k = i;
        }
      }
      else
      {
        return k;
      }
      j += 1;
    }
  }
  
  public static String removeExtension(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    return paramString.replaceAll("\\.[^\\.]+$", "");
  }
  
  public static void writeBER32(ByteBuffer paramByteBuffer, int paramInt)
  {
    paramByteBuffer.put((byte)(paramInt >> 21 | 0x80));
    paramByteBuffer.put((byte)(paramInt >> 14 | 0x80));
    paramByteBuffer.put((byte)(paramInt >> 7 | 0x80));
    paramByteBuffer.put((byte)(paramInt & 0x7F));
  }
  
  public static enum Format
  {
    MOV,  MPEG_PS,  MPEG_TS;
    
    private Format() {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/JCodecUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */