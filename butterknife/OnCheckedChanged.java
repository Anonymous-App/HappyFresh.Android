package butterknife;

import butterknife.internal.ListenerClass;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ListenerClass(method={@butterknife.internal.ListenerMethod(name="onCheckedChanged", parameters={"android.widget.CompoundButton", "boolean"})}, setter="setOnCheckedChangeListener", targetType="android.widget.CompoundButton", type="android.widget.CompoundButton.OnCheckedChangeListener")
@Retention(RetentionPolicy.CLASS)
@Target({java.lang.annotation.ElementType.METHOD})
public @interface OnCheckedChanged
{
  int[] value() default {-1};
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/butterknife/OnCheckedChanged.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */