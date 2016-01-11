package com.happyfresh.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.activeandroid.ActiveAndroid;
import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.A4SApplication;
import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustAttribution;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.OnAttributionChangedListener;
import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.happyfresh.activities.IntroductionActivity;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.customs.ICartEventBuilder;
import com.happyfresh.managers.ApplicationStateManager;
import com.happyfresh.managers.ConfigManager;
import com.happyfresh.managers.OrderManager;
import com.happyfresh.managers.ProductManager;
import com.happyfresh.managers.ReplacementManager;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.managers.ShoppingListManager;
import com.happyfresh.managers.UserManager;
import com.happyfresh.models.AddressResponse;
import com.happyfresh.models.EditingCartInProgress;
import com.happyfresh.models.LineItem;
import com.happyfresh.models.Order;
import com.happyfresh.models.OrderResponse;
import com.happyfresh.models.ShoppingList;
import com.happyfresh.models.SubDistrict;
import com.happyfresh.models.User;
import com.happyfresh.utils.IANATimeZoneUtils;
import com.happyfresh.utils.LogUtils;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.mpmetrics.MixpanelAPI.People;
import com.optimizely.Optimizely;
import com.optimizely.integrations.universalanalytics.OptimizelyUniversalAnalyticsIntegration;
import com.parse.Parse;
import com.parse.ParseInstallation;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class ICartApplication
  extends A4SApplication
{
  private static ICartApplication INSTANCE;
  private static final String TAG = ICartApplication.class.getSimpleName();
  public Order currentOrder;
  public SubDistrict currentSubDistrict;
  private A4S mAccengage;
  private String mAndroidAdsId;
  private ApplicationStateManager mApplicationStateManager;
  private ConfigManager mConfigManager;
  private String mCountryCode;
  private User mCurrentUser;
  private EditingCartInProgress mEditingCartInProgress;
  private ShoppingList mFavoriteList;
  private boolean mFavouritesNeedToBeSynced;
  private ICartNotification mICartNotification;
  private boolean mIsCurrentOrderSync = false;
  private boolean mIsCurrentSubDistrictSync = false;
  private boolean mIsShippingAddressSync = false;
  private com.happyfresh.managers.LocationManager mLocationManager;
  private com.happyfresh.managers.LoginManager mLoginManager;
  private MixpanelAPI mMixpanel;
  private List<Long> mOrderIdAddedToCartList = new ArrayList();
  private List<Order> mOrderList;
  private boolean mOrderListNeedToBeSynced;
  private OrderManager mOrderManager;
  private boolean mOrderStatusNeedToBeSynced;
  private ProductManager mProductManager;
  private ReplacementManager mReplacementManager;
  private SharedPreferencesManager mSharedPreferencesManager;
  private ShoppingListManager mShoppingListManager;
  private int mSortByCriteria;
  private Activity mTempActivity;
  private String mTemporaryAccengageDeviceId;
  private List<LineItem> mTemporaryLineItems;
  private Tracker mTracker;
  private UserManager mUserManager;
  public List<LineItem> outOfStockItems = new ArrayList();
  
  private void deleteAccengageDeviceId()
  {
    String str = getSharedPreferencesManager().getAccengageDeviceId();
    if (str != null) {}
    try
    {
      getUserManager().deleteAccengageDeviceId(str, null);
      return;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localUnsupportedEncodingException.printStackTrace();
    }
  }
  
  public static ICartApplication get()
  {
    return INSTANCE;
  }
  
  public static ICartApplication get(Activity paramActivity)
  {
    return INSTANCE;
  }
  
  public static ICartApplication get(DialogFragment paramDialogFragment)
  {
    if (paramDialogFragment.getActivity() == null) {
      return null;
    }
    return (ICartApplication)paramDialogFragment.getActivity().getApplication();
  }
  
  public static ICartApplication get(Fragment paramFragment)
  {
    return INSTANCE;
  }
  
  private void initAdjust()
  {
    AdjustConfig localAdjustConfig = new AdjustConfig(this, "nqqhdhdtnk3r", "production");
    localAdjustConfig.setOnAttributionChangedListener(new OnAttributionChangedListener()
    {
      private void insertJsonProperty(JSONObject paramAnonymousJSONObject, String paramAnonymousString1, String paramAnonymousString2)
      {
        if (paramAnonymousString2 != null) {}
        try
        {
          paramAnonymousJSONObject.put(paramAnonymousString1, paramAnonymousString2);
          return;
        }
        catch (JSONException paramAnonymousJSONObject) {}
      }
      
      public void onAttributionChanged(AdjustAttribution paramAnonymousAdjustAttribution)
      {
        Object localObject = new JSONObject();
        insertJsonProperty((JSONObject)localObject, "[Adjust]Network", paramAnonymousAdjustAttribution.network);
        insertJsonProperty((JSONObject)localObject, "[Adjust]Campaign", paramAnonymousAdjustAttribution.campaign);
        insertJsonProperty((JSONObject)localObject, "[Adjust]Adgroup", paramAnonymousAdjustAttribution.adgroup);
        insertJsonProperty((JSONObject)localObject, "[Adjust]Creative", paramAnonymousAdjustAttribution.creative);
        ICartApplication.this.getMixpanel().registerSuperProperties((JSONObject)localObject);
        localObject = new Bundle();
        ((Bundle)localObject).putString("[Adjust]Network", paramAnonymousAdjustAttribution.network);
        ((Bundle)localObject).putString("[Adjust]Campaign", paramAnonymousAdjustAttribution.campaign);
        ((Bundle)localObject).putString("[Adjust]Adgroup", paramAnonymousAdjustAttribution.adgroup);
        ((Bundle)localObject).putString("[Adjust]Creative", paramAnonymousAdjustAttribution.creative);
        ICartApplication.this.getAccengage().updateDeviceInfo((Bundle)localObject);
      }
    });
    Adjust.onCreate(localAdjustConfig);
  }
  
  private void initOptimizely()
  {
    Optimizely.setEditGestureEnabled(false);
    Optimizely.startOptimizelyWithAPIToken("AAM7hIkA4bNJ--VCggeeeIzyYiPxXl8U~3015940220", this);
  }
  
  private void logoutFacebook()
  {
    com.facebook.login.LoginManager localLoginManager = com.facebook.login.LoginManager.getInstance();
    if (localLoginManager != null) {
      localLoginManager.logOut();
    }
  }
  
  private void setAccengageDeviceId()
  {
    getAccengage().getA4SId(new A4S.Callback()
    {
      public void onError(int paramAnonymousInt, String paramAnonymousString)
      {
        LogUtils.LOG("Accengage onError device id: " + paramAnonymousString);
      }
      
      public void onResult(final String paramAnonymousString)
      {
        LogUtils.LOG("Accengage onResult device id: " + paramAnonymousString);
        ICartApplication.access$402(ICartApplication.this, paramAnonymousString);
        try
        {
          ICartApplication.this.getUserManager().setAccengageDeviceId(paramAnonymousString, new ICartCallback(ICartApplication.TAG)
          {
            public void onSuccess(Object paramAnonymous2Object)
            {
              ICartApplication.this.getSharedPreferencesManager().saveAccengageDeviceId(paramAnonymousString);
            }
          });
          return;
        }
        catch (JSONException paramAnonymousString)
        {
          paramAnonymousString.printStackTrace();
          return;
        }
        catch (UnsupportedEncodingException paramAnonymousString)
        {
          paramAnonymousString.printStackTrace();
        }
      }
    });
  }
  
  public void addOrderIdAddedToCartList(Long paramLong)
  {
    this.mOrderIdAddedToCartList.add(paramLong);
  }
  
  public void clearOrderIdAddedToCartList()
  {
    this.mOrderIdAddedToCartList.clear();
  }
  
  public void createOrder()
  {
    getOrderManager().createOrder(new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        ICartApplication.access$202(ICartApplication.this, false);
      }
      
      public void onSuccess(Object paramAnonymousObject)
      {
        paramAnonymousObject = (Order)paramAnonymousObject;
        ICartApplication.this.currentOrder = ((Order)paramAnonymousObject);
        ICartApplication.this.getICartNotification().raiseCurrentOrderSetEvent(this, (Order)paramAnonymousObject);
        ICartApplication.this.setMixpanelGuestUser();
        ICartApplication.this.setMixpanelPushNotifications();
        ICartApplication.this.getSharedPreferencesManager().saveOrderToken((Order)paramAnonymousObject);
        ICartApplication.access$202(ICartApplication.this, false);
      }
    });
  }
  
  public void deleteFavoriteList()
  {
    this.mFavoriteList = null;
  }
  
  public void finishTemporaryActivity()
  {
    if (this.mTempActivity != null)
    {
      this.mTempActivity.finish();
      this.mTempActivity = null;
    }
  }
  
  public A4S getAccengage()
  {
    if (this.mAccengage == null) {
      this.mAccengage = A4S.get(this);
    }
    return this.mAccengage;
  }
  
  public Address getAddressByLastKnownLocation()
  {
    Geocoder localGeocoder = new Geocoder(this);
    android.location.LocationManager localLocationManager = (android.location.LocationManager)getSystemService("location");
    Object localObject1 = null;
    Iterator localIterator = localLocationManager.getAllProviders().iterator();
    Object localObject2;
    do
    {
      do
      {
        localObject2 = localObject1;
        if (!localIterator.hasNext()) {
          break;
        }
        localObject2 = (String)localIterator.next();
      } while ((ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) || (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") != 0));
      localObject2 = localLocationManager.getLastKnownLocation((String)localObject2);
      localObject1 = localObject2;
    } while (localObject2 == null);
    if (localObject2 != null) {
      try
      {
        localObject1 = localGeocoder.getFromLocation(((Location)localObject2).getLatitude(), ((Location)localObject2).getLongitude(), 100).iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (Address)((Iterator)localObject1).next();
          if (localObject2 != null) {
            return (Address)localObject2;
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    return null;
  }
  
  public void getAndroidAdsId(final ICartCallback<String> paramICartCallback)
  {
    if (!TextUtils.isEmpty(this.mAndroidAdsId))
    {
      if (paramICartCallback != null) {
        paramICartCallback.onSuccess(this.mAndroidAdsId);
      }
      return;
    }
    new AsyncTask()
    {
      protected String doInBackground(Void... paramAnonymousVarArgs)
      {
        paramAnonymousVarArgs = null;
        try
        {
          AdvertisingIdClient.Info localInfo = AdvertisingIdClient.getAdvertisingIdInfo(ICartApplication.this.getApplicationContext());
          paramAnonymousVarArgs = localInfo;
          return Settings.Secure.getString(ICartApplication.this.getApplicationContext().getContentResolver(), "android_id");
        }
        catch (GooglePlayServicesNotAvailableException localGooglePlayServicesNotAvailableException)
        {
          for (;;)
          {
            try
            {
              paramAnonymousVarArgs = paramAnonymousVarArgs.getId();
              return paramAnonymousVarArgs;
            }
            catch (NullPointerException paramAnonymousVarArgs) {}
            localGooglePlayServicesNotAvailableException = localGooglePlayServicesNotAvailableException;
            localGooglePlayServicesNotAvailableException.printStackTrace();
          }
        }
        catch (GooglePlayServicesRepairableException localGooglePlayServicesRepairableException)
        {
          for (;;)
          {
            localGooglePlayServicesRepairableException.printStackTrace();
          }
        }
        catch (IOException localIOException)
        {
          for (;;)
          {
            localIOException.printStackTrace();
          }
        }
      }
      
      protected void onPostExecute(String paramAnonymousString)
      {
        ICartApplication.access$602(ICartApplication.this, paramAnonymousString);
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousString);
        }
      }
    }.execute(new Void[0]);
  }
  
  public ApplicationStateManager getApplicationStateManager()
  {
    if (this.mApplicationStateManager == null) {
      this.mApplicationStateManager = new ApplicationStateManager(this);
    }
    return this.mApplicationStateManager;
  }
  
  public ConfigManager getConfigManager()
  {
    if (this.mConfigManager == null) {
      this.mConfigManager = new ConfigManager(this);
    }
    return this.mConfigManager;
  }
  
  public String getCountryCode()
  {
    if (this.mCountryCode == null)
    {
      this.mCountryCode = getSharedPreferencesManager().getStockLocationCountryCode();
      if (this.mCountryCode == null)
      {
        Geocoder localGeocoder = new Geocoder(this);
        android.location.LocationManager localLocationManager = (android.location.LocationManager)getSystemService("location");
        Object localObject1 = null;
        Iterator localIterator = localLocationManager.getAllProviders().iterator();
        Object localObject3;
        do
        {
          do
          {
            localObject3 = localObject1;
            if (!localIterator.hasNext()) {
              break;
            }
            localObject3 = (String)localIterator.next();
          } while ((ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) || (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") != 0));
          localObject3 = localLocationManager.getLastKnownLocation((String)localObject3);
          localObject1 = localObject3;
        } while (localObject3 == null);
        if (localObject3 != null) {
          try
          {
            localObject1 = localGeocoder.getFromLocation(((Location)localObject3).getLatitude(), ((Location)localObject3).getLongitude(), 100).iterator();
            while (((Iterator)localObject1).hasNext())
            {
              localObject3 = (Address)((Iterator)localObject1).next();
              if ((localObject3 != null) && (((Address)localObject3).getCountryCode() != null)) {
                this.mCountryCode = ((Address)localObject3).getCountryCode().toUpperCase();
              }
            }
            if (this.mCountryCode != null) {
              break label213;
            }
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
          }
        }
      }
      Object localObject2 = IANATimeZoneUtils.getCountry(getApplicationContext());
      if (localObject2 != null) {
        this.mCountryCode = ((String)localObject2);
      }
      label213:
      if (this.mCountryCode == null)
      {
        localObject2 = (TelephonyManager)getSystemService("phone");
        if (!TextUtils.isEmpty(((TelephonyManager)localObject2).getNetworkCountryIso())) {
          this.mCountryCode = ((TelephonyManager)localObject2).getNetworkCountryIso().toUpperCase();
        }
      }
      if (this.mCountryCode == null) {
        this.mCountryCode = getResources().getConfiguration().locale.getCountry();
      }
    }
    return this.mCountryCode;
  }
  
  public String getCountryFromTZData()
  {
    return IANATimeZoneUtils.getCountry(getApplicationContext());
  }
  
  public Locale getCurrentLocale()
  {
    Locale localLocale2 = Locale.getDefault();
    Locale localLocale1 = localLocale2;
    if (!localLocale2.getCountry().matches("[Mm][Yy]|[Tt][Hh]|[Ii][Dd]|[Tt][Ww]")) {
      localLocale1 = Locale.ENGLISH;
    }
    return localLocale1;
  }
  
  public User getCurrentUser()
  {
    if (this.mCurrentUser == null) {
      this.mCurrentUser = User.getCurrentUser();
    }
    return this.mCurrentUser;
  }
  
  public EditingCartInProgress getEditingCartInProgress()
  {
    return this.mEditingCartInProgress;
  }
  
  public ShoppingList getFavoriteList()
  {
    if (this.mFavoriteList == null) {
      this.mFavoriteList = ShoppingList.findFavoriteList();
    }
    return this.mFavoriteList;
  }
  
  public ICartNotification getICartNotification()
  {
    if (this.mICartNotification == null) {
      this.mICartNotification = new ICartNotification();
    }
    return this.mICartNotification;
  }
  
  public Locale getLocaleByCountryCode(String paramString)
  {
    Locale localLocale = null;
    if ("my".equalsIgnoreCase(paramString)) {
      localLocale = new Locale("ms", "MY");
    }
    for (;;)
    {
      paramString = localLocale;
      if (localLocale == null) {
        paramString = Locale.getDefault();
      }
      return paramString;
      if ("id".equalsIgnoreCase(paramString)) {
        localLocale = new Locale("in", "ID");
      } else if ("tw".equalsIgnoreCase(paramString)) {
        localLocale = new Locale("zh", "TW");
      } else if ("th".equalsIgnoreCase(paramString)) {
        localLocale = new Locale("th", "TH");
      }
    }
  }
  
  public com.happyfresh.managers.LocationManager getLocationManager()
  {
    if (this.mLocationManager == null) {
      this.mLocationManager = new com.happyfresh.managers.LocationManager(this);
    }
    return this.mLocationManager;
  }
  
  public com.happyfresh.managers.LoginManager getLoginManager()
  {
    if (this.mLoginManager == null) {
      this.mLoginManager = new com.happyfresh.managers.LoginManager(this);
    }
    return this.mLoginManager;
  }
  
  public MixpanelAPI getMixpanel()
  {
    if (this.mMixpanel == null) {
      this.mMixpanel = MixpanelAPI.getInstance(getApplicationContext(), "c4ca44d6c794acf2dca4e1cefd127b11");
    }
    return this.mMixpanel;
  }
  
  public NumberFormat getNumberFormatter()
  {
    return getNumberFormatter(getSharedPreferencesManager().getStockLocationCountryCode());
  }
  
  public NumberFormat getNumberFormatter(String paramString)
  {
    NumberFormat localNumberFormat = NumberFormat.getCurrencyInstance(getLocaleByCountryCode(paramString));
    if (paramString.equalsIgnoreCase("MY"))
    {
      localNumberFormat.setMinimumFractionDigits(2);
      return localNumberFormat;
    }
    localNumberFormat.setMinimumFractionDigits(0);
    return localNumberFormat;
  }
  
  public List<Order> getOrderList()
  {
    if (this.mOrderList == null) {
      this.mOrderList = new ArrayList();
    }
    return this.mOrderList;
  }
  
  public OrderManager getOrderManager()
  {
    if (this.mOrderManager == null) {
      this.mOrderManager = new OrderManager(this);
    }
    return this.mOrderManager;
  }
  
  public String getOrderNumber()
  {
    return getSharedPreferencesManager().getOrderNumber();
  }
  
  public String getParseUserChannelName()
  {
    User localUser = getCurrentUser();
    if (localUser != null) {
      return "user_" + localUser.remoteId;
    }
    return null;
  }
  
  public ProductManager getProductManager()
  {
    if (this.mProductManager == null) {
      this.mProductManager = new ProductManager(this);
    }
    return this.mProductManager;
  }
  
  public ReplacementManager getReplacementManager()
  {
    if (this.mReplacementManager == null) {
      this.mReplacementManager = new ReplacementManager(this);
    }
    return this.mReplacementManager;
  }
  
  public SharedPreferencesManager getSharedPreferencesManager()
  {
    return this.mSharedPreferencesManager;
  }
  
  public void getShippingAddress()
  {
    if (this.mIsShippingAddressSync) {}
    User localUser;
    do
    {
      return;
      localUser = getCurrentUser();
    } while ((localUser == null) && (localUser.getAddresses().size() > 0));
    this.mIsShippingAddressSync = true;
    getUserManager().getMyAddresses("shipping", null, Integer.valueOf(1000), new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        ICartApplication.access$102(ICartApplication.this, false);
      }
      
      public void onSuccess(AddressResponse paramAnonymousAddressResponse)
      {
        paramAnonymousAddressResponse = paramAnonymousAddressResponse.addresses;
        if (ICartApplication.this.mCurrentUser == null) {
          ICartApplication.access$002(ICartApplication.this, ICartApplication.this.getCurrentUser());
        }
        if (ICartApplication.this.mCurrentUser == null)
        {
          ICartApplication.access$102(ICartApplication.this, false);
          return;
        }
        ICartApplication.this.mCurrentUser.setAddresses(paramAnonymousAddressResponse);
        ICartApplication.this.getICartNotification().raiseCurrentUserAddressSetEvent(this, paramAnonymousAddressResponse);
        ICartApplication.access$102(ICartApplication.this, false);
      }
    });
  }
  
  public ShoppingListManager getShoppingListManager()
  {
    if (this.mShoppingListManager == null) {
      this.mShoppingListManager = new ShoppingListManager(this);
    }
    return this.mShoppingListManager;
  }
  
  public int getSortByCriteria()
  {
    return this.mSortByCriteria;
  }
  
  public long getStockLocationId()
  {
    return getSharedPreferencesManager().getStockLocationId();
  }
  
  public SubDistrict getSubDistrictByLatLon(double paramDouble1, double paramDouble2)
  {
    try
    {
      SubDistrict localSubDistrict = new SubDistrict();
      Iterator localIterator = new Geocoder(this, getCurrentLocale()).getFromLocation(paramDouble1, paramDouble2, 100).iterator();
      boolean bool;
      do
      {
        do
        {
          if (!localIterator.hasNext()) {
            break;
          }
          Object localObject = (Address)localIterator.next();
          String str = ((Address)localObject).getPostalCode();
          if (!TextUtils.isEmpty(str)) {
            localSubDistrict.zipcode = str;
          }
          localObject = ((Address)localObject).getSubLocality();
          if ((localObject != null) && (!((String)localObject).matches("[0-9]+"))) {
            localSubDistrict.name = ((String)localObject);
          }
        } while (TextUtils.isEmpty(localSubDistrict.name));
        bool = TextUtils.isEmpty(localSubDistrict.zipcode);
      } while (bool);
      return localSubDistrict;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return null;
  }
  
  public List<LineItem> getTemporaryLineItems()
  {
    return this.mTemporaryLineItems;
  }
  
  public Tracker getTracker()
  {
    if (this.mTracker == null)
    {
      GoogleAnalytics localGoogleAnalytics = GoogleAnalytics.getInstance(this);
      localGoogleAnalytics.setDryRun(false);
      localGoogleAnalytics.getLogger().setLogLevel(0);
      this.mTracker = localGoogleAnalytics.newTracker("UA-59442508-3");
      this.mTracker.enableAdvertisingIdCollection(true);
    }
    return this.mTracker;
  }
  
  public UserManager getUserManager()
  {
    if (this.mUserManager == null) {
      this.mUserManager = new UserManager(this);
    }
    return this.mUserManager;
  }
  
  public boolean hasOrderIdBeenAddedToCart(Long paramLong)
  {
    return this.mOrderIdAddedToCartList.contains(paramLong);
  }
  
  public boolean isFavouritesNeedToBeSynced()
  {
    return this.mFavouritesNeedToBeSynced;
  }
  
  public boolean isLoggedIn()
  {
    return getSharedPreferencesManager().isLoggedIn();
  }
  
  public boolean isOrderListNeedToBeSynced()
  {
    return this.mOrderListNeedToBeSynced;
  }
  
  public boolean isOrderStatusNeedToBeSynced()
  {
    return this.mOrderStatusNeedToBeSynced;
  }
  
  public void onApplicationCreate()
  {
    super.onApplicationCreate();
    INSTANCE = this;
    this.mSharedPreferencesManager = new SharedPreferencesManager(this);
    this.mEditingCartInProgress = new EditingCartInProgress();
    Fabric.with(this, new Kit[] { new Crashlytics() });
    ActiveAndroid.initialize(this);
    Parse.initialize(this, "GoSBPiiOHZZEJ2t9VBtvzHZfSFUlebnxp7ejn58t", "kNX4FdpgdmUD6tgzSL6rjUlQ7fGinIdyT2g4z6Qk");
    setMixpanelUser();
    setMixpanelSuperProperties();
    setMixpanelPushNotifications();
    setAccengageUser();
    setAccengageProperties();
    subscribeParseChannels();
    getCountryFromTZData();
    this.mTracker = getTracker();
    initOptimizely();
    OptimizelyUniversalAnalyticsIntegration.setTracker(this.mTracker);
    Optimizely.registerPlugin(new OptimizelyUniversalAnalyticsIntegration());
    initAdjust();
    FacebookSdk.sdkInitialize(getApplicationContext());
  }
  
  public void onLogout()
  {
    Object localObject = ICartRestClient.INSTANCE;
    resetAccengageProperties();
    Locale localLocale = getSharedPreferencesManager().getLocale();
    getSharedPreferencesManager().clearPreferenceData();
    getSharedPreferencesManager().saveLocale(localLocale);
    ((ICartRestClient)localObject).reset();
    User.deleteUser();
    getMixpanel().reset();
    this.mTracker = null;
    this.mCurrentUser = null;
    this.currentOrder = null;
    resetParseChannels();
    trackCrashlytics();
    logoutFacebook();
    getSharedPreferencesManager().saveSentUserData(false);
    getICartNotification().raiseCurrentUserSetEvent(this, null);
    localObject = new Intent(getApplicationContext(), IntroductionActivity.class);
    ((Intent)localObject).setFlags(268468224);
    startActivity((Intent)localObject);
  }
  
  public void overrideCountry(String paramString)
  {
    this.mCountryCode = paramString;
  }
  
  public void resetAccengageProperties()
  {
    Bundle localBundle = new Bundle();
    localBundle.putLong("userId", 0L);
    localBundle.putString("userEmail", null);
    localBundle.putString("totalOrders30", null);
    getAccengage().updateDeviceInfo(localBundle);
    deleteAccengageDeviceId();
  }
  
  public void resetCountryCode()
  {
    try
    {
      this.mCountryCode = null;
      return;
    }
    finally {}
  }
  
  public void resetParseChannels()
  {
    ParseInstallation localParseInstallation = ParseInstallation.getCurrentInstallation();
    String str2 = getSharedPreferencesManager().getStockLocationCountryCode();
    String str1 = str2;
    if (TextUtils.isEmpty(str2)) {
      str1 = getCountryCode();
    }
    if (!TextUtils.isEmpty(str1)) {
      localParseInstallation.put("channels", Arrays.asList(new String[] { str1 }));
    }
    for (;;)
    {
      localParseInstallation.saveInBackground();
      return;
      localParseInstallation.remove("channels");
    }
  }
  
  public void sendEvent(String paramString1, String paramString2, String paramString3)
  {
    getTracker().send(ICartEventBuilder.build(this).setCategory(paramString1).setAction(paramString2).setLabel(paramString3).build());
  }
  
  public void sendUserData(String paramString1, String paramString2, String paramString3, String paramString4, final boolean paramBoolean)
  {
    if ((!paramBoolean) && (getSharedPreferencesManager().hasSentUserData())) {
      return;
    }
    String str2 = getSharedPreferencesManager().getAccengageDeviceId();
    String str1 = str2;
    if (str2 == null) {
      str1 = this.mTemporaryAccengageDeviceId;
    }
    try
    {
      get().getUserManager().sendUserData(paramString1, paramString2, paramString3, paramString4, str1, new ICartCallback(TAG)
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          LogUtils.LOG("SEND USER DATA Failed");
        }
        
        public void onSuccess(Object paramAnonymousObject)
        {
          if (!paramBoolean) {
            ICartApplication.this.getSharedPreferencesManager().saveSentUserData(true);
          }
          LogUtils.LOG("SEND USER DATA Success");
        }
      });
      return;
    }
    catch (JSONException paramString1)
    {
      paramString1.printStackTrace();
      LogUtils.LOG("SEND USER DATA JSONException");
      return;
    }
    catch (UnsupportedEncodingException paramString1)
    {
      paramString1.printStackTrace();
      LogUtils.LOG("SEND USER DATA UnsupportedEncodingException");
    }
  }
  
  public void setAccengageInAppDisplayLocked(boolean paramBoolean)
  {
    getAccengage().setInAppDisplayLocked(paramBoolean);
  }
  
  public void setAccengageProperties() {}
  
  public void setAccengageUser()
  {
    User localUser = User.getCurrentUser();
    if (localUser != null)
    {
      Bundle localBundle = new Bundle();
      localBundle.putLong("userId", localUser.remoteId);
      localBundle.putString("userEmail", localUser.email);
      getAccengage().updateDeviceInfo(localBundle);
      if (getSharedPreferencesManager().getAccengageDeviceId() == null) {
        setAccengageDeviceId();
      }
      return;
    }
    setAccengageDeviceId();
  }
  
  public void setCurrentOrder()
  {
    if (this.mIsCurrentOrderSync) {}
    while (((this.currentOrder != null) && (!this.currentOrder.isCompleted())) || (!isLoggedIn())) {
      return;
    }
    this.mIsCurrentOrderSync = true;
    getOrderManager().getMyOrder(Integer.valueOf(1), Integer.valueOf(1), new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        ICartApplication.access$202(ICartApplication.this, false);
        ICartApplication.this.createOrder();
      }
      
      public void onSuccess(Object paramAnonymousObject)
      {
        paramAnonymousObject = ((OrderResponse)paramAnonymousObject).orders;
        if ((((List)paramAnonymousObject).size() > 0) && (!((Order)((List)paramAnonymousObject).get(0)).isCompleted()) && (!((Order)((List)paramAnonymousObject).get(0)).isCanceled()))
        {
          paramAnonymousObject = (Order)((List)paramAnonymousObject).get(0);
          ICartApplication.this.currentOrder = ((Order)paramAnonymousObject);
          ICartApplication.this.getICartNotification().raiseCurrentOrderSetEvent(this, (Order)paramAnonymousObject);
          ICartApplication.this.getSharedPreferencesManager().saveOrderToken((Order)paramAnonymousObject);
          ICartApplication.access$202(ICartApplication.this, false);
          return;
        }
        ICartApplication.this.createOrder();
      }
    });
  }
  
  public void setCurrentSubDistrict()
  {
    if (this.mIsCurrentSubDistrictSync) {}
    Long localLong;
    do
    {
      return;
      localLong = getSharedPreferencesManager().getSubDistrictId();
    } while ((this.currentSubDistrict != null) || (localLong.longValue() == 0L));
    this.mIsCurrentSubDistrictSync = true;
    getLocationManager().getSubDistrict(localLong, new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        ICartApplication.access$302(ICartApplication.this, false);
      }
      
      public void onSuccess(SubDistrict paramAnonymousSubDistrict)
      {
        ICartApplication.this.currentSubDistrict = paramAnonymousSubDistrict;
        ICartApplication.this.getSharedPreferencesManager().saveSubDistrict(paramAnonymousSubDistrict);
        ICartApplication.access$302(ICartApplication.this, false);
      }
    });
  }
  
  public void setCurrentUser()
  {
    if ((isLoggedIn()) && (this.mCurrentUser == null))
    {
      this.mCurrentUser = User.getCurrentUser();
      getShippingAddress();
    }
  }
  
  public void setCurrentUserAddress()
  {
    if ((isLoggedIn()) && (this.mCurrentUser.getAddresses().isEmpty())) {
      getShippingAddress();
    }
  }
  
  public void setDefaultSortByCriteria()
  {
    setSortByCriteria(0);
  }
  
  public void setFavouritesNeedToBeSynced(boolean paramBoolean)
  {
    this.mFavouritesNeedToBeSynced = paramBoolean;
  }
  
  public void setMixpanelGuestUser()
  {
    if ((getCurrentUser() == null) && (this.currentOrder != null)) {
      getMixpanel().identify(this.currentOrder.token);
    }
  }
  
  public void setMixpanelPushNotifications()
  {
    MixpanelAPI.People localPeople = getMixpanel().getPeople();
    User localUser = getCurrentUser();
    if (localUser == null) {
      if (this.currentOrder != null) {
        localPeople.identify(this.currentOrder.token);
      }
    }
    for (;;)
    {
      localPeople.initPushHandling(getString(2131165674));
      return;
      localPeople.identify(String.valueOf(localUser.remoteId));
    }
  }
  
  public void setMixpanelSuperProperties()
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      Object localObject = getSharedPreferencesManager().getSubDistrictId();
      if (((Long)localObject).longValue() > -1L) {
        localJSONObject.put("subdistrict_id", localObject);
      }
      localObject = getSharedPreferencesManager().getSubDistrictName();
      if (!TextUtils.isEmpty((CharSequence)localObject)) {
        localJSONObject.put("subdistrict_name", localObject);
      }
      localObject = Long.valueOf(getStockLocationId());
      if (((Long)localObject).longValue() > -1L) {
        localJSONObject.put("stock_location_id", localObject);
      }
      localObject = getSharedPreferencesManager().getStockLocationName();
      if (!TextUtils.isEmpty((CharSequence)localObject)) {
        localJSONObject.put("stock_location_name", localObject);
      }
      localObject = getCountryCode();
      if (!TextUtils.isEmpty((CharSequence)localObject)) {
        localJSONObject.put("country_iso", localObject);
      }
      getMixpanel().registerSuperProperties(localJSONObject);
      return;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      Crashlytics.log(localJSONException.getLocalizedMessage());
    }
  }
  
  public void setMixpanelUser()
  {
    User localUser = getCurrentUser();
    if ((localUser == null) || (localUser.remoteId == 0L))
    {
      setMixpanelGuestUser();
      return;
    }
    long l = localUser.remoteId;
    getMixpanel().identify(String.valueOf(l));
  }
  
  public void setOrderListNeedToBeSynced(boolean paramBoolean)
  {
    this.mOrderListNeedToBeSynced = paramBoolean;
  }
  
  public void setOrderStatusNeedToBeSynced(boolean paramBoolean)
  {
    this.mOrderStatusNeedToBeSynced = paramBoolean;
  }
  
  public void setSortByCriteria(int paramInt)
  {
    this.mSortByCriteria = paramInt;
  }
  
  public void setTemporaryActivity(Activity paramActivity)
  {
    this.mTempActivity = paramActivity;
  }
  
  public void setTemporaryLineItems(List<LineItem> paramList)
  {
    this.mTemporaryLineItems = paramList;
  }
  
  public void subscribeParseChannels()
  {
    ArrayList localArrayList = new ArrayList();
    if (getCurrentUser() != null)
    {
      localObject = getParseUserChannelName();
      if (!TextUtils.isEmpty((CharSequence)localObject)) {
        localArrayList.add(localObject);
      }
    }
    String str = getSharedPreferencesManager().getStockLocationCountryCode();
    Object localObject = str;
    if (TextUtils.isEmpty(str)) {
      localObject = getCountryCode();
    }
    if (!TextUtils.isEmpty((CharSequence)localObject)) {
      localArrayList.add(localObject);
    }
    if (localArrayList.size() > 0)
    {
      localObject = ParseInstallation.getCurrentInstallation();
      ((ParseInstallation)localObject).addAllUnique("channels", localArrayList);
      ((ParseInstallation)localObject).put("GCMSenderId", getString(2131165674));
      ((ParseInstallation)localObject).saveInBackground();
    }
  }
  
  public void trackCrashlytics()
  {
    User localUser = getCurrentUser();
    if (localUser != null)
    {
      Crashlytics.setUserName(localUser.getFullName());
      Crashlytics.setUserEmail(localUser.email);
      Crashlytics.setUserIdentifier(localUser.token);
      return;
    }
    Crashlytics.setUserIdentifier(getSharedPreferencesManager().getOrderToken());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/common/ICartApplication.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */