package com.facebook.internal;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.facebook.AccessToken;
import com.facebook.FacebookDialogException;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookServiceException;
import com.facebook.R.drawable;
import com.facebook.R.string;

public class WebDialog
  extends Dialog
{
  private static final int API_EC_DIALOG_CANCEL = 4201;
  private static final int BACKGROUND_GRAY = -872415232;
  static final String CANCEL_URI = "fbconnect://cancel";
  public static final int DEFAULT_THEME = 16973840;
  static final boolean DISABLE_SSL_CHECK_FOR_TESTING = false;
  private static final String DISPLAY_TOUCH = "touch";
  private static final String LOG_TAG = "FacebookSDK.WebDialog";
  private static final int MAX_PADDING_SCREEN_HEIGHT = 1280;
  private static final int MAX_PADDING_SCREEN_WIDTH = 800;
  private static final double MIN_SCALE_FACTOR = 0.5D;
  private static final int NO_PADDING_SCREEN_HEIGHT = 800;
  private static final int NO_PADDING_SCREEN_WIDTH = 480;
  static final String REDIRECT_URI = "fbconnect://success";
  private FrameLayout contentFrameLayout;
  private ImageView crossImageView;
  private String expectedRedirectUrl = "fbconnect://success";
  private boolean isDetached = false;
  private boolean isPageFinished = false;
  private boolean listenerCalled = false;
  private OnCompleteListener onCompleteListener;
  private ProgressDialog spinner;
  private String url;
  private WebView webView;
  
  public WebDialog(Context paramContext, String paramString)
  {
    this(paramContext, paramString, 16973840);
  }
  
  public WebDialog(Context paramContext, String paramString, int paramInt)
  {
    super(paramContext, paramInt);
    this.url = paramString;
  }
  
  public WebDialog(Context paramContext, String paramString, Bundle paramBundle, int paramInt, OnCompleteListener paramOnCompleteListener)
  {
    super(paramContext, paramInt);
    paramContext = paramBundle;
    if (paramBundle == null) {
      paramContext = new Bundle();
    }
    paramContext.putString("redirect_uri", "fbconnect://success");
    paramContext.putString("display", "touch");
    this.url = Utility.buildUri(ServerProtocol.getDialogAuthority(), ServerProtocol.getAPIVersion() + "/" + "dialog/" + paramString, paramContext).toString();
    this.onCompleteListener = paramOnCompleteListener;
  }
  
  private void createCrossImage()
  {
    this.crossImageView = new ImageView(getContext());
    this.crossImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        WebDialog.this.cancel();
      }
    });
    Drawable localDrawable = getContext().getResources().getDrawable(R.drawable.com_facebook_close);
    this.crossImageView.setImageDrawable(localDrawable);
    this.crossImageView.setVisibility(4);
  }
  
  private int getScaledSize(int paramInt1, float paramFloat, int paramInt2, int paramInt3)
  {
    int i = (int)(paramInt1 / paramFloat);
    double d;
    if (i <= paramInt2) {
      d = 1.0D;
    }
    for (;;)
    {
      return (int)(paramInt1 * d);
      if (i >= paramInt3) {
        d = 0.5D;
      } else {
        d = 0.5D + (paramInt3 - i) / (paramInt3 - paramInt2) * 0.5D;
      }
    }
  }
  
  @SuppressLint({"SetJavaScriptEnabled"})
  private void setUpWebView(int paramInt)
  {
    LinearLayout localLinearLayout = new LinearLayout(getContext());
    this.webView = new WebView(getContext().getApplicationContext())
    {
      public void onWindowFocusChanged(boolean paramAnonymousBoolean)
      {
        try
        {
          super.onWindowFocusChanged(paramAnonymousBoolean);
          return;
        }
        catch (NullPointerException localNullPointerException) {}
      }
    };
    this.webView.setVerticalScrollBarEnabled(false);
    this.webView.setHorizontalScrollBarEnabled(false);
    this.webView.setWebViewClient(new DialogWebViewClient(null));
    this.webView.getSettings().setJavaScriptEnabled(true);
    this.webView.loadUrl(this.url);
    this.webView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
    this.webView.setVisibility(4);
    this.webView.getSettings().setSavePassword(false);
    this.webView.getSettings().setSaveFormData(false);
    this.webView.setFocusable(true);
    this.webView.setFocusableInTouchMode(true);
    this.webView.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        if (!paramAnonymousView.hasFocus()) {
          paramAnonymousView.requestFocus();
        }
        return false;
      }
    });
    localLinearLayout.setPadding(paramInt, paramInt, paramInt, paramInt);
    localLinearLayout.addView(this.webView);
    localLinearLayout.setBackgroundColor(-872415232);
    this.contentFrameLayout.addView(localLinearLayout);
  }
  
  public void cancel()
  {
    if ((this.onCompleteListener != null) && (!this.listenerCalled)) {
      sendErrorToListener(new FacebookOperationCanceledException());
    }
  }
  
  public void dismiss()
  {
    if (this.webView != null) {
      this.webView.stopLoading();
    }
    if ((!this.isDetached) && (this.spinner != null) && (this.spinner.isShowing())) {
      this.spinner.dismiss();
    }
    super.dismiss();
  }
  
  public OnCompleteListener getOnCompleteListener()
  {
    return this.onCompleteListener;
  }
  
  protected WebView getWebView()
  {
    return this.webView;
  }
  
  protected boolean isListenerCalled()
  {
    return this.listenerCalled;
  }
  
  protected boolean isPageFinished()
  {
    return this.isPageFinished;
  }
  
  public void onAttachedToWindow()
  {
    this.isDetached = false;
    super.onAttachedToWindow();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.spinner = new ProgressDialog(getContext());
    this.spinner.requestWindowFeature(1);
    this.spinner.setMessage(getContext().getString(R.string.com_facebook_loading));
    this.spinner.setOnCancelListener(new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramAnonymousDialogInterface)
      {
        WebDialog.this.cancel();
      }
    });
    requestWindowFeature(1);
    this.contentFrameLayout = new FrameLayout(getContext());
    resize();
    getWindow().setGravity(17);
    getWindow().setSoftInputMode(16);
    createCrossImage();
    setUpWebView(this.crossImageView.getDrawable().getIntrinsicWidth() / 2 + 1);
    this.contentFrameLayout.addView(this.crossImageView, new ViewGroup.LayoutParams(-2, -2));
    setContentView(this.contentFrameLayout);
  }
  
  public void onDetachedFromWindow()
  {
    this.isDetached = true;
    super.onDetachedFromWindow();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4) {
      cancel();
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  protected void onStart()
  {
    super.onStart();
    resize();
  }
  
  protected Bundle parseResponseUri(String paramString)
  {
    paramString = Uri.parse(paramString);
    Bundle localBundle = Utility.parseUrlQueryString(paramString.getQuery());
    localBundle.putAll(Utility.parseUrlQueryString(paramString.getFragment()));
    return localBundle;
  }
  
  public void resize()
  {
    Display localDisplay = ((WindowManager)getContext().getSystemService("window")).getDefaultDisplay();
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    localDisplay.getMetrics(localDisplayMetrics);
    int i;
    if (localDisplayMetrics.widthPixels < localDisplayMetrics.heightPixels)
    {
      i = localDisplayMetrics.widthPixels;
      if (localDisplayMetrics.widthPixels >= localDisplayMetrics.heightPixels) {
        break label141;
      }
    }
    label141:
    for (int j = localDisplayMetrics.heightPixels;; j = localDisplayMetrics.widthPixels)
    {
      i = Math.min(getScaledSize(i, localDisplayMetrics.density, 480, 800), localDisplayMetrics.widthPixels);
      j = Math.min(getScaledSize(j, localDisplayMetrics.density, 800, 1280), localDisplayMetrics.heightPixels);
      getWindow().setLayout(i, j);
      return;
      i = localDisplayMetrics.heightPixels;
      break;
    }
  }
  
  protected void sendErrorToListener(Throwable paramThrowable)
  {
    if ((this.onCompleteListener != null) && (!this.listenerCalled))
    {
      this.listenerCalled = true;
      if (!(paramThrowable instanceof FacebookException)) {
        break label47;
      }
    }
    label47:
    for (paramThrowable = (FacebookException)paramThrowable;; paramThrowable = new FacebookException(paramThrowable))
    {
      this.onCompleteListener.onComplete(null, paramThrowable);
      dismiss();
      return;
    }
  }
  
  protected void sendSuccessToListener(Bundle paramBundle)
  {
    if ((this.onCompleteListener != null) && (!this.listenerCalled))
    {
      this.listenerCalled = true;
      this.onCompleteListener.onComplete(paramBundle, null);
      dismiss();
    }
  }
  
  protected void setExpectedRedirectUrl(String paramString)
  {
    this.expectedRedirectUrl = paramString;
  }
  
  public void setOnCompleteListener(OnCompleteListener paramOnCompleteListener)
  {
    this.onCompleteListener = paramOnCompleteListener;
  }
  
  public static class Builder
  {
    private AccessToken accessToken;
    private String action;
    private String applicationId;
    private Context context;
    private WebDialog.OnCompleteListener listener;
    private Bundle parameters;
    private int theme = 16973840;
    
    public Builder(Context paramContext, String paramString, Bundle paramBundle)
    {
      this.accessToken = AccessToken.getCurrentAccessToken();
      if (this.accessToken == null)
      {
        String str = Utility.getMetadataApplicationId(paramContext);
        if (str != null) {
          this.applicationId = str;
        }
      }
      else
      {
        finishInit(paramContext, paramString, paramBundle);
        return;
      }
      throw new FacebookException("Attempted to create a builder without a valid access token or a valid default Application ID.");
    }
    
    public Builder(Context paramContext, String paramString1, String paramString2, Bundle paramBundle)
    {
      String str = paramString1;
      if (paramString1 == null) {
        str = Utility.getMetadataApplicationId(paramContext);
      }
      Validate.notNullOrEmpty(str, "applicationId");
      this.applicationId = str;
      finishInit(paramContext, paramString2, paramBundle);
    }
    
    private void finishInit(Context paramContext, String paramString, Bundle paramBundle)
    {
      this.context = paramContext;
      this.action = paramString;
      if (paramBundle != null)
      {
        this.parameters = paramBundle;
        return;
      }
      this.parameters = new Bundle();
    }
    
    public WebDialog build()
    {
      if (this.accessToken != null)
      {
        this.parameters.putString("app_id", this.accessToken.getApplicationId());
        this.parameters.putString("access_token", this.accessToken.getToken());
      }
      for (;;)
      {
        return new WebDialog(this.context, this.action, this.parameters, this.theme, this.listener);
        this.parameters.putString("app_id", this.applicationId);
      }
    }
    
    public String getApplicationId()
    {
      return this.applicationId;
    }
    
    public Context getContext()
    {
      return this.context;
    }
    
    public WebDialog.OnCompleteListener getListener()
    {
      return this.listener;
    }
    
    public Bundle getParameters()
    {
      return this.parameters;
    }
    
    public int getTheme()
    {
      return this.theme;
    }
    
    public Builder setOnCompleteListener(WebDialog.OnCompleteListener paramOnCompleteListener)
    {
      this.listener = paramOnCompleteListener;
      return this;
    }
    
    public Builder setTheme(int paramInt)
    {
      this.theme = paramInt;
      return this;
    }
  }
  
  private class DialogWebViewClient
    extends WebViewClient
  {
    private DialogWebViewClient() {}
    
    public void onPageFinished(WebView paramWebView, String paramString)
    {
      super.onPageFinished(paramWebView, paramString);
      if (!WebDialog.this.isDetached) {
        WebDialog.this.spinner.dismiss();
      }
      WebDialog.this.contentFrameLayout.setBackgroundColor(0);
      WebDialog.this.webView.setVisibility(0);
      WebDialog.this.crossImageView.setVisibility(0);
      WebDialog.access$702(WebDialog.this, true);
    }
    
    public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
    {
      Utility.logd("FacebookSDK.WebDialog", "Webview loading URL: " + paramString);
      super.onPageStarted(paramWebView, paramString, paramBitmap);
      if (!WebDialog.this.isDetached) {
        WebDialog.this.spinner.show();
      }
    }
    
    public void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2)
    {
      super.onReceivedError(paramWebView, paramInt, paramString1, paramString2);
      WebDialog.this.sendErrorToListener(new FacebookDialogException(paramString1, paramInt, paramString2));
    }
    
    public void onReceivedSslError(WebView paramWebView, SslErrorHandler paramSslErrorHandler, SslError paramSslError)
    {
      super.onReceivedSslError(paramWebView, paramSslErrorHandler, paramSslError);
      paramSslErrorHandler.cancel();
      WebDialog.this.sendErrorToListener(new FacebookDialogException(null, -11, null));
    }
    
    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      Utility.logd("FacebookSDK.WebDialog", "Redirect URL: " + paramString);
      if (paramString.startsWith(WebDialog.this.expectedRedirectUrl))
      {
        Bundle localBundle = WebDialog.this.parseResponseUri(paramString);
        paramWebView = localBundle.getString("error");
        paramString = paramWebView;
        if (paramWebView == null) {
          paramString = localBundle.getString("error_type");
        }
        Object localObject = localBundle.getString("error_msg");
        paramWebView = (WebView)localObject;
        if (localObject == null) {
          paramWebView = localBundle.getString("error_message");
        }
        localObject = paramWebView;
        if (paramWebView == null) {
          localObject = localBundle.getString("error_description");
        }
        paramWebView = localBundle.getString("error_code");
        int i = -1;
        if (!Utility.isNullOrEmpty(paramWebView)) {}
        try
        {
          i = Integer.parseInt(paramWebView);
          if ((Utility.isNullOrEmpty(paramString)) && (Utility.isNullOrEmpty((String)localObject)) && (i == -1))
          {
            WebDialog.this.sendSuccessToListener(localBundle);
            return true;
          }
        }
        catch (NumberFormatException paramWebView)
        {
          for (;;)
          {
            i = -1;
          }
          if ((paramString != null) && ((paramString.equals("access_denied")) || (paramString.equals("OAuthAccessDeniedException"))))
          {
            WebDialog.this.cancel();
            return true;
          }
          if (i == 4201)
          {
            WebDialog.this.cancel();
            return true;
          }
          paramWebView = new FacebookRequestError(i, paramString, (String)localObject);
          WebDialog.this.sendErrorToListener(new FacebookServiceException(paramWebView, (String)localObject));
          return true;
        }
      }
      if (paramString.startsWith("fbconnect://cancel"))
      {
        WebDialog.this.cancel();
        return true;
      }
      if (paramString.contains("touch")) {
        return false;
      }
      WebDialog.this.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(paramString)));
      return true;
    }
  }
  
  public static abstract interface OnCompleteListener
  {
    public abstract void onComplete(Bundle paramBundle, FacebookException paramFacebookException);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/internal/WebDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */