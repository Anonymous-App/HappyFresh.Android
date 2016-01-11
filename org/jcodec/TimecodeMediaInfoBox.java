package org.jcodec;

import java.nio.ByteBuffer;

public class TimecodeMediaInfoBox
  extends FullBox
{
  private short[] bgcolor = new short[3];
  private short[] color = new short[3];
  private short face;
  private short font;
  private String name;
  private short size;
  
  public TimecodeMediaInfoBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public TimecodeMediaInfoBox(short paramShort1, short paramShort2, short paramShort3, short[] paramArrayOfShort1, short[] paramArrayOfShort2, String paramString)
  {
    this(new Header(fourcc()));
    this.font = paramShort1;
    this.face = paramShort2;
    this.size = paramShort3;
    this.name = paramString;
  }
  
  public static String fourcc()
  {
    return "tcmi";
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
    paramByteBuffer.putShort(this.font);
    paramByteBuffer.putShort(this.face);
    paramByteBuffer.putShort(this.size);
    paramByteBuffer.putShort((short)0);
    paramByteBuffer.putShort(this.color[0]);
    paramByteBuffer.putShort(this.color[1]);
    paramByteBuffer.putShort(this.color[2]);
    paramByteBuffer.putShort(this.bgcolor[0]);
    paramByteBuffer.putShort(this.bgcolor[1]);
    paramByteBuffer.putShort(this.bgcolor[2]);
    NIOUtils.writePascalString(paramByteBuffer, this.name);
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    super.parse(paramByteBuffer);
    this.font = paramByteBuffer.getShort();
    this.face = paramByteBuffer.getShort();
    this.size = paramByteBuffer.getShort();
    paramByteBuffer.getShort();
    this.color[0] = paramByteBuffer.getShort();
    this.color[1] = paramByteBuffer.getShort();
    this.color[2] = paramByteBuffer.getShort();
    this.bgcolor[0] = paramByteBuffer.getShort();
    this.bgcolor[1] = paramByteBuffer.getShort();
    this.bgcolor[2] = paramByteBuffer.getShort();
    this.name = NIOUtils.readPascalString(paramByteBuffer);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/TimecodeMediaInfoBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */