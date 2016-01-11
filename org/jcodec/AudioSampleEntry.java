package org.jcodec;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AudioSampleEntry
  extends SampleEntry
{
  private static final MyFactory FACTORY = new MyFactory();
  public static Set<String> pcms = new HashSet();
  private int bytesPerFrame;
  private int bytesPerPkt;
  private int bytesPerSample;
  private short channelCount;
  private int compressionId;
  private int lpcmFlags;
  private int pktSize;
  private short revision;
  private float sampleRate;
  private short sampleSize;
  private int samplesPerPkt;
  private int vendor;
  private short version;
  
  static
  {
    pcms.add("raw ");
    pcms.add("twos");
    pcms.add("sowt");
    pcms.add("fl32");
    pcms.add("fl64");
    pcms.add("in24");
    pcms.add("in32");
    pcms.add("lpcm");
  }
  
  public AudioSampleEntry(Header paramHeader)
  {
    super(paramHeader);
    this.factory = FACTORY;
  }
  
  public AudioSampleEntry(Header paramHeader, short paramShort1, short paramShort2, short paramShort3, int paramInt1, short paramShort4, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, short paramShort5)
  {
    super(paramHeader, paramShort1);
    this.channelCount = paramShort2;
    this.sampleSize = paramShort3;
    this.sampleRate = paramInt1;
    this.revision = paramShort4;
    this.vendor = paramInt2;
    this.compressionId = paramInt3;
    this.pktSize = paramInt4;
    this.samplesPerPkt = paramInt5;
    this.bytesPerPkt = paramInt6;
    this.bytesPerFrame = paramInt7;
    this.bytesPerSample = paramInt8;
    this.version = paramShort5;
  }
  
  public int calcFrameSize()
  {
    if ((this.version == 0) || (this.bytesPerFrame == 0)) {
      return (this.sampleSize >> 3) * this.channelCount;
    }
    return this.bytesPerFrame;
  }
  
  public int calcSampleSize()
  {
    return calcFrameSize() / this.channelCount;
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
    paramByteBuffer.putShort(this.version);
    paramByteBuffer.putShort(this.revision);
    paramByteBuffer.putInt(this.vendor);
    if (this.version < 2)
    {
      paramByteBuffer.putShort(this.channelCount);
      if (this.version == 0)
      {
        paramByteBuffer.putShort(this.sampleSize);
        paramByteBuffer.putShort((short)this.compressionId);
        paramByteBuffer.putShort((short)this.pktSize);
        paramByteBuffer.putInt((int)Math.round(this.sampleRate * 65536.0D));
        if (this.version == 1)
        {
          paramByteBuffer.putInt(this.samplesPerPkt);
          paramByteBuffer.putInt(this.bytesPerPkt);
          paramByteBuffer.putInt(this.bytesPerFrame);
          paramByteBuffer.putInt(this.bytesPerSample);
          writeExtensions(paramByteBuffer);
        }
      }
    }
    while (this.version != 2) {
      for (;;)
      {
        return;
        paramByteBuffer.putShort((short)16);
      }
    }
    paramByteBuffer.putShort((short)3);
    paramByteBuffer.putShort((short)16);
    paramByteBuffer.putShort((short)-2);
    paramByteBuffer.putShort((short)0);
    paramByteBuffer.putInt(65536);
    paramByteBuffer.putInt(72);
    paramByteBuffer.putLong(Double.doubleToLongBits(this.sampleRate));
    paramByteBuffer.putInt(this.channelCount);
    paramByteBuffer.putInt(2130706432);
    paramByteBuffer.putInt(this.sampleSize);
    paramByteBuffer.putInt(this.lpcmFlags);
    paramByteBuffer.putInt(this.bytesPerFrame);
    paramByteBuffer.putInt(this.samplesPerPkt);
    writeExtensions(paramByteBuffer);
  }
  
  public void dump(StringBuilder paramStringBuilder)
  {
    paramStringBuilder.append(this.header.getFourcc() + ": {\n");
    paramStringBuilder.append("entry: ");
    ToJSON.toJSON(this, paramStringBuilder, new String[] { "channelCount", "sampleSize", "sampleRat", "revision", "vendor", "compressionId", "pktSize", "samplesPerPkt", "bytesPerPkt", "bytesPerFrame", "bytesPerSample", "version", "lpcmFlags" });
    paramStringBuilder.append(",\nexts: [\n");
    dumpBoxes(paramStringBuilder);
    paramStringBuilder.append("\n]\n");
    paramStringBuilder.append("}\n");
  }
  
  public int getBytesPerFrame()
  {
    return this.bytesPerFrame;
  }
  
  public int getBytesPerSample()
  {
    return this.bytesPerSample;
  }
  
  public short getChannelCount()
  {
    return this.channelCount;
  }
  
  public float getSampleRate()
  {
    return this.sampleRate;
  }
  
  public short getSampleSize()
  {
    return this.sampleSize;
  }
  
  public short getVersion()
  {
    return this.version;
  }
  
  public boolean isPCM()
  {
    return pcms.contains(this.header.getFourcc());
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    super.parse(paramByteBuffer);
    this.version = paramByteBuffer.getShort();
    this.revision = paramByteBuffer.getShort();
    this.vendor = paramByteBuffer.getInt();
    this.channelCount = paramByteBuffer.getShort();
    this.sampleSize = paramByteBuffer.getShort();
    this.compressionId = paramByteBuffer.getShort();
    this.pktSize = paramByteBuffer.getShort();
    this.sampleRate = ((float)(paramByteBuffer.getInt() & 0xFFFFFFFF) / 65536.0F);
    if (this.version == 1)
    {
      this.samplesPerPkt = paramByteBuffer.getInt();
      this.bytesPerPkt = paramByteBuffer.getInt();
      this.bytesPerFrame = paramByteBuffer.getInt();
      this.bytesPerSample = paramByteBuffer.getInt();
    }
    for (;;)
    {
      parseExtensions(paramByteBuffer);
      return;
      if (this.version == 2)
      {
        paramByteBuffer.getInt();
        this.sampleRate = ((float)Double.longBitsToDouble(paramByteBuffer.getLong()));
        this.channelCount = ((short)paramByteBuffer.getInt());
        paramByteBuffer.getInt();
        this.sampleSize = ((short)paramByteBuffer.getInt());
        this.lpcmFlags = paramByteBuffer.getInt();
        this.bytesPerFrame = paramByteBuffer.getInt();
        this.samplesPerPkt = paramByteBuffer.getInt();
      }
    }
  }
  
  public static class MyFactory
    extends BoxFactory
  {
    private Map<String, Class<? extends Box>> mappings = new HashMap();
    
    public MyFactory()
    {
      this.mappings.put(WaveExtension.fourcc(), WaveExtension.class);
      this.mappings.put(ChannelBox.fourcc(), ChannelBox.class);
      this.mappings.put("esds", LeafBox.class);
    }
    
    public Class<? extends Box> toClass(String paramString)
    {
      return (Class)this.mappings.get(paramString);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/AudioSampleEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */