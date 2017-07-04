package com.twiceyuan.autoform.sample.form;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.twiceyuan.autoform.FormItemEntity;
import com.twiceyuan.autoform.ResultWatcher;
import com.twiceyuan.autoform.provider.LayoutProvider;
import com.twiceyuan.autoform.sample.R;

/**
 * Created by twiceYuan on 2017/7/3.
 * <p>
 * 手机号输入框
 */
public class PhoneLayoutProvider implements LayoutProvider {

    private EditText mEtPhone;
    private TextView mTvLabel;

    @Override
    public int layoutId() {
        return R.layout.form_item_phone;
    }

    @Override
    public void bindData(FormItemEntity field) {
        mEtPhone.setHint(field.hint);
        mTvLabel.setText(field.label);
    }

    @Override
    public void resultWatcher(final ResultWatcher watcher) {
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                watcher.updateResult(mEtPhone.getText().toString());
            }
        });
    }

    @Override
    public void initView(View view) {
        mEtPhone = (EditText) view.findViewById(R.id.et_phone);
        mTvLabel = (TextView) view.findViewById(R.id.tv_label);
    }

    public EditText getEtPhone() {
        return mEtPhone;
    }
}
