package com.twiceyuan.autoform.provider;

import android.support.annotation.LayoutRes;
import android.view.View;

import com.twiceyuan.autoform.FormItemEntity;
import com.twiceyuan.autoform.ResultWatcher;

/**
 * Created by twiceYuan on 2017/7/2.
 * <p>
 * 基本 Form 表单显示类型，需要可以扩展并实现
 */
public interface LayoutProvider {

    @LayoutRes int layoutId();

    void bindData(FormItemEntity field);

    void resultWatcher(ResultWatcher watcher);

    void initView(View view);
}
