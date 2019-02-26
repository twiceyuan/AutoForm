package com.twiceyuan.autoform.sample.form;

import android.widget.EditText;

import com.twiceyuan.autoform.FormItemEntity;
import com.twiceyuan.autoform.provider.Validator;

/**
 * Created by twiceYuan on 2017/7/3.
 *
 * 手机号码验证器
 */
public class PhoneValidator implements Validator<PhoneLayoutProvider> {

    @Override
    public void onValidateFailed(FormItemEntity entity, PhoneLayoutProvider provider) {
        EditText mEtContent = provider.getEtPhone();
        if (mEtContent.length() != 11) {
            mEtContent.requestFocus();
            mEtContent.setError("手机号码必须为 11 位纯数字");
        }
    }

    @Override
    public void validate(Object result, ValidateCallback callback) {
        callback.handleResult(result instanceof String && ((String) result).length() == 11);
    }
}
