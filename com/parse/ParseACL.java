package com.parse;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseACL
{
  private static final String KEY_ROLE_PREFIX = "role:";
  private static final String PUBLIC_KEY = "*";
  private static final String UNRESOLVED_KEY = "*unresolved";
  private static final String UNRESOLVED_USER_JSON_KEY = "unresolvedUser";
  private Map<String, Permissions> permissionsById = new HashMap();
  private boolean shared;
  private ParseUser unresolvedUser;
  
  public ParseACL() {}
  
  public ParseACL(ParseUser paramParseUser)
  {
    this();
    setReadAccess(paramParseUser, true);
    setWriteAccess(paramParseUser, true);
  }
  
  static ParseACL createACLFromJSONObject(JSONObject paramJSONObject, ParseDecoder paramParseDecoder)
  {
    ParseACL localParseACL = new ParseACL();
    Iterator localIterator = ParseJSONUtils.keys(paramJSONObject).iterator();
    while (localIterator.hasNext())
    {
      Object localObject = (String)localIterator.next();
      if (((String)localObject).equals("unresolvedUser")) {
        try
        {
          localObject = paramJSONObject.getJSONObject((String)localObject);
          localParseACL.unresolvedUser = ((ParseUser)paramParseDecoder.decode(localObject));
        }
        catch (JSONException paramJSONObject)
        {
          throw new RuntimeException(paramJSONObject);
        }
      } else {
        try
        {
          Permissions localPermissions = Permissions.createPermissionsFromJSONObject(paramJSONObject.getJSONObject((String)localObject));
          localParseACL.permissionsById.put(localObject, localPermissions);
        }
        catch (JSONException paramJSONObject)
        {
          throw new RuntimeException("could not decode ACL: " + paramJSONObject.getMessage());
        }
      }
    }
    return localParseACL;
  }
  
  static ParseACL getDefaultACL()
  {
    return getDefaultACLController().get();
  }
  
  private static ParseDefaultACLController getDefaultACLController()
  {
    return ParseCorePlugins.getInstance().getDefaultACLController();
  }
  
  private void prepareUnresolvedUser(ParseUser paramParseUser)
  {
    if (this.unresolvedUser != paramParseUser)
    {
      this.permissionsById.remove("*unresolved");
      this.unresolvedUser = paramParseUser;
      paramParseUser.registerSaveListener(new UserResolutionListener(this));
    }
  }
  
  public static void setDefaultACL(ParseACL paramParseACL, boolean paramBoolean)
  {
    getDefaultACLController().set(paramParseACL, paramBoolean);
  }
  
  private void setPermissionsIfNonEmpty(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((!paramBoolean1) && (!paramBoolean2))
    {
      this.permissionsById.remove(paramString);
      return;
    }
    this.permissionsById.put(paramString, new Permissions(paramBoolean1, paramBoolean2));
  }
  
  private void setUnresolvedReadAccess(ParseUser paramParseUser, boolean paramBoolean)
  {
    prepareUnresolvedUser(paramParseUser);
    setReadAccess("*unresolved", paramBoolean);
  }
  
  private void setUnresolvedWriteAccess(ParseUser paramParseUser, boolean paramBoolean)
  {
    prepareUnresolvedUser(paramParseUser);
    setWriteAccess("*unresolved", paramBoolean);
  }
  
  private static void validateRoleState(ParseRole paramParseRole)
  {
    if ((paramParseRole == null) || (paramParseRole.getObjectId() == null)) {
      throw new IllegalArgumentException("Roles must be saved to the server before they can be used in an ACL.");
    }
  }
  
  ParseACL copy()
  {
    ParseACL localParseACL = new ParseACL();
    Iterator localIterator = this.permissionsById.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localParseACL.permissionsById.put(str, new Permissions((Permissions)this.permissionsById.get(str)));
    }
    localParseACL.unresolvedUser = this.unresolvedUser;
    if (this.unresolvedUser != null) {
      this.unresolvedUser.registerSaveListener(new UserResolutionListener(localParseACL));
    }
    return localParseACL;
  }
  
  Map<String, Permissions> getPermissionsById()
  {
    return this.permissionsById;
  }
  
  public boolean getPublicReadAccess()
  {
    return getReadAccess("*");
  }
  
  public boolean getPublicWriteAccess()
  {
    return getWriteAccess("*");
  }
  
  public boolean getReadAccess(ParseUser paramParseUser)
  {
    if (paramParseUser == this.unresolvedUser) {
      return getReadAccess("*unresolved");
    }
    if (paramParseUser.isLazy()) {
      return false;
    }
    if (paramParseUser.getObjectId() == null) {
      throw new IllegalArgumentException("cannot getReadAccess for a user with null id");
    }
    return getReadAccess(paramParseUser.getObjectId());
  }
  
  public boolean getReadAccess(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("cannot getReadAccess for null userId");
    }
    paramString = (Permissions)this.permissionsById.get(paramString);
    return (paramString != null) && (paramString.getReadPermission());
  }
  
  public boolean getRoleReadAccess(ParseRole paramParseRole)
  {
    validateRoleState(paramParseRole);
    return getRoleReadAccess(paramParseRole.getName());
  }
  
  public boolean getRoleReadAccess(String paramString)
  {
    return getReadAccess("role:" + paramString);
  }
  
  public boolean getRoleWriteAccess(ParseRole paramParseRole)
  {
    validateRoleState(paramParseRole);
    return getRoleWriteAccess(paramParseRole.getName());
  }
  
  public boolean getRoleWriteAccess(String paramString)
  {
    return getWriteAccess("role:" + paramString);
  }
  
  ParseUser getUnresolvedUser()
  {
    return this.unresolvedUser;
  }
  
  public boolean getWriteAccess(ParseUser paramParseUser)
  {
    if (paramParseUser == this.unresolvedUser) {
      return getWriteAccess("*unresolved");
    }
    if (paramParseUser.isLazy()) {
      return false;
    }
    if (paramParseUser.getObjectId() == null) {
      throw new IllegalArgumentException("cannot getWriteAccess for a user with null id");
    }
    return getWriteAccess(paramParseUser.getObjectId());
  }
  
  public boolean getWriteAccess(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("cannot getWriteAccess for null userId");
    }
    paramString = (Permissions)this.permissionsById.get(paramString);
    return (paramString != null) && (paramString.getWritePermission());
  }
  
  boolean hasUnresolvedUser()
  {
    return this.unresolvedUser != null;
  }
  
  boolean isShared()
  {
    return this.shared;
  }
  
  void resolveUser(ParseUser paramParseUser)
  {
    if (paramParseUser != this.unresolvedUser) {
      return;
    }
    if (this.permissionsById.containsKey("*unresolved"))
    {
      this.permissionsById.put(paramParseUser.getObjectId(), this.permissionsById.get("*unresolved"));
      this.permissionsById.remove("*unresolved");
    }
    this.unresolvedUser = null;
  }
  
  public void setPublicReadAccess(boolean paramBoolean)
  {
    setReadAccess("*", paramBoolean);
  }
  
  public void setPublicWriteAccess(boolean paramBoolean)
  {
    setWriteAccess("*", paramBoolean);
  }
  
  public void setReadAccess(ParseUser paramParseUser, boolean paramBoolean)
  {
    if (paramParseUser.getObjectId() == null)
    {
      if (paramParseUser.isLazy())
      {
        setUnresolvedReadAccess(paramParseUser, paramBoolean);
        return;
      }
      throw new IllegalArgumentException("cannot setReadAccess for a user with null id");
    }
    setReadAccess(paramParseUser.getObjectId(), paramBoolean);
  }
  
  public void setReadAccess(String paramString, boolean paramBoolean)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("cannot setReadAccess for null userId");
    }
    setPermissionsIfNonEmpty(paramString, paramBoolean, getWriteAccess(paramString));
  }
  
  public void setRoleReadAccess(ParseRole paramParseRole, boolean paramBoolean)
  {
    validateRoleState(paramParseRole);
    setRoleReadAccess(paramParseRole.getName(), paramBoolean);
  }
  
  public void setRoleReadAccess(String paramString, boolean paramBoolean)
  {
    setReadAccess("role:" + paramString, paramBoolean);
  }
  
  public void setRoleWriteAccess(ParseRole paramParseRole, boolean paramBoolean)
  {
    validateRoleState(paramParseRole);
    setRoleWriteAccess(paramParseRole.getName(), paramBoolean);
  }
  
  public void setRoleWriteAccess(String paramString, boolean paramBoolean)
  {
    setWriteAccess("role:" + paramString, paramBoolean);
  }
  
  void setShared(boolean paramBoolean)
  {
    this.shared = paramBoolean;
  }
  
  public void setWriteAccess(ParseUser paramParseUser, boolean paramBoolean)
  {
    if (paramParseUser.getObjectId() == null)
    {
      if (paramParseUser.isLazy())
      {
        setUnresolvedWriteAccess(paramParseUser, paramBoolean);
        return;
      }
      throw new IllegalArgumentException("cannot setWriteAccess for a user with null id");
    }
    setWriteAccess(paramParseUser.getObjectId(), paramBoolean);
  }
  
  public void setWriteAccess(String paramString, boolean paramBoolean)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("cannot setWriteAccess for null userId");
    }
    setPermissionsIfNonEmpty(paramString, getReadAccess(paramString), paramBoolean);
  }
  
  JSONObject toJSONObject(ParseEncoder paramParseEncoder)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      Iterator localIterator = this.permissionsById.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        localJSONObject.put(str, ((Permissions)this.permissionsById.get(str)).toJSONObject());
      }
      if (this.unresolvedUser == null) {
        return localJSONObject;
      }
    }
    catch (JSONException paramParseEncoder)
    {
      throw new RuntimeException(paramParseEncoder);
    }
    localJSONObject.put("unresolvedUser", paramParseEncoder.encode(this.unresolvedUser));
    return localJSONObject;
  }
  
  private static class Permissions
  {
    private static final String READ_PERMISSION = "read";
    private static final String WRITE_PERMISSION = "write";
    private final boolean readPermission;
    private final boolean writePermission;
    
    Permissions(Permissions paramPermissions)
    {
      this.readPermission = paramPermissions.readPermission;
      this.writePermission = paramPermissions.writePermission;
    }
    
    Permissions(boolean paramBoolean1, boolean paramBoolean2)
    {
      this.readPermission = paramBoolean1;
      this.writePermission = paramBoolean2;
    }
    
    static Permissions createPermissionsFromJSONObject(JSONObject paramJSONObject)
    {
      return new Permissions(paramJSONObject.optBoolean("read", false), paramJSONObject.optBoolean("write", false));
    }
    
    boolean getReadPermission()
    {
      return this.readPermission;
    }
    
    boolean getWritePermission()
    {
      return this.writePermission;
    }
    
    JSONObject toJSONObject()
    {
      JSONObject localJSONObject = new JSONObject();
      try
      {
        if (this.readPermission) {
          localJSONObject.put("read", true);
        }
        if (this.writePermission) {
          localJSONObject.put("write", true);
        }
        return localJSONObject;
      }
      catch (JSONException localJSONException)
      {
        throw new RuntimeException(localJSONException);
      }
    }
  }
  
  private static class UserResolutionListener
    implements GetCallback<ParseObject>
  {
    private final WeakReference<ParseACL> parent;
    
    public UserResolutionListener(ParseACL paramParseACL)
    {
      this.parent = new WeakReference(paramParseACL);
    }
    
    public void done(ParseObject paramParseObject, ParseException paramParseException)
    {
      try
      {
        paramParseException = (ParseACL)this.parent.get();
        if (paramParseException != null) {
          paramParseException.resolveUser((ParseUser)paramParseObject);
        }
        return;
      }
      finally
      {
        paramParseObject.unregisterSaveListener(this);
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseACL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */