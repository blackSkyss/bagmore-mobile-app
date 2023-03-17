package com.example.bagmore.Interfaces;

import com.example.bagmore.Models.data.CartViewModel;

public interface IClickItemCartItem {
    void handlerButtonClick(CartViewModel viewModel);

    void moveTo(CartViewModel viewModel);
}
