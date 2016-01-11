package org.jcodec;

import java.nio.ByteBuffer;

public class SampleEntry
  extends NodeBox
{
  private short drefInd;
  
  public SampleEntry(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public SampleEntry(Header paramHeader, short paramShort)
  {
    super(paramHeader);
    this.drefInd = paramShort;
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.put(new byte[] { 0, 0, 0, 0, 0, 0 });
    paramByteBuffer.putShort(this.drefInd);
  }
  
  public short getDrefInd()
  {
    return this.drefInd;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.getInt();
    paramByteBuffer.getShort();
    this.drefInd = paramByteBuffer.getShort();
  }
  
  protected void parseExtensions(ByteBuffer paramByteBuffer)
  {
    super.parse(paramByteBuffer);
  }
  
  public void setDrefInd(short paramShort)
  {
    this.drefInd = paramShort;
  }
  
  public void setMediaType(String paramString)
  {
    this.header = new Header(paramString);
  }
  
  protected void writeExtensions(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/SampleEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */