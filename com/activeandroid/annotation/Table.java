package com.activeandroid.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface Table
{
  public static final String DEFAULT_ID_NAME = "Id";
  
  String id() default "Id";
  
  String name();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/annotation/Table.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */