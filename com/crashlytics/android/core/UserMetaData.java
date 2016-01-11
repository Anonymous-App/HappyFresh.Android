package com.crashlytics.android.core;

public class UserMetaData
{
  public static final UserMetaData EMPTY = new UserMetaData();
  public final String email;
  public final String id;
  public final String name;
  
  public UserMetaData()
  {
    this(null, null, null);
  }
  
  public UserMetaData(String paramString1, String paramString2, String paramString3)
  {
    this.id = paramString1;
    this.name = paramString2;
    this.email = paramString3;
  }
  
  public boolean isEmpty()
  {
    return (this.id == null) && (this.name == null) && (this.email == null);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/crashlytics/android/core/UserMetaData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */