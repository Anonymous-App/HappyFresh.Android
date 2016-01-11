package com.ad4screen.sdk;

import com.ad4screen.sdk.common.annotations.API;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
@API
public @interface Tag
{
  String name();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/Tag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */