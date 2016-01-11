package com.parse;

import java.util.Iterator;

class ParseTextUtils
{
  public static boolean equals(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    return (paramCharSequence1 == paramCharSequence2) || ((paramCharSequence1 != null) && (paramCharSequence1.equals(paramCharSequence2)));
  }
  
  public static boolean isEmpty(CharSequence paramCharSequence)
  {
    return (paramCharSequence == null) || (paramCharSequence.length() == 0);
  }
  
  static String join(CharSequence paramCharSequence, Iterable paramIterable)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 1;
    paramIterable = paramIterable.iterator();
    if (paramIterable.hasNext())
    {
      Object localObject = paramIterable.next();
      if (i != 0) {
        i = 0;
      }
      for (;;)
      {
        localStringBuilder.append(localObject);
        break;
        localStringBuilder.append(paramCharSequence);
      }
    }
    return localStringBuilder.toString();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseTextUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */