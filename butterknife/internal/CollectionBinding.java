package butterknife.internal;

final class CollectionBinding
  implements Binding
{
  private final Kind kind;
  private final String name;
  private final boolean required;
  private final String type;
  
  CollectionBinding(String paramString1, String paramString2, Kind paramKind, boolean paramBoolean)
  {
    this.name = paramString1;
    this.type = paramString2;
    this.kind = paramKind;
    this.required = paramBoolean;
  }
  
  public String getDescription()
  {
    return "field '" + this.name + "'";
  }
  
  public Kind getKind()
  {
    return this.kind;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public boolean isRequired()
  {
    return this.required;
  }
  
  static enum Kind
  {
    ARRAY,  LIST;
    
    private Kind() {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/butterknife/internal/CollectionBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */