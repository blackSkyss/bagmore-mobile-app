package com.example.bagmore.Interfaces;

import com.example.bagmore.Models.data.ItemCartViewModel;

public interface IClickItemCartItem {
    void handlerButtonClick(ItemCartViewModel viewModel);

    void moveTo(ItemCartViewModel viewModel);
}
