package org.jcodec;

public class Rational
{
  private final int den;
  private final int num;
  
  public Rational(int paramInt1, int paramInt2)
  {
    this.num = paramInt1;
    this.den = paramInt2;
  }
  
  public static RationalLarge R(long paramLong1, long paramLong2)
  {
    return new RationalLarge(paramLong1, paramLong2);
  }
  
  public float asFloat()
  {
    return this.num / this.den;
  }
  
  public long divide(long paramLong)
  {
    return this.den * paramLong / this.num;
  }
  
  public Rational divide(int paramInt)
  {
    return new Rational(this.den * paramInt, this.num);
  }
  
  public Rational divide(Rational paramRational)
  {
    return new Rational(paramRational.num * this.den, paramRational.den * this.num);
  }
  
  public Rational divideBy(int paramInt)
  {
    return new Rational(this.num, this.den * paramInt);
  }
  
  public Rational divideBy(Rational paramRational)
  {
    return new Rational(this.num * paramRational.den, this.den * paramRational.num);
  }
  
  public int divideByS(int paramInt)
  {
    return this.num / (this.den * paramInt);
  }
  
  public int divideS(int paramInt)
  {
    return (int)(this.den * paramInt / this.num);
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
      paramObject = (Rational)paramObject;
      if (this.den != ((Rational)paramObject).den) {
        return false;
      }
    } while (this.num == ((Rational)paramObject).num);
    return false;
  }
  
  public boolean equals(Rational paramRational)
  {
    return this.num * paramRational.den == paramRational.num * this.den;
  }
  
  public Rational flip()
  {
    return new Rational(this.den, this.num);
  }
  
  public int getDen()
  {
    return this.den;
  }
  
  public int getNum()
  {
    return this.num;
  }
  
  public boolean greaterOrEqualTo(Rational paramRational)
  {
    return this.num * paramRational.den >= paramRational.num * this.den;
  }
  
  public boolean greaterThen(Rational paramRational)
  {
    return this.num * paramRational.den > paramRational.num * this.den;
  }
  
  public int hashCode()
  {
    return (this.den + 31) * 31 + this.num;
  }
  
  public Rational minus(int paramInt)
  {
    return new Rational(this.num - this.den * paramInt, this.den);
  }
  
  public Rational minus(Rational paramRational)
  {
    return new Rational(this.num * paramRational.den - paramRational.num * this.den, this.den * paramRational.den);
  }
  
  public long multiply(long paramLong)
  {
    return this.num * paramLong / this.den;
  }
  
  public Rational multiply(int paramInt)
  {
    return new Rational(this.num * paramInt, this.den);
  }
  
  public Rational multiply(Rational paramRational)
  {
    return new Rational(this.num * paramRational.num, this.den * paramRational.den);
  }
  
  public int multiplyS(int paramInt)
  {
    return (int)(this.num * paramInt / this.den);
  }
  
  public Rational plus(int paramInt)
  {
    return new Rational(this.num + this.den * paramInt, this.den);
  }
  
  public Rational plus(Rational paramRational)
  {
    return new Rational(this.num * paramRational.den + paramRational.num * this.den, this.den * paramRational.den);
  }
  
  public boolean smallerOrEqualTo(Rational paramRational)
  {
    return this.num * paramRational.den <= paramRational.num * this.den;
  }
  
  public boolean smallerThen(Rational paramRational)
  {
    return this.num * paramRational.den < paramRational.num * this.den;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/Rational.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */