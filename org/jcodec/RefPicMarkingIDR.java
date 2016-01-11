package org.jcodec;

public class RefPicMarkingIDR
{
  boolean discardDecodedPics;
  boolean useForlongTerm;
  
  public RefPicMarkingIDR(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.discardDecodedPics = paramBoolean1;
    this.useForlongTerm = paramBoolean2;
  }
  
  public boolean isDiscardDecodedPics()
  {
    return this.discardDecodedPics;
  }
  
  public boolean isUseForlongTerm()
  {
    return this.useForlongTerm;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/RefPicMarkingIDR.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */