package com.appsee;

import java.util.EnumSet;

 enum vi
{
  static
  {
    G = new vi(AppseeBackgroundUploader.i("i\002|\034cN\"\027h\032k\003u\030{\037z\005k^+\017e\032v"), 1, 2L);
    k = new vi(AppseeBackgroundUploader.i("f\003n\005o\037mN2\033m\033v"), 2, 4L);
  }
  
  public static EnumSet<vi> i(long paramLong)
  {
    EnumSet localEnumSet = EnumSet.noneOf(vi.class);
    vi[] arrayOfvi = values();
    int i1 = arrayOfvi.length;
    int j = 0;
    for (int n = 0; j < i1; n = j)
    {
      vi localvi = arrayOfvi[n];
      if ((localvi.i & paramLong) == localvi.i) {
        localEnumSet.add(localvi);
      }
      j = n + 1;
    }
    return localEnumSet;
  }
  
  public long i()
  {
    return this.i;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/vi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */