package com.example.bagmore.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.Fragment;

import com.example.bagmore.R;
import com.google.android.material.button.MaterialButton;


public class InformationFragment extends Fragment {


    //region init UI component
    AppCompatRadioButton rbS, rbM, rbL, rbXL, rbXXL, rbw, rbb, rbr, rbp, rbbr;
    MaterialButton btnMinus, btnPlus;
    EditText edtQuantity;
    //endregion

    private int quantity = 1;

    public InformationFragment() {

    }


    public static InformationFragment newInstance(String param1, String param2) {
        InformationFragment fragment = new InformationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_information, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //region mapping UI
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
    }

    //region logic handler

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
            }
        });

        // quantity edittext
        TextWatcher quantityWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtQuantity.getText().toString() == null
                        || edtQuantity.getText().toString().isEmpty()
                        || Integer.valueOf(edtQuantity.getText().toString()) == 0) {
                    quantity = 1;
                    btnMinus.setEnabled(false);
                    edtQuantity.setText(String.valueOf(quantity));
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

    //endregion
}