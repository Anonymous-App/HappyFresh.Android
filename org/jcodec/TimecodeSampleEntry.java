package org.jcodec;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class TimecodeSampleEntry
  extends SampleEntry
{
  private static final MyFactory FACTORY = new MyFactory();
  public static final int FLAG_24HOURMAX = 2;
  public static final int FLAG_COUNTER = 8;
  public static final int FLAG_DROPFRAME = 1;
  public static final int FLAG_NEGATIVETIMEOK = 4;
  private int flags;
  private int frameDuration;
  private byte numFrames;
  private int timescale;
  
  public TimecodeSampleEntry()
  {
    super(new Header("tmcd"));
    this.factory = FACTORY;
  }
  
  public TimecodeSampleEntry(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super(new Header("tmcd"));
    this.flags = paramInt1;
    this.timescale = paramInt2;
    this.frameDuration = paramInt3;
    this.numFrames = ((byte)paramInt4);
  }
  
  public TimecodeSampleEntry(Header paramHeader)
  {
    super(paramHeader);
    this.factory = FACTORY;
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
    paramByteBuffer.putInt(0);
    paramByteBuffer.putInt(this.flags);
    paramByteBuffer.putInt(this.timescale);
    paramByteBuffer.putInt(this.frameDuration);
    paramByteBuffer.put(this.numFrames);
    paramByteBuffer.put((byte)-49);
  }
  
  public void dump(StringBuilder paramStringBuilder)
  {
    paramStringBuilder.append(this.header.getFourcc() + ": {\n");
    paramStringBuilder.append("entry: ");
    ToJSON.toJSON(this, paramStringBuilder, new String[] { "flags", "timescale", "frameDuration", "numFrames" });
    paramStringBuilder.append(",\nexts: [\n");
    dumpBoxes(paramStringBuilder);
    paramStringBuilder.append("\n]\n");
    paramStringBuilder.append("}\n");
  }
  
  public int getFlags()
  {
    return this.flags;
  }
  
  public int getFrameDuration()
  {
    return this.frameDuration;
  }
  
  public byte getNumFrames()
  {
    return this.numFrames;
  }
  
  public int getTimescale()
  {
    return this.timescale;
  }
  
  public boolean isDropFrame()
  {
    return (this.flags & 0x1) != 0;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    super.parse(paramByteBuffer);
    NIOUtils.skip(paramByteBuffer, 4);
    this.flags = paramByteBuffer.getInt();
    this.timescale = paramByteBuffer.getInt();
    this.frameDuration = paramByteBuffer.getInt();
    this.numFrames = paramByteBuffer.get();
    NIOUtils.skip(paramByteBuffer, 1);
  }
  
  public static class MyFactory
    extends BoxFactory
  {
    private Map<String, Class<? extends Box>> mappings = new HashMap();
    
    public Class<? extends Box> toClass(String paramString)
    {
      return (Class)this.mappings.get(paramString);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/TimecodeSampleEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */