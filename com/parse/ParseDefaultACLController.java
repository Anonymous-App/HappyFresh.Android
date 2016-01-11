package com.parse;

import java.lang.ref.WeakReference;

class ParseDefaultACLController
{
  ParseACL defaultACL;
  boolean defaultACLUsesCurrentUser;
  ParseACL defaultACLWithCurrentUser;
  WeakReference<ParseUser> lastCurrentUser;
  
  public ParseACL get()
  {
    if ((this.defaultACLUsesCurrentUser) && (this.defaultACL != null))
    {
      ParseUser localParseUser = ParseUser.getCurrentUser();
      if (localParseUser != null)
      {
        if (this.lastCurrentUser != null) {}
        for (Object localObject = (ParseUser)this.lastCurrentUser.get();; localObject = null)
        {
          if (localObject != localParseUser)
          {
            localObject = this.defaultACL.copy();
            ((ParseACL)localObject).setShared(true);
            ((ParseACL)localObject).setReadAccess(localParseUser, true);
            ((ParseACL)localObject).setWriteAccess(localParseUser, true);
            this.defaultACLWithCurrentUser = ((ParseACL)localObject);
            this.lastCurrentUser = new WeakReference(localParseUser);
          }
          return this.defaultACLWithCurrentUser;
        }
      }
    }
    return this.defaultACL;
  }
  
  public void set(ParseACL paramParseACL, boolean paramBoolean)
  {
    this.defaultACLWithCurrentUser = null;
    this.lastCurrentUser = null;
    if (paramParseACL != null)
    {
      paramParseACL = paramParseACL.copy();
      paramParseACL.setShared(true);
      this.defaultACL = paramParseACL;
      this.defaultACLUsesCurrentUser = paramBoolean;
      return;
    }
    this.defaultACL = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseDefaultACLController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */