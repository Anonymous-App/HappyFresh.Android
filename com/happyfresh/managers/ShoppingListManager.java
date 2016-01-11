package com.happyfresh.managers;

import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.callbacks.ICartRetrofitCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartRestClient;
import com.happyfresh.models.RecommendedListResponse;
import com.happyfresh.models.ShoppingList;
import com.happyfresh.models.ShoppingListItem;
import com.happyfresh.models.ShoppingListResponse;
import com.happyfresh.models.Variant;
import com.happyfresh.services.ShoppingListService;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class ShoppingListManager
  extends BaseManager
{
  public ShoppingListManager(ICartApplication paramICartApplication)
  {
    super(paramICartApplication);
  }
  
  public void addToFavorite(Variant paramVariant, final ICartCallback paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    final Object localObject = new JSONObject();
    ((JSONObject)localObject).put("variant_id", paramVariant.remoteId);
    paramVariant = new TypedByteArray("application/json", ((JSONObject)localObject).toString().getBytes("UTF-8"));
    localObject = this.mICartApplication.getFavoriteList().id;
    this.mICartRestClient.getShoppingListService().addShoppingListItem((Long)localObject, paramVariant, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(ShoppingListItem paramAnonymousShoppingListItem, Response paramAnonymousResponse)
      {
        if (paramAnonymousShoppingListItem != null)
        {
          paramAnonymousShoppingListItem.shoppingListId = localObject;
          paramAnonymousShoppingListItem.variantId = paramAnonymousShoppingListItem.variant.remoteId;
          paramAnonymousShoppingListItem.save();
        }
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousShoppingListItem);
        }
      }
    });
  }
  
  public void deleteFromFavorite(final Variant paramVariant, final ICartCallback paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    Long localLong = this.mICartApplication.getFavoriteList().id;
    this.mICartRestClient.getShoppingListService().deleteShoppingListItem(localLong, Long.valueOf(paramVariant.remoteId), new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Object paramAnonymousObject, Response paramAnonymousResponse)
      {
        paramAnonymousResponse = ShoppingListItem.findShoppingListItemByVariantId(Long.valueOf(paramVariant.remoteId));
        if (paramAnonymousResponse != null) {
          paramAnonymousResponse.delete();
        }
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousObject);
        }
      }
    });
  }
  
  public void getFavoriteList(final ICartCallback paramICartCallback)
  {
    long l = this.mICartApplication.getStockLocationId();
    this.mICartRestClient.getShoppingListService().getFavoriteList(Long.valueOf(l), new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(ShoppingListResponse paramAnonymousShoppingListResponse, Response paramAnonymousResponse)
      {
        ShoppingListManager.this.mICartApplication.deleteFavoriteList();
        ShoppingList.deleteFavoriteList();
        if (paramAnonymousShoppingListResponse != null)
        {
          paramAnonymousResponse = paramAnonymousShoppingListResponse.shoppingList;
          if (paramAnonymousResponse != null)
          {
            paramAnonymousResponse.save();
            Iterator localIterator = paramAnonymousResponse.items.iterator();
            while (localIterator.hasNext())
            {
              ShoppingListItem localShoppingListItem = (ShoppingListItem)localIterator.next();
              localShoppingListItem.shoppingListId = paramAnonymousResponse.id;
              localShoppingListItem.variantId = localShoppingListItem.variant.remoteId;
              localShoppingListItem.save();
            }
            if (paramICartCallback != null) {
              paramICartCallback.onSuccess(paramAnonymousShoppingListResponse);
            }
          }
        }
      }
    });
  }
  
  public void getRecommendedList(final ICartCallback paramICartCallback)
  {
    long l = this.mICartApplication.getStockLocationId();
    this.mICartRestClient.getShoppingListService().getRecommendedList(Long.valueOf(l), new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        paramICartCallback.onFailure(paramAnonymousRetrofitError);
      }
      
      public void success(RecommendedListResponse paramAnonymousRecommendedListResponse, Response paramAnonymousResponse)
      {
        if (paramAnonymousRecommendedListResponse != null) {
          paramICartCallback.onSuccess(paramAnonymousRecommendedListResponse.shoppingLists);
        }
      }
    });
  }
  
  public void getShoppingListItem(Long paramLong, final ICartCallback paramICartCallback)
  {
    long l = this.mICartApplication.getStockLocationId();
    this.mICartRestClient.getShoppingListService().getShoppingListItem(paramLong, Long.valueOf(l), new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(ShoppingListResponse paramAnonymousShoppingListResponse, Response paramAnonymousResponse)
      {
        if ((paramAnonymousShoppingListResponse != null) && (paramICartCallback != null)) {
          paramICartCallback.onSuccess(paramAnonymousShoppingListResponse.products);
        }
      }
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/managers/ShoppingListManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */