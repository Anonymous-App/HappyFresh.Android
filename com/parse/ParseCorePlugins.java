package com.parse;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

class ParseCorePlugins
{
  static final String FILENAME_CURRENT_CONFIG = "currentConfig";
  static final String FILENAME_CURRENT_INSTALLATION = "currentInstallation";
  static final String FILENAME_CURRENT_USER = "currentUser";
  private static final ParseCorePlugins INSTANCE = new ParseCorePlugins();
  static final String PIN_CURRENT_INSTALLATION = "_currentInstallation";
  static final String PIN_CURRENT_USER = "_currentUser";
  private AtomicReference<ParseAnalyticsController> analyticsController = new AtomicReference();
  private AtomicReference<ParseAuthenticationManager> authenticationController = new AtomicReference();
  private AtomicReference<ParseCloudCodeController> cloudCodeController = new AtomicReference();
  private AtomicReference<ParseConfigController> configController = new AtomicReference();
  private AtomicReference<ParseCurrentInstallationController> currentInstallationController = new AtomicReference();
  private AtomicReference<ParseCurrentUserController> currentUserController = new AtomicReference();
  private AtomicReference<ParseDefaultACLController> defaultACLController = new AtomicReference();
  private AtomicReference<ParseFileController> fileController = new AtomicReference();
  private AtomicReference<LocalIdManager> localIdManager = new AtomicReference();
  private AtomicReference<ParseObjectController> objectController = new AtomicReference();
  private AtomicReference<ParsePushChannelsController> pushChannelsController = new AtomicReference();
  private AtomicReference<ParsePushController> pushController = new AtomicReference();
  private AtomicReference<ParseQueryController> queryController = new AtomicReference();
  private AtomicReference<ParseSessionController> sessionController = new AtomicReference();
  private AtomicReference<ParseUserController> userController = new AtomicReference();
  
  public static ParseCorePlugins getInstance()
  {
    return INSTANCE;
  }
  
  public ParseAnalyticsController getAnalyticsController()
  {
    if (this.analyticsController.get() == null) {
      this.analyticsController.compareAndSet(null, new ParseAnalyticsController(Parse.getEventuallyQueue()));
    }
    return (ParseAnalyticsController)this.analyticsController.get();
  }
  
  public ParseAuthenticationManager getAuthenticationManager()
  {
    if (this.authenticationController.get() == null)
    {
      ParseAuthenticationManager localParseAuthenticationManager = new ParseAuthenticationManager(getCurrentUserController());
      this.authenticationController.compareAndSet(null, localParseAuthenticationManager);
    }
    return (ParseAuthenticationManager)this.authenticationController.get();
  }
  
  public ParseCloudCodeController getCloudCodeController()
  {
    if (this.cloudCodeController.get() == null) {
      this.cloudCodeController.compareAndSet(null, new ParseCloudCodeController(ParsePlugins.get().restClient()));
    }
    return (ParseCloudCodeController)this.cloudCodeController.get();
  }
  
  public ParseConfigController getConfigController()
  {
    if (this.configController.get() == null)
    {
      ParseCurrentConfigController localParseCurrentConfigController = new ParseCurrentConfigController(new File(ParsePlugins.get().getParseDir(), "currentConfig"));
      this.configController.compareAndSet(null, new ParseConfigController(ParsePlugins.get().restClient(), localParseCurrentConfigController));
    }
    return (ParseConfigController)this.configController.get();
  }
  
  public ParseCurrentInstallationController getCurrentInstallationController()
  {
    Object localObject;
    if (this.currentInstallationController.get() == null)
    {
      localObject = new FileObjectStore(ParseInstallation.class, new File(ParsePlugins.get().getParseDir(), "currentInstallation"), ParseObjectCurrentCoder.get());
      if (!Parse.isLocalDatastoreEnabled()) {
        break label93;
      }
      localObject = new OfflineObjectStore(ParseInstallation.class, "_currentInstallation", (ParseObjectStore)localObject);
    }
    label93:
    for (;;)
    {
      localObject = new CachedCurrentInstallationController((ParseObjectStore)localObject, ParsePlugins.get().installationId());
      this.currentInstallationController.compareAndSet(null, localObject);
      return (ParseCurrentInstallationController)this.currentInstallationController.get();
    }
  }
  
  public ParseCurrentUserController getCurrentUserController()
  {
    Object localObject;
    if (this.currentUserController.get() == null)
    {
      localObject = new FileObjectStore(ParseUser.class, new File(Parse.getParseDir(), "currentUser"), ParseUserCurrentCoder.get());
      if (!Parse.isLocalDatastoreEnabled()) {
        break label84;
      }
      localObject = new OfflineObjectStore(ParseUser.class, "_currentUser", (ParseObjectStore)localObject);
    }
    label84:
    for (;;)
    {
      localObject = new CachedCurrentUserController((ParseObjectStore)localObject);
      this.currentUserController.compareAndSet(null, localObject);
      return (ParseCurrentUserController)this.currentUserController.get();
    }
  }
  
  public ParseDefaultACLController getDefaultACLController()
  {
    if (this.defaultACLController.get() == null)
    {
      ParseDefaultACLController localParseDefaultACLController = new ParseDefaultACLController();
      this.defaultACLController.compareAndSet(null, localParseDefaultACLController);
    }
    return (ParseDefaultACLController)this.defaultACLController.get();
  }
  
  public ParseFileController getFileController()
  {
    if (this.fileController.get() == null) {
      this.fileController.compareAndSet(null, new ParseFileController(ParsePlugins.get().restClient(), Parse.getParseCacheDir("files")));
    }
    return (ParseFileController)this.fileController.get();
  }
  
  public LocalIdManager getLocalIdManager()
  {
    if (this.localIdManager.get() == null)
    {
      LocalIdManager localLocalIdManager = new LocalIdManager(Parse.getParseDir());
      this.localIdManager.compareAndSet(null, localLocalIdManager);
    }
    return (LocalIdManager)this.localIdManager.get();
  }
  
  public ParseObjectController getObjectController()
  {
    if (this.objectController.get() == null) {
      this.objectController.compareAndSet(null, new NetworkObjectController(ParsePlugins.get().restClient()));
    }
    return (ParseObjectController)this.objectController.get();
  }
  
  public ParsePushChannelsController getPushChannelsController()
  {
    if (this.pushChannelsController.get() == null) {
      this.pushChannelsController.compareAndSet(null, new ParsePushChannelsController());
    }
    return (ParsePushChannelsController)this.pushChannelsController.get();
  }
  
  public ParsePushController getPushController()
  {
    if (this.pushController.get() == null) {
      this.pushController.compareAndSet(null, new ParsePushController(ParsePlugins.get().restClient()));
    }
    return (ParsePushController)this.pushController.get();
  }
  
  public ParseQueryController getQueryController()
  {
    if (this.queryController.get() == null)
    {
      localObject = new NetworkQueryController(ParsePlugins.get().restClient());
      if (!Parse.isLocalDatastoreEnabled()) {
        break label63;
      }
    }
    label63:
    for (Object localObject = new OfflineQueryController(Parse.getLocalDatastore(), (ParseQueryController)localObject);; localObject = new CacheQueryController((NetworkQueryController)localObject))
    {
      this.queryController.compareAndSet(null, localObject);
      return (ParseQueryController)this.queryController.get();
    }
  }
  
  public ParseSessionController getSessionController()
  {
    if (this.sessionController.get() == null) {
      this.sessionController.compareAndSet(null, new NetworkSessionController(ParsePlugins.get().restClient()));
    }
    return (ParseSessionController)this.sessionController.get();
  }
  
  public ParseUserController getUserController()
  {
    if (this.userController.get() == null) {
      this.userController.compareAndSet(null, new NetworkUserController(ParsePlugins.get().restClient()));
    }
    return (ParseUserController)this.userController.get();
  }
  
  public void registerAnalyticsController(ParseAnalyticsController paramParseAnalyticsController)
  {
    if (!this.analyticsController.compareAndSet(null, paramParseAnalyticsController)) {
      throw new IllegalStateException("Another analytics controller was already registered: " + this.analyticsController.get());
    }
  }
  
  public void registerAuthenticationManager(ParseAuthenticationManager paramParseAuthenticationManager)
  {
    if (!this.authenticationController.compareAndSet(null, paramParseAuthenticationManager)) {
      throw new IllegalStateException("Another authentication manager was already registered: " + this.authenticationController.get());
    }
  }
  
  public void registerCloudCodeController(ParseCloudCodeController paramParseCloudCodeController)
  {
    if (!this.cloudCodeController.compareAndSet(null, paramParseCloudCodeController)) {
      throw new IllegalStateException("Another cloud code controller was already registered: " + this.cloudCodeController.get());
    }
  }
  
  public void registerConfigController(ParseConfigController paramParseConfigController)
  {
    if (!this.configController.compareAndSet(null, paramParseConfigController)) {
      throw new IllegalStateException("Another config controller was already registered: " + this.configController.get());
    }
  }
  
  public void registerCurrentInstallationController(ParseCurrentInstallationController paramParseCurrentInstallationController)
  {
    if (!this.currentInstallationController.compareAndSet(null, paramParseCurrentInstallationController)) {
      throw new IllegalStateException("Another currentInstallation controller was already registered: " + this.currentInstallationController.get());
    }
  }
  
  public void registerCurrentUserController(ParseCurrentUserController paramParseCurrentUserController)
  {
    if (!this.currentUserController.compareAndSet(null, paramParseCurrentUserController)) {
      throw new IllegalStateException("Another currentUser controller was already registered: " + this.currentUserController.get());
    }
  }
  
  public void registerDefaultACLController(ParseDefaultACLController paramParseDefaultACLController)
  {
    if (!this.defaultACLController.compareAndSet(null, paramParseDefaultACLController)) {
      throw new IllegalStateException("Another defaultACL controller was already registered: " + this.defaultACLController.get());
    }
  }
  
  public void registerFileController(ParseFileController paramParseFileController)
  {
    if (!this.fileController.compareAndSet(null, paramParseFileController)) {
      throw new IllegalStateException("Another file controller was already registered: " + this.fileController.get());
    }
  }
  
  public void registerLocalIdManager(LocalIdManager paramLocalIdManager)
  {
    if (!this.localIdManager.compareAndSet(null, paramLocalIdManager)) {
      throw new IllegalStateException("Another localId manager was already registered: " + this.localIdManager.get());
    }
  }
  
  public void registerObjectController(ParseObjectController paramParseObjectController)
  {
    if (!this.objectController.compareAndSet(null, paramParseObjectController)) {
      throw new IllegalStateException("Another object controller was already registered: " + this.objectController.get());
    }
  }
  
  public void registerPushChannelsController(ParsePushChannelsController paramParsePushChannelsController)
  {
    if (!this.pushChannelsController.compareAndSet(null, paramParsePushChannelsController)) {
      throw new IllegalStateException("Another pushChannels controller was already registered: " + this.pushChannelsController.get());
    }
  }
  
  public void registerPushController(ParsePushController paramParsePushController)
  {
    if (!this.pushController.compareAndSet(null, paramParsePushController)) {
      throw new IllegalStateException("Another push controller was already registered: " + this.pushController.get());
    }
  }
  
  public void registerQueryController(ParseQueryController paramParseQueryController)
  {
    if (!this.queryController.compareAndSet(null, paramParseQueryController)) {
      throw new IllegalStateException("Another query controller was already registered: " + this.queryController.get());
    }
  }
  
  public void registerSessionController(ParseSessionController paramParseSessionController)
  {
    if (!this.sessionController.compareAndSet(null, paramParseSessionController)) {
      throw new IllegalStateException("Another session controller was already registered: " + this.sessionController.get());
    }
  }
  
  public void registerUserController(ParseUserController paramParseUserController)
  {
    if (!this.userController.compareAndSet(null, paramParseUserController)) {
      throw new IllegalStateException("Another user controller was already registered: " + this.userController.get());
    }
  }
  
  void reset()
  {
    this.objectController.set(null);
    this.userController.set(null);
    this.sessionController.set(null);
    this.currentUserController.set(null);
    this.currentInstallationController.set(null);
    this.authenticationController.set(null);
    this.queryController.set(null);
    this.fileController.set(null);
    this.analyticsController.set(null);
    this.cloudCodeController.set(null);
    this.configController.set(null);
    this.pushController.set(null);
    this.pushChannelsController.set(null);
    this.defaultACLController.set(null);
    this.localIdManager.set(null);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseCorePlugins.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */