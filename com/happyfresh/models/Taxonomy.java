package com.happyfresh.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ConflictAction;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;

@Table(name="taxonomies")
public class Taxonomy
  extends Model
{
  @Column(name="name")
  public String name;
  @Column(name="remote_id", onUniqueConflict=Column.ConflictAction.REPLACE)
  @SerializedName("id")
  public long remoteId;
  public Taxon root;
  
  public static void deleteAll()
  {
    new Delete().from(Taxonomy.class).execute();
  }
  
  public static Taxonomy getCategory()
  {
    return (Taxonomy)new Select().from(Taxonomy.class).executeSingle();
  }
  
  public boolean isCategory()
  {
    return this.name.equalsIgnoreCase("categories");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/Taxonomy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */