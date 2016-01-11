package retrofit.android;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;

public final class MainThreadExecutor
  implements Executor
{
  private final Handler handler = new Handler(Looper.getMainLooper());
  
  public void execute(Runnable paramRunnable)
  {
    this.handler.post(paramRunnable);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/retrofit/android/MainThreadExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */