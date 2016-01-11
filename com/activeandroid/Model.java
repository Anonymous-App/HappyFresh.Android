package com.activeandroid;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.activeandroid.content.ContentProvider;
import com.activeandroid.query.Delete;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.activeandroid.serializer.TypeSerializer;
import com.activeandroid.util.Log;
import com.activeandroid.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class Model
{
  private static final int HASH_PRIME = 739;
  private final String idName = this.mTableInfo.getIdName();
  private Long mId = null;
  private final TableInfo mTableInfo = Cache.getTableInfo(getClass());
  
  public static void delete(Class<? extends Model> paramClass, long paramLong)
  {
    TableInfo localTableInfo = Cache.getTableInfo(paramClass);
    new Delete().from(paramClass).where(localTableInfo.getIdName() + "=?", new Object[] { Long.valueOf(paramLong) }).execute();
  }
  
  public static <T extends Model> T load(Class<T> paramClass, long paramLong)
  {
    TableInfo localTableInfo = Cache.getTableInfo(paramClass);
    return new Select().from(paramClass).where(localTableInfo.getIdName() + "=?", new Object[] { Long.valueOf(paramLong) }).executeSingle();
  }
  
  public final void delete()
  {
    Cache.openDatabase().delete(this.mTableInfo.getTableName(), this.idName + "=?", new String[] { getId().toString() });
    Cache.removeEntity(this);
    Cache.getContext().getContentResolver().notifyChange(ContentProvider.createUri(this.mTableInfo.getType(), this.mId), null);
  }
  
  public boolean equals(Object paramObject)
  {
    if (((paramObject instanceof Model)) && (this.mId != null))
    {
      paramObject = (Model)paramObject;
      if ((!this.mId.equals(((Model)paramObject).mId)) || (!this.mTableInfo.getTableName().equals(((Model)paramObject).mTableInfo.getTableName()))) {}
    }
    while (this == paramObject)
    {
      return true;
      return false;
    }
    return false;
  }
  
  public final Long getId()
  {
    return this.mId;
  }
  
  protected final <T extends Model> List<T> getMany(Class<T> paramClass, String paramString)
  {
    return new Select().from(paramClass).where(Cache.getTableName(paramClass) + "." + paramString + "=?", new Object[] { getId() }).execute();
  }
  
  public int hashCode()
  {
    if (this.mId == null) {}
    for (int i = super.hashCode();; i = this.mId.hashCode()) {
      return 739 + i * 739 + this.mTableInfo.getTableName().hashCode() * 739;
    }
  }
  
  public final void loadFromCursor(Cursor paramCursor)
  {
    ArrayList localArrayList = new ArrayList(Arrays.asList(paramCursor.getColumnNames()));
    Iterator localIterator = this.mTableInfo.getFields().iterator();
    Field localField;
    String str;
    Object localObject3;
    int i;
    while (localIterator.hasNext())
    {
      localField = (Field)localIterator.next();
      str = this.mTableInfo.getColumnName(localField);
      localObject3 = localField.getType();
      i = localArrayList.indexOf(str);
      if (i >= 0) {
        localField.setAccessible(true);
      }
    }
    for (;;)
    {
      boolean bool2;
      Object localObject2;
      boolean bool1;
      try
      {
        bool2 = paramCursor.isNull(i);
        TypeSerializer localTypeSerializer = Cache.getParserForType((Class)localObject3);
        str = null;
        if (localTypeSerializer == null) {
          break label766;
        }
        localObject3 = localTypeSerializer.getSerializedType();
        break label766;
        localObject3 = str;
        if (localTypeSerializer != null)
        {
          localObject3 = str;
          if (!bool2) {
            localObject3 = localTypeSerializer.deserialize(str);
          }
        }
        if (localObject3 == null) {
          break;
        }
        ((Field)localObject2).set(this, localObject3);
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        Log.e(localIllegalArgumentException.getClass().getName(), localIllegalArgumentException);
        break;
        if ((localObject3.equals(Byte.class)) || (localObject3.equals(Byte.TYPE)))
        {
          localObject1 = Integer.valueOf(paramCursor.getInt(i));
          localObject2 = localField;
          continue;
        }
        if ((localObject3.equals(Short.class)) || (localObject3.equals(Short.TYPE)))
        {
          localObject1 = Integer.valueOf(paramCursor.getInt(i));
          localObject2 = localField;
          continue;
        }
        if ((localObject3.equals(Integer.class)) || (localObject3.equals(Integer.TYPE)))
        {
          localObject1 = Integer.valueOf(paramCursor.getInt(i));
          localObject2 = localField;
          continue;
        }
        if ((localObject3.equals(Long.class)) || (localObject3.equals(Long.TYPE)))
        {
          localObject1 = Long.valueOf(paramCursor.getLong(i));
          localObject2 = localField;
          continue;
        }
        if ((localObject3.equals(Float.class)) || (localObject3.equals(Float.TYPE)))
        {
          localObject1 = Float.valueOf(paramCursor.getFloat(i));
          localObject2 = localField;
          continue;
        }
        if ((localObject3.equals(Double.class)) || (localObject3.equals(Double.TYPE)))
        {
          localObject1 = Double.valueOf(paramCursor.getDouble(i));
          localObject2 = localField;
          continue;
        }
        if ((localObject3.equals(Boolean.class)) || (localObject3.equals(Boolean.TYPE)))
        {
          if (paramCursor.getInt(i) == 0) {
            break label777;
          }
          bool1 = true;
          localObject1 = Boolean.valueOf(bool1);
          localObject2 = localField;
          continue;
        }
        if ((localObject3.equals(Character.class)) || (localObject3.equals(Character.TYPE)))
        {
          localObject1 = Character.valueOf(paramCursor.getString(i).charAt(0));
          localObject2 = localField;
          continue;
        }
        if (localObject3.equals(String.class))
        {
          localObject1 = paramCursor.getString(i);
          localObject2 = localField;
          continue;
        }
        if ((localObject3.equals(Byte[].class)) || (localObject3.equals(byte[].class)))
        {
          localObject1 = paramCursor.getBlob(i);
          localObject2 = localField;
          continue;
        }
        if (ReflectionUtils.isModel((Class)localObject3))
        {
          long l = paramCursor.getLong(i);
          localObject2 = Cache.getEntity((Class)localObject3, l);
          localObject1 = localObject2;
          if (localObject2 != null) {
            break label782;
          }
          localObject1 = new Select().from((Class)localObject3).where(this.idName + "=?", new Object[] { Long.valueOf(l) }).executeSingle();
          break label782;
        }
        localObject2 = localField;
        if (!ReflectionUtils.isSubclassOf((Class)localObject3, Enum.class)) {
          continue;
        }
        Object localObject1 = Enum.valueOf((Class)localObject3, paramCursor.getString(i));
        localObject2 = localField;
        continue;
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        Log.e(localIllegalAccessException.getClass().getName(), localIllegalAccessException);
      }
      catch (SecurityException localSecurityException)
      {
        Log.e(localSecurityException.getClass().getName(), localSecurityException);
      }
      break;
      if (this.mId != null) {
        Cache.addEntity(this);
      }
      return;
      label766:
      if (bool2)
      {
        localObject2 = null;
        continue;
        label777:
        bool1 = false;
        continue;
        label782:
        localObject2 = localField;
      }
    }
  }
  
  public final Long save()
  {
    SQLiteDatabase localSQLiteDatabase = Cache.openDatabase();
    ContentValues localContentValues = new ContentValues();
    Iterator localIterator = this.mTableInfo.getFields().iterator();
    while (localIterator.hasNext())
    {
      Object localObject1 = (Field)localIterator.next();
      String str = this.mTableInfo.getColumnName((Field)localObject1);
      Class localClass = ((Field)localObject1).getType();
      ((Field)localObject1).setAccessible(true);
      Object localObject2;
      try
      {
        Object localObject3 = ((Field)localObject1).get(this);
        localObject1 = localClass;
        localObject2 = localObject3;
        if (localObject3 != null)
        {
          TypeSerializer localTypeSerializer = Cache.getParserForType(localClass);
          localObject1 = localClass;
          localObject2 = localObject3;
          if (localTypeSerializer != null)
          {
            localObject3 = localTypeSerializer.serialize(localObject3);
            localObject1 = localClass;
            localObject2 = localObject3;
            if (localObject3 != null)
            {
              localClass = localObject3.getClass();
              localObject1 = localClass;
              localObject2 = localObject3;
              if (!localClass.equals(localTypeSerializer.getSerializedType()))
              {
                Log.w(String.format("TypeSerializer returned wrong type: expected a %s but got a %s", new Object[] { localTypeSerializer.getSerializedType(), localClass }));
                localObject2 = localObject3;
                localObject1 = localClass;
              }
            }
          }
        }
        if (localObject2 == null) {
          localContentValues.putNull(str);
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        Log.e(localIllegalArgumentException.getClass().getName(), localIllegalArgumentException);
        continue;
        if ((!localIllegalArgumentException.equals(Byte.class)) && (!localIllegalArgumentException.equals(Byte.TYPE))) {
          break label254;
        }
        localContentValues.put(str, (Byte)localObject2);
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        Log.e(localIllegalAccessException.getClass().getName(), localIllegalAccessException);
      }
      continue;
      label254:
      if ((localIllegalAccessException.equals(Short.class)) || (localIllegalAccessException.equals(Short.TYPE))) {
        localContentValues.put(str, (Short)localObject2);
      } else if ((localIllegalAccessException.equals(Integer.class)) || (localIllegalAccessException.equals(Integer.TYPE))) {
        localContentValues.put(str, (Integer)localObject2);
      } else if ((localIllegalAccessException.equals(Long.class)) || (localIllegalAccessException.equals(Long.TYPE))) {
        localContentValues.put(str, (Long)localObject2);
      } else if ((localIllegalAccessException.equals(Float.class)) || (localIllegalAccessException.equals(Float.TYPE))) {
        localContentValues.put(str, (Float)localObject2);
      } else if ((localIllegalAccessException.equals(Double.class)) || (localIllegalAccessException.equals(Double.TYPE))) {
        localContentValues.put(str, (Double)localObject2);
      } else if ((localIllegalAccessException.equals(Boolean.class)) || (localIllegalAccessException.equals(Boolean.TYPE))) {
        localContentValues.put(str, (Boolean)localObject2);
      } else if ((localIllegalAccessException.equals(Character.class)) || (localIllegalAccessException.equals(Character.TYPE))) {
        localContentValues.put(str, localObject2.toString());
      } else if (localIllegalAccessException.equals(String.class)) {
        localContentValues.put(str, localObject2.toString());
      } else if ((localIllegalAccessException.equals(Byte[].class)) || (localIllegalAccessException.equals(byte[].class))) {
        localContentValues.put(str, (byte[])localObject2);
      } else if (ReflectionUtils.isModel(localIllegalAccessException)) {
        localContentValues.put(str, ((Model)localObject2).getId());
      } else if (ReflectionUtils.isSubclassOf(localIllegalAccessException, Enum.class)) {
        localContentValues.put(str, ((Enum)localObject2).name());
      }
    }
    if (this.mId == null) {
      this.mId = Long.valueOf(localSQLiteDatabase.insert(this.mTableInfo.getTableName(), null, localContentValues));
    }
    for (;;)
    {
      Cache.getContext().getContentResolver().notifyChange(ContentProvider.createUri(this.mTableInfo.getType(), this.mId), null);
      return this.mId;
      localSQLiteDatabase.update(this.mTableInfo.getTableName(), localContentValues, this.idName + "=" + this.mId, null);
    }
  }
  
  public String toString()
  {
    return this.mTableInfo.getTableName() + "@" + getId();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/Model.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */