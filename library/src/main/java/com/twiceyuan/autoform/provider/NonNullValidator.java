package com.twiceyuan.autoform.provider;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.twiceyuan.autoform.FormItemEntity;

/**
 * Created by twiceYuan on 2017/7/2.
 * <p>
 * 非空验证器
 */
public class NonNullValidator implements FormItemValidator<SimpleFormItemProvider> {

    @Override
    public void onValidateFailed(FormItemEntity entity, SimpleFormItemProvider provider) {
        EditText editText = provider.getEtContent();
        Context context = editText.getContext();
        editText.requestFocus();
        Toast.makeText(context, entity.label + "不能为空", Toast.LENGTH_SHORT).show();
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