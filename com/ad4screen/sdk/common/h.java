package com.ad4screen.sdk.common;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.compatibility.k.g;
import com.ad4screen.sdk.service.modules.member.c;
import com.ad4screen.sdk.systems.b;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class h
{
  public static String a()
  {
    return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Resources.getSystem().getConfiguration().locale).format(Calendar.getInstance().getTime());
  }
  
  public static String a(Context paramContext, String paramString, boolean paramBoolean, e... paramVarArgs)
  {
    if (paramString == null) {
      return null;
    }
    b localb = b.a(paramContext);
    c localc = c.a(paramContext);
    Object localObject = "portrait";
    if (k.g.a(paramContext) == 2) {
      localObject = "landscape";
    }
    label90:
    int i;
    if (localb.f == null)
    {
      paramContext = "";
      paramString = paramString.replace("|id|", paramContext).replace("|country|", Uri.encode(localb.k)).replace("|lang|", Uri.encode(localb.m));
      if (localb.d != null) {
        break label217;
      }
      paramContext = "";
      paramString = paramString.replace("|pid|", paramContext).replace("|o|", (CharSequence)localObject).replace("|uid|", Uri.encode(localc.b()));
      paramContext = paramString;
      if (paramVarArgs == null) {
        break label235;
      }
      int j = paramVarArgs.length;
      i = 0;
      paramContext = paramString;
      label133:
      if (i >= j) {
        break label235;
      }
      localObject = paramVarArgs[i];
      if (((e)localObject).a == null) {
        break label259;
      }
      if (((e)localObject).b != null) {
        break label226;
      }
      paramString = "";
      label165:
      paramContext = paramContext.replace("|" + ((e)localObject).a + "|", paramString);
    }
    label217:
    label226:
    label235:
    label259:
    for (;;)
    {
      i += 1;
      break label133;
      paramContext = localb.f;
      break;
      paramContext = localb.d;
      break label90;
      paramString = ((e)localObject).b;
      break label165;
      paramString = paramContext;
      if (paramBoolean) {
        paramString = paramContext.replaceAll("\\|.*?\\|", "");
      }
      return paramString.replaceAll("&amp;", "&");
    }
  }
  
  public static String a(Context paramContext, String paramString, e... paramVarArgs)
  {
    return a(paramContext, paramString, true, paramVarArgs);
  }
  
  public static String a(String paramString)
  {
    try
    {
      String str = URLEncoder.encode(paramString, "UTF-8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      Log.internal("Could not urlencode string \"" + paramString + "\"", localUnsupportedEncodingException);
    }
    return "";
  }
  
  public static String a(String paramString, String... paramVarArgs)
  {
    if ((paramVarArgs == null) || (paramVarArgs.length == 0)) {
      return "";
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramVarArgs[0]);
    int i = 1;
    while (i < paramVarArgs.length)
    {
      localStringBuilder.append(paramString).append(paramVarArgs[i]);
      i += 1;
    }
    return localStringBuilder.toString();
  }
  
  public static String a(Date paramDate, a parama)
  {
    if (paramDate == null) {
      return null;
    }
    if (parama == a.b) {}
    for (parama = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);; parama = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.US))
    {
      parama.setTimeZone(TimeZone.getTimeZone("GMT"));
      return parama.format(paramDate);
    }
  }
  
  private static String a(byte[] paramArrayOfByte)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int m = paramArrayOfByte.length;
    int i = 0;
    int n;
    int j;
    int k;
    if (i < m)
    {
      n = paramArrayOfByte[i];
      j = n >>> 4 & 0xF;
      k = 0;
    }
    for (;;)
    {
      if ((j >= 0) && (j <= 9)) {}
      for (char c = (char)(j + 48);; c = (char)(j - 10 + 97))
      {
        localStringBuilder.append(c);
        if (k < 1) {
          break label91;
        }
        i += 1;
        break;
      }
      return localStringBuilder.toString();
      label91:
      k += 1;
      j = n & 0xF;
    }
  }
  
  public static String a(e... paramVarArgs)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if ((paramVarArgs != null) && (paramVarArgs.length > 0))
    {
      localStringBuilder.append(paramVarArgs[0].a).append('=').append(a(paramVarArgs[0].b));
      int i = 1;
      while (i < paramVarArgs.length)
      {
        localStringBuilder.append('&').append(paramVarArgs[i].a).append('=').append(a(paramVarArgs[i].b));
        i += 1;
      }
    }
    return localStringBuilder.toString();
  }
  
  public static Date a(String paramString, a parama)
  {
    if (paramString == null) {
      return null;
    }
    SimpleDateFormat localSimpleDateFormat;
    if (parama == a.b)
    {
      localObject = paramString;
      if (paramString.endsWith("Z")) {
        localObject = paramString.substring(0, paramString.length() - 1) + "+0000";
      }
      localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
      paramString = (String)localObject;
    }
    for (Object localObject = localSimpleDateFormat;; localObject = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.US)) {
      try
      {
        localObject = ((SimpleDateFormat)localObject).parse(paramString);
        return (Date)localObject;
      }
      catch (ParseException localParseException)
      {
        if (parama != a.b) {
          break;
        }
        parama = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
        try
        {
          parama = parama.parse(paramString);
          return parama;
        }
        catch (ParseException parama)
        {
          Log.error("Could not parse date : " + paramString, localParseException);
          return null;
        }
        Log.error("Could not parse date : " + paramString, localParseException);
      }
    }
    return null;
  }
  
  public static String b(String paramString)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-1");
      paramString = paramString.getBytes("UTF-8");
      localMessageDigest.update(paramString, 0, paramString.length);
      paramString = a(localMessageDigest.digest());
      return paramString;
    }
    catch (NoSuchAlgorithmException paramString)
    {
      Log.internal("No SHA-1 algorithm found", paramString);
      return null;
    }
    catch (UnsupportedEncodingException paramString)
    {
      for (;;)
      {
        Log.internal("Error while encoding string", paramString);
      }
    }
  }
  
  public static enum a
  {
    private a() {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */