package com.google.android.gms.analytics.internal;

import android.text.TextUtils;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zzu;
import java.util.HashSet;
import java.util.Set;

public class zzr
{
  private final zzf zzIa;
  private Boolean zzKO;
  private String zzKP;
  private Set<Integer> zzKQ;
  
  protected zzr(zzf paramzzf)
  {
    zzu.zzu(paramzzf);
    this.zzIa = paramzzf;
  }
  
  public boolean zziW()
  {
    return zzd.zzZR;
  }
  
  /* Error */
  public boolean zziX()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 38	com/google/android/gms/analytics/internal/zzr:zzKO	Ljava/lang/Boolean;
    //   4: ifnonnull +154 -> 158
    //   7: aload_0
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield 38	com/google/android/gms/analytics/internal/zzr:zzKO	Ljava/lang/Boolean;
    //   13: ifnonnull +143 -> 156
    //   16: aload_0
    //   17: getfield 26	com/google/android/gms/analytics/internal/zzr:zzIa	Lcom/google/android/gms/analytics/internal/zzf;
    //   20: invokevirtual 44	com/google/android/gms/analytics/internal/zzf:getContext	()Landroid/content/Context;
    //   23: astore 4
    //   25: aload 4
    //   27: invokevirtual 50	android/content/Context:getApplicationInfo	()Landroid/content/pm/ApplicationInfo;
    //   30: astore_3
    //   31: aload_3
    //   32: ifnull +98 -> 130
    //   35: aload_3
    //   36: getfield 55	android/content/pm/ApplicationInfo:processName	Ljava/lang/String;
    //   39: astore_3
    //   40: aload 4
    //   42: ldc 57
    //   44: invokevirtual 61	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   47: checkcast 63	android/app/ActivityManager
    //   50: astore 4
    //   52: aload 4
    //   54: ifnull +76 -> 130
    //   57: invokestatic 69	android/os/Process:myPid	()I
    //   60: istore_1
    //   61: aload 4
    //   63: invokevirtual 73	android/app/ActivityManager:getRunningAppProcesses	()Ljava/util/List;
    //   66: invokeinterface 79 1 0
    //   71: astore 4
    //   73: aload 4
    //   75: invokeinterface 84 1 0
    //   80: ifeq +50 -> 130
    //   83: aload 4
    //   85: invokeinterface 88 1 0
    //   90: checkcast 90	android/app/ActivityManager$RunningAppProcessInfo
    //   93: astore 5
    //   95: iload_1
    //   96: aload 5
    //   98: getfield 94	android/app/ActivityManager$RunningAppProcessInfo:pid	I
    //   101: if_icmpne -28 -> 73
    //   104: aload_3
    //   105: ifnull +61 -> 166
    //   108: aload_3
    //   109: aload 5
    //   111: getfield 95	android/app/ActivityManager$RunningAppProcessInfo:processName	Ljava/lang/String;
    //   114: invokevirtual 101	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   117: ifeq +49 -> 166
    //   120: iconst_1
    //   121: istore_2
    //   122: aload_0
    //   123: iload_2
    //   124: invokestatic 107	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   127: putfield 38	com/google/android/gms/analytics/internal/zzr:zzKO	Ljava/lang/Boolean;
    //   130: aload_0
    //   131: getfield 38	com/google/android/gms/analytics/internal/zzr:zzKO	Ljava/lang/Boolean;
    //   134: ifnonnull +22 -> 156
    //   137: aload_0
    //   138: getstatic 110	java/lang/Boolean:TRUE	Ljava/lang/Boolean;
    //   141: putfield 38	com/google/android/gms/analytics/internal/zzr:zzKO	Ljava/lang/Boolean;
    //   144: aload_0
    //   145: getfield 26	com/google/android/gms/analytics/internal/zzr:zzIa	Lcom/google/android/gms/analytics/internal/zzf;
    //   148: invokevirtual 114	com/google/android/gms/analytics/internal/zzf:zzhQ	()Lcom/google/android/gms/analytics/internal/zzaf;
    //   151: ldc 116
    //   153: invokevirtual 122	com/google/android/gms/analytics/internal/zzaf:zzaX	(Ljava/lang/String;)V
    //   156: aload_0
    //   157: monitorexit
    //   158: aload_0
    //   159: getfield 38	com/google/android/gms/analytics/internal/zzr:zzKO	Ljava/lang/Boolean;
    //   162: invokevirtual 125	java/lang/Boolean:booleanValue	()Z
    //   165: ireturn
    //   166: iconst_0
    //   167: istore_2
    //   168: goto -46 -> 122
    //   171: astore_3
    //   172: aload_0
    //   173: monitorexit
    //   174: aload_3
    //   175: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	176	0	this	zzr
    //   60	42	1	i	int
    //   121	47	2	bool	boolean
    //   30	79	3	localObject1	Object
    //   171	4	3	localObject2	Object
    //   23	61	4	localObject3	Object
    //   93	17	5	localRunningAppProcessInfo	android.app.ActivityManager.RunningAppProcessInfo
    // Exception table:
    //   from	to	target	type
    //   9	31	171	finally
    //   35	52	171	finally
    //   57	73	171	finally
    //   73	104	171	finally
    //   108	120	171	finally
    //   122	130	171	finally
    //   130	156	171	finally
    //   156	158	171	finally
    //   172	174	171	finally
  }
  
  public boolean zziY()
  {
    return ((Boolean)zzy.zzLa.get()).booleanValue();
  }
  
  public int zziZ()
  {
    return ((Integer)zzy.zzLt.get()).intValue();
  }
  
  public int zzjA()
  {
    return ((Integer)zzy.zzLE.get()).intValue();
  }
  
  public long zzjB()
  {
    return ((Long)zzy.zzLF.get()).longValue();
  }
  
  public long zzjC()
  {
    return ((Long)zzy.zzLO.get()).longValue();
  }
  
  public int zzja()
  {
    return ((Integer)zzy.zzLx.get()).intValue();
  }
  
  public int zzjb()
  {
    return ((Integer)zzy.zzLy.get()).intValue();
  }
  
  public int zzjc()
  {
    return ((Integer)zzy.zzLz.get()).intValue();
  }
  
  public long zzjd()
  {
    return ((Long)zzy.zzLi.get()).longValue();
  }
  
  public long zzje()
  {
    return ((Long)zzy.zzLh.get()).longValue();
  }
  
  public long zzjf()
  {
    return ((Long)zzy.zzLl.get()).longValue();
  }
  
  public long zzjg()
  {
    return ((Long)zzy.zzLm.get()).longValue();
  }
  
  public int zzjh()
  {
    return ((Integer)zzy.zzLn.get()).intValue();
  }
  
  public int zzji()
  {
    return ((Integer)zzy.zzLo.get()).intValue();
  }
  
  public long zzjj()
  {
    return ((Integer)zzy.zzLB.get()).intValue();
  }
  
  public String zzjk()
  {
    return (String)zzy.zzLq.get();
  }
  
  public String zzjl()
  {
    return (String)zzy.zzLp.get();
  }
  
  public String zzjm()
  {
    return (String)zzy.zzLr.get();
  }
  
  public String zzjn()
  {
    return (String)zzy.zzLs.get();
  }
  
  public zzm zzjo()
  {
    return zzm.zzbc((String)zzy.zzLu.get());
  }
  
  public zzo zzjp()
  {
    return zzo.zzbd((String)zzy.zzLv.get());
  }
  
  public Set<Integer> zzjq()
  {
    String str1 = (String)zzy.zzLA.get();
    String[] arrayOfString;
    HashSet localHashSet;
    int j;
    int i;
    if ((this.zzKQ == null) || (this.zzKP == null) || (!this.zzKP.equals(str1)))
    {
      arrayOfString = TextUtils.split(str1, ",");
      localHashSet = new HashSet();
      j = arrayOfString.length;
      i = 0;
    }
    for (;;)
    {
      String str2;
      if (i < j) {
        str2 = arrayOfString[i];
      }
      try
      {
        localHashSet.add(Integer.valueOf(Integer.parseInt(str2)));
        i += 1;
        continue;
        this.zzKP = str1;
        this.zzKQ = localHashSet;
        return this.zzKQ;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        for (;;) {}
      }
    }
  }
  
  public long zzjr()
  {
    return ((Long)zzy.zzLJ.get()).longValue();
  }
  
  public long zzjs()
  {
    return ((Long)zzy.zzLK.get()).longValue();
  }
  
  public long zzjt()
  {
    return ((Long)zzy.zzLN.get()).longValue();
  }
  
  public int zzju()
  {
    return ((Integer)zzy.zzLe.get()).intValue();
  }
  
  public int zzjv()
  {
    return ((Integer)zzy.zzLg.get()).intValue();
  }
  
  public String zzjw()
  {
    return "google_analytics_v4.db";
  }
  
  public String zzjx()
  {
    return "google_analytics2_v4.db";
  }
  
  public long zzjy()
  {
    return 86400000L;
  }
  
  public int zzjz()
  {
    return ((Integer)zzy.zzLD.get()).intValue();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/zzr.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */