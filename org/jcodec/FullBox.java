package org.jcodec;

import java.nio.ByteBuffer;

public class FullBox
  extends Box
{
  protected int flags;
  protected byte version;
  
  public FullBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.putInt(this.version << 24 | this.flags & 0xFFFFFF);
  }
  
  public int getFlags()
  {
    return this.flags;
  }
  
  public byte getVersion()
  {
    return this.version;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    int i = paramByteBuffer.getInt();
    this.version = ((byte)(i >> 24 & 0xFF));
    this.flags = (0xFFFFFF & i);
  }
  
  public void setFlags(int paramInt)
  {
    this.flags = paramInt;
  }
  
  public void setVersion(byte paramByte)
  {
    this.version = paramByte;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/FullBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */