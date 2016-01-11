package butterknife.internal;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.ANNOTATION_TYPE})
public @interface ListenerClass
{
  Class<? extends Enum<?>> callbacks() default NONE.class;
  
  int genericArguments() default 0;
  
  ListenerMethod[] method() default {};
  
  String setter();
  
  String targetType();
  
  String type();
  
  public static enum NONE
  {
    private NONE() {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/butterknife/internal/ListenerClass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */