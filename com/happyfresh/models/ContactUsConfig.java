package com.happyfresh.models;

import android.database.sqlite.SQLiteException;
import com.activeandroid.Model;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;

@Table(name="contact_us_config")
public class ContactUsConfig
  extends Model
{
  @SerializedName("strings")
  public ContactUsString contactUsString;
  @SerializedName("cs_phone")
  public String csPhone;
  
  public static void deleteConfig()
  {
    try
    {
      new Delete().from(ContactUsConfig.class).execute();
      return;
    }
    catch (SQLiteException localSQLiteException) {}
  }
  
  public static ContactUsConfig getConfig()
  {
    return (ContactUsConfig)new Select().from(ContactUsConfig.class).executeSingle();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/ContactUsConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */