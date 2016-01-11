package org.jcodec;

import java.nio.ByteBuffer;

public class MP4Packet
  extends Packet
{
  private int entryNo;
  private long fileOff;
  private long mediaPts;
  private int size;
  
  public MP4Packet(ByteBuffer paramByteBuffer, long paramLong1, long paramLong2, long paramLong3, long paramLong4, boolean paramBoolean, TapeTimecode paramTapeTimecode, long paramLong5, int paramInt)
  {
    super(paramByteBuffer, paramLong1, paramLong2, paramLong3, paramLong4, paramBoolean, paramTapeTimecode);
    this.mediaPts = paramLong5;
    this.entryNo = paramInt;
  }
  
  public MP4Packet(ByteBuffer paramByteBuffer, long paramLong1, long paramLong2, long paramLong3, long paramLong4, boolean paramBoolean, TapeTimecode paramTapeTimecode, long paramLong5, int paramInt1, long paramLong6, int paramInt2)
  {
    super(paramByteBuffer, paramLong1, paramLong2, paramLong3, paramLong4, paramBoolean, paramTapeTimecode);
    this.mediaPts = paramLong5;
    this.entryNo = paramInt1;
    this.fileOff = paramLong6;
    this.size = paramInt2;
  }
  
  public MP4Packet(MP4Packet paramMP4Packet)
  {
    super(paramMP4Packet);
    this.mediaPts = paramMP4Packet.mediaPts;
    this.entryNo = paramMP4Packet.entryNo;
  }
  
  public MP4Packet(MP4Packet paramMP4Packet, ByteBuffer paramByteBuffer)
  {
    super(paramMP4Packet, paramByteBuffer);
    this.mediaPts = paramMP4Packet.mediaPts;
    this.entryNo = paramMP4Packet.entryNo;
  }
  
  public MP4Packet(MP4Packet paramMP4Packet, TapeTimecode paramTapeTimecode)
  {
    super(paramMP4Packet, paramTapeTimecode);
    this.mediaPts = paramMP4Packet.mediaPts;
    this.entryNo = paramMP4Packet.entryNo;
  }
  
  public MP4Packet(Packet paramPacket, long paramLong, int paramInt)
  {
    super(paramPacket);
    this.mediaPts = paramLong;
    this.entryNo = paramInt;
  }
  
  public int getEntryNo()
  {
    return this.entryNo;
  }
  
  public long getFileOff()
  {
    return this.fileOff;
  }
  
  public long getMediaPts()
  {
    return this.mediaPts;
  }
  
  public int getSize()
  {
    return this.size;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/MP4Packet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */