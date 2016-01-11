package com.appsee;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

class ib
{
  protected static Object b = new Object();
  protected static String i = "c03e86fd74e090eadb4dc2f5e57b1842";
  protected final int m = 60000;
  
  protected JSONObject i(String paramString)
    throws Exception
  {
    paramString = new JSONObject(paramString);
    String str = paramString.optString(AppseeBackgroundUploader.i("1$A2J"), null);
    if (str != null) {
      throw new Exception(str);
    }
    return paramString;
  }
  
  protected JSONObject i(String paramString, JSONObject paramJSONObject)
    throws Exception
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put(AppseeBackgroundUploader.i("\017F4K?QeY\"J-]"), AppseeBackgroundUploader.i(".Z?F%J;K3P[<@2V"));
    return i(paramString, localHashMap, new StringEntity(paramJSONObject.toString(), AppseeBackgroundUploader.i("!\002up\000")));
  }
  
  protected JSONObject i(String paramString1, byte[] paramArrayOfByte, String paramString2, Map<String, String> paramMap)
    throws Exception
  {
    String str = UUID.randomUUID().toString().replace(AppseeBackgroundUploader.i("\025"), "");
    paramArrayOfByte = i(paramArrayOfByte, paramString2, paramMap, str);
    paramString2 = new HashMap();
    paramString2.put(AppseeBackgroundUploader.i("\017F4K?QeY\"J-]"), str);
    paramString2.put(AppseeBackgroundUploader.i("i#G.Z4K]\0218T)P"), Integer.toString(paramArrayOfByte.length));
    return i(paramString1, paramString2, new ByteArrayEntity(paramArrayOfByte));
  }
  
  public JSONObject i(byte[] paramArrayOfByte, String paramString1, String paramString2, long paramLong1, long paramLong2)
    throws Exception
  {
    String str = AppseeBackgroundUploader.i("\020d\004:\\<\\");
    HashMap localHashMap = new HashMap();
    localHashMap.put(AppseeBackgroundUploader.i("l?Lb\0359]\024\\"), paramString1);
    localHashMap.put(AppseeBackgroundUploader.i("\017\\(M?Qe=8W8@"), Long.toString(paramLong1));
    localHashMap.put(AppseeBackgroundUploader.i("k5Kp\030\005Z']"), Long.toString(paramLong2));
    return i(str, paramArrayOfByte, paramString2, localHashMap);
  }
  
  public void i()
  {
    for (;;)
    {
      synchronized (b)
      {
        if ((this.k != null) && (!this.k.isAborted()))
        {
          Thread localThread = new Thread(new tb(this), AppseeBackgroundUploader.i("r,I#P7yt\000)T=A\034^#Y*V4XE\034$V<\\"));
          try
          {
            localThread.start();
            localThread.join();
            return;
          }
          catch (InterruptedException localInterruptedException)
          {
            vd.l(localInterruptedException, AppseeBackgroundUploader.i("v.K?Grde\033.K&D(\n\"L.H5MzT5R1T"));
          }
        }
      }
    }
  }
  
  protected byte[] i(byte[] paramArrayOfByte, String paramString1, Map<String, String> paramMap, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      localStringBuilder.append(paramString2 + AppseeBackgroundUploader.i("P2"));
      localStringBuilder.append((String)localEntry.getKey() + AppseeBackgroundUploader.i("V[9P2"));
      localStringBuilder.append((String)localEntry.getValue() + AppseeBackgroundUploader.i("P2"));
    }
    localStringBuilder.append(paramString2 + AppseeBackgroundUploader.i("P2"));
    localStringBuilder.append(paramString1 + AppseeBackgroundUploader.i("t\bP2"));
    localStringBuilder.append(AppseeBackgroundUploader.i("\fE\"]?Q.\022E\r&Vg\0307C,U9V3Cx\0330\024 I;O8\004)K(Zp\031[9P2"));
    paramMap = paramString2 + AppseeBackgroundUploader.i("{\036P2");
    paramString1 = localStringBuilder.toString().getBytes();
    paramMap = paramMap.getBytes();
    paramString2 = new byte[paramString1.length + paramArrayOfByte.length + paramMap.length];
    System.arraycopy(paramString1, 0, paramString2, 0, paramString1.length);
    System.arraycopy(paramArrayOfByte, 0, paramString2, paramString1.length, paramArrayOfByte.length);
    System.arraycopy(paramMap, 0, paramString2, paramString1.length + paramArrayOfByte.length, paramMap.length);
    return paramString2;
  }
  
  public JSONObject l(String paramString)
    throws Exception
  {
    String str = AppseeBackgroundUploader.i("\020r\0338U4_");
    JSONObject localJSONObject = new JSONObject();
    Object localObject2 = rj.i();
    ki localki = rj.i();
    Object localObject1 = localObject2[0];
    localObject2 = localObject2[1];
    localJSONObject.put(AppseeBackgroundUploader.i("\033O*it\006%Z2V"), rj.E());
    localJSONObject.put(AppseeBackgroundUploader.i("\030J\020:V\024\\"), rj.l());
    localJSONObject.put(AppseeBackgroundUploader.i("\036Z,Vr\021\025\\9]"), rj.j());
    localJSONObject.put(AppseeBackgroundUploader.i("p\tit\006%Z2V"), rj.c());
    localJSONObject.put(AppseeBackgroundUploader.i("z!N=E%M\t{\021it\006%Z2V"), rj.i());
    localJSONObject.put(AppseeBackgroundUploader.i("\037P)K?R]\0335R1]"), rj.k());
    localJSONObject.put(AppseeBackgroundUploader.i("\017F4Q?\\e\035 Z)A"), rj.i().ordinal());
    localJSONObject.put(AppseeBackgroundUploader.i("z9M?Z#?W)P"), ((Dimension)localObject2).getWidth());
    localJSONObject.put(AppseeBackgroundUploader.i("\037J(Z?QY\021?T5L"), ((Dimension)localObject2).getHeight());
    localJSONObject.put(AppseeBackgroundUploader.i("\031S3Z\000\002Z0]"), lb.i(new Date()));
    localJSONObject.put(AppseeBackgroundUploader.i("m?Nd\021%G\024\\"), paramString);
    paramString = gb.i();
    if (paramString != null) {
      localJSONObject.put(AppseeBackgroundUploader.i("e)L @4Z\tZb\007?\\3K"), paramString);
    }
    localJSONObject.put(AppseeBackgroundUploader.i("\032^9C,O\017F>Z\023Qe\021$]<T"), rj.i());
    localJSONObject.put(AppseeBackgroundUploader.i("\001H4J<^r\000#A8J"), rj.H());
    localJSONObject.put(AppseeBackgroundUploader.i("d*^\037J(Z?QY\021?T5L"), ((Dimension)localObject1).getHeight());
    localJSONObject.put(AppseeBackgroundUploader.i("\001O8z9M?Z#?W)P"), ((Dimension)localObject1).getWidth());
    localJSONObject.put(AppseeBackgroundUploader.i("l9Mt\0218w-Q"), rj.l());
    localJSONObject.put(AppseeBackgroundUploader.i("\016S6Y&I-E\027Z4JS\001\"G2V"), rj.l());
    localJSONObject.put(AppseeBackgroundUploader.i("y5Qe'5R1]"), rj.l());
    localJSONObject.put(AppseeBackgroundUploader.i("\013O:@9Z\017Qx\005#V\024|"), rj.F());
    localJSONObject.put(AppseeBackgroundUploader.i("\027U&^&K f(V?Qe\025\"Z2V"), localki.ordinal());
    paramString = ob.i();
    if (paramString != null) {
      localJSONObject.put(AppseeBackgroundUploader.i("\007K=N;H(Z\037Qr\0332V/K"), new JSONArray(paramString));
    }
    mc.l(localJSONObject, 1);
    return i(str, localJSONObject);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/ib.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */