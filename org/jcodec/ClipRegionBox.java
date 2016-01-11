package org.jcodec;

import java.nio.ByteBuffer;

public class ClipRegionBox
  extends Box
{
  private short height;
  private short rgnSize;
  private short width;
  private short x;
  private short y;
  
  public ClipRegionBox()
  {
    super(new Header(fourcc()));
  }
  
  public ClipRegionBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public ClipRegionBox(short paramShort1, short paramShort2, short paramShort3, short paramShort4)
  {
    this();
    this.rgnSize = 10;
    this.x = paramShort1;
    this.y = paramShort2;
    this.width = paramShort3;
    this.height = paramShort4;
  }
  
  public static String fourcc()
  {
    return "crgn";
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.putShort(this.rgnSize);
    paramByteBuffer.putShort(this.y);
    paramByteBuffer.putShort(this.x);
    paramByteBuffer.putShort(this.height);
    paramByteBuffer.putShort(this.width);
  }
  
  public short getHeight()
  {
    return this.height;
  }
  
  public short getRgnSize()
  {
    return this.rgnSize;
  }
  
  public short getWidth()
  {
    return this.width;
  }
  
  public short getX()
  {
    return this.x;
  }
  
  public short getY()
  {
    return this.y;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    this.rgnSize = paramByteBuffer.getShort();
    this.y = paramByteBuffer.getShort();
    this.x = paramByteBuffer.getShort();
    this.height = paramByteBuffer.getShort();
    this.width = paramByteBuffer.getShort();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/ClipRegionBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */