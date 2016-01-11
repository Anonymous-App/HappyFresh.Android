package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.view.View;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.internal.zzpt;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class zze
{
  private final Account zzMY;
  private final String zzOd;
  private final String zzOe;
  private final Set<Scope> zzWv;
  private final int zzWw;
  private final View zzWx;
  private final Set<Scope> zzZS;
  private final Map<Api<?>, zza> zzZT;
  private final zzpt zzZU;
  private Integer zzZV;
  
  public zze(Account paramAccount, Collection<Scope> paramCollection, Map<Api<?>, zza> paramMap, int paramInt, View paramView, String paramString1, String paramString2, zzpt paramzzpt)
  {
    this.zzMY = paramAccount;
    if (paramCollection == null) {}
    for (paramAccount = Collections.EMPTY_SET;; paramAccount = Collections.unmodifiableSet(new HashSet(paramCollection)))
    {
      this.zzWv = paramAccount;
      paramAccount = paramMap;
      if (paramMap == null) {
        paramAccount = Collections.EMPTY_MAP;
      }
      this.zzZT = paramAccount;
      this.zzWx = paramView;
      this.zzWw = paramInt;
      this.zzOe = paramString1;
      this.zzOd = paramString2;
      this.zzZU = paramzzpt;
      paramAccount = new HashSet(this.zzWv);
      paramCollection = this.zzZT.values().iterator();
      while (paramCollection.hasNext()) {
        paramAccount.addAll(((zza)paramCollection.next()).zzWJ);
      }
    }
    this.zzZS = Collections.unmodifiableSet(paramAccount);
  }
  
  public Account getAccount()
  {
    return this.zzMY;
  }
  
  @Deprecated
  public String getAccountName()
  {
    if (this.zzMY != null) {
      return this.zzMY.name;
    }
    return null;
  }
  
  public void zza(Integer paramInteger)
  {
    this.zzZV = paramInteger;
  }
  
  public Set<Scope> zzb(Api<?> paramApi)
  {
    paramApi = (zza)this.zzZT.get(paramApi);
    if ((paramApi == null) || (paramApi.zzWJ.isEmpty())) {
      return this.zzWv;
    }
    HashSet localHashSet = new HashSet(this.zzWv);
    localHashSet.addAll(paramApi.zzWJ);
    return localHashSet;
  }
  
  public View zznA()
  {
    return this.zzWx;
  }
  
  public zzpt zznB()
  {
    return this.zzZU;
  }
  
  public Integer zznC()
  {
    return this.zzZV;
  }
  
  @Deprecated
  public String zzns()
  {
    return zznt().name;
  }
  
  public Account zznt()
  {
    if (this.zzMY != null) {
      return this.zzMY;
    }
    return new Account("<<default account>>", "com.google");
  }
  
  public int zznu()
  {
    return this.zzWw;
  }
  
  public Set<Scope> zznv()
  {
    return this.zzWv;
  }
  
  public Set<Scope> zznw()
  {
    return this.zzZS;
  }
  
  public Map<Api<?>, zza> zznx()
  {
    return this.zzZT;
  }
  
  public String zzny()
  {
    return this.zzOe;
  }
  
  public String zznz()
  {
    return this.zzOd;
  }
  
  public static final class zza
  {
    public final Set<Scope> zzWJ;
    public final boolean zzZW;
    
    public zza(Set<Scope> paramSet, boolean paramBoolean)
    {
      zzu.zzu(paramSet);
      this.zzWJ = Collections.unmodifiableSet(paramSet);
      this.zzZW = paramBoolean;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/internal/zze.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */