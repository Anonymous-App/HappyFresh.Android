package com.ad4screen.sdk.common;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.compatibility.k.c;
import com.ad4screen.sdk.common.compatibility.k.k;
import com.ad4screen.sdk.common.compatibility.k.l;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Locale;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public final class i
{
  private static double a(double paramDouble)
  {
    return 3.141592653589793D * paramDouble / 180.0D;
  }
  
  public static int a(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    double d1 = Math.sin(a(paramDouble1));
    double d2 = Math.sin(a(paramDouble3));
    paramDouble1 = Math.cos(a(paramDouble1));
    paramDouble3 = Math.cos(a(paramDouble3));
    return (int)(b(Math.acos(Math.cos(a(paramDouble2 - paramDouble4)) * (paramDouble1 * paramDouble3) + d1 * d2)) * 60.0D * 1.1515D * 1.609344D * 1000.0D);
  }
  
  public static int a(Context paramContext, int paramInt)
  {
    return (int)(paramContext.getResources().getDisplayMetrics().density * paramInt + 0.5F);
  }
  
  public static <T> T a(Class<T> paramClass, Bundle paramBundle, String paramString, T paramT)
  {
    paramBundle = paramBundle.get(paramString);
    if (paramClass.isInstance(paramBundle)) {
      paramT = paramClass.cast(paramBundle);
    }
    return paramT;
  }
  
  public static String a(Context paramContext, String paramString, Class<? extends Service> paramClass)
  {
    if (paramContext == null) {}
    for (;;)
    {
      return null;
      paramContext = paramContext.getApplicationContext();
      try
      {
        paramContext = paramContext.getPackageManager().getServiceInfo(new ComponentName(paramContext, paramClass), 128);
        if ((paramContext.metaData != null) && (paramContext.metaData.containsKey(paramString)))
        {
          paramContext = paramContext.metaData.get(paramString).toString();
          return paramContext;
        }
      }
      catch (Exception paramContext)
      {
        Log.internal("Could not load service metadata", paramContext);
      }
    }
    return null;
  }
  
  public static String a(PackageInfo paramPackageInfo, ApplicationInfo paramApplicationInfo)
  {
    long l2 = k.l.a(paramPackageInfo);
    long l1 = l2;
    if (l2 == 0L)
    {
      paramPackageInfo = new File(paramApplicationInfo.dataDir);
      Log.internal("Fetching install time from app data dir : " + paramApplicationInfo.packageName + " => " + paramApplicationInfo.dataDir + " modified :" + paramPackageInfo.lastModified());
      l1 = paramPackageInfo.lastModified();
    }
    l2 = l1;
    if (l1 == 0L)
    {
      paramPackageInfo = new File(paramApplicationInfo.sourceDir);
      Log.internal("Fetching install time from app source dir : " + paramApplicationInfo.packageName + " => " + paramApplicationInfo.sourceDir + " modified :" + paramPackageInfo.lastModified());
      l2 = paramPackageInfo.lastModified();
    }
    if (l2 != 0L)
    {
      paramPackageInfo = h.a(new Date(l2), h.a.b);
      if (paramPackageInfo != null) {
        return paramPackageInfo;
      }
    }
    return "";
  }
  
  public static String a(String paramString)
  {
    int i = 0;
    try
    {
      Object localObject = MessageDigest.getInstance("MD5");
      ((MessageDigest)localObject).reset();
      ((MessageDigest)localObject).update(paramString.getBytes());
      paramString = ((MessageDigest)localObject).digest();
      localObject = new StringBuilder();
      while (i < paramString.length)
      {
        ((StringBuilder)localObject).append(String.format("%1$02x", new Object[] { Integer.valueOf(paramString[i] & 0xFF) }));
        i += 1;
      }
      paramString = ((StringBuilder)localObject).toString();
      return paramString;
    }
    catch (Exception paramString)
    {
      Log.error("Could not use md5 digest algorithm ", paramString);
    }
    return "";
  }
  
  public static String a(Cipher paramCipher, String paramString)
  {
    try
    {
      paramCipher = k.c.a(paramCipher.doFinal(paramString.getBytes()), 0);
      return paramCipher;
    }
    catch (IllegalBlockSizeException paramCipher)
    {
      Log.error("Illegal Block Size ", paramCipher);
      return null;
    }
    catch (BadPaddingException paramCipher)
    {
      for (;;)
      {
        Log.error("Bad Padding ", paramCipher);
      }
    }
  }
  
  public static String a(byte[] paramArrayOfByte, String paramString, SecretKey paramSecretKey)
  {
    try
    {
      Cipher localCipher = Cipher.getInstance(b());
      localCipher.init(2, paramSecretKey, new IvParameterSpec(paramArrayOfByte));
      paramArrayOfByte = new String(localCipher.doFinal(k.c.a(paramString, 0)));
      return paramArrayOfByte;
    }
    catch (NoSuchAlgorithmException paramArrayOfByte)
    {
      Log.error("Could not use AES algorithm ", paramArrayOfByte);
      return null;
    }
    catch (NoSuchPaddingException paramArrayOfByte)
    {
      for (;;)
      {
        Log.error("Padding problem ", paramArrayOfByte);
      }
    }
    catch (IllegalBlockSizeException paramArrayOfByte)
    {
      for (;;)
      {
        Log.error("Illegal Block Size ", paramArrayOfByte);
      }
    }
    catch (BadPaddingException paramArrayOfByte)
    {
      for (;;)
      {
        Log.error("Bad Padding ", paramArrayOfByte);
      }
    }
    catch (InvalidKeyException paramArrayOfByte)
    {
      for (;;)
      {
        Log.error("Invalid key ", paramArrayOfByte);
      }
    }
    catch (InvalidAlgorithmParameterException paramArrayOfByte)
    {
      for (;;)
      {
        Log.error("Invalid Algorithm Parameter ", paramArrayOfByte);
      }
    }
  }
  
  public static Cipher a(SecretKey paramSecretKey)
  {
    try
    {
      Cipher localCipher = Cipher.getInstance(b());
      localCipher.init(1, paramSecretKey);
      return localCipher;
    }
    catch (NoSuchAlgorithmException paramSecretKey)
    {
      Log.error("Could not use AES algorithm ", paramSecretKey);
      return null;
    }
    catch (NoSuchPaddingException paramSecretKey)
    {
      for (;;)
      {
        Log.error("Padding problem ", paramSecretKey);
      }
    }
    catch (InvalidKeyException paramSecretKey)
    {
      for (;;)
      {
        Log.error("Invalid key ", paramSecretKey);
      }
    }
  }
  
  @SuppressLint({"TrulyRandom"})
  public static SecretKey a()
  {
    try
    {
      Object localObject = new SecureRandom();
      KeyGenerator localKeyGenerator = KeyGenerator.getInstance("AES");
      localKeyGenerator.init(256, (SecureRandom)localObject);
      localObject = localKeyGenerator.generateKey();
      return (SecretKey)localObject;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      Log.error("Could not use AES algorithm ", localNoSuchAlgorithmException);
    }
    return null;
  }
  
  public static void a(Context paramContext, Intent paramIntent)
  {
    paramIntent.setPackage(paramContext.getPackageName());
    paramContext.sendBroadcast(paramIntent, paramContext.getPackageName() + ".permission.A4S_SEND");
  }
  
  public static void a(String paramString, final A4S.Callback<Bitmap> paramCallback, boolean paramBoolean)
  {
    new AsyncTask()
    {
      protected Bitmap a(String... paramAnonymousVarArgs)
      {
        try
        {
          paramAnonymousVarArgs = (HttpURLConnection)new URL(paramAnonymousVarArgs[0]).openConnection();
          paramAnonymousVarArgs.setConnectTimeout(20000);
          paramAnonymousVarArgs.setReadTimeout(20000);
          paramAnonymousVarArgs.setInstanceFollowRedirects(true);
          int i = paramAnonymousVarArgs.getResponseCode();
          int j = paramAnonymousVarArgs.getContentLength();
          if (i / 100 == 3)
          {
            localObject = paramAnonymousVarArgs.getHeaderField("Location");
            paramAnonymousVarArgs.disconnect();
            return a(new String[] { localObject });
          }
          Object localObject = new ByteArrayOutputStream();
          paramAnonymousVarArgs = paramAnonymousVarArgs.getInputStream();
          for (i = paramAnonymousVarArgs.read(); i != -1; i = paramAnonymousVarArgs.read()) {
            ((ByteArrayOutputStream)localObject).write(i);
          }
          paramAnonymousVarArgs.close();
          paramAnonymousVarArgs = ((ByteArrayOutputStream)localObject).toByteArray();
          if (j != paramAnonymousVarArgs.length) {
            throw new SocketTimeoutException("Content-Length is not equal to final decoded bitmap bytes");
          }
        }
        catch (Exception paramAnonymousVarArgs)
        {
          if (paramCallback != null) {
            paramCallback.onError(0, "Can't download this bitmap");
          }
          Log.error("An error occured while downloading this image", paramAnonymousVarArgs);
          for (;;)
          {
            return null;
            paramAnonymousVarArgs = BitmapFactory.decodeByteArray(paramAnonymousVarArgs, 0, paramAnonymousVarArgs.length);
            if (!this.a) {
              return paramAnonymousVarArgs;
            }
            if (paramCallback != null)
            {
              if (paramAnonymousVarArgs == null) {
                break;
              }
              paramCallback.onResult(paramAnonymousVarArgs);
            }
          }
        }
        catch (Error paramAnonymousVarArgs)
        {
          for (;;)
          {
            if (paramCallback != null) {
              paramCallback.onError(0, "Error while decoding this image into a bitmap");
            }
            Log.error("An error occured while converting this image to a bitmap", paramAnonymousVarArgs);
            continue;
            paramCallback.onError(0, "Can't download this bitmap");
          }
        }
        return paramAnonymousVarArgs;
      }
      
      protected void a(Bitmap paramAnonymousBitmap)
      {
        if ((!this.a) && (paramCallback != null))
        {
          if (paramAnonymousBitmap != null) {
            paramCallback.onResult(paramAnonymousBitmap);
          }
        }
        else {
          return;
        }
        paramCallback.onError(0, "Can't download this bitmap");
      }
    }.execute(new String[] { paramString });
  }
  
  @SuppressLint({"NewApi"})
  public static boolean a(Context paramContext)
  {
    KeyguardManager localKeyguardManager = (KeyguardManager)paramContext.getSystemService("keyguard");
    paramContext = (PowerManager)paramContext.getSystemService("power");
    boolean bool2 = localKeyguardManager.inKeyguardRestrictedInputMode();
    boolean bool1 = bool2;
    if (Build.VERSION.SDK_INT >= 7)
    {
      bool1 = bool2;
      if (Build.VERSION.SDK_INT < 20)
      {
        bool1 = bool2;
        if (!bool2)
        {
          if (paramContext.isScreenOn()) {
            break label114;
          }
          bool1 = true;
        }
      }
    }
    bool2 = bool1;
    if (Build.VERSION.SDK_INT >= 16)
    {
      bool2 = bool1;
      if (!bool1) {
        bool2 = localKeyguardManager.isKeyguardLocked();
      }
    }
    bool1 = bool2;
    if (Build.VERSION.SDK_INT >= 20)
    {
      bool1 = bool2;
      if (!bool2) {
        if (paramContext.isInteractive()) {
          break label119;
        }
      }
    }
    label114:
    label119:
    for (bool1 = true;; bool1 = false)
    {
      if (bool1) {
        break label124;
      }
      return true;
      bool1 = false;
      break;
    }
    label124:
    return false;
  }
  
  public static boolean a(Context paramContext, String paramString)
  {
    return paramContext.getPackageManager().checkPermission(paramString, paramContext.getPackageName()) == 0;
  }
  
  private static boolean a(Context paramContext, int[] paramArrayOfInt)
  {
    paramContext = (ConnectivityManager)paramContext.getSystemService("connectivity");
    int j = paramArrayOfInt.length;
    int i = 0;
    while (i < j)
    {
      Object localObject = paramContext.getNetworkInfo(paramArrayOfInt[i]);
      if (localObject != null)
      {
        localObject = ((NetworkInfo)localObject).getState();
        if ((localObject == NetworkInfo.State.CONNECTED) || (localObject == NetworkInfo.State.CONNECTING)) {
          return true;
        }
      }
      i += 1;
    }
    return false;
  }
  
  private static double b(double paramDouble)
  {
    return 180.0D * paramDouble / 3.141592653589793D;
  }
  
  public static String b()
  {
    return "AES/CBC/PKCS5Padding";
  }
  
  public static String b(Context paramContext)
  {
    String str = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
    paramContext = str;
    if (str == null) {
      paramContext = "";
    }
    return paramContext.toLowerCase(Locale.US);
  }
  
  public static String c(Context paramContext)
  {
    return paramContext.getPackageManager().getLaunchIntentForPackage(paramContext.getPackageName()).toUri(1);
  }
  
  public static int d(Context paramContext)
  {
    return paramContext.getApplicationInfo().icon;
  }
  
  public static String e(Context paramContext)
  {
    ApplicationInfo localApplicationInfo = paramContext.getApplicationInfo();
    return (String)paramContext.getPackageManager().getApplicationLabel(localApplicationInfo);
  }
  
  public static String f(Context paramContext)
  {
    paramContext = (TelephonyManager)paramContext.getSystemService("phone");
    if ((paramContext.getNetworkOperatorName() != null) && (paramContext.getNetworkOperatorName().length() > 0)) {
      return paramContext.getNetworkOperatorName();
    }
    return null;
  }
  
  public static boolean g(Context paramContext)
  {
    return a(paramContext, new int[] { 0 });
  }
  
  @SuppressLint({"InlinedApi"})
  public static boolean h(Context paramContext)
  {
    return a(paramContext, new int[] { 1, 6 });
  }
  
  public static boolean i(Context paramContext)
  {
    return k.k.a(paramContext);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */