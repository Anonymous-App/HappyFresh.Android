package com.ad4screen.sdk.service.modules.common;

import android.content.Context;
import android.os.Bundle;
import com.ad4screen.sdk.A4SService.a;
import com.ad4screen.sdk.service.modules.inapp.a;
import com.ad4screen.sdk.service.modules.profile.b;
import com.ad4screen.sdk.service.modules.profile.c;

public final class h
{
  public static void a(Context paramContext, String paramString, com.ad4screen.sdk.common.e... paramVarArgs)
  {
    new g(paramContext, paramString, paramVarArgs).run();
  }
  
  public static void a(A4SService.a parama, Bundle paramBundle, boolean paramBoolean)
  {
    if (paramBoolean) {
      new c(parama.a(), paramBundle).run();
    }
    for (;;)
    {
      parama.d().e();
      return;
      new b(parama.a(), paramBundle).run();
    }
  }
  
  public static void a(A4SService.a parama, String paramString)
  {
    new f(parama.a(), paramString).run();
  }
  
  public static void a(A4SService.a parama, String paramString, d.a parama1)
  {
    new d(parama.a(), paramString, parama1).run();
  }
  
  public static void a(A4SService.a parama, String paramString, e.a parama1)
  {
    new e(parama.a(), paramString, parama1).run();
  }
  
  public static void a(A4SService.a parama, String paramString1, String paramString2, d.a parama1)
  {
    new d(parama.a(), paramString1, paramString2, parama1).run();
  }
  
  public static void a(A4SService.a parama, String paramString1, String paramString2, e.a parama1)
  {
    new e(parama.a(), paramString1, paramString2, parama1).run();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/common/h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */