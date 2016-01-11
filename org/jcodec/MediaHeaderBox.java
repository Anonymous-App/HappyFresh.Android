package org.jcodec;

import java.nio.ByteBuffer;

public class MediaHeaderBox
  extends FullBox
{
  private long created;
  private long duration;
  private int language;
  private long modified;
  private int quality;
  private int timescale;
  
  public MediaHeaderBox()
  {
    super(new Header(fourcc()));
  }
  
  public MediaHeaderBox(int paramInt1, long paramLong1, int paramInt2, long paramLong2, long paramLong3, int paramInt3)
  {
    super(new Header(fourcc()));
    this.timescale = paramInt1;
    this.duration = paramLong1;
    this.language = paramInt2;
    this.created = paramLong2;
    this.modified = paramLong3;
    this.quality = paramInt3;
  }
  
  public static String fourcc()
  {
    return "mdhd";
  }
  
  public void doWrite(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
    paramByteBuffer.putInt(TimeUtil.toMovTime(this.created));
    paramByteBuffer.putInt(TimeUtil.toMovTime(this.modified));
    paramByteBuffer.putInt(this.timescale);
    paramByteBuffer.putInt((int)this.duration);
    paramByteBuffer.putShort((short)this.language);
    paramByteBuffer.putShort((short)this.quality);
  }
  
  protected void dump(StringBuilder paramStringBuilder)
  {
    super.dump(paramStringBuilder);
    paramStringBuilder.append(": ");
    ToJSON.toJSON(this, paramStringBuilder, new String[] { "created", "modified", "timescale", "duration", "language", "quality" });
  }
  
  public long getDuration()
  {
    return this.duration;
  }
  
  public int getTimescale()
  {
    return this.timescale;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    super.parse(paramByteBuffer);
    if (this.version == 0)
    {
      this.created = TimeUtil.fromMovTime(paramByteBuffer.getInt());
      this.modified = TimeUtil.fromMovTime(paramByteBuffer.getInt());
      this.timescale = paramByteBuffer.getInt();
      this.duration = paramByteBuffer.getInt();
      return;
    }
    if (this.version == 1)
    {
      this.created = TimeUtil.fromMovTime((int)paramByteBuffer.getLong());
      this.modified = TimeUtil.fromMovTime((int)paramByteBuffer.getLong());
      this.timescale = paramByteBuffer.getInt();
      this.duration = paramByteBuffer.getLong();
      return;
    }
    throw new RuntimeException("Unsupported version");
  }
  
  public void setDuration(long paramLong)
  {
    this.duration = paramLong;
  }
  
  public void setTimescale(int paramInt)
  {
    this.timescale = paramInt;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/MediaHeaderBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */