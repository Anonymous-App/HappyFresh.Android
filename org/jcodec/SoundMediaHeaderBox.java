package org.jcodec;

import java.nio.ByteBuffer;

public class SoundMediaHeaderBox
  extends FullBox
{
  private short balance;
  
  public SoundMediaHeaderBox()
  {
    super(new Header(fourcc()));
  }
  
  public SoundMediaHeaderBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public static String fourcc()
  {
    return "smhd";
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
    paramByteBuffer.putShort(this.balance);
    paramByteBuffer.putShort((short)0);
  }
  
  public short getBalance()
  {
    return this.balance;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    super.parse(paramByteBuffer);
    this.balance = paramByteBuffer.getShort();
    paramByteBuffer.getShort();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/SoundMediaHeaderBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */