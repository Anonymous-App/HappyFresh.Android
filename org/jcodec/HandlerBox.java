package org.jcodec;

import java.nio.ByteBuffer;

public class HandlerBox
  extends FullBox
{
  private int componentFlags;
  private int componentFlagsMask;
  private String componentManufacturer;
  private String componentName;
  private String componentSubType;
  private String componentType;
  
  public HandlerBox()
  {
    super(new Header(fourcc()));
  }
  
  public HandlerBox(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2)
  {
    super(new Header("hdlr"));
    this.componentType = paramString1;
    this.componentSubType = paramString2;
    this.componentManufacturer = paramString3;
    this.componentFlags = paramInt1;
    this.componentFlagsMask = paramInt2;
    this.componentName = "";
  }
  
  public static String fourcc()
  {
    return "hdlr";
  }
  
  public void doWrite(ByteBuffer paramByteBuffer)
  {
    super.doWrite(paramByteBuffer);
    paramByteBuffer.put(JCodecUtil.asciiString(this.componentType));
    paramByteBuffer.put(JCodecUtil.asciiString(this.componentSubType));
    paramByteBuffer.put(JCodecUtil.asciiString(this.componentManufacturer));
    paramByteBuffer.putInt(this.componentFlags);
    paramByteBuffer.putInt(this.componentFlagsMask);
    if (this.componentName != null) {
      paramByteBuffer.put(JCodecUtil.asciiString(this.componentName));
    }
  }
  
  public int getComponentFlags()
  {
    return this.componentFlags;
  }
  
  public int getComponentFlagsMask()
  {
    return this.componentFlagsMask;
  }
  
  public String getComponentManufacturer()
  {
    return this.componentManufacturer;
  }
  
  public String getComponentSubType()
  {
    return this.componentSubType;
  }
  
  public String getComponentType()
  {
    return this.componentType;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    super.parse(paramByteBuffer);
    this.componentType = NIOUtils.readString(paramByteBuffer, 4);
    this.componentSubType = NIOUtils.readString(paramByteBuffer, 4);
    this.componentManufacturer = NIOUtils.readString(paramByteBuffer, 4);
    this.componentFlags = paramByteBuffer.getInt();
    this.componentFlagsMask = paramByteBuffer.getInt();
    this.componentName = NIOUtils.readString(paramByteBuffer, paramByteBuffer.remaining());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/HandlerBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */