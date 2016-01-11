package com.activeandroid.util;

import java.io.IOException;
import java.io.InputStream;

public class Tokenizer
{
  private int mCurrent;
  private boolean mIsNext;
  private final InputStream mStream;
  
  public Tokenizer(InputStream paramInputStream)
  {
    this.mStream = paramInputStream;
  }
  
  public boolean hasNext()
    throws IOException
  {
    if (!this.mIsNext)
    {
      this.mIsNext = true;
      this.mCurrent = this.mStream.read();
    }
    return this.mCurrent != -1;
  }
  
  public int next()
    throws IOException
  {
    if (!this.mIsNext) {
      this.mCurrent = this.mStream.read();
    }
    this.mIsNext = false;
    return this.mCurrent;
  }
  
  public boolean skip(String paramString)
    throws IOException
  {
    if ((paramString == null) || (paramString.length() == 0)) {}
    while (paramString.charAt(0) != this.mCurrent) {
      return false;
    }
    int j = paramString.length();
    this.mStream.mark(j - 1);
    int i = 1;
    while (i < j)
    {
      if (this.mStream.read() != paramString.charAt(i))
      {
        this.mStream.reset();
        return false;
      }
      i += 1;
    }
    return true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/util/Tokenizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */