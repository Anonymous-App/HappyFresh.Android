package com.happyfresh.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ConflictAction;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name="products")
public class Product
  extends Model
  implements Parcelable
{
  public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
  {
    public Product createFromParcel(Parcel paramAnonymousParcel)
    {
      Product localProduct = new Product();
      localProduct.remoteId = ((Long)paramAnonymousParcel.readValue(Long.class.getClassLoader()));
      localProduct.taxon = ((Taxon)paramAnonymousParcel.readParcelable(Taxon.class.getClassLoader()));
      localProduct.name = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localProduct.description = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localProduct.price = ((Double)paramAnonymousParcel.readValue(Double.class.getClassLoader()));
      localProduct.displayPrice = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localProduct.normalPrice = ((String)paramAnonymousParcel.readValue(Double.class.getClassLoader()));
      localProduct.displayNormalPrice = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localProduct.displayPromoPercentage = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localProduct.availableOn = new Date(paramAnonymousParcel.readLong());
      localProduct.slug = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localProduct.metaDescription = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localProduct.metaKeywords = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localProduct.shippingCategoryId = ((Long)paramAnonymousParcel.readValue(Long.class.getClassLoader()));
      localProduct.taxonIds = paramAnonymousParcel.readArrayList(Long.class.getClassLoader());
      localProduct.totalOnHand = ((Integer)paramAnonymousParcel.readValue(Integer.class.getClassLoader()));
      if (((Byte)paramAnonymousParcel.readValue(Byte.class.getClassLoader())).byteValue() != 0) {}
      for (boolean bool = true;; bool = false)
      {
        localProduct.hasVariants = bool;
        localProduct.popularity = ((Double)paramAnonymousParcel.readValue(Double.class.getClassLoader()));
        localProduct.variants = paramAnonymousParcel.readArrayList(Variant.class.getClassLoader());
        localProduct.productProperties = paramAnonymousParcel.readArrayList(ProductProperty.class.getClassLoader());
        localProduct.displayUnit = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
        localProduct.displayAverageWeight = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
        localProduct.category = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
        localProduct.maxOrderQuantity = ((Integer)paramAnonymousParcel.readValue(Integer.class.getClassLoader()));
        localProduct.displayPromoCombinationText = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
        localProduct.categories = paramAnonymousParcel.readArrayList(String.class.getClassLoader());
        localProduct.displayPromoActions = paramAnonymousParcel.readArrayList(DisplayPromoAction.class.getClassLoader());
        return localProduct;
      }
    }
    
    public Product[] newArray(int paramAnonymousInt)
    {
      return new Product[paramAnonymousInt];
    }
  };
  @SerializedName("available_on")
  public Date availableOn;
  public List<String> categories;
  @Column(name="category")
  public String category;
  public String description;
  @SerializedName("display_average_weight")
  public String displayAverageWeight;
  @SerializedName("display_normal_price")
  public String displayNormalPrice;
  @SerializedName("display_price")
  public String displayPrice;
  @SerializedName("promotion_actions")
  public List<DisplayPromoAction> displayPromoActions;
  @SerializedName("display_promotion_actions_combination_text")
  public String displayPromoCombinationText;
  @SerializedName("display_promo_price_percentage")
  public String displayPromoPercentage;
  @SerializedName("display_unit")
  public String displayUnit;
  @SerializedName("has_variants")
  public boolean hasVariants;
  @SerializedName("max_order_quantity")
  public Integer maxOrderQuantity;
  @SerializedName("meta_description")
  public String metaDescription;
  @SerializedName("meta_keywords")
  public String metaKeywords;
  public String name;
  @SerializedName("normal_price")
  public String normalPrice;
  @SerializedName("option_types")
  public List<OptionType> optionTypes = new ArrayList();
  public Double popularity;
  public Double price;
  @SerializedName("product_properties")
  public List<ProductProperty> productProperties = new ArrayList();
  @Column(index=true, name="remote_id", onUniqueConflict=Column.ConflictAction.IGNORE)
  @SerializedName("id")
  public Long remoteId;
  public boolean replaced;
  @SerializedName("shipping_category_id")
  public Long shippingCategoryId;
  public String slug;
  @SerializedName("taxon")
  public Taxon taxon;
  @SerializedName("taxon_ids")
  public List<Long> taxonIds;
  @SerializedName("total_on_hand")
  public Integer totalOnHand;
  public List<Variant> variants = new ArrayList();
  
  public static void deleteAll()
  {
    new Delete().from(Product.class).execute();
  }
  
  public static Product findById(Long paramLong)
  {
    return (Product)new Select().from(Product.class).where("remote_id = ?", new Object[] { paramLong }).executeSingle();
  }
  
  public static Product findByVariantId(Long paramLong)
  {
    return (Product)new Select().from(Product.class).where("remote_id = ?", new Object[] { paramLong }).executeSingle();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean hasPromo()
  {
    boolean bool = false;
    int i;
    if ((this.displayPromoPercentage != null) && (!this.displayPromoPercentage.isEmpty()))
    {
      i = 1;
      if ((this.categories == null) || (this.categories.isEmpty()) || (!"specials".equalsIgnoreCase((String)this.categories.get(0)))) {
        break label80;
      }
    }
    label80:
    for (int j = 1;; j = 0)
    {
      if ((i != 0) || (j != 0)) {
        bool = true;
      }
      return bool;
      i = 0;
      break;
    }
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeValue(this.remoteId);
    paramParcel.writeParcelable(this.taxon, paramInt);
    paramParcel.writeValue(this.name);
    paramParcel.writeValue(this.description);
    paramParcel.writeValue(this.price);
    paramParcel.writeValue(this.displayPrice);
    paramParcel.writeValue(this.normalPrice);
    paramParcel.writeValue(this.displayNormalPrice);
    paramParcel.writeValue(this.displayPromoPercentage);
    long l;
    if (this.availableOn == null)
    {
      l = 0L;
      paramParcel.writeLong(l);
      paramParcel.writeValue(this.slug);
      paramParcel.writeValue(this.metaDescription);
      paramParcel.writeValue(this.metaKeywords);
      paramParcel.writeValue(this.shippingCategoryId);
      paramParcel.writeList(this.taxonIds);
      paramParcel.writeValue(this.totalOnHand);
      if (!this.hasVariants) {
        break label245;
      }
    }
    label245:
    for (paramInt = 1;; paramInt = 0)
    {
      paramParcel.writeValue(Byte.valueOf((byte)paramInt));
      paramParcel.writeValue(this.popularity);
      paramParcel.writeList(this.variants);
      paramParcel.writeList(this.productProperties);
      paramParcel.writeValue(this.displayUnit);
      paramParcel.writeValue(this.displayAverageWeight);
      paramParcel.writeValue(this.category);
      paramParcel.writeValue(this.maxOrderQuantity);
      paramParcel.writeValue(this.displayPromoCombinationText);
      paramParcel.writeList(this.categories);
      paramParcel.writeList(this.displayPromoActions);
      return;
      l = this.availableOn.getTime();
      break;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/Product.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */