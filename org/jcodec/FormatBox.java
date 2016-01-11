package org.jcodec;

import java.nio.ByteBuffer;

public class FormatBox
  extends Box
{
  private String fmt;
  
  public FormatBox(String paramString)
  {
    super(new Header(fourcc()));
    this.fmt = paramString;
  }
  
  public FormatBox(Box paramBox)
  {
    super(paramBox);
  }
  
  public FormatBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public static String fourcc()
  {
    return "frma";
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.put(JCodecUtil.asciiString(this.fmt));
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    this.fmt = NIOUtils.readString(paramByteBuffer, 4);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/FormatBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */