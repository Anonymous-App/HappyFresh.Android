package com.facebook.share.internal;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.internal.WorkQueue;
import com.facebook.internal.WorkQueue.WorkItem;
import com.facebook.share.Sharer.Result;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class VideoUploader
{
  private static final String ERROR_BAD_SERVER_RESPONSE = "Unexpected error in server response";
  private static final String ERROR_UPLOAD = "Video upload failed";
  private static final int MAX_RETRIES_PER_PHASE = 2;
  private static final String PARAM_DESCRIPTION = "description";
  private static final String PARAM_END_OFFSET = "end_offset";
  private static final String PARAM_FILE_SIZE = "file_size";
  private static final String PARAM_REF = "ref";
  private static final String PARAM_SESSION_ID = "upload_session_id";
  private static final String PARAM_START_OFFSET = "start_offset";
  private static final String PARAM_TITLE = "title";
  private static final String PARAM_UPLOAD_PHASE = "upload_phase";
  private static final String PARAM_VALUE_UPLOAD_FINISH_PHASE = "finish";
  private static final String PARAM_VALUE_UPLOAD_START_PHASE = "start";
  private static final String PARAM_VALUE_UPLOAD_TRANSFER_PHASE = "transfer";
  private static final String PARAM_VIDEO_FILE_CHUNK = "video_file_chunk";
  private static final String PARAM_VIDEO_ID = "video_id";
  private static final int RETRY_DELAY_BACK_OFF_FACTOR = 3;
  private static final int RETRY_DELAY_UNIT_MS = 5000;
  private static final String TAG = "VideoUploader";
  private static final int UPLOAD_QUEUE_MAX_CONCURRENT = 8;
  private static AccessTokenTracker accessTokenTracker;
  private static Handler handler;
  private static boolean initialized;
  private static Set<UploadContext> pendingUploads = new HashSet();
  private static WorkQueue uploadQueue = new WorkQueue(8);
  
  private static void cancelAllRequests()
  {
    try
    {
      Iterator localIterator = pendingUploads.iterator();
      while (localIterator.hasNext()) {
        ((UploadContext)localIterator.next()).isCanceled = true;
      }
    }
    finally {}
  }
  
  private static void enqueueRequest(UploadContext paramUploadContext, Runnable paramRunnable)
  {
    try
    {
      paramUploadContext.workItem = uploadQueue.addActiveWorkItem(paramRunnable);
      return;
    }
    finally
    {
      paramUploadContext = finally;
      throw paramUploadContext;
    }
  }
  
  private static void enqueueUploadChunk(UploadContext paramUploadContext, String paramString1, String paramString2, int paramInt)
  {
    enqueueRequest(paramUploadContext, new TransferChunkWorkItem(paramUploadContext, paramString1, paramString2, paramInt));
  }
  
  private static void enqueueUploadFinish(UploadContext paramUploadContext, int paramInt)
  {
    enqueueRequest(paramUploadContext, new FinishUploadWorkItem(paramUploadContext, paramInt));
  }
  
  private static void enqueueUploadStart(UploadContext paramUploadContext, int paramInt)
  {
    enqueueRequest(paramUploadContext, new StartUploadWorkItem(paramUploadContext, paramInt));
  }
  
  private static byte[] getChunk(UploadContext paramUploadContext, String paramString1, String paramString2)
    throws IOException
  {
    if (!Utility.areObjectsEqual(paramString1, paramUploadContext.chunkStart))
    {
      logError(null, "Error reading video chunk. Expected chunk '%s'. Requested chunk '%s'.", new Object[] { paramUploadContext.chunkStart, paramString1 });
      return null;
    }
    long l = Long.parseLong(paramString1);
    int i = (int)(Long.parseLong(paramString2) - l);
    paramString1 = new ByteArrayOutputStream();
    byte[] arrayOfByte = new byte[Math.min(8192, i)];
    int k;
    int j;
    do
    {
      k = paramUploadContext.videoStream.read(arrayOfByte);
      if (k != -1)
      {
        paramString1.write(arrayOfByte, 0, k);
        j = i - k;
        if (j != 0) {}
      }
      else
      {
        paramUploadContext.chunkStart = paramString2;
        return paramString1.toByteArray();
      }
      i = j;
    } while (j >= 0);
    logError(null, "Error reading video chunk. Expected buffer length - '%d'. Actual - '%d'.", new Object[] { Integer.valueOf(j + k), Integer.valueOf(k) });
    return null;
  }
  
  private static Handler getHandler()
  {
    try
    {
      if (handler == null) {
        handler = new Handler(Looper.getMainLooper());
      }
      Handler localHandler = handler;
      return localHandler;
    }
    finally {}
  }
  
  private static void issueResponse(UploadContext paramUploadContext, FacebookException paramFacebookException, String paramString)
  {
    removePendingUpload(paramUploadContext);
    Utility.closeQuietly(paramUploadContext.videoStream);
    if (paramUploadContext.callback != null)
    {
      if (paramFacebookException != null) {
        ShareInternalUtility.invokeOnErrorCallback(paramUploadContext.callback, paramFacebookException);
      }
    }
    else {
      return;
    }
    if (paramUploadContext.isCanceled)
    {
      ShareInternalUtility.invokeOnCancelCallback(paramUploadContext.callback);
      return;
    }
    ShareInternalUtility.invokeOnSuccessCallback(paramUploadContext.callback, paramString);
  }
  
  private static void logError(Exception paramException, String paramString, Object... paramVarArgs)
  {
    Log.e("VideoUploader", String.format(Locale.ROOT, paramString, paramVarArgs), paramException);
  }
  
  private static void registerAccessTokenTracker()
  {
    accessTokenTracker = new AccessTokenTracker()
    {
      protected void onCurrentAccessTokenChanged(AccessToken paramAnonymousAccessToken1, AccessToken paramAnonymousAccessToken2)
      {
        if (paramAnonymousAccessToken1 == null) {}
        while ((paramAnonymousAccessToken2 != null) && (Utility.areObjectsEqual(paramAnonymousAccessToken2.getUserId(), paramAnonymousAccessToken1.getUserId()))) {
          return;
        }
        VideoUploader.access$200();
      }
    };
  }
  
  private static void removePendingUpload(UploadContext paramUploadContext)
  {
    try
    {
      pendingUploads.remove(paramUploadContext);
      return;
    }
    finally
    {
      paramUploadContext = finally;
      throw paramUploadContext;
    }
  }
  
  public static void uploadAsync(ShareVideoContent paramShareVideoContent, FacebookCallback<Sharer.Result> paramFacebookCallback)
    throws FileNotFoundException
  {
    try
    {
      uploadAsync(paramShareVideoContent, "me", paramFacebookCallback);
      return;
    }
    finally
    {
      paramShareVideoContent = finally;
      throw paramShareVideoContent;
    }
  }
  
  public static void uploadAsync(ShareVideoContent paramShareVideoContent, String paramString, FacebookCallback<Sharer.Result> paramFacebookCallback)
    throws FileNotFoundException
  {
    try
    {
      if (!initialized)
      {
        registerAccessTokenTracker();
        initialized = true;
      }
      Validate.notNull(paramShareVideoContent, "videoContent");
      Validate.notNull(paramString, "targetId");
      ShareVideo localShareVideo = paramShareVideoContent.getVideo();
      Validate.notNull(localShareVideo, "videoContent.video");
      Validate.notNull(localShareVideo.getLocalUrl(), "videoContent.video.localUrl");
      paramShareVideoContent = new UploadContext(paramShareVideoContent, paramString, paramFacebookCallback, null);
      paramShareVideoContent.initialize();
      pendingUploads.add(paramShareVideoContent);
      enqueueUploadStart(paramShareVideoContent, 0);
      return;
    }
    finally {}
  }
  
  private static class FinishUploadWorkItem
    extends VideoUploader.UploadWorkItemBase
  {
    static final Set<Integer> transientErrorCodes = new HashSet() {};
    
    public FinishUploadWorkItem(VideoUploader.UploadContext paramUploadContext, int paramInt)
    {
      super(paramInt);
    }
    
    protected void enqueueRetry(int paramInt)
    {
      VideoUploader.enqueueUploadFinish(this.uploadContext, paramInt);
    }
    
    public Bundle getParameters()
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("upload_phase", "finish");
      localBundle.putString("upload_session_id", this.uploadContext.sessionId);
      Utility.putNonEmptyString(localBundle, "title", this.uploadContext.title);
      Utility.putNonEmptyString(localBundle, "description", this.uploadContext.description);
      Utility.putNonEmptyString(localBundle, "ref", this.uploadContext.ref);
      return localBundle;
    }
    
    protected Set<Integer> getTransientErrorCodes()
    {
      return transientErrorCodes;
    }
    
    protected void handleError(FacebookException paramFacebookException)
    {
      VideoUploader.logError(paramFacebookException, "Video '%s' failed to finish uploading", new Object[] { this.uploadContext.videoId });
      endUploadWithFailure(paramFacebookException);
    }
    
    protected void handleSuccess(JSONObject paramJSONObject)
      throws JSONException
    {
      if (paramJSONObject.getBoolean("success"))
      {
        issueResponseOnMainThread(null, this.uploadContext.videoId);
        return;
      }
      handleError(new FacebookException("Unexpected error in server response"));
    }
  }
  
  private static class StartUploadWorkItem
    extends VideoUploader.UploadWorkItemBase
  {
    static final Set<Integer> transientErrorCodes = new HashSet() {};
    
    public StartUploadWorkItem(VideoUploader.UploadContext paramUploadContext, int paramInt)
    {
      super(paramInt);
    }
    
    protected void enqueueRetry(int paramInt)
    {
      VideoUploader.enqueueUploadStart(this.uploadContext, paramInt);
    }
    
    public Bundle getParameters()
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("upload_phase", "start");
      localBundle.putLong("file_size", this.uploadContext.videoSize);
      return localBundle;
    }
    
    protected Set<Integer> getTransientErrorCodes()
    {
      return transientErrorCodes;
    }
    
    protected void handleError(FacebookException paramFacebookException)
    {
      VideoUploader.logError(paramFacebookException, "Error starting video upload", new Object[0]);
      endUploadWithFailure(paramFacebookException);
    }
    
    protected void handleSuccess(JSONObject paramJSONObject)
      throws JSONException
    {
      this.uploadContext.sessionId = paramJSONObject.getString("upload_session_id");
      this.uploadContext.videoId = paramJSONObject.getString("video_id");
      String str = paramJSONObject.getString("start_offset");
      paramJSONObject = paramJSONObject.getString("end_offset");
      VideoUploader.enqueueUploadChunk(this.uploadContext, str, paramJSONObject, 0);
    }
  }
  
  private static class TransferChunkWorkItem
    extends VideoUploader.UploadWorkItemBase
  {
    static final Set<Integer> transientErrorCodes = new HashSet() {};
    private String chunkEnd;
    private String chunkStart;
    
    public TransferChunkWorkItem(VideoUploader.UploadContext paramUploadContext, String paramString1, String paramString2, int paramInt)
    {
      super(paramInt);
      this.chunkStart = paramString1;
      this.chunkEnd = paramString2;
    }
    
    protected void enqueueRetry(int paramInt)
    {
      VideoUploader.enqueueUploadChunk(this.uploadContext, this.chunkStart, this.chunkEnd, paramInt);
    }
    
    public Bundle getParameters()
      throws IOException
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("upload_phase", "transfer");
      localBundle.putString("upload_session_id", this.uploadContext.sessionId);
      localBundle.putString("start_offset", this.chunkStart);
      byte[] arrayOfByte = VideoUploader.getChunk(this.uploadContext, this.chunkStart, this.chunkEnd);
      if (arrayOfByte != null)
      {
        localBundle.putByteArray("video_file_chunk", arrayOfByte);
        return localBundle;
      }
      throw new FacebookException("Error reading video");
    }
    
    protected Set<Integer> getTransientErrorCodes()
    {
      return transientErrorCodes;
    }
    
    protected void handleError(FacebookException paramFacebookException)
    {
      VideoUploader.logError(paramFacebookException, "Error uploading video '%s'", new Object[] { this.uploadContext.videoId });
      endUploadWithFailure(paramFacebookException);
    }
    
    protected void handleSuccess(JSONObject paramJSONObject)
      throws JSONException
    {
      String str = paramJSONObject.getString("start_offset");
      paramJSONObject = paramJSONObject.getString("end_offset");
      if (Utility.areObjectsEqual(str, paramJSONObject))
      {
        VideoUploader.enqueueUploadFinish(this.uploadContext, 0);
        return;
      }
      VideoUploader.enqueueUploadChunk(this.uploadContext, str, paramJSONObject, 0);
    }
  }
  
  private static class UploadContext
  {
    public final AccessToken accessToken = AccessToken.getCurrentAccessToken();
    public final FacebookCallback<Sharer.Result> callback;
    public String chunkStart = "0";
    public final String description;
    public boolean isCanceled;
    public final String ref;
    public String sessionId;
    public final String targetId;
    public final String title;
    public String videoId;
    public long videoSize;
    public InputStream videoStream;
    public final Uri videoUri;
    public WorkQueue.WorkItem workItem;
    
    private UploadContext(ShareVideoContent paramShareVideoContent, String paramString, FacebookCallback<Sharer.Result> paramFacebookCallback)
    {
      this.videoUri = paramShareVideoContent.getVideo().getLocalUrl();
      this.title = paramShareVideoContent.getContentTitle();
      this.description = paramShareVideoContent.getContentDescription();
      this.ref = paramShareVideoContent.getRef();
      this.targetId = paramString;
      this.callback = paramFacebookCallback;
    }
    
    private void initialize()
      throws FileNotFoundException
    {
      Object localObject5 = null;
      Object localObject6 = null;
      ParcelFileDescriptor localParcelFileDescriptor = null;
      Object localObject2 = localObject5;
      Object localObject1 = localObject6;
      try
      {
        if (Utility.isFileUri(this.videoUri))
        {
          localObject2 = localObject5;
          localObject1 = localObject6;
          localParcelFileDescriptor = ParcelFileDescriptor.open(new File(this.videoUri.getPath()), 268435456);
          localObject2 = localParcelFileDescriptor;
          localObject1 = localParcelFileDescriptor;
          this.videoSize = localParcelFileDescriptor.getStatSize();
          localObject2 = localParcelFileDescriptor;
          localObject1 = localParcelFileDescriptor;
        }
        for (this.videoStream = new ParcelFileDescriptor.AutoCloseInputStream(localParcelFileDescriptor);; this.videoStream = FacebookSdk.getApplicationContext().getContentResolver().openInputStream(this.videoUri))
        {
          Utility.closeQuietly(localParcelFileDescriptor);
          return;
          localObject2 = localObject5;
          localObject1 = localObject6;
          if (!Utility.isContentUri(this.videoUri)) {
            break;
          }
          localObject2 = localObject5;
          localObject1 = localObject6;
          this.videoSize = Utility.getContentSize(this.videoUri);
          localObject2 = localObject5;
          localObject1 = localObject6;
        }
        Object localObject4 = localObject5;
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        localObject1 = localObject2;
        Utility.closeQuietly(this.videoStream);
        localObject1 = localObject2;
        throw localFileNotFoundException;
      }
      finally
      {
        Utility.closeQuietly((Closeable)localObject1);
      }
      localObject1 = localObject6;
      throw new FacebookException("Uri must be a content:// or file:// uri");
    }
  }
  
  private static abstract class UploadWorkItemBase
    implements Runnable
  {
    protected int completedRetries;
    protected VideoUploader.UploadContext uploadContext;
    
    protected UploadWorkItemBase(VideoUploader.UploadContext paramUploadContext, int paramInt)
    {
      this.uploadContext = paramUploadContext;
      this.completedRetries = paramInt;
    }
    
    private boolean attemptRetry(int paramInt)
    {
      if ((this.completedRetries < 2) && (getTransientErrorCodes().contains(Integer.valueOf(paramInt))))
      {
        paramInt = (int)Math.pow(3.0D, this.completedRetries);
        VideoUploader.access$800().postDelayed(new Runnable()
        {
          public void run()
          {
            VideoUploader.UploadWorkItemBase.this.enqueueRetry(VideoUploader.UploadWorkItemBase.this.completedRetries + 1);
          }
        }, paramInt * 5000);
        return true;
      }
      return false;
    }
    
    protected void endUploadWithFailure(FacebookException paramFacebookException)
    {
      issueResponseOnMainThread(paramFacebookException, null);
    }
    
    protected abstract void enqueueRetry(int paramInt);
    
    protected void executeGraphRequestSynchronously(Bundle paramBundle)
    {
      paramBundle = new GraphRequest(this.uploadContext.accessToken, String.format(Locale.ROOT, "%s/videos", new Object[] { this.uploadContext.targetId }), paramBundle, HttpMethod.POST, null).executeAndWait();
      if (paramBundle != null)
      {
        FacebookRequestError localFacebookRequestError = paramBundle.getError();
        JSONObject localJSONObject = paramBundle.getJSONObject();
        if (localFacebookRequestError != null)
        {
          if (!attemptRetry(localFacebookRequestError.getSubErrorCode())) {
            handleError(new FacebookGraphResponseException(paramBundle, "Video upload failed"));
          }
          return;
        }
        if (localJSONObject != null) {
          try
          {
            handleSuccess(localJSONObject);
            return;
          }
          catch (JSONException paramBundle)
          {
            endUploadWithFailure(new FacebookException("Unexpected error in server response", paramBundle));
            return;
          }
        }
        handleError(new FacebookException("Unexpected error in server response"));
        return;
      }
      handleError(new FacebookException("Unexpected error in server response"));
    }
    
    protected abstract Bundle getParameters()
      throws Exception;
    
    protected abstract Set<Integer> getTransientErrorCodes();
    
    protected abstract void handleError(FacebookException paramFacebookException);
    
    protected abstract void handleSuccess(JSONObject paramJSONObject)
      throws JSONException;
    
    protected void issueResponseOnMainThread(final FacebookException paramFacebookException, final String paramString)
    {
      VideoUploader.access$800().post(new Runnable()
      {
        public void run()
        {
          VideoUploader.issueResponse(VideoUploader.UploadWorkItemBase.this.uploadContext, paramFacebookException, paramString);
        }
      });
    }
    
    public void run()
    {
      if (!this.uploadContext.isCanceled) {
        try
        {
          executeGraphRequestSynchronously(getParameters());
          return;
        }
        catch (FacebookException localFacebookException)
        {
          endUploadWithFailure(localFacebookException);
          return;
        }
        catch (Exception localException)
        {
          endUploadWithFailure(new FacebookException("Video upload failed", localException));
          return;
        }
      }
      endUploadWithFailure(null);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/internal/VideoUploader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */