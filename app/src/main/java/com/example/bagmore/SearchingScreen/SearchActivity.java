package com.example.bagmore.SearchingScreen;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bagmore.Fragments.searchs.SearchModelFragment;
import com.example.bagmore.Fragments.searchs.SearchingResultFragment;
import com.example.bagmore.Models.data.CategoryViewModel;
import com.example.bagmore.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    //region init
    private FragmentTransaction fragmentTransaction;

    @BindView(R.id.txtSearch)
    EditText txtSearchValue;

    @BindView(R.id.imgSearchClose)
    ImageView imgClose;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = SearchActivity.this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(SearchActivity.this, R.color.white));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        SearchModelFragment searchModelFragment = new SearchModelFragment();
        SearchingResultFragment searchingResultFragment = new SearchingResultFragment();

        onClickHandler(searchModelFragment, searchingResultFragment);

        if (txtSearchValue.getText().toString().trim().isEmpty()) {
            //init for first time
            replaceFragment(searchModelFragment);
        }
    }

    //region handler event
    private void onClickHandler(SearchModelFragment searchModelFragment, SearchingResultFragment searchingResultFragment) {
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtSearchValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Execute search with search value
                List<CategoryViewModel> result = searchingResultFragment.searchProducts(s.toString());
                //render result searching search value with list results
                searchingResultFragment.setCategories(result);
                replaceFragment(searchingResultFragment);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //replaceFragment();
                if (s.toString().trim().isEmpty()) {
                    replaceFragment(searchModelFragment);
                }
            }
        });
    }
    //endregion

    private void replaceFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameSearchLayout, fragment);
        fragmentTransaction.commit();
    }

}