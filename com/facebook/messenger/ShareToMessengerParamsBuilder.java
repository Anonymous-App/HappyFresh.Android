package com.facebook.messenger;

import android.net.Uri;

public class ShareToMessengerParamsBuilder
{
  private Uri mExternalUri;
  private String mMetaData;
  private final String mMimeType;
  private final Uri mUri;
  
  ShareToMessengerParamsBuilder(Uri paramUri, String paramString)
  {
    this.mUri = paramUri;
    this.mMimeType = paramString;
  }
  
  public ShareToMessengerParams build()
  {
    return new ShareToMessengerParams(this);
  }
  
  public Uri getExternalUri()
  {
    return this.mExternalUri;
  }
  
  public String getMetaData()
  {
    return this.mMetaData;
  }
  
  public String getMimeType()
  {
    return this.mMimeType;
  }
  
  public Uri getUri()
  {
    return this.mUri;
  }
  
  public ShareToMessengerParamsBuilder setExternalUri(Uri paramUri)
  {
    this.mExternalUri = paramUri;
    return this;
  }
  
  public ShareToMessengerParamsBuilder setMetaData(String paramString)
  {
    this.mMetaData = paramString;
    return this;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/messenger/ShareToMessengerParamsBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */