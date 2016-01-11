package org.jcodec;

public class ScalingList
{
  public int[] scalingList;
  public boolean useDefaultScalingMatrixFlag;
  
  public static ScalingList read(BitReader paramBitReader, int paramInt)
  {
    ScalingList localScalingList = new ScalingList();
    localScalingList.scalingList = new int[paramInt];
    int j = 8;
    int m = 8;
    int k = 0;
    if (k < paramInt)
    {
      int i = m;
      boolean bool;
      label70:
      int[] arrayOfInt;
      if (m != 0)
      {
        i = (j + CAVLCReader.readSE(paramBitReader, "deltaScale") + 256) % 256;
        if ((k == 0) && (i == 0))
        {
          bool = true;
          localScalingList.useDefaultScalingMatrixFlag = bool;
        }
      }
      else
      {
        arrayOfInt = localScalingList.scalingList;
        if (i != 0) {
          break label121;
        }
      }
      for (;;)
      {
        arrayOfInt[k] = j;
        j = localScalingList.scalingList[k];
        k += 1;
        m = i;
        break;
        bool = false;
        break label70;
        label121:
        j = i;
      }
    }
    return localScalingList;
  }
  
  public void write(BitWriter paramBitWriter)
  {
    if (this.useDefaultScalingMatrixFlag) {
      CAVLCWriter.writeSE(paramBitWriter, 0, "SPS: ");
    }
    for (;;)
    {
      return;
      int j = 8;
      int i = 0;
      while (i < this.scalingList.length)
      {
        if (8 != 0) {
          CAVLCWriter.writeSE(paramBitWriter, this.scalingList[i] - j - 256, "SPS: ");
        }
        j = this.scalingList[i];
        i += 1;
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/ScalingList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */