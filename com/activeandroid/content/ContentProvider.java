package com.activeandroid.content;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.SparseArray;
import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.Configuration;
import com.activeandroid.Configuration.Builder;
import com.activeandroid.Model;
import com.activeandroid.TableInfo;
import java.util.ArrayList;
import java.util.List;

public class ContentProvider
  extends android.content.ContentProvider
{
  private static final SparseArray<Class<? extends Model>> TYPE_CODES = new SparseArray();
  private static final UriMatcher URI_MATCHER = new UriMatcher(-1);
  private static String sAuthority;
  private static SparseArray<String> sMimeTypeCache = new SparseArray();
  
  public static Uri createUri(Class<? extends Model> paramClass, Long paramLong)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("content://");
    localStringBuilder.append(sAuthority);
    localStringBuilder.append("/");
    localStringBuilder.append(Cache.getTableName(paramClass).toLowerCase());
    if (paramLong != null)
    {
      localStringBuilder.append("/");
      localStringBuilder.append(paramLong.toString());
    }
    return Uri.parse(localStringBuilder.toString());
  }
  
  private Class<? extends Model> getModelType(Uri paramUri)
  {
    int i = URI_MATCHER.match(paramUri);
    if (i != -1) {
      return (Class)TYPE_CODES.get(i);
    }
    return null;
  }
  
  private void notifyChange(Uri paramUri)
  {
    getContext().getContentResolver().notifyChange(paramUri, null);
  }
  
  public int delete(Uri paramUri, String paramString, String[] paramArrayOfString)
  {
    Class localClass = getModelType(paramUri);
    int i = Cache.openDatabase().delete(Cache.getTableName(localClass), paramString, paramArrayOfString);
    notifyChange(paramUri);
    return i;
  }
  
  protected String getAuthority()
  {
    return getContext().getPackageName();
  }
  
  protected Configuration getConfiguration()
  {
    return new Configuration.Builder(getContext()).create();
  }
  
  public String getType(Uri paramUri)
  {
    int j = URI_MATCHER.match(paramUri);
    Object localObject = (String)sMimeTypeCache.get(j);
    if (localObject != null) {
      return (String)localObject;
    }
    localObject = getModelType(paramUri);
    int i;
    StringBuilder localStringBuilder;
    if (j % 2 == 0)
    {
      i = 1;
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("vnd");
      localStringBuilder.append(".");
      localStringBuilder.append(sAuthority);
      localStringBuilder.append(".");
      if (i == 0) {
        break label174;
      }
    }
    label174:
    for (paramUri = "item";; paramUri = "dir")
    {
      localStringBuilder.append(paramUri);
      localStringBuilder.append("/");
      localStringBuilder.append("vnd");
      localStringBuilder.append(".");
      localStringBuilder.append(sAuthority);
      localStringBuilder.append(".");
      localStringBuilder.append(Cache.getTableName((Class)localObject));
      sMimeTypeCache.append(j, localStringBuilder.toString());
      return localStringBuilder.toString();
      i = 0;
      break;
    }
  }
  
  public Uri insert(Uri paramUri, ContentValues paramContentValues)
  {
    Object localObject = null;
    Class localClass = getModelType(paramUri);
    paramContentValues = Long.valueOf(Cache.openDatabase().insert(Cache.getTableName(localClass), null, paramContentValues));
    paramUri = (Uri)localObject;
    if (paramContentValues != null)
    {
      paramUri = (Uri)localObject;
      if (paramContentValues.longValue() > 0L)
      {
        paramUri = createUri(localClass, paramContentValues);
        notifyChange(paramUri);
      }
    }
    return paramUri;
  }
  
  public boolean onCreate()
  {
    ActiveAndroid.initialize(getConfiguration());
    sAuthority = getAuthority();
    ArrayList localArrayList = new ArrayList(Cache.getTableInfos());
    int j = localArrayList.size();
    int i = 0;
    while (i < j)
    {
      TableInfo localTableInfo = (TableInfo)localArrayList.get(i);
      int k = i * 2 + 1;
      int m = i * 2 + 2;
      URI_MATCHER.addURI(sAuthority, localTableInfo.getTableName().toLowerCase(), k);
      TYPE_CODES.put(k, localTableInfo.getType());
      URI_MATCHER.addURI(sAuthority, localTableInfo.getTableName().toLowerCase() + "/#", m);
      TYPE_CODES.put(m, localTableInfo.getType());
      i += 1;
    }
    return true;
  }
  
  public Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2)
  {
    Class localClass = getModelType(paramUri);
    paramArrayOfString1 = Cache.openDatabase().query(Cache.getTableName(localClass), paramArrayOfString1, paramString1, paramArrayOfString2, null, null, paramString2);
    paramArrayOfString1.setNotificationUri(getContext().getContentResolver(), paramUri);
    return paramArrayOfString1;
  }
  
  public int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString)
  {
    Class localClass = getModelType(paramUri);
    int i = Cache.openDatabase().update(Cache.getTableName(localClass), paramContentValues, paramString, paramArrayOfString);
    notifyChange(paramUri);
    return i;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/content/ContentProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */