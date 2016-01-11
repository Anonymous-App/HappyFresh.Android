package org.jcodec;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class H264Utils
{
  public static SampleEntry createMOVSampleEntry(List<ByteBuffer> paramList1, List<ByteBuffer> paramList2)
  {
    SeqParameterSet localSeqParameterSet = readSPS(NIOUtils.duplicate((ByteBuffer)paramList1.get(0)));
    paramList1 = new AvcCBox(localSeqParameterSet.profile_idc, 0, localSeqParameterSet.level_idc, paramList1, paramList2);
    int i = localSeqParameterSet.pic_width_in_mbs_minus1 + 1 << 4;
    int j = getPicHeightInMbs(localSeqParameterSet) << 4;
    if (localSeqParameterSet.frame_cropping_flag)
    {
      i -= (localSeqParameterSet.frame_crop_right_offset + localSeqParameterSet.frame_crop_left_offset << localSeqParameterSet.chroma_format_idc.compWidth[1]);
      if (!localSeqParameterSet.frame_cropping_flag) {
        break label150;
      }
      j -= (localSeqParameterSet.frame_crop_bottom_offset + localSeqParameterSet.frame_crop_top_offset << localSeqParameterSet.chroma_format_idc.compHeight[1]);
    }
    label150:
    for (;;)
    {
      paramList2 = MP4Muxer.videoSampleEntry("avc1", new Size(i, j), "JCodec");
      paramList2.add(paramList1);
      return paramList2;
      break;
    }
  }
  
  public static SampleEntry createMOVSampleEntry(SeqParameterSet paramSeqParameterSet, PictureParameterSet paramPictureParameterSet)
  {
    ByteBuffer localByteBuffer1 = ByteBuffer.allocate(512);
    ByteBuffer localByteBuffer2 = ByteBuffer.allocate(512);
    paramSeqParameterSet.write(localByteBuffer1);
    paramPictureParameterSet.write(localByteBuffer2);
    localByteBuffer1.flip();
    localByteBuffer2.flip();
    return createMOVSampleEntry(Arrays.asList(new ByteBuffer[] { localByteBuffer1 }), Arrays.asList(new ByteBuffer[] { localByteBuffer2 }));
  }
  
  public static void encodeMOVPacket(ByteBuffer paramByteBuffer)
  {
    ByteBuffer localByteBuffer1 = paramByteBuffer.duplicate();
    paramByteBuffer = paramByteBuffer.duplicate();
    int i = paramByteBuffer.position();
    for (;;)
    {
      ByteBuffer localByteBuffer2 = nextNALUnit(localByteBuffer1);
      if (localByteBuffer2 == null) {
        return;
      }
      paramByteBuffer.position(i);
      paramByteBuffer.putInt(localByteBuffer2.remaining());
      i += localByteBuffer2.remaining() + 4;
    }
  }
  
  public static final void escapeNAL(ByteBuffer paramByteBuffer)
  {
    int[] arrayOfInt = searchEscapeLocations(paramByteBuffer);
    int j = paramByteBuffer.limit();
    paramByteBuffer.limit(paramByteBuffer.limit() + arrayOfInt.length);
    int i = paramByteBuffer.limit() - 1;
    j -= 1;
    int m;
    for (int k = arrayOfInt.length - 1; i >= paramByteBuffer.position(); k = m)
    {
      paramByteBuffer.put(i, paramByteBuffer.get(j));
      m = k;
      int n = i;
      if (k >= 0)
      {
        m = k;
        n = i;
        if (arrayOfInt[k] == j)
        {
          n = i - 1;
          paramByteBuffer.put(n, (byte)3);
          m = k - 1;
        }
      }
      i = n - 1;
      j -= 1;
    }
  }
  
  public static final void escapeNAL(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2)
  {
    int i = paramByteBuffer1.get();
    int j = paramByteBuffer1.get();
    paramByteBuffer2.put(i);
    paramByteBuffer2.put(j);
    int m = j;
    int k;
    for (int n = i; paramByteBuffer1.hasRemaining(); n = k)
    {
      i = paramByteBuffer1.get();
      k = m;
      if (n == 0)
      {
        k = m;
        if (m == 0)
        {
          k = m;
          if ((i & 0xFF) <= 3)
          {
            paramByteBuffer2.put((byte)3);
            k = 3;
          }
        }
      }
      paramByteBuffer2.put(i);
      m = i;
    }
  }
  
  public static PictureParameterSet findPPS(List<PictureParameterSet> paramList, int paramInt)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      PictureParameterSet localPictureParameterSet = (PictureParameterSet)paramList.next();
      if (localPictureParameterSet.pic_parameter_set_id == paramInt) {
        return localPictureParameterSet;
      }
    }
    return null;
  }
  
  public static SeqParameterSet findSPS(List<SeqParameterSet> paramList, int paramInt)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      SeqParameterSet localSeqParameterSet = (SeqParameterSet)paramList.next();
      if (localSeqParameterSet.seq_parameter_set_id == paramInt) {
        return localSeqParameterSet;
      }
    }
    return null;
  }
  
  public static int getPicHeightInMbs(SeqParameterSet paramSeqParameterSet)
  {
    int j = paramSeqParameterSet.pic_height_in_map_units_minus1;
    if (paramSeqParameterSet.frame_mbs_only_flag) {}
    for (int i = 0;; i = 1) {
      return j + 1 << i;
    }
  }
  
  public static Size getPicSize(SeqParameterSet paramSeqParameterSet)
  {
    int m = paramSeqParameterSet.pic_width_in_mbs_minus1 + 1 << 4;
    int k = getPicHeightInMbs(paramSeqParameterSet) << 4;
    int j = k;
    int i = m;
    if (paramSeqParameterSet.frame_cropping_flag)
    {
      i = m - (paramSeqParameterSet.frame_crop_left_offset + paramSeqParameterSet.frame_crop_right_offset << paramSeqParameterSet.chroma_format_idc.compWidth[1]);
      j = k - (paramSeqParameterSet.frame_crop_top_offset + paramSeqParameterSet.frame_crop_bottom_offset << paramSeqParameterSet.chroma_format_idc.compHeight[1]);
    }
    return new Size(i, j);
  }
  
  public static int golomb2Signed(int paramInt)
  {
    return ((paramInt >> 1) + (paramInt & 0x1)) * (((paramInt & 0x1) << 1) - 1);
  }
  
  public static final ByteBuffer gotoNALUnit(ByteBuffer paramByteBuffer)
  {
    Object localObject;
    if (!paramByteBuffer.hasRemaining())
    {
      localObject = null;
      return (ByteBuffer)localObject;
    }
    int k = paramByteBuffer.position();
    ByteBuffer localByteBuffer = paramByteBuffer.slice();
    localByteBuffer.order(ByteOrder.BIG_ENDIAN);
    int i = -1;
    int j;
    do
    {
      localObject = localByteBuffer;
      if (!paramByteBuffer.hasRemaining()) {
        break;
      }
      j = i << 8 | paramByteBuffer.get() & 0xFF;
      i = j;
    } while ((0xFFFFFF & j) != 1);
    int m = paramByteBuffer.position();
    if (j == 1) {}
    for (i = 4;; i = 3)
    {
      paramByteBuffer.position(m - i);
      localByteBuffer.limit(paramByteBuffer.position() - k);
      return localByteBuffer;
    }
  }
  
  public static boolean idrSlice(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer = paramByteBuffer.duplicate();
    ByteBuffer localByteBuffer;
    do
    {
      localByteBuffer = nextNALUnit(paramByteBuffer);
      if (localByteBuffer == null) {
        break;
      }
    } while (NALUnit.read(localByteBuffer).type != NALUnitType.IDR_SLICE);
    return true;
    return false;
  }
  
  public static boolean idrSlice(List<ByteBuffer> paramList)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      if (NALUnit.read(((ByteBuffer)paramList.next()).duplicate()).type == NALUnitType.IDR_SLICE) {
        return true;
      }
    }
    return false;
  }
  
  public static void joinNALUnits(List<ByteBuffer> paramList, ByteBuffer paramByteBuffer)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      ByteBuffer localByteBuffer = (ByteBuffer)paramList.next();
      paramByteBuffer.putInt(1);
      paramByteBuffer.put(localByteBuffer.duplicate());
    }
  }
  
  public static ByteBuffer nextNALUnit(ByteBuffer paramByteBuffer)
  {
    skipToNALUnit(paramByteBuffer);
    return gotoNALUnit(paramByteBuffer);
  }
  
  private static int readLen(ByteBuffer paramByteBuffer, int paramInt)
  {
    switch (paramInt)
    {
    default: 
      throw new IllegalArgumentException("NAL Unit length size can not be " + paramInt);
    case 1: 
      return paramByteBuffer.get() & 0xFF;
    case 2: 
      return paramByteBuffer.getShort() & 0xFFFF;
    case 3: 
      return (paramByteBuffer.getShort() & 0xFFFF) << 8 | paramByteBuffer.get() & 0xFF;
    }
    return paramByteBuffer.getInt();
  }
  
  public static List<PictureParameterSet> readPPS(List<ByteBuffer> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      localArrayList.add(readPPS(NIOUtils.duplicate((ByteBuffer)paramList.next())));
    }
    return localArrayList;
  }
  
  public static PictureParameterSet readPPS(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer = NIOUtils.duplicate(paramByteBuffer);
    unescapeNAL(paramByteBuffer);
    return PictureParameterSet.read(paramByteBuffer);
  }
  
  public static List<SeqParameterSet> readSPS(List<ByteBuffer> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      localArrayList.add(readSPS(NIOUtils.duplicate((ByteBuffer)paramList.next())));
    }
    return localArrayList;
  }
  
  public static SeqParameterSet readSPS(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer = NIOUtils.duplicate(paramByteBuffer);
    unescapeNAL(paramByteBuffer);
    return SeqParameterSet.read(paramByteBuffer);
  }
  
  public static void saveRawFrame(ByteBuffer paramByteBuffer, AvcCBox paramAvcCBox, File paramFile)
    throws IOException
  {
    paramFile = NIOUtils.writableFileChannel(paramFile);
    saveStreamParams(paramAvcCBox, paramFile);
    paramFile.write(paramByteBuffer.duplicate());
    paramFile.close();
  }
  
  public static void saveStreamParams(AvcCBox paramAvcCBox, SeekableByteChannel paramSeekableByteChannel)
    throws IOException
  {
    ByteBuffer localByteBuffer1 = ByteBuffer.allocate(1024);
    Object localObject = paramAvcCBox.getSpsList().iterator();
    while (((Iterator)localObject).hasNext())
    {
      ByteBuffer localByteBuffer2 = (ByteBuffer)((Iterator)localObject).next();
      paramSeekableByteChannel.write(ByteBuffer.wrap(new byte[] { 0, 0, 0, 1, 103 }));
      escapeNAL(localByteBuffer2.duplicate(), localByteBuffer1);
      localByteBuffer1.flip();
      paramSeekableByteChannel.write(localByteBuffer1);
      localByteBuffer1.clear();
    }
    paramAvcCBox = paramAvcCBox.getPpsList().iterator();
    while (paramAvcCBox.hasNext())
    {
      localObject = (ByteBuffer)paramAvcCBox.next();
      paramSeekableByteChannel.write(ByteBuffer.wrap(new byte[] { 0, 0, 0, 1, 104 }));
      escapeNAL(((ByteBuffer)localObject).duplicate(), localByteBuffer1);
      localByteBuffer1.flip();
      paramSeekableByteChannel.write(localByteBuffer1);
      localByteBuffer1.clear();
    }
  }
  
  private static int[] searchEscapeLocations(ByteBuffer paramByteBuffer)
  {
    IntArrayList localIntArrayList = new IntArrayList();
    paramByteBuffer = paramByteBuffer.duplicate();
    int k;
    int j;
    for (int i = paramByteBuffer.getShort(); paramByteBuffer.hasRemaining(); i = (short)(k & 0xFF | (short)(j << 8)))
    {
      k = paramByteBuffer.get();
      j = i;
      if (i == 0)
      {
        j = i;
        if ((k & 0xFFFFFFFC) == 0)
        {
          localIntArrayList.add(paramByteBuffer.position() - 1);
          j = 3;
        }
      }
    }
    return localIntArrayList.toArray();
  }
  
  public static final void skipToNALUnit(ByteBuffer paramByteBuffer)
  {
    if (!paramByteBuffer.hasRemaining()) {}
    int j;
    do
    {
      return;
      while (!paramByteBuffer.hasRemaining()) {
        i = -1;
      }
      j = i << 8 | paramByteBuffer.get() & 0xFF;
      int i = j;
    } while ((0xFFFFFF & j) != 1);
    paramByteBuffer.position(paramByteBuffer.position());
  }
  
  public static List<ByteBuffer> splitFrame(ByteBuffer paramByteBuffer)
  {
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      ByteBuffer localByteBuffer = nextNALUnit(paramByteBuffer);
      if (localByteBuffer == null) {
        break;
      }
      localArrayList.add(localByteBuffer);
    }
    return localArrayList;
  }
  
  public static List<ByteBuffer> splitMOVPacket(ByteBuffer paramByteBuffer, AvcCBox paramAvcCBox)
  {
    ArrayList localArrayList = new ArrayList();
    int i = paramAvcCBox.getNalLengthSize();
    paramByteBuffer = paramByteBuffer.duplicate();
    for (;;)
    {
      int j;
      if (paramByteBuffer.remaining() >= i)
      {
        j = readLen(paramByteBuffer, i);
        if (j != 0) {}
      }
      else
      {
        return localArrayList;
      }
      localArrayList.add(NIOUtils.read(paramByteBuffer, j));
    }
  }
  
  public static final void unescapeNAL(ByteBuffer paramByteBuffer)
  {
    if (paramByteBuffer.remaining() < 2) {
      return;
    }
    ByteBuffer localByteBuffer1 = paramByteBuffer.duplicate();
    ByteBuffer localByteBuffer2 = paramByteBuffer.duplicate();
    byte b1 = localByteBuffer1.get();
    localByteBuffer2.put(b1);
    byte b2 = localByteBuffer1.get();
    localByteBuffer2.put(b2);
    byte b3 = b2;
    byte b4 = b1;
    while (localByteBuffer1.hasRemaining())
    {
      b1 = localByteBuffer1.get();
      if ((b4 != 0) || (b3 != 0) || (b1 != 3)) {
        localByteBuffer2.put(b1);
      }
      byte b5 = b1;
      b4 = b3;
      b3 = b5;
    }
    paramByteBuffer.limit(localByteBuffer2.position());
  }
  
  public static void wipePS(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2, List<ByteBuffer> paramList1, List<ByteBuffer> paramList2)
  {
    paramByteBuffer1 = paramByteBuffer1.duplicate();
    for (;;)
    {
      ByteBuffer localByteBuffer;
      if (paramByteBuffer1.hasRemaining())
      {
        localByteBuffer = nextNALUnit(paramByteBuffer1);
        if (localByteBuffer != null) {}
      }
      else
      {
        paramByteBuffer2.flip();
        return;
      }
      NALUnit localNALUnit = NALUnit.read(localByteBuffer.duplicate());
      if (localNALUnit.type == NALUnitType.PPS)
      {
        if (paramList2 != null) {
          paramList2.add(localByteBuffer);
        }
      }
      else if (localNALUnit.type == NALUnitType.SPS)
      {
        if (paramList1 != null) {
          paramList1.add(localByteBuffer);
        }
      }
      else
      {
        paramByteBuffer2.putInt(1);
        paramByteBuffer2.put(localByteBuffer);
      }
    }
  }
  
  public static void wipePS(ByteBuffer paramByteBuffer, List<ByteBuffer> paramList1, List<ByteBuffer> paramList2)
  {
    ByteBuffer localByteBuffer1 = paramByteBuffer.duplicate();
    for (;;)
    {
      ByteBuffer localByteBuffer2;
      if (localByteBuffer1.hasRemaining())
      {
        localByteBuffer2 = nextNALUnit(localByteBuffer1);
        if (localByteBuffer2 != null) {
          break label24;
        }
      }
      label24:
      NALUnit localNALUnit;
      do
      {
        return;
        localNALUnit = NALUnit.read(localByteBuffer2);
        if (localNALUnit.type == NALUnitType.PPS)
        {
          if (paramList2 != null) {
            paramList2.add(localByteBuffer2);
          }
          paramByteBuffer.position(localByteBuffer1.position());
          break;
        }
      } while (localNALUnit.type != NALUnitType.SPS);
      if (paramList1 != null) {
        paramList1.add(localByteBuffer2);
      }
      paramByteBuffer.position(localByteBuffer1.position());
    }
  }
  
  public static ByteBuffer writePPS(PictureParameterSet paramPictureParameterSet, int paramInt)
  {
    ByteBuffer localByteBuffer = ByteBuffer.allocate(paramInt + 8);
    paramPictureParameterSet.write(localByteBuffer);
    localByteBuffer.flip();
    escapeNAL(localByteBuffer);
    return localByteBuffer;
  }
  
  public static List<ByteBuffer> writePPS(List<PictureParameterSet> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      localArrayList.add(writePPS((PictureParameterSet)paramList.next(), 64));
    }
    return localArrayList;
  }
  
  public static ByteBuffer writeSPS(SeqParameterSet paramSeqParameterSet, int paramInt)
  {
    ByteBuffer localByteBuffer = ByteBuffer.allocate(paramInt + 8);
    paramSeqParameterSet.write(localByteBuffer);
    localByteBuffer.flip();
    escapeNAL(localByteBuffer);
    return localByteBuffer;
  }
  
  public static List<ByteBuffer> writeSPS(List<SeqParameterSet> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      localArrayList.add(writeSPS((SeqParameterSet)paramList.next(), 256));
    }
    return localArrayList;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/H264Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */