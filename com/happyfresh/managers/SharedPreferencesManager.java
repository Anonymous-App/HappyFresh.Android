package com.happyfresh.managers;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartRestClient;
import com.happyfresh.models.Coordinate;
import com.happyfresh.models.Country;
import com.happyfresh.models.District;
import com.happyfresh.models.OnboardingString;
import com.happyfresh.models.Order;
import com.happyfresh.models.PromotionString;
import com.happyfresh.models.State;
import com.happyfresh.models.StockLocation;
import com.happyfresh.models.SubDistrict;
import com.happyfresh.models.User;
import com.happyfresh.utils.LogUtils;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SharedPreferencesManager
{
  protected ICartApplication mICartApplication;
  
  public SharedPreferencesManager(ICartApplication paramICartApplication)
  {
    this.mICartApplication = paramICartApplication;
  }
  
  private String getString(int paramInt)
  {
    return this.mICartApplication.getString(paramInt);
  }
  
  public void clearOnboardingStrings()
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    localEditor.remove("ICartConstant.KEYS.SCREEN_1_TITLE");
    localEditor.remove("ICartConstant.KEYS.SCREEN_2_TITLE");
    localEditor.remove("ICartConstant.KEYS.SCREEN_3_TITLE");
    localEditor.remove("ICartConstant.KEYS.SCREEN_1_CONTENT");
    localEditor.remove("ICartConstant.KEYS.SCREEN_2_CONTENT");
    localEditor.remove("ICartConstant.KEYS.SCREEN_3_CONTENT");
    localEditor.commit();
  }
  
  public void clearPreferenceData()
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    localEditor.clear();
    localEditor.commit();
  }
  
  public void deleteCoordinate()
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    localEditor.remove("ICartConstant.KEYS.LONGITUDE");
    localEditor.remove("ICartConstant.KEYS.LATITUDE");
    localEditor.commit();
  }
  
  public void deleteSubDistrict()
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    localEditor.remove("ICartConstant.KEYS.SUB_DISTRICT_ID");
    localEditor.remove("ICartConstant.KEYS.SUB_DISTRICT_NAME");
    localEditor.remove("ICartConstant.KEYS.DISTRICT_NAME");
    localEditor.remove("ICartConstant.KEYS.SUB_DISTRICT_ZIP_CODE");
    localEditor.commit();
  }
  
  public void deleteZipCode()
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    localEditor.remove("ICartConstant.KEYS.ZIP_CODE");
    localEditor.commit();
  }
  
  public String getAccengageDeviceId()
  {
    return PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).getString("ICartConstant.KEYS.ACCENGAGE_DEVICE_ID", null);
  }
  
  public Coordinate getCoordinate()
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication);
    Double localDouble1 = null;
    Double localDouble2 = null;
    if (localSharedPreferences.contains("ICartConstant.KEYS.LONGITUDE")) {
      localDouble1 = Double.valueOf(Double.longBitsToDouble(localSharedPreferences.getLong("ICartConstant.KEYS.LONGITUDE", 0L)));
    }
    if (localSharedPreferences.contains("ICartConstant.KEYS.LATITUDE")) {
      localDouble2 = Double.valueOf(Double.longBitsToDouble(localSharedPreferences.getLong("ICartConstant.KEYS.LATITUDE", 0L)));
    }
    if ((localDouble1 == null) || (localDouble2 == null)) {
      return null;
    }
    return new Coordinate(localDouble1, localDouble2);
  }
  
  public long getLastCancelOrder()
  {
    return PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).getLong("ICartConstant.KEYS.LAST_CANCEL_ORDER", -1L);
  }
  
  public long getLastFirstTimeAddToCart()
  {
    return PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).getLong("ICartConstant.KEYS.LAST_FIRST_ITEM_ADD_TO_CART", -1L);
  }
  
  public String getLastOverallReplacementOption()
  {
    return PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).getString("ICartConstant.KEYS.LAST_OVERALL_REPLACEMENT_OPTION", "by_shopper");
  }
  
  public Locale getLocale()
  {
    if (!isLoggedIn()) {
      return this.mICartApplication.getCurrentLocale();
    }
    Object localObject = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication);
    String str = ((SharedPreferences)localObject).getString("ICartConstant.KEYS.LANGUAGE_CODE", null);
    localObject = ((SharedPreferences)localObject).getString("ICartConstant.KEYS.COUNTRY_CODE", null);
    if (str == null)
    {
      saveLocale(this.mICartApplication.getCurrentLocale());
      return getLocale();
    }
    if (localObject == null) {
      return new Locale(str);
    }
    return new Locale(str, (String)localObject);
  }
  
  public OnboardingString getOnboardingStrings()
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication);
    OnboardingString localOnboardingString = new OnboardingString();
    localOnboardingString.screen1Title = localSharedPreferences.getString("ICartConstant.KEYS.SCREEN_1_TITLE", getString(2131165340));
    localOnboardingString.screen2Title = localSharedPreferences.getString("ICartConstant.KEYS.SCREEN_2_TITLE", getString(2131165342));
    localOnboardingString.screen3Title = localSharedPreferences.getString("ICartConstant.KEYS.SCREEN_3_TITLE", getString(2131165344));
    localOnboardingString.screen1Content = localSharedPreferences.getString("ICartConstant.KEYS.SCREEN_1_CONTENT", getString(2131165339));
    localOnboardingString.screen2Content = localSharedPreferences.getString("ICartConstant.KEYS.SCREEN_2_CONTENT", getString(2131165341));
    localOnboardingString.screen3Content = localSharedPreferences.getString("ICartConstant.KEYS.SCREEN_3_CONTENT", getString(2131165343));
    return localOnboardingString;
  }
  
  public String getOrderNumber()
  {
    return PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).getString("ICartConstant.KEYS.ORDER_NUMBER", null);
  }
  
  public String getOrderToken()
  {
    return PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).getString("ICartConstant.KEYS.ORDER_TOKEN", null);
  }
  
  public String getOverallReplacementOption()
  {
    return PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).getString("ICartConstant.KEYS.OVERALL_REPLACEMENT_OPTION", "by_shopper");
  }
  
  public PromotionString getPromotionString()
  {
    Object localObject = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication);
    PromotionString localPromotionString = new PromotionString();
    localPromotionString.title = ((SharedPreferences)localObject).getString("ICartConstant.KEYS.PROMO_TITLE", null);
    localPromotionString.subject = ((SharedPreferences)localObject).getString("ICartConstant.KEYS.PROMO_SUBJECT", null);
    localPromotionString.line1 = ((SharedPreferences)localObject).getString("ICartConstant.KEYS.PROMO_LINE_1", null);
    localPromotionString.line2 = ((SharedPreferences)localObject).getString("ICartConstant.KEYS.PROMO_LINE_2", null);
    localPromotionString.buttonString = ((SharedPreferences)localObject).getString("ICartConstant.KEYS.PROMO_BUTTON_TEXT", null);
    localObject = localPromotionString;
    if (localPromotionString.title == null)
    {
      localObject = localPromotionString;
      if (localPromotionString.subject == null)
      {
        localObject = localPromotionString;
        if (localPromotionString.line1 == null)
        {
          localObject = localPromotionString;
          if (localPromotionString.line2 == null)
          {
            localObject = localPromotionString;
            if (localPromotionString.buttonString == null) {
              localObject = null;
            }
          }
        }
      }
    }
    return (PromotionString)localObject;
  }
  
  public String getStockLocationCity()
  {
    return PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).getString("ICartConstant.KEYS.LOCATION_CITY", "");
  }
  
  public String getStockLocationCountryCode()
  {
    return PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).getString("ICartConstant.KEYS.LOCATION_COUNTRY", null);
  }
  
  public long getStockLocationId()
  {
    return PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).getLong("ICartConstant.KEYS.LOCATION_ID", -1L);
  }
  
  public String getStockLocationName()
  {
    return PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).getString("ICartConstant.KEYS.LOCATION_NAME", "");
  }
  
  public String getStockLocationZipcode()
  {
    return PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).getString("ICartConstant.KEYS.LOCATION_ZIPCODE", "");
  }
  
  public Long getSubDistrictId()
  {
    return Long.valueOf(PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).getLong("ICartConstant.KEYS.SUB_DISTRICT_ID", 0L));
  }
  
  public String getSubDistrictName()
  {
    return PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).getString("ICartConstant.KEYS.SUB_DISTRICT_NAME", null);
  }
  
  public String getZipCode()
  {
    return PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).getString("ICartConstant.KEYS.ZIP_CODE", null);
  }
  
  public boolean hasSentUserData()
  {
    return PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).getBoolean("ICartConstant.KEYS.SENT_USER_DATA", false);
  }
  
  public boolean isAlreadyMoreThanOneHourSinceLastFirstTimeAddToCart()
  {
    long l1 = getLastFirstTimeAddToCart();
    if (l1 == -1L) {}
    long l2;
    do
    {
      return true;
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.setTimeZone(TimeZone.getTimeZone("gmt"));
      l1 = (localCalendar.getTime().getTime() - l1) / 1000L;
      l2 = l1 / 3600L;
      LogUtils.LOG("REFRESH CART >>> " + l2 + " hours | " + l1 + " seconds");
    } while (l2 >= 1L);
    return false;
  }
  
  public boolean isGuest()
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication);
    return (!isLoggedIn()) && (localSharedPreferences.getString("ICartConstant.KEYS.ORDER_TOKEN", null) != null);
  }
  
  public boolean isLessThanTwoHoursSinceLastCancelOrder()
  {
    long l1 = getLastCancelOrder();
    if (l1 == -1L) {}
    long l2;
    do
    {
      return false;
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.setTimeZone(TimeZone.getTimeZone("gmt"));
      l1 = (localCalendar.getTime().getTime() - l1) / 1000L;
      l2 = l1 / 3600L;
      LogUtils.LOG("TRACK CANCEL ORDER >>> " + l2 + " hours | " + l1 + " seconds");
    } while (l2 > 1L);
    return true;
  }
  
  public boolean isLoggedIn()
  {
    return PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).getString("ICartConstant.KEYS.USER_TOKEN", null) != null;
  }
  
  public boolean isNeedReferralCode()
  {
    return PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).getBoolean("ICartConstant.KEYS.FB_REFERRAL_CODE", false);
  }
  
  public boolean isNewUser()
  {
    return PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).getBoolean("ICartConstant.KEYS.AFTER_SIGN_UP", false);
  }
  
  public void markingNeedReferralCode(boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    localEditor.putBoolean("ICartConstant.KEYS.FB_REFERRAL_CODE", paramBoolean);
    localEditor.commit();
  }
  
  public void markingNewUser(boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    localEditor.putBoolean("ICartConstant.KEYS.AFTER_SIGN_UP", paramBoolean);
    localEditor.commit();
  }
  
  public void resetLastCancelOrder()
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    localEditor.putLong("ICartConstant.KEYS.LAST_CANCEL_ORDER", -1L);
    localEditor.commit();
  }
  
  public void resetLastFirstTimeAddToCart()
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    localEditor.putLong("ICartConstant.KEYS.LAST_FIRST_ITEM_ADD_TO_CART", -1L);
    localEditor.commit();
  }
  
  public void saveAccengageDeviceId(String paramString)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    localEditor.putString("ICartConstant.KEYS.ACCENGAGE_DEVICE_ID", paramString);
    localEditor.commit();
  }
  
  public void saveCoordinate(Coordinate paramCoordinate)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    localEditor.putLong("ICartConstant.KEYS.LONGITUDE", Double.doubleToRawLongBits(paramCoordinate.latitude.doubleValue()));
    localEditor.putLong("ICartConstant.KEYS.LATITUDE", Double.doubleToRawLongBits(paramCoordinate.longitude.doubleValue()));
    localEditor.commit();
  }
  
  public void saveFirstTimeUser(boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    localEditor.putBoolean("ICartConstant.KEYS.FIRST_TIME_USER", paramBoolean);
    localEditor.commit();
  }
  
  public void saveLastCancelOrder()
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeZone(TimeZone.getTimeZone("gmt"));
    localEditor.putLong("ICartConstant.KEYS.LAST_CANCEL_ORDER", localCalendar.getTime().getTime());
    localEditor.commit();
  }
  
  public void saveLastFirstTimeAddToCart()
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeZone(TimeZone.getTimeZone("gmt"));
    localEditor.putLong("ICartConstant.KEYS.LAST_FIRST_ITEM_ADD_TO_CART", localCalendar.getTime().getTime());
    localEditor.commit();
  }
  
  public void saveLocale(Locale paramLocale)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    localEditor.putString("ICartConstant.KEYS.LANGUAGE_CODE", paramLocale.getLanguage());
    localEditor.putString("ICartConstant.KEYS.COUNTRY_CODE", paramLocale.getCountry());
    localEditor.commit();
    ICartRestClient.INSTANCE.reset();
    this.mICartApplication.setMixpanelSuperProperties();
  }
  
  public void saveOnboardingStrings(OnboardingString paramOnboardingString)
  {
    if (paramOnboardingString == null) {
      return;
    }
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    if (!TextUtils.isEmpty(paramOnboardingString.screen1Title)) {
      localEditor.putString("ICartConstant.KEYS.SCREEN_1_TITLE", paramOnboardingString.screen1Title);
    }
    if (!TextUtils.isEmpty(paramOnboardingString.screen2Title)) {
      localEditor.putString("ICartConstant.KEYS.SCREEN_2_TITLE", paramOnboardingString.screen2Title);
    }
    if (!TextUtils.isEmpty(paramOnboardingString.screen3Title)) {
      localEditor.putString("ICartConstant.KEYS.SCREEN_3_TITLE", paramOnboardingString.screen3Title);
    }
    if (!TextUtils.isEmpty(paramOnboardingString.screen1Content)) {
      localEditor.putString("ICartConstant.KEYS.SCREEN_1_CONTENT", paramOnboardingString.screen1Content);
    }
    if (!TextUtils.isEmpty(paramOnboardingString.screen2Content)) {
      localEditor.putString("ICartConstant.KEYS.SCREEN_2_CONTENT", paramOnboardingString.screen2Content);
    }
    if (!TextUtils.isEmpty(paramOnboardingString.screen3Content)) {
      localEditor.putString("ICartConstant.KEYS.SCREEN_3_CONTENT", paramOnboardingString.screen3Content);
    }
    localEditor.commit();
  }
  
  public void saveOrderToken(Order paramOrder)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    localEditor.putString("ICartConstant.KEYS.ORDER_NUMBER", paramOrder.number);
    localEditor.putString("ICartConstant.KEYS.ORDER_TOKEN", paramOrder.token);
    localEditor.commit();
  }
  
  public void saveOverallReplacementOption(String paramString)
  {
    String str2 = getOverallReplacementOption();
    String str3 = getLastOverallReplacementOption();
    String str1 = str3;
    if ("per_product".equalsIgnoreCase(paramString))
    {
      str1 = str3;
      if (!"per_product".equalsIgnoreCase(str2)) {
        str1 = str2;
      }
    }
    saveOverallReplacementOption(paramString, str1);
  }
  
  public void saveOverallReplacementOption(String paramString1, String paramString2)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    localEditor.putString("ICartConstant.KEYS.OVERALL_REPLACEMENT_OPTION", paramString1);
    localEditor.putString("ICartConstant.KEYS.LAST_OVERALL_REPLACEMENT_OPTION", paramString2);
    localEditor.commit();
  }
  
  public void savePromotionStrings(PromotionString paramPromotionString)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    localEditor.putString("ICartConstant.KEYS.PROMO_TITLE", paramPromotionString.title);
    localEditor.putString("ICartConstant.KEYS.PROMO_SUBJECT", paramPromotionString.subject);
    localEditor.putString("ICartConstant.KEYS.PROMO_LINE_1", paramPromotionString.line1);
    localEditor.putString("ICartConstant.KEYS.PROMO_LINE_2", paramPromotionString.line2);
    localEditor.putString("ICartConstant.KEYS.PROMO_BUTTON_TEXT", paramPromotionString.buttonString);
    localEditor.commit();
  }
  
  public void saveSentUserData(boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    localEditor.putBoolean("ICartConstant.KEYS.SENT_USER_DATA", paramBoolean);
    localEditor.commit();
  }
  
  public void saveStockLocation(StockLocation paramStockLocation)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    localEditor.putLong("ICartConstant.KEYS.LOCATION_ID", paramStockLocation.remoteId);
    localEditor.putString("ICartConstant.KEYS.LOCATION_NAME", paramStockLocation.name);
    localEditor.putString("ICartConstant.KEYS.LOCATION_COUNTRY", paramStockLocation.country.isoName);
    localEditor.putString("ICartConstant.KEYS.LOCATION_CITY", paramStockLocation.state.name);
    localEditor.putString("ICartConstant.KEYS.LOCATION_ZIPCODE", paramStockLocation.zipcode);
    localEditor.commit();
    this.mICartApplication.resetCountryCode();
    ICartRestClient.INSTANCE.reset();
    this.mICartApplication.setMixpanelSuperProperties();
    this.mICartApplication.setAccengageProperties();
  }
  
  public void saveSubDistrict(SubDistrict paramSubDistrict)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    localEditor.putLong("ICartConstant.KEYS.SUB_DISTRICT_ID", paramSubDistrict.remoteId.longValue());
    localEditor.putString("ICartConstant.KEYS.SUB_DISTRICT_NAME", paramSubDistrict.name);
    localEditor.putString("ICartConstant.KEYS.DISTRICT_NAME", paramSubDistrict.district.name);
    localEditor.putString("ICartConstant.KEYS.SUB_DISTRICT_ZIP_CODE", paramSubDistrict.zipcode);
    localEditor.commit();
  }
  
  public void saveSubDistrictName(String paramString)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    localEditor.putString("ICartConstant.KEYS.SUB_DISTRICT_NAME", paramString);
    localEditor.commit();
  }
  
  public void saveUserAttributes(User paramUser)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    localEditor.putString("ICartConstant.KEYS.USER_TOKEN", paramUser.token);
    localEditor.commit();
    if (paramUser.promotionString != null) {
      savePromotionStrings(paramUser.promotionString);
    }
    paramUser.save();
  }
  
  public void saveZipCode(String paramString)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mICartApplication).edit();
    localEditor.putString("ICartConstant.KEYS.ZIP_CODE", paramString);
    localEditor.commit();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/managers/SharedPreferencesManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */