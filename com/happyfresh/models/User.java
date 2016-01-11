package com.happyfresh.models;

import android.text.TextUtils;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Table(name="users")
public class User
  extends Model
{
  @Column(name="code")
  public String code;
  @Column(name="email")
  public String email;
  @Column(name="first_name")
  @SerializedName("first_name")
  public String firstName;
  public String language;
  @Column(name="last_name")
  @SerializedName("last_name")
  public String lastName;
  @SerializedName("verified")
  public boolean phoneVerified;
  @SerializedName("strings")
  public PromotionString promotionString;
  @Column(name="remote_id")
  @SerializedName("id")
  public long remoteId;
  private List<Address> shippingAddresses = new ArrayList();
  @Column(name="token")
  public String token;
  
  public static void deleteUser()
  {
    new Delete().from(User.class).execute();
  }
  
  public static User getCurrentUser()
  {
    return (User)new Select().from(User.class).executeSingle();
  }
  
  public List<Address> getAddresses()
  {
    try
    {
      List localList = this.shippingAddresses;
      return localList;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public String getFullName()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (!TextUtils.isEmpty(this.firstName)) {
      localStringBuilder.append(this.firstName);
    }
    if (!TextUtils.isEmpty(this.lastName))
    {
      localStringBuilder.append(" ");
      localStringBuilder.append(this.lastName);
    }
    return localStringBuilder.toString();
  }
  
  public Address getPrimaryShippingAddress()
  {
    if (this.shippingAddresses.size() > 0)
    {
      Object localObject2 = null;
      Iterator localIterator = this.shippingAddresses.iterator();
      Object localObject1;
      do
      {
        localObject1 = localObject2;
        if (!localIterator.hasNext()) {
          break;
        }
        localObject1 = (Address)localIterator.next();
      } while (!((Address)localObject1).isPrimary);
      return (Address)localObject1;
    }
    return null;
  }
  
  public void setAddresses(List<Address> paramList)
  {
    try
    {
      getAddresses().addAll(paramList);
      return;
    }
    finally
    {
      paramList = finally;
      throw paramList;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/User.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */