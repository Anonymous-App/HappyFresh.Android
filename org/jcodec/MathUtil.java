package org.jcodec;

public class MathUtil
{
  public static final int abs(int paramInt)
  {
    int i = paramInt >> 31;
    return (paramInt ^ i) - i;
  }
  
  public static final int golomb(int paramInt)
  {
    if (paramInt == 0) {
      return 0;
    }
    return (abs(paramInt) << 1) - ((paramInt ^ 0xFFFFFFFF) >>> 31);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/MathUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */