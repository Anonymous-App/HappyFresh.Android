package com.adjust.sdk;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONObject;

public class PackageHandler
  extends HandlerThread
  implements IPackageHandler
{
  private static final String PACKAGE_QUEUE_FILENAME = "AdjustIoPackageQueue";
  private static final String PACKAGE_QUEUE_NAME = "Package queue";
  private IActivityHandler activityHandler;
  private Context context;
  private final InternalHandler internalHandler;
  private AtomicBoolean isSending;
  private ILogger logger;
  private List<ActivityPackage> packageQueue;
  private boolean paused;
  private IRequestHandler requestHandler;
  
  public PackageHandler(IActivityHandler paramIActivityHandler, Context paramContext, boolean paramBoolean)
  {
    super("Adjust", 1);
    setDaemon(true);
    start();
    this.internalHandler = new InternalHandler(getLooper(), this);
    this.logger = AdjustFactory.getLogger();
    init(paramIActivityHandler, paramContext, paramBoolean);
    paramIActivityHandler = Message.obtain();
    paramIActivityHandler.arg1 = 1;
    this.internalHandler.sendMessage(paramIActivityHandler);
  }
  
  private void addInternal(ActivityPackage paramActivityPackage)
  {
    this.packageQueue.add(paramActivityPackage);
    this.logger.debug("Added package %d (%s)", new Object[] { Integer.valueOf(this.packageQueue.size()), paramActivityPackage });
    this.logger.verbose("%s", new Object[] { paramActivityPackage.getExtendedString() });
    writePackageQueue();
  }
  
  public static Boolean deletePackageQueue(Context paramContext)
  {
    return Boolean.valueOf(paramContext.deleteFile("AdjustIoPackageQueue"));
  }
  
  private void initInternal()
  {
    this.requestHandler = AdjustFactory.getRequestHandler(this);
    this.isSending = new AtomicBoolean();
    readPackageQueue();
  }
  
  private void readPackageQueue()
  {
    this.packageQueue = ((List)Util.readObject(this.context, "AdjustIoPackageQueue", "Package queue"));
    if (this.packageQueue != null)
    {
      this.logger.debug("Package handler read %d packages", new Object[] { Integer.valueOf(this.packageQueue.size()) });
      return;
    }
    this.packageQueue = new ArrayList();
  }
  
  private void sendFirstInternal()
  {
    if (this.packageQueue.isEmpty()) {
      return;
    }
    if (this.paused)
    {
      this.logger.debug("Package handler is paused", new Object[0]);
      return;
    }
    if (this.isSending.getAndSet(true))
    {
      this.logger.verbose("Package handler is already sending", new Object[0]);
      return;
    }
    ActivityPackage localActivityPackage = (ActivityPackage)this.packageQueue.get(0);
    this.requestHandler.sendPackage(localActivityPackage);
  }
  
  private void sendNextInternal()
  {
    this.packageQueue.remove(0);
    writePackageQueue();
    this.isSending.set(false);
    sendFirstInternal();
  }
  
  private void writePackageQueue()
  {
    Util.writeObject(this.packageQueue, this.context, "AdjustIoPackageQueue", "Package queue");
    this.logger.debug("Package handler wrote %d packages", new Object[] { Integer.valueOf(this.packageQueue.size()) });
  }
  
  public void addPackage(ActivityPackage paramActivityPackage)
  {
    Message localMessage = Message.obtain();
    localMessage.arg1 = 2;
    localMessage.obj = paramActivityPackage;
    this.internalHandler.sendMessage(localMessage);
  }
  
  public void closeFirstPackage()
  {
    this.isSending.set(false);
  }
  
  public void finishedTrackingActivity(JSONObject paramJSONObject)
  {
    this.activityHandler.finishedTrackingActivity(paramJSONObject);
  }
  
  public String getFailureMessage()
  {
    return "Will retry later.";
  }
  
  public void init(IActivityHandler paramIActivityHandler, Context paramContext, boolean paramBoolean)
  {
    this.activityHandler = paramIActivityHandler;
    this.context = paramContext;
    this.paused = paramBoolean;
  }
  
  public void pauseSending()
  {
    this.paused = true;
  }
  
  public void resumeSending()
  {
    this.paused = false;
  }
  
  public void sendClickPackage(ActivityPackage paramActivityPackage)
  {
    this.logger.debug("Sending click package (%s)", new Object[] { paramActivityPackage });
    this.logger.verbose("%s", new Object[] { paramActivityPackage.getExtendedString() });
    this.requestHandler.sendClickPackage(paramActivityPackage);
  }
  
  public void sendFirstPackage()
  {
    Message localMessage = Message.obtain();
    localMessage.arg1 = 4;
    this.internalHandler.sendMessage(localMessage);
  }
  
  public void sendNextPackage()
  {
    Message localMessage = Message.obtain();
    localMessage.arg1 = 3;
    this.internalHandler.sendMessage(localMessage);
  }
  
  private static final class InternalHandler
    extends Handler
  {
    private static final int ADD = 2;
    private static final int INIT = 1;
    private static final int SEND_FIRST = 4;
    private static final int SEND_NEXT = 3;
    private final WeakReference<PackageHandler> packageHandlerReference;
    
    protected InternalHandler(Looper paramLooper, PackageHandler paramPackageHandler)
    {
      super();
      this.packageHandlerReference = new WeakReference(paramPackageHandler);
    }
    
    public void handleMessage(Message paramMessage)
    {
      super.handleMessage(paramMessage);
      PackageHandler localPackageHandler = (PackageHandler)this.packageHandlerReference.get();
      if (localPackageHandler == null) {
        return;
      }
      switch (paramMessage.arg1)
      {
      default: 
        return;
      case 1: 
        localPackageHandler.initInternal();
        return;
      case 2: 
        localPackageHandler.addInternal((ActivityPackage)paramMessage.obj);
        return;
      case 4: 
        localPackageHandler.sendFirstInternal();
        return;
      }
      localPackageHandler.sendNextInternal();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/adjust/sdk/PackageHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */