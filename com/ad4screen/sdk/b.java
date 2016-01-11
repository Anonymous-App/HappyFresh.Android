package com.ad4screen.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.FrameLayout.LayoutParams;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import com.ad4screen.sdk.plugins.model.Beacon;
import java.util.ArrayList;

final class b
  extends A4S
{
  protected void a() {}
  
  protected void a(int paramInt, A4S.Callback<Inbox> paramCallback, boolean paramBoolean)
  {
    if (paramCallback != null) {
      paramCallback.onResult(null);
    }
  }
  
  protected void a(Context paramContext, boolean paramBoolean1, boolean paramBoolean2) {}
  
  protected void a(String paramString) {}
  
  protected void a(String paramString, A4S.Callback<Message> paramCallback)
  {
    if (paramCallback != null) {
      paramCallback.onResult(null);
    }
  }
  
  protected void a(String paramString1, String paramString2, String[] paramArrayOfString) {}
  
  protected void b() {}
  
  protected void b(String paramString) {}
  
  protected void c() {}
  
  protected void c(String paramString) {}
  
  protected void d(String paramString) {}
  
  public void displayInApp(String paramString) {}
  
  public void getA4SId(A4S.Callback<String> paramCallback)
  {
    if (paramCallback != null) {
      paramCallback.onResult(null);
    }
  }
  
  public String getAndroidId()
  {
    return "";
  }
  
  protected ArrayList<Beacon> getBeacons()
  {
    return null;
  }
  
  public void getIDFV(A4S.Callback<String> paramCallback)
  {
    if (paramCallback != null) {
      paramCallback.onResult(null);
    }
  }
  
  public void getInbox(A4S.Callback<Inbox> paramCallback)
  {
    if (paramCallback != null) {
      paramCallback.onResult(null);
    }
  }
  
  protected void handleGeofencingMessage(Bundle paramBundle) {}
  
  protected void handlePushMessage(Bundle paramBundle) {}
  
  public void isGCMEnabled(A4S.Callback<Boolean> paramCallback)
  {
    if (paramCallback != null) {
      paramCallback.onResult(Boolean.valueOf(false));
    }
  }
  
  public void isInAppDisplayLocked(A4S.Callback<Boolean> paramCallback)
  {
    if (paramCallback != null) {
      paramCallback.onResult(Boolean.valueOf(false));
    }
  }
  
  public void isPushEnabled(A4S.Callback<Boolean> paramCallback)
  {
    if (paramCallback != null) {
      paramCallback.onResult(Boolean.valueOf(false));
    }
  }
  
  public boolean isPushNotificationLocked()
  {
    return false;
  }
  
  public void isRestrictedConnection(A4S.Callback<Boolean> paramCallback)
  {
    if (paramCallback != null) {
      paramCallback.onResult(Boolean.valueOf(false));
    }
  }
  
  public void putState(String paramString1, String paramString2) {}
  
  public void resetOverlayPosition() {}
  
  public void sendGCMToken(String paramString) {}
  
  public void sendPushToken(String paramString) {}
  
  public void setGCMEnabled(boolean paramBoolean) {}
  
  public void setInAppClickedCallback(A4S.Callback<InApp> paramCallback, int... paramVarArgs) {}
  
  public void setInAppClosedCallback(A4S.Callback<InApp> paramCallback, int... paramVarArgs) {}
  
  public void setInAppDisplayLocked(boolean paramBoolean) {}
  
  public void setInAppDisplayedCallback(A4S.Callback<InApp> paramCallback, int... paramVarArgs) {}
  
  public void setInAppReadyCallback(boolean paramBoolean, A4S.Callback<InApp> paramCallback, int... paramVarArgs) {}
  
  public void setIntent(Intent paramIntent) {}
  
  public void setOverlayPosition(FrameLayout.LayoutParams paramLayoutParams) {}
  
  public void setPushEnabled(boolean paramBoolean) {}
  
  public void setPushNotificationLocked(boolean paramBoolean) {}
  
  public void setRestrictedConnection(boolean paramBoolean) {}
  
  public void setView(String paramString) {}
  
  public void startActivity(Activity paramActivity) {}
  
  public void stopActivity(Activity paramActivity) {}
  
  public void trackAddToCart(Cart paramCart) {}
  
  public void trackEvent(long paramLong, String... paramVarArgs) {}
  
  public void trackLead(Lead paramLead) {}
  
  public void trackPurchase(Purchase paramPurchase) {}
  
  protected void triggerBeacons(Bundle paramBundle) {}
  
  public void updateDeviceInfo(Bundle paramBundle) {}
  
  public void updateGeolocation(Location paramLocation) {}
  
  public void updateMessages(Inbox paramInbox) {}
  
  protected void updatePushRegistration(Bundle paramBundle) {}
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */