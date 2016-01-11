package com.happyfresh.receivers;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import com.happyfresh.activities.MainActivity;
import com.happyfresh.activities.OrderStatusActivity;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.models.Order;
import com.parse.ParsePushBroadcastReceiver;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class ICartParsePushBroadcastReceiver
  extends ParsePushBroadcastReceiver
{
  public final String TAG = getClass().getSimpleName();
  
  protected Notification getNotification(Context paramContext, Intent paramIntent)
  {
    paramIntent = super.getNotification(paramContext, paramIntent);
    if ((paramIntent != null) && (Build.VERSION.SDK_INT >= 21)) {
      paramIntent.color = paramContext.getResources().getColor(2131493006);
    }
    return paramIntent;
  }
  
  protected int getSmallIconId(Context paramContext, Intent paramIntent)
  {
    return 2130837791;
  }
  
  protected void onPushOpen(Context paramContext, Intent paramIntent)
  {
    String str = paramIntent.getExtras().getString("com.parse.Data");
    try
    {
      str = new JSONObject(str).getString("order_number");
      Intent localIntent1 = new Intent(paramContext, MainActivity.class);
      localIntent1.setFlags(localIntent1.getFlags() | 0x8000 | 0x10000000);
      Intent localIntent2 = new Intent(paramContext, OrderStatusActivity.class);
      localIntent2.putExtra("ICartConstant.KEYS.ORDER_NUMBER", str);
      paramContext.startActivities(new Intent[] { localIntent1, localIntent2 });
      return;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      super.onPushOpen(paramContext, paramIntent);
    }
  }
  
  protected void onPushReceive(Context paramContext, Intent paramIntent)
  {
    super.onPushReceive(paramContext, paramIntent);
    paramIntent = paramIntent.getExtras().getString("com.parse.Data");
    try
    {
      paramIntent = new JSONObject(paramIntent).optString("order_number");
      if (!TextUtils.isEmpty(paramIntent))
      {
        paramContext = ((ICartApplication)paramContext.getApplicationContext()).getOrderList();
        if (paramContext.size() > 0)
        {
          paramContext = paramContext.iterator();
          while (paramContext.hasNext())
          {
            Order localOrder = (Order)paramContext.next();
            if (localOrder.number.equals(paramIntent)) {
              localOrder.isMustSync = true;
            }
          }
        }
      }
      return;
    }
    catch (JSONException paramContext)
    {
      paramContext.printStackTrace();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/receivers/ICartParsePushBroadcastReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */