package com.ad4screen.sdk.service.modules.push.model;

import android.os.Bundle;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.model.displayformats.d.a;
import com.ad4screen.sdk.model.displayformats.e.a;

public final class a
{
  public d.a a = d.a.b;
  public String b;
  public e.a c = e.a.a;
  public String d;
  public String e;
  public boolean f;
  public boolean g;
  public int h;
  public String i;
  public int j;
  public String k;
  public String l;
  public String m;
  public String n;
  public String o;
  public String p;
  public String q;
  public String r;
  public String s;
  public String t;
  public String u;
  public int v = 1001;
  public boolean w;
  public String x;
  public boolean y;
  public boolean z;
  
  public static a a(Bundle paramBundle)
  {
    if (paramBundle == null) {}
    a locala;
    do
    {
      return null;
      locala = new a();
      locala.n = paramBundle.getString("a4scontent");
    } while (locala.n == null);
    locala.h = paramBundle.getInt("a4spriority");
    locala.i = paramBundle.getString("a4scategory");
    locala.j = paramBundle.getInt("a4saccentcolor");
    locala.k = paramBundle.getString("a4ssmalliconname");
    locala.m = paramBundle.getString("a4stitle");
    locala.o = paramBundle.getString("a4sbigcontent");
    locala.q = paramBundle.getString("a4stemplate");
    locala.r = paramBundle.getString("a4sbigtemplate");
    locala.l = paramBundle.getString("a4sicon");
    String str = paramBundle.getString("a4strk");
    locala.a = d.a.b;
    if (str != null) {}
    try
    {
      if (Integer.valueOf(str).intValue() == 3) {
        locala.a = d.a.a;
      }
      locala.y = a(paramBundle.getString("isAlarm"));
      locala.b = paramBundle.getString("a4sid");
      locala.d = paramBundle.getString("a4surl");
      locala.e = paramBundle.getString("a4st");
      str = paramBundle.getString("openWithSafari");
      if ((str != null) && (str.matches(".*[yYtT].*"))) {
        locala.c = e.a.b;
      }
      locala.t = paramBundle.getString("a4sok");
      locala.u = paramBundle.getString("a4scancel");
      str = paramBundle.getString("a4ssysid");
      if (str == null) {}
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      try
      {
        locala.v = Integer.valueOf(str).intValue();
        locala.g = a(paramBundle.getString("a4smultiplelines"));
        locala.f = a(paramBundle.getString("a4spopup"));
        locala.w = a(paramBundle.getString("a4sforeground"));
        locala.p = paramBundle.getString("a4sbigpicture");
        locala.z = a(paramBundle.get("displayed"));
        locala.s = paramBundle.getString("a4sb");
        locala.x = ("Notification#" + locala.b);
        return locala;
        localNumberFormatException1 = localNumberFormatException1;
        Log.internal(localNumberFormatException1.getMessage(), localNumberFormatException1);
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        for (;;)
        {
          Log.internal("Could not parse a4ssysid parameter : " + localNumberFormatException1);
        }
      }
    }
  }
  
  public static boolean a(Object paramObject)
  {
    if (paramObject == null) {}
    do
    {
      return false;
      if ((paramObject instanceof Boolean)) {
        return ((Boolean)paramObject).booleanValue();
      }
    } while (!(paramObject instanceof String));
    return ((String)paramObject).matches(".*[yYtT].*");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/push/model/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */