package com.ad4screen.sdk.service.modules.inapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.R.id;
import com.ad4screen.sdk.common.compatibility.k.b;
import com.ad4screen.sdk.common.compatibility.k.b.a;
import com.ad4screen.sdk.common.compatibility.k.m;
import com.ad4screen.sdk.common.e;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.model.displayformats.a;
import com.ad4screen.sdk.model.displayformats.b;
import com.ad4screen.sdk.service.modules.tracking.webview.c;
import com.ad4screen.sdk.service.modules.tracking.webview.d;

public class DisplayView
  extends FrameLayout
{
  private b a;
  private b b;
  private WebView c;
  private ToggleButton d;
  private Button e;
  private ProgressBar f;
  private a g;
  private Button h;
  private Button i;
  private View j;
  private int k = -1;
  private int l;
  private View m;
  private boolean n;
  private final View.OnClickListener o = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      if (DisplayView.c(DisplayView.this) == null) {
        return;
      }
      if (DisplayView.d(DisplayView.this).isChecked())
      {
        DisplayView.c(DisplayView.this).reload();
        return;
      }
      DisplayView.c(DisplayView.this).stopLoading();
    }
  };
  private final View.OnClickListener p = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      DisplayView.h(DisplayView.this);
    }
  };
  private final View.OnClickListener q = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      if (DisplayView.a(DisplayView.this) != null) {
        DisplayView.a(DisplayView.this).d(DisplayView.this);
      }
    }
  };
  private final View.OnClickListener r = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      if (DisplayView.c(DisplayView.this) != null)
      {
        if (DisplayView.c(DisplayView.this).canGoBack()) {
          DisplayView.c(DisplayView.this).goBack();
        }
        DisplayView.e(DisplayView.this);
      }
    }
  };
  private final View.OnClickListener s = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      if (DisplayView.c(DisplayView.this) != null)
      {
        if (DisplayView.c(DisplayView.this).canGoForward()) {
          DisplayView.c(DisplayView.this).goForward();
        }
        DisplayView.e(DisplayView.this);
      }
    }
  };
  
  public DisplayView(Context paramContext)
  {
    super(paramContext);
    f();
  }
  
  public DisplayView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    f();
  }
  
  public DisplayView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    f();
  }
  
  private <V> V a(Class<V> paramClass, int paramInt)
  {
    View localView = this.m.findViewById(paramInt);
    if (paramClass.isInstance(localView)) {
      return (V)paramClass.cast(localView);
    }
    return null;
  }
  
  private void a(View... paramVarArgs)
  {
    int i1 = 0;
    while (i1 < paramVarArgs.length)
    {
      if ((paramVarArgs[i1] != null) && (paramVarArgs[i1].getVisibility() != 8)) {
        paramVarArgs[i1].setVisibility(8);
      }
      i1 += 1;
    }
  }
  
  private void f()
  {
    if (getVisibility() != 8) {
      setVisibility(8);
    }
  }
  
  private void g()
  {
    k();
    if (getVisibility() != 8) {
      setVisibility(8);
    }
  }
  
  private void h()
  {
    this.f = ((ProgressBar)a(ProgressBar.class, R.id.com_ad4screen_sdk_progress));
    this.c = ((WebView)a(WebView.class, R.id.com_ad4screen_sdk_webview));
    this.d = ((ToggleButton)a(ToggleButton.class, R.id.com_ad4screen_sdk_reloadbutton));
    this.h = ((Button)a(Button.class, R.id.com_ad4screen_sdk_backbutton));
    this.i = ((Button)a(Button.class, R.id.com_ad4screen_sdk_forwardbutton));
    this.e = ((Button)a(Button.class, R.id.com_ad4screen_sdk_closebutton));
    Button localButton = (Button)a(Button.class, R.id.com_ad4screen_sdk_browsebutton);
    TextView localTextView1 = (TextView)a(TextView.class, R.id.com_ad4screen_sdk_title);
    TextView localTextView2 = (TextView)a(TextView.class, R.id.com_ad4screen_sdk_body);
    ImageView localImageView = (ImageView)a(ImageView.class, R.id.com_ad4screen_sdk_logo);
    if (this.d != null)
    {
      this.d.setChecked(false);
      this.d.setOnClickListener(this.o);
    }
    if (this.h != null)
    {
      this.h.setEnabled(false);
      this.h.setOnClickListener(this.r);
    }
    if (this.i != null)
    {
      this.i.setEnabled(false);
      this.i.setOnClickListener(this.s);
    }
    if (localButton != null) {
      localButton.setOnClickListener(this.q);
    }
    if ((this.c != null) && (this.a.c != null))
    {
      setupWebView(this.c);
      this.c.setVisibility(0);
      this.c.loadUrl(h.a(getContext(), this.a.c, new e[0]));
      if (localTextView1 != null)
      {
        if (this.a.a != null) {
          break label443;
        }
        localTextView1.setText(i.e(getContext()));
      }
    }
    for (;;)
    {
      localTextView1.setVisibility(0);
      if ((localTextView2 != null) && (this.a.b != null))
      {
        localTextView2.setText(this.a.b);
        localTextView2.setVisibility(0);
      }
      if (localImageView != null) {
        localImageView.setImageDrawable(getResources().getDrawable(i.d(getContext())));
      }
      if (this.e != null) {
        this.e.setOnClickListener(this.p);
      }
      return;
      a(new View[] { this.f, this.d, this.h, this.i, localButton });
      break;
      label443:
      localTextView1.setText(this.a.a);
    }
  }
  
  private void i()
  {
    Object localObject1 = this.m.getLayoutParams();
    Object localObject2 = getLayoutParams();
    ((ViewGroup.LayoutParams)localObject2).width = ((ViewGroup.LayoutParams)localObject1).width;
    ((ViewGroup.LayoutParams)localObject2).height = ((ViewGroup.LayoutParams)localObject1).height;
    setLayoutParams((ViewGroup.LayoutParams)localObject2);
    if ((this.l & 0x2) != 0) {
      j();
    }
    if ((this.l & 0x1) != 0)
    {
      localObject2 = new a(getContext());
      ((a)localObject2).setLayoutParams(new FrameLayout.LayoutParams(((ViewGroup.LayoutParams)localObject1).width, ((ViewGroup.LayoutParams)localObject1).height));
      ((a)localObject2).addView(this.m);
      addView((View)localObject2);
      if (this.e != null)
      {
        localObject1 = this.e.getParent();
        if ((localObject1 instanceof ViewGroup)) {
          ((ViewGroup)localObject1).removeView(this.e);
        }
        int i1 = i.a(getContext(), 33);
        addView(this.e, new FrameLayout.LayoutParams(i1, i1, 53));
      }
      return;
    }
    addView(this.m);
  }
  
  private void j()
  {
    if (getVisibility() == 0) {}
    do
    {
      return;
      setVisibility(0);
    } while ((this.a == null) || (this.a.e == null));
    k.b.a(getContext(), this, this.a.e, null);
  }
  
  private void k()
  {
    e();
    removeAllViews();
    this.n = false;
    this.a = null;
  }
  
  private void l()
  {
    j();
    if ((this.b != null) && (!this.n))
    {
      this.n = true;
      this.b.a(this);
    }
  }
  
  private void m()
  {
    if (this.c != null)
    {
      if (this.h != null) {
        this.h.setEnabled(this.c.canGoBack());
      }
      if (this.i != null) {
        this.i.setEnabled(this.c.canGoForward());
      }
    }
  }
  
  private void n()
  {
    if (this.b != null) {
      this.b.b(this);
    }
  }
  
  @SuppressLint({"SetJavaScriptEnabled"})
  private void setupWebView(WebView paramWebView)
  {
    WebSettings localWebSettings = paramWebView.getSettings();
    k.m.d(paramWebView, true);
    localWebSettings.setSupportZoom(true);
    localWebSettings.setBuiltInZoomControls(true);
    localWebSettings.setSupportZoom(true);
    localWebSettings.setUseWideViewPort(true);
    k.m.b(paramWebView, false);
    k.m.c(paramWebView, true);
    k.m.a(paramWebView, true);
    paramWebView.setScrollBarStyle(33554432);
    k.m.a(paramWebView);
    paramWebView.setWebViewClient(new WebViewClient()
    {
      private boolean b;
      
      private void a(String paramAnonymousString)
      {
        d.a(paramAnonymousString).a(new c(A4S.get(DisplayView.this.getContext()), DisplayView.a(DisplayView.this), DisplayView.this));
      }
      
      public void doUpdateVisitedHistory(WebView paramAnonymousWebView, String paramAnonymousString, boolean paramAnonymousBoolean)
      {
        DisplayView.e(DisplayView.this);
      }
      
      public void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString)
      {
        paramAnonymousString = paramAnonymousWebView.getBackground();
        if (((paramAnonymousString instanceof ColorDrawable)) && (((ColorDrawable)paramAnonymousString).getAlpha() == 0)) {
          paramAnonymousWebView.setBackgroundColor(0);
        }
        if (DisplayView.f(DisplayView.this) != null) {
          DisplayView.f(DisplayView.this).setVisibility(8);
        }
        if (DisplayView.d(DisplayView.this) != null) {
          DisplayView.d(DisplayView.this).setChecked(false);
        }
        if (!this.b) {
          DisplayView.g(DisplayView.this);
        }
        while (DisplayView.a(DisplayView.this) == null) {
          return;
        }
        DisplayView.a(DisplayView.this).e(DisplayView.this);
      }
      
      public void onPageStarted(WebView paramAnonymousWebView, String paramAnonymousString, Bitmap paramAnonymousBitmap)
      {
        DisplayView.e(DisplayView.this);
        if (DisplayView.f(DisplayView.this) != null) {
          DisplayView.f(DisplayView.this).setVisibility(0);
        }
        if (DisplayView.d(DisplayView.this) != null) {
          DisplayView.d(DisplayView.this).setChecked(true);
        }
      }
      
      public void onReceivedError(WebView paramAnonymousWebView, int paramAnonymousInt, String paramAnonymousString1, String paramAnonymousString2)
      {
        this.b = true;
      }
      
      public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
      {
        a(paramAnonymousString);
        if (paramAnonymousString.startsWith("http"))
        {
          paramAnonymousWebView.loadUrl(paramAnonymousString);
          return true;
        }
        try
        {
          paramAnonymousWebView = Intent.parseUri(paramAnonymousString, 1);
          DisplayView.this.getContext().startActivity(paramAnonymousWebView);
          return true;
        }
        catch (Exception paramAnonymousWebView)
        {
          Log.error("Error while browsing url : " + paramAnonymousString, paramAnonymousWebView);
        }
        return true;
      }
    });
  }
  
  public void a(b paramb, int paramInt)
  {
    int i1 = 0;
    k();
    if (paramb == null) {
      Log.debug("DisplayView|No banner provided, skipping display");
    }
    do
    {
      for (;;)
      {
        return;
        this.a = paramb;
        if (this.a.d != null)
        {
          paramb = getContext().getPackageName();
          i1 = getResources().getIdentifier(this.a.d, "layout", paramb);
        }
        if (i1 == 0)
        {
          Log.warn("DisplayView|Wrong banner template provided : " + this.a.d + " using default");
          if (this.b != null) {
            this.b.e(this);
          }
        }
        else
        {
          try
          {
            this.m = LayoutInflater.from(getContext()).inflate(i1, this, false);
            h();
            this.l = paramInt;
            if ((this.c == null) && ((this.l & 0x1) == 0)) {
              this.l = (paramInt | 0x1);
            }
            i();
            if (this.c == null)
            {
              l();
              return;
            }
          }
          catch (InflateException paramb)
          {
            Log.error("DisplayView|A fatal error occured when inflating template " + this.a.d + " or default template. Aborting display..", paramb);
          }
        }
      }
    } while (this.b == null);
    this.b.e(this);
  }
  
  public boolean a()
  {
    return (this.a.c == null) || (!this.a.c.contains("|o|"));
  }
  
  public boolean b()
  {
    if (this.c != null) {
      return this.c.canGoBack();
    }
    return false;
  }
  
  public void c()
  {
    this.c.goBack();
  }
  
  public void d()
  {
    k.b.a local1 = new k.b.a()
    {
      public void a() {}
      
      public void b()
      {
        DisplayView.b(DisplayView.this);
      }
    };
    if ((this.a != null) && (this.a.f != null)) {
      if (!k.b.a(getContext(), this, this.a.f, local1)) {
        g();
      }
    }
    for (;;)
    {
      this.b = null;
      this.j = null;
      this.k = -1;
      return;
      g();
    }
  }
  
  public void e()
  {
    if (this.c != null) {
      this.c.stopLoading();
    }
  }
  
  public a getBanner()
  {
    return this.g;
  }
  
  public int getLayout()
  {
    return this.k;
  }
  
  public View getParentView()
  {
    return this.j;
  }
  
  public void setBanner(a parama)
  {
    this.g = parama;
  }
  
  public void setDelegate(b paramb)
  {
    this.b = paramb;
  }
  
  public void setLayout(int paramInt)
  {
    this.k = paramInt;
  }
  
  public void setParentView(View paramView)
  {
    this.j = paramView;
  }
  
  @SuppressLint({"ClickableViewAccessibility"})
  private class a
    extends FrameLayout
  {
    public a(Context paramContext)
    {
      super();
    }
    
    public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
    {
      return paramMotionEvent.getAction() == 0;
    }
    
    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      if (paramMotionEvent.getAction() == 0)
      {
        if (DisplayView.a(DisplayView.this) != null) {
          DisplayView.a(DisplayView.this).c(DisplayView.this);
        }
        return true;
      }
      return false;
    }
  }
  
  public static abstract interface b
  {
    public abstract void a(DisplayView paramDisplayView);
    
    public abstract void b(DisplayView paramDisplayView);
    
    public abstract void c(DisplayView paramDisplayView);
    
    public abstract void d(DisplayView paramDisplayView);
    
    public abstract void e(DisplayView paramDisplayView);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/DisplayView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */