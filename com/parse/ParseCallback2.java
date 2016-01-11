package com.parse;

abstract interface ParseCallback2<T1, T2 extends Throwable>
{
  public abstract void done(T1 paramT1, T2 paramT2);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseCallback2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */