package com.optimizely.CodeBlocks;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.optimizely.Optimizely;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OptimizelyCodeBlocks
{
  private static final String OPTIMIZELY_CODE_BLOCKS_COMPONENT = "OptimizelyCodeBlocks";
  @NonNull
  private final Map<String, OptimizelyCodeBlock.Callback> callbacksByBlocks = new HashMap();
  @NonNull
  private final Optimizely optimizely;
  @NonNull
  private final Map<String, OptimizelyCodeBlock> registeredCodeBlocks = new HashMap();
  
  public OptimizelyCodeBlocks(@NonNull Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
  }
  
  @NonNull
  private Map<String, Object> codeBlockToMap(@NonNull OptimizelyCodeBlock paramOptimizelyCodeBlock)
  {
    HashMap localHashMap1 = new HashMap();
    localHashMap1.put("action", "registerCodeTest");
    localHashMap1.put("key", paramOptimizelyCodeBlock.getBlockName());
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("blockKeys", paramOptimizelyCodeBlock.getBranchNames());
    localHashMap1.put("info", localHashMap2);
    return localHashMap1;
  }
  
  @NonNull
  public OptimizelyCodeBlockBuilder blockBuilder(@NonNull String paramString)
  {
    return new OptimizelyCodeBlockBuilder(paramString);
  }
  
  public void forceBranchForBlock(String paramString1, @Nullable String paramString2)
  {
    String str;
    if (paramString2 != null)
    {
      str = paramString2;
      if (!paramString2.isEmpty()) {}
    }
    else
    {
      str = "default-branch";
    }
    this.optimizely.verboseLog("OptimizelyCodeBlocks", "Setting %s as handler for block %s.", new Object[] { str, paramString1 });
    if (this.registeredCodeBlocks.containsKey(paramString1))
    {
      ((OptimizelyCodeBlock)this.registeredCodeBlocks.get(paramString1)).forceActiveBranch(str);
      paramString1 = (OptimizelyCodeBlock.Callback)this.callbacksByBlocks.get(paramString1);
      if (paramString1 != null) {
        paramString1.onBranchChange();
      }
    }
  }
  
  @NonNull
  public OptimizelyCodeBlock makeCodeBlock(@NonNull String paramString, @Nullable OptimizelyCodeBlock.Callback paramCallback, @NonNull String... paramVarArgs)
  {
    if (!this.registeredCodeBlocks.containsKey(paramString))
    {
      paramVarArgs = new OptimizelyCodeBlock(this.optimizely, paramString, paramVarArgs);
      this.registeredCodeBlocks.put(paramString, paramVarArgs);
      sendCodeBlockToEditor(paramVarArgs);
      if (paramCallback != null) {
        this.callbacksByBlocks.put(paramString, paramCallback);
      }
    }
    return (OptimizelyCodeBlock)this.registeredCodeBlocks.get(paramString);
  }
  
  @NonNull
  public OptimizelyCodeBlock makeCodeBlock(@NonNull String paramString, @NonNull String... paramVarArgs)
  {
    return makeCodeBlock(paramString, null, paramVarArgs);
  }
  
  void sendAppHasNoRegisteredCodeBlocks()
  {
    if ((this.optimizely.isActive()) && (this.optimizely.isEditorEnabled().booleanValue()))
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("action", "noCodeBlocks");
      this.optimizely.sendMap(localHashMap);
    }
  }
  
  void sendCodeBlockToEditor(@NonNull OptimizelyCodeBlock paramOptimizelyCodeBlock)
  {
    if ((this.optimizely.isActive()) && (this.optimizely.isEditorEnabled().booleanValue()))
    {
      paramOptimizelyCodeBlock = codeBlockToMap(paramOptimizelyCodeBlock);
      this.optimizely.sendMap(paramOptimizelyCodeBlock);
    }
  }
  
  public void sendCodeBlocks()
  {
    this.optimizely.verboseLog("OptimizelyCodeBlocks", "Sending %1$s", new Object[] { this.registeredCodeBlocks.toString() });
    if (!this.registeredCodeBlocks.isEmpty())
    {
      this.optimizely.socketBatchBegin();
      Iterator localIterator = this.registeredCodeBlocks.values().iterator();
      while (localIterator.hasNext()) {
        sendCodeBlockToEditor((OptimizelyCodeBlock)localIterator.next());
      }
      this.optimizely.socketBatchEnd();
      return;
    }
    sendAppHasNoRegisteredCodeBlocks();
  }
  
  public class OptimizelyCodeBlockBuilder
  {
    @NonNull
    private final String blockName;
    @Nullable
    private OptimizelyCodeBlock.Callback callback;
    
    public OptimizelyCodeBlockBuilder(String paramString)
    {
      this.blockName = paramString;
    }
    
    public OptimizelyCodeBlockBuilder setCallback(@NonNull OptimizelyCodeBlock.Callback paramCallback)
    {
      this.callback = paramCallback;
      return this;
    }
    
    public OptimizelyCodeBlock withBranchNames(@NonNull String... paramVarArgs)
    {
      return OptimizelyCodeBlocks.this.makeCodeBlock(this.blockName, this.callback, paramVarArgs);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/CodeBlocks/OptimizelyCodeBlocks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */