package com.happyfresh.managers;

import android.app.Activity;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.afollestad.materialdialogs.MaterialDialog;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.callbacks.ICartDialogCallback;
import com.happyfresh.callbacks.ICartRetrofitCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartRestClient;
import com.happyfresh.models.StockLocation;
import com.happyfresh.models.StockLocationResponse;
import com.happyfresh.models.SubDistrict;
import com.happyfresh.models.SubDistrictResponse;
import com.happyfresh.services.LocationService;
import com.happyfresh.utils.DialogColor;
import com.happyfresh.utils.DialogUtils;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class LocationManager
  extends BaseManager
{
  public LocationManager(ICartApplication paramICartApplication)
  {
    super(paramICartApplication);
  }
  
  public void getNearbyStockLocations(Double paramDouble1, Double paramDouble2, String paramString1, boolean paramBoolean, String paramString2, Integer paramInteger1, Integer paramInteger2, final ICartCallback<StockLocationResponse> paramICartCallback)
  {
    this.mICartRestClient.getLocationService().getNearbyStockLocations(paramDouble1, paramDouble2, paramString1, paramBoolean, paramString2, paramInteger1, paramInteger2, Integer.valueOf(1), new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(StockLocationResponse paramAnonymousStockLocationResponse, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousStockLocationResponse);
        }
      }
    });
  }
  
  public void getNearbyStockLocations(String paramString, Integer paramInteger1, Integer paramInteger2, final ICartCallback<StockLocationResponse> paramICartCallback)
  {
    this.mICartRestClient.getLocationService().getStockLocationsByZipCode(paramString, paramInteger1, paramInteger2, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(StockLocationResponse paramAnonymousStockLocationResponse, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousStockLocationResponse);
        }
      }
    });
  }
  
  public void getNearestStockLocations(Long paramLong1, Double paramDouble1, Double paramDouble2, String paramString1, String paramString2, Long paramLong2, final ICartCallback<StockLocationResponse> paramICartCallback)
  {
    this.mICartRestClient.getLocationService().getNearestStockLocations(paramLong1, paramDouble1, paramDouble2, paramString1, paramString2, paramLong2, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(StockLocationResponse paramAnonymousStockLocationResponse, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousStockLocationResponse);
        }
      }
    });
  }
  
  public void getSingleStockLocation(long paramLong, final ICartCallback<StockLocation> paramICartCallback)
  {
    this.mICartRestClient.getLocationService().getSingleStockLocations(Long.valueOf(paramLong), new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(StockLocation paramAnonymousStockLocation, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousStockLocation);
        }
      }
    });
  }
  
  public void getStockLocations(Long paramLong, Integer paramInteger1, Integer paramInteger2, final ICartCallback<StockLocationResponse> paramICartCallback)
  {
    this.mICartRestClient.getLocationService().getStockLocations(paramLong, paramInteger1, paramInteger2, Integer.valueOf(1), new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(StockLocationResponse paramAnonymousStockLocationResponse, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousStockLocationResponse);
        }
      }
    });
  }
  
  public void getSubDistrict(Long paramLong, final ICartCallback<SubDistrict> paramICartCallback)
  {
    this.mICartRestClient.getLocationService().getSubDistrict(paramLong, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(SubDistrict paramAnonymousSubDistrict, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousSubDistrict);
        }
      }
    });
  }
  
  public void getSuggestedLocation(String paramString, Integer paramInteger1, Integer paramInteger2, final ICartCallback<SubDistrictResponse> paramICartCallback)
  {
    this.mICartRestClient.getLocationService().getSuggestedLocations("*" + paramString + "*", paramInteger1, paramInteger2, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(SubDistrictResponse paramAnonymousSubDistrictResponse, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousSubDistrictResponse);
        }
      }
    });
  }
  
  public void locationRequest(double paramDouble1, double paramDouble2, long paramLong, String paramString)
    throws JSONException, UnsupportedEncodingException
  {
    LocationService localLocationService = this.mICartRestClient.getLocationService();
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("lat", paramDouble1);
    localJSONObject.put("lon", paramDouble2);
    localJSONObject.put("sub_district_id", paramLong);
    localJSONObject.put("email", paramString);
    paramString = new JSONObject();
    paramString.put("location_request", localJSONObject);
    localLocationService.locationRequest(new TypedByteArray("application/json", paramString.toString().getBytes("UTF-8")), new ICartRetrofitCallback(this.mICartApplication)
    {
      public void success(Object paramAnonymousObject, Response paramAnonymousResponse) {}
    });
  }
  
  public MaterialDialog showGetNotifedDialog(Activity paramActivity, final double paramDouble1, double paramDouble2, final long paramLong)
  {
    DialogColor localDialogColor = new DialogColor();
    localDialogColor.positiveButtonColor = Integer.valueOf(paramActivity.getResources().getColor(2131492983));
    View localView = LayoutInflater.from(paramActivity).inflate(2130903160, null);
    ICartDialogCallback local8 = new ICartDialogCallback()
    {
      public void onCancel() {}
      
      public void onNegative(MaterialDialog paramAnonymousMaterialDialog) {}
      
      public void onNeutral(MaterialDialog paramAnonymousMaterialDialog) {}
      
      public void onPositive(MaterialDialog paramAnonymousMaterialDialog)
      {
        try
        {
          paramAnonymousMaterialDialog = this.val$emailText.getText().toString();
          if (!TextUtils.isEmpty(paramAnonymousMaterialDialog)) {
            LocationManager.this.locationRequest(paramDouble1, paramLong, this.val$subDisctrictId, paramAnonymousMaterialDialog);
          }
          return;
        }
        catch (JSONException paramAnonymousMaterialDialog)
        {
          Log.e(this.val$activity.getClass().getSimpleName(), paramAnonymousMaterialDialog.getLocalizedMessage());
          return;
        }
        catch (UnsupportedEncodingException paramAnonymousMaterialDialog)
        {
          Log.e(this.val$activity.getClass().getSimpleName(), paramAnonymousMaterialDialog.getLocalizedMessage());
        }
      }
    };
    return DialogUtils.showDialog(paramActivity, paramActivity.getString(2131165362), null, paramActivity.getString(2131165594), paramActivity.getString(2131165293), null, local8, localView, localDialogColor);
  }
  
  public void showLocationOutOfRangeDialog(Activity paramActivity, ICartDialogCallback paramICartDialogCallback)
  {
    DialogColor localDialogColor = new DialogColor();
    localDialogColor.positiveButtonColor = Integer.valueOf(paramActivity.getResources().getColor(2131492983));
    View localView = LayoutInflater.from(paramActivity).inflate(2130903104, null);
    DialogUtils.showDialog(paramActivity, paramActivity.getString(2131165362), null, paramActivity.getString(2131165361), paramActivity.getString(2131165360), null, paramICartDialogCallback, localView, localDialogColor);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/managers/LocationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */