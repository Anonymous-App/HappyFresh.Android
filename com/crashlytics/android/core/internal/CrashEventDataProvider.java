package com.crashlytics.android.core.internal;

import com.crashlytics.android.core.internal.models.SessionEventData;

public abstract interface CrashEventDataProvider
{
  public abstract SessionEventData getCrashEventData();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/crashlytics/android/core/internal/CrashEventDataProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */