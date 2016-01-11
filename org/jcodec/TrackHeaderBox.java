package org.jcodec;

import java.nio.ByteBuffer;

public class TrackHeaderBox
  extends FullBox
{
  private long altGroup;
  private long created;
  private long duration;
  private float height;
  private short layer;
  private int[] matrix;
  private long modified;
  private int trackId;
  private float volume;
  private float width;
  
  public TrackHeaderBox()
  {
    super(new Header(fourcc()));
  }
  
  public TrackHeaderBox(int paramInt, long paramLong1, float paramFloat1, float paramFloat2, long paramLong2, long paramLong3, float paramFloat3, short paramShort, long paramLong4, int[] paramArrayOfInt)
  {
    super(new Header(fourcc()));
    this.trackId = paramInt;
    this.duration = paramLong1;
    this.width = paramFloat1;
    this.height = paramFloat2;
    this.created = paramLong2;
    this.modified = paramLong3;
    this.volume = paramFloat3;
    this.layer = paramShort;
    this.altGroup = paramLong4;
    this.matrix = paramArrayOfInt;
  }
  
  public static String fourcc()
  {
    return "tkhd";
  }
  
  private void readMatrix(ByteBuffer paramByteBuffer)
  {
    this.matrix = new int[9];
    int i = 0;
    while (i < 9)
    {
      this.matrix[i] = paramByteBuffer.getInt();
      i += 1;
    }
  }
  
  private float readVolume(ByteBuffer paramByteBuffer)
  {
    return (float)(paramByteBuffer.getShort() / 256.0D);
  }
  
  private void writeMatrix(ByteBuffer paramByteBuffer)
  {
    int i = 0;
    while (i < 9)
    {
      paramByteBuffer.putInt(this.matrix[i]);
      i += 1;
    }
  }
  
  private void writeVolume(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.putShort((short)(int)(this.volume * 256.0D));
  }
  
  public void doWrite(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
    paramByteBuffer.putInt(TimeUtil.toMovTime(this.created));
    paramByteBuffer.putInt(TimeUtil.toMovTime(this.modified));
    paramByteBuffer.putInt(this.trackId);
    paramByteBuffer.putInt(0);
    paramByteBuffer.putInt((int)this.duration);
    paramByteBuffer.putInt(0);
    paramByteBuffer.putInt(0);
    paramByteBuffer.putShort(this.layer);
    paramByteBuffer.putShort((short)(int)this.altGroup);
    writeVolume(paramByteBuffer);
    paramByteBuffer.putShort((short)0);
    writeMatrix(paramByteBuffer);
    paramByteBuffer.putInt((int)(this.width * 65536.0F));
    paramByteBuffer.putInt((int)(this.height * 65536.0F));
  }
  
  protected void dump(StringBuilder paramStringBuilder)
  {
    super.dump(paramStringBuilder);
    paramStringBuilder.append(": ");
    ToJSON.toJSON(this, paramStringBuilder, new String[] { "trackId", "duration", "width", "height", "created", "modified", "volume", "layer", "altGroup" });
  }
  
  public long getDuration()
  {
    return this.duration;
  }
  
  public float getHeight()
  {
    return this.height;
  }
  
  public short getLayer()
  {
    return this.layer;
  }
  
  public int[] getMatrix()
  {
    return this.matrix;
  }
  
  public int getNo()
  {
    return this.trackId;
  }
  
  public float getVolume()
  {
    return this.volume;
  }
  
  public float getWidth()
  {
    return this.width;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    super.parse(paramByteBuffer);
    if (this.version == 0)
    {
      this.created = TimeUtil.fromMovTime(paramByteBuffer.getInt());
      this.modified = TimeUtil.fromMovTime(paramByteBuffer.getInt());
      this.trackId = paramByteBuffer.getInt();
      paramByteBuffer.getInt();
      if (this.version != 0) {
        break label161;
      }
    }
    label161:
    for (this.duration = paramByteBuffer.getInt();; this.duration = paramByteBuffer.getLong())
    {
      paramByteBuffer.getInt();
      paramByteBuffer.getInt();
      this.layer = paramByteBuffer.getShort();
      this.altGroup = paramByteBuffer.getShort();
      this.volume = readVolume(paramByteBuffer);
      paramByteBuffer.getShort();
      readMatrix(paramByteBuffer);
      this.width = (paramByteBuffer.getInt() / 65536.0F);
      this.height = (paramByteBuffer.getInt() / 65536.0F);
      return;
      this.created = TimeUtil.fromMovTime((int)paramByteBuffer.getLong());
      this.modified = TimeUtil.fromMovTime((int)paramByteBuffer.getLong());
      break;
    }
  }
  
  public void setDuration(long paramLong)
  {
    this.duration = paramLong;
  }
  
  public void setHeight(float paramFloat)
  {
    this.height = paramFloat;
  }
  
  public void setNo(int paramInt)
  {
    this.trackId = paramInt;
  }
  
  public void setWidth(float paramFloat)
  {
    this.width = paramFloat;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/TrackHeaderBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */