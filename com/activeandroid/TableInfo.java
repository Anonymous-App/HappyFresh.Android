package com.activeandroid;

import android.text.TextUtils;
import android.util.Log;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class TableInfo
{
  private Map<Field, String> mColumnNames = new LinkedHashMap();
  private String mIdName = "Id";
  private String mTableName;
  private Class<? extends Model> mType;
  
  public TableInfo(Class<? extends Model> paramClass)
  {
    this.mType = paramClass;
    Object localObject = (Table)paramClass.getAnnotation(Table.class);
    if (localObject != null)
    {
      this.mTableName = ((Table)localObject).name();
      this.mIdName = ((Table)localObject).id();
    }
    for (;;)
    {
      localObject = getIdField(paramClass);
      this.mColumnNames.put(localObject, this.mIdName);
      paramClass = new LinkedList(ReflectionUtils.getDeclaredColumnFields(paramClass));
      Collections.reverse(paramClass);
      Iterator localIterator = paramClass.iterator();
      while (localIterator.hasNext())
      {
        Field localField = (Field)localIterator.next();
        if (localField.isAnnotationPresent(Column.class))
        {
          localObject = ((Column)localField.getAnnotation(Column.class)).name();
          paramClass = (Class<? extends Model>)localObject;
          if (TextUtils.isEmpty((CharSequence)localObject)) {
            paramClass = localField.getName();
          }
          this.mColumnNames.put(localField, paramClass);
        }
      }
      this.mTableName = paramClass.getSimpleName();
    }
  }
  
  private Field getIdField(Class<?> paramClass)
  {
    if (paramClass.equals(Model.class)) {
      try
      {
        paramClass = paramClass.getDeclaredField("mId");
        return paramClass;
      }
      catch (NoSuchFieldException paramClass)
      {
        Log.e("Impossible!", paramClass.toString());
      }
    }
    while (paramClass.getSuperclass() == null) {
      return null;
    }
    return getIdField(paramClass.getSuperclass());
  }
  
  public String getColumnName(Field paramField)
  {
    return (String)this.mColumnNames.get(paramField);
  }
  
  public Collection<Field> getFields()
  {
    return this.mColumnNames.keySet();
  }
  
  public String getIdName()
  {
    return this.mIdName;
  }
  
  public String getTableName()
  {
    return this.mTableName;
  }
  
  public Class<? extends Model> getType()
  {
    return this.mType;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/TableInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */