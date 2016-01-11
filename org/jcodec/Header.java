package org.jcodec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;

public class Header
{
  private static final long MAX_UNSIGNED_INT = 4294967296L;
  private String fourcc;
  private boolean lng;
  private long size;
  
  public Header(String paramString)
  {
    this.fourcc = paramString;
  }
  
  public Header(String paramString, long paramLong)
  {
    this.size = paramLong;
    this.fourcc = paramString;
  }
  
  public Header(String paramString, long paramLong, boolean paramBoolean)
  {
    this(paramString, paramLong);
    this.lng = paramBoolean;
  }
  
  public Header(Header paramHeader)
  {
    this.fourcc = paramHeader.fourcc;
    this.size = paramHeader.size;
  }
  
  public static Header read(ByteBuffer paramByteBuffer)
  {
    long l1 = 0L;
    while (paramByteBuffer.remaining() >= 4)
    {
      l2 = paramByteBuffer.getInt() & 0xFFFFFFFF;
      l1 = l2;
      if (l2 != 0L) {
        l1 = l2;
      }
    }
    if ((paramByteBuffer.remaining() < 4) || ((l1 < 8L) && (l1 != 1L)))
    {
      System.out.println("Broken atom of size " + l1);
      return null;
    }
    String str = NIOUtils.readString(paramByteBuffer, 4);
    boolean bool = false;
    long l2 = l1;
    if (l1 == 1L)
    {
      if (paramByteBuffer.remaining() >= 8)
      {
        bool = true;
        l2 = paramByteBuffer.getLong();
      }
    }
    else {
      return new Header(str, l2, bool);
    }
    System.out.println("Broken atom of size " + l1);
    return null;
  }
  
  public long getBodySize()
  {
    return this.size - headerSize();
  }
  
  public String getFourcc()
  {
    return this.fourcc;
  }
  
  public long getSize()
  {
    return this.size;
  }
  
  public long headerSize()
  {
    if ((this.lng) || (this.size > 4294967296L)) {
      return 16L;
    }
    return 8L;
  }
  
  public void print()
  {
    System.out.println(this.fourcc + "," + this.size);
  }
  
  public byte[] readContents(InputStream paramInputStream)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    int i = 0;
    while (i < this.size - headerSize())
    {
      localByteArrayOutputStream.write(paramInputStream.read());
      i += 1;
    }
    return localByteArrayOutputStream.toByteArray();
  }
  
  public void setBodySize(int paramInt)
  {
    this.size = (paramInt + headerSize());
  }
  
  public void write(ByteBuffer paramByteBuffer)
  {
    if (this.size > 4294967296L) {
      paramByteBuffer.putInt(1);
    }
    for (;;)
    {
      paramByteBuffer.put(JCodecUtil.asciiString(this.fourcc));
      if (this.size > 4294967296L) {
        paramByteBuffer.putLong(this.size);
      }
      return;
      paramByteBuffer.putInt((int)this.size);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/Header.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */