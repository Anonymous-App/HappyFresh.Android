package org.jcodec;

import java.nio.ByteBuffer;

public class LoadSettingsBox
  extends Box
{
  private int defaultHints;
  private int preloadDuration;
  private int preloadFlags;
  private int preloadStartTime;
  
  public LoadSettingsBox()
  {
    super(new Header(fourcc()));
  }
  
  public LoadSettingsBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public static String fourcc()
  {
    return "load";
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.putInt(this.preloadStartTime);
    paramByteBuffer.putInt(this.preloadDuration);
    paramByteBuffer.putInt(this.preloadFlags);
    paramByteBuffer.putInt(this.defaultHints);
  }
  
  public int getDefaultHints()
  {
    return this.defaultHints;
  }
  
  public int getPreloadDuration()
  {
    return this.preloadDuration;
  }
  
  public int getPreloadFlags()
  {
    return this.preloadFlags;
  }
  
  public int getPreloadStartTime()
  {
    return this.preloadStartTime;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    this.preloadStartTime = paramByteBuffer.getInt();
    this.preloadDuration = paramByteBuffer.getInt();
    this.preloadFlags = paramByteBuffer.getInt();
    this.defaultHints = paramByteBuffer.getInt();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/LoadSettingsBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */