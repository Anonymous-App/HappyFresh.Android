package com.appsee;

import org.apache.http.client.methods.HttpRequestBase;

class tb
  implements Runnable
{
  tb(ib paramib) {}
  
  public void run()
  {
    ib.i(this.G).abort();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/tb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */