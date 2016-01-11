package org.jcodec;

import android.annotation.SuppressLint;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class NIOUtils
{
  public static void closeQuietly(ReadableByteChannel paramReadableByteChannel)
  {
    if (paramReadableByteChannel == null) {
      return;
    }
    try
    {
      paramReadableByteChannel.close();
      return;
    }
    catch (IOException paramReadableByteChannel) {}
  }
  
  public static ByteBuffer combine(Iterable<ByteBuffer> paramIterable)
  {
    int i = 0;
    Object localObject = paramIterable.iterator();
    while (((Iterator)localObject).hasNext()) {
      i += ((ByteBuffer)((Iterator)localObject).next()).remaining();
    }
    localObject = ByteBuffer.allocate(i);
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext()) {
      write((ByteBuffer)localObject, (ByteBuffer)paramIterable.next());
    }
    ((ByteBuffer)localObject).flip();
    return (ByteBuffer)localObject;
  }
  
  public static ByteBuffer combine(ByteBuffer... paramVarArgs)
  {
    return combine(paramVarArgs);
  }
  
  public static void copy(ReadableByteChannel paramReadableByteChannel, WritableByteChannel paramWritableByteChannel, long paramLong)
    throws IOException
  {
    ByteBuffer localByteBuffer = ByteBuffer.allocate(65536);
    long l;
    do
    {
      localByteBuffer.position(0);
      localByteBuffer.limit((int)Math.min(paramLong, localByteBuffer.capacity()));
      int i = paramReadableByteChannel.read(localByteBuffer);
      l = paramLong;
      if (i != -1)
      {
        localByteBuffer.flip();
        paramWritableByteChannel.write(localByteBuffer);
        l = paramLong - i;
      }
      if (i == -1) {
        break;
      }
      paramLong = l;
    } while (l > 0L);
  }
  
  public static ByteBuffer duplicate(ByteBuffer paramByteBuffer)
  {
    ByteBuffer localByteBuffer = ByteBuffer.allocate(paramByteBuffer.remaining());
    localByteBuffer.put(paramByteBuffer.duplicate());
    localByteBuffer.flip();
    return localByteBuffer;
  }
  
  public static ByteBuffer fetchFrom(File paramFile)
    throws IOException
  {
    return fetchFrom(paramFile, (int)paramFile.length());
  }
  
  public static ByteBuffer fetchFrom(File paramFile, int paramInt)
    throws IOException
  {
    File localFile = null;
    try
    {
      paramFile = new FileInputStream(paramFile).getChannel();
      localFile = paramFile;
      ByteBuffer localByteBuffer = fetchFrom(paramFile, paramInt);
      return localByteBuffer;
    }
    finally
    {
      closeQuietly(localFile);
    }
  }
  
  public static ByteBuffer fetchFrom(ByteBuffer paramByteBuffer, ReadableByteChannel paramReadableByteChannel, int paramInt)
    throws IOException
  {
    paramByteBuffer = paramByteBuffer.duplicate();
    paramByteBuffer.limit(paramInt);
    read(paramReadableByteChannel, paramByteBuffer);
    paramByteBuffer.flip();
    return paramByteBuffer;
  }
  
  public static ByteBuffer fetchFrom(ReadableByteChannel paramReadableByteChannel, int paramInt)
    throws IOException
  {
    ByteBuffer localByteBuffer = ByteBuffer.allocate(paramInt);
    read(paramReadableByteChannel, localByteBuffer);
    localByteBuffer.flip();
    return localByteBuffer;
  }
  
  public static void fill(ByteBuffer paramByteBuffer, byte paramByte)
  {
    while (paramByteBuffer.hasRemaining()) {
      paramByteBuffer.put(paramByte);
    }
  }
  
  public static int find(List<ByteBuffer> paramList, ByteBuffer paramByteBuffer)
  {
    paramByteBuffer = toArray(paramByteBuffer);
    int i = 0;
    while (i < paramList.size())
    {
      if (Arrays.equals(toArray((ByteBuffer)paramList.get(i)), paramByteBuffer)) {
        return i;
      }
      i += 1;
    }
    return -1;
  }
  
  public static ByteBuffer from(ByteBuffer paramByteBuffer, int paramInt)
  {
    paramByteBuffer = paramByteBuffer.duplicate();
    paramByteBuffer.position(paramByteBuffer.position() + paramInt);
    return paramByteBuffer;
  }
  
  public static final MappedByteBuffer map(File paramFile)
    throws IOException
  {
    FileInputStream localFileInputStream = new FileInputStream(paramFile);
    paramFile = localFileInputStream.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, paramFile.length());
    localFileInputStream.close();
    return paramFile;
  }
  
  public static final MappedByteBuffer map(String paramString)
    throws IOException
  {
    return map(new File(paramString));
  }
  
  public static int read(ReadableByteChannel paramReadableByteChannel, ByteBuffer paramByteBuffer)
    throws IOException
  {
    int i = paramByteBuffer.position();
    while ((paramReadableByteChannel.read(paramByteBuffer) != -1) && (paramByteBuffer.hasRemaining())) {}
    return paramByteBuffer.position() - i;
  }
  
  public static int read(ReadableByteChannel paramReadableByteChannel, ByteBuffer paramByteBuffer, int paramInt)
    throws IOException
  {
    ByteBuffer localByteBuffer = paramByteBuffer.duplicate();
    localByteBuffer.limit(Math.min(localByteBuffer.position() + paramInt, localByteBuffer.limit()));
    while ((paramReadableByteChannel.read(localByteBuffer) != -1) && (localByteBuffer.hasRemaining())) {}
    paramInt = localByteBuffer.position();
    int i = paramByteBuffer.position();
    paramByteBuffer.position(localByteBuffer.position());
    return paramInt - i;
  }
  
  public static ByteBuffer read(ByteBuffer paramByteBuffer)
  {
    ByteBuffer localByteBuffer = paramByteBuffer.duplicate();
    paramByteBuffer.position(paramByteBuffer.limit());
    return localByteBuffer;
  }
  
  public static final ByteBuffer read(ByteBuffer paramByteBuffer, int paramInt)
  {
    ByteBuffer localByteBuffer = paramByteBuffer.duplicate();
    paramInt = paramByteBuffer.position() + paramInt;
    localByteBuffer.limit(paramInt);
    paramByteBuffer.position(paramInt);
    return localByteBuffer;
  }
  
  public static byte readByte(ReadableByteChannel paramReadableByteChannel)
    throws IOException
  {
    ByteBuffer localByteBuffer = ByteBuffer.allocate(1);
    paramReadableByteChannel.read(localByteBuffer);
    localByteBuffer.flip();
    return localByteBuffer.get();
  }
  
  public static int readInt(ReadableByteChannel paramReadableByteChannel)
    throws IOException
  {
    ByteBuffer localByteBuffer = ByteBuffer.allocate(4);
    paramReadableByteChannel.read(localByteBuffer);
    localByteBuffer.flip();
    return localByteBuffer.getInt();
  }
  
  public static int readInt(ReadableByteChannel paramReadableByteChannel, ByteOrder paramByteOrder)
    throws IOException
  {
    paramByteOrder = ByteBuffer.allocate(4).order(paramByteOrder);
    paramReadableByteChannel.read(paramByteOrder);
    paramByteOrder.flip();
    return paramByteOrder.getInt();
  }
  
  public static byte[] readNByte(ReadableByteChannel paramReadableByteChannel, int paramInt)
    throws IOException
  {
    byte[] arrayOfByte = new byte[paramInt];
    paramReadableByteChannel.read(ByteBuffer.wrap(arrayOfByte));
    return arrayOfByte;
  }
  
  public static String readNullTermString(ByteBuffer paramByteBuffer)
  {
    return readNullTermString(paramByteBuffer, Charset.defaultCharset());
  }
  
  @SuppressLint({"NewApi"})
  public static String readNullTermString(ByteBuffer paramByteBuffer, Charset paramCharset)
  {
    ByteBuffer localByteBuffer = paramByteBuffer.duplicate();
    while ((paramByteBuffer.hasRemaining()) && (paramByteBuffer.get() != 0)) {}
    if (paramByteBuffer.hasRemaining()) {
      localByteBuffer.limit(paramByteBuffer.position() - 1);
    }
    return new String(toArray(localByteBuffer), paramCharset);
  }
  
  public static String readPascalString(ByteBuffer paramByteBuffer)
  {
    return readString(paramByteBuffer, paramByteBuffer.get() & 0xFF);
  }
  
  public static String readPascalString(ByteBuffer paramByteBuffer, int paramInt)
  {
    paramByteBuffer = read(paramByteBuffer, paramInt + 1);
    return new String(toArray(read(paramByteBuffer, Math.min(paramByteBuffer.get() & 0xFF, paramInt))));
  }
  
  public static String readString(ByteBuffer paramByteBuffer, int paramInt)
  {
    return new String(toArray(read(paramByteBuffer, paramInt)));
  }
  
  public static ByteBuffer search(ByteBuffer paramByteBuffer, int paramInt, byte... paramVarArgs)
  {
    ByteBuffer localByteBuffer = paramByteBuffer.duplicate();
    int k = 0;
    int i = paramByteBuffer.position();
    int j = paramInt;
    paramInt = k;
    for (;;)
    {
      if (paramByteBuffer.hasRemaining())
      {
        if (paramByteBuffer.get() != paramVarArgs[paramInt]) {
          break label87;
        }
        k = paramInt + 1;
        paramInt = k;
        if (k != paramVarArgs.length) {
          continue;
        }
        if (j == 0)
        {
          paramByteBuffer.position(i);
          localByteBuffer.limit(paramByteBuffer.position());
        }
      }
      else
      {
        return localByteBuffer;
      }
      j -= 1;
      paramInt = 0;
      continue;
      label87:
      if (paramInt != 0)
      {
        paramInt = 0;
        i += 1;
        paramByteBuffer.position(i);
      }
      else
      {
        i = paramByteBuffer.position();
      }
    }
  }
  
  public static int skip(ByteBuffer paramByteBuffer, int paramInt)
  {
    paramInt = Math.min(paramByteBuffer.remaining(), paramInt);
    paramByteBuffer.position(paramByteBuffer.position() + paramInt);
    return paramInt;
  }
  
  public static byte[] toArray(ByteBuffer paramByteBuffer)
  {
    byte[] arrayOfByte = new byte[paramByteBuffer.remaining()];
    paramByteBuffer.duplicate().get(arrayOfByte);
    return arrayOfByte;
  }
  
  public static byte[] toArray(ByteBuffer paramByteBuffer, int paramInt)
  {
    byte[] arrayOfByte = new byte[Math.min(paramByteBuffer.remaining(), paramInt)];
    paramByteBuffer.duplicate().get(arrayOfByte);
    return arrayOfByte;
  }
  
  public static FileChannelWrapper writableFileChannel(File paramFile)
    throws FileNotFoundException
  {
    return new FileChannelWrapper(new FileOutputStream(paramFile).getChannel(), paramFile.getAbsolutePath(), 0L);
  }
  
  public static FileChannelWrapper writableFileChannel(String paramString, long paramLong)
    throws FileNotFoundException
  {
    return new FileChannelWrapper(new FileOutputStream(paramString).getChannel(), paramString, paramLong);
  }
  
  public static void write(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2)
  {
    if (paramByteBuffer2.hasArray())
    {
      paramByteBuffer1.put(paramByteBuffer2.array(), paramByteBuffer2.arrayOffset() + paramByteBuffer2.position(), Math.min(paramByteBuffer1.remaining(), paramByteBuffer2.remaining()));
      return;
    }
    paramByteBuffer1.put(toArray(paramByteBuffer2, paramByteBuffer1.remaining()));
  }
  
  public static void write(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2, int paramInt)
  {
    if (paramByteBuffer2.hasArray())
    {
      paramByteBuffer1.put(paramByteBuffer2.array(), paramByteBuffer2.arrayOffset() + paramByteBuffer2.position(), Math.min(paramByteBuffer2.remaining(), paramInt));
      return;
    }
    paramByteBuffer1.put(toArray(paramByteBuffer2, paramInt));
  }
  
  public static void writeByte(WritableByteChannel paramWritableByteChannel, byte paramByte)
    throws IOException
  {
    paramWritableByteChannel.write((ByteBuffer)ByteBuffer.allocate(1).put(paramByte).flip());
  }
  
  public static void writeInt(WritableByteChannel paramWritableByteChannel, int paramInt)
    throws IOException
  {
    paramWritableByteChannel.write((ByteBuffer)ByteBuffer.allocate(4).putInt(paramInt).flip());
  }
  
  public static void writeInt(WritableByteChannel paramWritableByteChannel, int paramInt, ByteOrder paramByteOrder)
    throws IOException
  {
    paramWritableByteChannel.write((ByteBuffer)ByteBuffer.allocate(4).order(paramByteOrder).putInt(paramInt).flip());
  }
  
  public static void writeLong(WritableByteChannel paramWritableByteChannel, long paramLong)
    throws IOException
  {
    paramWritableByteChannel.write((ByteBuffer)ByteBuffer.allocate(8).putLong(paramLong).flip());
  }
  
  public static void writePascalString(ByteBuffer paramByteBuffer, String paramString)
  {
    paramByteBuffer.put((byte)paramString.length());
    paramByteBuffer.put(JCodecUtil.asciiString(paramString));
  }
  
  public static void writePascalString(ByteBuffer paramByteBuffer, String paramString, int paramInt)
  {
    paramByteBuffer.put((byte)paramString.length());
    paramByteBuffer.put(JCodecUtil.asciiString(paramString));
    skip(paramByteBuffer, paramInt - paramString.length());
  }
  
  public static void writeTo(ByteBuffer paramByteBuffer, File paramFile)
    throws IOException
  {
    File localFile = null;
    try
    {
      paramFile = new FileOutputStream(paramFile).getChannel();
      localFile = paramFile;
      paramFile.write(paramByteBuffer);
      return;
    }
    finally
    {
      closeQuietly(localFile);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/NIOUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */