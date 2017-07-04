package com.twiceyuan.autoform.provider;

import android.widget.EditText;

/**
 * Created by twiceYuan on 2017/7/3.
 *
 * 带输入框的表单元素，用于匹配通用的验证器
 */
public interface TextItemProvider extends LayoutProvider {

    /**
     * 以文字输入为主要输入的表单元素，可以直接继承该布局提供者来共享默认的文本校验器
     *
     * @return 表单元素主要的文本输入框
     */
    EditText inputArea();
}
