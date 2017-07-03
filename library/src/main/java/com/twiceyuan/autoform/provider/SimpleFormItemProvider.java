package com.twiceyuan.autoform.provider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.twiceyuan.autoform.FormItemEntity;
import com.twiceyuan.autoform.R;
import com.twiceyuan.autoform.ResultWatcher;

/**
 * Created by twiceYuan on 2017/7/2.
 * <p>
 * 表单默认样式
 */
public class SimpleFormItemProvider extends FormItemProvider {

    private TextView mTvLabel;
    private EditText mEtContent;

    @Override
    public int layoutId() {
        return R.layout.auto_form_default_item;
    }

    @Override
    public void bindData(final FormItemEntity field) {
        mTvLabel.setText(field.label);
        mEtContent.setHint(field.hint);
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

    public void initView(View convertView) {
        mTvLabel = (TextView) convertView.findViewById(R.id.tv_label);
        mEtContent = (EditText) convertView.findViewById(R.id.et_content);
    }

    @Override
    public void onValidate(FormItemEntity entity) {
        if (mEtContent.length() == 0) {
            mEtContent.requestFocus();
            Toast.makeText(mEtContent.getContext(), entity.label + "不能为空", Toast.LENGTH_SHORT).show();
        }
    }
}
