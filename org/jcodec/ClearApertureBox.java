package org.jcodec;

import java.nio.ByteBuffer;

public class ClearApertureBox
  extends FullBox
{
  private float height;
  private float width;
  
  public ClearApertureBox()
  {
    super(new Header(fourcc()));
  }
  
  public ClearApertureBox(int paramInt1, int paramInt2)
  {
    this();
    this.width = paramInt1;
    this.height = paramInt2;
  }
  
  public ClearApertureBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public ClearApertureBox(Header paramHeader, int paramInt1, int paramInt2)
  {
    super(paramHeader);
    this.width = paramInt1;
    this.height = paramInt2;
  }
  
  public static String fourcc()
  {
    return "clef";
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
    paramByteBuffer.putInt((int)(this.width * 65536.0F));
    paramByteBuffer.putInt((int)(this.height * 65536.0F));
  }
  
  public float getHeight()
  {
    return this.height;
  }
  
  public float getWidth()
  {
    return this.width;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    super.parse(paramByteBuffer);
    this.width = (paramByteBuffer.getInt() / 65536.0F);
    this.height = (paramByteBuffer.getInt() / 65536.0F);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/ClearApertureBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */