package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Arrays;

public abstract interface zzrr
{
  public static final class zza
    extends zzrh<zza>
  {
    public String[] zzaWp;
    public String[] zzaWq;
    public int[] zzaWr;
    public int[] zzaWs;
    
    public zza()
    {
      zzBW();
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
          do
          {
            do
            {
              do
              {
                return bool1;
                bool1 = bool2;
              } while (!(paramObject instanceof zza));
              paramObject = (zza)paramObject;
              bool1 = bool2;
            } while (!zzrl.equals(this.zzaWp, ((zza)paramObject).zzaWp));
            bool1 = bool2;
          } while (!zzrl.equals(this.zzaWq, ((zza)paramObject).zzaWq));
          bool1 = bool2;
        } while (!zzrl.equals(this.zzaWr, ((zza)paramObject).zzaWr));
        bool1 = bool2;
      } while (!zzrl.equals(this.zzaWs, ((zza)paramObject).zzaWs));
      return zza((zzrh)paramObject);
    }
    
    public int hashCode()
    {
      return ((((zzrl.hashCode(this.zzaWp) + 527) * 31 + zzrl.hashCode(this.zzaWq)) * 31 + zzrl.hashCode(this.zzaWr)) * 31 + zzrl.hashCode(this.zzaWs)) * 31 + zzBI();
    }
    
    protected int zzB()
    {
      int i2 = 0;
      int i1 = super.zzB();
      int j;
      int k;
      String str;
      int n;
      int m;
      if ((this.zzaWp != null) && (this.zzaWp.length > 0))
      {
        i = 0;
        j = 0;
        for (k = 0; i < this.zzaWp.length; k = m)
        {
          str = this.zzaWp[i];
          n = j;
          m = k;
          if (str != null)
          {
            m = k + 1;
            n = j + zzrg.zzfj(str);
          }
          i += 1;
          j = n;
        }
      }
      for (int i = i1 + j + k * 1;; i = i1)
      {
        j = i;
        if (this.zzaWq != null)
        {
          j = i;
          if (this.zzaWq.length > 0)
          {
            j = 0;
            k = 0;
            for (m = 0; j < this.zzaWq.length; m = n)
            {
              str = this.zzaWq[j];
              i1 = k;
              n = m;
              if (str != null)
              {
                n = m + 1;
                i1 = k + zzrg.zzfj(str);
              }
              j += 1;
              k = i1;
            }
            j = i + k + m * 1;
          }
        }
        i = j;
        if (this.zzaWr != null)
        {
          i = j;
          if (this.zzaWr.length > 0)
          {
            i = 0;
            k = 0;
            while (i < this.zzaWr.length)
            {
              k += zzrg.zzkJ(this.zzaWr[i]);
              i += 1;
            }
            i = j + k + this.zzaWr.length * 1;
          }
        }
        j = i;
        if (this.zzaWs != null)
        {
          j = i;
          if (this.zzaWs.length > 0)
          {
            k = 0;
            j = i2;
            while (j < this.zzaWs.length)
            {
              k += zzrg.zzkJ(this.zzaWs[j]);
              j += 1;
            }
            j = i + k + this.zzaWs.length * 1;
          }
        }
        return j;
      }
    }
    
    public zza zzB(zzrf paramzzrf)
      throws IOException
    {
      for (;;)
      {
        int i = paramzzrf.zzBr();
        int j;
        Object localObject;
        int k;
        switch (i)
        {
        default: 
          if (zza(paramzzrf, i)) {}
          break;
        case 0: 
          return this;
        case 10: 
          j = zzrq.zzb(paramzzrf, 10);
          if (this.zzaWp == null) {}
          for (i = 0;; i = this.zzaWp.length)
          {
            localObject = new String[j + i];
            j = i;
            if (i != 0)
            {
              System.arraycopy(this.zzaWp, 0, localObject, 0, i);
              j = i;
            }
            while (j < localObject.length - 1)
            {
              localObject[j] = paramzzrf.readString();
              paramzzrf.zzBr();
              j += 1;
            }
          }
          localObject[j] = paramzzrf.readString();
          this.zzaWp = ((String[])localObject);
          break;
        case 18: 
          j = zzrq.zzb(paramzzrf, 18);
          if (this.zzaWq == null) {}
          for (i = 0;; i = this.zzaWq.length)
          {
            localObject = new String[j + i];
            j = i;
            if (i != 0)
            {
              System.arraycopy(this.zzaWq, 0, localObject, 0, i);
              j = i;
            }
            while (j < localObject.length - 1)
            {
              localObject[j] = paramzzrf.readString();
              paramzzrf.zzBr();
              j += 1;
            }
          }
          localObject[j] = paramzzrf.readString();
          this.zzaWq = ((String[])localObject);
          break;
        case 24: 
          j = zzrq.zzb(paramzzrf, 24);
          if (this.zzaWr == null) {}
          for (i = 0;; i = this.zzaWr.length)
          {
            localObject = new int[j + i];
            j = i;
            if (i != 0)
            {
              System.arraycopy(this.zzaWr, 0, localObject, 0, i);
              j = i;
            }
            while (j < localObject.length - 1)
            {
              localObject[j] = paramzzrf.zzBu();
              paramzzrf.zzBr();
              j += 1;
            }
          }
          localObject[j] = paramzzrf.zzBu();
          this.zzaWr = ((int[])localObject);
          break;
        case 26: 
          k = paramzzrf.zzkC(paramzzrf.zzBy());
          i = paramzzrf.getPosition();
          j = 0;
          while (paramzzrf.zzBD() > 0)
          {
            paramzzrf.zzBu();
            j += 1;
          }
          paramzzrf.zzkE(i);
          if (this.zzaWr == null) {}
          for (i = 0;; i = this.zzaWr.length)
          {
            localObject = new int[j + i];
            j = i;
            if (i != 0)
            {
              System.arraycopy(this.zzaWr, 0, localObject, 0, i);
              j = i;
            }
            while (j < localObject.length)
            {
              localObject[j] = paramzzrf.zzBu();
              j += 1;
            }
          }
          this.zzaWr = ((int[])localObject);
          paramzzrf.zzkD(k);
          break;
        case 32: 
          j = zzrq.zzb(paramzzrf, 32);
          if (this.zzaWs == null) {}
          for (i = 0;; i = this.zzaWs.length)
          {
            localObject = new int[j + i];
            j = i;
            if (i != 0)
            {
              System.arraycopy(this.zzaWs, 0, localObject, 0, i);
              j = i;
            }
            while (j < localObject.length - 1)
            {
              localObject[j] = paramzzrf.zzBu();
              paramzzrf.zzBr();
              j += 1;
            }
          }
          localObject[j] = paramzzrf.zzBu();
          this.zzaWs = ((int[])localObject);
          break;
        case 34: 
          k = paramzzrf.zzkC(paramzzrf.zzBy());
          i = paramzzrf.getPosition();
          j = 0;
          while (paramzzrf.zzBD() > 0)
          {
            paramzzrf.zzBu();
            j += 1;
          }
          paramzzrf.zzkE(i);
          if (this.zzaWs == null) {}
          for (i = 0;; i = this.zzaWs.length)
          {
            localObject = new int[j + i];
            j = i;
            if (i != 0)
            {
              System.arraycopy(this.zzaWs, 0, localObject, 0, i);
              j = i;
            }
            while (j < localObject.length)
            {
              localObject[j] = paramzzrf.zzBu();
              j += 1;
            }
          }
          this.zzaWs = ((int[])localObject);
          paramzzrf.zzkD(k);
        }
      }
    }
    
    public zza zzBW()
    {
      this.zzaWp = zzrq.zzaWm;
      this.zzaWq = zzrq.zzaWm;
      this.zzaWr = zzrq.zzaWh;
      this.zzaWs = zzrq.zzaWh;
      this.zzaVU = null;
      this.zzaWf = -1;
      return this;
    }
    
    public void zza(zzrg paramzzrg)
      throws IOException
    {
      int j = 0;
      int i;
      String str;
      if ((this.zzaWp != null) && (this.zzaWp.length > 0))
      {
        i = 0;
        while (i < this.zzaWp.length)
        {
          str = this.zzaWp[i];
          if (str != null) {
            paramzzrg.zzb(1, str);
          }
          i += 1;
        }
      }
      if ((this.zzaWq != null) && (this.zzaWq.length > 0))
      {
        i = 0;
        while (i < this.zzaWq.length)
        {
          str = this.zzaWq[i];
          if (str != null) {
            paramzzrg.zzb(2, str);
          }
          i += 1;
        }
      }
      if ((this.zzaWr != null) && (this.zzaWr.length > 0))
      {
        i = 0;
        while (i < this.zzaWr.length)
        {
          paramzzrg.zzy(3, this.zzaWr[i]);
          i += 1;
        }
      }
      if ((this.zzaWs != null) && (this.zzaWs.length > 0))
      {
        i = j;
        while (i < this.zzaWs.length)
        {
          paramzzrg.zzy(4, this.zzaWs[i]);
          i += 1;
        }
      }
      super.zza(paramzzrg);
    }
  }
  
  public static final class zzb
    extends zzrh<zzb>
  {
    public String version;
    public int zzaWt;
    public String zzaWu;
    
    public zzb()
    {
      zzBX();
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
          do
          {
            do
            {
              return bool1;
              bool1 = bool2;
            } while (!(paramObject instanceof zzb));
            paramObject = (zzb)paramObject;
            bool1 = bool2;
          } while (this.zzaWt != ((zzb)paramObject).zzaWt);
          if (this.zzaWu != null) {
            break;
          }
          bool1 = bool2;
        } while (((zzb)paramObject).zzaWu != null);
        if (this.version != null) {
          break label92;
        }
        bool1 = bool2;
      } while (((zzb)paramObject).version != null);
      label92:
      while (this.version.equals(((zzb)paramObject).version))
      {
        return zza((zzrh)paramObject);
        if (this.zzaWu.equals(((zzb)paramObject).zzaWu)) {
          break;
        }
        return false;
      }
      return false;
    }
    
    public int hashCode()
    {
      int j = 0;
      int k = this.zzaWt;
      int i;
      if (this.zzaWu == null)
      {
        i = 0;
        if (this.version != null) {
          break label58;
        }
      }
      for (;;)
      {
        return ((i + (k + 527) * 31) * 31 + j) * 31 + zzBI();
        i = this.zzaWu.hashCode();
        break;
        label58:
        j = this.version.hashCode();
      }
    }
    
    protected int zzB()
    {
      int j = super.zzB();
      int i = j;
      if (this.zzaWt != 0) {
        i = j + zzrg.zzA(1, this.zzaWt);
      }
      j = i;
      if (!this.zzaWu.equals("")) {
        j = i + zzrg.zzk(2, this.zzaWu);
      }
      i = j;
      if (!this.version.equals("")) {
        i = j + zzrg.zzk(3, this.version);
      }
      return i;
    }
    
    public zzb zzBX()
    {
      this.zzaWt = 0;
      this.zzaWu = "";
      this.version = "";
      this.zzaVU = null;
      this.zzaWf = -1;
      return this;
    }
    
    public zzb zzC(zzrf paramzzrf)
      throws IOException
    {
      for (;;)
      {
        int i = paramzzrf.zzBr();
        switch (i)
        {
        default: 
          if (zza(paramzzrf, i)) {}
          break;
        case 0: 
          return this;
        case 8: 
          i = paramzzrf.zzBu();
          switch (i)
          {
          default: 
            break;
          case 0: 
          case 1: 
          case 2: 
          case 3: 
          case 4: 
          case 5: 
          case 6: 
          case 7: 
          case 8: 
          case 9: 
          case 10: 
          case 11: 
          case 12: 
          case 13: 
          case 14: 
          case 15: 
          case 16: 
          case 17: 
          case 18: 
          case 19: 
          case 20: 
          case 21: 
          case 22: 
          case 23: 
          case 24: 
          case 25: 
          case 26: 
            this.zzaWt = i;
          }
          break;
        case 18: 
          this.zzaWu = paramzzrf.readString();
          break;
        case 26: 
          this.version = paramzzrf.readString();
        }
      }
    }
    
    public void zza(zzrg paramzzrg)
      throws IOException
    {
      if (this.zzaWt != 0) {
        paramzzrg.zzy(1, this.zzaWt);
      }
      if (!this.zzaWu.equals("")) {
        paramzzrg.zzb(2, this.zzaWu);
      }
      if (!this.version.equals("")) {
        paramzzrg.zzb(3, this.version);
      }
      super.zza(paramzzrg);
    }
  }
  
  public static final class zzc
    extends zzrh<zzc>
  {
    public byte[] zzaWv;
    public byte[][] zzaWw;
    public boolean zzaWx;
    
    public zzc()
    {
      zzBY();
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
          do
          {
            do
            {
              return bool1;
              bool1 = bool2;
            } while (!(paramObject instanceof zzc));
            paramObject = (zzc)paramObject;
            bool1 = bool2;
          } while (!Arrays.equals(this.zzaWv, ((zzc)paramObject).zzaWv));
          bool1 = bool2;
        } while (!zzrl.zza(this.zzaWw, ((zzc)paramObject).zzaWw));
        bool1 = bool2;
      } while (this.zzaWx != ((zzc)paramObject).zzaWx);
      return zza((zzrh)paramObject);
    }
    
    public int hashCode()
    {
      int j = Arrays.hashCode(this.zzaWv);
      int k = zzrl.zza(this.zzaWw);
      if (this.zzaWx) {}
      for (int i = 1231;; i = 1237) {
        return (i + ((j + 527) * 31 + k) * 31) * 31 + zzBI();
      }
    }
    
    protected int zzB()
    {
      int n = 0;
      int j = super.zzB();
      int i = j;
      if (!Arrays.equals(this.zzaWv, zzrq.zzaWo)) {
        i = j + zzrg.zzb(1, this.zzaWv);
      }
      j = i;
      if (this.zzaWw != null)
      {
        j = i;
        if (this.zzaWw.length > 0)
        {
          int k = 0;
          int m = 0;
          j = n;
          while (j < this.zzaWw.length)
          {
            byte[] arrayOfByte = this.zzaWw[j];
            int i1 = k;
            n = m;
            if (arrayOfByte != null)
            {
              n = m + 1;
              i1 = k + zzrg.zzC(arrayOfByte);
            }
            j += 1;
            k = i1;
            m = n;
          }
          j = i + k + m * 1;
        }
      }
      i = j;
      if (this.zzaWx) {
        i = j + zzrg.zzc(3, this.zzaWx);
      }
      return i;
    }
    
    public zzc zzBY()
    {
      this.zzaWv = zzrq.zzaWo;
      this.zzaWw = zzrq.zzaWn;
      this.zzaWx = false;
      this.zzaVU = null;
      this.zzaWf = -1;
      return this;
    }
    
    public zzc zzD(zzrf paramzzrf)
      throws IOException
    {
      for (;;)
      {
        int i = paramzzrf.zzBr();
        switch (i)
        {
        default: 
          if (zza(paramzzrf, i)) {}
          break;
        case 0: 
          return this;
        case 10: 
          this.zzaWv = paramzzrf.readBytes();
          break;
        case 18: 
          int j = zzrq.zzb(paramzzrf, 18);
          if (this.zzaWw == null) {}
          byte[][] arrayOfByte;
          for (i = 0;; i = this.zzaWw.length)
          {
            arrayOfByte = new byte[j + i][];
            j = i;
            if (i != 0)
            {
              System.arraycopy(this.zzaWw, 0, arrayOfByte, 0, i);
              j = i;
            }
            while (j < arrayOfByte.length - 1)
            {
              arrayOfByte[j] = paramzzrf.readBytes();
              paramzzrf.zzBr();
              j += 1;
            }
          }
          arrayOfByte[j] = paramzzrf.readBytes();
          this.zzaWw = arrayOfByte;
          break;
        case 24: 
          this.zzaWx = paramzzrf.zzBv();
        }
      }
    }
    
    public void zza(zzrg paramzzrg)
      throws IOException
    {
      if (!Arrays.equals(this.zzaWv, zzrq.zzaWo)) {
        paramzzrg.zza(1, this.zzaWv);
      }
      if ((this.zzaWw != null) && (this.zzaWw.length > 0))
      {
        int i = 0;
        while (i < this.zzaWw.length)
        {
          byte[] arrayOfByte = this.zzaWw[i];
          if (arrayOfByte != null) {
            paramzzrg.zza(2, arrayOfByte);
          }
          i += 1;
        }
      }
      if (this.zzaWx) {
        paramzzrg.zzb(3, this.zzaWx);
      }
      super.zza(paramzzrg);
    }
  }
  
  public static final class zzd
    extends zzrh<zzd>
  {
    public String tag;
    public int zzaWA;
    public int zzaWB;
    public boolean zzaWC;
    public zzrr.zze[] zzaWD;
    public zzrr.zzb zzaWE;
    public byte[] zzaWF;
    public byte[] zzaWG;
    public byte[] zzaWH;
    public zzrr.zza zzaWI;
    public String zzaWJ;
    public long zzaWK;
    public zzrr.zzc zzaWL;
    public byte[] zzaWM;
    public long zzaWy;
    public long zzaWz;
    
    public zzd()
    {
      zzBZ();
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool2 = false;
      boolean bool1;
      if (paramObject == this) {
        bool1 = true;
      }
      label69:
      label140:
      label204:
      label220:
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                do
                {
                  do
                  {
                    do
                    {
                      do
                      {
                        do
                        {
                          do
                          {
                            do
                            {
                              do
                              {
                                do
                                {
                                  do
                                  {
                                    do
                                    {
                                      return bool1;
                                      bool1 = bool2;
                                    } while (!(paramObject instanceof zzd));
                                    paramObject = (zzd)paramObject;
                                    bool1 = bool2;
                                  } while (this.zzaWy != ((zzd)paramObject).zzaWy);
                                  bool1 = bool2;
                                } while (this.zzaWz != ((zzd)paramObject).zzaWz);
                                if (this.tag != null) {
                                  break;
                                }
                                bool1 = bool2;
                              } while (((zzd)paramObject).tag != null);
                              bool1 = bool2;
                            } while (this.zzaWA != ((zzd)paramObject).zzaWA);
                            bool1 = bool2;
                          } while (this.zzaWB != ((zzd)paramObject).zzaWB);
                          bool1 = bool2;
                        } while (this.zzaWC != ((zzd)paramObject).zzaWC);
                        bool1 = bool2;
                      } while (!zzrl.equals(this.zzaWD, ((zzd)paramObject).zzaWD));
                      if (this.zzaWE != null) {
                        break label288;
                      }
                      bool1 = bool2;
                    } while (((zzd)paramObject).zzaWE != null);
                    bool1 = bool2;
                  } while (!Arrays.equals(this.zzaWF, ((zzd)paramObject).zzaWF));
                  bool1 = bool2;
                } while (!Arrays.equals(this.zzaWG, ((zzd)paramObject).zzaWG));
                bool1 = bool2;
              } while (!Arrays.equals(this.zzaWH, ((zzd)paramObject).zzaWH));
              if (this.zzaWI != null) {
                break label304;
              }
              bool1 = bool2;
            } while (((zzd)paramObject).zzaWI != null);
            if (this.zzaWJ != null) {
              break label320;
            }
            bool1 = bool2;
          } while (((zzd)paramObject).zzaWJ != null);
          bool1 = bool2;
        } while (this.zzaWK != ((zzd)paramObject).zzaWK);
        if (this.zzaWL != null) {
          break label336;
        }
        bool1 = bool2;
      } while (((zzd)paramObject).zzaWL != null);
      label288:
      label304:
      label320:
      label336:
      while (this.zzaWL.equals(((zzd)paramObject).zzaWL))
      {
        bool1 = bool2;
        if (!Arrays.equals(this.zzaWM, ((zzd)paramObject).zzaWM)) {
          break;
        }
        return zza((zzrh)paramObject);
        if (this.tag.equals(((zzd)paramObject).tag)) {
          break label69;
        }
        return false;
        if (this.zzaWE.equals(((zzd)paramObject).zzaWE)) {
          break label140;
        }
        return false;
        if (this.zzaWI.equals(((zzd)paramObject).zzaWI)) {
          break label204;
        }
        return false;
        if (this.zzaWJ.equals(((zzd)paramObject).zzaWJ)) {
          break label220;
        }
        return false;
      }
      return false;
    }
    
    public int hashCode()
    {
      int i1 = 0;
      int i2 = (int)(this.zzaWy ^ this.zzaWy >>> 32);
      int i3 = (int)(this.zzaWz ^ this.zzaWz >>> 32);
      int i;
      int i4;
      int i5;
      int j;
      label65:
      int i6;
      int k;
      label83:
      int i7;
      int i8;
      int i9;
      int m;
      label120:
      int n;
      label130:
      int i10;
      if (this.tag == null)
      {
        i = 0;
        i4 = this.zzaWA;
        i5 = this.zzaWB;
        if (!this.zzaWC) {
          break label270;
        }
        j = 1231;
        i6 = zzrl.hashCode(this.zzaWD);
        if (this.zzaWE != null) {
          break label277;
        }
        k = 0;
        i7 = Arrays.hashCode(this.zzaWF);
        i8 = Arrays.hashCode(this.zzaWG);
        i9 = Arrays.hashCode(this.zzaWH);
        if (this.zzaWI != null) {
          break label288;
        }
        m = 0;
        if (this.zzaWJ != null) {
          break label300;
        }
        n = 0;
        i10 = (int)(this.zzaWK ^ this.zzaWK >>> 32);
        if (this.zzaWL != null) {
          break label312;
        }
      }
      for (;;)
      {
        return ((((n + (m + ((((k + ((j + (((i + ((i2 + 527) * 31 + i3) * 31) * 31 + i4) * 31 + i5) * 31) * 31 + i6) * 31) * 31 + i7) * 31 + i8) * 31 + i9) * 31) * 31) * 31 + i10) * 31 + i1) * 31 + Arrays.hashCode(this.zzaWM)) * 31 + zzBI();
        i = this.tag.hashCode();
        break;
        label270:
        j = 1237;
        break label65;
        label277:
        k = this.zzaWE.hashCode();
        break label83;
        label288:
        m = this.zzaWI.hashCode();
        break label120;
        label300:
        n = this.zzaWJ.hashCode();
        break label130;
        label312:
        i1 = this.zzaWL.hashCode();
      }
    }
    
    protected int zzB()
    {
      int i = super.zzB();
      int j = i;
      if (this.zzaWy != 0L) {
        j = i + zzrg.zzd(1, this.zzaWy);
      }
      i = j;
      if (!this.tag.equals("")) {
        i = j + zzrg.zzk(2, this.tag);
      }
      j = i;
      if (this.zzaWD != null)
      {
        j = i;
        if (this.zzaWD.length > 0)
        {
          j = 0;
          while (j < this.zzaWD.length)
          {
            zzrr.zze localzze = this.zzaWD[j];
            int k = i;
            if (localzze != null) {
              k = i + zzrg.zzc(3, localzze);
            }
            j += 1;
            i = k;
          }
          j = i;
        }
      }
      i = j;
      if (!Arrays.equals(this.zzaWF, zzrq.zzaWo)) {
        i = j + zzrg.zzb(6, this.zzaWF);
      }
      j = i;
      if (this.zzaWI != null) {
        j = i + zzrg.zzc(7, this.zzaWI);
      }
      i = j;
      if (!Arrays.equals(this.zzaWG, zzrq.zzaWo)) {
        i = j + zzrg.zzb(8, this.zzaWG);
      }
      j = i;
      if (this.zzaWE != null) {
        j = i + zzrg.zzc(9, this.zzaWE);
      }
      i = j;
      if (this.zzaWC) {
        i = j + zzrg.zzc(10, this.zzaWC);
      }
      j = i;
      if (this.zzaWA != 0) {
        j = i + zzrg.zzA(11, this.zzaWA);
      }
      i = j;
      if (this.zzaWB != 0) {
        i = j + zzrg.zzA(12, this.zzaWB);
      }
      j = i;
      if (!Arrays.equals(this.zzaWH, zzrq.zzaWo)) {
        j = i + zzrg.zzb(13, this.zzaWH);
      }
      i = j;
      if (!this.zzaWJ.equals("")) {
        i = j + zzrg.zzk(14, this.zzaWJ);
      }
      j = i;
      if (this.zzaWK != 180000L) {
        j = i + zzrg.zze(15, this.zzaWK);
      }
      i = j;
      if (this.zzaWL != null) {
        i = j + zzrg.zzc(16, this.zzaWL);
      }
      j = i;
      if (this.zzaWz != 0L) {
        j = i + zzrg.zzd(17, this.zzaWz);
      }
      i = j;
      if (!Arrays.equals(this.zzaWM, zzrq.zzaWo)) {
        i = j + zzrg.zzb(18, this.zzaWM);
      }
      return i;
    }
    
    public zzd zzBZ()
    {
      this.zzaWy = 0L;
      this.zzaWz = 0L;
      this.tag = "";
      this.zzaWA = 0;
      this.zzaWB = 0;
      this.zzaWC = false;
      this.zzaWD = zzrr.zze.zzCa();
      this.zzaWE = null;
      this.zzaWF = zzrq.zzaWo;
      this.zzaWG = zzrq.zzaWo;
      this.zzaWH = zzrq.zzaWo;
      this.zzaWI = null;
      this.zzaWJ = "";
      this.zzaWK = 180000L;
      this.zzaWL = null;
      this.zzaWM = zzrq.zzaWo;
      this.zzaVU = null;
      this.zzaWf = -1;
      return this;
    }
    
    public zzd zzE(zzrf paramzzrf)
      throws IOException
    {
      for (;;)
      {
        int i = paramzzrf.zzBr();
        switch (i)
        {
        default: 
          if (zza(paramzzrf, i)) {}
          break;
        case 0: 
          return this;
        case 8: 
          this.zzaWy = paramzzrf.zzBt();
          break;
        case 18: 
          this.tag = paramzzrf.readString();
          break;
        case 26: 
          int j = zzrq.zzb(paramzzrf, 26);
          if (this.zzaWD == null) {}
          zzrr.zze[] arrayOfzze;
          for (i = 0;; i = this.zzaWD.length)
          {
            arrayOfzze = new zzrr.zze[j + i];
            j = i;
            if (i != 0)
            {
              System.arraycopy(this.zzaWD, 0, arrayOfzze, 0, i);
              j = i;
            }
            while (j < arrayOfzze.length - 1)
            {
              arrayOfzze[j] = new zzrr.zze();
              paramzzrf.zza(arrayOfzze[j]);
              paramzzrf.zzBr();
              j += 1;
            }
          }
          arrayOfzze[j] = new zzrr.zze();
          paramzzrf.zza(arrayOfzze[j]);
          this.zzaWD = arrayOfzze;
          break;
        case 50: 
          this.zzaWF = paramzzrf.readBytes();
          break;
        case 58: 
          if (this.zzaWI == null) {
            this.zzaWI = new zzrr.zza();
          }
          paramzzrf.zza(this.zzaWI);
          break;
        case 66: 
          this.zzaWG = paramzzrf.readBytes();
          break;
        case 74: 
          if (this.zzaWE == null) {
            this.zzaWE = new zzrr.zzb();
          }
          paramzzrf.zza(this.zzaWE);
          break;
        case 80: 
          this.zzaWC = paramzzrf.zzBv();
          break;
        case 88: 
          this.zzaWA = paramzzrf.zzBu();
          break;
        case 96: 
          this.zzaWB = paramzzrf.zzBu();
          break;
        case 106: 
          this.zzaWH = paramzzrf.readBytes();
          break;
        case 114: 
          this.zzaWJ = paramzzrf.readString();
          break;
        case 120: 
          this.zzaWK = paramzzrf.zzBx();
          break;
        case 130: 
          if (this.zzaWL == null) {
            this.zzaWL = new zzrr.zzc();
          }
          paramzzrf.zza(this.zzaWL);
          break;
        case 136: 
          this.zzaWz = paramzzrf.zzBt();
          break;
        case 146: 
          this.zzaWM = paramzzrf.readBytes();
        }
      }
    }
    
    public void zza(zzrg paramzzrg)
      throws IOException
    {
      if (this.zzaWy != 0L) {
        paramzzrg.zzb(1, this.zzaWy);
      }
      if (!this.tag.equals("")) {
        paramzzrg.zzb(2, this.tag);
      }
      if ((this.zzaWD != null) && (this.zzaWD.length > 0))
      {
        int i = 0;
        while (i < this.zzaWD.length)
        {
          zzrr.zze localzze = this.zzaWD[i];
          if (localzze != null) {
            paramzzrg.zza(3, localzze);
          }
          i += 1;
        }
      }
      if (!Arrays.equals(this.zzaWF, zzrq.zzaWo)) {
        paramzzrg.zza(6, this.zzaWF);
      }
      if (this.zzaWI != null) {
        paramzzrg.zza(7, this.zzaWI);
      }
      if (!Arrays.equals(this.zzaWG, zzrq.zzaWo)) {
        paramzzrg.zza(8, this.zzaWG);
      }
      if (this.zzaWE != null) {
        paramzzrg.zza(9, this.zzaWE);
      }
      if (this.zzaWC) {
        paramzzrg.zzb(10, this.zzaWC);
      }
      if (this.zzaWA != 0) {
        paramzzrg.zzy(11, this.zzaWA);
      }
      if (this.zzaWB != 0) {
        paramzzrg.zzy(12, this.zzaWB);
      }
      if (!Arrays.equals(this.zzaWH, zzrq.zzaWo)) {
        paramzzrg.zza(13, this.zzaWH);
      }
      if (!this.zzaWJ.equals("")) {
        paramzzrg.zzb(14, this.zzaWJ);
      }
      if (this.zzaWK != 180000L) {
        paramzzrg.zzc(15, this.zzaWK);
      }
      if (this.zzaWL != null) {
        paramzzrg.zza(16, this.zzaWL);
      }
      if (this.zzaWz != 0L) {
        paramzzrg.zzb(17, this.zzaWz);
      }
      if (!Arrays.equals(this.zzaWM, zzrq.zzaWo)) {
        paramzzrg.zza(18, this.zzaWM);
      }
      super.zza(paramzzrg);
    }
  }
  
  public static final class zze
    extends zzrh<zze>
  {
    private static volatile zze[] zzaWN;
    public String value;
    public String zzaC;
    
    public zze()
    {
      zzCb();
    }
    
    public static zze[] zzCa()
    {
      if (zzaWN == null) {}
      synchronized (zzrl.zzaWe)
      {
        if (zzaWN == null) {
          zzaWN = new zze[0];
        }
        return zzaWN;
      }
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
          do
          {
            return bool1;
            bool1 = bool2;
          } while (!(paramObject instanceof zze));
          paramObject = (zze)paramObject;
          if (this.zzaC != null) {
            break;
          }
          bool1 = bool2;
        } while (((zze)paramObject).zzaC != null);
        if (this.value != null) {
          break label79;
        }
        bool1 = bool2;
      } while (((zze)paramObject).value != null);
      label79:
      while (this.value.equals(((zze)paramObject).value))
      {
        return zza((zzrh)paramObject);
        if (this.zzaC.equals(((zze)paramObject).zzaC)) {
          break;
        }
        return false;
      }
      return false;
    }
    
    public int hashCode()
    {
      int j = 0;
      int i;
      if (this.zzaC == null)
      {
        i = 0;
        if (this.value != null) {
          break label48;
        }
      }
      for (;;)
      {
        return ((i + 527) * 31 + j) * 31 + zzBI();
        i = this.zzaC.hashCode();
        break;
        label48:
        j = this.value.hashCode();
      }
    }
    
    protected int zzB()
    {
      int j = super.zzB();
      int i = j;
      if (!this.zzaC.equals("")) {
        i = j + zzrg.zzk(1, this.zzaC);
      }
      j = i;
      if (!this.value.equals("")) {
        j = i + zzrg.zzk(2, this.value);
      }
      return j;
    }
    
    public zze zzCb()
    {
      this.zzaC = "";
      this.value = "";
      this.zzaVU = null;
      this.zzaWf = -1;
      return this;
    }
    
    public zze zzF(zzrf paramzzrf)
      throws IOException
    {
      for (;;)
      {
        int i = paramzzrf.zzBr();
        switch (i)
        {
        default: 
          if (zza(paramzzrf, i)) {}
          break;
        case 0: 
          return this;
        case 10: 
          this.zzaC = paramzzrf.readString();
          break;
        case 18: 
          this.value = paramzzrf.readString();
        }
      }
    }
    
    public void zza(zzrg paramzzrg)
      throws IOException
    {
      if (!this.zzaC.equals("")) {
        paramzzrg.zzb(1, this.zzaC);
      }
      if (!this.value.equals("")) {
        paramzzrg.zzb(2, this.value);
      }
      super.zza(paramzzrg);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzrr.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */