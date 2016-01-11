package com.crashlytics.android.core.internal.models;

public class SessionEventData
{
  public final BinaryImageData[] binaryImages;
  public final CustomAttributeData[] customAttributes;
  public final DeviceData deviceData;
  public final SignalData signal;
  public final ThreadData[] threads;
  public final long timestamp;
  
  public SessionEventData(long paramLong, SignalData paramSignalData, ThreadData[] paramArrayOfThreadData, BinaryImageData[] paramArrayOfBinaryImageData, CustomAttributeData[] paramArrayOfCustomAttributeData, DeviceData paramDeviceData)
  {
    this.timestamp = paramLong;
    this.signal = paramSignalData;
    this.threads = paramArrayOfThreadData;
    this.binaryImages = paramArrayOfBinaryImageData;
    this.customAttributes = paramArrayOfCustomAttributeData;
    this.deviceData = paramDeviceData;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/crashlytics/android/core/internal/models/SessionEventData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */