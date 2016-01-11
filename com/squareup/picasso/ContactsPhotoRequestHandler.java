package com.squareup.picasso;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.UriMatcher;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.ContactsContract.Contacts;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

class ContactsPhotoRequestHandler
  extends RequestHandler
{
  private static final int ID_CONTACT = 3;
  private static final int ID_DISPLAY_PHOTO = 4;
  private static final int ID_LOOKUP = 1;
  private static final int ID_THUMBNAIL = 2;
  private static final UriMatcher matcher = new UriMatcher(-1);
  final Context context;
  
  static
  {
    matcher.addURI("com.android.contacts", "contacts/lookup/*/#", 1);
    matcher.addURI("com.android.contacts", "contacts/lookup/*", 1);
    matcher.addURI("com.android.contacts", "contacts/#/photo", 2);
    matcher.addURI("com.android.contacts", "contacts/#", 3);
    matcher.addURI("com.android.contacts", "display_photo/#", 4);
  }
  
  ContactsPhotoRequestHandler(Context paramContext)
  {
    this.context = paramContext;
  }
  
  private Bitmap decodeStream(InputStream paramInputStream, Request paramRequest)
    throws IOException
  {
    if (paramInputStream == null) {
      return null;
    }
    BitmapFactory.Options localOptions = createBitmapOptions(paramRequest);
    InputStream localInputStream;
    if (requiresInSampleSize(localOptions)) {
      localInputStream = getInputStream(paramRequest);
    }
    try
    {
      BitmapFactory.decodeStream(localInputStream, null, localOptions);
      Utils.closeQuietly(localInputStream);
      calculateInSampleSize(paramRequest.targetWidth, paramRequest.targetHeight, localOptions, paramRequest);
      return BitmapFactory.decodeStream(paramInputStream, null, localOptions);
    }
    finally
    {
      Utils.closeQuietly(localInputStream);
    }
  }
  
  private InputStream getInputStream(Request paramRequest)
    throws IOException
  {
    ContentResolver localContentResolver = this.context.getContentResolver();
    Uri localUri = paramRequest.uri;
    paramRequest = localUri;
    switch (matcher.match(localUri))
    {
    default: 
      throw new IllegalStateException("Invalid uri: " + localUri);
    case 1: 
      localUri = ContactsContract.Contacts.lookupContact(localContentResolver, localUri);
      paramRequest = localUri;
      if (localUri == null) {
        return null;
      }
    case 3: 
      if (Build.VERSION.SDK_INT < 14) {
        return ContactsContract.Contacts.openContactPhotoInputStream(localContentResolver, paramRequest);
      }
      return ContactPhotoStreamIcs.get(localContentResolver, paramRequest);
    }
    return localContentResolver.openInputStream(localUri);
  }
  
  public boolean canHandleRequest(Request paramRequest)
  {
    paramRequest = paramRequest.uri;
    return ("content".equals(paramRequest.getScheme())) && (ContactsContract.Contacts.CONTENT_URI.getHost().equals(paramRequest.getHost())) && (!paramRequest.getPathSegments().contains("photo"));
  }
  
  public RequestHandler.Result load(Request paramRequest)
    throws IOException
  {
    Object localObject = null;
    try
    {
      InputStream localInputStream = getInputStream(paramRequest);
      localObject = localInputStream;
      paramRequest = new RequestHandler.Result(decodeStream(localInputStream, paramRequest), Picasso.LoadedFrom.DISK);
      Utils.closeQuietly(localInputStream);
      return paramRequest;
    }
    finally
    {
      Utils.closeQuietly((InputStream)localObject);
    }
  }
  
  @TargetApi(14)
  private static class ContactPhotoStreamIcs
  {
    static InputStream get(ContentResolver paramContentResolver, Uri paramUri)
    {
      return ContactsContract.Contacts.openContactPhotoInputStream(paramContentResolver, paramUri, true);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/squareup/picasso/ContactsPhotoRequestHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */