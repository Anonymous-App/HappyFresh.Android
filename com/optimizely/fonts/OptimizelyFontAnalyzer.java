package com.optimizely.fonts;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.optimizely.Optimizely;
import java.io.IOException;
import java.io.InputStream;

public class OptimizelyFontAnalyzer
{
  private int currentPointer = 0;
  @Nullable
  private InputStream inputStream = null;
  private final Optimizely optimizely;
  
  public OptimizelyFontAnalyzer(@NonNull Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
  }
  
  private int getWord(@NonNull byte[] paramArrayOfByte, int paramInt)
  {
    return (paramArrayOfByte[paramInt] & 0xFF) << 8 | paramArrayOfByte[(paramInt + 1)] & 0xFF;
  }
  
  private void offset(int paramInt)
    throws IOException
  {
    byte[] arrayOfByte = new byte[paramInt - this.currentPointer];
    this.inputStream.read(arrayOfByte);
  }
  
  private void read(@NonNull byte[] paramArrayOfByte)
    throws IOException
  {
    this.currentPointer += paramArrayOfByte.length;
    if (this.inputStream.read(paramArrayOfByte) != paramArrayOfByte.length) {
      throw new IOException();
    }
  }
  
  private int readByte()
    throws IOException
  {
    this.currentPointer += 1;
    return this.inputStream.read() & 0xFF;
  }
  
  private int readCharacter()
    throws IOException
  {
    return readByte() << 8 | readByte();
  }
  
  private int readDouble()
    throws IOException
  {
    return readByte() << 24 | readByte() << 16 | readByte() << 8 | readByte();
  }
  
  @Nullable
  public String findFontName(String paramString)
  {
    label287:
    label294:
    do
    {
      try
      {
        this.inputStream = this.optimizely.getCurrentContext().getAssets().open(paramString);
        this.currentPointer = 0;
        i = readDouble();
        if ((i != 1953658213) && (i != 65536))
        {
          str = null;
          paramString = str;
          if (this.inputStream == null) {}
        }
      }
      catch (Exception paramString)
      {
        do
        {
          int i;
          String str;
          int k;
          paramString = null;
        } while (this.inputStream == null);
        try
        {
          this.inputStream.close();
          return null;
        }
        catch (IOException paramString)
        {
          return null;
        }
      }
      finally
      {
        if (this.inputStream != null) {}
        try
        {
          this.inputStream.close();
          throw paramString;
        }
        catch (IOException localIOException)
        {
          for (;;) {}
        }
      }
      try
      {
        this.inputStream.close();
        paramString = str;
        return paramString;
      }
      catch (IOException paramString) {}
      k = readCharacter();
      readCharacter();
      readCharacter();
      readCharacter();
      i = 0;
      for (;;)
      {
        if (i >= k) {
          break label294;
        }
        int j = readDouble();
        readDouble();
        int m = readDouble();
        int n = readDouble();
        if (j == 1851878757)
        {
          paramString = new byte[n];
          offset(m);
          read(paramString);
          m = getWord(paramString, 2);
          n = getWord(paramString, 4);
          j = 0;
          for (;;)
          {
            if (j >= m) {
              break label287;
            }
            int i1 = j * 12 + 6;
            int i2 = getWord(paramString, i1);
            if ((getWord(paramString, i1 + 6) == 4) && (i2 == 1))
            {
              i2 = getWord(paramString, i1 + 8);
              i1 = getWord(paramString, i1 + 10) + n;
              if ((i1 >= 0) && (i1 + i2 < paramString.length))
              {
                str = new String(paramString, i1, i2);
                paramString = str;
                if (this.inputStream == null) {
                  break;
                }
                try
                {
                  this.inputStream.close();
                  return str;
                }
                catch (IOException paramString)
                {
                  return str;
                }
              }
            }
            j += 1;
          }
        }
        i += 1;
      }
      paramString = null;
    } while (this.inputStream == null);
    try
    {
      this.inputStream.close();
      return null;
    }
    catch (IOException paramString)
    {
      return null;
    }
    return null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/fonts/OptimizelyFontAnalyzer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */