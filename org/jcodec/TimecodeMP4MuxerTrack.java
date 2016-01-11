package org.jcodec;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class TimecodeMP4MuxerTrack
  extends FramesMP4MuxerTrack
{
  private TapeTimecode firstTimecode;
  private int fpsEstimate;
  private List<Packet> gop = new ArrayList();
  private List<Edit> lower = new ArrayList();
  private TapeTimecode prevTimecode;
  private long sampleDuration;
  private long samplePts;
  private int tcFrames;
  
  public TimecodeMP4MuxerTrack(SeekableByteChannel paramSeekableByteChannel, int paramInt1, int paramInt2)
  {
    super(paramSeekableByteChannel, paramInt1, TrackType.TIMECODE, paramInt2);
  }
  
  private void addTimecodeInt(Packet paramPacket)
    throws IOException
  {
    TapeTimecode localTapeTimecode = paramPacket.getTapeTimecode();
    boolean bool = isGap(this.prevTimecode, localTapeTimecode);
    this.prevTimecode = localTapeTimecode;
    if (bool)
    {
      outTimecodeSample();
      this.firstTimecode = localTapeTimecode;
      if (!localTapeTimecode.isDropFrame()) {
        break label100;
      }
    }
    label100:
    for (int i = 30;; i = -1)
    {
      this.fpsEstimate = i;
      this.samplePts += this.sampleDuration;
      this.sampleDuration = 0L;
      this.tcFrames = 0;
      this.sampleDuration += paramPacket.getDuration();
      this.tcFrames += 1;
      return;
    }
  }
  
  private boolean isGap(TapeTimecode paramTapeTimecode1, TapeTimecode paramTapeTimecode2)
  {
    boolean bool = false;
    if ((paramTapeTimecode1 == null) && (paramTapeTimecode2 != null)) {
      bool = true;
    }
    while (paramTapeTimecode1 == null) {
      return bool;
    }
    if (paramTapeTimecode2 == null) {
      return true;
    }
    if (paramTapeTimecode1.isDropFrame() != paramTapeTimecode2.isDropFrame()) {
      return true;
    }
    return isTimeGap(paramTapeTimecode1, paramTapeTimecode2);
  }
  
  private boolean isTimeGap(TapeTimecode paramTapeTimecode1, TapeTimecode paramTapeTimecode2)
  {
    boolean bool = false;
    int i = toSec(paramTapeTimecode2);
    int j = i - toSec(paramTapeTimecode1);
    if (j == 0)
    {
      j = paramTapeTimecode2.getFrame() - paramTapeTimecode1.getFrame();
      i = j;
      if (this.fpsEstimate != -1) {
        i = (this.fpsEstimate + j) % this.fpsEstimate;
      }
      if (i != 1) {
        bool = true;
      }
    }
    for (;;)
    {
      return bool;
      return false;
      if (j != 1) {
        break;
      }
      if (this.fpsEstimate == -1)
      {
        if (paramTapeTimecode2.getFrame() == 0)
        {
          this.fpsEstimate = (paramTapeTimecode1.getFrame() + 1);
          return false;
        }
        return true;
      }
      if ((paramTapeTimecode2.isDropFrame()) && (i % 60 == 0) && (i % 600 != 0)) {}
      for (i = 2; (paramTapeTimecode2.getFrame() != i) || (paramTapeTimecode1.getFrame() != this.fpsEstimate - 1); i = 0) {
        return true;
      }
    }
    return true;
  }
  
  private void outTimecodeSample()
    throws IOException
  {
    if (this.sampleDuration > 0L)
    {
      if (this.firstTimecode == null) {
        break label190;
      }
      if (this.fpsEstimate == -1) {
        this.fpsEstimate = (this.prevTimecode.getFrame() + 1);
      }
      if (!this.firstTimecode.isDropFrame()) {
        break label185;
      }
    }
    label185:
    for (int i = 1;; i = 0)
    {
      Object localObject = new TimecodeSampleEntry(i, this.timescale, (int)(this.sampleDuration / this.tcFrames), this.fpsEstimate);
      this.sampleEntries.add(localObject);
      localObject = ByteBuffer.allocate(4);
      ((ByteBuffer)localObject).putInt(toCounter(this.firstTimecode, this.fpsEstimate));
      ((ByteBuffer)localObject).flip();
      addFrame(new MP4Packet((ByteBuffer)localObject, this.samplePts, this.timescale, this.sampleDuration, 0L, true, null, this.samplePts, this.sampleEntries.size() - 1));
      this.lower.add(new Edit(this.sampleDuration, this.samplePts, 1.0F));
      return;
    }
    label190:
    this.lower.add(new Edit(this.sampleDuration, -1L, 1.0F));
  }
  
  private void processGop()
    throws IOException
  {
    if (this.gop.size() > 0)
    {
      Iterator localIterator = sortByDisplay(this.gop).iterator();
      while (localIterator.hasNext()) {
        addTimecodeInt((Packet)localIterator.next());
      }
      this.gop.clear();
    }
  }
  
  private List<Packet> sortByDisplay(List<Packet> paramList)
  {
    paramList = new ArrayList(paramList);
    Collections.sort(paramList, new Comparator()
    {
      public int compare(Packet paramAnonymousPacket1, Packet paramAnonymousPacket2)
      {
        int j = 1;
        int i;
        if ((paramAnonymousPacket1 == null) && (paramAnonymousPacket2 == null)) {
          i = 0;
        }
        do
        {
          do
          {
            return i;
            if (paramAnonymousPacket1 == null) {
              return -1;
            }
            i = j;
          } while (paramAnonymousPacket2 == null);
          i = j;
        } while (paramAnonymousPacket1.getDisplayOrder() > paramAnonymousPacket2.getDisplayOrder());
        if (paramAnonymousPacket1.getDisplayOrder() == paramAnonymousPacket2.getDisplayOrder()) {
          return 0;
        }
        return -1;
      }
    });
    return paramList;
  }
  
  private int toCounter(TapeTimecode paramTapeTimecode, int paramInt)
  {
    int i = toSec(paramTapeTimecode) * paramInt + paramTapeTimecode.getFrame();
    paramInt = i;
    if (paramTapeTimecode.isDropFrame())
    {
      long l1 = i / 18000;
      long l2 = i % 18000;
      paramInt = (int)(i - (18L * l1 + 2L * ((l2 - 2L) / 1800L)));
    }
    return paramInt;
  }
  
  private int toSec(TapeTimecode paramTapeTimecode)
  {
    return paramTapeTimecode.getHour() * 3600 + paramTapeTimecode.getMinute() * 60 + paramTapeTimecode.getSecond();
  }
  
  public void addTimecode(Packet paramPacket)
    throws IOException
  {
    if (paramPacket.isKeyFrame()) {
      processGop();
    }
    this.gop.add(new Packet(paramPacket, (ByteBuffer)null));
  }
  
  protected Box finish(MovieHeaderBox paramMovieHeaderBox)
    throws IOException
  {
    processGop();
    outTimecodeSample();
    if (this.sampleEntries.size() == 0) {
      return null;
    }
    if (this.edits != null) {}
    for (this.edits = Util.editsOnEdits(new Rational(1, 1), this.lower, this.edits);; this.edits = this.lower) {
      return super.finish(paramMovieHeaderBox);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/TimecodeMP4MuxerTrack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */