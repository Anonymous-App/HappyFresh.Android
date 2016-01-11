package com.happyfresh.common;

public class ICartConstant
{
  public static abstract interface APPSEE
  {
    public static final String EVENT_CHANGE_SORT_BY_OPTION_SUBCATEGORY = "change sort by option in Subcategory";
    public static final String EVENT_FB_INVITE_CODE_ERROR_TOAST = "FB Invite Code Error Toast";
    public static final String EVENT_SHOW_PASS_LOGIN = "show password in Login";
    public static final String EVENT_SHOW_PASS_SIGNUP = "show password in Signup";
    public static final String PROPERTY_SORT_BY_OPTION = "Option";
  }
  
  public static abstract interface EXTRAS
  {
    public static final String ADDRESS_ID = "ICartConstant.KEYS.EXTRAS.ADDRESS_ID";
    public static final String CITY_NAME = "ICartConstant.KEYS.EXTRAS.CITY_NAME";
    public static final String CREDIT_CARD_ID = "ICartConstant.KEYS.EXTRAS.CREDIT_CARD_ID";
    public static final String FROM_FAVOURITES_LIST = "ICartConstant.KEYS.EXTRAS.FROM_FAVOURITES_LIST";
    public static final String FROM_ORDER_HISTORY = "ICartConstant.KEYS.EXTRAS.FROM_ORDER_HISTORY";
    public static final String INTRODUCTION_PAGE_POSITION = "ICartConstant.KEYS.EXTRAS.INTRODUCTION_PAGE_POSITION";
    public static final String INVITE_CODE_REQUIRED = "ICartConstant.KEYS.EXTRAS.INVITE_CODE_REQUIRED";
    public static final String LATITUDE = "ICartConstant.KEYS.EXTRAS.LONGITUDE";
    public static final String LINE_ITEM_REMOTE_ID = "ICartConstant.LINE_ITEM_REMOTE_ID";
    public static final String LONGITUDE = "ICartConstant.KEYS.EXTRAS.LATITUDE";
    public static final String ORDER_NUMBER = "ICartConstant.ORDER_NUMBER";
    public static final String ORDER_PAYLOAD = "ICartConstant.ORDER_PAYLOAD";
    public static final String ORDER_PRICE = "ICartConstant.ORDER_PRICE";
    public static final String ORDER_STARS = "ICartConstant.ORDER_STARS";
    public static final String ORDER_STATUS = "ICartConstant.ORDER_STATUS";
    public static final String PREVIOUS_SCREEN = "ICartConstant.KEYS.EXTRAS.PREVIOUS_SCREEN";
    public static final String PRODUCT = "ICartConstant.KEYS.EXTRAS.PRODUCT";
    public static final String PRODUCT_IMAGES = "ICartConstant.KEYS.EXTRAS.PRODUCT_IMAGES";
    public static final String PRODUCT_IMAGES_POSITION = "ICartConstant.KEYS.EXTRAS.PRODUCT_IMAGES_POSITION";
    public static final String PRODUCT_IMAGE_URL = "ICartConstant.KEYS.EXTRAS.PRODUCT_IMAGE_URL";
    public static final String SEARCH_ID = "ICartConstant.SEARCH_ID";
    public static final String SHOPPING_LIST_ID = "ICartConstant.KEYS.EXTRAS.SHOPPING_LIST_ID";
    public static final String SHOPPING_LIST_NAME = "ICartConstant.KEYS.EXTRAS.SHOPPING_LIST_NAME";
    public static final String SLOTS = "ICartConstant.KEYS.EXTRAS.SLOTS";
    public static final String STOCK_LOCATIONS = "ICartConstant.KEYS.EXTRAS.STOCK_LOCATIONS";
    public static final String STOCK_LOCATION_ID = "ICartConstant.KEYS.EXTRAS.STOCK_LOCATION_ID";
    public static final String SUB_DISTRICT_ID = "ICartConstant.KEYS.EXTRAS.SUB_DISTRICT_ID";
    public static final String SUB_DISTRICT_NAME = "ICartConstant.KEYS.EXTRAS.SUB_DISTRICT_NAME";
    public static final String SUB_LOCALITY = "ICartConstant.KEYS.EXTRAS.SUB_LOCALITY";
    public static final String ZIP_CODE = "ICartConstant.KEYS.EXTRAS.ZIP_CODE";
    
    public static abstract interface DEEP_LINKS
    {
      public static final String STOCK_LOCATION_ID = "ICartConstant.EXTRAS.DEEP_LINKS.STOCK_LOCATION_ID";
      public static final String SUPPLIER_ID = "ICartConstant.EXTRAS.DEEP_LINKS.SUPPLIER_ID";
      public static final String TYPE = "ICartConstant.EXTRAS.DEEP_LINKS.TYPE";
    }
  }
  
  public static abstract interface GA
  {
    public static final String CATEGORY_UI_ACTION = "ui_action";
    public static final String EVENT_CANCEL_ORDER = "Cancel Order";
    public static final String EVENT_CATEGORY_VIEWED = "Category Viewed";
    public static final String EVENT_CHANGED_STORE = "Changed Store";
    public static final String EVENT_CHANGE_SORT_BY_OPTION = "Change Sort By";
    public static final String EVENT_CHOOSE_REPLACEMENT = "Replacement Chosen";
    public static final String EVENT_COMMENTED_AFTER_RATING = "Rated";
    public static final String EVENT_COMPLETED_SIGN_UP = "Signed Up";
    public static final String EVENT_COMPLETE_PAYMENT_AFTER_CANCEL_ORDER = "Complete Payment After Cancel Order";
    public static final String EVENT_EDIT_ORDER = "Edit Order";
    public static final String EVENT_LAUNCHED_APP = "Opened App";
    public static final String EVENT_ORDER_HISTORY_VIEWED = "Viewed Order History";
    public static final String EVENT_POPULAR_PRODUCT_VIEWED = "Popular Products Viewed";
    public static final String EVENT_REPLACEMENT_OPTION = "Replacement Option";
    public static final String EVENT_REPLACEMENT_PER_PRODUCT_OPTION = "Replacement Per Product Option";
    public static final String EVENT_SHARED = "Shared";
    public static final String EVENT_SIGN_UP_FB = "Signed Up FB";
    public static final String EVENT_SWITCH_REPLACEMENT = "Switched On Replacement";
    
    public static abstract interface CHECKOUT_STEPS
    {
      public static final int ADDRESS = 2;
      public static final int DELIVERY = 3;
      public static final int PAYMENT = 4;
      public static final int REPLACEMENT = 1;
    }
    
    public static abstract interface REPLACEMENT_OPTIONS
    {
      public static final String CALL_ME = "Call Me";
      public static final String DO_NOT_REPLACE = "Do Not Replace";
      public static final String LET_SHOPPER_PICK = "Let Shopper Pick";
      public static final String PER_PRODUCT = "Per Product";
    }
    
    public static abstract interface SCREEN_NAME
    {
      public static final String ABOUT = "About";
      public static final String ACCOUNT = "Account";
      public static final String ACTIVE_ORDERS = "Active Orders Details";
      public static final String ADD_ADDRESS = "Add Address";
      public static final String AREA_NOT_COVERED = "Area Not Covered";
      public static final String CANCEL_ORDER_CONFIRMATION = "Cancel Order Confirmation";
      public static final String CART = "Cart";
      public static final String CATEGORY = "Category";
      public static final String CHANGE_STORE = "Change Store";
      public static final String CHOOSE_AREA = "Choose Area";
      public static final String CHOOSE_CITY = "Choose City";
      public static final String CHOOSE_COUNTRY = "Choose Country";
      public static final String CHOOSE_DELIVERY_ADDRESS = "Choose Delivery Address";
      public static final String CHOOSE_LOCATION = "Choose Location";
      public static final String CHOOSE_PAYMENT_METHOD = "Choose Payment Method";
      public static final String CHOOSE_REPLACEMENT = "Choose Replacement";
      public static final String CHOOSE_STORE = "Choose Store";
      public static final String CONTACT_US = "Contact Us";
      public static final String CREDIT_CARD_DETAILS = "Credit Card Details";
      public static final String DELIVERY = "Delivery";
      public static final String DELIVERY_TIME = "Delivery Time";
      public static final String EDIT_ADDRESS = "Edit Address";
      public static final String EDIT_ORDER_FAILED = "Edit Order Failed Popup";
      public static final String FAQ = "FAQ";
      public static final String FAVORITES_LIST = "Favourites List";
      public static final String FB_INVITE_CODE = "FB Invite Code Popup";
      public static final String FEEDBACK = "Feedback";
      public static final String INDEX = "Index Screen";
      public static final String INTRODUCTION = "Introduction AB Testing";
      public static final String INTRODUCTION_1 = "Introduction 1";
      public static final String INTRODUCTION_2 = "Introduction 2";
      public static final String INTRODUCTION_3 = "Introduction 3";
      public static final String INVALID_EMAIL = "Invalid Email Popup";
      public static final String LAUNCH = "Launch";
      public static final String LOGIN = "Login";
      public static final String LOGIN_ERROR = "Login Error Popup";
      public static final String MORE = "More";
      public static final String ORDERS_OVERVIEW = "Orders Overview";
      public static final String ORDER_CONFIRMATION = "Order Confirmation";
      public static final String PAST_ORDERS = "Past Orders Details";
      public static final String PRIVACY_POLICY = "Privacy Policy";
      public static final String PRODUCT_DETAILS = "Product Details";
      public static final String PRODUCT_IMAGES = "Product Images";
      public static final String RATE = "Rate";
      public static final String RECOMMENDED_LIST = "Recommended List";
      public static final String RECOMMENDED_OVERVIEW = "Recommended List Overview";
      public static final String REDIRECT_TO_ADYEN = "Redirect To Adyen";
      public static final String REMOVE_FROM_CART = "Remove From Cart";
      public static final String REPLACEMENT = "Replacement";
      public static final String REQUEST_AREA_NOTIF = "Request Area Notification";
      public static final String RESET_PASSWORD = "Reset Password";
      public static final String SEARCH_PRODUCTS = "Search Products";
      public static final String SHARE = "Share";
      public static final String SHOP = "Shop";
      public static final String SIGN_UP = "Signup";
      public static final String SORTING = "Sorting";
      public static final String STORE_POPUP = "Store Popup";
      public static final String SUB_CATEGORY = "Subcategory";
      public static final String TERMS_AND_CONDITIONS = "Terms And Conditions";
      public static final String VERIFY_NUMBER_1 = "Verify Number 1";
      public static final String VERIFY_NUMBER_2 = "Verify Number 2";
    }
    
    public static abstract interface SORT_BY
    {
      public static final String A_TO_Z = "A - Z";
      public static final String HIGHEST_PRICE = "Highest Price";
      public static final String LOWEST_PRICE = "Lowest Price";
      public static final String POPULARITY = "Popularity";
      public static final String Z_TO_A = "Z - A";
    }
  }
  
  public static abstract interface KEYS
  {
    public static final String ACCENGAGE_DEVICE_ID = "ICartConstant.KEYS.ACCENGAGE_DEVICE_ID";
    public static final String ADDRESS_SCREEN_TITLE = "ICartConstant.KEYS.ADDRESS_SCREEN_TITLE";
    public static final String AFTER_SIGN_UP = "ICartConstant.KEYS.AFTER_SIGN_UP";
    public static final String CATEGORY_ALL = "#{ALL}";
    public static final String COUNTRY_CODE = "ICartConstant.KEYS.COUNTRY_CODE";
    public static final String CURRENT_TAXON = "ICartConstant.KEYS.CURRENT_TAXON";
    public static final String DISTRICT_NAME = "ICartConstant.KEYS.DISTRICT_NAME";
    public static final String EDITED_ADDRESS = "ICartConstant.KEYS.EDITED_ADDRESS";
    public static final String EXPRESS_CHECKOUT = "ICartConstant.KEYS.EXPRESS_CHECKOUT";
    public static final String FB_REFERRAL_CODE = "ICartConstant.KEYS.FB_REFERRAL_CODE";
    public static final String FIRST_TIME_USER = "ICartConstant.KEYS.FIRST_TIME_USER";
    public static final String LANGUAGE_CODE = "ICartConstant.KEYS.LANGUAGE_CODE";
    public static final String LAST_CANCEL_ORDER = "ICartConstant.KEYS.LAST_CANCEL_ORDER";
    public static final String LAST_FIRST_ITEM_ADD_TO_CART = "ICartConstant.KEYS.LAST_FIRST_ITEM_ADD_TO_CART";
    public static final String LAST_OVERALL_REPLACEMENT_OPTION = "ICartConstant.KEYS.LAST_OVERALL_REPLACEMENT_OPTION";
    public static final String LATITUDE = "ICartConstant.KEYS.LONGITUDE";
    public static final String LONGITUDE = "ICartConstant.KEYS.LATITUDE";
    public static final String ORDER_NUMBER = "ICartConstant.KEYS.ORDER_NUMBER";
    public static final String ORDER_TOKEN = "ICartConstant.KEYS.ORDER_TOKEN";
    public static final String OVERALL_REPLACEMENT_OPTION = "ICartConstant.KEYS.OVERALL_REPLACEMENT_OPTION";
    public static final String PAYMENT_PAYLOAD = "ICartConstant.KEYS.PAYMENT_PAYLOAD";
    public static final String PHONE_NUMBER = "ICartConstant.KEYS.PHONE_NUMBER";
    public static final String PHONE_VERIFICATION = "ICartConstant.KEYS.PHONE_VERIFICATION";
    public static final String PROMO_BUTTON_TEXT = "ICartConstant.KEYS.PROMO_BUTTON_TEXT";
    public static final String PROMO_LINE_1 = "ICartConstant.KEYS.PROMO_LINE_1";
    public static final String PROMO_LINE_2 = "ICartConstant.KEYS.PROMO_LINE_2";
    public static final String PROMO_SUBJECT = "ICartConstant.KEYS.PROMO_SUBJECT";
    public static final String PROMO_TITLE = "ICartConstant.KEYS.PROMO_TITLE";
    public static final String SCREEN_1_CONTENT = "ICartConstant.KEYS.SCREEN_1_CONTENT";
    public static final String SCREEN_1_TITLE = "ICartConstant.KEYS.SCREEN_1_TITLE";
    public static final String SCREEN_2_CONTENT = "ICartConstant.KEYS.SCREEN_2_CONTENT";
    public static final String SCREEN_2_TITLE = "ICartConstant.KEYS.SCREEN_2_TITLE";
    public static final String SCREEN_3_CONTENT = "ICartConstant.KEYS.SCREEN_3_CONTENT";
    public static final String SCREEN_3_TITLE = "ICartConstant.KEYS.SCREEN_3_TITLE";
    public static final String SEARCH_LOCATION_IS_PICKER = "ICartConstant.KEYS.SEARCH_LOCATION_IS_PICKER";
    public static final String SENT_USER_DATA = "ICartConstant.KEYS.SENT_USER_DATA";
    public static final String STOCK_LOCATION_CITY = "ICartConstant.KEYS.LOCATION_CITY";
    public static final String STOCK_LOCATION_COUNTRY = "ICartConstant.KEYS.LOCATION_COUNTRY";
    public static final String STOCK_LOCATION_ID = "ICartConstant.KEYS.LOCATION_ID";
    public static final String STOCK_LOCATION_LATITUDE = "ICartConstant.KEYS.LOCATION_LATITUDE";
    public static final String STOCK_LOCATION_LONGITUDE = "ICartConstant.KEYS.LOCATION_LONGITUDE";
    public static final String STOCK_LOCATION_NAME = "ICartConstant.KEYS.LOCATION_NAME";
    public static final String STOCK_LOCATION_ZIPCODE = "ICartConstant.KEYS.LOCATION_ZIPCODE";
    public static final String SUB_DISTRICT_BUNDLE = "ICartConstant.KEYS.SUB_DISTRICT_BUNDLE";
    public static final String SUB_DISTRICT_ID = "ICartConstant.KEYS.SUB_DISTRICT_ID";
    public static final String SUB_DISTRICT_NAME = "ICartConstant.KEYS.SUB_DISTRICT_NAME";
    public static final String SUB_DISTRICT_ZIP_CODE = "ICartConstant.KEYS.SUB_DISTRICT_ZIP_CODE";
    public static final String TAXON = "ICartConstant.KEYS.TAXON";
    public static final String USER_CODE = "ICartConstant.KEYS.USER_CODE";
    public static final String USER_EMAIL = "ICartConstant.KEYS.USER_EMAIL";
    public static final String USER_ID = "ICartConstant.KEYS.USER_ID";
    public static final String USER_TOKEN = "ICartConstant.KEYS.USER_TOKEN";
    public static final String ZIP_CODE = "ICartConstant.KEYS.ZIP_CODE";
  }
  
  public static abstract interface MISC
  {
    public static final int API_CONNECT_TIME_OUT = 60000;
    public static final int API_READ_TIME_OUT = 60000;
    public static final int CART_ADD = 1;
    public static final int CART_REMOVE = 3;
    public static final int CART_UPDATE = 2;
    public static final int NUMBER_OF_INTRODUCTION_PAGE = 3;
    public static final String PAYMENT_COD = "cod";
    public static final String PAYMENT_CREDIT_CARD = "credit_card";
    public static final int POPULAR_PRODUCT_NUMBER_OF_COLUMN = 3;
    public static final String REPLACEMENT_CALL_ME = "by_call";
    public static final String REPLACEMENT_DO_NOT_REPLACE = "dont_replace";
    public static final String REPLACEMENT_LET_SHOPPER_PICK = "by_shopper";
    public static final String REPLACEMENT_PER_PRODUCT = "per_product";
    public static final int SORT_BY_A_Z = 3;
    public static final int SORT_BY_HIGHEST_PRICE = 2;
    public static final int SORT_BY_LOWEST_PRICE = 1;
    public static final int SORT_BY_POPULARITY = 0;
    public static final int SORT_BY_Z_A = 4;
    public static final int SUB_CATEGORY_PRODUCT_NUMBER_OF_COLUMN = 2;
  }
  
  public static abstract interface REQUEST_CODES
  {
    public static final int CHECKOUT = 1;
    public static final int CHOOSE_STORE = 12;
    public static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    public static final int LOGIN = 7;
    public static final int MY_ACCOUNT = 11;
    public static final int RATE = 14;
    public static final int SEARCH_REPLACEMENT = 9100;
    public static final int SIGNUP = 8;
    public static final int SPLASH = 13;
  }
  
  public static abstract interface URL
  {
    public static final String FAQ = "/faq?app=1&c=";
    public static final String HAPPYFRESH = "https://www.happyfresh.com";
    public static final String PRIVACY = "/privacy?app=1&c=";
    public static final String TERMS = "/terms?app=1&c=";
  }
  
  public static abstract interface VIEW_TYPES
  {
    public static final int TYPE_CATEGORIES = 1003;
    public static final int TYPE_CATEGORY = 1001;
    public static final int TYPE_CONTENT = 2001;
    public static final int TYPE_EMPTY_FAVOURITES = 3001;
    public static final int TYPE_EMPTY_PREV_PURCHASED = 3002;
    public static final int TYPE_HEADER = 1002;
    public static final int TYPE_MORE = 2002;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/common/ICartConstant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */