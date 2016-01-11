package org.jcodec;

import java.util.Arrays;

public enum Brand
{
  MP4("isom", 512, new String[] { "isom", "iso2", "avc1", "mp41" });
  
  private FileTypeBox ftyp;
  
  private Brand(String paramString, int paramInt, String[] paramArrayOfString)
  {
    this.ftyp = new FileTypeBox(paramString, paramInt, Arrays.asList(paramArrayOfString));
  }
  
  public FileTypeBox getFileTypeBox()
  {
    return this.ftyp;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/Brand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */