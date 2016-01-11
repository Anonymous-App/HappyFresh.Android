package org.jcodec;

import java.nio.ByteBuffer;

public class GenericMediaInfoBox
  extends FullBox
{
  private short bOpColor;
  private short balance;
  private short gOpColor;
  private short graphicsMode;
  private short rOpColor;
  
  public GenericMediaInfoBox()
  {
    this(new Header(fourcc()));
  }
  
  public GenericMediaInfoBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public GenericMediaInfoBox(short paramShort1, short paramShort2, short paramShort3, short paramShort4, short paramShort5)
  {
    this();
    this.graphicsMode = paramShort1;
    this.rOpColor = paramShort2;
    this.gOpColor = paramShort3;
    this.bOpColor = paramShort4;
    this.balance = paramShort5;
  }
  
  public static String fourcc()
  {
    return "gmin";
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
    paramByteBuffer.putShort(this.graphicsMode);
    paramByteBuffer.putShort(this.rOpColor);
    paramByteBuffer.putShort(this.gOpColor);
    paramByteBuffer.putShort(this.bOpColor);
    paramByteBuffer.putShort(this.balance);
    paramByteBuffer.putShort((short)0);
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    super.parse(paramByteBuffer);
    this.graphicsMode = paramByteBuffer.getShort();
    this.rOpColor = paramByteBuffer.getShort();
    this.gOpColor = paramByteBuffer.getShort();
    this.bOpColor = paramByteBuffer.getShort();
    this.balance = paramByteBuffer.getShort();
    paramByteBuffer.getShort();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/GenericMediaInfoBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */