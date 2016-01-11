package com.happyfresh.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ConflictAction;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import java.util.List;

@Table(name="shopping_list_item")
public class ShoppingListItem
  extends Model
{
  @Column(name="remote_id", onUniqueConflict=Column.ConflictAction.IGNORE, unique=true)
  public Long id;
  @Column(name="shopping_list_id")
  public Long shoppingListId;
  public Variant variant;
  @Column(name="variant_id")
  public long variantId;
  
  public static ShoppingListItem findShoppingListItemByVariantId(Long paramLong)
  {
    return (ShoppingListItem)new Select().from(ShoppingListItem.class).where("variant_id = ?", new Object[] { paramLong }).executeSingle();
  }
  
  public static List<ShoppingListItem> findShoppingListItemsByShoppingListId(Long paramLong)
  {
    return new Select().from(ShoppingListItem.class).where("shopping_list_id = ?", new Object[] { paramLong }).execute();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/ShoppingListItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */