package com.happyfresh.fragments;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.activities.ContactUsActivity;
import com.happyfresh.activities.WebContentActivity;
import com.happyfresh.common.ICartApplication;

public class AboutFragment
  extends BaseFragment
{
  @InjectView(2131558580)
  TextView mVersion;
  
  protected String getScreenName()
  {
    return null;
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    try
    {
      paramBundle = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
      paramBundle = String.format("%s (%s)", new Object[] { paramBundle.versionName, Integer.valueOf(paramBundle.versionCode) });
      this.mVersion.setText(getString(2131165631, new Object[] { paramBundle }));
      return;
    }
    catch (PackageManager.NameNotFoundException paramBundle) {}
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903105, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
  
  @OnClick({2131558578})
  void openContactUs()
  {
    startActivity(new Intent(getActivity(), ContactUsActivity.class));
  }
  
  @OnClick({2131558572})
  void openFaq()
  {
    sendTracker("FAQ");
    Intent localIntent = new Intent(getActivity(), WebContentActivity.class);
    localIntent.putExtra("ABOUT_TITLE", getString(2131165253));
    localIntent.putExtra("ABOUT_URL", String.format("%1s%2s", new Object[] { "https://www.happyfresh.com", "/faq?app=1&c=" + getApplication().getCountryCode() }));
    startActivity(localIntent);
  }
  
  @OnClick({2131558576})
  void openPrivacy()
  {
    sendTracker("Privacy Policy");
    Intent localIntent = new Intent(getActivity(), WebContentActivity.class);
    localIntent.putExtra("ABOUT_TITLE", getString(2131165255));
    localIntent.putExtra("ABOUT_URL", String.format("%1s%2s", new Object[] { "https://www.happyfresh.com", "/privacy?app=1&c=" + getApplication().getCountryCode() }));
    startActivity(localIntent);
  }
  
  @OnClick({2131558574})
  void openToc()
  {
    sendTracker("Terms And Conditions");
    Intent localIntent = new Intent(getActivity(), WebContentActivity.class);
    localIntent.putExtra("ABOUT_TITLE", getString(2131165256));
    localIntent.putExtra("ABOUT_URL", String.format("%1s%2s", new Object[] { "https://www.happyfresh.com", "/terms?app=1&c=" + getApplication().getCountryCode() }));
    startActivity(localIntent);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/AboutFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */