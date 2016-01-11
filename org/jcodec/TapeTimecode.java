package org.jcodec;

public class TapeTimecode
{
  private boolean dropFrame;
  private byte frame;
  private short hour;
  private byte minute;
  private byte second;
  
  public TapeTimecode(short paramShort, byte paramByte1, byte paramByte2, byte paramByte3, boolean paramBoolean)
  {
    this.hour = paramShort;
    this.minute = paramByte1;
    this.second = paramByte2;
    this.frame = paramByte3;
    this.dropFrame = paramBoolean;
  }
  
  public byte getFrame()
  {
    return this.frame;
  }
  
  public short getHour()
  {
    return this.hour;
  }
  
  public byte getMinute()
  {
    return this.minute;
  }
  
  public byte getSecond()
  {
    return this.second;
  }
  
  public boolean isDropFrame()
  {
    return this.dropFrame;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append(String.format("%02d:%02d:%02d", new Object[] { Short.valueOf(this.hour), Byte.valueOf(this.minute), Byte.valueOf(this.second) }));
    if (this.dropFrame) {}
    for (String str = ";";; str = ":") {
      return str + String.format("%02d", new Object[] { Byte.valueOf(this.frame) });
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/TapeTimecode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */