package org.jcodec;

import java.nio.ByteBuffer;

public class VideoMediaHeaderBox
  extends FullBox
{
  int bOpColor;
  int gOpColor;
  int graphicsMode;
  int rOpColor;
  
  public VideoMediaHeaderBox()
  {
    super(new Header(fourcc()));
  }
  
  public VideoMediaHeaderBox(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super(new Header(fourcc()));
    this.graphicsMode = paramInt1;
    this.rOpColor = paramInt2;
    this.gOpColor = paramInt3;
    this.bOpColor = paramInt4;
  }
  
  public VideoMediaHeaderBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public static String fourcc()
  {
    return "vmhd";
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
    paramByteBuffer.putShort((short)this.graphicsMode);
    paramByteBuffer.putShort((short)this.rOpColor);
    paramByteBuffer.putShort((short)this.gOpColor);
    paramByteBuffer.putShort((short)this.bOpColor);
  }
  
  public int getGraphicsMode()
  {
    return this.graphicsMode;
  }
  
  public int getbOpColor()
  {
    return this.bOpColor;
  }
  
  public int getgOpColor()
  {
    return this.gOpColor;
  }
  
  public int getrOpColor()
  {
    return this.rOpColor;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    super.parse(paramByteBuffer);
    this.graphicsMode = paramByteBuffer.getShort();
    this.rOpColor = paramByteBuffer.getShort();
    this.gOpColor = paramByteBuffer.getShort();
    this.bOpColor = paramByteBuffer.getShort();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/VideoMediaHeaderBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */