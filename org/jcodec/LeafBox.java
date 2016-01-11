package org.jcodec;

import java.nio.ByteBuffer;

public class LeafBox
  extends Box
{
  private ByteBuffer data;
  
  public LeafBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public LeafBox(Header paramHeader, ByteBuffer paramByteBuffer)
  {
    super(paramHeader);
    this.data = paramByteBuffer;
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    NIOUtils.write(paramByteBuffer, this.data);
  }
  
  public ByteBuffer getData()
  {
    return this.data.duplicate();
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    this.data = NIOUtils.read(paramByteBuffer, (int)this.header.getBodySize());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/LeafBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */