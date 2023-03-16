package com.example.bagmore.Models.json.response;

import com.example.bagmore.Models.data.ProductViewModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonProductViewModel {

    @SerializedName("success")
    private boolean success;
    @SerializedName("sortby")
    private String sortBy;
    @SerializedName("filterByCategory")
    private List<Integer> filterByCategory;
    @SerializedName("filterByColor")
    private List<Integer> filterByColor;
    @SerializedName("filterBySize")
    private List<Integer> filterBySize;
    @SerializedName("data")
    private List<ProductViewModel> productViewModels;

    public JsonProductViewModel() {
    }

    public JsonProductViewModel(boolean success, String sortBy, List<Integer> filterByCategory, List<Integer> filterByColor, List<Integer> filterBySize, List<ProductViewModel> productViewModels) {
        this.success = success;
        this.sortBy = sortBy;
        this.filterByCategory = filterByCategory;
        this.filterByColor = filterByColor;
        this.filterBySize = filterBySize;
        this.productViewModels = productViewModels;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public List<Integer> getFilterByCategory() {
        return filterByCategory;
    }

    public void setFilterByCategory(List<Integer> filterByCategory) {
        this.filterByCategory = filterByCategory;
    }

    public List<Integer> getFilterByColor() {
        return filterByColor;
    }

    public void setFilterByColor(List<Integer> filterByColor) {
        this.filterByColor = filterByColor;
    }

    public List<Integer> getFilterBySize() {
        return filterBySize;
    }

    public void setFilterBySize(List<Integer> filterBySize) {
        this.filterBySize = filterBySize;
    }

    public List<ProductViewModel> getProductViewModels() {
        return productViewModels;
    }

    public void setProductViewModels(List<ProductViewModel> productViewModels) {
        this.productViewModels = productViewModels;
    }
}
