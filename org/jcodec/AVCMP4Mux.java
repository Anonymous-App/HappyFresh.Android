package org.jcodec;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.List;

public class AVCMP4Mux
{
  private static AvcCBox avcC;
  
  private static void addSampleEntry(FramesMP4MuxerTrack paramFramesMP4MuxerTrack, SeqParameterSet[] paramArrayOfSeqParameterSet, PictureParameterSet[] paramArrayOfPictureParameterSet)
  {
    SeqParameterSet localSeqParameterSet = paramArrayOfSeqParameterSet[0];
    VideoSampleEntry localVideoSampleEntry = MP4Muxer.videoSampleEntry("avc1", new Size(localSeqParameterSet.pic_width_in_mbs_minus1 + 1 << 4, H264Utils.getPicHeightInMbs(localSeqParameterSet) << 4), "JCodec");
    avcC = new AvcCBox(localSeqParameterSet.profile_idc, 0, localSeqParameterSet.level_idc, write(paramArrayOfSeqParameterSet), write(paramArrayOfPictureParameterSet));
    localVideoSampleEntry.add(avcC);
    paramFramesMP4MuxerTrack.addSampleEntry(localVideoSampleEntry);
  }
  
  private static final MappedByteBuffer mapFile(File paramFile)
    throws IOException
  {
    RandomAccessFile localRandomAccessFile = new RandomAccessFile(paramFile, "rw");
    paramFile = localRandomAccessFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0L, paramFile.length());
    localRandomAccessFile.close();
    return paramFile;
  }
  
  private static void mux(FramesMP4MuxerTrack paramFramesMP4MuxerTrack, File paramFile, List<Long> paramList)
    throws IOException
  {
    paramFile = new MappedH264ES(mapFile(paramFile));
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    int i = 0;
    Object localObject = paramFile.nextFrame();
    if (localObject != null)
    {
      localObject = ((Packet)localObject).getData();
      H264Utils.wipePS((ByteBuffer)localObject, localArrayList1, localArrayList2);
      H264Utils.encodeMOVPacket((ByteBuffer)localObject);
      long l2 = ((Long)paramList.get(i)).longValue();
      if (paramList.size() - 1 > i) {}
      for (long l1 = ((Long)paramList.get(i + 1)).longValue() - l2;; l1 = 250000L)
      {
        paramFramesMP4MuxerTrack.addFrame(new MP4Packet((ByteBuffer)localObject, l2, 1000000L, l1, i, true, null, l2, 0));
        i += 1;
        break;
      }
    }
    addSampleEntry(paramFramesMP4MuxerTrack, paramFile.getSps(), paramFile.getPps());
  }
  
  private static List<ByteBuffer> write(PictureParameterSet[] paramArrayOfPictureParameterSet)
  {
    ArrayList localArrayList = new ArrayList();
    int j = paramArrayOfPictureParameterSet.length;
    int i = 0;
    while (i < j)
    {
      PictureParameterSet localPictureParameterSet = paramArrayOfPictureParameterSet[i];
      ByteBuffer localByteBuffer = ByteBuffer.allocate(1024);
      localPictureParameterSet.write(localByteBuffer);
      localByteBuffer.flip();
      localArrayList.add(localByteBuffer);
      i += 1;
    }
    return localArrayList;
  }
  
  private static List<ByteBuffer> write(SeqParameterSet[] paramArrayOfSeqParameterSet)
  {
    ArrayList localArrayList = new ArrayList();
    int j = paramArrayOfSeqParameterSet.length;
    int i = 0;
    while (i < j)
    {
      SeqParameterSet localSeqParameterSet = paramArrayOfSeqParameterSet[i];
      ByteBuffer localByteBuffer = ByteBuffer.allocate(1024);
      localSeqParameterSet.write(localByteBuffer);
      localByteBuffer.flip();
      localArrayList.add(localByteBuffer);
      i += 1;
    }
    return localArrayList;
  }
  
  public void muxH264File(File paramFile, String paramString, List<Long> paramList)
    throws Exception
  {
    paramString = NIOUtils.writableFileChannel(paramString, paramFile.length());
    MP4Muxer localMP4Muxer = new MP4Muxer(paramString, Brand.MP4);
    mux(localMP4Muxer.addTrackForCompressed(TrackType.VIDEO, 1000000), paramFile, paramList);
    localMP4Muxer.writeHeader();
    paramString.close();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/AVCMP4Mux.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */