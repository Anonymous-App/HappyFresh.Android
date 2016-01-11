package com.appsee;

import android.graphics.Rect;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class jc
{
  public static jc i()
  {
    try
    {
      if (M == null) {
        M = new jc();
      }
      jc localjc = M;
      return localjc;
    }
    finally {}
  }
  
  public void a()
  {
    this.m = rj.i();
    this.K = true;
    this.k = false;
    this.b = null;
    lk locallk;
    xm localxm;
    if (ud.i().i())
    {
      locallk = lk.i();
      localxm = xm.K;
      if (!ud.i().l()) {
        break label70;
      }
    }
    label70:
    for (String str = AppseeBackgroundUploader.i("!B)\\");; str = AppseeBackgroundUploader.i("\0224\\/\\"))
    {
      locallk.i(localxm, str, null, null);
      this.d = rj.i();
      return;
    }
  }
  
  public long i()
  {
    if (ge.i().e()) {
      if (m.i().i() != 0L) {}
    }
    while (this.m == 0L)
    {
      return -1L;
      return rj.i() - m.i().i();
    }
    return rj.i() - this.m;
  }
  
  public Rect i()
  {
    return this.h;
  }
  
  public pd i()
  {
    return this.C;
  }
  
  public to i()
  {
    return this.b;
  }
  
  public String i()
  {
    return this.i;
  }
  
  public void i()
  {
    synchronized (this.a)
    {
      this.a.clear();
      return;
    }
  }
  
  public void i(Rect paramRect)
  {
    this.h = paramRect;
  }
  
  public void i(ki paramki)
  {
    long l1 = 0L;
    if (!this.K) {
      return;
    }
    long l2 = i();
    if ((rj.k()) && (!ge.i().u())) {
      i(pb.i(ub.i().i()), l2);
    }
    for (;;)
    {
      synchronized (this.I)
      {
        if (this.I.isEmpty())
        {
          localObject = this.d;
          if (paramki != localObject)
          {
            mc.l(paramki, 1);
            localObject = this.I;
            if (l2 >= 0L) {
              break label178;
            }
            ((List)localObject).add(new oc(l1, paramki));
          }
          return;
        }
      }
      Object localObject = ((oc)this.I.get(this.I.size() - 1)).i();
      continue;
      label178:
      l1 = l2;
    }
  }
  
  public void i(pd parampd)
  {
    this.C = parampd;
  }
  
  public void i(to paramto)
  {
    this.b = paramto;
  }
  
  public void i(String paramString)
  {
    this.G = paramString;
  }
  
  public void i(String arg1, Map<String, Object> paramMap)
  {
    if (lb.i(???))
    {
      mc.l(AppseeBackgroundUploader.i("\025:_\n\\*D8\023\rV.WA\006:@9K\"Z9Jj\025\033Yg\0252R+\n*\\)G.\0374^|\021o\020{\036"), 3);
      return;
    }
    Object localObject2 = AppseeBackgroundUploader.i("\031");
    Object localObject1 = localObject2;
    Iterator localIterator;
    Object localObject3;
    if (paramMap != null)
    {
      localObject1 = localObject2;
      if (!paramMap.isEmpty())
      {
        localObject1 = new StringBuilder(paramMap.size() * 20);
        localIterator = paramMap.values().iterator();
        while (localIterator.hasNext())
        {
          localObject3 = localIterator.next();
          if (localObject3 != null) {
            ((StringBuilder)localObject1).append(AppseeBackgroundUploader.i("p\031"));
          }
        }
        localObject1 = ((StringBuilder)localObject1).toString();
      }
    }
    mc.l(??? + (String)localObject1, 1);
    localObject2 = new HashMap();
    if ((paramMap != null) && (!paramMap.isEmpty()))
    {
      localIterator = paramMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        localObject3 = (Map.Entry)localIterator.next();
        localObject1 = ((Map.Entry)localObject3).getValue();
        if (lb.i((String)((Map.Entry)localObject3).getKey()))
        {
          mc.l(??? + AppseeBackgroundUploader.i("\036"), 3);
        }
        else if (!i(localObject1))
        {
          mc.l((String)((Map.Entry)localObject3).getKey() + AppseeBackgroundUploader.i("k\t5YzZg\021;D|\036") + ??? + AppseeBackgroundUploader.i("\036"), 3);
        }
        else
        {
          if (localObject1 != null) {
            break label657;
          }
          localObject1 = "";
        }
      }
    }
    label657:
    for (;;)
    {
      paramMap = (Map<String, Object>)localObject1;
      if (localObject1.getClass().equals(URL.class)) {
        paramMap = ((URL)localObject1).toString();
      }
      localObject1 = paramMap;
      if (paramMap.getClass().equals(Date.class)) {
        localObject1 = lb.i((Date)paramMap);
      }
      paramMap = (Map<String, Object>)localObject1;
      if (localObject1.getClass().equals(Boolean.class)) {
        paramMap = localObject1.toString().toLowerCase();
      }
      ((Map)localObject2).put(((Map.Entry)localObject3).getKey(), paramMap);
      break;
      if (((Map)localObject2).isEmpty()) {}
      for (paramMap = null;; paramMap = (Map<String, Object>)localObject2)
      {
        long l2 = i();
        long l1 = l2;
        if (l2 < 0L) {
          l1 = 0L;
        }
        paramMap = new tn(???, l1, paramMap);
        if (paramMap.i() != null) {}
        for (int j = paramMap.i().size(); (ge.i().k() > 0) && (j + i() > ge.i().k()); j = 1)
        {
          mc.l(paramMap.i() + AppseeBackgroundUploader.i("\030zLx\0326U|@9F|\\(V7Ru\021:\033;B*\n-D5J4K1\0333\0209O3](JpT$Vx\030?Y#OoC\"\t#P/M1\0049Q2\027"), 3);
          return;
        }
        synchronized (this.a)
        {
          this.a.add(paramMap);
          return;
        }
      }
    }
  }
  
  public void i(boolean paramBoolean)
  {
    this.k = paramBoolean;
  }
  
  public boolean i()
  {
    return this.K;
  }
  
  public String l()
  {
    return this.G;
  }
  
  /* Error */
  public void l()
    throws org.json.JSONException, java.io.IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 249	com/appsee/jc:i	()J
    //   4: lstore_1
    //   5: lload_1
    //   6: lconst_0
    //   7: lcmp
    //   8: ifge +872 -> 880
    //   11: lconst_0
    //   12: lstore_1
    //   13: invokestatic 377	com/appsee/ld:i	()Lcom/appsee/ld;
    //   16: invokevirtual 380	com/appsee/ld:i	()Lorg/json/JSONArray;
    //   19: astore_3
    //   20: invokestatic 205	com/appsee/lk:i	()Lcom/appsee/lk;
    //   23: invokevirtual 381	com/appsee/lk:i	()Lorg/json/JSONArray;
    //   26: astore 4
    //   28: invokestatic 386	com/appsee/sc:i	()Lcom/appsee/sc;
    //   31: invokevirtual 388	com/appsee/sc:l	()Lorg/json/JSONArray;
    //   34: astore 5
    //   36: invokestatic 386	com/appsee/sc:i	()Lcom/appsee/sc;
    //   39: invokevirtual 389	com/appsee/sc:i	()Lorg/json/JSONArray;
    //   42: astore 6
    //   44: invokestatic 394	com/appsee/mb:i	()Lcom/appsee/mb;
    //   47: invokevirtual 395	com/appsee/mb:i	()Lorg/json/JSONArray;
    //   50: astore 7
    //   52: new 397	org/json/JSONArray
    //   55: dup
    //   56: invokespecial 398	org/json/JSONArray:<init>	()V
    //   59: astore 8
    //   61: aload_0
    //   62: getfield 47	com/appsee/jc:a	Ljava/util/List;
    //   65: astore 9
    //   67: aload 9
    //   69: monitorenter
    //   70: aload_0
    //   71: getfield 47	com/appsee/jc:a	Ljava/util/List;
    //   74: invokeinterface 63 1 0
    //   79: astore 10
    //   81: aload 10
    //   83: invokeinterface 69 1 0
    //   88: ifeq +25 -> 113
    //   91: aload 8
    //   93: aload 10
    //   95: invokeinterface 73 1 0
    //   100: checkcast 75	com/appsee/tn
    //   103: invokevirtual 401	com/appsee/tn:i	()Lorg/json/JSONObject;
    //   106: invokevirtual 404	org/json/JSONArray:put	(Ljava/lang/Object;)Lorg/json/JSONArray;
    //   109: pop
    //   110: goto -29 -> 81
    //   113: aload 9
    //   115: monitorexit
    //   116: new 397	org/json/JSONArray
    //   119: dup
    //   120: invokespecial 398	org/json/JSONArray:<init>	()V
    //   123: astore 9
    //   125: new 406	java/util/HashSet
    //   128: dup
    //   129: invokespecial 407	java/util/HashSet:<init>	()V
    //   132: astore 10
    //   134: aload_0
    //   135: getfield 45	com/appsee/jc:I	Ljava/util/List;
    //   138: astore 11
    //   140: aload 11
    //   142: monitorenter
    //   143: aload_0
    //   144: getfield 45	com/appsee/jc:I	Ljava/util/List;
    //   147: invokeinterface 63 1 0
    //   152: astore 12
    //   154: aload 12
    //   156: invokeinterface 69 1 0
    //   161: ifeq +112 -> 273
    //   164: aload 12
    //   166: invokeinterface 73 1 0
    //   171: checkcast 273	com/appsee/oc
    //   174: astore 13
    //   176: lload_1
    //   177: aload 13
    //   179: invokevirtual 408	com/appsee/oc:i	()J
    //   182: lsub
    //   183: ldc2_w 409
    //   186: lcmp
    //   187: ifle +37 -> 224
    //   190: aload 9
    //   192: aload 13
    //   194: invokevirtual 411	com/appsee/oc:i	()Lorg/json/JSONObject;
    //   197: invokevirtual 404	org/json/JSONArray:put	(Ljava/lang/Object;)Lorg/json/JSONArray;
    //   200: pop
    //   201: goto -47 -> 154
    //   204: astore_3
    //   205: aload 11
    //   207: monitorexit
    //   208: aload_3
    //   209: athrow
    //   210: astore_3
    //   211: aload_0
    //   212: iconst_1
    //   213: invokevirtual 413	com/appsee/jc:l	(Z)V
    //   216: aload_3
    //   217: athrow
    //   218: astore_3
    //   219: aload 9
    //   221: monitorexit
    //   222: aload_3
    //   223: athrow
    //   224: aload 10
    //   226: aload 13
    //   228: invokevirtual 408	com/appsee/oc:i	()J
    //   231: invokestatic 419	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   234: invokevirtual 420	java/util/HashSet:add	(Ljava/lang/Object;)Z
    //   237: pop
    //   238: new 113	java/lang/StringBuilder
    //   241: dup
    //   242: invokespecial 114	java/lang/StringBuilder:<init>	()V
    //   245: iconst_0
    //   246: ldc_w 422
    //   249: invokestatic 121	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   252: invokevirtual 125	java/lang/StringBuilder:insert	(ILjava/lang/String;)Ljava/lang/StringBuilder;
    //   255: aload 13
    //   257: invokevirtual 411	com/appsee/oc:i	()Lorg/json/JSONObject;
    //   260: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   263: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   266: iconst_1
    //   267: invokestatic 139	com/appsee/mc:l	(Ljava/lang/String;I)V
    //   270: goto -116 -> 154
    //   273: aload 11
    //   275: monitorexit
    //   276: new 397	org/json/JSONArray
    //   279: dup
    //   280: invokespecial 398	org/json/JSONArray:<init>	()V
    //   283: astore 11
    //   285: aload_0
    //   286: getfield 92	com/appsee/jc:h	Landroid/graphics/Rect;
    //   289: ifnonnull +10 -> 299
    //   292: aload_0
    //   293: invokestatic 423	com/appsee/pb:i	()Landroid/graphics/Rect;
    //   296: putfield 92	com/appsee/jc:h	Landroid/graphics/Rect;
    //   299: aload 11
    //   301: aload_0
    //   302: getfield 92	com/appsee/jc:h	Landroid/graphics/Rect;
    //   305: getfield 426	android/graphics/Rect:left	I
    //   308: invokevirtual 429	org/json/JSONArray:put	(I)Lorg/json/JSONArray;
    //   311: pop
    //   312: aload 11
    //   314: aload_0
    //   315: getfield 92	com/appsee/jc:h	Landroid/graphics/Rect;
    //   318: getfield 432	android/graphics/Rect:top	I
    //   321: invokevirtual 429	org/json/JSONArray:put	(I)Lorg/json/JSONArray;
    //   324: pop
    //   325: aload 11
    //   327: aload_0
    //   328: getfield 92	com/appsee/jc:h	Landroid/graphics/Rect;
    //   331: invokevirtual 435	android/graphics/Rect:width	()I
    //   334: invokevirtual 429	org/json/JSONArray:put	(I)Lorg/json/JSONArray;
    //   337: pop
    //   338: aload 11
    //   340: aload_0
    //   341: getfield 92	com/appsee/jc:h	Landroid/graphics/Rect;
    //   344: invokevirtual 438	android/graphics/Rect:height	()I
    //   347: invokevirtual 429	org/json/JSONArray:put	(I)Lorg/json/JSONArray;
    //   350: pop
    //   351: aload_0
    //   352: aconst_null
    //   353: putfield 92	com/appsee/jc:h	Landroid/graphics/Rect;
    //   356: new 397	org/json/JSONArray
    //   359: dup
    //   360: invokespecial 398	org/json/JSONArray:<init>	()V
    //   363: astore 12
    //   365: aload_0
    //   366: getfield 49	com/appsee/jc:J	Ljava/util/List;
    //   369: astore 13
    //   371: aload 13
    //   373: monitorenter
    //   374: aload_0
    //   375: getfield 49	com/appsee/jc:J	Ljava/util/List;
    //   378: invokeinterface 63 1 0
    //   383: astore 14
    //   385: aload 14
    //   387: invokeinterface 69 1 0
    //   392: ifeq +51 -> 443
    //   395: aload 14
    //   397: invokeinterface 73 1 0
    //   402: checkcast 102	com/appsee/bl
    //   405: astore 15
    //   407: aload 10
    //   409: aload 15
    //   411: invokevirtual 144	com/appsee/bl:i	()J
    //   414: invokestatic 419	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   417: invokevirtual 441	java/util/HashSet:contains	(Ljava/lang/Object;)Z
    //   420: ifne -35 -> 385
    //   423: aload 12
    //   425: aload 15
    //   427: invokevirtual 442	com/appsee/bl:i	()Lorg/json/JSONObject;
    //   430: invokevirtual 404	org/json/JSONArray:put	(Ljava/lang/Object;)Lorg/json/JSONArray;
    //   433: pop
    //   434: goto -49 -> 385
    //   437: astore_3
    //   438: aload 13
    //   440: monitorexit
    //   441: aload_3
    //   442: athrow
    //   443: aload 13
    //   445: monitorexit
    //   446: new 444	org/json/JSONObject
    //   449: dup
    //   450: invokespecial 445	org/json/JSONObject:<init>	()V
    //   453: astore 10
    //   455: aload 10
    //   457: ldc_w 447
    //   460: invokestatic 121	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   463: aload_0
    //   464: getfield 55	com/appsee/jc:d	Lcom/appsee/ki;
    //   467: invokevirtual 450	com/appsee/ki:ordinal	()I
    //   470: invokevirtual 453	org/json/JSONObject:put	(Ljava/lang/String;I)Lorg/json/JSONObject;
    //   473: pop
    //   474: aload 10
    //   476: ldc_w 455
    //   479: invokestatic 121	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   482: aload 11
    //   484: invokevirtual 458	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   487: pop
    //   488: aload 10
    //   490: ldc_w 460
    //   493: invokestatic 121	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   496: aload_0
    //   497: getfield 38	com/appsee/jc:k	Z
    //   500: invokevirtual 463	org/json/JSONObject:put	(Ljava/lang/String;Z)Lorg/json/JSONObject;
    //   503: pop
    //   504: aload 10
    //   506: ldc_w 465
    //   509: invokestatic 121	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   512: lload_1
    //   513: invokevirtual 468	org/json/JSONObject:put	(Ljava/lang/String;J)Lorg/json/JSONObject;
    //   516: pop
    //   517: aload 10
    //   519: ldc_w 470
    //   522: invokestatic 121	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   525: invokestatic 227	com/appsee/ge:i	()Lcom/appsee/ge;
    //   528: invokevirtual 229	com/appsee/ge:e	()Z
    //   531: invokevirtual 463	org/json/JSONObject:put	(Ljava/lang/String;Z)Lorg/json/JSONObject;
    //   534: pop
    //   535: aload 10
    //   537: ldc_w 472
    //   540: invokestatic 121	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   543: aload 8
    //   545: invokevirtual 458	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   548: pop
    //   549: aload 10
    //   551: ldc_w 474
    //   554: invokestatic 121	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   557: aload 5
    //   559: invokevirtual 458	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   562: pop
    //   563: aload 10
    //   565: ldc_w 476
    //   568: invokestatic 121	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   571: aload 6
    //   573: invokevirtual 458	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   576: pop
    //   577: aload 10
    //   579: ldc_w 478
    //   582: invokestatic 121	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   585: aload_3
    //   586: invokevirtual 458	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   589: pop
    //   590: aload 10
    //   592: ldc_w 480
    //   595: invokestatic 121	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   598: aload 4
    //   600: invokevirtual 458	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   603: pop
    //   604: aload 10
    //   606: ldc_w 482
    //   609: invokestatic 121	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   612: aload 9
    //   614: invokevirtual 458	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   617: pop
    //   618: aload 10
    //   620: ldc_w 484
    //   623: invokestatic 121	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   626: aload 12
    //   628: invokevirtual 458	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   631: pop
    //   632: aload 10
    //   634: ldc_w 486
    //   637: invokestatic 121	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   640: aload 7
    //   642: invokevirtual 458	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   645: pop
    //   646: aload_0
    //   647: getfield 282	com/appsee/jc:G	Ljava/lang/String;
    //   650: invokestatic 288	com/appsee/lb:i	(Ljava/lang/String;)Z
    //   653: ifne +19 -> 672
    //   656: aload 10
    //   658: ldc_w 488
    //   661: invokestatic 121	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   664: aload_0
    //   665: getfield 282	com/appsee/jc:G	Ljava/lang/String;
    //   668: invokevirtual 458	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   671: pop
    //   672: aload_0
    //   673: getfield 240	com/appsee/jc:C	Lcom/appsee/pd;
    //   676: ifnull +22 -> 698
    //   679: aload 10
    //   681: ldc_w 490
    //   684: invokestatic 121	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   687: aload_0
    //   688: getfield 240	com/appsee/jc:C	Lcom/appsee/pd;
    //   691: invokevirtual 493	com/appsee/pd:i	()Lorg/json/JSONObject;
    //   694: invokevirtual 458	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   697: pop
    //   698: aload_0
    //   699: getfield 243	com/appsee/jc:i	Ljava/lang/String;
    //   702: invokestatic 288	com/appsee/lb:i	(Ljava/lang/String;)Z
    //   705: ifne +19 -> 724
    //   708: aload 10
    //   710: ldc_w 495
    //   713: invokestatic 121	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   716: aload_0
    //   717: getfield 243	com/appsee/jc:i	Ljava/lang/String;
    //   720: invokevirtual 458	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   723: pop
    //   724: invokestatic 227	com/appsee/ge:i	()Lcom/appsee/ge;
    //   727: invokevirtual 497	com/appsee/ge:m	()Z
    //   730: ifeq +21 -> 751
    //   733: aload 10
    //   735: ldc_w 499
    //   738: invokestatic 121	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   741: invokestatic 504	com/appsee/xb:i	()Lcom/appsee/xb;
    //   744: invokevirtual 505	com/appsee/xb:i	()Lorg/json/JSONArray;
    //   747: invokevirtual 458	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   750: pop
    //   751: invokestatic 227	com/appsee/ge:i	()Lcom/appsee/ge;
    //   754: invokevirtual 506	com/appsee/ge:i	()Lorg/json/JSONArray;
    //   757: ifnull +21 -> 778
    //   760: aload 10
    //   762: ldc_w 508
    //   765: invokestatic 121	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   768: invokestatic 227	com/appsee/ge:i	()Lcom/appsee/ge;
    //   771: invokevirtual 506	com/appsee/ge:i	()Lorg/json/JSONArray;
    //   774: invokevirtual 458	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   777: pop
    //   778: aload_0
    //   779: getfield 40	com/appsee/jc:b	Lcom/appsee/to;
    //   782: ifnull +22 -> 804
    //   785: aload 10
    //   787: ldc_w 510
    //   790: invokestatic 121	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   793: aload_0
    //   794: getfield 40	com/appsee/jc:b	Lcom/appsee/to;
    //   797: invokevirtual 513	com/appsee/to:i	()Lorg/json/JSONObject;
    //   800: invokevirtual 458	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   803: pop
    //   804: invokestatic 227	com/appsee/ge:i	()Lcom/appsee/ge;
    //   807: invokevirtual 515	com/appsee/ge:d	()Z
    //   810: ifeq +21 -> 831
    //   813: aload 10
    //   815: ldc_w 517
    //   818: invokestatic 121	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   821: invokestatic 522	com/appsee/yc:i	()Lcom/appsee/yc;
    //   824: invokevirtual 523	com/appsee/yc:i	()Lorg/json/JSONObject;
    //   827: invokevirtual 458	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   830: pop
    //   831: new 113	java/lang/StringBuilder
    //   834: dup
    //   835: invokespecial 114	java/lang/StringBuilder:<init>	()V
    //   838: iconst_0
    //   839: ldc_w 525
    //   842: invokestatic 121	com/appsee/AppseeBackgroundUploader:i	(Ljava/lang/String;)Ljava/lang/String;
    //   845: invokevirtual 125	java/lang/StringBuilder:insert	(ILjava/lang/String;)Ljava/lang/StringBuilder;
    //   848: aload 10
    //   850: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   853: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   856: iconst_1
    //   857: invokestatic 139	com/appsee/mc:l	(Ljava/lang/String;I)V
    //   860: aload 10
    //   862: invokevirtual 526	org/json/JSONObject:toString	()Ljava/lang/String;
    //   865: invokestatic 227	com/appsee/ge:i	()Lcom/appsee/ge;
    //   868: invokevirtual 528	com/appsee/ge:l	()Ljava/lang/String;
    //   871: invokestatic 533	com/appsee/hd:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   874: aload_0
    //   875: iconst_1
    //   876: invokevirtual 413	com/appsee/jc:l	(Z)V
    //   879: return
    //   880: goto -867 -> 13
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	883	0	this	jc
    //   4	509	1	l	long
    //   19	1	3	localJSONArray1	org.json.JSONArray
    //   204	5	3	localObject1	Object
    //   210	7	3	localObject2	Object
    //   218	5	3	localObject3	Object
    //   437	149	3	localObject4	Object
    //   26	573	4	localJSONArray2	org.json.JSONArray
    //   34	524	5	localJSONArray3	org.json.JSONArray
    //   42	530	6	localJSONArray4	org.json.JSONArray
    //   50	591	7	localJSONArray5	org.json.JSONArray
    //   59	485	8	localJSONArray6	org.json.JSONArray
    //   79	782	10	localObject6	Object
    //   152	475	12	localObject8	Object
    //   383	13	14	localIterator	Iterator
    //   405	21	15	localbl	bl
    // Exception table:
    //   from	to	target	type
    //   143	154	204	finally
    //   154	201	204	finally
    //   205	208	204	finally
    //   224	270	204	finally
    //   273	276	204	finally
    //   0	5	210	finally
    //   13	70	210	finally
    //   116	143	210	finally
    //   208	210	210	finally
    //   222	224	210	finally
    //   276	299	210	finally
    //   299	374	210	finally
    //   441	443	210	finally
    //   446	672	210	finally
    //   672	698	210	finally
    //   698	724	210	finally
    //   724	751	210	finally
    //   751	778	210	finally
    //   778	804	210	finally
    //   804	831	210	finally
    //   831	874	210	finally
    //   70	81	218	finally
    //   81	110	218	finally
    //   113	116	218	finally
    //   219	222	218	finally
    //   374	385	437	finally
    //   385	434	437	finally
    //   438	441	437	finally
    //   443	446	437	finally
  }
  
  public void l(Rect paramRect)
  {
    i(paramRect, i());
  }
  
  public void l(String paramString)
  {
    this.i = paramString;
  }
  
  public void l(boolean paramBoolean)
  {
    this.m = 0L;
    this.K = false;
    synchronized (this.I)
    {
      this.I.clear();
    }
    synchronized (this.J)
    {
      this.J.clear();
      xb.i().i();
      ld.i().i();
      lk.i().l();
      yc.i().i();
      if (paramBoolean)
      {
        sc.i().i();
        mb.i().l();
        i();
      }
      return;
      localObject1 = finally;
      throw ((Throwable)localObject1);
    }
  }
  
  public boolean l()
  {
    return this.k;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/jc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */