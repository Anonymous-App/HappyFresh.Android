package com.activeandroid;

import android.content.Context;
import com.activeandroid.serializer.TypeSerializer;
import com.activeandroid.util.Log;
import com.activeandroid.util.ReflectionUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Configuration
{
  public static final String SQL_PARSER_DELIMITED = "delimited";
  public static final String SQL_PARSER_LEGACY = "legacy";
  private int mCacheSize;
  private Context mContext;
  private String mDatabaseName;
  private int mDatabaseVersion;
  private List<Class<? extends Model>> mModelClasses;
  private String mSqlParser;
  private List<Class<? extends TypeSerializer>> mTypeSerializers;
  
  private Configuration(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public int getCacheSize()
  {
    return this.mCacheSize;
  }
  
  public Context getContext()
  {
    return this.mContext;
  }
  
  public String getDatabaseName()
  {
    return this.mDatabaseName;
  }
  
  public int getDatabaseVersion()
  {
    return this.mDatabaseVersion;
  }
  
  public List<Class<? extends Model>> getModelClasses()
  {
    return this.mModelClasses;
  }
  
  public String getSqlParser()
  {
    return this.mSqlParser;
  }
  
  public List<Class<? extends TypeSerializer>> getTypeSerializers()
  {
    return this.mTypeSerializers;
  }
  
  public boolean isValid()
  {
    return (this.mModelClasses != null) && (this.mModelClasses.size() > 0);
  }
  
  public static class Builder
  {
    private static final String AA_DB_NAME = "AA_DB_NAME";
    private static final String AA_DB_VERSION = "AA_DB_VERSION";
    private static final String AA_MODELS = "AA_MODELS";
    private static final String AA_SERIALIZERS = "AA_SERIALIZERS";
    private static final String AA_SQL_PARSER = "AA_SQL_PARSER";
    private static final int DEFAULT_CACHE_SIZE = 1024;
    private static final String DEFAULT_DB_NAME = "Application.db";
    private static final String DEFAULT_SQL_PARSER = "legacy";
    private Integer mCacheSize;
    private Context mContext;
    private String mDatabaseName;
    private Integer mDatabaseVersion;
    private List<Class<? extends Model>> mModelClasses;
    private String mSqlParser;
    private List<Class<? extends TypeSerializer>> mTypeSerializers;
    
    public Builder(Context paramContext)
    {
      this.mContext = paramContext.getApplicationContext();
      this.mCacheSize = Integer.valueOf(1024);
    }
    
    private String getMetaDataDatabaseNameOrDefault()
    {
      String str2 = (String)ReflectionUtils.getMetaData(this.mContext, "AA_DB_NAME");
      String str1 = str2;
      if (str2 == null) {
        str1 = "Application.db";
      }
      return str1;
    }
    
    private int getMetaDataDatabaseVersionOrDefault()
    {
      Integer localInteger2 = (Integer)ReflectionUtils.getMetaData(this.mContext, "AA_DB_VERSION");
      Integer localInteger1;
      if (localInteger2 != null)
      {
        localInteger1 = localInteger2;
        if (localInteger2.intValue() != 0) {}
      }
      else
      {
        localInteger1 = Integer.valueOf(1);
      }
      return localInteger1.intValue();
    }
    
    private String getMetaDataSqlParserOrDefault()
    {
      String str2 = (String)ReflectionUtils.getMetaData(this.mContext, "AA_SQL_PARSER");
      String str1 = str2;
      if (str2 == null) {
        str1 = "legacy";
      }
      return str1;
    }
    
    private List<Class<? extends Model>> loadModelList(String[] paramArrayOfString)
    {
      int i = 0;
      ArrayList localArrayList = new ArrayList();
      ClassLoader localClassLoader = this.mContext.getClass().getClassLoader();
      int j = paramArrayOfString.length;
      for (;;)
      {
        if (i < j)
        {
          Object localObject = paramArrayOfString[i];
          try
          {
            localObject = Class.forName(((String)localObject).trim(), false, localClassLoader);
            if (ReflectionUtils.isModel((Class)localObject)) {
              localArrayList.add(localObject);
            }
            i += 1;
          }
          catch (ClassNotFoundException localClassNotFoundException)
          {
            for (;;)
            {
              Log.e("Couldn't create class.", localClassNotFoundException);
            }
          }
        }
      }
      return localArrayList;
    }
    
    private List<Class<? extends TypeSerializer>> loadSerializerList(String[] paramArrayOfString)
    {
      int i = 0;
      ArrayList localArrayList = new ArrayList();
      ClassLoader localClassLoader = this.mContext.getClass().getClassLoader();
      int j = paramArrayOfString.length;
      for (;;)
      {
        if (i < j)
        {
          Object localObject = paramArrayOfString[i];
          try
          {
            localObject = Class.forName(((String)localObject).trim(), false, localClassLoader);
            if (ReflectionUtils.isTypeSerializer((Class)localObject)) {
              localArrayList.add(localObject);
            }
            i += 1;
          }
          catch (ClassNotFoundException localClassNotFoundException)
          {
            for (;;)
            {
              Log.e("Couldn't create class.", localClassNotFoundException);
            }
          }
        }
      }
      return localArrayList;
    }
    
    public Builder addModelClass(Class<? extends Model> paramClass)
    {
      if (this.mModelClasses == null) {
        this.mModelClasses = new ArrayList();
      }
      this.mModelClasses.add(paramClass);
      return this;
    }
    
    public Builder addModelClasses(Class<? extends Model>... paramVarArgs)
    {
      if (this.mModelClasses == null) {
        this.mModelClasses = new ArrayList();
      }
      this.mModelClasses.addAll(Arrays.asList(paramVarArgs));
      return this;
    }
    
    public Builder addTypeSerializer(Class<? extends TypeSerializer> paramClass)
    {
      if (this.mTypeSerializers == null) {
        this.mTypeSerializers = new ArrayList();
      }
      this.mTypeSerializers.add(paramClass);
      return this;
    }
    
    public Builder addTypeSerializers(Class<? extends TypeSerializer>... paramVarArgs)
    {
      if (this.mTypeSerializers == null) {
        this.mTypeSerializers = new ArrayList();
      }
      this.mTypeSerializers.addAll(Arrays.asList(paramVarArgs));
      return this;
    }
    
    public Configuration create()
    {
      Configuration localConfiguration = new Configuration(this.mContext, null);
      Configuration.access$102(localConfiguration, this.mCacheSize.intValue());
      if (this.mDatabaseName != null)
      {
        Configuration.access$202(localConfiguration, this.mDatabaseName);
        if (this.mDatabaseVersion == null) {
          break label122;
        }
        Configuration.access$302(localConfiguration, this.mDatabaseVersion.intValue());
        label60:
        if (this.mSqlParser == null) {
          break label134;
        }
        Configuration.access$402(localConfiguration, this.mSqlParser);
        label76:
        if (this.mModelClasses == null) {
          break label146;
        }
        Configuration.access$502(localConfiguration, this.mModelClasses);
        label92:
        if (this.mTypeSerializers == null) {
          break label181;
        }
        Configuration.access$602(localConfiguration, this.mTypeSerializers);
      }
      label122:
      label134:
      label146:
      String str;
      label181:
      do
      {
        return localConfiguration;
        Configuration.access$202(localConfiguration, getMetaDataDatabaseNameOrDefault());
        break;
        Configuration.access$302(localConfiguration, getMetaDataDatabaseVersionOrDefault());
        break label60;
        Configuration.access$402(localConfiguration, getMetaDataSqlParserOrDefault());
        break label76;
        str = (String)ReflectionUtils.getMetaData(this.mContext, "AA_MODELS");
        if (str == null) {
          break label92;
        }
        Configuration.access$502(localConfiguration, loadModelList(str.split(",")));
        break label92;
        str = (String)ReflectionUtils.getMetaData(this.mContext, "AA_SERIALIZERS");
      } while (str == null);
      Configuration.access$602(localConfiguration, loadSerializerList(str.split(",")));
      return localConfiguration;
    }
    
    public Builder setCacheSize(int paramInt)
    {
      this.mCacheSize = Integer.valueOf(paramInt);
      return this;
    }
    
    public Builder setDatabaseName(String paramString)
    {
      this.mDatabaseName = paramString;
      return this;
    }
    
    public Builder setDatabaseVersion(int paramInt)
    {
      this.mDatabaseVersion = Integer.valueOf(paramInt);
      return this;
    }
    
    public Builder setModelClasses(Class<? extends Model>... paramVarArgs)
    {
      this.mModelClasses = Arrays.asList(paramVarArgs);
      return this;
    }
    
    public Builder setSqlParser(String paramString)
    {
      this.mSqlParser = paramString;
      return this;
    }
    
    public Builder setTypeSerializers(Class<? extends TypeSerializer>... paramVarArgs)
    {
      this.mTypeSerializers = Arrays.asList(paramVarArgs);
      return this;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/Configuration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */