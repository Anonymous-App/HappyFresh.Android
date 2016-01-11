package bolts;

import java.util.Locale;
import java.util.concurrent.CancellationException;

public class CancellationToken
{
  private final CancellationTokenSource tokenSource;
  
  CancellationToken(CancellationTokenSource paramCancellationTokenSource)
  {
    this.tokenSource = paramCancellationTokenSource;
  }
  
  public boolean isCancellationRequested()
  {
    return this.tokenSource.isCancellationRequested();
  }
  
  public CancellationTokenRegistration register(Runnable paramRunnable)
  {
    return this.tokenSource.register(paramRunnable);
  }
  
  public void throwIfCancellationRequested()
    throws CancellationException
  {
    this.tokenSource.throwIfCancellationRequested();
  }
  
  public String toString()
  {
    return String.format(Locale.US, "%s@%s[cancellationRequested=%s]", new Object[] { getClass().getName(), Integer.toHexString(hashCode()), Boolean.toString(this.tokenSource.isCancellationRequested()) });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/bolts/CancellationToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */