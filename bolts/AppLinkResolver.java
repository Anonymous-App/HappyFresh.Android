package bolts;

import android.net.Uri;

public abstract interface AppLinkResolver
{
  public abstract Task<AppLink> getAppLinkFromUrlInBackground(Uri paramUri);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/bolts/AppLinkResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */