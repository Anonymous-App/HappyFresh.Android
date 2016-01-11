package com.activeandroid.util;

import java.util.Comparator;

public class NaturalOrderComparator
  implements Comparator<Object>
{
  static char charAt(String paramString, int paramInt)
  {
    if (paramInt >= paramString.length()) {
      return '\000';
    }
    return paramString.charAt(paramInt);
  }
  
  public int compare(Object paramObject1, Object paramObject2)
  {
    paramObject1 = paramObject1.toString();
    paramObject2 = paramObject2.toString();
    int j = 0;
    int k;
    for (int i = 0;; i = k)
    {
      int i1 = 0;
      int m = 0;
      char c1 = charAt((String)paramObject1, j);
      char c3 = charAt((String)paramObject2, i);
      k = j;
      char c2;
      int n;
      if (!Character.isSpaceChar(c1))
      {
        c2 = c3;
        j = i;
        n = i1;
        if (c1 != '0') {}
      }
      else
      {
        if (c1 == '0') {}
        for (j = m + 1;; j = 0)
        {
          k += 1;
          c1 = charAt((String)paramObject1, k);
          m = j;
          break;
        }
      }
      if ((Character.isSpaceChar(c2)) || (c2 == '0'))
      {
        if (c2 == '0') {}
        for (i = n + 1;; i = 0)
        {
          j += 1;
          c2 = charAt((String)paramObject2, j);
          n = i;
          break;
        }
      }
      if ((Character.isDigit(c1)) && (Character.isDigit(c2)))
      {
        i = compareRight(((String)paramObject1).substring(k), ((String)paramObject2).substring(j));
        if (i != 0) {
          return i;
        }
      }
      if ((c1 == 0) && (c2 == 0)) {
        return m - n;
      }
      if (c1 < c2) {
        return -1;
      }
      if (c1 > c2) {
        return 1;
      }
      i = k + 1;
      k = j + 1;
      j = i;
    }
  }
  
  int compareRight(String paramString1, String paramString2)
  {
    int m = 0;
    int k = 0;
    int j = 0;
    char c1 = charAt(paramString1, k);
    char c2 = charAt(paramString2, j);
    if ((!Character.isDigit(c1)) && (!Character.isDigit(c2))) {
      return m;
    }
    if (!Character.isDigit(c1)) {
      return -1;
    }
    if (!Character.isDigit(c2)) {
      return 1;
    }
    int i;
    if (c1 < c2)
    {
      i = m;
      if (m == 0) {
        i = -1;
      }
    }
    label119:
    do
    {
      do
      {
        for (;;)
        {
          k += 1;
          j += 1;
          m = i;
          break;
          if (c1 <= c2) {
            break label119;
          }
          i = m;
          if (m == 0) {
            i = 1;
          }
        }
        i = m;
      } while (c1 != 0);
      i = m;
    } while (c2 != 0);
    return m;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/util/NaturalOrderComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */