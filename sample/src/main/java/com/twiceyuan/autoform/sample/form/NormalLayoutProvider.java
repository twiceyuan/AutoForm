package com.twiceyuan.autoform.sample.form;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.twiceyuan.autoform.FormItemEntity;
import com.twiceyuan.autoform.ResultWatcher;
import com.twiceyuan.autoform.provider.TextItemProvider;
import com.twiceyuan.autoform.sample.R;

/**
 * Created by twiceYuan on 2017/7/3.
 * <p>
 * 默认的表单单元
 */
public class NormalLayoutProvider implements TextItemProvider {
    private EditText mEtContent;
    private TextView mTvLabel;

    @Override
    public int layoutId() {
        return R.layout.form_item_normal;
    }

    @Override
    public void bindData(FormItemEntity field) {
        mEtContent.setHint(field.hint);
        mTvLabel.setText(field.label);

        // 初始化默认数据
        if (field.result != null && field.result instanceof String) {
            mEtContent.setText((String) field.result);
        }
    }

    @Override
    public void resultWatcher(final ResultWatcher watcher) {
        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                watcher.updateResult(s.toString());
            }
        });
    }

    @Override
    public void initView(View view) {
        mEtContent = (EditText) view.findViewById(R.id.et_content);
        mTvLabel = (TextView) view.findViewById(R.id.tv_label);
    }

    public EditText getEtContent() {
        return mEtContent;
    }

    @Override
    public EditText inputArea() {
        return mEtContent;
    }
}
