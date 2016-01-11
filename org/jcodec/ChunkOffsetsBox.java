package org.jcodec;

import java.nio.ByteBuffer;

public class ChunkOffsetsBox
  extends FullBox
{
  private long[] chunkOffsets;
  
  public ChunkOffsetsBox()
  {
    super(new Header(fourcc()));
  }
  
  public ChunkOffsetsBox(long[] paramArrayOfLong)
  {
    super(new Header(fourcc()));
    this.chunkOffsets = paramArrayOfLong;
  }
  
  public static String fourcc()
  {
    return "stco";
  }
  
  public void doWrite(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
    paramByteBuffer.putInt(this.chunkOffsets.length);
    long[] arrayOfLong = this.chunkOffsets;
    int j = arrayOfLong.length;
    int i = 0;
    while (i < j)
    {
      paramByteBuffer.putInt((int)arrayOfLong[i]);
      i += 1;
    }
  }
  
  public long[] getChunkOffsets()
  {
    return this.chunkOffsets;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    super.parse(paramByteBuffer);
    int j = paramByteBuffer.getInt();
    this.chunkOffsets = new long[j];
    int i = 0;
    while (i < j)
    {
      this.chunkOffsets[i] = (paramByteBuffer.getInt() & 0xFFFFFFFF);
      i += 1;
    }
  }
  
  public void setChunkOffsets(long[] paramArrayOfLong)
  {
    this.chunkOffsets = paramArrayOfLong;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/ChunkOffsetsBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */