package org.jcodec;

public class AspectRatio
{
  public static final AspectRatio Extended_SAR = new AspectRatio(255);
  private int value;
  
  private AspectRatio(int paramInt)
  {
    this.value = paramInt;
  }
  
  public static AspectRatio fromValue(int paramInt)
  {
    if (paramInt == Extended_SAR.value) {
      return Extended_SAR;
    }
    return new AspectRatio(paramInt);
  }
  
  public int getValue()
  {
    return this.value;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/AspectRatio.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */