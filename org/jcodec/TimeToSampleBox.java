package org.jcodec;

import java.nio.ByteBuffer;

public class TimeToSampleBox
  extends FullBox
{
  private TimeToSampleEntry[] entries;
  
  public TimeToSampleBox()
  {
    super(new Header(fourcc()));
  }
  
  public TimeToSampleBox(TimeToSampleEntry[] paramArrayOfTimeToSampleEntry)
  {
    super(new Header(fourcc()));
    this.entries = paramArrayOfTimeToSampleEntry;
  }
  
  public static String fourcc()
  {
    return "stts";
  }
  
  public void doWrite(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
    paramByteBuffer.putInt(this.entries.length);
    TimeToSampleEntry[] arrayOfTimeToSampleEntry = this.entries;
    int j = arrayOfTimeToSampleEntry.length;
    int i = 0;
    while (i < j)
    {
      TimeToSampleEntry localTimeToSampleEntry = arrayOfTimeToSampleEntry[i];
      paramByteBuffer.putInt(localTimeToSampleEntry.getSampleCount());
      paramByteBuffer.putInt(localTimeToSampleEntry.getSampleDuration());
      i += 1;
    }
  }
  
  public TimeToSampleEntry[] getEntries()
  {
    return this.entries;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    super.parse(paramByteBuffer);
    int j = paramByteBuffer.getInt();
    this.entries = new TimeToSampleEntry[j];
    int i = 0;
    while (i < j)
    {
      this.entries[i] = new TimeToSampleEntry(paramByteBuffer.getInt(), paramByteBuffer.getInt());
      i += 1;
    }
  }
  
  public void setEntries(TimeToSampleEntry[] paramArrayOfTimeToSampleEntry)
  {
    this.entries = paramArrayOfTimeToSampleEntry;
  }
  
  public static class TimeToSampleEntry
  {
    int sampleCount;
    int sampleDuration;
    
    public TimeToSampleEntry(int paramInt1, int paramInt2)
    {
      this.sampleCount = paramInt1;
      this.sampleDuration = paramInt2;
    }
    
    public int getSampleCount()
    {
      return this.sampleCount;
    }
    
    public int getSampleDuration()
    {
      return this.sampleDuration;
    }
    
    public void setSampleCount(int paramInt)
    {
      this.sampleCount = paramInt;
    }
    
    public void setSampleDuration(int paramInt)
    {
      this.sampleDuration = paramInt;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/TimeToSampleBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */