package org.jcodec;

import java.lang.reflect.Array;

public class IntObjectMap<T>
{
  private static final int GROW_BY = 128;
  private int size;
  private Object[] storage = new Object[''];
  
  public void clear()
  {
    int i = 0;
    while (i < this.storage.length)
    {
      this.storage[i] = null;
      i += 1;
    }
    this.size = 0;
  }
  
  public T get(int paramInt)
  {
    if (paramInt >= this.storage.length) {
      return null;
    }
    return (T)this.storage[paramInt];
  }
  
  public int[] keys()
  {
    int[] arrayOfInt = new int[this.size];
    int i = 0;
    int k;
    for (int j = 0; i < this.storage.length; j = k)
    {
      k = j;
      if (this.storage[i] != null)
      {
        arrayOfInt[j] = i;
        k = j + 1;
      }
      i += 1;
    }
    return arrayOfInt;
  }
  
  public void put(int paramInt, T paramT)
  {
    if (this.storage.length <= paramInt)
    {
      Object[] arrayOfObject = new Object[this.storage.length + 128];
      System.arraycopy(this.storage, 0, arrayOfObject, 0, this.storage.length);
      this.storage = arrayOfObject;
    }
    if (this.storage[paramInt] == null) {
      this.size += 1;
    }
    this.storage[paramInt] = paramT;
  }
  
  public void remove(int paramInt)
  {
    if (this.storage[paramInt] != null) {
      this.size -= 1;
    }
    this.storage[paramInt] = null;
  }
  
  public int size()
  {
    return this.size;
  }
  
  public T[] values(T[] paramArrayOfT)
  {
    paramArrayOfT = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), this.size);
    int i = 0;
    int k;
    for (int j = 0; i < this.storage.length; j = k)
    {
      k = j;
      if (this.storage[i] != null)
      {
        paramArrayOfT[j] = this.storage[i];
        k = j + 1;
      }
      i += 1;
    }
    return paramArrayOfT;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/IntObjectMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */