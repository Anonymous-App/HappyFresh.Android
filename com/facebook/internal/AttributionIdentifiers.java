package com.facebook.internal;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Looper;
import android.util.Log;
import com.facebook.FacebookException;
import java.lang.reflect.Method;

public class AttributionIdentifiers
{
  private static final String ANDROID_ID_COLUMN_NAME = "androidid";
  private static final String ATTRIBUTION_ID_COLUMN_NAME = "aid";
  private static final String ATTRIBUTION_ID_CONTENT_PROVIDER = "com.facebook.katana.provider.AttributionIdProvider";
  private static final String ATTRIBUTION_ID_CONTENT_PROVIDER_WAKIZASHI = "com.facebook.wakizashi.provider.AttributionIdProvider";
  private static final int CONNECTION_RESULT_SUCCESS = 0;
  private static final long IDENTIFIER_REFRESH_INTERVAL_MILLIS = 3600000L;
  private static final String LIMIT_TRACKING_COLUMN_NAME = "limit_tracking";
  private static final String TAG = AttributionIdentifiers.class.getCanonicalName();
  private static AttributionIdentifiers recentlyFetchedIdentifiers;
  private String androidAdvertiserId;
  private String attributionId;
  private long fetchTime;
  private boolean limitTracking;
  
  private static AttributionIdentifiers getAndroidId(Context paramContext)
  {
    AttributionIdentifiers localAttributionIdentifiers = new AttributionIdentifiers();
    Object localObject;
    Method localMethod;
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              try
              {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                  throw new FacebookException("getAndroidId cannot be called on the main thread.");
                }
              }
              catch (Exception paramContext)
              {
                Utility.logd("android_id", paramContext);
                return localAttributionIdentifiers;
              }
              localObject = Utility.getMethodQuietly("com.google.android.gms.common.GooglePlayServicesUtil", "isGooglePlayServicesAvailable", new Class[] { Context.class });
            } while (localObject == null);
            localObject = Utility.invokeMethodQuietly(null, (Method)localObject, new Object[] { paramContext });
          } while ((!(localObject instanceof Integer)) || (((Integer)localObject).intValue() != 0));
          localObject = Utility.getMethodQuietly("com.google.android.gms.ads.identifier.AdvertisingIdClient", "getAdvertisingIdInfo", new Class[] { Context.class });
        } while (localObject == null);
        paramContext = Utility.invokeMethodQuietly(null, (Method)localObject, new Object[] { paramContext });
      } while (paramContext == null);
      localObject = Utility.getMethodQuietly(paramContext.getClass(), "getId", new Class[0]);
      localMethod = Utility.getMethodQuietly(paramContext.getClass(), "isLimitAdTrackingEnabled", new Class[0]);
    } while ((localObject == null) || (localMethod == null));
    localAttributionIdentifiers.androidAdvertiserId = ((String)Utility.invokeMethodQuietly(paramContext, (Method)localObject, new Object[0]));
    localAttributionIdentifiers.limitTracking = ((Boolean)Utility.invokeMethodQuietly(paramContext, localMethod, new Object[0])).booleanValue();
    return localAttributionIdentifiers;
  }
  
  public static AttributionIdentifiers getAttributionIdentifiers(Context paramContext)
  {
    if ((recentlyFetchedIdentifiers != null) && (System.currentTimeMillis() - recentlyFetchedIdentifiers.fetchTime < 3600000L)) {
      paramContext = recentlyFetchedIdentifiers;
    }
    for (;;)
    {
      return paramContext;
      AttributionIdentifiers localAttributionIdentifiers = getAndroidId(paramContext);
      Object localObject4 = null;
      Object localObject5 = null;
      Object localObject3 = null;
      Object localObject1 = localObject5;
      Object localObject2 = localObject4;
      try
      {
        if (paramContext.getPackageManager().resolveContentProvider("com.facebook.katana.provider.AttributionIdProvider", 0) != null)
        {
          localObject1 = localObject5;
          localObject2 = localObject4;
          localObject3 = Uri.parse("content://com.facebook.katana.provider.AttributionIdProvider");
        }
        for (;;)
        {
          if (localObject3 != null) {
            break label139;
          }
          paramContext = localAttributionIdentifiers;
          if (0 == 0) {
            break;
          }
          throw new NullPointerException();
          localObject1 = localObject5;
          localObject2 = localObject4;
          if (paramContext.getPackageManager().resolveContentProvider("com.facebook.wakizashi.provider.AttributionIdProvider", 0) != null)
          {
            localObject1 = localObject5;
            localObject2 = localObject4;
            localObject3 = Uri.parse("content://com.facebook.wakizashi.provider.AttributionIdProvider");
          }
        }
        label139:
        localObject1 = localObject5;
        localObject2 = localObject4;
        localObject3 = paramContext.getContentResolver().query((Uri)localObject3, new String[] { "aid", "androidid", "limit_tracking" }, null, null, null);
        if (localObject3 != null)
        {
          localObject1 = localObject3;
          localObject2 = localObject3;
          boolean bool = ((Cursor)localObject3).moveToFirst();
          if (bool) {}
        }
        else
        {
          paramContext = localAttributionIdentifiers;
          return localAttributionIdentifiers;
        }
        localObject1 = localObject3;
        localObject2 = localObject3;
        int k = ((Cursor)localObject3).getColumnIndex("aid");
        localObject1 = localObject3;
        localObject2 = localObject3;
        int j = ((Cursor)localObject3).getColumnIndex("androidid");
        localObject1 = localObject3;
        localObject2 = localObject3;
        int i = ((Cursor)localObject3).getColumnIndex("limit_tracking");
        localObject1 = localObject3;
        localObject2 = localObject3;
        localAttributionIdentifiers.attributionId = ((Cursor)localObject3).getString(k);
        if ((j > 0) && (i > 0))
        {
          localObject1 = localObject3;
          localObject2 = localObject3;
          if (localAttributionIdentifiers.getAndroidAdvertiserId() == null)
          {
            localObject1 = localObject3;
            localObject2 = localObject3;
            localAttributionIdentifiers.androidAdvertiserId = ((Cursor)localObject3).getString(j);
            localObject1 = localObject3;
            localObject2 = localObject3;
            localAttributionIdentifiers.limitTracking = Boolean.parseBoolean(((Cursor)localObject3).getString(i));
          }
        }
        if (localObject3 != null) {
          ((Cursor)localObject3).close();
        }
        localAttributionIdentifiers.fetchTime = System.currentTimeMillis();
        recentlyFetchedIdentifiers = localAttributionIdentifiers;
        return localAttributionIdentifiers;
      }
      catch (Exception paramContext)
      {
        localObject2 = localObject1;
        Log.d(TAG, "Caught unexpected exception in getAttributionId(): " + paramContext.toString());
        paramContext = null;
        return null;
      }
      finally
      {
        if (localObject2 != null) {
          ((Cursor)localObject2).close();
        }
      }
    }
  }
  
  public String getAndroidAdvertiserId()
  {
    return this.androidAdvertiserId;
  }
  
  public String getAttributionId()
  {
    return this.attributionId;
  }
  
  public boolean isTrackingLimited()
  {
    return this.limitTracking;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/internal/AttributionIdentifiers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */