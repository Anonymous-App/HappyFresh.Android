package org.jcodec;

import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class NodeBox
  extends Box
{
  private static final int MAX_BOX_SIZE = 134217728;
  protected List<Box> boxes = new LinkedList();
  protected BoxFactory factory = BoxFactory.getDefault();
  
  public NodeBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public NodeBox(NodeBox paramNodeBox)
  {
    super(paramNodeBox);
    this.boxes = paramNodeBox.boxes;
    this.factory = paramNodeBox.factory;
  }
  
  public static Box newBox(Header paramHeader, BoxFactory paramBoxFactory)
  {
    paramBoxFactory = paramBoxFactory.toClass(paramHeader.getFourcc());
    if (paramBoxFactory == null) {
      return new LeafBox(paramHeader);
    }
    try
    {
      paramHeader = (Box)paramBoxFactory.getConstructor(new Class[] { Header.class }).newInstance(new Object[] { paramHeader });
      return paramHeader;
    }
    catch (NoSuchMethodException paramHeader)
    {
      paramHeader = (Box)paramBoxFactory.newInstance();
      return paramHeader;
    }
    catch (Exception paramHeader)
    {
      throw new RuntimeException(paramHeader);
    }
  }
  
  public static Box parseBox(ByteBuffer paramByteBuffer, Header paramHeader, BoxFactory paramBoxFactory)
  {
    paramBoxFactory = newBox(paramHeader, paramBoxFactory);
    if (paramHeader.getBodySize() < 134217728L)
    {
      paramBoxFactory.parse(paramByteBuffer);
      return paramBoxFactory;
    }
    return new LeafBox(new Header("free", 8L));
  }
  
  public static Box parseChildBox(ByteBuffer paramByteBuffer, BoxFactory paramBoxFactory)
  {
    Object localObject = paramByteBuffer.duplicate();
    while ((paramByteBuffer.remaining() >= 4) && (((ByteBuffer)localObject).getInt() == 0)) {
      paramByteBuffer.getInt();
    }
    if (paramByteBuffer.remaining() < 4) {}
    do
    {
      return null;
      localObject = Header.read(paramByteBuffer);
    } while ((localObject == null) || (paramByteBuffer.remaining() < ((Header)localObject).getBodySize()));
    return parseBox(NIOUtils.read(paramByteBuffer, (int)((Header)localObject).getBodySize()), (Header)localObject, paramBoxFactory);
  }
  
  public void add(Box paramBox)
  {
    this.boxes.add(paramBox);
  }
  
  public void addFirst(MovieHeaderBox paramMovieHeaderBox)
  {
    this.boxes.add(0, paramMovieHeaderBox);
  }
  
  protected void doWrite(ByteBuffer paramByteBuffer)
  {
    Iterator localIterator = this.boxes.iterator();
    while (localIterator.hasNext()) {
      ((Box)localIterator.next()).write(paramByteBuffer);
    }
  }
  
  public void dump(StringBuilder paramStringBuilder)
  {
    super.dump(paramStringBuilder);
    paramStringBuilder.append(": {\n");
    dumpBoxes(paramStringBuilder);
    paramStringBuilder.append("\n}");
  }
  
  protected void dumpBoxes(StringBuilder paramStringBuilder)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = this.boxes.iterator();
    while (localIterator.hasNext())
    {
      ((Box)localIterator.next()).dump(localStringBuilder);
      if (localIterator.hasNext()) {
        localStringBuilder.append(",\n");
      }
    }
    paramStringBuilder.append(localStringBuilder.toString().replaceAll("([^\n]*)\n", "  $1\n"));
  }
  
  public List<Box> getBoxes()
  {
    return this.boxes;
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    while (paramByteBuffer.remaining() >= 8)
    {
      Box localBox = parseChildBox(paramByteBuffer, this.factory);
      if (localBox != null) {
        this.boxes.add(localBox);
      }
    }
  }
  
  public void removeChildren(String... paramVarArgs)
  {
    Iterator localIterator = this.boxes.iterator();
    label72:
    while (localIterator.hasNext())
    {
      String str = ((Box)localIterator.next()).getFourcc();
      int j = paramVarArgs.length;
      int i = 0;
      for (;;)
      {
        if (i >= j) {
          break label72;
        }
        if (paramVarArgs[i].equals(str))
        {
          localIterator.remove();
          break;
        }
        i += 1;
      }
    }
  }
  
  public void replace(String paramString, Box paramBox)
  {
    removeChildren(new String[] { paramString });
    add(paramBox);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/NodeBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */