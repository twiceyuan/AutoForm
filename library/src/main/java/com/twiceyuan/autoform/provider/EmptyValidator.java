package com.twiceyuan.autoform.provider;

/**
 * Created by twiceYuan on 2017/7/2.
 *
 * 非空验证器
 */
public class EmptyValidator implements FormItemValidator {
    @Override
    public boolean validate(Object result) {
        return true;
    }
}
