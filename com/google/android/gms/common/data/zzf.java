package com.google.android.gms.common.data;

import java.util.ArrayList;

public abstract class zzf<T>
  extends AbstractDataBuffer<T>
{
  private boolean zzYK = false;
  private ArrayList<Integer> zzYL;
  
  protected zzf(DataHolder paramDataHolder)
  {
    super(paramDataHolder);
  }
  
  private void zznj()
  {
    for (;;)
    {
      int i;
      String str2;
      try
      {
        if (this.zzYK) {
          break label193;
        }
        int j = this.zzWu.getCount();
        this.zzYL = new ArrayList();
        if (j <= 0) {
          break label188;
        }
        this.zzYL.add(Integer.valueOf(0));
        String str3 = zzni();
        i = this.zzWu.zzbh(0);
        String str1 = this.zzWu.zzd(str3, 0, i);
        i = 1;
        if (i >= j) {
          break label188;
        }
        int k = this.zzWu.zzbh(i);
        str2 = this.zzWu.zzd(str3, i, k);
        if (str2 == null) {
          throw new NullPointerException("Missing value for markerColumn: " + str3 + ", at row: " + i + ", for window: " + k);
        }
      }
      finally {}
      if (!str2.equals(localObject1))
      {
        this.zzYL.add(Integer.valueOf(i));
        Object localObject2 = str2;
        break label196;
        label188:
        this.zzYK = true;
        label193:
        return;
      }
      label196:
      i += 1;
    }
  }
  
  public final T get(int paramInt)
  {
    zznj();
    return (T)zzj(zzbk(paramInt), zzbl(paramInt));
  }
  
  public int getCount()
  {
    zznj();
    return this.zzYL.size();
  }
  
  int zzbk(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.zzYL.size())) {
      throw new IllegalArgumentException("Position " + paramInt + " is out of bounds for this buffer");
    }
    return ((Integer)this.zzYL.get(paramInt)).intValue();
  }
  
  protected int zzbl(int paramInt)
  {
    int j;
    if ((paramInt < 0) || (paramInt == this.zzYL.size()))
    {
      j = 0;
      return j;
    }
    if (paramInt == this.zzYL.size() - 1) {}
    for (int i = this.zzWu.getCount() - ((Integer)this.zzYL.get(paramInt)).intValue();; i = ((Integer)this.zzYL.get(paramInt + 1)).intValue() - ((Integer)this.zzYL.get(paramInt)).intValue())
    {
      j = i;
      if (i != 1) {
        break;
      }
      paramInt = zzbk(paramInt);
      int k = this.zzWu.zzbh(paramInt);
      String str = zznk();
      j = i;
      if (str == null) {
        break;
      }
      j = i;
      if (this.zzWu.zzd(str, paramInt, k) != null) {
        break;
      }
      return 0;
    }
  }
  
  protected abstract T zzj(int paramInt1, int paramInt2);
  
  protected abstract String zzni();
  
  protected String zznk()
  {
    return null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/data/zzf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */