package com.google.android.gms.gcm;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.iid.InstanceID;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GcmPubSub
{
  private static GcmPubSub zzavH;
  private static final Pattern zzavJ = Pattern.compile("/topics/[a-zA-Z0-9-_.~%]{1,900}");
  private InstanceID zzavI;
  
  private GcmPubSub(Context paramContext)
  {
    this.zzavI = InstanceID.getInstance(paramContext);
  }
  
  public static GcmPubSub getInstance(Context paramContext)
  {
    try
    {
      if (zzavH == null) {
        zzavH = new GcmPubSub(paramContext);
      }
      paramContext = zzavH;
      return paramContext;
    }
    finally {}
  }
  
  public void subscribe(String paramString1, String paramString2, Bundle paramBundle)
    throws IOException
  {
    if ((paramString1 == null) || (paramString1.isEmpty())) {
      throw new IllegalArgumentException("Invalid appInstanceToken: " + paramString1);
    }
    if ((paramString2 == null) || (!zzavJ.matcher(paramString2).matches())) {
      throw new IllegalArgumentException("Invalid topic name: " + paramString2);
    }
    Bundle localBundle = paramBundle;
    if (paramBundle == null) {
      localBundle = new Bundle();
    }
    localBundle.putString("gcm.topic", paramString2);
    this.zzavI.getToken(paramString1, paramString2, localBundle);
  }
  
  public void unsubscribe(String paramString1, String paramString2)
    throws IOException
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("gcm.topic", paramString2);
    this.zzavI.zzb(paramString1, paramString2, localBundle);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/gcm/GcmPubSub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */