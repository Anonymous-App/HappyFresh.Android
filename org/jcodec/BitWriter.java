package org.jcodec;

import java.nio.ByteBuffer;

public class BitWriter
{
  private final ByteBuffer buf;
  private int curBit;
  private int curInt;
  private int initPos;
  
  public BitWriter(ByteBuffer paramByteBuffer)
  {
    this.buf = paramByteBuffer;
    this.initPos = paramByteBuffer.position();
  }
  
  private BitWriter(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3)
  {
    this.buf = paramByteBuffer;
    this.curBit = paramInt1;
    this.curInt = paramInt2;
    this.initPos = paramInt3;
  }
  
  private final void putInt(int paramInt)
  {
    this.buf.put((byte)(paramInt >>> 24));
    this.buf.put((byte)(paramInt >> 16));
    this.buf.put((byte)(paramInt >> 8));
    this.buf.put((byte)paramInt);
  }
  
  public int curBit()
  {
    return this.curBit & 0x7;
  }
  
  public void flush()
  {
    int j = this.curBit;
    int i = 0;
    while (i < j + 7 >> 3)
    {
      this.buf.put((byte)(this.curInt >>> 24));
      this.curInt <<= 8;
      i += 1;
    }
  }
  
  public BitWriter fork()
  {
    return new BitWriter(this.buf.duplicate(), this.curBit, this.curInt, this.initPos);
  }
  
  public ByteBuffer getBuffer()
  {
    return this.buf;
  }
  
  public int position()
  {
    return (this.buf.position() - this.initPos << 3) + this.curBit;
  }
  
  public void write1Bit(int paramInt)
  {
    this.curInt |= paramInt << 32 - this.curBit - 1;
    this.curBit += 1;
    if (this.curBit == 32)
    {
      putInt(this.curInt);
      this.curBit = 0;
      this.curInt = 0;
    }
  }
  
  public final void writeNBit(int paramInt1, int paramInt2)
  {
    if (paramInt2 > 32) {
      throw new IllegalArgumentException("Max 32 bit to write");
    }
    if (paramInt2 == 0) {}
    do
    {
      return;
      paramInt1 &= -1 >>> 32 - paramInt2;
      if (32 - this.curBit < paramInt2) {
        break;
      }
      this.curInt |= paramInt1 << 32 - this.curBit - paramInt2;
      this.curBit += paramInt2;
    } while (this.curBit != 32);
    putInt(this.curInt);
    this.curBit = 0;
    this.curInt = 0;
    return;
    paramInt2 -= 32 - this.curBit;
    this.curInt |= paramInt1 >>> paramInt2;
    putInt(this.curInt);
    this.curInt = (paramInt1 << 32 - paramInt2);
    this.curBit = paramInt2;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/BitWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */