package com.twiceyuan.autoform.provider;

import android.text.TextUtils;

/**
 * Created by twiceYuan on 2017/7/2.
 *
 * 非空验证器
 */
public class NonNullValidator implements FormItemValidator {
    @Override
    public boolean validate(Object result) {
        if (result instanceof String) {
            return !TextUtils.isEmpty((CharSequence) result);
        } else {
            return result != null;
        }
    }
}