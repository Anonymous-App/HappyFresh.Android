package org.jcodec;

import java.nio.ByteBuffer;

public class NALUnit
{
  public int nal_ref_idc;
  public NALUnitType type;
  
  public NALUnit(NALUnitType paramNALUnitType, int paramInt)
  {
    this.type = paramNALUnitType;
    this.nal_ref_idc = paramInt;
  }
  
  public static NALUnit read(ByteBuffer paramByteBuffer)
  {
    int i = paramByteBuffer.get() & 0xFF;
    return new NALUnit(NALUnitType.fromValue(i & 0x1F), i >> 5 & 0x3);
  }
  
  public void write(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.put((byte)(this.type.getValue() | this.nal_ref_idc << 5));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/NALUnit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */