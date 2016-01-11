package com.facebook.internal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookWebFallbackDialog
  extends WebDialog
{
  private static final int OS_BACK_BUTTON_RESPONSE_TIMEOUT_MILLISECONDS = 1500;
  private static final String TAG = FacebookWebFallbackDialog.class.getName();
  private boolean waitingForDialogToClose;
  
  public FacebookWebFallbackDialog(Context paramContext, String paramString1, String paramString2)
  {
    super(paramContext, paramString1);
    setExpectedRedirectUrl(paramString2);
  }
  
  public void cancel()
  {
    WebView localWebView = getWebView();
    if ((!isPageFinished()) || (isListenerCalled()) || (localWebView == null) || (!localWebView.isShown())) {
      super.cancel();
    }
    while (this.waitingForDialogToClose) {
      return;
    }
    this.waitingForDialogToClose = true;
    localWebView.loadUrl("javascript:" + "(function() {  var event = document.createEvent('Event');  event.initEvent('fbPlatformDialogMustClose',true,true);  document.dispatchEvent(event);})();");
    new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
    {
      public void run()
      {
        FacebookWebFallbackDialog.this.cancel();
      }
    }, 1500L);
  }
  
  protected Bundle parseResponseUri(String paramString)
  {
    Bundle localBundle = Utility.parseUrlQueryString(Uri.parse(paramString).getQuery());
    paramString = localBundle.getString("bridge_args");
    localBundle.remove("bridge_args");
    if (!Utility.isNullOrEmpty(paramString)) {}
    try
    {
      localBundle.putBundle("com.facebook.platform.protocol.BRIDGE_ARGS", BundleJSONConverter.convertToBundle(new JSONObject(paramString)));
      String str = localBundle.getString("method_results");
      localBundle.remove("method_results");
      if (!Utility.isNullOrEmpty(str))
      {
        paramString = str;
        if (Utility.isNullOrEmpty(str)) {
          paramString = "{}";
        }
      }
    }
    catch (JSONException paramString)
    {
      try
      {
        localBundle.putBundle("com.facebook.platform.protocol.RESULT_ARGS", BundleJSONConverter.convertToBundle(new JSONObject(paramString)));
        localBundle.remove("version");
        localBundle.putInt("com.facebook.platform.protocol.PROTOCOL_VERSION", NativeProtocol.getLatestKnownVersion());
        return localBundle;
        paramString = paramString;
        Utility.logd(TAG, "Unable to parse bridge_args JSON", paramString);
      }
      catch (JSONException paramString)
      {
        for (;;)
        {
          Utility.logd(TAG, "Unable to parse bridge_args JSON", paramString);
        }
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/internal/FacebookWebFallbackDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */