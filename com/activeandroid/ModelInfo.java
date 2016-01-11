package com.activeandroid;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.activeandroid.serializer.CalendarSerializer;
import com.activeandroid.serializer.FileSerializer;
import com.activeandroid.serializer.SqlDateSerializer;
import com.activeandroid.serializer.TypeSerializer;
import com.activeandroid.serializer.UtilDateSerializer;
import com.activeandroid.util.Log;
import com.activeandroid.util.ReflectionUtils;
import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

final class ModelInfo
{
  private Map<Class<? extends Model>, TableInfo> mTableInfos = new HashMap();
  private Map<Class<?>, TypeSerializer> mTypeSerializers = new HashMap() {};
  
  public ModelInfo(Configuration paramConfiguration)
  {
    if (!loadModelFromMetaData(paramConfiguration)) {}
    try
    {
      scanForModel(paramConfiguration.getContext());
      Log.i("ModelInfo loaded.");
      return;
    }
    catch (IOException paramConfiguration)
    {
      for (;;)
      {
        Log.e("Couldn't open source path.", paramConfiguration);
      }
    }
  }
  
  private boolean loadModelFromMetaData(Configuration paramConfiguration)
  {
    if (!paramConfiguration.isValid()) {
      return false;
    }
    Object localObject = paramConfiguration.getModelClasses();
    if (localObject != null)
    {
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        Class localClass = (Class)((Iterator)localObject).next();
        this.mTableInfos.put(localClass, new TableInfo(localClass));
      }
    }
    paramConfiguration = paramConfiguration.getTypeSerializers();
    if (paramConfiguration != null)
    {
      paramConfiguration = paramConfiguration.iterator();
      while (paramConfiguration.hasNext())
      {
        localObject = (Class)paramConfiguration.next();
        try
        {
          localObject = (TypeSerializer)((Class)localObject).newInstance();
          this.mTypeSerializers.put(((TypeSerializer)localObject).getDeserializedType(), localObject);
        }
        catch (InstantiationException localInstantiationException)
        {
          Log.e("Couldn't instantiate TypeSerializer.", localInstantiationException);
        }
        catch (IllegalAccessException localIllegalAccessException)
        {
          Log.e("IllegalAccessException", localIllegalAccessException);
        }
      }
    }
    return true;
  }
  
  private void scanForModel(Context paramContext)
    throws IOException
  {
    String str1 = paramContext.getPackageName();
    Object localObject2 = paramContext.getApplicationInfo().sourceDir;
    Object localObject1 = new ArrayList();
    if ((localObject2 != null) && (!new File((String)localObject2).isDirectory())) {
      localObject2 = new DexFile((String)localObject2).entries();
    }
    while (((Enumeration)localObject2).hasMoreElements())
    {
      ((List)localObject1).add(((Enumeration)localObject2).nextElement());
      continue;
      localObject2 = Thread.currentThread().getContextClassLoader().getResources("");
      while (((Enumeration)localObject2).hasMoreElements())
      {
        String str2 = ((URL)((Enumeration)localObject2).nextElement()).getFile();
        if ((str2.contains("bin")) || (str2.contains("classes"))) {
          ((List)localObject1).add(str2);
        }
      }
    }
    localObject1 = ((List)localObject1).iterator();
    while (((Iterator)localObject1).hasNext()) {
      scanForModelClasses(new File((String)((Iterator)localObject1).next()), str1, paramContext.getClassLoader());
    }
  }
  
  private void scanForModelClasses(File paramFile, String paramString, ClassLoader paramClassLoader)
  {
    int i = 0;
    if (paramFile.isDirectory())
    {
      paramFile = paramFile.listFiles();
      int j = paramFile.length;
      while (i < j)
      {
        scanForModelClasses(paramFile[i], paramString, paramClassLoader);
        i += 1;
      }
    }
    String str2 = paramFile.getName();
    String str1 = str2;
    if (!paramFile.getPath().equals(str2))
    {
      paramFile = paramFile.getPath();
      if (paramFile.endsWith(".class"))
      {
        paramFile = paramFile.substring(0, paramFile.length() - 6).replace(System.getProperty("file.separator"), ".");
        i = paramFile.lastIndexOf(paramString);
        if (i >= 0) {
          break label117;
        }
      }
    }
    for (;;)
    {
      return;
      label117:
      str1 = paramFile.substring(i);
      try
      {
        paramFile = Class.forName(str1, false, paramClassLoader);
        if (ReflectionUtils.isModel(paramFile))
        {
          this.mTableInfos.put(paramFile, new TableInfo(paramFile));
          return;
        }
      }
      catch (ClassNotFoundException paramFile)
      {
        Log.e("Couldn't create class.", paramFile);
        return;
        if (ReflectionUtils.isTypeSerializer(paramFile))
        {
          paramFile = (TypeSerializer)paramFile.newInstance();
          this.mTypeSerializers.put(paramFile.getDeserializedType(), paramFile);
          return;
        }
      }
      catch (InstantiationException paramFile)
      {
        Log.e("Couldn't instantiate TypeSerializer.", paramFile);
        return;
      }
      catch (IllegalAccessException paramFile)
      {
        Log.e("IllegalAccessException", paramFile);
      }
    }
  }
  
  public TableInfo getTableInfo(Class<? extends Model> paramClass)
  {
    return (TableInfo)this.mTableInfos.get(paramClass);
  }
  
  public Collection<TableInfo> getTableInfos()
  {
    return this.mTableInfos.values();
  }
  
  public TypeSerializer getTypeSerializer(Class<?> paramClass)
  {
    return (TypeSerializer)this.mTypeSerializers.get(paramClass);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/ModelInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */