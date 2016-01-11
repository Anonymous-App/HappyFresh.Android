package com.google.android.gms.location;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ActivityRecognitionResult
  implements SafeParcelable
{
  public static final ActivityRecognitionResultCreator CREATOR = new ActivityRecognitionResultCreator();
  private final int zzCY;
  List<DetectedActivity> zzaxr;
  long zzaxs;
  long zzaxt;
  int zzaxu;
  
  public ActivityRecognitionResult(int paramInt1, List<DetectedActivity> paramList, long paramLong1, long paramLong2, int paramInt2)
  {
    this.zzCY = paramInt1;
    this.zzaxr = paramList;
    this.zzaxs = paramLong1;
    this.zzaxt = paramLong2;
    this.zzaxu = paramInt2;
  }
  
  public ActivityRecognitionResult(DetectedActivity paramDetectedActivity, long paramLong1, long paramLong2)
  {
    this(paramDetectedActivity, paramLong1, paramLong2, 0);
  }
  
  public ActivityRecognitionResult(DetectedActivity paramDetectedActivity, long paramLong1, long paramLong2, int paramInt)
  {
    this(Collections.singletonList(paramDetectedActivity), paramLong1, paramLong2, paramInt);
  }
  
  public ActivityRecognitionResult(List<DetectedActivity> paramList, long paramLong1, long paramLong2)
  {
    this(paramList, paramLong1, paramLong2, 0);
  }
  
  public ActivityRecognitionResult(List<DetectedActivity> paramList, long paramLong1, long paramLong2, int paramInt)
  {
    if ((paramList != null) && (paramList.size() > 0))
    {
      bool1 = true;
      zzu.zzb(bool1, "Must have at least 1 detected activity");
      if ((paramLong1 <= 0L) || (paramLong2 <= 0L)) {
        break label88;
      }
    }
    label88:
    for (boolean bool1 = bool2;; bool1 = false)
    {
      zzu.zzb(bool1, "Must set times");
      this.zzCY = 2;
      this.zzaxr = paramList;
      this.zzaxs = paramLong1;
      this.zzaxt = paramLong2;
      this.zzaxu = paramInt;
      return;
      bool1 = false;
      break;
    }
  }
  
  public static ActivityRecognitionResult extractResult(Intent paramIntent)
  {
    if (!hasResult(paramIntent)) {
      return null;
    }
    paramIntent = paramIntent.getExtras().get("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT");
    if ((paramIntent instanceof byte[]))
    {
      Parcel localParcel = Parcel.obtain();
      localParcel.unmarshall((byte[])paramIntent, 0, ((byte[])paramIntent).length);
      localParcel.setDataPosition(0);
      return CREATOR.createFromParcel(localParcel);
    }
    if ((paramIntent instanceof ActivityRecognitionResult)) {
      return (ActivityRecognitionResult)paramIntent;
    }
    return null;
  }
  
  public static boolean hasResult(Intent paramIntent)
  {
    if (paramIntent == null) {
      return false;
    }
    return paramIntent.hasExtra("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT");
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public int getActivityConfidence(int paramInt)
  {
    Iterator localIterator = this.zzaxr.iterator();
    while (localIterator.hasNext())
    {
      DetectedActivity localDetectedActivity = (DetectedActivity)localIterator.next();
      if (localDetectedActivity.getType() == paramInt) {
        return localDetectedActivity.getConfidence();
      }
    }
    return 0;
  }
  
  public long getElapsedRealtimeMillis()
  {
    return this.zzaxt;
  }
  
  public DetectedActivity getMostProbableActivity()
  {
    return (DetectedActivity)this.zzaxr.get(0);
  }
  
  public List<DetectedActivity> getProbableActivities()
  {
    return this.zzaxr;
  }
  
  public long getTime()
  {
    return this.zzaxs;
  }
  
  public int getVersionCode()
  {
    return this.zzCY;
  }
  
  public String toString()
  {
    return "ActivityRecognitionResult [probableActivities=" + this.zzaxr + ", timeMillis=" + this.zzaxs + ", elapsedRealtimeMillis=" + this.zzaxt + "]";
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    ActivityRecognitionResultCreator.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/ActivityRecognitionResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */