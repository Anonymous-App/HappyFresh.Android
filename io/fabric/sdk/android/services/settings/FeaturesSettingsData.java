package io.fabric.sdk.android.services.settings;

public class FeaturesSettingsData
{
  public final boolean collectAnalytics;
  public final boolean collectLoggedException;
  public final boolean collectReports;
  public final boolean promptEnabled;
  
  public FeaturesSettingsData(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    this.promptEnabled = paramBoolean1;
    this.collectLoggedException = paramBoolean2;
    this.collectReports = paramBoolean3;
    this.collectAnalytics = paramBoolean4;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/settings/FeaturesSettingsData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */