package com.ad4screen.sdk.common.cache;

import com.ad4screen.sdk.common.a;
import com.ad4screen.sdk.common.g;
import com.ad4screen.sdk.common.tasks.c;

public class b
{
  public static c a(c paramc1, c paramc2)
  {
    if (paramc1 == null)
    {
      if (paramc2 != null) {
        return paramc2;
      }
      return null;
    }
    paramc1.b = g.e().a();
    c localc = paramc1;
    if ((paramc1.g() & 0x2) == 0) {
      if (paramc2 != null) {
        break label43;
      }
    }
    label43:
    for (localc = paramc1;; localc = paramc1.b(paramc2)) {
      return localc;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/cache/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */