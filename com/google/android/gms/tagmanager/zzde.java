package com.google.android.gms.tagmanager;

class zzde
  extends Number
  implements Comparable<zzde>
{
  private double zzaOq;
  private long zzaOr;
  private boolean zzaOs;
  
  private zzde(double paramDouble)
  {
    this.zzaOq = paramDouble;
    this.zzaOs = false;
  }
  
  private zzde(long paramLong)
  {
    this.zzaOr = paramLong;
    this.zzaOs = true;
  }
  
  public static zzde zzT(long paramLong)
  {
    return new zzde(paramLong);
  }
  
  public static zzde zza(Double paramDouble)
  {
    return new zzde(paramDouble.doubleValue());
  }
  
  public static zzde zzeI(String paramString)
    throws NumberFormatException
  {
    try
    {
      zzde localzzde1 = new zzde(Long.parseLong(paramString));
      return localzzde1;
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      try
      {
        zzde localzzde2 = new zzde(Double.parseDouble(paramString));
        return localzzde2;
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        throw new NumberFormatException(paramString + " is not a valid TypedNumber");
      }
    }
  }
  
  public byte byteValue()
  {
    return (byte)(int)longValue();
  }
  
  public double doubleValue()
  {
    if (zzzG()) {
      return this.zzaOr;
    }
    return this.zzaOq;
  }
  
  public boolean equals(Object paramObject)
  {
    return ((paramObject instanceof zzde)) && (zza((zzde)paramObject) == 0);
  }
  
  public float floatValue()
  {
    return (float)doubleValue();
  }
  
  public int hashCode()
  {
    return new Long(longValue()).hashCode();
  }
  
  public int intValue()
  {
    return zzzI();
  }
  
  public long longValue()
  {
    return zzzH();
  }
  
  public short shortValue()
  {
    return zzzJ();
  }
  
  public String toString()
  {
    if (zzzG()) {
      return Long.toString(this.zzaOr);
    }
    return Double.toString(this.zzaOq);
  }
  
  public int zza(zzde paramzzde)
  {
    if ((zzzG()) && (paramzzde.zzzG())) {
      return new Long(this.zzaOr).compareTo(Long.valueOf(paramzzde.zzaOr));
    }
    return Double.compare(doubleValue(), paramzzde.doubleValue());
  }
  
  public boolean zzzF()
  {
    return !zzzG();
  }
  
  public boolean zzzG()
  {
    return this.zzaOs;
  }
  
  public long zzzH()
  {
    if (zzzG()) {
      return this.zzaOr;
    }
    return this.zzaOq;
  }
  
  public int zzzI()
  {
    return (int)longValue();
  }
  
  public short zzzJ()
  {
    return (short)(int)longValue();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzde.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */