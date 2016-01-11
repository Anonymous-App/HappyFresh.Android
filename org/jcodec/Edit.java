package org.jcodec;

public class Edit
{
  private long duration;
  private long mediaTime;
  private float rate;
  
  public Edit(long paramLong1, long paramLong2, float paramFloat)
  {
    this.duration = paramLong1;
    this.mediaTime = paramLong2;
    this.rate = paramFloat;
  }
  
  public Edit(Edit paramEdit)
  {
    this.duration = paramEdit.duration;
    this.mediaTime = paramEdit.mediaTime;
    this.rate = paramEdit.rate;
  }
  
  public long getDuration()
  {
    return this.duration;
  }
  
  public long getMediaTime()
  {
    return this.mediaTime;
  }
  
  public float getRate()
  {
    return this.rate;
  }
  
  public void setDuration(long paramLong)
  {
    this.duration = paramLong;
  }
  
  public void setMediaTime(long paramLong)
  {
    this.mediaTime = paramLong;
  }
  
  public void shift(long paramLong)
  {
    this.mediaTime += paramLong;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/Edit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */