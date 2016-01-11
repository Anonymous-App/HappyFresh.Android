package org.jcodec;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class FileTypeBox
  extends Box
{
  private Collection<String> compBrands = new LinkedList();
  private String majorBrand;
  private int minorVersion;
  
  public FileTypeBox()
  {
    super(new Header(fourcc()));
  }
  
  public FileTypeBox(String paramString, int paramInt, Collection<String> paramCollection)
  {
    super(new Header(fourcc()));
    this.majorBrand = paramString;
    this.minorVersion = paramInt;
    this.compBrands = paramCollection;
  }
  
  public static String fourcc()
  {
    return "ftyp";
  }
  
  public void doWrite(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.put(JCodecUtil.asciiString(this.majorBrand));
    paramByteBuffer.putInt(this.minorVersion);
    Iterator localIterator = this.compBrands.iterator();
    while (localIterator.hasNext()) {
      paramByteBuffer.put(JCodecUtil.asciiString((String)localIterator.next()));
    }
  }
  
  public Collection<String> getCompBrands()
  {
    return this.compBrands;
  }
  
  public String getMajorBrand()
  {
    return this.majorBrand;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    this.majorBrand = NIOUtils.readString(paramByteBuffer, 4);
    this.minorVersion = paramByteBuffer.getInt();
    for (;;)
    {
      String str = NIOUtils.readString(paramByteBuffer, 4);
      if (str == null) {
        break;
      }
      this.compBrands.add(str);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/FileTypeBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */