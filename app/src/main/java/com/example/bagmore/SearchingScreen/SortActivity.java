package com.example.bagmore.SearchingScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bagmore.Adapters.RecyclerViewAdapters.SortingRVAdapter;
import com.example.bagmore.Interfaces.IClickItemSort;
import com.example.bagmore.Models.data.SortingViewModel;
import com.example.bagmore.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SortActivity extends AppCompatActivity {

    //region init
    private RecyclerView rcvSorting;
    private SortingRVAdapter sortingAdapter;
    private ImageView closeSorting;
    private TextView appliedSorting;
    @BindView(R.id.title_bottom_order)
    TextView tvSorting;

    private String keySort = "";
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        ButterKnife.bind(this);

        configTextBottom();
        onClickSorting();

        Window window = SortActivity.this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(SortActivity.this, R.color.white));
        }

        setLayout();

        //Close sort activity => come back to home
        closeSorting = findViewById(R.id.imgCloseSort);
        closeSorting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    //region config text bottom
    private void configTextBottom() {
        tvSorting.setText("SORTING");
    }
    //endregion

    //region handler sorting bottom
    private void onClickSorting() {
        tvSorting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (keySort == "" || keySort.equals("")) {
                    Toast.makeText(SortActivity.this, "Please choose any option", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra("key_sort", keySort);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
    //endregion

    private void setLayout() {
        rcvSorting = findViewById(R.id.rcvSortBag);
        sortingAdapter = new SortingRVAdapter(this, new IClickItemSort() {
            @Override
            public void onClickHandler(SortingViewModel model) {
                if (model.getId() == 1) {
                    keySort = "NAMEASC";
                } else if (model.getId() == 2) {
                    keySort = "NAMEDESC";
                } else if (model.getId() == 3) {
                    keySort = "PRICEASC";
                } else {
                    keySort = "PRICEDESC";
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvSorting.setLayoutManager(linearLayoutManager);

        sortingAdapter.setData(getSortings());
        rcvSorting.setAdapter(sortingAdapter);
    }

    private List<SortingViewModel> getSortings() {
        List<SortingViewModel> sortingList = new ArrayList<>();
        sortingList.add(new SortingViewModel(1, "Name growing"));
        sortingList.add(new SortingViewModel(2, "Name decreasing"));
        sortingList.add(new SortingViewModel(3, "Price growing"));
        sortingList.add(new SortingViewModel(4, "Price decreasing"));

        return sortingList;
    }
}