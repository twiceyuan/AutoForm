package com.twiceyuan.autoform.provider;

import android.text.TextUtils;
import android.widget.EditText;

import com.twiceyuan.autoform.FormItemEntity;

/**
 * Created by twiceYuan on 2017/7/2.
 * <p>
 * 非空验证器
 */
public class NonNullValidator implements Validator<TextItemProvider> {

    @Override
    public void onValidateFailed(FormItemEntity entity, TextItemProvider provider) {
        EditText editText = provider.inputArea();
        editText.requestFocus();
        editText.setError(entity.label + "不能为空");
    }

    @Override
    public boolean validate(Object result) {
        if (result instanceof String) {
            return !TextUtils.isEmpty((CharSequence) result);
        } else {
            return result != null;
        }
    }
}