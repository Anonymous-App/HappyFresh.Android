package com.google.android.gms.internal;

import com.google.android.gms.tagmanager.zzbg;
import com.google.android.gms.tagmanager.zzdf;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class zzqf
{
  private static zzag.zza zza(int paramInt, zzaf.zzf paramzzf, zzag.zza[] paramArrayOfzza, Set<Integer> paramSet)
    throws zzqf.zzg
  {
    int k = 0;
    int m = 0;
    int j = 0;
    if (paramSet.contains(Integer.valueOf(paramInt))) {
      zzeT("Value cycle detected.  Current value reference: " + paramInt + "." + "  Previous value references: " + paramSet + ".");
    }
    zzag.zza localzza1 = (zzag.zza)zza(paramzzf.zzic, paramInt, "values");
    if (paramArrayOfzza[paramInt] != null) {
      return paramArrayOfzza[paramInt];
    }
    Object localObject = null;
    paramSet.add(Integer.valueOf(paramInt));
    switch (localzza1.type)
    {
    }
    for (;;)
    {
      if (localObject == null) {
        zzeT("Invalid value: " + localzza1);
      }
      paramArrayOfzza[paramInt] = localObject;
      paramSet.remove(Integer.valueOf(paramInt));
      return (zzag.zza)localObject;
      localObject = zzp(localzza1);
      zzag.zza localzza2 = zzo(localzza1);
      localzza2.zziS = new zzag.zza[((zzaf.zzh)localObject).zziD.length];
      int[] arrayOfInt = ((zzaf.zzh)localObject).zziD;
      k = arrayOfInt.length;
      int i = 0;
      for (;;)
      {
        localObject = localzza2;
        if (j >= k) {
          break;
        }
        m = arrayOfInt[j];
        localzza2.zziS[i] = zza(m, paramzzf, paramArrayOfzza, paramSet);
        j += 1;
        i += 1;
      }
      localzza2 = zzo(localzza1);
      localObject = zzp(localzza1);
      if (((zzaf.zzh)localObject).zziE.length != ((zzaf.zzh)localObject).zziF.length) {
        zzeT("Uneven map keys (" + ((zzaf.zzh)localObject).zziE.length + ") and map values (" + ((zzaf.zzh)localObject).zziF.length + ")");
      }
      localzza2.zziT = new zzag.zza[((zzaf.zzh)localObject).zziE.length];
      localzza2.zziU = new zzag.zza[((zzaf.zzh)localObject).zziE.length];
      arrayOfInt = ((zzaf.zzh)localObject).zziE;
      m = arrayOfInt.length;
      j = 0;
      i = 0;
      while (j < m)
      {
        int n = arrayOfInt[j];
        localzza2.zziT[i] = zza(n, paramzzf, paramArrayOfzza, paramSet);
        j += 1;
        i += 1;
      }
      arrayOfInt = ((zzaf.zzh)localObject).zziF;
      m = arrayOfInt.length;
      i = 0;
      j = k;
      for (;;)
      {
        localObject = localzza2;
        if (j >= m) {
          break;
        }
        k = arrayOfInt[j];
        localzza2.zziU[i] = zza(k, paramzzf, paramArrayOfzza, paramSet);
        j += 1;
        i += 1;
      }
      localObject = zzo(localzza1);
      ((zzag.zza)localObject).zziV = zzdf.zzg(zza(zzp(localzza1).zziI, paramzzf, paramArrayOfzza, paramSet));
      continue;
      localzza2 = zzo(localzza1);
      localObject = zzp(localzza1);
      localzza2.zziZ = new zzag.zza[((zzaf.zzh)localObject).zziH.length];
      arrayOfInt = ((zzaf.zzh)localObject).zziH;
      k = arrayOfInt.length;
      i = 0;
      j = m;
      for (;;)
      {
        localObject = localzza2;
        if (j >= k) {
          break;
        }
        m = arrayOfInt[j];
        localzza2.zziZ[i] = zza(m, paramzzf, paramArrayOfzza, paramSet);
        j += 1;
        i += 1;
      }
      localObject = localzza1;
    }
  }
  
  private static zza zza(zzaf.zzb paramzzb, zzaf.zzf paramzzf, zzag.zza[] paramArrayOfzza, int paramInt)
    throws zzqf.zzg
  {
    zzb localzzb = zza.zzAm();
    paramzzb = paramzzb.zzhN;
    int i = paramzzb.length;
    paramInt = 0;
    if (paramInt < i)
    {
      int j = paramzzb[paramInt];
      Object localObject = (zzaf.zze)zza(paramzzf.zzid, Integer.valueOf(j).intValue(), "properties");
      String str = (String)zza(paramzzf.zzib, ((zzaf.zze)localObject).key, "keys");
      localObject = (zzag.zza)zza(paramArrayOfzza, ((zzaf.zze)localObject).value, "values");
      if (zzae.zzgs.toString().equals(str)) {
        localzzb.zzq((zzag.zza)localObject);
      }
      for (;;)
      {
        paramInt += 1;
        break;
        localzzb.zzb(str, (zzag.zza)localObject);
      }
    }
    return localzzb.zzAo();
  }
  
  private static zze zza(zzaf.zzg paramzzg, List<zza> paramList1, List<zza> paramList2, List<zza> paramList3, zzaf.zzf paramzzf)
  {
    zzf localzzf = zze.zzAt();
    int[] arrayOfInt = paramzzg.zzir;
    int j = arrayOfInt.length;
    int i = 0;
    while (i < j)
    {
      localzzf.zzd((zza)paramList3.get(Integer.valueOf(arrayOfInt[i]).intValue()));
      i += 1;
    }
    arrayOfInt = paramzzg.zzis;
    j = arrayOfInt.length;
    i = 0;
    while (i < j)
    {
      localzzf.zze((zza)paramList3.get(Integer.valueOf(arrayOfInt[i]).intValue()));
      i += 1;
    }
    paramList3 = paramzzg.zzit;
    j = paramList3.length;
    i = 0;
    while (i < j)
    {
      localzzf.zzf((zza)paramList1.get(Integer.valueOf(paramList3[i]).intValue()));
      i += 1;
    }
    paramList3 = paramzzg.zziv;
    j = paramList3.length;
    i = 0;
    int k;
    while (i < j)
    {
      k = paramList3[i];
      localzzf.zzeW(paramzzf.zzic[Integer.valueOf(k).intValue()].zziR);
      i += 1;
    }
    paramList3 = paramzzg.zziu;
    j = paramList3.length;
    i = 0;
    while (i < j)
    {
      localzzf.zzg((zza)paramList1.get(Integer.valueOf(paramList3[i]).intValue()));
      i += 1;
    }
    paramList1 = paramzzg.zziw;
    j = paramList1.length;
    i = 0;
    while (i < j)
    {
      k = paramList1[i];
      localzzf.zzeX(paramzzf.zzic[Integer.valueOf(k).intValue()].zziR);
      i += 1;
    }
    paramList1 = paramzzg.zzix;
    j = paramList1.length;
    i = 0;
    while (i < j)
    {
      localzzf.zzh((zza)paramList2.get(Integer.valueOf(paramList1[i]).intValue()));
      i += 1;
    }
    paramList1 = paramzzg.zziz;
    j = paramList1.length;
    i = 0;
    while (i < j)
    {
      k = paramList1[i];
      localzzf.zzeY(paramzzf.zzic[Integer.valueOf(k).intValue()].zziR);
      i += 1;
    }
    paramList1 = paramzzg.zziy;
    j = paramList1.length;
    i = 0;
    while (i < j)
    {
      localzzf.zzi((zza)paramList2.get(Integer.valueOf(paramList1[i]).intValue()));
      i += 1;
    }
    paramzzg = paramzzg.zziA;
    j = paramzzg.length;
    i = 0;
    while (i < j)
    {
      k = paramzzg[i];
      localzzf.zzeZ(paramzzf.zzic[Integer.valueOf(k).intValue()].zziR);
      i += 1;
    }
    return localzzf.zzAE();
  }
  
  private static <T> T zza(T[] paramArrayOfT, int paramInt, String paramString)
    throws zzqf.zzg
  {
    if ((paramInt < 0) || (paramInt >= paramArrayOfT.length)) {
      zzeT("Index out of bounds detected: " + paramInt + " in " + paramString);
    }
    return paramArrayOfT[paramInt];
  }
  
  public static zzc zzb(zzaf.zzf paramzzf)
    throws zzqf.zzg
  {
    int j = 0;
    Object localObject = new zzag.zza[paramzzf.zzic.length];
    int i = 0;
    while (i < paramzzf.zzic.length)
    {
      zza(i, paramzzf, (zzag.zza[])localObject, new HashSet(0));
      i += 1;
    }
    zzd localzzd = zzc.zzAp();
    ArrayList localArrayList1 = new ArrayList();
    i = 0;
    while (i < paramzzf.zzif.length)
    {
      localArrayList1.add(zza(paramzzf.zzif[i], paramzzf, (zzag.zza[])localObject, i));
      i += 1;
    }
    ArrayList localArrayList2 = new ArrayList();
    i = 0;
    while (i < paramzzf.zzig.length)
    {
      localArrayList2.add(zza(paramzzf.zzig[i], paramzzf, (zzag.zza[])localObject, i));
      i += 1;
    }
    ArrayList localArrayList3 = new ArrayList();
    i = 0;
    while (i < paramzzf.zzie.length)
    {
      zza localzza = zza(paramzzf.zzie[i], paramzzf, (zzag.zza[])localObject, i);
      localzzd.zzc(localzza);
      localArrayList3.add(localzza);
      i += 1;
    }
    localObject = paramzzf.zzih;
    int k = localObject.length;
    i = j;
    while (i < k)
    {
      localzzd.zzb(zza(localObject[i], localArrayList1, localArrayList3, localArrayList2, paramzzf));
      i += 1;
    }
    localzzd.zzeV(paramzzf.version);
    localzzd.zzjb(paramzzf.zzip);
    return localzzd.zzAs();
  }
  
  public static void zzc(InputStream paramInputStream, OutputStream paramOutputStream)
    throws IOException
  {
    byte[] arrayOfByte = new byte['Ѐ'];
    for (;;)
    {
      int i = paramInputStream.read(arrayOfByte);
      if (i == -1) {
        return;
      }
      paramOutputStream.write(arrayOfByte, 0, i);
    }
  }
  
  private static void zzeT(String paramString)
    throws zzqf.zzg
  {
    zzbg.zzaz(paramString);
    throw new zzg(paramString);
  }
  
  public static zzag.zza zzo(zzag.zza paramzza)
  {
    zzag.zza localzza = new zzag.zza();
    localzza.type = paramzza.type;
    localzza.zzja = ((int[])paramzza.zzja.clone());
    if (paramzza.zzjb) {
      localzza.zzjb = paramzza.zzjb;
    }
    return localzza;
  }
  
  private static zzaf.zzh zzp(zzag.zza paramzza)
    throws zzqf.zzg
  {
    if ((zzaf.zzh)paramzza.zza(zzaf.zzh.zziB) == null) {
      zzeT("Expected a ServingValue and didn't get one. Value is: " + paramzza);
    }
    return (zzaf.zzh)paramzza.zza(zzaf.zzh.zziB);
  }
  
  public static class zza
  {
    private final zzag.zza zzaNw;
    private final Map<String, zzag.zza> zzaPy;
    
    private zza(Map<String, zzag.zza> paramMap, zzag.zza paramzza)
    {
      this.zzaPy = paramMap;
      this.zzaNw = paramzza;
    }
    
    public static zzqf.zzb zzAm()
    {
      return new zzqf.zzb(null);
    }
    
    public String toString()
    {
      return "Properties: " + zzAn() + " pushAfterEvaluate: " + this.zzaNw;
    }
    
    public Map<String, zzag.zza> zzAn()
    {
      return Collections.unmodifiableMap(this.zzaPy);
    }
    
    public void zza(String paramString, zzag.zza paramzza)
    {
      this.zzaPy.put(paramString, paramzza);
    }
    
    public zzag.zza zzzs()
    {
      return this.zzaNw;
    }
  }
  
  public static class zzb
  {
    private zzag.zza zzaNw;
    private final Map<String, zzag.zza> zzaPy = new HashMap();
    
    public zzqf.zza zzAo()
    {
      return new zzqf.zza(this.zzaPy, this.zzaNw, null);
    }
    
    public zzb zzb(String paramString, zzag.zza paramzza)
    {
      this.zzaPy.put(paramString, paramzza);
      return this;
    }
    
    public zzb zzq(zzag.zza paramzza)
    {
      this.zzaNw = paramzza;
      return this;
    }
  }
  
  public static class zzc
  {
    private final String zzTQ;
    private final Map<String, List<zzqf.zza>> zzaPA;
    private final int zzaPB;
    private final List<zzqf.zze> zzaPz;
    
    private zzc(List<zzqf.zze> paramList, Map<String, List<zzqf.zza>> paramMap, String paramString, int paramInt)
    {
      this.zzaPz = Collections.unmodifiableList(paramList);
      this.zzaPA = Collections.unmodifiableMap(paramMap);
      this.zzTQ = paramString;
      this.zzaPB = paramInt;
    }
    
    public static zzqf.zzd zzAp()
    {
      return new zzqf.zzd(null);
    }
    
    public String getVersion()
    {
      return this.zzTQ;
    }
    
    public String toString()
    {
      return "Rules: " + zzAq() + "  Macros: " + this.zzaPA;
    }
    
    public List<zzqf.zze> zzAq()
    {
      return this.zzaPz;
    }
    
    public Map<String, List<zzqf.zza>> zzAr()
    {
      return this.zzaPA;
    }
  }
  
  public static class zzd
  {
    private String zzTQ = "";
    private final Map<String, List<zzqf.zza>> zzaPA = new HashMap();
    private int zzaPB = 0;
    private final List<zzqf.zze> zzaPz = new ArrayList();
    
    public zzqf.zzc zzAs()
    {
      return new zzqf.zzc(this.zzaPz, this.zzaPA, this.zzTQ, this.zzaPB, null);
    }
    
    public zzd zzb(zzqf.zze paramzze)
    {
      this.zzaPz.add(paramzze);
      return this;
    }
    
    public zzd zzc(zzqf.zza paramzza)
    {
      String str = zzdf.zzg((zzag.zza)paramzza.zzAn().get(zzae.zzfr.toString()));
      List localList = (List)this.zzaPA.get(str);
      Object localObject = localList;
      if (localList == null)
      {
        localObject = new ArrayList();
        this.zzaPA.put(str, localObject);
      }
      ((List)localObject).add(paramzza);
      return this;
    }
    
    public zzd zzeV(String paramString)
    {
      this.zzTQ = paramString;
      return this;
    }
    
    public zzd zzjb(int paramInt)
    {
      this.zzaPB = paramInt;
      return this;
    }
  }
  
  public static class zze
  {
    private final List<zzqf.zza> zzaPC;
    private final List<zzqf.zza> zzaPD;
    private final List<zzqf.zza> zzaPE;
    private final List<zzqf.zza> zzaPF;
    private final List<zzqf.zza> zzaPG;
    private final List<zzqf.zza> zzaPH;
    private final List<String> zzaPI;
    private final List<String> zzaPJ;
    private final List<String> zzaPK;
    private final List<String> zzaPL;
    
    private zze(List<zzqf.zza> paramList1, List<zzqf.zza> paramList2, List<zzqf.zza> paramList3, List<zzqf.zza> paramList4, List<zzqf.zza> paramList5, List<zzqf.zza> paramList6, List<String> paramList7, List<String> paramList8, List<String> paramList9, List<String> paramList10)
    {
      this.zzaPC = Collections.unmodifiableList(paramList1);
      this.zzaPD = Collections.unmodifiableList(paramList2);
      this.zzaPE = Collections.unmodifiableList(paramList3);
      this.zzaPF = Collections.unmodifiableList(paramList4);
      this.zzaPG = Collections.unmodifiableList(paramList5);
      this.zzaPH = Collections.unmodifiableList(paramList6);
      this.zzaPI = Collections.unmodifiableList(paramList7);
      this.zzaPJ = Collections.unmodifiableList(paramList8);
      this.zzaPK = Collections.unmodifiableList(paramList9);
      this.zzaPL = Collections.unmodifiableList(paramList10);
    }
    
    public static zzqf.zzf zzAt()
    {
      return new zzqf.zzf(null);
    }
    
    public String toString()
    {
      return "Positive predicates: " + zzAu() + "  Negative predicates: " + zzAv() + "  Add tags: " + zzAw() + "  Remove tags: " + zzAx() + "  Add macros: " + zzAy() + "  Remove macros: " + zzAD();
    }
    
    public List<String> zzAA()
    {
      return this.zzaPJ;
    }
    
    public List<String> zzAB()
    {
      return this.zzaPK;
    }
    
    public List<String> zzAC()
    {
      return this.zzaPL;
    }
    
    public List<zzqf.zza> zzAD()
    {
      return this.zzaPH;
    }
    
    public List<zzqf.zza> zzAu()
    {
      return this.zzaPC;
    }
    
    public List<zzqf.zza> zzAv()
    {
      return this.zzaPD;
    }
    
    public List<zzqf.zza> zzAw()
    {
      return this.zzaPE;
    }
    
    public List<zzqf.zza> zzAx()
    {
      return this.zzaPF;
    }
    
    public List<zzqf.zza> zzAy()
    {
      return this.zzaPG;
    }
    
    public List<String> zzAz()
    {
      return this.zzaPI;
    }
  }
  
  public static class zzf
  {
    private final List<zzqf.zza> zzaPC = new ArrayList();
    private final List<zzqf.zza> zzaPD = new ArrayList();
    private final List<zzqf.zza> zzaPE = new ArrayList();
    private final List<zzqf.zza> zzaPF = new ArrayList();
    private final List<zzqf.zza> zzaPG = new ArrayList();
    private final List<zzqf.zza> zzaPH = new ArrayList();
    private final List<String> zzaPI = new ArrayList();
    private final List<String> zzaPJ = new ArrayList();
    private final List<String> zzaPK = new ArrayList();
    private final List<String> zzaPL = new ArrayList();
    
    public zzqf.zze zzAE()
    {
      return new zzqf.zze(this.zzaPC, this.zzaPD, this.zzaPE, this.zzaPF, this.zzaPG, this.zzaPH, this.zzaPI, this.zzaPJ, this.zzaPK, this.zzaPL, null);
    }
    
    public zzf zzd(zzqf.zza paramzza)
    {
      this.zzaPC.add(paramzza);
      return this;
    }
    
    public zzf zze(zzqf.zza paramzza)
    {
      this.zzaPD.add(paramzza);
      return this;
    }
    
    public zzf zzeW(String paramString)
    {
      this.zzaPK.add(paramString);
      return this;
    }
    
    public zzf zzeX(String paramString)
    {
      this.zzaPL.add(paramString);
      return this;
    }
    
    public zzf zzeY(String paramString)
    {
      this.zzaPI.add(paramString);
      return this;
    }
    
    public zzf zzeZ(String paramString)
    {
      this.zzaPJ.add(paramString);
      return this;
    }
    
    public zzf zzf(zzqf.zza paramzza)
    {
      this.zzaPE.add(paramzza);
      return this;
    }
    
    public zzf zzg(zzqf.zza paramzza)
    {
      this.zzaPF.add(paramzza);
      return this;
    }
    
    public zzf zzh(zzqf.zza paramzza)
    {
      this.zzaPG.add(paramzza);
      return this;
    }
    
    public zzf zzi(zzqf.zza paramzza)
    {
      this.zzaPH.add(paramzza);
      return this;
    }
  }
  
  public static class zzg
    extends Exception
  {
    public zzg(String paramString)
    {
      super();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzqf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */