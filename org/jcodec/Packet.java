package org.jcodec;

import java.nio.ByteBuffer;
import java.util.Comparator;

public class Packet
{
  public static final Comparator<Packet> FRAME_ASC = new Comparator()
  {
    public int compare(Packet paramAnonymousPacket1, Packet paramAnonymousPacket2)
    {
      int j = -1;
      int i;
      if ((paramAnonymousPacket1 == null) && (paramAnonymousPacket2 == null)) {
        i = 0;
      }
      do
      {
        do
        {
          return i;
          i = j;
        } while (paramAnonymousPacket1 == null);
        if (paramAnonymousPacket2 == null) {
          return 1;
        }
        i = j;
      } while (paramAnonymousPacket1.frameNo < paramAnonymousPacket2.frameNo);
      if (paramAnonymousPacket1.frameNo == paramAnonymousPacket2.frameNo) {
        return 0;
      }
      return 1;
    }
  };
  private ByteBuffer data;
  private int displayOrder;
  private long duration;
  private long frameNo;
  private boolean keyFrame;
  private long pts;
  private TapeTimecode tapeTimecode;
  private long timescale;
  
  public Packet(ByteBuffer paramByteBuffer, long paramLong1, long paramLong2, long paramLong3, long paramLong4, boolean paramBoolean, TapeTimecode paramTapeTimecode)
  {
    this(paramByteBuffer, paramLong1, paramLong2, paramLong3, paramLong4, paramBoolean, paramTapeTimecode, 0);
  }
  
  public Packet(ByteBuffer paramByteBuffer, long paramLong1, long paramLong2, long paramLong3, long paramLong4, boolean paramBoolean, TapeTimecode paramTapeTimecode, int paramInt)
  {
    this.data = paramByteBuffer;
    this.pts = paramLong1;
    this.timescale = paramLong2;
    this.duration = paramLong3;
    this.frameNo = paramLong4;
    this.keyFrame = paramBoolean;
    this.tapeTimecode = paramTapeTimecode;
    this.displayOrder = paramInt;
  }
  
  public Packet(Packet paramPacket)
  {
    this(paramPacket.data, paramPacket.pts, paramPacket.timescale, paramPacket.duration, paramPacket.frameNo, paramPacket.keyFrame, paramPacket.tapeTimecode);
    this.displayOrder = paramPacket.displayOrder;
  }
  
  public Packet(Packet paramPacket, ByteBuffer paramByteBuffer)
  {
    this(paramByteBuffer, paramPacket.pts, paramPacket.timescale, paramPacket.duration, paramPacket.frameNo, paramPacket.keyFrame, paramPacket.tapeTimecode);
    this.displayOrder = paramPacket.displayOrder;
  }
  
  public Packet(Packet paramPacket, TapeTimecode paramTapeTimecode)
  {
    this(paramPacket.data, paramPacket.pts, paramPacket.timescale, paramPacket.duration, paramPacket.frameNo, paramPacket.keyFrame, paramTapeTimecode);
    this.displayOrder = paramPacket.displayOrder;
  }
  
  public ByteBuffer getData()
  {
    return this.data;
  }
  
  public int getDisplayOrder()
  {
    return this.displayOrder;
  }
  
  public long getDuration()
  {
    return this.duration;
  }
  
  public double getDurationD()
  {
    return this.duration / this.timescale;
  }
  
  public long getFrameNo()
  {
    return this.frameNo;
  }
  
  public long getPts()
  {
    return this.pts;
  }
  
  public double getPtsD()
  {
    return this.pts / this.timescale;
  }
  
  public RationalLarge getPtsR()
  {
    return RationalLarge.R(this.pts, this.timescale);
  }
  
  public TapeTimecode getTapeTimecode()
  {
    return this.tapeTimecode;
  }
  
  public long getTimescale()
  {
    return this.timescale;
  }
  
  public boolean isKeyFrame()
  {
    return this.keyFrame;
  }
  
  public void setData(ByteBuffer paramByteBuffer)
  {
    this.data = paramByteBuffer;
  }
  
  public void setDisplayOrder(int paramInt)
  {
    this.displayOrder = paramInt;
  }
  
  public void setTapeTimecode(TapeTimecode paramTapeTimecode)
  {
    this.tapeTimecode = paramTapeTimecode;
  }
  
  public void setTimescale(int paramInt)
  {
    this.timescale = paramInt;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/Packet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */