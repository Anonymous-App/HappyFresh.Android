package io.fabric.sdk.android.services.persistence;

public abstract interface SerializationStrategy<T>
{
  public abstract T deserialize(String paramString);
  
  public abstract String serialize(T paramT);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/persistence/SerializationStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */