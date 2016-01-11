package com.appsee;

import java.io.File;
import java.util.Comparator;

class rc
  implements Comparator<File>
{
  rc(fc paramfc) {}
  
  public int i(File paramFile1, File paramFile2)
  {
    int i = fc.i(this.G, lb.i(paramFile1.getName()));
    int j = fc.i(this.G, lb.i(paramFile2.getName()));
    if (i < j) {}
    do
    {
      return -1;
      if (i > j) {
        return 1;
      }
    } while (paramFile1.lastModified() < paramFile2.lastModified());
    return 1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/rc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */