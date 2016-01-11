package com.parse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Pair;
import bolts.Capture;
import bolts.Continuation;
import bolts.Task;
import bolts.Task.TaskCompletionSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import org.json.JSONException;
import org.json.JSONObject;

class OfflineStore
{
  private static final int MAX_SQL_VARIABLES = 999;
  private final WeakValueHashMap<Pair<String, String>, ParseObject> classNameAndObjectIdToObjectMap = new WeakValueHashMap();
  private final WeakHashMap<ParseObject, Task<ParseObject>> fetchedObjects = new WeakHashMap();
  private final OfflineSQLiteOpenHelper helper;
  private final Object lock = new Object();
  private final WeakHashMap<ParseObject, Task<String>> objectToUuidMap = new WeakHashMap();
  private final WeakValueHashMap<String, ParseObject> uuidToObjectMap = new WeakValueHashMap();
  
  OfflineStore(Context paramContext)
  {
    this(new OfflineSQLiteOpenHelper(paramContext));
  }
  
  OfflineStore(OfflineSQLiteOpenHelper paramOfflineSQLiteOpenHelper)
  {
    this.helper = paramOfflineSQLiteOpenHelper;
  }
  
  private <T extends ParseObject> Task<Integer> countFromPinAsync(String paramString, final ParseQuery.State<T> paramState, final ParseUser paramParseUser, final ParseSQLiteDatabase paramParseSQLiteDatabase)
  {
    if (paramString != null) {}
    for (paramString = getParsePin(paramString, paramParseSQLiteDatabase);; paramString = Task.forResult(null)) {
      paramString.onSuccessTask(new Continuation()
      {
        public Task<Integer> then(Task<ParsePin> paramAnonymousTask)
          throws Exception
        {
          paramAnonymousTask = (ParsePin)paramAnonymousTask.getResult();
          OfflineStore.this.findAsync(paramState, paramParseUser, paramAnonymousTask, true, paramParseSQLiteDatabase).onSuccess(new Continuation()
          {
            public Integer then(Task<List<T>> paramAnonymous2Task)
              throws Exception
            {
              return Integer.valueOf(((List)paramAnonymous2Task.getResult()).size());
            }
          });
        }
      });
    }
  }
  
  private Task<Void> deleteDataForObjectAsync(final ParseObject paramParseObject, final ParseSQLiteDatabase paramParseSQLiteDatabase)
  {
    final Capture localCapture = new Capture();
    synchronized (this.lock)
    {
      Task localTask = (Task)this.objectToUuidMap.get(paramParseObject);
      if (localTask == null)
      {
        paramParseObject = Task.forResult(null);
        return paramParseObject;
      }
      localTask.onSuccessTask(new Continuation()
      {
        public Task<String> then(Task<String> paramAnonymousTask)
          throws Exception
        {
          localCapture.set(paramAnonymousTask.getResult());
          return paramAnonymousTask;
        }
      }).onSuccessTask(new Continuation()
      {
        public Task<Cursor> then(Task<String> paramAnonymousTask)
          throws Exception
        {
          paramAnonymousTask = (String)localCapture.get();
          return paramParseSQLiteDatabase.queryAsync("Dependencies", new String[] { "key" }, "uuid=?", new String[] { paramAnonymousTask });
        }
      }).onSuccessTask(new Continuation()
      {
        public Task<Void> then(Task<Cursor> paramAnonymousTask)
          throws Exception
        {
          paramAnonymousTask = (Cursor)paramAnonymousTask.getResult();
          Object localObject = new ArrayList();
          paramAnonymousTask.moveToFirst();
          while (!paramAnonymousTask.isAfterLast())
          {
            ((List)localObject).add(paramAnonymousTask.getString(0));
            paramAnonymousTask.moveToNext();
          }
          paramAnonymousTask.close();
          paramAnonymousTask = new ArrayList();
          localObject = ((List)localObject).iterator();
          while (((Iterator)localObject).hasNext())
          {
            final String str = (String)((Iterator)localObject).next();
            paramAnonymousTask.add(OfflineStore.this.getPointerAsync(str, paramParseSQLiteDatabase).onSuccessTask(new Continuation()
            {
              public Task<ParsePin> then(Task<ParseObject> paramAnonymous2Task)
                throws Exception
              {
                paramAnonymous2Task = (ParsePin)paramAnonymous2Task.getResult();
                return OfflineStore.this.fetchLocallyAsync(paramAnonymous2Task, OfflineStore.31.this.val$db);
              }
            }).continueWithTask(new Continuation()
            {
              public Task<Void> then(Task<ParsePin> paramAnonymous2Task)
                throws Exception
              {
                ParsePin localParsePin = (ParsePin)paramAnonymous2Task.getResult();
                List localList = localParsePin.getObjects();
                if ((localList == null) || (!localList.contains(OfflineStore.31.this.val$object))) {
                  return paramAnonymous2Task.makeVoid();
                }
                localList.remove(OfflineStore.31.this.val$object);
                if (localList.size() == 0) {
                  return OfflineStore.this.unpinAsync(str, OfflineStore.31.this.val$db);
                }
                localParsePin.setObjects(localList);
                return OfflineStore.this.saveLocallyAsync(localParsePin, true, OfflineStore.31.this.val$db);
              }
            }));
          }
          return Task.whenAll(paramAnonymousTask);
        }
      }).onSuccessTask(new Continuation()
      {
        public Task<Void> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          paramAnonymousTask = (String)localCapture.get();
          return paramParseSQLiteDatabase.deleteAsync("Dependencies", "uuid=?", new String[] { paramAnonymousTask });
        }
      }).onSuccessTask(new Continuation()
      {
        public Task<Void> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          paramAnonymousTask = (String)localCapture.get();
          return paramParseSQLiteDatabase.deleteAsync("ParseObjects", "uuid=?", new String[] { paramAnonymousTask });
        }
      }).onSuccessTask(new Continuation()
      {
        public Task<Void> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          synchronized (OfflineStore.this.lock)
          {
            OfflineStore.this.fetchedObjects.remove(paramParseObject);
            return paramAnonymousTask;
          }
        }
      });
    }
  }
  
  private Task<Void> deleteObjects(final List<String> paramList, final ParseSQLiteDatabase paramParseSQLiteDatabase)
  {
    if (paramList.size() <= 0) {
      return Task.forResult(null);
    }
    if (paramList.size() > 999) {
      deleteObjects(paramList.subList(0, 999), paramParseSQLiteDatabase).onSuccessTask(new Continuation()
      {
        public Task<Void> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          return OfflineStore.this.deleteObjects(paramList.subList(999, paramList.size()), paramParseSQLiteDatabase);
        }
      });
    }
    String[] arrayOfString = new String[paramList.size()];
    int i = 0;
    while (i < arrayOfString.length)
    {
      arrayOfString[i] = "?";
      i += 1;
    }
    return paramParseSQLiteDatabase.deleteAsync("ParseObjects", "uuid IN (" + TextUtils.join(",", arrayOfString) + ")", (String[])paramList.toArray(new String[paramList.size()]));
  }
  
  private <T extends ParseObject> Task<List<T>> findAsync(final ParseQuery.State<T> paramState, final ParseUser paramParseUser, ParsePin paramParsePin, final boolean paramBoolean, final ParseSQLiteDatabase paramParseSQLiteDatabase)
  {
    final OfflineQueryLogic localOfflineQueryLogic = new OfflineQueryLogic(this);
    final ArrayList localArrayList = new ArrayList();
    String str;
    if (paramParsePin == null)
    {
      paramParsePin = "className=?" + " AND isDeletingEventually=0";
      str = paramState.className();
    }
    for (paramParsePin = paramParseSQLiteDatabase.queryAsync("ParseObjects", new String[] { "uuid" }, paramParsePin, new String[] { str });; paramParsePin = paramParsePin.onSuccessTask(new Continuation()
        {
          public Task<Cursor> then(Task<String> paramAnonymousTask)
            throws Exception
          {
            paramAnonymousTask = (String)paramAnonymousTask.getResult();
            String str1 = "className=? AND key=?" + " AND isDeletingEventually=0";
            String str2 = paramState.className();
            return paramParseSQLiteDatabase.queryAsync("ParseObjects A  INNER JOIN Dependencies B  ON A.uuid=B.uuid", new String[] { "A.uuid" }, str1, new String[] { str2, paramAnonymousTask });
          }
        }))
    {
      paramParsePin.onSuccessTask(new Continuation()
      {
        public Task<Void> then(Task<Cursor> paramAnonymousTask)
          throws Exception
        {
          paramAnonymousTask = (Cursor)paramAnonymousTask.getResult();
          Object localObject = new ArrayList();
          paramAnonymousTask.moveToFirst();
          while (!paramAnonymousTask.isAfterLast())
          {
            ((List)localObject).add(paramAnonymousTask.getString(0));
            paramAnonymousTask.moveToNext();
          }
          paramAnonymousTask.close();
          final OfflineQueryLogic.ConstraintMatcher localConstraintMatcher = localOfflineQueryLogic.createMatcher(paramState, paramParseUser);
          paramAnonymousTask = Task.forResult(null);
          localObject = ((List)localObject).iterator();
          while (((Iterator)localObject).hasNext())
          {
            final String str = (String)((Iterator)localObject).next();
            final Capture localCapture = new Capture();
            paramAnonymousTask = paramAnonymousTask.onSuccessTask(new Continuation()
            {
              public Task<T> then(Task<Void> paramAnonymous2Task)
                throws Exception
              {
                return OfflineStore.this.getPointerAsync(str, OfflineStore.6.this.val$db);
              }
            }).onSuccessTask(new Continuation()
            {
              public Task<T> then(Task<T> paramAnonymous2Task)
                throws Exception
              {
                localCapture.set(paramAnonymous2Task.getResult());
                return OfflineStore.this.fetchLocallyAsync((ParseObject)localCapture.get(), OfflineStore.6.this.val$db);
              }
            }).onSuccessTask(new Continuation()
            {
              public Task<Boolean> then(Task<T> paramAnonymous2Task)
                throws Exception
              {
                if (!((ParseObject)localCapture.get()).isDataAvailable()) {
                  return Task.forResult(Boolean.valueOf(false));
                }
                return localConstraintMatcher.matchesAsync((ParseObject)localCapture.get(), OfflineStore.6.this.val$db);
              }
            }).onSuccess(new Continuation()
            {
              public Void then(Task<Boolean> paramAnonymous2Task)
              {
                if (((Boolean)paramAnonymous2Task.getResult()).booleanValue()) {
                  OfflineStore.6.this.val$results.add(localCapture.get());
                }
                return null;
              }
            });
          }
          return paramAnonymousTask;
        }
      }).onSuccessTask(new Continuation()
      {
        public Task<List<T>> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          OfflineQueryLogic.sort(localArrayList, paramState);
          final Object localObject = localArrayList;
          int i = paramState.skip();
          paramAnonymousTask = (Task<Void>)localObject;
          if (!paramBoolean)
          {
            paramAnonymousTask = (Task<Void>)localObject;
            if (i >= 0) {
              paramAnonymousTask = ((List)localObject).subList(Math.min(paramState.skip(), ((List)localObject).size()), ((List)localObject).size());
            }
          }
          i = paramState.limit();
          localObject = paramAnonymousTask;
          if (!paramBoolean)
          {
            localObject = paramAnonymousTask;
            if (i >= 0)
            {
              localObject = paramAnonymousTask;
              if (paramAnonymousTask.size() > i) {
                localObject = paramAnonymousTask.subList(0, i);
              }
            }
          }
          paramAnonymousTask = Task.forResult(null);
          Iterator localIterator = ((List)localObject).iterator();
          while (localIterator.hasNext()) {
            paramAnonymousTask = paramAnonymousTask.onSuccessTask(new Continuation()
            {
              public Task<Void> then(Task<Void> paramAnonymous2Task)
                throws Exception
              {
                return OfflineStore.5.this.val$queryLogic.fetchIncludesAsync(this.val$object, OfflineStore.5.this.val$query, OfflineStore.5.this.val$db);
              }
            });
          }
          paramAnonymousTask.onSuccess(new Continuation()
          {
            public List<T> then(Task<Void> paramAnonymous2Task)
              throws Exception
            {
              return localObject;
            }
          });
        }
      });
      paramParsePin = (Task)this.objectToUuidMap.get(paramParsePin);
      if (paramParsePin == null) {
        return Task.forResult(localArrayList);
      }
    }
  }
  
  private <T extends ParseObject> Task<List<T>> findFromPinAsync(String paramString, final ParseQuery.State<T> paramState, final ParseUser paramParseUser, final ParseSQLiteDatabase paramParseSQLiteDatabase)
  {
    if (paramString != null) {}
    for (paramString = getParsePin(paramString, paramParseSQLiteDatabase);; paramString = Task.forResult(null)) {
      paramString.onSuccessTask(new Continuation()
      {
        public Task<List<T>> then(Task<ParsePin> paramAnonymousTask)
          throws Exception
        {
          paramAnonymousTask = (ParsePin)paramAnonymousTask.getResult();
          return OfflineStore.this.findAsync(paramState, paramParseUser, paramAnonymousTask, false, paramParseSQLiteDatabase);
        }
      });
    }
  }
  
  private Task<String> getOrCreateUUIDAsync(final ParseObject paramParseObject, ParseSQLiteDatabase paramParseSQLiteDatabase)
  {
    final String str = UUID.randomUUID().toString();
    final Task.TaskCompletionSource localTaskCompletionSource = Task.create();
    synchronized (this.lock)
    {
      Task localTask = (Task)this.objectToUuidMap.get(paramParseObject);
      if (localTask != null) {
        return localTask;
      }
      this.objectToUuidMap.put(paramParseObject, localTaskCompletionSource.getTask());
      this.uuidToObjectMap.put(str, paramParseObject);
      this.fetchedObjects.put(paramParseObject, localTaskCompletionSource.getTask().onSuccess(new Continuation()
      {
        public ParseObject then(Task<String> paramAnonymousTask)
          throws Exception
        {
          return paramParseObject;
        }
      }));
      ??? = new ContentValues();
      ((ContentValues)???).put("uuid", str);
      ((ContentValues)???).put("className", paramParseObject.getClassName());
      paramParseSQLiteDatabase.insertOrThrowAsync("ParseObjects", (ContentValues)???).continueWith(new Continuation()
      {
        public Void then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          localTaskCompletionSource.setResult(str);
          return null;
        }
      });
      return localTaskCompletionSource.getTask();
    }
  }
  
  private Task<ParsePin> getParsePin(final String paramString, ParseSQLiteDatabase paramParseSQLiteDatabase)
  {
    findAsync(new ParseQuery.State.Builder(ParsePin.class).whereEqualTo("_name", paramString).build(), null, null, paramParseSQLiteDatabase).onSuccess(new Continuation()
    {
      public ParsePin then(Task<List<ParsePin>> paramAnonymousTask)
        throws Exception
      {
        Object localObject2 = null;
        Object localObject1 = localObject2;
        if (paramAnonymousTask.getResult() != null)
        {
          localObject1 = localObject2;
          if (((List)paramAnonymousTask.getResult()).size() > 0) {
            localObject1 = (ParsePin)((List)paramAnonymousTask.getResult()).get(0);
          }
        }
        paramAnonymousTask = (Task<List<ParsePin>>)localObject1;
        if (localObject1 == null)
        {
          paramAnonymousTask = (ParsePin)ParseObject.create(ParsePin.class);
          paramAnonymousTask.setName(paramString);
        }
        return paramAnonymousTask;
      }
    });
  }
  
  private <T extends ParseObject> Task<T> getPointerAsync(final String paramString, ParseSQLiteDatabase paramParseSQLiteDatabase)
  {
    synchronized (this.lock)
    {
      ParseObject localParseObject = (ParseObject)this.uuidToObjectMap.get(paramString);
      if (localParseObject != null)
      {
        paramString = Task.forResult(localParseObject);
        return paramString;
      }
      paramParseSQLiteDatabase.queryAsync("ParseObjects", new String[] { "className", "objectId" }, "uuid = ?", new String[] { paramString }).onSuccess(new Continuation()
      {
        public T then(Task<Cursor> arg1)
          throws Exception
        {
          Object localObject1 = (Cursor)???.getResult();
          ((Cursor)localObject1).moveToFirst();
          if (((Cursor)localObject1).isAfterLast())
          {
            ((Cursor)localObject1).close();
            throw new IllegalStateException("Attempted to find non-existent uuid " + paramString);
          }
          synchronized (OfflineStore.this.lock)
          {
            Object localObject3 = (ParseObject)OfflineStore.this.uuidToObjectMap.get(paramString);
            if (localObject3 != null) {
              return (T)localObject3;
            }
            String str = ((Cursor)localObject1).getString(0);
            localObject3 = ((Cursor)localObject1).getString(1);
            ((Cursor)localObject1).close();
            localObject1 = ParseObject.createWithoutData(str, (String)localObject3);
            if (localObject3 == null)
            {
              OfflineStore.this.uuidToObjectMap.put(paramString, localObject1);
              OfflineStore.this.objectToUuidMap.put(localObject1, Task.forResult(paramString));
            }
            return (T)localObject1;
          }
        }
      });
    }
  }
  
  private <T extends ParseObject> Task<Void> pinAllObjectsAsync(String paramString, final List<T> paramList, final boolean paramBoolean, final ParseSQLiteDatabase paramParseSQLiteDatabase)
  {
    if ((paramList == null) || (paramList.size() == 0)) {
      return Task.forResult(null);
    }
    getParsePin(paramString, paramParseSQLiteDatabase).onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<ParsePin> paramAnonymousTask)
        throws Exception
      {
        ParsePin localParsePin = (ParsePin)paramAnonymousTask.getResult();
        List localList = localParsePin.getObjects();
        if (localList == null)
        {
          paramAnonymousTask = new ArrayList(paramList);
          localParsePin.setObjects(paramAnonymousTask);
          if (paramBoolean) {
            return OfflineStore.this.saveLocallyAsync(localParsePin, true, paramParseSQLiteDatabase);
          }
        }
        else
        {
          Iterator localIterator = paramList.iterator();
          for (;;)
          {
            paramAnonymousTask = localList;
            if (!localIterator.hasNext()) {
              break;
            }
            paramAnonymousTask = (ParseObject)localIterator.next();
            if (!localList.contains(paramAnonymousTask)) {
              localList.add(paramAnonymousTask);
            }
          }
        }
        return OfflineStore.this.saveLocallyAsync(localParsePin, localParsePin.getObjects(), paramParseSQLiteDatabase);
      }
    });
  }
  
  private <T> Task<T> runWithManagedConnection(final SQLiteDatabaseCallable<Task<T>> paramSQLiteDatabaseCallable)
  {
    this.helper.getWritableDatabaseAsync().onSuccessTask(new Continuation()
    {
      public Task<T> then(final Task<ParseSQLiteDatabase> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (ParseSQLiteDatabase)paramAnonymousTask.getResult();
        ((Task)paramSQLiteDatabaseCallable.call(paramAnonymousTask)).continueWithTask(new Continuation()
        {
          public Task<T> then(Task<T> paramAnonymous2Task)
            throws Exception
          {
            paramAnonymousTask.closeAsync();
            return paramAnonymous2Task;
          }
        });
      }
    });
  }
  
  private Task<Void> runWithManagedTransaction(final SQLiteDatabaseCallable<Task<Void>> paramSQLiteDatabaseCallable)
  {
    this.helper.getWritableDatabaseAsync().onSuccessTask(new Continuation()
    {
      public Task<Void> then(final Task<ParseSQLiteDatabase> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (ParseSQLiteDatabase)paramAnonymousTask.getResult();
        paramAnonymousTask.beginTransactionAsync().onSuccessTask(new Continuation()
        {
          public Task<Void> then(Task<Void> paramAnonymous2Task)
            throws Exception
          {
            ((Task)OfflineStore.48.this.val$callable.call(paramAnonymousTask)).onSuccessTask(new Continuation()
            {
              public Task<Void> then(Task<Void> paramAnonymous3Task)
                throws Exception
              {
                return OfflineStore.48.1.this.val$db.setTransactionSuccessfulAsync();
              }
            }).continueWithTask(new Continuation()
            {
              public Task<Void> then(Task<Void> paramAnonymous3Task)
                throws Exception
              {
                OfflineStore.48.1.this.val$db.endTransactionAsync();
                OfflineStore.48.1.this.val$db.closeAsync();
                return paramAnonymous3Task;
              }
            });
          }
        });
      }
    });
  }
  
  private Task<Void> saveLocallyAsync(final ParseObject paramParseObject, final List<ParseObject> paramList, final ParseSQLiteDatabase paramParseSQLiteDatabase)
  {
    if (paramList != null) {}
    ArrayList localArrayList;
    for (paramList = new ArrayList(paramList);; paramList = new ArrayList())
    {
      if (!paramList.contains(paramParseObject)) {
        paramList.add(paramParseObject);
      }
      localArrayList = new ArrayList();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext()) {
        localArrayList.add(fetchLocallyAsync((ParseObject)localIterator.next(), paramParseSQLiteDatabase).makeVoid());
      }
    }
    Task.whenAll(localArrayList).continueWithTask(new Continuation()
    {
      public Task<String> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return (Task)OfflineStore.this.objectToUuidMap.get(paramParseObject);
      }
    }).onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<String> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (String)paramAnonymousTask.getResult();
        if (paramAnonymousTask == null) {
          return null;
        }
        return OfflineStore.this.unpinAsync(paramAnonymousTask, paramParseSQLiteDatabase);
      }
    }).onSuccessTask(new Continuation()
    {
      public Task<String> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return OfflineStore.this.getOrCreateUUIDAsync(paramParseObject, paramParseSQLiteDatabase);
      }
    }).onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<String> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (String)paramAnonymousTask.getResult();
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext())
        {
          ParseObject localParseObject = (ParseObject)localIterator.next();
          localArrayList.add(OfflineStore.this.saveLocallyAsync(paramAnonymousTask, localParseObject, paramParseSQLiteDatabase));
        }
        return Task.whenAll(localArrayList);
      }
    });
  }
  
  private Task<Void> saveLocallyAsync(ParseObject paramParseObject, boolean paramBoolean, ParseSQLiteDatabase paramParseSQLiteDatabase)
  {
    final ArrayList localArrayList = new ArrayList();
    if (!paramBoolean) {
      localArrayList.add(paramParseObject);
    }
    for (;;)
    {
      return saveLocallyAsync(paramParseObject, localArrayList, paramParseSQLiteDatabase);
      new ParseTraverser()
      {
        protected boolean visit(Object paramAnonymousObject)
        {
          if ((paramAnonymousObject instanceof ParseObject)) {
            localArrayList.add((ParseObject)paramAnonymousObject);
          }
          return true;
        }
      }.setYieldRoot(true).setTraverseParseObjects(true).traverse(paramParseObject);
    }
  }
  
  private Task<Void> saveLocallyAsync(final String paramString, final ParseObject paramParseObject, final ParseSQLiteDatabase paramParseSQLiteDatabase)
  {
    if ((paramParseObject.getObjectId() != null) && (!paramParseObject.isDataAvailable()) && (!paramParseObject.hasChanges()) && (!paramParseObject.hasOutstandingOperations())) {
      return Task.forResult(null);
    }
    final Capture localCapture = new Capture();
    getOrCreateUUIDAsync(paramParseObject, paramParseSQLiteDatabase).onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<String> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (String)paramAnonymousTask.getResult();
        localCapture.set(paramAnonymousTask);
        return OfflineStore.this.updateDataForObjectAsync(paramAnonymousTask, paramParseObject, paramParseSQLiteDatabase);
      }
    }).onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = new ContentValues();
        paramAnonymousTask.put("key", paramString);
        paramAnonymousTask.put("uuid", (String)localCapture.get());
        return paramParseSQLiteDatabase.insertWithOnConflict("Dependencies", paramAnonymousTask, 4);
      }
    });
  }
  
  private Task<Void> unpinAllObjectsAsync(String paramString, final ParseSQLiteDatabase paramParseSQLiteDatabase)
  {
    getParsePin(paramString, paramParseSQLiteDatabase).continueWithTask(new Continuation()
    {
      public Task<Void> then(Task<ParsePin> paramAnonymousTask)
        throws Exception
      {
        if (paramAnonymousTask.isFaulted()) {
          return paramAnonymousTask.makeVoid();
        }
        paramAnonymousTask = (ParsePin)paramAnonymousTask.getResult();
        return OfflineStore.this.unpinAsync(paramAnonymousTask, paramParseSQLiteDatabase);
      }
    });
  }
  
  private <T extends ParseObject> Task<Void> unpinAllObjectsAsync(String paramString, final List<T> paramList, final ParseSQLiteDatabase paramParseSQLiteDatabase)
  {
    if ((paramList == null) || (paramList.size() == 0)) {
      return Task.forResult(null);
    }
    getParsePin(paramString, paramParseSQLiteDatabase).onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<ParsePin> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (ParsePin)paramAnonymousTask.getResult();
        List localList = paramAnonymousTask.getObjects();
        if (localList == null) {
          return Task.forResult(null);
        }
        localList.removeAll(paramList);
        if (localList.size() == 0) {
          return OfflineStore.this.unpinAsync(paramAnonymousTask, paramParseSQLiteDatabase);
        }
        paramAnonymousTask.setObjects(localList);
        return OfflineStore.this.saveLocallyAsync(paramAnonymousTask, true, paramParseSQLiteDatabase);
      }
    });
  }
  
  private Task<Void> unpinAsync(ParseObject paramParseObject, final ParseSQLiteDatabase paramParseSQLiteDatabase)
  {
    paramParseObject = (Task)this.objectToUuidMap.get(paramParseObject);
    if (paramParseObject == null) {
      return Task.forResult(null);
    }
    paramParseObject.continueWithTask(new Continuation()
    {
      public Task<Void> then(Task<String> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (String)paramAnonymousTask.getResult();
        if (paramAnonymousTask == null) {
          return Task.forResult(null);
        }
        return OfflineStore.this.unpinAsync(paramAnonymousTask, paramParseSQLiteDatabase);
      }
    });
  }
  
  private Task<Void> unpinAsync(final String paramString, final ParseSQLiteDatabase paramParseSQLiteDatabase)
  {
    final LinkedList localLinkedList = new LinkedList();
    Task.forResult((Void)null).continueWithTask(new Continuation()
    {
      public Task<Cursor> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = paramString;
        return paramParseSQLiteDatabase.rawQueryAsync("SELECT uuid FROM Dependencies WHERE key=? AND uuid IN ( SELECT uuid FROM Dependencies GROUP BY uuid HAVING COUNT(uuid)=1)", new String[] { paramAnonymousTask });
      }
    }).onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<Cursor> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (Cursor)paramAnonymousTask.getResult();
        while (paramAnonymousTask.moveToNext()) {
          localLinkedList.add(paramAnonymousTask.getString(0));
        }
        paramAnonymousTask.close();
        return OfflineStore.this.deleteObjects(localLinkedList, paramParseSQLiteDatabase);
      }
    }).onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = paramString;
        return paramParseSQLiteDatabase.deleteAsync("Dependencies", "key=?", new String[] { paramAnonymousTask });
      }
    }).onSuccess(new Continuation()
    {
      public Void then(Task<Void> arg1)
        throws Exception
      {
        synchronized (OfflineStore.this.lock)
        {
          Iterator localIterator = localLinkedList.iterator();
          while (localIterator.hasNext())
          {
            String str = (String)localIterator.next();
            ParseObject localParseObject = (ParseObject)OfflineStore.this.uuidToObjectMap.get(str);
            if (localParseObject != null)
            {
              OfflineStore.this.objectToUuidMap.remove(localParseObject);
              OfflineStore.this.uuidToObjectMap.remove(str);
            }
          }
        }
        return null;
      }
    });
  }
  
  private Task<Void> updateDataForObjectAsync(final ParseObject paramParseObject, final ParseSQLiteDatabase paramParseSQLiteDatabase)
  {
    synchronized (this.lock)
    {
      Task localTask = (Task)this.objectToUuidMap.get(paramParseObject);
      if (localTask == null)
      {
        paramParseObject = Task.forResult(null);
        return paramParseObject;
      }
      localTask.onSuccessTask(new Continuation()
      {
        public Task<Void> then(Task<String> paramAnonymousTask)
          throws Exception
        {
          paramAnonymousTask = (String)paramAnonymousTask.getResult();
          return OfflineStore.this.updateDataForObjectAsync(paramAnonymousTask, paramParseObject, paramParseSQLiteDatabase);
        }
      });
    }
  }
  
  private Task<Void> updateDataForObjectAsync(final String paramString, final ParseObject paramParseObject, final ParseSQLiteDatabase paramParseSQLiteDatabase)
  {
    OfflineEncoder localOfflineEncoder = new OfflineEncoder(paramParseSQLiteDatabase);
    final JSONObject localJSONObject = paramParseObject.toRest(localOfflineEncoder);
    localOfflineEncoder.whenFinished().onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        String str1 = paramParseObject.getClassName();
        String str2 = paramParseObject.getObjectId();
        int i = localJSONObject.getInt("__isDeletingEventually");
        paramAnonymousTask = new ContentValues();
        paramAnonymousTask.put("className", str1);
        paramAnonymousTask.put("json", localJSONObject.toString());
        if (str2 != null) {
          paramAnonymousTask.put("objectId", str2);
        }
        paramAnonymousTask.put("isDeletingEventually", Integer.valueOf(i));
        str1 = paramString;
        return paramParseSQLiteDatabase.updateAsync("ParseObjects", paramAnonymousTask, "uuid = ?", new String[] { str1 }).makeVoid();
      }
    });
  }
  
  void clearDatabase(Context paramContext)
  {
    this.helper.clearDatabase(paramContext);
  }
  
  <T extends ParseObject> Task<Integer> countFromPinAsync(final String paramString, final ParseQuery.State<T> paramState, final ParseUser paramParseUser)
  {
    runWithManagedConnection(new SQLiteDatabaseCallable()
    {
      public Task<Integer> call(ParseSQLiteDatabase paramAnonymousParseSQLiteDatabase)
      {
        return OfflineStore.this.countFromPinAsync(paramString, paramState, paramParseUser, paramAnonymousParseSQLiteDatabase);
      }
    });
  }
  
  Task<Void> deleteDataForObjectAsync(final ParseObject paramParseObject)
  {
    this.helper.getWritableDatabaseAsync().continueWithTask(new Continuation()
    {
      public Task<Void> then(final Task<ParseSQLiteDatabase> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (ParseSQLiteDatabase)paramAnonymousTask.getResult();
        paramAnonymousTask.beginTransactionAsync().onSuccessTask(new Continuation()
        {
          public Task<Void> then(Task<Void> paramAnonymous2Task)
            throws Exception
          {
            OfflineStore.this.deleteDataForObjectAsync(OfflineStore.29.this.val$object, paramAnonymousTask).onSuccessTask(new Continuation()
            {
              public Task<Void> then(Task<Void> paramAnonymous3Task)
                throws Exception
              {
                return OfflineStore.29.1.this.val$db.setTransactionSuccessfulAsync();
              }
            }).continueWithTask(new Continuation()
            {
              public Task<Void> then(Task<Void> paramAnonymous3Task)
                throws Exception
              {
                OfflineStore.29.1.this.val$db.endTransactionAsync();
                OfflineStore.29.1.this.val$db.closeAsync();
                return paramAnonymous3Task;
              }
            });
          }
        });
      }
    });
  }
  
  <T extends ParseObject> Task<T> fetchLocallyAsync(final T paramT)
  {
    runWithManagedConnection(new SQLiteDatabaseCallable()
    {
      public Task<T> call(ParseSQLiteDatabase paramAnonymousParseSQLiteDatabase)
      {
        return OfflineStore.this.fetchLocallyAsync(paramT, paramAnonymousParseSQLiteDatabase);
      }
    });
  }
  
  <T extends ParseObject> Task<T> fetchLocallyAsync(final T paramT, ParseSQLiteDatabase arg2)
  {
    final Task.TaskCompletionSource localTaskCompletionSource = Task.create();
    for (;;)
    {
      Task localTask;
      String str1;
      String str2;
      synchronized (this.lock)
      {
        if (this.fetchedObjects.containsKey(paramT))
        {
          paramT = (Task)this.fetchedObjects.get(paramT);
          return paramT;
        }
        this.fetchedObjects.put(paramT, localTaskCompletionSource.getTask());
        localTask = (Task)this.objectToUuidMap.get(paramT);
        str1 = paramT.getClassName();
        str2 = paramT.getObjectId();
        ??? = Task.forResult(null);
        if (str2 != null) {
          break label178;
        }
        if (localTask == null) {
          ((Task)???).onSuccessTask(new Continuation()
          {
            public Task<Void> then(final Task<String> paramAnonymousTask)
              throws Exception
            {
              paramAnonymousTask = (String)paramAnonymousTask.getResult();
              if (paramAnonymousTask == null) {
                return Task.forError(new ParseException(120, "Attempted to fetch an object offline which was never saved to the offline cache."));
              }
              try
              {
                paramAnonymousTask = new JSONObject(paramAnonymousTask);
                final HashMap localHashMap = new HashMap();
                new ParseTraverser()
                {
                  protected boolean visit(Object paramAnonymous2Object)
                  {
                    if (((paramAnonymous2Object instanceof JSONObject)) && (((JSONObject)paramAnonymous2Object).optString("__type").equals("OfflineObject")))
                    {
                      paramAnonymous2Object = ((JSONObject)paramAnonymous2Object).optString("uuid");
                      localHashMap.put(paramAnonymous2Object, OfflineStore.this.getPointerAsync((String)paramAnonymous2Object, OfflineStore.11.this.val$db));
                    }
                    return true;
                  }
                }.setTraverseParseObjects(false).setYieldRoot(false).traverse(paramAnonymousTask);
                Task.whenAll(localHashMap.values()).onSuccess(new Continuation()
                {
                  public Void then(Task<Void> paramAnonymous2Task)
                    throws Exception
                  {
                    OfflineStore.11.this.val$object.mergeREST(OfflineStore.11.this.val$object.getState(), paramAnonymousTask, new OfflineStore.OfflineDecoder(OfflineStore.this, localHashMap, null));
                    return null;
                  }
                });
              }
              catch (JSONException paramAnonymousTask) {}
              return Task.forError(paramAnonymousTask);
            }
          }).continueWithTask(new Continuation()
          {
            public Task<T> then(Task<Void> paramAnonymousTask)
              throws Exception
            {
              if (paramAnonymousTask.isCancelled()) {
                localTaskCompletionSource.setCancelled();
              }
              for (;;)
              {
                return localTaskCompletionSource.getTask();
                if (paramAnonymousTask.isFaulted()) {
                  localTaskCompletionSource.setError(paramAnonymousTask.getError());
                } else {
                  localTaskCompletionSource.setResult(paramT);
                }
              }
            }
          });
        }
      }
      ??? = new Capture();
      ??? = localTask.onSuccessTask(new Continuation()
      {
        public Task<Cursor> then(Task<String> paramAnonymousTask)
          throws Exception
        {
          localObject.set(paramAnonymousTask.getResult());
          paramAnonymousTask = (String)localObject.get();
          return paramParseSQLiteDatabase.queryAsync("ParseObjects", this.val$select, "uuid = ?", new String[] { paramAnonymousTask });
        }
      }).onSuccess(new Continuation()
      {
        public String then(Task<Cursor> paramAnonymousTask)
          throws Exception
        {
          paramAnonymousTask = (Cursor)paramAnonymousTask.getResult();
          paramAnonymousTask.moveToFirst();
          if (paramAnonymousTask.isAfterLast())
          {
            paramAnonymousTask.close();
            throw new IllegalStateException("Attempted to find non-existent uuid " + (String)localObject.get());
          }
          String str = paramAnonymousTask.getString(0);
          paramAnonymousTask.close();
          return str;
        }
      });
      continue;
      label178:
      if (localTask != null)
      {
        localTaskCompletionSource.setError(new IllegalStateException("This object must have already been fetched from the local datastore, but isn't marked as fetched."));
        synchronized (this.lock)
        {
          this.fetchedObjects.remove(paramT);
          return localTaskCompletionSource.getTask();
        }
      }
      ??? = String.format("%s = ? AND %s = ?", new Object[] { "className", "objectId" });
      ??? = ???.queryAsync("ParseObjects", new String[] { "json", "uuid" }, (String)???, new String[] { str1, str2 }).onSuccess(new Continuation()
      {
        public String then(Task<Cursor> paramAnonymousTask)
          throws Exception
        {
          ??? = (Cursor)paramAnonymousTask.getResult();
          ((Cursor)???).moveToFirst();
          if (((Cursor)???).isAfterLast())
          {
            ((Cursor)???).close();
            throw new ParseException(120, "This object is not available in the offline cache.");
          }
          paramAnonymousTask = ((Cursor)???).getString(0);
          String str = ((Cursor)???).getString(1);
          ((Cursor)???).close();
          synchronized (OfflineStore.this.lock)
          {
            OfflineStore.this.objectToUuidMap.put(paramT, Task.forResult(str));
            OfflineStore.this.uuidToObjectMap.put(str, paramT);
            return paramAnonymousTask;
          }
        }
      });
    }
  }
  
  <T extends ParseObject> Task<List<T>> findAsync(ParseQuery.State<T> paramState, ParseUser paramParseUser, ParsePin paramParsePin, ParseSQLiteDatabase paramParseSQLiteDatabase)
  {
    return findAsync(paramState, paramParseUser, paramParsePin, false, paramParseSQLiteDatabase);
  }
  
  <T extends ParseObject> Task<List<T>> findFromPinAsync(final String paramString, final ParseQuery.State<T> paramState, final ParseUser paramParseUser)
  {
    runWithManagedConnection(new SQLiteDatabaseCallable()
    {
      public Task<List<T>> call(ParseSQLiteDatabase paramAnonymousParseSQLiteDatabase)
      {
        return OfflineStore.this.findFromPinAsync(paramString, paramState, paramParseUser, paramAnonymousParseSQLiteDatabase);
      }
    });
  }
  
  ParseObject getObject(String arg1, String paramString2)
  {
    if (paramString2 == null) {
      throw new IllegalStateException("objectId cannot be null.");
    }
    paramString2 = Pair.create(???, paramString2);
    synchronized (this.lock)
    {
      paramString2 = (ParseObject)this.classNameAndObjectIdToObjectMap.get(paramString2);
      return paramString2;
    }
  }
  
  <T extends ParseObject> Task<Void> pinAllObjectsAsync(final String paramString, final List<T> paramList, final boolean paramBoolean)
  {
    runWithManagedTransaction(new SQLiteDatabaseCallable()
    {
      public Task<Void> call(ParseSQLiteDatabase paramAnonymousParseSQLiteDatabase)
      {
        return OfflineStore.this.pinAllObjectsAsync(paramString, paramList, paramBoolean, paramAnonymousParseSQLiteDatabase);
      }
    });
  }
  
  void registerNewObject(ParseObject paramParseObject)
  {
    synchronized (this.lock)
    {
      Object localObject2 = paramParseObject.getObjectId();
      if (localObject2 != null)
      {
        localObject2 = Pair.create(paramParseObject.getClassName(), localObject2);
        this.classNameAndObjectIdToObjectMap.put(localObject2, paramParseObject);
      }
      return;
    }
  }
  
  void simulateReboot()
  {
    synchronized (this.lock)
    {
      this.uuidToObjectMap.clear();
      this.objectToUuidMap.clear();
      this.classNameAndObjectIdToObjectMap.clear();
      this.fetchedObjects.clear();
      return;
    }
  }
  
  Task<Void> unpinAllObjectsAsync(final String paramString)
  {
    runWithManagedTransaction(new SQLiteDatabaseCallable()
    {
      public Task<Void> call(ParseSQLiteDatabase paramAnonymousParseSQLiteDatabase)
      {
        return OfflineStore.this.unpinAllObjectsAsync(paramString, paramAnonymousParseSQLiteDatabase);
      }
    });
  }
  
  <T extends ParseObject> Task<Void> unpinAllObjectsAsync(final String paramString, final List<T> paramList)
  {
    runWithManagedTransaction(new SQLiteDatabaseCallable()
    {
      public Task<Void> call(ParseSQLiteDatabase paramAnonymousParseSQLiteDatabase)
      {
        return OfflineStore.this.unpinAllObjectsAsync(paramString, paramList, paramAnonymousParseSQLiteDatabase);
      }
    });
  }
  
  void unregisterObject(ParseObject paramParseObject)
  {
    synchronized (this.lock)
    {
      String str = paramParseObject.getObjectId();
      if (str != null) {
        this.classNameAndObjectIdToObjectMap.remove(Pair.create(paramParseObject.getClassName(), str));
      }
      return;
    }
  }
  
  Task<Void> updateDataForObjectAsync(final ParseObject paramParseObject)
  {
    synchronized (this.lock)
    {
      Task localTask = (Task)this.fetchedObjects.get(paramParseObject);
      if (localTask == null)
      {
        paramParseObject = Task.forError(new IllegalStateException("An object cannot be updated if it wasn't fetched."));
        return paramParseObject;
      }
      localTask.continueWithTask(new Continuation()
      {
        public Task<Void> then(Task<ParseObject> paramAnonymousTask)
          throws Exception
        {
          if (paramAnonymousTask.isFaulted())
          {
            if (((paramAnonymousTask.getError() instanceof ParseException)) && (((ParseException)paramAnonymousTask.getError()).getCode() == 120)) {
              return Task.forResult(null);
            }
            return paramAnonymousTask.makeVoid();
          }
          OfflineStore.this.helper.getWritableDatabaseAsync().continueWithTask(new Continuation()
          {
            public Task<Void> then(final Task<ParseSQLiteDatabase> paramAnonymous2Task)
              throws Exception
            {
              paramAnonymous2Task = (ParseSQLiteDatabase)paramAnonymous2Task.getResult();
              paramAnonymous2Task.beginTransactionAsync().onSuccessTask(new Continuation()
              {
                public Task<Void> then(Task<Void> paramAnonymous3Task)
                  throws Exception
                {
                  OfflineStore.this.updateDataForObjectAsync(OfflineStore.26.this.val$object, paramAnonymous2Task).onSuccessTask(new Continuation()
                  {
                    public Task<Void> then(Task<Void> paramAnonymous4Task)
                      throws Exception
                    {
                      return OfflineStore.26.1.1.this.val$db.setTransactionSuccessfulAsync();
                    }
                  }).continueWithTask(new Continuation()
                  {
                    public Task<Void> then(Task<Void> paramAnonymous4Task)
                      throws Exception
                    {
                      OfflineStore.26.1.1.this.val$db.endTransactionAsync();
                      OfflineStore.26.1.1.this.val$db.closeAsync();
                      return paramAnonymous4Task;
                    }
                  });
                }
              });
            }
          });
        }
      });
    }
  }
  
  void updateObjectId(ParseObject paramParseObject, String arg2, String paramString2)
  {
    if (??? != null)
    {
      if (???.equals(paramString2)) {
        return;
      }
      throw new RuntimeException("objectIds cannot be changed in offline mode.");
    }
    paramString2 = Pair.create(paramParseObject.getClassName(), paramString2);
    synchronized (this.lock)
    {
      ParseObject localParseObject = (ParseObject)this.classNameAndObjectIdToObjectMap.get(paramString2);
      if ((localParseObject != null) && (localParseObject != paramParseObject)) {
        throw new RuntimeException("Attempted to change an objectId to one that's already known to the Offline Store.");
      }
    }
    this.classNameAndObjectIdToObjectMap.put(paramString2, paramParseObject);
  }
  
  private class OfflineDecoder
    extends ParseDecoder
  {
    private Map<String, Task<ParseObject>> offlineObjects;
    
    private OfflineDecoder()
    {
      Map localMap;
      this.offlineObjects = localMap;
    }
    
    public Object decode(Object paramObject)
    {
      if (((paramObject instanceof JSONObject)) && (((JSONObject)paramObject).optString("__type").equals("OfflineObject")))
      {
        paramObject = ((JSONObject)paramObject).optString("uuid");
        return ((Task)this.offlineObjects.get(paramObject)).getResult();
      }
      return super.decode(paramObject);
    }
  }
  
  private class OfflineEncoder
    extends ParseEncoder
  {
    private ParseSQLiteDatabase db;
    private ArrayList<Task<Void>> tasks = new ArrayList();
    private final Object tasksLock = new Object();
    
    public OfflineEncoder(ParseSQLiteDatabase paramParseSQLiteDatabase)
    {
      this.db = paramParseSQLiteDatabase;
    }
    
    /* Error */
    public JSONObject encodeRelatedObject(ParseObject paramParseObject)
    {
      // Byte code:
      //   0: aload_1
      //   1: invokevirtual 54	com/parse/ParseObject:getObjectId	()Ljava/lang/String;
      //   4: ifnull +44 -> 48
      //   7: new 56	org/json/JSONObject
      //   10: dup
      //   11: invokespecial 57	org/json/JSONObject:<init>	()V
      //   14: astore_2
      //   15: aload_2
      //   16: ldc 59
      //   18: ldc 61
      //   20: invokevirtual 65	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      //   23: pop
      //   24: aload_2
      //   25: ldc 67
      //   27: aload_1
      //   28: invokevirtual 54	com/parse/ParseObject:getObjectId	()Ljava/lang/String;
      //   31: invokevirtual 65	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      //   34: pop
      //   35: aload_2
      //   36: ldc 69
      //   38: aload_1
      //   39: invokevirtual 72	com/parse/ParseObject:getClassName	()Ljava/lang/String;
      //   42: invokevirtual 65	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      //   45: pop
      //   46: aload_2
      //   47: areturn
      //   48: new 56	org/json/JSONObject
      //   51: dup
      //   52: invokespecial 57	org/json/JSONObject:<init>	()V
      //   55: astore_3
      //   56: aload_3
      //   57: ldc 59
      //   59: ldc 74
      //   61: invokevirtual 65	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      //   64: pop
      //   65: aload_0
      //   66: getfield 37	com/parse/OfflineStore$OfflineEncoder:tasksLock	Ljava/lang/Object;
      //   69: astore_2
      //   70: aload_2
      //   71: monitorenter
      //   72: aload_0
      //   73: getfield 32	com/parse/OfflineStore$OfflineEncoder:tasks	Ljava/util/ArrayList;
      //   76: aload_0
      //   77: getfield 24	com/parse/OfflineStore$OfflineEncoder:this$0	Lcom/parse/OfflineStore;
      //   80: aload_1
      //   81: aload_0
      //   82: getfield 39	com/parse/OfflineStore$OfflineEncoder:db	Lcom/parse/ParseSQLiteDatabase;
      //   85: invokestatic 78	com/parse/OfflineStore:access$200	(Lcom/parse/OfflineStore;Lcom/parse/ParseObject;Lcom/parse/ParseSQLiteDatabase;)Lbolts/Task;
      //   88: new 11	com/parse/OfflineStore$OfflineEncoder$2
      //   91: dup
      //   92: aload_0
      //   93: aload_3
      //   94: invokespecial 81	com/parse/OfflineStore$OfflineEncoder$2:<init>	(Lcom/parse/OfflineStore$OfflineEncoder;Lorg/json/JSONObject;)V
      //   97: invokevirtual 87	bolts/Task:onSuccess	(Lbolts/Continuation;)Lbolts/Task;
      //   100: invokevirtual 91	java/util/ArrayList:add	(Ljava/lang/Object;)Z
      //   103: pop
      //   104: aload_2
      //   105: monitorexit
      //   106: aload_3
      //   107: areturn
      //   108: astore_1
      //   109: aload_2
      //   110: monitorexit
      //   111: aload_1
      //   112: athrow
      //   113: astore_1
      //   114: new 93	java/lang/RuntimeException
      //   117: dup
      //   118: aload_1
      //   119: invokespecial 96	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
      //   122: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	123	0	this	OfflineEncoder
      //   0	123	1	paramParseObject	ParseObject
      //   55	52	3	localJSONObject	JSONObject
      // Exception table:
      //   from	to	target	type
      //   72	106	108	finally
      //   109	111	108	finally
      //   0	46	113	org/json/JSONException
      //   48	72	113	org/json/JSONException
      //   111	113	113	org/json/JSONException
    }
    
    public Task<Void> whenFinished()
    {
      Task.whenAll(this.tasks).continueWithTask(new Continuation()
      {
        public Task<Void> then(Task<Void> arg1)
          throws Exception
        {
          synchronized (OfflineStore.OfflineEncoder.this.tasksLock)
          {
            Object localObject1 = OfflineStore.OfflineEncoder.this.tasks.iterator();
            while (((Iterator)localObject1).hasNext())
            {
              Task localTask = (Task)((Iterator)localObject1).next();
              if ((localTask.isFaulted()) || (localTask.isCancelled())) {
                return localTask;
              }
            }
            OfflineStore.OfflineEncoder.this.tasks.clear();
            localObject1 = Task.forResult((Void)null);
            return (Task<Void>)localObject1;
          }
        }
      });
    }
  }
  
  private static abstract interface SQLiteDatabaseCallable<T>
  {
    public abstract T call(ParseSQLiteDatabase paramParseSQLiteDatabase);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/OfflineStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */