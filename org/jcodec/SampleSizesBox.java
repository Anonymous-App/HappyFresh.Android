package org.jcodec;

import java.nio.ByteBuffer;

public class SampleSizesBox
  extends FullBox
{
  private int count;
  private int defaultSize;
  private int[] sizes;
  
  public SampleSizesBox()
  {
    super(new Header(fourcc()));
  }
  
  public SampleSizesBox(int paramInt1, int paramInt2)
  {
    this();
    this.defaultSize = paramInt1;
    this.count = paramInt2;
  }
  
  public SampleSizesBox(int[] paramArrayOfInt)
  {
    this();
    this.sizes = paramArrayOfInt;
  }
  
  public static String fourcc()
  {
    return "stsz";
  }
  
  public void doWrite(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
    paramByteBuffer.putInt(this.defaultSize);
    if (this.defaultSize == 0)
    {
      paramByteBuffer.putInt(this.sizes.length);
      int[] arrayOfInt = this.sizes;
      int j = arrayOfInt.length;
      int i = 0;
      while (i < j)
      {
        paramByteBuffer.putInt((int)arrayOfInt[i]);
        i += 1;
      }
    }
    paramByteBuffer.putInt(this.count);
  }
  
  public int getCount()
  {
    return this.count;
  }
  
  public int getDefaultSize()
  {
    return this.defaultSize;
  }
  
  public int[] getSizes()
  {
    return this.sizes;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    super.parse(paramByteBuffer);
    this.defaultSize = paramByteBuffer.getInt();
    this.count = paramByteBuffer.getInt();
    if (this.defaultSize == 0)
    {
      this.sizes = new int[this.count];
      int i = 0;
      while (i < this.count)
      {
        this.sizes[i] = paramByteBuffer.getInt();
        i += 1;
      }
    }
  }
  
  public void setCount(int paramInt)
  {
    this.count = paramInt;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/SampleSizesBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */