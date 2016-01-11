package org.jcodec;

import java.nio.ByteBuffer;

public class FielExtension
  extends Box
{
  private int order;
  private int type;
  
  public FielExtension()
  {
    super(new Header(fourcc()));
  }
  
  public FielExtension(byte paramByte1, byte paramByte2)
  {
    super(new Header(fourcc()));
    this.type = paramByte1;
    this.order = paramByte2;
  }
  
  public static String fourcc()
  {
    return "fiel";
  }
  
  public void doWrite(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.put((byte)this.type);
    paramByteBuffer.put((byte)this.order);
  }
  
  public String getOrderInterpretation()
  {
    if (isInterlaced()) {}
    switch (this.order)
    {
    default: 
      return "";
    case 1: 
      return "top";
    case 6: 
      return "bottom";
    case 9: 
      return "bottomtop";
    }
    return "topbottom";
  }
  
  public boolean isInterlaced()
  {
    return this.type == 2;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    this.type = (paramByteBuffer.get() & 0xFF);
    if (isInterlaced()) {
      this.order = (paramByteBuffer.get() & 0xFF);
    }
  }
  
  public boolean topFieldFirst()
  {
    return (this.order == 1) || (this.order == 6);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/FielExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */