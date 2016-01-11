package org.jcodec;

public class CAVLCReader
{
  public static boolean moreRBSPData(BitReader paramBitReader)
  {
    return (paramBitReader.remaining() >= 32) || (paramBitReader.checkNBit(1) != 1) || (paramBitReader.checkNBit(24) << 9 != 0);
  }
  
  public static boolean readBool(BitReader paramBitReader, String paramString)
  {
    int i = 1;
    boolean bool;
    if (paramBitReader.read1Bit() == 0)
    {
      bool = false;
      if (!bool) {
        break label37;
      }
    }
    for (;;)
    {
      Debug.trace(paramString, new Object[] { Integer.valueOf(i) });
      return bool;
      bool = true;
      break;
      label37:
      i = 0;
    }
  }
  
  public static int readME(BitReader paramBitReader, String paramString)
  {
    return readUE(paramBitReader, paramString);
  }
  
  public static int readNBit(BitReader paramBitReader, int paramInt, String paramString)
  {
    paramInt = paramBitReader.readNBit(paramInt);
    Debug.trace(paramString, new Object[] { Integer.valueOf(paramInt) });
    return paramInt;
  }
  
  public static int readSE(BitReader paramBitReader, String paramString)
  {
    int i = H264Utils.golomb2Signed(readUE(paramBitReader));
    Debug.trace(paramString, new Object[] { Integer.valueOf(i) });
    return i;
  }
  
  public static int readTE(BitReader paramBitReader, int paramInt)
  {
    if (paramInt > 1) {
      return readUE(paramBitReader);
    }
    return (paramBitReader.read1Bit() ^ 0xFFFFFFFF) & 0x1;
  }
  
  public static int readU(BitReader paramBitReader, int paramInt, String paramString)
  {
    return readNBit(paramBitReader, paramInt, paramString);
  }
  
  public static int readUE(BitReader paramBitReader)
  {
    int i = 0;
    while ((paramBitReader.read1Bit() == 0) && (i < 31)) {
      i += 1;
    }
    int j = 0;
    if (i > 0)
    {
      long l = paramBitReader.readNBit(i);
      j = (int)((1 << i) - 1 + l);
    }
    return j;
  }
  
  public static int readUE(BitReader paramBitReader, String paramString)
  {
    int i = readUE(paramBitReader);
    Debug.trace(paramString, new Object[] { Integer.valueOf(i) });
    return i;
  }
  
  public static int readZeroBitCount(BitReader paramBitReader, String paramString)
  {
    int i = 0;
    while ((paramBitReader.read1Bit() == 0) && (i < 32)) {
      i += 1;
    }
    Debug.trace(paramString, new Object[] { String.valueOf(i) });
    return i;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/CAVLCReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */