package com.mixpanel.android.mpmetrics;

import android.R.drawable;
import android.R.id;
import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class ResourceReader
  implements ResourceIds
{
  private static final String LOGTAG = "MixpanelAPI.RsrcReader";
  private final Context mContext;
  private final Map<String, Integer> mIdNameToId;
  private final SparseArray<String> mIdToIdName;
  
  protected ResourceReader(Context paramContext)
  {
    this.mContext = paramContext;
    this.mIdNameToId = new HashMap();
    this.mIdToIdName = new SparseArray();
  }
  
  private static void readClassIds(Class<?> paramClass, String paramString, Map<String, Integer> paramMap)
  {
    for (;;)
    {
      int i;
      try
      {
        Field[] arrayOfField = paramClass.getFields();
        i = 0;
        if (i < arrayOfField.length)
        {
          Field localField = arrayOfField[i];
          if ((Modifier.isStatic(localField.getModifiers())) && (localField.getType() == Integer.TYPE))
          {
            String str = localField.getName();
            int j = localField.getInt(null);
            if (paramString == null)
            {
              paramMap.put(str, Integer.valueOf(j));
            }
            else
            {
              str = paramString + ":" + str;
              continue;
            }
          }
        }
        else
        {
          return;
        }
      }
      catch (IllegalAccessException paramString)
      {
        Log.e("MixpanelAPI.RsrcReader", "Can't read built-in id names from " + paramClass.getName(), paramString);
      }
      i += 1;
    }
  }
  
  protected abstract String getLocalClassName(Context paramContext);
  
  protected abstract Class<?> getSystemClass();
  
  public int idFromName(String paramString)
  {
    return ((Integer)this.mIdNameToId.get(paramString)).intValue();
  }
  
  protected void initialize()
  {
    this.mIdNameToId.clear();
    this.mIdToIdName.clear();
    readClassIds(getSystemClass(), "android", this.mIdNameToId);
    Object localObject = getLocalClassName(this.mContext);
    try
    {
      readClassIds(Class.forName((String)localObject), null, this.mIdNameToId);
      localObject = this.mIdNameToId.entrySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
        this.mIdToIdName.put(((Integer)localEntry.getValue()).intValue(), localEntry.getKey());
      }
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      for (;;)
      {
        Log.w("MixpanelAPI.RsrcReader", "Can't load names for Android view ids from '" + (String)localObject + "', ids by name will not be available in the events editor.");
        Log.i("MixpanelAPI.RsrcReader", "You may be missing a Resources class for your package due to your proguard configuration, or you may be using an applicationId in your build that isn't the same as the package declared in your AndroidManifest.xml file.\nIf you're using proguard, you can fix this issue by adding the following to your proguard configuration:\n\n-keep class **.R$* {\n    <fields>;\n}\n\nIf you're not using proguard, or if your proguard configuration already contains the directive above, you can add the following to your AndroidManifest.xml file to explicitly point the Mixpanel library to the appropriate library for your resources class:\n\n<meta-data android:name=\"com.mixpanel.android.MPConfig.ResourcePackageName\" android:value=\"YOUR_PACKAGE_NAME\" />\n\nwhere YOUR_PACKAGE_NAME is the same string you use for the \"package\" attribute in your <manifest> tag.");
      }
    }
  }
  
  public boolean knownIdName(String paramString)
  {
    return this.mIdNameToId.containsKey(paramString);
  }
  
  public String nameForId(int paramInt)
  {
    return (String)this.mIdToIdName.get(paramInt);
  }
  
  public static class Drawables
    extends ResourceReader
  {
    private final String mResourcePackageName;
    
    protected Drawables(String paramString, Context paramContext)
    {
      super();
      this.mResourcePackageName = paramString;
      initialize();
    }
    
    protected String getLocalClassName(Context paramContext)
    {
      return this.mResourcePackageName + ".R$drawable";
    }
    
    protected Class<?> getSystemClass()
    {
      return R.drawable.class;
    }
  }
  
  public static class Ids
    extends ResourceReader
  {
    private final String mResourcePackageName;
    
    public Ids(String paramString, Context paramContext)
    {
      super();
      this.mResourcePackageName = paramString;
      initialize();
    }
    
    protected String getLocalClassName(Context paramContext)
    {
      return this.mResourcePackageName + ".R$id";
    }
    
    protected Class<?> getSystemClass()
    {
      return R.id.class;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/mpmetrics/ResourceReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */