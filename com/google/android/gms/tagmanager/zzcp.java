package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzaf.zzi;
import com.google.android.gms.internal.zzag.zza;
import com.google.android.gms.internal.zzqf;
import com.google.android.gms.internal.zzqf.zza;
import com.google.android.gms.internal.zzqf.zzc;
import com.google.android.gms.internal.zzqf.zze;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class zzcp
{
  private static final zzbw<zzag.zza> zzaNe = new zzbw(zzdf.zzzQ(), true);
  private final DataLayer zzaKz;
  private final zzqf.zzc zzaNf;
  private final zzah zzaNg;
  private final Map<String, zzak> zzaNh;
  private final Map<String, zzak> zzaNi;
  private final Map<String, zzak> zzaNj;
  private final zzl<zzqf.zza, zzbw<zzag.zza>> zzaNk;
  private final zzl<String, zzb> zzaNl;
  private final Set<zzqf.zze> zzaNm;
  private final Map<String, zzc> zzaNn;
  private volatile String zzaNo;
  private int zzaNp;
  
  public zzcp(Context paramContext, zzqf.zzc paramzzc, DataLayer paramDataLayer, zzt.zza paramzza1, zzt.zza paramzza2, zzah paramzzah)
  {
    if (paramzzc == null) {
      throw new NullPointerException("resource cannot be null");
    }
    this.zzaNf = paramzzc;
    this.zzaNm = new HashSet(paramzzc.zzAq());
    this.zzaKz = paramDataLayer;
    this.zzaNg = paramzzah;
    paramzzc = new zzm.zza()
    {
      public int zza(zzqf.zza paramAnonymouszza, zzbw<zzag.zza> paramAnonymouszzbw)
      {
        return ((zzag.zza)paramAnonymouszzbw.getObject()).zzBU();
      }
    };
    this.zzaNk = new zzm().zza(1048576, paramzzc);
    paramzzc = new zzm.zza()
    {
      public int zza(String paramAnonymousString, zzcp.zzb paramAnonymouszzb)
      {
        return paramAnonymousString.length() + paramAnonymouszzb.getSize();
      }
    };
    this.zzaNl = new zzm().zza(1048576, paramzzc);
    this.zzaNh = new HashMap();
    zzb(new zzj(paramContext));
    zzb(new zzt(paramzza2));
    zzb(new zzx(paramDataLayer));
    zzb(new zzdg(paramContext, paramDataLayer));
    zzb(new zzdb(paramContext, paramDataLayer));
    this.zzaNi = new HashMap();
    zzc(new zzr());
    zzc(new zzae());
    zzc(new zzaf());
    zzc(new zzam());
    zzc(new zzan());
    zzc(new zzbc());
    zzc(new zzbd());
    zzc(new zzcf());
    zzc(new zzcy());
    this.zzaNj = new HashMap();
    zza(new zzb(paramContext));
    zza(new zzc(paramContext));
    zza(new zze(paramContext));
    zza(new zzf(paramContext));
    zza(new zzg(paramContext));
    zza(new zzh(paramContext));
    zza(new zzi(paramContext));
    zza(new zzn());
    zza(new zzq(this.zzaNf.getVersion()));
    zza(new zzt(paramzza1));
    zza(new zzv(paramDataLayer));
    zza(new zzaa(paramContext));
    zza(new zzab());
    zza(new zzad());
    zza(new zzai(this));
    zza(new zzao());
    zza(new zzap());
    zza(new zzaw(paramContext));
    zza(new zzay());
    zza(new zzbb());
    zza(new zzbi());
    zza(new zzbk(paramContext));
    zza(new zzbx());
    zza(new zzbz());
    zza(new zzcc());
    zza(new zzce());
    zza(new zzcg(paramContext));
    zza(new zzcq());
    zza(new zzcr());
    zza(new zzda());
    zza(new zzdh());
    this.zzaNn = new HashMap();
    paramDataLayer = this.zzaNm.iterator();
    while (paramDataLayer.hasNext())
    {
      paramzza1 = (zzqf.zze)paramDataLayer.next();
      if (paramzzah.zzyL())
      {
        zza(paramzza1.zzAy(), paramzza1.zzAz(), "add macro");
        zza(paramzza1.zzAD(), paramzza1.zzAA(), "remove macro");
        zza(paramzza1.zzAw(), paramzza1.zzAB(), "add tag");
        zza(paramzza1.zzAx(), paramzza1.zzAC(), "remove tag");
      }
      int i = 0;
      while (i < paramzza1.zzAy().size())
      {
        paramzza2 = (zzqf.zza)paramzza1.zzAy().get(i);
        paramzzc = "Unknown";
        paramContext = paramzzc;
        if (paramzzah.zzyL())
        {
          paramContext = paramzzc;
          if (i < paramzza1.zzAz().size()) {
            paramContext = (String)paramzza1.zzAz().get(i);
          }
        }
        paramzzc = zzh(this.zzaNn, zza(paramzza2));
        paramzzc.zza(paramzza1);
        paramzzc.zza(paramzza1, paramzza2);
        paramzzc.zza(paramzza1, paramContext);
        i += 1;
      }
      i = 0;
      while (i < paramzza1.zzAD().size())
      {
        paramzza2 = (zzqf.zza)paramzza1.zzAD().get(i);
        paramzzc = "Unknown";
        paramContext = paramzzc;
        if (paramzzah.zzyL())
        {
          paramContext = paramzzc;
          if (i < paramzza1.zzAA().size()) {
            paramContext = (String)paramzza1.zzAA().get(i);
          }
        }
        paramzzc = zzh(this.zzaNn, zza(paramzza2));
        paramzzc.zza(paramzza1);
        paramzzc.zzb(paramzza1, paramzza2);
        paramzzc.zzb(paramzza1, paramContext);
        i += 1;
      }
    }
    paramContext = this.zzaNf.zzAr().entrySet().iterator();
    while (paramContext.hasNext())
    {
      paramzzc = (Map.Entry)paramContext.next();
      paramDataLayer = ((List)paramzzc.getValue()).iterator();
      while (paramDataLayer.hasNext())
      {
        paramzza1 = (zzqf.zza)paramDataLayer.next();
        if (!zzdf.zzk((zzag.zza)paramzza1.zzAn().get(com.google.android.gms.internal.zzae.zzfT.toString())).booleanValue()) {
          zzh(this.zzaNn, (String)paramzzc.getKey()).zzb(paramzza1);
        }
      }
    }
  }
  
  private zzbw<zzag.zza> zza(zzag.zza paramzza, Set<String> paramSet, zzdi paramzzdi)
  {
    if (!paramzza.zzjb) {
      return new zzbw(paramzza, true);
    }
    zzbw localzzbw1;
    switch (paramzza.type)
    {
    case 5: 
    case 6: 
    default: 
      zzbg.zzaz("Unknown type: " + paramzza.type);
      return zzaNe;
    case 2: 
      localzza = zzqf.zzo(paramzza);
      localzza.zziS = new zzag.zza[paramzza.zziS.length];
      i = 0;
      while (i < paramzza.zziS.length)
      {
        localzzbw1 = zza(paramzza.zziS[i], paramSet, paramzzdi.zziU(i));
        if (localzzbw1 == zzaNe) {
          return zzaNe;
        }
        localzza.zziS[i] = ((zzag.zza)localzzbw1.getObject());
        i += 1;
      }
      return new zzbw(localzza, false);
    case 3: 
      localzza = zzqf.zzo(paramzza);
      if (paramzza.zziT.length != paramzza.zziU.length)
      {
        zzbg.zzaz("Invalid serving value: " + paramzza.toString());
        return zzaNe;
      }
      localzza.zziT = new zzag.zza[paramzza.zziT.length];
      localzza.zziU = new zzag.zza[paramzza.zziT.length];
      i = 0;
      while (i < paramzza.zziT.length)
      {
        localzzbw1 = zza(paramzza.zziT[i], paramSet, paramzzdi.zziV(i));
        zzbw localzzbw2 = zza(paramzza.zziU[i], paramSet, paramzzdi.zziW(i));
        if ((localzzbw1 == zzaNe) || (localzzbw2 == zzaNe)) {
          return zzaNe;
        }
        localzza.zziT[i] = ((zzag.zza)localzzbw1.getObject());
        localzza.zziU[i] = ((zzag.zza)localzzbw2.getObject());
        i += 1;
      }
      return new zzbw(localzza, false);
    case 4: 
      if (paramSet.contains(paramzza.zziV))
      {
        zzbg.zzaz("Macro cycle detected.  Current macro reference: " + paramzza.zziV + "." + "  Previous macro references: " + paramSet.toString() + ".");
        return zzaNe;
      }
      paramSet.add(paramzza.zziV);
      paramzzdi = zzdj.zza(zza(paramzza.zziV, paramSet, paramzzdi.zzyZ()), paramzza.zzja);
      paramSet.remove(paramzza.zziV);
      return paramzzdi;
    }
    zzag.zza localzza = zzqf.zzo(paramzza);
    localzza.zziZ = new zzag.zza[paramzza.zziZ.length];
    int i = 0;
    while (i < paramzza.zziZ.length)
    {
      localzzbw1 = zza(paramzza.zziZ[i], paramSet, paramzzdi.zziX(i));
      if (localzzbw1 == zzaNe) {
        return zzaNe;
      }
      localzza.zziZ[i] = ((zzag.zza)localzzbw1.getObject());
      i += 1;
    }
    return new zzbw(localzza, false);
  }
  
  private zzbw<zzag.zza> zza(String paramString, Set<String> paramSet, zzbj paramzzbj)
  {
    this.zzaNp += 1;
    Object localObject = (zzb)this.zzaNl.get(paramString);
    if ((localObject != null) && (!this.zzaNg.zzyL()))
    {
      zza(((zzb)localObject).zzzs(), paramSet);
      this.zzaNp -= 1;
      return ((zzb)localObject).zzzr();
    }
    localObject = (zzc)this.zzaNn.get(paramString);
    if (localObject == null)
    {
      zzbg.zzaz(zzzq() + "Invalid macro: " + paramString);
      this.zzaNp -= 1;
      return zzaNe;
    }
    zzbw localzzbw = zza(paramString, ((zzc)localObject).zzzt(), ((zzc)localObject).zzzu(), ((zzc)localObject).zzzv(), ((zzc)localObject).zzzx(), ((zzc)localObject).zzzw(), paramSet, paramzzbj.zzyB());
    if (((Set)localzzbw.getObject()).isEmpty()) {}
    for (localObject = ((zzc)localObject).zzzy(); localObject == null; localObject = (zzqf.zza)((Set)localzzbw.getObject()).iterator().next())
    {
      this.zzaNp -= 1;
      return zzaNe;
      if (((Set)localzzbw.getObject()).size() > 1) {
        zzbg.zzaC(zzzq() + "Multiple macros active for macroName " + paramString);
      }
    }
    paramzzbj = zza(this.zzaNj, (zzqf.zza)localObject, paramSet, paramzzbj.zzyR());
    boolean bool;
    if ((localzzbw.zzza()) && (paramzzbj.zzza()))
    {
      bool = true;
      if (paramzzbj != zzaNe) {
        break label392;
      }
    }
    label392:
    for (paramzzbj = zzaNe;; paramzzbj = new zzbw(paramzzbj.getObject(), bool))
    {
      localObject = ((zzqf.zza)localObject).zzzs();
      if (paramzzbj.zzza()) {
        this.zzaNl.zzf(paramString, new zzb(paramzzbj, (zzag.zza)localObject));
      }
      zza((zzag.zza)localObject, paramSet);
      this.zzaNp -= 1;
      return paramzzbj;
      bool = false;
      break;
    }
  }
  
  private zzbw<zzag.zza> zza(Map<String, zzak> paramMap, zzqf.zza paramzza, Set<String> paramSet, zzch paramzzch)
  {
    boolean bool = true;
    Object localObject1 = (zzag.zza)paramzza.zzAn().get(com.google.android.gms.internal.zzae.zzfg.toString());
    if (localObject1 == null)
    {
      zzbg.zzaz("No function id in properties");
      paramMap = zzaNe;
    }
    zzak localzzak;
    do
    {
      return paramMap;
      localObject1 = ((zzag.zza)localObject1).zziW;
      localzzak = (zzak)paramMap.get(localObject1);
      if (localzzak == null)
      {
        zzbg.zzaz((String)localObject1 + " has no backing implementation.");
        return zzaNe;
      }
      paramMap = (zzbw)this.zzaNk.get(paramzza);
    } while ((paramMap != null) && (!this.zzaNg.zzyL()));
    paramMap = new HashMap();
    Iterator localIterator = paramzza.zzAn().entrySet().iterator();
    int i = 1;
    if (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      Object localObject2 = paramzzch.zzez((String)localEntry.getKey());
      localObject2 = zza((zzag.zza)localEntry.getValue(), paramSet, ((zzcj)localObject2).zze((zzag.zza)localEntry.getValue()));
      if (localObject2 == zzaNe) {
        return zzaNe;
      }
      if (((zzbw)localObject2).zzza()) {
        paramzza.zza((String)localEntry.getKey(), (zzag.zza)((zzbw)localObject2).getObject());
      }
      for (;;)
      {
        paramMap.put(localEntry.getKey(), ((zzbw)localObject2).getObject());
        break;
        i = 0;
      }
    }
    if (!localzzak.zzg(paramMap.keySet()))
    {
      zzbg.zzaz("Incorrect keys for function " + (String)localObject1 + " required " + localzzak.zzyN() + " had " + paramMap.keySet());
      return zzaNe;
    }
    if ((i != 0) && (localzzak.zzyh())) {}
    for (;;)
    {
      paramMap = new zzbw(localzzak.zzE(paramMap), bool);
      if (bool) {
        this.zzaNk.zzf(paramzza, paramMap);
      }
      paramzzch.zzd((zzag.zza)paramMap.getObject());
      return paramMap;
      bool = false;
    }
  }
  
  private zzbw<Set<zzqf.zza>> zza(Set<zzqf.zze> paramSet, Set<String> paramSet1, zza paramzza, zzco paramzzco)
  {
    HashSet localHashSet1 = new HashSet();
    HashSet localHashSet2 = new HashSet();
    paramSet = paramSet.iterator();
    boolean bool = true;
    if (paramSet.hasNext())
    {
      zzqf.zze localzze = (zzqf.zze)paramSet.next();
      zzck localzzck = paramzzco.zzyY();
      zzbw localzzbw = zza(localzze, paramSet1, localzzck);
      if (((Boolean)localzzbw.getObject()).booleanValue()) {
        paramzza.zza(localzze, localHashSet1, localHashSet2, localzzck);
      }
      if ((bool) && (localzzbw.zzza())) {}
      for (bool = true;; bool = false) {
        break;
      }
    }
    localHashSet1.removeAll(localHashSet2);
    paramzzco.zzh(localHashSet1);
    return new zzbw(localHashSet1, bool);
  }
  
  private static String zza(zzqf.zza paramzza)
  {
    return zzdf.zzg((zzag.zza)paramzza.zzAn().get(com.google.android.gms.internal.zzae.zzfr.toString()));
  }
  
  private void zza(zzag.zza paramzza, Set<String> paramSet)
  {
    if (paramzza == null) {}
    for (;;)
    {
      return;
      paramzza = zza(paramzza, paramSet, new zzbu());
      if (paramzza != zzaNe)
      {
        paramzza = zzdf.zzl((zzag.zza)paramzza.getObject());
        if ((paramzza instanceof Map))
        {
          paramzza = (Map)paramzza;
          this.zzaKz.push(paramzza);
          return;
        }
        if (!(paramzza instanceof List)) {
          break;
        }
        paramzza = ((List)paramzza).iterator();
        while (paramzza.hasNext())
        {
          paramSet = paramzza.next();
          if ((paramSet instanceof Map))
          {
            paramSet = (Map)paramSet;
            this.zzaKz.push(paramSet);
          }
          else
          {
            zzbg.zzaC("pushAfterEvaluate: value not a Map");
          }
        }
      }
    }
    zzbg.zzaC("pushAfterEvaluate: value not a Map or List");
  }
  
  private static void zza(List<zzqf.zza> paramList, List<String> paramList1, String paramString)
  {
    if (paramList.size() != paramList1.size()) {
      zzbg.zzaA("Invalid resource: imbalance of rule names of functions for " + paramString + " operation. Using default rule name instead");
    }
  }
  
  private static void zza(Map<String, zzak> paramMap, zzak paramzzak)
  {
    if (paramMap.containsKey(paramzzak.zzyM())) {
      throw new IllegalArgumentException("Duplicate function type name: " + paramzzak.zzyM());
    }
    paramMap.put(paramzzak.zzyM(), paramzzak);
  }
  
  private static zzc zzh(Map<String, zzc> paramMap, String paramString)
  {
    zzc localzzc2 = (zzc)paramMap.get(paramString);
    zzc localzzc1 = localzzc2;
    if (localzzc2 == null)
    {
      localzzc1 = new zzc();
      paramMap.put(paramString, localzzc1);
    }
    return localzzc1;
  }
  
  private String zzzq()
  {
    if (this.zzaNp <= 1) {
      return "";
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(Integer.toString(this.zzaNp));
    int i = 2;
    while (i < this.zzaNp)
    {
      localStringBuilder.append(' ');
      i += 1;
    }
    localStringBuilder.append(": ");
    return localStringBuilder.toString();
  }
  
  zzbw<Boolean> zza(zzqf.zza paramzza, Set<String> paramSet, zzch paramzzch)
  {
    paramzza = zza(this.zzaNi, paramzza, paramSet, paramzzch);
    paramSet = zzdf.zzk((zzag.zza)paramzza.getObject());
    paramzzch.zzd(zzdf.zzI(paramSet));
    return new zzbw(paramSet, paramzza.zzza());
  }
  
  zzbw<Boolean> zza(zzqf.zze paramzze, Set<String> paramSet, zzck paramzzck)
  {
    Object localObject = paramzze.zzAv().iterator();
    boolean bool = true;
    if (((Iterator)localObject).hasNext())
    {
      zzbw localzzbw = zza((zzqf.zza)((Iterator)localObject).next(), paramSet, paramzzck.zzyS());
      if (((Boolean)localzzbw.getObject()).booleanValue())
      {
        paramzzck.zzf(zzdf.zzI(Boolean.valueOf(false)));
        return new zzbw(Boolean.valueOf(false), localzzbw.zzza());
      }
      if ((bool) && (localzzbw.zzza())) {}
      for (bool = true;; bool = false) {
        break;
      }
    }
    paramzze = paramzze.zzAu().iterator();
    while (paramzze.hasNext())
    {
      localObject = zza((zzqf.zza)paramzze.next(), paramSet, paramzzck.zzyT());
      if (!((Boolean)((zzbw)localObject).getObject()).booleanValue())
      {
        paramzzck.zzf(zzdf.zzI(Boolean.valueOf(false)));
        return new zzbw(Boolean.valueOf(false), ((zzbw)localObject).zzza());
      }
      if ((bool) && (((zzbw)localObject).zzza())) {
        bool = true;
      } else {
        bool = false;
      }
    }
    paramzzck.zzf(zzdf.zzI(Boolean.valueOf(true)));
    return new zzbw(Boolean.valueOf(true), bool);
  }
  
  zzbw<Set<zzqf.zza>> zza(String paramString, Set<zzqf.zze> paramSet, final Map<zzqf.zze, List<zzqf.zza>> paramMap1, final Map<zzqf.zze, List<String>> paramMap2, final Map<zzqf.zze, List<zzqf.zza>> paramMap3, final Map<zzqf.zze, List<String>> paramMap4, Set<String> paramSet1, zzco paramzzco)
  {
    zza(paramSet, paramSet1, new zza()
    {
      public void zza(zzqf.zze paramAnonymouszze, Set<zzqf.zza> paramAnonymousSet1, Set<zzqf.zza> paramAnonymousSet2, zzck paramAnonymouszzck)
      {
        List localList1 = (List)paramMap1.get(paramAnonymouszze);
        List localList2 = (List)paramMap2.get(paramAnonymouszze);
        if (localList1 != null)
        {
          paramAnonymousSet1.addAll(localList1);
          paramAnonymouszzck.zzyU().zzc(localList1, localList2);
        }
        paramAnonymousSet1 = (List)paramMap3.get(paramAnonymouszze);
        paramAnonymouszze = (List)paramMap4.get(paramAnonymouszze);
        if (paramAnonymousSet1 != null)
        {
          paramAnonymousSet2.addAll(paramAnonymousSet1);
          paramAnonymouszzck.zzyV().zzc(paramAnonymousSet1, paramAnonymouszze);
        }
      }
    }, paramzzco);
  }
  
  zzbw<Set<zzqf.zza>> zza(Set<zzqf.zze> paramSet, zzco paramzzco)
  {
    zza(paramSet, new HashSet(), new zza()
    {
      public void zza(zzqf.zze paramAnonymouszze, Set<zzqf.zza> paramAnonymousSet1, Set<zzqf.zza> paramAnonymousSet2, zzck paramAnonymouszzck)
      {
        paramAnonymousSet1.addAll(paramAnonymouszze.zzAw());
        paramAnonymousSet2.addAll(paramAnonymouszze.zzAx());
        paramAnonymouszzck.zzyW().zzc(paramAnonymouszze.zzAw(), paramAnonymouszze.zzAB());
        paramAnonymouszzck.zzyX().zzc(paramAnonymouszze.zzAx(), paramAnonymouszze.zzAC());
      }
    }, paramzzco);
  }
  
  void zza(zzak paramzzak)
  {
    zza(this.zzaNj, paramzzak);
  }
  
  void zzb(zzak paramzzak)
  {
    zza(this.zzaNh, paramzzak);
  }
  
  void zzc(zzak paramzzak)
  {
    zza(this.zzaNi, paramzzak);
  }
  
  public zzbw<zzag.zza> zzeD(String paramString)
  {
    this.zzaNp = 0;
    zzag localzzag = this.zzaNg.zzet(paramString);
    paramString = zza(paramString, new HashSet(), localzzag.zzyI());
    localzzag.zzyK();
    return paramString;
  }
  
  void zzeE(String paramString)
  {
    try
    {
      this.zzaNo = paramString;
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public void zzeh(String paramString)
  {
    try
    {
      zzeE(paramString);
      paramString = this.zzaNg.zzeu(paramString);
      zzu localzzu = paramString.zzyJ();
      Iterator localIterator = ((Set)zza(this.zzaNm, localzzu.zzyB()).getObject()).iterator();
      while (localIterator.hasNext())
      {
        zzqf.zza localzza = (zzqf.zza)localIterator.next();
        zza(this.zzaNh, localzza, new HashSet(), localzzu.zzyA());
      }
      paramString.zzyK();
    }
    finally {}
    zzeE(null);
  }
  
  public void zzs(List<zzaf.zzi> paramList)
  {
    for (;;)
    {
      try
      {
        paramList = paramList.iterator();
        if (!paramList.hasNext()) {
          break;
        }
        zzaf.zzi localzzi = (zzaf.zzi)paramList.next();
        if ((localzzi.name == null) || (!localzzi.name.startsWith("gaExperiment:"))) {
          zzbg.zzaB("Ignored supplemental: " + localzzi);
        } else {
          zzaj.zza(this.zzaKz, localzzi);
        }
      }
      finally {}
    }
  }
  
  String zzzp()
  {
    try
    {
      String str = this.zzaNo;
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  static abstract interface zza
  {
    public abstract void zza(zzqf.zze paramzze, Set<zzqf.zza> paramSet1, Set<zzqf.zza> paramSet2, zzck paramzzck);
  }
  
  private static class zzb
  {
    private zzbw<zzag.zza> zzaNv;
    private zzag.zza zzaNw;
    
    public zzb(zzbw<zzag.zza> paramzzbw, zzag.zza paramzza)
    {
      this.zzaNv = paramzzbw;
      this.zzaNw = paramzza;
    }
    
    public int getSize()
    {
      int j = ((zzag.zza)this.zzaNv.getObject()).zzBU();
      if (this.zzaNw == null) {}
      for (int i = 0;; i = this.zzaNw.zzBU()) {
        return i + j;
      }
    }
    
    public zzbw<zzag.zza> zzzr()
    {
      return this.zzaNv;
    }
    
    public zzag.zza zzzs()
    {
      return this.zzaNw;
    }
  }
  
  private static class zzc
  {
    private final Map<zzqf.zze, List<String>> zzaNA = new HashMap();
    private zzqf.zza zzaNB;
    private final Set<zzqf.zze> zzaNm = new HashSet();
    private final Map<zzqf.zze, List<zzqf.zza>> zzaNx = new HashMap();
    private final Map<zzqf.zze, List<zzqf.zza>> zzaNy = new HashMap();
    private final Map<zzqf.zze, List<String>> zzaNz = new HashMap();
    
    public void zza(zzqf.zze paramzze)
    {
      this.zzaNm.add(paramzze);
    }
    
    public void zza(zzqf.zze paramzze, zzqf.zza paramzza)
    {
      List localList = (List)this.zzaNx.get(paramzze);
      Object localObject = localList;
      if (localList == null)
      {
        localObject = new ArrayList();
        this.zzaNx.put(paramzze, localObject);
      }
      ((List)localObject).add(paramzza);
    }
    
    public void zza(zzqf.zze paramzze, String paramString)
    {
      List localList = (List)this.zzaNz.get(paramzze);
      Object localObject = localList;
      if (localList == null)
      {
        localObject = new ArrayList();
        this.zzaNz.put(paramzze, localObject);
      }
      ((List)localObject).add(paramString);
    }
    
    public void zzb(zzqf.zza paramzza)
    {
      this.zzaNB = paramzza;
    }
    
    public void zzb(zzqf.zze paramzze, zzqf.zza paramzza)
    {
      List localList = (List)this.zzaNy.get(paramzze);
      Object localObject = localList;
      if (localList == null)
      {
        localObject = new ArrayList();
        this.zzaNy.put(paramzze, localObject);
      }
      ((List)localObject).add(paramzza);
    }
    
    public void zzb(zzqf.zze paramzze, String paramString)
    {
      List localList = (List)this.zzaNA.get(paramzze);
      Object localObject = localList;
      if (localList == null)
      {
        localObject = new ArrayList();
        this.zzaNA.put(paramzze, localObject);
      }
      ((List)localObject).add(paramString);
    }
    
    public Set<zzqf.zze> zzzt()
    {
      return this.zzaNm;
    }
    
    public Map<zzqf.zze, List<zzqf.zza>> zzzu()
    {
      return this.zzaNx;
    }
    
    public Map<zzqf.zze, List<String>> zzzv()
    {
      return this.zzaNz;
    }
    
    public Map<zzqf.zze, List<String>> zzzw()
    {
      return this.zzaNA;
    }
    
    public Map<zzqf.zze, List<zzqf.zza>> zzzx()
    {
      return this.zzaNy;
    }
    
    public zzqf.zza zzzy()
    {
      return this.zzaNB;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzcp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */