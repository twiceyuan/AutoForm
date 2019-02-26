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
    public void validate(Object result, ValidateCallback callback) {
        if (result instanceof String) {
            callback.handleResult(!TextUtils.isEmpty((CharSequence) result));
        } else {
            callback.handleResult(result != null);
        }
    }
}