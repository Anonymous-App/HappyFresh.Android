package org.jcodec;

public class MediaInfoBox
  extends NodeBox
{
  public MediaInfoBox()
  {
    super(new Header(fourcc()));
  }
  
  public MediaInfoBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public static String fourcc()
  {
    return "minf";
  }
  
  public DataInfoBox getDinf()
  {
    return (DataInfoBox)findFirst(this, DataInfoBox.class, new String[] { "dinf" });
  }
  
  public NodeBox getStbl()
  {
    return (NodeBox)findFirst(this, NodeBox.class, new String[] { "stbl" });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/MediaInfoBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */