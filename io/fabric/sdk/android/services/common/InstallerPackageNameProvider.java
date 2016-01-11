package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.content.pm.PackageManager;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.cache.MemoryValueCache;
import io.fabric.sdk.android.services.cache.ValueLoader;

public class InstallerPackageNameProvider
{
  private static final String NO_INSTALLER_PACKAGE_NAME = "";
  private final MemoryValueCache<String> installerPackageNameCache = new MemoryValueCache();
  private final ValueLoader<String> installerPackageNameLoader = new ValueLoader()
  {
    public String load(Context paramAnonymousContext)
      throws Exception
    {
      String str = paramAnonymousContext.getPackageManager().getInstallerPackageName(paramAnonymousContext.getPackageName());
      paramAnonymousContext = str;
      if (str == null) {
        paramAnonymousContext = "";
      }
      return paramAnonymousContext;
    }
  };
  
  public String getInstallerPackageName(Context paramContext)
  {
    try
    {
      paramContext = (String)this.installerPackageNameCache.get(paramContext, this.installerPackageNameLoader);
      boolean bool = "".equals(paramContext);
      if (bool) {
        paramContext = null;
      }
      return paramContext;
    }
    catch (Exception paramContext)
    {
      Fabric.getLogger().e("Fabric", "Failed to determine installer package name", paramContext);
    }
    return null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/common/InstallerPackageNameProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */