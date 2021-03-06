package butterknife;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface InjectView
{
  int value();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/butterknife/InjectView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */