package org.jcodec;

import java.nio.ByteBuffer;

public class PixelAspectExt
  extends Box
{
  private int hSpacing;
  private int vSpacing;
  
  public PixelAspectExt()
  {
    super(new Header(fourcc()));
  }
  
  public PixelAspectExt(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public PixelAspectExt(Rational paramRational)
  {
    this();
    this.hSpacing = paramRational.getNum();
    this.vSpacing = paramRational.getDen();
  }
  
  public static String fourcc()
  {
    return "pasp";
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.putInt(this.hSpacing);
    paramByteBuffer.putInt(this.vSpacing);
  }
  
  public Rational getRational()
  {
    return new Rational(this.hSpacing, this.vSpacing);
  }
  
  public int gethSpacing()
  {
    return this.hSpacing;
  }
  
  public int getvSpacing()
  {
    return this.vSpacing;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    this.hSpacing = paramByteBuffer.getInt();
    this.vSpacing = paramByteBuffer.getInt();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/PixelAspectExt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */