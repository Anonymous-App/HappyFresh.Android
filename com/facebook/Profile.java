package com.facebook;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.facebook.internal.ImageRequest;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.GraphMeRequestWithCacheCallback;
import com.facebook.internal.Validate;
import org.json.JSONException;
import org.json.JSONObject;

public final class Profile
  implements Parcelable
{
  public static final Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator()
  {
    public Profile createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Profile(paramAnonymousParcel, null);
    }
    
    public Profile[] newArray(int paramAnonymousInt)
    {
      return new Profile[paramAnonymousInt];
    }
  };
  private static final String FIRST_NAME_KEY = "first_name";
  private static final String ID_KEY = "id";
  private static final String LAST_NAME_KEY = "last_name";
  private static final String LINK_URI_KEY = "link_uri";
  private static final String MIDDLE_NAME_KEY = "middle_name";
  private static final String NAME_KEY = "name";
  private final String firstName;
  private final String id;
  private final String lastName;
  private final Uri linkUri;
  private final String middleName;
  private final String name;
  
  private Profile(Parcel paramParcel)
  {
    this.id = paramParcel.readString();
    this.firstName = paramParcel.readString();
    this.middleName = paramParcel.readString();
    this.lastName = paramParcel.readString();
    this.name = paramParcel.readString();
    paramParcel = paramParcel.readString();
    if (paramParcel == null) {}
    for (paramParcel = null;; paramParcel = Uri.parse(paramParcel))
    {
      this.linkUri = paramParcel;
      return;
    }
  }
  
  public Profile(String paramString1, @Nullable String paramString2, @Nullable String paramString3, @Nullable String paramString4, @Nullable String paramString5, @Nullable Uri paramUri)
  {
    Validate.notNullOrEmpty(paramString1, "id");
    this.id = paramString1;
    this.firstName = paramString2;
    this.middleName = paramString3;
    this.lastName = paramString4;
    this.name = paramString5;
    this.linkUri = paramUri;
  }
  
  Profile(JSONObject paramJSONObject)
  {
    this.id = paramJSONObject.optString("id", null);
    this.firstName = paramJSONObject.optString("first_name", null);
    this.middleName = paramJSONObject.optString("middle_name", null);
    this.lastName = paramJSONObject.optString("last_name", null);
    this.name = paramJSONObject.optString("name", null);
    paramJSONObject = paramJSONObject.optString("link_uri", null);
    if (paramJSONObject == null) {}
    for (paramJSONObject = (JSONObject)localObject;; paramJSONObject = Uri.parse(paramJSONObject))
    {
      this.linkUri = paramJSONObject;
      return;
    }
  }
  
  public static void fetchProfileForCurrentAccessToken()
  {
    AccessToken localAccessToken = AccessToken.getCurrentAccessToken();
    if (localAccessToken == null)
    {
      setCurrentProfile(null);
      return;
    }
    Utility.getGraphMeRequestWithCacheAsync(localAccessToken.getToken(), new Utility.GraphMeRequestWithCacheCallback()
    {
      public void onFailure(FacebookException paramAnonymousFacebookException) {}
      
      public void onSuccess(JSONObject paramAnonymousJSONObject)
      {
        String str1 = paramAnonymousJSONObject.optString("id");
        if (str1 == null) {
          return;
        }
        String str6 = paramAnonymousJSONObject.optString("link");
        String str2 = paramAnonymousJSONObject.optString("first_name");
        String str3 = paramAnonymousJSONObject.optString("middle_name");
        String str4 = paramAnonymousJSONObject.optString("last_name");
        String str5 = paramAnonymousJSONObject.optString("name");
        if (str6 != null) {}
        for (paramAnonymousJSONObject = Uri.parse(str6);; paramAnonymousJSONObject = null)
        {
          Profile.setCurrentProfile(new Profile(str1, str2, str3, str4, str5, paramAnonymousJSONObject));
          return;
        }
      }
    });
  }
  
  public static Profile getCurrentProfile()
  {
    return ProfileManager.getInstance().getCurrentProfile();
  }
  
  public static void setCurrentProfile(Profile paramProfile)
  {
    ProfileManager.getInstance().setCurrentProfile(paramProfile);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              return true;
              if (!(paramObject instanceof Profile)) {
                return false;
              }
              paramObject = (Profile)paramObject;
              if ((!this.id.equals(((Profile)paramObject).id)) || (this.firstName != null)) {
                break;
              }
            } while (((Profile)paramObject).firstName == null);
            return false;
            if ((!this.firstName.equals(((Profile)paramObject).firstName)) || (this.middleName != null)) {
              break;
            }
          } while (((Profile)paramObject).middleName == null);
          return false;
          if ((!this.middleName.equals(((Profile)paramObject).middleName)) || (this.lastName != null)) {
            break;
          }
        } while (((Profile)paramObject).lastName == null);
        return false;
        if ((!this.lastName.equals(((Profile)paramObject).lastName)) || (this.name != null)) {
          break;
        }
      } while (((Profile)paramObject).name == null);
      return false;
      if ((!this.name.equals(((Profile)paramObject).name)) || (this.linkUri != null)) {
        break;
      }
    } while (((Profile)paramObject).linkUri == null);
    return false;
    return this.linkUri.equals(((Profile)paramObject).linkUri);
  }
  
  public String getFirstName()
  {
    return this.firstName;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public String getLastName()
  {
    return this.lastName;
  }
  
  public Uri getLinkUri()
  {
    return this.linkUri;
  }
  
  public String getMiddleName()
  {
    return this.middleName;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public Uri getProfilePictureUri(int paramInt1, int paramInt2)
  {
    return ImageRequest.getProfilePictureUri(this.id, paramInt1, paramInt2);
  }
  
  public int hashCode()
  {
    int j = this.id.hashCode() + 527;
    int i = j;
    if (this.firstName != null) {
      i = j * 31 + this.firstName.hashCode();
    }
    j = i;
    if (this.middleName != null) {
      j = i * 31 + this.middleName.hashCode();
    }
    i = j;
    if (this.lastName != null) {
      i = j * 31 + this.lastName.hashCode();
    }
    j = i;
    if (this.name != null) {
      j = i * 31 + this.name.hashCode();
    }
    i = j;
    if (this.linkUri != null) {
      i = j * 31 + this.linkUri.hashCode();
    }
    return i;
  }
  
  JSONObject toJSONObject()
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("id", this.id);
      localJSONObject.put("first_name", this.firstName);
      localJSONObject.put("middle_name", this.middleName);
      localJSONObject.put("last_name", this.lastName);
      localJSONObject.put("name", this.name);
      if (this.linkUri != null) {
        localJSONObject.put("link_uri", this.linkUri.toString());
      }
      return localJSONObject;
    }
    catch (JSONException localJSONException) {}
    return null;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.id);
    paramParcel.writeString(this.firstName);
    paramParcel.writeString(this.middleName);
    paramParcel.writeString(this.lastName);
    paramParcel.writeString(this.name);
    if (this.linkUri == null) {}
    for (String str = null;; str = this.linkUri.toString())
    {
      paramParcel.writeString(str);
      return;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/Profile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */