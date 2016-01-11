package com.google.android.gms.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class zzrk
  implements Cloneable
{
  private zzri<?, ?> zzaWb;
  private Object zzaWc;
  private List<zzrp> zzaWd = new ArrayList();
  
  private byte[] toByteArray()
    throws IOException
  {
    byte[] arrayOfByte = new byte[zzB()];
    zza(zzrg.zzA(arrayOfByte));
    return arrayOfByte;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = false;
    boolean bool1;
    if (paramObject == this) {
      bool1 = true;
    }
    do
    {
      do
      {
        return bool1;
        bool1 = bool2;
      } while (!(paramObject instanceof zzrk));
      paramObject = (zzrk)paramObject;
      if ((this.zzaWc == null) || (((zzrk)paramObject).zzaWc == null)) {
        break;
      }
      bool1 = bool2;
    } while (this.zzaWb != ((zzrk)paramObject).zzaWb);
    if (!this.zzaWb.zzaVV.isArray()) {
      return this.zzaWc.equals(((zzrk)paramObject).zzaWc);
    }
    if ((this.zzaWc instanceof byte[])) {
      return Arrays.equals((byte[])this.zzaWc, (byte[])((zzrk)paramObject).zzaWc);
    }
    if ((this.zzaWc instanceof int[])) {
      return Arrays.equals((int[])this.zzaWc, (int[])((zzrk)paramObject).zzaWc);
    }
    if ((this.zzaWc instanceof long[])) {
      return Arrays.equals((long[])this.zzaWc, (long[])((zzrk)paramObject).zzaWc);
    }
    if ((this.zzaWc instanceof float[])) {
      return Arrays.equals((float[])this.zzaWc, (float[])((zzrk)paramObject).zzaWc);
    }
    if ((this.zzaWc instanceof double[])) {
      return Arrays.equals((double[])this.zzaWc, (double[])((zzrk)paramObject).zzaWc);
    }
    if ((this.zzaWc instanceof boolean[])) {
      return Arrays.equals((boolean[])this.zzaWc, (boolean[])((zzrk)paramObject).zzaWc);
    }
    return Arrays.deepEquals((Object[])this.zzaWc, (Object[])((zzrk)paramObject).zzaWc);
    if ((this.zzaWd != null) && (((zzrk)paramObject).zzaWd != null)) {
      return this.zzaWd.equals(((zzrk)paramObject).zzaWd);
    }
    try
    {
      bool1 = Arrays.equals(toByteArray(), ((zzrk)paramObject).toByteArray());
      return bool1;
    }
    catch (IOException paramObject)
    {
      throw new IllegalStateException((Throwable)paramObject);
    }
  }
  
  public int hashCode()
  {
    try
    {
      int i = Arrays.hashCode(toByteArray());
      return i + 527;
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException(localIOException);
    }
  }
  
  int zzB()
  {
    int j;
    if (this.zzaWc != null)
    {
      j = this.zzaWb.zzQ(this.zzaWc);
      return j;
    }
    Iterator localIterator = this.zzaWd.iterator();
    for (int i = 0;; i = ((zzrp)localIterator.next()).zzB() + i)
    {
      j = i;
      if (!localIterator.hasNext()) {
        break;
      }
    }
  }
  
  public final zzrk zzBM()
  {
    int i = 0;
    zzrk localzzrk = new zzrk();
    try
    {
      localzzrk.zzaWb = this.zzaWb;
      if (this.zzaWd == null) {
        localzzrk.zzaWd = null;
      }
      while (this.zzaWc == null)
      {
        return localzzrk;
        localzzrk.zzaWd.addAll(this.zzaWd);
      }
      if (!(this.zzaWc instanceof zzrn)) {
        break label92;
      }
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      throw new AssertionError(localCloneNotSupportedException);
    }
    localCloneNotSupportedException.zzaWc = ((zzrn)this.zzaWc).zzBK();
    return localCloneNotSupportedException;
    label92:
    if ((this.zzaWc instanceof byte[]))
    {
      localCloneNotSupportedException.zzaWc = ((byte[])this.zzaWc).clone();
      return localCloneNotSupportedException;
    }
    Object localObject1;
    Object localObject2;
    if ((this.zzaWc instanceof byte[][]))
    {
      localObject1 = (byte[][])this.zzaWc;
      localObject2 = new byte[localObject1.length][];
      localCloneNotSupportedException.zzaWc = localObject2;
      i = 0;
      while (i < localObject1.length)
      {
        localObject2[i] = ((byte[])localObject1[i].clone());
        i += 1;
      }
    }
    if ((this.zzaWc instanceof boolean[]))
    {
      localCloneNotSupportedException.zzaWc = ((boolean[])this.zzaWc).clone();
      return localCloneNotSupportedException;
    }
    if ((this.zzaWc instanceof int[]))
    {
      localCloneNotSupportedException.zzaWc = ((int[])this.zzaWc).clone();
      return localCloneNotSupportedException;
    }
    if ((this.zzaWc instanceof long[]))
    {
      localCloneNotSupportedException.zzaWc = ((long[])this.zzaWc).clone();
      return localCloneNotSupportedException;
    }
    if ((this.zzaWc instanceof float[]))
    {
      localCloneNotSupportedException.zzaWc = ((float[])this.zzaWc).clone();
      return localCloneNotSupportedException;
    }
    if ((this.zzaWc instanceof double[]))
    {
      localCloneNotSupportedException.zzaWc = ((double[])this.zzaWc).clone();
      return localCloneNotSupportedException;
    }
    if ((this.zzaWc instanceof zzrn[]))
    {
      localObject1 = (zzrn[])this.zzaWc;
      localObject2 = new zzrn[localObject1.length];
      localCloneNotSupportedException.zzaWc = localObject2;
      while (i < localObject1.length)
      {
        localObject2[i] = localObject1[i].zzBK();
        i += 1;
      }
    }
    return localCloneNotSupportedException;
  }
  
  void zza(zzrg paramzzrg)
    throws IOException
  {
    if (this.zzaWc != null) {
      this.zzaWb.zza(this.zzaWc, paramzzrg);
    }
    for (;;)
    {
      return;
      Iterator localIterator = this.zzaWd.iterator();
      while (localIterator.hasNext()) {
        ((zzrp)localIterator.next()).zza(paramzzrg);
      }
    }
  }
  
  void zza(zzrp paramzzrp)
  {
    this.zzaWd.add(paramzzrp);
  }
  
  <T> T zzb(zzri<?, T> paramzzri)
  {
    if (this.zzaWc != null)
    {
      if (this.zzaWb != paramzzri) {
        throw new IllegalStateException("Tried to getExtension with a differernt Extension.");
      }
    }
    else
    {
      this.zzaWb = paramzzri;
      this.zzaWc = paramzzri.zzx(this.zzaWd);
      this.zzaWd = null;
    }
    return (T)this.zzaWc;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzrk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */