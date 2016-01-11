package com.appsee;

public class Dimension
{
  private int height;
  private int width;
  
  public Dimension() {}
  
  public Dimension(int paramInt1, int paramInt2)
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
      if ((paramObject == null) || (getClass() != paramObject.getClass())) {
        return false;
      }
      paramObject = (Dimension)paramObject;
    } while ((this.width == ((Dimension)paramObject).width) && (this.height == ((Dimension)paramObject).height));
    return false;
  }
  
  public int getHeight()
  {
    return this.height;
  }
  
  public double getRatio()
  {
    return this.width / this.height;
  }
  
  public int getWidth()
  {
    return this.width;
  }
  
  public void setHeight(int paramInt)
  {
    this.height = paramInt;
  }
  
  public void setWidth(int paramInt)
  {
    this.width = paramInt;
  }
  
  public void setWidthHeight(int paramInt1, int paramInt2)
  {
    this.width = paramInt1;
    this.height = paramInt2;
  }
  
  public String toString()
  {
    return this.width + "x" + this.height;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/Dimension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */