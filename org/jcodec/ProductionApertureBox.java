package org.jcodec;

public class ProductionApertureBox
  extends ClearApertureBox
{
  public ProductionApertureBox()
  {
    super(new Header(fourcc()));
  }
  
  public ProductionApertureBox(int paramInt1, int paramInt2)
  {
    super(new Header(fourcc()), paramInt1, paramInt2);
  }
  
  public ProductionApertureBox(Header paramHeader)
  {
    super(paramHeader);
  }
  
  public static String fourcc()
  {
    return "prof";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/ProductionApertureBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */