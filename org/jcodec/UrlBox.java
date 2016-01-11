package org.jcodec;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class UrlBox
  extends FullBox
{
  private String url;
  
  public UrlBox(String paramString)
  {
    super(new Header(fourcc()));
    this.url = paramString;
  }
  
  public UrlBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public static String fourcc()
  {
    return "url ";
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
    Charset localCharset = Charset.forName("utf-8");
    if (this.url != null)
    {
      NIOUtils.write(paramByteBuffer, ByteBuffer.wrap(this.url.getBytes(localCharset)));
      paramByteBuffer.put((byte)0);
    }
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    super.parse(paramByteBuffer);
    if ((this.flags & 0x1) != 0) {
      return;
    }
    this.url = NIOUtils.readNullTermString(paramByteBuffer, Charset.forName("utf-8"));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/UrlBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */