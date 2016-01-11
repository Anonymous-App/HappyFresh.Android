package com.appsee;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

class lb
{
  public static String a(String paramString)
  {
    if (i(paramString)) {}
    int i;
    do
    {
      return null;
      i = paramString.lastIndexOf(AppseeBackgroundUploader.i("\026"));
    } while (i < 1);
    return paramString.substring(0, i);
  }
  
  public static String i(Iterable<String> paramIterable, String paramString)
  {
    if ((paramIterable == null) || (!paramIterable.iterator().hasNext())) {
      return null;
    }
    paramIterable = paramIterable.iterator();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append((String)paramIterable.next());
    while (paramIterable.hasNext()) {
      localStringBuilder.append((String)paramIterable.next());
    }
    return localStringBuilder.toString();
  }
  
  public static String i(String paramString)
  {
    if (i(paramString)) {}
    int i;
    do
    {
      return null;
      i = paramString.lastIndexOf(AppseeBackgroundUploader.i("\026"));
    } while (i < 0);
    return paramString.substring(i + 1).toLowerCase();
  }
  
  public static String i(Date paramDate)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(AppseeBackgroundUploader.i("\020:\024\002g`S5P#\037\022w+\031>\f.K"), Locale.ENGLISH);
    localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone(AppseeBackgroundUploader.i("c\t{")));
    return localSimpleDateFormat.format(paramDate);
  }
  
  public static final String i(byte[] paramArrayOfByte)
    throws Exception
  {
    Object localObject = MessageDigest.getInstance(AppseeBackgroundUploader.i("{\031\r"));
    ((MessageDigest)localObject).update(paramArrayOfByte);
    byte[] arrayOfByte = ((MessageDigest)localObject).digest();
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    for (int j = 0; i < arrayOfByte.length; j = i)
    {
      paramArrayOfByte = Integer.toHexString(arrayOfByte[j] & 0xFF);
      for (localObject = paramArrayOfByte; paramArrayOfByte.length() < 2; localObject = paramArrayOfByte) {
        paramArrayOfByte = (String)localObject;
      }
      i = j + 1;
      localStringBuffer.append((String)localObject);
    }
    return localStringBuffer.toString();
  }
  
  public static boolean i(CharSequence paramCharSequence)
  {
    if (paramCharSequence == null) {
      return true;
    }
    return i(paramCharSequence.toString());
  }
  
  public static boolean i(String paramString)
  {
    return (paramString == null) || (paramString.length() == 0) || (paramString.trim().length() == 0);
  }
  
  public static boolean i(String paramString1, String paramString2)
  {
    if ((paramString1 == null) && (paramString2 == null)) {
      return true;
    }
    if ((paramString1 == null) || (paramString2 == null)) {
      return false;
    }
    return paramString1.trim().toLowerCase().equals(paramString2.trim().toLowerCase());
  }
  
  public static final String l(String paramString)
    throws Exception
  {
    return i(paramString.getBytes());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/lb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */