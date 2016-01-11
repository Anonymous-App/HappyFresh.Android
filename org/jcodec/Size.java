package org.jcodec;

public class Size
{
  private int height;
  private int width;
  
  public Size(int paramInt1, int paramInt2)
  {
    this.width = paramInt1;
    this.height = paramInt2;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if (paramObject == null) {
        return false;
      }
      if (getClass() != paramObject.getClass()) {
        return false;
      }
      paramObject = (Size)paramObject;
      if (this.height != ((Size)paramObject).height) {
        return false;
      }
    } while (this.width == ((Size)paramObject).width);
    return false;
  }
  
  public int getHeight()
  {
    return this.height;
  }
  
  public int getWidth()
  {
    return this.width;
  }
  
  public int hashCode()
  {
    return (this.height + 31) * 31 + this.width;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/Size.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */