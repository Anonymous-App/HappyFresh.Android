package io.fabric.sdk.android.services.common;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueueFile
  implements Closeable
{
  static final int HEADER_LENGTH = 16;
  private static final int INITIAL_LENGTH = 4096;
  private static final Logger LOGGER = Logger.getLogger(QueueFile.class.getName());
  private final byte[] buffer = new byte[16];
  private int elementCount;
  int fileLength;
  private Element first;
  private Element last;
  private final RandomAccessFile raf;
  
  public QueueFile(File paramFile)
    throws IOException
  {
    if (!paramFile.exists()) {
      initialize(paramFile);
    }
    this.raf = open(paramFile);
    readHeader();
  }
  
  QueueFile(RandomAccessFile paramRandomAccessFile)
    throws IOException
  {
    this.raf = paramRandomAccessFile;
    readHeader();
  }
  
  private void expandIfNecessary(int paramInt)
    throws IOException
  {
    int m = paramInt + 4;
    paramInt = remainingBytes();
    if (paramInt >= m) {
      return;
    }
    int i = this.fileLength;
    int k;
    int j;
    do
    {
      k = paramInt + i;
      j = i << 1;
      i = j;
      paramInt = k;
    } while (k < m);
    setLength(j);
    paramInt = wrapPosition(this.last.position + 4 + this.last.length);
    if (paramInt < this.first.position)
    {
      FileChannel localFileChannel = this.raf.getChannel();
      localFileChannel.position(this.fileLength);
      paramInt -= 4;
      if (localFileChannel.transferTo(16L, paramInt, localFileChannel) != paramInt) {
        throw new AssertionError("Copied insufficient number of bytes!");
      }
    }
    if (this.last.position < this.first.position)
    {
      paramInt = this.fileLength + this.last.position - 16;
      writeHeader(j, this.elementCount, this.first.position, paramInt);
      this.last = new Element(paramInt, this.last.length);
    }
    for (;;)
    {
      this.fileLength = j;
      return;
      writeHeader(j, this.elementCount, this.first.position, this.last.position);
    }
  }
  
  private static void initialize(File paramFile)
    throws IOException
  {
    File localFile = new File(paramFile.getPath() + ".tmp");
    RandomAccessFile localRandomAccessFile = open(localFile);
    try
    {
      localRandomAccessFile.setLength(4096L);
      localRandomAccessFile.seek(0L);
      byte[] arrayOfByte = new byte[16];
      writeInts(arrayOfByte, new int[] { 4096, 0, 0, 0 });
      localRandomAccessFile.write(arrayOfByte);
      localRandomAccessFile.close();
      if (!localFile.renameTo(paramFile)) {
        throw new IOException("Rename failed!");
      }
    }
    finally
    {
      localRandomAccessFile.close();
    }
  }
  
  private static <T> T nonNull(T paramT, String paramString)
  {
    if (paramT == null) {
      throw new NullPointerException(paramString);
    }
    return paramT;
  }
  
  private static RandomAccessFile open(File paramFile)
    throws FileNotFoundException
  {
    return new RandomAccessFile(paramFile, "rwd");
  }
  
  private Element readElement(int paramInt)
    throws IOException
  {
    if (paramInt == 0) {
      return Element.NULL;
    }
    this.raf.seek(paramInt);
    return new Element(paramInt, this.raf.readInt());
  }
  
  private void readHeader()
    throws IOException
  {
    this.raf.seek(0L);
    this.raf.readFully(this.buffer);
    this.fileLength = readInt(this.buffer, 0);
    if (this.fileLength > this.raf.length()) {
      throw new IOException("File is truncated. Expected length: " + this.fileLength + ", Actual length: " + this.raf.length());
    }
    this.elementCount = readInt(this.buffer, 4);
    int i = readInt(this.buffer, 8);
    int j = readInt(this.buffer, 12);
    this.first = readElement(i);
    this.last = readElement(j);
  }
  
  private static int readInt(byte[] paramArrayOfByte, int paramInt)
  {
    return ((paramArrayOfByte[paramInt] & 0xFF) << 24) + ((paramArrayOfByte[(paramInt + 1)] & 0xFF) << 16) + ((paramArrayOfByte[(paramInt + 2)] & 0xFF) << 8) + (paramArrayOfByte[(paramInt + 3)] & 0xFF);
  }
  
  private int remainingBytes()
  {
    return this.fileLength - usedBytes();
  }
  
  private void ringRead(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
    throws IOException
  {
    paramInt1 = wrapPosition(paramInt1);
    if (paramInt1 + paramInt3 <= this.fileLength)
    {
      this.raf.seek(paramInt1);
      this.raf.readFully(paramArrayOfByte, paramInt2, paramInt3);
      return;
    }
    int i = this.fileLength - paramInt1;
    this.raf.seek(paramInt1);
    this.raf.readFully(paramArrayOfByte, paramInt2, i);
    this.raf.seek(16L);
    this.raf.readFully(paramArrayOfByte, paramInt2 + i, paramInt3 - i);
  }
  
  private void ringWrite(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
    throws IOException
  {
    paramInt1 = wrapPosition(paramInt1);
    if (paramInt1 + paramInt3 <= this.fileLength)
    {
      this.raf.seek(paramInt1);
      this.raf.write(paramArrayOfByte, paramInt2, paramInt3);
      return;
    }
    int i = this.fileLength - paramInt1;
    this.raf.seek(paramInt1);
    this.raf.write(paramArrayOfByte, paramInt2, i);
    this.raf.seek(16L);
    this.raf.write(paramArrayOfByte, paramInt2 + i, paramInt3 - i);
  }
  
  private void setLength(int paramInt)
    throws IOException
  {
    this.raf.setLength(paramInt);
    this.raf.getChannel().force(true);
  }
  
  private int wrapPosition(int paramInt)
  {
    if (paramInt < this.fileLength) {
      return paramInt;
    }
    return paramInt + 16 - this.fileLength;
  }
  
  private void writeHeader(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws IOException
  {
    writeInts(this.buffer, new int[] { paramInt1, paramInt2, paramInt3, paramInt4 });
    this.raf.seek(0L);
    this.raf.write(this.buffer);
  }
  
  private static void writeInt(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    paramArrayOfByte[paramInt1] = ((byte)(paramInt2 >> 24));
    paramArrayOfByte[(paramInt1 + 1)] = ((byte)(paramInt2 >> 16));
    paramArrayOfByte[(paramInt1 + 2)] = ((byte)(paramInt2 >> 8));
    paramArrayOfByte[(paramInt1 + 3)] = ((byte)paramInt2);
  }
  
  private static void writeInts(byte[] paramArrayOfByte, int... paramVarArgs)
  {
    int j = 0;
    int k = paramVarArgs.length;
    int i = 0;
    while (i < k)
    {
      writeInt(paramArrayOfByte, j, paramVarArgs[i]);
      j += 4;
      i += 1;
    }
  }
  
  public void add(byte[] paramArrayOfByte)
    throws IOException
  {
    add(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public void add(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    try
    {
      nonNull(paramArrayOfByte, "buffer");
      if (((paramInt1 | paramInt2) < 0) || (paramInt2 > paramArrayOfByte.length - paramInt1)) {
        throw new IndexOutOfBoundsException();
      }
    }
    finally {}
    expandIfNecessary(paramInt2);
    boolean bool = isEmpty();
    int i;
    Element localElement;
    if (bool)
    {
      i = 16;
      localElement = new Element(i, paramInt2);
      writeInt(this.buffer, 0, paramInt2);
      ringWrite(localElement.position, this.buffer, 0, 4);
      ringWrite(localElement.position + 4, paramArrayOfByte, paramInt1, paramInt2);
      if (!bool) {
        break label196;
      }
    }
    label196:
    for (paramInt1 = localElement.position;; paramInt1 = this.first.position)
    {
      writeHeader(this.fileLength, this.elementCount + 1, paramInt1, localElement.position);
      this.last = localElement;
      this.elementCount += 1;
      if (bool) {
        this.first = this.last;
      }
      return;
      i = wrapPosition(this.last.position + 4 + this.last.length);
      break;
    }
  }
  
  public void clear()
    throws IOException
  {
    try
    {
      writeHeader(4096, 0, 0, 0);
      this.elementCount = 0;
      this.first = Element.NULL;
      this.last = Element.NULL;
      if (this.fileLength > 4096) {
        setLength(4096);
      }
      this.fileLength = 4096;
      return;
    }
    finally {}
  }
  
  public void close()
    throws IOException
  {
    try
    {
      this.raf.close();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void forEach(ElementReader paramElementReader)
    throws IOException
  {
    try
    {
      int j = this.first.position;
      int i = 0;
      while (i < this.elementCount)
      {
        Element localElement = readElement(j);
        paramElementReader.read(new ElementInputStream(localElement, null), localElement.length);
        j = wrapPosition(localElement.position + 4 + localElement.length);
        i += 1;
      }
      return;
    }
    finally {}
  }
  
  public boolean hasSpaceFor(int paramInt1, int paramInt2)
  {
    return usedBytes() + 4 + paramInt1 <= paramInt2;
  }
  
  /* Error */
  public boolean isEmpty()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 144	io/fabric/sdk/android/services/common/QueueFile:elementCount	I
    //   6: istore_1
    //   7: iload_1
    //   8: ifne +9 -> 17
    //   11: iconst_1
    //   12: istore_2
    //   13: aload_0
    //   14: monitorexit
    //   15: iload_2
    //   16: ireturn
    //   17: iconst_0
    //   18: istore_2
    //   19: goto -6 -> 13
    //   22: astore_3
    //   23: aload_0
    //   24: monitorexit
    //   25: aload_3
    //   26: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	27	0	this	QueueFile
    //   6	2	1	i	int
    //   12	7	2	bool	boolean
    //   22	4	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	7	22	finally
  }
  
  public void peek(ElementReader paramElementReader)
    throws IOException
  {
    try
    {
      if (this.elementCount > 0) {
        paramElementReader.read(new ElementInputStream(this.first, null), this.first.length);
      }
      return;
    }
    finally
    {
      paramElementReader = finally;
      throw paramElementReader;
    }
  }
  
  /* Error */
  public byte[] peek()
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual 264	io/fabric/sdk/android/services/common/QueueFile:isEmpty	()Z
    //   6: istore_2
    //   7: iload_2
    //   8: ifeq +9 -> 17
    //   11: aconst_null
    //   12: astore_3
    //   13: aload_0
    //   14: monitorexit
    //   15: aload_3
    //   16: areturn
    //   17: aload_0
    //   18: getfield 118	io/fabric/sdk/android/services/common/QueueFile:first	Lio/fabric/sdk/android/services/common/QueueFile$Element;
    //   21: getfield 116	io/fabric/sdk/android/services/common/QueueFile$Element:length	I
    //   24: istore_1
    //   25: iload_1
    //   26: newarray <illegal type>
    //   28: astore_3
    //   29: aload_0
    //   30: aload_0
    //   31: getfield 118	io/fabric/sdk/android/services/common/QueueFile:first	Lio/fabric/sdk/android/services/common/QueueFile$Element;
    //   34: getfield 113	io/fabric/sdk/android/services/common/QueueFile$Element:position	I
    //   37: iconst_4
    //   38: iadd
    //   39: aload_3
    //   40: iconst_0
    //   41: iload_1
    //   42: invokespecial 95	io/fabric/sdk/android/services/common/QueueFile:ringRead	(I[BII)V
    //   45: goto -32 -> 13
    //   48: astore_3
    //   49: aload_0
    //   50: monitorexit
    //   51: aload_3
    //   52: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	53	0	this	QueueFile
    //   24	18	1	i	int
    //   6	2	2	bool	boolean
    //   12	28	3	arrayOfByte	byte[]
    //   48	4	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	7	48	finally
    //   17	45	48	finally
  }
  
  public void remove()
    throws IOException
  {
    try
    {
      if (isEmpty()) {
        throw new NoSuchElementException();
      }
    }
    finally {}
    if (this.elementCount == 1) {
      clear();
    }
    for (;;)
    {
      return;
      int i = wrapPosition(this.first.position + 4 + this.first.length);
      ringRead(i, this.buffer, 0, 4);
      int j = readInt(this.buffer, 0);
      writeHeader(this.fileLength, this.elementCount - 1, i, this.last.position);
      this.elementCount -= 1;
      this.first = new Element(i, j);
    }
  }
  
  public int size()
  {
    try
    {
      int i = this.elementCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public String toString()
  {
    final StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getClass().getSimpleName()).append('[');
    localStringBuilder.append("fileLength=").append(this.fileLength);
    localStringBuilder.append(", size=").append(this.elementCount);
    localStringBuilder.append(", first=").append(this.first);
    localStringBuilder.append(", last=").append(this.last);
    localStringBuilder.append(", element lengths=[");
    try
    {
      forEach(new ElementReader()
      {
        boolean first = true;
        
        public void read(InputStream paramAnonymousInputStream, int paramAnonymousInt)
          throws IOException
        {
          if (this.first) {
            this.first = false;
          }
          for (;;)
          {
            localStringBuilder.append(paramAnonymousInt);
            return;
            localStringBuilder.append(", ");
          }
        }
      });
      localStringBuilder.append("]]");
      return localStringBuilder.toString();
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        LOGGER.log(Level.WARNING, "read error", localIOException);
      }
    }
  }
  
  public int usedBytes()
  {
    if (this.elementCount == 0) {
      return 16;
    }
    if (this.last.position >= this.first.position) {
      return this.last.position - this.first.position + 4 + this.last.length + 16;
    }
    return this.last.position + 4 + this.last.length + this.fileLength - this.first.position;
  }
  
  static class Element
  {
    static final int HEADER_LENGTH = 4;
    static final Element NULL = new Element(0, 0);
    final int length;
    final int position;
    
    Element(int paramInt1, int paramInt2)
    {
      this.position = paramInt1;
      this.length = paramInt2;
    }
    
    public String toString()
    {
      return getClass().getSimpleName() + "[" + "position = " + this.position + ", length = " + this.length + "]";
    }
  }
  
  private final class ElementInputStream
    extends InputStream
  {
    private int position;
    private int remaining;
    
    private ElementInputStream(QueueFile.Element paramElement)
    {
      this.position = QueueFile.this.wrapPosition(paramElement.position + 4);
      this.remaining = paramElement.length;
    }
    
    public int read()
      throws IOException
    {
      if (this.remaining == 0) {
        return -1;
      }
      QueueFile.this.raf.seek(this.position);
      int i = QueueFile.this.raf.read();
      this.position = QueueFile.this.wrapPosition(this.position + 1);
      this.remaining -= 1;
      return i;
    }
    
    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      QueueFile.nonNull(paramArrayOfByte, "buffer");
      if (((paramInt1 | paramInt2) < 0) || (paramInt2 > paramArrayOfByte.length - paramInt1)) {
        throw new ArrayIndexOutOfBoundsException();
      }
      if (this.remaining > 0)
      {
        int i = paramInt2;
        if (paramInt2 > this.remaining) {
          i = this.remaining;
        }
        QueueFile.this.ringRead(this.position, paramArrayOfByte, paramInt1, i);
        this.position = QueueFile.this.wrapPosition(this.position + i);
        this.remaining -= i;
        return i;
      }
      return -1;
    }
  }
  
  public static abstract interface ElementReader
  {
    public abstract void read(InputStream paramInputStream, int paramInt)
      throws IOException;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/common/QueueFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */