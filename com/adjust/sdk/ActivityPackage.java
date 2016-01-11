package com.adjust.sdk;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class ActivityPackage
  implements Serializable
{
  private static final ObjectStreamField[] serialPersistentFields = { new ObjectStreamField("path", String.class), new ObjectStreamField("clientSdk", String.class), new ObjectStreamField("parameters", Map.class), new ObjectStreamField("activityKind", ActivityKind.class), new ObjectStreamField("suffix", String.class) };
  private static final long serialVersionUID = -35935556512024097L;
  private ActivityKind activityKind = ActivityKind.UNKNOWN;
  private String clientSdk;
  private transient int hashCode;
  private Map<String, String> parameters;
  private String path;
  private String suffix;
  
  public ActivityPackage(ActivityKind paramActivityKind)
  {
    this.activityKind = paramActivityKind;
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream)
    throws ClassNotFoundException, IOException
  {
    paramObjectInputStream = paramObjectInputStream.readFields();
    this.path = Util.readStringField(paramObjectInputStream, "path", null);
    this.clientSdk = Util.readStringField(paramObjectInputStream, "clientSdk", null);
    this.parameters = ((Map)Util.readObjectField(paramObjectInputStream, "parameters", null));
    this.activityKind = ((ActivityKind)Util.readObjectField(paramObjectInputStream, "activityKind", ActivityKind.UNKNOWN));
    this.suffix = Util.readStringField(paramObjectInputStream, "suffix", null);
  }
  
  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.defaultWriteObject();
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {}
    do
    {
      return true;
      if (paramObject == null) {
        return false;
      }
      if (getClass() != paramObject.getClass()) {
        return false;
      }
      paramObject = (ActivityPackage)paramObject;
      if (!Util.equalString(this.path, ((ActivityPackage)paramObject).path)) {
        return false;
      }
      if (!Util.equalString(this.clientSdk, ((ActivityPackage)paramObject).clientSdk)) {
        return false;
      }
      if (!Util.equalsMap(this.parameters, ((ActivityPackage)paramObject).parameters)) {
        return false;
      }
      if (!Util.equalEnum(this.activityKind, ((ActivityPackage)paramObject).activityKind)) {
        return false;
      }
    } while (Util.equalString(this.suffix, ((ActivityPackage)paramObject).suffix));
    return false;
  }
  
  public ActivityKind getActivityKind()
  {
    return this.activityKind;
  }
  
  public String getClientSdk()
  {
    return this.clientSdk;
  }
  
  public String getExtendedString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(String.format(Locale.US, "Path:      %s\n", new Object[] { this.path }));
    localStringBuilder.append(String.format(Locale.US, "ClientSdk: %s\n", new Object[] { this.clientSdk }));
    if (this.parameters != null)
    {
      localStringBuilder.append("Parameters:");
      Iterator localIterator = new TreeMap(this.parameters).entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localStringBuilder.append(String.format(Locale.US, "\n\t%-16s %s", new Object[] { localEntry.getKey(), localEntry.getValue() }));
      }
    }
    return localStringBuilder.toString();
  }
  
  protected String getFailureMessage()
  {
    return String.format(Locale.US, "Failed to track %s%s", new Object[] { this.activityKind.toString(), this.suffix });
  }
  
  public Map<String, String> getParameters()
  {
    return this.parameters;
  }
  
  public String getPath()
  {
    return this.path;
  }
  
  public String getSuffix()
  {
    return this.suffix;
  }
  
  public int hashCode()
  {
    if (this.hashCode == 0)
    {
      this.hashCode = 17;
      this.hashCode = (this.hashCode * 37 + Util.hashString(this.path));
      this.hashCode = (this.hashCode * 37 + Util.hashString(this.clientSdk));
      this.hashCode = (this.hashCode * 37 + Util.hashMap(this.parameters));
      this.hashCode = (this.hashCode * 37 + Util.hashEnum(this.activityKind));
      this.hashCode = (this.hashCode * 37 + Util.hashString(this.suffix));
    }
    return this.hashCode;
  }
  
  public void setClientSdk(String paramString)
  {
    this.clientSdk = paramString;
  }
  
  public void setParameters(Map<String, String> paramMap)
  {
    this.parameters = paramMap;
  }
  
  public void setPath(String paramString)
  {
    this.path = paramString;
  }
  
  public void setSuffix(String paramString)
  {
    this.suffix = paramString;
  }
  
  public String toString()
  {
    return String.format(Locale.US, "%s%s", new Object[] { this.activityKind.toString(), this.suffix });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/adjust/sdk/ActivityPackage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */