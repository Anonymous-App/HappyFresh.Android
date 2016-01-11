package com.ad4screen.sdk.systems;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.ad4screen.sdk.A4SIdsProvider;
import com.ad4screen.sdk.A4SService;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.plugins.AdvertiserPlugin;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class b
{
  private static b N;
  public boolean A;
  public boolean B;
  public String C;
  public boolean D;
  public String E;
  public String F;
  public String G;
  public String H;
  public b I;
  public String J;
  public Date K;
  public Date L;
  private final c M;
  public String a;
  public String b;
  public String c;
  public String d;
  public String e;
  public String f;
  public String g;
  public String h;
  public String i;
  public String j;
  public String k;
  public String l;
  public String m;
  public String n;
  public String o;
  public String p;
  public String q;
  public int r;
  public int s;
  public String t;
  public String u;
  public a v;
  public String w;
  public boolean x;
  public boolean y;
  public boolean z;
  
  private b(Context paramContext)
  {
    this.M = new c(paramContext);
    Object localObject1 = paramContext.getPackageManager();
    Object localObject2 = paramContext.getApplicationInfo();
    try
    {
      Object localObject3 = ((PackageManager)localObject1).getPackageInfo(((ApplicationInfo)localObject2).packageName, 0);
      this.p = i.a((PackageInfo)localObject3, (ApplicationInfo)localObject2);
      this.n = Integer.toString(((PackageInfo)localObject3).versionCode);
      this.a = "A3.2.1";
      this.g = ("Android " + Build.MODEL);
      this.h = Build.VERSION.RELEASE;
      this.i = ((ApplicationInfo)localObject2).packageName;
      this.j = Resources.getSystem().getConfiguration().locale.toString();
      this.k = Resources.getSystem().getConfiguration().locale.getDisplayCountry();
      this.l = Resources.getSystem().getConfiguration().locale.getCountry();
      this.m = Resources.getSystem().getConfiguration().locale.getLanguage();
      this.o = ((String)((PackageManager)localObject1).getApplicationLabel((ApplicationInfo)localObject2));
      this.K = c();
      this.L = d();
      this.u = d(paramContext);
      this.v = e(paramContext);
      this.q = TimeZone.getDefault().getID();
      this.d = f(paramContext);
      this.e = g(paramContext);
      this.c = f();
      this.b = i.b(paramContext);
      this.w = h(paramContext);
      this.C = i.a(paramContext, "com.ad4screen.environment", A4SService.class);
      if (this.C == null) {
        this.C = "production";
      }
      localObject1 = i.a(paramContext, "com.ad4screen.logging", A4SService.class);
      localObject2 = i.a(paramContext, "com.ad4screen.no_geoloc", A4SService.class);
      localObject3 = i.a(paramContext, "com.ad4screen.debuggable", A4SService.class);
      String str1 = i.a(paramContext, "com.ad4screen.usbstorage", A4SService.class);
      String str2 = i.a(paramContext, "com.ad4screen.unsecurepush", A4SService.class);
      String str3 = i.a(paramContext, "com.ad4screen.facebook_appid", A4SService.class);
      String str4 = i.a(paramContext, "com.ad4screen.advertiser_id", A4SService.class);
      String str5 = i.a(paramContext, "com.ad4screen.anonym_id", A4SService.class);
      String str6 = i.a(paramContext, "com.ad4screen.tracking_mode", A4SService.class);
      this.J = i.a(paramContext, "com.ad4screen.webview.script_url", A4SService.class);
      this.y = g((String)localObject1);
      if ((localObject2 != null) && (((String)localObject2).equalsIgnoreCase("true")))
      {
        bool1 = true;
        this.z = bool1;
        if ((localObject3 == null) || (!((String)localObject3).equalsIgnoreCase("true"))) {
          break label678;
        }
        bool1 = true;
        this.x = bool1;
        if ((str1 == null) || (!str1.equalsIgnoreCase("true"))) {
          break label683;
        }
        bool1 = true;
        this.A = bool1;
        if ((str2 == null) || (!str2.equalsIgnoreCase("true"))) {
          break label688;
        }
        bool1 = true;
        this.B = bool1;
        this.E = h(str3);
        this.F = g();
        if (str4 != null)
        {
          bool1 = bool2;
          if (!str4.equalsIgnoreCase("true")) {}
        }
        else if (str5 != null)
        {
          bool1 = bool2;
          if (!str5.equalsIgnoreCase("false")) {}
        }
        else
        {
          bool1 = true;
        }
        this.D = bool1;
        this.I = b.a;
        if ((str6 != null) && (str6.equalsIgnoreCase("Restricted"))) {
          this.I = b.b;
        }
        this.f = e();
        this.G = h();
        this.H = i();
        this.r = this.M.f();
        this.s = this.M.g();
        this.t = this.M.h();
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      for (;;)
      {
        Log.warn("DeviceInfo|Could not retrieve current package information");
        continue;
        boolean bool1 = false;
        continue;
        bool1 = false;
        continue;
        bool1 = false;
        continue;
        bool1 = false;
      }
    }
    catch (RuntimeException localRuntimeException)
    {
      label678:
      label683:
      label688:
      for (;;) {}
    }
  }
  
  public static b a(Context paramContext)
  {
    return a(paramContext, false);
  }
  
  public static b a(Context paramContext, boolean paramBoolean)
  {
    try
    {
      if ((N == null) || (paramBoolean)) {
        N = new b(paramContext.getApplicationContext());
      }
      paramContext = N;
      return paramContext;
    }
    finally {}
  }
  
  private String d(Context paramContext)
  {
    switch (paramContext.getResources().getConfiguration().screenLayout & 0xF)
    {
    default: 
      return "unknown";
    case 1: 
      return "small";
    case 2: 
      return "normal";
    case 3: 
      return "large";
    }
    return "xlarge";
  }
  
  private a e(Context paramContext)
  {
    paramContext = (WindowManager)paramContext.getSystemService("window");
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    paramContext.getDefaultDisplay().getMetrics(localDisplayMetrics);
    if (localDisplayMetrics.densityDpi <= 120) {
      paramContext = a.b;
    }
    for (;;)
    {
      Object localObject = paramContext;
      if (Build.VERSION.SDK_INT >= 16)
      {
        if (localDisplayMetrics.densityDpi <= 480) {
          paramContext = a.f;
        }
        localObject = paramContext;
        if (Build.VERSION.SDK_INT >= 18)
        {
          localObject = paramContext;
          if (localDisplayMetrics.densityDpi <= 640) {
            localObject = a.g;
          }
        }
      }
      return (a)localObject;
      if (localDisplayMetrics.densityDpi <= 160) {
        paramContext = a.c;
      } else if (localDisplayMetrics.densityDpi <= 240) {
        paramContext = a.d;
      } else {
        paramContext = a.e;
      }
    }
  }
  
  private String e()
  {
    Object localObject2 = this.M.a();
    Object localObject1;
    if (localObject2 != null)
    {
      localObject1 = localObject2;
      if (((String)localObject2).length() != 0) {}
    }
    else
    {
      localObject1 = this.c;
    }
    if (localObject1 != null)
    {
      localObject2 = localObject1;
      if (((String)localObject1).length() != 0) {}
    }
    else
    {
      localObject2 = null;
    }
    return (String)localObject2;
  }
  
  private String f()
  {
    return this.M.e();
  }
  
  private String f(Context paramContext)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("com.ad4screen.sdk.common.DeviceInfo", 0);
    String str2 = i.a(paramContext, "com.ad4screen.partnerid", A4SService.class);
    String str1 = str2;
    if (str2 == null) {
      str1 = localSharedPreferences.getString("com.ad4screen.partnerid", null);
    }
    if (str1 == null)
    {
      str2 = i.a(paramContext, "com.ad4screen.idsprovider", A4SService.class);
      try
      {
        paramContext = ((A4SIdsProvider)Class.forName(str2).newInstance()).getPartnerId(paramContext);
        if (paramContext != null) {
          localSharedPreferences.edit().putString("com.ad4screen.partnerid", paramContext).commit();
        }
        return paramContext;
      }
      catch (Exception paramContext)
      {
        for (;;)
        {
          Log.error("DeviceInfo|Exception while calling your class : " + str2, paramContext);
          paramContext = str1;
        }
      }
      catch (Error paramContext)
      {
        for (;;)
        {
          Log.error("DeviceInfo|Error while calling your class : " + str2, paramContext);
          paramContext = str1;
        }
      }
    }
    return str1;
  }
  
  private String g()
  {
    return this.M.j();
  }
  
  private String g(Context paramContext)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("com.ad4screen.sdk.common.DeviceInfo", 0);
    String str2 = i.a(paramContext, "com.ad4screen.privatekey", A4SService.class);
    String str1 = str2;
    if (str2 == null) {
      str1 = localSharedPreferences.getString("com.ad4screen.privatekey", null);
    }
    if (str1 == null)
    {
      str2 = i.a(paramContext, "com.ad4screen.idsprovider", A4SService.class);
      try
      {
        paramContext = ((A4SIdsProvider)Class.forName(str2).newInstance()).getPrivateKey(paramContext);
        if (paramContext != null) {
          localSharedPreferences.edit().putString("com.ad4screen.privatekey", paramContext).commit();
        }
        return paramContext;
      }
      catch (Exception paramContext)
      {
        for (;;)
        {
          Log.error("DeviceInfo|Exception while calling your class : " + str2, paramContext);
          paramContext = str1;
        }
      }
      catch (Error paramContext)
      {
        for (;;)
        {
          Log.error("DeviceInfo|Error while calling your class : " + str2, paramContext);
          paramContext = str1;
        }
      }
    }
    return str1;
  }
  
  private boolean g(String paramString)
  {
    boolean bool2 = this.M.m();
    boolean bool1 = bool2;
    if (!bool2)
    {
      if ((paramString != null) && (paramString.equalsIgnoreCase("true"))) {
        bool1 = true;
      }
    }
    else {
      return bool1;
    }
    return false;
  }
  
  private String h()
  {
    return this.M.k();
  }
  
  private static String h(Context paramContext)
  {
    String str = i.a(paramContext, "com.ad4screen.senderid", A4SService.class);
    paramContext = str;
    if (str != null)
    {
      paramContext = str;
      if (str.toLowerCase(Locale.US).startsWith("gcm:")) {
        paramContext = str.substring("gcm:".length());
      }
    }
    return paramContext;
  }
  
  private String h(String paramString)
  {
    String str;
    if (this.M.i() != null) {
      str = this.M.i();
    }
    do
    {
      do
      {
        return str;
        str = paramString;
      } while (paramString == null);
      str = paramString;
    } while (!paramString.toLowerCase(Locale.US).startsWith("fb:"));
    return paramString.substring("fb:".length());
  }
  
  private String i()
  {
    return this.M.l();
  }
  
  public void a()
  {
    this.r += 1;
    this.M.a(this.r);
  }
  
  public void a(String paramString)
  {
    this.t = paramString;
    this.M.d(this.t);
  }
  
  public void a(Date paramDate)
  {
    this.M.i(h.a(paramDate, h.a.b));
    this.K = paramDate;
  }
  
  public void a(boolean paramBoolean)
  {
    this.y = paramBoolean;
    this.M.a(this.y);
  }
  
  public String b(Context paramContext)
  {
    AdvertiserPlugin localAdvertiserPlugin = com.ad4screen.sdk.common.plugins.b.c();
    if (localAdvertiserPlugin != null)
    {
      Log.debug("AdvertiserPlugin|Looking for an advertiser id..");
      return localAdvertiserPlugin.getId(paramContext);
    }
    return null;
  }
  
  public void b()
  {
    this.s += 1;
    this.M.b(this.s);
  }
  
  public void b(String paramString)
  {
    this.f = paramString;
    this.M.b(paramString);
  }
  
  public void b(Date paramDate)
  {
    this.M.j(h.a(paramDate, h.a.b));
    this.L = paramDate;
  }
  
  public Date c()
  {
    String str = this.M.n();
    if (str == null) {
      return null;
    }
    return h.a(str, h.a.b);
  }
  
  public void c(String paramString)
  {
    this.c = paramString;
    this.M.c(paramString);
    this.f = e();
  }
  
  public boolean c(Context paramContext)
  {
    AdvertiserPlugin localAdvertiserPlugin = com.ad4screen.sdk.common.plugins.b.c();
    if (localAdvertiserPlugin != null)
    {
      Log.debug("AdvertiserPlugin|Looking for an advertiser id..");
      return localAdvertiserPlugin.isLimitAdTrackingEnabled(paramContext);
    }
    return false;
  }
  
  public Date d()
  {
    String str = this.M.o();
    if (str == null) {
      return null;
    }
    return h.a(str, h.a.b);
  }
  
  public void d(String paramString)
  {
    this.E = paramString;
    this.M.e(paramString);
  }
  
  public void e(String paramString)
  {
    this.F = paramString;
    this.M.f(paramString);
  }
  
  public void f(String paramString)
  {
    this.G = paramString;
    this.H = h.a(Calendar.getInstance(Locale.US).getTime(), h.a.b);
    this.M.g(this.G);
    this.M.h(this.H);
  }
  
  public static enum a
  {
    private a() {}
  }
  
  public static enum b
  {
    private b() {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/systems/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */