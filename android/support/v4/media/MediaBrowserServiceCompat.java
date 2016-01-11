package android.support.v4.media;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class MediaBrowserServiceCompat
  extends Service
{
  private static final boolean DBG = false;
  public static final String KEY_MEDIA_ITEM = "media_item";
  public static final String SERVICE_INTERFACE = "android.media.browse.MediaBrowserServiceCompat";
  private static final String TAG = "MediaBrowserServiceCompat";
  private ServiceBinder mBinder;
  private final ArrayMap<IBinder, ConnectionRecord> mConnections = new ArrayMap();
  private final Handler mHandler = new Handler();
  MediaSessionCompat.Token mSession;
  
  private void addSubscription(String paramString, ConnectionRecord paramConnectionRecord)
  {
    paramConnectionRecord.subscriptions.add(paramString);
    performLoadChildren(paramString, paramConnectionRecord);
  }
  
  private boolean isValidPackage(String paramString, int paramInt)
  {
    if (paramString == null) {}
    for (;;)
    {
      return false;
      String[] arrayOfString = getPackageManager().getPackagesForUid(paramInt);
      int i = arrayOfString.length;
      paramInt = 0;
      while (paramInt < i)
      {
        if (arrayOfString[paramInt].equals(paramString)) {
          return true;
        }
        paramInt += 1;
      }
    }
  }
  
  private void performLoadChildren(final String paramString, final ConnectionRecord paramConnectionRecord)
  {
    Result local3 = new Result(paramString, paramString)
    {
      void onResultSent(List<MediaBrowserCompat.MediaItem> paramAnonymousList)
      {
        if (paramAnonymousList == null) {
          throw new IllegalStateException("onLoadChildren sent null list for id " + paramString);
        }
        if (MediaBrowserServiceCompat.this.mConnections.get(paramConnectionRecord.callbacks.asBinder()) != paramConnectionRecord) {
          return;
        }
        try
        {
          paramConnectionRecord.callbacks.onLoadChildren(paramString, paramAnonymousList);
          return;
        }
        catch (RemoteException paramAnonymousList)
        {
          Log.w("MediaBrowserServiceCompat", "Calling onLoadChildren() failed for id=" + paramString + " package=" + paramConnectionRecord.pkg);
        }
      }
    };
    onLoadChildren(paramString, local3);
    if (!local3.isDone()) {
      throw new IllegalStateException("onLoadChildren must call detach() or sendResult() before returning for package=" + paramConnectionRecord.pkg + " id=" + paramString);
    }
  }
  
  private void performLoadItem(String paramString, final ResultReceiver paramResultReceiver)
  {
    paramResultReceiver = new Result(paramString, paramResultReceiver)
    {
      void onResultSent(MediaBrowserCompat.MediaItem paramAnonymousMediaItem)
      {
        Bundle localBundle = new Bundle();
        localBundle.putParcelable("media_item", paramAnonymousMediaItem);
        paramResultReceiver.send(0, localBundle);
      }
    };
    onLoadItem(paramString, paramResultReceiver);
    if (!paramResultReceiver.isDone()) {
      throw new IllegalStateException("onLoadItem must call detach() or sendResult() before returning for id=" + paramString);
    }
  }
  
  public void dump(FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString) {}
  
  @Nullable
  public MediaSessionCompat.Token getSessionToken()
  {
    return this.mSession;
  }
  
  public void notifyChildrenChanged(@NonNull final String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
    }
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        Iterator localIterator = MediaBrowserServiceCompat.this.mConnections.keySet().iterator();
        while (localIterator.hasNext())
        {
          Object localObject = (IBinder)localIterator.next();
          localObject = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(localObject);
          if (((MediaBrowserServiceCompat.ConnectionRecord)localObject).subscriptions.contains(paramString)) {
            MediaBrowserServiceCompat.this.performLoadChildren(paramString, (MediaBrowserServiceCompat.ConnectionRecord)localObject);
          }
        }
      }
    });
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    if ("android.media.browse.MediaBrowserServiceCompat".equals(paramIntent.getAction())) {
      return this.mBinder;
    }
    return null;
  }
  
  public void onCreate()
  {
    super.onCreate();
    this.mBinder = new ServiceBinder(null);
  }
  
  @Nullable
  public abstract BrowserRoot onGetRoot(@NonNull String paramString, int paramInt, @Nullable Bundle paramBundle);
  
  public abstract void onLoadChildren(@NonNull String paramString, @NonNull Result<List<MediaBrowserCompat.MediaItem>> paramResult);
  
  public void onLoadItem(String paramString, Result<MediaBrowserCompat.MediaItem> paramResult)
  {
    paramResult.sendResult(null);
  }
  
  public void setSessionToken(final MediaSessionCompat.Token paramToken)
  {
    if (paramToken == null) {
      throw new IllegalArgumentException("Session token may not be null.");
    }
    if (this.mSession != null) {
      throw new IllegalStateException("The session token has already been set.");
    }
    this.mSession = paramToken;
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        Iterator localIterator = MediaBrowserServiceCompat.this.mConnections.keySet().iterator();
        while (localIterator.hasNext())
        {
          IBinder localIBinder = (IBinder)localIterator.next();
          MediaBrowserServiceCompat.ConnectionRecord localConnectionRecord = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(localIBinder);
          try
          {
            localConnectionRecord.callbacks.onConnect(localConnectionRecord.root.getRootId(), paramToken, localConnectionRecord.root.getExtras());
          }
          catch (RemoteException localRemoteException)
          {
            Log.w("MediaBrowserServiceCompat", "Connection for " + localConnectionRecord.pkg + " is no longer valid.");
            MediaBrowserServiceCompat.this.mConnections.remove(localIBinder);
          }
        }
      }
    });
  }
  
  public static final class BrowserRoot
  {
    private final Bundle mExtras;
    private final String mRootId;
    
    public BrowserRoot(@NonNull String paramString, @Nullable Bundle paramBundle)
    {
      if (paramString == null) {
        throw new IllegalArgumentException("The root id in BrowserRoot cannot be null. Use null for BrowserRoot instead.");
      }
      this.mRootId = paramString;
      this.mExtras = paramBundle;
    }
    
    public Bundle getExtras()
    {
      return this.mExtras;
    }
    
    public String getRootId()
    {
      return this.mRootId;
    }
  }
  
  private class ConnectionRecord
  {
    IMediaBrowserServiceCompatCallbacks callbacks;
    String pkg;
    MediaBrowserServiceCompat.BrowserRoot root;
    Bundle rootHints;
    HashSet<String> subscriptions = new HashSet();
    
    private ConnectionRecord() {}
  }
  
  public class Result<T>
  {
    private Object mDebug;
    private boolean mDetachCalled;
    private boolean mSendResultCalled;
    
    Result(Object paramObject)
    {
      this.mDebug = paramObject;
    }
    
    public void detach()
    {
      if (this.mDetachCalled) {
        throw new IllegalStateException("detach() called when detach() had already been called for: " + this.mDebug);
      }
      if (this.mSendResultCalled) {
        throw new IllegalStateException("detach() called when sendResult() had already been called for: " + this.mDebug);
      }
      this.mDetachCalled = true;
    }
    
    boolean isDone()
    {
      return (this.mDetachCalled) || (this.mSendResultCalled);
    }
    
    void onResultSent(T paramT) {}
    
    public void sendResult(T paramT)
    {
      if (this.mSendResultCalled) {
        throw new IllegalStateException("sendResult() called twice for: " + this.mDebug);
      }
      this.mSendResultCalled = true;
      onResultSent(paramT);
    }
  }
  
  private class ServiceBinder
    extends IMediaBrowserServiceCompat.Stub
  {
    private ServiceBinder() {}
    
    public void addSubscription(final String paramString, final IMediaBrowserServiceCompatCallbacks paramIMediaBrowserServiceCompatCallbacks)
    {
      MediaBrowserServiceCompat.this.mHandler.post(new Runnable()
      {
        public void run()
        {
          Object localObject = paramIMediaBrowserServiceCompatCallbacks.asBinder();
          localObject = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(localObject);
          if (localObject == null)
          {
            Log.w("MediaBrowserServiceCompat", "addSubscription for callback that isn't registered id=" + paramString);
            return;
          }
          MediaBrowserServiceCompat.this.addSubscription(paramString, (MediaBrowserServiceCompat.ConnectionRecord)localObject);
        }
      });
    }
    
    public void connect(final String paramString, final Bundle paramBundle, final IMediaBrowserServiceCompatCallbacks paramIMediaBrowserServiceCompatCallbacks)
    {
      final int i = Binder.getCallingUid();
      if (!MediaBrowserServiceCompat.this.isValidPackage(paramString, i)) {
        throw new IllegalArgumentException("Package/uid mismatch: uid=" + i + " package=" + paramString);
      }
      MediaBrowserServiceCompat.this.mHandler.post(new Runnable()
      {
        public void run()
        {
          IBinder localIBinder = paramIMediaBrowserServiceCompatCallbacks.asBinder();
          MediaBrowserServiceCompat.this.mConnections.remove(localIBinder);
          MediaBrowserServiceCompat.ConnectionRecord localConnectionRecord = new MediaBrowserServiceCompat.ConnectionRecord(MediaBrowserServiceCompat.this, null);
          localConnectionRecord.pkg = paramString;
          localConnectionRecord.rootHints = paramBundle;
          localConnectionRecord.callbacks = paramIMediaBrowserServiceCompatCallbacks;
          localConnectionRecord.root = MediaBrowserServiceCompat.this.onGetRoot(paramString, i, paramBundle);
          if (localConnectionRecord.root == null) {
            Log.i("MediaBrowserServiceCompat", "No root for client " + paramString + " from service " + getClass().getName());
          }
          for (;;)
          {
            try
            {
              paramIMediaBrowserServiceCompatCallbacks.onConnectFailed();
              return;
            }
            catch (RemoteException localRemoteException1)
            {
              Log.w("MediaBrowserServiceCompat", "Calling onConnectFailed() failed. Ignoring. pkg=" + paramString);
              return;
            }
            try
            {
              MediaBrowserServiceCompat.this.mConnections.put(localRemoteException1, localConnectionRecord);
              if (MediaBrowserServiceCompat.this.mSession != null)
              {
                paramIMediaBrowserServiceCompatCallbacks.onConnect(localConnectionRecord.root.getRootId(), MediaBrowserServiceCompat.this.mSession, localConnectionRecord.root.getExtras());
                return;
              }
            }
            catch (RemoteException localRemoteException2)
            {
              Log.w("MediaBrowserServiceCompat", "Calling onConnect() failed. Dropping client. pkg=" + paramString);
              MediaBrowserServiceCompat.this.mConnections.remove(localRemoteException1);
            }
          }
        }
      });
    }
    
    public void disconnect(final IMediaBrowserServiceCompatCallbacks paramIMediaBrowserServiceCompatCallbacks)
    {
      MediaBrowserServiceCompat.this.mHandler.post(new Runnable()
      {
        public void run()
        {
          IBinder localIBinder = paramIMediaBrowserServiceCompatCallbacks.asBinder();
          if ((MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.remove(localIBinder) != null) {}
        }
      });
    }
    
    public void getMediaItem(final String paramString, final ResultReceiver paramResultReceiver)
    {
      if ((TextUtils.isEmpty(paramString)) || (paramResultReceiver == null)) {
        return;
      }
      MediaBrowserServiceCompat.this.mHandler.post(new Runnable()
      {
        public void run()
        {
          MediaBrowserServiceCompat.this.performLoadItem(paramString, paramResultReceiver);
        }
      });
    }
    
    public void removeSubscription(final String paramString, final IMediaBrowserServiceCompatCallbacks paramIMediaBrowserServiceCompatCallbacks)
    {
      MediaBrowserServiceCompat.this.mHandler.post(new Runnable()
      {
        public void run()
        {
          Object localObject = paramIMediaBrowserServiceCompatCallbacks.asBinder();
          localObject = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(localObject);
          if (localObject == null) {
            Log.w("MediaBrowserServiceCompat", "removeSubscription for callback that isn't registered id=" + paramString);
          }
          while (((MediaBrowserServiceCompat.ConnectionRecord)localObject).subscriptions.remove(paramString)) {
            return;
          }
          Log.w("MediaBrowserServiceCompat", "removeSubscription called for " + paramString + " which is not subscribed");
        }
      });
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/android/support/v4/media/MediaBrowserServiceCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */