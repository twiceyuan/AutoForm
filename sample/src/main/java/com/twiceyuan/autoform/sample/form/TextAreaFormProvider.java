package com.twiceyuan.autoform.sample.form;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.twiceyuan.autoform.FormItemEntity;
import com.twiceyuan.autoform.ResultWatcher;
import com.twiceyuan.autoform.provider.TextItemProvider;
import com.twiceyuan.autoform.sample.R;

/**
 * Created by twiceYuan on 2017/7/3.
 *
 * 多行文本内容编辑器
 */
public class TextAreaFormProvider extends TextItemProvider {

    private EditText mEtContent;

    @Override
    public int layoutId() {
        return R.layout.form_item_text_area;
    }

    @Override
    public void bindData(FormItemEntity field) {
        mEtContent.setHint(field.hint);
    }

    public EditText getEtContent() {
        return mEtContent;
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
    }

    @Override
    public EditText inputArea() {
        return mEtContent;
    }
}
