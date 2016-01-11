package io.fabric.sdk.android.services.settings;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.KitInfo;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.ResponseParser;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.io.Closeable;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

abstract class AbstractAppSpiCall
  extends AbstractSpiCall
  implements AppSpiCall
{
  public static final String APP_BUILD_VERSION_PARAM = "app[build_version]";
  public static final String APP_BUILT_SDK_VERSION_PARAM = "app[built_sdk_version]";
  public static final String APP_DISPLAY_VERSION_PARAM = "app[display_version]";
  public static final String APP_ICON_DATA_PARAM = "app[icon][data]";
  public static final String APP_ICON_HASH_PARAM = "app[icon][hash]";
  public static final String APP_ICON_HEIGHT_PARAM = "app[icon][height]";
  public static final String APP_ICON_PRERENDERED_PARAM = "app[icon][prerendered]";
  public static final String APP_ICON_WIDTH_PARAM = "app[icon][width]";
  public static final String APP_IDENTIFIER_PARAM = "app[identifier]";
  public static final String APP_INSTANCE_IDENTIFIER_PARAM = "app[instance_identifier]";
  public static final String APP_MIN_SDK_VERSION_PARAM = "app[minimum_sdk_version]";
  public static final String APP_NAME_PARAM = "app[name]";
  public static final String APP_SDK_MODULES_PARAM_BUILD_TYPE = "app[build][libraries][%s][type]";
  public static final String APP_SDK_MODULES_PARAM_PREFIX = "app[build][libraries][%s]";
  public static final String APP_SDK_MODULES_PARAM_VERSION = "app[build][libraries][%s][version]";
  public static final String APP_SOURCE_PARAM = "app[source]";
  static final String ICON_CONTENT_TYPE = "application/octet-stream";
  static final String ICON_FILE_NAME = "icon.png";
  
  public AbstractAppSpiCall(Kit paramKit, String paramString1, String paramString2, HttpRequestFactory paramHttpRequestFactory, HttpMethod paramHttpMethod)
  {
    super(paramKit, paramString1, paramString2, paramHttpRequestFactory, paramHttpMethod);
  }
  
  private HttpRequest applyHeadersTo(HttpRequest paramHttpRequest, AppRequestData paramAppRequestData)
  {
    return paramHttpRequest.header("X-CRASHLYTICS-API-KEY", paramAppRequestData.apiKey).header("X-CRASHLYTICS-API-CLIENT-TYPE", "android").header("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion());
  }
  
  private HttpRequest applyMultipartDataTo(HttpRequest paramHttpRequest, AppRequestData paramAppRequestData)
  {
    HttpRequest localHttpRequest = paramHttpRequest.part("app[identifier]", paramAppRequestData.appId).part("app[name]", paramAppRequestData.name).part("app[display_version]", paramAppRequestData.displayVersion).part("app[build_version]", paramAppRequestData.buildVersion).part("app[source]", Integer.valueOf(paramAppRequestData.source)).part("app[minimum_sdk_version]", paramAppRequestData.minSdkVersion).part("app[built_sdk_version]", paramAppRequestData.builtSdkVersion);
    if (!CommonUtils.isNullOrEmpty(paramAppRequestData.instanceIdentifier)) {
      localHttpRequest.part("app[instance_identifier]", paramAppRequestData.instanceIdentifier);
    }
    Object localObject;
    if (paramAppRequestData.icon != null)
    {
      localObject = null;
      paramHttpRequest = null;
    }
    try
    {
      InputStream localInputStream = this.kit.getContext().getResources().openRawResource(paramAppRequestData.icon.iconResourceId);
      paramHttpRequest = localInputStream;
      localObject = localInputStream;
      localHttpRequest.part("app[icon][hash]", paramAppRequestData.icon.hash).part("app[icon][data]", "icon.png", "application/octet-stream", localInputStream).part("app[icon][width]", Integer.valueOf(paramAppRequestData.icon.width)).part("app[icon][height]", Integer.valueOf(paramAppRequestData.icon.height));
      CommonUtils.closeOrLog(localInputStream, "Failed to close app icon InputStream.");
    }
    catch (Resources.NotFoundException localNotFoundException)
    {
      for (;;)
      {
        localObject = paramHttpRequest;
        Fabric.getLogger().e("Fabric", "Failed to find app icon with resource ID: " + paramAppRequestData.icon.iconResourceId, localNotFoundException);
        CommonUtils.closeOrLog(paramHttpRequest, "Failed to close app icon InputStream.");
      }
    }
    finally
    {
      CommonUtils.closeOrLog((Closeable)localObject, "Failed to close app icon InputStream.");
    }
    if (paramAppRequestData.sdkKits != null)
    {
      paramHttpRequest = paramAppRequestData.sdkKits.iterator();
      while (paramHttpRequest.hasNext())
      {
        paramAppRequestData = (KitInfo)paramHttpRequest.next();
        localHttpRequest.part(getKitVersionKey(paramAppRequestData), paramAppRequestData.getVersion());
        localHttpRequest.part(getKitBuildTypeKey(paramAppRequestData), paramAppRequestData.getBuildType());
      }
    }
    return localHttpRequest;
  }
  
  String getKitBuildTypeKey(KitInfo paramKitInfo)
  {
    return String.format(Locale.US, "app[build][libraries][%s][type]", new Object[] { paramKitInfo.getIdentifier() });
  }
  
  String getKitVersionKey(KitInfo paramKitInfo)
  {
    return String.format(Locale.US, "app[build][libraries][%s][version]", new Object[] { paramKitInfo.getIdentifier() });
  }
  
  public boolean invoke(AppRequestData paramAppRequestData)
  {
    HttpRequest localHttpRequest = applyMultipartDataTo(applyHeadersTo(getHttpRequest(), paramAppRequestData), paramAppRequestData);
    Fabric.getLogger().d("Fabric", "Sending app info to " + getUrl());
    if (paramAppRequestData.icon != null)
    {
      Fabric.getLogger().d("Fabric", "App icon hash is " + paramAppRequestData.icon.hash);
      Fabric.getLogger().d("Fabric", "App icon size is " + paramAppRequestData.icon.width + "x" + paramAppRequestData.icon.height);
    }
    int i = localHttpRequest.code();
    if ("POST".equals(localHttpRequest.method())) {}
    for (paramAppRequestData = "Create";; paramAppRequestData = "Update")
    {
      Fabric.getLogger().d("Fabric", paramAppRequestData + " app request ID: " + localHttpRequest.header("X-REQUEST-ID"));
      Fabric.getLogger().d("Fabric", "Result was " + i);
      if (ResponseParser.parse(i) != 0) {
        break;
      }
      return true;
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/settings/AbstractAppSpiCall.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */