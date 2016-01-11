package com.optimizely.CodeBlocks;

import android.support.annotation.NonNull;
import android.util.Pair;
import com.optimizely.Core.OptimizelyData;
import com.optimizely.JSON.OptimizelyCodeTest;
import com.optimizely.JSON.OptimizelyExperiment;
import com.optimizely.JSON.OptimizelyVariation;
import com.optimizely.Optimizely;
import com.optimizely.Optimizely.OptimizelyRunningMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OptimizelyCodeBlock
{
  static final String DEFAULT_BRANCH = "default-branch";
  private static final String OPTIMIZELY_CODE_BLOCK_COMPONENT = "OptimizelyCodeBlock";
  @NonNull
  private final String blockName;
  @NonNull
  private final List<String> codeBranches = new ArrayList();
  @NonNull
  private final Map<String, CodeBranch> codeBranchesByNames = new HashMap();
  private String editModeBranchNameToExecute;
  private final Optimizely optimizely;
  
  OptimizelyCodeBlock(@NonNull Optimizely paramOptimizely, @NonNull String paramString, @NonNull String... paramVarArgs)
  {
    this.optimizely = paramOptimizely;
    this.blockName = paramString;
    this.editModeBranchNameToExecute = "default-branch";
    this.codeBranchesByNames.put("default-branch", null);
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      paramOptimizely = paramVarArgs[i];
      this.codeBranchesByNames.put(paramOptimizely, null);
      this.codeBranches.add(paramOptimizely);
      i += 1;
    }
  }
  
  @NonNull
  private OptimizelyCodeBlock setHandlerForDefaultBranch(DefaultCodeBranch paramDefaultCodeBranch)
  {
    this.codeBranchesByNames.put("default-branch", paramDefaultCodeBranch);
    return this;
  }
  
  public void execute(@NonNull DefaultCodeBranch paramDefaultCodeBranch, @NonNull CodeBranch... paramVarArgs)
  {
    setHandlerForDefaultBranch(paramDefaultCodeBranch);
    int i = 0;
    if (i < paramVarArgs.length)
    {
      localObject = paramVarArgs[i];
      if (((CodeBranch)localObject).branchName != null) {
        this.codeBranchesByNames.put(((CodeBranch)localObject).branchName, localObject);
      }
      for (;;)
      {
        i += 1;
        break;
        if (i < this.codeBranches.size()) {
          this.codeBranchesByNames.put(this.codeBranches.get(i), localObject);
        } else {
          this.optimizely.verboseLog(true, "OptimizelyCodeBlock", "Only %d branch names were supplied when declaring this code block, but %d blocks were passed to execute", new Object[] { Integer.valueOf(this.codeBranches.size()), Integer.valueOf(paramVarArgs.length) });
        }
      }
    }
    Object localObject = getBranchNames().iterator();
    while (((Iterator)localObject).hasNext())
    {
      paramVarArgs = (String)((Iterator)localObject).next();
      if (this.codeBranchesByNames.get(paramVarArgs) == null)
      {
        if (paramVarArgs.equals("default-branch")) {}
        for (paramVarArgs = "You must set a default code block using setHandlerForDefaultBranch()";; paramVarArgs = "You must set a handler for the branch named: " + paramVarArgs)
        {
          this.optimizely.verboseLog(true, "OptimizelyCodeBlock", paramVarArgs, new Object[0]);
          break;
        }
      }
    }
    if (Optimizely.getRunningMode() == Optimizely.OptimizelyRunningMode.EDIT)
    {
      this.optimizely.getOptimizelyCodeBlocks().sendCodeBlockToEditor(this);
      paramVarArgs = (CodeBranch)this.codeBranchesByNames.get(this.editModeBranchNameToExecute);
      if (paramVarArgs != null) {
        paramVarArgs.execute();
      }
    }
    do
    {
      return;
      this.optimizely.verboseLog(true, "OptimizelyCodeBlock", "No matching block with name: %s found for block key %s. Executing default block", new Object[] { this.editModeBranchNameToExecute, this.blockName });
      paramDefaultCodeBranch.execute();
      return;
      paramDefaultCodeBranch = findActiveCodeBlock();
    } while (paramDefaultCodeBranch.second == null);
    this.optimizely.getOptimizelyData().addLockedCodeBlock((String)paramDefaultCodeBranch.first);
    ((CodeBranch)paramDefaultCodeBranch.second).execute();
  }
  
  @NonNull
  public Pair<String, CodeBranch> findActiveCodeBlock()
  {
    if (this.optimizely.isActive())
    {
      OptimizelyCodeTest localOptimizelyCodeTest;
      do
      {
        OptimizelyExperiment localOptimizelyExperiment;
        do
        {
          Iterator localIterator = this.optimizely.getOptimizelyData().getRunningExperimentsById().values().iterator();
          Object localObject;
          while (!((Iterator)localObject).hasNext())
          {
            do
            {
              for (;;)
              {
                if (!localIterator.hasNext()) {
                  break label242;
                }
                localOptimizelyExperiment = (OptimizelyExperiment)localIterator.next();
                localObject = localOptimizelyExperiment.getActiveVariation();
                if (localObject != null) {
                  break;
                }
                this.optimizely.verboseLog(true, "OptimizelyCodeBlock", "No active variation for experiment %1$s", new Object[] { localOptimizelyExperiment.getExperimentId() });
              }
              localObject = ((OptimizelyVariation)localObject).getCodeTests();
            } while (localObject == null);
            localObject = ((List)localObject).iterator();
          }
          localOptimizelyCodeTest = (OptimizelyCodeTest)((Iterator)localObject).next();
        } while (!this.blockName.equals(localOptimizelyCodeTest.getBlockName()));
        OptimizelyData.markExperimentAsViewedIfNecessary(localOptimizelyExperiment, this.optimizely);
      } while (!this.codeBranchesByNames.containsKey(localOptimizelyCodeTest.getBlockKey()));
      this.optimizely.verboseLog("OptimizelyCodeBlock", "Running code block branch %s for the block named %s", new Object[] { localOptimizelyCodeTest.getBlockKey(), localOptimizelyCodeTest.getBlockName() });
      return new Pair(localOptimizelyCodeTest.getBlockName(), this.codeBranchesByNames.get(localOptimizelyCodeTest.getBlockKey()));
    }
    this.optimizely.verboseLog(true, "OptimizelyCodeBlock", "Warning: code block %s was evaluated before Optimizely was started.", new Object[] { getBlockName() });
    label242:
    this.optimizely.verboseLog("OptimizelyCodeBlock", "Running default code block branch for block named %s", new Object[] { this.blockName });
    return new Pair("default-branch", this.codeBranchesByNames.get("default-branch"));
  }
  
  public void forceActiveBranch(String paramString)
  {
    this.editModeBranchNameToExecute = paramString;
  }
  
  @NonNull
  public String getBlockName()
  {
    return this.blockName;
  }
  
  @NonNull
  public Set<String> getBranchNames()
  {
    return this.codeBranchesByNames.keySet();
  }
  
  @NonNull
  public String getDefaultBranchName()
  {
    return "default-branch";
  }
  
  boolean hasHandlerForBranch(String paramString)
  {
    return this.codeBranchesByNames.get(paramString) != null;
  }
  
  public static abstract interface Callback
  {
    public abstract void onBranchChange();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/CodeBlocks/OptimizelyCodeBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */