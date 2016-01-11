package com.parse;

import bolts.Continuation;
import bolts.Task;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class OfflineQueryLogic
{
  private final OfflineStore store;
  
  OfflineQueryLogic(OfflineStore paramOfflineStore)
  {
    this.store = paramOfflineStore;
  }
  
  private static boolean compare(Object paramObject1, Object paramObject2, Decider paramDecider)
  {
    if ((paramObject2 instanceof List)) {
      return compareList(paramObject1, (List)paramObject2, paramDecider);
    }
    if ((paramObject2 instanceof JSONArray)) {
      return compareArray(paramObject1, (JSONArray)paramObject2, paramDecider);
    }
    return paramDecider.decide(paramObject1, paramObject2);
  }
  
  private static boolean compareArray(Object paramObject, JSONArray paramJSONArray, Decider paramDecider)
  {
    int i = 0;
    while (i < paramJSONArray.length())
    {
      try
      {
        boolean bool = paramDecider.decide(paramObject, paramJSONArray.get(i));
        if (bool) {
          return true;
        }
      }
      catch (JSONException paramObject)
      {
        throw new RuntimeException((Throwable)paramObject);
      }
      i += 1;
    }
    return false;
  }
  
  private static boolean compareList(Object paramObject, List<?> paramList, Decider paramDecider)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      if (paramDecider.decide(paramObject, paramList.next())) {
        return true;
      }
    }
    return false;
  }
  
  private static int compareTo(Object paramObject1, Object paramObject2)
  {
    int i;
    int j;
    if ((paramObject1 == JSONObject.NULL) || (paramObject1 == null))
    {
      i = 1;
      if ((paramObject2 != JSONObject.NULL) && (paramObject2 != null)) {
        break label45;
      }
      j = 1;
    }
    for (;;)
    {
      if ((i != 0) || (j != 0))
      {
        if (i == 0)
        {
          return 1;
          i = 0;
          break;
          label45:
          j = 0;
          continue;
        }
        if (j == 0) {
          return -1;
        }
        return 0;
      }
    }
    if (((paramObject1 instanceof Date)) && ((paramObject2 instanceof Date))) {
      return ((Date)paramObject1).compareTo((Date)paramObject2);
    }
    if (((paramObject1 instanceof String)) && ((paramObject2 instanceof String))) {
      return ((String)paramObject1).compareTo((String)paramObject2);
    }
    if (((paramObject1 instanceof Number)) && ((paramObject2 instanceof Number))) {
      return Numbers.compare((Number)paramObject1, (Number)paramObject2);
    }
    throw new IllegalArgumentException(String.format("Cannot compare %s against %s", new Object[] { paramObject1, paramObject2 }));
  }
  
  private <T extends ParseObject> ConstraintMatcher<T> createDontSelectMatcher(ParseUser paramParseUser, Object paramObject, String paramString)
  {
    new ConstraintMatcher(paramParseUser, createSelectMatcher(paramParseUser, paramObject, paramString))
    {
      public Task<Boolean> matchesAsync(T paramAnonymousT, ParseSQLiteDatabase paramAnonymousParseSQLiteDatabase)
      {
        this.val$selectMatcher.matchesAsync(paramAnonymousT, paramAnonymousParseSQLiteDatabase).onSuccess(new Continuation()
        {
          public Boolean then(Task<Boolean> paramAnonymous2Task)
            throws Exception
          {
            if (!((Boolean)paramAnonymous2Task.getResult()).booleanValue()) {}
            for (boolean bool = true;; bool = false) {
              return Boolean.valueOf(bool);
            }
          }
        });
      }
    };
  }
  
  private <T extends ParseObject> ConstraintMatcher<T> createInQueryMatcher(ParseUser paramParseUser, Object paramObject, final String paramString)
  {
    new SubQueryMatcher(paramParseUser, ((ParseQuery.State.Builder)paramObject).build(), paramString)
    {
      protected boolean matches(T paramAnonymousT, List<T> paramAnonymousList)
        throws ParseException
      {
        return OfflineQueryLogic.matchesInConstraint(paramAnonymousList, OfflineQueryLogic.access$200(paramAnonymousT, paramString));
      }
    };
  }
  
  private <T extends ParseObject> ConstraintMatcher<T> createMatcher(ParseUser paramParseUser, ParseQuery.QueryConstraints paramQueryConstraints)
  {
    final ArrayList localArrayList = new ArrayList();
    Iterator localIterator1 = paramQueryConstraints.keySet().iterator();
    while (localIterator1.hasNext())
    {
      final String str1 = (String)localIterator1.next();
      final Object localObject = paramQueryConstraints.get(str1);
      if (str1.equals("$or"))
      {
        localArrayList.add(createOrMatcher(paramParseUser, (ArrayList)localObject));
      }
      else if ((localObject instanceof ParseQuery.KeyConstraints))
      {
        localObject = (ParseQuery.KeyConstraints)localObject;
        Iterator localIterator2 = ((ParseQuery.KeyConstraints)localObject).keySet().iterator();
        while (localIterator2.hasNext())
        {
          String str2 = (String)localIterator2.next();
          localArrayList.add(createMatcher(paramParseUser, str2, ((ParseQuery.KeyConstraints)localObject).get(str2), str1, (ParseQuery.KeyConstraints)localObject));
        }
      }
      else if ((localObject instanceof ParseQuery.RelationConstraint))
      {
        localArrayList.add(new ConstraintMatcher(paramParseUser, (ParseQuery.RelationConstraint)localObject)
        {
          public Task<Boolean> matchesAsync(T paramAnonymousT, ParseSQLiteDatabase paramAnonymousParseSQLiteDatabase)
          {
            return Task.forResult(Boolean.valueOf(localObject.getRelation().hasKnownObject(paramAnonymousT)));
          }
        });
      }
      else
      {
        localArrayList.add(new ConstraintMatcher(paramParseUser, str1)
        {
          public Task<Boolean> matchesAsync(T paramAnonymousT, ParseSQLiteDatabase paramAnonymousParseSQLiteDatabase)
          {
            try
            {
              paramAnonymousT = OfflineQueryLogic.getValue(paramAnonymousT, str1);
              return Task.forResult(Boolean.valueOf(OfflineQueryLogic.matchesEqualConstraint(localObject, paramAnonymousT)));
            }
            catch (ParseException paramAnonymousT) {}
            return Task.forError(paramAnonymousT);
          }
        });
      }
    }
    new ConstraintMatcher(paramParseUser, localArrayList)
    {
      public Task<Boolean> matchesAsync(final T paramAnonymousT, final ParseSQLiteDatabase paramAnonymousParseSQLiteDatabase)
      {
        Task localTask = Task.forResult(Boolean.valueOf(true));
        Iterator localIterator = localArrayList.iterator();
        while (localIterator.hasNext()) {
          localTask = localTask.onSuccessTask(new Continuation()
          {
            public Task<Boolean> then(Task<Boolean> paramAnonymous2Task)
              throws Exception
            {
              if (!((Boolean)paramAnonymous2Task.getResult()).booleanValue()) {
                return paramAnonymous2Task;
              }
              return this.val$matcher.matchesAsync(paramAnonymousT, paramAnonymousParseSQLiteDatabase);
            }
          });
        }
        return localTask;
      }
    };
  }
  
  private <T extends ParseObject> ConstraintMatcher<T> createMatcher(ParseUser paramParseUser, final String paramString1, final Object paramObject, final String paramString2, final ParseQuery.KeyConstraints paramKeyConstraints)
  {
    int i = -1;
    switch (paramString1.hashCode())
    {
    }
    for (;;)
    {
      switch (i)
      {
      default: 
        new ConstraintMatcher(paramParseUser, paramString2)
        {
          public Task<Boolean> matchesAsync(T paramAnonymousT, ParseSQLiteDatabase paramAnonymousParseSQLiteDatabase)
          {
            try
            {
              paramAnonymousT = OfflineQueryLogic.getValue(paramAnonymousT, paramString2);
              paramAnonymousT = Task.forResult(Boolean.valueOf(OfflineQueryLogic.matchesStatelessConstraint(paramString1, paramObject, paramAnonymousT, paramKeyConstraints)));
              return paramAnonymousT;
            }
            catch (ParseException paramAnonymousT) {}
            return Task.forError(paramAnonymousT);
          }
        };
        if (paramString1.equals("$inQuery"))
        {
          i = 0;
          continue;
          if (paramString1.equals("$notInQuery"))
          {
            i = 1;
            continue;
            if (paramString1.equals("$select"))
            {
              i = 2;
              continue;
              if (paramString1.equals("$dontSelect")) {
                i = 3;
              }
            }
          }
        }
        break;
      }
    }
    return createInQueryMatcher(paramParseUser, paramObject, paramString2);
    return createNotInQueryMatcher(paramParseUser, paramObject, paramString2);
    return createSelectMatcher(paramParseUser, paramObject, paramString2);
    return createDontSelectMatcher(paramParseUser, paramObject, paramString2);
  }
  
  private <T extends ParseObject> ConstraintMatcher<T> createNotInQueryMatcher(ParseUser paramParseUser, Object paramObject, String paramString)
  {
    new ConstraintMatcher(paramParseUser, createInQueryMatcher(paramParseUser, paramObject, paramString))
    {
      public Task<Boolean> matchesAsync(T paramAnonymousT, ParseSQLiteDatabase paramAnonymousParseSQLiteDatabase)
      {
        this.val$inQueryMatcher.matchesAsync(paramAnonymousT, paramAnonymousParseSQLiteDatabase).onSuccess(new Continuation()
        {
          public Boolean then(Task<Boolean> paramAnonymous2Task)
            throws Exception
          {
            if (!((Boolean)paramAnonymous2Task.getResult()).booleanValue()) {}
            for (boolean bool = true;; bool = false) {
              return Boolean.valueOf(bool);
            }
          }
        });
      }
    };
  }
  
  private <T extends ParseObject> ConstraintMatcher<T> createOrMatcher(ParseUser paramParseUser, ArrayList<ParseQuery.QueryConstraints> paramArrayList)
  {
    final ArrayList localArrayList = new ArrayList();
    paramArrayList = paramArrayList.iterator();
    while (paramArrayList.hasNext()) {
      localArrayList.add(createMatcher(paramParseUser, (ParseQuery.QueryConstraints)paramArrayList.next()));
    }
    new ConstraintMatcher(paramParseUser, localArrayList)
    {
      public Task<Boolean> matchesAsync(final T paramAnonymousT, final ParseSQLiteDatabase paramAnonymousParseSQLiteDatabase)
      {
        Task localTask = Task.forResult(Boolean.valueOf(false));
        Iterator localIterator = localArrayList.iterator();
        while (localIterator.hasNext()) {
          localTask = localTask.onSuccessTask(new Continuation()
          {
            public Task<Boolean> then(Task<Boolean> paramAnonymous2Task)
              throws Exception
            {
              if (((Boolean)paramAnonymous2Task.getResult()).booleanValue()) {
                return paramAnonymous2Task;
              }
              return this.val$matcher.matchesAsync(paramAnonymousT, paramAnonymousParseSQLiteDatabase);
            }
          });
        }
        return localTask;
      }
    };
  }
  
  private <T extends ParseObject> ConstraintMatcher<T> createSelectMatcher(ParseUser paramParseUser, Object paramObject, final String paramString)
  {
    paramObject = (Map)paramObject;
    new SubQueryMatcher(paramParseUser, ((ParseQuery.State.Builder)((Map)paramObject).get("query")).build(), paramString)
    {
      protected boolean matches(T paramAnonymousT, List<T> paramAnonymousList)
        throws ParseException
      {
        paramAnonymousT = OfflineQueryLogic.getValue(paramAnonymousT, paramString);
        paramAnonymousList = paramAnonymousList.iterator();
        while (paramAnonymousList.hasNext()) {
          if (OfflineQueryLogic.matchesEqualConstraint(paramAnonymousT, OfflineQueryLogic.access$200((ParseObject)paramAnonymousList.next(), this.val$resultKey))) {
            return true;
          }
        }
        return false;
      }
    };
  }
  
  private Task<Void> fetchIncludeAsync(final Object paramObject, final String paramString, final ParseSQLiteDatabase paramParseSQLiteDatabase)
    throws ParseException
  {
    if (paramObject == null)
    {
      paramObject = Task.forResult(null);
      return (Task<Void>)paramObject;
    }
    final Object localObject;
    if ((paramObject instanceof Collection))
    {
      paramObject = (Collection)paramObject;
      localTask = Task.forResult(null);
      localObject = ((Collection)paramObject).iterator();
      for (;;)
      {
        paramObject = localTask;
        if (!((Iterator)localObject).hasNext()) {
          break;
        }
        localTask = localTask.onSuccessTask(new Continuation()
        {
          public Task<Void> then(Task<Void> paramAnonymousTask)
            throws Exception
          {
            return OfflineQueryLogic.this.fetchIncludeAsync(this.val$item, paramString, paramParseSQLiteDatabase);
          }
        });
      }
    }
    if ((paramObject instanceof JSONArray))
    {
      localObject = (JSONArray)paramObject;
      localTask = Task.forResult(null);
      final int i = 0;
      for (;;)
      {
        paramObject = localTask;
        if (i >= ((JSONArray)localObject).length()) {
          break;
        }
        localTask = localTask.onSuccessTask(new Continuation()
        {
          public Task<Void> then(Task<Void> paramAnonymousTask)
            throws Exception
          {
            return OfflineQueryLogic.this.fetchIncludeAsync(localObject.get(i), paramString, paramParseSQLiteDatabase);
          }
        });
        i += 1;
      }
    }
    if (paramString == null)
    {
      if (JSONObject.NULL.equals(paramObject)) {
        return Task.forResult(null);
      }
      if ((paramObject instanceof ParseObject))
      {
        paramObject = (ParseObject)paramObject;
        return this.store.fetchLocallyAsync((ParseObject)paramObject, paramParseSQLiteDatabase).makeVoid();
      }
      return Task.forError(new ParseException(121, "include is invalid for non-ParseObjects"));
    }
    paramString = paramString.split("\\.", 2);
    final Task localTask = paramString[0];
    if (paramString.length > 1) {}
    for (paramString = paramString[1];; paramString = null) {
      Task.forResult(null).continueWithTask(new Continuation()
      {
        public Task<Object> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          paramAnonymousTask = null;
          if ((paramObject instanceof ParseObject)) {
            paramAnonymousTask = OfflineQueryLogic.this.fetchIncludeAsync(paramObject, null, paramParseSQLiteDatabase).onSuccess(new Continuation()
            {
              public Object then(Task<Void> paramAnonymous2Task)
                throws Exception
              {
                return ((ParseObject)OfflineQueryLogic.20.this.val$container).get(OfflineQueryLogic.20.this.val$key);
              }
            });
          }
          do
          {
            return paramAnonymousTask;
            if ((paramObject instanceof Map)) {
              return Task.forResult(((Map)paramObject).get(localTask));
            }
            if ((paramObject instanceof JSONObject)) {
              return Task.forResult(((JSONObject)paramObject).opt(localTask));
            }
          } while (JSONObject.NULL.equals(paramObject));
          return Task.forError(new IllegalStateException("include is invalid"));
        }
      }).onSuccessTask(new Continuation()
      {
        public Task<Void> then(Task<Object> paramAnonymousTask)
          throws Exception
        {
          return OfflineQueryLogic.this.fetchIncludeAsync(paramAnonymousTask.getResult(), paramString, paramParseSQLiteDatabase);
        }
      });
    }
  }
  
  private static Object getValue(Object paramObject, String paramString)
    throws ParseException
  {
    return getValue(paramObject, paramString, 0);
  }
  
  private static Object getValue(Object paramObject, String paramString, int paramInt)
    throws ParseException
  {
    String[] arrayOfString = null;
    Object localObject;
    if (paramString.contains("."))
    {
      arrayOfString = paramString.split("\\.", 2);
      localObject = getValue(paramObject, arrayOfString[0], paramInt + 1);
      if ((localObject != null) && (localObject != JSONObject.NULL) && (!(localObject instanceof Map)) && (!(localObject instanceof JSONObject))) {
        if (paramInt > 0) {
          paramObject = null;
        }
      }
    }
    try
    {
      localObject = PointerEncoder.get().encode(localObject);
      paramObject = localObject;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    if ((paramObject instanceof JSONObject)) {
      localObject = getValue(paramObject, arrayOfString[1], paramInt + 1);
    }
    do
    {
      do
      {
        return localObject;
        throw new ParseException(102, String.format("Key %s is invalid.", new Object[] { paramString }));
        return getValue(localObject, arrayOfString[1], paramInt + 1);
        if ((paramObject instanceof ParseObject))
        {
          paramObject = (ParseObject)paramObject;
          if (!((ParseObject)paramObject).isDataAvailable()) {
            throw new ParseException(121, String.format("Bad key: %s", new Object[] { paramString }));
          }
          paramInt = -1;
          switch (paramString.hashCode())
          {
          }
          for (;;)
          {
            switch (paramInt)
            {
            default: 
              return ((ParseObject)paramObject).get(paramString);
              if (paramString.equals("objectId"))
              {
                paramInt = 0;
                continue;
                if (paramString.equals("createdAt"))
                {
                  paramInt = 1;
                  continue;
                  if (paramString.equals("_created_at"))
                  {
                    paramInt = 2;
                    continue;
                    if (paramString.equals("updatedAt"))
                    {
                      paramInt = 3;
                      continue;
                      if (paramString.equals("_updated_at")) {
                        paramInt = 4;
                      }
                    }
                  }
                }
              }
              break;
            }
          }
          return ((ParseObject)paramObject).getObjectId();
          return ((ParseObject)paramObject).getCreatedAt();
          return ((ParseObject)paramObject).getUpdatedAt();
        }
        if ((paramObject instanceof JSONObject)) {
          return ((JSONObject)paramObject).opt(paramString);
        }
        if ((paramObject instanceof Map)) {
          return ((Map)paramObject).get(paramString);
        }
        localObject = arrayOfString;
      } while (paramObject == JSONObject.NULL);
      localObject = arrayOfString;
    } while (paramObject == null);
    throw new ParseException(121, String.format("Bad key: %s", new Object[] { paramString }));
  }
  
  static <T extends ParseObject> boolean hasReadAccess(ParseUser paramParseUser, T paramT)
  {
    if (paramParseUser == paramT) {}
    do
    {
      return true;
      paramT = paramT.getACL();
    } while ((paramT == null) || (paramT.getPublicReadAccess()) || ((paramParseUser != null) && (paramT.getReadAccess(paramParseUser))));
    return false;
  }
  
  static <T extends ParseObject> boolean hasWriteAccess(ParseUser paramParseUser, T paramT)
  {
    if (paramParseUser == paramT) {}
    do
    {
      return true;
      paramT = paramT.getACL();
    } while ((paramT == null) || (paramT.getPublicWriteAccess()) || ((paramParseUser != null) && (paramT.getWriteAccess(paramParseUser))));
    return false;
  }
  
  private static boolean matchesAllConstraint(Object paramObject1, Object paramObject2)
  {
    if ((paramObject2 == null) || (paramObject2 == JSONObject.NULL)) {
      return false;
    }
    if (!(paramObject2 instanceof Collection)) {
      throw new IllegalArgumentException("Value type not supported for $all queries.");
    }
    if ((paramObject1 instanceof Collection))
    {
      paramObject1 = ((Collection)paramObject1).iterator();
      while (((Iterator)paramObject1).hasNext()) {
        if (!matchesEqualConstraint(((Iterator)paramObject1).next(), paramObject2)) {
          return false;
        }
      }
      return true;
    }
    throw new IllegalArgumentException("Constraint type not supported for $all queries.");
  }
  
  private static boolean matchesEqualConstraint(Object paramObject1, Object paramObject2)
  {
    if ((paramObject1 == null) || (paramObject2 == null)) {
      if (paramObject1 != paramObject2) {}
    }
    do
    {
      do
      {
        return true;
        return false;
        if ((!(paramObject1 instanceof Number)) || (!(paramObject2 instanceof Number))) {
          break;
        }
      } while (compareTo(paramObject1, paramObject2) == 0);
      return false;
      if ((!(paramObject1 instanceof ParseGeoPoint)) || (!(paramObject2 instanceof ParseGeoPoint))) {
        break;
      }
      paramObject1 = (ParseGeoPoint)paramObject1;
      paramObject2 = (ParseGeoPoint)paramObject2;
    } while ((((ParseGeoPoint)paramObject1).getLatitude() == ((ParseGeoPoint)paramObject2).getLatitude()) && (((ParseGeoPoint)paramObject1).getLongitude() == ((ParseGeoPoint)paramObject2).getLongitude()));
    return false;
    compare(paramObject1, paramObject2, new Decider()
    {
      public boolean decide(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        return paramAnonymousObject1.equals(paramAnonymousObject2);
      }
    });
  }
  
  private static boolean matchesExistsConstraint(Object paramObject1, Object paramObject2)
  {
    boolean bool = false;
    if ((paramObject1 != null) && (((Boolean)paramObject1).booleanValue())) {
      return (paramObject2 != null) && (paramObject2 != JSONObject.NULL);
    }
    if ((paramObject2 == null) || (paramObject2 == JSONObject.NULL)) {
      bool = true;
    }
    return bool;
  }
  
  private static boolean matchesGreaterThanConstraint(Object paramObject1, Object paramObject2)
  {
    compare(paramObject1, paramObject2, new Decider()
    {
      public boolean decide(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        if ((paramAnonymousObject2 == null) || (paramAnonymousObject2 == JSONObject.NULL)) {}
        while (OfflineQueryLogic.compareTo(paramAnonymousObject1, paramAnonymousObject2) >= 0) {
          return false;
        }
        return true;
      }
    });
  }
  
  private static boolean matchesGreaterThanOrEqualToConstraint(Object paramObject1, Object paramObject2)
  {
    compare(paramObject1, paramObject2, new Decider()
    {
      public boolean decide(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        if ((paramAnonymousObject2 == null) || (paramAnonymousObject2 == JSONObject.NULL)) {}
        while (OfflineQueryLogic.compareTo(paramAnonymousObject1, paramAnonymousObject2) > 0) {
          return false;
        }
        return true;
      }
    });
  }
  
  private static boolean matchesInConstraint(Object paramObject1, Object paramObject2)
  {
    if ((paramObject1 instanceof Collection))
    {
      paramObject1 = ((Collection)paramObject1).iterator();
      while (((Iterator)paramObject1).hasNext()) {
        if (matchesEqualConstraint(((Iterator)paramObject1).next(), paramObject2)) {
          return true;
        }
      }
      return false;
    }
    throw new IllegalArgumentException("Constraint type not supported for $in queries.");
  }
  
  private static boolean matchesLessThanConstraint(Object paramObject1, Object paramObject2)
  {
    compare(paramObject1, paramObject2, new Decider()
    {
      public boolean decide(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        if ((paramAnonymousObject2 == null) || (paramAnonymousObject2 == JSONObject.NULL)) {}
        while (OfflineQueryLogic.compareTo(paramAnonymousObject1, paramAnonymousObject2) <= 0) {
          return false;
        }
        return true;
      }
    });
  }
  
  private static boolean matchesLessThanOrEqualToConstraint(Object paramObject1, Object paramObject2)
  {
    compare(paramObject1, paramObject2, new Decider()
    {
      public boolean decide(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        if ((paramAnonymousObject2 == null) || (paramAnonymousObject2 == JSONObject.NULL)) {}
        while (OfflineQueryLogic.compareTo(paramAnonymousObject1, paramAnonymousObject2) < 0) {
          return false;
        }
        return true;
      }
    });
  }
  
  private static boolean matchesNearSphereConstraint(Object paramObject1, Object paramObject2, Double paramDouble)
  {
    boolean bool2 = true;
    boolean bool1;
    if ((paramObject2 == null) || (paramObject2 == JSONObject.NULL)) {
      bool1 = false;
    }
    do
    {
      do
      {
        return bool1;
        bool1 = bool2;
      } while (paramDouble == null);
      bool1 = bool2;
    } while (((ParseGeoPoint)paramObject1).distanceInRadiansTo((ParseGeoPoint)paramObject2) <= paramDouble.doubleValue());
    return false;
  }
  
  private static boolean matchesNotEqualConstraint(Object paramObject1, Object paramObject2)
  {
    return !matchesEqualConstraint(paramObject1, paramObject2);
  }
  
  private static boolean matchesNotInConstraint(Object paramObject1, Object paramObject2)
  {
    return !matchesInConstraint(paramObject1, paramObject2);
  }
  
  private static boolean matchesRegexConstraint(Object paramObject1, Object paramObject2, String paramString)
    throws ParseException
  {
    if ((paramObject2 == null) || (paramObject2 == JSONObject.NULL)) {
      return false;
    }
    String str = paramString;
    if (paramString == null) {
      str = "";
    }
    if (!str.matches("^[imxs]*$")) {
      throw new ParseException(102, String.format("Invalid regex options: %s", new Object[] { str }));
    }
    int j = 0;
    if (str.contains("i")) {
      j = 0x0 | 0x2;
    }
    int i = j;
    if (str.contains("m")) {
      i = j | 0x8;
    }
    j = i;
    if (str.contains("x")) {
      j = i | 0x4;
    }
    i = j;
    if (str.contains("s")) {
      i = j | 0x20;
    }
    return Pattern.compile((String)paramObject1, i).matcher((String)paramObject2).find();
  }
  
  private static boolean matchesStatelessConstraint(String paramString, Object paramObject1, Object paramObject2, ParseQuery.KeyConstraints paramKeyConstraints)
    throws ParseException
  {
    boolean bool = true;
    int i = -1;
    switch (paramString.hashCode())
    {
    }
    for (;;)
    {
      switch (i)
      {
      default: 
        throw new UnsupportedOperationException(String.format("The offline store does not yet support the %s operator.", new Object[] { paramString }));
        if (paramString.equals("$ne"))
        {
          i = 0;
          continue;
          if (paramString.equals("$lt"))
          {
            i = 1;
            continue;
            if (paramString.equals("$lte"))
            {
              i = 2;
              continue;
              if (paramString.equals("$gt"))
              {
                i = 3;
                continue;
                if (paramString.equals("$gte"))
                {
                  i = 4;
                  continue;
                  if (paramString.equals("$in"))
                  {
                    i = 5;
                    continue;
                    if (paramString.equals("$nin"))
                    {
                      i = 6;
                      continue;
                      if (paramString.equals("$all"))
                      {
                        i = 7;
                        continue;
                        if (paramString.equals("$regex"))
                        {
                          i = 8;
                          continue;
                          if (paramString.equals("$options"))
                          {
                            i = 9;
                            continue;
                            if (paramString.equals("$exists"))
                            {
                              i = 10;
                              continue;
                              if (paramString.equals("$nearSphere"))
                              {
                                i = 11;
                                continue;
                                if (paramString.equals("$maxDistance"))
                                {
                                  i = 12;
                                  continue;
                                  if (paramString.equals("$within")) {
                                    i = 13;
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
        break;
      }
    }
    bool = matchesNotEqualConstraint(paramObject1, paramObject2);
    return bool;
    return matchesLessThanConstraint(paramObject1, paramObject2);
    return matchesLessThanOrEqualToConstraint(paramObject1, paramObject2);
    return matchesGreaterThanConstraint(paramObject1, paramObject2);
    return matchesGreaterThanOrEqualToConstraint(paramObject1, paramObject2);
    return matchesInConstraint(paramObject1, paramObject2);
    return matchesNotInConstraint(paramObject1, paramObject2);
    return matchesAllConstraint(paramObject1, paramObject2);
    return matchesRegexConstraint(paramObject1, paramObject2, (String)paramKeyConstraints.get("$options"));
    return matchesExistsConstraint(paramObject1, paramObject2);
    return matchesNearSphereConstraint(paramObject1, paramObject2, (Double)paramKeyConstraints.get("$maxDistance"));
    return matchesWithinConstraint(paramObject1, paramObject2);
  }
  
  private static boolean matchesWithinConstraint(Object paramObject1, Object paramObject2)
    throws ParseException
  {
    boolean bool = true;
    if ((paramObject2 == null) || (paramObject2 == JSONObject.NULL)) {
      bool = false;
    }
    Object localObject;
    do
    {
      return bool;
      localObject = (ArrayList)((HashMap)paramObject1).get("$box");
      paramObject1 = (ParseGeoPoint)((ArrayList)localObject).get(0);
      localObject = (ParseGeoPoint)((ArrayList)localObject).get(1);
      paramObject2 = (ParseGeoPoint)paramObject2;
      if (((ParseGeoPoint)localObject).getLongitude() < ((ParseGeoPoint)paramObject1).getLongitude()) {
        throw new ParseException(102, "whereWithinGeoBox queries cannot cross the International Date Line.");
      }
      if (((ParseGeoPoint)localObject).getLatitude() < ((ParseGeoPoint)paramObject1).getLatitude()) {
        throw new ParseException(102, "The southwest corner of a geo box must be south of the northeast corner.");
      }
      if (((ParseGeoPoint)localObject).getLongitude() - ((ParseGeoPoint)paramObject1).getLongitude() > 180.0D) {
        throw new ParseException(102, "Geo box queries larger than 180 degrees in longitude are not supported. Please check point order.");
      }
    } while ((((ParseGeoPoint)paramObject2).getLatitude() >= ((ParseGeoPoint)paramObject1).getLatitude()) && (((ParseGeoPoint)paramObject2).getLatitude() <= ((ParseGeoPoint)localObject).getLatitude()) && (((ParseGeoPoint)paramObject2).getLongitude() >= ((ParseGeoPoint)paramObject1).getLongitude()) && (((ParseGeoPoint)paramObject2).getLongitude() <= ((ParseGeoPoint)localObject).getLongitude()));
    return false;
  }
  
  static <T extends ParseObject> void sort(List<T> paramList, ParseQuery.State<T> paramState)
    throws ParseException
  {
    final List localList = paramState.order();
    Object localObject1 = paramState.order().iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (String)((Iterator)localObject1).next();
      if ((!((String)localObject2).matches("^-?[A-Za-z][A-Za-z0-9_]*$")) && (!"_created_at".equals(localObject2)) && (!"_updated_at".equals(localObject2))) {
        throw new ParseException(105, String.format("Invalid key name: \"%s\".", new Object[] { localObject2 }));
      }
    }
    localObject1 = null;
    final Object localObject2 = null;
    Iterator localIterator = paramState.constraints().keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Object localObject3 = paramState.constraints().get(str);
      if ((localObject3 instanceof ParseQuery.KeyConstraints))
      {
        localObject3 = (ParseQuery.KeyConstraints)localObject3;
        if (((ParseQuery.KeyConstraints)localObject3).containsKey("$nearSphere"))
        {
          localObject1 = str;
          localObject2 = (ParseGeoPoint)((ParseQuery.KeyConstraints)localObject3).get("$nearSphere");
        }
      }
    }
    if ((localList.size() == 0) && (localObject1 == null)) {
      return;
    }
    Collections.sort(paramList, new Comparator()
    {
      /* Error */
      public int compare(T paramAnonymousT1, T paramAnonymousT2)
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 22	com/parse/OfflineQueryLogic$16:val$nearSphereKey	Ljava/lang/String;
        //   4: ifnull +80 -> 84
        //   7: aload_1
        //   8: aload_0
        //   9: getfield 22	com/parse/OfflineQueryLogic$16:val$nearSphereKey	Ljava/lang/String;
        //   12: invokestatic 40	com/parse/OfflineQueryLogic:access$200	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
        //   15: checkcast 42	com/parse/ParseGeoPoint
        //   18: astore 10
        //   20: aload_2
        //   21: aload_0
        //   22: getfield 22	com/parse/OfflineQueryLogic$16:val$nearSphereKey	Ljava/lang/String;
        //   25: invokestatic 40	com/parse/OfflineQueryLogic:access$200	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
        //   28: checkcast 42	com/parse/ParseGeoPoint
        //   31: astore 11
        //   33: aload 10
        //   35: aload_0
        //   36: getfield 24	com/parse/OfflineQueryLogic$16:val$nearSphereValue	Lcom/parse/ParseGeoPoint;
        //   39: invokevirtual 46	com/parse/ParseGeoPoint:distanceInRadiansTo	(Lcom/parse/ParseGeoPoint;)D
        //   42: dstore_3
        //   43: aload 11
        //   45: aload_0
        //   46: getfield 24	com/parse/OfflineQueryLogic$16:val$nearSphereValue	Lcom/parse/ParseGeoPoint;
        //   49: invokevirtual 46	com/parse/ParseGeoPoint:distanceInRadiansTo	(Lcom/parse/ParseGeoPoint;)D
        //   52: dstore 5
        //   54: dload_3
        //   55: dload 5
        //   57: dcmpl
        //   58: ifeq +26 -> 84
        //   61: dload_3
        //   62: dload 5
        //   64: dsub
        //   65: dconst_0
        //   66: dcmpl
        //   67: ifle +15 -> 82
        //   70: iconst_1
        //   71: ireturn
        //   72: astore_1
        //   73: new 48	java/lang/RuntimeException
        //   76: dup
        //   77: aload_1
        //   78: invokespecial 51	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
        //   81: athrow
        //   82: iconst_m1
        //   83: ireturn
        //   84: aload_0
        //   85: getfield 26	com/parse/OfflineQueryLogic$16:val$keys	Ljava/util/List;
        //   88: invokeinterface 57 1 0
        //   93: astore 12
        //   95: aload 12
        //   97: invokeinterface 63 1 0
        //   102: ifeq +124 -> 226
        //   105: aload 12
        //   107: invokeinterface 67 1 0
        //   112: checkcast 69	java/lang/String
        //   115: astore 11
        //   117: iconst_0
        //   118: istore 7
        //   120: aload 11
        //   122: astore 10
        //   124: aload 11
        //   126: ldc 71
        //   128: invokevirtual 75	java/lang/String:startsWith	(Ljava/lang/String;)Z
        //   131: ifeq +14 -> 145
        //   134: iconst_1
        //   135: istore 7
        //   137: aload 11
        //   139: iconst_1
        //   140: invokevirtual 79	java/lang/String:substring	(I)Ljava/lang/String;
        //   143: astore 10
        //   145: aload_1
        //   146: aload 10
        //   148: invokestatic 40	com/parse/OfflineQueryLogic:access$200	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
        //   151: astore 11
        //   153: aload_2
        //   154: aload 10
        //   156: invokestatic 40	com/parse/OfflineQueryLogic:access$200	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
        //   159: astore 13
        //   161: aload 11
        //   163: aload 13
        //   165: invokestatic 83	com/parse/OfflineQueryLogic:access$000	(Ljava/lang/Object;Ljava/lang/Object;)I
        //   168: istore 8
        //   170: iload 8
        //   172: ifeq -77 -> 95
        //   175: iload 8
        //   177: istore 9
        //   179: iload 7
        //   181: ifeq +8 -> 189
        //   184: iload 8
        //   186: ineg
        //   187: istore 9
        //   189: iload 9
        //   191: ireturn
        //   192: astore_1
        //   193: new 48	java/lang/RuntimeException
        //   196: dup
        //   197: aload_1
        //   198: invokespecial 51	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
        //   201: athrow
        //   202: astore_1
        //   203: new 36	java/lang/IllegalArgumentException
        //   206: dup
        //   207: ldc 85
        //   209: iconst_1
        //   210: anewarray 5	java/lang/Object
        //   213: dup
        //   214: iconst_0
        //   215: aload 10
        //   217: aastore
        //   218: invokestatic 89	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   221: aload_1
        //   222: invokespecial 92	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
        //   225: athrow
        //   226: iconst_0
        //   227: ireturn
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	228	0	this	16
        //   0	228	1	paramAnonymousT1	T
        //   0	228	2	paramAnonymousT2	T
        //   42	20	3	d1	double
        //   52	11	5	d2	double
        //   118	62	7	i	int
        //   168	17	8	j	int
        //   177	13	9	k	int
        //   18	198	10	localObject1	Object
        //   31	131	11	localObject2	Object
        //   93	13	12	localIterator	Iterator
        //   159	5	13	localObject3	Object
        // Exception table:
        //   from	to	target	type
        //   7	33	72	com/parse/ParseException
        //   145	161	192	com/parse/ParseException
        //   161	170	202	java/lang/IllegalArgumentException
      }
    });
  }
  
  <T extends ParseObject> ConstraintMatcher<T> createMatcher(ParseQuery.State<T> paramState, ParseUser paramParseUser)
  {
    new ConstraintMatcher(paramParseUser, paramState.ignoreACLs())
    {
      public Task<Boolean> matchesAsync(T paramAnonymousT, ParseSQLiteDatabase paramAnonymousParseSQLiteDatabase)
      {
        if ((!this.val$ignoreACLs) && (!OfflineQueryLogic.hasReadAccess(this.user, paramAnonymousT))) {
          return Task.forResult(Boolean.valueOf(false));
        }
        return this.val$constraintMatcher.matchesAsync(paramAnonymousT, paramAnonymousParseSQLiteDatabase);
      }
    };
  }
  
  <T extends ParseObject> Task<Void> fetchIncludesAsync(final T paramT, ParseQuery.State<T> paramState, final ParseSQLiteDatabase paramParseSQLiteDatabase)
  {
    Object localObject = paramState.includes();
    paramState = Task.forResult(null);
    localObject = ((Set)localObject).iterator();
    while (((Iterator)localObject).hasNext()) {
      paramState = paramState.onSuccessTask(new Continuation()
      {
        public Task<Void> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          return OfflineQueryLogic.this.fetchIncludeAsync(paramT, this.val$include, paramParseSQLiteDatabase);
        }
      });
    }
    return paramState;
  }
  
  abstract class ConstraintMatcher<T extends ParseObject>
  {
    final ParseUser user;
    
    public ConstraintMatcher(ParseUser paramParseUser)
    {
      this.user = paramParseUser;
    }
    
    abstract Task<Boolean> matchesAsync(T paramT, ParseSQLiteDatabase paramParseSQLiteDatabase);
  }
  
  private static abstract interface Decider
  {
    public abstract boolean decide(Object paramObject1, Object paramObject2);
  }
  
  private abstract class SubQueryMatcher<T extends ParseObject>
    extends OfflineQueryLogic.ConstraintMatcher<T>
  {
    private final ParseQuery.State<T> subQuery;
    private Task<List<T>> subQueryResults = null;
    
    public SubQueryMatcher(ParseQuery.State<T> paramState)
    {
      super(paramState);
      ParseQuery.State localState;
      this.subQuery = localState;
    }
    
    protected abstract boolean matches(T paramT, List<T> paramList)
      throws ParseException;
    
    public Task<Boolean> matchesAsync(final T paramT, ParseSQLiteDatabase paramParseSQLiteDatabase)
    {
      if (this.subQueryResults == null) {
        this.subQueryResults = OfflineQueryLogic.this.store.findAsync(this.subQuery, this.user, null, paramParseSQLiteDatabase);
      }
      this.subQueryResults.onSuccess(new Continuation()
      {
        public Boolean then(Task<List<T>> paramAnonymousTask)
          throws ParseException
        {
          return Boolean.valueOf(OfflineQueryLogic.SubQueryMatcher.this.matches(paramT, (List)paramAnonymousTask.getResult()));
        }
      });
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/OfflineQueryLogic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */