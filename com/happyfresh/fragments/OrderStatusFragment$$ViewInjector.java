package com.happyfresh.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class OrderStatusFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, OrderStatusFragment paramOrderStatusFragment, Object paramObject)
  {
    paramOrderStatusFragment.orderDetailDate = ((TextView)paramFinder.findRequiredView(paramObject, 2131558796, "field 'orderDetailDate'"));
    paramOrderStatusFragment.orderDetailText = ((TextView)paramFinder.findRequiredView(paramObject, 2131558797, "field 'orderDetailText'"));
    paramOrderStatusFragment.orderNo = ((TextView)paramFinder.findRequiredView(paramObject, 2131558802, "field 'orderNo'"));
    paramOrderStatusFragment.deliveryInfoStockLocation = ((TextView)paramFinder.findRequiredView(paramObject, 2131558804, "field 'deliveryInfoStockLocation'"));
    paramOrderStatusFragment.deliveryInfoName = ((TextView)paramFinder.findRequiredView(paramObject, 2131558806, "field 'deliveryInfoName'"));
    paramOrderStatusFragment.deliveryInfoAddress = ((TextView)paramFinder.findRequiredView(paramObject, 2131558807, "field 'deliveryInfoAddress'"));
    paramOrderStatusFragment.deliveryInfoInstructionView = paramFinder.findRequiredView(paramObject, 2131558808, "field 'deliveryInfoInstructionView'");
    paramOrderStatusFragment.deliveryInfoInstruction = ((TextView)paramFinder.findRequiredView(paramObject, 2131558809, "field 'deliveryInfoInstruction'"));
    paramOrderStatusFragment.statusListView = ((ListView)paramFinder.findRequiredView(paramObject, 2131558798, "field 'statusListView'"));
    paramOrderStatusFragment.itemListView = ((ListView)paramFinder.findRequiredView(paramObject, 2131558813, "field 'itemListView'"));
    paramOrderStatusFragment.itemReplacementListView = ((ListView)paramFinder.findRequiredView(paramObject, 2131558817, "field 'itemReplacementListView'"));
    paramOrderStatusFragment.itemTotalListView = ((ListView)paramFinder.findRequiredView(paramObject, 2131558819, "field 'itemTotalListView'"));
    paramOrderStatusFragment.itemsView = paramFinder.findRequiredView(paramObject, 2131558810, "field 'itemsView'");
    paramOrderStatusFragment.itemsReplacementView = paramFinder.findRequiredView(paramObject, 2131558814, "field 'itemsReplacementView'");
    View localView = paramFinder.findRequiredView(paramObject, 2131558811, "field 'addAllItemToCart' and method 'addItemsToCart'");
    paramOrderStatusFragment.addAllItemToCart = ((TextView)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.addItemsToCart();
      }
    });
    paramOrderStatusFragment.addAllItemToCartDisable = ((TextView)paramFinder.findRequiredView(paramObject, 2131558812, "field 'addAllItemToCartDisable'"));
    localView = paramFinder.findRequiredView(paramObject, 2131558815, "field 'addAllReplacementsToCart' and method 'addReplacementsToCart'");
    paramOrderStatusFragment.addAllReplacementsToCart = ((TextView)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.addReplacementsToCart();
      }
    });
    paramOrderStatusFragment.addAllReplacementsToCartDisable = ((TextView)paramFinder.findRequiredView(paramObject, 2131558816, "field 'addAllReplacementsToCartDisable'"));
    paramOrderStatusFragment.itemTotalContainer = paramFinder.findRequiredView(paramObject, 2131558818, "field 'itemTotalContainer'");
    localView = paramFinder.findRequiredView(paramObject, 2131558800, "field 'editOrderButton' and method 'editOrder'");
    paramOrderStatusFragment.editOrderButton = ((Button)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.editOrder();
      }
    });
    paramOrderStatusFragment.editOrderContainer = paramFinder.findRequiredView(paramObject, 2131558799, "field 'editOrderContainer'");
  }
  
  public static void reset(OrderStatusFragment paramOrderStatusFragment)
  {
    paramOrderStatusFragment.orderDetailDate = null;
    paramOrderStatusFragment.orderDetailText = null;
    paramOrderStatusFragment.orderNo = null;
    paramOrderStatusFragment.deliveryInfoStockLocation = null;
    paramOrderStatusFragment.deliveryInfoName = null;
    paramOrderStatusFragment.deliveryInfoAddress = null;
    paramOrderStatusFragment.deliveryInfoInstructionView = null;
    paramOrderStatusFragment.deliveryInfoInstruction = null;
    paramOrderStatusFragment.statusListView = null;
    paramOrderStatusFragment.itemListView = null;
    paramOrderStatusFragment.itemReplacementListView = null;
    paramOrderStatusFragment.itemTotalListView = null;
    paramOrderStatusFragment.itemsView = null;
    paramOrderStatusFragment.itemsReplacementView = null;
    paramOrderStatusFragment.addAllItemToCart = null;
    paramOrderStatusFragment.addAllItemToCartDisable = null;
    paramOrderStatusFragment.addAllReplacementsToCart = null;
    paramOrderStatusFragment.addAllReplacementsToCartDisable = null;
    paramOrderStatusFragment.itemTotalContainer = null;
    paramOrderStatusFragment.editOrderButton = null;
    paramOrderStatusFragment.editOrderContainer = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/OrderStatusFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */