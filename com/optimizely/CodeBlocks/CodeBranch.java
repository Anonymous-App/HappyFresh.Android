package com.optimizely.CodeBlocks;

public abstract class CodeBranch
{
  public String branchName;
  
  public CodeBranch() {}
  
  public CodeBranch(String paramString)
  {
    this.branchName = paramString;
  }
  
  public abstract void execute();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/CodeBlocks/CodeBranch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */