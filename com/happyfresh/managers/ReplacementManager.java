package com.happyfresh.managers;

import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.callbacks.ICartRetrofitCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartRestClient;
import com.happyfresh.models.LineItem;
import com.happyfresh.models.Product;
import com.happyfresh.models.Replacement;
import com.happyfresh.models.Variant;
import com.happyfresh.services.ReplacementService;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class ReplacementManager
  extends BaseManager
{
  private static final String TAG = OrderManager.class.getSimpleName();
  
  public ReplacementManager(ICartApplication paramICartApplication)
  {
    super(paramICartApplication);
  }
  
  public void createReplacement(LineItem paramLineItem, Product paramProduct, final ICartCallback<Replacement> paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("variant_id", ((Variant)paramProduct.variants.get(0)).remoteId);
    localJSONObject.put("priority", 1);
    paramProduct = new TypedByteArray("application/json", localJSONObject.toString().getBytes("UTF-8"));
    this.mICartRestClient.getReplacementService().createReplacement(Long.valueOf(paramLineItem.remoteId), paramProduct, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Replacement paramAnonymousReplacement, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousReplacement);
        }
      }
    });
  }
  
  public void deleteReplacement(LineItem paramLineItem, final ICartCallback<Object> paramICartCallback)
  {
    if (paramLineItem.getReplacement() == null) {
      return;
    }
    this.mICartRestClient.getReplacementService().deleteReplacement(Long.valueOf(paramLineItem.getReplacement().remoteId), new ICartRetrofitCallback(this.mICartApplication)
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
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousObject);
        }
      }
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/managers/ReplacementManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */