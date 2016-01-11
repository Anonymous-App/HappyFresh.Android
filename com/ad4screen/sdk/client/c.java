package com.ad4screen.sdk.client;

import android.os.Bundle;
import com.ad4screen.sdk.systems.f.a;

public final class c
{
  public static abstract interface a
  {
    public abstract void a(Bundle paramBundle);
  }
  
  public static final class b
    implements f.a<c.a>
  {
    private Bundle a;
    
    public b(Bundle paramBundle)
    {
      this.a = paramBundle;
    }
    
    public void a(c.a parama)
    {
      parama.a(this.a);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/client/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */