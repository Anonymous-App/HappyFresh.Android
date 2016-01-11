package com.happyfresh.managers;

import android.widget.Toast;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.callbacks.ICartRetrofitCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartRestClient;
import com.happyfresh.models.AutoSuggestionResponse;
import com.happyfresh.models.Product;
import com.happyfresh.models.ProductResponse;
import com.happyfresh.models.Taxon;
import com.happyfresh.models.TaxonResponse;
import com.happyfresh.models.TaxonomyResponse;
import com.happyfresh.services.ProductService;
import com.happyfresh.utils.AccengageTrackerUtils;
import com.happyfresh.utils.AdjustUtils;
import com.happyfresh.utils.GAUtils;
import com.happyfresh.utils.LogUtils;
import com.happyfresh.utils.MixpanelTrackerUtils;
import java.util.Iterator;
import java.util.List;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProductManager
  extends BaseManager
{
  private static final String TAG = ProductManager.class.getSimpleName();
  
  public ProductManager(ICartApplication paramICartApplication)
  {
    super(paramICartApplication);
  }
  
  private String getSortByQueryString(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return null;
    case 0: 
      return "popularity desc";
    case 2: 
      return "price desc";
    case 1: 
      return "price asc";
    case 3: 
      return "name asc";
    }
    return "name desc";
  }
  
  public void conversionViewProductDetail(Long paramLong1, Long paramLong2)
  {
    this.mICartRestClient.getProductService().conversionViewProductDetail(paramLong1, paramLong2, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        LogUtils.LOG("Send view product conversion failed");
      }
      
      public void success(Object paramAnonymousObject, Response paramAnonymousResponse)
      {
        LogUtils.LOG("Send view product conversion completed");
      }
    });
  }
  
  public void getCategories(Integer paramInteger1, Integer paramInteger2, final ICartCallback paramICartCallback)
  {
    this.mICartRestClient.getProductService().getTaxonomies(paramInteger1, paramInteger2, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(TaxonomyResponse paramAnonymousTaxonomyResponse, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousTaxonomyResponse);
        }
      }
    });
  }
  
  public void getPopularProductsByLocation(Long paramLong, Integer paramInteger1, Integer paramInteger2, final ICartCallback paramICartCallback)
  {
    ProductService localProductService = this.mICartRestClient.getProductService();
    AdjustUtils.trackPopularProduct();
    MixpanelTrackerUtils.trackViewPopularProduct(this.mICartApplication);
    AccengageTrackerUtils.trackViewPopularProduct(this.mICartApplication);
    GAUtils.trackPopularProduct(this.mICartApplication, "Popular");
    localProductService.getPopularProductsByLocation(paramLong.longValue(), paramInteger1, paramInteger2, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(ProductResponse paramAnonymousProductResponse, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousProductResponse);
        }
      }
    });
  }
  
  public void getProductsById(Long paramLong1, Long paramLong2, final ICartRetrofitCallback<Object> paramICartRetrofitCallback)
  {
    this.mICartRestClient.getProductService().getProduct(paramLong1.longValue(), paramLong2.longValue(), new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartRetrofitCallback != null) {
          paramICartRetrofitCallback.failure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Product paramAnonymousProduct, Response paramAnonymousResponse)
      {
        if (paramICartRetrofitCallback != null) {
          paramICartRetrofitCallback.success(paramAnonymousProduct, paramAnonymousResponse);
        }
      }
    });
  }
  
  public void getProductsByTaxon(Long paramLong, Taxon paramTaxon, Integer paramInteger1, Integer paramInteger2, int paramInt, final ICartCallback paramICartCallback)
  {
    if (paramTaxon == null)
    {
      Toast.makeText(this.mICartApplication, this.mICartApplication.getString(2131165671), 0).show();
      return;
    }
    ProductService localProductService = this.mICartRestClient.getProductService();
    MixpanelTrackerUtils.trackViewCategory(this.mICartApplication, paramTaxon);
    AccengageTrackerUtils.trackViewCategory(this.mICartApplication, paramTaxon);
    GAUtils.trackCategoryProduct(this.mICartApplication, paramTaxon.name);
    String str = getSortByQueryString(paramInt);
    localProductService.getProductsByTaxon(paramLong.longValue(), Long.valueOf(paramTaxon.remoteId), paramInteger1, paramInteger2, str, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(ProductResponse paramAnonymousProductResponse, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousProductResponse);
        }
      }
    });
  }
  
  public void getProductsByTaxon(Long paramLong, Taxon paramTaxon, Integer paramInteger1, Integer paramInteger2, ICartCallback paramICartCallback)
  {
    getProductsByTaxon(paramLong, paramTaxon, paramInteger1, paramInteger2, -1, paramICartCallback);
  }
  
  public void getSearchSuggestions(String paramString, final ICartCallback<AutoSuggestionResponse> paramICartCallback)
  {
    this.mICartRestClient.getProductService().getSearchSuggestions(paramString, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(AutoSuggestionResponse paramAnonymousAutoSuggestionResponse, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousAutoSuggestionResponse);
        }
      }
    });
  }
  
  public void getSpecialProducts(Integer paramInteger1, Integer paramInteger2, int paramInt, final ICartCallback paramICartCallback)
  {
    long l = this.mICartApplication.getStockLocationId();
    String str = getSortByQueryString(paramInt);
    this.mICartRestClient.getProductService().getSpecialProducts(Long.valueOf(l).longValue(), paramInteger1, paramInteger2, str, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(ProductResponse paramAnonymousProductResponse, Response paramAnonymousResponse)
      {
        if ((paramAnonymousProductResponse != null) && (paramAnonymousProductResponse.products != null) && (paramICartCallback != null)) {
          paramICartCallback.onSuccess(paramAnonymousProductResponse);
        }
      }
    });
  }
  
  public void getSpecialProducts(Integer paramInteger1, Integer paramInteger2, ICartCallback paramICartCallback)
  {
    getSpecialProducts(paramInteger1, paramInteger2, -1, paramICartCallback);
  }
  
  public void getSubCategories(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2, final ICartCallback paramICartCallback)
  {
    this.mICartRestClient.getProductService().getTaxons(paramLong1, paramLong2, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(TaxonResponse paramAnonymousTaxonResponse, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousTaxonResponse);
        }
      }
    });
  }
  
  public void saveProduct(Product paramProduct)
  {
    if (!paramProduct.taxonIds.isEmpty())
    {
      Taxon localTaxon = Taxon.findByParent((Long)paramProduct.taxonIds.get(0));
      if (localTaxon != null)
      {
        paramProduct.category = localTaxon.permalink;
        paramProduct.save();
      }
    }
  }
  
  public void saveProduct(final List<Product> paramList)
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext())
        {
          Product localProduct = (Product)localIterator.next();
          ProductManager.this.saveProduct(localProduct);
        }
      }
    }).start();
  }
  
  public void searchProduct(Long paramLong, String paramString, Integer paramInteger1, Integer paramInteger2, final ICartCallback paramICartCallback)
  {
    ProductService localProductService = this.mICartRestClient.getProductService();
    paramString = String.format("*%s*", new Object[] { paramString });
    localProductService.searchProducts(paramLong.longValue(), paramString, paramInteger1, paramInteger2, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(ProductResponse paramAnonymousProductResponse, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousProductResponse);
        }
      }
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/managers/ProductManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */