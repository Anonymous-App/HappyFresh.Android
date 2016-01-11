package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class MediaBrowserCompat
{
  private final MediaBrowserImplBase mImpl;
  
  public MediaBrowserCompat(Context paramContext, ComponentName paramComponentName, ConnectionCallback paramConnectionCallback, Bundle paramBundle)
  {
    this.mImpl = new MediaBrowserImplBase(paramContext, paramComponentName, paramConnectionCallback, paramBundle);
  }
  
  public void connect()
  {
    this.mImpl.connect();
  }
  
  public void disconnect()
  {
    this.mImpl.disconnect();
  }
  
  @Nullable
  public Bundle getExtras()
  {
    return this.mImpl.getExtras();
  }
  
  public void getItem(@NonNull String paramString, @NonNull ItemCallback paramItemCallback)
  {
    this.mImpl.getItem(paramString, paramItemCallback);
  }
  
  @NonNull
  public String getRoot()
  {
    return this.mImpl.getRoot();
  }
  
  @NonNull
  public ComponentName getServiceComponent()
  {
    return this.mImpl.getServiceComponent();
  }
  
  @NonNull
  public MediaSessionCompat.Token getSessionToken()
  {
    return this.mImpl.getSessionToken();
  }
  
  public boolean isConnected()
  {
    return this.mImpl.isConnected();
  }
  
  public void subscribe(@NonNull String paramString, @NonNull SubscriptionCallback paramSubscriptionCallback)
  {
    this.mImpl.subscribe(paramString, paramSubscriptionCallback);
  }
  
  public void unsubscribe(@NonNull String paramString)
  {
    this.mImpl.unsubscribe(paramString);
  }
  
  public static class ConnectionCallback
  {
    public void onConnected() {}
    
    public void onConnectionFailed() {}
    
    public void onConnectionSuspended() {}
  }
  
  public static abstract class ItemCallback
  {
    public void onError(@NonNull String paramString) {}
    
    public void onItemLoaded(MediaBrowserCompat.MediaItem paramMediaItem) {}
  }
  
  static class MediaBrowserImplBase
  {
    private static final int CONNECT_STATE_CONNECTED = 2;
    private static final int CONNECT_STATE_CONNECTING = 1;
    private static final int CONNECT_STATE_DISCONNECTED = 0;
    private static final int CONNECT_STATE_SUSPENDED = 3;
    private static final boolean DBG = false;
    private static final String TAG = "MediaBrowserCompat";
    private final MediaBrowserCompat.ConnectionCallback mCallback;
    private final Context mContext;
    private Bundle mExtras;
    private final Handler mHandler = new Handler();
    private MediaSessionCompat.Token mMediaSessionToken;
    private final Bundle mRootHints;
    private String mRootId;
    private IMediaBrowserServiceCompat mServiceBinder;
    private IMediaBrowserServiceCompatCallbacks mServiceCallbacks;
    private final ComponentName mServiceComponent;
    private MediaServiceConnection mServiceConnection;
    private int mState = 0;
    private final ArrayMap<String, Subscription> mSubscriptions = new ArrayMap();
    
    public MediaBrowserImplBase(Context paramContext, ComponentName paramComponentName, MediaBrowserCompat.ConnectionCallback paramConnectionCallback, Bundle paramBundle)
    {
      if (paramContext == null) {
        throw new IllegalArgumentException("context must not be null");
      }
      if (paramComponentName == null) {
        throw new IllegalArgumentException("service component must not be null");
      }
      if (paramConnectionCallback == null) {
        throw new IllegalArgumentException("connection callback must not be null");
      }
      this.mContext = paramContext;
      this.mServiceComponent = paramComponentName;
      this.mCallback = paramConnectionCallback;
      this.mRootHints = paramBundle;
    }
    
    private void forceCloseConnection()
    {
      if (this.mServiceConnection != null) {
        this.mContext.unbindService(this.mServiceConnection);
      }
      this.mState = 0;
      this.mServiceConnection = null;
      this.mServiceBinder = null;
      this.mServiceCallbacks = null;
      this.mRootId = null;
      this.mMediaSessionToken = null;
    }
    
    private ServiceCallbacks getNewServiceCallbacks()
    {
      return new ServiceCallbacks(this);
    }
    
    private static String getStateLabel(int paramInt)
    {
      switch (paramInt)
      {
      default: 
        return "UNKNOWN/" + paramInt;
      case 0: 
        return "CONNECT_STATE_DISCONNECTED";
      case 1: 
        return "CONNECT_STATE_CONNECTING";
      case 2: 
        return "CONNECT_STATE_CONNECTED";
      }
      return "CONNECT_STATE_SUSPENDED";
    }
    
    private boolean isCurrent(IMediaBrowserServiceCompatCallbacks paramIMediaBrowserServiceCompatCallbacks, String paramString)
    {
      if (this.mServiceCallbacks != paramIMediaBrowserServiceCompatCallbacks)
      {
        if (this.mState != 0) {
          Log.i("MediaBrowserCompat", paramString + " for " + this.mServiceComponent + " with mServiceConnection=" + this.mServiceCallbacks + " this=" + this);
        }
        return false;
      }
      return true;
    }
    
    private final void onConnectionFailed(final IMediaBrowserServiceCompatCallbacks paramIMediaBrowserServiceCompatCallbacks)
    {
      this.mHandler.post(new Runnable()
      {
        public void run()
        {
          Log.e("MediaBrowserCompat", "onConnectFailed for " + MediaBrowserCompat.MediaBrowserImplBase.this.mServiceComponent);
          if (!MediaBrowserCompat.MediaBrowserImplBase.this.isCurrent(paramIMediaBrowserServiceCompatCallbacks, "onConnectFailed")) {
            return;
          }
          if (MediaBrowserCompat.MediaBrowserImplBase.this.mState != 1)
          {
            Log.w("MediaBrowserCompat", "onConnect from service while mState=" + MediaBrowserCompat.MediaBrowserImplBase.getStateLabel(MediaBrowserCompat.MediaBrowserImplBase.this.mState) + "... ignoring");
            return;
          }
          MediaBrowserCompat.MediaBrowserImplBase.this.forceCloseConnection();
          MediaBrowserCompat.MediaBrowserImplBase.this.mCallback.onConnectionFailed();
        }
      });
    }
    
    private final void onLoadChildren(final IMediaBrowserServiceCompatCallbacks paramIMediaBrowserServiceCompatCallbacks, final String paramString, final List paramList)
    {
      this.mHandler.post(new Runnable()
      {
        public void run()
        {
          if (!MediaBrowserCompat.MediaBrowserImplBase.this.isCurrent(paramIMediaBrowserServiceCompatCallbacks, "onLoadChildren")) {}
          Object localObject2;
          Object localObject1;
          do
          {
            return;
            localObject2 = paramList;
            localObject1 = localObject2;
            if (localObject2 == null) {
              localObject1 = Collections.emptyList();
            }
            localObject2 = (MediaBrowserCompat.MediaBrowserImplBase.Subscription)MediaBrowserCompat.MediaBrowserImplBase.this.mSubscriptions.get(paramString);
          } while (localObject2 == null);
          ((MediaBrowserCompat.MediaBrowserImplBase.Subscription)localObject2).callback.onChildrenLoaded(paramString, (List)localObject1);
        }
      });
    }
    
    private final void onServiceConnected(final IMediaBrowserServiceCompatCallbacks paramIMediaBrowserServiceCompatCallbacks, final String paramString, final MediaSessionCompat.Token paramToken, final Bundle paramBundle)
    {
      this.mHandler.post(new Runnable()
      {
        public void run()
        {
          if (!MediaBrowserCompat.MediaBrowserImplBase.this.isCurrent(paramIMediaBrowserServiceCompatCallbacks, "onConnect")) {}
          for (;;)
          {
            return;
            if (MediaBrowserCompat.MediaBrowserImplBase.this.mState != 1)
            {
              Log.w("MediaBrowserCompat", "onConnect from service while mState=" + MediaBrowserCompat.MediaBrowserImplBase.getStateLabel(MediaBrowserCompat.MediaBrowserImplBase.this.mState) + "... ignoring");
              return;
            }
            MediaBrowserCompat.MediaBrowserImplBase.access$802(MediaBrowserCompat.MediaBrowserImplBase.this, paramString);
            MediaBrowserCompat.MediaBrowserImplBase.access$902(MediaBrowserCompat.MediaBrowserImplBase.this, paramToken);
            MediaBrowserCompat.MediaBrowserImplBase.access$1002(MediaBrowserCompat.MediaBrowserImplBase.this, paramBundle);
            MediaBrowserCompat.MediaBrowserImplBase.access$602(MediaBrowserCompat.MediaBrowserImplBase.this, 2);
            MediaBrowserCompat.MediaBrowserImplBase.this.mCallback.onConnected();
            Iterator localIterator = MediaBrowserCompat.MediaBrowserImplBase.this.mSubscriptions.keySet().iterator();
            while (localIterator.hasNext())
            {
              String str = (String)localIterator.next();
              try
              {
                MediaBrowserCompat.MediaBrowserImplBase.this.mServiceBinder.addSubscription(str, MediaBrowserCompat.MediaBrowserImplBase.this.mServiceCallbacks);
              }
              catch (RemoteException localRemoteException)
              {
                Log.d("MediaBrowserCompat", "addSubscription failed with RemoteException parentId=" + str);
              }
            }
          }
        }
      });
    }
    
    public void connect()
    {
      if (this.mState != 0) {
        throw new IllegalStateException("connect() called while not disconnected (state=" + getStateLabel(this.mState) + ")");
      }
      if (this.mServiceBinder != null) {
        throw new RuntimeException("mServiceBinder should be null. Instead it is " + this.mServiceBinder);
      }
      if (this.mServiceCallbacks != null) {
        throw new RuntimeException("mServiceCallbacks should be null. Instead it is " + this.mServiceCallbacks);
      }
      this.mState = 1;
      Intent localIntent = new Intent("android.media.browse.MediaBrowserServiceCompat");
      localIntent.setComponent(this.mServiceComponent);
      final MediaServiceConnection localMediaServiceConnection = new MediaServiceConnection(null);
      this.mServiceConnection = localMediaServiceConnection;
      int i = 0;
      try
      {
        boolean bool = this.mContext.bindService(localIntent, this.mServiceConnection, 1);
        i = bool;
      }
      catch (Exception localException)
      {
        for (;;)
        {
          Log.e("MediaBrowserCompat", "Failed binding to service " + this.mServiceComponent);
        }
      }
      if (i == 0) {
        this.mHandler.post(new Runnable()
        {
          public void run()
          {
            if (localMediaServiceConnection == MediaBrowserCompat.MediaBrowserImplBase.this.mServiceConnection)
            {
              MediaBrowserCompat.MediaBrowserImplBase.this.forceCloseConnection();
              MediaBrowserCompat.MediaBrowserImplBase.this.mCallback.onConnectionFailed();
            }
          }
        });
      }
    }
    
    public void disconnect()
    {
      if (this.mServiceCallbacks != null) {}
      try
      {
        this.mServiceBinder.disconnect(this.mServiceCallbacks);
        forceCloseConnection();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        for (;;)
        {
          Log.w("MediaBrowserCompat", "RemoteException during connect for " + this.mServiceComponent);
        }
      }
    }
    
    void dump()
    {
      Log.d("MediaBrowserCompat", "MediaBrowserCompat...");
      Log.d("MediaBrowserCompat", "  mServiceComponent=" + this.mServiceComponent);
      Log.d("MediaBrowserCompat", "  mCallback=" + this.mCallback);
      Log.d("MediaBrowserCompat", "  mRootHints=" + this.mRootHints);
      Log.d("MediaBrowserCompat", "  mState=" + getStateLabel(this.mState));
      Log.d("MediaBrowserCompat", "  mServiceConnection=" + this.mServiceConnection);
      Log.d("MediaBrowserCompat", "  mServiceBinder=" + this.mServiceBinder);
      Log.d("MediaBrowserCompat", "  mServiceCallbacks=" + this.mServiceCallbacks);
      Log.d("MediaBrowserCompat", "  mRootId=" + this.mRootId);
      Log.d("MediaBrowserCompat", "  mMediaSessionToken=" + this.mMediaSessionToken);
    }
    
    @Nullable
    public Bundle getExtras()
    {
      if (!isConnected()) {
        throw new IllegalStateException("getExtras() called while not connected (state=" + getStateLabel(this.mState) + ")");
      }
      return this.mExtras;
    }
    
    public void getItem(@NonNull final String paramString, @NonNull final MediaBrowserCompat.ItemCallback paramItemCallback)
    {
      if (TextUtils.isEmpty(paramString)) {
        throw new IllegalArgumentException("mediaId is empty.");
      }
      if (paramItemCallback == null) {
        throw new IllegalArgumentException("cb is null.");
      }
      if (this.mState != 2)
      {
        Log.i("MediaBrowserCompat", "Not connected, unable to retrieve the MediaItem.");
        this.mHandler.post(new Runnable()
        {
          public void run()
          {
            paramItemCallback.onError(paramString);
          }
        });
        return;
      }
      ResultReceiver local3 = new ResultReceiver(this.mHandler)
      {
        protected void onReceiveResult(int paramAnonymousInt, Bundle paramAnonymousBundle)
        {
          if ((paramAnonymousInt != 0) || (paramAnonymousBundle == null) || (!paramAnonymousBundle.containsKey("media_item")))
          {
            paramItemCallback.onError(paramString);
            return;
          }
          paramAnonymousBundle = paramAnonymousBundle.getParcelable("media_item");
          if (!(paramAnonymousBundle instanceof MediaBrowserCompat.MediaItem))
          {
            paramItemCallback.onError(paramString);
            return;
          }
          paramItemCallback.onItemLoaded((MediaBrowserCompat.MediaItem)paramAnonymousBundle);
        }
      };
      try
      {
        this.mServiceBinder.getMediaItem(paramString, local3);
        return;
      }
      catch (RemoteException localRemoteException)
      {
        Log.i("MediaBrowserCompat", "Remote error getting media item.");
        this.mHandler.post(new Runnable()
        {
          public void run()
          {
            paramItemCallback.onError(paramString);
          }
        });
      }
    }
    
    @NonNull
    public String getRoot()
    {
      if (!isConnected()) {
        throw new IllegalStateException("getSessionToken() called while not connected(state=" + getStateLabel(this.mState) + ")");
      }
      return this.mRootId;
    }
    
    @NonNull
    public ComponentName getServiceComponent()
    {
      if (!isConnected()) {
        throw new IllegalStateException("getServiceComponent() called while not connected (state=" + this.mState + ")");
      }
      return this.mServiceComponent;
    }
    
    @NonNull
    public MediaSessionCompat.Token getSessionToken()
    {
      if (!isConnected()) {
        throw new IllegalStateException("getSessionToken() called while not connected(state=" + this.mState + ")");
      }
      return this.mMediaSessionToken;
    }
    
    public boolean isConnected()
    {
      return this.mState == 2;
    }
    
    public void subscribe(@NonNull String paramString, @NonNull MediaBrowserCompat.SubscriptionCallback paramSubscriptionCallback)
    {
      if (paramString == null) {
        throw new IllegalArgumentException("parentId is null");
      }
      if (paramSubscriptionCallback == null) {
        throw new IllegalArgumentException("callback is null");
      }
      Subscription localSubscription = (Subscription)this.mSubscriptions.get(paramString);
      if (localSubscription == null) {}
      for (int i = 1;; i = 0)
      {
        if (i != 0)
        {
          localSubscription = new Subscription(paramString);
          this.mSubscriptions.put(paramString, localSubscription);
        }
        localSubscription.callback = paramSubscriptionCallback;
        if (this.mState == 2) {}
        try
        {
          this.mServiceBinder.addSubscription(paramString, this.mServiceCallbacks);
          return;
        }
        catch (RemoteException paramSubscriptionCallback)
        {
          Log.d("MediaBrowserCompat", "addSubscription failed with RemoteException parentId=" + paramString);
        }
      }
    }
    
    public void unsubscribe(@NonNull String paramString)
    {
      if (TextUtils.isEmpty(paramString)) {
        throw new IllegalArgumentException("parentId is empty.");
      }
      Subscription localSubscription = (Subscription)this.mSubscriptions.remove(paramString);
      if ((this.mState == 2) && (localSubscription != null)) {}
      try
      {
        this.mServiceBinder.removeSubscription(paramString, this.mServiceCallbacks);
        return;
      }
      catch (RemoteException localRemoteException)
      {
        Log.d("MediaBrowserCompat", "removeSubscription failed with RemoteException parentId=" + paramString);
      }
    }
    
    private class MediaServiceConnection
      implements ServiceConnection
    {
      private MediaServiceConnection() {}
      
      private boolean isCurrent(String paramString)
      {
        if (MediaBrowserCompat.MediaBrowserImplBase.this.mServiceConnection != this)
        {
          if (MediaBrowserCompat.MediaBrowserImplBase.this.mState != 0) {
            Log.i("MediaBrowserCompat", paramString + " for " + MediaBrowserCompat.MediaBrowserImplBase.this.mServiceComponent + " with mServiceConnection=" + MediaBrowserCompat.MediaBrowserImplBase.this.mServiceConnection + " this=" + this);
          }
          return false;
        }
        return true;
      }
      
      public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
      {
        if (!isCurrent("onServiceConnected")) {
          return;
        }
        MediaBrowserCompat.MediaBrowserImplBase.access$1302(MediaBrowserCompat.MediaBrowserImplBase.this, IMediaBrowserServiceCompat.Stub.asInterface(paramIBinder));
        MediaBrowserCompat.MediaBrowserImplBase.access$1202(MediaBrowserCompat.MediaBrowserImplBase.this, MediaBrowserCompat.MediaBrowserImplBase.this.getNewServiceCallbacks());
        MediaBrowserCompat.MediaBrowserImplBase.access$602(MediaBrowserCompat.MediaBrowserImplBase.this, 1);
        try
        {
          MediaBrowserCompat.MediaBrowserImplBase.this.mServiceBinder.connect(MediaBrowserCompat.MediaBrowserImplBase.this.mContext.getPackageName(), MediaBrowserCompat.MediaBrowserImplBase.this.mRootHints, MediaBrowserCompat.MediaBrowserImplBase.this.mServiceCallbacks);
          return;
        }
        catch (RemoteException paramComponentName)
        {
          Log.w("MediaBrowserCompat", "RemoteException during connect for " + MediaBrowserCompat.MediaBrowserImplBase.this.mServiceComponent);
        }
      }
      
      public void onServiceDisconnected(ComponentName paramComponentName)
      {
        if (!isCurrent("onServiceDisconnected")) {
          return;
        }
        MediaBrowserCompat.MediaBrowserImplBase.access$1302(MediaBrowserCompat.MediaBrowserImplBase.this, null);
        MediaBrowserCompat.MediaBrowserImplBase.access$1202(MediaBrowserCompat.MediaBrowserImplBase.this, null);
        MediaBrowserCompat.MediaBrowserImplBase.access$602(MediaBrowserCompat.MediaBrowserImplBase.this, 3);
        MediaBrowserCompat.MediaBrowserImplBase.this.mCallback.onConnectionSuspended();
      }
    }
    
    private static class ServiceCallbacks
      extends IMediaBrowserServiceCompatCallbacks.Stub
    {
      private WeakReference<MediaBrowserCompat.MediaBrowserImplBase> mMediaBrowser;
      
      public ServiceCallbacks(MediaBrowserCompat.MediaBrowserImplBase paramMediaBrowserImplBase)
      {
        this.mMediaBrowser = new WeakReference(paramMediaBrowserImplBase);
      }
      
      public void onConnect(String paramString, MediaSessionCompat.Token paramToken, Bundle paramBundle)
      {
        MediaBrowserCompat.MediaBrowserImplBase localMediaBrowserImplBase = (MediaBrowserCompat.MediaBrowserImplBase)this.mMediaBrowser.get();
        if (localMediaBrowserImplBase != null) {
          localMediaBrowserImplBase.onServiceConnected(this, paramString, paramToken, paramBundle);
        }
      }
      
      public void onConnectFailed()
      {
        MediaBrowserCompat.MediaBrowserImplBase localMediaBrowserImplBase = (MediaBrowserCompat.MediaBrowserImplBase)this.mMediaBrowser.get();
        if (localMediaBrowserImplBase != null) {
          localMediaBrowserImplBase.onConnectionFailed(this);
        }
      }
      
      public void onLoadChildren(String paramString, List paramList)
      {
        MediaBrowserCompat.MediaBrowserImplBase localMediaBrowserImplBase = (MediaBrowserCompat.MediaBrowserImplBase)this.mMediaBrowser.get();
        if (localMediaBrowserImplBase != null) {
          localMediaBrowserImplBase.onLoadChildren(this, paramString, paramList);
        }
      }
    }
    
    private static class Subscription
    {
      MediaBrowserCompat.SubscriptionCallback callback;
      final String id;
      
      Subscription(String paramString)
      {
        this.id = paramString;
      }
    }
  }
  
  public static class MediaItem
    implements Parcelable
  {
    public static final Parcelable.Creator<MediaItem> CREATOR = new Parcelable.Creator()
    {
      public MediaBrowserCompat.MediaItem createFromParcel(Parcel paramAnonymousParcel)
      {
        return new MediaBrowserCompat.MediaItem(paramAnonymousParcel, null);
      }
      
      public MediaBrowserCompat.MediaItem[] newArray(int paramAnonymousInt)
      {
        return new MediaBrowserCompat.MediaItem[paramAnonymousInt];
      }
    };
    public static final int FLAG_BROWSABLE = 1;
    public static final int FLAG_PLAYABLE = 2;
    private final MediaDescriptionCompat mDescription;
    private final int mFlags;
    
    private MediaItem(Parcel paramParcel)
    {
      this.mFlags = paramParcel.readInt();
      this.mDescription = ((MediaDescriptionCompat)MediaDescriptionCompat.CREATOR.createFromParcel(paramParcel));
    }
    
    public MediaItem(@NonNull MediaDescriptionCompat paramMediaDescriptionCompat, int paramInt)
    {
      if (paramMediaDescriptionCompat == null) {
        throw new IllegalArgumentException("description cannot be null");
      }
      if (TextUtils.isEmpty(paramMediaDescriptionCompat.getMediaId())) {
        throw new IllegalArgumentException("description must have a non-empty media id");
      }
      this.mFlags = paramInt;
      this.mDescription = paramMediaDescriptionCompat;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    @NonNull
    public MediaDescriptionCompat getDescription()
    {
      return this.mDescription;
    }
    
    public int getFlags()
    {
      return this.mFlags;
    }
    
    @NonNull
    public String getMediaId()
    {
      return this.mDescription.getMediaId();
    }
    
    public boolean isBrowsable()
    {
      return (this.mFlags & 0x1) != 0;
    }
    
    public boolean isPlayable()
    {
      return (this.mFlags & 0x2) != 0;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder("MediaItem{");
      localStringBuilder.append("mFlags=").append(this.mFlags);
      localStringBuilder.append(", mDescription=").append(this.mDescription);
      localStringBuilder.append('}');
      return localStringBuilder.toString();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeInt(this.mFlags);
      this.mDescription.writeToParcel(paramParcel, paramInt);
    }
    
    @Retention(RetentionPolicy.SOURCE)
    public static @interface Flags {}
  }
  
  public static abstract class SubscriptionCallback
  {
    public void onChildrenLoaded(@NonNull String paramString, @NonNull List<MediaBrowserCompat.MediaItem> paramList) {}
    
    public void onError(@NonNull String paramString) {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/android/support/v4/media/MediaBrowserCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */