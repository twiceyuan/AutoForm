package com.twiceyuan.autoform;

import com.twiceyuan.autoform.annotations.Form;
import com.twiceyuan.autoform.pool.Singletons;
import com.twiceyuan.autoform.provider.FormItemValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by twiceYuan on 2017/7/2.
 *
 * 表单逻辑生成
 */
public class FormManager {

    private FormAdapter mAdapter;

    private FormManager() {
    }

    public static FormManager build(Class formClass) {

        Form form = (Form) formClass.getAnnotation(Form.class);
        if (form == null) {
            throw new IllegalStateException("formClass 必须含有 @Form 注解");
        }

        FormManager factory = new FormManager();
        factory.mAdapter = new FormAdapter(formClass);
        return factory;
    }

    public FormAdapter getAdapter() {
        return mAdapter;
    }

    public boolean validate() {
        List<FormFieldEntity> formFields = getAdapter().getFormFields();
        for (FormFieldEntity formField : formFields) {
            FormItemValidator validator = Singletons.getFormItemValidator(formField.validator);
            if (!validator.validate(formField.result)) {
                if (formField.mItemProvider != null) {
                    formField.mItemProvider.onValidate(formField);
                }
                return false;
            }
        }
        return true;
    }

    public Map<String, Object> getResult() {
        List<FormFieldEntity> formFields = mAdapter.getFormFields();
        Map<String, Object> results = new HashMap<>();
        for (FormFieldEntity formField : formFields) {
            results.put(formField.key, formField.result);
        }
        return results;
    }
}
