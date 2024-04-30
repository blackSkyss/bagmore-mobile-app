package com.example.bagmore.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.bagmore.DetailActivity;
import com.example.bagmore.Helpers.TokenManager;
import com.example.bagmore.Models.data.DescribeProductsViewModel;
import com.example.bagmore.Models.data.ProductDetailViewModel;
import com.example.bagmore.Models.data.TokenRefreshViewModel;
import com.example.bagmore.Models.json.request.JsonAddToCartReq;
import com.example.bagmore.Models.json.request.JsonRefreshTokenReq;
import com.example.bagmore.Models.json.response.JsonLogoutRes;
import com.example.bagmore.Models.json.response.JsonRefreshTokenRes;
import com.example.bagmore.R;
import com.example.bagmore.Repository.CartRepository;
import com.example.bagmore.Repository.UserRepository;
import com.example.bagmore.Services.CartService;
import com.example.bagmore.Services.UserService;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InformationFragment extends Fragment {

    //region init UI component
    AppCompatRadioButton rbS, rbM, rbL, rbXL, rbXXL, rbw, rbb, rbr, rbp, rbbr;
    MaterialButton btnMinus, btnPlus;
    EditText edtQuantity;

    TextView tvName, tvDescription;
    ProductDetailViewModel product;

    private int productId = 0;
    private int quantity = 1;

    private int colorId = 1;
    private int sizeId = 1;

    private String colorName = "";

    private String sizeName = "";

    private boolean acceptAddToCart = false;

    @BindView(R.id.title_bottom_order)
    TextView titleOrder;

    CartService cartService;

    UserService userService;
    //endregion


    public InformationFragment(ProductDetailViewModel product) {
        this.product = product;

    }

    public static InformationFragment newInstance(String param1, String param2, ProductDetailViewModel product) {
        InformationFragment fragment = new InformationFragment(product);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        ButterKnife.bind(this, view);
        changeTitle(colorId, sizeId, quantity);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //region mapping UI
        tvName = view.findViewById(R.id.tv_title_product);
        tvDescription = view.findViewById(R.id.tv_description_product);
        rbS = view.findViewById(R.id.rb_S);
        rbM = view.findViewById(R.id.rb_M);
        rbL = view.findViewById(R.id.rb_L);
        rbXL = view.findViewById(R.id.rb_XL);
        rbXXL = view.findViewById(R.id.rb_XXL);
        rbw = view.findViewById(R.id.rb_w);
        rbb = view.findViewById(R.id.rb_b);
        rbr = view.findViewById(R.id.rb_r);
        rbp = view.findViewById(R.id.rb_p);
        rbbr = view.findViewById(R.id.rb_br);

        btnMinus = view.findViewById(R.id.btn_minus);
        btnPlus = view.findViewById(R.id.btn_plus);
        edtQuantity = view.findViewById(R.id.edt_quantity);

        tvName.setText(product.getName());
        tvDescription.setText(android.text.Html.fromHtml(product.getDescription()));
        //endregion

        //region handler size
        // Set on change for size group
        rbS.setOnCheckedChangeListener(listener_rbs);
        rbM.setOnCheckedChangeListener(listener_rbm);
        rbL.setOnCheckedChangeListener(listener_rbl);
        rbXL.setOnCheckedChangeListener(listener_rbxl);
        rbXXL.setOnCheckedChangeListener(listener_rbxxl);
        //endregion

        //region handler color
        // Set on change for color group
        rbw.setOnCheckedChangeListener(listener_rbw);
        rbb.setOnCheckedChangeListener(listener_rbb);
        rbr.setOnCheckedChangeListener(listener_rbr);
        rbp.setOnCheckedChangeListener(listener_rbp);
        rbbr.setOnCheckedChangeListener(listener_rbbr);
        //endregion

        edtQuantity.setText(String.valueOf(quantity));

        handlerQuantity();

        cartService = CartRepository.getCartService();
        userService = UserRepository.getUserService();

        addToCart();

    }


    //region logic handler size
    // When size S is picked
    CompoundButton.OnCheckedChangeListener listener_rbs = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                rbS.setTextColor(Color.WHITE);
                rbM.setTextColor(Color.BLACK);
                rbL.setTextColor(Color.BLACK);
                rbXL.setTextColor(Color.BLACK);
                rbXXL.setTextColor(Color.BLACK);
                sizeId = 1;
                changeTitle(colorId, sizeId, quantity);
            }
        }
    };

    // When size M is picked
    CompoundButton.OnCheckedChangeListener listener_rbm = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                rbS.setTextColor(Color.BLACK);
                rbM.setTextColor(Color.WHITE);
                rbL.setTextColor(Color.BLACK);
                rbXL.setTextColor(Color.BLACK);
                rbXXL.setTextColor(Color.BLACK);
                sizeId = 2;
                changeTitle(colorId, sizeId, quantity);
            }

        }
    };

    // When size L is picked
    CompoundButton.OnCheckedChangeListener listener_rbl = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                rbS.setTextColor(Color.BLACK);
                rbM.setTextColor(Color.BLACK);
                rbL.setTextColor(Color.WHITE);
                rbXL.setTextColor(Color.BLACK);
                rbXXL.setTextColor(Color.BLACK);
                sizeId = 3;
                changeTitle(colorId, sizeId, quantity);
            }

        }
    };

    // When size XL is picked
    CompoundButton.OnCheckedChangeListener listener_rbxl = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                rbS.setTextColor(Color.BLACK);
                rbM.setTextColor(Color.BLACK);
                rbL.setTextColor(Color.BLACK);
                rbXL.setTextColor(Color.WHITE);
                rbXXL.setTextColor(Color.BLACK);
                sizeId = 4;
                changeTitle(colorId, sizeId, quantity);
            }

        }
    };

    // When size XXL is picked
    CompoundButton.OnCheckedChangeListener listener_rbxxl = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                rbS.setTextColor(Color.BLACK);
                rbM.setTextColor(Color.BLACK);
                rbL.setTextColor(Color.BLACK);
                rbXL.setTextColor(Color.BLACK);
                rbXXL.setTextColor(Color.WHITE);
                sizeId = 5;
                changeTitle(colorId, sizeId, quantity);
            }

        }
    };
    //endregion

    //region logic handler color
    // When white color is picked
    CompoundButton.OnCheckedChangeListener listener_rbw = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                rbw.setTextColor(Color.BLACK);
                rbb.setTextColor(Color.BLACK);
                rbr.setTextColor(getResources().getColor(R.color.red));
                rbp.setTextColor(getResources().getColor(R.color.pink));
                rbbr.setTextColor(getResources().getColor(R.color.brown));
                colorId = 1;
                changeTitle(colorId, sizeId, quantity);
            }

        }
    };

    // When black color is picked
    CompoundButton.OnCheckedChangeListener listener_rbb = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                rbw.setTextColor(Color.WHITE);
                rbb.setTextColor(Color.WHITE);
                rbr.setTextColor(getResources().getColor(R.color.red));
                rbp.setTextColor(getResources().getColor(R.color.pink));
                rbbr.setTextColor(getResources().getColor(R.color.brown));
                colorId = 2;
                changeTitle(colorId, sizeId, quantity);

            }

        }
    };

    CompoundButton.OnCheckedChangeListener listener_rbr = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                rbw.setTextColor(Color.WHITE);
                rbb.setTextColor(Color.BLACK);
                rbr.setTextColor(getResources().getColor(R.color.white));
                rbp.setTextColor(getResources().getColor(R.color.pink));
                rbbr.setTextColor(getResources().getColor(R.color.brown));
                colorId = 3;
                changeTitle(colorId, sizeId, quantity);
            }

        }
    };

    CompoundButton.OnCheckedChangeListener listener_rbp = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                rbw.setTextColor(Color.WHITE);
                rbb.setTextColor(Color.BLACK);
                rbr.setTextColor(getResources().getColor(R.color.red));
                rbp.setTextColor(getResources().getColor(R.color.white));
                rbbr.setTextColor(getResources().getColor(R.color.brown));
                colorId = 4;
                changeTitle(colorId, sizeId, quantity);
            }

        }
    };

    CompoundButton.OnCheckedChangeListener listener_rbbr = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                rbw.setTextColor(Color.WHITE);
                rbb.setTextColor(Color.BLACK);
                rbr.setTextColor(getResources().getColor(R.color.red));
                rbp.setTextColor(getResources().getColor(R.color.pink));
                rbbr.setTextColor(getResources().getColor(R.color.white));
                colorId = 5;
                changeTitle(colorId, sizeId, quantity);
            }

        }
    };

    //endregion

    //region logic handler quantity
    private void handlerQuantity() {
        btnMinus.setEnabled(false);
        // minus button
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity == 1) {
                    btnMinus.setEnabled(false);
                    return;
                }
                quantity -= 1;
                edtQuantity.setText(String.valueOf(quantity));
                changeTitle(colorId, sizeId, quantity);
            }
        });

        // plus button
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity += 1;
                if (quantity > 1) {
                    btnMinus.setEnabled(true);
                }
                edtQuantity.setText(String.valueOf(quantity));
                changeTitle(colorId, sizeId, quantity);
            }
        });

        // quantity edittext
        TextWatcher quantityWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtQuantity.getText().toString() == null || edtQuantity.getText().toString().isEmpty() || Integer.valueOf(edtQuantity.getText().toString()) == 0) {
                    quantity = 1;
                    btnMinus.setEnabled(false);
                    edtQuantity.setText(String.valueOf(quantity));
                    changeTitle(colorId, sizeId, quantity);
                }

                quantity = Integer.parseInt(edtQuantity.getText().toString().trim());
                if (quantity <= 1) {
                    btnMinus.setEnabled(false);
                } else {
                    btnMinus.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        edtQuantity.addTextChangedListener(quantityWatcher);
    }
    //endregion

    //region check color and size
    private boolean checkDescription(int idColor, int idSize) {
        for (DescribeProductsViewModel item : product.getDescribeProducts()) {
            if (item.Color.getId() == idColor && item.Size.getId() == idSize) {
                return true;
            }
        }
        return false;
    }
    //endregion

    //region get price of size and color
    private BigDecimal getPrice(int idColor, int idSize) {
        for (DescribeProductsViewModel item : product.getDescribeProducts()) {
            if (item.Color.getId() == idColor && item.Size.getId() == idSize) {
                return item.getPrice();
            }
        }
        return BigDecimal.valueOf(0);
    }
    //endregion

    //region change title add to cart
    private void changeTitle(int colorId, int sizeId, int quantity) {
        if (checkDescription(colorId, sizeId) == false) {
            acceptAddToCart = false;
            titleOrder.setText("Out of stock");
        } else {
            acceptAddToCart = true;
            BigDecimal total = BigDecimal.valueOf(Double.valueOf(quantity + ""));
            BigDecimal result = total.multiply(getPrice(colorId, sizeId));
            titleOrder.setText("Add \u25cf " + "$" + result.intValue());
        }
    }
    //endregion

    //region handler add to cart
    private void addToCart() {
        titleOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCartAPI();
            }
        });
    }
    //endregion

    //region get name color and size
    private void setNameColorSize(int colorId, int sizeId) {
        for (DescribeProductsViewModel item : product.getDescribeProducts()) {
            if (item.Color.getId() == colorId && item.Size.getId() == sizeId) {
                colorName = item.Color.getName();
                sizeName = item.Size.getName();
            }
        }
    }
    //endregion

    //region notification
    private void pushNotification(String title) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "HAHA")
                .setSmallIcon(R.drawable.ic_success_32px)
                .setContentTitle("Add to cart")
                .setContentText("Product: " + title + " has been added to the cart")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(getNotificationId(), builder.build());
    }


    private int getNotificationId() {
        return (int) new Date().getTime();
    }
    //endregion

    //region call add to cart API
    private void addToCartAPI() {
        if (acceptAddToCart == false) {
            Toast.makeText(getContext(), "Out of stock", Toast.LENGTH_SHORT).show();
            return;
        } else {
            setNameColorSize(colorId, sizeId);
            try {
                TokenManager tokenManager = new TokenManager(getContext());
                productId = product.getId();
                JsonAddToCartReq model = new JsonAddToCartReq(productId, colorName, quantity, sizeName);
                Call<JsonLogoutRes> callAddToCart = cartService.addToCart("bearer " + tokenManager.getAccessToken(), model);
                callAddToCart.enqueue(new Callback<JsonLogoutRes>() {
                    @Override
                    public void onResponse(Call<JsonLogoutRes> call, Response<JsonLogoutRes> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Add successfully", Toast.LENGTH_SHORT).show();
                            pushNotification(product.getName());
                        } else if (response.code() == 401) {
                            Toast.makeText(getContext(), "ReAuthentication", Toast.LENGTH_SHORT).show();
                            refreshTokenAPI();
                        } else {
                            ResponseBody errorBody = response.errorBody();
                            if (errorBody != null) {
                                try {
                                    String errorString = errorBody.string();
                                    Gson gson = new Gson();
                                    JsonObject errorJson = gson.fromJson(errorString, JsonObject.class);
                                    JsonLogoutRes jsonLogoutRes = gson.fromJson(errorJson, JsonLogoutRes.class);
                                    Toast.makeText(getContext(), jsonLogoutRes.getMessage(), Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonLogoutRes> call, Throwable t) {
                        Toast.makeText(getContext(), "Failed to all API", Toast.LENGTH_SHORT).show();

                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    //endregion

    //region refresh token
    private void refreshTokenAPI() {
        try {
            TokenManager tokenManager = new TokenManager(getContext());
            JsonRefreshTokenReq json = new JsonRefreshTokenReq(tokenManager.getAccessToken(), tokenManager.getRefreshToken());
            Call<JsonRefreshTokenRes> result = userService.userRefreshToken(json);
            result.enqueue(new Callback<JsonRefreshTokenRes>() {
                @Override
                public void onResponse(Call<JsonRefreshTokenRes> call, Response<JsonRefreshTokenRes> response) {
                    if (response.isSuccessful()) {
                        JsonRefreshTokenRes jsonRefreshTokenResponse = response.body();
                        TokenRefreshViewModel tokenRefresh = jsonRefreshTokenResponse.getData();
                        tokenManager.clearToken();
                        tokenManager.saveToken(tokenRefresh.getAccessToken(), tokenRefresh.getRefreshToken());
                        addToCartAPI();
                    } else {
                        tokenManager.clearToken();
                        if (getActivity() instanceof DetailActivity) {
                            DetailActivity activity = (DetailActivity) getActivity();
                            activity.navigation();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonRefreshTokenRes> call, Throwable t) {
                    Toast.makeText(getContext(), "Failed to call API", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //endregion

}