package org.jcodec;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import junit.framework.Assert;

public class AvcCBox
  extends Box
{
  private int level;
  private int nalLengthSize;
  private List<ByteBuffer> ppsList = new ArrayList();
  private int profile;
  private int profileCompat;
  private List<ByteBuffer> spsList = new ArrayList();
  
  public AvcCBox()
  {
    super(new Header(fourcc()));
  }
  
  public AvcCBox(int paramInt1, int paramInt2, int paramInt3, List<ByteBuffer> paramList1, List<ByteBuffer> paramList2)
  {
    this();
    this.profile = paramInt1;
    this.profileCompat = paramInt2;
    this.level = paramInt3;
  }
  
  public AvcCBox(Box paramBox)
  {
    super(paramBox);
  }
  
  public AvcCBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public static String fourcc()
  {
    return "avcC";
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.put((byte)1);
    paramByteBuffer.put((byte)this.profile);
    paramByteBuffer.put((byte)this.profileCompat);
    paramByteBuffer.put((byte)this.level);
    paramByteBuffer.put((byte)-1);
    paramByteBuffer.put((byte)(this.spsList.size() | 0xE0));
    Iterator localIterator = this.spsList.iterator();
    ByteBuffer localByteBuffer;
    while (localIterator.hasNext())
    {
      localByteBuffer = (ByteBuffer)localIterator.next();
      paramByteBuffer.putShort((short)(localByteBuffer.remaining() + 1));
      paramByteBuffer.put((byte)103);
      NIOUtils.write(paramByteBuffer, localByteBuffer);
    }
    paramByteBuffer.put((byte)this.ppsList.size());
    localIterator = this.ppsList.iterator();
    while (localIterator.hasNext())
    {
      localByteBuffer = (ByteBuffer)localIterator.next();
      paramByteBuffer.putShort((short)(byte)(localByteBuffer.remaining() + 1));
      paramByteBuffer.put((byte)104);
      NIOUtils.write(paramByteBuffer, localByteBuffer);
    }
  }
  
  public int getNalLengthSize()
  {
    return this.nalLengthSize;
  }
  
  public List<ByteBuffer> getPpsList()
  {
    return this.ppsList;
  }
  
  public List<ByteBuffer> getSpsList()
  {
    return this.spsList;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    NIOUtils.skip(paramByteBuffer, 1);
    this.profile = (paramByteBuffer.get() & 0xFF);
    this.profileCompat = (paramByteBuffer.get() & 0xFF);
    this.level = (paramByteBuffer.get() & 0xFF);
    this.nalLengthSize = ((paramByteBuffer.get() & 0xFF & 0x3) + 1);
    int j = paramByteBuffer.get();
    int i = 0;
    int k;
    while (i < (j & 0x1F))
    {
      k = paramByteBuffer.getShort();
      Assert.assertEquals(39, paramByteBuffer.get() & 0x3F);
      this.spsList.add(NIOUtils.read(paramByteBuffer, k - 1));
      i += 1;
    }
    j = paramByteBuffer.get();
    i = 0;
    while (i < (j & 0xFF))
    {
      k = paramByteBuffer.getShort();
      Assert.assertEquals(40, paramByteBuffer.get() & 0x3F);
      this.ppsList.add(NIOUtils.read(paramByteBuffer, k - 1));
      i += 1;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/AvcCBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */