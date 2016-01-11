package com.appsee;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import java.io.File;
import java.util.List;

class xc
  implements q, u, i
{
  public static xc i()
  {
    try
    {
      if (m == null) {
        m = new xc();
      }
      xc localxc = m;
      return localxc;
    }
    finally {}
  }
  
  public void E()
  {
    if (!m.i().i())
    {
      mc.l(AppseeBackgroundUploader.i("\037K9Z3Q=\037g\035#G3\027"), 2);
      m.i().l(true);
      jc.i().i(AppseeBackgroundUploader.i("h*O)Zt$&W/\\"), null);
    }
  }
  
  public void F()
  {
    mc.l(AppseeBackgroundUploader.i(";^t\033:Y*XlJ;S6ZuT4V3I"), 1);
    ol.i(ak.G);
  }
  
  /* Error */
  public void F(boolean paramBoolean)
    throws Exception
  {
    // Byte code:
    //   0: invokestatic 177	com/appsee/jc:i	()Lcom/appsee/jc;
    //   3: invokevirtual 194	com/appsee/jc:i	()Z
    //   6: istore_2
    //   7: invokestatic 82	com/appsee/ge:i	()Lcom/appsee/ge;
    //   10: invokevirtual 85	com/appsee/ge:l	()Ljava/lang/String;
    //   13: astore 4
    //   15: invokestatic 82	com/appsee/ge:i	()Lcom/appsee/ge;
    //   18: invokevirtual 198	com/appsee/ge:a	()I
    //   21: ifle +14 -> 35
    //   24: aload_0
    //   25: getfield 200	com/appsee/xc:b	Landroid/os/Handler;
    //   28: aload_0
    //   29: getfield 48	com/appsee/xc:G	Ljava/lang/Runnable;
    //   32: invokevirtual 206	android/os/Handler:removeCallbacks	(Ljava/lang/Runnable;)V
    //   35: invokestatic 177	com/appsee/jc:i	()Lcom/appsee/jc;
    //   38: invokevirtual 194	com/appsee/jc:i	()Z
    //   41: istore_3
    //   42: iload_3
    //   43: ifeq +9 -> 52
    //   46: invokestatic 177	com/appsee/jc:i	()Lcom/appsee/jc;
    //   49: invokevirtual 208	com/appsee/jc:l	()V
    //   52: invokestatic 165	com/appsee/m:i	()Lcom/appsee/m;
    //   55: invokevirtual 210	com/appsee/m:F	()Z
    //   58: istore_3
    //   59: iload_3
    //   60: ifeq +9 -> 69
    //   63: invokestatic 165	com/appsee/m:i	()Lcom/appsee/m;
    //   66: invokevirtual 212	com/appsee/m:a	()V
    //   69: invokestatic 82	com/appsee/ge:i	()Lcom/appsee/ge;
    //   72: invokevirtual 215	com/appsee/ge:i	()Lcom/appsee/yd;
    //   75: getstatic 220	com/appsee/yd:i	Lcom/appsee/yd;
    //   78: if_acmpne +7 -> 85
    //   81: iconst_0
    //   82: invokestatic 222	com/appsee/mc:i	(Z)V
    //   85: new 22	com/appsee/uc
    //   88: dup
    //   89: aload_0
    //   90: aload 4
    //   92: invokespecial 225	com/appsee/uc:<init>	(Lcom/appsee/xc;Ljava/lang/String;)V
    //   95: iconst_1
    //   96: invokestatic 228	com/appsee/ol:i	(Lcom/appsee/s;Z)V
    //   99: iload_1
    //   100: ifeq +23 -> 123
    //   103: aload_0
    //   104: getfield 41	com/appsee/xc:K	Z
    //   107: ifne +16 -> 123
    //   110: iload_2
    //   111: ifeq +6 -> 117
    //   114: invokestatic 230	com/appsee/mc:i	()V
    //   117: invokestatic 235	com/appsee/fc:i	()Lcom/appsee/fc;
    //   120: invokevirtual 236	com/appsee/fc:a	()V
    //   123: return
    //   124: astore 5
    //   126: aload 5
    //   128: ldc -18
    //   130: invokestatic 57	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   133: invokestatic 115	com/appsee/vd:l	(Ljava/lang/Throwable;Ljava/lang/String;)V
    //   136: goto -84 -> 52
    //   139: astore 5
    //   141: invokestatic 82	com/appsee/ge:i	()Lcom/appsee/ge;
    //   144: invokevirtual 215	com/appsee/ge:i	()Lcom/appsee/yd;
    //   147: getstatic 220	com/appsee/yd:i	Lcom/appsee/yd;
    //   150: if_acmpne +7 -> 157
    //   153: iconst_0
    //   154: invokestatic 222	com/appsee/mc:i	(Z)V
    //   157: new 22	com/appsee/uc
    //   160: dup
    //   161: aload_0
    //   162: aload 4
    //   164: invokespecial 225	com/appsee/uc:<init>	(Lcom/appsee/xc;Ljava/lang/String;)V
    //   167: iconst_1
    //   168: invokestatic 228	com/appsee/ol:i	(Lcom/appsee/s;Z)V
    //   171: iload_1
    //   172: ifeq +23 -> 195
    //   175: aload_0
    //   176: getfield 41	com/appsee/xc:K	Z
    //   179: ifne +16 -> 195
    //   182: iload_2
    //   183: ifeq +6 -> 189
    //   186: invokestatic 230	com/appsee/mc:i	()V
    //   189: invokestatic 235	com/appsee/fc:i	()Lcom/appsee/fc;
    //   192: invokevirtual 236	com/appsee/fc:a	()V
    //   195: aload 5
    //   197: athrow
    //   198: astore 5
    //   200: aload 5
    //   202: ldc -16
    //   204: invokestatic 57	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   207: invokestatic 115	com/appsee/vd:l	(Ljava/lang/Throwable;Ljava/lang/String;)V
    //   210: goto -141 -> 69
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	213	0	this	xc
    //   0	213	1	paramBoolean	boolean
    //   6	177	2	bool1	boolean
    //   41	19	3	bool2	boolean
    //   13	150	4	str	String
    //   124	3	5	localException1	Exception
    //   139	57	5	localObject	Object
    //   198	3	5	localException2	Exception
    // Exception table:
    //   from	to	target	type
    //   46	52	124	java/lang/Exception
    //   35	42	139	finally
    //   46	52	139	finally
    //   52	59	139	finally
    //   63	69	139	finally
    //   126	136	139	finally
    //   200	210	139	finally
    //   63	69	198	java/lang/Exception
  }
  
  public void M()
    throws Exception
  {
    mc.l(ge.i().l(), 2);
    if (ge.i().i() == yd.i)
    {
      mc.i(1);
      mc.i(true);
    }
    if (!ge.i().k())
    {
      k();
      return;
    }
    if (hd.i(ge.i().c()))
    {
      mc.i(AppseeBackgroundUploader.i("{3Mt\0273M.@Rc3VpY3Ev\021r\033.H X8@4XzLt\0074K3W"), 2);
      k();
      return;
    }
    if (ge.i().h())
    {
      if (!this.k)
      {
        mc.l(AppseeBackgroundUploader.i("\f^(C<^)[?[z\\c\0254J9J"), 1);
        yl.i();
        AppseeNativeExtensions.a();
        this.k = true;
      }
      AppseeNativeExtensions.F();
    }
    if (ge.i().H()) {
      L();
    }
    kk.i().i();
    jc.i().a();
    if (ge.i().K()) {
      vb.i().i();
    }
    if (ge.i().e()) {}
    try
    {
      m.i().f();
      if ((!ge.i().g()) || (ge.i().a() > 0))
      {
        int j = ge.i().a() * 1000;
        mc.l(j, 1);
        if (this.b == null) {
          this.b = new Handler(Looper.getMainLooper());
        }
        this.b.postDelayed(this.G, j);
      }
    }
    catch (Exception localException)
    {
      for (;;)
      {
        vd.l(localException, AppseeBackgroundUploader.i("d=P\036r8\031$ZrDe\025,Oo\\&N)FzM?\\~\006#K2^"));
        ge.i().p(false);
      }
    }
    H();
    ol.i(new nc(this), true);
  }
  
  public void a()
  {
    ol.l(new ic(this));
  }
  
  public void a(boolean paramBoolean)
  {
    this.K = false;
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("com.appsee.Action.UploadMode", paramBoolean);
    ol.i(ak.i, localBundle);
  }
  
  public boolean a()
  {
    return gb.i();
  }
  
  public void c()
    throws Exception
  {
    if (a())
    {
      mc.l(AppseeBackgroundUploader.i("k<Y)Z?\037f\033)\005(\031/M=K$\025=Y1\0250\033 Z;O(\0045J.\037u\0211K?\\"), 1);
      return;
    }
    mc.l(AppseeBackgroundUploader.i("\bv\023v\031\017M1G&^\023~U*]oY)Z)V5Q1^m\bv\023"), 2);
    hd.l();
    gb.a();
    ge.i().p(false);
    ge.i().F(false);
    jc.i().l(false);
    m.i().K();
    fc.i().k();
  }
  
  public void f()
    throws Exception
  {
    if (m.i().F()) {
      m.i().a();
    }
  }
  
  public void i()
    throws Exception
  {
    if (jc.i().i()) {
      kk.i().l();
    }
  }
  
  public void i(ki paramki)
  {
    jc.i().i(paramki);
  }
  
  public void i(n paramn)
    throws Exception
  {
    paramn.a();
    if (jc.i().i()) {
      paramn.l();
    }
  }
  
  public void i(n paramn, List<View> paramList)
  {
    paramn.l(paramList);
  }
  
  public void i(to paramto)
    throws Exception
  {
    if (jc.i().i())
    {
      m.i().F();
      mc.l(AppseeBackgroundUploader.i("\017\\#F;XT=I.Y'O(\005zJ*S~\025#K2^"), 2);
      jc.i().i(true);
      jc.i().i(paramto);
      this.K = false;
      F(true);
    }
    while (!m.i().a()) {
      return;
    }
    this.K = false;
    F(true);
  }
  
  public void i(String paramString)
  {
    lk.i().i(paramString);
  }
  
  public void i(boolean paramBoolean)
  {
    ie localie = lk.i().i(xm.K);
    if ((localie != null) && (jc.i().i() - localie.i() <= 1250L))
    {
      mc.l(paramBoolean, 1);
      if (!paramBoolean) {
        break label77;
      }
    }
    label77:
    for (String str = AppseeBackgroundUploader.i("3P)\\");; str = AppseeBackgroundUploader.i("\022&N/\\"))
    {
      localie.i(str);
      return;
    }
  }
  
  public void i(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean1)
    {
      lk locallk = lk.i();
      xm localxm = xm.K;
      if (paramBoolean2) {}
      for (String str = AppseeBackgroundUploader.i("3P)\\");; str = AppseeBackgroundUploader.i("\022&N/\\"))
      {
        locallk.i(localxm, str, null, null);
        return;
      }
    }
    lk.i().i(xm.i, null, null, null);
  }
  
  public void j()
  {
    if (m.i().i())
    {
      mc.l(AppseeBackgroundUploader.i("x*Y9D3Q=\037g\035#G3\027"), 2);
      m.i().l(false);
      jc.i().i(AppseeBackgroundUploader.i("\rY*L?ZC\0214W1\\"), null);
    }
  }
  
  public void k()
  {
    mc.l(AppseeBackgroundUploader.i("5<T=^&D+\t)Z)Lx\033)\fr\027"), 1);
    try
    {
      ub.i().f();
      if (jc.i().i()) {
        jc.i().l(true);
      }
      if (!m.i().F()) {}
    }
    catch (Exception localException1)
    {
      for (;;)
      {
        try
        {
          m.i().a();
          File localFile1 = hd.i(AppseeBackgroundUploader.i("\027") + "mp4");
          File localFile2 = hd.i(AppseeBackgroundUploader.i("\027") + "h264");
          hd.i(localFile1);
          hd.i(localFile2);
          return;
        }
        catch (Exception localException2)
        {
          vd.l(localException2, AppseeBackgroundUploader.i("\002P.V&\021:P>\\!_x\0329\0339C+O#\t(Z9Pc\020.L;\027"));
        }
        localException1 = localException1;
        vd.l(localException1, AppseeBackgroundUploader.i("\002P.V&\021/M?E\"^\023~H,X*O\"\t>Z.Zr\000.M2\027"));
      }
    }
  }
  
  public void k(boolean paramBoolean)
  {
    this.i = paramBoolean;
  }
  
  public void l()
  {
    ol.l(new ec(this));
  }
  
  public void l(String paramString)
    throws Exception
  {
    if (this.d)
    {
      mc.l(AppseeBackgroundUploader.i("t\"Gb\021;\033.F=O-M#\037(J\032.L;\027"), 2);
      if ((!sd.i()) && (!jn.i())) {
        sd.i().i(AppseeBackgroundUploader.i(".Z?Y)LzM?Le\0255V9]"));
      }
    }
    do
    {
      return;
      if (Build.VERSION.SDK_INT < 8)
      {
        mc.l(AppseeBackgroundUploader.i("5.K<O*\n>\\4LzPT\024f\027\031%S.J9Z<\027)Tv}=E6Ee\t5MzWx\023/G.\027"), 3);
        ge.i().p(false);
        ge.i().F(false);
        return;
      }
      if (!this.i)
      {
        if (bc.l())
        {
          mc.l(AppseeBackgroundUploader.i("\033O*Lt\021iQ(X!Bt\020pF:Xd\030:\033-OoI-E6Z>\037w\006(O|X=\026\035Z$\\$^e\r~Z!NoD#]zY(P|T3J9\031\022F,U9V3Cx\0330\033,F.Y?\005z^8Pc\000.L;\027"), 3);
          return;
        }
        if (!bc.i())
        {
          mc.l(AppseeBackgroundUploader.i("\016Z<Z?ZtLe\0255Vt\020sE4V%Y6\027s\021~X.F#O(\t<M5R1\025)\002\035Z'_*P$L|X7,^.^*\002e\t5MzP&\"Q)T6\036u\031=P&_~\020-\033 D#Sm\005z^8Pc\000.L;\027"), 3);
          return;
        }
      }
      if (!jn.l())
      {
        mc.l(AppseeBackgroundUploader.i("\037X=X3MpR7C1\025.K#C,K8@5Qz\\~\0323G$M"), 3);
        return;
      }
    } while (!i());
    this.d = true;
    hd.i();
    if ((!sd.i()) && (jn.i()))
    {
      mc.l(AppseeBackgroundUploader.i("m?K?\\e\021#\002)W:B|M5F&^\023r\033<B:^8@4Xz[~\003)\fr\027"), 3);
      return;
    }
    ge.i().i(paramString);
    ub.i().i(this);
    sd.i().i(this);
    ud.i().i(this);
    sd.i().F();
    ub.i().F();
    mb.i().i();
    ol.i(ak.d);
  }
  
  public void l(boolean paramBoolean)
  {
    if (paramBoolean != a())
    {
      if (!gb.i(paramBoolean)) {
        break label74;
      }
      mc.i(paramBoolean);
    }
    for (;;)
    {
      if (this.d)
      {
        if (!paramBoolean) {
          break;
        }
        jc.i().i(AppseeBackgroundUploader.i("\rY*L?Z^\0043m)M"), null);
        i().a(false);
      }
      return;
      label74:
      vd.l(null, AppseeBackgroundUploader.i("T\0065M.\031 W*P>RrVa\004-^*\n Z8\t5J.\037b\000&V)J"));
    }
    jc.i().i(AppseeBackgroundUploader.i("h*O)Zt;7V\025W"), null);
    ol.i(ak.d);
  }
  
  public boolean l()
  {
    return this.d;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/xc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */