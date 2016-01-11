package org.jcodec;

public class AudioFormat
{
  private boolean bigEndian;
  private int channelCount;
  private int sampleRate;
  private int sampleSizeInBits;
  private boolean signed;
  
  public AudioFormat(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.sampleRate = paramInt1;
    this.sampleSizeInBits = paramInt2;
    this.channelCount = paramInt3;
    this.signed = paramBoolean1;
    this.bigEndian = paramBoolean2;
  }
  
  public int getChannels()
  {
    return this.channelCount;
  }
  
  public int getFrameRate()
  {
    return this.sampleRate;
  }
  
  public short getFrameSize()
  {
    return (short)((this.sampleSizeInBits >> 3) * this.channelCount);
  }
  
  public int getSampleRate()
  {
    return this.sampleRate;
  }
  
  public int getSampleSizeInBits()
  {
    return this.sampleSizeInBits;
  }
  
  public boolean isBigEndian()
  {
    return this.bigEndian;
  }
  
  public boolean isSigned()
  {
    return this.signed;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/AudioFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */