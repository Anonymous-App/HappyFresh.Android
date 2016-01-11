package org.jcodec;

public class EncodedPixelBox
  extends ClearApertureBox
{
  public EncodedPixelBox()
  {
    super(new Header(fourcc()));
  }
  
  public EncodedPixelBox(int paramInt1, int paramInt2)
  {
    super(new Header(fourcc()), paramInt1, paramInt2);
  }
  
  public EncodedPixelBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public static String fourcc()
  {
    return "enof";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/EncodedPixelBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */