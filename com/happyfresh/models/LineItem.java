package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LineItem
{
  public List<Adjustment> adjustments;
  public boolean btnSubItemEnabled = true;
  @SerializedName("display_amount")
  public String displayAmount;
  private boolean isOutOfStock = false;
  @SerializedName("max_order_quantity")
  public Integer maxOrderQuantity;
  public Order order;
  public Double price;
  public Integer quantity;
  @SerializedName("id")
  public long remoteId;
  @SerializedName("replacement_type")
  public String replacementOption;
  @SerializedName("replacements")
  private List<Replacement> replacements = new ArrayList();
  @SerializedName("single_display_amount")
  public String singleDisplayAmount;
  @SerializedName("single_display_unit")
  public String singleDisplayUnit;
  @SerializedName("stock_location")
  public StockLocation stockLocation;
  public Double total;
  public Variant variant;
  @SerializedName("variant_id")
  public Long variantId;
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if ((paramObject == null) || (getClass() != paramObject.getClass())) {
        return false;
      }
    } while (this.remoteId == ((LineItem)paramObject).remoteId);
    return false;
  }
  
  public String getCategory()
  {
    if ((this.variant != null) && (this.variant.product != null) && (this.variant.product.taxon != null)) {
      return this.variant.product.taxon.permalink;
    }
    return "";
  }
  
  public Replacement getCustomerReplacement(Long paramLong)
  {
    Object localObject2 = null;
    Iterator localIterator = this.replacements.iterator();
    Object localObject1;
    do
    {
      localObject1 = localObject2;
      if (!localIterator.hasNext()) {
        break;
      }
      localObject1 = (Replacement)localIterator.next();
    } while ((((Replacement)localObject1).userId != null) && (!((Replacement)localObject1).userId.equals(paramLong)));
    return (Replacement)localObject1;
  }
  
  public Replacement getReplacement()
  {
    if (this.replacements.size() > 0) {
      return (Replacement)this.replacements.get(0);
    }
    return null;
  }
  
  public List<Replacement> getReplacements()
  {
    return this.replacements;
  }
  
  public Replacement getShopperReplacement(Long paramLong)
  {
    Object localObject2 = null;
    Iterator localIterator = this.replacements.iterator();
    Object localObject1;
    do
    {
      localObject1 = localObject2;
      if (!localIterator.hasNext()) {
        break;
      }
      localObject1 = (Replacement)localIterator.next();
    } while ((((Replacement)localObject1).userId == null) || (((Replacement)localObject1).userId.equals(paramLong)));
    return (Replacement)localObject1;
  }
  
  public boolean hasReplacement()
  {
    return this.replacements.size() > 0;
  }
  
  public boolean isOutOfStock()
  {
    return this.isOutOfStock;
  }
  
  public void setOutOfStock()
  {
    this.isOutOfStock = true;
  }
  
  public void setReplacement(Replacement paramReplacement)
  {
    this.replacements.clear();
    if (paramReplacement != null) {
      this.replacements.add(paramReplacement);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/LineItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */