package io.fabric.sdk.android.services.settings;

public class BetaSettingsData
{
  public final int updateSuspendDurationSeconds;
  public final String updateUrl;
  
  public BetaSettingsData(String paramString, int paramInt)
  {
    this.updateUrl = paramString;
    this.updateSuspendDurationSeconds = paramInt;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/settings/BetaSettingsData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */