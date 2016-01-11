package com.google.android.gms.internal;

import java.io.IOException;

public abstract interface zzag
{
  public static final class zza
    extends zzrh<zza>
  {
    private static volatile zza[] zziQ;
    public int type;
    public String zziR;
    public zza[] zziS;
    public zza[] zziT;
    public zza[] zziU;
    public String zziV;
    public String zziW;
    public long zziX;
    public boolean zziY;
    public zza[] zziZ;
    public int[] zzja;
    public boolean zzjb;
    
    public zza()
    {
      zzR();
    }
    
    public static zza[] zzQ()
    {
      if (zziQ == null) {}
      synchronized (zzrl.zzaWe)
      {
        if (zziQ == null) {
          zziQ = new zza[0];
        }
        return zziQ;
      }
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool2 = false;
      boolean bool1;
      if (paramObject == this) {
        bool1 = true;
      }
      label54:
      label118:
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
                    } while (!(paramObject instanceof zza));
                    paramObject = (zza)paramObject;
                    bool1 = bool2;
                  } while (this.type != ((zza)paramObject).type);
                  if (this.zziR != null) {
                    break;
                  }
                  bool1 = bool2;
                } while (((zza)paramObject).zziR != null);
                bool1 = bool2;
              } while (!zzrl.equals(this.zziS, ((zza)paramObject).zziS));
              bool1 = bool2;
            } while (!zzrl.equals(this.zziT, ((zza)paramObject).zziT));
            bool1 = bool2;
          } while (!zzrl.equals(this.zziU, ((zza)paramObject).zziU));
          if (this.zziV != null) {
            break label228;
          }
          bool1 = bool2;
        } while (((zza)paramObject).zziV != null);
        if (this.zziW != null) {
          break label244;
        }
        bool1 = bool2;
      } while (((zza)paramObject).zziW != null);
      label228:
      label244:
      while (this.zziW.equals(((zza)paramObject).zziW))
      {
        bool1 = bool2;
        if (this.zziX != ((zza)paramObject).zziX) {
          break;
        }
        bool1 = bool2;
        if (this.zziY != ((zza)paramObject).zziY) {
          break;
        }
        bool1 = bool2;
        if (!zzrl.equals(this.zziZ, ((zza)paramObject).zziZ)) {
          break;
        }
        bool1 = bool2;
        if (!zzrl.equals(this.zzja, ((zza)paramObject).zzja)) {
          break;
        }
        bool1 = bool2;
        if (this.zzjb != ((zza)paramObject).zzjb) {
          break;
        }
        return zza((zzrh)paramObject);
        if (this.zziR.equals(((zza)paramObject).zziR)) {
          break label54;
        }
        return false;
        if (this.zziV.equals(((zza)paramObject).zziV)) {
          break label118;
        }
        return false;
      }
      return false;
    }
    
    public int hashCode()
    {
      int n = 1231;
      int k = 0;
      int i1 = this.type;
      int i;
      int i2;
      int i3;
      int i4;
      int j;
      label58:
      label65:
      int i5;
      int m;
      label92:
      int i6;
      int i7;
      if (this.zziR == null)
      {
        i = 0;
        i2 = zzrl.hashCode(this.zziS);
        i3 = zzrl.hashCode(this.zziT);
        i4 = zzrl.hashCode(this.zziU);
        if (this.zziV != null) {
          break label206;
        }
        j = 0;
        if (this.zziW != null) {
          break label217;
        }
        i5 = (int)(this.zziX ^ this.zziX >>> 32);
        if (!this.zziY) {
          break label228;
        }
        m = 1231;
        i6 = zzrl.hashCode(this.zziZ);
        i7 = zzrl.hashCode(this.zzja);
        if (!this.zzjb) {
          break label236;
        }
      }
      for (;;)
      {
        return ((((m + (((j + ((((i + (i1 + 527) * 31) * 31 + i2) * 31 + i3) * 31 + i4) * 31) * 31 + k) * 31 + i5) * 31) * 31 + i6) * 31 + i7) * 31 + n) * 31 + zzBI();
        i = this.zziR.hashCode();
        break;
        label206:
        j = this.zziV.hashCode();
        break label58;
        label217:
        k = this.zziW.hashCode();
        break label65;
        label228:
        m = 1237;
        break label92;
        label236:
        n = 1237;
      }
    }
    
    protected int zzB()
    {
      int m = 0;
      int j = super.zzB() + zzrg.zzA(1, this.type);
      int i = j;
      if (!this.zziR.equals("")) {
        i = j + zzrg.zzk(2, this.zziR);
      }
      j = i;
      zza localzza;
      int k;
      if (this.zziS != null)
      {
        j = i;
        if (this.zziS.length > 0)
        {
          j = 0;
          while (j < this.zziS.length)
          {
            localzza = this.zziS[j];
            k = i;
            if (localzza != null) {
              k = i + zzrg.zzc(3, localzza);
            }
            j += 1;
            i = k;
          }
          j = i;
        }
      }
      i = j;
      if (this.zziT != null)
      {
        i = j;
        if (this.zziT.length > 0)
        {
          i = j;
          j = 0;
          while (j < this.zziT.length)
          {
            localzza = this.zziT[j];
            k = i;
            if (localzza != null) {
              k = i + zzrg.zzc(4, localzza);
            }
            j += 1;
            i = k;
          }
        }
      }
      j = i;
      if (this.zziU != null)
      {
        j = i;
        if (this.zziU.length > 0)
        {
          j = 0;
          while (j < this.zziU.length)
          {
            localzza = this.zziU[j];
            k = i;
            if (localzza != null) {
              k = i + zzrg.zzc(5, localzza);
            }
            j += 1;
            i = k;
          }
          j = i;
        }
      }
      i = j;
      if (!this.zziV.equals("")) {
        i = j + zzrg.zzk(6, this.zziV);
      }
      j = i;
      if (!this.zziW.equals("")) {
        j = i + zzrg.zzk(7, this.zziW);
      }
      i = j;
      if (this.zziX != 0L) {
        i = j + zzrg.zzd(8, this.zziX);
      }
      j = i;
      if (this.zzjb) {
        j = i + zzrg.zzc(9, this.zzjb);
      }
      i = j;
      if (this.zzja != null)
      {
        i = j;
        if (this.zzja.length > 0)
        {
          i = 0;
          k = 0;
          while (i < this.zzja.length)
          {
            k += zzrg.zzkJ(this.zzja[i]);
            i += 1;
          }
          i = j + k + this.zzja.length * 1;
        }
      }
      j = i;
      if (this.zziZ != null)
      {
        j = i;
        if (this.zziZ.length > 0)
        {
          k = m;
          for (;;)
          {
            j = i;
            if (k >= this.zziZ.length) {
              break;
            }
            localzza = this.zziZ[k];
            j = i;
            if (localzza != null) {
              j = i + zzrg.zzc(11, localzza);
            }
            k += 1;
            i = j;
          }
        }
      }
      i = j;
      if (this.zziY) {
        i = j + zzrg.zzc(12, this.zziY);
      }
      return i;
    }
    
    public zza zzR()
    {
      this.type = 1;
      this.zziR = "";
      this.zziS = zzQ();
      this.zziT = zzQ();
      this.zziU = zzQ();
      this.zziV = "";
      this.zziW = "";
      this.zziX = 0L;
      this.zziY = false;
      this.zziZ = zzQ();
      this.zzja = zzrq.zzaWh;
      this.zzjb = false;
      this.zzaVU = null;
      this.zzaWf = -1;
      return this;
    }
    
    public void zza(zzrg paramzzrg)
      throws IOException
    {
      int j = 0;
      paramzzrg.zzy(1, this.type);
      if (!this.zziR.equals("")) {
        paramzzrg.zzb(2, this.zziR);
      }
      int i;
      zza localzza;
      if ((this.zziS != null) && (this.zziS.length > 0))
      {
        i = 0;
        while (i < this.zziS.length)
        {
          localzza = this.zziS[i];
          if (localzza != null) {
            paramzzrg.zza(3, localzza);
          }
          i += 1;
        }
      }
      if ((this.zziT != null) && (this.zziT.length > 0))
      {
        i = 0;
        while (i < this.zziT.length)
        {
          localzza = this.zziT[i];
          if (localzza != null) {
            paramzzrg.zza(4, localzza);
          }
          i += 1;
        }
      }
      if ((this.zziU != null) && (this.zziU.length > 0))
      {
        i = 0;
        while (i < this.zziU.length)
        {
          localzza = this.zziU[i];
          if (localzza != null) {
            paramzzrg.zza(5, localzza);
          }
          i += 1;
        }
      }
      if (!this.zziV.equals("")) {
        paramzzrg.zzb(6, this.zziV);
      }
      if (!this.zziW.equals("")) {
        paramzzrg.zzb(7, this.zziW);
      }
      if (this.zziX != 0L) {
        paramzzrg.zzb(8, this.zziX);
      }
      if (this.zzjb) {
        paramzzrg.zzb(9, this.zzjb);
      }
      if ((this.zzja != null) && (this.zzja.length > 0))
      {
        i = 0;
        while (i < this.zzja.length)
        {
          paramzzrg.zzy(10, this.zzja[i]);
          i += 1;
        }
      }
      if ((this.zziZ != null) && (this.zziZ.length > 0))
      {
        i = j;
        while (i < this.zziZ.length)
        {
          localzza = this.zziZ[i];
          if (localzza != null) {
            paramzzrg.zza(11, localzza);
          }
          i += 1;
        }
      }
      if (this.zziY) {
        paramzzrg.zzb(12, this.zziY);
      }
      super.zza(paramzzrg);
    }
    
    public zza zzl(zzrf paramzzrf)
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
        case 8: 
          i = paramzzrf.zzBu();
          switch (i)
          {
          default: 
            break;
          case 1: 
          case 2: 
          case 3: 
          case 4: 
          case 5: 
          case 6: 
          case 7: 
          case 8: 
            this.type = i;
          }
          break;
        case 18: 
          this.zziR = paramzzrf.readString();
          break;
        case 26: 
          j = zzrq.zzb(paramzzrf, 26);
          if (this.zziS == null) {}
          for (i = 0;; i = this.zziS.length)
          {
            localObject = new zza[j + i];
            j = i;
            if (i != 0)
            {
              System.arraycopy(this.zziS, 0, localObject, 0, i);
              j = i;
            }
            while (j < localObject.length - 1)
            {
              localObject[j] = new zza();
              paramzzrf.zza(localObject[j]);
              paramzzrf.zzBr();
              j += 1;
            }
          }
          localObject[j] = new zza();
          paramzzrf.zza(localObject[j]);
          this.zziS = ((zza[])localObject);
          break;
        case 34: 
          j = zzrq.zzb(paramzzrf, 34);
          if (this.zziT == null) {}
          for (i = 0;; i = this.zziT.length)
          {
            localObject = new zza[j + i];
            j = i;
            if (i != 0)
            {
              System.arraycopy(this.zziT, 0, localObject, 0, i);
              j = i;
            }
            while (j < localObject.length - 1)
            {
              localObject[j] = new zza();
              paramzzrf.zza(localObject[j]);
              paramzzrf.zzBr();
              j += 1;
            }
          }
          localObject[j] = new zza();
          paramzzrf.zza(localObject[j]);
          this.zziT = ((zza[])localObject);
          break;
        case 42: 
          j = zzrq.zzb(paramzzrf, 42);
          if (this.zziU == null) {}
          for (i = 0;; i = this.zziU.length)
          {
            localObject = new zza[j + i];
            j = i;
            if (i != 0)
            {
              System.arraycopy(this.zziU, 0, localObject, 0, i);
              j = i;
            }
            while (j < localObject.length - 1)
            {
              localObject[j] = new zza();
              paramzzrf.zza(localObject[j]);
              paramzzrf.zzBr();
              j += 1;
            }
          }
          localObject[j] = new zza();
          paramzzrf.zza(localObject[j]);
          this.zziU = ((zza[])localObject);
          break;
        case 50: 
          this.zziV = paramzzrf.readString();
          break;
        case 58: 
          this.zziW = paramzzrf.readString();
          break;
        case 64: 
          this.zziX = paramzzrf.zzBt();
          break;
        case 72: 
          this.zzjb = paramzzrf.zzBv();
          break;
        case 80: 
          int m = zzrq.zzb(paramzzrf, 80);
          localObject = new int[m];
          j = 0;
          i = 0;
          if (j < m)
          {
            if (j != 0) {
              paramzzrf.zzBr();
            }
            int n = paramzzrf.zzBu();
            switch (n)
            {
            }
            for (;;)
            {
              j += 1;
              break;
              k = i + 1;
              localObject[i] = n;
              i = k;
            }
          }
          if (i != 0)
          {
            if (this.zzja == null) {}
            for (j = 0;; j = this.zzja.length)
            {
              if ((j != 0) || (i != localObject.length)) {
                break label810;
              }
              this.zzja = ((int[])localObject);
              break;
            }
            int[] arrayOfInt = new int[j + i];
            if (j != 0) {
              System.arraycopy(this.zzja, 0, arrayOfInt, 0, j);
            }
            System.arraycopy(localObject, 0, arrayOfInt, j, i);
            this.zzja = arrayOfInt;
          }
          break;
        case 82: 
          k = paramzzrf.zzkC(paramzzrf.zzBy());
          i = paramzzrf.getPosition();
          j = 0;
          while (paramzzrf.zzBD() > 0) {
            switch (paramzzrf.zzBu())
            {
            default: 
              break;
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
              j += 1;
            }
          }
          if (j != 0)
          {
            paramzzrf.zzkE(i);
            if (this.zzja == null) {}
            for (i = 0;; i = this.zzja.length)
            {
              localObject = new int[j + i];
              j = i;
              if (i != 0)
              {
                System.arraycopy(this.zzja, 0, localObject, 0, i);
                j = i;
              }
              while (paramzzrf.zzBD() > 0)
              {
                i = paramzzrf.zzBu();
                switch (i)
                {
                default: 
                  break;
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
                  localObject[j] = i;
                  j += 1;
                }
              }
            }
            this.zzja = ((int[])localObject);
          }
          paramzzrf.zzkD(k);
          break;
        case 90: 
          j = zzrq.zzb(paramzzrf, 90);
          if (this.zziZ == null) {}
          for (i = 0;; i = this.zziZ.length)
          {
            localObject = new zza[j + i];
            j = i;
            if (i != 0)
            {
              System.arraycopy(this.zziZ, 0, localObject, 0, i);
              j = i;
            }
            while (j < localObject.length - 1)
            {
              localObject[j] = new zza();
              paramzzrf.zza(localObject[j]);
              paramzzrf.zzBr();
              j += 1;
            }
          }
          localObject[j] = new zza();
          paramzzrf.zza(localObject[j]);
          this.zziZ = ((zza[])localObject);
          break;
        case 96: 
          label810:
          this.zziY = paramzzrf.zzBv();
        }
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */