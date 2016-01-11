package com.parse;

class Numbers
{
  static Number add(Number paramNumber1, Number paramNumber2)
  {
    if (((paramNumber1 instanceof Double)) || ((paramNumber2 instanceof Double))) {
      return Double.valueOf(paramNumber1.doubleValue() + paramNumber2.doubleValue());
    }
    if (((paramNumber1 instanceof Float)) || ((paramNumber2 instanceof Float))) {
      return Float.valueOf(paramNumber1.floatValue() + paramNumber2.floatValue());
    }
    if (((paramNumber1 instanceof Long)) || ((paramNumber2 instanceof Long))) {
      return Long.valueOf(paramNumber1.longValue() + paramNumber2.longValue());
    }
    if (((paramNumber1 instanceof Integer)) || ((paramNumber2 instanceof Integer))) {
      return Integer.valueOf(paramNumber1.intValue() + paramNumber2.intValue());
    }
    if (((paramNumber1 instanceof Short)) || ((paramNumber2 instanceof Short))) {
      return Integer.valueOf(paramNumber1.shortValue() + paramNumber2.shortValue());
    }
    if (((paramNumber1 instanceof Byte)) || ((paramNumber2 instanceof Byte))) {
      return Integer.valueOf(paramNumber1.byteValue() + paramNumber2.byteValue());
    }
    throw new RuntimeException("Unknown number type.");
  }
  
  static int compare(Number paramNumber1, Number paramNumber2)
  {
    if (((paramNumber1 instanceof Double)) || ((paramNumber2 instanceof Double))) {
      return (int)Math.signum(paramNumber1.doubleValue() - paramNumber2.doubleValue());
    }
    if (((paramNumber1 instanceof Float)) || ((paramNumber2 instanceof Float))) {
      return (int)Math.signum(paramNumber1.floatValue() - paramNumber2.floatValue());
    }
    if (((paramNumber1 instanceof Long)) || ((paramNumber2 instanceof Long)))
    {
      long l = paramNumber1.longValue() - paramNumber2.longValue();
      if (l < 0L) {
        return -1;
      }
      if (l > 0L) {
        return 1;
      }
      return 0;
    }
    if (((paramNumber1 instanceof Integer)) || ((paramNumber2 instanceof Integer))) {
      return paramNumber1.intValue() - paramNumber2.intValue();
    }
    if (((paramNumber1 instanceof Short)) || ((paramNumber2 instanceof Short))) {
      return paramNumber1.shortValue() - paramNumber2.shortValue();
    }
    if (((paramNumber1 instanceof Byte)) || ((paramNumber2 instanceof Byte))) {
      return paramNumber1.byteValue() - paramNumber2.byteValue();
    }
    throw new RuntimeException("Unknown number type.");
  }
  
  static Number subtract(Number paramNumber1, Number paramNumber2)
  {
    if (((paramNumber1 instanceof Double)) || ((paramNumber2 instanceof Double))) {
      return Double.valueOf(paramNumber1.doubleValue() - paramNumber2.doubleValue());
    }
    if (((paramNumber1 instanceof Float)) || ((paramNumber2 instanceof Float))) {
      return Float.valueOf(paramNumber1.floatValue() - paramNumber2.floatValue());
    }
    if (((paramNumber1 instanceof Long)) || ((paramNumber2 instanceof Long))) {
      return Long.valueOf(paramNumber1.longValue() - paramNumber2.longValue());
    }
    if (((paramNumber1 instanceof Integer)) || ((paramNumber2 instanceof Integer))) {
      return Integer.valueOf(paramNumber1.intValue() - paramNumber2.intValue());
    }
    if (((paramNumber1 instanceof Short)) || ((paramNumber2 instanceof Short))) {
      return Integer.valueOf(paramNumber1.shortValue() - paramNumber2.shortValue());
    }
    if (((paramNumber1 instanceof Byte)) || ((paramNumber2 instanceof Byte))) {
      return Integer.valueOf(paramNumber1.byteValue() - paramNumber2.byteValue());
    }
    throw new RuntimeException("Unknown number type.");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/Numbers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */