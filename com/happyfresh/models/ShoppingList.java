package com.happyfresh.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ConflictAction;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;
import java.util.Iterator;
import java.util.List;

@Table(name="shopping_list")
public class ShoppingList
  extends Model
{
  public static final String LIST_TYPE_FAVORITE = "favorite";
  @Column(name="remote_id", onUniqueConflict=Column.ConflictAction.IGNORE, unique=true)
  public Long id;
  @SerializedName("shopping_list_items")
  public List<ShoppingListItem> items;
  @Column(name="list_type")
  @SerializedName("list_type")
  public String listType;
  @Column(name="name")
  public String name;
  public Photo photo;
  @SerializedName("stock_location_id")
  public Long stockLocationId;
  @Column(name="user_id")
  @SerializedName("user_id")
  public Long userId;
  
  public static void deleteFavoriteList()
  {
    Iterator localIterator = new Select().from(ShoppingList.class).where("list_type = ?", new Object[] { "favorite" }).execute().iterator();
    while (localIterator.hasNext())
    {
      ShoppingList localShoppingList = (ShoppingList)localIterator.next();
      new Delete().from(ShoppingListItem.class).where("shopping_list_id = ?", new Object[] { localShoppingList.id }).execute();
      localShoppingList.delete();
    }
  }
  
  public static ShoppingList findFavoriteList()
  {
    return (ShoppingList)new Select().from(ShoppingList.class).where("list_type = ?", new Object[] { "favorite" }).executeSingle();
  }
  
  public static List<ShoppingListItem> getFavoriteListItem()
  {
    ShoppingList localShoppingList = findFavoriteList();
    if (localShoppingList != null) {
      return ShoppingListItem.findShoppingListItemsByShoppingListId(localShoppingList.id);
    }
    return null;
  }
  
  public class Photo
  {
    @SerializedName("medium_url")
    public String mediumUrl;
    @SerializedName("thumb_url")
    public String thumUrl;
    public String url;
    
    public Photo() {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/ShoppingList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */