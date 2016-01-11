package com.activeandroid.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.serializer.TypeSerializer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

public final class ReflectionUtils
{
  public static Set<Field> getDeclaredColumnFields(Class<?> paramClass)
  {
    Object localObject1 = Collections.emptySet();
    if ((isSubclassOf(paramClass, Model.class)) || (Model.class.equals(paramClass)))
    {
      LinkedHashSet localLinkedHashSet = new LinkedHashSet();
      localObject1 = paramClass.getDeclaredFields();
      Arrays.sort((Object[])localObject1, new Comparator()
      {
        public int compare(Field paramAnonymousField1, Field paramAnonymousField2)
        {
          return paramAnonymousField2.getName().compareTo(paramAnonymousField1.getName());
        }
      });
      int j = localObject1.length;
      int i = 0;
      while (i < j)
      {
        Object localObject2 = localObject1[i];
        if (((Field)localObject2).isAnnotationPresent(Column.class)) {
          localLinkedHashSet.add(localObject2);
        }
        i += 1;
      }
      paramClass = paramClass.getSuperclass();
      localObject1 = localLinkedHashSet;
      if (paramClass != null)
      {
        localLinkedHashSet.addAll(getDeclaredColumnFields(paramClass));
        localObject1 = localLinkedHashSet;
      }
    }
    return (Set<Field>)localObject1;
  }
  
  public static <T> T getMetaData(Context paramContext, String paramString)
  {
    try
    {
      paramContext = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128);
      if (paramContext.metaData != null)
      {
        paramContext = paramContext.metaData.get(paramString);
        return paramContext;
      }
    }
    catch (Exception paramContext)
    {
      Log.w("Couldn't find meta-data: " + paramString);
    }
    return null;
  }
  
  public static boolean isModel(Class<?> paramClass)
  {
    return (isSubclassOf(paramClass, Model.class)) && (!Modifier.isAbstract(paramClass.getModifiers()));
  }
  
  public static boolean isSubclassOf(Class<?> paramClass1, Class<?> paramClass2)
  {
    if (paramClass1.getSuperclass() != null)
    {
      if (paramClass1.getSuperclass().equals(paramClass2)) {
        return true;
      }
      return isSubclassOf(paramClass1.getSuperclass(), paramClass2);
    }
    return false;
  }
  
  public static boolean isTypeSerializer(Class<?> paramClass)
  {
    return isSubclassOf(paramClass, TypeSerializer.class);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/util/ReflectionUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */