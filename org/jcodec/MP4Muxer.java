package org.jcodec;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MP4Muxer
{
  private long mdatOffset;
  private int nextTrackId = 1;
  SeekableByteChannel out;
  private List<AbstractMP4MuxerTrack> tracks = new ArrayList();
  
  public MP4Muxer(SeekableByteChannel paramSeekableByteChannel)
    throws IOException
  {
    this(paramSeekableByteChannel, Brand.MP4);
  }
  
  public MP4Muxer(SeekableByteChannel paramSeekableByteChannel, Brand paramBrand)
    throws IOException
  {
    this(paramSeekableByteChannel, paramBrand.getFileTypeBox());
  }
  
  public MP4Muxer(SeekableByteChannel paramSeekableByteChannel, FileTypeBox paramFileTypeBox)
    throws IOException
  {
    this.out = paramSeekableByteChannel;
    ByteBuffer localByteBuffer = ByteBuffer.allocate(1024);
    paramFileTypeBox.write(localByteBuffer);
    new Header("wide", 8L).write(localByteBuffer);
    new Header("mdat", 1L).write(localByteBuffer);
    this.mdatOffset = localByteBuffer.position();
    localByteBuffer.putLong(0L);
    localByteBuffer.flip();
    paramSeekableByteChannel.write(localByteBuffer);
  }
  
  public static String lookupFourcc(AudioFormat paramAudioFormat)
  {
    if ((paramAudioFormat.getSampleSizeInBits() == 16) && (!paramAudioFormat.isBigEndian())) {
      return "sowt";
    }
    if (paramAudioFormat.getSampleSizeInBits() == 24) {
      return "in24";
    }
    throw new IllegalArgumentException("Audio format " + paramAudioFormat + " is not supported.");
  }
  
  private MovieHeaderBox movieHeader(NodeBox paramNodeBox)
  {
    int i = ((AbstractMP4MuxerTrack)this.tracks.get(0)).getTimescale();
    long l1 = ((AbstractMP4MuxerTrack)this.tracks.get(0)).getTrackTotalDuration();
    paramNodeBox = getVideoTrack();
    if (paramNodeBox != null)
    {
      i = paramNodeBox.getTimescale();
      l1 = paramNodeBox.getTrackTotalDuration();
    }
    long l2 = new Date().getTime();
    long l3 = new Date().getTime();
    int j = this.nextTrackId;
    return new MovieHeaderBox(i, l1, 1.0F, 1.0F, l2, l3, new int[] { 65536, 0, 0, 0, 65536, 0, 0, 0, 1073741824 }, j);
  }
  
  public static LeafBox terminatorAtom()
  {
    return new LeafBox(new Header(new String(new byte[4])), ByteBuffer.allocate(0));
  }
  
  public static VideoSampleEntry videoSampleEntry(String paramString1, Size paramSize, String paramString2)
  {
    paramString1 = new Header(paramString1);
    short s1 = (short)paramSize.getWidth();
    short s2 = (short)paramSize.getHeight();
    if (paramString2 != null) {}
    for (;;)
    {
      return new VideoSampleEntry(paramString1, (short)0, (short)0, "jcod", 0, 768, s1, s2, 72L, 72L, (short)1, paramString2, (short)24, (short)1, (short)-1);
      paramString2 = "jcodec";
    }
  }
  
  public TimecodeMP4MuxerTrack addTimecodeTrack(int paramInt)
  {
    Object localObject = this.out;
    int i = this.nextTrackId;
    this.nextTrackId = (i + 1);
    localObject = new TimecodeMP4MuxerTrack((SeekableByteChannel)localObject, i, paramInt);
    this.tracks.add(localObject);
    return (TimecodeMP4MuxerTrack)localObject;
  }
  
  public FramesMP4MuxerTrack addTrackForCompressed(TrackType paramTrackType, int paramInt)
  {
    SeekableByteChannel localSeekableByteChannel = this.out;
    int i = this.nextTrackId;
    this.nextTrackId = (i + 1);
    paramTrackType = new FramesMP4MuxerTrack(localSeekableByteChannel, i, paramTrackType, paramInt);
    this.tracks.add(paramTrackType);
    return paramTrackType;
  }
  
  public PCMMP4MuxerTrack addTrackForUncompressed(TrackType paramTrackType, int paramInt1, int paramInt2, int paramInt3, SampleEntry paramSampleEntry)
  {
    SeekableByteChannel localSeekableByteChannel = this.out;
    int i = this.nextTrackId;
    this.nextTrackId = (i + 1);
    paramTrackType = new PCMMP4MuxerTrack(localSeekableByteChannel, i, paramTrackType, paramInt1, paramInt2, paramInt3, paramSampleEntry);
    this.tracks.add(paramTrackType);
    return paramTrackType;
  }
  
  public FramesMP4MuxerTrack addVideoTrack(String paramString1, Size paramSize, String paramString2, int paramInt)
  {
    FramesMP4MuxerTrack localFramesMP4MuxerTrack = addTrackForCompressed(TrackType.VIDEO, paramInt);
    localFramesMP4MuxerTrack.addSampleEntry(videoSampleEntry(paramString1, paramSize, paramString2));
    return localFramesMP4MuxerTrack;
  }
  
  public FramesMP4MuxerTrack addVideoTrackWithTimecode(String paramString1, Size paramSize, String paramString2, int paramInt)
  {
    TimecodeMP4MuxerTrack localTimecodeMP4MuxerTrack = addTimecodeTrack(paramInt);
    FramesMP4MuxerTrack localFramesMP4MuxerTrack = addTrackForCompressed(TrackType.VIDEO, paramInt);
    localFramesMP4MuxerTrack.addSampleEntry(videoSampleEntry(paramString1, paramSize, paramString2));
    localFramesMP4MuxerTrack.setTimecode(localTimecodeMP4MuxerTrack);
    return localFramesMP4MuxerTrack;
  }
  
  public MovieBox finalizeHeader()
    throws IOException
  {
    MovieBox localMovieBox = new MovieBox();
    MovieHeaderBox localMovieHeaderBox = movieHeader(localMovieBox);
    localMovieBox.addFirst(localMovieHeaderBox);
    Iterator localIterator = this.tracks.iterator();
    while (localIterator.hasNext())
    {
      Box localBox = ((AbstractMP4MuxerTrack)localIterator.next()).finish(localMovieHeaderBox);
      if (localBox != null) {
        localMovieBox.add(localBox);
      }
    }
    return localMovieBox;
  }
  
  public List<AbstractMP4MuxerTrack> getAudioTracks()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.tracks.iterator();
    while (localIterator.hasNext())
    {
      AbstractMP4MuxerTrack localAbstractMP4MuxerTrack = (AbstractMP4MuxerTrack)localIterator.next();
      if (localAbstractMP4MuxerTrack.isAudio()) {
        localArrayList.add(localAbstractMP4MuxerTrack);
      }
    }
    return localArrayList;
  }
  
  public AbstractMP4MuxerTrack getTimecodeTrack()
  {
    Iterator localIterator = this.tracks.iterator();
    while (localIterator.hasNext())
    {
      AbstractMP4MuxerTrack localAbstractMP4MuxerTrack = (AbstractMP4MuxerTrack)localIterator.next();
      if (localAbstractMP4MuxerTrack.isTimecode()) {
        return localAbstractMP4MuxerTrack;
      }
    }
    return null;
  }
  
  public List<AbstractMP4MuxerTrack> getTracks()
  {
    return this.tracks;
  }
  
  public AbstractMP4MuxerTrack getVideoTrack()
  {
    Iterator localIterator = this.tracks.iterator();
    while (localIterator.hasNext())
    {
      AbstractMP4MuxerTrack localAbstractMP4MuxerTrack = (AbstractMP4MuxerTrack)localIterator.next();
      if (localAbstractMP4MuxerTrack.isVideo()) {
        return localAbstractMP4MuxerTrack;
      }
    }
    return null;
  }
  
  public void storeHeader(MovieBox paramMovieBox)
    throws IOException
  {
    long l1 = this.out.position();
    long l2 = this.mdatOffset;
    MP4Util.writeMovieOnFile(this.out, paramMovieBox);
    this.out.position(this.mdatOffset);
    NIOUtils.writeLong(this.out, l1 - l2 + 8L);
  }
  
  public void writeHeader()
    throws IOException
  {
    storeHeader(finalizeHeader());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/MP4Muxer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */