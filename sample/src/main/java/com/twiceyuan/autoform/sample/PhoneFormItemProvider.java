package com.twiceyuan.autoform.sample;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.twiceyuan.autoform.FormItemEntity;
import com.twiceyuan.autoform.ResultWatcher;
import com.twiceyuan.autoform.provider.FormItemProvider;

/**
 * Created by twiceYuan on 2017/7/3.
 *
 * 手机号输入框
 */
public class PhoneFormItemProvider extends FormItemProvider {

    private EditText mEtPhone;

    @Override
    public int layoutId() {
        return R.layout.phone_form_item;
    }

    @Override
    public void bindData(FormItemEntity field) {
        mEtPhone.setHint(field.hint);
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
    }

    public EditText getEtPhone() {
        return mEtPhone;
    }
}
