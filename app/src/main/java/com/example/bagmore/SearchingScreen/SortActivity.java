package com.example.bagmore.SearchingScreen;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bagmore.Adapters.RecyclerViewAdapters.SortingRVAdapter;
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
    //Click Sorting button => get value sort => execute sort => return value => back to home with return value
    private void onClickSorting() {
        tvSorting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    //endregion

    private void setLayout() {
        rcvSorting = findViewById(R.id.rcvSortBag);
        sortingAdapter = new SortingRVAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvSorting.setLayoutManager(linearLayoutManager);

        sortingAdapter.setData(getSortings());
        rcvSorting.setAdapter(sortingAdapter);
    }

    private List<SortingViewModel> getSortings() {
        List<SortingViewModel> sortingList = new ArrayList<>();
        sortingList.add(new SortingViewModel("Name growing"));
        sortingList.add(new SortingViewModel("Name decreasing"));
        sortingList.add(new SortingViewModel("Price growing"));
        sortingList.add(new SortingViewModel("Price decreasing"));

        return sortingList;
    }
}