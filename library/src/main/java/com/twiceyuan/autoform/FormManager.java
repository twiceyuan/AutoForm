package com.twiceyuan.autoform;

import android.widget.FrameLayout;

import com.twiceyuan.autoform.annotations.Form;
import com.twiceyuan.autoform.pool.Instances;
import com.twiceyuan.autoform.provider.FormItemValidator;
import com.twiceyuan.autoform.view.FormView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by twiceYuan on 2017/7/2.
 *
 * 表单逻辑生成
 */
public class FormManager {

    private FormView mFormView;

    private FormManager() {
    }

    public static FormManager build(FrameLayout container, Class formClass) {

        Form form = (Form) formClass.getAnnotation(Form.class);
        if (form == null) {
            throw new IllegalStateException("formClass 必须含有 @Form 注解");
        }

        int childCount = container.getChildCount();
        if (childCount > 0) {
            throw new IllegalStateException("Form 不能注入，因为 container 已经包含了 Child View");
        }

        FormView formView = new FormView(container.getContext());
        formView.inflateForm(formClass);
        container.addView(formView);

        FormManager manager = new FormManager();
        manager.mFormView = formView;
        return manager;
    }

    public boolean validate() {
        List<FormItemEntity> formFields = getFormView().getFormItems();
        return validateItems(formFields);
    }

    private boolean validateItems(List<FormItemEntity> entities) {
        for (FormItemEntity formField : entities) {
            FormItemValidator validator = Instances.getFormItemValidator(formField.validator);
            if (!validator.validate(formField.result)) {
                if (formField.itemProviderInstance != null) {
                    formField.itemProviderInstance.onValidate(formField);
                }
                return false;
            }
        }
        return true;
    }

    public FormView getFormView() {
        return mFormView;
    }

    public Map<String, Object> getResult() {
        List<FormItemEntity> formFields = getFormView().getFormItems();
        Map<String, Object> results = new HashMap<>();
        for (FormItemEntity formField : formFields) {
            results.put(formField.key, formField.result);
        }
        return results;
    }
}
