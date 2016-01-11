package org.jcodec;

import java.nio.ByteBuffer;

public class SampleToChunkBox
  extends FullBox
{
  private SampleToChunkEntry[] sampleToChunk;
  
  public SampleToChunkBox()
  {
    super(new Header(fourcc()));
  }
  
  public SampleToChunkBox(SampleToChunkEntry[] paramArrayOfSampleToChunkEntry)
  {
    super(new Header(fourcc()));
    this.sampleToChunk = paramArrayOfSampleToChunkEntry;
  }
  
  public static String fourcc()
  {
    return "stsc";
  }
  
  public void doWrite(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
    paramByteBuffer.putInt(this.sampleToChunk.length);
    SampleToChunkEntry[] arrayOfSampleToChunkEntry = this.sampleToChunk;
    int j = arrayOfSampleToChunkEntry.length;
    int i = 0;
    while (i < j)
    {
      SampleToChunkEntry localSampleToChunkEntry = arrayOfSampleToChunkEntry[i];
      paramByteBuffer.putInt((int)localSampleToChunkEntry.getFirst());
      paramByteBuffer.putInt(localSampleToChunkEntry.getCount());
      paramByteBuffer.putInt(localSampleToChunkEntry.getEntry());
      i += 1;
    }
  }
  
  public SampleToChunkEntry[] getSampleToChunk()
  {
    return this.sampleToChunk;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    super.parse(paramByteBuffer);
    int j = paramByteBuffer.getInt();
    this.sampleToChunk = new SampleToChunkEntry[j];
    int i = 0;
    while (i < j)
    {
      this.sampleToChunk[i] = new SampleToChunkEntry(paramByteBuffer.getInt(), paramByteBuffer.getInt(), paramByteBuffer.getInt());
      i += 1;
    }
  }
  
  public void setSampleToChunk(SampleToChunkEntry[] paramArrayOfSampleToChunkEntry)
  {
    this.sampleToChunk = paramArrayOfSampleToChunkEntry;
  }
  
  public static class SampleToChunkEntry
  {
    private int count;
    private int entry;
    private long first;
    
    public SampleToChunkEntry(long paramLong, int paramInt1, int paramInt2)
    {
      this.first = paramLong;
      this.count = paramInt1;
      this.entry = paramInt2;
    }
    
    public int getCount()
    {
      return this.count;
    }
    
    public int getEntry()
    {
      return this.entry;
    }
    
    public long getFirst()
    {
      return this.first;
    }
    
    public void setCount(int paramInt)
    {
      this.count = paramInt;
    }
    
    public void setEntry(int paramInt)
    {
      this.entry = paramInt;
    }
    
    public void setFirst(long paramLong)
    {
      this.first = paramLong;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/SampleToChunkBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */