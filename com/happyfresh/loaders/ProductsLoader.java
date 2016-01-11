package com.happyfresh.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import com.happyfresh.models.PopularProduct;
import com.happyfresh.models.Product;
import com.happyfresh.models.Taxon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class ProductsLoader
  extends AsyncTaskLoader<List<PopularProduct>>
{
  private Taxon mCurrentTaxon;
  private List<PopularProduct> mData;
  private List<Product> mDataProducts;
  private boolean mFromSearch = false;
  private boolean mSortByPopularity = true;
  
  public ProductsLoader(Context paramContext, List<Product> paramList, Taxon paramTaxon)
  {
    super(paramContext);
    this.mDataProducts = paramList;
    this.mCurrentTaxon = paramTaxon;
  }
  
  public ProductsLoader(Context paramContext, List<Product> paramList, Taxon paramTaxon, boolean paramBoolean)
  {
    this(paramContext, paramList, paramTaxon);
  }
  
  public ProductsLoader(Context paramContext, List<Product> paramList, Taxon paramTaxon, boolean paramBoolean1, boolean paramBoolean2)
  {
    this(paramContext, paramList, paramTaxon, paramBoolean1);
    this.mSortByPopularity = paramBoolean2;
  }
  
  private void releaseResources(List<PopularProduct> paramList) {}
  
  public void deliverResult(List<PopularProduct> paramList)
  {
    if (isReset()) {
      releaseResources(paramList);
    }
    List localList;
    do
    {
      return;
      localList = this.mData;
      this.mData = paramList;
      if (isStarted()) {
        super.deliverResult(paramList);
      }
    } while ((localList == null) || (localList == paramList));
    releaseResources(localList);
  }
  
  public List<PopularProduct> loadInBackground()
  {
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    Object localObject6;
    for (;;)
    {
      Product localProduct;
      synchronized (this.mDataProducts)
      {
        localObject6 = this.mDataProducts.iterator();
        if (!((Iterator)localObject6).hasNext()) {
          break;
        }
        localProduct = (Product)((Iterator)localObject6).next();
        if (localProduct.taxonIds.isEmpty()) {
          continue;
        }
        localObject4 = new ArrayList();
        if (this.mCurrentTaxon == null) {
          break label228;
        }
        if (this.mCurrentTaxon.levelOneId == null)
        {
          Object localObject1 = Taxon.findChildren(Long.valueOf(this.mCurrentTaxon.remoteId), localProduct.taxonIds);
          localObject4 = ((List)localObject1).iterator();
          if (!((Iterator)localObject4).hasNext()) {
            continue;
          }
          Taxon localTaxon = (Taxon)((Iterator)localObject4).next();
          localObject1 = new ArrayList();
          if (localLinkedHashMap.containsKey(localTaxon)) {
            localObject1 = (List)localLinkedHashMap.get(localTaxon);
          }
          ((List)localObject1).add(localProduct);
          localLinkedHashMap.put(localTaxon, localObject1);
        }
      }
      localObject3 = localObject4;
      if (localProduct.taxonIds.contains(Long.valueOf(this.mCurrentTaxon.remoteId)))
      {
        ((List)localObject4).add(this.mCurrentTaxon);
        localObject3 = localObject4;
        continue;
        label228:
        if (this.mFromSearch) {
          localObject3 = Taxon.getTaxons(localProduct.taxonIds);
        } else {
          localObject3 = Taxon.getLevelOneTaxons(localProduct.taxonIds);
        }
      }
    }
    Object localObject3 = new ArrayList();
    Object localObject4 = new ArrayList(localLinkedHashMap.keySet());
    if (!this.mFromSearch) {
      Collections.sort((List)localObject4, new Comparator()
      {
        public int compare(Taxon paramAnonymousTaxon1, Taxon paramAnonymousTaxon2)
        {
          return paramAnonymousTaxon1.position.compareTo(paramAnonymousTaxon2.position);
        }
      });
    }
    localObject4 = ((List)localObject4).iterator();
    while (((Iterator)localObject4).hasNext())
    {
      ??? = (Taxon)((Iterator)localObject4).next();
      if ((!this.mFromSearch) && (this.mSortByPopularity)) {
        Collections.sort((List)localLinkedHashMap.get(???), new Comparator()
        {
          public int compare(Product paramAnonymousProduct1, Product paramAnonymousProduct2)
          {
            return paramAnonymousProduct2.popularity.compareTo(paramAnonymousProduct1.popularity);
          }
        });
      }
      localObject6 = new PopularProduct();
      ((PopularProduct)localObject6).taxon = ((Taxon)???);
      ((PopularProduct)localObject6).products = ((List)localLinkedHashMap.get(???));
      ((List)localObject3).add(localObject6);
    }
    return (List<PopularProduct>)localObject3;
  }
  
  public void onCanceled(List<PopularProduct> paramList)
  {
    super.onCanceled(paramList);
    releaseResources(paramList);
  }
  
  protected void onReset()
  {
    onStopLoading();
    if (this.mData != null)
    {
      releaseResources(this.mData);
      this.mData = null;
    }
  }
  
  protected void onStartLoading()
  {
    if (this.mData != null) {
      deliverResult(this.mData);
    }
    if ((takeContentChanged()) || (this.mData == null)) {
      forceLoad();
    }
  }
  
  protected void onStopLoading()
  {
    cancelLoad();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/loaders/ProductsLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */