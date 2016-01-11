package com.adjust.sdk;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.util.Locale;
import org.json.JSONObject;

public class AdjustAttribution
  implements Serializable
{
  private static final ObjectStreamField[] serialPersistentFields = { new ObjectStreamField("trackerToken", String.class), new ObjectStreamField("trackerName", String.class), new ObjectStreamField("network", String.class), new ObjectStreamField("campaign", String.class), new ObjectStreamField("adgroup", String.class), new ObjectStreamField("creative", String.class) };
  private static final long serialVersionUID = 1L;
  public String adgroup;
  public String campaign;
  public String creative;
  public String network;
  public String trackerName;
  public String trackerToken;
  
  public static AdjustAttribution fromJson(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null) {
      return null;
    }
    AdjustAttribution localAdjustAttribution = new AdjustAttribution();
    localAdjustAttribution.trackerToken = paramJSONObject.optString("tracker_token", null);
    localAdjustAttribution.trackerName = paramJSONObject.optString("tracker_name", null);
    localAdjustAttribution.network = paramJSONObject.optString("network", null);
    localAdjustAttribution.campaign = paramJSONObject.optString("campaign", null);
    localAdjustAttribution.adgroup = paramJSONObject.optString("adgroup", null);
    localAdjustAttribution.creative = paramJSONObject.optString("creative", null);
    return localAdjustAttribution;
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream)
    throws ClassNotFoundException, IOException
  {
    paramObjectInputStream.defaultReadObject();
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
      paramObject = (AdjustAttribution)paramObject;
      if (!Util.equalString(this.trackerToken, ((AdjustAttribution)paramObject).trackerToken)) {
        return false;
      }
      if (!Util.equalString(this.trackerName, ((AdjustAttribution)paramObject).trackerName)) {
        return false;
      }
      if (!Util.equalString(this.network, ((AdjustAttribution)paramObject).network)) {
        return false;
      }
      if (!Util.equalString(this.campaign, ((AdjustAttribution)paramObject).campaign)) {
        return false;
      }
      if (!Util.equalString(this.adgroup, ((AdjustAttribution)paramObject).adgroup)) {
        return false;
      }
    } while (Util.equalString(this.creative, ((AdjustAttribution)paramObject).creative));
    return false;
  }
  
  public int hashCode()
  {
    return (((((Util.hashString(this.trackerToken) + 629) * 37 + Util.hashString(this.trackerName)) * 37 + Util.hashString(this.network)) * 37 + Util.hashString(this.campaign)) * 37 + Util.hashString(this.adgroup)) * 37 + Util.hashString(this.creative);
  }
  
  public String toString()
  {
    return String.format(Locale.US, "tt:%s tn:%s net:%s cam:%s adg:%s cre:%s", new Object[] { this.trackerToken, this.trackerName, this.network, this.campaign, this.adgroup, this.creative });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/adjust/sdk/AdjustAttribution.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */