package com.happyfresh.fragments;

import butterknife.ButterKnife.Finder;
import com.happyfresh.customs.FullScreenImageView;

public class ImageFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, ImageFragment paramImageFragment, Object paramObject)
  {
    paramImageFragment.mImageView = ((FullScreenImageView)paramFinder.findRequiredView(paramObject, 2131558739, "field 'mImageView'"));
  }
  
  public static void reset(ImageFragment paramImageFragment)
  {
    paramImageFragment.mImageView = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/ImageFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */