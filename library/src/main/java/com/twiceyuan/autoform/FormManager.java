package com.twiceyuan.autoform;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.twiceyuan.autoform.annotations.Form;
import com.twiceyuan.autoform.annotations.FormField;
import com.twiceyuan.autoform.provider.DynamicFormItem;
import com.twiceyuan.autoform.provider.LayoutProvider;
import com.twiceyuan.autoform.provider.Validator;
import com.twiceyuan.autoform.util.FormInitHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by twiceYuan on 2017/7/2.
 * <p>
 * 表单逻辑生成
 */
public class FormManager {

    private static final String TAG = "FormManager";

    private List<FormItemEntity> mFormItemEntities;

    private FormManager(List<FormItemEntity> entities) {
        mFormItemEntities = entities;
    }

    public static FormManager build(Class formClass) {
        Form form = (Form) formClass.getAnnotation(Form.class);

        if (form == null) {
            throw new IllegalStateException("formClass 必须含有 @Form 注解");
        }

        List<FormItemEntity> entities = new ArrayList<>();
        FormInitHelper initHelper = FormInitHelper.build(form);

        // 获取注解了 FormField 的字段，传递到 FormFieldEntity 实体中
        for (Field field : formClass.getDeclaredFields()) {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);

            FormField formField = field.getAnnotation(FormField.class);
            if (formField != null) {
                FormItemEntity formItemEntity = initHelper.initFormField(field, formField);

                entities.add(formItemEntity);
            }

            field.setAccessible(accessible);
        }

        resort(entities);

        return new FormManager(entities);
    }

    /**
     * 对字段根据 order 排序
     *
     * @param entities 需要排序的字段
     */
    private static void resort(List<FormItemEntity> entities) {
        // 根据 order 值排序字段
        Collections.sort(entities, new Comparator<FormItemEntity>() {
            @Override
            public int compare(FormItemEntity f1, FormItemEntity f2) {
                return Float.compare(f1.order, f2.order);
            }
        });
    }

    public FormManager inject(FrameLayout container) {
        return inject(container, false);
    }

    public FormManager append(DynamicFormItem formItem) {

        FormItemEntity entity = new FormItemEntity();
        entity.hint = formItem.hint();
        entity.key = formItem.key();
        entity.label = formItem.label();
        entity.order = formItem.order();
        entity.layout = formItem.layoutProvider();
        entity.validator = formItem.validator();
        mFormItemEntities.add(entity);
        resort(mFormItemEntities);
        return this;
    }

    public FormManager inject(FrameLayout container, boolean isForce) {

        int childCount = container.getChildCount();
        if (childCount > 0 && !isForce) {
            throw new IllegalStateException("Form 不能注入，因为 container 已经包含了 Child View");
        } else {
            container.removeAllViews();
        }

        LinearLayout formLayout = new LinearLayout(container.getContext());
        formLayout.setOrientation(LinearLayout.VERTICAL);
        formLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        List<View> views = formViews(formLayout);
        for (View formItemView : views) {
            formLayout.addView(formItemView);
        }

        container.addView(formLayout);
        return this;
    }

    public <T extends LayoutProvider> T findLayoutProviderByKey(String key) {
        for (FormItemEntity entity : mFormItemEntities) {
            if (entity.key.equals(key)) {
                //noinspection unchecked
                return (T) entity.layout;
            }
        }
        return null;
    }

    public FormItemEntity findFormEntityByKey(String key) {
        for (FormItemEntity entity : mFormItemEntities) {
            if (entity.key.equals(key)) {
                return entity;
            }
        }
        return null;
    }

    public <T extends Validator> T findValidatorByKey(String key) {
        for (FormItemEntity entity : mFormItemEntities) {
            if (entity.key.equals(key)) {
                //noinspection unchecked
                return (T) entity.validator;
            }
        }
        return null;
    }

    public List<View> formViews(ViewGroup container) {

        List<View> formViews = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(container.getContext());

        for (final FormItemEntity formField : mFormItemEntities) {
            LayoutProvider layoutProvider = formField.layout;

            // 根据 LayoutId Inflate View
            View formItemView = inflater.inflate(layoutProvider.layoutId(), container, false);

            // 绑定 ViewProvider 中的控件
            layoutProvider.initView(formItemView);

            // 初始化模板数据
            layoutProvider.bindData(formField);

            layoutProvider.resultWatcher(new ResultWatcher() {
                @Override
                public void updateResult(Object result) {
                    formField.result = result;
                }
            });

            // 添加该行
            formViews.add(formItemView);
        }

        return formViews;
    }

    public boolean validate() {
        return validateItems(mFormItemEntities);
    }

    private boolean validateItems(List<FormItemEntity> entities) {
        for (FormItemEntity formField : entities) {
            Validator validator = formField.validator;
            if (!validator.validate(formField.result)) {
                if (formField.layout != null) {
                    try {
                        // 这里会有类型不匹配的情况，原因是使用了自定义的 layoutProvider 却没有适配对应的检查器
                        //noinspection unchecked
                        validator.onValidateFailed(formField, formField.layout);
                    } catch (Exception ignored) {
                        Log.e(TAG, String.format("Validator type does not match: validator is %s, but layoutProvider is %s",
                                validator.getClass().getName(),
                                formField.layout.getClass().getName()));
                    }
                }
                return false;
            }
        }
        return true;
    }

    public Map<String, Object> getResult() {
        Map<String, Object> results = new HashMap<>();
        for (FormItemEntity formField : mFormItemEntities) {
            results.put(formField.key, formField.result);
        }
        return results;
    }
}
