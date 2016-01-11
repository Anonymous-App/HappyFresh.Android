package org.jcodec;

import java.util.Arrays;

public class LongArrayList
{
  private static final int DEFAULT_GROW_AMOUNT = 128;
  private int growAmount;
  private int size;
  private long[] storage;
  
  public LongArrayList()
  {
    this(128);
  }
  
  public LongArrayList(int paramInt)
  {
    this.growAmount = paramInt;
    this.storage = new long[paramInt];
  }
  
  public void add(long paramLong)
  {
    if (this.size >= this.storage.length)
    {
      arrayOfLong = new long[this.storage.length + this.growAmount];
      System.arraycopy(this.storage, 0, arrayOfLong, 0, this.storage.length);
      this.storage = arrayOfLong;
    }
    long[] arrayOfLong = this.storage;
    int i = this.size;
    this.size = (i + 1);
    arrayOfLong[i] = paramLong;
  }
  
  public void addAll(long[] paramArrayOfLong)
  {
    if (this.size + paramArrayOfLong.length >= this.storage.length)
    {
      long[] arrayOfLong = new long[this.size + this.growAmount + paramArrayOfLong.length];
      System.arraycopy(this.storage, 0, arrayOfLong, 0, this.size);
      this.storage = arrayOfLong;
    }
    System.arraycopy(paramArrayOfLong, 0, this.storage, this.size, paramArrayOfLong.length);
    this.size += paramArrayOfLong.length;
  }
  
  public void fill(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt2 > this.storage.length)
    {
      long[] arrayOfLong = new long[this.growAmount + paramInt2];
      System.arraycopy(this.storage, 0, arrayOfLong, 0, this.storage.length);
      this.storage = arrayOfLong;
    }
    Arrays.fill(this.storage, paramInt1, paramInt2, paramInt3);
    this.size = Math.max(this.size, paramInt2);
  }
  
  public long get(int paramInt)
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
  
  public long[] toArray()
  {
    long[] arrayOfLong = new long[this.size];
    System.arraycopy(this.storage, 0, arrayOfLong, 0, this.size);
    return arrayOfLong;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/LongArrayList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */