package org.jcodec;

import java.util.Arrays;

public class IntArrayList
{
  private static final int DEFAULT_GROW_AMOUNT = 128;
  private int growAmount;
  private int size;
  private int[] storage;
  
  public IntArrayList()
  {
    this(128);
  }
  
  public IntArrayList(int paramInt)
  {
    this.growAmount = paramInt;
    this.storage = new int[paramInt];
  }
  
  public void add(int paramInt)
  {
    if (this.size >= this.storage.length)
    {
      arrayOfInt = new int[this.storage.length + this.growAmount];
      System.arraycopy(this.storage, 0, arrayOfInt, 0, this.storage.length);
      this.storage = arrayOfInt;
    }
    int[] arrayOfInt = this.storage;
    int i = this.size;
    this.size = (i + 1);
    arrayOfInt[i] = paramInt;
  }
  
  public void addAll(int[] paramArrayOfInt)
  {
    if (this.size + paramArrayOfInt.length >= this.storage.length)
    {
      int[] arrayOfInt = new int[this.size + this.growAmount + paramArrayOfInt.length];
      System.arraycopy(this.storage, 0, arrayOfInt, 0, this.size);
      this.storage = arrayOfInt;
    }
    System.arraycopy(paramArrayOfInt, 0, this.storage, this.size, paramArrayOfInt.length);
    this.size += paramArrayOfInt.length;
  }
  
  public void fill(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt2 > this.storage.length)
    {
      int[] arrayOfInt = new int[this.growAmount + paramInt2];
      System.arraycopy(this.storage, 0, arrayOfInt, 0, this.storage.length);
      this.storage = arrayOfInt;
    }
    Arrays.fill(this.storage, paramInt1, paramInt2, paramInt3);
    this.size = Math.max(this.size, paramInt2);
  }
  
  public int get(int paramInt)
  {
    return this.storage[paramInt];
  }
  
  public void set(int paramInt1, int paramInt2)
  {
    this.storage[paramInt1] = paramInt2;
  }
  
  public int size()
  {
    return this.size;
  }
  
  public int[] toArray()
  {
    int[] arrayOfInt = new int[this.size];
    System.arraycopy(this.storage, 0, arrayOfInt, 0, this.size);
    return arrayOfInt;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/IntArrayList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */