package com.happyfresh.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.activities.FullImageActivity;
import com.happyfresh.models.Image;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.ArrayList;
import java.util.List;

public class ProductImageFragment
  extends Fragment
{
  @InjectView(2131558739)
  ImageView mImageView;
  private List<Image> mImages = new ArrayList();
  private int mPosition;
  
  @OnClick({2131558739})
  void onClick()
  {
    Intent localIntent = new Intent(getActivity(), FullImageActivity.class);
    localIntent.putParcelableArrayListExtra("ICartConstant.KEYS.EXTRAS.PRODUCT_IMAGES", (ArrayList)this.mImages);
    localIntent.putExtra("ICartConstant.KEYS.EXTRAS.PRODUCT_IMAGES_POSITION", this.mPosition);
    startActivity(localIntent);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903130, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mImages = getArguments().getParcelableArrayList("ICartConstant.KEYS.EXTRAS.PRODUCT_IMAGES");
    this.mPosition = getArguments().getInt("ICartConstant.KEYS.EXTRAS.PRODUCT_IMAGES_POSITION");
    paramView = (Image)this.mImages.get(this.mPosition);
    Picasso.with(getActivity()).load(paramView.productUrl).into(this.mImageView);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/ProductImageFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */