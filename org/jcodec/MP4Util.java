package org.jcodec;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MP4Util
{
  private static final int MIN_HEADER_SIZE = 256000;
  private static Map<Codec, String> codecMapping = new HashMap();
  
  static
  {
    codecMapping.put(Codec.MPEG2, "m2v1");
    codecMapping.put(Codec.H264, "avc1");
    codecMapping.put(Codec.J2K, "mjp2");
  }
  
  public static String getFourcc(Codec paramCodec)
  {
    return (String)codecMapping.get(paramCodec);
  }
  
  public static List<Atom> getRootAtoms(SeekableByteChannel paramSeekableByteChannel)
    throws IOException
  {
    paramSeekableByteChannel.position(0L);
    ArrayList localArrayList = new ArrayList();
    Header localHeader;
    for (long l = 0L;; l += localHeader.getSize())
    {
      if (l < paramSeekableByteChannel.size())
      {
        paramSeekableByteChannel.position(l);
        localHeader = Header.read(NIOUtils.fetchFrom(paramSeekableByteChannel, 16));
        if (localHeader != null) {}
      }
      else
      {
        return localArrayList;
      }
      localArrayList.add(new Atom(localHeader, l));
    }
  }
  
  public static void writeMovie(File paramFile, MovieBox paramMovieBox)
    throws IOException
  {
    Object localObject = null;
    try
    {
      FileChannel localFileChannel = new FileInputStream(paramFile).getChannel();
      localObject = localFileChannel;
      writeMovie(paramFile, paramMovieBox);
      localFileChannel.close();
      return;
    }
    finally
    {
      ((FileChannel)localObject).close();
    }
  }
  
  public static void writeMovieOnFile(SeekableByteChannel paramSeekableByteChannel, MovieBox paramMovieBox)
    throws IOException
  {
    long l = paramSeekableByteChannel.position();
    paramSeekableByteChannel.close();
    RandomAccessFile localRandomAccessFile = new RandomAccessFile(paramSeekableByteChannel.getFileName(), "rws");
    localRandomAccessFile.setLength(Math.max(paramSeekableByteChannel.getWantedSize() * 2L, 256000L));
    MappedByteBuffer localMappedByteBuffer = localRandomAccessFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0L, localRandomAccessFile.length());
    localMappedByteBuffer.position((int)l);
    paramMovieBox.write(localMappedByteBuffer);
    int i = localMappedByteBuffer.position();
    paramMovieBox = localRandomAccessFile.getChannel();
    paramMovieBox.truncate(i);
    paramSeekableByteChannel.setChannel(paramMovieBox);
  }
  
  public static void writeMovieOnRAM(SeekableByteChannel paramSeekableByteChannel, MovieBox paramMovieBox)
    throws IOException
  {
    ByteBuffer localByteBuffer = ByteBuffer.allocate(16777216);
    paramMovieBox.write(localByteBuffer);
    localByteBuffer.flip();
    paramSeekableByteChannel.write(localByteBuffer);
  }
  
  public static class Atom
  {
    private Header header;
    private long offset;
    
    public Atom(Header paramHeader, long paramLong)
    {
      this.header = paramHeader;
      this.offset = paramLong;
    }
    
    public void copy(SeekableByteChannel paramSeekableByteChannel, WritableByteChannel paramWritableByteChannel)
      throws IOException
    {
      paramSeekableByteChannel.position(this.offset);
      NIOUtils.copy(paramSeekableByteChannel, paramWritableByteChannel, this.header.getSize());
    }
    
    public Header getHeader()
    {
      return this.header;
    }
    
    public long getOffset()
    {
      return this.offset;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/MP4Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */