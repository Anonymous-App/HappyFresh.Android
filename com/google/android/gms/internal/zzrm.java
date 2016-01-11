package com.google.android.gms.internal;

import java.io.IOException;

public class zzrm
  extends IOException
{
  public zzrm(String paramString)
  {
    super(paramString);
  }
  
  static zzrm zzBN()
  {
    return new zzrm("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either than the input has been truncated or that an embedded message misreported its own length.");
  }
  
  static zzrm zzBO()
  {
    return new zzrm("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
  }
  
  static zzrm zzBP()
  {
    return new zzrm("CodedInputStream encountered a malformed varint.");
  }
  
  static zzrm zzBQ()
  {
    return new zzrm("Protocol message contained an invalid tag (zero).");
  }
  
  static zzrm zzBR()
  {
    return new zzrm("Protocol message end-group tag did not match expected tag.");
  }
  
  static zzrm zzBS()
  {
    return new zzrm("Protocol message tag had invalid wire type.");
  }
  
  static zzrm zzBT()
  {
    return new zzrm("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzrm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */