package butterknife.internal;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface ListenerMethod
{
  String defaultReturn() default "null";
  
  String name();
  
  String[] parameters() default {};
  
  String returnType() default "void";
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/butterknife/internal/ListenerMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */