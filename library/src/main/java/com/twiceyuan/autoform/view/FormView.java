package com.twiceyuan.autoform.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.twiceyuan.autoform.FormItemEntity;
import com.twiceyuan.autoform.ResultWatcher;
import com.twiceyuan.autoform.annotations.Form;
import com.twiceyuan.autoform.annotations.FormField;
import com.twiceyuan.autoform.pool.Instances;
import com.twiceyuan.autoform.provider.FormItemProvider;
import com.twiceyuan.autoform.util.FormInitHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by twiceYuan on 2017/7/3.
 * <p>
 * 表单 View
 */
public class FormView extends LinearLayout {

    private List<FormItemEntity> mFormItems = new ArrayList<>();

    public FormView(Context context) {
        super(context);
        init(context);
    }

    public FormView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FormView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressWarnings("unused")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FormView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @SuppressWarnings("UnusedParameters")
    private void init(Context context) {
        setOrientation(VERTICAL);
    }

    public void inflateForm(Class formClass) {

        initFormFields(formClass);

        inflateFormView();
    }

    private void initFormFields(Class formClass) {
        Form form = (Form) formClass.getAnnotation(Form.class);

        FormInitHelper initHelper = FormInitHelper.build(form);

        // 获取注解了 FormField 的字段，传递到 FormFieldEntity 实体中
        for (Field field : formClass.getDeclaredFields()) {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);

            FormField formField = field.getAnnotation(FormField.class);
            if (formField != null) {
                FormItemEntity formItemEntity = initHelper.initFormField(field, formField);

                mFormItems.add(formItemEntity);
            }

            field.setAccessible(accessible);
        }

        // 根据 order 值排序字段
        Collections.sort(mFormItems, new Comparator<FormItemEntity>() {
            @Override
            public int compare(FormItemEntity f1, FormItemEntity f2) {
                return f1.order - f2.order;
            }
        });
    }

    private void inflateFormView() {
        for (final FormItemEntity formField : mFormItems) {
            FormItemProvider itemProvider = Instances.newInstance(formField.itemProvider);

            // 根据 LayoutId Inflate View
            View formItemView = LayoutInflater.from(getContext()).inflate(itemProvider.layoutId(), this, false);

            // 绑定 ViewProvider 中的控件
            itemProvider.initView(formItemView);

            // 初始化模板数据
            itemProvider.bindData(formField);

            itemProvider.resultWatcher(new ResultWatcher() {
                @Override
                public void updateResult(Object result) {
                    formField.result = result;
                }
            });
            // 临时绑定
            formField.itemProviderInstance = itemProvider;

            // 添加该行
            addView(formItemView);
        }
    }

    public List<FormItemEntity> getFormItems() {
        return mFormItems;
    }
}
