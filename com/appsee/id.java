package com.appsee;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class id
  implements InvocationHandler
{
  id(ud paramud, Object paramObject) {}
  
  public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject)
    throws Throwable
  {
    ol.l(new rd(this, paramMethod, paramArrayOfObject));
    return bc.i(this.G, paramMethod.getName(), paramMethod.getParameterTypes(), paramArrayOfObject);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/id.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */