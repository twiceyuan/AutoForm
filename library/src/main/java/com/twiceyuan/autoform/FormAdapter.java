package com.twiceyuan.autoform;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twiceyuan.autoform.annotations.Form;
import com.twiceyuan.autoform.annotations.FormField;
import com.twiceyuan.autoform.pool.Singletons;
import com.twiceyuan.autoform.provider.FormItemProvider;
import com.twiceyuan.autoform.util.FormInitHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by twiceYuan on 2017/7/2.
 * <p>
 * 表单适配器
 */
public class FormAdapter extends RecyclerView.Adapter<FormHolder> {

    private List<FormFieldEntity>             mFormFields;
    private TreeMap<Integer, FormFieldEntity> mFormFieldMap;

    public FormAdapter(Class formDefineClass) {
        Field[] fields = formDefineClass.getDeclaredFields();

        mFormFields = new ArrayList<>();
        mFormFieldMap = new TreeMap<>();

        Form form = (Form) formDefineClass.getAnnotation(Form.class);

        FormInitHelper initHelper = FormInitHelper.build(form);

        for (Field field : fields) {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);

            FormField formField = field.getAnnotation(FormField.class);
            if (formField != null) {
                FormFieldEntity formFieldEntity = initHelper.initFormField(field, formField);

                mFormFieldMap.put(formField.itemProvider().hashCode(), formFieldEntity);
                mFormFields.add(formFieldEntity);
            }

            field.setAccessible(accessible);
        }

        Collections.sort(mFormFields, new Comparator<FormFieldEntity>() {
            @Override
            public int compare(FormFieldEntity f1, FormFieldEntity f2) {
                return f1.order - f2.order;
            }
        });
    }


    @Override
    public FormHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FormFieldEntity formField = mFormFieldMap.get(viewType);
        FormItemProvider provider = Singletons.getFormItemProvider(formField.itemProvider);
        int layoutId = provider.layoutId();
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        FormHolder formHolder = new FormHolder(view, provider);
        provider.initView(view);
        return formHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return mFormFields.get(position).itemProvider.hashCode();
    }

    @Override
    public void onBindViewHolder(FormHolder holder, int position) {
        holder.bindData(mFormFields.get(position));
    }

    @Override
    public int getItemCount() {
        return mFormFields.size();
    }

    public List<FormFieldEntity> getFormFields() {
        return mFormFields;
    }
}
