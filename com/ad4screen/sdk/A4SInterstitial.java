package com.ad4screen.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.model.displayformats.b;
import com.ad4screen.sdk.model.displayformats.d;
import com.ad4screen.sdk.model.displayformats.e.a;
import com.ad4screen.sdk.service.modules.inapp.DisplayView;
import com.ad4screen.sdk.service.modules.inapp.DisplayView.b;
import com.ad4screen.sdk.service.modules.inapp.c.d;
import com.ad4screen.sdk.service.modules.inapp.c.f;
import com.ad4screen.sdk.systems.f;
import org.json.JSONException;
import org.json.JSONObject;

@API
public class A4SInterstitial
  extends Activity
{
  public static final int FLAG_INAPP = 1;
  private static com.ad4screen.sdk.common.persistence.e a = new com.ad4screen.sdk.common.persistence.e();
  private DisplayView b;
  private boolean c;
  private com.ad4screen.sdk.model.displayformats.e d;
  private int e;
  
  private void a()
  {
    if (this.c) {}
    do
    {
      return;
      this.c = true;
    } while ((this.e & 0x1) == 0);
    f.a().a(new c.f(this.d.i, -1, "com_ad4screen_sdk_theme_interstitial", null));
  }
  
  private void b()
  {
    FrameLayout localFrameLayout = new FrameLayout(this);
    this.b = new DisplayView(this);
    setContentView(localFrameLayout, new ViewGroup.LayoutParams(-1, -1));
    localFrameLayout.addView(this.b, new ViewGroup.LayoutParams(-1, -1));
    this.b.a(this.d.a, 2);
    this.b.setDelegate(new DisplayView.b()
    {
      public void a(DisplayView paramAnonymousDisplayView) {}
      
      public void b(DisplayView paramAnonymousDisplayView)
      {
        if (!A4SInterstitial.this.isFinishing()) {
          A4SInterstitial.this.finish();
        }
      }
      
      public void c(DisplayView paramAnonymousDisplayView) {}
      
      public void d(DisplayView paramAnonymousDisplayView)
      {
        if ((A4SInterstitial.a(A4SInterstitial.this) != null) && (A4SInterstitial.a(A4SInterstitial.this).a != null) && (A4SInterstitial.a(A4SInterstitial.this).a.c != null) && (A4SInterstitial.a(A4SInterstitial.this).a.c.length() != 0)) {}
        try
        {
          A4SInterstitial.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(h.a(paramAnonymousDisplayView.getContext(), A4SInterstitial.a(A4SInterstitial.this).a.c, new com.ad4screen.sdk.common.e[0]))));
          return;
        }
        catch (ActivityNotFoundException paramAnonymousDisplayView)
        {
          Log.error("Failed to load interstitial url : '" + A4SInterstitial.a(A4SInterstitial.this).a.c + "'", paramAnonymousDisplayView);
        }
      }
      
      public void e(DisplayView paramAnonymousDisplayView)
      {
        Log.error("Failed to load interstitial url : '" + A4SInterstitial.a(A4SInterstitial.this).a.c + "'");
        if (!A4SInterstitial.this.isFinishing()) {
          A4SInterstitial.this.finish();
        }
      }
    });
  }
  
  public static Intent build(Context paramContext, int paramInt, com.ad4screen.sdk.model.displayformats.e parame)
  {
    return build(paramContext, paramInt, parame, null);
  }
  
  public static Intent build(Context paramContext, int paramInt, com.ad4screen.sdk.model.displayformats.e parame, Bundle paramBundle)
  {
    paramContext = new Intent(paramContext, A4SInterstitial.class);
    paramContext.putExtra("com.ad4screen.sdk.A4SIntersticial.flags", paramInt);
    if (paramBundle != null) {
      paramContext.putExtra("com.ad4screen.sdk.A4SIntersticial.payload", paramBundle);
    }
    try
    {
      parame = a.a(parame).toString();
      if (parame != null)
      {
        paramContext.putExtra("com.ad4screen.sdk.A4SIntersticial.format", parame);
        return paramContext;
      }
      Log.error("A4SIntersticial|Could not serialize Intersticial as JSON");
      return paramContext;
    }
    catch (JSONException parame)
    {
      Log.internal("A4SIntersticial|Error while serializing Format", parame);
    }
    return paramContext;
  }
  
  @TargetApi(5)
  public void onBackPressed()
  {
    if (this.b.b()) {
      this.b.c();
    }
    while (isFinishing()) {
      return;
    }
    finish();
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    if (!this.b.a())
    {
      Bundle localBundle = new Bundle();
      onSaveInstanceState(localBundle);
      onCreate(localBundle);
    }
    super.onConfigurationChanged(paramConfiguration);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle == null)
    {
      this.c = false;
      paramBundle = getIntent().getExtras();
      if (paramBundle != null) {
        break label57;
      }
      Log.warn("A4SIntersticial|No extras found");
      if (!isFinishing()) {
        finish();
      }
    }
    for (;;)
    {
      return;
      this.c = paramBundle.getBoolean("com.ad4screen.sdk.A4SIntersticial.displayTracked", false);
      break;
      label57:
      this.e = paramBundle.getInt("com.ad4screen.sdk.A4SIntersticial.flags", 0);
      paramBundle = paramBundle.getString("com.ad4screen.sdk.A4SIntersticial.format");
      if (paramBundle == null)
      {
        Log.warn("A4SIntersticial|No format found");
        if (!isFinishing()) {
          finish();
        }
      }
      else
      {
        try
        {
          this.d = ((com.ad4screen.sdk.model.displayformats.e)a.a(paramBundle, new d()));
          if (this.d == null)
          {
            Log.warn("A4SIntersticial|Invalid Intersticial format found");
            if (isFinishing()) {
              continue;
            }
            finish();
          }
        }
        catch (JSONException paramBundle)
        {
          for (;;)
          {
            Log.internal("A4SIntersticial|Error while deserializing LandingPage", paramBundle);
          }
          if ((this.d.a.b == null) && (this.d.a.c == null))
          {
            Log.debug("A4SIntersticial|Intersticial should have content to be displayed properly");
            if (!isFinishing()) {
              finish();
            }
          }
          else if ((this.d.b == e.a.b) && (this.d.a.c == null))
          {
            Log.debug("A4SIntersticial|We can't open system intent without intent uri. Please specify an uri for this notification");
            if (!isFinishing()) {
              finish();
            }
          }
          else
          {
            a();
            if (this.d.b == e.a.b) {
              try
              {
                startActivity(Intent.parseUri(this.d.a.c, 1));
                if (isFinishing()) {
                  continue;
                }
                finish();
                return;
              }
              catch (Exception paramBundle)
              {
                for (;;)
                {
                  Log.warn("A4SIntersticial|Error while opening intersticial with url: " + this.d.a.c, paramBundle);
                }
              }
            } else {
              b();
            }
          }
        }
      }
    }
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      if (this.b.b()) {
        this.b.c();
      }
      for (;;)
      {
        return true;
        if (!isFinishing()) {
          finish();
        }
      }
    }
    return false;
  }
  
  protected void onPause()
  {
    super.onPause();
    if (this.b != null) {
      this.b.e();
    }
    if ((isFinishing()) && ((this.e & 0x1) != 0)) {
      f.a().a(new c.d(this.d.i, -1, "com_ad4screen_sdk_theme_interstitial", false));
    }
    A4S.get(getApplicationContext()).b();
  }
  
  protected void onResume()
  {
    super.onResume();
    A4S.get(getApplicationContext()).a();
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBoolean("com.ad4screen.sdk.A4SIntersticial.displayTracked", this.c);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/A4SInterstitial.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */