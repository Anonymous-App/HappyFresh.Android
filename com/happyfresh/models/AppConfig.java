package com.happyfresh.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;

@Table(name="app_config")
public class AppConfig
  extends Model
{
  @SerializedName("strings")
  public OnboardingString onboardingString;
  @Column(name="require_code")
  @SerializedName("require_code")
  public boolean requireCode;
  
  public static void deleteConfig()
  {
    new Delete().from(AppConfig.class).execute();
  }
  
  public static AppConfig getConfig()
  {
    return (AppConfig)new Select().from(AppConfig.class).executeSingle();
  }
  
  public static boolean isRequireCode()
  {
    AppConfig localAppConfig = (AppConfig)new Select().from(AppConfig.class).executeSingle();
    if (localAppConfig != null) {
      return localAppConfig.requireCode;
    }
    return true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/AppConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */