package org.jcodec;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import junit.framework.Assert;

public class FramesMP4MuxerTrack
  extends AbstractMP4MuxerTrack
{
  private boolean allIframes = true;
  private LongArrayList chunkOffsets = new LongArrayList();
  private List<CompositionOffsetsBox.Entry> compositionOffsets = new ArrayList();
  private long curDuration = -1L;
  private int curFrame;
  private IntArrayList iframes = new IntArrayList();
  private int lastCompositionOffset = 0;
  private int lastCompositionSamples = 0;
  private int lastEntry = -1;
  private SeekableByteChannel out;
  private long ptsEstimate = 0L;
  private long sameDurCount = 0L;
  private List<TimeToSampleBox.TimeToSampleEntry> sampleDurations = new ArrayList();
  private IntArrayList sampleSizes = new IntArrayList();
  private TimecodeMP4MuxerTrack timecodeTrack;
  private long trackTotalDuration;
  
  public FramesMP4MuxerTrack(SeekableByteChannel paramSeekableByteChannel, int paramInt1, TrackType paramTrackType, int paramInt2)
  {
    super(paramInt1, paramTrackType, paramInt2);
    this.out = paramSeekableByteChannel;
    setTgtChunkDuration(new Rational(1, 1), Unit.FRAME);
  }
  
  public static int minOffset(List<CompositionOffsetsBox.Entry> paramList)
  {
    int i = Integer.MAX_VALUE;
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      CompositionOffsetsBox.Entry localEntry = (CompositionOffsetsBox.Entry)paramList.next();
      if (localEntry.getOffset() < i) {
        i = localEntry.getOffset();
      }
    }
    return i;
  }
  
  private void outChunkIfNeeded(int paramInt)
    throws IOException
  {
    boolean bool;
    if ((this.tgtChunkDurationUnit == Unit.FRAME) || (this.tgtChunkDurationUnit == Unit.SEC))
    {
      bool = true;
      Assert.assertTrue(bool);
      if ((this.tgtChunkDurationUnit != Unit.FRAME) || (this.curChunk.size() * this.tgtChunkDuration.getDen() != this.tgtChunkDuration.getNum())) {
        break label74;
      }
      outChunk(paramInt);
    }
    label74:
    while ((this.tgtChunkDurationUnit != Unit.SEC) || (this.chunkDuration <= 0L) || (this.chunkDuration * this.tgtChunkDuration.getDen() < this.tgtChunkDuration.getNum() * this.timescale))
    {
      return;
      bool = false;
      break;
    }
    outChunk(paramInt);
  }
  
  private void processTimecode(MP4Packet paramMP4Packet)
    throws IOException
  {
    if (this.timecodeTrack != null) {
      this.timecodeTrack.addTimecode(paramMP4Packet);
    }
  }
  
  private void putCompositionOffsets(NodeBox paramNodeBox)
  {
    Object localObject1;
    Object localObject2;
    if (this.compositionOffsets.size() > 0)
    {
      this.compositionOffsets.add(new CompositionOffsetsBox.Entry(this.lastCompositionSamples, this.lastCompositionOffset));
      int i = minOffset(this.compositionOffsets);
      if (i > 0)
      {
        localObject1 = this.compositionOffsets.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (CompositionOffsetsBox.Entry)((Iterator)localObject1).next();
          ((CompositionOffsetsBox.Entry)localObject2).offset -= i;
        }
      }
      localObject1 = (CompositionOffsetsBox.Entry)this.compositionOffsets.get(0);
      if (((CompositionOffsetsBox.Entry)localObject1).getOffset() > 0)
      {
        if (this.edits != null) {
          break label188;
        }
        this.edits = new ArrayList();
        this.edits.add(new Edit(this.trackTotalDuration, ((CompositionOffsetsBox.Entry)localObject1).getOffset(), 1.0F));
      }
    }
    for (;;)
    {
      paramNodeBox.add(new CompositionOffsetsBox((CompositionOffsetsBox.Entry[])this.compositionOffsets.toArray(new CompositionOffsetsBox.Entry[0])));
      return;
      label188:
      localObject2 = this.edits.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        Edit localEdit = (Edit)((Iterator)localObject2).next();
        localEdit.setMediaTime(localEdit.getMediaTime() + ((CompositionOffsetsBox.Entry)localObject1).getOffset());
      }
    }
  }
  
  public void addFrame(MP4Packet paramMP4Packet)
    throws IOException
  {
    if (this.finished) {
      throw new IllegalStateException("The muxer track has finished muxing");
    }
    int i = paramMP4Packet.getEntryNo() + 1;
    int j = (int)(paramMP4Packet.getPts() - this.ptsEstimate);
    if (j != this.lastCompositionOffset)
    {
      if (this.lastCompositionSamples > 0) {
        this.compositionOffsets.add(new CompositionOffsetsBox.Entry(this.lastCompositionSamples, this.lastCompositionOffset));
      }
      this.lastCompositionOffset = j;
      this.lastCompositionSamples = 0;
    }
    this.lastCompositionSamples += 1;
    this.ptsEstimate += paramMP4Packet.getDuration();
    if ((this.lastEntry != -1) && (this.lastEntry != i))
    {
      outChunk(this.lastEntry);
      this.samplesInLastChunk = -1;
    }
    this.curChunk.add(paramMP4Packet.getData());
    if (paramMP4Packet.isKeyFrame()) {
      this.iframes.add(this.curFrame + 1);
    }
    for (;;)
    {
      this.curFrame += 1;
      this.chunkDuration += paramMP4Packet.getDuration();
      if ((this.curDuration != -1L) && (paramMP4Packet.getDuration() != this.curDuration))
      {
        this.sampleDurations.add(new TimeToSampleBox.TimeToSampleEntry((int)this.sameDurCount, (int)this.curDuration));
        this.sameDurCount = 0L;
      }
      this.curDuration = paramMP4Packet.getDuration();
      this.sameDurCount += 1L;
      this.trackTotalDuration += paramMP4Packet.getDuration();
      outChunkIfNeeded(i);
      processTimecode(paramMP4Packet);
      this.lastEntry = i;
      return;
      this.allIframes = false;
    }
  }
  
  public void addSampleEntries(SampleEntry[] paramArrayOfSampleEntry)
  {
    int j = paramArrayOfSampleEntry.length;
    int i = 0;
    while (i < j)
    {
      addSampleEntry(paramArrayOfSampleEntry[i]);
      i += 1;
    }
  }
  
  protected Box finish(MovieHeaderBox paramMovieHeaderBox)
    throws IOException
  {
    if (this.finished) {
      throw new IllegalStateException("The muxer track has finished muxing");
    }
    outChunk(this.lastEntry);
    if (this.sameDurCount > 0L) {
      this.sampleDurations.add(new TimeToSampleBox.TimeToSampleEntry((int)this.sameDurCount, (int)this.curDuration));
    }
    this.finished = true;
    TrakBox localTrakBox = new TrakBox();
    Object localObject = getDisplayDimensions();
    paramMovieHeaderBox = new TrackHeaderBox(this.trackId, paramMovieHeaderBox.getTimescale() * this.trackTotalDuration / this.timescale, ((Size)localObject).getWidth(), ((Size)localObject).getHeight(), new Date().getTime(), new Date().getTime(), 1.0F, (short)0, 0L, new int[] { 65536, 0, 0, 0, 65536, 0, 0, 0, 1073741824 });
    paramMovieHeaderBox.setFlags(15);
    localTrakBox.add(paramMovieHeaderBox);
    tapt(localTrakBox);
    localObject = new MediaBox();
    localTrakBox.add((Box)localObject);
    ((MediaBox)localObject).add(new MediaHeaderBox(this.timescale, this.trackTotalDuration, 0, new Date().getTime(), new Date().getTime(), 0));
    ((MediaBox)localObject).add(new HandlerBox("mhlr", this.type.getHandler(), "appl", 0, 0));
    paramMovieHeaderBox = new MediaInfoBox();
    ((MediaBox)localObject).add(paramMovieHeaderBox);
    mediaHeader(paramMovieHeaderBox, this.type);
    paramMovieHeaderBox.add(new HandlerBox("dhlr", "url ", "appl", 0, 0));
    addDref(paramMovieHeaderBox);
    localObject = new NodeBox(new Header("stbl"));
    paramMovieHeaderBox.add((Box)localObject);
    putCompositionOffsets((NodeBox)localObject);
    putEdits(localTrakBox);
    putName(localTrakBox);
    ((NodeBox)localObject).add(new SampleDescriptionBox((SampleEntry[])this.sampleEntries.toArray(new SampleEntry[0])));
    ((NodeBox)localObject).add(new SampleToChunkBox((SampleToChunkBox.SampleToChunkEntry[])this.samplesInChunks.toArray(new SampleToChunkBox.SampleToChunkEntry[0])));
    ((NodeBox)localObject).add(new SampleSizesBox(this.sampleSizes.toArray()));
    ((NodeBox)localObject).add(new TimeToSampleBox((TimeToSampleBox.TimeToSampleEntry[])this.sampleDurations.toArray(new TimeToSampleBox.TimeToSampleEntry[0])));
    ((NodeBox)localObject).add(new ChunkOffsets64Box(this.chunkOffsets.toArray()));
    if ((!this.allIframes) && (this.iframes.size() > 0)) {
      ((NodeBox)localObject).add(new SyncSamplesBox(this.iframes.toArray()));
    }
    return localTrakBox;
  }
  
  public TimecodeMP4MuxerTrack getTimecodeTrack()
  {
    return this.timecodeTrack;
  }
  
  public long getTrackTotalDuration()
  {
    return this.trackTotalDuration;
  }
  
  void outChunk(int paramInt)
    throws IOException
  {
    if (this.curChunk.size() == 0) {
      return;
    }
    this.chunkOffsets.add(this.out.position());
    Iterator localIterator = this.curChunk.iterator();
    while (localIterator.hasNext())
    {
      ByteBuffer localByteBuffer = (ByteBuffer)localIterator.next();
      this.sampleSizes.add(localByteBuffer.remaining());
      this.out.write(localByteBuffer);
    }
    if ((this.samplesInLastChunk == -1) || (this.samplesInLastChunk != this.curChunk.size())) {
      this.samplesInChunks.add(new SampleToChunkBox.SampleToChunkEntry(this.chunkNo + 1, this.curChunk.size(), paramInt));
    }
    this.samplesInLastChunk = this.curChunk.size();
    this.chunkNo += 1;
    this.chunkDuration = 0L;
    this.curChunk.clear();
  }
  
  public void setTimecode(TimecodeMP4MuxerTrack paramTimecodeMP4MuxerTrack)
  {
    this.timecodeTrack = paramTimecodeMP4MuxerTrack;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/FramesMP4MuxerTrack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */