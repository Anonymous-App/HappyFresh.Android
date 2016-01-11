package org.jcodec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Util
{
  public static List<Edit> editsOnEdits(Rational paramRational, List<Edit> paramList1, List<Edit> paramList2)
  {
    ArrayList localArrayList = new ArrayList();
    paramList1 = new ArrayList(paramList1);
    paramList2 = paramList2.iterator();
    while (paramList2.hasNext())
    {
      Edit localEdit = (Edit)paramList2.next();
      long l = paramRational.multiply(localEdit.getMediaTime());
      paramList1 = split((List)split(paramList1, paramRational.flip(), l).getB(), paramRational.flip(), localEdit.getDuration() + l);
      localArrayList.addAll((Collection)paramList1.getA());
      paramList1 = (List)paramList1.getB();
    }
    return localArrayList;
  }
  
  public static long[] getTimevalues(TrakBox paramTrakBox)
  {
    paramTrakBox = (TimeToSampleBox)Box.findFirst(paramTrakBox, TimeToSampleBox.class, new String[] { "mdia", "minf", "stbl", "stts" });
    int j = 0;
    paramTrakBox = paramTrakBox.getEntries();
    int i = 0;
    while (i < paramTrakBox.length)
    {
      j += paramTrakBox[i].getSampleCount();
      i += 1;
    }
    long[] arrayOfLong = new long[j + 1];
    j = 0;
    i = 0;
    while (i < paramTrakBox.length)
    {
      int k = 0;
      while (k < paramTrakBox[i].getSampleCount())
      {
        arrayOfLong[(j + 1)] = (arrayOfLong[j] + paramTrakBox[i].getSampleDuration());
        k += 1;
        j += 1;
      }
      i += 1;
    }
    return arrayOfLong;
  }
  
  public static void shift(MovieBox paramMovieBox, TrakBox paramTrakBox, long paramLong)
  {
    paramTrakBox.getEdits().add(0, new Edit(paramLong, -1L, 1.0F));
  }
  
  public static Pair<List<Edit>> split(List<Edit> paramList, Rational paramRational, long paramLong)
  {
    long l = 0L;
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    paramList = paramList.listIterator();
    for (;;)
    {
      Edit localEdit;
      if (paramList.hasNext())
      {
        localEdit = (Edit)paramList.next();
        if (localEdit.getDuration() + l <= paramLong) {
          break label206;
        }
        int i = (int)(paramLong - l);
        int j = paramRational.multiplyS(i);
        paramRational = new Edit(i, localEdit.getMediaTime(), 1.0F);
        localEdit = new Edit(localEdit.getDuration() - i, j + localEdit.getMediaTime(), 1.0F);
        paramList.remove();
        if (paramRational.getDuration() > 0L)
        {
          paramList.add(paramRational);
          localArrayList1.add(paramRational);
        }
        if (localEdit.getDuration() > 0L)
        {
          paramList.add(localEdit);
          localArrayList2.add(localEdit);
        }
      }
      while (paramList.hasNext()) {
        localArrayList2.add(paramList.next());
      }
      label206:
      localArrayList1.add(localEdit);
      l += localEdit.getDuration();
    }
    return new Pair(localArrayList1, localArrayList2);
  }
  
  public static Pair<List<Edit>> split(MovieBox paramMovieBox, TrakBox paramTrakBox, long paramLong)
  {
    return split(paramTrakBox.getEdits(), new Rational(paramTrakBox.getTimescale(), paramMovieBox.getTimescale()), paramLong);
  }
  
  public static void spread(MovieBox paramMovieBox, TrakBox paramTrakBox, long paramLong1, long paramLong2)
  {
    paramMovieBox = split(paramMovieBox, paramTrakBox, paramLong1);
    paramTrakBox.getEdits().add(((List)paramMovieBox.getA()).size(), new Edit(paramLong2, -1L, 1.0F));
  }
  
  public static class Pair<T>
  {
    private T a;
    private T b;
    
    public Pair(T paramT1, T paramT2)
    {
      this.a = paramT1;
      this.b = paramT2;
    }
    
    public T getA()
    {
      return (T)this.a;
    }
    
    public T getB()
    {
      return (T)this.b;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */