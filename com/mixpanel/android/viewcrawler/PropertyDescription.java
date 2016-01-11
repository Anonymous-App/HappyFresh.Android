package com.mixpanel.android.viewcrawler;

class PropertyDescription
{
  public final Caller accessor;
  private final String mMutatorName;
  public final String name;
  public final Class<?> targetClass;
  
  public PropertyDescription(String paramString1, Class<?> paramClass, Caller paramCaller, String paramString2)
  {
    this.name = paramString1;
    this.targetClass = paramClass;
    this.accessor = paramCaller;
    this.mMutatorName = paramString2;
  }
  
  public Caller makeMutator(Object[] paramArrayOfObject)
    throws NoSuchMethodException
  {
    if (this.mMutatorName == null) {
      return null;
    }
    return new Caller(this.targetClass, this.mMutatorName, paramArrayOfObject, Void.TYPE);
  }
  
  public String toString()
  {
    return "[PropertyDescription " + this.name + "," + this.targetClass + ", " + this.accessor + "/" + this.mMutatorName + "]";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/viewcrawler/PropertyDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */