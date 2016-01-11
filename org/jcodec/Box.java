package org.jcodec;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import junit.framework.Assert;

public abstract class Box
{
  protected Header header;
  
  public Box(Box paramBox)
  {
    this.header = paramBox.header;
  }
  
  public Box(Header paramHeader)
  {
    this.header = paramHeader;
  }
  
  public static <T> T[] findAll(Box paramBox, Class<T> paramClass, String... paramVarArgs)
  {
    LinkedList localLinkedList1 = new LinkedList();
    LinkedList localLinkedList2 = new LinkedList();
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      localLinkedList2.add(paramVarArgs[i]);
      i += 1;
    }
    findSub(paramBox, localLinkedList2, localLinkedList1);
    return localLinkedList1.toArray((Object[])Array.newInstance(paramClass, 0));
  }
  
  public static Box[] findAll(Box paramBox, String... paramVarArgs)
  {
    return (Box[])findAll(paramBox, Box.class, paramVarArgs);
  }
  
  public static <T> T findFirst(NodeBox paramNodeBox, Class<T> paramClass, String... paramVarArgs)
  {
    paramNodeBox = (Object[])findAll(paramNodeBox, paramClass, paramVarArgs);
    if (paramNodeBox.length > 0) {
      return paramNodeBox[0];
    }
    return null;
  }
  
  public static Box findFirst(NodeBox paramNodeBox, String... paramVarArgs)
  {
    return (Box)findFirst(paramNodeBox, Box.class, paramVarArgs);
  }
  
  private static void findSub(Box paramBox, List<String> paramList, Collection<Box> paramCollection)
  {
    if (paramList.size() > 0)
    {
      String str = (String)paramList.remove(0);
      if ((paramBox instanceof NodeBox))
      {
        paramBox = ((NodeBox)paramBox).getBoxes().iterator();
        while (paramBox.hasNext())
        {
          Box localBox = (Box)paramBox.next();
          if ((str == null) || (str.equals(localBox.header.getFourcc()))) {
            findSub(localBox, paramList, paramCollection);
          }
        }
      }
      paramList.add(0, str);
      return;
    }
    paramCollection.add(paramBox);
  }
  
  protected abstract void doWrite(ByteBuffer paramByteBuffer);
  
  protected void dump(StringBuilder paramStringBuilder)
  {
    paramStringBuilder.append("'" + this.header.getFourcc() + "'");
  }
  
  public String getFourcc()
  {
    return this.header.getFourcc();
  }
  
  public Header getHeader()
  {
    return this.header;
  }
  
  public abstract void parse(ByteBuffer paramByteBuffer);
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    dump(localStringBuilder);
    return localStringBuilder.toString();
  }
  
  public void write(ByteBuffer paramByteBuffer)
  {
    ByteBuffer localByteBuffer = paramByteBuffer.duplicate();
    NIOUtils.skip(paramByteBuffer, 8);
    doWrite(paramByteBuffer);
    this.header.setBodySize(paramByteBuffer.position() - localByteBuffer.position() - 8);
    Assert.assertEquals(this.header.headerSize(), 8L);
    this.header.write(localByteBuffer);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/Box.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */