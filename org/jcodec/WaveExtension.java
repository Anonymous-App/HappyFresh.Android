package org.jcodec;

import java.util.HashMap;
import java.util.Map;

public class WaveExtension
  extends NodeBox
{
  private static final MyFactory FACTORY = new MyFactory();
  
  public WaveExtension(Header paramHeader)
  {
    super(paramHeader);
    this.factory = FACTORY;
  }
  
  public static String fourcc()
  {
    return "wave";
  }
  
  public static class MyFactory
    extends BoxFactory
  {
    private Map<String, Class<? extends Box>> mappings = new HashMap();
    
    public MyFactory()
    {
      this.mappings.put(FormatBox.fourcc(), FormatBox.class);
      this.mappings.put(EndianBox.fourcc(), EndianBox.class);
    }
    
    public Class<? extends Box> toClass(String paramString)
    {
      return (Class)this.mappings.get(paramString);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/WaveExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */