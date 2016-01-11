package com.ad4screen.sdk.common.tasks;

import android.content.Context;
import android.net.Uri;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.compatibility.k;
import com.ad4screen.sdk.common.e;
import com.ad4screen.sdk.common.g;
import com.ad4screen.sdk.common.persistence.d;
import com.ad4screen.sdk.service.modules.authentication.a.a;
import com.ad4screen.sdk.service.modules.authentication.a.b;
import com.ad4screen.sdk.service.modules.authentication.a.c;
import com.ad4screen.sdk.service.modules.tracking.h.d;
import com.ad4screen.sdk.service.modules.tracking.h.f;
import com.ad4screen.sdk.systems.f;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class c
  implements com.ad4screen.sdk.common.persistence.c<c>, d, Runnable
{
  private static com.ad4screen.sdk.service.modules.authentication.b l;
  HttpURLConnection a;
  public long b = g.e().a();
  private String c = "text/xml;charset=utf-8";
  private int d = 1;
  private boolean e = false;
  private boolean f = false;
  private int g;
  private long h = g.e().a();
  private int i = 0;
  private int j = 0;
  private com.ad4screen.sdk.systems.b k;
  private Context m;
  private a.c n = new a.c()
  {
    public void a()
    {
      f.a().b(a.a.class, c.c(c.this));
      f.a().b(a.b.class, c.c(c.this));
      Log.error("Could not retrieve a valid token");
      c.this.a(new ConnectException("Could not retrieve a valid token"));
      f.a().a(new d.a(c.this, c.this.c()));
    }
    
    public void a(com.ad4screen.sdk.service.modules.authentication.model.a paramAnonymousa, boolean paramAnonymousBoolean)
    {
      f.a().b(a.a.class, c.c(c.this));
      f.a().b(a.b.class, c.c(c.this));
      c.this.a(c.this.e(), c.this.d());
    }
  };
  
  public c(Context paramContext)
  {
    this.m = paramContext;
    this.k = com.ad4screen.sdk.systems.b.a(this.m);
  }
  
  private String a(InputStream paramInputStream)
    throws IOException
  {
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
      StringBuilder localStringBuilder = new StringBuilder(8192);
      for (String str = localBufferedReader.readLine(); str != null; str = localBufferedReader.readLine()) {
        localStringBuilder.append(str);
      }
      str = localStringBuilder.toString();
      return str;
    }
    catch (IOException localIOException)
    {
      throw localIOException;
    }
    finally
    {
      if (paramInputStream != null) {
        paramInputStream.close();
      }
    }
  }
  
  protected static String a(String paramString1, String paramString2, long paramLong)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (paramString2 != null) {
      localStringBuilder.append(paramString2);
    }
    if (paramString1 != null) {
      localStringBuilder.append(paramString1);
    }
    localStringBuilder.append(paramLong);
    return com.ad4screen.sdk.common.h.b(localStringBuilder.toString());
  }
  
  private void a(HttpURLConnection paramHttpURLConnection, String paramString)
  {
    c("application/json;charset=utf-8");
    long l1 = Calendar.getInstance(Locale.US).getTimeInMillis() / 1000L;
    Object localObject = null;
    StringBuilder localStringBuilder = new StringBuilder();
    if (this.k != null)
    {
      localObject = com.ad4screen.sdk.systems.h.a(this.m).g();
      if (localObject != null)
      {
        if ((((com.ad4screen.sdk.service.modules.authentication.model.a)localObject).b != null) && (!c().equals(com.ad4screen.sdk.systems.d.b.b.toString()))) {
          localStringBuilder.append(((com.ad4screen.sdk.service.modules.authentication.model.a)localObject).b);
        }
        if ((((com.ad4screen.sdk.service.modules.authentication.model.a)localObject).a != null) && (!c().equals(com.ad4screen.sdk.systems.d.b.b.toString()))) {
          localStringBuilder.append(" ").append(((com.ad4screen.sdk.service.modules.authentication.model.a)localObject).a);
        }
      }
      if (localStringBuilder.length() > 0) {
        paramHttpURLConnection.setRequestProperty("Authorization", localStringBuilder.toString());
      }
      localObject = this.k.e;
    }
    paramHttpURLConnection.setRequestProperty("Accengage-Signature", a((String)localObject, paramString, l1));
    paramHttpURLConnection.setRequestProperty("Accengage-Time", String.valueOf(l1));
  }
  
  private void b()
  {
    f.a().a(a.a.class, this.n);
    f.a().a(a.b.class, this.n);
    if (l != null) {
      l.run();
    }
  }
  
  protected void a(int paramInt)
  {
    this.d = paramInt;
  }
  
  protected abstract void a(String paramString);
  
  public void a(String paramString1, String paramString2)
  {
    this.i += 1;
    String str = paramString2;
    if (paramString2 != null)
    {
      str = paramString2;
      if (paramString2.contains("null")) {
        str = null;
      }
    }
    try
    {
      this.a = ((HttpURLConnection)new URL(com.ad4screen.sdk.common.h.a(this.m, paramString1, new e[] { new e("partnerId", Uri.encode(this.k.d)), new e("sharedId", Uri.encode(this.k.f)) })).openConnection());
      if (n())
      {
        a(this.a, str);
        paramString2 = com.ad4screen.sdk.systems.h.a(this.m).g();
        if ((c() != null) && (!c().equals(com.ad4screen.sdk.systems.d.b.b.toString())) && ((paramString2 == null) || (paramString2.a == null) || (paramString2.b == null)))
        {
          b();
          return;
        }
      }
    }
    catch (MalformedURLException paramString2)
    {
      int i1;
      Log.internal("URLConnectionTask URL error @ " + paramString1, paramString2);
      a(paramString2);
      f.a().a(new d.a(this, c()));
      return;
      if (i1 != 401) {
        break label556;
      }
      Log.internal("Token expired, retrieving new one");
      this.a.disconnect();
      b();
      return;
      if (i1 != 200) {
        break label646;
      }
      paramString2 = a(this.a.getInputStream());
      Log.internal("Server response @ " + paramString1 + " : " + paramString2);
      a(paramString2);
      f.a().a(new d.b(this, c()));
      for (;;)
      {
        return;
        paramString2 = a(this.a.getErrorStream());
        Log.error("Could not reach Accengage servers");
        if (!a(i1, paramString2)) {
          break;
        }
        f.a().a(new d.b(this, c()));
      }
    }
    catch (IOException paramString2)
    {
      while ((n()) && (paramString2.getMessage() != null) && (paramString2.getMessage().contains("authentication challenge")))
      {
        Log.internal("Token invalid, retrying with new one");
        b();
        return;
        a(new ConnectException("Could not reach Accengage servers"));
        f.a().a(new d.a(this, c()));
      }
    }
    catch (RuntimeException paramString1)
    {
      Log.internal("Tracking needs more permission to work. Please refer to the documentation.", paramString1);
      a(paramString1);
      f.a().a(new d.a(this, c()));
      return;
      if (!a(-1, paramString2.getMessage())) {
        break label880;
      }
      a("");
      f.a().a(new d.b(this, c()));
      for (;;)
      {
        return;
        Log.internal("URLConnectionTask IO error! @ " + paramString1, paramString2);
        a(paramString2);
        f.a().a(new d.a(this, c()));
      }
    }
    finally
    {
      if (this.a == null) {
        break label945;
      }
      this.a.disconnect();
    }
    this.a.setDoInput(true);
    this.a.setUseCaches(false);
    this.a.setConnectTimeout(10000);
    this.a.setReadTimeout(10000);
    this.a.setRequestProperty("Content-Type", this.c);
    this.a.setRequestProperty("User-Agent", k.a(this.m));
    this.a.setInstanceFollowRedirects(true);
    if (str == null)
    {
      this.a.setDoOutput(false);
      this.a.setRequestMethod("GET");
    }
    for (;;)
    {
      i1 = this.a.getResponseCode();
      Log.internal("Server response code @ " + paramString1 + ": " + i1);
      if (i1 / 100 != 3) {
        break label517;
      }
      paramString2 = this.a.getHeaderField("Location");
      this.a.disconnect();
      a(paramString2, str);
      if (this.a == null) {
        break;
      }
      this.a.disconnect();
      return;
      this.a.setDoOutput(true);
      this.a.setRequestMethod("POST");
      paramString2 = new BufferedOutputStream(this.a.getOutputStream());
      Log.internal("Request :\n" + str);
      paramString2.write(str.getBytes());
      paramString2.close();
    }
  }
  
  protected abstract void a(Throwable paramThrowable);
  
  protected boolean a()
  {
    return true;
  }
  
  protected boolean a(int paramInt, String paramString)
  {
    return false;
  }
  
  public abstract c b(c paramc);
  
  protected void b(int paramInt)
  {
    this.d |= paramInt;
  }
  
  public void b(String paramString)
  {
    com.ad4screen.sdk.common.cache.a locala = com.ad4screen.sdk.common.cache.a.a(this.m);
    if ((this.e) || ((paramString != null) && (paramString.equals(com.ad4screen.sdk.systems.d.b.a.toString()))))
    {
      this.j += 1;
      a(e(), d());
    }
    do
    {
      do
      {
        return;
        if (l())
        {
          this.e = true;
          locala.a(this, paramString);
        }
        if (this.j < 3) {
          break;
        }
        Log.internal("This task has been retried too many times, will be retried at next flush");
        this.j = 2;
        f.a().a(new d.a(this, c()));
      } while (!c().equals(com.ad4screen.sdk.systems.d.b.b.toString()));
      f.a().a(new a.a());
      return;
      if (!l())
      {
        this.j += 1;
        locala.a(new Runnable()
        {
          public void run()
          {
            c.this.a(c.this.e(), c.this.d());
          }
        });
        return;
      }
    } while ((this.d & 0x4) == 0);
    this.j += 1;
    locala.f();
  }
  
  protected abstract String c();
  
  public void c(int paramInt)
  {
    this.h = g.e().a();
    this.g = paramInt;
  }
  
  protected void c(String paramString)
  {
    this.c = paramString;
  }
  
  public c d(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.common.tasks.URLConnectionTask");
    if (!paramString.isNull("flags")) {
      this.d = paramString.getInt("flags");
    }
    if (!paramString.isNull("cached")) {
      this.e = paramString.getBoolean("cached");
    }
    if (!paramString.isNull("alreadyPrepared")) {
      this.f = paramString.getBoolean("alreadyPrepared");
    }
    if (!paramString.isNull("contentType")) {
      this.c = paramString.getString("contentType");
    }
    if (!paramString.isNull("creationTimestamp")) {
      this.b = paramString.getLong("creationTimestamp");
    }
    if ((!paramString.isNull("isSecure")) && (paramString.getBoolean("isSecure"))) {
      h();
    }
    return this;
  }
  
  protected abstract String d();
  
  protected abstract String e();
  
  public void f()
  {
    if ((this.d & 0x2) != 0) {
      a();
    }
    a(e(), d());
  }
  
  public int g()
  {
    return this.d;
  }
  
  public abstract String getClassKey();
  
  protected void h()
  {
    if (l == null) {
      l = new com.ad4screen.sdk.service.modules.authentication.b(this.m);
    }
    if (n()) {
      return;
    }
    b(8);
  }
  
  protected void i()
  {
    b(16);
  }
  
  public int j()
  {
    return this.g;
  }
  
  public boolean k()
  {
    return (g.e().a() - this.h > this.g) && (this.i < 6);
  }
  
  public boolean l()
  {
    return (g() & 0x1) != 0;
  }
  
  public boolean m()
  {
    return (g() & 0x10) != 0;
  }
  
  public boolean n()
  {
    return (g() & 0x8) != 0;
  }
  
  public boolean o()
  {
    return e() != null;
  }
  
  public int p()
  {
    return this.i;
  }
  
  public int q()
  {
    return 6;
  }
  
  public void run()
  {
    String str = e();
    Log.internal("Starting URL request @ " + str);
    if (com.ad4screen.sdk.systems.b.a(this.m).f == null)
    {
      Log.debug("No Shared id available yet, waiting..");
      f.a().a(h.f.class, new h.d()
      {
        public void a()
        {
          c.this.run();
        }
      });
      return;
    }
    if ((!this.f) && (!a()))
    {
      Log.internal("Cancelled URL request @ " + str);
      return;
    }
    this.f = true;
    b(c());
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("flags", this.d);
    localJSONObject2.put("cached", this.e);
    localJSONObject2.put("alreadyPrepared", this.f);
    localJSONObject2.put("contentType", this.c);
    localJSONObject2.put("creationTimestamp", this.b);
    localJSONObject2.put("isSecure", n());
    localJSONObject1.put("type", getClassKey());
    localJSONObject1.put("com.ad4screen.sdk.common.tasks.URLConnectionTask", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/tasks/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */