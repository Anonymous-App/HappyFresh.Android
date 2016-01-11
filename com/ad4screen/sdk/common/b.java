package com.ad4screen.sdk.common;

import android.os.Bundle;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Item;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class b
{
  public static Lead a(String paramString)
  {
    try
    {
      paramString = new JSONObject(paramString);
      paramString = new Lead(paramString.getString("label"), paramString.getString("value"));
      return paramString;
    }
    catch (JSONException paramString)
    {
      Log.error("Lead|Error during json parsing", paramString);
    }
    return null;
  }
  
  public static Cart b(String paramString)
  {
    try
    {
      paramString = new JSONObject(paramString);
      String str1 = paramString.getString("cartId");
      int i = paramString.getInt("quantity");
      String str2 = paramString.getString("category");
      String str3 = paramString.getString("label");
      double d = paramString.getDouble("price");
      paramString = new Cart(str1, new Item(paramString.getString("articleId"), str3, str2, paramString.getString("currency"), d, i));
      return paramString;
    }
    catch (JSONException paramString)
    {
      Log.error("Cart|Error during json parsing", paramString);
    }
    return null;
  }
  
  public static Purchase c(String paramString)
  {
    try
    {
      paramString = new JSONObject(paramString);
      String str1 = paramString.getString("purchaseId");
      double d1 = paramString.getDouble("totalPrice");
      String str2 = paramString.getString("currency");
      if (paramString.isNull("items")) {
        paramString = null;
      }
      while (paramString != null)
      {
        int j = paramString.length();
        Item[] arrayOfItem = new Item[j];
        int i = 0;
        for (;;)
        {
          if (i < j)
          {
            JSONObject localJSONObject = paramString.getJSONObject(i);
            String str3 = localJSONObject.getString("id");
            int k = localJSONObject.getInt("quantity");
            String str4 = localJSONObject.getString("category");
            String str5 = localJSONObject.getString("label");
            double d2 = localJSONObject.getDouble("price");
            arrayOfItem[i] = new Item(str3, str5, str4, localJSONObject.getString("currency"), d2, k);
            i += 1;
            continue;
            paramString = paramString.getJSONArray("items");
            break;
          }
        }
        return new Purchase(str1, str2, d1, arrayOfItem);
      }
      paramString = new Purchase(str1, str2, d1);
      return paramString;
    }
    catch (JSONException paramString)
    {
      Log.error("Purchase|Error during json parsing", paramString);
    }
    return null;
  }
  
  public static Bundle d(String paramString)
  {
    try
    {
      Bundle localBundle = new Bundle();
      paramString = new JSONObject(paramString);
      Iterator localIterator = paramString.keys();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        localBundle.putString(str, paramString.getString(str));
      }
      return localBundle;
    }
    catch (JSONException paramString)
    {
      Log.error("UpdateDeviceInfo|Error during json parsing", paramString);
      return null;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */