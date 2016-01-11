package io.fabric.sdk.android.services.settings;

public abstract interface SettingsController
{
  public abstract SettingsData loadSettingsData();
  
  public abstract SettingsData loadSettingsData(SettingsCacheBehavior paramSettingsCacheBehavior);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/settings/SettingsController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */