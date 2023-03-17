package com.example.bagmore.Interfaces;

import com.example.bagmore.Models.data.CartViewModel;
import com.example.bagmore.Models.data.ItemCartViewModel;
import com.example.bagmore.OrderScreen.CartActivity;

public interface IClickItemCartItem {
    void handlerButtonClick(CartViewModel viewModel);

    void moveTo(CartViewModel viewModel);
}
