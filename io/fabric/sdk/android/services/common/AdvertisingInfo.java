package io.fabric.sdk.android.services.common;

class AdvertisingInfo
{
  public final String advertisingId;
  public final boolean limitAdTrackingEnabled;
  
  AdvertisingInfo(String paramString, boolean paramBoolean)
  {
    this.advertisingId = paramString;
    this.limitAdTrackingEnabled = paramBoolean;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if ((paramObject == null) || (getClass() != paramObject.getClass())) {
        return false;
      }
      paramObject = (AdvertisingInfo)paramObject;
      if (this.limitAdTrackingEnabled != ((AdvertisingInfo)paramObject).limitAdTrackingEnabled) {
        return false;
      }
      if (this.advertisingId == null) {
        break;
      }
    } while (this.advertisingId.equals(((AdvertisingInfo)paramObject).advertisingId));
    for (;;)
    {
      return false;
      if (((AdvertisingInfo)paramObject).advertisingId == null) {
        break;
      }
    }
  }
  
  public int hashCode()
  {
    int j = 0;
    if (this.advertisingId != null) {}
    for (int i = this.advertisingId.hashCode();; i = 0)
    {
      if (this.limitAdTrackingEnabled) {
        j = 1;
      }
      return i * 31 + j;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/common/AdvertisingInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */