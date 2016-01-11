package io.fabric.sdk.android;

import android.os.SystemClock;
import android.text.TextUtils;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.Closeable;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

class FabricKitsFinder
  implements Callable<Map<String, KitInfo>>
{
  private static final String FABRIC_BUILD_TYPE_KEY = "fabric-build-type";
  static final String FABRIC_DIR = "fabric/";
  private static final String FABRIC_IDENTIFIER_KEY = "fabric-identifier";
  private static final String FABRIC_VERSION_KEY = "fabric-version";
  final String apkFileName;
  
  FabricKitsFinder(String paramString)
  {
    this.apkFileName = paramString;
  }
  
  private KitInfo loadKitInfo(ZipEntry paramZipEntry, ZipFile paramZipFile)
  {
    Object localObject1 = null;
    ZipFile localZipFile = null;
    try
    {
      paramZipFile = paramZipFile.getInputStream(paramZipEntry);
      localZipFile = paramZipFile;
      localObject1 = paramZipFile;
      localObject3 = new Properties();
      localZipFile = paramZipFile;
      localObject1 = paramZipFile;
      ((Properties)localObject3).load(paramZipFile);
      localZipFile = paramZipFile;
      localObject1 = paramZipFile;
      localObject2 = ((Properties)localObject3).getProperty("fabric-identifier");
      localZipFile = paramZipFile;
      localObject1 = paramZipFile;
      str = ((Properties)localObject3).getProperty("fabric-version");
      localZipFile = paramZipFile;
      localObject1 = paramZipFile;
      localObject3 = ((Properties)localObject3).getProperty("fabric-build-type");
      localZipFile = paramZipFile;
      localObject1 = paramZipFile;
      if (!TextUtils.isEmpty((CharSequence)localObject2))
      {
        localZipFile = paramZipFile;
        localObject1 = paramZipFile;
        if (!TextUtils.isEmpty(str)) {}
      }
      else
      {
        localZipFile = paramZipFile;
        localObject1 = paramZipFile;
        throw new IllegalStateException("Invalid format of fabric file," + paramZipEntry.getName());
      }
    }
    catch (IOException paramZipFile)
    {
      Object localObject3;
      String str;
      localObject1 = localZipFile;
      Fabric.getLogger().e("Fabric", "Error when parsing fabric properties " + paramZipEntry.getName(), paramZipFile);
      CommonUtils.closeQuietly(localZipFile);
      return null;
      localZipFile = paramZipFile;
      localObject1 = paramZipFile;
      Object localObject2 = new KitInfo((String)localObject2, str, (String)localObject3);
      CommonUtils.closeQuietly(paramZipFile);
      return (KitInfo)localObject2;
    }
    finally
    {
      CommonUtils.closeQuietly((Closeable)localObject1);
    }
  }
  
  public Map<String, KitInfo> call()
    throws Exception
  {
    HashMap localHashMap = new HashMap();
    long l = SystemClock.elapsedRealtime();
    int i = 0;
    ZipFile localZipFile = loadApkFile();
    Enumeration localEnumeration = localZipFile.entries();
    while (localEnumeration.hasMoreElements())
    {
      int j = i + 1;
      Object localObject = (ZipEntry)localEnumeration.nextElement();
      i = j;
      if (((ZipEntry)localObject).getName().startsWith("fabric/"))
      {
        localObject = loadKitInfo((ZipEntry)localObject, localZipFile);
        i = j;
        if (localObject != null)
        {
          localHashMap.put(((KitInfo)localObject).getIdentifier(), localObject);
          Fabric.getLogger().v("Fabric", String.format("Found kit:[%s] version:[%s]", new Object[] { ((KitInfo)localObject).getIdentifier(), ((KitInfo)localObject).getVersion() }));
          i = j;
        }
      }
    }
    if (localZipFile != null) {}
    try
    {
      localZipFile.close();
      Fabric.getLogger().v("Fabric", "finish scanning in " + (SystemClock.elapsedRealtime() - l) + " reading:" + i);
      return localHashMap;
    }
    catch (IOException localIOException)
    {
      for (;;) {}
    }
  }
  
  protected ZipFile loadApkFile()
    throws IOException
  {
    return new ZipFile(this.apkFileName);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/FabricKitsFinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */