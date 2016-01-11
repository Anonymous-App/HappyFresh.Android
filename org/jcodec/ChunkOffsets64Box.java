package org.jcodec;

import java.nio.ByteBuffer;

public class ChunkOffsets64Box
  extends FullBox
{
  private long[] chunkOffsets;
  
  public ChunkOffsets64Box()
  {
    super(new Header(fourcc(), 0L));
  }
  
  public ChunkOffsets64Box(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public ChunkOffsets64Box(long[] paramArrayOfLong)
  {
    this();
    this.chunkOffsets = paramArrayOfLong;
  }
  
  public static String fourcc()
  {
    return "co64";
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
    paramByteBuffer.putInt(this.chunkOffsets.length);
    long[] arrayOfLong = this.chunkOffsets;
    int j = arrayOfLong.length;
    int i = 0;
    while (i < j)
    {
      paramByteBuffer.putLong(arrayOfLong[i]);
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
      this.chunkOffsets[i] = paramByteBuffer.getLong();
      i += 1;
    }
  }
  
  public void setChunkOffsets(long[] paramArrayOfLong)
  {
    this.chunkOffsets = paramArrayOfLong;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/ChunkOffsets64Box.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */