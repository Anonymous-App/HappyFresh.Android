package org.jcodec;

public class DataInfoBox
  extends NodeBox
{
  public DataInfoBox()
  {
    super(new Header(fourcc()));
  }
  
  public DataInfoBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public static String fourcc()
  {
    return "dinf";
  }
  
  public DataRefBox getDref()
  {
    return (DataRefBox)findFirst(this, DataRefBox.class, new String[] { "dref" });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/DataInfoBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */