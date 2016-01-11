package com.activeandroid.query;

import android.content.ContentResolver;
import android.content.Context;
import android.text.TextUtils;
import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.content.ContentProvider;
import com.activeandroid.util.Log;
import com.activeandroid.util.SQLiteUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class From
  implements Sqlable
{
  private String mAlias;
  private List<Object> mArguments;
  private String mGroupBy;
  private String mHaving;
  private List<Join> mJoins;
  private String mLimit;
  private String mOffset;
  private String mOrderBy;
  private Sqlable mQueryBase;
  private Class<? extends Model> mType;
  private final StringBuilder mWhere = new StringBuilder();
  
  public From(Class<? extends Model> paramClass, Sqlable paramSqlable)
  {
    this.mType = paramClass;
    this.mJoins = new ArrayList();
    this.mQueryBase = paramSqlable;
    this.mJoins = new ArrayList();
    this.mArguments = new ArrayList();
  }
  
  private void addFrom(StringBuilder paramStringBuilder)
  {
    paramStringBuilder.append("FROM ");
    paramStringBuilder.append(Cache.getTableName(this.mType)).append(" ");
    if (this.mAlias != null)
    {
      paramStringBuilder.append("AS ");
      paramStringBuilder.append(this.mAlias);
      paramStringBuilder.append(" ");
    }
  }
  
  private void addGroupBy(StringBuilder paramStringBuilder)
  {
    if (this.mGroupBy != null)
    {
      paramStringBuilder.append("GROUP BY ");
      paramStringBuilder.append(this.mGroupBy);
      paramStringBuilder.append(" ");
    }
  }
  
  private void addHaving(StringBuilder paramStringBuilder)
  {
    if (this.mHaving != null)
    {
      paramStringBuilder.append("HAVING ");
      paramStringBuilder.append(this.mHaving);
      paramStringBuilder.append(" ");
    }
  }
  
  private void addJoins(StringBuilder paramStringBuilder)
  {
    Iterator localIterator = this.mJoins.iterator();
    while (localIterator.hasNext()) {
      paramStringBuilder.append(((Join)localIterator.next()).toSql());
    }
  }
  
  private void addLimit(StringBuilder paramStringBuilder)
  {
    if (this.mLimit != null)
    {
      paramStringBuilder.append("LIMIT ");
      paramStringBuilder.append(this.mLimit);
      paramStringBuilder.append(" ");
    }
  }
  
  private void addOffset(StringBuilder paramStringBuilder)
  {
    if (this.mOffset != null)
    {
      paramStringBuilder.append("OFFSET ");
      paramStringBuilder.append(this.mOffset);
      paramStringBuilder.append(" ");
    }
  }
  
  private void addOrderBy(StringBuilder paramStringBuilder)
  {
    if (this.mOrderBy != null)
    {
      paramStringBuilder.append("ORDER BY ");
      paramStringBuilder.append(this.mOrderBy);
      paramStringBuilder.append(" ");
    }
  }
  
  private void addWhere(StringBuilder paramStringBuilder)
  {
    if (this.mWhere.length() > 0)
    {
      paramStringBuilder.append("WHERE ");
      paramStringBuilder.append(this.mWhere);
      paramStringBuilder.append(" ");
    }
  }
  
  private String sqlString(StringBuilder paramStringBuilder)
  {
    paramStringBuilder = paramStringBuilder.toString().trim();
    if (Log.isEnabled()) {
      Log.v(paramStringBuilder + " " + TextUtils.join(",", getArguments()));
    }
    return paramStringBuilder;
  }
  
  void addArguments(Object[] paramArrayOfObject)
  {
    int k = paramArrayOfObject.length;
    int i = 0;
    if (i < k)
    {
      Object localObject2 = paramArrayOfObject[i];
      Object localObject1;
      if (localObject2.getClass() != Boolean.TYPE)
      {
        localObject1 = localObject2;
        if (localObject2.getClass() != Boolean.class) {}
      }
      else
      {
        if (!localObject2.equals(Boolean.valueOf(true))) {
          break label81;
        }
      }
      label81:
      for (int j = 1;; j = 0)
      {
        localObject1 = Integer.valueOf(j);
        this.mArguments.add(localObject1);
        i += 1;
        break;
      }
    }
  }
  
  public From and(String paramString)
  {
    return where(paramString);
  }
  
  public From and(String paramString, Object... paramVarArgs)
  {
    return where(paramString, paramVarArgs);
  }
  
  public From as(String paramString)
  {
    this.mAlias = paramString;
    return this;
  }
  
  public int count()
  {
    return SQLiteUtils.intQuery(toCountSql(), getArguments());
  }
  
  public Join crossJoin(Class<? extends Model> paramClass)
  {
    paramClass = new Join(this, paramClass, Join.JoinType.CROSS);
    this.mJoins.add(paramClass);
    return paramClass;
  }
  
  public <T extends Model> List<T> execute()
  {
    if ((this.mQueryBase instanceof Select)) {
      return SQLiteUtils.rawQuery(this.mType, toSql(), getArguments());
    }
    SQLiteUtils.execSql(toSql(), getArguments());
    Cache.getContext().getContentResolver().notifyChange(ContentProvider.createUri(this.mType, null), null);
    return null;
  }
  
  public <T extends Model> T executeSingle()
  {
    if ((this.mQueryBase instanceof Select))
    {
      limit(1);
      return SQLiteUtils.rawQuerySingle(this.mType, toSql(), getArguments());
    }
    limit(1);
    SQLiteUtils.rawQuerySingle(this.mType, toSql(), getArguments()).delete();
    return null;
  }
  
  public boolean exists()
  {
    return SQLiteUtils.intQuery(toExistsSql(), getArguments()) != 0;
  }
  
  public String[] getArguments()
  {
    int j = this.mArguments.size();
    String[] arrayOfString = new String[j];
    int i = 0;
    while (i < j)
    {
      arrayOfString[i] = this.mArguments.get(i).toString();
      i += 1;
    }
    return arrayOfString;
  }
  
  public From groupBy(String paramString)
  {
    this.mGroupBy = paramString;
    return this;
  }
  
  public From having(String paramString)
  {
    this.mHaving = paramString;
    return this;
  }
  
  public Join innerJoin(Class<? extends Model> paramClass)
  {
    paramClass = new Join(this, paramClass, Join.JoinType.INNER);
    this.mJoins.add(paramClass);
    return paramClass;
  }
  
  public Join join(Class<? extends Model> paramClass)
  {
    paramClass = new Join(this, paramClass, null);
    this.mJoins.add(paramClass);
    return paramClass;
  }
  
  public Join leftJoin(Class<? extends Model> paramClass)
  {
    paramClass = new Join(this, paramClass, Join.JoinType.LEFT);
    this.mJoins.add(paramClass);
    return paramClass;
  }
  
  public From limit(int paramInt)
  {
    return limit(String.valueOf(paramInt));
  }
  
  public From limit(String paramString)
  {
    this.mLimit = paramString;
    return this;
  }
  
  public From offset(int paramInt)
  {
    return offset(String.valueOf(paramInt));
  }
  
  public From offset(String paramString)
  {
    this.mOffset = paramString;
    return this;
  }
  
  public From or(String paramString)
  {
    if (this.mWhere.length() > 0) {
      this.mWhere.append(" OR ");
    }
    this.mWhere.append(paramString);
    return this;
  }
  
  public From or(String paramString, Object... paramVarArgs)
  {
    or(paramString).addArguments(paramVarArgs);
    return this;
  }
  
  public From orderBy(String paramString)
  {
    this.mOrderBy = paramString;
    return this;
  }
  
  public Join outerJoin(Class<? extends Model> paramClass)
  {
    paramClass = new Join(this, paramClass, Join.JoinType.OUTER);
    this.mJoins.add(paramClass);
    return paramClass;
  }
  
  public String toCountSql()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("SELECT COUNT(*) ");
    addFrom(localStringBuilder);
    addJoins(localStringBuilder);
    addWhere(localStringBuilder);
    addGroupBy(localStringBuilder);
    addHaving(localStringBuilder);
    addLimit(localStringBuilder);
    addOffset(localStringBuilder);
    return sqlString(localStringBuilder);
  }
  
  public String toExistsSql()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("SELECT EXISTS(SELECT 1 ");
    addFrom(localStringBuilder);
    addJoins(localStringBuilder);
    addWhere(localStringBuilder);
    addGroupBy(localStringBuilder);
    addHaving(localStringBuilder);
    addLimit(localStringBuilder);
    addOffset(localStringBuilder);
    localStringBuilder.append(")");
    return sqlString(localStringBuilder);
  }
  
  public String toSql()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.mQueryBase.toSql());
    addFrom(localStringBuilder);
    addJoins(localStringBuilder);
    addWhere(localStringBuilder);
    addGroupBy(localStringBuilder);
    addHaving(localStringBuilder);
    addOrderBy(localStringBuilder);
    addLimit(localStringBuilder);
    addOffset(localStringBuilder);
    return sqlString(localStringBuilder);
  }
  
  public From where(String paramString)
  {
    if (this.mWhere.length() > 0) {
      this.mWhere.append(" AND ");
    }
    this.mWhere.append(paramString);
    return this;
  }
  
  public From where(String paramString, Object... paramVarArgs)
  {
    where(paramString).addArguments(paramVarArgs);
    return this;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/query/From.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */