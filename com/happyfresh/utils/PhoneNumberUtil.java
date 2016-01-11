package com.happyfresh.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PhoneNumberUtil
{
  private static final Map<String, String> mCountryCodes = new HashMap();
  
  static
  {
    mCountryCodes.put("ID", "+62");
    mCountryCodes.put("MY", "+60");
    mCountryCodes.put("TH", "+66");
    mCountryCodes.put("TW", "+886");
  }
  
  public static String convertPhoneNumber(String paramString1, String paramString2)
  {
    Iterator localIterator = mCountryCodes.values().iterator();
    while (localIterator.hasNext()) {
      if ((paramString1.startsWith((String)localIterator.next())) || (paramString1.startsWith("+"))) {
        return paramString1;
      }
    }
    paramString2 = (String)mCountryCodes.get(paramString2);
    if (paramString1.startsWith("0")) {
      return paramString2 + paramString1.substring(1);
    }
    return paramString2 + paramString1;
  }
  
  public static boolean isPhoneNumberValid(String paramString)
  {
    return paramString.matches("^[+]?\\d{5,15}");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/utils/PhoneNumberUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */