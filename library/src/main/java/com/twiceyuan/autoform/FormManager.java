package com.twiceyuan.autoform;

import android.text.TextUtils;
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
import com.twiceyuan.autoform.util.Instances;

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
public class FormManager<T> {

    private static final String TAG = "FormManager";

    private Class<T>             mDataClass;
    private List<FormItemEntity> mFormItemEntities;

    private FormManager(List<FormItemEntity> entities, Class<T> dataClass) {
        mFormItemEntities = entities;
        mDataClass = dataClass;
    }

    public static <T> FormManager<T> build(Class<T> formClass) {

        Form form = formClass.getAnnotation(Form.class);

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

        return new FormManager<>(entities, formClass);
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

    /**
     * 默认生成一个 LinearLayout，add 传入的 FrameLayout 中。FrameLayout 中不能有其他 View
     *
     * @param container 放置表单的布局
     * @return 表单管理器对象
     */
    @SuppressWarnings("unused")
    public FormManager<T> inject(FrameLayout container) {
        return inject(container, false);
    }

    /**
     * 默认生成一个 LinearLayout，add 传入的 FrameLayout 中。当 FrameLayout 中有其他 Child View 时，传入 isForce 值为 true 会
     * 移除其他所有 childView
     *
     * @param container 放置表单的布局
     * @param isForce   是否移除原有的 Child View
     * @return 表单管理器对象
     */
    public FormManager<T> inject(FrameLayout container, boolean isForce) {

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

        List<View> views = getAllFormViews(formLayout);
        for (View formItemView : views) {
            formLayout.addView(formItemView);
        }

        container.addView(formLayout);
        return this;
    }

    /**
     * 初始化一个实体数据，用户初始化表单默认输入
     *
     * @param data 数据实体
     * @return manager 实例
     */
    public FormManager<T> initData(T data) {
        for (FormItemEntity entity : mFormItemEntities) {
            try {
                entity.result = mDataClass.getField(entity.fieldName).get(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    /**
     * 动态添加一个表单元素
     *
     * @param formItem 构造出来的表单元素
     * @return 表单管理器对象
     */
    @SuppressWarnings("unused")
    public FormManager<T> append(DynamicFormItem formItem) {

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

    /**
     * 根据 key 查找一个布局管理器
     *
     * @param key        字段的 key
     * @param <Provider> Provider 的类型，可以由前置定义决定
     * @return 对应的 layoutProvider
     */
    public <Provider extends LayoutProvider> Provider findLayoutProviderByKey(String key) {
        for (FormItemEntity entity : mFormItemEntities) {
            if (entity.key.equals(key)) {
                //noinspection unchecked
                return (Provider) entity.layout;
            }
        }
        return null;
    }

    /**
     * 根据 key 查找一个表单元素
     *
     * @param key 字段的 key
     * @return 对应的 formEntity
     */
    public FormItemEntity findFormEntityByKey(String key) {
        for (FormItemEntity entity : mFormItemEntities) {
            if (entity.key.equals(key)) {
                return entity;
            }
        }
        return null;
    }

    /**
     * 根据 key 查找一个校验器
     *
     * @param key 字段的 key
     * @param <T> 校验器的类型，可以由前置定义决定
     * @return 对应的校验器
     */
    @SuppressWarnings("unused")
    public <T extends Validator> T findValidatorByKey(String key) {
        for (FormItemEntity entity : mFormItemEntities) {
            if (entity.key.equals(key)) {
                //noinspection unchecked
                return (T) entity.validator;
            }
        }
        return null;
    }

    /**
     * Inflate 并获取所有的表单单元 View，表单单元之间是有序的。此方法主要是用来自由操作表单样式，在表单之间插入表单无关元素、分割线等。
     *
     * @param container 表单的容器
     * @return 表单所有元素的 View 集合。
     */
    @SuppressWarnings("WeakerAccess")
    public List<View> getAllFormViews(ViewGroup container) {

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

    /**
     * 校验所有表单输入，调用该方法后，表单会根据 FormItem 配置的校验器来校验数据，没有配置校验器时，默认使用非空校验器。如果一个字段
     * 不需要校验，可以配置 {@link com.twiceyuan.autoform.provider.PassValidator} 来跳过校验
     *
     * @return 校验是否成功
     */
    public boolean validate() {
        return validateItems(mFormItemEntities);
    }

    /**
     * 内部校验功能
     *
     * @param entities 需要校验的单元
     * @return 校验是否成功
     */
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

    /**
     * 获取所有输出结果，如果需要校验，请先调用 {@link this#validate()} 方法进行校验再获取
     *
     * @return 表单当前的输入结果
     */
    public Map<String, Object> getResult() {
        Map<String, Object> results = new HashMap<>();
        for (FormItemEntity formField : mFormItemEntities) {
            results.put(formField.key, formField.result);
        }
        return results;
    }

    /**
     * 获取所有输出结果，如果需要校验，请先调用 {@link this#validate()} 方法进行校验再获取
     *
     * @return 表单当前的输入结果
     */
    public T getData() {
        T data = Instances.newInstance(mDataClass);
        Field[] declaredFields = mDataClass.getDeclaredFields();
        Map<String, Field> keyFieldMaps = new HashMap<>();

        for (Field declaredField : declaredFields) {
            FormField formField = declaredField.getAnnotation(FormField.class);
            if (formField != null && !TextUtils.isEmpty(formField.key())) {
                keyFieldMaps.put(formField.key(), declaredField);
            } else {
                keyFieldMaps.put(declaredField.getName(), declaredField);
            }
        }

        for (FormItemEntity formField : mFormItemEntities) {
            Field field = keyFieldMaps.get(formField.key);
            boolean accessible = field.isAccessible();
            if (!accessible) {
                field.setAccessible(true);
            }

            try {
                field.set(data, formField.result);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            field.setAccessible(accessible);
        }
        return data;
    }
}
