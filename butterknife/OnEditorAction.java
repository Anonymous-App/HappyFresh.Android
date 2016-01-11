package butterknife;

import butterknife.internal.ListenerClass;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ListenerClass(method={@butterknife.internal.ListenerMethod(defaultReturn="false", name="onEditorAction", parameters={"android.widget.TextView", "int", "android.view.KeyEvent"}, returnType="boolean")}, setter="setOnEditorActionListener", targetType="android.widget.TextView", type="android.widget.TextView.OnEditorActionListener")
@Retention(RetentionPolicy.CLASS)
@Target({java.lang.annotation.ElementType.METHOD})
public @interface OnEditorAction
{
  int[] value() default {-1};
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/butterknife/OnEditorAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */