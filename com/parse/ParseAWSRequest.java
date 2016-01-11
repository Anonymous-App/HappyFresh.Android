package com.parse;

import bolts.Task;
import com.parse.http.ParseHttpRequest.Method;
import com.parse.http.ParseHttpResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.Callable;

class ParseAWSRequest
  extends ParseRequest<Void>
{
  private final File tempFile;
  
  public ParseAWSRequest(ParseHttpRequest.Method paramMethod, String paramString, File paramFile)
  {
    super(paramMethod, paramString);
    this.tempFile = paramFile;
  }
  
  protected Task<Void> onResponseAsync(final ParseHttpResponse paramParseHttpResponse, final ProgressCallback paramProgressCallback)
  {
    int i = paramParseHttpResponse.getStatusCode();
    if (((i >= 200) && (i < 300)) || (i == 304))
    {
      if (this.method != ParseHttpRequest.Method.GET) {
        return null;
      }
    }
    else
    {
      if (this.method == ParseHttpRequest.Method.GET) {}
      for (paramProgressCallback = "Download from";; paramProgressCallback = "Upload to") {
        return Task.forError(new ParseException(100, String.format("%s S3 failed. %s", new Object[] { paramProgressCallback, paramParseHttpResponse.getReasonPhrase() })));
      }
    }
    Task.call(new Callable()
    {
      public Void call()
        throws Exception
      {
        long l3 = paramParseHttpResponse.getTotalSize();
        long l1 = 0L;
        Object localObject = null;
        try
        {
          InputStream localInputStream1 = paramParseHttpResponse.getContent();
          localObject = localInputStream1;
          FileOutputStream localFileOutputStream = ParseFileUtils.openOutputStream(ParseAWSRequest.this.tempFile);
          localObject = localInputStream1;
          byte[] arrayOfByte = new byte[32768];
          for (;;)
          {
            localObject = localInputStream1;
            int i = localInputStream1.read(arrayOfByte, 0, arrayOfByte.length);
            if (i == -1) {
              break;
            }
            localObject = localInputStream1;
            localFileOutputStream.write(arrayOfByte, 0, i);
            long l2 = l1 + i;
            l1 = l2;
            localObject = localInputStream1;
            if (paramProgressCallback != null)
            {
              l1 = l2;
              if (l3 != -1L)
              {
                localObject = localInputStream1;
                i = Math.round((float)l2 / (float)l3 * 100.0F);
                localObject = localInputStream1;
                paramProgressCallback.done(Integer.valueOf(i));
                l1 = l2;
              }
            }
          }
          ParseIOUtils.closeQuietly(localInputStream2);
        }
        finally
        {
          ParseIOUtils.closeQuietly((InputStream)localObject);
        }
        return null;
      }
    }, ParseExecutors.io());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseAWSRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */