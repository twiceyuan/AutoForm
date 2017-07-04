package com.twiceyuan.autoform.sample;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.FrameLayout;

import com.twiceyuan.autoform.FormManager;

public class MainActivity extends AppCompatActivity {

    private FrameLayout     mForm;
    private FormManager     mFormManager;
    private AppCompatButton mBtnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        mFormManager = FormManager.inject(mForm, DemoForm.class, true);

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mockSubmit();
            }
        });
    }

    private void mockSubmit() {
        if (!mFormManager.validate()) {
            return;
        }

        new AlertDialog.Builder(MainActivity.this)
                .setMessage(String.valueOf(mFormManager.getResult()))
                .setPositiveButton("确定", null)
                .show();
    }

    private void initView() {
        mForm = (FrameLayout) findViewById(R.id.form);
        mBtnSubmit = (AppCompatButton) findViewById(R.id.btn_submit);
    }
}
