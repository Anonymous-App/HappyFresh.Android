package com.mixpanel.android.viewcrawler;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.Pair;
import com.mixpanel.android.mpmetrics.ResourceIds;
import com.mixpanel.android.util.JSONUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class EditProtocol
{
  private static final String LOGTAG = "MixpanelAPI.EProtocol";
  private static final List<Pathfinder.PathElement> NEVER_MATCH_PATH = Collections.emptyList();
  private static final Class<?>[] NO_PARAMS = new Class[0];
  private final ImageStore mImageStore;
  private final ViewVisitor.OnLayoutErrorListener mLayoutErrorListener;
  private final ResourceIds mResourceIds;
  
  public EditProtocol(ResourceIds paramResourceIds, ImageStore paramImageStore, ViewVisitor.OnLayoutErrorListener paramOnLayoutErrorListener)
  {
    this.mResourceIds = paramResourceIds;
    this.mImageStore = paramImageStore;
    this.mLayoutErrorListener = paramOnLayoutErrorListener;
  }
  
  private Object convertArgument(Object paramObject, String paramString, List<String> paramList)
    throws EditProtocol.BadInstructionsException, EditProtocol.CantGetEditAssetsException
  {
    try
    {
      if ("java.lang.CharSequence".equals(paramString)) {
        return paramObject;
      }
      if ((!"boolean".equals(paramString)) && (!"java.lang.Boolean".equals(paramString)))
      {
        if (("int".equals(paramString)) || ("java.lang.Integer".equals(paramString))) {
          return Integer.valueOf(((Number)paramObject).intValue());
        }
        if (("float".equals(paramString)) || ("java.lang.Float".equals(paramString))) {
          return Float.valueOf(((Number)paramObject).floatValue());
        }
        if ("android.graphics.drawable.Drawable".equals(paramString)) {
          return readBitmapDrawable((JSONObject)paramObject, paramList);
        }
        if ("android.graphics.drawable.BitmapDrawable".equals(paramString)) {
          return readBitmapDrawable((JSONObject)paramObject, paramList);
        }
        if ("android.graphics.drawable.ColorDrawable".equals(paramString)) {
          return new ColorDrawable(((Number)paramObject).intValue());
        }
        throw new BadInstructionsException("Don't know how to interpret type " + paramString + " (arg was " + paramObject + ")");
      }
    }
    catch (ClassCastException paramList)
    {
      throw new BadInstructionsException("Couldn't interpret <" + paramObject + "> as " + paramString);
    }
    return paramObject;
  }
  
  private Drawable readBitmapDrawable(JSONObject paramJSONObject, List<String> paramList)
    throws EditProtocol.BadInstructionsException, EditProtocol.CantGetEditAssetsException
  {
    try
    {
      if (paramJSONObject.isNull("url")) {
        throw new BadInstructionsException("Can't construct a BitmapDrawable with a null url");
      }
    }
    catch (JSONException paramJSONObject)
    {
      throw new BadInstructionsException("Couldn't read drawable description", paramJSONObject);
    }
    String str = paramJSONObject.getString("url");
    boolean bool = paramJSONObject.isNull("dimensions");
    int i;
    int m;
    int k;
    int j;
    if (bool)
    {
      i = 0;
      m = 0;
      k = 0;
      j = 0;
    }
    for (int n = 0;; n = 1)
    {
      try
      {
        paramJSONObject = this.mImageStore.getImage(str);
        paramList.add(str);
        paramJSONObject = new BitmapDrawable(Resources.getSystem(), paramJSONObject);
        if (n == 0) {
          break;
        }
        paramJSONObject.setBounds(j, m, k, i);
        return paramJSONObject;
      }
      catch (ImageStore.CantGetImageException paramJSONObject)
      {
        throw new CantGetEditAssetsException(paramJSONObject.getMessage(), paramJSONObject.getCause());
      }
      paramJSONObject = paramJSONObject.getJSONObject("dimensions");
      j = paramJSONObject.getInt("left");
      k = paramJSONObject.getInt("right");
      m = paramJSONObject.getInt("top");
      i = paramJSONObject.getInt("bottom");
    }
    return paramJSONObject;
  }
  
  /* Error */
  private PropertyDescription readPropertyDescription(Class<?> paramClass, JSONObject paramJSONObject)
    throws EditProtocol.BadInstructionsException
  {
    // Byte code:
    //   0: aload_2
    //   1: ldc -21
    //   3: invokevirtual 172	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   6: astore 4
    //   8: aconst_null
    //   9: astore_3
    //   10: aload_2
    //   11: ldc -19
    //   13: invokevirtual 240	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   16: ifeq +51 -> 67
    //   19: aload_2
    //   20: ldc -19
    //   22: invokevirtual 206	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   25: astore 5
    //   27: aload 5
    //   29: ldc -14
    //   31: invokevirtual 172	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   34: astore_3
    //   35: aload 5
    //   37: ldc -12
    //   39: invokevirtual 206	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   42: ldc -10
    //   44: invokevirtual 172	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   47: invokestatic 250	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   50: astore 5
    //   52: new 252	com/mixpanel/android/viewcrawler/Caller
    //   55: dup
    //   56: aload_1
    //   57: aload_3
    //   58: getstatic 40	com/mixpanel/android/viewcrawler/EditProtocol:NO_PARAMS	[Ljava/lang/Class;
    //   61: aload 5
    //   63: invokespecial 255	com/mixpanel/android/viewcrawler/Caller:<init>	(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;Ljava/lang/Class;)V
    //   66: astore_3
    //   67: aload_2
    //   68: ldc_w 257
    //   71: invokevirtual 240	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   74: ifeq +31 -> 105
    //   77: aload_2
    //   78: ldc_w 257
    //   81: invokevirtual 206	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   84: ldc -14
    //   86: invokevirtual 172	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   89: astore_2
    //   90: new 259	com/mixpanel/android/viewcrawler/PropertyDescription
    //   93: dup
    //   94: aload 4
    //   96: aload_1
    //   97: aload_3
    //   98: aload_2
    //   99: invokespecial 262	com/mixpanel/android/viewcrawler/PropertyDescription:<init>	(Ljava/lang/String;Ljava/lang/Class;Lcom/mixpanel/android/viewcrawler/Caller;Ljava/lang/String;)V
    //   102: astore_1
    //   103: aload_1
    //   104: areturn
    //   105: aconst_null
    //   106: astore_2
    //   107: goto -17 -> 90
    //   110: astore_1
    //   111: new 8	com/mixpanel/android/viewcrawler/EditProtocol$BadInstructionsException
    //   114: dup
    //   115: ldc_w 264
    //   118: aload_1
    //   119: invokespecial 168	com/mixpanel/android/viewcrawler/EditProtocol$BadInstructionsException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   122: athrow
    //   123: astore_1
    //   124: new 8	com/mixpanel/android/viewcrawler/EditProtocol$BadInstructionsException
    //   127: dup
    //   128: ldc_w 266
    //   131: aload_1
    //   132: invokespecial 168	com/mixpanel/android/viewcrawler/EditProtocol$BadInstructionsException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   135: athrow
    //   136: astore_1
    //   137: new 8	com/mixpanel/android/viewcrawler/EditProtocol$BadInstructionsException
    //   140: dup
    //   141: ldc_w 268
    //   144: aload_1
    //   145: invokespecial 168	com/mixpanel/android/viewcrawler/EditProtocol$BadInstructionsException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   148: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	149	0	this	EditProtocol
    //   0	149	1	paramClass	Class<?>
    //   0	149	2	paramJSONObject	JSONObject
    //   9	89	3	localObject1	Object
    //   6	89	4	str	String
    //   25	37	5	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   0	8	110	java/lang/NoSuchMethodException
    //   10	67	110	java/lang/NoSuchMethodException
    //   67	90	110	java/lang/NoSuchMethodException
    //   90	103	110	java/lang/NoSuchMethodException
    //   0	8	123	org/json/JSONException
    //   10	67	123	org/json/JSONException
    //   67	90	123	org/json/JSONException
    //   90	103	123	org/json/JSONException
    //   0	8	136	java/lang/ClassNotFoundException
    //   10	67	136	java/lang/ClassNotFoundException
    //   67	90	136	java/lang/ClassNotFoundException
    //   90	103	136	java/lang/ClassNotFoundException
  }
  
  private Integer reconcileIds(int paramInt, String paramString, ResourceIds paramResourceIds)
  {
    if (paramString != null) {
      if (!paramResourceIds.knownIdName(paramString)) {}
    }
    for (int i = paramResourceIds.idFromName(paramString); (-1 != i) && (-1 != paramInt) && (i != paramInt); i = -1)
    {
      Log.e("MixpanelAPI.EProtocol", "Path contains both a named and an explicit id, and they don't match. No views will be matched.");
      return null;
      Log.w("MixpanelAPI.EProtocol", "Path element contains an id name not known to the system. No views will be matched.\nMake sure that you're not stripping your packages R class out with proguard.\nid name was \"" + paramString + "\"");
      return null;
    }
    if (-1 != i) {
      return Integer.valueOf(i);
    }
    return Integer.valueOf(paramInt);
  }
  
  public Edit readEdit(JSONObject paramJSONObject)
    throws EditProtocol.BadInstructionsException, EditProtocol.CantGetEditAssetsException
  {
    ArrayList localArrayList = new ArrayList();
    List localList;
    Object localObject1;
    try
    {
      localList = readPath(paramJSONObject.getJSONArray("path"), this.mResourceIds);
      if (localList.size() == 0) {
        throw new InapplicableInstructionsException("Edit will not be bound to any element in the UI.");
      }
    }
    catch (NoSuchMethodException paramJSONObject)
    {
      throw new BadInstructionsException("Can't create property mutator", paramJSONObject);
      if (!paramJSONObject.getString("change_type").equals("property")) {
        break label314;
      }
      localObject1 = paramJSONObject.getJSONObject("property").getString("classname");
      if (localObject1 == null) {
        throw new BadInstructionsException("Can't bind an edit property without a target class");
      }
    }
    catch (JSONException paramJSONObject)
    {
      throw new BadInstructionsException("Can't interpret instructions due to JSONException", paramJSONObject);
    }
    Object localObject2;
    int i;
    Object localObject3;
    try
    {
      localObject2 = Class.forName((String)localObject1);
      localObject1 = readPropertyDescription((Class)localObject2, paramJSONObject.getJSONObject("property"));
      paramJSONObject = paramJSONObject.getJSONArray("args");
      localObject2 = new Object[paramJSONObject.length()];
      i = 0;
      while (i < paramJSONObject.length())
      {
        localObject3 = paramJSONObject.getJSONArray(i);
        localObject2[i] = convertArgument(((JSONArray)localObject3).get(0), ((JSONArray)localObject3).getString(1), localArrayList);
        i += 1;
      }
      paramJSONObject = ((PropertyDescription)localObject1).makeMutator((Object[])localObject2);
    }
    catch (ClassNotFoundException paramJSONObject)
    {
      throw new BadInstructionsException("Can't find class for visit path: " + (String)localObject1, paramJSONObject);
    }
    if (paramJSONObject == null) {
      throw new BadInstructionsException("Can't update a read-only property " + ((PropertyDescription)localObject1).name + " (add a mutator to make this work)");
    }
    paramJSONObject = new ViewVisitor.PropertySetVisitor(localList, paramJSONObject, ((PropertyDescription)localObject1).accessor);
    return new Edit(paramJSONObject, localArrayList, null);
    label314:
    int j;
    if (paramJSONObject.getString("change_type").equals("layout"))
    {
      localObject2 = paramJSONObject.getJSONArray("args");
      localObject3 = new ArrayList();
      j = ((JSONArray)localObject2).length();
      i = 0;
    }
    for (;;)
    {
      JSONObject localJSONObject;
      String str1;
      String str2;
      Integer localInteger;
      if (i < j)
      {
        localJSONObject = ((JSONArray)localObject2).optJSONObject(i);
        str1 = localJSONObject.getString("view_id_name");
        str2 = localJSONObject.getString("anchor_id_name");
        localInteger = reconcileIds(-1, str1, this.mResourceIds);
        if (str2.equals("0"))
        {
          localObject1 = Integer.valueOf(0);
          break label575;
        }
      }
      for (;;)
      {
        label422:
        Log.w("MixpanelAPI.EProtocol", "View (" + str1 + ") or anchor (" + str2 + ") not found.");
        break label588;
        if (str2.equals("-1")) {
          localObject1 = Integer.valueOf(-1);
        } else {
          localObject1 = reconcileIds(-1, str2, this.mResourceIds);
        }
        label575:
        do
        {
          ((ArrayList)localObject3).add(new ViewVisitor.LayoutRule(localInteger.intValue(), localJSONObject.getInt("verb"), ((Integer)localObject1).intValue()));
          break label588;
          paramJSONObject = new ViewVisitor.LayoutUpdateVisitor(localList, (List)localObject3, paramJSONObject.getString("name"), this.mLayoutErrorListener);
          break;
          throw new BadInstructionsException("Can't figure out the edit type");
          if (localInteger == null) {
            break label422;
          }
        } while (localObject1 != null);
      }
      label588:
      i += 1;
    }
  }
  
  public ViewVisitor readEventBinding(JSONObject paramJSONObject, ViewVisitor.OnEventListener paramOnEventListener)
    throws EditProtocol.BadInstructionsException
  {
    String str1;
    String str2;
    try
    {
      str1 = paramJSONObject.getString("event_name");
      str2 = paramJSONObject.getString("event_type");
      paramJSONObject = readPath(paramJSONObject.getJSONArray("path"), this.mResourceIds);
      if (paramJSONObject.size() == 0) {
        throw new InapplicableInstructionsException("event '" + str1 + "' will not be bound to any element in the UI.");
      }
    }
    catch (JSONException paramJSONObject)
    {
      throw new BadInstructionsException("Can't interpret instructions due to JSONException", paramJSONObject);
    }
    if ("click".equals(str2)) {
      return new ViewVisitor.AddAccessibilityEventVisitor(paramJSONObject, 1, str1, paramOnEventListener);
    }
    if ("selected".equals(str2)) {
      return new ViewVisitor.AddAccessibilityEventVisitor(paramJSONObject, 4, str1, paramOnEventListener);
    }
    if ("text_changed".equals(str2)) {
      return new ViewVisitor.AddTextChangeListener(paramJSONObject, str1, paramOnEventListener);
    }
    if ("detected".equals(str2)) {
      return new ViewVisitor.ViewDetectorVisitor(paramJSONObject, str1, paramOnEventListener);
    }
    throw new BadInstructionsException("Mixpanel can't track event type \"" + str2 + "\"");
  }
  
  List<Pathfinder.PathElement> readPath(JSONArray paramJSONArray, ResourceIds paramResourceIds)
    throws JSONException
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    for (;;)
    {
      Object localObject1 = localArrayList;
      Object localObject3;
      Object localObject2;
      int k;
      String str1;
      int m;
      String str2;
      if (i < paramJSONArray.length())
      {
        localObject3 = paramJSONArray.getJSONObject(i);
        localObject2 = JSONUtils.optionalStringKey((JSONObject)localObject3, "prefix");
        localObject1 = JSONUtils.optionalStringKey((JSONObject)localObject3, "view_class");
        k = ((JSONObject)localObject3).optInt("index", -1);
        str1 = JSONUtils.optionalStringKey((JSONObject)localObject3, "contentDescription");
        m = ((JSONObject)localObject3).optInt("id", -1);
        str2 = JSONUtils.optionalStringKey((JSONObject)localObject3, "mp_id_name");
        localObject3 = JSONUtils.optionalStringKey((JSONObject)localObject3, "tag");
        if (!"shortest".equals(localObject2)) {
          break label140;
        }
      }
      for (int j = 1;; j = 0)
      {
        localObject2 = reconcileIds(m, str2, paramResourceIds);
        if (localObject2 != null) {
          break label188;
        }
        localObject1 = NEVER_MATCH_PATH;
        return (List<Pathfinder.PathElement>)localObject1;
        label140:
        if (localObject2 != null) {
          break;
        }
      }
      Log.w("MixpanelAPI.EProtocol", "Unrecognized prefix type \"" + (String)localObject2 + "\". No views will be matched");
      return NEVER_MATCH_PATH;
      label188:
      localArrayList.add(new Pathfinder.PathElement(j, (String)localObject1, k, ((Integer)localObject2).intValue(), str1, (String)localObject3));
      i += 1;
    }
  }
  
  public ViewSnapshot readSnapshotConfig(JSONObject paramJSONObject)
    throws EditProtocol.BadInstructionsException
  {
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      int i;
      try
      {
        paramJSONObject = paramJSONObject.getJSONObject("config").getJSONArray("classes");
        i = 0;
        if (i < paramJSONObject.length())
        {
          Object localObject = paramJSONObject.getJSONObject(i);
          Class localClass = Class.forName(((JSONObject)localObject).getString("name"));
          localObject = ((JSONObject)localObject).getJSONArray("properties");
          int j = 0;
          if (j < ((JSONArray)localObject).length())
          {
            localArrayList.add(readPropertyDescription(localClass, ((JSONArray)localObject).getJSONObject(j)));
            j += 1;
            continue;
          }
        }
        else
        {
          paramJSONObject = new ViewSnapshot(localArrayList, this.mResourceIds);
          return paramJSONObject;
        }
      }
      catch (JSONException paramJSONObject)
      {
        throw new BadInstructionsException("Can't read snapshot configuration", paramJSONObject);
      }
      catch (ClassNotFoundException paramJSONObject)
      {
        throw new BadInstructionsException("Can't resolve types for snapshot configuration", paramJSONObject);
      }
      i += 1;
    }
  }
  
  public Pair<String, Object> readTweak(JSONObject paramJSONObject)
    throws EditProtocol.BadInstructionsException
  {
    String str2;
    for (;;)
    {
      try
      {
        String str1 = paramJSONObject.getString("name");
        str2 = paramJSONObject.getString("type");
        if ("number".equals(str2))
        {
          str2 = paramJSONObject.getString("encoding");
          if ("d".equals(str2))
          {
            paramJSONObject = Double.valueOf(paramJSONObject.getDouble("value"));
            return new Pair(str1, paramJSONObject);
          }
          if ("l".equals(str2))
          {
            paramJSONObject = Long.valueOf(paramJSONObject.getLong("value"));
            continue;
          }
          throw new BadInstructionsException("number must have encoding of type \"l\" for long or \"d\" for double in: " + paramJSONObject);
        }
      }
      catch (JSONException paramJSONObject)
      {
        throw new BadInstructionsException("Can't read tweak update", paramJSONObject);
      }
      if ("boolean".equals(str2))
      {
        paramJSONObject = Boolean.valueOf(paramJSONObject.getBoolean("value"));
      }
      else
      {
        if (!"string".equals(str2)) {
          break;
        }
        paramJSONObject = paramJSONObject.getString("value");
      }
    }
    throw new BadInstructionsException("Unrecognized tweak type " + str2 + " in: " + paramJSONObject);
  }
  
  public static class BadInstructionsException
    extends Exception
  {
    private static final long serialVersionUID = -4062004792184145311L;
    
    public BadInstructionsException(String paramString)
    {
      super();
    }
    
    public BadInstructionsException(String paramString, Throwable paramThrowable)
    {
      super(paramThrowable);
    }
  }
  
  public static class CantGetEditAssetsException
    extends Exception
  {
    public CantGetEditAssetsException(String paramString)
    {
      super();
    }
    
    public CantGetEditAssetsException(String paramString, Throwable paramThrowable)
    {
      super(paramThrowable);
    }
  }
  
  public static class Edit
  {
    public final List<String> imageUrls;
    public final ViewVisitor visitor;
    
    private Edit(ViewVisitor paramViewVisitor, List<String> paramList)
    {
      this.visitor = paramViewVisitor;
      this.imageUrls = paramList;
    }
  }
  
  public static class InapplicableInstructionsException
    extends EditProtocol.BadInstructionsException
  {
    private static final long serialVersionUID = 3977056710817909104L;
    
    public InapplicableInstructionsException(String paramString)
    {
      super();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/viewcrawler/EditProtocol.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */