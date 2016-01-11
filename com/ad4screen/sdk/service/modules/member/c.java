package com.ad4screen.sdk.service.modules.member;

import android.content.Context;
import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.g;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import java.util.ArrayList;
import java.util.Date;

public final class c
{
  private static c c;
  private final d a;
  private final Context b;
  
  private c(Context paramContext)
  {
    this.b = paramContext;
    this.a = new d(paramContext);
  }
  
  private int a(String paramString, ArrayList<com.ad4screen.sdk.service.modules.member.model.b> paramArrayList)
  {
    int i = 0;
    while (i < paramArrayList.size())
    {
      if (((com.ad4screen.sdk.service.modules.member.model.b)paramArrayList.get(i)).a.equals(paramString))
      {
        Log.internal("MemberManager|Found member " + paramString + " on this device. Last connection : " + h.a(new Date(((com.ad4screen.sdk.service.modules.member.model.b)paramArrayList.get(i)).c), h.a.b));
        return i;
      }
      i += 1;
    }
    return -1;
  }
  
  public static c a(Context paramContext)
  {
    return a(paramContext, false);
  }
  
  public static c a(Context paramContext, boolean paramBoolean)
  {
    try
    {
      if ((c == null) || (paramBoolean)) {
        c = new c(paramContext.getApplicationContext());
      }
      return c;
    }
    finally {}
  }
  
  private void a(com.ad4screen.sdk.service.modules.member.model.b paramb)
  {
    if (paramb == null)
    {
      this.a.f();
      return;
    }
    this.a.a(paramb);
  }
  
  private com.ad4screen.sdk.service.modules.member.model.b b(String paramString)
  {
    com.ad4screen.sdk.service.modules.member.model.a locala = d();
    ArrayList localArrayList = locala.a;
    int i = a(paramString, localArrayList);
    if (i != -1)
    {
      ((com.ad4screen.sdk.service.modules.member.model.b)localArrayList.get(i)).c = g.e().a();
      paramString = (com.ad4screen.sdk.service.modules.member.model.b)localArrayList.get(i);
      paramString.b += 1;
      locala.a = localArrayList;
      new a((com.ad4screen.sdk.service.modules.member.model.b)localArrayList.get(i), this.b).run();
      this.a.a(locala);
      return (com.ad4screen.sdk.service.modules.member.model.b)localArrayList.get(i);
    }
    com.ad4screen.sdk.service.modules.member.model.b localb = new com.ad4screen.sdk.service.modules.member.model.b(paramString);
    Log.debug("MemberManager|Linking member " + paramString + " to this device");
    localArrayList.add(localb);
    new a(localb, this.b).run();
    this.a.a(locala);
    Log.internal("MemberManager|Member " + paramString + " is now linked to this device");
    return localb;
  }
  
  public void a()
  {
    if (!c())
    {
      Log.internal("MemberManager|No member currently logged in. No member to log out");
      return;
    }
    Log.debug("MemberManager|Logging out member : " + b());
    a(null);
    Log.internal("MemberManager|Logged out");
  }
  
  public void a(A4S.Callback<com.ad4screen.sdk.service.modules.member.model.a> paramCallback)
  {
    b(paramCallback);
  }
  
  public void a(String paramString)
  {
    if (c()) {
      a();
    }
    paramString = b(paramString);
    a(paramString);
    Log.debug("MemberManager|Member : " + paramString.a + " is now connected. Total Connections : " + paramString.b);
  }
  
  public void a(String[] paramArrayOfString)
  {
    com.ad4screen.sdk.service.modules.member.model.a locala = d();
    ArrayList localArrayList = locala.a;
    new f(paramArrayOfString, this.b).run();
    int i = 0;
    while (i < paramArrayOfString.length)
    {
      if (b().equals(paramArrayOfString[i])) {
        a();
      }
      Log.internal("MemberManager|Removing member " + paramArrayOfString[i]);
      int j = 0;
      while (j < localArrayList.size())
      {
        if (((com.ad4screen.sdk.service.modules.member.model.b)localArrayList.get(j)).a.equals(paramArrayOfString[i]))
        {
          localArrayList.remove(j);
          Log.debug("MemberManager|Member " + paramArrayOfString[i] + " has been removed from this device");
        }
        j += 1;
      }
      i += 1;
    }
    this.a.a(locala);
  }
  
  public String b()
  {
    String str2 = "";
    com.ad4screen.sdk.service.modules.member.model.b localb = this.a.e();
    String str1 = str2;
    if (localb != null)
    {
      str1 = str2;
      if (localb.a != null) {
        str1 = localb.a;
      }
    }
    return str1;
  }
  
  public void b(final A4S.Callback<com.ad4screen.sdk.service.modules.member.model.a> paramCallback)
  {
    final com.ad4screen.sdk.service.modules.member.model.a locala = d();
    new b(this.b, new b.a()
    {
      public void a()
      {
        Log.debug("MemberManager|Can't update Members right now. Retrieving from local storage");
        paramCallback.onResult(c.this.d());
      }
      
      public void a(String[] paramAnonymousArrayOfString)
      {
        int i = 0;
        while (i < paramAnonymousArrayOfString.length)
        {
          if (c.a(c.this, paramAnonymousArrayOfString[i], locala.a) == -1)
          {
            com.ad4screen.sdk.service.modules.member.model.b localb = new com.ad4screen.sdk.service.modules.member.model.b(paramAnonymousArrayOfString[i]);
            locala.a.add(localb);
            c.a(c.this).a(locala);
          }
          i += 1;
        }
        paramCallback.onResult(locala);
      }
    }).run();
  }
  
  public boolean c()
  {
    return (b() != null) && (b().length() > 0);
  }
  
  public com.ad4screen.sdk.service.modules.member.model.a d()
  {
    return this.a.a();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/member/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */