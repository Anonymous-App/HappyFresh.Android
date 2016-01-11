package org.jcodec;

import java.nio.ByteBuffer;

public class BitReader
{
  private ByteBuffer bb;
  protected int curInt;
  protected int deficit;
  
  public BitReader(ByteBuffer paramByteBuffer)
  {
    this.bb = paramByteBuffer;
    this.curInt = readInt();
    this.deficit = 0;
  }
  
  private int nextIgnore()
  {
    if (this.bb.hasRemaining()) {
      return this.bb.get() & 0xFF;
    }
    return 0;
  }
  
  private int readIntSafe()
  {
    this.deficit -= (this.bb.remaining() << 3);
    int i = 0;
    if (this.bb.hasRemaining()) {
      i = 0x0 | this.bb.get() & 0xFF;
    }
    int j = i << 8;
    i = j;
    if (this.bb.hasRemaining()) {
      i = j | this.bb.get() & 0xFF;
    }
    j = i << 8;
    i = j;
    if (this.bb.hasRemaining()) {
      i = j | this.bb.get() & 0xFF;
    }
    j = i << 8;
    i = j;
    if (this.bb.hasRemaining()) {
      i = j | this.bb.get() & 0xFF;
    }
    return i;
  }
  
  public int checkNBit(int paramInt)
  {
    if (paramInt > 24) {
      throw new IllegalArgumentException("Can not check more then 24 bit");
    }
    while (this.deficit + paramInt > 32)
    {
      this.deficit -= 8;
      this.curInt |= nextIgnore() << this.deficit;
    }
    return this.curInt >>> 32 - paramInt;
  }
  
  public int curBit()
  {
    return this.deficit & 0x7;
  }
  
  public int read1Bit()
  {
    int i = this.curInt;
    this.curInt <<= 1;
    this.deficit += 1;
    if (this.deficit == 32) {
      this.curInt = readInt();
    }
    return i >>> 31;
  }
  
  public final int readInt()
  {
    if (this.bb.remaining() >= 4)
    {
      this.deficit -= 32;
      return (this.bb.get() & 0xFF) << 24 | (this.bb.get() & 0xFF) << 16 | (this.bb.get() & 0xFF) << 8 | this.bb.get() & 0xFF;
    }
    return readIntSafe();
  }
  
  public int readNBit(int paramInt)
  {
    if (paramInt > 32) {
      throw new IllegalArgumentException("Can not read more then 32 bit");
    }
    int i = 0;
    int j = paramInt;
    if (this.deficit + paramInt > 31)
    {
      i = this.curInt;
      int k = this.deficit;
      j = paramInt - (32 - this.deficit);
      i = (0x0 | i >>> k) << j;
      this.deficit = 32;
      this.curInt = readInt();
    }
    paramInt = i;
    if (j != 0)
    {
      paramInt = i | this.curInt >>> 32 - j;
      this.curInt <<= j;
      this.deficit += j;
    }
    return paramInt;
  }
  
  public int remaining()
  {
    return (this.bb.remaining() << 3) + 32 - this.deficit;
  }
  
  public void stop()
  {
    this.bb.position(this.bb.position() - (32 - this.deficit >> 3));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/BitReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */