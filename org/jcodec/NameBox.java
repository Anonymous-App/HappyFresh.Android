package org.jcodec;

import java.nio.ByteBuffer;

public class NameBox
  extends Box
{
  private String name;
  
  public NameBox()
  {
    super(new Header(fourcc()));
  }
  
  public NameBox(String paramString)
  {
    this();
    this.name = paramString;
  }
  
  private NameBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public static String fourcc()
  {
    return "name";
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.put(JCodecUtil.asciiString(this.name));
    paramByteBuffer.putInt(0);
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    this.name = NIOUtils.readNullTermString(paramByteBuffer);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/NameBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */