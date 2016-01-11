package com.adjust.sdk;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;

public class ActivityState
  implements Serializable, Cloneable
{
  private static final ObjectStreamField[] serialPersistentFields = { new ObjectStreamField("uuid", String.class), new ObjectStreamField("enabled", Boolean.TYPE), new ObjectStreamField("askingAttribution", Boolean.TYPE), new ObjectStreamField("eventCount", Integer.TYPE), new ObjectStreamField("sessionCount", Integer.TYPE), new ObjectStreamField("subsessionCount", Integer.TYPE), new ObjectStreamField("sessionLength", Long.TYPE), new ObjectStreamField("timeSpent", Long.TYPE), new ObjectStreamField("lastActivity", Long.TYPE), new ObjectStreamField("lastInterval", Long.TYPE) };
  private static final long serialVersionUID = 9039439291143138148L;
  protected boolean askingAttribution = false;
  protected boolean enabled = true;
  protected int eventCount = 0;
  protected long lastActivity = -1L;
  protected long lastInterval = -1L;
  private transient ILogger logger = AdjustFactory.getLogger();
  protected int sessionCount = 0;
  protected long sessionLength = -1L;
  protected int subsessionCount = -1;
  protected long timeSpent = -1L;
  protected String uuid = Util.createUuid();
  
  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    paramObjectInputStream = paramObjectInputStream.readFields();
    this.eventCount = Util.readIntField(paramObjectInputStream, "eventCount", 0);
    this.sessionCount = Util.readIntField(paramObjectInputStream, "sessionCount", 0);
    this.subsessionCount = Util.readIntField(paramObjectInputStream, "subsessionCount", -1);
    this.sessionLength = Util.readLongField(paramObjectInputStream, "sessionLength", -1L);
    this.timeSpent = Util.readLongField(paramObjectInputStream, "timeSpent", -1L);
    this.lastActivity = Util.readLongField(paramObjectInputStream, "lastActivity", -1L);
    this.lastInterval = Util.readLongField(paramObjectInputStream, "lastInterval", -1L);
    this.uuid = Util.readStringField(paramObjectInputStream, "uuid", null);
    this.enabled = Util.readBooleanField(paramObjectInputStream, "enabled", true);
    this.askingAttribution = Util.readBooleanField(paramObjectInputStream, "askingAttribution", false);
    if (this.uuid == null) {
      this.uuid = Util.createUuid();
    }
  }
  
  private static String stamp(long paramLong)
  {
    Calendar.getInstance().setTimeInMillis(paramLong);
    return String.format(Locale.US, "%02d:%02d:%02d", new Object[] { Integer.valueOf(11), Integer.valueOf(12), Integer.valueOf(13) });
  }
  
  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.defaultWriteObject();
  }
  
  public ActivityState clone()
  {
    try
    {
      ActivityState localActivityState = (ActivityState)super.clone();
      return localActivityState;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException) {}
    return null;
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
      paramObject = (ActivityState)paramObject;
      if (!Util.equalString(this.uuid, ((ActivityState)paramObject).uuid)) {
        return false;
      }
      if (!Util.equalBoolean(Boolean.valueOf(this.enabled), Boolean.valueOf(((ActivityState)paramObject).enabled))) {
        return false;
      }
      if (!Util.equalBoolean(Boolean.valueOf(this.askingAttribution), Boolean.valueOf(((ActivityState)paramObject).askingAttribution))) {
        return false;
      }
      if (!Util.equalInt(Integer.valueOf(this.eventCount), Integer.valueOf(((ActivityState)paramObject).eventCount))) {
        return false;
      }
      if (!Util.equalInt(Integer.valueOf(this.sessionCount), Integer.valueOf(((ActivityState)paramObject).sessionCount))) {
        return false;
      }
      if (!Util.equalInt(Integer.valueOf(this.subsessionCount), Integer.valueOf(((ActivityState)paramObject).subsessionCount))) {
        return false;
      }
      if (!Util.equalLong(Long.valueOf(this.sessionLength), Long.valueOf(((ActivityState)paramObject).sessionLength))) {
        return false;
      }
      if (!Util.equalLong(Long.valueOf(this.timeSpent), Long.valueOf(((ActivityState)paramObject).timeSpent))) {
        return false;
      }
    } while (Util.equalLong(Long.valueOf(this.lastInterval), Long.valueOf(((ActivityState)paramObject).lastInterval)));
    return false;
  }
  
  public int hashCode()
  {
    return ((((((((Util.hashString(this.uuid) + 629) * 37 + Util.hashBoolean(Boolean.valueOf(this.enabled))) * 37 + Util.hashBoolean(Boolean.valueOf(this.askingAttribution))) * 37 + this.eventCount) * 37 + this.sessionCount) * 37 + this.subsessionCount) * 37 + Util.hashLong(Long.valueOf(this.sessionLength))) * 37 + Util.hashLong(Long.valueOf(this.timeSpent))) * 37 + Util.hashLong(Long.valueOf(this.lastInterval));
  }
  
  protected void resetSessionAttributes(long paramLong)
  {
    this.subsessionCount = 1;
    this.sessionLength = 0L;
    this.timeSpent = 0L;
    this.lastActivity = paramLong;
    this.lastInterval = -1L;
  }
  
  public String toString()
  {
    return String.format(Locale.US, "ec:%d sc:%d ssc:%d sl:%.1f ts:%.1f la:%s uuid:%s", new Object[] { Integer.valueOf(this.eventCount), Integer.valueOf(this.sessionCount), Integer.valueOf(this.subsessionCount), Double.valueOf(this.sessionLength / 1000.0D), Double.valueOf(this.timeSpent / 1000.0D), stamp(this.lastActivity), this.uuid });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/adjust/sdk/ActivityState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */