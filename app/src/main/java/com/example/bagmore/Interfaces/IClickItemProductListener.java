package com.example.bagmore.Interfaces;

import com.example.bagmore.Models.data.ProductViewModel;

public interface IClickItemProductListener {
    void OnClickImageView(ProductViewModel product);
    void OnClickAddFavoriteButton(ProductViewModel product);
    void OnClickRemoveFavoriteButton(ProductViewModel product);
}
