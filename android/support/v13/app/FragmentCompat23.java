package android.support.v13.app;

import android.app.Fragment;

class FragmentCompat23
{
  public static void requestPermissions(Fragment paramFragment, String[] paramArrayOfString, int paramInt)
  {
    paramFragment.requestPermissions(paramArrayOfString, paramInt);
  }
  
  public static boolean shouldShowRequestPermissionRationale(Fragment paramFragment, String paramString)
  {
    return paramFragment.shouldShowRequestPermissionRationale(paramString);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/android/support/v13/app/FragmentCompat23.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */