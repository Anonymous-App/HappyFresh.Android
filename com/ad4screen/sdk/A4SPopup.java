package com.ad4screen.sdk;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.compatibility.k.d;
import com.ad4screen.sdk.common.compatibility.k.f;
import com.ad4screen.sdk.common.compatibility.k.i;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.common.persistence.e;
import com.ad4screen.sdk.model.displayformats.d;
import com.ad4screen.sdk.model.displayformats.g;
import com.ad4screen.sdk.model.displayformats.g.a;
import com.ad4screen.sdk.service.modules.inapp.c.c;
import com.ad4screen.sdk.service.modules.inapp.c.d;
import com.ad4screen.sdk.service.modules.inapp.c.f;
import com.ad4screen.sdk.systems.f.a;
import org.json.JSONException;
import org.json.JSONObject;

@API
public class A4SPopup
  extends Activity
{
  public static final int FLAG_INAPP = 1;
  public static final int FLAG_PUSH = 2;
  public static final int FLAG_SHOW_APP_ICON = 4;
  private static e a = new e();
  private Integer b;
  private g c;
  private int d;
  
  private Button a(LinearLayout.LayoutParams paramLayoutParams, g.a parama)
  {
    Button localButton = k.d.a(this);
    localButton.setLayoutParams(paramLayoutParams);
    localButton.setText(parama.b);
    localButton.setOnClickListener(new a(parama, null));
    return localButton;
  }
  
  private void a(LinearLayout paramLinearLayout)
  {
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -2, 1.0F);
    int i = 0;
    while (i < this.c.c.length)
    {
      g.a locala = this.c.c[i];
      if (locala != null) {
        paramLinearLayout.addView(a(localLayoutParams, locala));
      }
      i += 1;
    }
  }
  
  private void a(String paramString)
  {
    setTitle(paramString);
    paramString = (TextView)findViewById(16908310);
    if (paramString != null)
    {
      paramString.setSingleLine(false);
      paramString.setMaxLines(2);
    }
  }
  
  private boolean a()
  {
    return (this.d & 0x2) != 0;
  }
  
  private void b(String paramString)
  {
    Log.error(paramString);
    if (!isFinishing()) {
      finish();
    }
  }
  
  private boolean b()
  {
    return (this.d & 0x1) != 0;
  }
  
  public static Intent build(Context paramContext, int paramInt, g paramg)
  {
    Intent localIntent = new Intent(paramContext, A4SPopup.class);
    localIntent.setFlags(1082130432);
    localIntent.putExtra("com.ad4screen.sdk.Popup.flags", paramInt);
    try
    {
      paramContext = a.a(paramg).toString();
      if (paramContext != null)
      {
        localIntent.putExtra("com.ad4screen.sdk.Popup.format", paramContext);
        return localIntent;
      }
    }
    catch (JSONException paramContext)
    {
      for (;;)
      {
        Log.internal("A4SPopup|Error while serializing Format", paramContext);
        paramContext = null;
      }
      Log.warn("A4SPopup|Could not serialize PopupFormat as JSON");
    }
    return localIntent;
  }
  
  public static Intent build(Context paramContext, int paramInt1, g paramg, int paramInt2)
  {
    paramContext = build(paramContext, paramInt1, paramg);
    paramContext.putExtra("com.ad4screen.sdk.Popup.systemNotificationId", paramInt2);
    return paramContext;
  }
  
  private boolean c()
  {
    return (this.d & 0x4) != 0;
  }
  
  private boolean d()
  {
    Bundle localBundle = getIntent().getExtras();
    if (localBundle == null)
    {
      b("A4SPopup|No extras parameters found");
      return false;
    }
    this.b = ((Integer)i.a(Integer.class, localBundle, "com.ad4screen.sdk.Popup.systemNotificationId", null));
    this.d = localBundle.getInt("com.ad4screen.sdk.Popup.flags", 0);
    try
    {
      this.c = ((g)a.a(localBundle.getString("com.ad4screen.sdk.Popup.format"), new d()));
      if (this.c == null)
      {
        b("A4SPopup|Invalid parameter for extra : 'com.ad4screen.sdk.Popup.format'");
        return false;
      }
    }
    catch (JSONException localJSONException)
    {
      b("A4SPopup|An error occurred while deserializing extra parameters");
      return false;
    }
    return true;
  }
  
  private View e()
  {
    ScrollView localScrollView = f();
    LinearLayout localLinearLayout = g();
    localLinearLayout.addView(h());
    localLinearLayout.addView(i());
    localScrollView.addView(localLinearLayout);
    return localScrollView;
  }
  
  private ScrollView f()
  {
    ScrollView localScrollView = new ScrollView(this);
    localScrollView.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
    return localScrollView;
  }
  
  private LinearLayout g()
  {
    LinearLayout localLinearLayout = k.i.a(this);
    localLinearLayout.setOrientation(1);
    localLinearLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
    return localLinearLayout;
  }
  
  private TextView h()
  {
    int i = i.a(this, 10);
    TextView localTextView = new TextView(this);
    localTextView.setTextAppearance(this, 16973896);
    localTextView.setPadding(i, i, i, i);
    localTextView.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
    localTextView.setText(this.c.b);
    return localTextView;
  }
  
  private LinearLayout i()
  {
    int j = 1;
    LinearLayout localLinearLayout = k.i.a(this);
    localLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
    if (this.c.c.length > 2)
    {
      i = 1;
      if (i == 0) {
        break label68;
      }
      label39:
      localLinearLayout.setOrientation(j);
      if (i == 0) {
        break label73;
      }
    }
    label68:
    label73:
    for (int i = 48;; i = 16)
    {
      localLinearLayout.setGravity(i);
      a(localLinearLayout);
      return localLinearLayout;
      i = 0;
      break;
      j = 0;
      break label39;
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    if (!d()) {}
    do
    {
      return;
      if (c()) {
        requestWindowFeature(3);
      }
      super.onCreate(paramBundle);
      k.f.a(this);
      setContentView(e());
      if (c()) {
        getWindow().setFeatureDrawableResource(3, i.d(this));
      }
      if (this.c.a != null) {
        a(this.c.a);
      }
      com.ad4screen.sdk.systems.f.a().a(new c(this));
    } while ((!b()) || (paramBundle != null));
    Log.debug("A4SPopup|Popup displayed with id #" + this.c.i);
    com.ad4screen.sdk.systems.f.a().a(new c.f(this.c.i, -1, "com_ad4screen_sdk_theme_popup", null));
  }
  
  protected void onPause()
  {
    super.onPause();
    if (!isFinishing()) {
      finish();
    }
    if (b())
    {
      Log.debug("A4SPopup|Popup closing with id #" + this.c.i);
      com.ad4screen.sdk.systems.f.a().a(new c.d(this.c.i, -1, "com_ad4screen_sdk_theme_popup", false));
    }
  }
  
  private final class a
    implements View.OnClickListener
  {
    private final g.a b;
    
    private a(g.a parama)
    {
      this.b = parama;
    }
    
    private void a()
    {
      if (A4SPopup.d(A4SPopup.this) != null) {
        ((NotificationManager)A4SPopup.this.getSystemService("notification")).cancel(A4SPopup.d(A4SPopup.this).intValue());
      }
    }
    
    private void a(d paramd)
    {
      if ((paramd instanceof com.ad4screen.sdk.model.displayformats.f)) {
        try
        {
          paramd = (com.ad4screen.sdk.model.displayformats.f)paramd;
          A4SPopup.this.startActivity(paramd.a);
          return;
        }
        catch (Exception paramd)
        {
          Log.error("A4SPopup|Could not open popup landing page", paramd);
          return;
        }
      }
      Log.warn("A4SPopup|Unsupported target type for push : " + paramd.getClass());
    }
    
    public void onClick(View paramView)
    {
      if (A4SPopup.a(A4SPopup.this)) {
        com.ad4screen.sdk.systems.f.a().a(new c.c(A4SPopup.b(A4SPopup.this).i, -1, "com_ad4screen_sdk_theme_popup", this.b.a, null));
      }
      if (A4SPopup.c(A4SPopup.this))
      {
        paramView = this.b.d;
        if (paramView != null)
        {
          a();
          a(paramView);
        }
      }
      if (!A4SPopup.this.isFinishing()) {
        A4SPopup.this.finish();
      }
    }
  }
  
  public static abstract interface b
  {
    public abstract void a(A4SPopup paramA4SPopup);
  }
  
  public static final class c
    implements f.a<A4SPopup.b>
  {
    private A4SPopup a;
    
    public c(A4SPopup paramA4SPopup)
    {
      this.a = paramA4SPopup;
    }
    
    public void a(A4SPopup.b paramb)
    {
      paramb.a(this.a);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/A4SPopup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */