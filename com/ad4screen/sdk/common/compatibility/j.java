package com.ad4screen.sdk.common.compatibility;

import android.annotation.TargetApi;
import android.content.pm.PackageInfo;

@TargetApi(9)
public final class j
{
  public static long a(PackageInfo paramPackageInfo)
  {
    return paramPackageInfo.firstInstallTime;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/compatibility/j.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */