package org.jcodec;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMP4MuxerTrack
{
  protected long chunkDuration;
  protected int chunkNo = 0;
  protected List<ByteBuffer> curChunk = new ArrayList();
  protected List<Edit> edits;
  protected boolean finished;
  private String name;
  protected List<SampleEntry> sampleEntries = new ArrayList();
  protected List<SampleToChunkBox.SampleToChunkEntry> samplesInChunks = new ArrayList();
  protected int samplesInLastChunk = -1;
  protected Rational tgtChunkDuration;
  protected Unit tgtChunkDurationUnit;
  protected int timescale;
  protected int trackId;
  protected TrackType type;
  
  public AbstractMP4MuxerTrack(int paramInt1, TrackType paramTrackType, int paramInt2)
  {
    this.trackId = paramInt1;
    this.type = paramTrackType;
    this.timescale = paramInt2;
  }
  
  protected void addDref(NodeBox paramNodeBox)
  {
    DataInfoBox localDataInfoBox = new DataInfoBox();
    paramNodeBox.add(localDataInfoBox);
    paramNodeBox = new DataRefBox();
    localDataInfoBox.add(paramNodeBox);
    paramNodeBox.add(new LeafBox(new Header("alis", 0L), ByteBuffer.wrap(new byte[] { 0, 0, 0, 1 })));
  }
  
  public void addSampleEntry(SampleEntry paramSampleEntry)
  {
    if (this.finished) {
      throw new IllegalStateException("The muxer track has finished muxing");
    }
    this.sampleEntries.add(paramSampleEntry);
  }
  
  protected abstract Box finish(MovieHeaderBox paramMovieHeaderBox)
    throws IOException;
  
  public Size getDisplayDimensions()
  {
    int j = 0;
    int i = 0;
    VideoSampleEntry localVideoSampleEntry;
    if ((this.sampleEntries.get(0) instanceof VideoSampleEntry))
    {
      localVideoSampleEntry = (VideoSampleEntry)this.sampleEntries.get(0);
      localObject = (PixelAspectExt)Box.findFirst(localVideoSampleEntry, PixelAspectExt.class, new String[] { PixelAspectExt.fourcc() });
      if (localObject == null) {
        break label97;
      }
    }
    label97:
    for (Object localObject = ((PixelAspectExt)localObject).getRational();; localObject = new Rational(1, 1))
    {
      j = ((Rational)localObject).getNum() * localVideoSampleEntry.getWidth() / ((Rational)localObject).getDen();
      i = localVideoSampleEntry.getHeight();
      return new Size(j, i);
    }
  }
  
  public List<SampleEntry> getEntries()
  {
    return this.sampleEntries;
  }
  
  public int getTimescale()
  {
    return this.timescale;
  }
  
  public abstract long getTrackTotalDuration();
  
  public boolean isAudio()
  {
    return this.type == TrackType.SOUND;
  }
  
  public boolean isTimecode()
  {
    return this.type == TrackType.TIMECODE;
  }
  
  public boolean isVideo()
  {
    return this.type == TrackType.VIDEO;
  }
  
  protected void mediaHeader(MediaInfoBox paramMediaInfoBox, TrackType paramTrackType)
  {
    switch (paramTrackType)
    {
    default: 
      throw new IllegalStateException("Handler " + paramTrackType.getHandler() + " not supported");
    case ???: 
      paramTrackType = new VideoMediaHeaderBox(0, 0, 0, 0);
      paramTrackType.setFlags(1);
      paramMediaInfoBox.add(paramTrackType);
      return;
    case ???: 
      paramTrackType = new SoundMediaHeaderBox();
      paramTrackType.setFlags(1);
      paramMediaInfoBox.add(paramTrackType);
      return;
    }
    paramTrackType = new NodeBox(new Header("gmhd"));
    paramTrackType.add(new GenericMediaInfoBox());
    NodeBox localNodeBox = new NodeBox(new Header("tmcd"));
    paramTrackType.add(localNodeBox);
    localNodeBox.add(new TimecodeMediaInfoBox((short)0, (short)0, (short)12, new short[] { 0, 0, 0 }, new short[] { 255, 255, 255 }, "Lucida Grande"));
    paramMediaInfoBox.add(paramTrackType);
  }
  
  protected void putEdits(TrakBox paramTrakBox)
  {
    if (this.edits != null)
    {
      NodeBox localNodeBox = new NodeBox(new Header("edts"));
      localNodeBox.add(new EditListBox(this.edits));
      paramTrakBox.add(localNodeBox);
    }
  }
  
  protected void putName(TrakBox paramTrakBox)
  {
    if (this.name != null)
    {
      NodeBox localNodeBox = new NodeBox(new Header("udta"));
      localNodeBox.add(new NameBox(this.name));
      paramTrakBox.add(localNodeBox);
    }
  }
  
  public void setEdits(List<Edit> paramList)
  {
    this.edits = paramList;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public void setTgtChunkDuration(Rational paramRational, Unit paramUnit)
  {
    this.tgtChunkDuration = paramRational;
    this.tgtChunkDurationUnit = paramUnit;
  }
  
  public void tapt(TrakBox paramTrakBox)
  {
    Size localSize = getDisplayDimensions();
    if (this.type == TrackType.VIDEO)
    {
      NodeBox localNodeBox = new NodeBox(new Header("tapt"));
      localNodeBox.add(new ClearApertureBox(localSize.getWidth(), localSize.getHeight()));
      localNodeBox.add(new ProductionApertureBox(localSize.getWidth(), localSize.getHeight()));
      localNodeBox.add(new EncodedPixelBox(localSize.getWidth(), localSize.getHeight()));
      paramTrakBox.add(localNodeBox);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/AbstractMP4MuxerTrack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */