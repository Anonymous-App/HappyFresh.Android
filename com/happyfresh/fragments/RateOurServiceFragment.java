package com.happyfresh.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.activities.FeedbackActivity;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.managers.OrderManager;
import com.happyfresh.models.Rating;
import com.happyfresh.utils.GAUtils;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

public class RateOurServiceFragment
  extends BaseFragment
{
  private static final String TAG = RateOurServiceFragment.class.getSimpleName();
  @InjectView(2131558827)
  TextView displayPrice;
  private String number;
  @InjectView(2131558792)
  TextView orderNumber;
  @InjectView(2131558828)
  TextView orderNumber2;
  private String price;
  @InjectView(2131558825)
  ScrollView rateContainer;
  @InjectView(2131558829)
  RatingBar ratingBar;
  @InjectView(2131558702)
  Button submitButton;
  
  public static RateOurServiceFragment newInstance(String paramString1, String paramString2)
  {
    RateOurServiceFragment localRateOurServiceFragment = new RateOurServiceFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("ICartConstant.ORDER_NUMBER", paramString1);
    localBundle.putString("ICartConstant.ORDER_PRICE", paramString2);
    localRateOurServiceFragment.setArguments(localBundle);
    return localRateOurServiceFragment;
  }
  
  protected String getScreenName()
  {
    return "Rate";
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903144, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    this.number = getArguments().getString("ICartConstant.ORDER_NUMBER");
    this.price = getArguments().getString("ICartConstant.ORDER_PRICE");
    this.orderNumber.setText(this.number);
    this.orderNumber2.setText(this.number);
    this.displayPrice.setText(this.price);
    this.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
    {
      public void onRatingChanged(RatingBar paramAnonymousRatingBar, float paramAnonymousFloat, boolean paramAnonymousBoolean)
      {
        if (RateOurServiceFragment.this.submitButton.getVisibility() != 0)
        {
          RateOurServiceFragment.this.submitButton.setVisibility(0);
          new Handler().postDelayed(new Runnable()
          {
            public void run()
            {
              RateOurServiceFragment.this.rateContainer.fullScroll(130);
            }
          }, 50L);
        }
      }
    });
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
  
  @OnClick({2131558702})
  void submitRate()
  {
    try
    {
      final float f = this.ratingBar.getRating();
      Rating localRating = new Rating();
      localRating.stars = Double.valueOf(f);
      getApplication().getOrderManager().rateOrder(this.number, localRating, new ICartCallback(TAG)
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          super.onFailure(paramAnonymousThrowable);
          paramAnonymousThrowable = RateOurServiceFragment.this.getActivity();
          if (paramAnonymousThrowable == null) {
            return;
          }
          paramAnonymousThrowable.finish();
        }
        
        public void onSuccess(Object paramAnonymousObject)
        {
          GAUtils.trackRating(RateOurServiceFragment.this.getApplication(), String.format("%f stars", new Object[] { Float.valueOf(f) }));
          paramAnonymousObject = RateOurServiceFragment.this.getActivity();
          if (paramAnonymousObject == null) {}
          do
          {
            return;
            ((Activity)paramAnonymousObject).finish();
          } while (f > 3.0D);
          paramAnonymousObject = new Intent((Context)paramAnonymousObject, FeedbackActivity.class);
          ((Intent)paramAnonymousObject).putExtra("ICartConstant.ORDER_NUMBER", RateOurServiceFragment.this.number);
          ((Intent)paramAnonymousObject).putExtra("ICartConstant.ORDER_STARS", f);
          RateOurServiceFragment.this.startActivityForResult((Intent)paramAnonymousObject, 14);
        }
      });
      return;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localUnsupportedEncodingException.printStackTrace();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/RateOurServiceFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */