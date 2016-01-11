package org.jcodec;

import java.nio.ByteBuffer;

public class ChannelBox
  extends FullBox
{
  private int channelBitmap;
  private int channelLayout;
  private ChannelDescription[] descriptions;
  
  public ChannelBox()
  {
    super(new Header(fourcc()));
  }
  
  public ChannelBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public static String fourcc()
  {
    return "chan";
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
    paramByteBuffer.putInt(this.channelLayout);
    paramByteBuffer.putInt(this.channelBitmap);
    paramByteBuffer.putInt(this.descriptions.length);
    ChannelDescription[] arrayOfChannelDescription = this.descriptions;
    int j = arrayOfChannelDescription.length;
    int i = 0;
    while (i < j)
    {
      ChannelDescription localChannelDescription = arrayOfChannelDescription[i];
      paramByteBuffer.putInt(localChannelDescription.getChannelLabel());
      paramByteBuffer.putInt(localChannelDescription.getChannelFlags());
      paramByteBuffer.putFloat(localChannelDescription.getCoordinates()[0]);
      paramByteBuffer.putFloat(localChannelDescription.getCoordinates()[1]);
      paramByteBuffer.putFloat(localChannelDescription.getCoordinates()[2]);
      i += 1;
    }
  }
  
  public int getChannelBitmap()
  {
    return this.channelBitmap;
  }
  
  public int getChannelLayout()
  {
    return this.channelLayout;
  }
  
  public ChannelDescription[] getDescriptions()
  {
    return this.descriptions;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    super.parse(paramByteBuffer);
    this.channelLayout = paramByteBuffer.getInt();
    this.channelBitmap = paramByteBuffer.getInt();
    int j = paramByteBuffer.getInt();
    this.descriptions = new ChannelDescription[j];
    int i = 0;
    while (i < j)
    {
      this.descriptions[i] = new ChannelDescription(paramByteBuffer.getInt(), paramByteBuffer.getInt(), new float[] { Float.intBitsToFloat(paramByteBuffer.getInt()), Float.intBitsToFloat(paramByteBuffer.getInt()), Float.intBitsToFloat(paramByteBuffer.getInt()) });
      i += 1;
    }
  }
  
  public void setChannelLayout(int paramInt)
  {
    this.channelLayout = paramInt;
  }
  
  public void setDescriptions(ChannelDescription[] paramArrayOfChannelDescription)
  {
    this.descriptions = paramArrayOfChannelDescription;
  }
  
  public static class ChannelDescription
  {
    private int channelFlags;
    private int channelLabel;
    private float[] coordinates = new float[3];
    
    public ChannelDescription(int paramInt1, int paramInt2, float[] paramArrayOfFloat)
    {
      this.channelLabel = paramInt1;
      this.channelFlags = paramInt2;
      this.coordinates = paramArrayOfFloat;
    }
    
    public int getChannelFlags()
    {
      return this.channelFlags;
    }
    
    public int getChannelLabel()
    {
      return this.channelLabel;
    }
    
    public float[] getCoordinates()
    {
      return this.coordinates;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/ChannelBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */