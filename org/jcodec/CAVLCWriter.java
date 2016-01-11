package org.jcodec;

public class CAVLCWriter
{
  public static void writeBool(BitWriter paramBitWriter, boolean paramBoolean, String paramString)
  {
    int j = 1;
    if (paramBoolean)
    {
      i = 1;
      paramBitWriter.write1Bit(i);
      if (!paramBoolean) {
        break label42;
      }
    }
    label42:
    for (int i = j;; i = 0)
    {
      Debug.trace(paramString, new Object[] { Integer.valueOf(i) });
      return;
      i = 0;
      break;
    }
  }
  
  public static void writeNBit(BitWriter paramBitWriter, long paramLong, int paramInt, String paramString)
  {
    int i = 0;
    while (i < paramInt)
    {
      paramBitWriter.write1Bit((int)(paramLong >> paramInt - i - 1) & 0x1);
      i += 1;
    }
    Debug.trace(paramString, new Object[] { Long.valueOf(paramLong) });
  }
  
  public static void writeSE(BitWriter paramBitWriter, int paramInt)
  {
    writeUE(paramBitWriter, MathUtil.golomb(paramInt));
  }
  
  public static void writeSE(BitWriter paramBitWriter, int paramInt, String paramString)
  {
    writeUE(paramBitWriter, MathUtil.golomb(paramInt));
    Debug.trace(paramString, new Object[] { Integer.valueOf(paramInt) });
  }
  
  public static void writeSliceTrailingBits()
  {
    throw new IllegalStateException("todo");
  }
  
  public static void writeTrailingBits(BitWriter paramBitWriter)
  {
    paramBitWriter.write1Bit(1);
    paramBitWriter.flush();
  }
  
  public static void writeU(BitWriter paramBitWriter, int paramInt1, int paramInt2)
  {
    paramBitWriter.writeNBit(paramInt1, paramInt2);
  }
  
  public static void writeUE(BitWriter paramBitWriter, int paramInt)
  {
    int m = 0;
    int j = 0;
    int i = 0;
    for (;;)
    {
      int k = m;
      if (i < 15)
      {
        if (paramInt < (1 << i) + j) {
          k = i;
        }
      }
      else
      {
        paramBitWriter.writeNBit(0, k);
        paramBitWriter.write1Bit(1);
        paramBitWriter.writeNBit(paramInt - j, k);
        return;
      }
      j += (1 << i);
      i += 1;
    }
  }
  
  public static void writeUE(BitWriter paramBitWriter, int paramInt, String paramString)
  {
    writeUE(paramBitWriter, paramInt);
    Debug.trace(paramString, new Object[] { Integer.valueOf(paramInt) });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/CAVLCWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */