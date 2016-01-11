package com.optimizely.Preview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.optimizely.utils.OptimizelyConstants;

abstract class OverlayContentView
{
  @NonNull
  private static final Paint sBORDER_PAINT = new Paint();
  private final Context context;
  private final OverlayNavigationViewPager navigationItem;
  
  static
  {
    sBORDER_PAINT.setColor(OptimizelyConstants.OPT_BRIGHT_BLUE);
    sBORDER_PAINT.setStrokeWidth(2.0F);
  }
  
  OverlayContentView(@NonNull Context paramContext, @NonNull OverlayNavigationViewPager paramOverlayNavigationViewPager)
  {
    this.context = paramContext;
    this.navigationItem = paramOverlayNavigationViewPager;
  }
  
  @NonNull
  public abstract View getRootView();
  
  @NonNull
  public abstract String getTitle();
  
  @NonNull
  TextView makeContentTextView(@NonNull String paramString, boolean paramBoolean)
  {
    TextView localTextView = new TextView(this.context);
    localTextView.setText(paramString);
    if (paramBoolean)
    {
      localTextView.setTextAppearance(this.context, 16973892);
      localTextView.setPadding(0, 0, 0, 24);
    }
    for (;;)
    {
      localTextView.setTextColor(-1);
      return localTextView;
      localTextView.setPadding(0, 0, 0, 54);
    }
  }
  
  View makeNavigationLink(@NonNull String paramString, @NonNull final OverlayContentView paramOverlayContentView)
  {
    LinearLayout local1 = new LinearLayout(this.context)
    {
      protected void onDraw(@NonNull Canvas paramAnonymousCanvas)
      {
        super.onDraw(paramAnonymousCanvas);
        paramAnonymousCanvas.drawLine(0.0F, 0.0F, getWidth(), 0.0F, OverlayContentView.sBORDER_PAINT);
        paramAnonymousCanvas.drawLine(0.0F, getHeight(), getWidth(), getHeight(), OverlayContentView.sBORDER_PAINT);
      }
    };
    local1.setWillNotDraw(false);
    local1.setOrientation(0);
    local1.setGravity(16);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(0, -2, 0.9F);
    TextView localTextView = new TextView(this.context);
    localTextView.setText(paramString);
    localTextView.setTextAppearance(this.context, 16973892);
    localTextView.setTextColor(-1);
    local1.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        OverlayContentView.this.navigationItem.pushPage(paramOverlayContentView);
      }
    });
    localTextView.setPadding(36, 36, 36, 36);
    local1.addView(localTextView, localLayoutParams);
    paramString = new SvgPathView(this.context, OptimizelyConstants.OPT_BRIGHT_BLUE, "M 10 6 L 8.59 7.41 13.17 12 l -4.58 4.59 L 10 18 l 6 -6 z");
    paramString.setPadding(2, 36, 2, 36);
    local1.addView(paramString, new LinearLayout.LayoutParams(0, -1, 0.1F));
    return local1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Preview/OverlayContentView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */