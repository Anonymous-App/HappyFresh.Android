package com.google.android.gms.internal;

import com.google.android.gms.tagmanager.zzbg;
import org.json.JSONException;

public final class zzqc
{
  public static zzqb zzaPm = new zzqb()
  {
    public Object zzt(byte[] paramAnonymousArrayOfByte)
      throws zzqf.zzg
    {
      if (paramAnonymousArrayOfByte == null) {
        throw new zzqf.zzg("Cannot parse a null byte[]");
      }
      if (paramAnonymousArrayOfByte.length == 0) {
        throw new zzqf.zzg("Cannot parse a 0 length byte[]");
      }
      try
      {
        paramAnonymousArrayOfByte = zzpz.zzey(new String(paramAnonymousArrayOfByte));
        if (paramAnonymousArrayOfByte != null) {
          zzbg.zzaB("The container was successfully parsed from the resource");
        }
        return paramAnonymousArrayOfByte;
      }
      catch (JSONException paramAnonymousArrayOfByte)
      {
        throw new zzqf.zzg("The resource data is corrupted. The container cannot be extracted from the binary data");
      }
      catch (zzqf.zzg paramAnonymousArrayOfByte)
      {
        throw new zzqf.zzg("The resource data is invalid. The container cannot be extracted from the binary data");
      }
    }
  };
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzqc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */