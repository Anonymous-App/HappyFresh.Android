package org.jcodec;

public class RationalLarge
{
  public static final Rational HALF = new Rational(1, 2);
  public static final Rational ONE = new Rational(1, 1);
  public static final RationalLarge ZERO = new RationalLarge(0L, 1L);
  private final long den;
  private final long num;
  
  public RationalLarge(long paramLong1, long paramLong2)
  {
    this.num = paramLong1;
    this.den = paramLong2;
  }
  
  public static RationalLarge R(long paramLong1, long paramLong2)
  {
    return new RationalLarge(paramLong1, paramLong2);
  }
  
  public RationalLarge divide(long paramLong)
  {
    return new RationalLarge(this.den * paramLong, this.num);
  }
  
  public RationalLarge divide(RationalLarge paramRationalLarge)
  {
    return new RationalLarge(paramRationalLarge.num * this.den, paramRationalLarge.den * this.num);
  }
  
  public RationalLarge divideBy(long paramLong)
  {
    return new RationalLarge(this.num, this.den * paramLong);
  }
  
  public RationalLarge divideBy(RationalLarge paramRationalLarge)
  {
    return new RationalLarge(this.num * paramRationalLarge.den, this.den * paramRationalLarge.num);
  }
  
  public long divideByS(long paramLong)
  {
    return this.num / (this.den * paramLong);
  }
  
  public long divideS(long paramLong)
  {
    return this.den * paramLong / this.num;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if (paramObject == null) {
        return false;
      }
      if (getClass() != paramObject.getClass()) {
        return false;
      }
      paramObject = (RationalLarge)paramObject;
      if (this.den != ((RationalLarge)paramObject).den) {
        return false;
      }
    } while (this.num == ((RationalLarge)paramObject).num);
    return false;
  }
  
  public boolean equals(RationalLarge paramRationalLarge)
  {
    return this.num * paramRationalLarge.den == paramRationalLarge.num * this.den;
  }
  
  public RationalLarge flip()
  {
    return new RationalLarge(this.den, this.num);
  }
  
  public long getDen()
  {
    return this.den;
  }
  
  public long getNum()
  {
    return this.num;
  }
  
  public boolean greaterOrEqualTo(RationalLarge paramRationalLarge)
  {
    return this.num * paramRationalLarge.den >= paramRationalLarge.num * this.den;
  }
  
  public boolean greaterThen(RationalLarge paramRationalLarge)
  {
    return this.num * paramRationalLarge.den > paramRationalLarge.num * this.den;
  }
  
  public int hashCode()
  {
    return ((int)(this.den ^ this.den >>> 32) + 31) * 31 + (int)(this.num ^ this.num >>> 32);
  }
  
  public boolean lessThen(RationalLarge paramRationalLarge)
  {
    return this.num * paramRationalLarge.den < paramRationalLarge.num * this.den;
  }
  
  public RationalLarge minus(long paramLong)
  {
    return new RationalLarge(this.num - this.den * paramLong, this.den);
  }
  
  public RationalLarge minus(RationalLarge paramRationalLarge)
  {
    return new RationalLarge(this.num * paramRationalLarge.den - paramRationalLarge.num * this.den, this.den * paramRationalLarge.den);
  }
  
  public RationalLarge multiply(long paramLong)
  {
    return new RationalLarge(this.num * paramLong, this.den);
  }
  
  public RationalLarge multiply(RationalLarge paramRationalLarge)
  {
    return new RationalLarge(this.num * paramRationalLarge.num, this.den * paramRationalLarge.den);
  }
  
  public long multiplyS(long paramLong)
  {
    return this.num * paramLong / this.den;
  }
  
  public RationalLarge plus(long paramLong)
  {
    return new RationalLarge(this.num + this.den * paramLong, this.den);
  }
  
  public RationalLarge plus(RationalLarge paramRationalLarge)
  {
    return new RationalLarge(this.num * paramRationalLarge.den + paramRationalLarge.num * this.den, this.den * paramRationalLarge.den);
  }
  
  public double scalar()
  {
    return this.num / this.den;
  }
  
  public boolean smallerOrEqualTo(RationalLarge paramRationalLarge)
  {
    return this.num * paramRationalLarge.den <= paramRationalLarge.num * this.den;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/RationalLarge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */