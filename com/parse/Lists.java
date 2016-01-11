package com.parse;

import java.util.AbstractList;
import java.util.List;

class Lists
{
  static <T> List<List<T>> partition(List<T> paramList, int paramInt)
  {
    return new Partition(paramList, paramInt);
  }
  
  private static class Partition<T>
    extends AbstractList<List<T>>
  {
    private final List<T> list;
    private final int size;
    
    public Partition(List<T> paramList, int paramInt)
    {
      this.list = paramList;
      this.size = paramInt;
    }
    
    public List<T> get(int paramInt)
    {
      paramInt *= this.size;
      int i = Math.min(this.size + paramInt, this.list.size());
      return this.list.subList(paramInt, i);
    }
    
    public int size()
    {
      return (int)Math.ceil(this.list.size() / this.size);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/Lists.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */