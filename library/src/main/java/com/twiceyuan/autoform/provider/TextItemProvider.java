package com.twiceyuan.autoform.provider;

import android.widget.EditText;

/**
 * Created by twiceYuan on 2017/7/3.
 *
 * 带输入框的表单元素，用于匹配通用的验证器
 */
public abstract class TextItemProvider extends FormItemProvider {

    public abstract EditText inputArea();
}
