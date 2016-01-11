package com.appsee;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

public class AppseeBackgroundUploader
  extends IntentService
{
  public AppseeBackgroundUploader()
  {
    super(i("\021E:\\4Q\031_\td(X<C>Q\013K7Q;[(Z"));
  }
  
  public static String i(String paramString)
  {
    Object localObject = new Exception().getStackTrace()[1];
    localObject = new StringBuffer(((StackTraceElement)localObject).getClassName()).insert(0, ((StackTraceElement)localObject).getMethodName()).toString();
    int k = ((String)localObject).length() - 1;
    int j = paramString.length();
    char[] arrayOfChar = new char[j];
    int n = j - 1;
    j = k;
    int m = n;
    for (;;)
    {
      if (n < 0) {}
      int i1;
      do
      {
        return new String(arrayOfChar);
        i1 = m - 1;
        arrayOfChar[m] = ((char)(paramString.charAt(m) ^ ((String)localObject).charAt(j) ^ 0x5A));
      } while (i1 < 0);
      int i = (char)(paramString.charAt(i1) ^ ((String)localObject).charAt(j) ^ 0x3F);
      m = i1 - 1;
      n = j - 1;
      arrayOfChar[i1] = i;
      j = n;
      if (n < 0) {
        j = k;
      }
      n = m;
    }
  }
  
  protected void onHandleIntent(Intent paramIntent)
  {
    localObject2 = ak.K;
    if (paramIntent != null) {}
    for (;;)
    {
      try
      {
        if (paramIntent.getExtras() == null)
        {
          mc.l(i("z?Z<O)k;\\1Xc\033\013u+?F#H>Z(\037\036y0U3MtW1S)\b\022@$K=Q{Q\004/&D'S>A~H>L,V.M"), 1);
          return;
        }
        localObject1 = ak.values()[paramIntent.getExtras().getInt("com.appsee.Action.Mode")];
      }
      catch (Exception paramIntent)
      {
        int i;
        boolean bool;
        localObject1 = localObject2;
        continue;
      }
      catch (NullPointerException paramIntent)
      {
        Object localObject1 = localObject2;
        continue;
      }
      try
      {
        mc.l(localObject1, 1);
        i = mk.G[localObject1.ordinal()];
        localObject2 = localObject1;
        switch (i)
        {
        default: 
          localObject2 = localObject1;
        }
      }
      catch (NullPointerException paramIntent)
      {
        if (paramIntent.getMessage() != "APPSEE_NO_CONTEXT") {
          continue;
        }
        mc.l(i("\rY*L?ZS\025\035p(X _\"M\017O6P\034|;I|Z5_6R9\b6\\$KqU+N\006f,K'_?[~X4P.Z5\\"), 1);
        return;
        bool = paramIntent.getExtras().getBoolean("com.appsee.Action.UploadMode");
        xc.i().F(bool);
        localObject2 = localObject1;
        continue;
      }
      catch (Exception paramIntent)
      {
        i(paramIntent, (ak)localObject1);
        localObject2 = localObject1;
        continue;
        xc.i().c();
        localObject2 = localObject1;
        continue;
        sd.i().l();
        localObject2 = localObject1;
        continue;
        if (jc.i().i()) {
          continue;
        }
        xc.i().l();
        localObject2 = localObject1;
        continue;
        mc.l(i("x.Dh^lO5M9Z1\025^u*]oY)Z)V5Q]o6R0\\tP6\035(P9F>F?S{M\017|<C<Xp\\-\033:].V;M"), 3);
        localObject2 = localObject1;
        continue;
        i(paramIntent, (ak)localObject1);
        localObject2 = localObject1;
        continue;
      }
      mc.l(localObject2, 1);
      return;
      xc.i().f();
      localObject2 = localObject1;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/AppseeBackgroundUploader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */