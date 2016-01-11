package org.jcodec;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import junit.framework.Assert;

public class PCMMP4MuxerTrack
  extends AbstractMP4MuxerTrack
{
  private LongArrayList chunkOffsets = new LongArrayList();
  private int frameDuration;
  private int frameSize;
  private int framesInCurChunk;
  private SeekableByteChannel out;
  private int totalFrames;
  
  public PCMMP4MuxerTrack(SeekableByteChannel paramSeekableByteChannel, int paramInt1, TrackType paramTrackType, int paramInt2, int paramInt3, int paramInt4, SampleEntry paramSampleEntry)
  {
    super(paramInt1, paramTrackType, paramInt2);
    this.out = paramSeekableByteChannel;
    this.frameDuration = paramInt3;
    this.frameSize = paramInt4;
    addSampleEntry(paramSampleEntry);
    setTgtChunkDuration(new Rational(1, 2), Unit.SEC);
  }
  
  private void outChunk()
    throws IOException
  {
    if (this.framesInCurChunk == 0) {
      return;
    }
    this.chunkOffsets.add(this.out.position());
    Iterator localIterator = this.curChunk.iterator();
    while (localIterator.hasNext())
    {
      ByteBuffer localByteBuffer = (ByteBuffer)localIterator.next();
      this.out.write(localByteBuffer);
    }
    this.curChunk.clear();
    if ((this.samplesInLastChunk == -1) || (this.framesInCurChunk != this.samplesInLastChunk)) {
      this.samplesInChunks.add(new SampleToChunkBox.SampleToChunkEntry(this.chunkNo + 1, this.framesInCurChunk, 1));
    }
    this.samplesInLastChunk = this.framesInCurChunk;
    this.chunkNo += 1;
    this.framesInCurChunk = 0;
    this.chunkDuration = 0L;
  }
  
  private void outChunkIfNeeded()
    throws IOException
  {
    boolean bool;
    if ((this.tgtChunkDurationUnit == Unit.FRAME) || (this.tgtChunkDurationUnit == Unit.SEC))
    {
      bool = true;
      Assert.assertTrue(bool);
      if ((this.tgtChunkDurationUnit != Unit.FRAME) || (this.framesInCurChunk * this.tgtChunkDuration.getDen() != this.tgtChunkDuration.getNum())) {
        break label68;
      }
      outChunk();
    }
    label68:
    while ((this.tgtChunkDurationUnit != Unit.SEC) || (this.chunkDuration <= 0L) || (this.chunkDuration * this.tgtChunkDuration.getDen() < this.tgtChunkDuration.getNum() * this.timescale))
    {
      return;
      bool = false;
      break;
    }
    outChunk();
  }
  
  public void addSamples(ByteBuffer paramByteBuffer)
    throws IOException
  {
    this.curChunk.add(paramByteBuffer);
    int i = paramByteBuffer.remaining() / this.frameSize;
    this.totalFrames += i;
    this.framesInCurChunk += i;
    this.chunkDuration += this.frameDuration * i;
    outChunkIfNeeded();
  }
  
  protected Box finish(MovieHeaderBox paramMovieHeaderBox)
    throws IOException
  {
    if (this.finished) {
      throw new IllegalStateException("The muxer track has finished muxing");
    }
    outChunk();
    this.finished = true;
    TrakBox localTrakBox = new TrakBox();
    Object localObject = getDisplayDimensions();
    paramMovieHeaderBox = new TrackHeaderBox(this.trackId, paramMovieHeaderBox.getTimescale() * this.totalFrames * this.frameDuration / this.timescale, ((Size)localObject).getWidth(), ((Size)localObject).getHeight(), new Date().getTime(), new Date().getTime(), 1.0F, (short)0, 0L, new int[] { 65536, 0, 0, 0, 65536, 0, 0, 0, 1073741824 });
    paramMovieHeaderBox.setFlags(15);
    localTrakBox.add(paramMovieHeaderBox);
    tapt(localTrakBox);
    localObject = new MediaBox();
    localTrakBox.add((Box)localObject);
    ((MediaBox)localObject).add(new MediaHeaderBox(this.timescale, this.totalFrames * this.frameDuration, 0, new Date().getTime(), new Date().getTime(), 0));
    ((MediaBox)localObject).add(new HandlerBox("mhlr", this.type.getHandler(), "appl", 0, 0));
    paramMovieHeaderBox = new MediaInfoBox();
    ((MediaBox)localObject).add(paramMovieHeaderBox);
    mediaHeader(paramMovieHeaderBox, this.type);
    paramMovieHeaderBox.add(new HandlerBox("dhlr", "url ", "appl", 0, 0));
    addDref(paramMovieHeaderBox);
    localObject = new NodeBox(new Header("stbl"));
    paramMovieHeaderBox.add((Box)localObject);
    putEdits(localTrakBox);
    putName(localTrakBox);
    ((NodeBox)localObject).add(new SampleDescriptionBox((SampleEntry[])this.sampleEntries.toArray(new SampleEntry[0])));
    ((NodeBox)localObject).add(new SampleToChunkBox((SampleToChunkBox.SampleToChunkEntry[])this.samplesInChunks.toArray(new SampleToChunkBox.SampleToChunkEntry[0])));
    ((NodeBox)localObject).add(new SampleSizesBox(this.frameSize, this.totalFrames));
    ((NodeBox)localObject).add(new TimeToSampleBox(new TimeToSampleBox.TimeToSampleEntry[] { new TimeToSampleBox.TimeToSampleEntry(this.totalFrames, this.frameDuration) }));
    ((NodeBox)localObject).add(new ChunkOffsets64Box(this.chunkOffsets.toArray()));
    return localTrakBox;
  }
  
  public long getTrackTotalDuration()
  {
    return this.totalFrames * this.frameDuration;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/PCMMP4MuxerTrack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */