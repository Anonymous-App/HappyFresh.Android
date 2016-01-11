package com.happyfresh.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ConflictAction;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Table(name="taxons")
public class Taxon
  extends Model
  implements Parcelable
{
  public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
  {
    public Taxon createFromParcel(Parcel paramAnonymousParcel)
    {
      Taxon localTaxon = new Taxon();
      localTaxon.remoteId = paramAnonymousParcel.readLong();
      localTaxon.name = ((String)paramAnonymousParcel.readValue(Long.class.getClassLoader()));
      localTaxon.prettyName = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localTaxon.permalink = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localTaxon.parentId = ((Long)paramAnonymousParcel.readValue(Long.class.getClassLoader()));
      localTaxon.taxonomyId = ((Long)paramAnonymousParcel.readValue(Long.class.getClassLoader()));
      localTaxon.iconUrl = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localTaxon.levelOneId = ((Long)paramAnonymousParcel.readValue(Long.class.getClassLoader()));
      localTaxon.productsCount = ((Integer)paramAnonymousParcel.readValue(Integer.class.getClassLoader()));
      if (((Byte)paramAnonymousParcel.readValue(Byte.class.getClassLoader())).byteValue() != 0) {}
      for (boolean bool = true;; bool = false)
      {
        localTaxon.isRoot = Boolean.valueOf(bool);
        localTaxon.position = ((Double)paramAnonymousParcel.readValue(Double.class.getClassLoader()));
        return localTaxon;
      }
    }
    
    public Taxon[] newArray(int paramAnonymousInt)
    {
      return new Taxon[paramAnonymousInt];
    }
  };
  @Column(name="icon_url")
  @SerializedName("icon_url")
  public String iconUrl;
  @Column(name="is_root")
  public Boolean isRoot;
  @Column(name="level_one_id")
  public Long levelOneId;
  @Column(name="name")
  public String name;
  @Column(name="parent_id")
  @SerializedName("parent_id")
  public Long parentId;
  @Column(name="permalink")
  public String permalink;
  @Column(name="position")
  public Double position;
  @Column(name="prettyName")
  @SerializedName("pretty_name")
  public String prettyName;
  @Column(name="products_count")
  @SerializedName("products_count")
  public Integer productsCount;
  @Column(name="remote_id", onUniqueConflict=Column.ConflictAction.IGNORE)
  @SerializedName("id")
  public long remoteId;
  @Column(name="taxonomy_id")
  @SerializedName("taxonomy_id")
  public Long taxonomyId;
  public List<Taxon> taxons = new ArrayList();
  
  public static void deleteAll()
  {
    new Delete().from(Taxon.class).execute();
  }
  
  public static void deleteAllSubCategories()
  {
    new Delete().from(Taxon.class).where("is_root IS NULL").execute();
  }
  
  public static Taxon findByParent(Long paramLong)
  {
    return (Taxon)new Select().from(Taxon.class).where("remote_id = ?", new Object[] { paramLong }).executeSingle();
  }
  
  public static List<Taxon> findChildren(Long paramLong, List<Long> paramList)
  {
    paramList = TextUtils.join(" OR remote_id = ", paramList);
    return new Select().from(Taxon.class).where("parent_id = ? AND (remote_id = " + paramList + ")", new Object[] { paramLong }).execute();
  }
  
  public static List<Taxon> getLevelOneTaxons(List<Long> paramList)
  {
    paramList = TextUtils.join(" OR remote_id = ", paramList);
    Object localObject = new Select().from(Taxon.class).where("remote_id = " + paramList).execute();
    paramList = new HashSet();
    localObject = ((List)localObject).iterator();
    while (((Iterator)localObject).hasNext()) {
      paramList.add(((Taxon)((Iterator)localObject).next()).levelOneId);
    }
    if (paramList.size() > 0)
    {
      paramList = TextUtils.join(" OR remote_id = ", paramList);
      return new Select().from(Taxon.class).where("remote_id = " + paramList).execute();
    }
    return Collections.EMPTY_LIST;
  }
  
  public static List<Taxon> getSubLevel(long paramLong)
  {
    return new Select().from(Taxon.class).where("parent_id = ?  AND (products_count > 0 OR level_one_id IS NULL)", new Object[] { Long.valueOf(paramLong) }).execute();
  }
  
  public static List<Taxon> getTaxons(List<Long> paramList)
  {
    paramList = TextUtils.join(" OR remote_id = ", paramList);
    return new Select().from(Taxon.class).where("remote_id = " + paramList).execute();
  }
  
  public static List<Taxon> getTopLevel()
  {
    return new Select().from(Taxon.class).where("level_one_id IS NULL AND remote_id > 0").execute();
  }
  
  public int countChildren()
  {
    return new Select().from(Taxon.class).where("parent_id = ?", new Object[] { Long.valueOf(this.remoteId) }).count();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeLong(this.remoteId);
    paramParcel.writeValue(this.name);
    paramParcel.writeValue(this.prettyName);
    paramParcel.writeValue(this.permalink);
    paramParcel.writeValue(this.parentId);
    paramParcel.writeValue(this.taxonomyId);
    paramParcel.writeValue(this.iconUrl);
    paramParcel.writeValue(this.levelOneId);
    paramParcel.writeValue(this.productsCount);
    if ((this.isRoot != null) && (this.isRoot.booleanValue())) {}
    for (paramInt = 1;; paramInt = 0)
    {
      paramParcel.writeValue(Byte.valueOf((byte)paramInt));
      paramParcel.writeValue(this.position);
      return;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/Taxon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */