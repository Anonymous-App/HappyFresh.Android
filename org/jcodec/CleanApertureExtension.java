package org.jcodec;

import java.nio.ByteBuffer;

public class CleanApertureExtension
  extends Box
{
  private int apertureHeightDenominator;
  private int apertureHeightNumerator;
  private int apertureWidthDenominator;
  private int apertureWidthNumerator;
  private int horizOffsetDenominator;
  private int horizOffsetNumerator;
  private int vertOffsetDenominator;
  private int vertOffsetNumerator;
  
  public CleanApertureExtension()
  {
    super(new Header(fourcc()));
  }
  
  public CleanApertureExtension(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    super(new Header(fourcc()));
    this.apertureWidthNumerator = paramInt1;
    this.apertureWidthDenominator = paramInt2;
    this.apertureHeightNumerator = paramInt3;
    this.apertureHeightDenominator = paramInt4;
    this.horizOffsetNumerator = paramInt5;
    this.horizOffsetDenominator = paramInt6;
    this.vertOffsetNumerator = paramInt7;
    this.vertOffsetDenominator = paramInt8;
  }
  
  public static String fourcc()
  {
    return "clap";
  }
  
  public void doWrite(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.putInt(this.apertureWidthNumerator);
    paramByteBuffer.putInt(this.apertureWidthDenominator);
    paramByteBuffer.putInt(this.apertureHeightNumerator);
    paramByteBuffer.putInt(this.apertureHeightDenominator);
    paramByteBuffer.putInt(this.horizOffsetNumerator);
    paramByteBuffer.putInt(this.horizOffsetDenominator);
    paramByteBuffer.putInt(this.vertOffsetNumerator);
    paramByteBuffer.putInt(this.vertOffsetDenominator);
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    this.apertureWidthNumerator = paramByteBuffer.getInt();
    this.apertureWidthDenominator = paramByteBuffer.getInt();
    this.apertureHeightNumerator = paramByteBuffer.getInt();
    this.apertureHeightDenominator = paramByteBuffer.getInt();
    this.horizOffsetNumerator = paramByteBuffer.getInt();
    this.horizOffsetDenominator = paramByteBuffer.getInt();
    this.vertOffsetNumerator = paramByteBuffer.getInt();
    this.vertOffsetDenominator = paramByteBuffer.getInt();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/CleanApertureExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */