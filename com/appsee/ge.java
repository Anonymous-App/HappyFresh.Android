package com.appsee;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class ge
{
  public static ge i()
  {
    try
    {
      if (J == null) {
        J = new ge();
      }
      ge localge = J;
      return localge;
    }
    finally {}
  }
  
  public void A(boolean paramBoolean)
  {
    this.V = paramBoolean;
  }
  
  public boolean A()
  {
    return this.t;
  }
  
  public void B(boolean paramBoolean)
  {
    this.n = paramBoolean;
  }
  
  public boolean B()
  {
    return this.X;
  }
  
  public void C(boolean paramBoolean)
  {
    this.l = paramBoolean;
  }
  
  public boolean C()
  {
    return this.e;
  }
  
  public void D(boolean paramBoolean)
  {
    this.v = paramBoolean;
  }
  
  public boolean D()
  {
    return this.R;
  }
  
  public void E(boolean paramBoolean)
  {
    this.x = paramBoolean;
  }
  
  public boolean E()
  {
    return this.h;
  }
  
  public int F()
  {
    return this.T;
  }
  
  public String F()
  {
    return this.z;
  }
  
  public List<a> F()
  {
    if (this.i == null) {
      return new ArrayList();
    }
    return this.i;
  }
  
  public void F(int paramInt)
  {
    this.T = paramInt;
  }
  
  public void F(String paramString)
  {
    this.N = paramString;
  }
  
  public void F(JSONArray paramJSONArray)
    throws JSONException
  {
    this.d = new ArrayList();
    if (paramJSONArray == null) {}
    for (;;)
    {
      return;
      int i1 = 0;
      for (int i2 = 0; i1 < paramJSONArray.length(); i2 = i1)
      {
        JSONObject localJSONObject = paramJSONArray.optJSONObject(i2);
        if (localJSONObject != null) {
          this.d.add(new dc(localJSONObject.optString(AppseeBackgroundUploader.i("B\027*X?Q")), localJSONObject.optString(AppseeBackgroundUploader.i("\033\\e\0357S\023[")), localJSONObject.optJSONArray(AppseeBackgroundUploader.i("Y\025+U?L"))));
        }
        i1 = i2 + 1;
      }
    }
  }
  
  public void F(boolean paramBoolean)
  {
    this.O = paramBoolean;
  }
  
  public boolean F()
  {
    return this.u;
  }
  
  public void G(boolean paramBoolean)
  {
    this.Q = paramBoolean;
  }
  
  public boolean G()
  {
    return this.v;
  }
  
  public int H()
  {
    return this.w;
  }
  
  public void H(int paramInt)
  {
    this.w = paramInt;
  }
  
  public void H(boolean paramBoolean)
  {
    this.j = paramBoolean;
  }
  
  public boolean H()
  {
    return this.g;
  }
  
  public void I(boolean paramBoolean)
  {
    this.G = paramBoolean;
  }
  
  public boolean I()
  {
    return this.l;
  }
  
  public void J(boolean paramBoolean)
  {
    this.R = paramBoolean;
  }
  
  public boolean J()
  {
    return this.j;
  }
  
  public void K(boolean paramBoolean)
  {
    this.s = paramBoolean;
  }
  
  public boolean K()
  {
    return this.f;
  }
  
  public void L(boolean paramBoolean)
  {
    this.c = paramBoolean;
  }
  
  public boolean L()
  {
    return this.Q;
  }
  
  public void M(boolean paramBoolean)
  {
    this.h = paramBoolean;
  }
  
  public boolean M()
  {
    return this.s;
  }
  
  public int a()
  {
    return this.S;
  }
  
  public String a()
  {
    return this.q;
  }
  
  public List<dc> a()
  {
    return this.d;
  }
  
  public void a(int paramInt)
  {
    this.E = paramInt;
  }
  
  public void a(String paramString)
  {
    this.q = paramString;
  }
  
  public void a(JSONArray paramJSONArray)
  {
    if (paramJSONArray == null) {}
    for (;;)
    {
      return;
      this.W = new ArrayList();
      int i2 = 0;
      for (int i1 = 0; i2 < paramJSONArray.length(); i1 = i2)
      {
        List localList = this.W;
        i2 = i1 + 1;
        localList.add(paramJSONArray.optString(i1));
      }
    }
  }
  
  public void a(boolean paramBoolean)
  {
    this.B = paramBoolean;
  }
  
  public boolean a()
  {
    return this.H;
  }
  
  public void b(boolean paramBoolean)
  {
    this.b = paramBoolean;
  }
  
  public boolean b()
  {
    return this.B;
  }
  
  public int c()
  {
    return this.r;
  }
  
  public void c(int paramInt)
  {
    this.K = paramInt;
  }
  
  public void c(boolean paramBoolean)
  {
    this.g = paramBoolean;
  }
  
  public boolean c()
  {
    return this.n;
  }
  
  public void d(boolean paramBoolean)
  {
    this.M = paramBoolean;
  }
  
  public boolean d()
  {
    return this.P;
  }
  
  public void e(boolean paramBoolean)
  {
    this.t = paramBoolean;
  }
  
  public boolean e()
  {
    return this.k;
  }
  
  public void f(boolean paramBoolean)
  {
    this.A = paramBoolean;
  }
  
  public boolean f()
  {
    return this.L;
  }
  
  public void g(boolean paramBoolean)
  {
    this.u = paramBoolean;
  }
  
  public boolean g()
  {
    return this.M;
  }
  
  public void h(boolean paramBoolean)
  {
    this.f = paramBoolean;
  }
  
  public boolean h()
  {
    return this.b;
  }
  
  public double i()
  {
    return this.D;
  }
  
  public int i()
  {
    return this.E;
  }
  
  public yd i()
  {
    return this.y;
  }
  
  public String i()
  {
    return this.Y;
  }
  
  public EnumSet<vi> i()
  {
    return vi.i(this.U);
  }
  
  public List<String> i()
  {
    return this.m;
  }
  
  public JSONArray i()
  {
    return this.I;
  }
  
  public void i(double paramDouble)
  {
    this.F = paramDouble;
  }
  
  public void i(int paramInt)
  {
    this.S = paramInt;
  }
  
  public void i(long paramLong)
  {
    this.U = paramLong;
  }
  
  public void i(yd paramyd)
  {
    this.y = paramyd;
  }
  
  public void i(String paramString)
  {
    this.z = paramString;
  }
  
  public void i(JSONArray paramJSONArray)
  {
    this.I = paramJSONArray;
  }
  
  public void i(JSONObject paramJSONObject)
    throws JSONException
  {
    F(paramJSONObject.optString(AppseeBackgroundUploader.i("l?Lb\0357S\023[")));
    a(paramJSONObject.optString(AppseeBackgroundUploader.i("\031Sx\0216I\023[")));
    p(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("{?\\5Mu\"1Y?P")));
    h(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("x*I#[>k?Le\"1Y?P")));
    F(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("\035O/F([\027Ze\025<\\.^")));
    M(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("\032Z#E-M\025Q\rVW\035\027S6F")));
    i(paramJSONObject.optInt(AppseeBackgroundUploader.i("\002K43[?P]\0216Z.W")));
    F(paramJSONObject.optInt(AppseeBackgroundUploader.i("\032@>Z5}x\000*\\.Z")));
    i(paramJSONObject.optInt(AppseeBackgroundUploader.i("\fVu\0217{\nl")));
    a(paramJSONObject.optInt(AppseeBackgroundUploader.i("\fV>Z~#1Y.W")));
    l(paramJSONObject.optInt(AppseeBackgroundUploader.i("3[?PY\0211Z2K")));
    I(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("\026R+O\034O\"Z3K3It\"1X-L")));
    m(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("w3[t=6M/K")));
    l(paramJSONObject.optJSONArray(AppseeBackgroundUploader.i("\007C(L\031P7O~\032=S.L")));
    d(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("{?\\5Mu5-Y3P")));
    l(paramJSONObject.optString(AppseeBackgroundUploader.i("\021@6^~:1O&L&I-]3P4rt\007+\\=Z")));
    k(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("z:^ n)]?\\.lr\006=X4L")));
    H(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("\013O8L9K\035Zb\000-O?L")));
    K(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("n)]?\\.~r\0001R4L")));
    B(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("\bL.Z9KA\033(H*L")));
    b(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("n)]?\\.|c\025+U?L")));
    C(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("\013K#E.N\032@>Z5p7*\\)W")));
    A(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("n?F K(}5J9WT\002=S.L")));
    i(paramJSONObject.optJSONArray(AppseeBackgroundUploader.i("i#G>V.V~\032+p?K")));
    F(paramJSONObject.optJSONArray(AppseeBackgroundUploader.i("\\\035-H&D(i#G.M5SX\0319Z?L")));
    a(paramJSONObject.optJSONArray(AppseeBackgroundUploader.i("\000Rr\033,_\000D#S\037J(Z?QX\0319Z?L")));
    f(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("\016^=V\017Z\"P7Yb\0341O<}'O\"}5J9WP\027,T,Z")));
    c(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("<E5^>~a\004\021^5Q")));
    c(paramJSONObject.optInt(AppseeBackgroundUploader.i("\023Z7o9O\"]\nM5Ot\006,T?L")));
    u(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("`7P f7Yb\035*R9O\031C?@8V6Ve\r\fX)K")));
    i(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("\025S>wx\000\fX)K")));
    G(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("\023X8\\.\\\005[ Rb\0041U<C9O\031G9S3\\z\025:Q?L")));
    q(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("\032Z#E-M\030Z4\\y\0319O1L")));
    g(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("|5Y~\006;}#K(y)J/M?hx\032<R-L")));
    l(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("\002R7\\\003V Rt\032-S ^<}$L4~4V|\025,T4X")));
    D(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("07H.H#O\rZ#Q9z\0277Y?M")));
    a(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("\024\\!Vs\030;z<S!I\tG9P>Zc&=N?K")));
    E(paramJSONObject.optBoolean(AppseeBackgroundUploader.i("}9F3U}\021\037K?l=K!L){?Kt\027,T5Q")));
    if (paramJSONObject.has(AppseeBackgroundUploader.i("\002K7n%[?\\.Pc\r\013T Z"))) {
      k(paramJSONObject.getInt(AppseeBackgroundUploader.i("\002K7n%[?\\.Pc\r\013T Z")));
    }
    if (paramJSONObject.has(AppseeBackgroundUploader.i("\021[x\0210O\003E(Y\031Y6P;[A\0334T9F"))) {
      i(yd.values()[paramJSONObject.getInt(AppseeBackgroundUploader.i("\021[x\0210O\003E(Y\031Y6P;[A\0334T9F"))]);
    }
    if (paramJSONObject.has(AppseeBackgroundUploader.i("\033Yb\000?W#}&D(F-|;S}\0269^1L"))) {
      L(paramJSONObject.getBoolean(AppseeBackgroundUploader.i("\033Yb\000?W#}&D(F-|;S}\0269^1L")));
    }
    if (paramJSONObject.has(AppseeBackgroundUploader.i("p>F&V}\030\023^!_\033E9J2s3Le\0216X(L"))) {
      J(paramJSONObject.getBoolean(AppseeBackgroundUploader.i("p>F&V}\030\023^!_\033E9J2s3Le\0216X(L")));
    }
    if (paramJSONObject.has(AppseeBackgroundUploader.i("X\032-O.F#a)P8P;Mu<7R1L"))) {
      e(paramJSONObject.getBoolean(AppseeBackgroundUploader.i("X\032-O.F#a)P8P;Mu<7R1L")));
    }
    if (paramJSONObject.has(AppseeBackgroundUploader.i("h:Z?E>]?[\037Qr\033<X(L"))) {
      l(paramJSONObject.getJSONObject(AppseeBackgroundUploader.i("h:Z?E>]?[\037Qr\033<X(L")));
    }
    if (paramJSONObject.has(AppseeBackgroundUploader.i("D\007=d\fj"))) {
      j(paramJSONObject.getBoolean(AppseeBackgroundUploader.i("D\007=d\fj")));
    }
    if (paramJSONObject.has(AppseeBackgroundUploader.i("\036Z8Jv24\\=L"))) {
      i(paramJSONObject.getLong(AppseeBackgroundUploader.i("\036Z8Jv24\\=L")));
    }
    gb.l();
  }
  
  public void i(boolean paramBoolean)
  {
    this.H = paramBoolean;
  }
  
  public boolean i()
  {
    return this.A;
  }
  
  public void j(boolean paramBoolean)
  {
    this.C = paramBoolean;
  }
  
  public boolean j()
  {
    return this.G;
  }
  
  public int k()
  {
    return this.K;
  }
  
  public String k()
  {
    String str = UUID.randomUUID().toString().replace(AppseeBackgroundUploader.i("\022"), "");
    mc.l(str, 2);
    return str;
  }
  
  public void k(int paramInt)
  {
    this.r = paramInt;
  }
  
  public void k(boolean paramBoolean)
  {
    this.e = paramBoolean;
  }
  
  public boolean k()
  {
    return this.O;
  }
  
  public double l()
  {
    return this.F;
  }
  
  public int l()
  {
    return this.a;
  }
  
  public String l()
  {
    return this.N;
  }
  
  public List<String> l()
  {
    return this.W;
  }
  
  public void l(double paramDouble)
  {
    this.D = paramDouble;
  }
  
  public void l(int paramInt)
  {
    this.a = paramInt;
  }
  
  public void l(String paramString)
  {
    this.Y = paramString;
  }
  
  public void l(JSONArray paramJSONArray)
  {
    if (paramJSONArray == null) {}
    for (;;)
    {
      return;
      this.m = new ArrayList();
      int i2 = 0;
      for (int i1 = 0; i2 < paramJSONArray.length(); i1 = i2)
      {
        List localList = this.m;
        i2 = i1 + 1;
        localList.add(paramJSONArray.optString(i1));
      }
    }
  }
  
  public void l(JSONObject paramJSONObject)
    throws JSONException
  {
    this.i = new ArrayList();
    Iterator localIterator = paramJSONObject.keys();
    while (localIterator.hasNext())
    {
      a locala = new a();
      locala.G = ((String)localIterator.next());
      locala.k = Integer.valueOf(paramJSONObject.getInt(locala.G));
      this.i.add(locala);
    }
  }
  
  public void l(boolean paramBoolean)
  {
    this.X = paramBoolean;
  }
  
  public boolean l()
  {
    return this.Z;
  }
  
  public void m(boolean paramBoolean)
  {
    this.L = paramBoolean;
  }
  
  public boolean m()
  {
    return this.V;
  }
  
  public void p(boolean paramBoolean)
  {
    this.k = paramBoolean;
  }
  
  public boolean p()
  {
    return this.C;
  }
  
  public void q(boolean paramBoolean)
  {
    this.P = paramBoolean;
  }
  
  public boolean q()
  {
    return this.c;
  }
  
  public void u(boolean paramBoolean)
  {
    this.Z = paramBoolean;
  }
  
  public boolean u()
  {
    return this.x;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/ge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */