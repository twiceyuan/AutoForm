package com.twiceyuan.autoform;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.twiceyuan.autoform.provider.FormItemProvider;

/**
 * Created by twiceYuan on 2017/7/2.
 *
 * 表单 ViewHolder
 */
public class FormHolder extends RecyclerView.ViewHolder {

    private FormItemProvider mFormItemProvider;

    public FormHolder(View itemView, FormItemProvider provider) {
        super(itemView);
        mFormItemProvider = provider;
    }

    protected void bindData(final FormFieldEntity field) {
        mFormItemProvider.bindData(field);
        mFormItemProvider.resultWatcher(new ResultWatcher() {
            @Override
            public void updateResult(Object result) {
                field.result = result;
            }
        });
        // 临时绑定
        field.mItemProvider = mFormItemProvider;
    }
}
