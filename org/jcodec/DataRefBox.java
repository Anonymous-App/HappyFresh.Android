package org.jcodec;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataRefBox
  extends NodeBox
{
  private static final MyFactory FACTORY = new MyFactory();
  
  public DataRefBox()
  {
    this(new Header(fourcc()));
  }
  
  private DataRefBox(Header paramHeader)
  {
    super(paramHeader);
    this.factory = FACTORY;
  }
  
  public static String fourcc()
  {
    return "dref";
  }
  
  public void doWrite(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.putInt(0);
    paramByteBuffer.putInt(this.boxes.size());
    super.doWrite(paramByteBuffer);
  }
  
  public void parse(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.getInt();
    paramByteBuffer.getInt();
    super.parse(paramByteBuffer);
  }
  
  public static class MyFactory
    extends BoxFactory
  {
    private Map<String, Class<? extends Box>> mappings = new HashMap();
    
    public MyFactory()
    {
      this.mappings.put(UrlBox.fourcc(), UrlBox.class);
      this.mappings.put(AliasBox.fourcc(), AliasBox.class);
      this.mappings.put("cios", AliasBox.class);
    }
    
    public Class<? extends Box> toClass(String paramString)
    {
      return (Class)this.mappings.get(paramString);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/DataRefBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */