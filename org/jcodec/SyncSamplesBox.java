package org.jcodec;

import java.nio.ByteBuffer;

public class SyncSamplesBox
  extends FullBox
{
  private int[] syncSamples;
  
  public SyncSamplesBox()
  {
    super(new Header(fourcc()));
  }
  
  public SyncSamplesBox(int[] paramArrayOfInt)
  {
    this();
    this.syncSamples = paramArrayOfInt;
  }
  
  public static String fourcc()
  {
    return "stss";
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
    paramByteBuffer.putInt(this.syncSamples.length);
    int i = 0;
    while (i < this.syncSamples.length)
    {
      paramByteBuffer.putInt(this.syncSamples[i]);
      i += 1;
    }
  }
  
  public int[] getSyncSamples()
  {
    return this.syncSamples;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    super.parse(paramByteBuffer);
    int j = paramByteBuffer.getInt();
    this.syncSamples = new int[j];
    int i = 0;
    while (i < j)
    {
      this.syncSamples[i] = paramByteBuffer.getInt();
      i += 1;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/SyncSamplesBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */