package com.google.android.gms.internal;

import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import android.util.LogPrinter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class zznn
  implements zznu
{
  private static final Uri zzaDR;
  private final LogPrinter zzaDS = new LogPrinter(4, "GA/LogCatTransport");
  
  static
  {
    Uri.Builder localBuilder = new Uri.Builder();
    localBuilder.scheme("uri");
    localBuilder.authority("local");
    zzaDR = localBuilder.build();
  }
  
  public void zzb(zzno paramzzno)
  {
    Object localObject = new ArrayList(paramzzno.zzvQ());
    Collections.sort((List)localObject, new Comparator()
    {
      public int zza(zznq paramAnonymouszznq1, zznq paramAnonymouszznq2)
      {
        return paramAnonymouszznq1.getClass().getCanonicalName().compareTo(paramAnonymouszznq2.getClass().getCanonicalName());
      }
    });
    paramzzno = new StringBuilder();
    localObject = ((List)localObject).iterator();
    while (((Iterator)localObject).hasNext())
    {
      String str = ((zznq)((Iterator)localObject).next()).toString();
      if (!TextUtils.isEmpty(str))
      {
        if (paramzzno.length() != 0) {
          paramzzno.append(", ");
        }
        paramzzno.append(str);
      }
    }
    this.zzaDS.println(paramzzno.toString());
  }
  
  public Uri zzhe()
  {
    return zzaDR;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zznn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */