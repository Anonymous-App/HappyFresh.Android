package com.happyfresh.common;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;
import android.util.Base64;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.serializers.DateDeserializer;
import com.happyfresh.services.ConfigService;
import com.happyfresh.services.LocationService;
import com.happyfresh.services.OrderGeneralService;
import com.happyfresh.services.OrderService;
import com.happyfresh.services.ProductService;
import com.happyfresh.services.ReplacementService;
import com.happyfresh.services.ShoppingListService;
import com.happyfresh.services.UserService;
import com.squareup.okhttp.OkHttpClient;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import retrofit.RequestInterceptor;
import retrofit.RequestInterceptor.RequestFacade;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

public class ICartRestClient
{
  public static final ICartRestClient INSTANCE = new ICartRestClient();
  private ConfigService mConfigService;
  private ICartApplication mICartApplication;
  private LocationService mLocationService;
  private OrderGeneralService mOrderGeneralService;
  private RestAdapter mOrderRestAdapter;
  private OrderService mOrderService;
  private ProductService mProductService;
  private ReplacementService mReplacementService;
  private RestAdapter mRestAdapter;
  private ShoppingListService mShoppingListService;
  private UserService mUserService;
  
  private RestAdapter.Builder buildDefaultBuilder()
  {
    return new RestAdapter.Builder().setEndpoint(getBaseUrl()).setClient(buildOkClient(buildOkHttpClient())).setConverter(buildGsonConverter(buildGson()));
  }
  
  private Gson buildGson()
  {
    return new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).excludeFieldsWithModifiers(new int[] { 16, 128, 8 }).create();
  }
  
  private GsonConverter buildGsonConverter(Gson paramGson)
  {
    return new GsonConverter(paramGson);
  }
  
  private OkClient buildOkClient(OkHttpClient paramOkHttpClient)
  {
    return new OkClient(paramOkHttpClient);
  }
  
  private OkHttpClient buildOkHttpClient()
  {
    OkHttpClient localOkHttpClient = new OkHttpClient();
    localOkHttpClient.setReadTimeout(60000L, TimeUnit.MILLISECONDS);
    localOkHttpClient.setConnectTimeout(60000L, TimeUnit.MILLISECONDS);
    return localOkHttpClient;
  }
  
  private RequestInterceptor buildRequestInterceptor(final boolean paramBoolean)
  {
    new RequestInterceptor()
    {
      public void intercept(RequestInterceptor.RequestFacade paramAnonymousRequestFacade)
      {
        if (ICartRestClient.this.mICartApplication.isLoggedIn()) {
          paramAnonymousRequestFacade.addHeader("X-Spree-Token", ICartRestClient.this.getUserToken());
        }
        for (;;)
        {
          if (paramBoolean) {
            paramAnonymousRequestFacade.addHeader("X-Spree-Order-Token", ICartRestClient.this.getOrderToken());
          }
          paramAnonymousRequestFacade.addHeader("Content-Type", "application/json");
          paramAnonymousRequestFacade.addHeader("Locale", ICartRestClient.this.mICartApplication.getSharedPreferencesManager().getLocale().getLanguage().toUpperCase());
          paramAnonymousRequestFacade.addHeader("Country", ICartRestClient.this.mICartApplication.getCountryCode());
          paramAnonymousRequestFacade.addHeader("X-Happy-Client-Type", ICartRestClient.this.getClientType());
          paramAnonymousRequestFacade.addHeader("X-Happy-Client-Version", ICartRestClient.this.getClientVersion());
          paramAnonymousRequestFacade.addHeader("X-Happy-Client-Build", ICartRestClient.this.getClientBuild());
          return;
          paramAnonymousRequestFacade.addHeader("X-Spree-Client-Token", ICartRestClient.this.getClientToken());
        }
      }
    };
  }
  
  private static String getBaseUrl()
  {
    return "https://api.happyfresh.com/api/";
  }
  
  private String getClientBuild()
  {
    String str = null;
    PackageInfo localPackageInfo = getPackageInfo();
    if (localPackageInfo != null) {
      str = String.valueOf(localPackageInfo.versionCode);
    }
    return str;
  }
  
  private String getClientToken()
  {
    return "0115f406e71219ec9ea58e2eaaa4480ef966bdc42e245ec4bf601b23f07bd48e";
  }
  
  private String getClientType()
  {
    return "android";
  }
  
  private String getClientVersion()
  {
    String str = null;
    PackageInfo localPackageInfo = getPackageInfo();
    if (localPackageInfo != null) {
      str = localPackageInfo.versionName;
    }
    return str;
  }
  
  private RestAdapter getOrderRestAdapter()
  {
    if (this.mOrderRestAdapter == null) {
      this.mOrderRestAdapter = buildDefaultBuilder().setRequestInterceptor(buildRequestInterceptor(true)).build();
    }
    return this.mOrderRestAdapter;
  }
  
  private String getOrderToken()
  {
    return PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).getString("ICartConstant.KEYS.ORDER_TOKEN", null);
  }
  
  private PackageInfo getPackageInfo()
  {
    try
    {
      PackageInfo localPackageInfo = this.mICartApplication.getPackageManager().getPackageInfo(this.mICartApplication.getPackageName(), 0);
      return localPackageInfo;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    return null;
  }
  
  private RestAdapter getRestAdapter()
  {
    if (this.mRestAdapter == null) {
      this.mRestAdapter = buildDefaultBuilder().setRequestInterceptor(buildRequestInterceptor(false)).build();
    }
    return this.mRestAdapter;
  }
  
  private String getUserToken()
  {
    return PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).getString("ICartConstant.KEYS.USER_TOKEN", null);
  }
  
  public RestAdapter buildLoginAdapter(String paramString1, String paramString2)
  {
    paramString1 = new RequestInterceptor()
    {
      public void intercept(RequestInterceptor.RequestFacade paramAnonymousRequestFacade)
      {
        paramAnonymousRequestFacade.addHeader("Authorization", "Basic " + this.val$base64Credentials);
        paramAnonymousRequestFacade.addHeader("X-Spree-Client-Token", ICartRestClient.this.getClientToken());
        paramAnonymousRequestFacade.addHeader("X-Spree-Order-Token", ICartRestClient.this.getOrderToken());
        paramAnonymousRequestFacade.addHeader("Locale", ICartRestClient.this.mICartApplication.getSharedPreferencesManager().getLocale().getLanguage().toUpperCase());
        paramAnonymousRequestFacade.addHeader("Country", ICartRestClient.this.mICartApplication.getCountryCode());
        paramAnonymousRequestFacade.addHeader("X-Happy-Client-Type", ICartRestClient.this.getClientType());
        paramAnonymousRequestFacade.addHeader("X-Happy-Client-Version", ICartRestClient.this.getClientVersion());
        paramAnonymousRequestFacade.addHeader("X-Happy-Client-Build", ICartRestClient.this.getClientBuild());
      }
    };
    return buildDefaultBuilder().setRequestInterceptor(paramString1).build();
  }
  
  public ConfigService getConfigService()
  {
    if (this.mConfigService == null) {
      this.mConfigService = ((ConfigService)getRestAdapter().create(ConfigService.class));
    }
    return this.mConfigService;
  }
  
  public LocationService getLocationService()
  {
    if (this.mLocationService == null) {
      this.mLocationService = ((LocationService)getRestAdapter().create(LocationService.class));
    }
    return this.mLocationService;
  }
  
  public OrderGeneralService getOrderGeneralService()
  {
    if (this.mOrderGeneralService == null) {
      this.mOrderGeneralService = ((OrderGeneralService)getRestAdapter().create(OrderGeneralService.class));
    }
    return this.mOrderGeneralService;
  }
  
  public OrderService getOrderService()
  {
    if (this.mOrderService == null) {
      this.mOrderService = ((OrderService)getOrderRestAdapter().create(OrderService.class));
    }
    return this.mOrderService;
  }
  
  public ProductService getProductService()
  {
    if (this.mProductService == null) {
      this.mProductService = ((ProductService)getRestAdapter().create(ProductService.class));
    }
    return this.mProductService;
  }
  
  public ReplacementService getReplacementService()
  {
    if (this.mReplacementService == null) {
      this.mReplacementService = ((ReplacementService)getRestAdapter().create(ReplacementService.class));
    }
    return this.mReplacementService;
  }
  
  public ShoppingListService getShoppingListService()
  {
    if (this.mShoppingListService == null) {
      this.mShoppingListService = ((ShoppingListService)getRestAdapter().create(ShoppingListService.class));
    }
    return this.mShoppingListService;
  }
  
  public UserService getUserService()
  {
    if (this.mUserService == null) {
      this.mUserService = ((UserService)getRestAdapter().create(UserService.class));
    }
    return this.mUserService;
  }
  
  public void reset()
  {
    this.mRestAdapter = null;
    this.mOrderRestAdapter = null;
    this.mLocationService = null;
    this.mOrderService = null;
    this.mOrderGeneralService = null;
    this.mProductService = null;
    this.mUserService = null;
    this.mShoppingListService = null;
  }
  
  public void setICartApplication(ICartApplication paramICartApplication)
  {
    this.mICartApplication = paramICartApplication;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/common/ICartRestClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */