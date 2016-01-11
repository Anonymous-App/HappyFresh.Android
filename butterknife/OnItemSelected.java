package butterknife;

import butterknife.internal.ListenerClass;
import butterknife.internal.ListenerMethod;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ListenerClass(callbacks=Callback.class, setter="setOnItemSelectedListener", targetType="android.widget.AdapterView<?>", type="android.widget.AdapterView.OnItemSelectedListener")
@Retention(RetentionPolicy.CLASS)
@Target({java.lang.annotation.ElementType.METHOD})
public @interface OnItemSelected
{
  Callback callback() default Callback.ITEM_SELECTED;
  
  int[] value() default {-1};
  
  public static enum Callback
  {
    ITEM_SELECTED,  NOTHING_SELECTED;
    
    private Callback() {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/butterknife/OnItemSelected.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */