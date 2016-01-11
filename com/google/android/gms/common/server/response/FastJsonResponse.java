package com.google.android.gms.common.server.response;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.server.converter.ConverterWrapper;
import com.google.android.gms.internal.zzky;
import com.google.android.gms.internal.zzlh;
import com.google.android.gms.internal.zzli;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class FastJsonResponse
{
  private void zza(StringBuilder paramStringBuilder, Field paramField, Object paramObject)
  {
    if (paramField.zzok() == 11)
    {
      paramStringBuilder.append(((FastJsonResponse)paramField.zzou().cast(paramObject)).toString());
      return;
    }
    if (paramField.zzok() == 7)
    {
      paramStringBuilder.append("\"");
      paramStringBuilder.append(zzlh.zzcr((String)paramObject));
      paramStringBuilder.append("\"");
      return;
    }
    paramStringBuilder.append(paramObject);
  }
  
  private void zza(StringBuilder paramStringBuilder, Field paramField, ArrayList<Object> paramArrayList)
  {
    paramStringBuilder.append("[");
    int i = 0;
    int j = paramArrayList.size();
    while (i < j)
    {
      if (i > 0) {
        paramStringBuilder.append(",");
      }
      Object localObject = paramArrayList.get(i);
      if (localObject != null) {
        zza(paramStringBuilder, paramField, localObject);
      }
      i += 1;
    }
    paramStringBuilder.append("]");
  }
  
  public String toString()
  {
    Map localMap = zzom();
    StringBuilder localStringBuilder = new StringBuilder(100);
    Iterator localIterator = localMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Field localField = (Field)localMap.get(str);
      if (zza(localField))
      {
        Object localObject = zza(localField, zzb(localField));
        if (localStringBuilder.length() == 0) {
          localStringBuilder.append("{");
        }
        for (;;)
        {
          localStringBuilder.append("\"").append(str).append("\":");
          if (localObject != null) {
            break label139;
          }
          localStringBuilder.append("null");
          break;
          localStringBuilder.append(",");
        }
        label139:
        switch (localField.zzol())
        {
        default: 
          if (localField.zzoq()) {
            zza(localStringBuilder, localField, (ArrayList)localObject);
          }
          break;
        case 8: 
          localStringBuilder.append("\"").append(zzky.zzi((byte[])localObject)).append("\"");
          break;
        case 9: 
          localStringBuilder.append("\"").append(zzky.zzj((byte[])localObject)).append("\"");
          break;
        case 10: 
          zzli.zza(localStringBuilder, (HashMap)localObject);
          continue;
          zza(localStringBuilder, localField, localObject);
        }
      }
    }
    if (localStringBuilder.length() > 0) {
      localStringBuilder.append("}");
    }
    for (;;)
    {
      return localStringBuilder.toString();
      localStringBuilder.append("{}");
    }
  }
  
  protected <O, I> I zza(Field<I, O> paramField, Object paramObject)
  {
    Object localObject = paramObject;
    if (Field.zzc(paramField) != null) {
      localObject = paramField.convertBack(paramObject);
    }
    return (I)localObject;
  }
  
  protected boolean zza(Field paramField)
  {
    if (paramField.zzol() == 11)
    {
      if (paramField.zzor()) {
        return zzcn(paramField.zzos());
      }
      return zzcm(paramField.zzos());
    }
    return zzcl(paramField.zzos());
  }
  
  protected Object zzb(Field paramField)
  {
    String str = paramField.zzos();
    if (paramField.zzou() != null)
    {
      boolean bool;
      if (zzck(paramField.zzos()) == null)
      {
        bool = true;
        zzu.zza(bool, "Concrete field shouldn't be value object: %s", new Object[] { paramField.zzos() });
        if (!paramField.zzor()) {
          break label71;
        }
      }
      label71:
      for (paramField = zzoo();; paramField = zzon())
      {
        if (paramField == null) {
          break label79;
        }
        return paramField.get(str);
        bool = false;
        break;
      }
      try
      {
        label79:
        paramField = "get" + Character.toUpperCase(str.charAt(0)) + str.substring(1);
        paramField = getClass().getMethod(paramField, new Class[0]).invoke(this, new Object[0]);
        return paramField;
      }
      catch (Exception paramField)
      {
        throw new RuntimeException(paramField);
      }
    }
    return zzck(paramField.zzos());
  }
  
  protected abstract Object zzck(String paramString);
  
  protected abstract boolean zzcl(String paramString);
  
  protected boolean zzcm(String paramString)
  {
    throw new UnsupportedOperationException("Concrete types not supported");
  }
  
  protected boolean zzcn(String paramString)
  {
    throw new UnsupportedOperationException("Concrete type arrays not supported");
  }
  
  public abstract Map<String, Field<?, ?>> zzom();
  
  public HashMap<String, Object> zzon()
  {
    return null;
  }
  
  public HashMap<String, Object> zzoo()
  {
    return null;
  }
  
  public static class Field<I, O>
    implements SafeParcelable
  {
    public static final zza CREATOR = new zza();
    private final int zzCY;
    protected final int zzabG;
    protected final boolean zzabH;
    protected final int zzabI;
    protected final boolean zzabJ;
    protected final String zzabK;
    protected final int zzabL;
    protected final Class<? extends FastJsonResponse> zzabM;
    protected final String zzabN;
    private FieldMappingDictionary zzabO;
    private FastJsonResponse.zza<I, O> zzabP;
    
    Field(int paramInt1, int paramInt2, boolean paramBoolean1, int paramInt3, boolean paramBoolean2, String paramString1, int paramInt4, String paramString2, ConverterWrapper paramConverterWrapper)
    {
      this.zzCY = paramInt1;
      this.zzabG = paramInt2;
      this.zzabH = paramBoolean1;
      this.zzabI = paramInt3;
      this.zzabJ = paramBoolean2;
      this.zzabK = paramString1;
      this.zzabL = paramInt4;
      if (paramString2 == null) {
        this.zzabM = null;
      }
      for (this.zzabN = null; paramConverterWrapper == null; this.zzabN = paramString2)
      {
        this.zzabP = null;
        return;
        this.zzabM = SafeParcelResponse.class;
      }
      this.zzabP = paramConverterWrapper.zzoi();
    }
    
    protected Field(int paramInt1, boolean paramBoolean1, int paramInt2, boolean paramBoolean2, String paramString, int paramInt3, Class<? extends FastJsonResponse> paramClass, FastJsonResponse.zza<I, O> paramzza)
    {
      this.zzCY = 1;
      this.zzabG = paramInt1;
      this.zzabH = paramBoolean1;
      this.zzabI = paramInt2;
      this.zzabJ = paramBoolean2;
      this.zzabK = paramString;
      this.zzabL = paramInt3;
      this.zzabM = paramClass;
      if (paramClass == null) {}
      for (this.zzabN = null;; this.zzabN = paramClass.getCanonicalName())
      {
        this.zzabP = paramzza;
        return;
      }
    }
    
    public static Field zza(String paramString, int paramInt, FastJsonResponse.zza<?, ?> paramzza, boolean paramBoolean)
    {
      return new Field(paramzza.zzok(), paramBoolean, paramzza.zzol(), false, paramString, paramInt, null, paramzza);
    }
    
    public static <T extends FastJsonResponse> Field<T, T> zza(String paramString, int paramInt, Class<T> paramClass)
    {
      return new Field(11, false, 11, false, paramString, paramInt, paramClass, null);
    }
    
    public static <T extends FastJsonResponse> Field<ArrayList<T>, ArrayList<T>> zzb(String paramString, int paramInt, Class<T> paramClass)
    {
      return new Field(11, true, 11, true, paramString, paramInt, paramClass, null);
    }
    
    public static Field<Integer, Integer> zzi(String paramString, int paramInt)
    {
      return new Field(0, false, 0, false, paramString, paramInt, null, null);
    }
    
    public static Field<Double, Double> zzj(String paramString, int paramInt)
    {
      return new Field(4, false, 4, false, paramString, paramInt, null, null);
    }
    
    public static Field<Boolean, Boolean> zzk(String paramString, int paramInt)
    {
      return new Field(6, false, 6, false, paramString, paramInt, null, null);
    }
    
    public static Field<String, String> zzl(String paramString, int paramInt)
    {
      return new Field(7, false, 7, false, paramString, paramInt, null, null);
    }
    
    public static Field<ArrayList<String>, ArrayList<String>> zzm(String paramString, int paramInt)
    {
      return new Field(7, true, 7, true, paramString, paramInt, null, null);
    }
    
    public I convertBack(O paramO)
    {
      return (I)this.zzabP.convertBack(paramO);
    }
    
    public int describeContents()
    {
      zza localzza = CREATOR;
      return 0;
    }
    
    public int getVersionCode()
    {
      return this.zzCY;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder1 = new StringBuilder();
      localStringBuilder1.append("Field\n");
      localStringBuilder1.append("            versionCode=").append(this.zzCY).append('\n');
      localStringBuilder1.append("                 typeIn=").append(this.zzabG).append('\n');
      localStringBuilder1.append("            typeInArray=").append(this.zzabH).append('\n');
      localStringBuilder1.append("                typeOut=").append(this.zzabI).append('\n');
      localStringBuilder1.append("           typeOutArray=").append(this.zzabJ).append('\n');
      localStringBuilder1.append("        outputFieldName=").append(this.zzabK).append('\n');
      localStringBuilder1.append("      safeParcelFieldId=").append(this.zzabL).append('\n');
      localStringBuilder1.append("       concreteTypeName=").append(zzov()).append('\n');
      if (zzou() != null) {
        localStringBuilder1.append("     concreteType.class=").append(zzou().getCanonicalName()).append('\n');
      }
      StringBuilder localStringBuilder2 = localStringBuilder1.append("          converterName=");
      if (this.zzabP == null) {}
      for (String str = "null";; str = this.zzabP.getClass().getCanonicalName())
      {
        localStringBuilder2.append(str).append('\n');
        return localStringBuilder1.toString();
      }
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      zza localzza = CREATOR;
      zza.zza(this, paramParcel, paramInt);
    }
    
    public void zza(FieldMappingDictionary paramFieldMappingDictionary)
    {
      this.zzabO = paramFieldMappingDictionary;
    }
    
    public int zzok()
    {
      return this.zzabG;
    }
    
    public int zzol()
    {
      return this.zzabI;
    }
    
    public Field<I, O> zzop()
    {
      return new Field(this.zzCY, this.zzabG, this.zzabH, this.zzabI, this.zzabJ, this.zzabK, this.zzabL, this.zzabN, zzox());
    }
    
    public boolean zzoq()
    {
      return this.zzabH;
    }
    
    public boolean zzor()
    {
      return this.zzabJ;
    }
    
    public String zzos()
    {
      return this.zzabK;
    }
    
    public int zzot()
    {
      return this.zzabL;
    }
    
    public Class<? extends FastJsonResponse> zzou()
    {
      return this.zzabM;
    }
    
    String zzov()
    {
      if (this.zzabN == null) {
        return null;
      }
      return this.zzabN;
    }
    
    public boolean zzow()
    {
      return this.zzabP != null;
    }
    
    ConverterWrapper zzox()
    {
      if (this.zzabP == null) {
        return null;
      }
      return ConverterWrapper.zza(this.zzabP);
    }
    
    public Map<String, Field<?, ?>> zzoy()
    {
      zzu.zzu(this.zzabN);
      zzu.zzu(this.zzabO);
      return this.zzabO.zzco(this.zzabN);
    }
  }
  
  public static abstract interface zza<I, O>
  {
    public abstract I convertBack(O paramO);
    
    public abstract int zzok();
    
    public abstract int zzol();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/server/response/FastJsonResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */