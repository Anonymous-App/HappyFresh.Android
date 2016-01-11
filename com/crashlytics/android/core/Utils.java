package com.crashlytics.android.core;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

final class Utils
{
  public static void capFileCount(File paramFile, FilenameFilter paramFilenameFilter, int paramInt, Comparator<File> paramComparator)
  {
    paramFile = paramFile.listFiles(paramFilenameFilter);
    int j;
    int k;
    int i;
    if ((paramFile != null) && (paramFile.length > paramInt))
    {
      Arrays.sort(paramFile, paramComparator);
      j = paramFile.length;
      k = paramFile.length;
      i = 0;
    }
    for (;;)
    {
      if (i < k)
      {
        paramFilenameFilter = paramFile[i];
        if (j > paramInt) {}
      }
      else
      {
        return;
      }
      paramFilenameFilter.delete();
      j -= 1;
      i += 1;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/crashlytics/android/core/Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */