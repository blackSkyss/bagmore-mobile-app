package com.example.bagmore.Models.data;

public class ProductImageViewModel {
    public byte[] Source;

    public ProductImageViewModel(byte[] source) {
        Source = source;
    }

    public byte[] getSource() {
        return Source;
    }

    public void setSource(byte[] source) {
        Source = source;
    }
}
