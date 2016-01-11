package org.jcodec;

import java.nio.ByteBuffer;

public class MovieHeaderBox
  extends FullBox
{
  private long created;
  private long duration;
  private int[] matrix;
  private long modified;
  private int nextTrackId;
  private float rate;
  private int timescale;
  private float volume;
  
  public MovieHeaderBox()
  {
    super(new Header(fourcc()));
  }
  
  public MovieHeaderBox(int paramInt1, long paramLong1, float paramFloat1, float paramFloat2, long paramLong2, long paramLong3, int[] paramArrayOfInt, int paramInt2)
  {
    super(new Header(fourcc()));
    this.timescale = paramInt1;
    this.duration = paramLong1;
    this.rate = paramFloat1;
    this.volume = paramFloat2;
    this.created = paramLong2;
    this.modified = paramLong3;
    this.matrix = paramArrayOfInt;
    this.nextTrackId = paramInt2;
  }
  
  public static String fourcc()
  {
    return "mvhd";
  }
  
  private int[] readMatrix(ByteBuffer paramByteBuffer)
  {
    int[] arrayOfInt = new int[9];
    int i = 0;
    while (i < 9)
    {
      arrayOfInt[i] = paramByteBuffer.getInt();
      i += 1;
    }
    return arrayOfInt;
  }
  
  private float readRate(ByteBuffer paramByteBuffer)
  {
    return paramByteBuffer.getInt() / 65536.0F;
  }
  
  private float readVolume(ByteBuffer paramByteBuffer)
  {
    return paramByteBuffer.getShort() / 256.0F;
  }
  
  private void writeFixed1616(ByteBuffer paramByteBuffer, float paramFloat)
  {
    paramByteBuffer.putInt((int)(paramFloat * 65536.0D));
  }
  
  private void writeFixed88(ByteBuffer paramByteBuffer, float paramFloat)
  {
    paramByteBuffer.putShort((short)(int)(paramFloat * 256.0D));
  }
  
  private void writeMatrix(ByteBuffer paramByteBuffer)
  {
    int i = 0;
    while (i < Math.min(9, this.matrix.length))
    {
      paramByteBuffer.putInt(this.matrix[i]);
      i += 1;
    }
    i = Math.min(9, this.matrix.length);
    while (i < 9)
    {
      paramByteBuffer.putInt(0);
      i += 1;
    }
  }
  
  public void doWrite(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
    paramByteBuffer.putInt(TimeUtil.toMovTime(this.created));
    paramByteBuffer.putInt(TimeUtil.toMovTime(this.modified));
    paramByteBuffer.putInt(this.timescale);
    paramByteBuffer.putInt((int)this.duration);
    writeFixed1616(paramByteBuffer, this.rate);
    writeFixed88(paramByteBuffer, this.volume);
    paramByteBuffer.put(new byte[10]);
    writeMatrix(paramByteBuffer);
    paramByteBuffer.put(new byte[24]);
    paramByteBuffer.putInt(this.nextTrackId);
  }
  
  protected void dump(StringBuilder paramStringBuilder)
  {
    super.dump(paramStringBuilder);
    paramStringBuilder.append(": ");
    ToJSON.toJSON(this, paramStringBuilder, new String[] { "timescale", "duration", "rate", "volume", "created", "modified", "nextTrackId" });
  }
  
  public long getDuration()
  {
    return this.duration;
  }
  
  public int getNextTrackId()
  {
    return this.nextTrackId;
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
    }
    for (this.duration = paramByteBuffer.getInt();; this.duration = paramByteBuffer.getLong())
    {
      this.rate = readRate(paramByteBuffer);
      this.volume = readVolume(paramByteBuffer);
      NIOUtils.skip(paramByteBuffer, 10);
      this.matrix = readMatrix(paramByteBuffer);
      NIOUtils.skip(paramByteBuffer, 24);
      this.nextTrackId = paramByteBuffer.getInt();
      return;
      if (this.version != 1) {
        break;
      }
      this.created = TimeUtil.fromMovTime((int)paramByteBuffer.getLong());
      this.modified = TimeUtil.fromMovTime((int)paramByteBuffer.getLong());
      this.timescale = paramByteBuffer.getInt();
    }
    throw new RuntimeException("Unsupported version");
  }
  
  public void setDuration(long paramLong)
  {
    this.duration = paramLong;
  }
  
  public void setNextTrackId(int paramInt)
  {
    this.nextTrackId = paramInt;
  }
  
  public void setTimescale(int paramInt)
  {
    this.timescale = paramInt;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/MovieHeaderBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */