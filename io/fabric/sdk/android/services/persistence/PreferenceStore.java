package io.fabric.sdk.android.services.persistence;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public abstract interface PreferenceStore
{
  public abstract SharedPreferences.Editor edit();
  
  public abstract SharedPreferences get();
  
  public abstract boolean save(SharedPreferences.Editor paramEditor);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/persistence/PreferenceStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */