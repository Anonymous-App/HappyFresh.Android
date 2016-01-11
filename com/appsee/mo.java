package com.appsee;

class mo
{
  public mo(String paramString)
  {
    this.i = paramString;
  }
  
  public void F()
  {
    if (this.b == 0L) {
      return;
    }
    long l = (System.nanoTime() - this.b) / 1000000L;
    this.b = 0L;
    double d = this.k;
    this.k = (l + d);
    this.G += 1;
  }
  
  public void a()
  {
    this.b = System.nanoTime();
  }
  
  public int i()
  {
    if (this.G == 0) {
      return -1;
    }
    return (int)(this.k / this.G);
  }
  
  public void i()
  {
    this.G = 0;
    this.k = 0;
  }
  
  public void l()
  {
    mc.l(this.i + AppseeBackgroundUploader.i("p\035") + this.G + AppseeBackgroundUploader.i("{\027m\025") + i() + AppseeBackgroundUploader.i("\027=F"), 1);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/mo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */