package org.jcodec;

public class MediaBox
  extends NodeBox
{
  public MediaBox()
  {
    super(new Header(fourcc()));
  }
  
  public MediaBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public static String fourcc()
  {
    return "mdia";
  }
  
  public MediaInfoBox getMinf()
  {
    return (MediaInfoBox)Box.findFirst(this, MediaInfoBox.class, new String[] { "minf" });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/MediaBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */