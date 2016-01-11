package org.jcodec;

import java.nio.ByteBuffer;

public class CompositionOffsetsBox
  extends FullBox
{
  private Entry[] entries;
  
  public CompositionOffsetsBox()
  {
    super(new Header(fourcc()));
  }
  
  public CompositionOffsetsBox(Entry[] paramArrayOfEntry)
  {
    super(new Header(fourcc()));
    this.entries = paramArrayOfEntry;
  }
  
  public static String fourcc()
  {
    return "ctts";
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
    paramByteBuffer.putInt(this.entries.length);
    int i = 0;
    while (i < this.entries.length)
    {
      paramByteBuffer.putInt(this.entries[i].count);
      paramByteBuffer.putInt(this.entries[i].offset);
      i += 1;
    }
  }
  
  public Entry[] getEntries()
  {
    return this.entries;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    super.parse(paramByteBuffer);
    int j = paramByteBuffer.getInt();
    this.entries = new Entry[j];
    int i = 0;
    while (i < j)
    {
      this.entries[i] = new Entry(paramByteBuffer.getInt(), paramByteBuffer.getInt());
      i += 1;
    }
  }
  
  public static class Entry
  {
    public int count;
    public int offset;
    
    public Entry(int paramInt1, int paramInt2)
    {
      this.count = paramInt1;
      this.offset = paramInt2;
    }
    
    public int getCount()
    {
      return this.count;
    }
    
    public int getOffset()
    {
      return this.offset;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/CompositionOffsetsBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */