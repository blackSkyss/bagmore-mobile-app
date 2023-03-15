package com.example.bagmore.Models.data;

public class SortingViewModel {
    private String name;
    private boolean isChecked;

    public SortingViewModel() {
    }

    public SortingViewModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
