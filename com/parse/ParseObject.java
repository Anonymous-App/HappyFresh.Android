package com.parse;

import bolts.Capture;
import bolts.Continuation;
import bolts.Task;
import bolts.Task.TaskCompletionSource;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseObject
{
  private static final String AUTO_CLASS_NAME = "_Automatic";
  public static final String DEFAULT_PIN = "_default";
  private static final String KEY_ACL = "ACL";
  private static final String KEY_CLASS_NAME = "className";
  private static final String KEY_COMPLETE = "__complete";
  private static final String KEY_CREATED_AT = "createdAt";
  static final String KEY_IS_DELETING_EVENTUALLY = "__isDeletingEventually";
  private static final String KEY_IS_DELETING_EVENTUALLY_OLD = "isDeletingEventually";
  private static final String KEY_OBJECT_ID = "objectId";
  private static final String KEY_OPERATIONS = "__operations";
  private static final String KEY_UPDATED_AT = "updatedAt";
  private static final String NEW_OFFLINE_OBJECT_ID_PLACEHOLDER = "*** Offline Object ***";
  static final String VERSION_NAME = "1.10.2";
  private static final Map<Class<? extends ParseObject>, String> classNames;
  private static final ThreadLocal<String> isCreatingPointerForObjectId = new ThreadLocal()
  {
    protected String initialValue()
    {
      return null;
    }
  };
  private static final Map<String, Class<? extends ParseObject>> objectTypes;
  static String server = "https://api.parse.com";
  private final Map<String, Object> estimatedData;
  private final Map<Object, ParseJSONCacheItem> hashedObjects;
  boolean isDeleted;
  int isDeletingEventually;
  private String localId;
  final Object mutex = new Object();
  final LinkedList<ParseOperationSet> operationSetQueue;
  private final ParseMulticastDelegate<ParseObject> saveEvent = new ParseMulticastDelegate();
  private State state;
  final TaskQueue taskQueue = new TaskQueue();
  
  static
  {
    classNames = new ConcurrentHashMap();
    objectTypes = new ConcurrentHashMap();
  }
  
  protected ParseObject()
  {
    this("_Automatic");
  }
  
  public ParseObject(String paramString)
  {
    String str2 = (String)isCreatingPointerForObjectId.get();
    if (paramString == null) {
      throw new IllegalArgumentException("You must specify a Parse class name when creating a new ParseObject.");
    }
    String str1 = paramString;
    if ("_Automatic".equals(paramString)) {
      str1 = getClassName(getClass());
    }
    if ((getClass().equals(ParseObject.class)) && (objectTypes.containsKey(str1)) && (!((Class)objectTypes.get(str1)).isInstance(this))) {
      throw new IllegalArgumentException("You must create this type of ParseObject using ParseObject.create() or the proper subclass.");
    }
    if ((!getClass().equals(ParseObject.class)) && (!getClass().equals(objectTypes.get(str1)))) {
      throw new IllegalArgumentException("You must register this ParseObject subclass before instantiating it.");
    }
    this.operationSetQueue = new LinkedList();
    this.operationSetQueue.add(new ParseOperationSet());
    this.estimatedData = new HashMap();
    this.hashedObjects = new IdentityHashMap();
    paramString = newStateBuilder(str1);
    if (str2 == null)
    {
      setDefaultValues();
      paramString.isComplete(true);
    }
    for (;;)
    {
      this.state = paramString.build();
      paramString = Parse.getLocalDatastore();
      if (paramString != null) {
        paramString.registerNewObject(this);
      }
      return;
      if (!str2.equals("*** Offline Object ***")) {
        paramString.objectId(str2);
      }
      paramString.isComplete(false);
    }
  }
  
  private void addToHashedObjects(Object paramObject)
  {
    synchronized (this.mutex)
    {
      try
      {
        this.hashedObjects.put(paramObject, new ParseJSONCacheItem(paramObject));
        return;
      }
      catch (JSONException paramObject)
      {
        throw new IllegalArgumentException("Couldn't serialize container value to JSON.");
      }
    }
  }
  
  private void applyOperations(ParseOperationSet paramParseOperationSet, Map<String, Object> paramMap)
  {
    Iterator localIterator = paramParseOperationSet.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Object localObject = ((ParseFieldOperation)paramParseOperationSet.get(str)).apply(paramMap.get(str), str);
      if (localObject != null) {
        paramMap.put(str, localObject);
      } else {
        paramMap.remove(str);
      }
    }
  }
  
  private boolean canBeSerialized()
  {
    synchronized (this.mutex)
    {
      final Capture localCapture = new Capture(Boolean.valueOf(true));
      new ParseTraverser()
      {
        protected boolean visit(Object paramAnonymousObject)
        {
          if (((paramAnonymousObject instanceof ParseFile)) && (((ParseFile)paramAnonymousObject).isDirty())) {
            localCapture.set(Boolean.valueOf(false));
          }
          if (((paramAnonymousObject instanceof ParseObject)) && (((ParseObject)paramAnonymousObject).getObjectId() == null)) {
            localCapture.set(Boolean.valueOf(false));
          }
          return ((Boolean)localCapture.get()).booleanValue();
        }
      }.setYieldRoot(false).setTraverseParseObjects(true).traverse(this);
      boolean bool = ((Boolean)localCapture.get()).booleanValue();
      return bool;
    }
  }
  
  private void checkForChangesToMutableContainer(String paramString, Object paramObject)
  {
    ParseJSONCacheItem localParseJSONCacheItem1;
    synchronized (this.mutex)
    {
      if (isContainerObject(paramString, paramObject))
      {
        localParseJSONCacheItem1 = (ParseJSONCacheItem)this.hashedObjects.get(paramObject);
        if (localParseJSONCacheItem1 == null) {
          throw new IllegalArgumentException("ParseObject contains container item that isn't cached.");
        }
      }
    }
    for (;;)
    {
      try
      {
        ParseJSONCacheItem localParseJSONCacheItem2 = new ParseJSONCacheItem(paramObject);
        if (!localParseJSONCacheItem1.equals(localParseJSONCacheItem2)) {
          performOperation(paramString, new ParseSetOperation(paramObject));
        }
        return;
      }
      catch (JSONException paramString)
      {
        throw new RuntimeException(paramString);
      }
      this.hashedObjects.remove(paramObject);
    }
  }
  
  private void checkGetAccess(String paramString)
  {
    if (!isDataAvailable(paramString)) {
      throw new IllegalStateException("ParseObject has no data for '" + paramString + "'. Call fetchIfNeeded() to get the data.");
    }
  }
  
  private void checkKeyIsMutable(String paramString)
  {
    if (!isKeyMutable(paramString)) {
      throw new IllegalArgumentException("Cannot modify `" + paramString + "` property of an " + getClassName() + " object.");
    }
  }
  
  private void checkpointAllMutableContainers()
  {
    synchronized (this.mutex)
    {
      Iterator localIterator = this.estimatedData.entrySet().iterator();
      if (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        checkpointMutableContainer((String)localEntry.getKey(), localEntry.getValue());
      }
    }
  }
  
  private void checkpointMutableContainer(String paramString, Object paramObject)
  {
    synchronized (this.mutex)
    {
      if (isContainerObject(paramString, paramObject)) {
        addToHashedObjects(paramObject);
      }
      return;
    }
  }
  
  private static void collectDirtyChildren(Object paramObject, Collection<ParseObject> paramCollection, Collection<ParseFile> paramCollection1)
  {
    collectDirtyChildren(paramObject, paramCollection, paramCollection1, new HashSet(), new HashSet());
  }
  
  private static void collectDirtyChildren(Object paramObject, final Collection<ParseObject> paramCollection, Collection<ParseFile> paramCollection1, final Set<ParseObject> paramSet1, final Set<ParseObject> paramSet2)
  {
    new ParseTraverser()
    {
      protected boolean visit(Object paramAnonymousObject)
      {
        if ((paramAnonymousObject instanceof ParseFile)) {
          if (this.val$dirtyFiles != null) {}
        }
        label188:
        for (;;)
        {
          return true;
          paramAnonymousObject = (ParseFile)paramAnonymousObject;
          if (((ParseFile)paramAnonymousObject).getUrl() == null)
          {
            this.val$dirtyFiles.add(paramAnonymousObject);
            return true;
            if (((paramAnonymousObject instanceof ParseObject)) && (paramCollection != null))
            {
              ParseObject localParseObject = (ParseObject)paramAnonymousObject;
              Object localObject = paramSet1;
              paramAnonymousObject = paramSet2;
              if (localParseObject.getObjectId() != null) {
                paramAnonymousObject = new HashSet();
              }
              for (;;)
              {
                if (((Set)localObject).contains(localParseObject)) {
                  break label188;
                }
                localObject = new HashSet((Collection)localObject);
                ((Set)localObject).add(localParseObject);
                ParseObject.collectDirtyChildren(localParseObject.estimatedData, paramCollection, this.val$dirtyFiles, (Set)localObject, (Set)paramAnonymousObject);
                if (!localParseObject.isDirty(false)) {
                  break;
                }
                paramCollection.add(localParseObject);
                return true;
                if (((Set)paramAnonymousObject).contains(localParseObject)) {
                  throw new RuntimeException("Found a circular dependency while saving.");
                }
                paramAnonymousObject = new HashSet((Collection)paramAnonymousObject);
                ((Set)paramAnonymousObject).add(localParseObject);
              }
            }
          }
        }
      }
    }.setYieldRoot(true).traverse(paramObject);
  }
  
  private Map<String, ParseObject> collectFetchedObjects()
  {
    final HashMap localHashMap = new HashMap();
    new ParseTraverser()
    {
      protected boolean visit(Object paramAnonymousObject)
      {
        if ((paramAnonymousObject instanceof ParseObject))
        {
          paramAnonymousObject = (ParseObject)paramAnonymousObject;
          ParseObject.State localState = ((ParseObject)paramAnonymousObject).getState();
          if ((localState.objectId() != null) && (localState.isComplete())) {
            localHashMap.put(localState.objectId(), paramAnonymousObject);
          }
        }
        return true;
      }
    }.traverse(this.estimatedData);
    return localHashMap;
  }
  
  public static <T extends ParseObject> T create(Class<T> paramClass)
  {
    return create(getClassName(paramClass));
  }
  
  public static ParseObject create(String paramString)
  {
    if (objectTypes.containsKey(paramString)) {
      try
      {
        paramString = (ParseObject)((Class)objectTypes.get(paramString)).newInstance();
        return paramString;
      }
      catch (Exception paramString)
      {
        if ((paramString instanceof RuntimeException)) {
          throw ((RuntimeException)paramString);
        }
        throw new RuntimeException("Failed to create instance of subclass.", paramString);
      }
    }
    return new ParseObject(paramString);
  }
  
  public static <T extends ParseObject> T createWithoutData(Class<T> paramClass, String paramString)
  {
    return createWithoutData(getClassName(paramClass), paramString);
  }
  
  /* Error */
  public static ParseObject createWithoutData(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: invokestatic 330	com/parse/Parse:getLocalDatastore	()Lcom/parse/OfflineStore;
    //   3: astore 4
    //   5: aload_1
    //   6: ifnonnull +78 -> 84
    //   9: getstatic 226	com/parse/ParseObject:isCreatingPointerForObjectId	Ljava/lang/ThreadLocal;
    //   12: ldc -80
    //   14: invokevirtual 588	java/lang/ThreadLocal:set	(Ljava/lang/Object;)V
    //   17: aconst_null
    //   18: astore_3
    //   19: aload_3
    //   20: astore_2
    //   21: aload 4
    //   23: ifnull +17 -> 40
    //   26: aload_3
    //   27: astore_2
    //   28: aload_1
    //   29: ifnull +11 -> 40
    //   32: aload 4
    //   34: aload_0
    //   35: aload_1
    //   36: invokevirtual 591	com/parse/OfflineStore:getObject	(Ljava/lang/String;Ljava/lang/String;)Lcom/parse/ParseObject;
    //   39: astore_2
    //   40: aload_2
    //   41: astore_1
    //   42: aload_2
    //   43: ifnonnull +64 -> 107
    //   46: aload_0
    //   47: invokestatic 568	com/parse/ParseObject:create	(Ljava/lang/String;)Lcom/parse/ParseObject;
    //   50: astore_0
    //   51: aload_0
    //   52: astore_1
    //   53: aload_0
    //   54: invokevirtual 594	com/parse/ParseObject:hasChanges	()Z
    //   57: ifeq +50 -> 107
    //   60: new 504	java/lang/IllegalStateException
    //   63: dup
    //   64: ldc_w 596
    //   67: invokespecial 520	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   70: athrow
    //   71: astore_0
    //   72: aload_0
    //   73: athrow
    //   74: astore_0
    //   75: getstatic 226	com/parse/ParseObject:isCreatingPointerForObjectId	Ljava/lang/ThreadLocal;
    //   78: aconst_null
    //   79: invokevirtual 588	java/lang/ThreadLocal:set	(Ljava/lang/Object;)V
    //   82: aload_0
    //   83: athrow
    //   84: getstatic 226	com/parse/ParseObject:isCreatingPointerForObjectId	Ljava/lang/ThreadLocal;
    //   87: aload_1
    //   88: invokevirtual 588	java/lang/ThreadLocal:set	(Ljava/lang/Object;)V
    //   91: goto -74 -> 17
    //   94: astore_0
    //   95: new 494	java/lang/RuntimeException
    //   98: dup
    //   99: ldc_w 576
    //   102: aload_0
    //   103: invokespecial 579	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   106: athrow
    //   107: getstatic 226	com/parse/ParseObject:isCreatingPointerForObjectId	Ljava/lang/ThreadLocal;
    //   110: aconst_null
    //   111: invokevirtual 588	java/lang/ThreadLocal:set	(Ljava/lang/Object;)V
    //   114: aload_1
    //   115: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	116	0	paramString1	String
    //   0	116	1	paramString2	String
    //   20	23	2	localObject1	Object
    //   18	9	3	localObject2	Object
    //   3	30	4	localOfflineStore	OfflineStore
    // Exception table:
    //   from	to	target	type
    //   9	17	71	java/lang/RuntimeException
    //   32	40	71	java/lang/RuntimeException
    //   46	51	71	java/lang/RuntimeException
    //   53	71	71	java/lang/RuntimeException
    //   84	91	71	java/lang/RuntimeException
    //   9	17	74	finally
    //   32	40	74	finally
    //   46	51	74	finally
    //   53	71	74	finally
    //   72	74	74	finally
    //   84	91	74	finally
    //   95	107	74	finally
    //   9	17	94	java/lang/Exception
    //   32	40	94	java/lang/Exception
    //   46	51	94	java/lang/Exception
    //   53	71	94	java/lang/Exception
    //   84	91	94	java/lang/Exception
  }
  
  private ParseOperationSet currentOperations()
  {
    synchronized (this.mutex)
    {
      ParseOperationSet localParseOperationSet = (ParseOperationSet)this.operationSetQueue.getLast();
      return localParseOperationSet;
    }
  }
  
  private ParseRESTObjectCommand currentSaveEventuallyCommand(ParseOperationSet paramParseOperationSet, ParseEncoder paramParseEncoder, String paramString)
    throws ParseException
  {
    State localState = getState();
    paramParseOperationSet = ParseRESTObjectCommand.saveObjectCommand(localState, toJSONObjectForSaving(localState, paramParseOperationSet, paramParseEncoder), paramString);
    paramParseOperationSet.enableRetrying();
    return paramParseOperationSet;
  }
  
  private static Task<Void> deepSaveAsync(final Object paramObject, final String paramString)
  {
    Object localObject1 = new HashSet();
    Object localObject3 = new HashSet();
    collectDirtyChildren(paramObject, (Collection)localObject1, (Collection)localObject3);
    Object localObject2 = new HashSet();
    paramObject = ((Set)localObject1).iterator();
    while (((Iterator)paramObject).hasNext())
    {
      localObject4 = (ParseObject)((Iterator)paramObject).next();
      if (((localObject4 instanceof ParseUser)) && (((ParseUser)localObject4).isLazy())) {
        ((Set)localObject2).add((ParseUser)localObject4);
      }
    }
    ((Set)localObject1).removeAll((Collection)localObject2);
    paramObject = new AtomicBoolean(false);
    final Object localObject4 = new ArrayList();
    localObject3 = ((Set)localObject3).iterator();
    while (((Iterator)localObject3).hasNext()) {
      ((List)localObject4).add(((ParseFile)((Iterator)localObject3).next()).saveAsync(paramString, null, null));
    }
    localObject3 = Task.whenAll((Collection)localObject4).continueWith(new Continuation()
    {
      public Void then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        this.val$filesComplete.set(true);
        return null;
      }
    });
    localObject4 = new AtomicBoolean(false);
    ArrayList localArrayList = new ArrayList();
    localObject2 = ((Set)localObject2).iterator();
    while (((Iterator)localObject2).hasNext()) {
      localArrayList.add(((ParseUser)((Iterator)localObject2).next()).saveAsync(paramString));
    }
    localObject2 = Task.whenAll(localArrayList).continueWith(new Continuation()
    {
      public Void then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        this.val$usersComplete.set(true);
        return null;
      }
    });
    localObject1 = new Capture(localObject1);
    Task.whenAll(Arrays.asList(new Task[] { localObject3, localObject2, Task.forResult(null).continueWhile(new Callable()new Continuation
    {
      public Boolean call()
        throws Exception
      {
        if (((Set)this.val$remaining.get()).size() > 0) {}
        for (boolean bool = true;; bool = false) {
          return Boolean.valueOf(bool);
        }
      }
    }, new Continuation()
    {
      public Task<Void> then(final Task<Void> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = new ArrayList();
        HashSet localHashSet = new HashSet();
        Iterator localIterator = ((Set)this.val$remaining.get()).iterator();
        while (localIterator.hasNext())
        {
          ParseObject localParseObject = (ParseObject)localIterator.next();
          if (localParseObject.canBeSerialized()) {
            paramAnonymousTask.add(localParseObject);
          } else {
            localHashSet.add(localParseObject);
          }
        }
        this.val$remaining.set(localHashSet);
        if ((paramAnonymousTask.size() == 0) && (paramObject.get()) && (localObject4.get())) {
          throw new RuntimeException("Unable to save a ParseObject with a relation to a cycle.");
        }
        if (paramAnonymousTask.size() == 0) {
          return Task.forResult(null);
        }
        ParseObject.enqueueForAll(paramAnonymousTask, new Continuation()
        {
          public Task<Void> then(Task<Void> paramAnonymous2Task)
            throws Exception
          {
            return ParseObject.saveAllAsync(paramAnonymousTask, ParseObject.41.this.val$sessionToken, paramAnonymous2Task);
          }
        });
      }
    }) }));
  }
  
  public static <T extends ParseObject> void deleteAll(List<T> paramList)
    throws ParseException
  {
    ParseTaskUtils.wait(deleteAllInBackground(paramList));
  }
  
  private static <T extends ParseObject> Task<Void> deleteAllAsync(List<T> paramList, final String paramString)
  {
    if (paramList.size() == 0) {
      return Task.forResult(null);
    }
    int j = paramList.size();
    ArrayList localArrayList = new ArrayList(j);
    HashSet localHashSet = new HashSet();
    int i = 0;
    while (i < j)
    {
      ParseObject localParseObject = (ParseObject)paramList.get(i);
      if (!localHashSet.contains(localParseObject.getObjectId()))
      {
        localHashSet.add(localParseObject.getObjectId());
        localArrayList.add(localParseObject);
      }
      i += 1;
    }
    enqueueForAll(localArrayList, new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return ParseObject.deleteAllAsync(this.val$uniqueObjects, paramString, paramAnonymousTask);
      }
    });
  }
  
  private static <T extends ParseObject> Task<Void> deleteAllAsync(List<T> paramList, final String paramString, Task<Void> paramTask)
  {
    paramTask.continueWithTask(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        int j = this.val$uniqueObjects.size();
        paramAnonymousTask = new ArrayList(j);
        int i = 0;
        while (i < j)
        {
          localObject = (ParseObject)this.val$uniqueObjects.get(i);
          ((ParseObject)localObject).validateDelete();
          paramAnonymousTask.add(((ParseObject)localObject).getState());
          i += 1;
        }
        paramAnonymousTask = ParseObject.access$800().deleteAllAsync(paramAnonymousTask, paramString);
        Object localObject = new ArrayList(j);
        i = 0;
        while (i < j)
        {
          ((List)localObject).add(((Task)paramAnonymousTask.get(i)).onSuccessTask(new Continuation()
          {
            public Task<Void> then(final Task<Void> paramAnonymous2Task)
              throws Exception
            {
              this.val$object.handleDeleteResultAsync().continueWithTask(new Continuation()
              {
                public Task<Void> then(Task<Void> paramAnonymous3Task)
                  throws Exception
                {
                  return paramAnonymous2Task;
                }
              });
            }
          }));
          i += 1;
        }
        return Task.whenAll((Collection)localObject);
      }
    });
  }
  
  public static <T extends ParseObject> Task<Void> deleteAllInBackground(List<T> paramList)
  {
    ParseUser.getCurrentSessionTokenAsync().onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<String> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (String)paramAnonymousTask.getResult();
        return ParseObject.deleteAllAsync(this.val$objects, paramAnonymousTask);
      }
    });
  }
  
  public static <T extends ParseObject> void deleteAllInBackground(List<T> paramList, DeleteCallback paramDeleteCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(deleteAllInBackground(paramList), paramDeleteCallback);
  }
  
  private Task<Void> deleteAsync(final String paramString, Task<Void> paramTask)
  {
    validateDelete();
    paramTask.onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        if (ParseObject.this.state.objectId() == null) {
          return paramAnonymousTask.cast();
        }
        return ParseObject.this.deleteAsync(paramString);
      }
    }).onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return ParseObject.this.handleDeleteResultAsync();
      }
    });
  }
  
  /* Error */
  static <T> Task<T> enqueueForAll(List<? extends ParseObject> paramList, final Continuation<Void, Task<T>> paramContinuation)
  {
    // Byte code:
    //   0: invokestatic 760	bolts/Task:create	()Lbolts/Task$TaskCompletionSource;
    //   3: astore_3
    //   4: new 641	java/util/ArrayList
    //   7: dup
    //   8: aload_0
    //   9: invokeinterface 706 1 0
    //   14: invokespecial 709	java/util/ArrayList:<init>	(I)V
    //   17: astore_2
    //   18: aload_0
    //   19: invokeinterface 761 1 0
    //   24: astore 4
    //   26: aload 4
    //   28: invokeinterface 431 1 0
    //   33: ifeq +29 -> 62
    //   36: aload_2
    //   37: aload 4
    //   39: invokeinterface 434 1 0
    //   44: checkcast 2	com/parse/ParseObject
    //   47: getfield 238	com/parse/ParseObject:taskQueue	Lcom/parse/TaskQueue;
    //   50: invokevirtual 765	com/parse/TaskQueue:getLock	()Ljava/util/concurrent/locks/Lock;
    //   53: invokeinterface 651 2 0
    //   58: pop
    //   59: goto -33 -> 26
    //   62: new 767	com/parse/LockSet
    //   65: dup
    //   66: aload_2
    //   67: invokespecial 770	com/parse/LockSet:<init>	(Ljava/util/Collection;)V
    //   70: astore_2
    //   71: aload_2
    //   72: invokevirtual 773	com/parse/LockSet:lock	()V
    //   75: aload_1
    //   76: aload_3
    //   77: invokevirtual 778	bolts/Task$TaskCompletionSource:getTask	()Lbolts/Task;
    //   80: invokeinterface 783 2 0
    //   85: checkcast 653	bolts/Task
    //   88: astore_1
    //   89: new 641	java/util/ArrayList
    //   92: dup
    //   93: invokespecial 642	java/util/ArrayList:<init>	()V
    //   96: astore 4
    //   98: aload_0
    //   99: invokeinterface 761 1 0
    //   104: astore_0
    //   105: aload_0
    //   106: invokeinterface 431 1 0
    //   111: ifeq +52 -> 163
    //   114: aload_0
    //   115: invokeinterface 434 1 0
    //   120: checkcast 2	com/parse/ParseObject
    //   123: getfield 238	com/parse/ParseObject:taskQueue	Lcom/parse/TaskQueue;
    //   126: new 34	com/parse/ParseObject$2
    //   129: dup
    //   130: aload 4
    //   132: aload_1
    //   133: invokespecial 786	com/parse/ParseObject$2:<init>	(Ljava/util/List;Lbolts/Task;)V
    //   136: invokevirtual 789	com/parse/TaskQueue:enqueue	(Lbolts/Continuation;)Lbolts/Task;
    //   139: pop
    //   140: goto -35 -> 105
    //   143: astore_0
    //   144: aload_2
    //   145: invokevirtual 792	com/parse/LockSet:unlock	()V
    //   148: aload_0
    //   149: athrow
    //   150: astore_0
    //   151: aload_0
    //   152: athrow
    //   153: astore_0
    //   154: new 494	java/lang/RuntimeException
    //   157: dup
    //   158: aload_0
    //   159: invokespecial 497	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   162: athrow
    //   163: aload 4
    //   165: invokestatic 657	bolts/Task:whenAll	(Ljava/util/Collection;)Lbolts/Task;
    //   168: new 60	com/parse/ParseObject$3
    //   171: dup
    //   172: aload_3
    //   173: invokespecial 795	com/parse/ParseObject$3:<init>	(Lbolts/Task$TaskCompletionSource;)V
    //   176: invokevirtual 664	bolts/Task:continueWith	(Lbolts/Continuation;)Lbolts/Task;
    //   179: pop
    //   180: aload_2
    //   181: invokevirtual 792	com/parse/LockSet:unlock	()V
    //   184: aload_1
    //   185: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	186	0	paramList	List<? extends ParseObject>
    //   0	186	1	paramContinuation	Continuation<Void, Task<T>>
    //   17	164	2	localObject1	Object
    //   3	170	3	localTaskCompletionSource	Task.TaskCompletionSource
    //   24	140	4	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   75	89	143	finally
    //   89	105	143	finally
    //   105	140	143	finally
    //   151	153	143	finally
    //   154	163	143	finally
    //   163	180	143	finally
    //   75	89	150	java/lang/RuntimeException
    //   75	89	153	java/lang/Exception
  }
  
  private Task<Void> enqueueSaveEventuallyOperationAsync(final ParseOperationSet paramParseOperationSet)
  {
    if (!paramParseOperationSet.isSaveEventually()) {
      throw new IllegalStateException("This should only be used to enqueue saveEventually operation sets");
    }
    this.taskQueue.enqueue(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask.continueWithTask(new Continuation()
        {
          public Task<Void> then(Task<Void> paramAnonymous2Task)
            throws Exception
          {
            return Parse.getEventuallyQueue().waitForOperationSetAndEventuallyPin(ParseObject.15.this.val$operationSet, null).makeVoid();
          }
        });
      }
    });
  }
  
  public static <T extends ParseObject> List<T> fetchAll(List<T> paramList)
    throws ParseException
  {
    return (List)ParseTaskUtils.wait(fetchAllInBackground(paramList));
  }
  
  private static <T extends ParseObject> Task<List<T>> fetchAllAsync(List<T> paramList, final ParseUser paramParseUser, final boolean paramBoolean, Task<Void> paramTask)
  {
    if (paramList.size() == 0) {
      return Task.forResult(paramList);
    }
    ArrayList localArrayList = new ArrayList();
    Object localObject = null;
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      ParseObject localParseObject = (ParseObject)localIterator.next();
      if ((!paramBoolean) || (!localParseObject.isDataAvailable()))
      {
        if ((localObject != null) && (!localParseObject.getClassName().equals(localObject))) {
          throw new IllegalArgumentException("All objects should have the same class");
        }
        localObject = localParseObject.getClassName();
        if (localParseObject.getObjectId() != null) {
          localArrayList.add(localParseObject.getObjectId());
        } else if (!paramBoolean) {
          throw new IllegalArgumentException("All objects must exist on the server");
        }
      }
    }
    if (localArrayList.size() == 0) {
      return Task.forResult(paramList);
    }
    paramTask.continueWithTask(new Continuation()
    {
      public Task<List<T>> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return this.val$query.findAsync(this.val$query.getBuilder().build(), paramParseUser, null);
      }
    }).onSuccess(new Continuation()
    {
      public List<T> then(Task<List<T>> paramAnonymousTask)
        throws Exception
      {
        HashMap localHashMap = new HashMap();
        paramAnonymousTask = ((List)paramAnonymousTask.getResult()).iterator();
        ParseObject localParseObject1;
        while (paramAnonymousTask.hasNext())
        {
          localParseObject1 = (ParseObject)paramAnonymousTask.next();
          localHashMap.put(localParseObject1.getObjectId(), localParseObject1);
        }
        paramAnonymousTask = this.val$objects.iterator();
        while (paramAnonymousTask.hasNext())
        {
          localParseObject1 = (ParseObject)paramAnonymousTask.next();
          if ((!paramBoolean) || (!localParseObject1.isDataAvailable()))
          {
            ParseObject localParseObject2 = (ParseObject)localHashMap.get(localParseObject1.getObjectId());
            if (localParseObject2 == null) {
              throw new ParseException(101, "Object id " + localParseObject1.getObjectId() + " does not exist");
            }
            if (!Parse.isLocalDatastoreEnabled()) {
              localParseObject1.mergeFromObject(localParseObject2);
            }
          }
        }
        return this.val$objects;
      }
    });
  }
  
  private static <T extends ParseObject> Task<List<T>> fetchAllAsync(List<T> paramList, final boolean paramBoolean)
  {
    ParseUser.getCurrentUserAsync().onSuccessTask(new Continuation()
    {
      public Task<List<T>> then(final Task<ParseUser> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (ParseUser)paramAnonymousTask.getResult();
        ParseObject.enqueueForAll(this.val$objects, new Continuation()
        {
          public Task<List<T>> then(Task<Void> paramAnonymous2Task)
            throws Exception
          {
            return ParseObject.fetchAllAsync(ParseObject.45.this.val$objects, paramAnonymousTask, ParseObject.45.this.val$onlyIfNeeded, paramAnonymous2Task);
          }
        });
      }
    });
  }
  
  public static <T extends ParseObject> List<T> fetchAllIfNeeded(List<T> paramList)
    throws ParseException
  {
    return (List)ParseTaskUtils.wait(fetchAllIfNeededInBackground(paramList));
  }
  
  public static <T extends ParseObject> Task<List<T>> fetchAllIfNeededInBackground(List<T> paramList)
  {
    return fetchAllAsync(paramList, true);
  }
  
  public static <T extends ParseObject> void fetchAllIfNeededInBackground(List<T> paramList, FindCallback<T> paramFindCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(fetchAllIfNeededInBackground(paramList), paramFindCallback);
  }
  
  public static <T extends ParseObject> Task<List<T>> fetchAllInBackground(List<T> paramList)
  {
    return fetchAllAsync(paramList, false);
  }
  
  public static <T extends ParseObject> void fetchAllInBackground(List<T> paramList, FindCallback<T> paramFindCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(fetchAllInBackground(paramList), paramFindCallback);
  }
  
  static <T extends ParseObject> T from(State paramState)
  {
    ParseObject localParseObject = createWithoutData(paramState.className(), paramState.objectId());
    synchronized (localParseObject.mutex)
    {
      if (paramState.isComplete())
      {
        localParseObject.setState(paramState);
        return localParseObject;
      }
      paramState = localParseObject.getState().newBuilder().apply(paramState).build();
    }
  }
  
  static <T extends ParseObject> T fromJSON(JSONObject paramJSONObject, String paramString, boolean paramBoolean)
  {
    return fromJSON(paramJSONObject, paramString, paramBoolean, ParseDecoder.get());
  }
  
  static <T extends ParseObject> T fromJSON(JSONObject paramJSONObject, String paramString, boolean paramBoolean, ParseDecoder paramParseDecoder)
  {
    paramString = paramJSONObject.optString("className", paramString);
    if (paramString == null) {
      return null;
    }
    paramString = createWithoutData(paramString, paramJSONObject.optString("objectId", null));
    paramString.setState(paramString.mergeFromServer(paramString.getState(), paramJSONObject, paramParseDecoder, paramBoolean));
    return paramString;
  }
  
  static <T extends ParseObject> T fromJSONPayload(JSONObject paramJSONObject, ParseDecoder paramParseDecoder)
  {
    Object localObject = paramJSONObject.optString("className");
    if ((localObject == null) || (ParseTextUtils.isEmpty((CharSequence)localObject))) {
      return null;
    }
    localObject = createWithoutData((String)localObject, paramJSONObject.optString("objectId", null));
    ((ParseObject)localObject).build(paramJSONObject, paramParseDecoder);
    return (T)localObject;
  }
  
  private ParseACL getACL(boolean paramBoolean)
  {
    synchronized (this.mutex)
    {
      checkGetAccess("ACL");
      Object localObject2 = this.estimatedData.get("ACL");
      if (localObject2 == null) {
        return null;
      }
      if (!(localObject2 instanceof ParseACL)) {
        throw new RuntimeException("only ACLs can be stored in the ACL key");
      }
    }
    if ((paramBoolean) && (((ParseACL)localObject3).isShared()))
    {
      localParseACL = ((ParseACL)localObject3).copy();
      this.estimatedData.put("ACL", localParseACL);
      addToHashedObjects(localParseACL);
      return localParseACL;
    }
    ParseACL localParseACL = (ParseACL)localParseACL;
    return localParseACL;
  }
  
  static String getClassName(Class<? extends ParseObject> paramClass)
  {
    String str = (String)classNames.get(paramClass);
    Object localObject = str;
    if (str == null)
    {
      localObject = (ParseClassName)paramClass.getAnnotation(ParseClassName.class);
      if (localObject == null) {
        return null;
      }
      localObject = ((ParseClassName)localObject).value();
      classNames.put(paramClass, localObject);
    }
    return (String)localObject;
  }
  
  private static LocalIdManager getLocalIdManager()
  {
    return ParseCorePlugins.getInstance().getLocalIdManager();
  }
  
  private static ParseObjectController getObjectController()
  {
    return ParseCorePlugins.getInstance().getObjectController();
  }
  
  private boolean hasDirtyChildren()
  {
    for (;;)
    {
      synchronized (this.mutex)
      {
        ArrayList localArrayList = new ArrayList();
        collectDirtyChildren(this.estimatedData, localArrayList, null);
        if (localArrayList.size() > 0)
        {
          bool = true;
          return bool;
        }
      }
      boolean bool = false;
    }
  }
  
  private static boolean isAccessible(Member paramMember)
  {
    return (Modifier.isPublic(paramMember.getModifiers())) || ((paramMember.getDeclaringClass().getPackage().getName().equals("com.parse")) && (!Modifier.isPrivate(paramMember.getModifiers())) && (!Modifier.isProtected(paramMember.getModifiers())));
  }
  
  private void notifyObjectIdChanged(String paramString1, String paramString2)
  {
    synchronized (this.mutex)
    {
      OfflineStore localOfflineStore = Parse.getLocalDatastore();
      if (localOfflineStore != null) {
        localOfflineStore.updateObjectId(this, paramString1, paramString2);
      }
      if (this.localId != null)
      {
        getLocalIdManager().setObjectId(this.localId, paramString2);
        this.localId = null;
      }
      return;
    }
  }
  
  public static <T extends ParseObject> void pinAll(String paramString, List<T> paramList)
    throws ParseException
  {
    ParseTaskUtils.wait(pinAllInBackground(paramString, paramList));
  }
  
  public static <T extends ParseObject> void pinAll(List<T> paramList)
    throws ParseException
  {
    ParseTaskUtils.wait(pinAllInBackground("_default", paramList));
  }
  
  public static <T extends ParseObject> Task<Void> pinAllInBackground(String paramString, List<T> paramList)
  {
    return pinAllInBackground(paramString, paramList, true);
  }
  
  private static <T extends ParseObject> Task<Void> pinAllInBackground(String paramString, final List<T> paramList, final boolean paramBoolean)
  {
    if (!Parse.isLocalDatastoreEnabled()) {
      throw new IllegalStateException("Method requires Local Datastore. Please refer to `Parse#enableLocalDatastore(Context)`.");
    }
    Task localTask = Task.forResult(null);
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext()) {
      localTask = localTask.onSuccessTask(new Continuation()
      {
        public Task<Void> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          if (!this.val$object.isDataAvailable("ACL")) {
            return Task.forResult(null);
          }
          paramAnonymousTask = this.val$object.getACL(false);
          if (paramAnonymousTask == null) {
            return Task.forResult(null);
          }
          paramAnonymousTask = paramAnonymousTask.getUnresolvedUser();
          if ((paramAnonymousTask == null) || (!paramAnonymousTask.isCurrentUser())) {
            return Task.forResult(null);
          }
          return ParseUser.pinCurrentUserIfNeededAsync(paramAnonymousTask);
        }
      });
    }
    localTask.onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        OfflineStore localOfflineStore = Parse.getLocalDatastore();
        if (this.val$name != null) {}
        for (paramAnonymousTask = this.val$name;; paramAnonymousTask = "_default") {
          return localOfflineStore.pinAllObjectsAsync(paramAnonymousTask, paramList, paramBoolean);
        }
      }
    }).onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        if ("_currentUser".equals(this.val$name)) {}
        Object localObject;
        do
        {
          do
          {
            Iterator localIterator;
            while (!localIterator.hasNext())
            {
              return paramAnonymousTask;
              localIterator = paramList.iterator();
            }
            localObject = (ParseObject)localIterator.next();
          } while (!(localObject instanceof ParseUser));
          localObject = (ParseUser)localObject;
        } while (!((ParseUser)localObject).isCurrentUser());
        return ParseUser.pinCurrentUserIfNeededAsync((ParseUser)localObject);
      }
    });
  }
  
  public static <T extends ParseObject> Task<Void> pinAllInBackground(List<T> paramList)
  {
    return pinAllInBackground("_default", paramList);
  }
  
  public static <T extends ParseObject> void pinAllInBackground(String paramString, List<T> paramList, SaveCallback paramSaveCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(pinAllInBackground(paramString, paramList), paramSaveCallback);
  }
  
  public static <T extends ParseObject> void pinAllInBackground(List<T> paramList, SaveCallback paramSaveCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(pinAllInBackground("_default", paramList), paramSaveCallback);
  }
  
  private void rebuildEstimatedData()
  {
    synchronized (this.mutex)
    {
      this.estimatedData.clear();
      Iterator localIterator1 = this.state.keySet().iterator();
      if (localIterator1.hasNext())
      {
        String str = (String)localIterator1.next();
        this.estimatedData.put(str, this.state.get(str));
      }
    }
    Iterator localIterator2 = this.operationSetQueue.iterator();
    while (localIterator2.hasNext()) {
      applyOperations((ParseOperationSet)localIterator2.next(), this.estimatedData);
    }
  }
  
  static void registerParseSubclasses()
  {
    registerSubclass(ParseUser.class);
    registerSubclass(ParseRole.class);
    registerSubclass(ParseInstallation.class);
    registerSubclass(ParseSession.class);
    registerSubclass(ParsePin.class);
    registerSubclass(EventuallyPin.class);
  }
  
  public static void registerSubclass(Class<? extends ParseObject> paramClass)
  {
    String str = getClassName(paramClass);
    if (str == null) {
      throw new IllegalArgumentException("No ParseClassName annotation provided on " + paramClass);
    }
    if (paramClass.getDeclaredConstructors().length > 0) {
      try
      {
        if (!isAccessible(paramClass.getDeclaredConstructor(new Class[0]))) {
          throw new IllegalArgumentException("Default constructor for " + paramClass + " is not accessible.");
        }
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        throw new IllegalArgumentException("No default constructor provided for " + paramClass);
      }
    }
    Class localClass = (Class)objectTypes.get(localNoSuchMethodException);
    if ((localClass != null) && (paramClass.isAssignableFrom(localClass))) {}
    do
    {
      do
      {
        return;
        objectTypes.put(localNoSuchMethodException, paramClass);
      } while ((localClass == null) || (paramClass.equals(localClass)));
      if (localNoSuchMethodException.equals(getClassName(ParseUser.class)))
      {
        ParseUser.getCurrentUserController().clearFromMemory();
        return;
      }
    } while (!localNoSuchMethodException.equals(getClassName(ParseInstallation.class)));
    ParseInstallation.getCurrentInstallationController().clearFromMemory();
  }
  
  public static <T extends ParseObject> void saveAll(List<T> paramList)
    throws ParseException
  {
    ParseTaskUtils.wait(saveAllInBackground(paramList));
  }
  
  private static <T extends ParseObject> Task<Void> saveAllAsync(List<T> paramList, final String paramString, Task<Void> paramTask)
  {
    paramTask.continueWithTask(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        int j = this.val$uniqueObjects.size();
        Object localObject = new ArrayList(j);
        paramAnonymousTask = new ArrayList(j);
        ArrayList localArrayList = new ArrayList(j);
        int i = 0;
        while (i < j)
        {
          ParseObject localParseObject = (ParseObject)this.val$uniqueObjects.get(i);
          localParseObject.updateBeforeSave();
          localParseObject.validateSave();
          ((List)localObject).add(localParseObject.getState());
          paramAnonymousTask.add(localParseObject.startSave());
          localArrayList.add(new KnownParseObjectDecoder(localParseObject.collectFetchedObjects()));
          i += 1;
        }
        localObject = ParseObject.access$800().saveAllAsync((List)localObject, paramAnonymousTask, paramString, localArrayList);
        localArrayList = new ArrayList(j);
        i = 0;
        while (i < j)
        {
          localArrayList.add(((Task)((List)localObject).get(i)).continueWithTask(new Continuation()
          {
            public Task<Void> then(final Task<ParseObject.State> paramAnonymous2Task)
              throws Exception
            {
              ParseObject.State localState = (ParseObject.State)paramAnonymous2Task.getResult();
              this.val$object.handleSaveResultAsync(localState, this.val$operations).continueWithTask(new Continuation()
              {
                public Task<Void> then(Task<Void> paramAnonymous3Task)
                  throws Exception
                {
                  if ((paramAnonymous3Task.isFaulted()) || (paramAnonymous3Task.isCancelled())) {
                    return paramAnonymous3Task;
                  }
                  return paramAnonymous2Task.makeVoid();
                }
              });
            }
          }));
          i += 1;
        }
        return Task.whenAll(localArrayList);
      }
    });
  }
  
  public static <T extends ParseObject> Task<Void> saveAllInBackground(List<T> paramList)
  {
    ParseUser.getCurrentUserAsync().onSuccessTask(new Continuation()
    {
      public Task<String> then(Task<ParseUser> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (ParseUser)paramAnonymousTask.getResult();
        if (paramAnonymousTask == null) {
          return Task.forResult(null);
        }
        if (!paramAnonymousTask.isLazy()) {
          return Task.forResult(paramAnonymousTask.getSessionToken());
        }
        paramAnonymousTask = this.val$objects.iterator();
        while (paramAnonymousTask.hasNext())
        {
          final Object localObject = (ParseObject)paramAnonymousTask.next();
          if (((ParseObject)localObject).isDataAvailable("ACL"))
          {
            localObject = ((ParseObject)localObject).getACL(false);
            if (localObject != null)
            {
              final ParseUser localParseUser = ((ParseACL)localObject).getUnresolvedUser();
              if ((localParseUser != null) && (localParseUser.isCurrentUser())) {
                localParseUser.saveAsync(null).onSuccess(new Continuation()
                {
                  public String then(Task<Void> paramAnonymous2Task)
                    throws Exception
                  {
                    if (localObject.hasUnresolvedUser()) {
                      throw new IllegalStateException("ACL has an unresolved ParseUser. Save or sign up before attempting to serialize the ACL.");
                    }
                    return localParseUser.getSessionToken();
                  }
                });
              }
            }
          }
        }
        return Task.forResult(null);
      }
    }).onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<String> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (String)paramAnonymousTask.getResult();
        return ParseObject.deepSaveAsync(this.val$objects, paramAnonymousTask);
      }
    });
  }
  
  public static <T extends ParseObject> void saveAllInBackground(List<T> paramList, SaveCallback paramSaveCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(saveAllInBackground(paramList), paramSaveCallback);
  }
  
  private void setState(State paramState, boolean paramBoolean)
  {
    synchronized (this.mutex)
    {
      String str1 = this.state.objectId();
      String str2 = paramState.objectId();
      this.state = paramState;
      if ((paramBoolean) && (!ParseTextUtils.equals(str1, str2))) {
        notifyObjectIdChanged(str1, str2);
      }
      rebuildEstimatedData();
      checkpointAllMutableContainers();
      return;
    }
  }
  
  public static void unpinAll()
    throws ParseException
  {
    ParseTaskUtils.wait(unpinAllInBackground());
  }
  
  public static void unpinAll(String paramString)
    throws ParseException
  {
    ParseTaskUtils.wait(unpinAllInBackground(paramString));
  }
  
  public static <T extends ParseObject> void unpinAll(String paramString, List<T> paramList)
    throws ParseException
  {
    ParseTaskUtils.wait(unpinAllInBackground(paramString, paramList));
  }
  
  public static <T extends ParseObject> void unpinAll(List<T> paramList)
    throws ParseException
  {
    ParseTaskUtils.wait(unpinAllInBackground("_default", paramList));
  }
  
  public static Task<Void> unpinAllInBackground()
  {
    return unpinAllInBackground("_default");
  }
  
  public static Task<Void> unpinAllInBackground(String paramString)
  {
    if (!Parse.isLocalDatastoreEnabled()) {
      throw new IllegalStateException("Method requires Local Datastore. Please refer to `Parse#enableLocalDatastore(Context)`.");
    }
    String str = paramString;
    if (paramString == null) {
      str = "_default";
    }
    return Parse.getLocalDatastore().unpinAllObjectsAsync(str);
  }
  
  public static <T extends ParseObject> Task<Void> unpinAllInBackground(String paramString, List<T> paramList)
  {
    if (!Parse.isLocalDatastoreEnabled()) {
      throw new IllegalStateException("Method requires Local Datastore. Please refer to `Parse#enableLocalDatastore(Context)`.");
    }
    String str = paramString;
    if (paramString == null) {
      str = "_default";
    }
    return Parse.getLocalDatastore().unpinAllObjectsAsync(str, paramList);
  }
  
  public static <T extends ParseObject> Task<Void> unpinAllInBackground(List<T> paramList)
  {
    return unpinAllInBackground("_default", paramList);
  }
  
  public static void unpinAllInBackground(DeleteCallback paramDeleteCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(unpinAllInBackground(), paramDeleteCallback);
  }
  
  public static void unpinAllInBackground(String paramString, DeleteCallback paramDeleteCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(unpinAllInBackground(paramString), paramDeleteCallback);
  }
  
  public static <T extends ParseObject> void unpinAllInBackground(String paramString, List<T> paramList, DeleteCallback paramDeleteCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(unpinAllInBackground(paramString, paramList), paramDeleteCallback);
  }
  
  public static <T extends ParseObject> void unpinAllInBackground(List<T> paramList, DeleteCallback paramDeleteCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(unpinAllInBackground("_default", paramList), paramDeleteCallback);
  }
  
  static void unregisterParseSubclasses()
  {
    unregisterSubclass(ParseUser.class);
    unregisterSubclass(ParseRole.class);
    unregisterSubclass(ParseInstallation.class);
    unregisterSubclass(ParseSession.class);
    unregisterSubclass(ParsePin.class);
    unregisterSubclass(EventuallyPin.class);
  }
  
  static void unregisterSubclass(Class<? extends ParseObject> paramClass)
  {
    unregisterSubclass(getClassName(paramClass));
  }
  
  static void unregisterSubclass(String paramString)
  {
    objectTypes.remove(paramString);
  }
  
  public void add(String paramString, Object paramObject)
  {
    addAll(paramString, Arrays.asList(new Object[] { paramObject }));
  }
  
  public void addAll(String paramString, Collection<?> paramCollection)
  {
    performOperation(paramString, new ParseAddOperation(paramCollection));
  }
  
  public void addAllUnique(String paramString, Collection<?> paramCollection)
  {
    performOperation(paramString, new ParseAddUniqueOperation(paramCollection));
  }
  
  public void addUnique(String paramString, Object paramObject)
  {
    addAllUnique(paramString, Arrays.asList(new Object[] { paramObject }));
  }
  
  void build(JSONObject paramJSONObject, ParseDecoder paramParseDecoder)
  {
    ParseObject.State.Builder localBuilder;
    for (;;)
    {
      String str;
      try
      {
        localBuilder = (ParseObject.State.Builder)new ParseObject.State.Builder(this.state).isComplete(true);
        localBuilder.clear();
        Iterator localIterator = paramJSONObject.keys();
        if (!localIterator.hasNext()) {
          break;
        }
        str = (String)localIterator.next();
        if (str.equals("className")) {
          continue;
        }
        if (str.equals("objectId"))
        {
          localBuilder.objectId(paramJSONObject.getString(str));
          continue;
        }
        if (!str.equals("createdAt")) {
          break label126;
        }
      }
      catch (JSONException paramJSONObject)
      {
        throw new RuntimeException(paramJSONObject);
      }
      localBuilder.createdAt(ParseDateFormat.getInstance().parse(paramJSONObject.getString(str)));
      continue;
      label126:
      if (str.equals("updatedAt"))
      {
        localBuilder.updatedAt(ParseDateFormat.getInstance().parse(paramJSONObject.getString(str)));
      }
      else
      {
        Object localObject = paramParseDecoder.decode(paramJSONObject.get(str));
        if ((localObject instanceof ParseFieldOperation)) {
          performOperation(str, (ParseFieldOperation)localObject);
        } else {
          put(str, localObject);
        }
      }
    }
    setState(localBuilder.build());
  }
  
  void checkForChangesToMutableContainers()
  {
    synchronized (this.mutex)
    {
      Iterator localIterator = this.estimatedData.keySet().iterator();
      if (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        checkForChangesToMutableContainer(str, this.estimatedData.get(str));
      }
    }
    this.hashedObjects.keySet().retainAll(this.estimatedData.values());
  }
  
  public boolean containsKey(String paramString)
  {
    synchronized (this.mutex)
    {
      boolean bool = this.estimatedData.containsKey(paramString);
      return bool;
    }
  }
  
  void copyChangesFrom(ParseObject paramParseObject)
  {
    synchronized (this.mutex)
    {
      paramParseObject = (ParseOperationSet)paramParseObject.operationSetQueue.getFirst();
      Iterator localIterator = paramParseObject.keySet().iterator();
      if (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        performOperation(str, (ParseFieldOperation)paramParseObject.get(str));
      }
    }
  }
  
  public final void delete()
    throws ParseException
  {
    ParseTaskUtils.wait(deleteInBackground());
  }
  
  Task<Void> deleteAsync(String paramString)
    throws ParseException
  {
    return getObjectController().deleteAsync(getState(), paramString);
  }
  
  public final Task<Void> deleteEventually()
  {
    synchronized (this.mutex)
    {
      validateDelete();
      this.isDeletingEventually += 1;
      Object localObject1 = null;
      if (getObjectId() == null) {
        localObject1 = getOrCreateLocalId();
      }
      Object localObject4 = ParseUser.getCurrentSessionToken();
      localObject4 = ParseRESTObjectCommand.deleteObjectCommand(getState(), (String)localObject4);
      ((ParseRESTCommand)localObject4).enableRetrying();
      ((ParseRESTCommand)localObject4).setLocalId((String)localObject1);
      localObject1 = Parse.getEventuallyQueue().enqueueEventuallyAsync((ParseRESTCommand)localObject4, this);
      if (Parse.isLocalDatastoreEnabled()) {
        return ((Task)localObject1).makeVoid();
      }
    }
    ((Task)localObject2).onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<JSONObject> paramAnonymousTask)
        throws Exception
      {
        return ParseObject.this.handleDeleteEventuallyResultAsync();
      }
    });
  }
  
  public final void deleteEventually(DeleteCallback paramDeleteCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(deleteEventually(), paramDeleteCallback);
  }
  
  public final Task<Void> deleteInBackground()
  {
    ParseUser.getCurrentSessionTokenAsync().onSuccessTask(new Continuation()
    {
      public Task<Void> then(final Task<String> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (String)paramAnonymousTask.getResult();
        ParseObject.this.taskQueue.enqueue(new Continuation()
        {
          public Task<Void> then(Task<Void> paramAnonymous2Task)
            throws Exception
          {
            return ParseObject.this.deleteAsync(paramAnonymousTask, paramAnonymous2Task);
          }
        });
      }
    });
  }
  
  public final void deleteInBackground(DeleteCallback paramDeleteCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(deleteInBackground(), paramDeleteCallback);
  }
  
  public <T extends ParseObject> T fetch()
    throws ParseException
  {
    return (ParseObject)ParseTaskUtils.wait(fetchInBackground());
  }
  
  <T extends ParseObject> Task<T> fetchAsync(final String paramString, Task<Void> paramTask)
  {
    paramTask.onSuccessTask(new Continuation()
    {
      public Task<ParseObject.State> then(Task<Void> arg1)
        throws Exception
      {
        synchronized (ParseObject.this.mutex)
        {
          ParseObject.State localState = ParseObject.this.getState();
          Map localMap = ParseObject.this.collectFetchedObjects();
          ??? = new KnownParseObjectDecoder(localMap);
          return ParseObject.access$800().fetchAsync(localState, paramString, ???);
        }
      }
    }).onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<ParseObject.State> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (ParseObject.State)paramAnonymousTask.getResult();
        return ParseObject.this.handleFetchResultAsync(paramAnonymousTask);
      }
    }).onSuccess(new Continuation()
    {
      public T then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return ParseObject.this;
      }
    });
  }
  
  public void fetchFromLocalDatastore()
    throws ParseException
  {
    ParseTaskUtils.wait(fetchFromLocalDatastoreAsync());
  }
  
  <T extends ParseObject> Task<T> fetchFromLocalDatastoreAsync()
  {
    if (!Parse.isLocalDatastoreEnabled()) {
      throw new IllegalStateException("Method requires Local Datastore. Please refer to `Parse#enableLocalDatastore(Context)`.");
    }
    return Parse.getLocalDatastore().fetchLocallyAsync(this);
  }
  
  public <T extends ParseObject> void fetchFromLocalDatastoreInBackground(GetCallback<T> paramGetCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(fetchFromLocalDatastoreAsync(), paramGetCallback);
  }
  
  public <T extends ParseObject> T fetchIfNeeded()
    throws ParseException
  {
    return (ParseObject)ParseTaskUtils.wait(fetchIfNeededInBackground());
  }
  
  public final <T extends ParseObject> Task<T> fetchIfNeededInBackground()
  {
    if (isDataAvailable()) {
      return Task.forResult(this);
    }
    ParseUser.getCurrentSessionTokenAsync().onSuccessTask(new Continuation()
    {
      public Task<T> then(final Task<String> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (String)paramAnonymousTask.getResult();
        ParseObject.this.taskQueue.enqueue(new Continuation()
        {
          public Task<T> then(Task<Void> paramAnonymous2Task)
            throws Exception
          {
            if (ParseObject.this.isDataAvailable()) {
              return Task.forResult(ParseObject.this);
            }
            return ParseObject.this.fetchAsync(paramAnonymousTask, paramAnonymous2Task);
          }
        });
      }
    });
  }
  
  public final <T extends ParseObject> void fetchIfNeededInBackground(GetCallback<T> paramGetCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(fetchIfNeededInBackground(), paramGetCallback);
  }
  
  public final <T extends ParseObject> Task<T> fetchInBackground()
  {
    ParseUser.getCurrentSessionTokenAsync().onSuccessTask(new Continuation()
    {
      public Task<T> then(final Task<String> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (String)paramAnonymousTask.getResult();
        ParseObject.this.taskQueue.enqueue(new Continuation()
        {
          public Task<T> then(Task<Void> paramAnonymous2Task)
            throws Exception
          {
            return ParseObject.this.fetchAsync(paramAnonymousTask, paramAnonymous2Task);
          }
        });
      }
    });
  }
  
  public final <T extends ParseObject> void fetchInBackground(GetCallback<T> paramGetCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(fetchInBackground(), paramGetCallback);
  }
  
  public Object get(String paramString)
  {
    synchronized (this.mutex)
    {
      if (paramString.equals("ACL"))
      {
        paramString = getACL();
        return paramString;
      }
      checkGetAccess(paramString);
      Object localObject2 = this.estimatedData.get(paramString);
      if ((localObject2 instanceof ParseRelation)) {
        ((ParseRelation)localObject2).ensureParentAndKey(this, paramString);
      }
      return localObject2;
    }
  }
  
  public ParseACL getACL()
  {
    return getACL(true);
  }
  
  public boolean getBoolean(String paramString)
  {
    synchronized (this.mutex)
    {
      checkGetAccess(paramString);
      paramString = this.estimatedData.get(paramString);
      if (!(paramString instanceof Boolean)) {
        return false;
      }
      boolean bool = ((Boolean)paramString).booleanValue();
      return bool;
    }
  }
  
  public byte[] getBytes(String paramString)
  {
    synchronized (this.mutex)
    {
      checkGetAccess(paramString);
      paramString = this.estimatedData.get(paramString);
      if (!(paramString instanceof byte[])) {
        return null;
      }
      paramString = (byte[])paramString;
      return paramString;
    }
  }
  
  public String getClassName()
  {
    synchronized (this.mutex)
    {
      String str = this.state.className();
      return str;
    }
  }
  
  public Date getCreatedAt()
  {
    long l = getState().createdAt();
    if (l > 0L) {
      return new Date(l);
    }
    return null;
  }
  
  public Date getDate(String paramString)
  {
    synchronized (this.mutex)
    {
      checkGetAccess(paramString);
      paramString = this.estimatedData.get(paramString);
      if (!(paramString instanceof Date)) {
        return null;
      }
      paramString = (Date)paramString;
      return paramString;
    }
  }
  
  public double getDouble(String paramString)
  {
    paramString = getNumber(paramString);
    if (paramString == null) {
      return 0.0D;
    }
    return paramString.doubleValue();
  }
  
  public int getInt(String paramString)
  {
    paramString = getNumber(paramString);
    if (paramString == null) {
      return 0;
    }
    return paramString.intValue();
  }
  
  public JSONArray getJSONArray(String paramString)
  {
    synchronized (this.mutex)
    {
      checkGetAccess(paramString);
      Object localObject2 = this.estimatedData.get(paramString);
      Object localObject1 = localObject2;
      if ((localObject2 instanceof List))
      {
        localObject1 = PointerOrLocalIdEncoder.get().encode(localObject2);
        put(paramString, localObject1);
      }
      if (!(localObject1 instanceof JSONArray)) {
        return null;
      }
      paramString = (JSONArray)localObject1;
      return paramString;
    }
  }
  
  public JSONObject getJSONObject(String paramString)
  {
    synchronized (this.mutex)
    {
      checkGetAccess(paramString);
      Object localObject2 = this.estimatedData.get(paramString);
      Object localObject1 = localObject2;
      if ((localObject2 instanceof Map))
      {
        localObject1 = PointerOrLocalIdEncoder.get().encode(localObject2);
        put(paramString, localObject1);
      }
      if (!(localObject1 instanceof JSONObject)) {
        return null;
      }
      paramString = (JSONObject)localObject1;
      return paramString;
    }
  }
  
  public <T> List<T> getList(String paramString)
  {
    synchronized (this.mutex)
    {
      Object localObject2 = this.estimatedData.get(paramString);
      Object localObject1 = localObject2;
      if ((localObject2 instanceof JSONArray))
      {
        localObject1 = ParseDecoder.get().convertJSONArrayToList((JSONArray)localObject2);
        put(paramString, localObject1);
      }
      if (!(localObject1 instanceof List)) {
        return null;
      }
      paramString = (List)localObject1;
      return paramString;
    }
  }
  
  public long getLong(String paramString)
  {
    paramString = getNumber(paramString);
    if (paramString == null) {
      return 0L;
    }
    return paramString.longValue();
  }
  
  public <V> Map<String, V> getMap(String paramString)
  {
    synchronized (this.mutex)
    {
      Object localObject2 = this.estimatedData.get(paramString);
      Object localObject1 = localObject2;
      if ((localObject2 instanceof JSONObject))
      {
        localObject1 = ParseDecoder.get().convertJSONObjectToMap((JSONObject)localObject2);
        put(paramString, localObject1);
      }
      if (!(localObject1 instanceof Map)) {
        return null;
      }
      paramString = (Map)localObject1;
      return paramString;
    }
  }
  
  public Number getNumber(String paramString)
  {
    synchronized (this.mutex)
    {
      checkGetAccess(paramString);
      paramString = this.estimatedData.get(paramString);
      if (!(paramString instanceof Number)) {
        return null;
      }
      paramString = (Number)paramString;
      return paramString;
    }
  }
  
  public String getObjectId()
  {
    synchronized (this.mutex)
    {
      String str = this.state.objectId();
      return str;
    }
  }
  
  String getOrCreateLocalId()
  {
    synchronized (this.mutex)
    {
      if (this.localId != null) {
        break label50;
      }
      if (this.state.objectId() != null) {
        throw new IllegalStateException("Attempted to get a localId for an object with an objectId.");
      }
    }
    this.localId = getLocalIdManager().createLocalId();
    label50:
    String str = this.localId;
    return str;
  }
  
  public ParseFile getParseFile(String paramString)
  {
    paramString = get(paramString);
    if (!(paramString instanceof ParseFile)) {
      return null;
    }
    return (ParseFile)paramString;
  }
  
  public ParseGeoPoint getParseGeoPoint(String paramString)
  {
    synchronized (this.mutex)
    {
      checkGetAccess(paramString);
      paramString = this.estimatedData.get(paramString);
      if (!(paramString instanceof ParseGeoPoint)) {
        return null;
      }
      paramString = (ParseGeoPoint)paramString;
      return paramString;
    }
  }
  
  public ParseObject getParseObject(String paramString)
  {
    paramString = get(paramString);
    if (!(paramString instanceof ParseObject)) {
      return null;
    }
    return (ParseObject)paramString;
  }
  
  public ParseUser getParseUser(String paramString)
  {
    paramString = get(paramString);
    if (!(paramString instanceof ParseUser)) {
      return null;
    }
    return (ParseUser)paramString;
  }
  
  public <T extends ParseObject> ParseRelation<T> getRelation(String paramString)
  {
    synchronized (this.mutex)
    {
      Object localObject2 = this.estimatedData.get(paramString);
      if ((localObject2 instanceof ParseRelation))
      {
        localObject2 = (ParseRelation)localObject2;
        ((ParseRelation)localObject2).ensureParentAndKey(this, paramString);
        return (ParseRelation<T>)localObject2;
      }
      localObject2 = new ParseRelation(this, paramString);
      this.estimatedData.put(paramString, localObject2);
      return (ParseRelation<T>)localObject2;
    }
  }
  
  State getState()
  {
    synchronized (this.mutex)
    {
      State localState = this.state;
      return localState;
    }
  }
  
  public String getString(String paramString)
  {
    synchronized (this.mutex)
    {
      checkGetAccess(paramString);
      paramString = this.estimatedData.get(paramString);
      if (!(paramString instanceof String)) {
        return null;
      }
      paramString = (String)paramString;
      return paramString;
    }
  }
  
  public Date getUpdatedAt()
  {
    long l = getState().updatedAt();
    if (l > 0L) {
      return new Date(l);
    }
    return null;
  }
  
  Task<Void> handleDeleteEventuallyResultAsync()
  {
    synchronized (this.mutex)
    {
      this.isDeletingEventually -= 1;
      handleDeleteResultAsync().onSuccessTask(new Continuation()
      {
        public Task<Void> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          Parse.getEventuallyQueue().notifyTestHelper(6);
          return paramAnonymousTask;
        }
      });
    }
  }
  
  Task<Void> handleDeleteResultAsync()
  {
    Task localTask = Task.forResult(null);
    synchronized (this.mutex)
    {
      this.isDeleted = true;
      final OfflineStore localOfflineStore = Parse.getLocalDatastore();
      ??? = localTask;
      if (localOfflineStore != null) {
        ??? = localTask.continueWithTask(new Continuation()
        {
          public Task<Void> then(Task<Void> arg1)
            throws Exception
          {
            synchronized (ParseObject.this.mutex)
            {
              if (ParseObject.this.isDeleted)
              {
                localOfflineStore.unregisterObject(ParseObject.this);
                localTask = localOfflineStore.deleteDataForObjectAsync(ParseObject.this);
                return localTask;
              }
              Task localTask = localOfflineStore.updateDataForObjectAsync(ParseObject.this);
              return localTask;
            }
          }
        });
      }
      return (Task<Void>)???;
    }
  }
  
  Task<Void> handleFetchResultAsync(final State paramState)
  {
    Task localTask2 = Task.forResult((Void)null);
    final OfflineStore localOfflineStore = Parse.getLocalDatastore();
    Task localTask1 = localTask2;
    if (localOfflineStore != null) {
      localTask1 = localTask2.onSuccessTask(new Continuation()
      {
        public Task<Void> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          return localOfflineStore.fetchLocallyAsync(ParseObject.this).makeVoid();
        }
      }).continueWithTask(new Continuation()
      {
        public Task<Void> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          Task<Void> localTask = paramAnonymousTask;
          if ((paramAnonymousTask.getError() instanceof ParseException))
          {
            localTask = paramAnonymousTask;
            if (((ParseException)paramAnonymousTask.getError()).getCode() == 120) {
              localTask = null;
            }
          }
          return localTask;
        }
      });
    }
    localTask1 = localTask1.onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        synchronized (ParseObject.this.mutex)
        {
          if (paramState.isComplete())
          {
            paramAnonymousTask = paramState;
            ParseObject.this.setState(paramAnonymousTask);
            return null;
          }
          paramAnonymousTask = ParseObject.this.getState().newBuilder().apply(paramState).build();
        }
      }
    });
    paramState = localTask1;
    if (localOfflineStore != null) {
      paramState = localTask1.onSuccessTask(new Continuation()
      {
        public Task<Void> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          return localOfflineStore.updateDataForObjectAsync(ParseObject.this);
        }
      }).continueWithTask(new Continuation()
      {
        public Task<Void> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          Task<Void> localTask = paramAnonymousTask;
          if ((paramAnonymousTask.getError() instanceof ParseException))
          {
            localTask = paramAnonymousTask;
            if (((ParseException)paramAnonymousTask.getError()).getCode() == 120) {
              localTask = null;
            }
          }
          return localTask;
        }
      });
    }
    return paramState;
  }
  
  Task<Void> handleSaveEventuallyResultAsync(JSONObject paramJSONObject, ParseOperationSet paramParseOperationSet)
  {
    if (paramJSONObject != null) {}
    for (final boolean bool = true;; bool = false) {
      handleSaveResultAsync(paramJSONObject, paramParseOperationSet).onSuccessTask(new Continuation()
      {
        public Task<Void> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          if (bool) {
            Parse.getEventuallyQueue().notifyTestHelper(5);
          }
          return paramAnonymousTask;
        }
      });
    }
  }
  
  Task<Void> handleSaveResultAsync(final State paramState, final ParseOperationSet paramParseOperationSet)
  {
    Task localTask = Task.forResult((Void)null);
    if (paramState != null) {}
    for (int i = 1;; i = 0) {
      synchronized (this.mutex)
      {
        final Object localObject2 = this.operationSetQueue.listIterator(this.operationSetQueue.indexOf(paramParseOperationSet));
        ((ListIterator)localObject2).next();
        ((ListIterator)localObject2).remove();
        if (i == 0)
        {
          ((ParseOperationSet)((ListIterator)localObject2).next()).mergeFrom(paramParseOperationSet);
          return localTask;
        }
        localObject2 = Parse.getLocalDatastore();
        ??? = localTask;
        if (localObject2 != null) {
          ??? = localTask.onSuccessTask(new Continuation()
          {
            public Task<Void> then(Task<Void> paramAnonymousTask)
              throws Exception
            {
              return localObject2.fetchLocallyAsync(ParseObject.this).makeVoid();
            }
          });
        }
        paramParseOperationSet = ((Task)???).continueWith(new Continuation()
        {
          public Void then(Task<Void> paramAnonymousTask)
            throws Exception
          {
            synchronized (ParseObject.this.mutex)
            {
              if (paramState.isComplete())
              {
                paramAnonymousTask = paramState;
                ParseObject.this.setState(paramAnonymousTask);
                return null;
              }
              paramAnonymousTask = ParseObject.this.getState().newBuilder().apply(paramParseOperationSet).apply(paramState).build();
            }
          }
        });
        paramState = paramParseOperationSet;
        if (localObject2 != null) {
          paramState = paramParseOperationSet.onSuccessTask(new Continuation()
          {
            public Task<Void> then(Task<Void> paramAnonymousTask)
              throws Exception
            {
              return localObject2.updateDataForObjectAsync(ParseObject.this);
            }
          });
        }
        paramState.onSuccess(new Continuation()
        {
          public Void then(Task<Void> paramAnonymousTask)
            throws Exception
          {
            ParseObject.this.saveEvent.invoke(ParseObject.this, null);
            return null;
          }
        });
      }
    }
  }
  
  Task<Void> handleSaveResultAsync(JSONObject paramJSONObject, ParseOperationSet paramParseOperationSet)
  {
    Object localObject1 = null;
    if (paramJSONObject != null) {}
    synchronized (this.mutex)
    {
      localObject1 = new KnownParseObjectDecoder(collectFetchedObjects());
      localObject1 = ParseObjectCoder.get().decode(getState().newBuilder().clear(), paramJSONObject, (ParseDecoder)localObject1).isComplete(false).build();
      return handleSaveResultAsync((State)localObject1, paramParseOperationSet);
    }
  }
  
  public boolean has(String paramString)
  {
    return containsKey(paramString);
  }
  
  boolean hasChanges()
  {
    for (;;)
    {
      synchronized (this.mutex)
      {
        if (currentOperations().size() > 0)
        {
          bool = true;
          return bool;
        }
      }
      boolean bool = false;
    }
  }
  
  boolean hasOutstandingOperations()
  {
    for (boolean bool = true;; bool = false) {
      synchronized (this.mutex)
      {
        if (this.operationSetQueue.size() > 1) {
          return bool;
        }
      }
    }
  }
  
  public boolean hasSameId(ParseObject paramParseObject)
  {
    for (;;)
    {
      synchronized (this.mutex)
      {
        if ((getClassName() != null) && (getObjectId() != null) && (getClassName().equals(paramParseObject.getClassName())) && (getObjectId().equals(paramParseObject.getObjectId())))
        {
          bool = true;
          return bool;
        }
      }
      boolean bool = false;
    }
  }
  
  public void increment(String paramString)
  {
    increment(paramString, Integer.valueOf(1));
  }
  
  public void increment(String paramString, Number paramNumber)
  {
    performOperation(paramString, new ParseIncrementOperation(paramNumber));
  }
  
  boolean isContainerObject(String paramString, Object paramObject)
  {
    return ((paramObject instanceof JSONObject)) || ((paramObject instanceof JSONArray)) || ((paramObject instanceof Map)) || ((paramObject instanceof List)) || ((paramObject instanceof ParseACL)) || ((paramObject instanceof ParseGeoPoint));
  }
  
  public boolean isDataAvailable()
  {
    synchronized (this.mutex)
    {
      boolean bool = this.state.isComplete();
      return bool;
    }
  }
  
  boolean isDataAvailable(String paramString)
  {
    for (;;)
    {
      synchronized (this.mutex)
      {
        if (!isDataAvailable())
        {
          if (!this.estimatedData.containsKey(paramString)) {
            break label44;
          }
          break label39;
          return bool;
        }
      }
      label39:
      boolean bool = true;
      continue;
      label44:
      bool = false;
    }
  }
  
  public boolean isDirty()
  {
    return isDirty(true);
  }
  
  public boolean isDirty(String paramString)
  {
    synchronized (this.mutex)
    {
      boolean bool = currentOperations().containsKey(paramString);
      return bool;
    }
  }
  
  boolean isDirty(boolean paramBoolean)
  {
    for (;;)
    {
      synchronized (this.mutex)
      {
        checkForChangesToMutableContainers();
        if ((!this.isDeleted) && (getObjectId() != null) && (!hasChanges()))
        {
          if ((!paramBoolean) || (!hasDirtyChildren())) {
            break label60;
          }
          break label55;
          return paramBoolean;
        }
      }
      label55:
      paramBoolean = true;
      continue;
      label60:
      paramBoolean = false;
    }
  }
  
  boolean isKeyMutable(String paramString)
  {
    return true;
  }
  
  public Set<String> keySet()
  {
    synchronized (this.mutex)
    {
      Set localSet = Collections.unmodifiableSet(this.estimatedData.keySet());
      return localSet;
    }
  }
  
  /* Error */
  void mergeFromObject(ParseObject paramParseObject)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 233	com/parse/ParseObject:mutex	Ljava/lang/Object;
    //   4: astore_2
    //   5: aload_2
    //   6: monitorenter
    //   7: aload_0
    //   8: aload_1
    //   9: if_acmpne +6 -> 15
    //   12: aload_2
    //   13: monitorexit
    //   14: return
    //   15: aload_0
    //   16: aload_1
    //   17: invokevirtual 608	com/parse/ParseObject:getState	()Lcom/parse/ParseObject$State;
    //   20: invokevirtual 873	com/parse/ParseObject$State:newBuilder	()Lcom/parse/ParseObject$State$Init;
    //   23: invokevirtual 322	com/parse/ParseObject$State$Init:build	()Lcom/parse/ParseObject$State;
    //   26: iconst_0
    //   27: invokespecial 1482	com/parse/ParseObject:setState	(Lcom/parse/ParseObject$State;Z)V
    //   30: aload_2
    //   31: monitorexit
    //   32: return
    //   33: astore_1
    //   34: aload_2
    //   35: monitorexit
    //   36: aload_1
    //   37: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	38	0	this	ParseObject
    //   0	38	1	paramParseObject	ParseObject
    //   4	31	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   12	14	33	finally
    //   15	32	33	finally
    //   34	36	33	finally
  }
  
  State mergeFromServer(State paramState, JSONObject paramJSONObject, ParseDecoder paramParseDecoder, boolean paramBoolean)
  {
    for (;;)
    {
      ParseObject.State.Init localInit;
      try
      {
        localInit = paramState.newBuilder();
        if (paramBoolean) {
          localInit.clear();
        }
        if (paramState.isComplete()) {
          break label249;
        }
        if (!paramBoolean) {
          break label121;
        }
      }
      catch (JSONException paramState)
      {
        throw new RuntimeException(paramState);
      }
      localInit.isComplete(paramBoolean);
      paramState = paramJSONObject.keys();
      if (paramState.hasNext())
      {
        String str = (String)paramState.next();
        if ((!str.equals("__type")) && (!str.equals("className"))) {
          if (str.equals("objectId"))
          {
            localInit.objectId(paramJSONObject.getString(str));
            continue;
            label121:
            paramBoolean = false;
          }
          else if (str.equals("createdAt"))
          {
            localInit.createdAt(ParseDateFormat.getInstance().parse(paramJSONObject.getString(str)));
          }
          else if (str.equals("updatedAt"))
          {
            localInit.updatedAt(ParseDateFormat.getInstance().parse(paramJSONObject.getString(str)));
          }
          else if (str.equals("ACL"))
          {
            localInit.put("ACL", ParseACL.createACLFromJSONObject(paramJSONObject.getJSONObject(str), paramParseDecoder));
          }
          else
          {
            localInit.put(str, paramParseDecoder.decode(paramJSONObject.get(str)));
          }
        }
      }
      else
      {
        paramState = localInit.build();
        return paramState;
        label249:
        paramBoolean = true;
      }
    }
  }
  
  void mergeREST(State paramState, JSONObject paramJSONObject, ParseDecoder paramParseDecoder)
  {
    ArrayList localArrayList = new ArrayList();
    Object localObject1;
    int i;
    ParseOperationSet localParseOperationSet1;
    synchronized (this.mutex)
    {
      try
      {
        boolean bool = paramJSONObject.getBoolean("__complete");
        this.isDeletingEventually = ParseJSONUtils.getInt(paramJSONObject, Arrays.asList(new String[] { "__isDeletingEventually", "isDeletingEventually" }));
        JSONArray localJSONArray = paramJSONObject.getJSONArray("__operations");
        ParseOperationSet localParseOperationSet2 = currentOperations();
        this.operationSetQueue.clear();
        localObject1 = null;
        i = 0;
        if (i < localJSONArray.length())
        {
          localParseOperationSet1 = ParseOperationSet.fromRest(localJSONArray.getJSONObject(i), paramParseDecoder);
          if (localParseOperationSet1.isSaveEventually())
          {
            Object localObject2 = localObject1;
            if (localObject1 != null)
            {
              this.operationSetQueue.add(localObject1);
              localObject2 = null;
            }
            localArrayList.add(localParseOperationSet1);
            this.operationSetQueue.add(localParseOperationSet1);
            localObject1 = localObject2;
            break label370;
          }
          if (localObject1 == null) {
            break label379;
          }
          localParseOperationSet1.mergeFrom((ParseOperationSet)localObject1);
          break label379;
        }
        if (localObject1 != null) {
          this.operationSetQueue.add(localObject1);
        }
        currentOperations().mergeFrom(localParseOperationSet2);
        int j = 0;
        if (paramState.updatedAt() < 0L) {
          i = 1;
        }
        for (;;)
        {
          if (i != 0) {
            setState(mergeFromServer(paramState, ParseJSONUtils.create(paramJSONObject, Arrays.asList(new String[] { "__complete", "__isDeletingEventually", "isDeletingEventually", "__operations" })), paramParseDecoder, bool));
          }
          paramState = localArrayList.iterator();
          while (paramState.hasNext()) {
            enqueueSaveEventuallyOperationAsync((ParseOperationSet)paramState.next());
          }
          i = j;
          if (paramJSONObject.has("updatedAt"))
          {
            localObject1 = ParseDateFormat.getInstance().parse(paramJSONObject.getString("updatedAt"));
            int k = new Date(paramState.updatedAt()).compareTo((Date)localObject1);
            i = j;
            if (k < 0) {
              i = 1;
            }
          }
        }
        paramState = finally;
      }
      catch (JSONException paramState)
      {
        throw new RuntimeException(paramState);
      }
    }
    return;
    for (;;)
    {
      label370:
      i += 1;
      break;
      label379:
      localObject1 = localParseOperationSet1;
    }
  }
  
  boolean needsDefaultACL()
  {
    return true;
  }
  
  ParseObject.State.Init<?> newStateBuilder(String paramString)
  {
    return new ParseObject.State.Builder(paramString);
  }
  
  void performOperation(String paramString, ParseFieldOperation paramParseFieldOperation)
  {
    synchronized (this.mutex)
    {
      Object localObject2 = paramParseFieldOperation.apply(this.estimatedData.get(paramString), paramString);
      if (localObject2 != null)
      {
        this.estimatedData.put(paramString, localObject2);
        paramParseFieldOperation = paramParseFieldOperation.mergeWithPrevious((ParseFieldOperation)currentOperations().get(paramString));
        currentOperations().put(paramString, paramParseFieldOperation);
        checkpointMutableContainer(paramString, localObject2);
        return;
      }
      this.estimatedData.remove(paramString);
    }
  }
  
  void performPut(String paramString, Object paramObject)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("key may not be null.");
    }
    if (paramObject == null) {
      throw new IllegalArgumentException("value may not be null.");
    }
    if (!ParseEncoder.isValidType(paramObject)) {
      throw new IllegalArgumentException("invalid type for value: " + paramObject.getClass().toString());
    }
    performOperation(paramString, new ParseSetOperation(paramObject));
  }
  
  void performRemove(String paramString)
  {
    synchronized (this.mutex)
    {
      if (get(paramString) != null) {
        performOperation(paramString, ParseDeleteOperation.getInstance());
      }
      return;
    }
  }
  
  public void pin()
    throws ParseException
  {
    ParseTaskUtils.wait(pinInBackground());
  }
  
  public void pin(String paramString)
    throws ParseException
  {
    ParseTaskUtils.wait(pinInBackground(paramString));
  }
  
  public Task<Void> pinInBackground()
  {
    return pinAllInBackground("_default", Arrays.asList(new ParseObject[] { this }));
  }
  
  public Task<Void> pinInBackground(String paramString)
  {
    return pinAllInBackground(paramString, Collections.singletonList(this));
  }
  
  Task<Void> pinInBackground(String paramString, boolean paramBoolean)
  {
    return pinAllInBackground(paramString, Collections.singletonList(this), paramBoolean);
  }
  
  public void pinInBackground(SaveCallback paramSaveCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(pinInBackground(), paramSaveCallback);
  }
  
  public void pinInBackground(String paramString, SaveCallback paramSaveCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(pinInBackground(paramString), paramSaveCallback);
  }
  
  public void put(String paramString, Object paramObject)
  {
    checkKeyIsMutable(paramString);
    performPut(paramString, paramObject);
  }
  
  @Deprecated
  public final void refresh()
    throws ParseException
  {
    fetch();
  }
  
  @Deprecated
  public final void refreshInBackground(RefreshCallback paramRefreshCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(fetchInBackground(), paramRefreshCallback);
  }
  
  void registerSaveListener(GetCallback<ParseObject> paramGetCallback)
  {
    synchronized (this.mutex)
    {
      this.saveEvent.subscribe(paramGetCallback);
      return;
    }
  }
  
  public void remove(String paramString)
  {
    checkKeyIsMutable(paramString);
    performRemove(paramString);
  }
  
  public void removeAll(String paramString, Collection<?> paramCollection)
  {
    checkKeyIsMutable(paramString);
    performOperation(paramString, new ParseRemoveOperation(paramCollection));
  }
  
  public void revert()
  {
    synchronized (this.mutex)
    {
      if (isDirty())
      {
        currentOperations().clear();
        rebuildEstimatedData();
        checkpointAllMutableContainers();
      }
      return;
    }
  }
  
  public void revert(String paramString)
  {
    synchronized (this.mutex)
    {
      if (isDirty(paramString))
      {
        currentOperations().remove(paramString);
        rebuildEstimatedData();
        checkpointAllMutableContainers();
      }
      return;
    }
  }
  
  public final void save()
    throws ParseException
  {
    ParseTaskUtils.wait(saveInBackground());
  }
  
  Task<JSONObject> saveAsync(ParseOperationSet paramParseOperationSet, String paramString)
    throws ParseException
  {
    return currentSaveEventuallyCommand(paramParseOperationSet, PointerEncoder.get(), paramString).executeAsync();
  }
  
  Task<Void> saveAsync(final String paramString)
  {
    this.taskQueue.enqueue(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return ParseObject.this.saveAsync(paramString, paramAnonymousTask);
      }
    });
  }
  
  Task<Void> saveAsync(final String paramString, Task<Void> paramTask)
  {
    if (!isDirty()) {
      return Task.forResult(null);
    }
    final ParseOperationSet localParseOperationSet;
    synchronized (this.mutex)
    {
      updateBeforeSave();
      validateSave();
      localParseOperationSet = startSave();
    }
    synchronized (this.mutex)
    {
      Task localTask = deepSaveAsync(this.estimatedData, paramString);
      localTask.onSuccessTask(TaskQueue.waitFor(paramTask)).onSuccessTask(new Continuation()
      {
        public Task<ParseObject.State> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          paramAnonymousTask = new KnownParseObjectDecoder(ParseObject.this.collectFetchedObjects());
          return ParseObject.access$800().saveAsync(ParseObject.this.getState(), localParseOperationSet, paramString, paramAnonymousTask);
        }
      }).continueWithTask(new Continuation()
      {
        public Task<Void> then(final Task<ParseObject.State> paramAnonymousTask)
          throws Exception
        {
          ParseObject.State localState = (ParseObject.State)paramAnonymousTask.getResult();
          ParseObject.this.handleSaveResultAsync(localState, localParseOperationSet).continueWithTask(new Continuation()
          {
            public Task<Void> then(Task<Void> paramAnonymous2Task)
              throws Exception
            {
              if ((paramAnonymous2Task.isFaulted()) || (paramAnonymous2Task.isCancelled())) {
                return paramAnonymous2Task;
              }
              return paramAnonymousTask.makeVoid();
            }
          });
        }
      });
      paramString = finally;
      throw paramString;
    }
  }
  
  public final Task<Void> saveEventually()
  {
    if (!isDirty())
    {
      Parse.getEventuallyQueue().fakeObjectUpdate();
      return Task.forResult(null);
    }
    synchronized (this.mutex)
    {
      updateBeforeSave();
    }
    final ParseOperationSet localParseOperationSet;
    Object localObject4;
    try
    {
      validateSaveEventually();
      ArrayList localArrayList = new ArrayList();
      collectDirtyChildren(this.estimatedData, localArrayList, null);
      Object localObject1 = null;
      if (getObjectId() == null) {
        localObject1 = getOrCreateLocalId();
      }
      localParseOperationSet = startSave();
      localParseOperationSet.setIsSaveEventually(true);
      localObject4 = ParseUser.getCurrentSessionToken();
      try
      {
        localObject4 = currentSaveEventuallyCommand(localParseOperationSet, PointerOrLocalIdEncoder.get(), (String)localObject4);
        ((ParseRESTCommand)localObject4).setLocalId((String)localObject1);
        ((ParseRESTCommand)localObject4).setOperationSetUUID(localParseOperationSet.getUUID());
        ((ParseRESTCommand)localObject4).retainLocalIds();
        localObject1 = localArrayList.iterator();
        while (((Iterator)localObject1).hasNext()) {
          ((ParseObject)((Iterator)localObject1).next()).saveEventually();
        }
        localObject2 = finally;
      }
      catch (ParseException localParseException1)
      {
        throw new IllegalStateException("Unable to saveEventually.", localParseException1);
      }
      throw ((Throwable)localObject2);
    }
    catch (ParseException localParseException2)
    {
      localTask = Task.forError(localParseException2);
      return localTask;
    }
    Task localTask = Parse.getEventuallyQueue().enqueueEventuallyAsync((ParseRESTCommand)localObject4, this);
    enqueueSaveEventuallyOperationAsync(localParseOperationSet);
    ((ParseRESTCommand)localObject4).releaseLocalIds();
    if (Parse.isLocalDatastoreEnabled()) {
      return localTask.makeVoid();
    }
    localTask.onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<JSONObject> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (JSONObject)paramAnonymousTask.getResult();
        return ParseObject.this.handleSaveEventuallyResultAsync(paramAnonymousTask, localParseOperationSet);
      }
    });
  }
  
  public final void saveEventually(SaveCallback paramSaveCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(saveEventually(), paramSaveCallback);
  }
  
  public final Task<Void> saveInBackground()
  {
    ParseUser.getCurrentUserAsync().onSuccessTask(new Continuation()
    {
      public Task<String> then(final Task<ParseUser> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (ParseUser)paramAnonymousTask.getResult();
        if (paramAnonymousTask == null) {
          return Task.forResult(null);
        }
        if (!paramAnonymousTask.isLazy()) {
          return Task.forResult(paramAnonymousTask.getSessionToken());
        }
        if (!ParseObject.this.isDataAvailable("ACL")) {
          return Task.forResult(null);
        }
        paramAnonymousTask = ParseObject.this.getACL(false);
        if (paramAnonymousTask == null) {
          return Task.forResult(null);
        }
        final ParseUser localParseUser = paramAnonymousTask.getUnresolvedUser();
        if ((localParseUser == null) || (!localParseUser.isCurrentUser())) {
          return Task.forResult(null);
        }
        localParseUser.saveAsync(null).onSuccess(new Continuation()
        {
          public String then(Task<Void> paramAnonymous2Task)
            throws Exception
          {
            if (paramAnonymousTask.hasUnresolvedUser()) {
              throw new IllegalStateException("ACL has an unresolved ParseUser. Save or sign up before attempting to serialize the ACL.");
            }
            return localParseUser.getSessionToken();
          }
        });
      }
    }).onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<String> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (String)paramAnonymousTask.getResult();
        return ParseObject.this.saveAsync(paramAnonymousTask);
      }
    });
  }
  
  public final void saveInBackground(SaveCallback paramSaveCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(saveInBackground(), paramSaveCallback);
  }
  
  public void setACL(ParseACL paramParseACL)
  {
    put("ACL", paramParseACL);
  }
  
  void setDefaultValues()
  {
    if ((needsDefaultACL()) && (ParseACL.getDefaultACL() != null)) {
      setACL(ParseACL.getDefaultACL());
    }
  }
  
  public void setObjectId(String paramString)
  {
    synchronized (this.mutex)
    {
      String str = this.state.objectId();
      if (ParseTextUtils.equals(str, paramString)) {
        return;
      }
      this.state = this.state.newBuilder().objectId(paramString).build();
      notifyObjectIdChanged(str, paramString);
      return;
    }
  }
  
  void setState(State paramState)
  {
    synchronized (this.mutex)
    {
      setState(paramState, true);
      return;
    }
  }
  
  ParseOperationSet startSave()
  {
    synchronized (this.mutex)
    {
      ParseOperationSet localParseOperationSet = currentOperations();
      this.operationSetQueue.addLast(new ParseOperationSet());
      return localParseOperationSet;
    }
  }
  
  <T extends State> JSONObject toJSONObjectForSaving(T paramT, ParseOperationSet paramParseOperationSet, ParseEncoder paramParseEncoder)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      Iterator localIterator = paramParseOperationSet.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        localJSONObject.put(str, paramParseEncoder.encode((ParseFieldOperation)paramParseOperationSet.get(str)));
      }
      if (paramT.objectId() == null) {
        break label97;
      }
    }
    catch (JSONException paramT)
    {
      throw new RuntimeException("could not serialize object to JSON");
    }
    localJSONObject.put("objectId", paramT.objectId());
    label97:
    return localJSONObject;
  }
  
  JSONObject toRest(ParseEncoder paramParseEncoder)
  {
    synchronized (this.mutex)
    {
      State localState = getState();
      int j = this.operationSetQueue.size();
      ArrayList localArrayList = new ArrayList(j);
      int i = 0;
      while (i < j)
      {
        localArrayList.add(new ParseOperationSet((ParseOperationSet)this.operationSetQueue.get(i)));
        i += 1;
      }
      return toRest(localState, localArrayList, paramParseEncoder);
    }
  }
  
  JSONObject toRest(State paramState, List<ParseOperationSet> paramList, ParseEncoder paramParseEncoder)
  {
    checkForChangesToMutableContainers();
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("className", paramState.className());
      if (paramState.objectId() != null) {
        localJSONObject.put("objectId", paramState.objectId());
      }
      if (paramState.createdAt() > 0L) {
        localJSONObject.put("createdAt", ParseDateFormat.getInstance().format(new Date(paramState.createdAt())));
      }
      if (paramState.updatedAt() > 0L) {
        localJSONObject.put("updatedAt", ParseDateFormat.getInstance().format(new Date(paramState.updatedAt())));
      }
      Iterator localIterator = paramState.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        localJSONObject.put(str, paramParseEncoder.encode(paramState.get(str)));
      }
      localJSONObject.put("__complete", paramState.isComplete());
    }
    catch (JSONException paramState)
    {
      throw new RuntimeException("could not serialize object to JSON");
    }
    localJSONObject.put("__isDeletingEventually", this.isDeletingEventually);
    paramState = new JSONArray();
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      paramState.put(((ParseOperationSet)paramList.next()).toRest(paramParseEncoder));
    }
    localJSONObject.put("__operations", paramState);
    return localJSONObject;
  }
  
  public void unpin()
    throws ParseException
  {
    ParseTaskUtils.wait(unpinInBackground());
  }
  
  public void unpin(String paramString)
    throws ParseException
  {
    ParseTaskUtils.wait(unpinInBackground(paramString));
  }
  
  public Task<Void> unpinInBackground()
  {
    return unpinAllInBackground("_default", Arrays.asList(new ParseObject[] { this }));
  }
  
  public Task<Void> unpinInBackground(String paramString)
  {
    return unpinAllInBackground(paramString, Arrays.asList(new ParseObject[] { this }));
  }
  
  public void unpinInBackground(DeleteCallback paramDeleteCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(unpinInBackground(), paramDeleteCallback);
  }
  
  public void unpinInBackground(String paramString, DeleteCallback paramDeleteCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(unpinInBackground(paramString), paramDeleteCallback);
  }
  
  void unregisterSaveListener(GetCallback<ParseObject> paramGetCallback)
  {
    synchronized (this.mutex)
    {
      this.saveEvent.unsubscribe(paramGetCallback);
      return;
    }
  }
  
  void updateBeforeSave() {}
  
  void validateDelete() {}
  
  void validateSave() {}
  
  void validateSaveEventually()
    throws ParseException
  {}
  
  static class State
  {
    private final String className;
    private final long createdAt;
    private final boolean isComplete;
    private final String objectId;
    private final Map<String, Object> serverData;
    private final long updatedAt;
    
    State(Init<?> paramInit)
    {
      this.className = paramInit.className;
      this.objectId = paramInit.objectId;
      this.createdAt = paramInit.createdAt;
      if (paramInit.updatedAt > 0L) {}
      for (long l = paramInit.updatedAt;; l = this.createdAt)
      {
        this.updatedAt = l;
        this.serverData = Collections.unmodifiableMap(new HashMap(paramInit.serverData));
        this.isComplete = paramInit.isComplete;
        return;
      }
    }
    
    public static Init<?> newBuilder(String paramString)
    {
      if ("_User".equals(paramString)) {
        return new ParseUser.State.Builder();
      }
      return new Builder(paramString);
    }
    
    public String className()
    {
      return this.className;
    }
    
    public long createdAt()
    {
      return this.createdAt;
    }
    
    public Object get(String paramString)
    {
      return this.serverData.get(paramString);
    }
    
    public boolean isComplete()
    {
      return this.isComplete;
    }
    
    public Set<String> keySet()
    {
      return this.serverData.keySet();
    }
    
    public <T extends Init<?>> T newBuilder()
    {
      return new Builder(this);
    }
    
    public String objectId()
    {
      return this.objectId;
    }
    
    public String toString()
    {
      return String.format(Locale.US, "%s@%s[className=%s, objectId=%s, createdAt=%d, updatedAt=%d, isComplete=%s, serverData=%s]", new Object[] { getClass().getName(), Integer.toHexString(hashCode()), this.className, this.objectId, Long.valueOf(this.createdAt), Long.valueOf(this.updatedAt), Boolean.valueOf(this.isComplete), this.serverData });
    }
    
    public long updatedAt()
    {
      return this.updatedAt;
    }
    
    static class Builder
      extends ParseObject.State.Init<Builder>
    {
      public Builder(ParseObject.State paramState)
      {
        super();
      }
      
      public Builder(String paramString)
      {
        super();
      }
      
      public ParseObject.State build()
      {
        return new ParseObject.State(this);
      }
      
      Builder self()
      {
        return this;
      }
    }
    
    static abstract class Init<T extends Init>
    {
      private final String className;
      private long createdAt = -1L;
      private boolean isComplete;
      private String objectId;
      Map<String, Object> serverData = new HashMap();
      private long updatedAt = -1L;
      
      Init(ParseObject.State paramState)
      {
        this.className = paramState.className();
        this.objectId = paramState.objectId();
        this.createdAt = paramState.createdAt();
        this.updatedAt = paramState.updatedAt();
        Iterator localIterator = paramState.keySet().iterator();
        while (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          this.serverData.put(str, paramState.get(str));
        }
        this.isComplete = paramState.isComplete();
      }
      
      public Init(String paramString)
      {
        this.className = paramString;
      }
      
      public T apply(ParseObject.State paramState)
      {
        if (paramState.objectId() != null) {
          objectId(paramState.objectId());
        }
        if (paramState.createdAt() > 0L) {
          createdAt(paramState.createdAt());
        }
        if (paramState.updatedAt() > 0L) {
          updatedAt(paramState.updatedAt());
        }
        if ((this.isComplete) || (paramState.isComplete())) {}
        for (boolean bool = true;; bool = false)
        {
          isComplete(bool);
          Iterator localIterator = paramState.keySet().iterator();
          while (localIterator.hasNext())
          {
            String str = (String)localIterator.next();
            put(str, paramState.get(str));
          }
        }
        return self();
      }
      
      public T apply(ParseOperationSet paramParseOperationSet)
      {
        Iterator localIterator = paramParseOperationSet.keySet().iterator();
        while (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          Object localObject = ((ParseFieldOperation)paramParseOperationSet.get(str)).apply(this.serverData.get(str), str);
          if (localObject != null) {
            put(str, localObject);
          } else {
            remove(str);
          }
        }
        return self();
      }
      
      abstract <S extends ParseObject.State> S build();
      
      public T clear()
      {
        this.objectId = null;
        this.createdAt = -1L;
        this.updatedAt = -1L;
        this.isComplete = false;
        this.serverData.clear();
        return self();
      }
      
      public T createdAt(long paramLong)
      {
        this.createdAt = paramLong;
        return self();
      }
      
      public T createdAt(Date paramDate)
      {
        this.createdAt = paramDate.getTime();
        return self();
      }
      
      public T isComplete(boolean paramBoolean)
      {
        this.isComplete = paramBoolean;
        return self();
      }
      
      public T objectId(String paramString)
      {
        this.objectId = paramString;
        return self();
      }
      
      public T put(String paramString, Object paramObject)
      {
        this.serverData.put(paramString, paramObject);
        return self();
      }
      
      public T remove(String paramString)
      {
        this.serverData.remove(paramString);
        return self();
      }
      
      abstract T self();
      
      public T updatedAt(long paramLong)
      {
        this.updatedAt = paramLong;
        return self();
      }
      
      public T updatedAt(Date paramDate)
      {
        this.updatedAt = paramDate.getTime();
        return self();
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */