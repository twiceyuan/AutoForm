package com.twiceyuan.autoform.provider;

import com.twiceyuan.autoform.FormItemEntity;

/**
 * Created by twiceYuan on 2017/7/2.
 * <p>
 * 不进行验证验证器
 */
@SuppressWarnings("unused")
public class PassValidator implements Validator<LayoutProvider> {

    @Override
    public void onValidateFailed(FormItemEntity entity, LayoutProvider provider) {
        // do nothing
    }

    @Override
    public boolean validate(Object result) {
        return true;
    }
}
