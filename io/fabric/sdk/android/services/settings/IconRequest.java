package io.fabric.sdk.android.services.settings;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;

public class IconRequest
{
  public final String hash;
  public final int height;
  public final int iconResourceId;
  public final int width;
  
  public IconRequest(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    this.hash = paramString;
    this.iconResourceId = paramInt1;
    this.width = paramInt2;
    this.height = paramInt3;
  }
  
  public static IconRequest build(Context paramContext, String paramString)
  {
    Object localObject = null;
    if (paramString != null) {}
    try
    {
      int i = CommonUtils.getAppIconResourceId(paramContext);
      Fabric.getLogger().d("Fabric", "App icon resource ID is " + i);
      localObject = new BitmapFactory.Options();
      ((BitmapFactory.Options)localObject).inJustDecodeBounds = true;
      BitmapFactory.decodeResource(paramContext.getResources(), i, (BitmapFactory.Options)localObject);
      localObject = new IconRequest(paramString, i, ((BitmapFactory.Options)localObject).outWidth, ((BitmapFactory.Options)localObject).outHeight);
      return (IconRequest)localObject;
    }
    catch (Exception paramContext)
    {
      Fabric.getLogger().e("Fabric", "Failed to load icon", paramContext);
    }
    return null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/settings/IconRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */