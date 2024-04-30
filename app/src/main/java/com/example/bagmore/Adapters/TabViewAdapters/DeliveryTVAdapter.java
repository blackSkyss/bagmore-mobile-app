package com.example.bagmore.Adapters.TabViewAdapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.bagmore.Fragments.delivery.CanceledFragment;
import com.example.bagmore.Fragments.delivery.ConfirmedFragment;
import com.example.bagmore.Fragments.delivery.ConfirmingFragment;
import com.example.bagmore.Fragments.delivery.DeliveredFragment;
import com.example.bagmore.Fragments.delivery.DeliveryFragment;
import com.example.bagmore.Fragments.delivery.DeliveryNothingFragment;
import com.example.bagmore.Fragments.delivery.ExchangeFragment;
import com.example.bagmore.Fragments.delivery.ReturnFragment;
import com.example.bagmore.Models.data.OrderViewModel;

import java.util.ArrayList;
import java.util.List;

public class DeliveryTVAdapter extends FragmentStatePagerAdapter {
    private List<OrderViewModel> items;
    private List<OrderViewModel> confirming = new ArrayList<>();
    private List<OrderViewModel> confirmed = new ArrayList<>();
    private List<OrderViewModel> delivery = new ArrayList<>();
    private List<OrderViewModel> delivered = new ArrayList<>();
    private List<OrderViewModel> canceled = new ArrayList<>();
    private List<OrderViewModel> returned = new ArrayList<>();
    private List<OrderViewModel> exchange = new ArrayList<>();

    public DeliveryTVAdapter(@NonNull FragmentManager fm, int behavior, List<OrderViewModel> items) {
        super(fm, behavior);
        this.items = items;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                confirmed.clear();
                if (items == null) {
                    return new DeliveryNothingFragment();
                }

                getConfirmed();

                if (confirmed == null || confirmed.size() == 0) {
                    return new DeliveryNothingFragment();
                }
                return new ConfirmedFragment(confirmed);
            case 2:
                delivery.clear();
                if (items == null) {
                    return new DeliveryNothingFragment();
                }

                getDelivery();
                if (delivery == null || delivery.size() == 0) {
                    return new DeliveryNothingFragment();
                }
                return new DeliveryFragment(delivery);
            case 3:
                delivered.clear();
                if (items == null) {
                    return new DeliveryNothingFragment();
                }
                getDelivered();
                if (delivered == null || delivered.size() == 0) {
                    return new DeliveryNothingFragment();
                }
                return new DeliveredFragment(delivered);
            case 4:
                canceled.clear();
                if (items == null) {
                    return new DeliveryNothingFragment();
                }
                getCanceled();
                if (canceled == null || canceled.size() == 0) {
                    return new DeliveryNothingFragment();
                }
                return new CanceledFragment(canceled);
            case 5:
                returned.clear();
                if (items == null) {
                    return new DeliveryNothingFragment();
                }
                getReturn();
                if (returned == null || returned.size() == 0) {
                    return new DeliveryNothingFragment();
                }
                return new ReturnFragment(returned);
            case 6:
                exchange.clear();
                if (items == null) {
                    return new DeliveryNothingFragment();
                }
                getExchange();
                if (exchange == null || exchange.size() == 0) {
                    return new DeliveryNothingFragment();
                }
                return new ExchangeFragment(exchange);

            default:
                confirming.clear();
                if (items == null) {
                    return new DeliveryNothingFragment();
                }
                getConfirming();
                if (confirming == null || confirming.size() == 0) {
                    return new DeliveryNothingFragment();
                }
                return new ConfirmingFragment(confirming);
        }
    }

    //region add to confirming
    private void getConfirming() {
        for (OrderViewModel o : items) {
            if (o.getDeliveryStatus() == 0) {
                confirming.add(o);
            }
        }
    }
    //endregion

    //region add to confirmed
    private void getConfirmed() {
        for (OrderViewModel o : items) {
            if (o.getDeliveryStatus() == 1) {
                confirmed.add(o);
            }
        }
    }
    //endregion

    //region add to delivery
    private void getDelivery() {
        for (OrderViewModel o : items) {
            if (o.getDeliveryStatus() == 2) {
                delivery.add(o);
            }
        }
    }
    //endregion

    //region add to delivered
    private void getDelivered() {
        for (OrderViewModel o : items) {
            if (o.getDeliveryStatus() == 3) {
                delivered.add(o);
            }
        }
    }
    //endregion

    //region add to canceled
    private void getCanceled() {
        for (OrderViewModel o : items) {
            if (o.getDeliveryStatus() == 4) {
                canceled.add(o);
            }
        }
    }
    //endregion

    //region add to return
    private void getReturn() {
        for (OrderViewModel o : items) {
            if (o.getDeliveryStatus() == 5) {
                returned.add(o);
            }
        }
    }
    //endregion

    //region add to exchange
    private void getExchange() {
        for (OrderViewModel o : items) {
            if (o.getDeliveryStatus() == 6) {
                exchange.add(o);
            }
        }
    }
    //endregion

    @Override
    public int getCount() {
        return 7;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Confirming";
                break;
            case 1:
                title = "Confirmed";
                break;
            case 2:
                title = "Delivery";
                break;
            case 3:
                title = "Delivered";
                break;
            case 4:
                title = "Canceled";
                break;
            case 5:
                title = "Return";
                break;
            case 6:
                title = "Exchange";
                break;
        }
        return title;
    }
}
