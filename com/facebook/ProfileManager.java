package com.facebook;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;

final class ProfileManager
{
  static final String ACTION_CURRENT_PROFILE_CHANGED = "com.facebook.sdk.ACTION_CURRENT_PROFILE_CHANGED";
  static final String EXTRA_NEW_PROFILE = "com.facebook.sdk.EXTRA_NEW_PROFILE";
  static final String EXTRA_OLD_PROFILE = "com.facebook.sdk.EXTRA_OLD_PROFILE";
  private static volatile ProfileManager instance;
  private Profile currentProfile;
  private final LocalBroadcastManager localBroadcastManager;
  private final ProfileCache profileCache;
  
  ProfileManager(LocalBroadcastManager paramLocalBroadcastManager, ProfileCache paramProfileCache)
  {
    Validate.notNull(paramLocalBroadcastManager, "localBroadcastManager");
    Validate.notNull(paramProfileCache, "profileCache");
    this.localBroadcastManager = paramLocalBroadcastManager;
    this.profileCache = paramProfileCache;
  }
  
  static ProfileManager getInstance()
  {
    if (instance == null) {}
    try
    {
      if (instance == null) {
        instance = new ProfileManager(LocalBroadcastManager.getInstance(FacebookSdk.getApplicationContext()), new ProfileCache());
      }
      return instance;
    }
    finally {}
  }
  
  private void sendCurrentProfileChangedBroadcast(Profile paramProfile1, Profile paramProfile2)
  {
    Intent localIntent = new Intent("com.facebook.sdk.ACTION_CURRENT_PROFILE_CHANGED");
    localIntent.putExtra("com.facebook.sdk.EXTRA_OLD_PROFILE", paramProfile1);
    localIntent.putExtra("com.facebook.sdk.EXTRA_NEW_PROFILE", paramProfile2);
    this.localBroadcastManager.sendBroadcast(localIntent);
  }
  
  private void setCurrentProfile(Profile paramProfile, boolean paramBoolean)
  {
    Profile localProfile = this.currentProfile;
    this.currentProfile = paramProfile;
    if (paramBoolean)
    {
      if (paramProfile == null) {
        break label41;
      }
      this.profileCache.save(paramProfile);
    }
    for (;;)
    {
      if (!Utility.areObjectsEqual(localProfile, paramProfile)) {
        sendCurrentProfileChangedBroadcast(localProfile, paramProfile);
      }
      return;
      label41:
      this.profileCache.clear();
    }
  }
  
  Profile getCurrentProfile()
  {
    return this.currentProfile;
  }
  
  boolean loadCurrentProfile()
  {
    boolean bool = false;
    Profile localProfile = this.profileCache.load();
    if (localProfile != null)
    {
      setCurrentProfile(localProfile, false);
      bool = true;
    }
    return bool;
  }
  
  void setCurrentProfile(Profile paramProfile)
  {
    setCurrentProfile(paramProfile, true);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/ProfileManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */