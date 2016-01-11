package org.jcodec;

import java.nio.ByteBuffer;

public class ColorExtension
  extends Box
{
  private short matrixIndex;
  private short primariesIndex;
  private short transferFunctionIndex;
  private final String type = "nclc";
  
  public ColorExtension()
  {
    super(new Header(fourcc()));
  }
  
  public ColorExtension(short paramShort1, short paramShort2, short paramShort3)
  {
    this();
    this.primariesIndex = paramShort1;
    this.transferFunctionIndex = paramShort2;
    this.matrixIndex = paramShort3;
  }
  
  public static String fourcc()
  {
    return "colr";
  }
  
  public void doWrite(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.put(JCodecUtil.asciiString("nclc"));
    paramByteBuffer.putShort(this.primariesIndex);
    paramByteBuffer.putShort(this.transferFunctionIndex);
    paramByteBuffer.putShort(this.matrixIndex);
  }
  
  public short getMatrixIndex()
  {
    return this.matrixIndex;
  }
  
  public short getPrimariesIndex()
  {
    return this.primariesIndex;
  }
  
  public short getTransferFunctionIndex()
  {
    return this.transferFunctionIndex;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    long l = paramByteBuffer.getInt();
    this.primariesIndex = paramByteBuffer.getShort();
    this.transferFunctionIndex = paramByteBuffer.getShort();
    this.matrixIndex = paramByteBuffer.getShort();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/ColorExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */