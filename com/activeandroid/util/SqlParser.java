package com.activeandroid.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SqlParser
{
  public static final int STATE_COMMENT = 2;
  public static final int STATE_COMMENT_BLOCK = 3;
  public static final int STATE_NONE = 0;
  public static final int STATE_STRING = 1;
  
  private static boolean isNewLine(char paramChar)
  {
    return (paramChar == '\r') || (paramChar == '\n');
  }
  
  private static boolean isWhitespace(char paramChar)
  {
    return (paramChar == '\r') || (paramChar == '\n') || (paramChar == '\t') || (paramChar == ' ');
  }
  
  public static List<String> parse(InputStream paramInputStream)
    throws IOException
  {
    paramInputStream = new BufferedInputStream(paramInputStream);
    ArrayList localArrayList = new ArrayList();
    StringBuffer localStringBuffer = new StringBuffer();
    int i;
    char c;
    try
    {
      Tokenizer localTokenizer = new Tokenizer(paramInputStream);
      i = 0;
      for (;;)
      {
        if (!localTokenizer.hasNext()) {
          break label261;
        }
        c = (char)localTokenizer.next();
        if (i == 3)
        {
          if (localTokenizer.skip("*/")) {
            i = 0;
          }
        }
        else if (i == 2)
        {
          if (isNewLine(c)) {
            i = 0;
          }
        }
        else if ((i == 0) && (localTokenizer.skip("/*")))
        {
          i = 3;
        }
        else if ((i == 0) && (localTokenizer.skip("--")))
        {
          i = 2;
        }
        else
        {
          if ((i != 0) || (c != ';')) {
            break;
          }
          localArrayList.add(localStringBuffer.toString().trim());
          localStringBuffer.setLength(0);
        }
      }
      if (i != 0) {
        break label292;
      }
    }
    finally
    {
      IOUtils.closeQuietly(paramInputStream);
    }
    int j;
    if (c == '\'') {
      j = 1;
    }
    for (;;)
    {
      if (j != 0)
      {
        i = j;
        if (j != 1) {
          break;
        }
      }
      if ((j == 0) && (isWhitespace(c)))
      {
        i = j;
        if (localStringBuffer.length() <= 0) {
          break;
        }
        i = j;
        if (localStringBuffer.charAt(localStringBuffer.length() - 1) == ' ') {
          break;
        }
        localStringBuffer.append(' ');
        i = j;
        break;
      }
      localStringBuffer.append(c);
      i = j;
      break;
      label261:
      IOUtils.closeQuietly(paramInputStream);
      if (localStringBuffer.length() > 0) {
        localList.add(localStringBuffer.toString().trim());
      }
      return localList;
      label292:
      j = i;
      if (i == 1)
      {
        j = i;
        if (c == '\'') {
          j = 0;
        }
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/util/SqlParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */