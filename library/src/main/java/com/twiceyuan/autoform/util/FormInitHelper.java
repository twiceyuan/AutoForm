package com.twiceyuan.autoform.util;

import android.text.TextUtils;

import com.twiceyuan.autoform.FormItemEntity;
import com.twiceyuan.autoform.annotations.Form;
import com.twiceyuan.autoform.annotations.FormField;
import com.twiceyuan.autoform.pool.Instances;
import com.twiceyuan.autoform.provider.FormItemProvider;
import com.twiceyuan.autoform.provider.HintProvider;
import com.twiceyuan.autoform.provider.SimpleFormItemProvider;

import java.lang.reflect.Field;

/**
 * Created by twiceYuan on 2017/7/2.
 * <p>
 * 反射相关工具方法
 */
public class FormInitHelper {

    private HintProvider mHintProvider;

    private Class<? extends FormItemProvider> mFormItemProviderClass;

    private FormInitHelper() {
    }

    public static FormInitHelper build(Form form) {
        FormInitHelper helper = new FormInitHelper();

        helper.mHintProvider = Instances.getHintProvider(form.hintProvider());
        helper.mFormItemProviderClass = form.itemProvider();

        return helper;
    }

    public FormItemEntity initFormField(final Field field, final FormField formField) {

        FormItemEntity entity = new FormItemEntity();
        entity.label = formField.label();
        entity.order = formField.order();
        entity.type = formField.type();
        entity.itemProvider = formField.itemProvider();
        entity.validator = formField.validator();

        if (TextUtils.isEmpty(formField.hint())) {
            entity.hint = mHintProvider.buildHint(formField.label());
        } else {
            entity.hint = formField.hint();
        }

        if (TextUtils.isEmpty(formField.key())) {
            entity.key = field.getName();
        } else {
            entity.key = formField.key();
        }

        // 如果 Form 定义了 FormItemProvider 而 Field 为默认的话，则用 Form 级定义的取代 Field 的默认值
        if (mFormItemProviderClass != null && formField.itemProvider() == SimpleFormItemProvider.class) {
            entity.itemProvider = mFormItemProviderClass;
        }

        return entity;
    }

}