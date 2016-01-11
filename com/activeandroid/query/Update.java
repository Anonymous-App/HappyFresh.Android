package com.activeandroid.query;

import com.activeandroid.Cache;
import com.activeandroid.Model;

public final class Update
  implements Sqlable
{
  private Class<? extends Model> mType;
  
  public Update(Class<? extends Model> paramClass)
  {
    this.mType = paramClass;
  }
  
  Class<? extends Model> getType()
  {
    return this.mType;
  }
  
  public Set set(String paramString)
  {
    return new Set(this, paramString);
  }
  
  public Set set(String paramString, Object... paramVarArgs)
  {
    return new Set(this, paramString, paramVarArgs);
  }
  
  public String toSql()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("UPDATE ");
    localStringBuilder.append(Cache.getTableName(this.mType));
    localStringBuilder.append(" ");
    return localStringBuilder.toString();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/query/Update.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */