package com.optimizely.View;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import com.optimizely.Assets.OptimizelyAssets;
import com.optimizely.Core.OptimizelyCodec;
import com.optimizely.Core.OptimizelyUtils;
import com.optimizely.Optimizely;
import com.optimizely.View.idmanager.OptimizelyIdManager;
import com.optimizely.fonts.OptimizelyFontsManager;
import java.util.Hashtable;
import java.util.Map;

public class OptimizelyViewPropertyHandler
{
  private static final String OPTIMIZELY_VIEW_PROPERTY_HANDLER = "OptimizelyViewPropertyHandler";
  
  @NonNull
  public static Map<String, Object> getViewProperties(@NonNull View paramView, @NonNull Optimizely paramOptimizely)
  {
    Hashtable localHashtable = new Hashtable();
    Context localContext = paramOptimizely.getCurrentContext();
    Object localObject;
    if ((paramView instanceof TextView))
    {
      TextView localTextView = (TextView)paramView;
      if (localTextView.getText() == null) {
        break label490;
      }
      localObject = localTextView.getText();
      localHashtable.put("text", ((CharSequence)localObject).toString());
      localHashtable.put("textColor", OptimizelyCodec.toColorMap(localTextView.getCurrentTextColor()));
      localObject = paramOptimizely.getFontsManager().getFont(localTextView);
      if (!((Map)localObject).isEmpty()) {
        localHashtable.put("font", localObject);
      }
      if (Build.VERSION.SDK_INT >= 17) {
        localHashtable.put("textAlignment", Integer.valueOf(OptimizelyCodec.encodeGravity(localTextView.getGravity())));
      }
    }
    if ((paramView instanceof ImageView)) {
      localHashtable.put("image", paramOptimizely.getAssets().getAssetName(paramView));
    }
    if ((paramView instanceof CheckBox)) {
      localHashtable.put("gravity", Integer.valueOf(((CheckBox)paramView).getGravity()));
    }
    if ((paramView instanceof ProgressBar))
    {
      paramOptimizely = (ProgressBar)paramView;
      localHashtable.put("max", Integer.valueOf(paramOptimizely.getMax()));
      localHashtable.put("isIndeterminate", Boolean.valueOf(paramOptimizely.isIndeterminate()));
    }
    if ((paramView instanceof CompoundButton)) {
      localHashtable.put("defaultState", Boolean.valueOf(((CompoundButton)paramView).isChecked()));
    }
    paramOptimizely = paramView.getBackground();
    if ((paramOptimizely instanceof ColorDrawable)) {
      localHashtable.put("backgroundColor", OptimizelyCodec.toColorMap(((ColorDrawable)paramOptimizely).getColor()));
    }
    for (;;)
    {
      localHashtable.put("alpha", Float.valueOf(paramView.getAlpha()));
      paramOptimizely = paramView.getLayoutParams();
      if (paramOptimizely != null)
      {
        int m = OptimizelyUtils.convertPXtoDP(localContext, (int)paramView.getX());
        int n = OptimizelyUtils.convertPXtoDP(localContext, (int)paramView.getY());
        int j = paramOptimizely.width;
        int k = paramOptimizely.height;
        int i = j;
        if (j > 0) {
          i = OptimizelyUtils.convertPXtoDP(localContext, j);
        }
        j = k;
        if (k > 0) {
          j = OptimizelyUtils.convertPXtoDP(localContext, k);
        }
        localHashtable.put("frame", OptimizelyCodec.toRectMap(new Rect(m, n, m + i, n + j)));
      }
      localHashtable.put("rotation", Float.valueOf(paramView.getRotation()));
      localHashtable.put("rotationX", Float.valueOf(paramView.getRotationX()));
      localHashtable.put("rotationY", Float.valueOf(paramView.getRotationY()));
      localHashtable.put("visibility", Integer.valueOf(paramView.getVisibility()));
      return localHashtable;
      label490:
      localObject = "";
      break;
      if (paramOptimizely == null) {
        localHashtable.put("backgroundColor", OptimizelyCodec.toColorMap(0));
      }
    }
  }
  
  @TargetApi(14)
  private static void setSwitchProperty(@NonNull Switch paramSwitch, @NonNull String paramString, @NonNull Object paramObject)
  {
    if (paramString.equalsIgnoreCase("textOn")) {
      paramSwitch.setTextOn((String)paramObject);
    }
    if (paramString.equalsIgnoreCase("textOff")) {
      paramSwitch.setTextOff((String)paramObject);
    }
  }
  
  private static void setTextViewProperty(@NonNull TextView paramTextView, @NonNull String paramString, @NonNull Object paramObject, @NonNull Optimizely paramOptimizely)
  {
    if (paramString.equalsIgnoreCase("text")) {
      paramTextView.setText((String)paramObject);
    }
    if (paramString.equalsIgnoreCase("textColor")) {
      paramTextView.setTextColor(OptimizelyCodec.toColor(paramOptimizely, paramObject));
    }
    if ((paramString.equals("font")) && ((paramObject instanceof Map))) {
      paramOptimizely.getFontsManager().setFont(paramTextView, (Map)paramObject);
    }
    if ((paramString.equalsIgnoreCase("textAlignment")) && ((paramObject instanceof Number)))
    {
      int i = OptimizelyCodec.decodeGravity(((Number)paramObject).intValue());
      if (i >= 0) {
        paramTextView.setGravity(i);
      }
    }
  }
  
  public static void setViewProperty(@Nullable View paramView, @NonNull String paramString, @Nullable Object paramObject, @NonNull Optimizely paramOptimizely)
  {
    localObject1 = paramOptimizely.getCurrentContext();
    if (paramView == null)
    {
      paramOptimizely.verboseLog(true, "OptimizelyViewPropertyHandler", "null view sent to setViewProperty", new Object[0]);
      return;
    }
    if (paramObject == null) {
      paramOptimizely.verboseLog(true, "OptimizelyViewPropertyHandler", "null value sent to setViewProperty", new Object[0]);
    }
    try
    {
      localObject2 = paramView.getLayoutParams();
      if (paramString.equalsIgnoreCase("frame"))
      {
        Rect localRect = OptimizelyCodec.toRect(paramOptimizely, paramObject);
        if ((localRect != null) && (localObject2 != null))
        {
          int k = localRect.width();
          int j = localRect.height();
          i = k;
          if (k > 0) {
            i = OptimizelyUtils.convertDPtoPX((Context)localObject1, k);
          }
          ((ViewGroup.LayoutParams)localObject2).width = i;
          i = j;
          if (j > 0) {
            i = OptimizelyUtils.convertDPtoPX((Context)localObject1, j);
          }
          ((ViewGroup.LayoutParams)localObject2).height = i;
          paramView.setX(OptimizelyUtils.convertDPtoPX((Context)localObject1, localRect.left));
          paramView.setY(OptimizelyUtils.convertDPtoPX((Context)localObject1, localRect.top));
          paramView.setLayoutParams((ViewGroup.LayoutParams)localObject2);
          localObject1 = paramOptimizely.getForegroundActivity();
          if (localObject1 != null) {
            ((Activity)localObject1).runOnUiThread(new Runnable()
            {
              public void run()
              {
                this.val$ref.requestLayout();
              }
            });
          }
        }
      }
      if ((paramString.equalsIgnoreCase("rotation")) && (paramObject != null)) {
        paramView.setRotation(((Number)paramObject).floatValue());
      }
      if ((paramString.equalsIgnoreCase("rotationX")) && (paramObject != null)) {
        paramView.setRotationX(((Number)paramObject).floatValue());
      }
      if ((paramString.equalsIgnoreCase("rotationY")) && (paramObject != null)) {
        paramView.setRotationY(((Number)paramObject).floatValue());
      }
      if ((paramString.equalsIgnoreCase("visibility")) && (paramObject != null))
      {
        i = ((Number)paramObject).intValue();
        if ((i != 0) && (i != 4) && (i != 8)) {
          break label491;
        }
        paramView.setVisibility(i);
      }
    }
    catch (ClassCastException paramObject)
    {
      for (;;)
      {
        paramOptimizely.verboseLog(true, "OptimizelyViewPropertyHandler", "Failed to convert object to correct data type for key %1$s with exp %2$s", new Object[] { paramString, ((ClassCastException)paramObject).toString() });
        paramView.postInvalidate();
        return;
        paramOptimizely.verboseLog(true, "OptimizelyViewPropertyHandler", "Unknown visibility flag %d", new Object[] { Integer.valueOf(i) });
      }
    }
    catch (Exception paramObject)
    {
      for (;;)
      {
        Object localObject2;
        paramOptimizely.verboseLog(true, "OptimizelyViewPropertyHandler", "Failed to apply %1$s with exception %2$s", new Object[] { paramString, ((Exception)paramObject).getLocalizedMessage() });
        continue;
        int i = ((String)localObject2).hashCode();
        if (paramView.getTag(i) == null) {
          paramView.setTag(i, ((ImageView)paramView).getDrawable());
        }
        if (paramObject == null)
        {
          if (paramView.getTag(i) == null) {
            ((ImageView)paramView).setImageDrawable(null);
          } else {
            ((ImageView)paramView).setImageDrawable((Drawable)paramView.getTag(i));
          }
        }
        else {
          paramOptimizely.getAssets().setAssetProperty((ImageView)localObject1, (Map)paramObject);
        }
      }
    }
    if ((paramString.equalsIgnoreCase("alpha")) && ((paramObject instanceof Number))) {
      paramView.setAlpha(((Number)paramObject).floatValue());
    }
    if (paramString.equalsIgnoreCase("backgroundColor")) {
      paramView.setBackgroundColor(OptimizelyCodec.toColor(paramOptimizely, paramObject));
    }
    if (((paramView instanceof Switch)) && (paramObject != null)) {
      setSwitchProperty((Switch)paramView, paramString, paramObject);
    }
    if (((paramView instanceof TextView)) && (paramObject != null)) {
      setTextViewProperty((TextView)paramView, paramString, paramObject, paramOptimizely);
    }
    if ((paramString.equalsIgnoreCase("image")) && ((paramView instanceof ImageView)))
    {
      localObject1 = (ImageView)paramView;
      paramObject = OptimizelyCodec.getImageValues(paramObject);
      localObject2 = paramOptimizely.getIdManager().getOptimizelyId(paramView);
      if (localObject2 != null) {
        break label545;
      }
      paramOptimizely.verboseLog(true, "OptimizelyViewPropertyHandler", "Failed to set image property on view because we could not find its optimizelyId", new Object[0]);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/View/OptimizelyViewPropertyHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */