package com.twiceyuan.autoform.provider;

import com.twiceyuan.autoform.FormItemEntity;

/**
 * Created by twiceYuan on 2017/7/2.
 *
 * 表单输入验证器
 */
public interface Validator<ItemProvider extends LayoutProvider> {

    void onValidateFailed(FormItemEntity entity, ItemProvider provider);

    boolean validate(Object result);
}
