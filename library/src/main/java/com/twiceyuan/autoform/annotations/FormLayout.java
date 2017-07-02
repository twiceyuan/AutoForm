package com.twiceyuan.autoform.annotations;

import android.support.annotation.LayoutRes;

/**
 * Created by twiceYuan on 2017/7/2.
 *
 * 表单布局
 */
public @interface FormLayout {

    @LayoutRes int value();
}
