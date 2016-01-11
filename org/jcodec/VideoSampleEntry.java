package org.jcodec;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class VideoSampleEntry
  extends SampleEntry
{
  private static final MyFactory FACTORY = new MyFactory();
  private short clrTbl;
  private String compressorName;
  private short depth;
  private short frameCount;
  private float hRes;
  private short height;
  private short revision;
  private int spacialQual;
  private int temporalQual;
  private float vRes;
  private String vendor;
  private short version;
  private short width;
  
  public VideoSampleEntry(Header paramHeader)
  {
    super(paramHeader);
    this.factory = FACTORY;
  }
  
  public VideoSampleEntry(Header paramHeader, short paramShort1, short paramShort2, String paramString1, int paramInt1, int paramInt2, short paramShort3, short paramShort4, long paramLong1, long paramLong2, short paramShort5, String paramString2, short paramShort6, short paramShort7, short paramShort8)
  {
    super(paramHeader, paramShort7);
    this.factory = FACTORY;
    this.version = paramShort1;
    this.revision = paramShort2;
    this.vendor = paramString1;
    this.temporalQual = paramInt1;
    this.spacialQual = paramInt2;
    this.width = paramShort3;
    this.height = paramShort4;
    this.hRes = ((float)paramLong1);
    this.vRes = ((float)paramLong2);
    this.frameCount = paramShort5;
    this.compressorName = paramString2;
    this.depth = paramShort6;
    this.clrTbl = paramShort8;
  }
  
  public void doWrite(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
    paramByteBuffer.putShort(this.version);
    paramByteBuffer.putShort(this.revision);
    paramByteBuffer.put(JCodecUtil.asciiString(this.vendor), 0, 4);
    paramByteBuffer.putInt(this.temporalQual);
    paramByteBuffer.putInt(this.spacialQual);
    paramByteBuffer.putShort(this.width);
    paramByteBuffer.putShort(this.height);
    paramByteBuffer.putInt((int)(this.hRes * 65536.0F));
    paramByteBuffer.putInt((int)(this.vRes * 65536.0F));
    paramByteBuffer.putInt(0);
    paramByteBuffer.putShort(this.frameCount);
    NIOUtils.writePascalString(paramByteBuffer, this.compressorName, 31);
    paramByteBuffer.putShort(this.depth);
    paramByteBuffer.putShort(this.clrTbl);
    writeExtensions(paramByteBuffer);
  }
  
  public void dump(StringBuilder paramStringBuilder)
  {
    paramStringBuilder.append(this.header.getFourcc() + ": {\n");
    paramStringBuilder.append("entry: ");
    ToJSON.toJSON(this, paramStringBuilder, new String[] { "version", "revision", "vendor", "temporalQual", "spacialQual", "width", "height", "hRes", "vRes", "frameCount", "compressorName", "depth", "clrTbl" });
    paramStringBuilder.append(",\nexts: [\n");
    dumpBoxes(paramStringBuilder);
    paramStringBuilder.append("\n]\n");
    paramStringBuilder.append("}\n");
  }
  
  public String getCompressorName()
  {
    return this.compressorName;
  }
  
  public long getDepth()
  {
    return this.depth;
  }
  
  public long getFrameCount()
  {
    return this.frameCount;
  }
  
  public int getHeight()
  {
    return this.height;
  }
  
  public String getVendor()
  {
    return this.vendor;
  }
  
  public int getWidth()
  {
    return this.width;
  }
  
  public float gethRes()
  {
    return this.hRes;
  }
  
  public float getvRes()
  {
    return this.vRes;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    super.parse(paramByteBuffer);
    this.version = paramByteBuffer.getShort();
    this.revision = paramByteBuffer.getShort();
    this.vendor = NIOUtils.readString(paramByteBuffer, 4);
    this.temporalQual = paramByteBuffer.getInt();
    this.spacialQual = paramByteBuffer.getInt();
    this.width = paramByteBuffer.getShort();
    this.height = paramByteBuffer.getShort();
    this.hRes = (paramByteBuffer.getInt() / 65536.0F);
    this.vRes = (paramByteBuffer.getInt() / 65536.0F);
    paramByteBuffer.getInt();
    this.frameCount = paramByteBuffer.getShort();
    this.compressorName = NIOUtils.readPascalString(paramByteBuffer, 31);
    this.depth = paramByteBuffer.getShort();
    this.clrTbl = paramByteBuffer.getShort();
    parseExtensions(paramByteBuffer);
  }
  
  public static class MyFactory
    extends BoxFactory
  {
    private Map<String, Class<? extends Box>> mappings = new HashMap();
    
    public MyFactory()
    {
      this.mappings.put(PixelAspectExt.fourcc(), PixelAspectExt.class);
      this.mappings.put(ColorExtension.fourcc(), ColorExtension.class);
      this.mappings.put(GamaExtension.fourcc(), GamaExtension.class);
      this.mappings.put(CleanApertureExtension.fourcc(), CleanApertureExtension.class);
      this.mappings.put(FielExtension.fourcc(), FielExtension.class);
    }
    
    public Class<? extends Box> toClass(String paramString)
    {
      return (Class)this.mappings.get(paramString);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/VideoSampleEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */