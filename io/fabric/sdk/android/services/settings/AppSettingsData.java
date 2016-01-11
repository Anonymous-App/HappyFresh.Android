package io.fabric.sdk.android.services.settings;

public class AppSettingsData
{
  public static final String STATUS_ACTIVATED = "activated";
  public static final String STATUS_CONFIGURED = "configured";
  public static final String STATUS_NEW = "new";
  public final AppIconSettingsData icon;
  public final String identifier;
  public final String reportsUrl;
  public final String status;
  public final boolean updateRequired;
  public final String url;
  
  public AppSettingsData(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean, AppIconSettingsData paramAppIconSettingsData)
  {
    this.identifier = paramString1;
    this.status = paramString2;
    this.url = paramString3;
    this.reportsUrl = paramString4;
    this.updateRequired = paramBoolean;
    this.icon = paramAppIconSettingsData;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/settings/AppSettingsData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */